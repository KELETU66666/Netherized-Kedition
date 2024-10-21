package mellohi138.netherized.objects.entity.ai;

import mellohi138.netherized.objects.entity.neutral.EntityPigman;
import mellohi138.netherized.util.ModUtils;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.item.EntityItem;

import java.util.List;

public class EntityPigmanMelee<T extends EntityPigman> extends EntityAIAttackMelee {
    private final T entity;

    public EntityPigmanMelee(T entity, double speed, boolean bool) {
        super(entity, speed, bool);
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