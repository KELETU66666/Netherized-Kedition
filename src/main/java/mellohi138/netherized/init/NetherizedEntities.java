package mellohi138.netherized.init;

import mellohi138.netherized.Netherized;
import mellohi138.netherized.objects.entity.EntityFireproofItem;
import mellohi138.netherized.objects.entity.EntityInfernoFireball;
import mellohi138.netherized.objects.entity.hostile.EntityHoveringInferno;
import mellohi138.netherized.objects.entity.hostile.EntityZombifiedPiglin;
import mellohi138.netherized.objects.entity.neutral.EntityHoglin;
import mellohi138.netherized.objects.entity.neutral.EntityPigman;
import mellohi138.netherized.objects.entity.neutral.EntityPigmanBrute;
import mellohi138.netherized.objects.entity.neutral.EntityZoglin;
import mellohi138.netherized.objects.entity.passive.EntityStrider;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

/**
 * Entities were assigned with the help of Chocolate Quest Repoured's source code. Credit goes to DerToaster.
 * Chocolate Quest Repoured belongs to ArloTheEpic.
 * Chocolate Quest Repoured Curseforge Page: https://www.curseforge.com/minecraft/mc-mods/cqrepoured
 * Chocolate Quest Repoured Github Link: https://github.com/TeamChocoQuest/ChocolateQuestRepoured
 */
@Mod.EventBusSubscriber(modid = Netherized.MODID)
public class NetherizedEntities {
	private static int entityID;
	
    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
    	event.getRegistry().register(registerEntity("strider", EntityStrider.class, 10236982, 5065037));
    	
    	event.getRegistry().register(NetherizedEntities.registerEntity("piglin", EntityPigman.class, 10051392, 16380836));
    	event.getRegistry().register(NetherizedEntities.registerEntity("piglin_brute", EntityPigmanBrute.class, 5843472, 16380836));
    	event.getRegistry().register(NetherizedEntities.registerEntity("hoglin", EntityHoglin.class, 13004373, 6251620));
    	event.getRegistry().register(NetherizedEntities.registerEntity("zoglin", EntityZoglin.class, 13004373, 15132390));
    	
    	event.getRegistry().register(registerEntity("hovering_inferno", EntityHoveringInferno.class, 1444107, 16758329));
		event.getRegistry().register(registerEntity("zombified_piglin", EntityZombifiedPiglin.class, 15373203, 5009705));
		
		event.getRegistry().register(registerEntity("fireproof_item", EntityFireproofItem.class));
		event.getRegistry().register(registerEntity("inferno_fireball", EntityInfernoFireball.class));
    }
    
    private static EntityEntry registerEntity(String name, Class<? extends Entity> entityClass, int mainEggColor, int subEggColor) {
        return EntityEntryBuilder.create().name(name).entity(entityClass).id(new ResourceLocation(Netherized.MODID, name), entityID++).egg(mainEggColor, subEggColor).tracker(64, 1, true).build();
    }
    
    private static EntityEntry registerEntity(String name, Class<? extends Entity> entityClass) {
        return EntityEntryBuilder.create().name(name).entity(entityClass).id(new ResourceLocation(Netherized.MODID, name), entityID++).tracker(64, 1, true).build();
    }
}
