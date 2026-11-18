package danger.orespawn.world.dimension;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraftforge.event.terraingen.TerrainGen;

import danger.orespawn.OreSpawnMain;

public class ChunkProviderOreSpawn6 implements IChunkProvider {

    private Random hellRNG;
    private Random random;
    private NoiseGeneratorOctaves netherNoiseGen1;
    private NoiseGeneratorOctaves netherNoiseGen2;
    private NoiseGeneratorOctaves netherNoiseGen3;
    private NoiseGeneratorOctaves slowsandGravelNoiseGen;
    private NoiseGeneratorOctaves netherrackExculsivityNoiseGen;
    public NoiseGeneratorOctaves netherNoiseGen6;
    public NoiseGeneratorOctaves netherNoiseGen7;
    private World worldObj;
    private double[] noiseField;
    private double[] slowsandNoise = new double[256];
    private double[] gravelNoise = new double[256];
    private double[] netherrackExclusivityNoise = new double[256];
    double[] noiseData1;
    double[] noiseData2;
    double[] noiseData3;
    double[] noiseData4;
    double[] noiseData5;

    public ChunkProviderOreSpawn6(World worldObj2, long time) {
        this.worldObj = worldObj2;
        this.hellRNG = new Random(time);
        this.random = new Random(time);
        this.netherNoiseGen1 = new NoiseGeneratorOctaves(this.hellRNG, 16);
        this.netherNoiseGen2 = new NoiseGeneratorOctaves(this.hellRNG, 16);
        this.netherNoiseGen3 = new NoiseGeneratorOctaves(this.hellRNG, 8);
        this.slowsandGravelNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 4);
        this.netherrackExculsivityNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 4);
        this.netherNoiseGen6 = new NoiseGeneratorOctaves(this.hellRNG, 10);
        this.netherNoiseGen7 = new NoiseGeneratorOctaves(this.hellRNG, 16);
        NoiseGenerator[] noiseGens = new NoiseGenerator[] { this.netherNoiseGen1, this.netherNoiseGen2,
            this.netherNoiseGen3, this.slowsandGravelNoiseGen, this.netherrackExculsivityNoiseGen, this.netherNoiseGen6,
            this.netherNoiseGen7 };
        noiseGens = TerrainGen.getModdedNoiseGenerators(worldObj2, this.hellRNG, noiseGens);
        this.netherNoiseGen1 = (NoiseGeneratorOctaves) noiseGens[0];
        this.netherNoiseGen2 = (NoiseGeneratorOctaves) noiseGens[1];
        this.netherNoiseGen3 = (NoiseGeneratorOctaves) noiseGens[2];
        this.slowsandGravelNoiseGen = (NoiseGeneratorOctaves) noiseGens[3];
        this.netherrackExculsivityNoiseGen = (NoiseGeneratorOctaves) noiseGens[4];
        this.netherNoiseGen6 = (NoiseGeneratorOctaves) noiseGens[5];
        this.netherNoiseGen7 = (NoiseGeneratorOctaves) noiseGens[6];
    }

    public void func_147419_a(int n, int n2, Block[] blocks) {
        byte b0 = 4;
        byte b1 = 32;
        int k = b0 + 1;
        byte b2 = 17;
        int l = b0 + 1;
        Block block = Blocks.stone;
        this.noiseField = this.initializeNoiseField(this.noiseField, n * b0, 0, n2 * b0, k, b2, l);

        for (int i1 = 0; i1 < b0; i1++) {
            for (int j1 = 0; j1 < b0; j1++) {
                for (int k1 = 0; k1 < 16; k1++) {
                    double d0 = 0.125;
                    double d1 = this.noiseField[((i1 + 0) * l + j1 + 0) * b2 + k1 + 0];
                    double d2 = this.noiseField[((i1 + 0) * l + j1 + 1) * b2 + k1 + 0];
                    double d3 = this.noiseField[((i1 + 1) * l + j1 + 0) * b2 + k1 + 0];
                    double d4 = this.noiseField[((i1 + 1) * l + j1 + 1) * b2 + k1 + 0];
                    double d5 = (this.noiseField[((i1 + 0) * l + j1 + 0) * b2 + k1 + 1] - d1) * d0;
                    double d6 = (this.noiseField[((i1 + 0) * l + j1 + 1) * b2 + k1 + 1] - d2) * d0;
                    double d7 = (this.noiseField[((i1 + 1) * l + j1 + 0) * b2 + k1 + 1] - d3) * d0;
                    double d8 = (this.noiseField[((i1 + 1) * l + j1 + 1) * b2 + k1 + 1] - d4) * d0;

                    for (int l1 = 0; l1 < 8; l1++) {
                        double d9 = 0.25;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int i2 = 0; i2 < 4; i2++) {
                            int j2 = i2 + i1 * 4 << 11 | 0 + j1 * 4 << 7 | k1 * 8 + l1;
                            short short1 = 128;
                            double d14 = 0.25;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;

                            for (int k2 = 0; k2 < 4; k2++) {
                                block = Blocks.stone;
                                if (d15 > 0.0) {
                                    block = null;
                                }

                                blocks[j2] = block;
                                j2 += short1;
                                d15 += d16;
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }

    public void replaceBiomeBlocks(int n, int n2, Block[] blocks, byte[] meta, BiomeGenBase[] biomes) {
        byte b0 = 64;
        double d0 = 0.03125;
        this.slowsandNoise = this.slowsandGravelNoiseGen
            .generateNoiseOctaves(this.slowsandNoise, n * 16, n2 * 16, 0, 16, 16, 1, d0, d0, 1.0);
        this.gravelNoise = this.slowsandGravelNoiseGen
            .generateNoiseOctaves(this.gravelNoise, n * 16, 109, n2 * 16, 16, 1, 16, d0, 1.0, d0);
        this.netherrackExclusivityNoise = this.netherrackExculsivityNoiseGen.generateNoiseOctaves(
            this.netherrackExclusivityNoise,
            n * 16,
            n2 * 16,
            0,
            16,
            16,
            1,
            d0 * 2.0,
            d0 * 2.0,
            d0 * 2.0);

        for (int k = 0; k < 16; k++) {
            for (int l = 0; l < 16; l++) {
                boolean flag = this.slowsandNoise[k + l * 16] + this.hellRNG.nextDouble() * 0.2 > 0.0;
                boolean flag1 = this.gravelNoise[k + l * 16] + this.hellRNG.nextDouble() * 0.2 > 0.0;
                int i1 = (int) (this.netherrackExclusivityNoise[k + l * 16] / 3.0 + 3.0
                    + this.hellRNG.nextDouble() * 0.25);
                int j1 = -1;
                Block block = Blocks.grass;
                Block block1 = Blocks.dirt;

                for (int k1 = 127; k1 >= 0; k1--) {
                    int l1 = (l * 16 + k) * 128 + k1;
                    if (k1 < 127 - this.hellRNG.nextInt(5) && k1 > 0 + this.hellRNG.nextInt(5)) {
                        Block block2 = blocks[l1];
                        if (block2 == null || block2.getMaterial() == Material.air) {
                            j1 = -1;
                        } else if (block2 == Blocks.stone) {
                            if (j1 == -1) {
                                if (i1 <= 0) {
                                    block = null;
                                    block1 = Blocks.stone;
                                } else if (k1 >= b0 - 4 && k1 <= b0 + 1) {
                                    block = Blocks.stone;
                                    block1 = Blocks.stone;
                                    if (flag1) {
                                        block = Blocks.grass;
                                        block1 = Blocks.dirt;
                                    }

                                    if (flag) {
                                        block = Blocks.grass;
                                        block1 = Blocks.dirt;
                                    }
                                }

                                if (k1 < b0 && block == Blocks.air) {
                                    block = Blocks.water;
                                }

                                j1 = i1;
                                if (k1 >= b0 - 1) {
                                    blocks[l1] = block;
                                } else {
                                    blocks[l1] = block1;
                                }
                            } else if (j1 > 0) {
                                j1--;
                                blocks[l1] = block1;
                            }
                        }
                    } else {
                        blocks[l1] = null;
                    }
                }
            }
        }
    }

    public Chunk loadChunk(int chunkX, int chunkZ) {
        return this.provideChunk(chunkX, chunkZ);
    }

    public Chunk provideChunk(int chunkX, int chunkZ) {
        this.hellRNG.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);
        Block[] ablock = new Block[32768];
        byte[] meta = new byte[ablock.length];
        this.func_147419_a(chunkX, chunkZ, ablock);
        this.replaceBiomeBlocks(chunkX, chunkZ, ablock, meta, null);
        Chunk chunk = new Chunk(this.worldObj, ablock, meta, chunkX, chunkZ);
        OreSpawnMain.Chunker.generateOresInChunk(this.worldObj, this.worldObj.rand, chunkX * 16, chunkZ * 16, chunk);
        this.addScragglyTrees(this.worldObj, chunkX * 16, chunkZ * 16, chunk);
        chunk.generateSkylightMap();
        return chunk;
    }

    private double[] initializeNoiseField(double[] values, int n, int n2, int n3, int n4, int n5, int n6) {
        int k1 = 0;
        int l1 = 0;
        double[] adouble1 = new double[n5];
        if (values == null) {
            values = new double[n4 * n5 * n6];
        }

        double d0 = 684.412;
        double d1 = 2053.236;
        this.noiseData4 = this.netherNoiseGen6
            .generateNoiseOctaves(this.noiseData4, n, n2, n3, n4, 1, n6, 1.0, 0.0, 1.0);
        this.noiseData5 = this.netherNoiseGen7
            .generateNoiseOctaves(this.noiseData5, n, n2, n3, n4, 1, n6, 100.0, 0.0, 100.0);
        this.noiseData1 = this.netherNoiseGen3
            .generateNoiseOctaves(this.noiseData1, n, n2, n3, n4, n5, n6, d0 / 80.0, d1 / 60.0, d0 / 80.0);
        this.noiseData2 = this.netherNoiseGen1.generateNoiseOctaves(this.noiseData2, n, n2, n3, n4, n5, n6, d0, d1, d0);
        this.noiseData3 = this.netherNoiseGen2.generateNoiseOctaves(this.noiseData3, n, n2, n3, n4, n5, n6, d0, d1, d0);

        for (int i2 = 0; i2 < n5; i2++) {
            adouble1[i2] = Math.cos(i2 * Math.PI * 6.0 / n5) * 2.0;
            double d2 = i2;
            if (i2 > n5 / 2) {
                d2 = n5 - 1 - i2;
            }

            if (d2 < 4.0) {
                d2 = 4.0 - d2;
                adouble1[i2] -= d2 * d2 * d2 * 10.0;
            }
        }

        for (int i = 0; i < n4; i++) {
            for (int k2 = 0; k2 < n6; k2++) {
                double d3 = (this.noiseData4[l1] + 256.0) / 512.0;
                if (d3 > 1.0) {
                    d3 = 1.0;
                }

                double d4 = 0.0;
                double d5 = this.noiseData5[l1] / 8000.0;
                if (d5 < 0.0) {
                    d5 = -d5;
                }

                d5 = d5 * 3.0 - 3.0;
                if (d5 < 0.0) {
                    d5 /= 2.0;
                    if (d5 < -1.0) {
                        d5 = -1.0;
                    }

                    d5 /= 1.4;
                    d5 /= 2.0;
                    d3 = 0.0;
                } else {
                    if (d5 > 1.0) {
                        d5 = 1.0;
                    }

                    d5 /= 6.0;
                }

                d3 += 0.5;
                d5 = d5 * n5 / 16.0;
                l1++;

                for (int j2 = 0; j2 < n5; j2++) {
                    double d6 = 0.0;
                    double d7 = adouble1[j2];
                    double d8 = this.noiseData2[k1] / 512.0;
                    double d9 = this.noiseData3[k1] / 512.0;
                    double d10 = (this.noiseData1[k1] / 10.0 + 1.0) / 2.0;
                    if (d10 < 0.0) {
                        d6 = d8;
                    } else if (d10 > 1.0) {
                        d6 = d9;
                    } else {
                        d6 = d8 + (d9 - d8) * d10;
                    }

                    d6 -= d7;
                    if (j2 > n5 - 4) {
                        double d11 = (j2 - (n5 - 4)) / 3.0F;
                        d6 = d6 * (1.0 - d11) + -10.0 * d11;
                    }

                    if (j2 < d4) {
                        double d11 = (d4 - j2) / 4.0;
                        if (d11 < 0.0) {
                            d11 = 0.0;
                        }

                        if (d11 > 1.0) {
                            d11 = 1.0;
                        }

                        d6 = d6 * (1.0 - d11) + -10.0 * d11;
                    }

                    values[k1] = d6;
                    k1++;
                }
            }
        }

        return values;
    }

    public boolean chunkExists(int chunkX, int chunkZ) {
        return true;
    }

    public void populate(IChunkProvider provider, int chunkX, int chunkZ) {
        BlockFalling.fallInstantly = false;
        int n = chunkX * 16;
        int n2 = chunkZ * 16;
        BiomeGenBase biomeGenForCoords = this.worldObj.getBiomeGenForCoords(n + 16, n2 + 16);
        biomeGenForCoords.decorate(this.worldObj, this.worldObj.rand, n, n2);
        SpawnerAnimals
            .performWorldGenSpawning(this.worldObj, biomeGenForCoords, n + 8, n2 + 8, 16, 16, this.worldObj.rand);
    }

    public boolean saveChunks(boolean saveAll, IProgressUpdate progress) {
        return true;
    }

    public void saveExtraData() {}

    public boolean unloadQueuedChunks() {
        return false;
    }

    public boolean canSave() {
        return true;
    }

    public String makeString() {
        return "ChaosDimension";
    }

    public List getPossibleCreatures(EnumCreatureType creatureType, int x, int y, int z) {
        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(x, z);
        return biomegenbase.getSpawnableList(creatureType);
    }

    public ChunkPosition func_147416_a(World world, String str, int n, int n2, int n3) {
        return null;
    }

    public int getLoadedChunkCount() {
        return 0;
    }

    public void recreateStructures(int n, int n2) {}

    public void addScragglyTrees(World world, int chunkX, int chunkZ, Chunk chunk) {
        int howmany = 1 + world.rand.nextInt(5);
        if (world.rand.nextInt(4) == 0) {
            if (OreSpawnMain.LessLag == 1) {
                howmany /= 2;
            }

            if (OreSpawnMain.LessLag == 2) {
                howmany /= 4;
            }

            if (howmany != 0) {
                for (int i = 0; i < howmany; i++) {
                    int posX = 2 + chunkX + this.random.nextInt(12);
                    int posZ = 2 + chunkZ + this.random.nextInt(12);

                    for (int posY = 120; posY > 50; posY--) {
                        if (OreSpawnMain.getBlockIDInChunk(chunk, posX, posY - 1, posZ) == Blocks.grass) {
                            this.ScragglyTreeWithBranches(world, posX, posY, posZ, chunk);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void makeScragglyBranch(World world, int x, int y, int z, int len, int biasx, int biasz, Chunk chunk) {
        for (int k = 0; k < len; k++) {
            int ix = this.random.nextInt(2) - this.random.nextInt(2) + biasx;
            int iz = this.random.nextInt(2) - this.random.nextInt(2) + biasz;
            if (ix > 1) {
                ix = 1;
            }

            if (ix < -1) {
                ix = -1;
            }

            if (iz > 1) {
                iz = 1;
            }

            if (iz < -1) {
                iz = -1;
            }

            int iy = this.random.nextInt(3) > 0 ? 1 : 0;
            x += ix;
            z += iz;
            y += iy;
            Block bid = OreSpawnMain.getBlockIDInChunk(chunk, x, y, z);
            if (bid != Blocks.air && bid != Blocks.log && bid != OreSpawnMain.MyAppleLeaves) {
                return;
            }

            OreSpawnMain.setBlockIDWithMetadataInChunk(chunk, x, y, z, Blocks.log, 0);

            for (int m = -1; m < 2; m++) {
                for (int n = -1; n < 2; n++) {
                    if (this.random.nextInt(2) == 1) {
                        bid = OreSpawnMain.getBlockIDInChunk(chunk, x + m, y, z + n);
                        if (bid == Blocks.air) {
                            OreSpawnMain
                                .setBlockIDWithMetadataInChunk(chunk, x + m, y, z + n, OreSpawnMain.MyAppleLeaves, 0);
                        }
                    }
                }
            }

            if (this.random.nextInt(2) == 1) {
                bid = OreSpawnMain.getBlockIDInChunk(chunk, x, y + 1, z);
                if (bid == Blocks.air) {
                    OreSpawnMain.setBlockIDWithMetadataInChunk(chunk, x, y + 1, z, OreSpawnMain.MyAppleLeaves, 0);
                }
            }
        }
    }

    public void ScragglyTreeWithBranches(World world, int x, int y, int z, Chunk chunk) {
        int i = 1 + this.random.nextInt(3);
        int j = i + this.random.nextInt(12);

        for (int k = 0; k < i; k++) {
            Block bid = OreSpawnMain.getBlockIDInChunk(chunk, x, y + k, z);
            if (k >= 1 && bid != Blocks.air && bid != Blocks.log && bid != OreSpawnMain.MyAppleLeaves) {
                return;
            }

            OreSpawnMain.setBlockIDWithMetadataInChunk(chunk, x, y + k, z, Blocks.log, 0);
        }

        y += i - 1;

        for (int i2 = i; i2 < j; i2++) {
            int ix = this.random.nextInt(2) - this.random.nextInt(2);
            int iz = this.random.nextInt(2) - this.random.nextInt(2);
            int iy = this.random.nextInt(4) > 0 ? 1 : 0;
            x += ix;
            z += iz;
            y += iy;
            Block bid = OreSpawnMain.getBlockIDInChunk(chunk, x, y, z);
            if (bid != Blocks.air && bid != Blocks.log && bid != OreSpawnMain.MyAppleLeaves) {
                break;
            }

            OreSpawnMain.setBlockIDWithMetadataInChunk(chunk, x, y, z, Blocks.log, 0);
            if (this.random.nextInt(4) == 1) {
                this.makeScragglyBranch(
                    world,
                    x,
                    y,
                    z,
                    this.random.nextInt(1 + j - i2),
                    this.random.nextInt(2) - this.random.nextInt(2),
                    this.random.nextInt(2) - this.random.nextInt(2),
                    chunk);
            }

            for (int m = -1; m < 2; m++) {
                for (int n = -1; n < 2; n++) {
                    if (this.random.nextInt(2) == 1) {
                        bid = OreSpawnMain.getBlockIDInChunk(chunk, x + m, y, z + n);
                        if (bid == Blocks.air) {
                            OreSpawnMain
                                .setBlockIDWithMetadataInChunk(chunk, x + m, y, z + n, OreSpawnMain.MyAppleLeaves, 0);
                        }
                    }
                }
            }

            if (this.random.nextInt(2) == 1) {
                bid = OreSpawnMain.getBlockIDInChunk(chunk, x, y + 1, z);
                if (bid == Blocks.air) {
                    OreSpawnMain.setBlockIDWithMetadataInChunk(chunk, x, y + 1, z, OreSpawnMain.MyAppleLeaves, 0);
                }
            }
        }
    }
}
