package mellohi138.netherized.init;

import mellohi138.netherized.Netherized;
import net.minecraft.block.SoundType;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class NetherizedSounds {
    public static final SoundEvent ITEM_ARMOR_EQUIP_NETHERITE = registerSound("item.armor.equip_netherite");

    public static final SoundEvent RECORD_PIGSTEP = registerSound("record.pigstep");

    public static final SoundEvent BLOCK_NETHERRACK_BREAK = registerSound("block.netherrack.break");
    public static final SoundEvent BLOCK_NETHERRACK_STEP = registerSound("block.netherrack.step");
    public static final SoundEvent BLOCK_NETHERRACK_PLACE = registerSound("block.netherrack.place");
    public static final SoundEvent BLOCK_NETHERRACK_HIT = registerSound("block.netherrack.hit");
    public static final SoundEvent BLOCK_NETHERRACK_FALL = registerSound("block.netherrack.fall");

    public static final SoundEvent BLOCK_SOUL_SAND_BREAK = registerSound("block.soul_sand.break");
    public static final SoundEvent BLOCK_SOUL_SAND_STEP = registerSound("block.soul_sand.step");
    public static final SoundEvent BLOCK_SOUL_SAND_PLACE = registerSound("block.soul_sand.place");
    public static final SoundEvent BLOCK_SOUL_SAND_HIT = registerSound("block.soul_sand.hit");
    public static final SoundEvent BLOCK_SOUL_SAND_FALL = registerSound("block.soul_sand.fall");

    public static final SoundEvent BLOCK_SOUL_SOIL_BREAK = registerSound("block.soul_soil.break");
    public static final SoundEvent BLOCK_SOUL_SOIL_STEP = registerSound("block.soul_soil.step");
    public static final SoundEvent BLOCK_SOUL_SOIL_PLACE = registerSound("block.soul_soil.place");
    public static final SoundEvent BLOCK_SOUL_SOIL_HIT = registerSound("block.soul_soil.hit");
    public static final SoundEvent BLOCK_SOUL_SOIL_FALL = registerSound("block.soul_soil.fall");

    public static final SoundEvent BLOCK_BONE_BLOCK_BREAK = registerSound("block.bone_block.break");
    public static final SoundEvent BLOCK_BONE_BLOCK_STEP = registerSound("block.bone_block.step");
    public static final SoundEvent BLOCK_BONE_BLOCK_PLACE = registerSound("block.bone_block.place");
    public static final SoundEvent BLOCK_BONE_BLOCK_HIT = registerSound("block.bone_block.hit");
    public static final SoundEvent BLOCK_BONE_BLOCK_FALL = registerSound("block.bone_block.fall");

    public static final SoundEvent BLOCK_BASALT_BREAK = registerSound("block.basalt.break");
    public static final SoundEvent BLOCK_BASALT_STEP = registerSound("block.basalt.step");
    public static final SoundEvent BLOCK_BASALT_PLACE = registerSound("block.basalt.place");
    public static final SoundEvent BLOCK_BASALT_HIT = registerSound("block.basalt.hit");
    public static final SoundEvent BLOCK_BASALT_FALL = registerSound("block.basalt.fall");

    public static final SoundEvent BLOCK_NETHER_NYLIUM_BREAK = registerSound("block.nether_nylium.break");
    public static final SoundEvent BLOCK_NETHER_NYLIUM_STEP = registerSound("block.nether_nylium.step");
    public static final SoundEvent BLOCK_NETHER_NYLIUM_PLACE = registerSound("block.nether_nylium.place");
    public static final SoundEvent BLOCK_NETHER_NYLIUM_HIT = registerSound("block.nether_nylium.hit");
    public static final SoundEvent BLOCK_NETHER_NYLIUM_FALL = registerSound("block.nether_nylium.fall");

    public static final SoundEvent BLOCK_NETHER_ROOTS_BREAK = registerSound("block.nether_roots.break");
    public static final SoundEvent BLOCK_NETHER_ROOTS_PLACE = registerSound("block.nether_roots.place");

    public static final SoundEvent BLOCK_WARPED_SPROUTS_BREAK = registerSound("block.warped_sprouts.break");
    public static final SoundEvent BLOCK_WARPED_SPROUTS_STEP = registerSound("block.warped_sprouts.step");
    public static final SoundEvent BLOCK_WARPED_SPROUTS_PLACE = registerSound("block.warped_sprouts.place");
    public static final SoundEvent BLOCK_WARPED_SPROUTS_HIT = registerSound("block.warped_sprouts.hit");
    public static final SoundEvent BLOCK_WARPED_SPROUTS_FALL = registerSound("block.warped_sprouts.fall");

    public static final SoundEvent BLOCK_NETHER_STEM_BREAK = registerSound("block.nether_stem.break");
    public static final SoundEvent BLOCK_NETHER_STEM_STEP = registerSound("block.nether_stem.step");
    public static final SoundEvent BLOCK_NETHER_STEM_PLACE = registerSound("block.nether_stem.place");
    public static final SoundEvent BLOCK_NETHER_STEM_HIT = registerSound("block.nether_stem.hit");
    public static final SoundEvent BLOCK_NETHER_STEM_FALL = registerSound("block.nether_stem.fall");

    public static final SoundEvent BLOCK_NETHER_FUNGUS_BREAK = registerSound("block.nether_fungus.break");
    public static final SoundEvent BLOCK_NETHER_FUNGUS_STEP = registerSound("block.nether_fungus.step");
    public static final SoundEvent BLOCK_NETHER_FUNGUS_PLACE = registerSound("block.nether_fungus.place");

    public static final SoundEvent BLOCK_SHROOMLIGHT_BREAK = registerSound("block.shroomlight.break");
    public static final SoundEvent BLOCK_SHROOMLIGHT_STEP = registerSound("block.shroomlight.step");
    public static final SoundEvent BLOCK_SHROOMLIGHT_PLACE = registerSound("block.shroomlight.place");
    public static final SoundEvent BLOCK_SHROOMLIGHT_HIT = registerSound("block.shroomlight.hit");
    public static final SoundEvent BLOCK_SHROOMLIGHT_FALL = registerSound("block.shroomlight.fall");

    public static final SoundEvent BLOCK_NETHER_ORE_BREAK = registerSound("block.nether_ore.break");
    public static final SoundEvent BLOCK_NETHER_ORE_STEP = registerSound("block.nether_ore.step");
    public static final SoundEvent BLOCK_NETHER_ORE_PLACE = registerSound("block.nether_ore.place");
    public static final SoundEvent BLOCK_NETHER_ORE_HIT = registerSound("block.nether_ore.hit");
    public static final SoundEvent BLOCK_NETHER_ORE_FALL = registerSound("block.nether_ore.fall");

    public static final SoundEvent BLOCK_LODESTONE_LOCK = registerSound("block.lodestone.lock");
    public static final SoundEvent BLOCK_LODESTONE_PLACE = registerSound("block.lodestone.place");

    public static final SoundEvent BLOCK_CHAIN_BREAK = registerSound("block.chain.break");
    public static final SoundEvent BLOCK_CHAIN_STEP = registerSound("block.chain.step");
    public static final SoundEvent BLOCK_CHAIN_PLACE = registerSound("block.chain.place");
    public static final SoundEvent BLOCK_CHAIN_HIT = registerSound("block.chain.hit");
    public static final SoundEvent BLOCK_CHAIN_FALL = registerSound("block.chain.fall");

    public static final SoundEvent BLOCK_NETHER_WART_BREAK = registerSound("block.nether_wart.break");
    public static final SoundEvent BLOCK_NETHER_WART_STEP = registerSound("block.nether_wart.step");
    public static final SoundEvent BLOCK_NETHER_WART_PLACE = registerSound("block.nether_wart.place");
    public static final SoundEvent BLOCK_NETHER_WART_HIT = registerSound("block.nether_wart.hit");
    public static final SoundEvent BLOCK_NETHER_WART_FALL = registerSound("block.nether_wart.fall");

    public static final SoundEvent BLOCK_NETHER_BRICKS_BREAK = registerSound("block.nether_bricks.break");
    public static final SoundEvent BLOCK_NETHER_BRICKS_STEP = registerSound("block.nether_bricks.step");
    public static final SoundEvent BLOCK_NETHER_BRICKS_PLACE = registerSound("block.nether_bricks.place");
    public static final SoundEvent BLOCK_NETHER_BRICKS_HIT = registerSound("block.nether_bricks.hit");
    public static final SoundEvent BLOCK_NETHER_BRICKS_FALL = registerSound("block.nether_bricks.fall");

    public static final SoundEvent BLOCK_RESPAWN_ANCHOR_CHARGE = registerSound("block.respawn_anchor.charge");
    public static final SoundEvent BLOCK_RESPAWN_ANCHOR_DEPLETE = registerSound("block.respawn_anchor.deplete");
    public static final SoundEvent BLOCK_RESPAWN_ANCHOR_SPAWN_SET = registerSound("block.respawn_anchor.spawn_set");
    public static final SoundEvent BLOCK_RESPAWN_ANCHOR_AMBIENCE = registerSound("block.respawn_anchor.ambience");

    public static final SoundEvent BLOCK_ANCIENT_DEBRIS_BREAK = registerSound("block.ancient_debris.break");
    public static final SoundEvent BLOCK_ANCIENT_DEBRIS_STEP = registerSound("block.ancient_debris.step");
    public static final SoundEvent BLOCK_ANCIENT_DEBRIS_PLACE = registerSound("block.ancient_debris.place");
    public static final SoundEvent BLOCK_ANCIENT_DEBRIS_HIT = registerSound("block.ancient_debris.hit");
    public static final SoundEvent BLOCK_ANCIENT_DEBRIS_FALL = registerSound("block.ancient_debris.fall");

    public static final SoundEvent BLOCK_NETHERITE_BLOCK_BREAK = registerSound("block.netherite_block.break");
    public static final SoundEvent BLOCK_NETHERITE_BLOCK_STEP = registerSound("block.netherite_block.step");
    public static final SoundEvent BLOCK_NETHERITE_BLOCK_PLACE = registerSound("block.netherite_block.place");
    public static final SoundEvent BLOCK_NETHERITE_BLOCK_HIT = registerSound("block.netherite_block.hit");
    public static final SoundEvent BLOCK_NETHERITE_BLOCK_FALL = registerSound("block.netherite_block.fall");

    public static final SoundEvent ENTITY_PIGLIN_SNORT = registerSound("entity.piglin.snort");
    public static final SoundEvent ENTITY_PIGLIN_SNORT_ANGRY = registerSound("entity.piglin.snort.angry");
    public static final SoundEvent ENTITY_PIGLIN_RETREAT = registerSound("entity.piglin.retreat");
    public static final SoundEvent ENTITY_PIGLIN_HURT = registerSound("entity.piglin.hurt");
    public static final SoundEvent ENTITY_PIGLIN_DEATH = registerSound("entity.piglin.death");
    public static final SoundEvent ENTITY_PIGLIN_STEP = registerSound("entity.piglin.step");
    public static final SoundEvent ENTITY_PIGLIN_CELEBRATE = registerSound("entity.piglin.celebrate");
    public static final SoundEvent ENTITY_PIGLIN_SNORT_ENVIOUS = registerSound("entity.piglin.snort.envious");
    public static final SoundEvent ENTITY_PIGLIN_ADMIRE = registerSound("entity.piglin.admire");
    public static final SoundEvent ENTITY_PIGLIN_CONVERSION = registerSound("entity.piglin.conversion");

    public static final SoundEvent ENTITY_HOGLIN_GROWL = registerSound("entity.hoglin.growl");
    public static final SoundEvent ENTITY_HOGLIN_GROWL_ANGRY = registerSound("entity.hoglin.growl.angry");
    public static final SoundEvent ENTITY_HOGLIN_ATTACK = registerSound("entity.hoglin.attack");
    public static final SoundEvent ENTITY_HOGLIN_RETREAT = registerSound("entity.hoglin.retreat");
    public static final SoundEvent ENTITY_HOGLIN_CONVERSION = registerSound("entity.hoglin.conversion");
    public static final SoundEvent ENTITY_HOGLIN_STEP = registerSound("entity.hoglin.step");
    public static final SoundEvent ENTITY_HOGLIN_HURT = registerSound("entity.hoglin.hurt");
    public static final SoundEvent ENTITY_HOGLIN_DEATH = registerSound("entity.hoglin.death");

    public static final SoundEvent ENTITY_PIGLIN_BRUTE_SNORT = registerSound("entity.piglin_brute.snort");
    public static final SoundEvent ENTITY_PIGLIN_BRUTE_SNORT_ANGRY = registerSound("entity.piglin_brute.snort.angry");
    public static final SoundEvent ENTITY_PIGLIN_BRUTE_HURT = registerSound("entity.piglin_brute.hurt");
    public static final SoundEvent ENTITY_PIGLIN_BRUTE_DEATH = registerSound("entity.piglin_brute.death");
    public static final SoundEvent ENTITY_PIGLIN_BRUTE_STEP = registerSound("entity.piglin_brute.step");
    public static final SoundEvent ENTITY_PIGLIN_BRUTE_CONVERSION = registerSound("entity.piglin_brute.conversion");

    public static final SoundEvent ENTITY_ZOGLIN_GROWL = registerSound("entity.zoglin.growl");
    public static final SoundEvent ENTITY_ZOGLIN_GROWL_ANGRY = registerSound("entity.zoglin.growl.angry");
    public static final SoundEvent ENTITY_ZOGLIN_ATTACK = registerSound("entity.zoglin.attack");
    public static final SoundEvent ENTITY_ZOGLIN_STEP = registerSound("entity.zoglin.step");
    public static final SoundEvent ENTITY_ZOGLIN_HURT = registerSound("entity.zoglin.hurt");
    public static final SoundEvent ENTITY_ZOGLIN_DEATH = registerSound("entity.zoglin.death");

    public static final SoundEvent ENTITY_STRIDER_CHIRP = registerSound("entity.strider.chirp");
    public static final SoundEvent ENTITY_STRIDER_DEATH = registerSound("entity.strider.death");
    public static final SoundEvent ENTITY_STRIDER_EAT = registerSound("entity.strider.eat");
    public static final SoundEvent ENTITY_STRIDER_HURT = registerSound("entity.strider.hurt");
    public static final SoundEvent ENTITY_STRIDER_RETREAT = registerSound("entity.strider.retreat");
    public static final SoundEvent ENTITY_STRIDER_STEP = registerSound("entity.strider.step");
    public static final SoundEvent ENTITY_STRIDER_STEP_LAVA = registerSound("entity.strider.step.lava");
    public static final SoundEvent ENTITY_STRIDER_WARBLE = registerSound("entity.strider.warble");

    public static final SoundEvent PARTICLE_SOUL_SCREAM = registerSound("particle.soul.scream");

    public static final SoundType SOUND_TYPE_FIRE = new SoundType(1.0F, 1.0F, SoundEvents.BLOCK_FIRE_EXTINGUISH, null, null, null, null);
    public static final SoundType SOUND_TYPE_NETHERRACK = new SoundType(1.0F, 1.0F, NetherizedSounds.BLOCK_NETHERRACK_BREAK, NetherizedSounds.BLOCK_NETHERRACK_STEP, NetherizedSounds.BLOCK_NETHERRACK_PLACE, NetherizedSounds.BLOCK_NETHERRACK_HIT, NetherizedSounds.BLOCK_NETHERRACK_FALL);
    public static final SoundType SOUND_TYPE_SOUL_SAND = new SoundType(1.0F, 1.0F, NetherizedSounds.BLOCK_SOUL_SAND_BREAK, NetherizedSounds.BLOCK_SOUL_SAND_STEP, NetherizedSounds.BLOCK_SOUL_SAND_PLACE, NetherizedSounds.BLOCK_SOUL_SAND_HIT, NetherizedSounds.BLOCK_SOUL_SAND_FALL);
    public static final SoundType SOUND_TYPE_SOUL_SOIL = new SoundType(1.0F, 1.0F, NetherizedSounds.BLOCK_SOUL_SOIL_BREAK, NetherizedSounds.BLOCK_SOUL_SOIL_STEP, NetherizedSounds.BLOCK_SOUL_SOIL_PLACE, NetherizedSounds.BLOCK_SOUL_SOIL_HIT, NetherizedSounds.BLOCK_SOUL_SOIL_FALL);
    public static final SoundType SOUND_TYPE_BONE_BLOCK = new SoundType(1.0F, 1.0F, NetherizedSounds.BLOCK_BONE_BLOCK_BREAK, NetherizedSounds.BLOCK_BONE_BLOCK_STEP, NetherizedSounds.BLOCK_BONE_BLOCK_PLACE, NetherizedSounds.BLOCK_BONE_BLOCK_HIT, NetherizedSounds.BLOCK_BONE_BLOCK_FALL);
    public static final SoundType SOUND_TYPE_BASALT = new SoundType(1.0F, 1.0F, NetherizedSounds.BLOCK_BASALT_BREAK, NetherizedSounds.BLOCK_BASALT_STEP, NetherizedSounds.BLOCK_BASALT_PLACE, NetherizedSounds.BLOCK_BASALT_HIT, NetherizedSounds.BLOCK_BASALT_FALL);
    public static final SoundType SOUND_TYPE_LODESTONE = new SoundType(1.0F, 1.0F, SoundEvents.BLOCK_STONE_BREAK, SoundEvents.BLOCK_STONE_STEP, NetherizedSounds.BLOCK_LODESTONE_PLACE, SoundEvents.BLOCK_STONE_HIT, SoundEvents.BLOCK_STONE_FALL);
    public static final SoundType SOUND_TYPE_NETHER_NYLIUM = new SoundType(1.0F, 1.0F, NetherizedSounds.BLOCK_NETHER_NYLIUM_BREAK, NetherizedSounds.BLOCK_NETHER_NYLIUM_STEP, NetherizedSounds.BLOCK_NETHER_NYLIUM_PLACE, NetherizedSounds.BLOCK_NETHER_NYLIUM_HIT, NetherizedSounds.BLOCK_NETHER_NYLIUM_FALL);
    public static final SoundType SOUND_TYPE_NETHER_ROOTS = new SoundType(1.0F, 1.0F, NetherizedSounds.BLOCK_NETHER_ROOTS_BREAK, NetherizedSounds.BLOCK_WARPED_SPROUTS_STEP, NetherizedSounds.BLOCK_NETHER_ROOTS_PLACE, NetherizedSounds.BLOCK_WARPED_SPROUTS_HIT, NetherizedSounds.BLOCK_WARPED_SPROUTS_FALL);
    public static final SoundType SOUND_TYPE_NETHER_STEM = new SoundType(1.0F, 1.0F, NetherizedSounds.BLOCK_NETHER_STEM_BREAK, BLOCK_NETHER_STEM_STEP, BLOCK_NETHER_STEM_PLACE, BLOCK_NETHER_STEM_HIT, BLOCK_NETHER_STEM_FALL);
    public static final SoundType SOUND_TYPE_NETHER_FUNGUS = new SoundType(1.0F, 1.0F, NetherizedSounds.BLOCK_NETHER_FUNGUS_BREAK, BLOCK_NETHER_FUNGUS_STEP, BLOCK_NETHER_FUNGUS_PLACE, null, null);
    public static final SoundType SOUND_TYPE_WARPED_SPROUTS = new SoundType(1.0F, 1.0F, NetherizedSounds.BLOCK_NETHER_NYLIUM_BREAK, NetherizedSounds.BLOCK_NETHER_NYLIUM_STEP, NetherizedSounds.BLOCK_NETHER_NYLIUM_PLACE, NetherizedSounds.BLOCK_NETHER_NYLIUM_HIT, NetherizedSounds.BLOCK_NETHER_NYLIUM_FALL);
    public static final SoundType SOUND_TYPE_SHROOMLIGHT = new SoundType(1.0F, 1.0F, NetherizedSounds.BLOCK_SHROOMLIGHT_BREAK, NetherizedSounds.BLOCK_SHROOMLIGHT_STEP, NetherizedSounds.BLOCK_SHROOMLIGHT_PLACE, NetherizedSounds.BLOCK_SHROOMLIGHT_HIT, NetherizedSounds.BLOCK_SHROOMLIGHT_FALL);
    public static final SoundType SOUND_TYPE_NETHER_ORE = new SoundType(1.0F, 1.0F, NetherizedSounds.BLOCK_NETHER_ORE_BREAK, NetherizedSounds.BLOCK_NETHER_ORE_STEP, NetherizedSounds.BLOCK_NETHER_ORE_PLACE, NetherizedSounds.BLOCK_NETHER_ORE_HIT, NetherizedSounds.BLOCK_NETHER_ORE_FALL);
    public static final SoundType SOUND_TYPE_CHAIN = new SoundType(1.0F, 1.0F, NetherizedSounds.BLOCK_CHAIN_BREAK, NetherizedSounds.BLOCK_CHAIN_STEP, NetherizedSounds.BLOCK_CHAIN_PLACE, NetherizedSounds.BLOCK_CHAIN_HIT, NetherizedSounds.BLOCK_CHAIN_FALL);
    public static final SoundType SOUND_TYPE_NETHER_WART = new SoundType(1.0F, 1.0F, NetherizedSounds.BLOCK_NETHER_WART_BREAK, NetherizedSounds.BLOCK_NETHER_WART_STEP, NetherizedSounds.BLOCK_NETHER_WART_PLACE, NetherizedSounds.BLOCK_NETHER_WART_HIT, NetherizedSounds.BLOCK_NETHER_WART_FALL);
    public static final SoundType SOUND_TYPE_NETHER_BRICKS = new SoundType(1.0F, 1.0F, NetherizedSounds.BLOCK_NETHER_BRICKS_BREAK, NetherizedSounds.BLOCK_NETHER_BRICKS_STEP, NetherizedSounds.BLOCK_NETHER_BRICKS_PLACE, NetherizedSounds.BLOCK_NETHER_BRICKS_HIT, NetherizedSounds.BLOCK_NETHER_BRICKS_FALL);
    public static final SoundType SOUND_TYPE_ANCIENT_DEBRIS = new SoundType(1.0F, 1.0F, NetherizedSounds.BLOCK_ANCIENT_DEBRIS_BREAK, NetherizedSounds.BLOCK_ANCIENT_DEBRIS_STEP, NetherizedSounds.BLOCK_ANCIENT_DEBRIS_PLACE, NetherizedSounds.BLOCK_ANCIENT_DEBRIS_HIT, NetherizedSounds.BLOCK_ANCIENT_DEBRIS_FALL);
    public static final SoundType SOUND_TYPE_NETHERITE_BLOCK = new SoundType(1.0F, 1.0F, NetherizedSounds.BLOCK_NETHERITE_BLOCK_BREAK, NetherizedSounds.BLOCK_NETHERITE_BLOCK_STEP, NetherizedSounds.BLOCK_NETHERITE_BLOCK_PLACE, NetherizedSounds.BLOCK_NETHERITE_BLOCK_HIT, NetherizedSounds.BLOCK_NETHERITE_BLOCK_FALL);

    private static SoundEvent registerSound(String name) {
        ResourceLocation location = new ResourceLocation(Netherized.MODID, name);
        SoundEvent event = new SoundEvent(location).setRegistryName(name);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;
    }
}
