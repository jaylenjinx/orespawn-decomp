package danger.orespawn.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderFish;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import danger.orespawn.CommonProxyOreSpawn;
import danger.orespawn.OreSpawnMain;
import danger.orespawn.OreSpawnSounds;
import danger.orespawn.client.gui.GirlfriendOverlayGui;
import danger.orespawn.client.model.ModelAlien;
import danger.orespawn.client.model.ModelAlosaurus;
import danger.orespawn.client.model.ModelAnt;
import danger.orespawn.client.model.ModelAntRobot;
import danger.orespawn.client.model.ModelAttackSquid;
import danger.orespawn.client.model.ModelBandP;
import danger.orespawn.client.model.ModelBaryonyx;
import danger.orespawn.client.model.ModelBasilisk;
import danger.orespawn.client.model.ModelBeaver;
import danger.orespawn.client.model.ModelBee;
import danger.orespawn.client.model.ModelBrutalfly;
import danger.orespawn.client.model.ModelButterfly;
import danger.orespawn.client.model.ModelCamarasaurus;
import danger.orespawn.client.model.ModelCassowary;
import danger.orespawn.client.model.ModelCaterKiller;
import danger.orespawn.client.model.ModelCaveFisher;
import danger.orespawn.client.model.ModelCephadrome;
import danger.orespawn.client.model.ModelChipmunk;
import danger.orespawn.client.model.ModelCliffRacer;
import danger.orespawn.client.model.ModelCloudShark;
import danger.orespawn.client.model.ModelCockateil;
import danger.orespawn.client.model.ModelCoin;
import danger.orespawn.client.model.ModelCrab;
import danger.orespawn.client.model.ModelCreepingHorror;
import danger.orespawn.client.model.ModelCricket;
import danger.orespawn.client.model.ModelCryolophosaurus;
import danger.orespawn.client.model.ModelDragon;
import danger.orespawn.client.model.ModelDragonfly;
import danger.orespawn.client.model.ModelDungeonBeast;
import danger.orespawn.client.model.ModelEasterBunny;
import danger.orespawn.client.model.ModelEmperorScorpion;
import danger.orespawn.client.model.ModelEnderKnight;
import danger.orespawn.client.model.ModelEnderReaper;
import danger.orespawn.client.model.ModelFairy;
import danger.orespawn.client.model.ModelFirefly;
import danger.orespawn.client.model.ModelFlounder;
import danger.orespawn.client.model.ModelFrog;
import danger.orespawn.client.model.ModelGammaMetroid;
import danger.orespawn.client.model.ModelGazelle;
import danger.orespawn.client.model.ModelGhost;
import danger.orespawn.client.model.ModelGhostSkelly;
import danger.orespawn.client.model.ModelGiantRobot;
import danger.orespawn.client.model.ModelGodzilla;
import danger.orespawn.client.model.ModelGoldFish;
import danger.orespawn.client.model.ModelHammerhead;
import danger.orespawn.client.model.ModelHerculesBeetle;
import danger.orespawn.client.model.ModelHydrolisc;
import danger.orespawn.client.model.ModelIrukandji;
import danger.orespawn.client.model.ModelIsland;
import danger.orespawn.client.model.ModelKraken;
import danger.orespawn.client.model.ModelKyuubi;
import danger.orespawn.client.model.ModelLeafMonster;
import danger.orespawn.client.model.ModelLeon;
import danger.orespawn.client.model.ModelLizard;
import danger.orespawn.client.model.ModelLurkingTerror;
import danger.orespawn.client.model.ModelMantis;
import danger.orespawn.client.model.ModelMolenoid;
import danger.orespawn.client.model.ModelMosquito;
import danger.orespawn.client.model.ModelNastysaurus;
import danger.orespawn.client.model.ModelOstrich;
import danger.orespawn.client.model.ModelPeacock;
import danger.orespawn.client.model.ModelPitchBlack;
import danger.orespawn.client.model.ModelPointysaurus;
import danger.orespawn.client.model.ModelPurplePower;
import danger.orespawn.client.model.ModelRat;
import danger.orespawn.client.model.ModelRobot1;
import danger.orespawn.client.model.ModelRobot2;
import danger.orespawn.client.model.ModelRobot3;
import danger.orespawn.client.model.ModelRobot4;
import danger.orespawn.client.model.ModelRobot5;
import danger.orespawn.client.model.ModelRockBase;
import danger.orespawn.client.model.ModelRotator;
import danger.orespawn.client.model.ModelRubberDucky;
import danger.orespawn.client.model.ModelScorpion;
import danger.orespawn.client.model.ModelSeaMonster;
import danger.orespawn.client.model.ModelSeaViper;
import danger.orespawn.client.model.ModelSkate;
import danger.orespawn.client.model.ModelSpiderRobot;
import danger.orespawn.client.model.ModelSpitBug;
import danger.orespawn.client.model.ModelSpyro;
import danger.orespawn.client.model.ModelStinkBug;
import danger.orespawn.client.model.ModelStinky;
import danger.orespawn.client.model.ModelTRex;
import danger.orespawn.client.model.ModelTerribleTerror;
import danger.orespawn.client.model.ModelTheKing;
import danger.orespawn.client.model.ModelThePrince;
import danger.orespawn.client.model.ModelThePrinceAdult;
import danger.orespawn.client.model.ModelThePrinceTeen;
import danger.orespawn.client.model.ModelThePrincess;
import danger.orespawn.client.model.ModelTheQueen;
import danger.orespawn.client.model.ModelTriffid;
import danger.orespawn.client.model.ModelTrooperBug;
import danger.orespawn.client.model.ModelTshirt;
import danger.orespawn.client.model.ModelUrchin;
import danger.orespawn.client.model.ModelVelocityRaptor;
import danger.orespawn.client.model.ModelVortex;
import danger.orespawn.client.model.ModelWaterDragon;
import danger.orespawn.client.model.ModelWhale;
import danger.orespawn.client.model.ModelWormLarge;
import danger.orespawn.client.model.ModelWormMedium;
import danger.orespawn.client.model.ModelWormSmall;
import danger.orespawn.client.render.RenderAlien;
import danger.orespawn.client.render.RenderAlosaurus;
import danger.orespawn.client.render.RenderAnt;
import danger.orespawn.client.render.RenderAntRobot;
import danger.orespawn.client.render.RenderAttackSquid;
import danger.orespawn.client.render.RenderBandP;
import danger.orespawn.client.render.RenderBaryonyx;
import danger.orespawn.client.render.RenderBasilisk;
import danger.orespawn.client.render.RenderBeaver;
import danger.orespawn.client.render.RenderBee;
import danger.orespawn.client.render.RenderBoyfriend;
import danger.orespawn.client.render.RenderBrutalfly;
import danger.orespawn.client.render.RenderButterfly;
import danger.orespawn.client.render.RenderCage;
import danger.orespawn.client.render.RenderCamarasaurus;
import danger.orespawn.client.render.RenderCassowary;
import danger.orespawn.client.render.RenderCaterKiller;
import danger.orespawn.client.render.RenderCaveFisher;
import danger.orespawn.client.render.RenderCephadrome;
import danger.orespawn.client.render.RenderChipmunk;
import danger.orespawn.client.render.RenderCliffRacer;
import danger.orespawn.client.render.RenderCloudShark;
import danger.orespawn.client.render.RenderCockateil;
import danger.orespawn.client.render.RenderCoin;
import danger.orespawn.client.render.RenderCrab;
import danger.orespawn.client.render.RenderCreepingHorror;
import danger.orespawn.client.render.RenderCricket;
import danger.orespawn.client.render.RenderCryolophosaurus;
import danger.orespawn.client.render.RenderDragon;
import danger.orespawn.client.render.RenderDragonfly;
import danger.orespawn.client.render.RenderDungeonBeast;
import danger.orespawn.client.render.RenderEasterBunny;
import danger.orespawn.client.render.RenderElevator;
import danger.orespawn.client.render.RenderEmperorScorpion;
import danger.orespawn.client.render.RenderEnchantedCow;
import danger.orespawn.client.render.RenderEnderKnight;
import danger.orespawn.client.render.RenderEnderReaper;
import danger.orespawn.client.render.RenderFairy;
import danger.orespawn.client.render.RenderFirefly;
import danger.orespawn.client.render.RenderFlounder;
import danger.orespawn.client.render.RenderFrog;
import danger.orespawn.client.render.RenderGammaMetroid;
import danger.orespawn.client.render.RenderGazelle;
import danger.orespawn.client.render.RenderGhost;
import danger.orespawn.client.render.RenderGhostSkelly;
import danger.orespawn.client.render.RenderGiantRobot;
import danger.orespawn.client.render.RenderGirlfriend;
import danger.orespawn.client.render.RenderGodzilla;
import danger.orespawn.client.render.RenderGodzillaHead;
import danger.orespawn.client.render.RenderGoldFish;
import danger.orespawn.client.render.RenderHammerhead;
import danger.orespawn.client.render.RenderHerculesBeetle;
import danger.orespawn.client.render.RenderHydrolisc;
import danger.orespawn.client.render.RenderIrukandji;
import danger.orespawn.client.render.RenderIsland;
import danger.orespawn.client.render.RenderIslandToo;
import danger.orespawn.client.render.RenderItemUrchin;
import danger.orespawn.client.render.RenderKingHead;
import danger.orespawn.client.render.RenderKraken;
import danger.orespawn.client.render.RenderKyuubi;
import danger.orespawn.client.render.RenderLeafMonster;
import danger.orespawn.client.render.RenderLeon;
import danger.orespawn.client.render.RenderLizard;
import danger.orespawn.client.render.RenderLurkingTerror;
import danger.orespawn.client.render.RenderMantis;
import danger.orespawn.client.render.RenderMolenoid;
import danger.orespawn.client.render.RenderMosquito;
import danger.orespawn.client.render.RenderNastysaurus;
import danger.orespawn.client.render.RenderOstrich;
import danger.orespawn.client.render.RenderPeacock;
import danger.orespawn.client.render.RenderPitchBlack;
import danger.orespawn.client.render.RenderPointysaurus;
import danger.orespawn.client.render.RenderPurplePower;
import danger.orespawn.client.render.RenderQueenHead;
import danger.orespawn.client.render.RenderRat;
import danger.orespawn.client.render.RenderRobot1;
import danger.orespawn.client.render.RenderRobot2;
import danger.orespawn.client.render.RenderRobot3;
import danger.orespawn.client.render.RenderRobot4;
import danger.orespawn.client.render.RenderRobot5;
import danger.orespawn.client.render.RenderRockBase;
import danger.orespawn.client.render.RenderRotator;
import danger.orespawn.client.render.RenderRubberDucky;
import danger.orespawn.client.render.RenderScorpion;
import danger.orespawn.client.render.RenderSeaMonster;
import danger.orespawn.client.render.RenderSeaViper;
import danger.orespawn.client.render.RenderShoe;
import danger.orespawn.client.render.RenderSkate;
import danger.orespawn.client.render.RenderSpiderDriver;
import danger.orespawn.client.render.RenderSpiderRobot;
import danger.orespawn.client.render.RenderSpitBug;
import danger.orespawn.client.render.RenderSpyro;
import danger.orespawn.client.render.RenderStinkBug;
import danger.orespawn.client.render.RenderStinky;
import danger.orespawn.client.render.RenderTRex;
import danger.orespawn.client.render.RenderTerribleTerror;
import danger.orespawn.client.render.RenderTheKing;
import danger.orespawn.client.render.RenderThePrince;
import danger.orespawn.client.render.RenderThePrinceAdult;
import danger.orespawn.client.render.RenderThePrinceTeen;
import danger.orespawn.client.render.RenderThePrincess;
import danger.orespawn.client.render.RenderTheQueen;
import danger.orespawn.client.render.RenderThrownRock;
import danger.orespawn.client.render.RenderTriffid;
import danger.orespawn.client.render.RenderTrooperBug;
import danger.orespawn.client.render.RenderTshirt;
import danger.orespawn.client.render.RenderUrchin;
import danger.orespawn.client.render.RenderVelocityRaptor;
import danger.orespawn.client.render.RenderVortex;
import danger.orespawn.client.render.RenderWaterDragon;
import danger.orespawn.client.render.RenderWhale;
import danger.orespawn.client.render.RenderWormLarge;
import danger.orespawn.client.render.RenderWormMedium;
import danger.orespawn.client.render.RenderWormSmall;
import danger.orespawn.client.render.item.RenderBattleAxe;
import danger.orespawn.client.render.item.RenderBertha;
import danger.orespawn.client.render.item.RenderChainsaw;
import danger.orespawn.client.render.item.RenderHammy;
import danger.orespawn.client.render.item.RenderQueenBattleAxe;
import danger.orespawn.client.render.item.RenderRoyal;
import danger.orespawn.client.render.item.RenderSlice;
import danger.orespawn.client.render.item.RenderSquidZooka;
import danger.orespawn.entity.Alien;
import danger.orespawn.entity.Alosaurus;
import danger.orespawn.entity.AntRobot;
import danger.orespawn.entity.AttackSquid;
import danger.orespawn.entity.BandP;
import danger.orespawn.entity.Baryonyx;
import danger.orespawn.entity.Basilisk;
import danger.orespawn.entity.Beaver;
import danger.orespawn.entity.Bee;
import danger.orespawn.entity.Boyfriend;
import danger.orespawn.entity.Brutalfly;
import danger.orespawn.entity.Camarasaurus;
import danger.orespawn.entity.Cassowary;
import danger.orespawn.entity.CaterKiller;
import danger.orespawn.entity.CaveFisher;
import danger.orespawn.entity.Cephadrome;
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
import danger.orespawn.entity.EasterBunny;
import danger.orespawn.entity.Elevator;
import danger.orespawn.entity.EmperorScorpion;
import danger.orespawn.entity.EnchantedCow;
import danger.orespawn.entity.EnderKnight;
import danger.orespawn.entity.EnderReaper;
import danger.orespawn.entity.EntityAnt;
import danger.orespawn.entity.EntityButterfly;
import danger.orespawn.entity.EntityLunaMoth;
import danger.orespawn.entity.EntityMosquito;
import danger.orespawn.entity.EntityRainbowAnt;
import danger.orespawn.entity.EntityRedAnt;
import danger.orespawn.entity.EntityUnstableAnt;
import danger.orespawn.entity.Fairy;
import danger.orespawn.entity.Firefly;
import danger.orespawn.entity.Flounder;
import danger.orespawn.entity.Frog;
import danger.orespawn.entity.GammaMetroid;
import danger.orespawn.entity.Gazelle;
import danger.orespawn.entity.Ghost;
import danger.orespawn.entity.GhostSkelly;
import danger.orespawn.entity.GiantRobot;
import danger.orespawn.entity.Girlfriend;
import danger.orespawn.entity.Godzilla;
import danger.orespawn.entity.GodzillaHead;
import danger.orespawn.entity.GoldCow;
import danger.orespawn.entity.GoldFish;
import danger.orespawn.entity.Hammerhead;
import danger.orespawn.entity.HerculesBeetle;
import danger.orespawn.entity.Hydrolisc;
import danger.orespawn.entity.Irukandji;
import danger.orespawn.entity.Island;
import danger.orespawn.entity.IslandToo;
import danger.orespawn.entity.KingHead;
import danger.orespawn.entity.Kraken;
import danger.orespawn.entity.Kyuubi;
import danger.orespawn.entity.LeafMonster;
import danger.orespawn.entity.Leon;
import danger.orespawn.entity.Lizard;
import danger.orespawn.entity.LurkingTerror;
import danger.orespawn.entity.Mantis;
import danger.orespawn.entity.Molenoid;
import danger.orespawn.entity.Mothra;
import danger.orespawn.entity.Nastysaurus;
import danger.orespawn.entity.Ostrich;
import danger.orespawn.entity.Peacock;
import danger.orespawn.entity.PitchBlack;
import danger.orespawn.entity.Pointysaurus;
import danger.orespawn.entity.PurplePower;
import danger.orespawn.entity.QueenHead;
import danger.orespawn.entity.Rat;
import danger.orespawn.entity.RedCow;
import danger.orespawn.entity.Robot1;
import danger.orespawn.entity.Robot2;
import danger.orespawn.entity.Robot3;
import danger.orespawn.entity.Robot4;
import danger.orespawn.entity.Robot5;
import danger.orespawn.entity.RockBase;
import danger.orespawn.entity.Rotator;
import danger.orespawn.entity.RubberDucky;
import danger.orespawn.entity.RubyBird;
import danger.orespawn.entity.Scorpion;
import danger.orespawn.entity.SeaMonster;
import danger.orespawn.entity.SeaViper;
import danger.orespawn.entity.Skate;
import danger.orespawn.entity.SpiderDriver;
import danger.orespawn.entity.SpiderRobot;
import danger.orespawn.entity.SpitBug;
import danger.orespawn.entity.Spyro;
import danger.orespawn.entity.StinkBug;
import danger.orespawn.entity.Stinky;
import danger.orespawn.entity.TRex;
import danger.orespawn.entity.Termite;
import danger.orespawn.entity.TerribleTerror;
import danger.orespawn.entity.TheKing;
import danger.orespawn.entity.ThePrince;
import danger.orespawn.entity.ThePrinceAdult;
import danger.orespawn.entity.ThePrinceTeen;
import danger.orespawn.entity.ThePrincess;
import danger.orespawn.entity.TheQueen;
import danger.orespawn.entity.Triffid;
import danger.orespawn.entity.TrooperBug;
import danger.orespawn.entity.Tshirt;
import danger.orespawn.entity.Urchin;
import danger.orespawn.entity.VelocityRaptor;
import danger.orespawn.entity.Vortex;
import danger.orespawn.entity.WaterDragon;
import danger.orespawn.entity.Whale;
import danger.orespawn.entity.WormLarge;
import danger.orespawn.entity.WormMedium;
import danger.orespawn.entity.WormSmall;
import danger.orespawn.entity.projectile.Acid;
import danger.orespawn.entity.projectile.BerthaHit;
import danger.orespawn.entity.projectile.DeadIrukandji;
import danger.orespawn.entity.projectile.EntityCage;
import danger.orespawn.entity.projectile.EntityThrownRock;
import danger.orespawn.entity.projectile.IceBall;
import danger.orespawn.entity.projectile.InkSack;
import danger.orespawn.entity.projectile.LaserBall;
import danger.orespawn.entity.projectile.Shoes;
import danger.orespawn.entity.projectile.SunspotUrchin;
import danger.orespawn.entity.projectile.UltimateArrow;
import danger.orespawn.entity.projectile.UltimateFishHook;
import danger.orespawn.entity.projectile.WaterBall;
import danger.orespawn.network.RiderControl;

public class ClientProxyOreSpawn extends CommonProxyOreSpawn {

    @Override
    public void registerRenderThings() {
        MinecraftForge.EVENT_BUS.register(new GirlfriendOverlayGui(Minecraft.getMinecraft()));
        RenderingRegistry
            .registerEntityRenderingHandler(Girlfriend.class, new RenderGirlfriend(new ModelBiped(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(Boyfriend.class, new RenderBoyfriend(new ModelBiped(), 0.55F));
        RenderingRegistry.registerEntityRenderingHandler(RedCow.class, new RenderEnchantedCow(new ModelCow(), 0.7F));
        RenderingRegistry.registerEntityRenderingHandler(GoldCow.class, new RenderEnchantedCow(new ModelCow(), 0.7F));
        RenderingRegistry
            .registerEntityRenderingHandler(EnchantedCow.class, new RenderEnchantedCow(new ModelCow(), 0.7F));
        RenderingRegistry
            .registerEntityRenderingHandler(CrystalCow.class, new RenderEnchantedCow(new ModelCow(), 0.7F));
        RenderingRegistry.registerEntityRenderingHandler(Shoes.class, new RenderShoe());
        RenderingRegistry.registerEntityRenderingHandler(SunspotUrchin.class, new RenderItemUrchin());
        RenderingRegistry.registerEntityRenderingHandler(WaterBall.class, new RenderItemUrchin());
        RenderingRegistry.registerEntityRenderingHandler(InkSack.class, new RenderItemUrchin());
        RenderingRegistry.registerEntityRenderingHandler(LaserBall.class, new RenderItemUrchin());
        RenderingRegistry.registerEntityRenderingHandler(IceBall.class, new RenderItemUrchin());
        RenderingRegistry.registerEntityRenderingHandler(Acid.class, new RenderItemUrchin());
        RenderingRegistry.registerEntityRenderingHandler(DeadIrukandji.class, new RenderItemUrchin());
        RenderingRegistry.registerEntityRenderingHandler(BerthaHit.class, new RenderItemUrchin());
        RenderingRegistry.registerEntityRenderingHandler(EntityCage.class, new RenderCage());
        RenderingRegistry.registerEntityRenderingHandler(UltimateFishHook.class, new RenderFish());
        RenderingRegistry.registerEntityRenderingHandler(UltimateArrow.class, new RenderArrow());
        RenderingRegistry.registerEntityRenderingHandler(EntityThrownRock.class, new RenderThrownRock());
        RenderingRegistry.registerEntityRenderingHandler(
            EntityButterfly.class,
            new RenderButterfly(new ModelButterfly(1.0F), 0.3F, 1.0F));
        RenderingRegistry
            .registerEntityRenderingHandler(Firefly.class, new RenderFirefly(new ModelFirefly(2.5F), 0.2F, 0.75F));
        RenderingRegistry.registerEntityRenderingHandler(
            EntityLunaMoth.class,
            new RenderButterfly(new ModelButterfly(0.75F), 0.4F, 1.5F));
        RenderingRegistry
            .registerEntityRenderingHandler(EntityMosquito.class, new RenderMosquito(new ModelMosquito(), 0.3F, 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(Ghost.class, new RenderGhost(new ModelGhost(), 0.0F, 0.65F));
        RenderingRegistry.registerEntityRenderingHandler(
            GhostSkelly.class,
            new RenderGhostSkelly(new ModelGhostSkelly(), 0.0F, 1.05F));
        RenderingRegistry
            .registerEntityRenderingHandler(Mothra.class, new RenderButterfly(new ModelButterfly(0.2F), 0.75F, 10.0F));
        RenderingRegistry.registerEntityRenderingHandler(EntityAnt.class, new RenderAnt(new ModelAnt(), 0.1F, 0.25F));
        RenderingRegistry
            .registerEntityRenderingHandler(EntityRedAnt.class, new RenderAnt(new ModelAnt(), 0.15F, 0.35F));
        RenderingRegistry
            .registerEntityRenderingHandler(EntityRainbowAnt.class, new RenderAnt(new ModelAnt(), 0.1F, 0.25F));
        RenderingRegistry
            .registerEntityRenderingHandler(EntityUnstableAnt.class, new RenderAnt(new ModelAnt(), 0.1F, 0.25F));
        RenderingRegistry.registerEntityRenderingHandler(
            Alosaurus.class,
            new RenderAlosaurus(new ModelAlosaurus(0.22F), 1.0F, 1.0F));
        RenderingRegistry.registerEntityRenderingHandler(TRex.class, new RenderTRex(new ModelTRex(0.2F), 1.0F, 1.2F));
        RenderingRegistry
            .registerEntityRenderingHandler(Tshirt.class, new RenderTshirt(new ModelTshirt(0.22F), 1.0F, 0.33F));
        RenderingRegistry.registerEntityRenderingHandler(
            Cryolophosaurus.class,
            new RenderCryolophosaurus(new ModelCryolophosaurus(0.75F), 0.75F, 0.5F));
        RenderingRegistry
            .registerEntityRenderingHandler(Basilisk.class, new RenderBasilisk(new ModelBasilisk(0.3F), 0.5F, 1.25F));
        RenderingRegistry.registerEntityRenderingHandler(
            Camarasaurus.class,
            new RenderCamarasaurus(new ModelCamarasaurus(0.65F), 0.65F, 0.65F));
        RenderingRegistry.registerEntityRenderingHandler(
            Hydrolisc.class,
            new RenderHydrolisc(new ModelHydrolisc(0.65F), 0.65F, 0.65F));
        RenderingRegistry.registerEntityRenderingHandler(
            VelocityRaptor.class,
            new RenderVelocityRaptor(new ModelVelocityRaptor(1.25F), 0.55F, 0.75F));
        RenderingRegistry
            .registerEntityRenderingHandler(Dragonfly.class, new RenderDragonfly(new ModelDragonfly(2.0F), 0.3F, 1.5F));
        RenderingRegistry.registerEntityRenderingHandler(Bee.class, new RenderBee(new ModelBee(2.0F), 0.9F, 1.1F));
        RenderingRegistry.registerEntityRenderingHandler(
            EmperorScorpion.class,
            new RenderEmperorScorpion(new ModelEmperorScorpion(0.22F), 0.95F, 1.5F));
        RenderingRegistry
            .registerEntityRenderingHandler(Spyro.class, new RenderSpyro(new ModelSpyro(0.65F), 0.65F, 0.75F));
        RenderingRegistry
            .registerEntityRenderingHandler(Baryonyx.class, new RenderBaryonyx(new ModelBaryonyx(0.25F), 1.0F, 1.0F));
        RenderingRegistry.registerEntityRenderingHandler(
            GammaMetroid.class,
            new RenderGammaMetroid(new ModelGammaMetroid(0.45F), 0.75F, 0.9F));
        RenderingRegistry.registerEntityRenderingHandler(
            Cockateil.class,
            new RenderCockateil(new ModelCockateil(1.0F), 0.3F, 0.75F));
        RenderingRegistry
            .registerEntityRenderingHandler(RubyBird.class, new RenderCockateil(new ModelCockateil(1.0F), 0.3F, 0.75F));
        RenderingRegistry
            .registerEntityRenderingHandler(Kyuubi.class, new RenderKyuubi(new ModelKyuubi(0.5F), 0.1F, 1.0F));
        RenderingRegistry
            .registerEntityRenderingHandler(Scorpion.class, new RenderScorpion(new ModelScorpion(0.62F), 0.35F, 0.75F));
        RenderingRegistry.registerEntityRenderingHandler(
            CaveFisher.class,
            new RenderCaveFisher(new ModelCaveFisher(0.62F), 0.35F, 0.75F));
        RenderingRegistry
            .registerEntityRenderingHandler(Alien.class, new RenderAlien(new ModelAlien(0.22F), 0.35F, 1.1F));
        RenderingRegistry.registerEntityRenderingHandler(
            WaterDragon.class,
            new RenderWaterDragon(new ModelWaterDragon(0.5F), 0.85F, 1.1F));
        RenderingRegistry.registerEntityRenderingHandler(
            AttackSquid.class,
            new RenderAttackSquid(new ModelAttackSquid(1.0F), 0.25F, 0.9F));
        RenderingRegistry.registerEntityRenderingHandler(Elevator.class, new RenderElevator());
        RenderingRegistry
            .registerEntityRenderingHandler(Robot1.class, new RenderRobot1(new ModelRobot1(2.0F), 0.3F, 1.0F));
        RenderingRegistry
            .registerEntityRenderingHandler(Robot2.class, new RenderRobot2(new ModelRobot2(1.0F), 1.0F, 1.0F));
        RenderingRegistry
            .registerEntityRenderingHandler(Robot3.class, new RenderRobot3(new ModelRobot3(1.0F), 1.0F, 0.5F));
        RenderingRegistry
            .registerEntityRenderingHandler(Robot4.class, new RenderRobot4(new ModelRobot4(1.0F), 1.0F, 1.0F));
        RenderingRegistry
            .registerEntityRenderingHandler(Robot5.class, new RenderRobot5(new ModelRobot5(1.0F), 0.5F, 1.0F));
        RenderingRegistry
            .registerEntityRenderingHandler(Kraken.class, new RenderKraken(new ModelKraken(1.0F), 1.0F, 1.0F));
        RenderingRegistry
            .registerEntityRenderingHandler(Lizard.class, new RenderLizard(new ModelLizard(0.65F), 0.75F, 1.0F));
        RenderingRegistry.registerEntityRenderingHandler(
            Cephadrome.class,
            new RenderCephadrome(new ModelCephadrome(0.55F), 1.25F, 1.0F));
        RenderingRegistry
            .registerEntityRenderingHandler(Dragon.class, new RenderDragon(new ModelDragon(0.65F), 1.25F, 1.0F));
        RenderingRegistry
            .registerEntityRenderingHandler(Chipmunk.class, new RenderChipmunk(new ModelChipmunk(1.0F), 0.15F, 0.9F));
        RenderingRegistry
            .registerEntityRenderingHandler(Gazelle.class, new RenderGazelle(new ModelGazelle(0.65F), 0.45F, 1.0F));
        RenderingRegistry
            .registerEntityRenderingHandler(Ostrich.class, new RenderOstrich(new ModelOstrich(0.65F), 0.55F, 1.0F));
        RenderingRegistry.registerEntityRenderingHandler(
            TrooperBug.class,
            new RenderTrooperBug(new ModelTrooperBug(0.22F), 0.95F, 1.1F));
        RenderingRegistry
            .registerEntityRenderingHandler(SpitBug.class, new RenderSpitBug(new ModelSpitBug(0.55F), 0.55F, 0.75F));
        RenderingRegistry
            .registerEntityRenderingHandler(StinkBug.class, new RenderStinkBug(new ModelStinkBug(0.75F), 0.35F, 0.85F));
        RenderingRegistry
            .registerEntityRenderingHandler(Island.class, new RenderIsland(new ModelIsland(1.0F), 0.25F, 1.0F));
        RenderingRegistry
            .registerEntityRenderingHandler(IslandToo.class, new RenderIslandToo(new ModelIsland(1.0F), 0.25F, 1.0F));
        RenderingRegistry.registerEntityRenderingHandler(
            CreepingHorror.class,
            new RenderCreepingHorror(new ModelCreepingHorror(), 0.45F, 0.75F));
        RenderingRegistry.registerEntityRenderingHandler(
            TerribleTerror.class,
            new RenderTerribleTerror(new ModelTerribleTerror(), 0.45F, 0.75F));
        RenderingRegistry.registerEntityRenderingHandler(
            CliffRacer.class,
            new RenderCliffRacer(new ModelCliffRacer(1.0F), 0.3F, 1.0F));
        RenderingRegistry
            .registerEntityRenderingHandler(Triffid.class, new RenderTriffid(new ModelTriffid(1.0F), 0.3F, 1.0F));
        RenderingRegistry.registerEntityRenderingHandler(
            PitchBlack.class,
            new RenderPitchBlack(new ModelPitchBlack(0.65F), 1.25F, 1.0F));
        RenderingRegistry.registerEntityRenderingHandler(
            LurkingTerror.class,
            new RenderLurkingTerror(new ModelLurkingTerror(), 0.45F, 0.85F));
        RenderingRegistry
            .registerEntityRenderingHandler(Godzilla.class, new RenderGodzilla(new ModelGodzilla(0.2F), 1.0F, 2.0F));
        RenderingRegistry.registerEntityRenderingHandler(GodzillaHead.class, new RenderGodzillaHead(null, 0.0F, 0.0F));
        RenderingRegistry.registerEntityRenderingHandler(KingHead.class, new RenderKingHead(null, 0.0F, 0.0F));
        RenderingRegistry.registerEntityRenderingHandler(QueenHead.class, new RenderQueenHead(null, 0.0F, 0.0F));
        RenderingRegistry
            .registerEntityRenderingHandler(WormSmall.class, new RenderWormSmall(new ModelWormSmall(), 0.1F, 1.0F));
        RenderingRegistry
            .registerEntityRenderingHandler(WormMedium.class, new RenderWormMedium(new ModelWormMedium(), 0.25F, 1.0F));
        RenderingRegistry
            .registerEntityRenderingHandler(WormLarge.class, new RenderWormLarge(new ModelWormLarge(), 0.9F, 1.0F));
        RenderingRegistry.registerEntityRenderingHandler(
            Cassowary.class,
            new RenderCassowary(new ModelCassowary(0.55F), 0.5F, 1.0F));
        RenderingRegistry
            .registerEntityRenderingHandler(GoldFish.class, new RenderGoldFish(new ModelGoldFish(0.7F), 0.2F, 1.0F));
        RenderingRegistry.registerEntityRenderingHandler(
            CloudShark.class,
            new RenderCloudShark(new ModelCloudShark(1.0F), 0.5F, 1.0F));
        RenderingRegistry.registerEntityRenderingHandler(
            LeafMonster.class,
            new RenderLeafMonster(new ModelLeafMonster(), 0.65F, 1.0F));
        RenderingRegistry.registerEntityRenderingHandler(
            EnderKnight.class,
            new RenderEnderKnight(new ModelEnderKnight(0.21F), 0.3F, 1.0F));
        RenderingRegistry.registerEntityRenderingHandler(
            EnderReaper.class,
            new RenderEnderReaper(new ModelEnderReaper(0.23F), 0.2F, 1.0F));
        RenderingRegistry
            .registerEntityRenderingHandler(Beaver.class, new RenderBeaver(new ModelBeaver(0.5F), 0.15F, 0.75F));
        RenderingRegistry.registerEntityRenderingHandler(Termite.class, new RenderAnt(new ModelAnt(), 0.15F, 0.35F));
        RenderingRegistry
            .registerEntityRenderingHandler(Fairy.class, new RenderFairy(new ModelFairy(1.5F), 0.1F, 0.35F));
        RenderingRegistry
            .registerEntityRenderingHandler(Peacock.class, new RenderPeacock(new ModelPeacock(0.75F), 0.25F, 1.0F));
        RenderingRegistry
            .registerEntityRenderingHandler(Rotator.class, new RenderRotator(new ModelRotator(0.25F), 0.1F, 1.0F));
        RenderingRegistry
            .registerEntityRenderingHandler(Vortex.class, new RenderVortex(new ModelVortex(0.25F), 0.1F, 1.0F));
        RenderingRegistry.registerEntityRenderingHandler(
            DungeonBeast.class,
            new RenderDungeonBeast(new ModelDungeonBeast(0.62F), 0.25F, 1.0F));
        RenderingRegistry.registerEntityRenderingHandler(Rat.class, new RenderRat(new ModelRat(1.0F), 0.1F, 0.75F));
        RenderingRegistry
            .registerEntityRenderingHandler(Flounder.class, new RenderFlounder(new ModelFlounder(), 0.1F, 1.0F));
        RenderingRegistry.registerEntityRenderingHandler(Whale.class, new RenderWhale(new ModelWhale(), 0.1F, 1.0F));
        RenderingRegistry.registerEntityRenderingHandler(
            Irukandji.class,
            new RenderIrukandji(new ModelIrukandji(1.0F), 0.1F, 0.25F));
        RenderingRegistry
            .registerEntityRenderingHandler(Skate.class, new RenderSkate(new ModelSkate(1.0F), 0.1F, 0.75F));
        RenderingRegistry
            .registerEntityRenderingHandler(Urchin.class, new RenderUrchin(new ModelUrchin(1.0F), 0.35F, 1.25F));
        RenderingRegistry
            .registerEntityRenderingHandler(Mantis.class, new RenderMantis(new ModelMantis(2.0F), 0.9F, 1.1F));
        RenderingRegistry.registerEntityRenderingHandler(
            HerculesBeetle.class,
            new RenderHerculesBeetle(new ModelHerculesBeetle(1.0F), 0.99F, 1.1F));
        RenderingRegistry
            .registerEntityRenderingHandler(Stinky.class, new RenderStinky(new ModelStinky(0.65F), 0.75F, 1.0F));
        RenderingRegistry
            .registerEntityRenderingHandler(Coin.class, new RenderCoin(new ModelCoin(0.22F), 0.75F, 0.125F));
        RenderingRegistry
            .registerEntityRenderingHandler(TheKing.class, new RenderTheKing(new ModelTheKing(0.65F), 1.9F, 2.1F));
        RenderingRegistry
            .registerEntityRenderingHandler(TheQueen.class, new RenderTheQueen(new ModelTheQueen(0.65F), 1.9F, 2.0F));
        RenderingRegistry.registerEntityRenderingHandler(
            ThePrince.class,
            new RenderThePrince(new ModelThePrince(0.65F), 0.75F, 0.75F));
        RenderingRegistry
            .registerEntityRenderingHandler(Molenoid.class, new RenderMolenoid(new ModelMolenoid(0.5F), 1.0F, 1.0F));
        RenderingRegistry.registerEntityRenderingHandler(
            SeaMonster.class,
            new RenderSeaMonster(new ModelSeaMonster(0.5F), 1.0F, 1.0F));
        RenderingRegistry
            .registerEntityRenderingHandler(SeaViper.class, new RenderSeaViper(new ModelSeaViper(0.5F), 1.0F, 1.0F));
        RenderingRegistry.registerEntityRenderingHandler(
            EasterBunny.class,
            new RenderEasterBunny(new ModelEasterBunny(0.55F), 0.5F, 1.0F));
        RenderingRegistry.registerEntityRenderingHandler(
            CaterKiller.class,
            new RenderCaterKiller(new ModelCaterKiller(0.22F), 1.0F, 1.25F));
        RenderingRegistry.registerEntityRenderingHandler(Leon.class, new RenderLeon(new ModelLeon(0.22F), 1.0F, 1.75F));
        RenderingRegistry.registerEntityRenderingHandler(
            Hammerhead.class,
            new RenderHammerhead(new ModelHammerhead(0.33F), 1.0F, 2.5F));
        RenderingRegistry.registerEntityRenderingHandler(
            RubberDucky.class,
            new RenderRubberDucky(new ModelRubberDucky(1.0F), 0.15F, 0.75F));
        RenderingRegistry.registerEntityRenderingHandler(
            ThePrinceTeen.class,
            new RenderThePrinceTeen(new ModelThePrinceTeen(0.65F), 1.0F, 1.25F));
        RenderingRegistry
            .registerEntityRenderingHandler(BandP.class, new RenderBandP(new ModelBandP(0.4F), 1.0F, 1.0F));
        RenderingRegistry
            .registerEntityRenderingHandler(RockBase.class, new RenderRockBase(new ModelRockBase(1.0F), 0.0F, 1.0F));
        RenderingRegistry.registerEntityRenderingHandler(
            PurplePower.class,
            new RenderPurplePower(new ModelPurplePower(1.0F), 0.3F, 2.75F));
        RenderingRegistry.registerEntityRenderingHandler(
            Brutalfly.class,
            new RenderBrutalfly(new ModelBrutalfly(0.2F), 0.75F, 9.0F));
        RenderingRegistry.registerEntityRenderingHandler(
            Nastysaurus.class,
            new RenderNastysaurus(new ModelNastysaurus(0.65F), 1.0F, 1.5F));
        RenderingRegistry.registerEntityRenderingHandler(
            Pointysaurus.class,
            new RenderPointysaurus(new ModelPointysaurus(1.0F), 1.0F, 1.0F));
        RenderingRegistry
            .registerEntityRenderingHandler(Cricket.class, new RenderCricket(new ModelCricket(2.5F), 0.15F, 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(
            ThePrincess.class,
            new RenderThePrincess(new ModelThePrincess(0.65F), 0.7F, 0.7F));
        RenderingRegistry.registerEntityRenderingHandler(Frog.class, new RenderFrog(new ModelFrog(1.0F), 0.35F, 1.0F));
        RenderingRegistry.registerEntityRenderingHandler(
            ThePrinceAdult.class,
            new RenderThePrinceAdult(new ModelThePrinceAdult(0.65F), 1.2F, 1.0F));
        RenderingRegistry.registerEntityRenderingHandler(
            SpiderRobot.class,
            new RenderSpiderRobot(new ModelSpiderRobot(1.0F), 0.99F, 1.0F));
        RenderingRegistry
            .registerEntityRenderingHandler(SpiderDriver.class, new RenderSpiderDriver(new ModelSpider(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(
            GiantRobot.class,
            new RenderGiantRobot(new ModelGiantRobot(0.25F), 0.99F, 1.0F));
        RenderingRegistry
            .registerEntityRenderingHandler(AntRobot.class, new RenderAntRobot(new ModelAntRobot(1.0F), 0.99F, 1.0F));
        RenderingRegistry.registerEntityRenderingHandler(Crab.class, new RenderCrab(new ModelCrab(1.0F), 0.99F, 1.0F));
        MinecraftForgeClient.registerItemRenderer(OreSpawnMain.MyBertha, new RenderBertha());
        MinecraftForgeClient.registerItemRenderer(OreSpawnMain.MySlice, new RenderSlice());
        MinecraftForgeClient.registerItemRenderer(OreSpawnMain.MyRoyal, new RenderRoyal());
        MinecraftForgeClient.registerItemRenderer(OreSpawnMain.MySquidZooka, new RenderSquidZooka());
        MinecraftForgeClient.registerItemRenderer(OreSpawnMain.MyHammy, new RenderHammy());
        MinecraftForgeClient.registerItemRenderer(OreSpawnMain.MyBattleAxe, new RenderBattleAxe());
        MinecraftForgeClient.registerItemRenderer(OreSpawnMain.MyChainsaw, new RenderChainsaw());
        MinecraftForgeClient.registerItemRenderer(OreSpawnMain.MyQueenBattleAxe, new RenderQueenBattleAxe());
    }

    @Override
    public void registerSoundThings() {
        MinecraftForge.EVENT_BUS.register(new OreSpawnSounds());
    }

    @Override
    public void registerKeyboardInput() {
        KeyHandler k = new KeyHandler();
        FMLCommonHandler.instance()
            .bus()
            .register(k);
        OreSpawnMain.MyKeyhandler = k;
    }

    @Override
    public void registerNetworkStuff() {
        super.registerNetworkStuff();
        FMLCommonHandler.instance()
            .bus()
            .register(new RiderControl(this.getNetwork()));
    }

    @Override
    public int setArmorPrefix(String string) {
        return RenderingRegistry.addNewArmourRendererPrefix(string);
    }
}
