package mellohi138.netherized.world;

import mellohi138.netherized.world.gen.bastion.BastionTemplate;
import mellohi138.netherized.world.gen.bastion.WorldGenBastion;
import net.minecraft.world.gen.structure.MapGenStructureIO;

public class StructureHandler {

    public static void handleStructureRegistries(){
        MapGenStructureIO.registerStructure(WorldGenBastion.Start.class, "BastionRemnants");
        MapGenStructureIO.registerStructureComponent(BastionTemplate.class, "BRP");
    }
}