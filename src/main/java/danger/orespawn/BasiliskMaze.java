package danger.orespawn;

import java.awt.Point;
import java.util.Vector;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class BasiliskMaze {

    public static final int WTOP = 1;
    public static final int WRGT = 2;
    public static final int WBOT = 4;
    public static final int WLFT = 8;
    private final WeightedRandomChestContent[] chestContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(Items.ender_pearl, 0, 3, 6, 15),
        new WeightedRandomChestContent(Items.diamond, 0, 15, 25, 20),
        new WeightedRandomChestContent(Items.blaze_rod, 0, 4, 12, 15),
        new WeightedRandomChestContent(OreSpawnMain.CageEmpty, 0, 3, 10, 20),
        new WeightedRandomChestContent(OreSpawnMain.CagedGirlfriend, 0, 2, 4, 15),
        new WeightedRandomChestContent(Items.iron_ingot, 0, 2, 20, 20),
        new WeightedRandomChestContent(Items.gold_ingot, 0, 4, 16, 20),
        new WeightedRandomChestContent(OreSpawnMain.MyIngotUranium, 0, 2, 8, 20),
        new WeightedRandomChestContent(OreSpawnMain.MyIngotTitanium, 0, 2, 6, 20),
        new WeightedRandomChestContent(OreSpawnMain.MySunFish, 0, 2, 8, 20),
        new WeightedRandomChestContent(OreSpawnMain.MyFireFish, 0, 3, 8, 20),
        new WeightedRandomChestContent(OreSpawnMain.MyLavaEel, 0, 5, 24, 20),
        new WeightedRandomChestContent(OreSpawnMain.MyCornDog, 0, 6, 12, 20),
        new WeightedRandomChestContent(Items.diamond_pickaxe, 0, 1, 1, 15),
        new WeightedRandomChestContent(Items.diamond_sword, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyUltimatePickaxe, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyUltimateSword, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyUltimateFishingRod, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyUltimateBow, 0, 1, 1, 15),
        new WeightedRandomChestContent(Items.diamond_chestplate, 0, 1, 1, 15),
        new WeightedRandomChestContent(Items.diamond_helmet, 0, 1, 1, 15),
        new WeightedRandomChestContent(Items.diamond_leggings, 0, 1, 1, 15),
        new WeightedRandomChestContent(Items.diamond_boots, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.UltimateBody, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.UltimateLegs, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.UltimateHelmet, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.UltimateBoots, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyRuby, 0, 1, 1, 5),
        new WeightedRandomChestContent(OreSpawnMain.MyThunderStaff, 0, 1, 1, 5),
        new WeightedRandomChestContent(OreSpawnMain.MagicApple, 0, 1, 1, 15),
        new WeightedRandomChestContent(Items.golden_apple, 0, 2, 4, 15) };

    public void buildBasiliskMaze(World world, int x, int y, int z) {
        int depth = 20 + world.rand.nextInt(10);
        this.clearArea(world, x + 3, y - depth - 4, z - 20);
        this.makeMaze(world, x + 3, y - depth - 3, z - 20, 10, 10, 3, 0);
        this.openMaze(world, x + 3, y - depth - 3, z - 20, 10, 10, 3);
        this.buildCastle(world, x + 3, y - depth - 4, z - 20);
        this.makeEntrance(world, x, y, z, depth);
    }

    private void makeMaze(World world, int xx, int yy, int zz, int xw, int zw, int csz, int b) {
        int gridw = xw;
        int gridh = zw;
        int cellsize = csz;
        if (csz < 3) {
            cellsize = 3;
        }

        int[][] cells = new int[xw][zw];
        int full = 15;

        for (int x = 0; x < gridw; x++) {
            for (int y = 0; y < gridh; y++) {
                cells[x][y] = full;
            }
        }

        int left = 128;
        int right = 32;

        for (int y = 0; y < gridh; y++) {
            cells[0][y] = cells[0][y] | left;
            cells[gridw - 1][y] = cells[gridw - 1][y] | right;
        }

        int top = 16;
        int bottom = 64;

        for (int var26 = 0; var26 < gridw; var26++) {
            cells[var26][0] = cells[var26][0] | top;
            cells[var26][gridh - 1] = cells[var26][gridh - 1] | bottom;
        }

        Vector outlist = new Vector(gridw * gridh);
        Vector inlist = new Vector(10, 10);
        Vector frontlist = new Vector(10, 10);

        for (int var27 = 0; var27 < gridw; var27++) {
            for (int var30 = 0; var30 < gridh; var30++) {
                outlist.addElement(new Point(var27, var30));
            }
        }

        Point current_cell = (Point) this.rndElement(outlist);
        inlist.addElement(current_cell);
        this.moveNbrs(current_cell, cells, outlist, frontlist);

        while (!frontlist.isEmpty()) {
            current_cell = (Point) this.rndElement(frontlist);
            inlist.addElement(current_cell);
            this.moveNbrs(current_cell, cells, outlist, frontlist);
            int dir = this.findInNbr(current_cell, cells, inlist);
            this.removeWall(current_cell, dir, cells);
        }

        current_cell = null;

        for (int var28 = 0; var28 < gridw; var28++) {
            for (int var31 = 0; var31 < gridh; var31++) {
                int val = cells[var28][var31];
                if ((val & 1) != 0) {
                    this.drawSide(
                        world,
                        var28 * cellsize,
                        var31 * cellsize,
                        (var28 + 1) * cellsize,
                        var31 * cellsize,
                        xx,
                        yy,
                        zz,
                        cellsize,
                        gridh,
                        gridw,
                        b);
                }

                if ((val & 2) != 0) {
                    this.drawSide(
                        world,
                        (var28 + 1) * cellsize - 1,
                        var31 * cellsize,
                        (var28 + 1) * cellsize - 1,
                        (var31 + 1) * cellsize,
                        xx,
                        yy,
                        zz,
                        cellsize,
                        gridh,
                        gridw,
                        b);
                }

                if ((val & 4) != 0) {
                    this.drawSide(
                        world,
                        var28 * cellsize,
                        (var31 + 1) * cellsize - 1,
                        (var28 + 1) * cellsize,
                        (var31 + 1) * cellsize - 1,
                        xx,
                        yy,
                        zz,
                        cellsize,
                        gridh,
                        gridw,
                        b);
                }

                if ((val & 8) != 0) {
                    this.drawSide(
                        world,
                        var28 * cellsize,
                        var31 * cellsize,
                        var28 * cellsize,
                        (var31 + 1) * cellsize,
                        xx,
                        yy,
                        zz,
                        cellsize,
                        gridh,
                        gridw,
                        b);
                }
            }
        }
    }

    private void drawSide(World world, int fromx, int fromz, int tox, int toz, int x, int y, int z, int cellsize,
        int gridh, int gridw, int bb) {
        Block blk = Blocks.obsidian;
        if (bb != 0) {
            blk = Blocks.bedrock;
        }

        if (fromx > tox) {
            int i = fromx;
            fromx = tox;
            tox = i;
        }

        if (fromz > toz) {
            int i = fromz;
            fromz = toz;
            toz = i;
        }

        if (fromx == tox) {
            int i = fromx;

            for (int j = fromz; j <= toz; j++) {
                if (j < cellsize * gridh) {
                    OreSpawnMain.setBlockFast(world, i + x, y, j + z, blk, 0, 2);
                    OreSpawnMain.setBlockFast(world, i + x, y + 1, j + z, blk, 0, 2);
                    OreSpawnMain.setBlockFast(world, i + x, y + 2, j + z, blk, 0, 2);
                }
            }
        } else {
            int jx = fromz;

            for (int i = fromx; i <= tox; i++) {
                if (i < cellsize * gridw) {
                    OreSpawnMain.setBlockFast(world, i + x, y, jx + z, blk, 0, 2);
                    OreSpawnMain.setBlockFast(world, i + x, y + 1, jx + z, blk, 0, 2);
                    OreSpawnMain.setBlockFast(world, i + x, y + 2, jx + z, blk, 0, 2);
                }
            }
        }
    }

    private int findInNbr(Point p, int[][] cells, Vector inlist) {
        int d = this.rnd(4) - 1;

        for (int k = 0; k < 4; k++) {
            switch (d) {
                case 0:
                    if ((cells[p.x][p.y] & 16) == 0 && inlist.indexOf(new Point(p.x, p.y - 1)) >= 0) {
                        return 1;
                    }
                    break;
                case 1:
                    if ((cells[p.x][p.y] & 32) == 0 && inlist.indexOf(new Point(p.x + 1, p.y)) >= 0) {
                        return 2;
                    }
                    break;
                case 2:
                    if ((cells[p.x][p.y] & 64) == 0 && inlist.indexOf(new Point(p.x, p.y + 1)) >= 0) {
                        return 4;
                    }
                    break;
                case 3:
                    if ((cells[p.x][p.y] & 128) == 0 && inlist.indexOf(new Point(p.x - 1, p.y)) >= 0) {
                        return 8;
                    }
            }

            d = (d + 1) % 4;
        }

        return 0;
    }

    private void moveNbrs(Point p, int[][] cells, Vector outlist, Vector frontlist) {
        if ((cells[p.x][p.y] & 16) == 0) {
            Point s = new Point(p.x, p.y - 1);
            this.movePoint(s, outlist, frontlist);
        }

        if ((cells[p.x][p.y] & 32) == 0) {
            Point s = new Point(p.x + 1, p.y);
            this.movePoint(s, outlist, frontlist);
        }

        if ((cells[p.x][p.y] & 64) == 0) {
            Point s = new Point(p.x, p.y + 1);
            this.movePoint(s, outlist, frontlist);
        }

        if ((cells[p.x][p.y] & 128) == 0) {
            Point s = new Point(p.x - 1, p.y);
            this.movePoint(s, outlist, frontlist);
        }
    }

    private void movePoint(Point p, Vector v, Vector w) {
        int i = v.indexOf(p);
        if (i >= 0) {
            v.removeElementAt(i);
            w.addElement(p);
        }
    }

    private void removeWall(Point p, int d, int[][] cells) {
        cells[p.x][p.y] = cells[p.x][p.y] ^ d;
        switch (d) {
            case 1:
                cells[p.x][p.y - 1] = cells[p.x][p.y - 1] ^ 4;
                break;
            case 2:
                cells[p.x + 1][p.y] = cells[p.x + 1][p.y] ^ 8;
            case 3:
            case 5:
            case 6:
            case 7:
            default:
                break;
            case 4:
                cells[p.x][p.y + 1] = cells[p.x][p.y + 1] ^ 1;
                break;
            case 8:
                cells[p.x - 1][p.y] = cells[p.x - 1][p.y] ^ 2;
        }
    }

    private int rnd(int n) {
        return (int) (Math.random() * n + 1.0);
    }

    private Object rndElement(Vector v) {
        int i = this.rnd(v.size()) - 1;
        Object s = v.elementAt(i);
        v.removeElementAt(i);
        return s;
    }

    private Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {
        Entity var8 = null;
        var8 = EntityList.createEntityByName(par1, par0World);
        if (var8 != null) {
            var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0F, 0.0F);
            par0World.spawnEntityInWorld(var8);
            ((EntityLiving) var8).playLivingSound();
        }

        return var8;
    }

    private void clearArea(World world, int x, int y, int z) {
        for (int i = 0; i < 60; i++) {
            int hi = 5;
            if (i >= 30) {
                hi = 7;
            }

            for (int j = 0; j < hi; j++) {
                for (int k = 0; k < 30; k++) {
                    OreSpawnMain.setBlockFast(world, x + i, y + j, z + k, Blocks.air, 0, 2);
                }
            }
        }

        for (int var9 = 0; var9 < 5; var9++) {
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < 30; k++) {
                    OreSpawnMain.setBlockFast(world, x - var9, y + j, z + k, Blocks.air, 0, 2);
                }
            }
        }
    }

    private void openMaze(World world, int xx, int yy, int zz, int xw, int zw, int csz) {
        for (int i = 0; i < zw * csz; i++) {
            Block bid = world.getBlock(xx + 1, yy, zz + i);
            if (bid == Blocks.air) {
                OreSpawnMain.setBlockFast(world, xx, yy, zz + i, Blocks.air, 0, 2);
                OreSpawnMain.setBlockFast(world, xx, yy + 1, zz + i, Blocks.air, 0, 2);
                OreSpawnMain.setBlockFast(world, xx, yy + 2, zz + i, Blocks.air, 0, 2);
                break;
            }
        }

        for (int var12 = zw * csz - 1; var12 >= 0; var12--) {
            Block bid = world.getBlock(xx + xw * csz - 2, yy, zz + var12);
            if (bid == Blocks.air) {
                OreSpawnMain.setBlockFast(world, xx + xw * csz - 1, yy, zz + var12, Blocks.air, 0, 2);
                OreSpawnMain.setBlockFast(world, xx + xw * csz - 1, yy + 1, zz + var12, Blocks.air, 0, 2);
                OreSpawnMain.setBlockFast(world, xx + xw * csz - 1, yy + 2, zz + var12, Blocks.air, 0, 2);
                break;
            }
        }
    }

    private void buildCastle(World world, int x, int y, int z) {
        for (int i = 0; i < 60; i++) {
            for (int k = 0; k < 30; k++) {
                OreSpawnMain.setBlockFast(world, x + i, y, z + k, Blocks.obsidian, 0, 2);
            }
        }

        for (int var11 = 0; var11 < 80; var11++) {
            OreSpawnMain.setBlockFast(
                world,
                x + world.rand.nextInt(28) + 1,
                y,
                z + world.rand.nextInt(28) + 1,
                Blocks.lava,
                0,
                2);
        }

        for (int var12 = 0; var12 < 20; var12++) {
            OreSpawnMain.setBlockFast(
                world,
                x + 30 + world.rand.nextInt(28) + 1,
                y,
                z + world.rand.nextInt(28) + 1,
                OreSpawnMain.MyRTPBlock,
                0,
                2);
        }

        for (int var13 = 0; var13 < 30; var13++) {
            for (int k = 0; k < 30; k++) {
                OreSpawnMain.setBlockFast(world, x + var13, y + 4, z + k, Blocks.bedrock, 0, 2);
            }
        }

        for (int var14 = 0; var14 < 30; var14++) {
            for (int k = 0; k < 30; k++) {
                OreSpawnMain.setBlockFast(world, x + var14 + 30, y + 6, z + k, Blocks.bedrock, 0, 2);
            }
        }

        for (int var15 = 0; var15 < 30; var15++) {
            for (int k = 0; k < 5; k++) {
                OreSpawnMain.setBlockFast(world, x + 59, y + k + 1, z + var15, Blocks.obsidian, 0, 2);
                OreSpawnMain.setBlockFast(world, x + 60, y + k + 1, z + var15, Blocks.bedrock, 0, 2);
                OreSpawnMain.setBlockFast(world, x + 61, y + k + 1, z + var15, Blocks.bedrock, 0, 2);
            }
        }

        for (int var16 = 0; var16 < 30; var16++) {
            for (int k = 0; k < 5; k++) {
                OreSpawnMain.setBlockFast(world, x + 30 + var16, y + k + 1, z, Blocks.obsidian, 0, 2);
                OreSpawnMain.setBlockFast(world, x + 30 + var16, y + k + 1, z - 1, Blocks.bedrock, 0, 2);
                OreSpawnMain.setBlockFast(world, x + 30 + var16, y + k + 1, z - 2, Blocks.bedrock, 0, 2);
            }
        }

        for (int var17 = 0; var17 < 30; var17++) {
            for (int k = 0; k < 5; k++) {
                OreSpawnMain.setBlockFast(world, x + 30 + var17, y + k + 1, z + 29, Blocks.obsidian, 0, 2);
                OreSpawnMain.setBlockFast(world, x + 30 + var17, y + k + 1, z + 30, Blocks.bedrock, 0, 2);
                OreSpawnMain.setBlockFast(world, x + 30 + var17, y + k + 1, z + 31, Blocks.bedrock, 0, 2);
            }
        }

        for (int var18 = 0; var18 < 30; var18++) {
            OreSpawnMain.setBlockFast(world, x + 30, y + 5, z + var18, Blocks.obsidian, 0, 2);
        }

        for (int var19 = 0; var19 < 30; var19++) {
            for (int k = 0; k < 4; k++) {
                OreSpawnMain.setBlockFast(world, x - 4 + k, y, z + var19, Blocks.sandstone, 0, 2);
            }
        }

        for (int var20 = 0; var20 < 30; var20++) {
            for (int k = 0; k < 4; k++) {
                OreSpawnMain.setBlockFast(world, x - 4 + k, y + 5, z + var20, Blocks.obsidian, 0, 2);
            }
        }

        for (int var21 = 0; var21 < 30; var21++) {
            for (int k = 1; k < 5; k++) {
                OreSpawnMain.setBlockFast(world, x - 5, y + k, z + var21, Blocks.iron_ore, 0, 2);
            }
        }

        for (int var22 = 0; var22 < 5; var22++) {
            for (int k = 1; k < 5; k++) {
                OreSpawnMain.setBlockFast(world, x - 4 + var22, y + k, z - 1, Blocks.iron_ore, 0, 2);
            }
        }

        for (int var23 = 0; var23 < 5; var23++) {
            for (int k = 1; k < 5; k++) {
                OreSpawnMain.setBlockFast(world, x - 4 + var23, y + k, z + 30, Blocks.iron_ore, 0, 2);
            }
        }

        for (int k = 0; k < 4; k++) {
            OreSpawnMain.setBlockFast(world, x - 4, y + 1 + k, z, Blocks.sandstone, 0, 2);
        }

        for (int var36 = 0; var36 < 4; var36++) {
            OreSpawnMain.setBlockFast(world, x - 4, y + 1 + var36, z + 15, Blocks.sandstone, 0, 2);
        }

        for (int var37 = 0; var37 < 4; var37++) {
            OreSpawnMain.setBlockFast(world, x - 4, y + 1 + var37, z + 29, Blocks.sandstone, 0, 2);
        }

        OreSpawnMain.setBlockFast(world, x - 3, y + 3, z, OreSpawnMain.ExtremeTorch, 0, 2);
        OreSpawnMain.setBlockFast(world, x - 3, y + 3, z + 15, OreSpawnMain.ExtremeTorch, 0, 2);
        OreSpawnMain.setBlockFast(world, x - 3, y + 3, z + 29, OreSpawnMain.ExtremeTorch, 0, 2);
        OreSpawnMain.setBlockFast(world, x + 30, y + 4, z + 2, Blocks.redstone_torch, 0, 2);
        OreSpawnMain.setBlockFast(world, x + 30, y + 4, z + 15, Blocks.redstone_torch, 0, 2);
        OreSpawnMain.setBlockFast(world, x + 30, y + 4, z + 27, Blocks.redstone_torch, 0, 2);
        TileEntityChest chest = null;
        int var24 = 2 + world.rand.nextInt(3);

        for (int var38 = 0; var38 < var24; var38++) {
            OreSpawnMain.setBlockFast(world, x + 58, y + 4, z + 2 + var38 * 2, Blocks.torch, 0, 2);
            OreSpawnMain.setBlockFast(world, x + 58, y + 1, z + 2 + var38 * 2, Blocks.chest, 0, 2);
            chest = (TileEntityChest) world.getTileEntity(x + 58, y + 1, z + 2 + var38 * 2);
            if (chest != null) {
                WeightedRandomChestContent
                    .generateChestContents(world.rand, this.chestContentsList, chest, 5 + world.rand.nextInt(6));
            }
        }

        Entity ent = null;
        ent = this.spawnCreature(world, "Basilisk", x + 45.0, y + 1.01, z + 15.0);
        if (ent != null) {
            Basilisk b = (Basilisk) ent;
            b.func_110163_bv();
        }

        ent = this.spawnCreature(world, "Basilisk", x + 46.0, y + 1.01, z + 15.0);
        if (ent != null) {
            Basilisk b = (Basilisk) ent;
            b.func_110163_bv();
        }

        ent = this.spawnCreature(world, "Basilisk", x + 47.0, y + 1.01, z + 15.0);
        if (ent != null) {
            Basilisk b = (Basilisk) ent;
            b.func_110163_bv();
        }
    }

    public void makeEntrance(World world, int x, int y, int z, int depth) {
        int width = 8;

        for (int j = width; j >= 0; j--) {
            for (int i = 0; i < j * 2 + 4; i++) {
                OreSpawnMain.setBlockFast(world, x + i - j, y + width - j, z - j, Blocks.sandstone, 0, 2);
                OreSpawnMain.setBlockFast(world, x + i - j, y + width - j, z + j + 3, Blocks.sandstone, 0, 2);
                OreSpawnMain.setBlockFast(world, x - j, y + width - j, z + i - j, Blocks.sandstone, 0, 2);
                OreSpawnMain.setBlockFast(world, x + j + 3, y + width - j, z + i - j, Blocks.sandstone, 0, 2);
            }
        }

        int k = 0;

        for (int var13 = width; var13 > -depth; var13--) {
            for (int i = 0; i < 4; i++) {
                OreSpawnMain.setBlockFast(world, x + i, y + var13, z, Blocks.bedrock, 0, 2);
                OreSpawnMain.setBlockFast(world, x + i, y + var13, z + 3, Blocks.bedrock, 0, 2);
                OreSpawnMain.setBlockFast(world, x, y + var13, z + i, Blocks.bedrock, 0, 2);
                OreSpawnMain.setBlockFast(world, x + 3, y + var13, z + i, Blocks.bedrock, 0, 2);
            }

            for (int l = 0; l < 2; l++) {
                for (int m = 0; m < 2; m++) {
                    OreSpawnMain.setBlockFast(world, x + 1 + l, y + var13, z + 1 + m, Blocks.air, 0, 2);
                }
            }

            switch (k) {
                case 0:
                    OreSpawnMain.setBlockFast(world, x + 1, y + var13, z + 1, Blocks.obsidian, 0, 2);
                    break;
                case 1:
                    OreSpawnMain.setBlockFast(world, x + 2, y + var13, z + 1, Blocks.obsidian, 0, 2);
                    break;
                case 2:
                    OreSpawnMain.setBlockFast(world, x + 2, y + var13, z + 2, Blocks.obsidian, 0, 2);
                    break;
                default:
                    OreSpawnMain.setBlockFast(world, x + 1, y + var13, z + 2, Blocks.obsidian, 0, 2);
            }

            if (++k > 3) {
                k = 0;
            }
        }
    }
}
