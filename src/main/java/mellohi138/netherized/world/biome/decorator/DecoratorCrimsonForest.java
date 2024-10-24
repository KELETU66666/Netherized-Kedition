package mellohi138.netherized.world.biome.decorator;

import mellohi138.netherized.enums.EnumNetherForestType;
import mellohi138.netherized.init.NetherizedBlocks;
import mellohi138.netherized.world.gen.feature.FeatureHugeFungus;
import mellohi138.netherized.world.gen.feature.HellRootGroup;
import mellohi138.netherized.world.gen.feature.NetherPlantGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class DecoratorCrimsonForest extends BiomeDecorator {

    protected World world;
    protected Random rand;
    protected int x;
    protected int z;
    protected int xx;
    protected int yy;
    protected int zz;
    protected int attempt;

    private final WorldGenerator genHugeFungi;
    private final WorldGenerator genRoots;
    private final WorldGenerator genFungi;
    private final WorldGenerator genShrub;
    private final WorldGenerator genWarpedFungi;

    public DecoratorCrimsonForest() {
        //this.genNetherrackSplatter = new BlockSplatter(Blocks.NETHERRACK, 0, 128, NetherizedBlocks.CRIMSON_NYLIUM);
        this.genHugeFungi = new FeatureHugeFungus(EnumNetherForestType.CRIMSON);
        this.genRoots = new HellRootGroup(NetherizedBlocks.CRIMSON_ROOTS, 64);
        this.genFungi = new NetherPlantGroup(NetherizedBlocks.CRIMSON_FUNGUS, 0, 64);
        this.genShrub = new NetherPlantGroup(NetherizedBlocks.CRIMSON_ROOTS, 0, 64);
        this.genWarpedFungi = new NetherPlantGroup(NetherizedBlocks.WARPED_FUNGUS, 1, 10);
    }

    @Override
    public void decorate(World world, Random rand, Biome biome, BlockPos pos) {
        this.world = world;
        this.rand = rand;
        this.x = pos.getX();
        this.z = pos.getZ();

        this.attempt = 0;
        while (this.attempt < 550) {
            this.xx = this.x + 9 + this.rand.nextInt(14);
            this.yy = 4 + this.rand.nextInt(116);
            this.zz = this.z + 9 + this.rand.nextInt(14);
            this.genHugeFungi.generate(this.world, this.rand, new BlockPos(this.xx, this.yy, this.zz));
            ++this.attempt;
        }
        this.attempt = 0;
        while (this.attempt < 30) {
            this.xx = this.x + this.offsetXZ();
            this.yy = 4 + this.rand.nextInt(116);
            this.zz = this.z + this.offsetXZ();
            this.genRoots.generate(this.world, this.rand, new BlockPos(this.xx, this.yy, this.zz));
            ++this.attempt;
        }
        this.attempt = 0;
        while (this.attempt < 20) {
            this.xx = this.x + this.offsetXZ();
            this.yy = 4 + this.rand.nextInt(116);
            this.zz = this.z + this.offsetXZ();
            this.genShrub.generate(this.world, this.rand, new BlockPos(this.xx, this.yy, this.zz));
            ++this.attempt;
        }
        this.attempt = 0;
        while (this.attempt < 10) {
            this.xx = this.x + this.offsetXZ();
            this.yy = 4 + this.rand.nextInt(116);
            this.zz = this.z + this.offsetXZ();
            this.genFungi.generate(this.world, this.rand, new BlockPos(this.xx, this.yy, this.zz));
            ++this.attempt;
        }
        this.attempt = 0;
        while (this.attempt < 5) {
            this.xx = this.x + this.offsetXZ();
            this.yy = 4 + this.rand.nextInt(116);
            this.zz = this.z + this.offsetXZ();
            this.genWarpedFungi.generate(this.world, this.rand, new BlockPos(this.xx, this.yy, this.zz));
            ++this.attempt;
        }
    }

    protected final int offsetXZ() {
        return this.rand.nextInt(16) + 8;
    }
}
