package mellohi138.netherized.objects.entity.neutral;

import com.google.common.collect.Lists;
import mellohi138.netherized.Netherized;
import mellohi138.netherized.init.NetherizedBlocks;
import mellohi138.netherized.init.NetherizedSounds;
import mellohi138.netherized.objects.entity.ai.EntityPigmanMelee;
import mellohi138.netherized.objects.entity.ai.EntityPigmanRanged;
import mellohi138.netherized.util.ModRand;
import mellohi138.netherized.util.ModUtils;
import mellohi138.netherized.util.config.NetherizedEntitiesConfig;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;

import javax.annotation.Nullable;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.UUID;

public class EntityPigman extends EntityMob implements IRangedAttackMob {
    private static final DataParameter<Boolean> IS_CHILD = EntityDataManager.createKey(EntityPigman.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_IMMUNE_TO_ZOMBIFICATION = EntityDataManager.createKey(EntityPigman.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_DANCING = EntityDataManager.createKey(EntityPigman.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> IMMOVABLE = EntityDataManager.createKey(EntityPigman.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> FIGHT_MODE = EntityDataManager.createKey(EntityPigman.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> INSIDE_BASTION = EntityDataManager.createKey(EntityPigman.class, DataSerializers.BOOLEAN);
    private static final ResourceLocation LOOT_TRADE = new ResourceLocation(Netherized.MODID, "piglin_trade");
    private static final UUID BABY_SPEED_MODIFIER_IDENTIFIER = UUID.fromString("766bfa64-11f3-11ea-8d71-362b9e155667");
    private static final AttributeModifier BABY_SPEED_MODIFIER = new AttributeModifier(BABY_SPEED_MODIFIER_IDENTIFIER, "Baby speed boost", 0.2F, 1);
    private final InventoryBasic piglinInventory;
    private float zombieHeight;
    private float zombieWidth;
    protected int timeInOverworld = 0;
    private boolean initiateBastionAI = false;
    protected int isHungryTimer = 60;
    private long lootTableTradeSeed;
    private boolean cannotHunt = false;
    protected int trade_delay = 30;

    protected boolean foundGoldIngot = false;
    private int dimensionCheck = 40;
    private int countDownToZombie = NetherizedEntitiesConfig.zombification_time * 20;

    private EntityHoglin foodTarget;

    public boolean convertTooZombie = false;

    protected boolean hasPlayedAngrySound = false;
    private boolean hasNearbyBlockItHates = false;
    private boolean setGear = false;

    private int tickOut = 100;
    private int checkForBlocksTimer = 30;
    protected boolean canTrade = true;

    public EntityPigman(World worldIn) {
        super(worldIn);
        this.piglinInventory = new InventoryBasic("Items", false, 8);
        this.experienceValue = 5;
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, getHeldItem());
        this.setCanPickUpLoot(true);
        this.setSize(0.6F, 1.95F);
        ((PathNavigateGround) this.getNavigator()).setBreakDoors(true);
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
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 9.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(3, new EntityAIAvoidEntity<>(this, EntityPigZombie.class, 3.0F, 1.1D, 1.2D));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<>(this, EntityWitherSkeleton.class, 1, true, false, null));
    }

    protected void createTargetFor(EntityLivingBase player) {
        //this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, player.getClass(), 1, true, false, null));
    }


    protected void addBastionChanges() {
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 1, true, false, null));
        this.initiateBastionAI = true;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(IS_CHILD, false);
        this.getDataManager().register(IS_IMMUNE_TO_ZOMBIFICATION, false);
        this.getDataManager().register(IS_DANCING, false);
        this.dataManager.register(IMMOVABLE, Boolean.FALSE);
        this.dataManager.register(FIGHT_MODE, Boolean.FALSE);
        this.dataManager.register(INSIDE_BASTION, Boolean.FALSE);
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
        compound.setLong("loot_table_seed", lootTableTradeSeed);
        compound.setBoolean("Fight_Mode", this.isFightMode());
        compound.setBoolean("Immovable", this.isImmovable());
        compound.setBoolean("Inside_Bastion", this.isInsideBastion());
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
        this.lootTableTradeSeed = compound.getLong("loot_table_seed");
        this.setFightMode(compound.getBoolean("Fight_Mode"));
        this.setImmovable(compound.getBoolean("Immovable"));
        this.setInsideBastion(compound.getBoolean("Inside_Bastion"));
    }

    @Override
    public boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    public void setChildSize(boolean isChild) {
        this.multiplySize(isChild ? 0.5F : 1.0F);
    }

    protected final void setSize(float width, float height) {
        boolean flag = this.zombieWidth > 0.0F && this.zombieHeight > 0.0F;
        this.zombieWidth = width;
        this.zombieHeight = height;
        if (!flag) {
            this.multiplySize(1.0F);
        }

    }

    public void setInsideBastion(boolean value) {
        this.dataManager.set(INSIDE_BASTION, value);
    }

    protected final void multiplySize(float size) {
        super.setSize(this.zombieWidth * size, this.zombieHeight * size);
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

    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setEquipmentBasedOnDifficulty(difficulty);


        return livingdata;
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
        return (double) this.height * 0.92D;
    }

    public double getYOffset() {
        return this.isChild() ? -0.05D : -0.45D;
    }

    public void setChild(boolean childPiglin) {
        this.getDataManager().set(IS_CHILD, childPiglin);

        if (this.world != null && !this.world.isRemote) {
            IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
            iattributeinstance.removeModifier(BABY_SPEED_MODIFIER);

            if (childPiglin) {
                iattributeinstance.applyModifier(BABY_SPEED_MODIFIER);
            }
        }

        this.setChildSize(childPiglin);
    }

    public void notifyDataManagerChange(DataParameter<?> key) {
        if (IS_CHILD.equals(key)) {
            this.setChildSize(this.isChild());
        }

        super.notifyDataManagerChange(key);
    }

    private List<ItemStack> trade_items = Lists.newArrayList();

    protected void getPiglinLootTable() {
        if (!world.isRemote) {
            LootContext.Builder lootcontext$builder = (new LootContext.Builder((WorldServer) this.world)).withLootedEntity(this);
            trade_items = this.world.getLootTableManager().getLootTableFromLocation(LOOT_TRADE).generateLootForPools(this.lootTableTradeSeed == 0 ? new Random() : new Random(this.lootTableTradeSeed), lootcontext$builder.build());
            for (ItemStack item : trade_items) {
                this.entityDropItem(item, 1.5F);
                //this.world.getLootTableManager().reloadLootTables();
            }
        }
    }

    protected boolean isImmovable() {
        return this.dataManager != null && this.dataManager.get(IMMOVABLE);
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

    protected void initMeleeAI() {
        if (this.getHeldItemOffhand().getItem() != ModUtils.getOffhandItem())
            this.tasks.addTask(2, new EntityPigmanMelee<>(this, 1.0D, false));
    }

    protected void initRangedAI() {
        this.tasks.addTask(2, new EntityPigmanRanged<>(this, 1.0D, 20, 15));
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distance) {
        EntityArrow entityarrow = getArrow(distance);
        if (getHeldItemMainhand().getItem() instanceof net.minecraft.item.ItemBow)
            entityarrow = ((net.minecraft.item.ItemBow) this.getHeldItemMainhand().getItem()).customizeArrow(entityarrow);
        double d0 = target.posX - posX;
        double d1 = target.getEntityBoundingBox().minY + (double) (target.height / 3f) - entityarrow.posY;
        double d2 = target.posZ - posZ;
        double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
        entityarrow.shoot(d0, d1 + d3 * 0.20000000298023224, d2, 1.6f, (float) (14 - world.getDifficulty().getId() * 4));
        playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1, 1f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
        world.spawnEntity(entityarrow);
    }

    @Override
    public void setSwingingArms(boolean b) {
        setFightMode(true);
    }

    protected EntityArrow getArrow(float distance) {
        EntityTippedArrow entitytippedarrow = new EntityTippedArrow(world, this);
        entitytippedarrow.setEnchantmentEffectsFromEntity(this, distance);
        return entitytippedarrow;
    }

    private ItemStack getHeldItem() {
        return (double) this.rand.nextFloat() < 0.5D ? new ItemStack(Items.BOW) : new ItemStack(Items.GOLDEN_SWORD);
    }

    public void initiateStuff() {
        if (this.getHeldItemMainhand().getItem() instanceof ItemBow) {
            this.initRangedAI();

        } else {
            this.initMeleeAI();
        }
        setGear = true;
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


    @Override
    public void onUpdate() {
        super.onUpdate();

        //function to set gear on each load of the entity, only occurs once per load
        if (!setGear && !world.isRemote) {
            this.initiateStuff();
        }

        EntityLivingBase target = this.getAttackTarget();

        if (target != null && !hasPlayedAngrySound) {
            this.playSound(NetherizedSounds.ENTITY_PIGLIN_SNORT_ANGRY, 1.0f, 1.0f / (rand.nextFloat() * 0.4f + 0.5f));
            hasPlayedAngrySound = true;
        } else if (target == null) {
            hasPlayedAngrySound = false;
        }

        //helper to set targeted to null if the intended target is dead
        if (target != null && !world.isRemote) {
            boolean canSee = this.getEntitySenses().canSee(target);

            if (!target.isEntityAlive() || !canSee || hasNearbyBlockItHates) {
                if (tickOut < 0) {
                    this.setAttackTarget(null);
                    tickOut = 100;
                } else {
                    tickOut--;
                }
            }
        }

        //Flee from XyZ blocks
        if (!world.isRemote) {
            AxisAlignedBB box = getEntityBoundingBox().grow(16, 6, 16);
            //This checks for the nearby blocks this entity is scared of
            BlockPos posToo = this.getPosition();

            if (checkForBlocksTimer < 0) {
                //Search for Soul Fire
                if (ModUtils.searchForBlocks(box, world, this, NetherizedBlocks.SOUL_FIRE.getDefaultState()) != null) {
                    hasNearbyBlockItHates = true;
                    posToo = ModUtils.searchForBlocks(box, world, this, NetherizedBlocks.SOUL_FIRE.getDefaultState());
                }
                //Search for Soul Torches
                else if (ModUtils.searchForBlocks(box, world, this, NetherizedBlocks.SOUL_TORCH.getDefaultState()) != null) {
                    hasNearbyBlockItHates = true;
                    posToo = ModUtils.searchForBlocks(box, world, this, NetherizedBlocks.SOUL_TORCH.getDefaultState());
                }
                //Since Lanterns are inside of Bastions, just as a check, might make this configurable
                else if (ModUtils.searchForBlocks(box, world, this, NetherizedBlocks.SHROOMLIGHT.getDefaultState()) != null && !isInsideBastion()) {
                    hasNearbyBlockItHates = true;
                    posToo = ModUtils.searchForBlocks(box, world, this, NetherizedBlocks.SHROOMLIGHT.getDefaultState());
                } else {
                    //sets to false
                    hasNearbyBlockItHates = false;
                    posToo = null;
                }
                checkForBlocksTimer = 30;
            } else {
                checkForBlocksTimer--;
            }

            // if it has a nearby block it hates run away from that Pos
            if (hasNearbyBlockItHates && posToo != null) {
                Vec3d away = this.getPositionVector().subtract(new Vec3d(posToo.getX(), posToo.getY(), posToo.getZ())).normalize();
                Vec3d pos = this.getPositionVector().add(away.scale(8)).add(ModRand.randVec().scale(4));
                this.getNavigator().tryMoveToXYZ(pos.x, pos.y, pos.z, 1.8D);
            }
        }


        if (!world.isRemote && world.rand.nextInt(12) == 0 && !this.isInsideBastion()) {
            if (this.isHungryTimer < 0) {
                List<EntityHoglin> nearbyHoglins = this.world.getEntitiesWithinAABB(EntityHoglin.class, this.getEntityBoundingBox().grow(16D), e -> !e.getIsInvulnerable());
                if (!nearbyHoglins.isEmpty() && target == null && foodTarget == null) {
                    for (EntityHoglin hoglin : nearbyHoglins) {
                        this.createTargetFor(hoglin);
                        foodTarget = hoglin;
                        this.isHungryTimer = 60;
                        List<EntityPigman> nearbyPigmen = this.world.getEntitiesWithinAABB(EntityPigman.class, this.getEntityBoundingBox().grow(10D), e -> !e.getIsInvulnerable());
                        if (!nearbyPigmen.isEmpty()) {
                            for (EntityPigman pigman : nearbyPigmen) {
                                pigman.setAttackTarget(hoglin);
                            }
                        }
                    }
                }
            } else {
                this.isHungryTimer--;
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
        if (this.canTrade && !this.isInsideBastion()) {
            if (trade_delay < 0) {
                List<EntityItem> nearbyItems = this.world.getEntitiesWithinAABB(EntityItem.class, this.getEntityBoundingBox().grow(5D), e -> !e.getIsInvulnerable());
                if (!nearbyItems.isEmpty()) {
                    for (EntityItem item : nearbyItems) {
                        ItemStack itemStack = item.getItem();
                        if (ModUtils.getItemsForBarter(itemStack.getItem()) && !foundGoldIngot) {
                            double distSq = this.getDistanceSq(item.posX, item.getEntityBoundingBox().minY, item.posZ);
                            this.getNavigator().tryMoveToEntityLiving(item, 1.2D);
                            if (distSq < 2) {
                                this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(itemStack.getItem()));
                                item.getItem().shrink(1);
                                foundGoldIngot = true;
                                this.doTrade();
                                trade_delay = NetherizedEntitiesConfig.piglins_trade_cooldown;
                            }

                        } else {
                            trade_delay = 30;
                        }

                    }
                } else {
                    trade_delay = 30;
                }

            }
            trade_delay--;

        }
        //Check For Players With Gold Armor nearby
        List<EntityPlayer> nearbyEntities = this.world.getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(14D), e -> !e.getIsInvulnerable());
        if (!nearbyEntities.isEmpty() && !this.isInsideBastion()) {
            for (EntityPlayer player : nearbyEntities) {
                if (player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() != Items.GOLDEN_HELMET && player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() != Items.GOLDEN_CHESTPLATE && player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() != Items.GOLDEN_LEGGINGS && player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() != Items.GOLDEN_BOOTS && this.getHeldItemOffhand().getItem() != ModUtils.getOffhandItem()) {
                    if (this.getAttackTarget() != player) {
                        if (!player.isSpectator() && !player.isCreative() && this.getAttackTarget() == null) {
                            //canTrade = false;
                            this.createTargetFor(player);
                            this.setAttackTarget(player);
                        }
                    }
                } else {
                    if (this.getAttackTarget() == player && !this.isInsideBastion()) {
                        if (player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == Items.GOLDEN_HELMET || player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == Items.GOLDEN_CHESTPLATE || player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() == Items.GOLDEN_LEGGINGS || player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == Items.GOLDEN_BOOTS || this.getHeldItemOffhand().getItem() == ModUtils.getOffhandItem()) {
                            this.setAttackTarget(null);
                        }
                    }
                    if (this.getAttackTarget() == null && !this.isInsideBastion()) {
                        canTrade = true;
                    }

                }
            }
        }
        if (!NetherizedEntitiesConfig.piglins_are_aggro && !initiateBastionAI) {
            this.setInsideBastion(false);
            this.initiateBastionAI = true;
        } else if (this.isInsideBastion() && !initiateBastionAI) {
            this.addBastionChanges();
        }
    }

    private void beginZombieTransformation() {
        if (!world.isRemote) {
            addEvent(() -> this.playSound(NetherizedSounds.ENTITY_PIGLIN_CONVERSION, 1.0f, 1.0f), 75);
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

    public void addEvent(Runnable runnable, int ticksFromNow) {
        events.add(new TimedEvent(runnable, this.ticksExisted + ticksFromNow));
    }

    protected void doTrade() {
        this.setFightMode(true);
        //this.setShortTrade(true);
        this.playSound(NetherizedSounds.ENTITY_PIGLIN_ADMIRE, 1.0f, 1.0f / (rand.nextFloat() * 0.4f + 0.5f));

        addEvent(this::getPiglinLootTable, 110);
        addEvent(() -> {
            this.playSound(NetherizedSounds.ENTITY_PIGLIN_SNORT, 1.0f, 1.0f / (rand.nextFloat() * 0.4f + 0.5f));
        }, 110);

        addEvent(() -> {
            this.setFightMode(false);
            //this.setShortTrade(false);
            this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.AIR));
            this.foundGoldIngot = false;
        }, 120);
    }

    private static final ResourceLocation LOOT = new ResourceLocation(Netherized.MODID, "piglin");

    @Override
    protected ResourceLocation getLootTable() {
        return LOOT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return NetherizedSounds.ENTITY_PIGLIN_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return NetherizedSounds.ENTITY_PIGLIN_SNORT;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(NetherizedSounds.ENTITY_PIGLIN_STEP, 0.15F, 1.0f / (rand.nextFloat() * 0.4F + 0.2f));
    }

    @Override
    protected SoundEvent getDeathSound() {
        return NetherizedSounds.ENTITY_PIGLIN_DEATH;
    }

    private PriorityQueue<EntityPigman.TimedEvent> events = new PriorityQueue<>();

    public static class TimedEvent implements Comparable<EntityPigman.TimedEvent> {
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
