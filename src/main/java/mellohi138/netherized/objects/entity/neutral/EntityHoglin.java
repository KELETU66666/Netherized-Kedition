package mellohi138.netherized.objects.entity.neutral;

import com.google.common.collect.Sets;
import mellohi138.netherized.Netherized;
import mellohi138.netherized.client.model.animation.EZAnimation;
import mellohi138.netherized.client.model.animation.EZAnimationHandler;
import mellohi138.netherized.client.model.animation.IAnimatedEntity;
import mellohi138.netherized.client.model.animation.IAttack;
import mellohi138.netherized.init.NetherizedBlocks;
import mellohi138.netherized.init.NetherizedSounds;
import mellohi138.netherized.objects.entity.EntityNetherAnimalBase;
import mellohi138.netherized.objects.entity.ai.EntityAIAvoidBlock;
import mellohi138.netherized.objects.entity.ai.EntityAIMateHoglin;
import mellohi138.netherized.objects.entity.ai.EntityAITemptHoglin;
import mellohi138.netherized.objects.entity.ai.EntityTimedAttackHoglin;
import mellohi138.netherized.util.ModRand;
import mellohi138.netherized.util.ModUtils;
import mellohi138.netherized.util.config.NetherizedEntitiesConfig;
import mellohi138.netherized.util.config.NetherizedGeneralConfig;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Set;

public class EntityHoglin extends EntityNetherAnimalBase implements IAttack, IAnimatedEntity {

    public static final EZAnimation ANIMATION_ATTACK_MELEE = EZAnimation.create(20);

    //used for animation system
    private int animationTick;
    //just a variable that holds what the current animation is
    private EZAnimation currentAnimation;
    private EntityAITemptHoglin temptationAI;
    private static final Set<Item> TEMPTATION_ITEMS = Sets.newHashSet(Item.getItemFromBlock(NetherizedBlocks.CRIMSON_FUNGUS));

    private boolean initChildAI = false;
    public EntityHoglin(World worldIn) {
        super(worldIn);
        this.setSize(1.9F, 1.8F);
        this.experienceValue = 5;
        this.isImmuneToFire = true;
    }

    private boolean hasPlayedAngrySound = false;

    private int dimensionCheck = 40;
    private int countDownToZombie = NetherizedEntitiesConfig.zombification_time * 20;

    @Override
    public boolean getCanSpawnHere()
    {
       return true;
    }
    public boolean convertTooZombie = false;
    @Override
    public void onUpdate() {
        super.onUpdate();
        //Animation stuff
        if(this.isFightMode() && this.getAnimation() == NO_ANIMATION) {
            this.setAnimation(ANIMATION_ATTACK_MELEE);
        }

        if(this.isChild() && !initChildAI) {
            this.initChildAITasks();
        }

        EntityLivingBase target = this.getAttackTarget();

        if(target != null && !hasPlayedAngrySound) {
            this.playSound(NetherizedSounds.ENTITY_HOGLIN_GROWL_ANGRY, 1.0f, 1.0f / (rand.nextFloat() * 0.4f + 0.5f));
            hasPlayedAngrySound = true;
        } else if (target == null) {
            hasPlayedAngrySound = false;
        }

        if(dimensionCheck < 0) {
            if(this.world.provider.getDimension() != -1) {
                //Start Zombification Process
                if(countDownToZombie < 0 && !this.convertTooZombie) {
                    this.setAttackTarget(null);
                    this.setImmovable(true);
                    this.convertTooZombie = true;
                    this.beginZombieTransformation();
                } else {
                    countDownToZombie--;
                }
            } else {
                dimensionCheck = 40;
            }
        } else {
            dimensionCheck--;
        }

        //NEVER FORGET THIS, OR ELSE ANIMATIONS WILL NOT WORK
        EZAnimationHandler.INSTANCE.updateAnimations(this);
    }


    private boolean isTempted() {
        return this.temptationAI != null && this.temptationAI.isRunning();
    }

    private void beginZombieTransformation() {
        if(!world.isRemote) {
            addEvent(() -> this.playSound(NetherizedSounds.ENTITY_HOGLIN_CONVERSION, 1.0f, 0.8f), 75);
            addEvent(() -> {
                EntityPigZombie zombie = new EntityPigZombie(world);
                zombie.copyLocationAndAnglesFrom(this);
                //if(this.isChild()) {
                //    zombie.setGrowingAge(-24000);
                //}
                zombie.setPosition(this.posX, this.posY, this.posZ);
                zombie.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 1));
                this.setDead();
                this.world.spawnEntity(zombie);
            }, 100);
        }
    }


    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(NetherizedEntitiesConfig.hoglin_health * NetherizedGeneralConfig.healthScale);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.6D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(NetherizedEntitiesConfig.hoglin_armor);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(NetherizedEntitiesConfig.hoglin_armor_toughness);
    }


    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAIMateHoglin(this, 1.0D));
       // this.tasks.addTask(2, new EntityAITempt(this, 1.4D, false, TEMPTATION_ITEMS));
        this.temptationAI = new EntityAITemptHoglin(this, 1.4D, false, TEMPTATION_ITEMS);
        this.tasks.addTask(2, temptationAI);
        this.tasks.addTask(3, new EntityTimedAttackHoglin<>(this, 1.6D, 60, 3, 0.3F));
        this.tasks.addTask(4, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(5, new EntityAIFollowParent(this, 1.0D));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 1, true, false, null));
        this.targetTasks.addTask(2, new EntityAIAvoidBlock(this, 1.2D, Arrays.asList(NetherizedBlocks.WARPED_FUNGUS, NetherizedBlocks.RESPAWN_ANCHOR, Blocks.PORTAL)));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));

    }

    protected void initChildAITasks() {
        this.tasks.addTask(5, new EntityAIPanic(this, 1.5D));
        initChildAI = true;
    }

    @Override
    public int getAnimationTick() {
        return animationTick;
    }

    @Override
    public void setAnimationTick(int tick) {
        animationTick = tick;
    }

    @Override
    public EZAnimation getAnimation() {
        return currentAnimation;
    }

    @Override
    public void setAnimation(EZAnimation animation) {
        currentAnimation = animation;
    }

    @Override
    public EZAnimation[] getAnimations() {
        return new EZAnimation[]{ANIMATION_ATTACK_MELEE};
    }


    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return NetherizedSounds.ENTITY_HOGLIN_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return NetherizedSounds.ENTITY_HOGLIN_GROWL;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(NetherizedSounds.ENTITY_HOGLIN_STEP, 0.15F, 1.0f / (rand.nextFloat() * 0.4F + 0.2f));
    }

    @Override
    protected boolean canDespawn() {
        // Edit this to restricting them not despawning in Dungeons
        return false;

    }

    @Override
    protected SoundEvent getDeathSound() {
        return NetherizedSounds.ENTITY_HOGLIN_DEATH;
    }

    @Override
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards) {
        this.setFightMode(true);
        addEvent(()-> this.playSound(NetherizedSounds.ENTITY_HOGLIN_ATTACK, 1.0f, 1.0f), 10);
        addEvent(()-> {
            Vec3d offset = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.0, 0.5, 0)));
            DamageSource source = DamageSource.causeMobDamage(this);
            float damage;
            if(this.isChild()) {
                damage = (float) (1F * NetherizedGeneralConfig.attackDamageScale);
            } else {
                damage = (float)(NetherizedEntitiesConfig.hoglin_attack_damange * NetherizedGeneralConfig.attackDamageScale);
            }
            ModUtils.handleAreaImpact(1.0f, (e)-> damage, this, offset, source, 0.9f, 0, false);
        }, 13);
        addEvent(()-> this.setFightMode(false), 20);
        return 20;
    }


    @Override
    public float getBlockPathWeight(BlockPos pos)
    {
        return 0.0F;
    }

    private static final ResourceLocation LOOT = new ResourceLocation(Netherized.MODID, "hoglin");

    @Override
    protected ResourceLocation getLootTable() {
        if(!this.isChild()) {
            return LOOT;
        }
        return null;
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        if (this.world.rand.nextInt(7) == 0)
        {
            for (int i = 0; i < 2; ++i)
            {
                EntityHoglin fox = new EntityHoglin(this.world);
                fox.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                fox.setGrowingAge(-24000);
                this.world.spawnEntity(fox);
            }
        }

        return livingdata;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack)
    {
        return stack.getItem() == TEMPTATION_ITEMS;
    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        EntityHoglin baby_hoglin = new EntityHoglin(world);

        return baby_hoglin;
    }
}