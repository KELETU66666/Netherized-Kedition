package mellohi138.netherized.world.gen.chunk;

import mellohi138.netherized.world.biome.INetherizedBiomes;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkGeneratorHell;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.Random;

public class ChunkGeneratorNetherized extends ChunkGeneratorHell {
    private final World world;
    private final Random rand;
    private double[] slowsandNoise = new double[256];
    private double[] gravelNoise = new double[256];
    private double[] depthBuffer = new double[256];
    protected Biome[] biomesForGeneration = new Biome[0];
    private final NoiseGeneratorPerlin stoneNoiseGen;
    private double[] stoneNoiseArray;

    public ChunkGeneratorNetherized(World worldIn, boolean p_i45637_2_, long seed) {
        super(worldIn, p_i45637_2_, seed);
        this.world = worldIn;
        this.rand = new Random(seed);
        this.stoneNoiseGen = new NoiseGeneratorPerlin(this.rand, 4);
        this.stoneNoiseArray = new double[256];
    }

    public World getWorld() {
        return world;
    }

    @Override
    public Chunk generateChunk(int x, int z) {
        this.rand.setSeed((long) x * 341873128712L + (long) z * 132897987541L);
        biomesForGeneration = this.world.getBiomeProvider().getBiomes(null, x * 16, z * 16, 16, 16);

        ChunkPrimer chunkprimer = new ChunkPrimer();
        this.prepareHeights(x, z, chunkprimer);
        this.buildSurfaces(x, z, chunkprimer);

        this.genNetherCaves.generate(this.world, x, z, chunkprimer);
        if (this.generateStructures) {
            this.genNetherBridge.generate(this.world, x, z, chunkprimer);
        }

        Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
        byte[] abyte = chunk.getBiomeArray();

        this.replaceBlocksForBiome(x, z, chunkprimer, biomesForGeneration);

        for (int i = 0; i < abyte.length; ++i) {
            abyte[i] = (byte) Biome.getIdForBiome(biomesForGeneration[i]);
        }

        chunk.resetRelightChecks();
        return chunk;
    }

    public void replaceBlocksForBiome(int chunkX, int chunkZ, ChunkPrimer primer, Biome[] biomes) {
        if (!ForgeEventFactory.onReplaceBiomeBlocks(this, chunkX, chunkZ, primer, this.world)) return;

        double d0 = 0.03125D;
        this.stoneNoiseArray = this.stoneNoiseGen.getRegion(this.stoneNoiseArray, chunkX * 16, chunkZ * 16, 16, 16, d0 * 2.0D, d0 * 2.0D, 1.0D);

        for (int localX = 0; localX < 16; ++localX) {
            for (int localZ = 0; localZ < 16; ++localZ) {
                Biome biome = biomes[localZ + localX * 16];
                biome.genTerrainBlocks(this.world, this.rand, primer, chunkX * 16 + localX, chunkZ * 16 + localZ, this.stoneNoiseArray[localZ + localX * 16]);
            }
        }
    }

    @Override
    public void buildSurfaces(int chunkX, int chunkZ, ChunkPrimer primer) {
        final int originX = chunkX << 4;
        final int originZ = chunkZ << 4;
        int i = this.world.getSeaLevel() + 1;

        slowsandNoise = slowsandGravelNoiseGen.generateNoiseOctaves(slowsandNoise, originX, originZ, 0, 16, 16, 1, 0.03125, 0.03125, 1);
        gravelNoise = slowsandGravelNoiseGen.generateNoiseOctaves(gravelNoise, originX, 109, originZ, 16, 1, 16, 0.03125, 1, 0.03125);
        depthBuffer = netherrackExculsivityNoiseGen.generateNoiseOctaves(depthBuffer, originX, originZ, 0, 16, 16, 1, 0.0625, 0.0625, 0.0625);

        for (int ix = 0; ix < 16; ix++) {
            for (int iz = 0; iz < 16; iz++) {
                //generate the bedrock
                for (int posY = 4; posY >= 0; posY--) {
                    if (posY <= rand.nextInt(5)) primer.setBlockState(ix, posY, iz, BEDROCK);
                    if (posY >= 4 - rand.nextInt(5))
                        primer.setBlockState(ix, posY + world.getActualHeight() - 5, iz, BEDROCK);
                }

                //replace netherrack top and filler blocks, and generate random soul sand & gravel
                final Biome biome = biomesForGeneration[iz << 4 | ix];
                if (biome instanceof INetherizedBiomes)
                    ((INetherizedBiomes) biome).buildSurface(this, chunkX, chunkZ, primer, ix, iz, slowsandNoise, gravelNoise, depthBuffer);
                else {
                    boolean flag = this.slowsandNoise[ix + iz * 16] + this.rand.nextDouble() * 0.2D > 0.0D;
                    boolean flag1 = this.gravelNoise[ix + iz * 16] + this.rand.nextDouble() * 0.2D > 0.0D;
                    int l = (int) (this.depthBuffer[ix + iz * 16] / 3.0D + 3.0D + this.rand.nextDouble() * 0.25D);
                    int i1 = -1;
                    IBlockState iblockstate = NETHERRACK;
                    IBlockState iblockstate1 = NETHERRACK;

                    for (int iy = 127; iy >= 0; --iy) {
                        if (iy < 127 - this.rand.nextInt(5) && iy > this.rand.nextInt(5)) {
                            IBlockState iblockstate2 = primer.getBlockState(iz, iy, ix);

                            if (iblockstate2.getBlock() != null && iblockstate2.getMaterial() != Material.AIR) {
                                if (iblockstate2.getBlock() == Blocks.NETHERRACK) {
                                    if (i1 == -1) {
                                        if (l <= 0) {
                                            iblockstate = AIR;
                                            iblockstate1 = NETHERRACK;
                                        } else if (iy >= i - 4 && iy <= i + 1) {
                                            iblockstate = NETHERRACK;
                                            iblockstate1 = NETHERRACK;

                                            if (flag1) {
                                                iblockstate = GRAVEL;
                                                iblockstate1 = NETHERRACK;
                                            }

                                            if (flag) {
                                                iblockstate = SOUL_SAND;
                                                iblockstate1 = SOUL_SAND;
                                            }
                                        }

                                        if (iy < i && (iblockstate == null || iblockstate.getMaterial() == Material.AIR)) {
                                            iblockstate = LAVA;
                                        }

                                        i1 = l;

                                        if (iy >= i - 1) {
                                            primer.setBlockState(iz, iy, ix, iblockstate);
                                        } else {
                                            primer.setBlockState(iz, iy, ix, iblockstate1);
                                        }
                                    } else if (i1 > 0) {
                                        --i1;
                                        primer.setBlockState(iz, iy, ix, iblockstate1);
                                    }
                                }
                            } else {
                                i1 = -1;
                            }
                        } else {
                            primer.setBlockState(iz, iy, ix, BEDROCK);
                        }
                    }
                }
            }
        }
    }
}