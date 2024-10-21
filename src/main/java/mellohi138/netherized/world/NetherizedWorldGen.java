package mellohi138.netherized.world;

import mellohi138.netherized.Netherized;
import mellohi138.netherized.util.IStructure;
import mellohi138.netherized.util.ModRand;
import mellohi138.netherized.util.ModUtils;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import org.apache.logging.log4j.LogManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class NetherizedWorldGen extends WorldGenerator implements IStructure {
    /**
     * Base Class
     */

    public String structureName;

    protected PlacementSettings placementSettings;

    private static final PlacementSettings DEFAULT_SETTINGS = new PlacementSettings();

    private Template template;

    private float attempts;

    private int maxVar;



    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        this.generateStructure(worldIn, position, ModRand.choice(Rotation.values()));
        return true;
    }

    public NetherizedWorldGen(String structureName) {
        this.structureName = structureName;
        this.placementSettings = DEFAULT_SETTINGS.setIgnoreEntities(true).setReplacedBlock(Blocks.AIR);
    }

    public float getAttempts() {
        return attempts;
    }

    private Template getTemplate(World world) {
        if (template != null) {
            return template;
        }

        MinecraftServer mcServer = world.getMinecraftServer();
        TemplateManager manager = worldServer.getStructureTemplateManager();
        ResourceLocation location = new ResourceLocation(Netherized.MODID , structureName);
        template = manager.get(mcServer, location);
        if (template == null) {
            LogManager.getLogger().debug("The template, " + location + " could not be loaded");
            return null;
        }
        return template;
    }

    public BlockPos getSize(World world) {
        if (getTemplate(world) == null) {
            return BlockPos.ORIGIN;
        }

        return template.getSize();
    }

    public int getMaxVariation(World world) {
        if (maxVar != 0) {
            return maxVar;
        }

        if (getTemplate(world) == null) {
            return 0;
        }

        return (int) Math.floor(template.getSize().getY() * 0.25);
    }

    public BlockPos getCenter(World world) {
        if (getTemplate(world) == null) {
            return BlockPos.ORIGIN;
        }

        return new BlockPos(Math.floorDiv(template.getSize().getX(), 2), Math.floorDiv(template.getSize().getY(), 2), Math.floorDiv(template.getSize().getZ(), 2));
    }

    public int getYGenHeight(World world, int x, int z) {
        BlockPos templateSize = this.getSize(world);
        return ModUtils.getAverageGroundHeight(world, x, z, templateSize.getX(), templateSize.getZ(), this.getMaxVariation(world));
    }

    public void generateStructure(World worldIn, BlockPos pos, Rotation rotation) {
        if (getTemplate(worldIn) != null) {
            Map<Rotation, BlockPos> rotations = new HashMap<Rotation, BlockPos>();
            rotations.put(Rotation.NONE, new BlockPos(0, 0, 0));
            rotations.put(Rotation.CLOCKWISE_90, new BlockPos(template.getSize().getX() - 1, 0, 0));
            rotations.put(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, 0, template.getSize().getZ() - 1));
            rotations.put(Rotation.CLOCKWISE_180, new BlockPos(template.getSize().getX() - 1, 0, template.getSize().getZ() - 1));

            BlockPos rotationOffset = rotations.get(rotation);
            PlacementSettings rotatedSettings = settings.setRotation(rotation).setReplacedBlock(Blocks.STRUCTURE_VOID);
            BlockPos rotatedPos = pos.add(rotationOffset);

            template.addBlocksToWorld(worldIn, rotatedPos, rotatedSettings, 18);
            Map<BlockPos, String> dataBlocks = template.getDataBlocks(rotatedPos, rotatedSettings);
            for (Map.Entry<BlockPos, String> entry : dataBlocks.entrySet()) {
                String s = entry.getValue();
                this.handleDataMarker(s, entry.getKey(), worldIn, worldIn.rand);
            }
        }
    }

    protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand) {
    }
}