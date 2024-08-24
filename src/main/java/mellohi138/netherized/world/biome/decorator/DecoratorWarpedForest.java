package mellohi138.netherized.world.biome.decorator;

import mellohi138.netherized.enums.EnumNetherForestType;
import mellohi138.netherized.init.NetherizedBlocks;
import mellohi138.netherized.world.gen.feature.FeatureHugeFungus;
import mellohi138.netherized.world.gen.feature.HellRootGroup;
import mellohi138.netherized.world.gen.feature.NetherPlantGroup;
import mellohi138.netherized.world.gen.feature.TwistingVinesGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class DecoratorWarpedForest extends BiomeDecorator {

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
    private final WorldGenerator genSprouts;
    private final WorldGenerator genFungi;
    private final WorldGenerator genTwistingVines;

    public DecoratorWarpedForest() {
        this.genHugeFungi = new FeatureHugeFungus(EnumNetherForestType.WARPED);
        this.genRoots = new HellRootGroup(NetherizedBlocks.WARPED_ROOTS, 64);
        this.genSprouts = new HellRootGroup(NetherizedBlocks.WARPED_SPROUTS, 64);
        this.genFungi = new NetherPlantGroup(NetherizedBlocks.WARPED_FUNGUS, 1, 64);
        this.genTwistingVines = new TwistingVinesGenerator();
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
        while (this.attempt < 50) {
            this.xx = this.x + this.offsetXZ();
            this.yy = 4 + this.rand.nextInt(116);
            this.zz = this.z + this.offsetXZ();
            this.genSprouts.generate(this.world, this.rand, new BlockPos(this.xx, this.yy, this.zz));
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
        while (this.attempt < 10) {
            this.xx = this.x + this.offsetXZ();
            this.yy = 4 + this.rand.nextInt(116);
            this.zz = this.z + this.offsetXZ();
            this.genFungi.generate(this.world, this.rand, new BlockPos(this.xx, this.yy, this.zz));
            ++this.attempt;
        }
        this.attempt = 0;
        while (this.attempt < 30) {
            this.xx = this.x + this.offsetXZ();
            this.yy = 4 + this.rand.nextInt(116);
            this.zz = this.z + this.offsetXZ();
            this.genTwistingVines.generate(this.world, this.rand, new BlockPos(this.xx, this.yy, this.zz));
            ++this.attempt;
        }
    }

    protected final int offsetXZ() {
        return this.rand.nextInt(16) + 8;
    }
}
