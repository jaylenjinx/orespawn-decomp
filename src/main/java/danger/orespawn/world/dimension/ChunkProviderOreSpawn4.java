package danger.orespawn.world.dimension;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

import danger.orespawn.OreSpawnMain;

public class ChunkProviderOreSpawn4 implements IChunkProvider {

    private World worldObj;
    private Random random;
    private final Block[] cachedBlockIDs = new Block[256];
    private final byte[] cachedBlockMetadata = new byte[256];

    public ChunkProviderOreSpawn4(World world, long time, boolean flag) {
        this.worldObj = world;
        this.random = new Random(time);

        for (int j = 0; j < 8; j++) {
            if (j == 0) {
                this.cachedBlockIDs[j] = Blocks.bedrock;
            } else if (j == 7) {
                this.cachedBlockIDs[j] = Blocks.grass;
            } else {
                this.cachedBlockIDs[j] = Blocks.dirt;
            }
        }
    }

    public Chunk loadChunk(int chunkX, int chunkZ) {
        return this.provideChunk(chunkX, chunkZ);
    }

    public Chunk provideChunk(int chunkX, int chunkZ) {
        Chunk chunk = new Chunk(this.worldObj, chunkX, chunkZ);

        for (int k = 0; k < this.cachedBlockIDs.length; k++) {
            Block block = this.cachedBlockIDs[k];
            if (block != null) {
                int l = k >> 4;
                ExtendedBlockStorage extendedblockstorage = chunk.getBlockStorageArray()[l];
                if (extendedblockstorage == null) {
                    extendedblockstorage = new ExtendedBlockStorage(k, !this.worldObj.provider.hasNoSky);
                    chunk.getBlockStorageArray()[l] = extendedblockstorage;
                }

                for (int i1 = 0; i1 < 16; i1++) {
                    for (int j1 = 0; j1 < 16; j1++) {
                        extendedblockstorage.func_150818_a(i1, k & 15, j1, block);
                        extendedblockstorage.setExtBlockMetadata(i1, k & 15, j1, this.cachedBlockMetadata[k]);
                    }
                }
            }
        }

        this.addScragglyTrees(this.worldObj, chunkX * 16, chunkZ * 16, chunk);
        chunk.generateSkylightMap();
        return chunk;
    }

    public boolean chunkExists(int chunkX, int chunkZ) {
        return true;
    }

    public void populate(IChunkProvider provider, int chunkX, int chunkZ) {
        int k = chunkX * 16;
        int l = chunkZ * 16;
        this.random.setSeed(this.worldObj.getSeed());
        long i1 = this.random.nextLong() / 2L * 2L + 1L;
        long j1 = this.random.nextLong() / 2L * 2L + 1L;
        this.random.setSeed(chunkX * i1 + chunkZ * j1 ^ this.worldObj.getSeed());
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
        return "DangerDimension";
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
        int howmany = 1 + this.random.nextInt(10);
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

                for (int posY = 20; posY > 2; posY--) {
                    if (OreSpawnMain.getBlockIDInChunk(chunk, posX, posY - 1, posZ) == Blocks.grass) {
                        this.ScragglyTreeWithBranches(world, posX, posY, posZ, chunk);
                        break;
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
