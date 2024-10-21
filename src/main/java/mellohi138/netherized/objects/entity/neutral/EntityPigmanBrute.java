package mellohi138.netherized.objects.entity.neutral;

import mellohi138.netherized.Netherized;
import mellohi138.netherized.client.model.animation.EZAnimation;
import mellohi138.netherized.init.NetherizedItems;
import mellohi138.netherized.init.NetherizedSounds;
import mellohi138.netherized.util.config.NetherizedEntitiesConfig;
import mellohi138.netherized.util.config.NetherizedGeneralConfig;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Predicate;

public class EntityPigmanBrute extends EntityMob {

    protected static final DataParameter<Boolean> MELEE_ATTACK = EntityDataManager.createKey(EntityPigmanBrute.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> IMMOVABLE = EntityDataManager.createKey(EntityPigmanBrute.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> FIGHT_MODE = EntityDataManager.createKey(EntityPigmanBrute.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> INSIDE_BASTION = EntityDataManager.createKey(EntityPigmanBrute.class, DataSerializers.BOOLEAN);

    protected void setMeleeAttack(boolean value) {
        this.dataManager.set(MELEE_ATTACK, value);
    }

    public boolean isMeleeAttack() {
        return this.dataManager.get(MELEE_ATTACK);
    }

    protected boolean isImmovable() {
        return this.dataManager == null ? false : this.dataManager.get(IMMOVABLE);
    }

    protected void setImmovable(boolean immovable) {
        this.dataManager.set(IMMOVABLE, immovable);
    }

    public boolean isFightMode() {
        return this.dataManager.get(FIGHT_MODE);
    }

    protected void setFightMode(boolean value) {
        this.dataManager.set(FIGHT_MODE, value);
    }

    public boolean isInsideBastion() {
        return this.dataManager.get(INSIDE_BASTION);
    }

    public void setInsideBastion(boolean value) {
        this.dataManager.set(INSIDE_BASTION, value);
    }

    //used for animation system
    private int animationTick;
    //just a variable that holds what the current animation is
    private EZAnimation currentAnimation;

    public EntityPigmanBrute(World worldIn) {
        super(worldIn);
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(NetherizedItems.BRUTE_AXE));
        this.experienceValue = 20;
        this.isImmuneToFire = true;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(MELEE_ATTACK, Boolean.FALSE);
        this.dataManager.register(IMMOVABLE, Boolean.FALSE);
        this.dataManager.register(FIGHT_MODE, Boolean.FALSE);
        this.dataManager.register(INSIDE_BASTION, Boolean.FALSE);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setBoolean("Melee_Attack", this.isMeleeAttack());
        nbt.setBoolean("Fight_Mode", this.isFightMode());
        nbt.setBoolean("Immovable", this.isImmovable());
        nbt.setBoolean("Inside_Bastion", this.isInsideBastion());

    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setMeleeAttack(nbt.getBoolean("Melee_Attack"));
        this.setFightMode(nbt.getBoolean("Fight_Mode"));
        this.setImmovable(nbt.getBoolean("Immovable"));
        this.setInsideBastion(nbt.getBoolean("Inside_Bastion"));
    }

    protected boolean hasPlayedAngrySound = false;

    private int dimensionCheck = 40;
    private int countDownToZombie = NetherizedEntitiesConfig.zombification_time * 20;

    public boolean convertTooZombie = false;
    private int tickOut = 200;

    @Override
    public void onUpdate() {
        super.onUpdate();

        EntityLivingBase target = this.getAttackTarget();

        if (target != null && !hasPlayedAngrySound) {
            this.playSound(NetherizedSounds.ENTITY_PIGLIN_BRUTE_SNORT_ANGRY, 1.0f, 1.0f / (rand.nextFloat() * 0.4f + 0.5f));
            //Allows Brutes to call on nearby Piglins to aid
            List<EntityPigman> nearbyPiglins = this.world.getEntitiesWithinAABB(EntityPigman.class, this.getEntityBoundingBox().grow(12D), e -> !e.getIsInvulnerable());
            if (!nearbyPiglins.isEmpty()) {
                for (EntityPigman piglin : nearbyPiglins) {
                    piglin.setAttackTarget(target);
                }
            }
            hasPlayedAngrySound = true;
        } else if (target == null) {
            hasPlayedAngrySound = false;
        }

        //helper to set targeted to null if the intended target is dead
        if (target != null && !world.isRemote) {
            boolean canSee = this.getEntitySenses().canSee(target);

            if (!target.isEntityAlive() || !canSee) {
                if (tickOut < 0) {
                    this.setAttackTarget(null);
                    tickOut = 200;
                } else {
                    tickOut--;
                }
            }


        }

        if (dimensionCheck < 0) {
            if (this.world.provider.getDimension() != -1) {
                //Start Zombification Process
                if (countDownToZombie < 0 && !this.convertTooZombie) {
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
    }

    @Override
    public boolean isOnSameTeam(Entity entity) {
        return entity == this || entity instanceof EntityPigman;
    }


    private void beginZombieTransformation() {
        if (!world.isRemote) {
            addEvent(() -> this.playSound(NetherizedSounds.ENTITY_PIGLIN_CONVERSION, 1.0f, 0.8f), 75);
            addEvent(() -> {
                EntityPigZombie zombie = new EntityPigZombie(world);
                zombie.copyLocationAndAnglesFrom(this);
                zombie.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, this.getHeldItemMainhand());
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
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(NetherizedEntitiesConfig.piglin_brute_health * NetherizedGeneralConfig.healthScale);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(9D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(20D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(NetherizedEntitiesConfig.piglin_brute_armor);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(NetherizedEntitiesConfig.piglin_brute_armor_toughness);
    }


    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.2D, false));
        this.tasks.addTask(3, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 1, true, false, null));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<>(this, EntityPigZombie.class, 1, true, false, null));
        this.targetTasks.addTask(5, new EntityAINearestAttackableTarget<>(this, EntityWitherSkeleton.class, 1, true, false, null));
    }

    @Override
    protected boolean canDespawn() {
        if (this.isInsideBastion()) {
            return false;
        }
        // Edit this to restricting them not despawning in Dungeons
        return this.ticksExisted > 20 * 60 * 20;

    }

    private static final ResourceLocation LOOT = new ResourceLocation(Netherized.MODID, "piglin_brute");

    @Override
    protected ResourceLocation getLootTable() {
        return null;
    }

    public static final Predicate<Entity> CAN_TARGET = entity -> !(entity instanceof EntityPigman || entity instanceof EntityPigmanBrute);

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {


        if (!CAN_TARGET.test(source.getTrueSource())) {
            return false;
        }

        return super.attackEntityFrom(source, amount);
    }


    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return NetherizedSounds.ENTITY_PIGLIN_BRUTE_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return NetherizedSounds.ENTITY_PIGLIN_BRUTE_SNORT;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(NetherizedSounds.ENTITY_PIGLIN_BRUTE_STEP, 0.15F, 1.0f / (rand.nextFloat() * 0.4F + 0.2f));
    }

    @Override
    protected SoundEvent getDeathSound() {
        return NetherizedSounds.ENTITY_PIGLIN_BRUTE_DEATH;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (!isDead && this.getHealth() > 0) {
            boolean foundEvent = true;
            while (foundEvent) {
                EntityPigman.TimedEvent event = events.peek();
                if (event != null && event.ticks <= this.ticksExisted) {
                    events.remove();
                    event.callback.run();
                } else {
                    foundEvent = false;
                }
            }
        }
    }

    private PriorityQueue<EntityPigman.TimedEvent> events = new PriorityQueue<EntityPigman.TimedEvent>();

    public void addEvent(Runnable runnable, int ticksFromNow) {
        events.add(new EntityPigman.TimedEvent(runnable, this.ticksExisted + ticksFromNow));
    }

    public class TimedEvent implements Comparable<EntityPigman.TimedEvent> {
        Runnable callback;
        int ticks;

        public TimedEvent(Runnable callback, int ticks) {
            this.callback = callback;
            this.ticks = ticks;
        }

        @Override
        public int compareTo(EntityPigman.TimedEvent event) {
            return event.ticks < ticks ? 1 : -1;
        }
    }
}