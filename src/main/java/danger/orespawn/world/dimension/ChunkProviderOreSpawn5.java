package danger.orespawn.world.dimension;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraftforge.event.terraingen.TerrainGen;

import danger.orespawn.OreSpawnMain;
import danger.orespawn.world.gen.CrystalMaze;

public class ChunkProviderOreSpawn5 implements IChunkProvider {

    private Random rand;
    private NoiseGeneratorOctaves field_147431_j;
    private NoiseGeneratorOctaves field_147432_k;
    private NoiseGeneratorOctaves field_147429_l;
    private NoiseGeneratorPerlin field_147430_m;
    public NoiseGeneratorOctaves noiseGen5;
    public NoiseGeneratorOctaves noiseGen6;
    public NoiseGeneratorOctaves mobSpawnerNoise;
    private World worldObj;
    private final boolean mapFeaturesEnabled;
    private WorldType field_147435_p;
    private final double[] field_147434_q;
    private final float[] parabolicField;
    private double[] stoneNoise = new double[256];
    private BiomeGenBase[] biomesForGeneration;
    double[] field_147427_d;
    double[] field_147428_e;
    double[] field_147425_f;
    double[] field_147426_g;
    int[][] field_73219_j = new int[32][32];

    public ChunkProviderOreSpawn5(World world, long time, boolean mapFeaturesEnabled2) {
        this.worldObj = world;
        this.mapFeaturesEnabled = mapFeaturesEnabled2;
        this.field_147435_p = world.getWorldInfo()
            .getTerrainType();
        this.rand = new Random(time);
        this.field_147431_j = new NoiseGeneratorOctaves(this.rand, 16);
        this.field_147432_k = new NoiseGeneratorOctaves(this.rand, 16);
        this.field_147429_l = new NoiseGeneratorOctaves(this.rand, 8);
        this.field_147430_m = new NoiseGeneratorPerlin(this.rand, 4);
        this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
        this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
        this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.rand, 8);
        this.field_147434_q = new double[825];
        this.parabolicField = new float[25];

        for (int j = -2; j <= 2; j++) {
            for (int k = -2; k <= 2; k++) {
                float f = 10.0F / MathHelper.sqrt_float(j * j + k * k + 0.2F);
                this.parabolicField[j + 2 + (k + 2) * 5] = f;
            }
        }

        NoiseGenerator[] noiseGens = new NoiseGenerator[] { this.field_147431_j, this.field_147432_k,
            this.field_147429_l, this.field_147430_m, this.noiseGen5, this.noiseGen6, this.mobSpawnerNoise };
        noiseGens = TerrainGen.getModdedNoiseGenerators(world, this.rand, noiseGens);
        this.field_147431_j = (NoiseGeneratorOctaves) noiseGens[0];
        this.field_147432_k = (NoiseGeneratorOctaves) noiseGens[1];
        this.field_147429_l = (NoiseGeneratorOctaves) noiseGens[2];
        this.field_147430_m = (NoiseGeneratorPerlin) noiseGens[3];
        this.noiseGen5 = (NoiseGeneratorOctaves) noiseGens[4];
        this.noiseGen6 = (NoiseGeneratorOctaves) noiseGens[5];
        this.mobSpawnerNoise = (NoiseGeneratorOctaves) noiseGens[6];
    }

    public void func_147424_a(int n, int n2, Block[] blocks) {
        byte b0 = 63;
        this.biomesForGeneration = this.worldObj.getWorldChunkManager()
            .getBiomesForGeneration(this.biomesForGeneration, n * 4 - 2, n2 * 4 - 2, 10, 10);
        this.func_147423_a(n * 4, 0, n2 * 4);

        for (int k = 0; k < 4; k++) {
            int l = k * 5;
            int i1 = (k + 1) * 5;

            for (int j1 = 0; j1 < 4; j1++) {
                int k1 = (l + j1) * 33;
                int l1 = (l + j1 + 1) * 33;
                int i2 = (i1 + j1) * 33;
                int j2 = (i1 + j1 + 1) * 33;

                for (int k2 = 0; k2 < 32; k2++) {
                    double d0 = 0.125;
                    double d1 = this.field_147434_q[k1 + k2];
                    double d2 = this.field_147434_q[l1 + k2];
                    double d3 = this.field_147434_q[i2 + k2];
                    double d4 = this.field_147434_q[j2 + k2];
                    double d5 = (this.field_147434_q[k1 + k2 + 1] - d1) * d0;
                    double d6 = (this.field_147434_q[l1 + k2 + 1] - d2) * d0;
                    double d7 = (this.field_147434_q[i2 + k2 + 1] - d3) * d0;
                    double d8 = (this.field_147434_q[j2 + k2 + 1] - d4) * d0;

                    for (int l2 = 0; l2 < 8; l2++) {
                        double d9 = 0.25;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int i3 = 0; i3 < 4; i3++) {
                            int j3 = i3 + k * 4 << 12 | 0 + j1 * 4 << 8 | k2 * 8 + l2;
                            short short1 = 256;
                            j3 -= short1;
                            double d14 = 0.25;
                            double d16 = (d11 - d10) * d14;
                            double d15 = d10 - d16;

                            for (int k3 = 0; k3 < 4; k3++) {
                                if ((d15 += d16) > 0.0) {
                                    blocks[j3 += short1] = OreSpawnMain.CrystalStone;
                                } else if (k2 * 8 + l2 < b0) {
                                    blocks[j3 += short1] = Blocks.water;
                                } else {
                                    blocks[j3 += short1] = null;
                                }
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

    public void replaceBlocksForBiome(int n, int n2, Block[] blocks, byte[] bytes, BiomeGenBase[] biomes) {
        double d0 = 0.03125;
        this.stoneNoise = this.field_147430_m
            .func_151599_a(this.stoneNoise, n * 16, n2 * 16, 16, 16, d0 * 2.0, d0 * 2.0, 1.0);

        for (int k = 0; k < 16; k++) {
            for (int l = 0; l < 16; l++) {
                this.MygenTerrainBlocks(
                    this.worldObj,
                    this.rand,
                    blocks,
                    bytes,
                    n * 16 + k,
                    n2 * 16 + l,
                    this.stoneNoise[l + k * 16]);
            }
        }
    }

    private void MygenTerrainBlocks(World world, Random rand2, Block[] blocks, byte[] bytes, int n, int n2, double d) {
        this.MygenBiomeTerrain(world, rand2, blocks, bytes, n, n2, d);
    }

    private final void MygenBiomeTerrain(World world, Random rand2, Block[] blocks, byte[] bytes, int n, int n2,
        double d) {
        boolean flag = true;
        Block block = OreSpawnMain.CrystalGrass;
        byte b0 = 0;
        Block block1 = OreSpawnMain.CrystalStone;
        int k = -1;
        int l = (int) (d / 3.0 + 3.0 + rand2.nextDouble() * 0.25);
        int i1 = n & 15;
        int j1 = n2 & 15;
        int k1 = blocks.length / 256;

        for (int l1 = 255; l1 >= 0; l1--) {
            int i2 = (j1 * 16 + i1) * k1 + l1;
            if (l1 <= 0 + rand2.nextInt(5)) {
                blocks[i2] = Blocks.bedrock;
            } else {
                Block block2 = blocks[i2];
                if (block2 == null || block2.getMaterial() == Material.air) {
                    k = -1;
                } else if (block2 == OreSpawnMain.CrystalStone) {
                    if (k == -1) {
                        if (l <= 0) {
                            block = null;
                            b0 = 0;
                            block1 = OreSpawnMain.CrystalStone;
                        } else if (l1 >= 59 && l1 <= 64) {
                            block = OreSpawnMain.CrystalGrass;
                            b0 = 0;
                            block1 = OreSpawnMain.CrystalStone;
                        }

                        if (l1 < 63 && (block == null || block.getMaterial() == Material.air)) {
                            block = Blocks.water;
                            b0 = 0;
                        }

                        k = l;
                        if (l1 >= 62) {
                            blocks[i2] = block;
                            bytes[i2] = b0;
                        } else {
                            blocks[i2] = block1;
                        }
                    } else if (k > 0) {
                        k--;
                        blocks[i2] = block1;
                    }
                }
            }
        }
    }

    public Chunk loadChunk(int chunkX, int chunkZ) {
        return this.provideChunk(chunkX, chunkZ);
    }

    public Chunk provideChunk(int chunkX, int chunkZ) {
        this.rand.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);
        Block[] ablock = new Block[65536];
        byte[] abyte = new byte[65536];
        this.func_147424_a(chunkX, chunkZ, ablock);
        this.biomesForGeneration = this.worldObj.getWorldChunkManager()
            .loadBlockGeneratorData(this.biomesForGeneration, chunkX * 16, chunkZ * 16, 16, 16);
        this.replaceBlocksForBiome(chunkX, chunkZ, ablock, abyte, this.biomesForGeneration);
        Chunk chunk = new Chunk(this.worldObj, ablock, abyte, chunkX, chunkZ);
        CrystalMaze cm = new CrystalMaze();
        cm.buildCrystalMaze(this.worldObj, chunkX * 16, 25, chunkZ * 16, chunk);
        this.generateCrystals(this.worldObj, this.rand, chunkX * 16, chunkZ * 16, chunk);
        this.addCrystalTrees(this.worldObj, this.rand, chunkX * 16, chunkZ * 16, chunk);
        this.generateCrystalOres(this.worldObj, this.rand, chunkX * 16, chunkZ * 16, chunk);
        this.addCrystalFlowers(this.worldObj, this.rand, chunkX * 16, chunkZ * 16, chunk);
        this.addRice(this.worldObj, this.rand, chunkX * 16, chunkZ * 16, chunk);
        this.addQuinoa(this.worldObj, this.rand, chunkX * 16, chunkZ * 16, chunk);
        chunk.generateSkylightMap();
        return chunk;
    }

    private void func_147423_a(int x, int y, int z) {
        double d0 = 684.412;
        double d1 = 684.412;
        double d2 = 512.0;
        double d3 = 512.0;
        this.field_147426_g = this.noiseGen6.generateNoiseOctaves(this.field_147426_g, x, z, 5, 5, 200.0, 200.0, 0.5);
        this.field_147427_d = this.field_147429_l.generateNoiseOctaves(
            this.field_147427_d,
            x,
            y,
            z,
            5,
            33,
            5,
            8.555150000000001,
            4.277575000000001,
            8.555150000000001);
        this.field_147428_e = this.field_147431_j
            .generateNoiseOctaves(this.field_147428_e, x, y, z, 5, 33, 5, 684.412, 684.412, 684.412);
        this.field_147425_f = this.field_147432_k
            .generateNoiseOctaves(this.field_147425_f, x, y, z, 5, 33, 5, 684.412, 684.412, 684.412);
        boolean flag1 = false;
        boolean flag = false;
        int l = 0;
        int i1 = 0;
        double d4 = 8.5;

        for (int j1 = 0; j1 < 5; j1++) {
            for (int k1 = 0; k1 < 5; k1++) {
                float f = 0.0F;
                float f1 = 0.0F;
                float f2 = 0.0F;
                byte b0 = 2;
                BiomeGenBase biomegenbase = this.biomesForGeneration[j1 + 2 + (k1 + 2) * 10];

                for (int l1 = -b0; l1 <= b0; l1++) {
                    for (int i2 = -b0; i2 <= b0; i2++) {
                        BiomeGenBase biomegenbase1 = this.biomesForGeneration[j1 + l1 + 2 + (k1 + i2 + 2) * 10];
                        float f3 = biomegenbase1.rootHeight;
                        float f4 = biomegenbase1.heightVariation;
                        if (this.field_147435_p == WorldType.AMPLIFIED && f3 > 0.0F) {
                            f3 = 1.0F + f3 * 2.0F;
                            f4 = 1.0F + f4 * 4.0F;
                        }

                        float f5 = this.parabolicField[l1 + 2 + (i2 + 2) * 5] / (f3 + 2.0F);
                        if (biomegenbase1.rootHeight > biomegenbase.rootHeight) {
                            f5 /= 2.0F;
                        }

                        f += f4 * f5;
                        f1 += f3 * f5;
                        f2 += f5;
                    }
                }

                f /= f2;
                f1 /= f2;
                f = f * 0.9F + 0.1F;
                f1 = (f1 * 4.0F - 1.0F) / 8.0F;
                double d13 = this.field_147426_g[i1] / 8000.0;
                if (d13 < 0.0) {
                    d13 = -d13 * 0.3;
                }

                d13 = d13 * 3.0 - 2.0;
                if (d13 < 0.0) {
                    d13 /= 2.0;
                    if (d13 < -1.0) {
                        d13 = -1.0;
                    }

                    d13 /= 1.4;
                    d13 /= 2.0;
                } else {
                    if (d13 > 1.0) {
                        d13 = 1.0;
                    }

                    d13 /= 8.0;
                }

                i1++;
                double d12 = f1;
                double d14 = f;
                d12 += d13 * 0.2;
                d12 = d12 * 8.5 / 8.0;
                double d5 = 8.5 + d12 * 4.0;

                for (int j2 = 0; j2 < 33; j2++) {
                    double d6 = (j2 - d5) * 12.0 * 128.0 / 256.0 / d14;
                    if (d6 < 0.0) {
                        d6 *= 4.0;
                    }

                    double d7 = this.field_147428_e[l] / 512.0;
                    double d8 = this.field_147425_f[l] / 512.0;
                    double d9 = (this.field_147427_d[l] / 10.0 + 1.0) / 2.0;
                    double d10 = MathHelper.denormalizeClamp(d7, d8, d9) - d6;
                    if (j2 > 29) {
                        double d11 = (j2 - 29) / 3.0F;
                        d10 = d10 * (1.0 - d11) + -10.0 * d11;
                    }

                    this.field_147434_q[l] = d10;
                    l++;
                }
            }
        }
    }

    public boolean chunkExists(int chunkX, int chunkZ) {
        return true;
    }

    public void populate(IChunkProvider provider, int chunkX, int chunkZ) {
        BlockFalling.fallInstantly = true;
        int k = chunkX * 16;
        int l = chunkZ * 16;
        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(k + 16, l + 16);
        this.rand.setSeed(this.worldObj.getSeed());
        long i1 = this.rand.nextLong() / 2L * 2L + 1L;
        long j1 = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed(chunkX * i1 + chunkZ * j1 ^ this.worldObj.getSeed());
        boolean flag = false;
        biomegenbase.decorate(this.worldObj, this.rand, k, l);
        SpawnerAnimals.performWorldGenSpawning(this.worldObj, biomegenbase, k + 8, l + 8, 16, 16, this.rand);
        BlockFalling.fallInstantly = false;
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
        return "CrystalDimension";
    }

    public List getPossibleCreatures(EnumCreatureType creatureType, int x, int y, int z) {
        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(x, z);
        return biomegenbase.getSpawnableList(creatureType);
    }

    public int getLoadedChunkCount() {
        return 0;
    }

    public void recreateStructures(int n, int n2) {}

    public void generateCrystals(World world, Random random, int chunkX, int chunkZ, Chunk chunk) {
        this.addPinkTourmaline(world, random, chunkX, chunkZ, chunk);
        this.addTigersEye(world, random, chunkX, chunkZ, chunk);
    }

    public void addPinkTourmaline(World world, Random random, int chunkX, int chunkZ, Chunk chunk) {
        if (random.nextInt(30) == 1) {
            int randPosX = 3 + chunkX + random.nextInt(10);
            int randPosY = 30 + random.nextInt(5);
            int randPosZ = 3 + chunkZ + random.nextInt(10);
            int patchy = 1 + random.nextInt(10);

            for (int i = 0; i < patchy; i++) {
                float dx = random.nextFloat() - random.nextFloat();
                float dz = random.nextFloat() - random.nextFloat();
                float dy = 0.5F + random.nextFloat() / 2.0F;
                int width = random.nextInt(2);
                int length = 1 + width * 3 + random.nextInt(15);
                float rx = randPosX;
                float ry = randPosY;
                float rz = randPosZ;

                for (int iy = 0; iy <= length; iy++) {
                    for (int ix = 0; ix <= width; ix++) {
                        for (int iz = 0; iz <= width; iz++) {
                            OreSpawnMain.setBlockIDWithMetadataInChunk(
                                chunk,
                                (int) (rx + ix),
                                (int) ry,
                                (int) (rz + iz),
                                OreSpawnMain.CrystalCrystal,
                                0);
                        }
                    }

                    ry += dy;
                    rx += dx;
                    rz += dz;
                }
            }
        }
    }

    public void addTigersEye(World world, Random random, int chunkX, int chunkZ, Chunk chunk) {
        if (random.nextInt(30) == 1) {
            int randPosX = 3 + chunkX + random.nextInt(10);
            int randPosY = 5 + random.nextInt(5);
            int randPosZ = 3 + chunkZ + random.nextInt(10);
            int patchy = 1 + random.nextInt(5);

            for (int i = 0; i < patchy; i++) {
                float dx = random.nextFloat() - random.nextFloat();
                float dz = random.nextFloat() - random.nextFloat();
                float dy = 0.5F + random.nextFloat() / 2.0F;
                int width = 0;
                int length = width * 3 + random.nextInt(6);
                float rx = randPosX;
                float ry = randPosY;
                float rz = randPosZ;

                for (int iy = 0; iy <= length; iy++) {
                    for (int ix = 0; ix <= width; ix++) {
                        for (int iz = 0; iz <= width; iz++) {
                            OreSpawnMain.setBlockIDWithMetadataInChunk(
                                chunk,
                                (int) (rx + ix),
                                (int) ry,
                                (int) (rz + iz),
                                OreSpawnMain.TigersEye,
                                0);
                        }
                    }

                    ry += dy;
                    rx += dx;
                    rz += dz;
                }
            }
        }
    }

    public void addCrystalTrees(World world, Random random, int chunkX, int chunkZ, Chunk chunk) {
        if (random.nextInt(5) == 0) {
            int howmany = 0;
            int what = random.nextInt(5);
            howmany = random.nextInt(8);
            if (what != 0) {
                howmany *= 2;
            }

            for (int i = 0; i < howmany; i++) {
                int posX = 4 + chunkX + random.nextInt(8);
                int posZ = 4 + chunkZ + random.nextInt(8);

                for (int posY = 128; posY > 40; posY--) {
                    if (OreSpawnMain.getBlockIDInChunk(chunk, posX, posY, posZ) == Blocks.air
                        && OreSpawnMain.getBlockIDInChunk(chunk, posX, posY - 1, posZ) == OreSpawnMain.CrystalGrass) {
                        if (what == 0) {
                            this.TallCrystalTree(world, posX, posY, posZ, chunk);
                        } else {
                            this.ScragglyCrystalTreeWithBranches(world, posX, posY, posZ, chunk);
                        }
                        break;
                    }
                }
            }
        }
    }

    public void makeScragglyCrystalBranch(World world, int x, int y, int z, int len, int biasx, int biasz,
        Chunk chunk) {
        for (int k = 0; k < len; k++) {
            int ix = world.rand.nextInt(2) - world.rand.nextInt(2) + biasx;
            int iz = world.rand.nextInt(2) - world.rand.nextInt(2) + biasz;
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

            int iy = world.rand.nextInt(3) > 0 ? 1 : 0;
            x += ix;
            z += iz;
            y += iy;
            Block bid = OreSpawnMain.getBlockIDInChunk(chunk, x, y, z);
            if (bid != Blocks.air && bid != OreSpawnMain.MyCrystalTreeLog && bid != OreSpawnMain.MyCrystalLeaves2) {
                return;
            }

            OreSpawnMain.setBlockIDWithMetadataInChunk(chunk, x, y, z, OreSpawnMain.MyCrystalTreeLog, 0);

            for (int m = -1; m < 2; m++) {
                for (int n = -1; n < 2; n++) {
                    if (world.rand.nextInt(2) == 1) {
                        bid = OreSpawnMain.getBlockIDInChunk(chunk, x + m, y, z + n);
                        if (bid == Blocks.air) {
                            OreSpawnMain.setBlockIDWithMetadataInChunk(
                                chunk,
                                x + m,
                                y,
                                z + n,
                                OreSpawnMain.MyCrystalLeaves2,
                                0);
                        }
                    }
                }
            }

            if (world.rand.nextInt(2) == 1) {
                bid = OreSpawnMain.getBlockIDInChunk(chunk, x, y + 1, z);
                if (bid == Blocks.air) {
                    OreSpawnMain.setBlockIDWithMetadataInChunk(chunk, x, y + 1, z, OreSpawnMain.MyCrystalLeaves2, 0);
                }
            }
        }
    }

    public void ScragglyCrystalTreeWithBranches(World world, int x, int y, int z, Chunk chunk) {
        int i = 1 + world.rand.nextInt(2);
        int j = i + world.rand.nextInt(8);

        for (int k = 0; k < i; k++) {
            Block bid = OreSpawnMain.getBlockIDInChunk(chunk, x, y + k, z);
            if (k >= 1 && bid != Blocks.air
                && bid != OreSpawnMain.MyCrystalTreeLog
                && bid != OreSpawnMain.MyCrystalLeaves2) {
                return;
            }

            OreSpawnMain.setBlockIDWithMetadataInChunk(chunk, x, y + k, z, OreSpawnMain.MyCrystalTreeLog, 0);
        }

        y += i - 1;

        for (int i2 = i; i2 < j; i2++) {
            int ix = world.rand.nextInt(2) - world.rand.nextInt(2);
            int iz = world.rand.nextInt(2) - world.rand.nextInt(2);
            int iy = world.rand.nextInt(4) > 0 ? 1 : 0;
            x += ix;
            z += iz;
            y += iy;
            Block bid = OreSpawnMain.getBlockIDInChunk(chunk, x, y, z);
            if (bid != Blocks.air && bid != OreSpawnMain.MyCrystalTreeLog && bid != OreSpawnMain.MyCrystalLeaves2) {
                break;
            }

            OreSpawnMain.setBlockIDWithMetadataInChunk(chunk, x, y, z, OreSpawnMain.MyCrystalTreeLog, 0);
            if (world.rand.nextInt(4) == 1) {
                this.makeScragglyCrystalBranch(
                    world,
                    x,
                    y,
                    z,
                    world.rand.nextInt(1 + j - i2),
                    world.rand.nextInt(2) - world.rand.nextInt(2),
                    world.rand.nextInt(2) - world.rand.nextInt(2),
                    chunk);
            }

            for (int m = -1; m < 2; m++) {
                for (int n = -1; n < 2; n++) {
                    if (world.rand.nextInt(2) == 1) {
                        bid = OreSpawnMain.getBlockIDInChunk(chunk, x + m, y, z + n);
                        if (bid == Blocks.air) {
                            OreSpawnMain.setBlockIDWithMetadataInChunk(
                                chunk,
                                x + m,
                                y,
                                z + n,
                                OreSpawnMain.MyCrystalLeaves2,
                                0);
                        }
                    }
                }
            }

            if (world.rand.nextInt(2) == 1) {
                bid = OreSpawnMain.getBlockIDInChunk(chunk, x, y + 1, z);
                if (bid == Blocks.air) {
                    OreSpawnMain.setBlockIDWithMetadataInChunk(chunk, x, y + 1, z, OreSpawnMain.MyCrystalLeaves2, 0);
                }
            }
        }
    }

    public void TallCrystalTree(World world, int x, int y, int z, Chunk chunk) {
        int i = 10 + world.rand.nextInt(12);
        if (OreSpawnMain.LessLag == 1) {
            i -= 2;
        }

        if (OreSpawnMain.LessLag == 2) {
            i -= 4;
        }

        int j = i + world.rand.nextInt(18 - OreSpawnMain.LessLag * 2);

        for (int k = 0; k < i; k++) {
            Block bid = OreSpawnMain.getBlockIDInChunk(chunk, x, y + k, z);
            if (k >= 1 && bid != Blocks.air
                && bid != OreSpawnMain.MyCrystalTreeLog
                && bid != OreSpawnMain.MyCrystalLeaves) {
                return;
            }

            OreSpawnMain.setBlockIDWithMetadataInChunk(chunk, x, y + k, z, OreSpawnMain.MyCrystalTreeLog, 0);
        }

        y += i - 1;

        for (int i2 = i; i2 < j; i2++) {
            Block bid = OreSpawnMain.getBlockIDInChunk(chunk, x, ++y, z);
            if (bid != Blocks.air && bid != OreSpawnMain.MyCrystalTreeLog && bid != OreSpawnMain.MyCrystalLeaves) {
                break;
            }

            OreSpawnMain.setBlockIDWithMetadataInChunk(chunk, x, y, z, OreSpawnMain.MyCrystalTreeLog, 0);
            if (i2 % 4 == 0) {
                for (int m = -1; m < 2; m++) {
                    for (int n = -1; n < 2; n++) {
                        if (world.rand.nextInt(2) == 1) {
                            bid = OreSpawnMain.getBlockIDInChunk(chunk, x + m, y, z + n);
                            if (bid == Blocks.air) {
                                OreSpawnMain.setBlockIDWithMetadataInChunk(
                                    chunk,
                                    x + m,
                                    y,
                                    z + n,
                                    OreSpawnMain.MyCrystalLeaves,
                                    0);
                            }
                        }
                    }
                }
            }
        }

        y++;

        for (int m = -1; m < 2; m++) {
            for (int nx = -1; nx < 2; nx++) {
                if (world.rand.nextInt(2) == 1) {
                    Block bidx = OreSpawnMain.getBlockIDInChunk(chunk, x + m, y, z + nx);
                    if (bidx == Blocks.air) {
                        OreSpawnMain
                            .setBlockIDWithMetadataInChunk(chunk, x + m, y, z + nx, OreSpawnMain.MyCrystalTreeLog, 0);
                    }
                }
            }
        }

        for (int i2 = -3; i2 < 4; i2++) {
            for (int nxx = -3; nxx < 4; nxx++) {
                Block bidx = OreSpawnMain.getBlockIDInChunk(chunk, x + i2, y, z + nxx);
                if (bidx == Blocks.air) {
                    OreSpawnMain
                        .setBlockIDWithMetadataInChunk(chunk, x + i2, y, z + nxx, OreSpawnMain.MyCrystalLeaves, 0);
                }
            }
        }

        y++;

        for (int i2 = -1; i2 < 2; i2++) {
            for (int nxxx = -1; nxxx < 2; nxxx++) {
                Block bidx = OreSpawnMain.getBlockIDInChunk(chunk, x + i2, y, z + nxxx);
                if (bidx == Blocks.air) {
                    OreSpawnMain
                        .setBlockIDWithMetadataInChunk(chunk, x + i2, y, z + nxxx, OreSpawnMain.MyCrystalLeaves, 0);
                }
            }
        }
    }

    public void generateCrystalOres(World world, Random random, int chunkX, int chunkZ, Chunk chunk) {
        int patchy = 25 + random.nextInt(30);
        if (random.nextInt(20) == 0) {
            patchy += 30;
        }

        for (int i = 0; i < patchy; i++) {
            int randPosX = 2 + chunkX + random.nextInt(12);
            int randPosY = random.nextInt(128);
            int randPosZ = 2 + chunkZ + random.nextInt(12);
            if (randPosY > 45) {
                int j = random.nextInt(11);
                Block b = Blocks.air;
                switch (j) {
                    case 0:
                        b = OreSpawnMain.MyUrchinSpawnBlock;
                        break;
                    case 1:
                        b = OreSpawnMain.MyFlounderSpawnBlock;
                        break;
                    case 2:
                        b = OreSpawnMain.MySkateSpawnBlock;
                        break;
                    case 3:
                        b = OreSpawnMain.MyRotatorSpawnBlock;
                        break;
                    case 4:
                        b = OreSpawnMain.MyPeacockSpawnBlock;
                        break;
                    case 5:
                        b = OreSpawnMain.MyFairySpawnBlock;
                        break;
                    case 6:
                        b = OreSpawnMain.MyDungeonBeastSpawnBlock;
                        break;
                    case 7:
                        b = OreSpawnMain.MyVortexSpawnBlock;
                        break;
                    case 8:
                        b = OreSpawnMain.MyRatSpawnBlock;
                        break;
                    case 9:
                        b = OreSpawnMain.MyWhaleSpawnBlock;
                        break;
                    case 10:
                        b = OreSpawnMain.MyIrukandjiSpawnBlock;
                }

                this.generateOre(world, random, randPosX, randPosY, randPosZ, chunk, b, 4, OreSpawnMain.CrystalStone);
            }
        }

        patchy = 3 + random.nextInt(8);

        for (int ix = 0; ix < patchy; ix++) {
            int randPosX = 2 + chunkX + random.nextInt(12);
            int randPosY = random.nextInt(128);
            int randPosZ = 2 + chunkZ + random.nextInt(12);
            this.generateOre(
                world,
                random,
                randPosX,
                randPosY,
                randPosZ,
                chunk,
                OreSpawnMain.CrystalCoal,
                6,
                OreSpawnMain.CrystalStone);
        }

        patchy = 15 + random.nextInt(20);

        for (int ix = 0; ix < patchy; ix++) {
            int randPosX = 2 + chunkX + random.nextInt(12);
            int randPosY = random.nextInt(128);
            int randPosZ = 2 + chunkZ + random.nextInt(12);
            if (randPosY < 25) {
                this.generateOre(
                    world,
                    random,
                    randPosX,
                    randPosY,
                    randPosZ,
                    chunk,
                    OreSpawnMain.CrystalRat,
                    6,
                    OreSpawnMain.CrystalStone);
            }
        }

        patchy = 12 + random.nextInt(20);

        for (int ixx = 0; ixx < patchy; ixx++) {
            int randPosX = 2 + chunkX + random.nextInt(12);
            int randPosY = random.nextInt(128);
            int randPosZ = 2 + chunkZ + random.nextInt(12);
            if (randPosY < 25) {
                this.generateOre(
                    world,
                    random,
                    randPosX,
                    randPosY,
                    randPosZ,
                    chunk,
                    OreSpawnMain.CrystalFairy,
                    6,
                    OreSpawnMain.CrystalStone);
            }
        }
    }

    public boolean generateOre(World world, Random rand2, int n, int n2, int n3, Chunk chunk, Block newbid,
        int numberOfBlocks, Block oldbid) {
        float f = rand2.nextFloat() * (float) Math.PI;
        double d0 = n + 8 + MathHelper.sin(f) * numberOfBlocks / 8.0F;
        double d1 = n + 8 - MathHelper.sin(f) * numberOfBlocks / 8.0F;
        double d2 = n3 + 8 + MathHelper.cos(f) * numberOfBlocks / 8.0F;
        double d3 = n3 + 8 - MathHelper.cos(f) * numberOfBlocks / 8.0F;
        double d4 = n2 + rand2.nextInt(3) - 2;
        double d5 = n2 + rand2.nextInt(3) - 2;

        for (int l = 0; l <= numberOfBlocks; l++) {
            double d6 = d0 + (d1 - d0) * l / numberOfBlocks;
            double d7 = d4 + (d5 - d4) * l / numberOfBlocks;
            double d8 = d2 + (d3 - d2) * l / numberOfBlocks;
            double d9 = rand2.nextDouble() * numberOfBlocks / 16.0;
            double d10 = (MathHelper.sin(l * (float) Math.PI / numberOfBlocks) + 1.0F) * d9 + 1.0;
            double d11 = (MathHelper.sin(l * (float) Math.PI / numberOfBlocks) + 1.0F) * d9 + 1.0;
            int i1 = MathHelper.floor_double(d6 - d10 / 2.0);
            int j1 = MathHelper.floor_double(d7 - d11 / 2.0);
            int k1 = MathHelper.floor_double(d8 - d10 / 2.0);
            int l1 = MathHelper.floor_double(d6 + d10 / 2.0);
            int i2 = MathHelper.floor_double(d7 + d11 / 2.0);
            int j2 = MathHelper.floor_double(d8 + d10 / 2.0);

            for (int k2 = i1; k2 <= l1; k2++) {
                double d12 = (k2 + 0.5 - d6) / (d10 / 2.0);
                if (d12 * d12 < 1.0) {
                    for (int l2 = j1; l2 <= i2; l2++) {
                        double d13 = (l2 + 0.5 - d7) / (d11 / 2.0);
                        if (d12 * d12 + d13 * d13 < 1.0) {
                            for (int i3 = k1; i3 <= j2; i3++) {
                                double d14 = (i3 + 0.5 - d8) / (d10 / 2.0);
                                Block bid = OreSpawnMain.getBlockIDInChunk(chunk, k2, l2, i3);
                                if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0 && bid == oldbid) {
                                    OreSpawnMain.setBlockIDWithMetadataInChunk(chunk, k2, l2, i3, newbid, 0);
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    public void addRice(World world, Random random, int chunkX, int chunkZ, Chunk chunk) {
        if (random.nextInt(10) == 0) {
            for (int i = 0; i < 5; i++) {
                int posX = chunkX + random.nextInt(16);
                int posZ = chunkZ + random.nextInt(16);

                for (int posY = 128; posY > 40; posY--) {
                    if (OreSpawnMain.getBlockIDInChunk(chunk, posX, posY, posZ) == Blocks.air
                        && OreSpawnMain.getBlockIDInChunk(chunk, posX, posY - 1, posZ) == OreSpawnMain.CrystalGrass) {
                        OreSpawnMain
                            .setBlockIDWithMetadataInChunk(chunk, posX, posY, posZ, OreSpawnMain.MyRicePlant, 0);
                        break;
                    }
                }
            }
        }
    }

    public void addQuinoa(World world, Random random, int chunkX, int chunkZ, Chunk chunk) {
        if (random.nextInt(20) == 0) {
            for (int i = 0; i < 5; i++) {
                int posX = chunkX + random.nextInt(16);
                int posZ = chunkZ + random.nextInt(16);

                for (int posY = 128; posY > 40; posY--) {
                    if (OreSpawnMain.getBlockIDInChunk(chunk, posX, posY, posZ) == Blocks.air
                        && OreSpawnMain.getBlockIDInChunk(chunk, posX, posY - 1, posZ) == OreSpawnMain.CrystalGrass) {
                        OreSpawnMain
                            .setBlockIDWithMetadataInChunk(chunk, posX, posY, posZ, OreSpawnMain.MyQuinoaPlant1, 0);
                        break;
                    }
                }
            }
        }
    }

    public void addCrystalFlowers(World world, Random random, int chunkX, int chunkZ, Chunk chunk) {
        if (random.nextInt(3) == 0) {
            int howmany = 0;
            howmany = 1 + random.nextInt(13);
            int what = random.nextInt(4);

            for (int i = 0; i < howmany; i++) {
                int posX = chunkX + random.nextInt(16);
                int posZ = chunkZ + random.nextInt(16);

                for (int posY = 128; posY > 40; posY--) {
                    if (OreSpawnMain.getBlockIDInChunk(chunk, posX, posY, posZ) == Blocks.air
                        && OreSpawnMain.getBlockIDInChunk(chunk, posX, posY - 1, posZ) == OreSpawnMain.CrystalGrass) {
                        if (what == 0) {
                            OreSpawnMain.setBlockIDWithMetadataInChunk(
                                chunk,
                                posX,
                                posY,
                                posZ,
                                OreSpawnMain.CrystalFlowerRedBlock,
                                0);
                        } else if (what == 1) {
                            OreSpawnMain.setBlockIDWithMetadataInChunk(
                                chunk,
                                posX,
                                posY,
                                posZ,
                                OreSpawnMain.CrystalFlowerGreenBlock,
                                0);
                        } else if (what == 2) {
                            OreSpawnMain.setBlockIDWithMetadataInChunk(
                                chunk,
                                posX,
                                posY,
                                posZ,
                                OreSpawnMain.CrystalFlowerBlueBlock,
                                0);
                        } else if (what == 3) {
                            OreSpawnMain.setBlockIDWithMetadataInChunk(
                                chunk,
                                posX,
                                posY,
                                posZ,
                                OreSpawnMain.CrystalFlowerYellowBlock,
                                0);
                        }
                        break;
                    }
                }
            }
        }
    }

    public ChunkPosition func_147416_a(World world, String str, int n, int n2, int n3) {
        return null;
    }
}
