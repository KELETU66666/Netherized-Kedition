package mellohi138.netherized.objects.entity.neutral;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import java.util.UUID;

public class EntityPiglin extends EntityAgeable {
	private static final DataParameter<Boolean> IS_CHILD = EntityDataManager.<Boolean>createKey(EntityPiglin.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> IS_IMMUNE_TO_ZOMBIFICATION = EntityDataManager.<Boolean>createKey(EntityPiglin.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> IS_DANCING = EntityDataManager.<Boolean>createKey(EntityPiglin.class, DataSerializers.BOOLEAN);
	
	private static final UUID BABY_SPEED_MODIFIER_IDENTIFIER = UUID.fromString("766bfa64-11f3-11ea-8d71-362b9e155667");
	private static final AttributeModifier BABY_SPEED_MODIFIER = new AttributeModifier(BABY_SPEED_MODIFIER_IDENTIFIER, "Baby speed boost", (double)0.2F, 1);
	private final InventoryBasic piglinInventory;
	
	protected int timeInOverworld = 0;
	private boolean cannotHunt = false;
	
	public EntityPiglin(World worldIn) {
		super(worldIn);
		this.piglinInventory = new InventoryBasic("Items", false, 8);
		this.experienceValue = 5;
		this.setCanPickUpLoot(true);
		this.setSize(0.6F, 1.95F);
        ((PathNavigateGround)this.getNavigator()).setBreakDoors(true);
		this.setPathPriority(PathNodeType.DANGER_FIRE, 16.0F);
		this.setPathPriority(PathNodeType.DAMAGE_FIRE, -1.0F);
	}
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0F);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0F);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35F);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(36.0F);
    }
	
	@Override
    protected void entityInit() {
    	super.entityInit();
    	this.getDataManager().register(IS_CHILD, false);
    	this.getDataManager().register(IS_DANCING, false);
    }
	
	@Override
    public void writeEntityToNBT(NBTTagCompound compound) {
    	super.writeEntityToNBT(compound);
    	
    	compound.setInteger("TimeInOverworld", this.timeInOverworld);
        
    	if (this.getIsImmuneToZombification()) {
    		compound.setBoolean("IsImmuneToZombification", true);
    	}
    	
    	if (this.isChild()) {
    		compound.setBoolean("IsBaby", true);
    	}
    	
    	if (this.cannotHunt) {
    		compound.setBoolean("CannotHunt", false);
    	}
         
    	NBTTagList NBTTagList = new NBTTagList();
    	
        for (int i = 0; i < this.piglinInventory.getSizeInventory(); ++i) {
            ItemStack itemstack = this.piglinInventory.getStackInSlot(i);

            if (!itemstack.isEmpty()) {
            	NBTTagList.appendTag(itemstack.writeToNBT(new NBTTagCompound()));
            }
        }

        compound.setTag("Inventory", NBTTagList);
    }

	@Override
    public void readEntityFromNBT(NBTTagCompound compound) {
    	super.readEntityFromNBT(compound);
    	this.setIsImmuneToZombification(compound.getBoolean("IsImmuneToZombification"));
    	this.timeInOverworld = compound.getInteger("TimeInOverworld");
    	
        this.setChild(compound.getBoolean("IsBaby"));
        this.setCannotHunt(compound.getBoolean("CannotHunt"));
        
        NBTTagList NBTTagList = compound.getTagList("Inventory", 10);

        for (int i = 0; i < NBTTagList.tagCount(); ++i) {
            ItemStack itemStack = new ItemStack(NBTTagList.getCompoundTagAt(i));

            if (!itemStack.isEmpty()) {
                this.piglinInventory.addItem(itemStack);
            }
        }
    }
	
	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		EntityPiglin piglin = new EntityPiglin(this.world);
		return piglin;
	}
    
    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
        if (!this.isChild()) {
           this.setEquipmentBasedOnRNG(EntityEquipmentSlot.HEAD, new ItemStack(Items.GOLDEN_HELMET));
           this.setEquipmentBasedOnRNG(EntityEquipmentSlot.CHEST, new ItemStack(Items.GOLDEN_CHESTPLATE));
           this.setEquipmentBasedOnRNG(EntityEquipmentSlot.LEGS, new ItemStack(Items.GOLDEN_LEGGINGS));
           this.setEquipmentBasedOnRNG(EntityEquipmentSlot.FEET, new ItemStack(Items.GOLDEN_BOOTS));
        }
     }
    
    private void setEquipmentBasedOnRNG(EntityEquipmentSlot slot, ItemStack stack) {
    	if (this.world.rand.nextFloat() < 0.1F) {
    		this.setItemStackToSlot(slot, stack);
        }
     }
    
	public boolean getIsImmuneToZombification() {
		return this.getDataManager().get(IS_IMMUNE_TO_ZOMBIFICATION);
	}
	
	public void setIsImmuneToZombification(boolean isImmuneToZombification) {
		this.getDataManager().set(IS_IMMUNE_TO_ZOMBIFICATION, isImmuneToZombification);
	}
	
	public boolean getIsDancing() {
		return this.dataManager.get(IS_DANCING);
	}
	
	public void setIsDancing(boolean isDancing) { 
		this.dataManager.set(IS_DANCING, isDancing);
	}
	
    private void setCannotHunt(boolean canHuntOrNot) {
    	this.cannotHunt = canHuntOrNot;
    }
	   
	public double getMountedYOffset() {
		return (double)this.height * 0.92D;
	}
	
    public double getYOffset() {
        return this.isChild() ? -0.05D : -0.45D;
    }
	
    public void setChild(boolean childPiglin) {
        this.getDataManager().set(IS_CHILD, Boolean.valueOf(childPiglin));

        if (this.world != null && !this.world.isRemote) {
            IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
            iattributeinstance.removeModifier(BABY_SPEED_MODIFIER);

            if (childPiglin) {
                iattributeinstance.applyModifier(BABY_SPEED_MODIFIER);
            }
        }
        
        this.setScaleForAge(childPiglin);
    }
}
