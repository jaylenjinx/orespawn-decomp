package danger.orespawn.world.dimension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCaves;
import net.minecraft.world.gen.MapGenRavine;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraftforge.event.terraingen.TerrainGen;

import danger.orespawn.OreSpawnMain;

public class ChunkProviderOreSpawn implements IChunkProvider {

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
    private MapGenBase caveGenerator = new MapGenCaves();
    private ArrayList MyAmbientList = null;
    private ArrayList MyCritterList = null;
    private MapGenStronghold strongholdGenerator = new MapGenStronghold();
    private MapGenMineshaft mineshaftGenerator = new MapGenMineshaft();
    private MapGenScatteredFeature scatteredFeatureGenerator = new MapGenScatteredFeature();
    private MapGenBase ravineGenerator = new MapGenRavine();
    private BiomeGenBase[] biomesForGeneration;
    double[] field_147427_d;
    double[] field_147428_e;
    double[] field_147425_f;
    double[] field_147426_g;
    int[][] field_73219_j = new int[32][32];

    public ChunkProviderOreSpawn(World world, long time, boolean mapFeaturesEnabled2) {
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
                                    blocks[j3 += short1] = Blocks.stone;
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
                BiomeGenBase biomegenbase = biomes[l + k * 16];
                biomegenbase.genTerrainBlocks(
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
        this.caveGenerator.func_151539_a(this, this.worldObj, chunkX, chunkZ, ablock);
        this.ravineGenerator.func_151539_a(this, this.worldObj, chunkX, chunkZ, ablock);
        Chunk chunk = new Chunk(this.worldObj, ablock, abyte, chunkX, chunkZ);
        OreSpawnMain.Chunker.generateOresInChunk(this.worldObj, this.rand, chunkX * 16, chunkZ * 16, chunk);
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
        BlockFalling.fallInstantly = false;
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
        return "UtopiaDimension";
    }

    public List getPossibleCreatures(EnumCreatureType creatureType, int x, int y, int z) {
        List l = null;
        List m = null;
        Iterator iterator = null;
        SpawnListEntry spawnEntry = null;
        BiomeGenBase biomeGenForCoords = null;
        biomeGenForCoords = this.worldObj.getBiomeGenForCoords(x, z);
        if (biomeGenForCoords == null) {
            return null;
        } else {
            l = biomeGenForCoords.getSpawnableList(creatureType);
            if (l == null) {
                return null;
            } else if (creatureType == EnumCreatureType.monster) {
                return null;
            } else if (creatureType != EnumCreatureType.ambient) {
                iterator = l.iterator();
                if (iterator == null) {
                    return null;
                } else {
                    List list = new ArrayList();
                    spawnEntry = null;

                    while (iterator.hasNext()) {
                        spawnEntry = (SpawnListEntry) iterator.next();
                        if (spawnEntry != null && spawnEntry.entityClass != EntityHorse.class) {
                            list.add(spawnEntry);
                        }
                    }

                    return list;
                }
            } else {
                if (this.MyAmbientList == null) {
                    this.MyAmbientList = new ArrayList();
                    iterator = l.iterator();
                    if (iterator == null) {
                        return null;
                    }

                    while (iterator.hasNext()) {
                        spawnEntry = (SpawnListEntry) iterator.next();
                        if (spawnEntry.entityClass != EntityHorse.class) {
                            this.MyAmbientList.add(spawnEntry);
                        }
                    }
                }

                return this.MyAmbientList;
            }
        }
    }

    public ChunkPosition func_147416_a(World world, String str, int n, int n2, int n3) {
        return "Stronghold".equals(str) && this.strongholdGenerator != null
            ? this.strongholdGenerator.func_151545_a(world, n, n2, n3)
            : null;
    }

    public int getLoadedChunkCount() {
        return 0;
    }

    public void recreateStructures(int n, int n2) {
        if (this.mapFeaturesEnabled) {
            this.mineshaftGenerator.func_151539_a(this, this.worldObj, n, n2, (Block[]) null);
            this.strongholdGenerator.func_151539_a(this, this.worldObj, n, n2, (Block[]) null);
            this.scatteredFeatureGenerator.func_151539_a(this, this.worldObj, n, n2, (Block[]) null);
        }
    }
}
