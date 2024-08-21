package mellohi138.netherized.objects.entity.hostile;

import mellohi138.netherized.objects.entity.ai.EntityAIInfernoFight;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityHoveringInferno extends EntityBlaze {
    private static final DataParameter<Boolean> IS_SHIELDING = EntityDataManager.<Boolean>createKey(EntityHoveringInferno.class, DataSerializers.BOOLEAN);
    
	public EntityHoveringInferno(World worldIn) {
		super(worldIn);
        this.setSize(0.8F, 2.0F);
        this.setPathPriority(PathNodeType.WATER, -1.0F);
        this.setPathPriority(PathNodeType.LAVA, 8.0F);
        this.setPathPriority(PathNodeType.DANGER_FIRE, 0.0F);
        this.setPathPriority(PathNodeType.DAMAGE_FIRE, 0.0F);
        this.isImmuneToFire = true;
        this.experienceValue = 20;
	}
	
    protected void initEntityAI() {
        this.tasks.addTask(4, new EntityAIInfernoFight(this));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D, 0.0F));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 12.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, EntityBlaze.class));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
    }
	
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.4D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(48.0D);
    }
	
	public boolean attackEntityFrom(DamageSource source, float amount) {
    	if(!this.world.isRemote) {
    		if(source.getImmediateSource() instanceof EntityLivingBase && this.isShielding()) {
    			EntityLivingBase attacker = (EntityLivingBase)source.getImmediateSource();
    			ItemStack stack = attacker.getHeldItemMainhand();
    			if(stack.getItem().canDisableShield(stack, null, this, attacker)) {
					double itemDamage = stack.getItem().getAttributeModifiers(EntityEquipmentSlot.MAINHAND, stack).get(SharedMonsterAttributes.ATTACK_DAMAGE.getName()).iterator().next().getAmount() * 1.5F;
    				if (amount >= itemDamage) {
    					this.playSound(SoundEvents.ITEM_SHIELD_BREAK, 1.0F, 1.0F);
    					this.setShielding(false);
    					return false;
    				}
    			} else {
     				this.playSound(SoundEvents.ITEM_SHIELD_BLOCK, 1.0F, 1.0F);
                    if (source.getImmediateSource() != null) {
                        source.getImmediateSource().setFire(source.isProjectile() ? 12 : 8);
                    }
     				return false;
    			}
    		}
    	}
    	return super.attackEntityFrom(source, amount);
    }
    
    protected void entityInit() {
        this.dataManager.register(IS_SHIELDING, Boolean.valueOf(false));
        super.entityInit();
    }
    
    @Override
    public void onLivingUpdate() { 
        if (!this.onGround && this.motionY < 0.0D) {
            this.motionY *= 0.6D;
        }
        
        if (this.world.isRemote) {
            if (this.rand.nextInt(24) == 0 && !this.isSilent()) {
                this.world.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, SoundEvents.ENTITY_BLAZE_BURN, this.getSoundCategory(), 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
            }
            
            for (int i = 0; i < 2; ++i) {
            	if(this.isShielding()) {
                    this.world.spawnParticle(EnumParticleTypes.LAVA, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
            	}
            }
        }
        
        super.onLivingUpdate();
    }
    
    public void setShielding(boolean shielding) {
    	this.dataManager.set(IS_SHIELDING, shielding);
    }

    public boolean isShielding() {
        return this.dataManager.get(IS_SHIELDING).booleanValue();
    }
    
    @Override
    public float getEyeHeight() {
        return 1.8F;
    }
    
    @Override
    protected float getSoundPitch() {
    	return 0.75F;
    }
    
    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
    	return null;
    }
}
