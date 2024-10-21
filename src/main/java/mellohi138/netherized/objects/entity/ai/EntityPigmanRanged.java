package mellohi138.netherized.objects.entity.ai;

import mellohi138.netherized.objects.entity.neutral.EntityPigman;
import mellohi138.netherized.util.ModUtils;
import net.minecraft.entity.ai.EntityAIAttackRangedBow;
import net.minecraft.entity.item.EntityItem;

import java.util.List;

public class EntityPigmanRanged<T extends EntityPigman> extends EntityAIAttackRangedBow {
    private final EntityPigman entity;

    public EntityPigmanRanged(T entity, double speed, int i1, int i2) {
        super(entity, speed, i1, i2);
        this.entity = entity;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        List<EntityItem> nearbyItems = this.entity.world.getEntitiesWithinAABB(EntityItem.class, this.entity.getEntityBoundingBox().grow(5D), e -> !e.getIsInvulnerable());
        if (!nearbyItems.isEmpty()) {
            for (EntityItem item : nearbyItems) {
                if (item.getItem().getItem().equals(ModUtils.getOffhandItem())) {
                    return false;
                }
            }
        }
        if (entity.getHeldItemOffhand().getItem() == ModUtils.getOffhandItem())
            return false;

        return super.shouldExecute();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return (this.shouldExecute() && !this.entity.getNavigator().noPath());
    }
}