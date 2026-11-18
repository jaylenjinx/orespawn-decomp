package danger.orespawn.world.dimension;

import java.util.ArrayList;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;

import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.Alosaurus;
import danger.orespawn.entity.BandP;
import danger.orespawn.entity.Baryonyx;
import danger.orespawn.entity.Basilisk;
import danger.orespawn.entity.Beaver;
import danger.orespawn.entity.Bee;
import danger.orespawn.entity.Boyfriend;
import danger.orespawn.entity.Brutalfly;
import danger.orespawn.entity.Cassowary;
import danger.orespawn.entity.CaterKiller;
import danger.orespawn.entity.CaveFisher;
import danger.orespawn.entity.Chipmunk;
import danger.orespawn.entity.CliffRacer;
import danger.orespawn.entity.CloudShark;
import danger.orespawn.entity.Cockateil;
import danger.orespawn.entity.Coin;
import danger.orespawn.entity.Crab;
import danger.orespawn.entity.CreepingHorror;
import danger.orespawn.entity.Cricket;
import danger.orespawn.entity.Cryolophosaurus;
import danger.orespawn.entity.CrystalCow;
import danger.orespawn.entity.Dragon;
import danger.orespawn.entity.Dragonfly;
import danger.orespawn.entity.DungeonBeast;
import danger.orespawn.entity.EmperorScorpion;
import danger.orespawn.entity.EnchantedCow;
import danger.orespawn.entity.EnderKnight;
import danger.orespawn.entity.EnderReaper;
import danger.orespawn.entity.EntityButterfly;
import danger.orespawn.entity.EntityLunaMoth;
import danger.orespawn.entity.Fairy;
import danger.orespawn.entity.Firefly;
import danger.orespawn.entity.Flounder;
import danger.orespawn.entity.Frog;
import danger.orespawn.entity.GammaMetroid;
import danger.orespawn.entity.Gazelle;
import danger.orespawn.entity.GiantRobot;
import danger.orespawn.entity.Girlfriend;
import danger.orespawn.entity.Godzilla;
import danger.orespawn.entity.GoldCow;
import danger.orespawn.entity.GoldFish;
import danger.orespawn.entity.Hammerhead;
import danger.orespawn.entity.HerculesBeetle;
import danger.orespawn.entity.Irukandji;
import danger.orespawn.entity.LeafMonster;
import danger.orespawn.entity.Leon;
import danger.orespawn.entity.LurkingTerror;
import danger.orespawn.entity.Mantis;
import danger.orespawn.entity.Molenoid;
import danger.orespawn.entity.Mothra;
import danger.orespawn.entity.Nastysaurus;
import danger.orespawn.entity.Ostrich;
import danger.orespawn.entity.Peacock;
import danger.orespawn.entity.PitchBlack;
import danger.orespawn.entity.Pointysaurus;
import danger.orespawn.entity.Rat;
import danger.orespawn.entity.RedCow;
import danger.orespawn.entity.Robot1;
import danger.orespawn.entity.Robot2;
import danger.orespawn.entity.Robot3;
import danger.orespawn.entity.Robot4;
import danger.orespawn.entity.Robot5;
import danger.orespawn.entity.Rotator;
import danger.orespawn.entity.Scorpion;
import danger.orespawn.entity.Skate;
import danger.orespawn.entity.SpiderDriver;
import danger.orespawn.entity.SpitBug;
import danger.orespawn.entity.StinkBug;
import danger.orespawn.entity.Stinky;
import danger.orespawn.entity.TRex;
import danger.orespawn.entity.TerribleTerror;
import danger.orespawn.entity.TrooperBug;
import danger.orespawn.entity.Tshirt;
import danger.orespawn.entity.Urchin;
import danger.orespawn.entity.Vortex;
import danger.orespawn.entity.Whale;

public class BiomeGenUtopianPlains extends BiomeGenBase {

    protected BiomeGenUtopianPlains(int n) {
        super(n);
        if (OreSpawnMain.GazelleEnable != 0) {
            this.spawnableCreatureList.add(new SpawnListEntry(Gazelle.class, 10, 2, 4));
        }

        if (OreSpawnMain.FireflyEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(Firefly.class, 15, 3, 6));
        }

        if (OreSpawnMain.GirlfriendEnable != 0) {
            this.spawnableCreatureList.add(new SpawnListEntry(Girlfriend.class, 5, 2, 3));
        }

        if (OreSpawnMain.BoyfriendEnable != 0) {
            this.spawnableCreatureList.add(new SpawnListEntry(Boyfriend.class, 5, 2, 3));
        }

        if (OreSpawnMain.CowEnable != 0) {
            this.spawnableCreatureList.add(new SpawnListEntry(RedCow.class, 10, 4, 8));
            this.spawnableCreatureList.add(new SpawnListEntry(GoldCow.class, 8, 2, 6));
            this.spawnableCreatureList.add(new SpawnListEntry(EnchantedCow.class, 5, 2, 4));
        }

        if (OreSpawnMain.ButterflyEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(EntityButterfly.class, 20, 3, 6));
        }

        if (OreSpawnMain.MothEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(EntityLunaMoth.class, 10, 1, 5));
        }

        if (OreSpawnMain.ChipmunkEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(Chipmunk.class, 3, 1, 2));
        }

        if (OreSpawnMain.CockateilEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(Cockateil.class, 10, 2, 4));
        }

        if (OreSpawnMain.GoldFishEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(GoldFish.class, 1, 1, 1));
        }

        if (OreSpawnMain.WhaleEnable != 0) {
            this.spawnableWaterCreatureList.add(new SpawnListEntry(Whale.class, 1, 1, 1));
        }

        if (OreSpawnMain.FlounderEnable != 0) {
            this.spawnableWaterCreatureList.add(new SpawnListEntry(Flounder.class, 2, 2, 4));
        }

        if (OreSpawnMain.CoinEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(Coin.class, 2, 1, 1));
        }

        if (OreSpawnMain.CricketEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(Cricket.class, 5, 4, 6));
        }

        if (OreSpawnMain.FrogEnable != 0) {
            this.spawnableWaterCreatureList.add(new SpawnListEntry(Frog.class, 5, 4, 6));
        }

        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.flowersPerChunk = 4;
        this.theBiomeDecorator.grassPerChunk = 6;
    }

    public void setIslandCreatures() {
        this.spawnableCreatureList = null;
        this.spawnableMonsterList = null;
        this.spawnableWaterCreatureList = null;
        this.spawnableCaveCreatureList = null;
        this.spawnableCreatureList = new ArrayList();
        this.spawnableMonsterList = new ArrayList();
        this.spawnableWaterCreatureList = new ArrayList();
        this.spawnableCaveCreatureList = new ArrayList();
        if (OreSpawnMain.ButterflyEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(EntityButterfly.class, 5, 2, 6));
        }

        if (OreSpawnMain.CockateilEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(Cockateil.class, 4, 1, 2));
        }

        if (OreSpawnMain.MothEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(EntityLunaMoth.class, 5, 2, 4));
        }

        if (OreSpawnMain.FireflyEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(Firefly.class, 10, 4, 8));
        }

        if (OreSpawnMain.DragonEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(Dragon.class, 1, 1, 2));
        }

        if (OreSpawnMain.StinkyEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(Stinky.class, 2, 1, 2));
        }

        if (OreSpawnMain.CliffRacerEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(CliffRacer.class, 20, 3, 6));
        }

        if (OreSpawnMain.CloudSharkEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(CloudShark.class, 1, 1, 1));
        }

        if (OreSpawnMain.GoldFishEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(GoldFish.class, 5, 2, 4));
        }

        if (OreSpawnMain.CreepingHorrorEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(CreepingHorror.class, 60, 4, 8));
        }

        if (OreSpawnMain.TerribleTerrorEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(TerribleTerror.class, 25, 3, 6));
        }

        if (OreSpawnMain.LurkingTerrorEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(LurkingTerror.class, 1, 1, 1));
        }

        if (OreSpawnMain.PitchBlackEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(PitchBlack.class, 15, 3, 6));
        }

        if (OreSpawnMain.LeafMonsterEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(LeafMonster.class, 35, 2, 4));
        }

        if (OreSpawnMain.EnderReaperEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(EnderReaper.class, 25, 2, 4));
        }

        if (OreSpawnMain.HerculesBeetleEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(HerculesBeetle.class, 5, 1, 2));
        }
    }

    public void setCrystalCreatures() {
        this.spawnableCreatureList = null;
        this.spawnableMonsterList = null;
        this.spawnableWaterCreatureList = null;
        this.spawnableCaveCreatureList = null;
        this.spawnableCreatureList = new ArrayList();
        this.spawnableMonsterList = new ArrayList();
        this.spawnableWaterCreatureList = new ArrayList();
        this.spawnableCaveCreatureList = new ArrayList();
        if (OreSpawnMain.CowEnable != 0) {
            this.spawnableCreatureList.add(new SpawnListEntry(CrystalCow.class, 1, 1, 4));
        }

        if (OreSpawnMain.FairyEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(Fairy.class, 10, 4, 8));
        }

        if (OreSpawnMain.PeacockEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(Peacock.class, 5, 4, 8));
        }

        if (OreSpawnMain.MantisEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(Mantis.class, 1, 1, 1));
        }

        if (OreSpawnMain.RotatorEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(Rotator.class, 4, 1, 2));
        }

        if (OreSpawnMain.VortexEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(Vortex.class, 3, 1, 2));
        }

        if (OreSpawnMain.UrchinEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(Urchin.class, 15, 2, 4));
        }

        if (OreSpawnMain.DungeonBeastEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(DungeonBeast.class, 30, 4, 6));
        }

        if (OreSpawnMain.RatEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(Rat.class, 40, 4, 6));
        }

        if (OreSpawnMain.ButterflyEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(EntityButterfly.class, 10, 2, 4));
        }

        if (OreSpawnMain.CockateilEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(Cockateil.class, 4, 1, 2));
        }

        if (OreSpawnMain.MothEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(EntityLunaMoth.class, 4, 1, 2));
        }

        if (OreSpawnMain.WhaleEnable != 0) {
            this.spawnableWaterCreatureList.add(new SpawnListEntry(Whale.class, 1, 1, 2));
        }

        if (OreSpawnMain.CrabEnable != 0) {
            this.spawnableWaterCreatureList.add(new SpawnListEntry(Crab.class, 1, 1, 2));
        }

        if (OreSpawnMain.FlounderEnable != 0) {
            this.spawnableWaterCreatureList.add(new SpawnListEntry(Flounder.class, 5, 6, 8));
        }

        if (OreSpawnMain.IrukandjiEnable != 0) {
            this.spawnableWaterCreatureList.add(new SpawnListEntry(Irukandji.class, 4, 2, 3));
        }

        if (OreSpawnMain.SkateEnable != 0) {
            this.spawnableWaterCreatureList.add(new SpawnListEntry(Skate.class, 2, 3, 6));
        }

        if (OreSpawnMain.FrogEnable != 0) {
            this.spawnableWaterCreatureList.add(new SpawnListEntry(Frog.class, 1, 3, 5));
        }

        this.theBiomeDecorator.flowersPerChunk = -999;
        this.theBiomeDecorator.grassPerChunk = -999;
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.bigMushroomsPerChunk = -999;
        this.theBiomeDecorator.mushroomsPerChunk = -999;
        this.theBiomeDecorator.reedsPerChunk = -999;
    }

    public void setVillageCreatures() {
        if (OreSpawnMain.Robot1Enable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(Robot1.class, 25, 4, 8));
        }

        if (OreSpawnMain.Robot2Enable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(Robot2.class, 16, 2, 8));
        }

        if (OreSpawnMain.Robot3Enable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(Robot3.class, 12, 2, 4));
        }

        if (OreSpawnMain.Robot4Enable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(Robot4.class, 8, 1, 2));
        }

        if (OreSpawnMain.Robot5Enable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(Robot5.class, 20, 4, 8));
        }

        if (OreSpawnMain.JefferyEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(GiantRobot.class, 8, 1, 2));
        }

        if (OreSpawnMain.SpiderDriverEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(SpiderDriver.class, 20, 3, 5));
        }

        if (OreSpawnMain.GodzillaEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(Godzilla.class, 2, 1, 1));
        }

        if (OreSpawnMain.FireflyEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(Firefly.class, 10, 3, 6));
        }

        if (OreSpawnMain.GirlfriendEnable != 0) {
            this.spawnableCreatureList.add(new SpawnListEntry(Girlfriend.class, 1, 2, 3));
        }

        if (OreSpawnMain.BoyfriendEnable != 0) {
            this.spawnableCreatureList.add(new SpawnListEntry(Boyfriend.class, 1, 2, 3));
        }

        if (OreSpawnMain.CowEnable != 0) {
            this.spawnableCreatureList.add(new SpawnListEntry(RedCow.class, 8, 4, 8));
            this.spawnableCreatureList.add(new SpawnListEntry(GoldCow.class, 6, 2, 6));
            this.spawnableCreatureList.add(new SpawnListEntry(EnchantedCow.class, 4, 2, 4));
        }

        if (OreSpawnMain.ButterflyEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(EntityButterfly.class, 25, 3, 6));
        }

        if (OreSpawnMain.MothEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(EntityLunaMoth.class, 20, 1, 5));
        }

        if (OreSpawnMain.ChipmunkEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(Chipmunk.class, 5, 1, 2));
        }

        if (OreSpawnMain.CockateilEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(Cockateil.class, 15, 2, 4));
        }

        if (OreSpawnMain.TshirtEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(Tshirt.class, 2, 1, 1));
        }

        if (OreSpawnMain.CoinEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(Coin.class, 2, 1, 1));
        }

        if (OreSpawnMain.CriminalEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(BandP.class, 15, 1, 2));
        }
    }

    public void setChaosCreatures() {
        this.spawnableCreatureList = null;
        this.spawnableMonsterList = null;
        this.spawnableWaterCreatureList = null;
        this.spawnableCaveCreatureList = null;
        this.spawnableCreatureList = new ArrayList();
        this.spawnableMonsterList = new ArrayList();
        this.spawnableWaterCreatureList = new ArrayList();
        this.spawnableCaveCreatureList = new ArrayList();
        this.theBiomeDecorator.flowersPerChunk = 2;
        this.theBiomeDecorator.grassPerChunk = 4;
        this.theBiomeDecorator.treesPerChunk = 1;
        this.theBiomeDecorator.bigMushroomsPerChunk = -999;
        this.theBiomeDecorator.mushroomsPerChunk = -999;
        this.theBiomeDecorator.reedsPerChunk = -999;
        if (OreSpawnMain.ButterflyEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(EntityButterfly.class, 20, 3, 6));
        }

        if (OreSpawnMain.MothEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(EntityLunaMoth.class, 10, 1, 5));
        }

        if (OreSpawnMain.CockateilEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(Cockateil.class, 10, 2, 4));
        }

        if (OreSpawnMain.FireflyEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(Firefly.class, 15, 3, 6));
        }

        if (OreSpawnMain.CliffRacerEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(CliffRacer.class, 30, 3, 6));
        }

        if (OreSpawnMain.CloudSharkEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(CloudShark.class, 2, 1, 1));
        }

        if (OreSpawnMain.GoldFishEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(GoldFish.class, 10, 2, 4));
        }

        if (OreSpawnMain.FairyEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(Fairy.class, 5, 2, 4));
        }

        if (OreSpawnMain.BaryonyxEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(Baryonyx.class, 2, 2, 4));
        }

        if (OreSpawnMain.BeeEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(Bee.class, 2, 2, 4));
        }

        if (OreSpawnMain.CassowaryEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(Cassowary.class, 2, 2, 4));
        }

        if (OreSpawnMain.DragonflyEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(Dragonfly.class, 2, 2, 4));
        }

        if (OreSpawnMain.PeacockEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(Peacock.class, 2, 2, 4));
        }

        if (OreSpawnMain.StinkBugEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(StinkBug.class, 3, 2, 4));
        }

        if (OreSpawnMain.OstrichEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(Ostrich.class, 1, 1, 2));
        }

        if (OreSpawnMain.ChipmunkEnable != 0) {
            this.spawnableCaveCreatureList.add(new SpawnListEntry(Chipmunk.class, 1, 1, 2));
        }

        if (OreSpawnMain.BeaverEnable != 0) {
            this.spawnableCreatureList.add(new SpawnListEntry(Beaver.class, 1, 1, 2));
        }

        if (OreSpawnMain.CowEnable != 0) {
            this.spawnableCreatureList.add(new SpawnListEntry(RedCow.class, 3, 2, 4));
            this.spawnableCreatureList.add(new SpawnListEntry(GoldCow.class, 2, 2, 4));
            this.spawnableCreatureList.add(new SpawnListEntry(EnchantedCow.class, 1, 2, 4));
        }

        if (OreSpawnMain.VortexEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(Vortex.class, 1, 1, 2));
        }

        if (OreSpawnMain.PitchBlackEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(PitchBlack.class, 1, 1, 2));
        }

        if (OreSpawnMain.TerribleTerrorEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(TerribleTerror.class, 4, 2, 6));
        }

        if (OreSpawnMain.AlosaurusEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(Alosaurus.class, 1, 1, 1));
        }

        if (OreSpawnMain.BasiliskEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(Basilisk.class, 1, 1, 1));
        }

        if (OreSpawnMain.Robot1Enable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(Robot1.class, 5, 2, 8));
        }

        if (OreSpawnMain.Robot2Enable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(Robot2.class, 2, 1, 4));
        }

        if (OreSpawnMain.Robot3Enable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(Robot3.class, 2, 1, 4));
        }

        if (OreSpawnMain.Robot4Enable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(Robot4.class, 1, 1, 2));
        }

        if (OreSpawnMain.Robot5Enable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(Robot5.class, 2, 3, 5));
        }

        if (OreSpawnMain.CaterKillerEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(CaterKiller.class, 1, 1, 1));
        }

        if (OreSpawnMain.CaveFisherEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(CaveFisher.class, 5, 1, 5));
        }

        if (OreSpawnMain.CreepingHorrorEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(CreepingHorror.class, 5, 1, 5));
        }

        if (OreSpawnMain.CryolophosaurusEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(Cryolophosaurus.class, 5, 1, 5));
        }

        if (OreSpawnMain.UrchinEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(Urchin.class, 2, 1, 5));
        }

        if (OreSpawnMain.DungeonBeastEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(DungeonBeast.class, 2, 1, 5));
        }

        if (OreSpawnMain.EmperorScorpionEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(EmperorScorpion.class, 1, 1, 1));
        }

        if (OreSpawnMain.EnderKnightEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(EnderKnight.class, 2, 1, 2));
        }

        if (OreSpawnMain.EnderReaperEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(EnderReaper.class, 1, 1, 1));
        }

        if (OreSpawnMain.HammerheadEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(Hammerhead.class, 1, 1, 1));
        }

        if (OreSpawnMain.HerculesBeetleEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(HerculesBeetle.class, 1, 1, 1));
        }

        if (OreSpawnMain.TrooperBugEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(TrooperBug.class, 1, 1, 1));
        }

        if (OreSpawnMain.MolenoidEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(Molenoid.class, 1, 1, 1));
        }

        if (OreSpawnMain.MothraEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(Mothra.class, 1, 1, 1));
        }

        if (OreSpawnMain.BrutalflyEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(Brutalfly.class, 1, 1, 1));
        }

        if (OreSpawnMain.RatEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(Rat.class, 10, 1, 10));
        }

        if (OreSpawnMain.RotatorEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(Rotator.class, 1, 1, 3));
        }

        if (OreSpawnMain.ScorpionEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(Scorpion.class, 2, 1, 3));
        }

        if (OreSpawnMain.SpitBugEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(SpitBug.class, 2, 1, 3));
        }

        if (OreSpawnMain.NastysaurusEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(Nastysaurus.class, 1, 1, 1));
        }

        if (OreSpawnMain.TRexEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(TRex.class, 1, 1, 1));
        }

        if (OreSpawnMain.LeafMonsterEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(LeafMonster.class, 2, 1, 4));
        }

        if (OreSpawnMain.PointysaurusEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(Pointysaurus.class, 2, 1, 4));
        }

        if (OreSpawnMain.LeonEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(Leon.class, 1, 1, 1));
        }

        if (OreSpawnMain.MantisEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(Mantis.class, 1, 1, 1));
        }

        if (OreSpawnMain.LurkingTerrorEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(LurkingTerror.class, 1, 1, 1));
        }

        if (OreSpawnMain.GammaMetroidEnable != 0) {
            this.spawnableMonsterList.add(new SpawnListEntry(GammaMetroid.class, 1, 1, 1));
        }
    }
}
