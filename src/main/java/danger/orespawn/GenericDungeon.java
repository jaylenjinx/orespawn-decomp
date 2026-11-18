package danger.orespawn;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class GenericDungeon {

    private final WeightedRandomChestContent[] RainbowContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(OreSpawnMain.MagicApple, 0, 1, 1, 25),
        new WeightedRandomChestContent(OreSpawnMain.CloudSharkEgg, 0, 4, 10, 25),
        new WeightedRandomChestContent(Items.bone, 0, 2, 16, 25),
        new WeightedRandomChestContent(Items.string, 0, 2, 16, 25),
        new WeightedRandomChestContent(Items.rotten_flesh, 0, 3, 10, 25),
        new WeightedRandomChestContent(Items.experience_bottle, 0, 4, 10, 25) };
    private final WeightedRandomChestContent[] WhiteHouseContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(OreSpawnMain.MyCornDog, 0, 6, 12, 35),
        new WeightedRandomChestContent(OreSpawnMain.UraniumNugget, 0, 2, 6, 10),
        new WeightedRandomChestContent(OreSpawnMain.TitaniumNugget, 0, 2, 6, 10),
        new WeightedRandomChestContent(OreSpawnMain.MyAmethyst, 0, 2, 6, 35),
        new WeightedRandomChestContent(OreSpawnMain.MyRuby, 0, 2, 6, 25),
        new WeightedRandomChestContent(OreSpawnMain.CriminalEgg, 0, 4, 10, 35),
        new WeightedRandomChestContent(Items.emerald, 0, 6, 16, 35),
        new WeightedRandomChestContent(Items.porkchop, 0, 6, 16, 35),
        new WeightedRandomChestContent(Items.cooked_porkchop, 0, 6, 16, 35),
        new WeightedRandomChestContent(Items.diamond, 0, 6, 16, 35),
        new WeightedRandomChestContent(Items.gold_ingot, 0, 6, 16, 35) };
    private final WeightedRandomChestContent[] RubberDuckyContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(OreSpawnMain.MyDeadStinkBug, 0, 4, 10, 35),
        new WeightedRandomChestContent(OreSpawnMain.MyFireFish, 0, 4, 10, 35),
        new WeightedRandomChestContent(OreSpawnMain.MySunFish, 0, 4, 10, 35),
        new WeightedRandomChestContent(OreSpawnMain.MySparkFish, 0, 4, 10, 35),
        new WeightedRandomChestContent(OreSpawnMain.MyGreenFish, 0, 4, 10, 35),
        new WeightedRandomChestContent(OreSpawnMain.MyBlueFish, 0, 4, 10, 35),
        new WeightedRandomChestContent(OreSpawnMain.MyPinkFish, 0, 4, 10, 35),
        new WeightedRandomChestContent(OreSpawnMain.MyRockFish, 0, 4, 10, 35),
        new WeightedRandomChestContent(OreSpawnMain.MyWoodFish, 0, 4, 10, 35),
        new WeightedRandomChestContent(OreSpawnMain.MyGreyFish, 0, 4, 10, 35),
        new WeightedRandomChestContent(OreSpawnMain.RubberDuckyEgg, 0, 4, 10, 35),
        new WeightedRandomChestContent(OreSpawnMain.MyPeacockFeather, 0, 4, 10, 35),
        new WeightedRandomChestContent(Items.feather, 0, 6, 16, 35) };
    private final WeightedRandomChestContent[] StinkyHouseContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(OreSpawnMain.MyDeadStinkBug, 0, 4, 10, 35),
        new WeightedRandomChestContent(OreSpawnMain.StinkyEgg, 0, 4, 10, 35),
        new WeightedRandomChestContent(OreSpawnMain.StinkBugEgg, 0, 4, 10, 35),
        new WeightedRandomChestContent(Items.bone, 0, 6, 16, 25),
        new WeightedRandomChestContent(Items.coal, 0, 6, 16, 25),
        new WeightedRandomChestContent(Items.string, 0, 6, 16, 25),
        new WeightedRandomChestContent(Items.rotten_flesh, 0, 3, 10, 35) };
    private final WeightedRandomChestContent[] NightmareRookeryContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(OreSpawnMain.MyDeadStinkBug, 0, 4, 10, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(OreSpawnMain.MyFlowerBlackBlock), 0, 4, 10, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(OreSpawnMain.MyFlowerScaryBlock), 0, 4, 10, 35),
        new WeightedRandomChestContent(OreSpawnMain.PitchBlackEgg, 0, 4, 10, 25),
        new WeightedRandomChestContent(OreSpawnMain.AntRobotKit, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.SpiderRobotKit, 0, 1, 1, 10),
        new WeightedRandomChestContent(Items.bone, 0, 6, 16, 25),
        new WeightedRandomChestContent(Items.string, 0, 6, 16, 25),
        new WeightedRandomChestContent(Items.rotten_flesh, 0, 3, 10, 35),
        new WeightedRandomChestContent(Items.experience_bottle, 0, 4, 10, 35) };
    private final WeightedRandomChestContent[] MonsterIslandContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(Item.getItemFromBlock(OreSpawnMain.CreeperRepellent), 0, 4, 10, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(OreSpawnMain.KrakenRepellent), 0, 4, 10, 35),
        new WeightedRandomChestContent(Items.dye, 0, 6, 16, 25),
        new WeightedRandomChestContent(Items.bone, 0, 6, 16, 25),
        new WeightedRandomChestContent(Items.string, 0, 6, 16, 25),
        new WeightedRandomChestContent(Items.porkchop, 0, 3, 10, 35),
        new WeightedRandomChestContent(Items.beef, 0, 3, 10, 35),
        new WeightedRandomChestContent(Items.chicken, 0, 3, 10, 35),
        new WeightedRandomChestContent(Items.fish, 0, 3, 10, 35),
        new WeightedRandomChestContent(Items.rotten_flesh, 0, 3, 10, 35),
        new WeightedRandomChestContent(Items.experience_bottle, 0, 4, 10, 35),
        new WeightedRandomChestContent(OreSpawnMain.MyRawBacon, 0, 6, 16, 35),
        new WeightedRandomChestContent(OreSpawnMain.MyRawPeacock, 0, 6, 16, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.log), 0, 6, 16, 25) };
    private final WeightedRandomChestContent[] GreenhouseContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(OreSpawnMain.GreenGoo, 0, 4, 10, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(OreSpawnMain.CreeperRepellent), 0, 4, 10, 35),
        new WeightedRandomChestContent(Items.flower_pot, 0, 6, 16, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.sapling), 0, 6, 16, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.leaves), 0, 6, 16, 25),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.dirt), 0, 6, 16, 25),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.log), 0, 6, 16, 25) };
    private final WeightedRandomChestContent[] CrystalBattleTowerRatContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(Items.cooked_porkchop, 0, 3, 10, 35),
        new WeightedRandomChestContent(Items.beef, 0, 3, 10, 35),
        new WeightedRandomChestContent(Items.cooked_chicken, 0, 3, 10, 35),
        new WeightedRandomChestContent(Items.cooked_fished, 0, 3, 10, 35),
        new WeightedRandomChestContent(OreSpawnMain.MyBLT, 0, 4, 10, 35),
        new WeightedRandomChestContent(OreSpawnMain.MySalad, 0, 4, 10, 35),
        new WeightedRandomChestContent(OreSpawnMain.MyCornDog, 0, 4, 10, 35) };
    private final WeightedRandomChestContent[] CrystalBattleTowerDungeonBeastContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(Items.dye, 0, 6, 16, 25),
        new WeightedRandomChestContent(OreSpawnMain.MySquidZooka, 0, 1, 1, 25),
        new WeightedRandomChestContent(Items.gold_nugget, 0, 5, 15, 15),
        new WeightedRandomChestContent(Items.rotten_flesh, 0, 6, 16, 25) };
    private final WeightedRandomChestContent[] CrystalBattleTowerUrchinContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(OreSpawnMain.CrystalPinkHelmet, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.CrystalPinkBody, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.CrystalPinkLegs, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.CrystalPinkBoots, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.MyFairySword, 0, 1, 1, 15) };
    private final WeightedRandomChestContent[] CrystalBattleTowerRotatorContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(OreSpawnMain.TigersEyeHelmet, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.TigersEyeBody, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.TigersEyeLegs, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.TigersEyeBoots, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.MyRatSword, 0, 1, 1, 15) };
    private final WeightedRandomChestContent[] CrystalBattleTowerVortexContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(Item.getItemFromBlock(OreSpawnMain.CrystalCoal), 0, 6, 10, 10),
        new WeightedRandomChestContent(Item.getItemFromBlock(OreSpawnMain.CrystalCoal), 0, 6, 10, 10),
        new WeightedRandomChestContent(OreSpawnMain.MyTigersEyeSword, 0, 1, 1, 10),
        new WeightedRandomChestContent(Item.getItemFromBlock(OreSpawnMain.MyTigersEyeBlock), 0, 4, 8, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyPoisonSword, 0, 1, 1, 15) };
    private final WeightedRandomChestContent[] RobotContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(Items.redstone, 0, 1, 10, 35),
        new WeightedRandomChestContent(Items.repeater, 0, 1, 10, 35),
        new WeightedRandomChestContent(Items.minecart, 0, 1, 1, 35),
        new WeightedRandomChestContent(Items.fire_charge, 0, 1, 10, 35),
        new WeightedRandomChestContent(Items.hopper_minecart, 0, 1, 1, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.redstone_block), 0, 1, 10, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.rail), 0, 1, 10, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.detector_rail), 0, 1, 10, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.sticky_piston), 0, 1, 10, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.piston), 0, 1, 10, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.redstone_torch), 0, 1, 10, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.tnt), 0, 1, 10, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.rail), 0, 1, 10, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.lever), 0, 1, 10, 35),
        new WeightedRandomChestContent(OreSpawnMain.AntRobotKit, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.SpiderRobotKit, 0, 1, 1, 10),
        new WeightedRandomChestContent(Items.iron_door, 0, 1, 10, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.redstone_torch), 0, 1, 10, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.wooden_button), 0, 1, 10, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.iron_bars), 0, 1, 10, 35),
        new WeightedRandomChestContent(Items.comparator, 0, 1, 10, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.activator_rail), 0, 1, 10, 35),
        new WeightedRandomChestContent(OreSpawnMain.MyRayGun, 0, 1, 1, 35) };
    private final WeightedRandomChestContent[] IncaPyramidContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(Items.golden_sword, 0, 1, 1, 35),
        new WeightedRandomChestContent(Items.golden_boots, 0, 1, 1, 35),
        new WeightedRandomChestContent(Items.golden_leggings, 0, 1, 1, 35),
        new WeightedRandomChestContent(Items.golden_helmet, 0, 1, 1, 35),
        new WeightedRandomChestContent(Items.golden_chestplate, 0, 1, 1, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.yellow_flower), 0, 3, 10, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.red_flower), 0, 3, 10, 35),
        new WeightedRandomChestContent(Items.gold_nugget, 0, 3, 10, 35),
        new WeightedRandomChestContent(Items.gold_ingot, 0, 3, 10, 35),
        new WeightedRandomChestContent(Items.experience_bottle, 0, 4, 10, 35),
        new WeightedRandomChestContent(OreSpawnMain.MyCornCob, 0, 4, 10, 35),
        new WeightedRandomChestContent(OreSpawnMain.MyExperienceCatcher, 0, 4, 10, 25),
        new WeightedRandomChestContent(Items.bone, 0, 4, 10, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.gold_block), 0, 4, 10, 35) };
    private final WeightedRandomChestContent[] DamselContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 35),
        new WeightedRandomChestContent(Items.iron_sword, 0, 1, 1, 35),
        new WeightedRandomChestContent(Items.cooked_porkchop, 0, 3, 10, 35),
        new WeightedRandomChestContent(Items.beef, 0, 3, 10, 35),
        new WeightedRandomChestContent(Items.cooked_chicken, 0, 3, 10, 35),
        new WeightedRandomChestContent(Items.cooked_fished, 0, 3, 10, 35),
        new WeightedRandomChestContent(OreSpawnMain.MyBLT, 0, 4, 10, 35),
        new WeightedRandomChestContent(OreSpawnMain.MySalad, 0, 4, 10, 35),
        new WeightedRandomChestContent(OreSpawnMain.MyCornDog, 0, 4, 10, 35) };
    private final WeightedRandomChestContent[] EnderCastleContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.ender_chest), 0, 2, 4, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.diamond_block), 0, 2, 4, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.dragon_egg), 0, 1, 1, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(OreSpawnMain.MyEnderPearlBlock), 0, 3, 6, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(OreSpawnMain.MyEyeOfEnderBlock), 0, 3, 6, 35),
        new WeightedRandomChestContent(OreSpawnMain.MyExperienceCatcher, 0, 4, 10, 25),
        new WeightedRandomChestContent(Items.ender_pearl, 0, 2, 4, 35),
        new WeightedRandomChestContent(Items.ender_eye, 0, 2, 4, 35) };
    private final WeightedRandomChestContent[] BouncyContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(Items.rotten_flesh, 0, 6, 16, 35),
        new WeightedRandomChestContent(Items.fish, 0, 6, 16, 25),
        new WeightedRandomChestContent(Items.bone, 0, 6, 16, 25),
        new WeightedRandomChestContent(Items.string, 0, 6, 16, 25),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.red_flower), 0, 6, 16, 25),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.yellow_flower), 0, 6, 16, 25),
        new WeightedRandomChestContent(Items.ender_pearl, 0, 2, 4, 20) };
    private final WeightedRandomChestContent[] SpitBugContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(Items.rotten_flesh, 0, 6, 16, 35),
        new WeightedRandomChestContent(Items.fish, 0, 6, 16, 25),
        new WeightedRandomChestContent(Items.bone, 0, 6, 16, 25),
        new WeightedRandomChestContent(Items.string, 0, 6, 16, 25),
        new WeightedRandomChestContent(OreSpawnMain.MyAmethystPickaxe, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyAmethystShovel, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyAmethystHoe, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyAmethystAxe, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyAmethystSword, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.AmethystBody, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.AmethystLegs, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.AmethystHelmet, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.AmethystBoots, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.InstantGarden, 0, 2, 4, 25),
        new WeightedRandomChestContent(OreSpawnMain.InstantShelter, 0, 2, 4, 25) };
    private final WeightedRandomChestContent[] GraveContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(Items.ender_eye, 0, 6, 16, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.red_flower), 0, 6, 16, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.yellow_flower), 0, 6, 16, 35),
        new WeightedRandomChestContent(Items.ender_pearl, 0, 6, 16, 35) };
    private final WeightedRandomChestContent[] HospitalContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.ender_chest), 0, 2, 4, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.diamond_block), 0, 2, 4, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.dragon_egg), 0, 1, 1, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(OreSpawnMain.MyEnderPearlBlock), 0, 3, 6, 35),
        new WeightedRandomChestContent(Items.ender_pearl, 0, 2, 4, 35),
        new WeightedRandomChestContent(Items.ender_eye, 0, 2, 4, 35) };
    private final WeightedRandomChestContent[] MiniContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(Items.golden_apple, 0, 6, 16, 35),
        new WeightedRandomChestContent(OreSpawnMain.MyCrystalApple, 0, 6, 16, 35),
        new WeightedRandomChestContent(OreSpawnMain.MyBacon, 0, 6, 16, 35),
        new WeightedRandomChestContent(OreSpawnMain.MyFireFish, 0, 6, 16, 35),
        new WeightedRandomChestContent(OreSpawnMain.InstantGarden, 0, 2, 4, 25),
        new WeightedRandomChestContent(OreSpawnMain.InstantShelter, 0, 2, 4, 25) };
    private final WeightedRandomChestContent[] LeafMonsterContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(Items.flower_pot, 0, 6, 16, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.sapling), 0, 6, 16, 35),
        new WeightedRandomChestContent(Items.flower_pot, 0, 6, 16, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.sapling), 0, 6, 16, 35),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.leaves), 0, 6, 16, 25),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.dirt), 0, 6, 16, 25),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.log), 0, 6, 16, 25),
        new WeightedRandomChestContent(OreSpawnMain.MyPoisonSword, 0, 1, 1, 15),
        new WeightedRandomChestContent(Items.rotten_flesh, 0, 6, 16, 25) };
    private final WeightedRandomChestContent[] CloudSharkContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(Items.fish, 0, 6, 16, 25),
        new WeightedRandomChestContent(Items.bone, 0, 6, 16, 25),
        new WeightedRandomChestContent(Items.string, 0, 6, 16, 25),
        new WeightedRandomChestContent(Items.paper, 0, 6, 16, 25),
        new WeightedRandomChestContent(OreSpawnMain.MyExperienceTreeSeed, 0, 1, 2, 15),
        new WeightedRandomChestContent(Items.rotten_flesh, 0, 6, 16, 25) };
    private final WeightedRandomChestContent[] WaterDragonContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(Items.fish, 0, 6, 16, 25),
        new WeightedRandomChestContent(OreSpawnMain.MyUltimateAxe, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyUltimatePickaxe, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyUltimateShovel, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyExperienceCatcher, 0, 4, 10, 25),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.iron_block), 0, 6, 16, 25),
        new WeightedRandomChestContent(Items.rotten_flesh, 0, 6, 16, 25) };
    private final WeightedRandomChestContent[] SquidContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(Items.dye, 0, 6, 16, 25),
        new WeightedRandomChestContent(OreSpawnMain.MySquidZooka, 0, 1, 1, 15),
        new WeightedRandomChestContent(Items.gold_nugget, 0, 5, 15, 15),
        new WeightedRandomChestContent(Items.rotten_flesh, 0, 6, 16, 25) };
    private final WeightedRandomChestContent[] KnightContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(Items.paper, 0, 2, 8, 20),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.planks), 0, 4, 8, 20),
        new WeightedRandomChestContent(Items.ender_eye, 0, 2, 8, 15),
        new WeightedRandomChestContent(Items.ender_pearl, 0, 2, 8, 15),
        new WeightedRandomChestContent(Items.rotten_flesh, 0, 6, 16, 25) };
    private final WeightedRandomChestContent[] AlienWTFContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.diamond_block), 0, 1, 2, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyRuby, 0, 1, 1, 20),
        new WeightedRandomChestContent(OreSpawnMain.MyAmethyst, 0, 1, 1, 20),
        new WeightedRandomChestContent(OreSpawnMain.MyIngotUranium, 0, 1, 2, 5),
        new WeightedRandomChestContent(OreSpawnMain.MyIngotTitanium, 0, 1, 2, 5),
        new WeightedRandomChestContent(OreSpawnMain.UltimateHelmet, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.UltimateBody, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.UltimateLegs, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.UltimateBoots, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.MyUltimateBow, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyNightmareSword, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyExperienceCatcher, 0, 4, 10, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyRayGun, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.CageEmpty, 0, 1, 10, 20),
        new WeightedRandomChestContent(OreSpawnMain.MyCornDog, 0, 1, 10, 20),
        new WeightedRandomChestContent(OreSpawnMain.MyBacon, 0, 1, 5, 20),
        new WeightedRandomChestContent(OreSpawnMain.MyPopcornBag, 0, 2, 8, 20),
        new WeightedRandomChestContent(OreSpawnMain.MyFireFish, 0, 2, 8, 15) };
    private final WeightedRandomChestContent[] shadowContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(Items.glowstone_dust, 0, 2, 8, 20),
        new WeightedRandomChestContent(Items.nether_wart, 0, 4, 8, 20),
        new WeightedRandomChestContent(Items.blaze_rod, 0, 2, 8, 15),
        new WeightedRandomChestContent(Items.blaze_powder, 0, 2, 8, 15),
        new WeightedRandomChestContent(Items.fire_charge, 0, 4, 8, 15),
        new WeightedRandomChestContent(Items.rotten_flesh, 0, 6, 16, 25),
        new WeightedRandomChestContent(Items.dye, 0, 6, 16, 25),
        new WeightedRandomChestContent(OreSpawnMain.MyRuby, 0, 2, 8, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyExperienceTreeSeed, 0, 2, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyElevator, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyNightmareSword, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyPoisonSword, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyRatSword, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.MyRubySword, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.MyBigHammer, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MySquidZooka, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyIngotTitanium, 0, 1, 1, 5),
        new WeightedRandomChestContent(OreSpawnMain.MyIngotUranium, 0, 1, 1, 5),
        new WeightedRandomChestContent(OreSpawnMain.MyUltimateSword, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.MyUltimateBow, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.EnderReaperEgg, 0, 2, 8, 15),
        new WeightedRandomChestContent(OreSpawnMain.PitchBlackEgg, 0, 2, 8, 15) };
    private final WeightedRandomChestContent[] kyuubiContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(Items.redstone, 0, 2, 8, 10),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.redstone_block), 0, 4, 8, 15),
        new WeightedRandomChestContent(Items.quartz, 0, 2, 8, 15),
        new WeightedRandomChestContent(Items.coal, 0, 2, 8, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyNightmareSword, 0, 1, 1, 20),
        new WeightedRandomChestContent(OreSpawnMain.MyPoisonSword, 0, 1, 1, 20),
        new WeightedRandomChestContent(OreSpawnMain.KyuubiEgg, 0, 2, 8, 15) };
    private final WeightedRandomChestContent[] blazeContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(Items.blaze_rod, 0, 2, 8, 15),
        new WeightedRandomChestContent(Items.blaze_powder, 0, 2, 8, 15),
        new WeightedRandomChestContent(Items.fire_charge, 0, 4, 8, 15),
        new WeightedRandomChestContent(Items.flint_and_steel, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.LavaEelHelmet, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.LavaEelBody, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.LavaEelLegs, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.LavaEelBoots, 0, 1, 1, 15),
        new WeightedRandomChestContent(Items.spawn_egg, 61, 2, 8, 15) };
    private final WeightedRandomChestContent[] beeContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(Items.sugar, 0, 2, 8, 15),
        new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.yellow_flower), 0, 4, 8, 15),
        new WeightedRandomChestContent(Items.gold_nugget, 0, 5, 15, 15),
        new WeightedRandomChestContent(Items.paper, 0, 2, 8, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyFairySword, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.CrystalPinkHelmet, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.CrystalPinkBody, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.CrystalPinkLegs, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.CrystalPinkBoots, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.MyButterCandy, 0, 2, 8, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyExperienceCatcher, 0, 4, 10, 10),
        new WeightedRandomChestContent(OreSpawnMain.BeeEgg, 0, 2, 8, 15) };
    private final WeightedRandomChestContent[] mantisContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(OreSpawnMain.MyMantisClaw, 0, 1, 1, 10),
        new WeightedRandomChestContent(Items.gold_nugget, 0, 4, 8, 15),
        new WeightedRandomChestContent(OreSpawnMain.UraniumNugget, 0, 1, 3, 5),
        new WeightedRandomChestContent(OreSpawnMain.TitaniumNugget, 0, 1, 3, 5),
        new WeightedRandomChestContent(OreSpawnMain.MantisEgg, 0, 2, 4, 20),
        new WeightedRandomChestContent(OreSpawnMain.TigersEyeHelmet, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.TigersEyeBody, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.TigersEyeLegs, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.TigersEyeBoots, 0, 1, 1, 10),
        new WeightedRandomChestContent(Items.rotten_flesh, 0, 6, 16, 25),
        new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 15) };
    private final WeightedRandomChestContent[] level1ContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(Items.emerald, 0, 2, 8, 15),
        new WeightedRandomChestContent(OreSpawnMain.MinersDream, 0, 4, 8, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyEmeraldPickaxe, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyEmeraldShovel, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyEmeraldHoe, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyEmeraldAxe, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyEmeraldSword, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.EmeraldBody, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.EmeraldLegs, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.EmeraldHelmet, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.EmeraldBoots, 0, 1, 1, 15) };
    private final WeightedRandomChestContent[] level2ContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(Items.experience_bottle, 0, 2, 8, 15),
        new WeightedRandomChestContent(Items.experience_bottle, 0, 2, 8, 15),
        new WeightedRandomChestContent(OreSpawnMain.CreeperLauncher, 0, 2, 10, 15),
        new WeightedRandomChestContent(OreSpawnMain.CrystalPinkHelmet, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.CrystalPinkBody, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.CrystalPinkLegs, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.CrystalPinkBoots, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.MyFairySword, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyEmeraldPickaxe, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyEmeraldShovel, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyEmeraldHoe, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyEmeraldAxe, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyEmeraldSword, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.ExperienceBody, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.ExperienceLegs, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.ExperienceHelmet, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.ExperienceBoots, 0, 1, 1, 15) };
    private final WeightedRandomChestContent[] level3ContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(OreSpawnMain.MySquidZooka, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyRatSword, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyAmethyst, 0, 2, 8, 15),
        new WeightedRandomChestContent(Items.dye, 0, 2, 8, 15),
        new WeightedRandomChestContent(OreSpawnMain.TigersEyeHelmet, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.TigersEyeBody, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.TigersEyeLegs, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.TigersEyeBoots, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.MyAmethystPickaxe, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyAmethystShovel, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyAmethystHoe, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyAmethystAxe, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyAmethystSword, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.AmethystBody, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.AmethystLegs, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.AmethystHelmet, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.AmethystBoots, 0, 1, 1, 15) };
    private final WeightedRandomChestContent[] level4ContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(OreSpawnMain.MyRuby, 0, 2, 8, 15),
        new WeightedRandomChestContent(OreSpawnMain.MagicApple, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyRayGun, 0, 1, 1, 15),
        new WeightedRandomChestContent(Item.getItemFromBlock(OreSpawnMain.CreeperRepellent), 0, 4, 10, 15),
        new WeightedRandomChestContent(Item.getItemFromBlock(OreSpawnMain.KrakenRepellent), 0, 4, 10, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyExperienceCatcher, 0, 4, 10, 15),
        new WeightedRandomChestContent(OreSpawnMain.ZooKeeper, 0, 10, 16, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyRubyPickaxe, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyRubyShovel, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyRubyHoe, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyRubyAxe, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyRubySword, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyThunderStaff, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.RubyBody, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.RubyLegs, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.RubyHelmet, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.RubyBoots, 0, 1, 1, 15) };
    private final WeightedRandomChestContent[] level5ContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(OreSpawnMain.MyNightmareSword, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyPoisonSword, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.WitherSkeletonEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.EnderDragonEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.SnowGolemEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.IronGolemEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.WitherBossEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.RedCowEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.GoldCowEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.EnchantedCowEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.MOTHRAEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.AloEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.CryoEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.CamaEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.VeloEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.HydroEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.BasilEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.DragonflyEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.EmperorScorpionEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.ScorpionEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.CaveFisherEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.SpyroEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.BaryonyxEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.CockateilEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.GammaMetroidEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.KyuubiEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.AlienEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.AttackSquidEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.WaterDragonEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.CephadromeEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.KrakenEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.LizardEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.DragonEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.BeeEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.TrooperBugEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.SpitBugEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.StinkBugEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.OstrichEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.GazelleEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.ChipmunkEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.CreepingHorrorEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.TerribleTerrorEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.CliffRacerEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.TriffidEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.PitchBlackEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.LurkingTerrorEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.SmallWormEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.MediumWormEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.LargeWormEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.TRexEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.GodzillaEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.MantisEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.HerculesEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.VortexEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.RatEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.DungeonBeastEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.FairyEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.WhaleEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.SkateEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.IrukandjiEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.Robot1Egg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.Robot2Egg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.Robot3Egg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.Robot4Egg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.Robot5Egg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.CriminalEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.CoinEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.BoyfriendEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.EasterBunnyEgg, 0, 1, 4, 5),
        new WeightedRandomChestContent(OreSpawnMain.MolenoidEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.SeaMonsterEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.SeaViperEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.CaterKillerEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.LeonEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.HammerheadEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.RubberDuckyEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.NastysaurusEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.PointysaurusEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.BrutalflyEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.CricketEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.FrogEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.AntRobotKit, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.SpiderRobotKit, 0, 1, 1, 10),
        new WeightedRandomChestContent(OreSpawnMain.JefferyEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.SpiderDriverEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.CrabEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.CassowaryEgg, 0, 1, 4, 15) };
    private final WeightedRandomChestContent[] chestContentsList = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(OreSpawnMain.MyBacon, 0, 6, 12, 20),
        new WeightedRandomChestContent(OreSpawnMain.MyButterCandy, 0, 6, 12, 20),
        new WeightedRandomChestContent(Items.emerald, 0, 2, 8, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyEmeraldPickaxe, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyEmeraldShovel, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyEmeraldHoe, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyEmeraldAxe, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyEmeraldSword, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.EmeraldBody, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.EmeraldLegs, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.EmeraldHelmet, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.EmeraldBoots, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyMothScale, 0, 2, 8, 15),
        new WeightedRandomChestContent(OreSpawnMain.MothScaleBody, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MothScaleLegs, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MothScaleHelmet, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MothScaleBoots, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyLavaEel, 0, 2, 8, 15),
        new WeightedRandomChestContent(OreSpawnMain.LavaEelBody, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.LavaEelLegs, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.LavaEelHelmet, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.LavaEelBoots, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.ExperienceBody, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.ExperienceLegs, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.ExperienceHelmet, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.ExperienceBoots, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.MyExperienceSword, 0, 1, 1, 15),
        new WeightedRandomChestContent(OreSpawnMain.WitherSkeletonEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.EnderDragonEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.SnowGolemEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.IronGolemEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.WitherBossEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.RedCowEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.GoldCowEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.EnchantedCowEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.MOTHRAEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.AloEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.CryoEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.CamaEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.VeloEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.HydroEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.BasilEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.DragonflyEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.EmperorScorpionEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.ScorpionEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.CaveFisherEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.SpyroEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.BaryonyxEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.CockateilEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.GammaMetroidEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.KyuubiEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.AlienEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.AttackSquidEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.WaterDragonEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.CephadromeEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.KrakenEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.LizardEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.DragonEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.BeeEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.TrooperBugEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.SpitBugEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.StinkBugEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.OstrichEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.GazelleEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.ChipmunkEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.CreepingHorrorEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.TerribleTerrorEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.CliffRacerEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.TriffidEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.PitchBlackEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.LurkingTerrorEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.SmallWormEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.MediumWormEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.LargeWormEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.CassowaryEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.MolenoidEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.SeaMonsterEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.SeaViperEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.CaterKillerEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.LeonEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.HammerheadEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.RubberDuckyEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.NastysaurusEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.PointysaurusEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.BrutalflyEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.CricketEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.FrogEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.JefferyEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.SpiderDriverEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.CrabEgg, 0, 1, 4, 15),
        new WeightedRandomChestContent(OreSpawnMain.CageEmpty, 0, 3, 10, 20) };
    private int[] king = new int[] { -1, -1, 24, 3, -1, 24, 5, -1, 17, 12, -1, 16, 15, -1, 15, 14, -1, 15, 6, 3, 5, -1,
        14, 6, 4, 3, -1, 14, 5, -1, 14, 5, -1, 12, 9, -1, 11, 11, -1, 8, 17, -1, 5, 23, -1, 3, 27, -1, 2, 29, -1, 1, 31,
        -1, 0, 33, -1, 13, 6, -1, 12, 9, -1, 11, 3, 1, 2, 1, 4, -1, 10, 3, 2, 2, 3, 2, -1, 10, 2, 4, 2, 3, 2, -1, 9, 2,
        5, 2, 4, 6, -1, 9, 2, 5, 2, 6, 4, -1, 8, 2, 6, 1, -1, 8, 2, 5, 2, -1, 8, 2, 5, 2, -1, 8, 2, 5, 2, -1, 15, 2, -1,
        -1, -1 };
    private int[] queen = new int[] { -1, -1, 24, 3, -1, 24, 5, -1, 17, 12, -1, 16, 15, -1, 15, 14, -1, 15, 6, 3, 5, -1,
        14, 6, 4, 3, -1, 14, 5, -1, 14, 5, -1, 12, 9, -1, 11, 11, -1, 8, 17, -1, 5, 23, -1, 3, 27, -1, 2, 29, -1, 1, 31,
        -1, 0, 33, -1, 13, 6, -1, 12, 9, -1, 11, 3, 1, 2, 1, 4, -1, 10, 3, 2, 2, 3, 2, -1, 10, 2, 4, 2, 3, 2, -1, 9, 2,
        5, 2, 4, 6, -1, 9, 2, 5, 2, 6, 4, -1, 8, 2, 6, 1, -1, 8, 2, 5, 2, -1, 8, 2, 5, 2, -1, 8, 2, 5, 2, -1, 15, 2, -1,
        -1, -1 };
    private int[] blkcolors = new int[] { 14, 1, 4, 5, 3, 11, 10, 6 };

    private void setThisBlock(World world, int cposx, int cposy, int cposz) {
        if (world.rand.nextInt(2) == 1) {
            this.FastSetBlock(world, cposx, cposy, cposz, Blocks.mossy_cobblestone);
        } else {
            this.FastSetBlock(world, cposx, cposy, cposz, Blocks.cobblestone);
        }
    }

    private TileEntityChest getChestTileEntity(World world, int cposx, int cposy, int cposz) {
        TileEntityChest chest = null;
        TileEntity t = null;
        t = world.getTileEntity(cposx, cposy, cposz);
        return t != null && t instanceof TileEntityChest ? (TileEntityChest) t : null;
    }

    private TileEntityMobSpawner getSpawnerTileEntity(World world, int cposx, int cposy, int cposz) {
        TileEntityMobSpawner chest = null;
        TileEntity t = null;
        t = world.getTileEntity(cposx, cposy, cposz);
        return t != null && t instanceof TileEntityMobSpawner ? (TileEntityMobSpawner) t : null;
    }

    public void makeDungeon(World world, int cposx, int cposy, int cposz) {
        int width = 12;
        int height = 6;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < width; k++) {
                    this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.air);
                }
            }
        }

        for (int var12 = 0; var12 < width; var12++) {
            int j = 0;

            for (int k = 0; k < width; k++) {
                this.FastSetBlock(world, cposx + var12, cposy + j, cposz + k, Blocks.mossy_cobblestone);
            }
        }

        for (int var13 = 0; var13 < width; var13++) {
            int j = height - 1;

            for (int k = 0; k < width; k++) {
                this.setThisBlock(world, cposx + var13, cposy + j, cposz + k);
            }
        }

        for (int var14 = 0; var14 < width; var14++) {
            for (int j = 0; j < height; j++) {
                int k = 0;
                this.setThisBlock(world, cposx + var14, cposy + j, cposz + k);
                k = width - 1;
                this.setThisBlock(world, cposx + var14, cposy + j, cposz + k);
            }
        }

        for (int k = 0; k < width; k++) {
            for (int j = 0; j < height; j++) {
                int var15 = 0;
                this.setThisBlock(world, cposx + var15, cposy + j, cposz + k);
                var15 = width - 1;
                this.setThisBlock(world, cposx + var15, cposy + j, cposz + k);
            }
        }

        world.setBlock(cposx + width / 2, cposy + 1, cposz + width / 2, Blocks.mob_spawner, 0, 2);
        TileEntityMobSpawner tileentitymobspawner = this
            .getSpawnerTileEntity(world, cposx + width / 2, cposy + 1, cposz + width / 2);
        if (tileentitymobspawner != null) {
            int t = world.rand.nextInt(12);
            if (t == 0) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Scorpion");
            }

            if (t == 1) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Alien");
            }

            if (t == 2) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Cryolophosaurus");
            }

            if (t == 3) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("WTF?");
            }

            if (t == 4) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Kyuubi");
            }

            if (t == 5) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Bee");
            }

            if (t == 6) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Cloud Shark");
            }

            if (t == 7) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Lurking Terror");
            }

            if (t == 8) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Terrible Terror");
            }

            if (t == 9) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Rotator");
            }

            if (t == 10) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Rat");
            }

            if (t == 11) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Dungeon Beast");
            }
        }

        TileEntityChest chest = null;
        world.setBlock(cposx + width / 2, cposy + 1, cposz + 1, Blocks.chest, 0, 2);
        chest = this.getChestTileEntity(world, cposx + width / 2, cposy + 1, cposz + 1);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, this.chestContentsList, chest, 5 + world.rand.nextInt(7));
        }
    }

    public void FastSetBlock(World world, int ix, int iy, int iz, Block id) {
        OreSpawnMain.setBlockFast(world, ix, iy, iz, id, 0, 2);
    }

    public void makeEnormousCastle(World world, int cposx, int cposy, int cposz) {
        int width = 28;
        int height = 16;
        int platformwidth = 11;
        int level = 0;
        if (!world.isRemote) {
            level = 1 + world.rand.nextInt(6);
            if (level <= 3 && world.rand.nextInt(3) != 1) {
                level += 3;
            }

            for (int i = -20; i < width + 4; i++) {
                for (int j = 1; j < height + 10; j++) {
                    for (int k = -4; k < width + 4; k++) {
                        this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.air);
                    }
                }
            }

            for (int var15 = 0; var15 < width; var15++) {
                int j = 0;

                for (int k = 0; k < width; k++) {
                    this.FastSetBlock(world, cposx + var15, cposy + j, cposz + k, Blocks.stone);
                }
            }

            for (int var16 = 0; var16 < width; var16++) {
                int j = height;

                for (int k = 0; k < width; k++) {
                    this.FastSetBlock(world, cposx + var16, cposy + j, cposz + k, Blocks.bedrock);
                }
            }

            for (int var17 = 0; var17 < width; var17++) {
                for (int j = 1; j < height; j++) {
                    int k = 0;
                    this.FastSetBlock(world, cposx + var17, cposy + j, cposz + k, Blocks.iron_bars);
                    k = width - 1;
                    this.FastSetBlock(world, cposx + var17, cposy + j, cposz + k, Blocks.iron_bars);
                }
            }

            for (int k = 0; k < width; k++) {
                for (int j = 1; j < height; j++) {
                    int var18 = 0;
                    this.FastSetBlock(world, cposx + var18, cposy + j, cposz + k, Blocks.iron_bars);
                    var18 = width - 1;
                    this.FastSetBlock(world, cposx + var18, cposy + j, cposz + k, Blocks.iron_bars);
                }
            }

            world.setBlock(cposx + 1, cposy + 1, cposz + 1, OreSpawnMain.ExtremeTorch);
            world.setBlock(cposx + 1, cposy + 1, cposz + width - 2, OreSpawnMain.ExtremeTorch);
            world.setBlock(cposx + width - 2, cposy + 1, cposz + 1, OreSpawnMain.ExtremeTorch);
            world.setBlock(cposx + width - 2, cposy + 1, cposz + width - 2, OreSpawnMain.ExtremeTorch);

            for (int var20 = -4; var20 < width + 4; var20++) {
                for (int var46 = -4; var46 < width + 4; var46++) {
                    if (var20 < 0 || var46 < 0 || var20 >= width || var46 >= width) {
                        this.FastSetBlock(world, cposx + var20, cposy, cposz + var46, Blocks.stone);
                    }

                    if (var20 == -4 || var46 == -4 || var20 == width + 3 || var46 == width + 3) {
                        this.FastSetBlock(world, cposx + var20, cposy + 1, cposz + var46, Blocks.nether_brick_fence);
                    }
                }
            }

            TileEntityMobSpawner tileentitymobspawner = null;

            for (int j = 0; j < 4; j++) {
                world.setBlock(cposx - 3, cposy + 1 + j, cposz - 3, Blocks.mob_spawner, 0, 2);
                tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 3, cposy + 1 + j, cposz - 3);
                if (tileentitymobspawner != null) {
                    tileentitymobspawner.func_145881_a()
                        .setEntityName("Terrible Terror");
                }

                world.setBlock(cposx - 3, cposy + 1 + j, cposz + width + 2, Blocks.mob_spawner, 0, 2);
                tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 3, cposy + 1 + j, cposz + width + 2);
                if (tileentitymobspawner != null) {
                    tileentitymobspawner.func_145881_a()
                        .setEntityName("Terrible Terror");
                }

                world.setBlock(cposx + width + 2, cposy + 1 + j, cposz - 3, Blocks.mob_spawner, 0, 2);
                tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width + 2, cposy + 1 + j, cposz - 3);
                if (tileentitymobspawner != null) {
                    tileentitymobspawner.func_145881_a()
                        .setEntityName("Terrible Terror");
                }

                world.setBlock(cposx + width + 2, cposy + 1 + j, cposz + width + 2, Blocks.mob_spawner, 0, 2);
                tileentitymobspawner = this
                    .getSpawnerTileEntity(world, cposx + width + 2, cposy + 1 + j, cposz + width + 2);
                if (tileentitymobspawner != null) {
                    tileentitymobspawner.func_145881_a()
                        .setEntityName("Terrible Terror");
                }
            }

            world.setBlock(cposx + width / 2, cposy + 2, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Emperor Scorpion");
            }

            world.setBlock(cposx + width / 2, cposy + 3, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Emperor Scorpion");
            }

            world.setBlock(cposx + width / 2, cposy + 4, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 4, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Emperor Scorpion");
            }

            this.buildLevel(
                world,
                cposx + 1,
                cposy + height,
                cposz + 1,
                width - 2,
                10,
                4,
                "Cloud Shark",
                1,
                -1,
                5,
                1,
                level);
            int var31 = height + 10;
            if (level >= 2) {
                this.buildLevel(
                    world,
                    cposx + 1,
                    cposy + var31,
                    cposz + 1,
                    width - 2,
                    10,
                    4,
                    "Lurking Terror",
                    0,
                    0,
                    4,
                    2,
                    level);
            }

            var31 += 10;
            if (level >= 3) {
                this.buildLevel(
                    world,
                    cposx + 2,
                    cposy + var31,
                    cposz + 2,
                    width - 4,
                    9,
                    4,
                    "Rotator",
                    1,
                    1,
                    4,
                    3,
                    level);
            }

            var31 += 9;
            if (level >= 4) {
                this.buildLevel(world, cposx + 2, cposy + var31, cposz + 2, width - 4, 9, 3, "Bee", 0, 0, 4, 4, level);
            }

            var31 += 9;
            if (level >= 5) {
                this.buildLevel(
                    world,
                    cposx + 3,
                    cposy + var31,
                    cposz + 3,
                    width - 6,
                    8,
                    3,
                    "Mantis",
                    1,
                    1,
                    4,
                    5,
                    level);
            }

            var31 += 8;
            if (level >= 6) {
                this.buildLevel(
                    world,
                    cposx + 3,
                    cposy + var31,
                    cposz + 3,
                    width - 6,
                    16,
                    3,
                    "Mothra",
                    0,
                    0,
                    3,
                    6,
                    level);
            }

            var31 += 16;

            for (int var21 = 0; var21 < platformwidth; var21++) {
                var31 = height;

                for (int var47 = -(platformwidth / 2); var47 <= platformwidth / 2; var47++) {
                    this.FastSetBlock(
                        world,
                        cposx + var21 - 20,
                        cposy + var31,
                        cposz + var47 + width / 2,
                        Blocks.quartz_block);
                    if ((var21 == 0 || var21 == platformwidth - 1
                        || var47 == -(platformwidth / 2)
                        || var47 == platformwidth / 2) && (var21 != 0 || var47 < -1 || var47 > 1)) {
                        this.FastSetBlock(
                            world,
                            cposx + var21 - 20,
                            cposy + var31 + 1,
                            cposz + var47 + width / 2,
                            Blocks.nether_brick_fence);
                    }
                }
            }

            for (int var22 = -10; var22 <= -3; var22++) {
                var31 = height;

                for (int var48 = -2; var48 < 3; var48++) {
                    if (var22 != -3 && var22 != -10) {
                        this.FastSetBlock(
                            world,
                            cposx + var22,
                            cposy + var31,
                            cposz + var48 + width / 2,
                            Blocks.quartz_block);
                        if (var48 == -2 || var48 == 2) {
                            this.FastSetBlock(
                                world,
                                cposx + var22,
                                cposy + var31 + 1,
                                cposz + var48 + width / 2,
                                Blocks.nether_brick_fence);
                        }
                    } else if (var48 != -2 && var48 != 2) {
                        this.FastSetBlock(
                            world,
                            cposx + var22,
                            cposy + var31 + 1,
                            cposz + var48 + width / 2,
                            Blocks.air);
                    } else {
                        this.FastSetBlock(
                            world,
                            cposx + var22,
                            cposy + var31 + 1,
                            cposz + var48 + width / 2,
                            Blocks.netherrack);
                        this.FastSetBlock(
                            world,
                            cposx + var22,
                            cposy + var31 + 2,
                            cposz + var48 + width / 2,
                            Blocks.netherrack);
                        this.FastSetBlock(
                            world,
                            cposx + var22,
                            cposy + var31 + 3,
                            cposz + var48 + width / 2,
                            Blocks.fire);
                    }
                }
            }

            int var23 = -21;

            for (int var39 = height; var39 >= 0; var39--) {
                for (int var49 = -2; var49 < 3; var49++) {
                    for (int t = 0; t < 6; t++) {
                        this.FastSetBlock(
                            world,
                            cposx + var23,
                            cposy + var39 + t + 1,
                            cposz + var49 + width / 2,
                            Blocks.air);
                    }

                    if (var39 == 0) {
                        if (var49 != -2 && var49 != 2) {
                            this.FastSetBlock(
                                world,
                                cposx + var23,
                                cposy + var39 + 1,
                                cposz + var49 + width / 2,
                                Blocks.air);
                        } else {
                            this.FastSetBlock(
                                world,
                                cposx + var23,
                                cposy + var39 + 1,
                                cposz + var49 + width / 2,
                                Blocks.netherrack);
                            this.FastSetBlock(
                                world,
                                cposx + var23,
                                cposy + var39 + 2,
                                cposz + var49 + width / 2,
                                Blocks.netherrack);
                            this.FastSetBlock(
                                world,
                                cposx + var23,
                                cposy + var39 + 3,
                                cposz + var49 + width / 2,
                                Blocks.fire);
                        }
                    } else {
                        this.FastSetBlock(
                            world,
                            cposx + var23,
                            cposy + var39,
                            cposz + var49 + width / 2,
                            Blocks.quartz_block);
                        if (var49 == -2 || var49 == 2) {
                            this.FastSetBlock(
                                world,
                                cposx + var23,
                                cposy + var39 + 1,
                                cposz + var49 + width / 2,
                                Blocks.nether_brick_fence);
                        }
                    }
                }

                var23--;
            }

            if (level >= 6) {
                int span = width * 3;

                for (int tries = 0; tries < 100; tries++) {
                    int var40 = -1;
                    var23 = world.rand.nextInt(span);
                    int var50 = world.rand.nextInt(span);
                    if (var23 < span / 4 || var23 > span * 3 / 4 || var50 < span / 4 || var50 > span * 3 / 4) {
                        var23 -= span / 2;
                        var50 -= span / 2;
                        world.setBlock(
                            cposx + var23 + width / 2,
                            cposy + var40,
                            cposz + var50 + width / 2,
                            Blocks.mob_spawner,
                            0,
                            2);
                        tileentitymobspawner = this.getSpawnerTileEntity(
                            world,
                            cposx + var23 + width / 2,
                            cposy + var40,
                            cposz + var50 + width / 2);
                        if (tileentitymobspawner != null) {
                            tileentitymobspawner.func_145881_a()
                                .setEntityName("Large Worm");
                        }
                    }
                }
            }
        }
    }

    public void buildLevel(World world, int cposx, int cposy, int cposz, int width, int height, int pw, String critter,
        int stepside, int stepoff, int holelen, int decor, int level) {
        for (int i = -pw; i < width + pw; i++) {
            for (int j = 1; j < height; j++) {
                for (int k = -pw; k < width + pw; k++) {
                    this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.air);
                }
            }
        }

        for (int var19 = 0; var19 < width; var19++) {
            int j = 0;

            for (int k = 0; k < width; k++) {
                this.FastSetBlock(world, cposx + var19, cposy + j, cposz + k, Blocks.bedrock);
            }
        }

        for (int var20 = 0; var20 < width; var20++) {
            int j = height;

            for (int k = 0; k < width; k++) {
                this.FastSetBlock(world, cposx + var20, cposy + j, cposz + k, Blocks.bedrock);
            }
        }

        for (int var21 = 0; var21 < width; var21++) {
            for (int j = 1; j < height; j++) {
                int k = 0;
                this.FastSetBlock(world, cposx + var21, cposy + j, cposz + k, Blocks.bedrock);
                k = width - 1;
                this.FastSetBlock(world, cposx + var21, cposy + j, cposz + k, Blocks.bedrock);
            }
        }

        for (int k = 0; k < width; k++) {
            for (int j = 1; j < height; j++) {
                Block blk = Blocks.bedrock;
                if (k == 0 || k == width - 1) {
                    blk = Blocks.gold_block;
                }

                int var22 = 0;
                this.FastSetBlock(world, cposx + var22, cposy + j, cposz + k, blk);
                var22 = width - 1;
                this.FastSetBlock(world, cposx + var22, cposy + j, cposz + k, blk);
            }
        }

        for (int var24 = -pw; var24 < width + pw; var24++) {
            for (int var40 = -pw; var40 < width + pw; var40++) {
                if (var24 < 0 || var40 < 0 || var24 >= width || var40 >= width) {
                    this.FastSetBlock(world, cposx + var24, cposy, cposz + var40, Blocks.stone);
                }

                if (var24 == -pw || var40 == -pw || var24 == width + (pw - 1) || var40 == width + (pw - 1)) {
                    this.FastSetBlock(world, cposx + var24, cposy + 1, cposz + var40, Blocks.nether_brick_fence);
                }
            }
        }

        int var25 = -(height / 2);
        var25 += width / 2;

        for (int j = 1; j < height; j++) {
            if (stepside != 0) {
                int var41 = -1;
                this.FastSetBlock(world, cposx + var25, cposy + j, cposz + var41, Blocks.stone);
            } else {
                this.FastSetBlock(world, cposx + var25, cposy + j, cposz + width, Blocks.stone);
            }

            var25++;
        }

        if (stepoff >= 0) {
            int var43;
            if (stepside == 0) {
                int var42 = -1;
                var43 = var42 - stepoff;
            } else {
                var43 = width + stepoff;
            }

            var25 = width / 2;
            int var33 = 0;

            for (int l = 0; l < holelen; l++) {
                this.FastSetBlock(world, cposx + var25 + l, cposy + var33, cposz + var43, Blocks.air);
            }
        }

        TileEntityMobSpawner tileentitymobspawner = null;

        for (int var34 = 0; var34 < 4; var34++) {
            world.setBlock(cposx - (pw - 1), cposy + var34 + 1, cposz - (pw - 1), Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this
                .getSpawnerTileEntity(world, cposx - (pw - 1), cposy + var34 + 1, cposz - (pw - 1));
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName(critter);
            }

            world.setBlock(cposx - (pw - 1), cposy + var34 + 1, cposz + width + (pw - 2), Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this
                .getSpawnerTileEntity(world, cposx - (pw - 1), cposy + var34 + 1, cposz + width + (pw - 2));
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName(critter);
            }

            world.setBlock(cposx + width + (pw - 2), cposy + var34 + 1, cposz - (pw - 1), Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this
                .getSpawnerTileEntity(world, cposx + width + (pw - 2), cposy + var34 + 1, cposz - (pw - 1));
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName(critter);
            }

            world.setBlock(
                cposx + width + (pw - 2),
                cposy + var34 + 1,
                cposz + width + (pw - 2),
                Blocks.mob_spawner,
                0,
                2);
            tileentitymobspawner = this
                .getSpawnerTileEntity(world, cposx + width + (pw - 2), cposy + var34 + 1, cposz + width + (pw - 2));
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName(critter);
            }
        }

        this.addLevelDecorations(world, cposx, cposy, cposz, width, height, decor, level);
    }

    public void addLevelDecorations(World world, int cposx, int cposy, int cposz, int width, int height, int decor,
        int difficulty) {
        TileEntityMobSpawner tileentitymobspawner = null;
        int reward = 1;
        String critter = "Alosaurus";
        if (decor == 6) {
            this.FastSetBlock(world, cposx, cposy + height, cposz, Blocks.netherrack);
            this.FastSetBlock(world, cposx, cposy + height + 1, cposz, Blocks.fire);
            this.FastSetBlock(world, cposx, cposy + height, cposz + width - 1, Blocks.netherrack);
            this.FastSetBlock(world, cposx, cposy + height + 1, cposz + width - 1, Blocks.fire);
            this.FastSetBlock(world, cposx + width - 1, cposy + height, cposz, Blocks.netherrack);
            this.FastSetBlock(world, cposx + width - 1, cposy + height + 1, cposz, Blocks.fire);
            this.FastSetBlock(world, cposx + width - 1, cposy + height, cposz + width - 1, Blocks.netherrack);
            this.FastSetBlock(world, cposx + width - 1, cposy + height + 1, cposz + width - 1, Blocks.fire);
            this.FastSetBlock(world, cposx + width / 2, cposy + height, cposz + width / 2, Blocks.air);
            world.setBlock(cposx + width / 2 - 1, cposy + height + 2, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this
                .getSpawnerTileEntity(world, cposx + width / 2 - 1, cposy + height + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Nightmare");
            }

            world.setBlock(cposx + width / 2 + 1, cposy + height + 2, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this
                .getSpawnerTileEntity(world, cposx + width / 2 + 1, cposy + height + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Nightmare");
            }

            world.setBlock(cposx + width / 2, cposy + height + 2, cposz + width / 2 - 1, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this
                .getSpawnerTileEntity(world, cposx + width / 2, cposy + height + 2, cposz + width / 2 - 1);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Nightmare");
            }

            world.setBlock(cposx + width / 2, cposy + height + 2, cposz + width / 2 + 1, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this
                .getSpawnerTileEntity(world, cposx + width / 2, cposy + height + 2, cposz + width / 2 + 1);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Nightmare");
            }

            for (int i = 1; i < width - 1; i++) {
                for (int j = 1; j < 5; j++) {
                    for (int k = 1; k < width - 1; k++) {
                        this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.dirt);
                    }
                }
            }

            world.setBlock(cposx + width / 2, cposy + 2, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Large Worm");
            }

            world.setBlock(cposx + width / 2, cposy + 3, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Large Worm");
            }

            world.setBlock(cposx + width / 2, cposy + 4, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 4, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Large Worm");
            }

            for (int j = 0; j < 10; j++) {
                this.FastSetBlock(world, cposx + 1, cposy + j, cposz + 1, Blocks.air);
            }

            this.fill_chests(world, cposx, cposy + 4, cposz, width, height, decor, reward);
        }

        if (decor == 5) {
            if (difficulty == 5) {
                critter = "Alosaurus";
                reward = 1;
            }

            if (difficulty == 6) {
                critter = "T. Rex";
                reward = 2;
            }

            world.setBlock(cposx + width / 2, cposy + 2, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName(critter);
            }

            world.setBlock(cposx + width / 2, cposy + 3, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName(critter);
            }

            for (int j = 1; j < 5; j++) {
                this.FastSetBlock(world, cposx + width / 2 - 1, cposy + j, cposz + width / 2, Blocks.bedrock);
                this.FastSetBlock(world, cposx + width / 2 + 1, cposy + j, cposz + width / 2, Blocks.bedrock);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 - 1, Blocks.bedrock);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 + 1, Blocks.bedrock);
            }

            this.FastSetBlock(world, cposx + width - 2, cposy, cposz + width - 2, Blocks.air);
            this.FastSetBlock(world, cposx + 1, cposy + height, cposz + 1, Blocks.air);
            this.fill_chests(world, cposx, cposy, cposz, width, height, decor, reward);
        }

        if (decor == 4) {
            if (difficulty == 4) {
                critter = "Alosaurus";
                reward = 1;
            }

            if (difficulty == 5) {
                critter = "T. Rex";
                reward = 2;
            }

            if (difficulty == 6) {
                critter = "Basilisk";
                reward = 3;
            }

            world.setBlock(cposx + width / 2, cposy + 2, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName(critter);
            }

            world.setBlock(cposx + width / 2, cposy + 3, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName(critter);
            }

            for (int j = 1; j < 5; j++) {
                this.FastSetBlock(world, cposx + width / 2 - 1, cposy + j, cposz + width / 2, Blocks.bedrock);
                this.FastSetBlock(world, cposx + width / 2 + 1, cposy + j, cposz + width / 2, Blocks.bedrock);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 - 1, Blocks.bedrock);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 + 1, Blocks.bedrock);
            }

            this.FastSetBlock(world, cposx + 1, cposy, cposz + 1, Blocks.air);
            this.FastSetBlock(world, cposx + width - 2, cposy + height, cposz + width - 2, Blocks.air);
            this.fill_chests(world, cposx, cposy, cposz, width, height, decor, reward);
        }

        if (decor == 3) {
            if (difficulty == 3) {
                critter = "Alosaurus";
                reward = 1;
            }

            if (difficulty == 4) {
                critter = "T. Rex";
                reward = 2;
            }

            if (difficulty == 5) {
                critter = "Basilisk";
                reward = 3;
            }

            if (difficulty == 6) {
                critter = "Hercules Beetle";
                reward = 4;
            }

            world.setBlock(cposx + width / 2, cposy + 2, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName(critter);
            }

            world.setBlock(cposx + width / 2, cposy + 3, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName(critter);
            }

            for (int j = 1; j < 5; j++) {
                this.FastSetBlock(world, cposx + width / 2 - 1, cposy + j, cposz + width / 2, Blocks.bedrock);
                this.FastSetBlock(world, cposx + width / 2 + 1, cposy + j, cposz + width / 2, Blocks.bedrock);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 - 1, Blocks.bedrock);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 + 1, Blocks.bedrock);
            }

            this.FastSetBlock(world, cposx + width - 2, cposy, cposz + width - 2, Blocks.air);
            this.FastSetBlock(world, cposx + 1, cposy + height, cposz + 1, Blocks.air);
            this.fill_chests(world, cposx, cposy, cposz, width, height, decor, reward);
        }

        if (decor == 2) {
            if (difficulty == 2) {
                critter = "Alosaurus";
                reward = 1;
            }

            if (difficulty == 3) {
                critter = "T. Rex";
                reward = 2;
            }

            if (difficulty == 4) {
                critter = "Basilisk";
                reward = 3;
            }

            if (difficulty == 5) {
                critter = "Hercules Beetle";
                reward = 4;
            }

            if (difficulty == 6) {
                critter = "Jumpy Bug";
                reward = 5;
            }

            world.setBlock(cposx + width / 2, cposy + 2, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName(critter);
            }

            world.setBlock(cposx + width / 2, cposy + 3, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName(critter);
            }

            for (int j = 1; j < 5; j++) {
                this.FastSetBlock(world, cposx + width / 2 - 1, cposy + j, cposz + width / 2, Blocks.bedrock);
                this.FastSetBlock(world, cposx + width / 2 + 1, cposy + j, cposz + width / 2, Blocks.bedrock);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 - 1, Blocks.bedrock);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 + 1, Blocks.bedrock);
            }

            this.FastSetBlock(world, cposx + 1, cposy, cposz + 1, Blocks.air);
            this.FastSetBlock(world, cposx + width - 2, cposy + height, cposz + width - 2, Blocks.air);
            this.fill_chests(world, cposx, cposy, cposz, width, height, decor, reward);
        }

        if (decor == 1) {
            if (difficulty == 1) {
                critter = "Alosaurus";
            }

            if (difficulty == 2) {
                critter = "T. Rex";
            }

            if (difficulty == 3) {
                critter = "Basilisk";
            }

            if (difficulty == 4) {
                critter = "Hercules Beetle";
            }

            if (difficulty == 5) {
                critter = "Jumpy Bug";
            }

            if (difficulty == 6) {
                critter = "Hammerhead";
            }

            world.setBlock(cposx + width / 2, cposy + 2, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName(critter);
            }

            world.setBlock(cposx + width / 2, cposy + 3, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName(critter);
            }

            for (int j = 1; j < 5; j++) {
                this.FastSetBlock(world, cposx + width / 2 - 1, cposy + j, cposz + width / 2, Blocks.bedrock);
                this.FastSetBlock(world, cposx + width / 2 + 1, cposy + j, cposz + width / 2, Blocks.bedrock);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 - 1, Blocks.bedrock);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 + 1, Blocks.bedrock);
            }

            this.FastSetBlock(world, cposx + width / 2 - 1, cposy + 1, cposz + width / 2 - 1, OreSpawnMain.MyRTPBlock);
            this.FastSetBlock(world, cposx + width / 2 + 1, cposy + 1, cposz + width / 2 + 1, OreSpawnMain.MyRTPBlock);
            this.FastSetBlock(world, cposx + width / 2 + 1, cposy + 1, cposz + width / 2 - 1, OreSpawnMain.MyRTPBlock);
            this.FastSetBlock(world, cposx + width / 2 - 1, cposy + 1, cposz + width / 2 + 1, OreSpawnMain.MyRTPBlock);
            this.FastSetBlock(world, cposx + 1, cposy + height, cposz + 1, Blocks.air);
            this.fill_chests(world, cposx, cposy, cposz, width, height, decor, difficulty);
        }
    }

    private void fill_chests(World world, int cposx, int cposy, int cposz, int width, int height, int decor,
        int reward) {
        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        chestContents = this.level1ContentsList;
        if (reward == 2) {
            chestContents = this.level2ContentsList;
        }

        if (reward == 3) {
            chestContents = this.level3ContentsList;
        }

        if (reward == 4) {
            chestContents = this.level4ContentsList;
        }

        if (reward == 5) {
            chestContents = this.level5ContentsList;
        }

        world.setBlock(cposx + 1, cposy + 1, cposz + width / 2, Blocks.chest, 0, 2);
        world.setBlockMetadataWithNotify(cposx + 1, cposy + 1, cposz + width / 2, 5, 3);
        chest = this.getChestTileEntity(world, cposx + 1, cposy + 1, cposz + width / 2);
        if (chest != null) {
            if (reward == 6) {
                chest.setInventorySlotContents(1, new ItemStack(OreSpawnMain.ThePrinceEgg, 1, 0));
            } else {
                WeightedRandomChestContent
                    .generateChestContents(world.rand, chestContents, chest, 5 + world.rand.nextInt(7));
            }
        }

        world.setBlock(cposx + width - 2, cposy + 1, cposz + width / 2, Blocks.chest, 0, 2);
        world.setBlockMetadataWithNotify(cposx + width - 2, cposy + 1, cposz + width / 2, 4, 3);
        chest = this.getChestTileEntity(world, cposx + width - 2, cposy + 1, cposz + width / 2);
        if (chest != null) {
            if (reward == 6) {
                chest.setInventorySlotContents(1, new ItemStack(OreSpawnMain.RoyalHelmet, 1, 0));
                chest.setInventorySlotContents(2, new ItemStack(OreSpawnMain.RoyalBody, 1, 0));
            } else {
                WeightedRandomChestContent
                    .generateChestContents(world.rand, chestContents, chest, 5 + world.rand.nextInt(7));
            }
        }

        world.setBlock(cposx + width / 2, cposy + 1, cposz + 1, Blocks.chest, 0, 2);
        world.setBlockMetadataWithNotify(cposx + width / 2, cposy + 1, cposz + 1, 3, 3);
        chest = this.getChestTileEntity(world, cposx + width / 2, cposy + 1, cposz + 1);
        if (chest != null) {
            if (reward == 6) {
                chest.setInventorySlotContents(1, new ItemStack(OreSpawnMain.RoyalLegs, 1, 0));
                chest.setInventorySlotContents(2, new ItemStack(OreSpawnMain.RoyalBoots, 1, 0));
            } else {
                WeightedRandomChestContent
                    .generateChestContents(world.rand, chestContents, chest, 5 + world.rand.nextInt(7));
            }
        }

        world.setBlock(cposx + width / 2, cposy + 1, cposz + width - 2, Blocks.chest, 0, 2);
        world.setBlockMetadataWithNotify(cposx + width / 2, cposy + 1, cposz + width - 2, 2, 3);
        chest = this.getChestTileEntity(world, cposx + width / 2, cposy + 1, cposz + width - 2);
        if (chest != null) {
            if (reward == 6) {
                chest.setInventorySlotContents(1, new ItemStack(OreSpawnMain.MyRoyal, 1, 0));
            } else {
                WeightedRandomChestContent
                    .generateChestContents(world.rand, chestContents, chest, 5 + world.rand.nextInt(7));
            }
        }
    }

    public void makeRotatorStation(World world, int cposx, int cposy, int cposz) {
        TileEntityMobSpawner tileentitymobspawner = null;
        TileEntityChest chest = null;
        world.setBlock(cposx, cposy + 4, cposz, OreSpawnMain.CrystalStone, 0, 2);
        world.setBlock(cposx, cposy + 5, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 5, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Rotator");
        }

        world.setBlock(cposx, cposy + 6, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 6, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Rotator");
        }

        world.setBlock(cposx, cposy + 7, cposz, OreSpawnMain.CrystalStone, 0, 2);
        world.setBlock(cposx, cposy + 8, cposz, Blocks.chest, 0, 2);
        world.setBlockMetadataWithNotify(cposx, cposy + 8, cposz, 2, 3);
        chest = this.getChestTileEntity(world, cposx, cposy + 8, cposz);
        if (chest != null) {
            chest.setInventorySlotContents(1, new ItemStack(OreSpawnMain.RotatorEgg, 1 + world.rand.nextInt(5), 0));
            chest.setInventorySlotContents(2, new ItemStack(OreSpawnMain.CrystalCoal, 4 + world.rand.nextInt(16), 0));
            chest.setInventorySlotContents(3, new ItemStack(OreSpawnMain.CrystalCoal, 4 + world.rand.nextInt(16), 0));
        }
    }

    public void makeBeeHive(World world, int cposx, int cposy, int cposz) {
        int width = 10;
        int height = 30;
        if (!world.isRemote) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < 5; j++) {
                    for (int k = 0; k < width; k++) {
                        this.FastSetBlock(world, cposx + i, cposy - j, cposz + k, Blocks.air);
                    }
                }
            }

            for (int var12 = 0; var12 < width; var12++) {
                int j = height;

                for (int k = 0; k < width; k++) {
                    this.FastSetBlock(world, cposx + var12, cposy - j, cposz + k, Blocks.coal_ore);
                }
            }

            Block blk = Blocks.coal_ore;

            for (int var13 = 0; var13 < width; var13++) {
                for (int k = 0; k < width; k++) {
                    for (int j = 1; j < height; j++) {
                        if (k != 0 && var13 != 0 && k != width - 1 && var13 != width - 1) {
                            this.FastSetBlock(world, cposx + var13, cposy - j, cposz + k, Blocks.air);
                        } else {
                            blk = Blocks.coal_ore;
                            if ((j & 1) == 1) {
                                blk = Blocks.gold_ore;
                            }

                            this.FastSetBlock(world, cposx + var13, cposy - j, cposz + k, blk);
                        }
                    }
                }
            }

            TileEntityMobSpawner tileentitymobspawner = null;

            for (int jx = 0; jx < 4; jx++) {
                world.setBlock(
                    cposx + width / 2,
                    cposy - 2 - jx * (height / 4),
                    cposz + width / 2,
                    Blocks.mob_spawner,
                    0,
                    2);
                tileentitymobspawner = this
                    .getSpawnerTileEntity(world, cposx + width / 2, cposy - 2 - jx * (height / 4), cposz + width / 2);
                if (tileentitymobspawner != null) {
                    tileentitymobspawner.func_145881_a()
                        .setEntityName("Bee");
                }
            }

            this.fill_beehive_chests(world, cposx, cposy, cposz, width, height);
        }
    }

    private void fill_beehive_chests(World world, int cposx, int cposy, int cposz, int width, int height) {
        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        chestContents = this.beeContentsList;

        for (int j = 2; j < height - 1; j += 2) {
            world.setBlock(cposx + 1, cposy - j, cposz + width / 2, Blocks.chest, 0, 2);
            world.setBlockMetadataWithNotify(cposx + 1, cposy - j, cposz + width / 2, 5, 3);
            chest = this.getChestTileEntity(world, cposx + 1, cposy - j, cposz + width / 2);
            if (chest != null) {
                WeightedRandomChestContent
                    .generateChestContents(world.rand, chestContents, chest, 1 + world.rand.nextInt(5));
            }

            world.setBlock(cposx + width - 2, cposy - j, cposz + width / 2, Blocks.chest, 0, 2);
            world.setBlockMetadataWithNotify(cposx + width - 2, cposy - j, cposz + width / 2, 4, 3);
            chest = this.getChestTileEntity(world, cposx + width - 2, cposy - j, cposz + width / 2);
            if (chest != null) {
                WeightedRandomChestContent
                    .generateChestContents(world.rand, chestContents, chest, 1 + world.rand.nextInt(5));
            }

            world.setBlock(cposx + width / 2, cposy - j, cposz + 1, Blocks.chest, 0, 2);
            world.setBlockMetadataWithNotify(cposx + width / 2, cposy - j, cposz + 1, 3, 3);
            chest = this.getChestTileEntity(world, cposx + width / 2, cposy - j, cposz + 1);
            if (chest != null) {
                WeightedRandomChestContent
                    .generateChestContents(world.rand, chestContents, chest, 1 + world.rand.nextInt(5));
            }

            world.setBlock(cposx + width / 2, cposy - j, cposz + width - 2, Blocks.chest, 0, 2);
            world.setBlockMetadataWithNotify(cposx + width / 2, cposy - j, cposz + width - 2, 2, 3);
            chest = this.getChestTileEntity(world, cposx + width / 2, cposy - j, cposz + width - 2);
            if (chest != null) {
                WeightedRandomChestContent
                    .generateChestContents(world.rand, chestContents, chest, 1 + world.rand.nextInt(5));
            }
        }
    }

    public void makeHauntedHouse(World world, int cposx, int cposy, int cposz) {
        int deltax = 0;
        int deltaz = 0;
        int bid = 0;
        int dirx = 0;
        int dirz = 0;
        int stuffdir = 0;
        TileEntityMobSpawner tileentitymobspawner = null;
        int length = 3;
        int width = 3;
        int height = 3;
        int var22 = 1;
        int var28 = 2;
        int x = cposx;
        int z = cposz;
        int y = cposy;
        if (!world.isRemote) {
            for (int i = -width; i <= width; i++) {
                for (int j = -length; j <= length; j++) {
                    for (int k = 0; k <= height + 1; k++) {
                        if (k == height + 1) {
                            world.setBlock(x + i, y + k, z + j, Blocks.planks);
                        } else if (k == 0) {
                            world.setBlock(x + i, y + k, z + j, Blocks.cobblestone);
                        } else if (i != width && j != length && i != -width && j != -length) {
                            world.setBlock(x + i, y + k, z + j, Blocks.air);
                        } else if (k == height) {
                            world.setBlock(x + i, y + k, z + j, Blocks.glass);
                        } else if ((k == 1 || k == 2) && i == var22 * width && j == deltaz * length) {
                            world.setBlock(x + i, y + k, z + j, Blocks.air);
                        } else {
                            world.setBlock(x + i, y + k, z + j, Blocks.planks);
                        }
                    }
                }
            }

            int var23 = 2;
            int kx = 1;
            int j = length - 1;
            world.setBlock(x + var23 * var22 + j * deltaz, y + kx, z + var23 * deltaz + j * var22, Blocks.furnace);
            world.setBlockMetadataWithNotify(
                x + var23 * var22 + j * deltaz,
                y + kx,
                z + var23 * deltaz + j * var22,
                var28,
                3);
            var23 = 1;
            world.setBlock(
                x + var23 * var22 + j * deltaz,
                y + kx,
                z + var23 * deltaz + j * var22,
                Blocks.crafting_table);
            var23 = 0;
            world.setBlock(x + var23 * var22 + j * deltaz, y + kx, z + var23 * deltaz + j * var22, Blocks.chest);
            world.setBlockMetadataWithNotify(
                x + var23 * var22 + j * deltaz,
                y + kx,
                z + var23 * deltaz + j * var22,
                var28,
                3);
            TileEntityChest chest = this
                .getChestTileEntity(world, x + var23 * var22 + j * deltaz, y + kx, z + var23 * deltaz + j * var22);
            if (chest != null) {
                if (world.rand.nextInt(2) == 0) {
                    chest.setInventorySlotContents(0, new ItemStack(Items.compass));
                }

                if (world.rand.nextInt(2) == 0) {
                    chest.setInventorySlotContents(1, new ItemStack(Items.map));
                }

                if (world.rand.nextInt(2) == 0) {
                    chest.setInventorySlotContents(2, new ItemStack(Items.cooked_porkchop, 8));
                }

                if (world.rand.nextInt(2) == 0) {
                    chest.setInventorySlotContents(3, new ItemStack(Item.getItemFromBlock(Blocks.torch), 32));
                }

                if (world.rand.nextInt(2) == 0) {
                    chest.setInventorySlotContents(4, new ItemStack(Items.coal, 16));
                }

                if (world.rand.nextInt(2) == 0) {
                    chest.setInventorySlotContents(5, new ItemStack(Items.bed));
                }

                if (world.rand.nextInt(2) == 0) {
                    chest.setInventorySlotContents(6, new ItemStack(Items.bed));
                }

                if (world.rand.nextInt(2) == 0) {
                    chest.setInventorySlotContents(7, new ItemStack(Items.wooden_door));
                }

                if (world.rand.nextInt(2) == 0) {
                    chest.setInventorySlotContents(8, new ItemStack(Items.iron_pickaxe));
                }

                if (world.rand.nextInt(2) == 0) {
                    chest.setInventorySlotContents(9, new ItemStack(Items.iron_sword));
                }

                if (world.rand.nextInt(2) == 0) {
                    chest.setInventorySlotContents(10, new ItemStack(Items.iron_axe));
                }

                if (world.rand.nextInt(2) == 0) {
                    chest.setInventorySlotContents(11, new ItemStack(Items.bucket));
                }

                if (world.rand.nextInt(2) == 0) {
                    chest.setInventorySlotContents(12, new ItemStack(OreSpawnMain.MyOreSaltBlock, 4));
                }

                if (world.rand.nextInt(2) == 0) {
                    chest.setInventorySlotContents(13, new ItemStack(Blocks.chest));
                }
            }

            world.setBlock(cposx, cposy + 1, cposz, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 1, cposz);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Rat");
            }

            world.setBlock(cposx, cposy + 2, cposz, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 2, cposz);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Ghost");
            }

            world.setBlock(cposx, cposy + 3, cposz, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 3, cposz);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Ghost Pumpkin Skelly");
            }
        }
    }

    public void makeMantisHive(World world, int cposx, int cposy, int cposz) {
        int width = 13;
        TileEntityMobSpawner tileentitymobspawner = null;
        if (!world.isRemote) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < 20; j++) {
                    for (int k = 0; k < width; k++) {
                        this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.air);
                    }
                }
            }

            int yoff = 0;
            int zoff = 0;

            int xoff;
            for (xoff = 0; width > 0; width -= 2) {
                for (int var14 = 0; var14 < width; var14++) {
                    for (int k = 0; k < width; k++) {
                        if (k != 0 && k != width - 1 && var14 != 0 && var14 != width - 1) {
                            this.FastSetBlock(world, cposx + var14 + xoff, cposy - yoff, cposz + k + zoff, Blocks.air);
                        } else {
                            Block blk = Blocks.gold_ore;
                            if ((yoff & 1) != 0) {
                                blk = Blocks.emerald_ore;
                            }

                            this.FastSetBlock(world, cposx + var14 + xoff, cposy - yoff, cposz + k + zoff, blk);
                        }
                    }
                }

                if (width <= 11 && width >= 7) {
                    this.fill_mantishive_chests(world, cposx + xoff, cposy - yoff, cposz + zoff, width, 0);
                }

                xoff++;
                zoff++;
                yoff++;
            }

            xoff--;
            zoff--;
            yoff--;

            for (int j = 4; j < 7; j++) {
                world.setBlock(cposx + xoff, cposy + j - yoff, cposz + yoff, Blocks.mob_spawner, 0, 2);
                tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + xoff, cposy + j - yoff, cposz + yoff);
                if (tileentitymobspawner != null) {
                    tileentitymobspawner.func_145881_a()
                        .setEntityName("Mantis");
                }
            }
        }
    }

    private void fill_mantishive_chests(World world, int cposx, int cposy, int cposz, int width, int height) {
        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        chestContents = this.mantisContentsList;
        world.setBlock(cposx + 1, cposy + height, cposz + width / 2, Blocks.chest, 0, 2);
        world.setBlockMetadataWithNotify(cposx + 1, cposy + height, cposz + width / 2, 5, 3);
        chest = this.getChestTileEntity(world, cposx + 1, cposy + height, cposz + width / 2);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 3 + world.rand.nextInt(7));
        }

        world.setBlock(cposx + width - 2, cposy + height, cposz + width / 2, Blocks.chest, 0, 2);
        world.setBlockMetadataWithNotify(cposx + width - 2, cposy + height, cposz + width / 2, 4, 3);
        chest = this.getChestTileEntity(world, cposx + width - 2, cposy + height, cposz + width / 2);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 3 + world.rand.nextInt(7));
        }

        world.setBlock(cposx + width / 2, cposy + height, cposz + 1, Blocks.chest, 0, 2);
        world.setBlockMetadataWithNotify(cposx + width / 2, cposy + height, cposz + 1, 3, 3);
        chest = this.getChestTileEntity(world, cposx + width / 2, cposy + height, cposz + 1);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 3 + world.rand.nextInt(7));
        }

        world.setBlock(cposx + width / 2, cposy + height, cposz + width - 2, Blocks.chest, 0, 2);
        world.setBlockMetadataWithNotify(cposx + width / 2, cposy + height, cposz + width - 2, 2, 3);
        chest = this.getChestTileEntity(world, cposx + width / 2, cposy + height, cposz + width - 2);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 3 + world.rand.nextInt(7));
        }
    }

    public void makeKyuubiDungeon(World world, int cposx, int cposy, int cposz) {
        int width = 5;
        int height = 5;
        int depth = 20;
        int length = 12;
        int rwidth = 30;
        int rheight = 18;
        int rlength = 20;
        if (!world.isRemote) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < 5; j++) {
                    for (int k = 0; k < width; k++) {
                        this.FastSetBlock(world, cposx + i, cposy - j, cposz + k, Blocks.air);
                    }
                }
            }

            int j = height;

            for (int var19 = 0; var19 < width; var19++) {
                for (int k = 0; k < width; k++) {
                    this.FastSetBlock(world, cposx + var19, cposy + j, cposz + k, Blocks.sandstone);
                }
            }

            this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2, Blocks.air);
            Block blk = Blocks.sandstone;

            for (int var20 = 0; var20 < width; var20++) {
                for (int k = 0; k < width; k++) {
                    for (int var27 = 0; var27 < height; var27++) {
                        if (k != 0 && k != width - 1 && var20 != 0 && var20 != width - 1) {
                            this.FastSetBlock(world, cposx + var20, cposy + var27, cposz + k, Blocks.air);
                        } else {
                            this.FastSetBlock(world, cposx + var20, cposy + var27, cposz + k, blk);
                        }
                    }
                }
            }

            blk = Blocks.stone;

            for (int var21 = 0; var21 < width; var21++) {
                for (int k = 0; k < width; k++) {
                    for (int var28 = -1; var28 > -depth; var28--) {
                        if (k != 0 && k != width - 1 && var21 != 0 && var21 != width - 1) {
                            this.FastSetBlock(world, cposx + var21, cposy + var28, cposz + k, Blocks.air);
                        } else {
                            this.FastSetBlock(world, cposx + var21, cposy + var28, cposz + k, blk);
                        }
                    }
                }
            }

            for (int var22 = 1; var22 < width - 1; var22++) {
                for (int k = 1; k < width - 1; k++) {
                    for (int var29 = -depth; var29 > -(depth + 2); var29--) {
                        this.FastSetBlock(world, cposx + var22, cposy + var29, cposz + k, Blocks.water);
                    }
                }
            }

            for (int var23 = 1; var23 < width - 1; var23++) {
                for (int k = 1; k < width - 1; k++) {
                    j = -(depth + 2);
                    this.FastSetBlock(world, cposx + var23, cposy + j, cposz + k, Blocks.stone);
                }
            }

            int x = cposx + width + length - 2;
            int z = cposz - rwidth / 2;
            int y = cposy - depth;
            blk = Blocks.netherrack;

            for (int var24 = 0; var24 < rlength; var24++) {
                for (int k = 0; k < rwidth; k++) {
                    for (int var31 = 0; var31 < rheight; var31++) {
                        if (k != 0 && k != rwidth - 1
                            && var31 != 0
                            && var31 != rheight - 1
                            && var24 != 0
                            && var24 != rlength - 1) {
                            this.FastSetBlock(world, x + var24, y + var31, z + k, Blocks.air);
                        } else {
                            this.FastSetBlock(world, x + var24, y + var31, z + k, blk);
                        }
                    }
                }
            }

            x = cposx + width - 1;
            z = cposz;
            y = cposy - depth;

            for (int var25 = 0; var25 < length; var25++) {
                for (int k = 0; k < width; k++) {
                    for (int var32 = 0; var32 < width; var32++) {
                        if (k != 0 && k != width - 1 && var32 != 0 && var32 != width - 1) {
                            this.FastSetBlock(world, x + var25, y + var32, z + k, Blocks.air);
                        } else {
                            blk = Blocks.stone;
                            if (var32 > 0 && var32 < width - 1) {
                                blk = Blocks.lava;
                            }

                            this.FastSetBlock(world, x + var25, y + var32, z + k, blk);
                        }
                    }
                }
            }

            x = cposx + width + length - 2;
            z = cposz - rwidth / 2;
            y = cposy - depth;
            this.addlavasquare(world, x + 2, ++y, z + 2);
            this.addlavasquare(world, x + 4, y, z + 6);
            this.addlavasquare(world, x + 12, y, z + 10);
            this.addlavasquare(world, x + 6, y, z + 15);
            this.addlavasquare(world, x + 3, y, z + 22);
            this.addkyuubi(world, x + rlength / 4, y, z + rwidth * 3 / 4 - 3);
            this.addblaze(world, x + rlength * 2 / 3 - 3, y, z + rwidth / 4 - 2);
            this.FastSetBlock(world, x + 7, y, z + 1, Blocks.fire);
            this.FastSetBlock(world, x + 5, y, z + 9, Blocks.fire);
            this.FastSetBlock(world, x + 2, y, z + 12, Blocks.fire);
            this.FastSetBlock(world, x + 16, y, z + 18, Blocks.fire);
            this.FastSetBlock(world, x + 2, y, z + 27, Blocks.fire);
            this.FastSetBlock(world, x + 18, y, z + 28, Blocks.fire);
        }
    }

    private void addlavasquare(World world, int x, int y, int z) {
        this.FastSetBlock(world, x - 1, y, z, Blocks.netherrack);
        this.FastSetBlock(world, x + 1, y, z, Blocks.netherrack);
        this.FastSetBlock(world, x, y, z + 1, Blocks.netherrack);
        this.FastSetBlock(world, x, y, z - 1, Blocks.netherrack);
        this.FastSetBlock(world, x, y, z, Blocks.lava);
    }

    private void addkyuubi(World world, int x, int y, int z) {
        int width = 9;
        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        TileEntityMobSpawner tileentitymobspawner = null;
        chestContents = this.kyuubiContentsList;

        for (int i = 0; i < width; i++) {
            for (int k = 0; k < width; k++) {
                if (k != 0 && k != width - 1 && i != 0 && i != width - 1) {
                    this.FastSetBlock(world, x + i, y, z + k, Blocks.lava);
                } else {
                    this.FastSetBlock(world, x + i, y, z + k, Blocks.nether_brick);
                }
            }
        }

        int var12 = 7;

        for (int var13 = 0; var13 < var12; var13++) {
            for (int kx = 0; kx < var12; kx++) {
                if (kx != 0 && kx != var12 - 1 && var13 != 0 && var13 != var12 - 1) {
                    this.FastSetBlock(world, x + var13 + 1, y + 1, z + kx + 1, Blocks.lava);
                } else {
                    this.FastSetBlock(world, x + var13 + 1, y + 1, z + kx + 1, Blocks.nether_brick);
                }
            }
        }

        for (int j = 0; j < 3; j++) {
            world.setBlock(x + 4, y + j + 2, z + 4, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, x + 4, y + j + 2, z + 4);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Kyuubi");
            }
        }

        world.setBlock(x + 4, y + 5, z + 4, Blocks.chest, 0, 2);
        world.setBlockMetadataWithNotify(x + 4, y + 5, z + 4, 2, 3);
        chest = this.getChestTileEntity(world, x + 4, y + 5, z + 4);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 7 + world.rand.nextInt(7));
        }
    }

    private void addblaze(World world, int x, int y, int z) {
        int width = 7;
        int height = 4;
        int xx = x;
        int yy = y;
        int zz = z;
        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        TileEntityMobSpawner tileentitymobspawner = null;
        chestContents = this.blazeContentsList;

        for (int i = 0; i < width; i++) {
            for (int k = 0; k < width; k++) {
                for (int j = 0; j < height; j++) {
                    this.FastSetBlock(world, xx + i, yy + j, zz + k, Blocks.obsidian);
                }
            }
        }

        xx++;
        yy += height;
        zz++;
        int var16 = 5;
        int var19 = 1;

        for (int var22 = 0; var22 < var16; var22++) {
            for (int k = 0; k < var16; k++) {
                for (int j = 0; j < var19; j++) {
                    this.FastSetBlock(world, xx + var22, yy + j, zz + k, Blocks.obsidian);
                }
            }
        }

        xx++;
        yy += var19;
        zz++;
        var16 = 3;
        var19 = 6;

        for (int var23 = 0; var23 < var16; var23++) {
            for (int k = 0; k < var16; k++) {
                for (int j = 0; j < var19; j++) {
                    this.FastSetBlock(world, xx + var23, yy + j, zz + k, Blocks.obsidian);
                }
            }
        }

        xx++;
        yy += var19;
        zz++;
        var16 = 1;
        var19 = 5;

        for (int var24 = 0; var24 < var16; var24++) {
            for (int k = 0; k < var16; k++) {
                for (int j = 0; j < var19; j++) {
                    this.FastSetBlock(world, xx + var24, yy + j, zz + k, Blocks.obsidian);
                }
            }
        }

        for (int j = 0; j < 2; j++) {
            world.setBlock(xx - 1, yy + var19 + j - 3, zz, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, xx - 1, yy + var19 + j - 3, zz);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Blaze");
            }

            world.setBlock(xx + 1, yy + var19 + j - 3, zz, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, xx + 1, yy + var19 + j - 3, zz);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Blaze");
            }

            world.setBlock(xx, yy + var19 + j - 3, zz - 1, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, xx, yy + var19 + j - 3, zz - 1);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Blaze");
            }

            world.setBlock(xx, yy + var19 + j - 3, zz + 1, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, xx, yy + var19 + j - 3, zz + 1);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Blaze");
            }
        }

        world.setBlock(x, y + 4, z + 3, Blocks.chest, 0, 2);
        world.setBlockMetadataWithNotify(x, y + 4, z + 3, 4, 3);
        chest = this.getChestTileEntity(world, x, y + 4, z + 3);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 4 + world.rand.nextInt(5));
        }

        world.setBlock(x + 3, y + 4, z, Blocks.chest, 0, 2);
        world.setBlockMetadataWithNotify(x + 3, y + 4, z, 2, 3);
        chest = this.getChestTileEntity(world, x + 3, y + 4, z);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 3 + world.rand.nextInt(5));
        }

        world.setBlock(x + 3, y + 4, z + 6, Blocks.chest, 0, 2);
        world.setBlockMetadataWithNotify(x + 3, y + 4, z + 6, 3, 3);
        chest = this.getChestTileEntity(world, x + 3, y + 4, z + 6);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 5 + world.rand.nextInt(5));
        }

        world.setBlock(x + 6, y + 4, z + 3, Blocks.chest, 0, 2);
        world.setBlockMetadataWithNotify(x + 6, y + 4, z + 3, 5, 3);
        chest = this.getChestTileEntity(world, x + 6, y + 4, z + 3);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 6 + world.rand.nextInt(5));
        }
    }

    public void makeSmallBeeHive(World world, int cposx, int cposy, int cposz) {
        int width = 7;
        int height = 21;
        TileEntityMobSpawner tileentitymobspawner = null;
        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        if (!world.isRemote) {
            for (int i = -3; i < width + 3; i++) {
                for (int j = height * 2 / 3; j < height; j++) {
                    for (int k = -3; k < width + 3; k++) {
                        this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.air);
                    }
                }
            }

            for (int var14 = 0; var14 < width; var14++) {
                for (int k = 0; k < width; k++) {
                    int j = height * 2 / 3;
                    this.FastSetBlock(world, cposx + var14, cposy + j, cposz + k, Blocks.sponge);
                    int blk = world.rand.nextInt(height / 3);
                    blk *= 2;
                    blk -= Math.abs(var14 - width / 2);
                    blk -= Math.abs(k - width / 2);
                    if (blk < 1) {
                        blk = 1;
                    }

                    if (var14 == width / 2 && k == width / 2) {
                        blk = height * 2 / 3;
                    }

                    for (int var20 = 0; var20 < blk; var20++) {
                        this.FastSetBlock(
                            world,
                            cposx + var14,
                            cposy + height * 2 / 3 - var20,
                            cposz + k,
                            Blocks.mossy_cobblestone);
                    }
                }
            }

            int jx = height * 2 / 3;

            for (int blkx = 0; blkx < height / 6; blkx++) {
                jx++;

                for (int var15 = 0; var15 < width; var15++) {
                    for (int k = 0; k < width; k++) {
                        if (k != 0 && var15 != 0 && k != width - 1 && var15 != width - 1) {
                            this.FastSetBlock(world, cposx + var15, cposy + jx, cposz + k, Blocks.air);
                        } else {
                            this.FastSetBlock(world, cposx + var15, cposy + jx, cposz + k, Blocks.sponge);
                        }
                    }
                }

                jx++;

                for (int var16 = -1; var16 < width + 1; var16++) {
                    for (int kx = -1; kx < width + 1; kx++) {
                        if (kx != -1 && var16 != -1 && kx != width && var16 != width) {
                            this.FastSetBlock(world, cposx + var16, cposy + jx, cposz + kx, Blocks.air);
                        } else {
                            this.FastSetBlock(world, cposx + var16, cposy + jx, cposz + kx, Blocks.sponge);
                        }
                    }
                }
            }

            jx++;

            for (int var17 = 0; var17 < width; var17++) {
                for (int kxx = 0; kxx < width; kxx++) {
                    this.FastSetBlock(world, cposx + var17, cposy + jx, cposz + kxx, Blocks.sponge);
                }
            }

            jx = height * 2 / 3 + 1;

            for (int var18 = -1; var18 < 1; var18++) {
                for (int kxx = 2; kxx < 4; kxx++) {
                    this.FastSetBlock(world, cposx + var18, cposy + jx, cposz + kxx, Blocks.air);
                    this.FastSetBlock(world, cposx + var18, cposy + jx + 1, cposz + kxx, Blocks.air);
                    this.FastSetBlock(world, cposx + var18, cposy + jx + 2, cposz + kxx, Blocks.air);
                }
            }

            for (int var34 = 0; var34 < 3; var34++) {
                world.setBlock(cposx + 1, cposy + var34 + jx, cposz + 1, Blocks.mob_spawner, 0, 2);
                tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 1, cposy + var34 + jx, cposz + 1);
                if (tileentitymobspawner != null) {
                    tileentitymobspawner.func_145881_a()
                        .setEntityName("Bee");
                }
            }

            chestContents = this.beeContentsList;
            world.setBlock(cposx + width / 2, cposy + jx, cposz + width / 2, Blocks.chest, 0, 2);
            world.setBlockMetadataWithNotify(cposx + width / 2, cposy + jx, cposz + width / 2, 5, 3);
            chest = this.getChestTileEntity(world, cposx + width / 2, cposy + jx, cposz + width / 2);
            if (chest != null) {
                WeightedRandomChestContent
                    .generateChestContents(world.rand, chestContents, chest, 7 + world.rand.nextInt(5));
            }
        }
    }

    public void makeShadowDungeon(World world, int cposx, int cposy, int cposz) {
        int totalwidth = 19;
        TileEntityMobSpawner tileentitymobspawner = null;
        String whichmob = null;
        if (!world.isRemote) {
            int yoff = 0;
            int zoff = 0;
            int xoff = 0;

            for (int width = totalwidth; width > 0; width -= 2) {
                for (int i = 0; i < width; i++) {
                    for (int k = 0; k < width; k++) {
                        if (k != 0 && k != width - 1 && i != 0 && i != width - 1) {
                            this.FastSetBlock(world, cposx + i + xoff, cposy - yoff, cposz + k + zoff, Blocks.air);
                        } else {
                            Block blk = Blocks.obsidian;
                            if ((yoff & 1) != 0) {
                                blk = Blocks.bedrock;
                            }

                            if (k >= width / 2 - 1 && k <= width / 2 + 1 || i >= width / 2 - 1 && i <= width / 2 + 1) {
                                blk = Blocks.soul_sand;
                            }

                            this.FastSetBlock(world, cposx + i + xoff, cposy - yoff, cposz + k + zoff, blk);
                        }
                    }
                }

                if (width <= 15 && width >= 9) {
                    if ((yoff & 1) != 0) {
                        this.fill_shadow_chests(world, cposx + xoff, cposy - yoff, cposz + zoff, width, 0);
                        whichmob = "Ender Reaper";
                    } else {
                        whichmob = "Nightmare";
                    }

                    world.setBlock(cposx + xoff + 1, cposy - yoff, cposz + zoff + 1, Blocks.mob_spawner, 0, 2);
                    tileentitymobspawner = this
                        .getSpawnerTileEntity(world, cposx + xoff + 1, cposy - yoff, cposz + zoff + 1);
                    if (tileentitymobspawner != null) {
                        tileentitymobspawner.func_145881_a()
                            .setEntityName(whichmob);
                    }

                    world.setBlock(cposx + xoff + width - 2, cposy - yoff, cposz + zoff + 1, Blocks.mob_spawner, 0, 2);
                    tileentitymobspawner = this
                        .getSpawnerTileEntity(world, cposx + xoff + width - 2, cposy - yoff, cposz + zoff + 1);
                    if (tileentitymobspawner != null) {
                        tileentitymobspawner.func_145881_a()
                            .setEntityName(whichmob);
                    }

                    world.setBlock(cposx + xoff + 1, cposy - yoff, cposz + zoff + width - 2, Blocks.mob_spawner, 0, 2);
                    tileentitymobspawner = this
                        .getSpawnerTileEntity(world, cposx + xoff + 1, cposy - yoff, cposz + zoff + width - 2);
                    if (tileentitymobspawner != null) {
                        tileentitymobspawner.func_145881_a()
                            .setEntityName(whichmob);
                    }

                    world.setBlock(
                        cposx + xoff + width - 2,
                        cposy - yoff,
                        cposz + zoff + width - 2,
                        Blocks.mob_spawner,
                        0,
                        2);
                    tileentitymobspawner = this
                        .getSpawnerTileEntity(world, cposx + xoff + width - 2, cposy - yoff, cposz + zoff + width - 2);
                    if (tileentitymobspawner != null) {
                        tileentitymobspawner.func_145881_a()
                            .setEntityName(whichmob);
                    }
                }

                xoff++;
                zoff++;
                yoff++;
            }

            yoff = 0;
            zoff = 0;
            xoff = 0;

            for (int var18 = totalwidth; var18 > 0; var18 -= 2) {
                for (int i = 0; i < var18; i++) {
                    for (int kx = 0; kx < var18; kx++) {
                        if (kx != 0 && kx != var18 - 1 && i != 0 && i != var18 - 1) {
                            this.FastSetBlock(world, cposx + i + xoff, cposy + yoff, cposz + kx + zoff, Blocks.air);
                        } else {
                            Block blkx = Blocks.obsidian;
                            if ((yoff & 1) != 0) {
                                blkx = Blocks.bedrock;
                            }

                            this.FastSetBlock(world, cposx + i + xoff, cposy + yoff, cposz + kx + zoff, blkx);
                        }
                    }
                }

                xoff++;
                zoff++;
                yoff++;
            }
        }
    }

    private void fill_shadow_chests(World world, int cposx, int cposy, int cposz, int width, int height) {
        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        chestContents = this.shadowContentsList;
        world.setBlock(cposx + 1, cposy + height, cposz + width / 2, Blocks.chest, 0, 2);
        world.setBlockMetadataWithNotify(cposx + 1, cposy + height, cposz + width / 2, 5, 3);
        chest = this.getChestTileEntity(world, cposx + 1, cposy + height, cposz + width / 2);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 3 + world.rand.nextInt(7));
        }

        world.setBlock(cposx + width - 2, cposy + height, cposz + width / 2, Blocks.chest, 0, 2);
        world.setBlockMetadataWithNotify(cposx + width - 2, cposy + height, cposz + width / 2, 4, 3);
        chest = this.getChestTileEntity(world, cposx + width - 2, cposy + height, cposz + width / 2);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 3 + world.rand.nextInt(7));
        }

        world.setBlock(cposx + width / 2, cposy + height, cposz + 1, Blocks.chest, 0, 2);
        world.setBlockMetadataWithNotify(cposx + width / 2, cposy + height, cposz + 1, 3, 3);
        chest = this.getChestTileEntity(world, cposx + width / 2, cposy + height, cposz + 1);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 3 + world.rand.nextInt(7));
        }

        world.setBlock(cposx + width / 2, cposy + height, cposz + width - 2, Blocks.chest, 0, 2);
        world.setBlockMetadataWithNotify(cposx + width / 2, cposy + height, cposz + width - 2, 2, 3);
        chest = this.getChestTileEntity(world, cposx + width / 2, cposy + height, cposz + width - 2);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 3 + world.rand.nextInt(7));
        }
    }

    public void makeAlienWTFDungeon(World world, int cposx, int cposy, int cposz) {
        int width = 5;
        int height = 5;
        int xwidth = 3;
        int zwidth = 6;
        int depth = 20;
        cposy -= depth - 3;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < width; k++) {
                    if (i != 0 && j != 0 && k != 0 && i != width - 1 && j != height - 1 && k != width - 1) {
                        this.FastSetBlock(world, cposx + i - 2, cposy + j, cposz + k - 2, Blocks.air);
                    } else {
                        this.FastSetBlock(world, cposx + i - 2, cposy + j, cposz + k - 2, Blocks.lapis_ore);
                    }
                }
            }
        }

        int s = 0;
        cposx--;
        cposz--;

        for (int j = 3; j < depth; j++) {
            for (int var22 = 0; var22 < 4; var22++) {
                for (int kx = 0; kx < 4; kx++) {
                    Block blk = Blocks.air;
                    if (var22 == 0 || kx == 0 || var22 == 3 || kx == 3) {
                        blk = Blocks.lapis_ore;
                    }

                    OreSpawnMain.setBlockFast(world, cposx + var22, cposy + j, cposz + kx, blk, 0, 2);
                }
            }

            switch (s) {
                case 0:
                    OreSpawnMain.setBlockFast(world, cposx + 1, cposy + j, cposz + 1, Blocks.stone, 0, 2);
                    break;
                case 1:
                    OreSpawnMain.setBlockFast(world, cposx + 2, cposy + j, cposz + 1, Blocks.stone, 0, 2);
                    break;
                case 2:
                    OreSpawnMain.setBlockFast(world, cposx + 2, cposy + j, cposz + 2, Blocks.stone, 0, 2);
                    break;
                default:
                    OreSpawnMain.setBlockFast(world, cposx + 1, cposy + j, cposz + 2, Blocks.stone, 0, 2);
            }

            if (++s > 3) {
                s = 0;
            }
        }

        this.makePart(world, ++cposx, cposy, ++cposz + 7, 9, 5, 1, 1, 1);

        for (int var23 = 0; var23 < xwidth; var23++) {
            for (int kx = 0; kx < zwidth; kx++) {
                for (int var28 = 0; var28 < 4; var28++) {
                    Block blk = Blocks.air;
                    if (var28 == 0 || var28 == 3) {
                        blk = Blocks.lapis_ore;
                    }

                    if (var23 == 0 || var23 == xwidth - 1) {
                        blk = Blocks.lapis_ore;
                    }

                    this.FastSetBlock(world, cposx + var23, cposy + var28, cposz + kx + 2, blk);
                }
            }
        }

        this.makePart(world, cposx + 7, cposy, cposz, 11, 6, 1, -1, 2);
        int var37 = 6;
        int var40 = 3;

        for (int var24 = 0; var24 < var37; var24++) {
            for (int kx = 0; kx < var40; kx++) {
                for (int var29 = 0; var29 < 4; var29++) {
                    Block blkx = Blocks.air;
                    if (var29 == 0 || var29 == 3) {
                        blkx = Blocks.lapis_ore;
                    }

                    if (kx == 0 || kx == var40 - 1) {
                        blkx = Blocks.lapis_ore;
                    }

                    this.FastSetBlock(world, cposx + var24 + 2, cposy + var29, cposz - kx, blkx);
                }
            }
        }

        this.makePart(world, cposx - 7, cposy, cposz, 13, 7, -1, 1, 3);
        var37 = 6;
        var40 = 3;

        for (int var25 = 0; var25 < var37; var25++) {
            for (int kx = 0; kx < var40; kx++) {
                for (int var30 = 0; var30 < 4; var30++) {
                    Block blkxx = Blocks.air;
                    if (var30 == 0 || var30 == 3) {
                        blkxx = Blocks.lapis_ore;
                    }

                    if (kx == 0 || kx == var40 - 1) {
                        blkxx = Blocks.lapis_ore;
                    }

                    this.FastSetBlock(world, cposx - var25 - 2, cposy + var30, cposz + kx, blkxx);
                }
            }
        }

        this.makePart(world, cposx, cposy, cposz - 7, 15, 8, -1, -1, 4);
        var37 = 3;
        var40 = 6;

        for (int var26 = 0; var26 < var37; var26++) {
            for (int kx = 0; kx < var40; kx++) {
                for (int var31 = 0; var31 < 4; var31++) {
                    Block blkxxx = Blocks.air;
                    if (var31 == 0 || var31 == 3) {
                        blkxxx = Blocks.lapis_ore;
                    }

                    if (var26 == 0 || var26 == var37 - 1) {
                        blkxxx = Blocks.lapis_ore;
                    }

                    this.FastSetBlock(world, cposx - var26, cposy + var31, cposz - kx - 2, blkxxx);
                }
            }
        }
    }

    private void makePart(World world, int cposx, int cposy, int cposz, int width, int height, int dx, int dz,
        int difficulty) {
        TileEntityMobSpawner tileentitymobspawner = null;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < width; k++) {
                    this.FastSetBlock(world, cposx + i * dx, cposy + j, cposz + k * dz, Blocks.air);
                }
            }
        }

        for (int var17 = 0; var17 < width; var17++) {
            int j = 0;

            for (int k = 0; k < width; k++) {
                Block blk = Blocks.quartz_block;
                if (var17 == width / 2 || k == width / 2) {
                    blk = Blocks.obsidian;
                }

                this.FastSetBlock(world, cposx + var17 * dx, cposy + j, cposz + k * dz, blk);
            }
        }

        for (int var18 = 0; var18 < width; var18++) {
            int j = height;

            for (int k = 0; k < width; k++) {
                Block blk = Blocks.obsidian;
                this.FastSetBlock(world, cposx + var18 * dx, cposy + j, cposz + k * dz, blk);
            }
        }

        for (int var19 = 0; var19 < width; var19++) {
            for (int j = 0; j < height; j++) {
                Block blk = Blocks.obsidian;
                int k = 0;
                this.FastSetBlock(world, cposx + var19 * dx, cposy + j, cposz + k * dz, blk);
                k = width - 1;
                this.FastSetBlock(world, cposx + var19 * dx, cposy + j, cposz + k * dz, blk);
            }
        }

        for (int k = 0; k < width; k++) {
            for (int j = 0; j < height; j++) {
                int var20 = 0;
                this.FastSetBlock(world, cposx + var20 * dx, cposy + j, cposz + k * dz, Blocks.obsidian);
                var20 = width - 1;
                this.FastSetBlock(world, cposx + var20 * dx, cposy + j, cposz + k * dz, Blocks.obsidian);
            }
        }

        for (int j = 0; j < difficulty; j++) {
            world.setBlock(cposx + dx * width / 2, cposy + j + 2, cposz + dz * width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this
                .getSpawnerTileEntity(world, cposx + dx * width / 2, cposy + j + 2, cposz + dz * width / 2);
            if (tileentitymobspawner != null) {
                int t = world.rand.nextInt(2);
                if (t == 0) {
                    tileentitymobspawner.func_145881_a()
                        .setEntityName("Alien");
                }

                if (t == 1) {
                    tileentitymobspawner.func_145881_a()
                        .setEntityName("WTF?");
                }
            }

            world.setBlock(
                cposx + dx * width / 2 + dx,
                cposy + j + 2,
                cposz + dz * width / 2 + dz,
                Blocks.mob_spawner,
                0,
                2);
            tileentitymobspawner = this
                .getSpawnerTileEntity(world, cposx + dx * width / 2 + dx, cposy + j + 2, cposz + dz * width / 2 + dz);
            if (tileentitymobspawner != null) {
                int tx = world.rand.nextInt(2);
                if (tx == 0) {
                    tileentitymobspawner.func_145881_a()
                        .setEntityName("Alien");
                }

                if (tx == 1) {
                    tileentitymobspawner.func_145881_a()
                        .setEntityName("WTF?");
                }
            }
        }

        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        chestContents = this.AlienWTFContentsList;
        world.setBlock(cposx + width * dx / 2, cposy + 1, cposz + dz, Blocks.chest, 0, 2);
        chest = this.getChestTileEntity(world, cposx + width * dx / 2, cposy + 1, cposz + dz);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 3 + world.rand.nextInt(5));
        }

        if (difficulty > 1) {
            world.setBlock(cposx + width * dx / 2, cposy + 1, cposz + (width - 2) * dz, Blocks.chest, 0, 2);
            chest = this.getChestTileEntity(world, cposx + width * dx / 2, cposy + 1, cposz + (width - 2) * dz);
            if (chest != null) {
                WeightedRandomChestContent
                    .generateChestContents(world.rand, chestContents, chest, 3 + world.rand.nextInt(5));
            }
        }

        if (difficulty > 2) {
            world.setBlock(cposx + dx, cposy + 1, cposz + width / 2 * dz, Blocks.chest, 0, 2);
            chest = this.getChestTileEntity(world, cposx + dx, cposy + 1, cposz + width / 2 * dz);
            if (chest != null) {
                WeightedRandomChestContent
                    .generateChestContents(world.rand, chestContents, chest, 3 + world.rand.nextInt(5));
            }
        }

        if (difficulty > 3) {
            world.setBlock(cposx + (width - 2) * dx, cposy + 1, cposz + width / 2 * dz, Blocks.chest, 0, 2);
            chest = this.getChestTileEntity(world, cposx + (width - 2) * dx, cposy + 1, cposz + width / 2 * dz);
            if (chest != null) {
                WeightedRandomChestContent
                    .generateChestContents(world.rand, chestContents, chest, 3 + world.rand.nextInt(5));
            }
        }
    }

    public void makeEnderKnightDungeon(World world, int cposx, int cposy, int cposz) {
        int height = 6;
        int zwidth = 5;
        TileEntityMobSpawner tileentitymobspawner = null;

        for (int i = 0; i < 4; i++) {
            for (int k = 0; k < 5; k++) {
                for (int j = 0; j < 5; j++) {
                    this.FastSetBlock(world, cposx, cposy + j, cposz + k, Blocks.air);
                }
            }

            cposx++;
        }

        int var30 = 5;

        for (int k = 0; k < var30; k++) {
            for (int j = 0; j < height; j++) {
                Block blk = Blocks.obsidian;
                if (k == 2 && j >= 1 && j <= 3) {
                    blk = Blocks.air;
                }

                this.FastSetBlock(world, cposx, cposy + j, cposz + k, blk);
            }
        }

        cposx++;
        cposz--;
        var30 = 7;

        for (int var26 = 0; var26 < var30; var26++) {
            for (int j = 0; j < height; j++) {
                Block blk = Blocks.air;
                if (j == 0 || j == height - 1) {
                    blk = Blocks.obsidian;
                }

                if (j == 0 && var26 > 0 && var26 < var30 - 1) {
                    blk = Blocks.end_stone;
                }

                if (var26 == 0 || var26 == var30 - 1) {
                    blk = Blocks.obsidian;
                }

                this.FastSetBlock(world, cposx, cposy + j, cposz + var26, blk);
            }

            if (var26 == 1 || var26 == 2 || var26 == var30 - 3 || var26 == var30 - 2) {
                this.makeShelves(world, cposx, cposy + 1, cposz + var26);
            }
        }

        cposz--;

        for (int m = 0; m < 5; m++) {
            cposx++;
            var30 = 9;

            for (int var27 = 0; var27 < var30; var27++) {
                for (int j = 0; j < height; j++) {
                    Block blkx = Blocks.air;
                    if (j == 0 || j == height - 1) {
                        blkx = Blocks.obsidian;
                    }

                    if (j == 0 && var27 > 0 && var27 < var30 - 1) {
                        blkx = Blocks.end_stone;
                    }

                    if (var27 == 0 || var27 == var30 - 1) {
                        blkx = Blocks.obsidian;
                    }

                    this.FastSetBlock(world, cposx, cposy + j, cposz + var27, blkx);
                }

                if (var27 == 1 || var27 == 2 || var27 == var30 - 3 || var27 == var30 - 2) {
                    this.makeShelves(world, cposx, cposy + 1, cposz + var27);
                }

                if (m == 2 && var27 == 4) {
                    world.setBlock(cposx, cposy + 2, cposz + var27, Blocks.mob_spawner, 0, 2);
                    tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 2, cposz + var27);
                    if (tileentitymobspawner != null) {
                        tileentitymobspawner.func_145881_a()
                            .setEntityName("Ender Knight");
                    }

                    world.setBlock(cposx, cposy + 3, cposz + var27, Blocks.mob_spawner, 0, 2);
                    tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 3, cposz + var27);
                    if (tileentitymobspawner != null) {
                        tileentitymobspawner.func_145881_a()
                            .setEntityName("Ender Knight");
                    }
                }
            }
        }

        cposz++;
        cposx++;
        var30 = 7;

        for (int var28 = 0; var28 < var30; var28++) {
            for (int j = 0; j < height; j++) {
                Block blkxx = Blocks.air;
                if (j == 0 || j == height - 1) {
                    blkxx = Blocks.obsidian;
                }

                if (j == 0 && var28 > 0 && var28 < var30 - 1) {
                    blkxx = Blocks.end_stone;
                }

                if (var28 == 0 || var28 == var30 - 1) {
                    blkxx = Blocks.obsidian;
                }

                this.FastSetBlock(world, cposx, cposy + j, cposz + var28, blkxx);
            }

            if (var28 == 1 || var28 == 2 || var28 == var30 - 3 || var28 == var30 - 2) {
                this.makeShelves(world, cposx, cposy + 1, cposz + var28);
            }
        }

        cposz++;
        cposx++;
        var30 = 5;

        for (int var29 = 0; var29 < var30; var29++) {
            for (int j = 0; j < height; j++) {
                Block blkxxx = Blocks.obsidian;
                this.FastSetBlock(world, cposx, cposy + j, cposz + var29, blkxxx);
            }
        }
    }

    private void makeShelves(World world, int cposx, int cposy, int cposz) {
        int i = world.rand.nextInt(4);
        Block blk = Blocks.air;
        if (i == 0) {
            TileEntityChest chest = null;
            WeightedRandomChestContent[] chestContents = null;
            chestContents = this.KnightContentsList;
            world.setBlock(cposx, cposy, cposz, Blocks.chest, 0, 2);
            chest = this.getChestTileEntity(world, cposx, cposy, cposz);
            if (chest != null) {
                WeightedRandomChestContent
                    .generateChestContents(world.rand, chestContents, chest, 3 + world.rand.nextInt(5));
            }
        }

        if (i == 1) {
            blk = Blocks.bookshelf;
            int k = 1 + world.rand.nextInt(4);

            for (int j = 0; j < k; j++) {
                this.FastSetBlock(world, cposx, cposy + j, cposz, blk);
            }
        }

        if (i == 2) {
            blk = Blocks.web;
            int k = 1 + world.rand.nextInt(4);

            for (int j = 0; j < k; j++) {
                this.FastSetBlock(world, cposx, cposy + j, cposz, blk);
            }
        }
    }

    public void makePlayPool(World world, int cposx, int cposy, int cposz) {
        TileEntityMobSpawner tileentitymobspawner = null;
        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        chestContents = this.SquidContentsList;

        for (int i = 0; i < 4; i++) {
            world.setBlock(cposx + i, cposy + 16, cposz, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + i, cposy + 16, cposz);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Attack Squid");
            }
        }

        world.setBlock(cposx + 1, cposy + 17, cposz, Blocks.chest, 0, 2);
        world.setBlock(cposx + 2, cposy + 17, cposz, Blocks.chest, 0, 2);
        chest = this.getChestTileEntity(world, cposx + 1, cposy + 17, cposz);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 3 + world.rand.nextInt(5));
        }

        for (int var9 = 0; var9 < 4; var9++) {
            world.setBlock(cposx + var9, cposy + 18, cposz, Blocks.water, 0, 3);
        }

        world.setBlock(cposx - 1, cposy + 18, cposz, Blocks.flowing_water, 0, 3);
        world.setBlock(cposx + 4, cposy + 18, cposz, Blocks.flowing_water, 0, 3);
    }

    public void makeWaterDragonLair(World world, int cposx, int cposy, int cposz) {
        TileEntityMobSpawner tileentitymobspawner = null;
        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        chestContents = this.WaterDragonContentsList;
        float radius = 10.0F;

        for (float currad = 0.0F; currad < radius; currad += 0.33F) {
            for (float curdeg = 0.0F; curdeg < 360.0F; curdeg += 5.0F) {
                float curx = (float) (currad * Math.cos(Math.toRadians(curdeg)));
                float curz = (float) (currad * Math.sin(Math.toRadians(curdeg)));
                Block blk = Blocks.bedrock;
                if (currad > 5.0F && currad < 6.0F) {
                    blk = Blocks.iron_block;
                }

                this.FastSetBlock(world, (int) (cposx + curx + 0.5F), cposy + 7, (int) (cposz + curz + 0.5F), blk);
            }
        }

        for (int i = 1; i < 10; i++) {
            this.FastSetBlock(world, (int) (cposx + i + 0.5F), cposy + 7, (int) (cposz + 0.5F), Blocks.iron_block);
            this.FastSetBlock(world, (int) (cposx - i + 0.5F), cposy + 7, (int) (cposz + 0.5F), Blocks.iron_block);
            this.FastSetBlock(world, (int) (cposx + 0.5F), cposy + 7, (int) (cposz + i + 0.5F), Blocks.iron_block);
            this.FastSetBlock(world, (int) (cposx + 0.5F), cposy + 7, (int) (cposz - i + 0.5F), Blocks.iron_block);
        }

        this.FastSetBlock(world, (int) (cposx + 0.5F), cposy + 7, (int) (cposz + 0.5F), Blocks.air);
        this.FastSetBlock(world, (int) (cposx + 1 + 0.5F), cposy + 7, (int) (cposz + 0.5F), Blocks.glowstone);
        this.FastSetBlock(world, (int) (cposx - 1 + 0.5F), cposy + 7, (int) (cposz + 0.5F), Blocks.glowstone);
        this.FastSetBlock(world, (int) (cposx + 0.5F), cposy + 7, (int) (cposz + 1 + 0.5F), Blocks.glowstone);
        this.FastSetBlock(world, (int) (cposx + 0.5F), cposy + 7, (int) (cposz - 1 + 0.5F), Blocks.glowstone);
        float var24 = 10.0F;

        for (float curdeg = 0.0F; curdeg < 360.0F; curdeg += 5.0F) {
            float curx = (float) (var24 * Math.cos(Math.toRadians(curdeg)));
            float curz = (float) (var24 * Math.sin(Math.toRadians(curdeg)));
            this.FastSetBlock(
                world,
                (int) (cposx + curx + 0.5F),
                cposy + 1,
                (int) (cposz + curz + 0.5F),
                Blocks.glowstone);
            Block blk = Blocks.lapis_block;
            if (world.rand.nextInt(2) == 0) {
                blk = OreSpawnMain.MyWaterDragonSpawnBlock;
            }

            this.FastSetBlock(world, (int) (cposx + curx + 0.5F), cposy + 2, (int) (cposz + curz + 0.5F), blk);
            blk = Blocks.lapis_block;
            if (world.rand.nextInt(2) == 0) {
                blk = OreSpawnMain.MyWaterDragonSpawnBlock;
            }

            this.FastSetBlock(world, (int) (cposx + curx + 0.5F), cposy + 3, (int) (cposz + curz + 0.5F), blk);
            this.FastSetBlock(
                world,
                (int) (cposx + curx + 0.5F),
                cposy + 4,
                (int) (cposz + curz + 0.5F),
                Blocks.glowstone);
            this.FastSetBlock(
                world,
                (int) (cposx + curx + 0.5F),
                cposy + 5,
                (int) (cposz + curz + 0.5F),
                Blocks.bedrock);
            this.FastSetBlock(
                world,
                (int) (cposx + curx + 0.5F),
                cposy + 6,
                (int) (cposz + curz + 0.5F),
                Blocks.bedrock);
        }

        for (int var18 = -3; var18 <= 3; var18++) {
            for (int j = -3; j <= 3; j++) {
                this.FastSetBlock(world, cposx + var18, cposy, cposz + j, Blocks.sand);
                this.FastSetBlock(world, cposx + var18, cposy - 1, cposz + j, Blocks.stone);
            }
        }

        for (int var19 = -2; var19 <= 2; var19++) {
            for (int j = -2; j <= 2; j++) {
                this.FastSetBlock(world, cposx + var19, cposy + 3, cposz + j, Blocks.leaves);
            }
        }

        this.FastSetBlock(world, cposx, cposy + 4, cposz, Blocks.leaves);
        this.FastSetBlock(world, cposx, cposy + 3, cposz, Blocks.log);
        this.FastSetBlock(world, cposx, cposy + 2, cposz, Blocks.log);
        this.FastSetBlock(world, cposx, cposy + 1, cposz, Blocks.log);
        this.FastSetBlock(world, cposx + 1, cposy + 3, cposz + 1, Blocks.log);
        this.FastSetBlock(world, cposx - 1, cposy + 3, cposz - 1, Blocks.log);
        this.FastSetBlock(world, cposx + 1, cposy + 3, cposz - 1, Blocks.log);
        this.FastSetBlock(world, cposx - 1, cposy + 3, cposz + 1, Blocks.log);
        world.setBlock(cposx + 1, cposy + 3, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 1, cposy + 3, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Water Dragon");
        }

        world.setBlock(cposx - 1, cposy + 3, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 1, cposy + 3, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Water Dragon");
        }

        world.setBlock(cposx, cposy + 3, cposz + 1, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 3, cposz + 1);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Water Dragon");
        }

        world.setBlock(cposx, cposy + 3, cposz - 1, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 3, cposz - 1);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Water Dragon");
        }

        world.setBlock(cposx, cposy + 1, cposz - 1, Blocks.chest, 0, 2);
        chest = this.getChestTileEntity(world, cposx, cposy + 1, cposz - 1);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 4 + world.rand.nextInt(5));
        }
    }

    public void makeCloudSharkDungeon(World world, int cposx, int cposy, int cposz) {
        TileEntityMobSpawner tileentitymobspawner = null;
        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        chestContents = this.CloudSharkContentsList;
        this.FastSetBlock(world, cposx, cposy, cposz, Blocks.glowstone);
        this.FastSetBlock(world, cposx, cposy - 1, cposz, Blocks.glowstone);
        world.setBlock(cposx + 1, cposy, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 1, cposy, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Cloud Shark");
        }

        world.setBlock(cposx - 1, cposy, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 1, cposy, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Cloud Shark");
        }

        world.setBlock(cposx, cposy, cposz + 1, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy, cposz + 1);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Cloud Shark");
        }

        world.setBlock(cposx, cposy, cposz - 1, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy, cposz - 1);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Cloud Shark");
        }

        world.setBlock(cposx, cposy + 1, cposz, Blocks.chest, 0, 2);
        chest = this.getChestTileEntity(world, cposx, cposy + 1, cposz);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 4 + world.rand.nextInt(5));
        }
    }

    public void makeLeafMonsterDungeon(World world, int cposx, int cposy, int cposz) {
        TileEntityMobSpawner tileentitymobspawner = null;
        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        chestContents = this.LeafMonsterContentsList;

        for (int i = -2; i < 6; i++) {
            for (int k = -3; k < 2; k++) {
                for (int j = 0; j < 4; j++) {
                    this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.air);
                }
            }
        }

        for (int var21 = 0; var21 < 4; var21++) {
            for (int k = 0; k < 4; k++) {
                for (int j = -1; j > -5; j--) {
                    Block blk = Blocks.log;
                    Block bid = world.getBlock(cposx + var21, cposy + j, cposz + k);
                    if (bid == Blocks.air || bid == Blocks.tallgrass) {
                        this.FastSetBlock(world, cposx + var21, cposy + j, cposz + k, blk);
                    }
                }
            }
        }

        for (int var22 = 0; var22 < 4; var22++) {
            for (int k = 0; k < 4; k++) {
                for (int jx = 0; jx < 10; jx++) {
                    Block blk = Blocks.log;
                    if (jx < 2 && (k == 0 || k == 1) && (var22 == 1 || var22 == 2)) {
                        blk = Blocks.air;
                    }

                    if (k == 1 && (var22 == 1 || var22 == 2)) {
                        blk = Blocks.air;
                    }

                    if (k != 2 || var22 != 1 && var22 != 2) {
                        this.FastSetBlock(world, cposx + var22, cposy + jx, cposz + k, blk);
                    }
                }
            }
        }

        for (int var23 = 0; var23 < 4; var23++) {
            for (int k = 0; k < 4; k++) {
                for (int jx = 0; jx < 10; jx++) {
                    if (k == 2 && (var23 == 1 || var23 == 2)) {
                        Block blkx = Blocks.ladder;
                        world.setBlock(cposx + var23, cposy + jx, cposz + k, blkx, 2, 3);
                    }
                }
            }
        }

        this.FastSetBlock(world, cposx + 1, cposy + 2, cposz - 1, Blocks.leaves);
        this.FastSetBlock(world, cposx + 2, cposy + 2, cposz - 1, Blocks.leaves);

        for (int var24 = -3; var24 < 7; var24++) {
            for (int k = -3; k < 7; k++) {
                int jxx = 9;
                if (var24 < 0 || var24 > 3 || k < 0 || k > 3) {
                    Block blkx = Blocks.log;
                    if (var24 == -3 || var24 == 6 || k == -3 || k == 6) {
                        blkx = Blocks.leaves;
                    }

                    this.FastSetBlock(world, cposx + var24, cposy + jxx, cposz + k, blkx);
                }
            }
        }

        for (int var25 = -3; var25 < 7; var25++) {
            for (int kx = -3; kx < 7; kx++) {
                for (int jxx = 10; jxx < 13; jxx++) {
                    Block blkx = Blocks.air;
                    if (var25 == -3 || var25 == 6 || kx == -3 || kx == 6) {
                        blkx = Blocks.leaves;
                    }

                    this.FastSetBlock(world, cposx + var25, cposy + jxx, cposz + kx, blkx);
                }
            }
        }

        for (int var26 = -2; var26 < 6; var26++) {
            for (int kx = -2; kx < 6; kx++) {
                int jxx = 13;
                Block blkx = Blocks.air;
                if (var26 == -2 || var26 == 5 || kx == -2 || kx == 5) {
                    blkx = Blocks.log;
                }

                if (var26 == -1 || var26 == 4 || kx == -1 || kx == 4) {
                    blkx = Blocks.leaves;
                }

                this.FastSetBlock(world, cposx + var26, cposy + jxx, cposz + kx, blkx);
            }
        }

        for (int var27 = -1; var27 < 5; var27++) {
            for (int kx = -1; kx < 5; kx++) {
                int jxxx = 14;
                Block blkxx = Blocks.leaves;
                this.FastSetBlock(world, cposx + var27, cposy + jxxx, cposz + kx, blkxx);
            }
        }

        for (int var28 = 0; var28 < 4; var28++) {
            for (int kx = 0; kx < 4; kx++) {
                int jxxx = 15;
                Block blkxx = Blocks.log;
                this.FastSetBlock(world, cposx + var28, cposy + jxxx, cposz + kx, blkxx);
            }
        }

        for (int var29 = 1; var29 < 3; var29++) {
            for (int kx = 1; kx < 3; kx++) {
                int jxxx = 16;
                Block blkxx = Blocks.leaves;
                this.FastSetBlock(world, cposx + var29, cposy + jxxx, cposz + kx, blkxx);
            }
        }

        world.setBlock(cposx - 2, cposy + 10, cposz - 2, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 2, cposy + 10, cposz - 2);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Leaf Monster");
        }

        world.setBlock(cposx + 5, cposy + 10, cposz + 5, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 5, cposy + 10, cposz + 5);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Leaf Monster");
        }

        world.setBlock(cposx - 2, cposy + 10, cposz + 5, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 2, cposy + 10, cposz + 5);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Leaf Monster");
        }

        world.setBlock(cposx + 5, cposy + 10, cposz - 2, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 5, cposy + 10, cposz - 2);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Leaf Monster");
        }

        world.setBlock(cposx + 1, cposy + 10, cposz + 5, Blocks.chest, 0, 2);
        world.setBlock(cposx + 2, cposy + 10, cposz + 5, Blocks.chest, 0, 2);
        chest = this.getChestTileEntity(world, cposx + 1, cposy + 10, cposz + 5);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 12 + world.rand.nextInt(5));
        }
    }

    public void makeMiniDungeon(World world, int cposx, int cposy, int cposz) {
        TileEntityMobSpawner tileentitymobspawner = null;
        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        chestContents = this.MiniContentsList;

        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {
                for (int j = 0; j < 7; j++) {
                    Block blk = Blocks.air;
                    if (i == 0 || k == 0 || i == 9 || k == 9) {
                        blk = Blocks.iron_bars;
                    }

                    if (i == 0 && k == 0) {
                        blk = Blocks.cobblestone;
                    }

                    if (i == 9 && k == 9) {
                        blk = Blocks.cobblestone;
                    }

                    if (i == 0 && k == 9) {
                        blk = Blocks.cobblestone;
                    }

                    if (i == 9 && k == 0) {
                        blk = Blocks.cobblestone;
                    }

                    if (j == 0) {
                        blk = Blocks.cobblestone;
                    }

                    if (j == 6 && (i == 0 || k == 0 || i == 9 || k == 9)) {
                        blk = Blocks.cobblestone;
                    }

                    this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, blk);
                }
            }
        }

        for (int var16 = 1; var16 < 9; var16++) {
            for (int k = 1; k < 9; k++) {
                int j = 7;
                Block blkx = Blocks.air;
                if (var16 == 1 || var16 == 8 || k == 1 || k == 8) {
                    blkx = Blocks.grass;
                }

                this.FastSetBlock(world, cposx + var16, cposy + j, cposz + k, blkx);
            }
        }

        for (int var17 = 2; var17 < 8; var17++) {
            for (int k = 2; k < 8; k++) {
                int j = 8;
                Block blkx = Blocks.air;
                if (var17 == 2 || var17 == 7 || k == 2 || k == 7) {
                    blkx = Blocks.grass;
                }

                this.FastSetBlock(world, cposx + var17, cposy + j, cposz + k, blkx);
            }
        }

        int var18 = -6;
        int j = 1;
        int k = 3;

        for (int m = 0; m < 6; m++) {
            this.FastSetBlock(world, cposx + var18, cposy + j, cposz + k, Blocks.planks);
            this.FastSetBlock(world, cposx + var18, cposy + j, cposz + k + 1, Blocks.planks);
            this.FastSetBlock(world, cposx + var18, cposy + j, cposz + k + 2, Blocks.planks);
            this.FastSetBlock(world, cposx + var18, cposy + j, cposz + k + 3, Blocks.planks);
            this.FastSetBlock(world, cposx + var18, cposy + j + 1, cposz + k, Blocks.fence);
            this.FastSetBlock(world, cposx + var18, cposy + j + 1, cposz + k + 3, Blocks.fence);
            this.FastSetBlock(world, cposx + var18, cposy + j + 2, cposz + k, Blocks.torch);
            this.FastSetBlock(world, cposx + var18, cposy + j + 2, cposz + k + 3, Blocks.torch);
            var18++;
            j++;
        }

        for (int var19 = 3; var19 < 7; var19++) {
            for (int var47 = 3; var47 < 7; var47++) {
                int var33 = 9;
                Block blkx = Blocks.air;
                if (var19 == 3 || var19 == 6 || var47 == 3 || var47 == 6) {
                    world.setBlock(cposx + var19, cposy + var33, cposz + var47, Blocks.mob_spawner, 0, 2);
                    tileentitymobspawner = this
                        .getSpawnerTileEntity(world, cposx + var19, cposy + var33, cposz + var47);
                    if (tileentitymobspawner != null) {
                        tileentitymobspawner.func_145881_a()
                            .setEntityName("Butterfly");
                    }
                }
            }
        }

        int var48 = 0;
        int var20 = 0;

        for (j = 7; j < 11; j++) {
            this.FastSetBlock(world, cposx + var20, cposy + j, cposz + var48, Blocks.cobblestone);
        }

        world.setBlock(cposx + var20, cposy + j, cposz + var48, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var20, cposy + j, cposz + var48);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Terrible Terror");
        }

        var48 = 9;
        var20 = 9;

        for (j = 7; j < 11; j++) {
            this.FastSetBlock(world, cposx + var20, cposy + j, cposz + var48, Blocks.cobblestone);
        }

        world.setBlock(cposx + var20, cposy + j, cposz + var48, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var20, cposy + j, cposz + var48);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Butterfly");
        }

        var20 = 0;
        var48 = 9;

        for (j = 7; j < 11; j++) {
            this.FastSetBlock(world, cposx + var20, cposy + j, cposz + var48, Blocks.cobblestone);
        }

        world.setBlock(cposx + var20, cposy + j, cposz + var48, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var20, cposy + j, cposz + var48);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Terrible Terror");
        }

        var20 = 9;
        var48 = 0;

        for (j = 7; j < 11; j++) {
            this.FastSetBlock(world, cposx + var20, cposy + j, cposz + var48, Blocks.cobblestone);
        }

        world.setBlock(cposx + var20, cposy + j, cposz + var48, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var20, cposy + j, cposz + var48);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Butterfly");
        }

        var48 = 1;
        var20 = 1;
        int var38 = 1;
        world.setBlock(cposx + var20, cposy + var38, cposz + var48, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var20, cposy + var38, cposz + var48);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Terrible Terror");
        }

        var48 = 8;
        var20 = 8;
        var38 = 1;
        world.setBlock(cposx + var20, cposy + var38, cposz + var48, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var20, cposy + var38, cposz + var48);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Terrible Terror");
        }

        var20 = 8;
        var48 = 1;
        var38 = 1;
        world.setBlock(cposx + var20, cposy + var38, cposz + var48, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var20, cposy + var38, cposz + var48);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Butterfly");
        }

        var20 = 1;
        var48 = 8;
        var38 = 1;
        world.setBlock(cposx + var20, cposy + var38, cposz + var48, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var20, cposy + var38, cposz + var48);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Butterfly");
        }

        var20 = 4;
        var48 = 4;
        var38 = 1;
        world.setBlock(cposx + var20, cposy + var38, cposz + var48, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var20, cposy + var38, cposz + var48);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Lurking Terror");
        }

        var20 = 5;
        var48 = 5;
        var38 = 1;
        world.setBlock(cposx + var20, cposy + var38, cposz + var48, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var20, cposy + var38, cposz + var48);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Lurking Terror");
        }

        world.setBlock(cposx + 3, cposy + 1, cposz + 3, Blocks.chest, 0, 2);
        chest = this.getChestTileEntity(world, cposx + 3, cposy + 1, cposz + 3);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 4 + world.rand.nextInt(5));
        }
    }

    public void makeGoldFishBowl(World world, int cposx, int cposy, int cposz) {
        TileEntityMobSpawner tileentitymobspawner = null;
        int j = 1;
        Block blk = Blocks.glass;

        for (int i = 0; i < 5; i++) {
            for (int k = 0; k < 5; k++) {
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, blk);
            }
        }

        int var27 = 2;

        for (int var17 = -1; var17 < 6; var17++) {
            for (int k = -1; k < 6; k++) {
                blk = Blocks.sand;
                if (var17 == -1 || k == -1 || var17 == 5 || k == 5) {
                    blk = Blocks.glass;
                }

                this.FastSetBlock(world, cposx + var17, cposy + var27, cposz + k, blk);
            }
        }

        var27 = 3;

        for (int var18 = -1; var18 < 6; var18++) {
            for (int k = -1; k < 6; k++) {
                blk = Blocks.water;
                if (var18 == -1 || k == -1 || var18 == 5 || k == 5) {
                    blk = Blocks.glass;
                }

                this.FastSetBlock(world, cposx + var18, cposy + var27, cposz + k, blk);
            }
        }

        int k = 0;
        int var19 = 0;
        blk = Blocks.glowstone;
        this.FastSetBlock(world, cposx + var19, cposy + var27, cposz + k, blk);
        int var36 = 4;
        var19 = 4;
        this.FastSetBlock(world, cposx + var19, cposy + var27, cposz + var36, blk);
        var36 = 4;
        var19 = 0;
        this.FastSetBlock(world, cposx + var19, cposy + var27, cposz + var36, blk);
        var36 = 0;
        var19 = 4;
        this.FastSetBlock(world, cposx + var19, cposy + var27, cposz + var36, blk);
        var27 = 4;

        for (int var23 = -1; var23 < 6; var23++) {
            for (int var39 = -1; var39 < 6; var39++) {
                blk = Blocks.water;
                if (var23 == -1 || var39 == -1 || var23 == 5 || var39 == 5) {
                    blk = Blocks.glass;
                }

                this.FastSetBlock(world, cposx + var23, cposy + var27, cposz + var39, blk);
            }
        }

        for (int var30 = 5; var30 < 8; var30++) {
            for (int var24 = -1; var24 < 6; var24++) {
                for (int var40 = -1; var40 < 6; var40++) {
                    blk = Blocks.air;
                    if (var24 == -1 || var40 == -1 || var24 == 5 || var40 == 5) {
                        blk = Blocks.glass;
                    }

                    this.FastSetBlock(world, cposx + var24, cposy + var30, cposz + var40, blk);
                }
            }
        }

        var27 = 8;
        blk = Blocks.glass;

        for (int var25 = 0; var25 < 5; var25++) {
            for (int var41 = 0; var41 < 5; var41++) {
                this.FastSetBlock(world, cposx + var25, cposy + var27, cposz + var41, blk);
            }
        }

        var19 = 2;
        var36 = 2;
        var27 = 6;
        world.setBlock(cposx + var19, cposy + var27, cposz + var36, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var19, cposy + var27, cposz + var36);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Gold Fish");
        }
    }

    public void makeEnderReaperGraveyard(World world, int cposx, int cposy, int cposz) {
        int width = 11;
        int length = 13;
        TileEntityMobSpawner tileentitymobspawner = null;

        for (int j = 1; j < 5; j++) {
            for (int i = 0; i < width; i++) {
                for (int k = 0; k < length; k++) {
                    if (world.getBlock(cposx + i, cposy - j, cposz + k) == Blocks.air) {
                        this.FastSetBlock(world, cposx + i, cposy - j, cposz + k, Blocks.end_stone);
                    }
                }
            }
        }

        int var20 = 0;
        Block blk = Blocks.end_stone;

        for (int i = 0; i < width; i++) {
            for (int kx = 0; kx < length; kx++) {
                this.FastSetBlock(world, cposx + i, cposy + var20, cposz + kx, blk);
            }
        }

        for (int var21 = 1; var21 < 5; var21++) {
            for (int var15 = 0; var15 < width; var15++) {
                for (int kx = 0; kx < length; kx++) {
                    blk = Blocks.air;
                    if (var15 == 0 || kx == 0 || var15 == width - 1 || kx == length - 1) {
                        blk = Blocks.iron_bars;
                    }

                    this.FastSetBlock(world, cposx + var15, cposy + var21, cposz + kx, blk);
                }
            }
        }

        int var16 = 1;
        int kx = 1;
        var20 = 1;
        world.setBlock(cposx + var16, cposy + var20, cposz + kx, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var16, cposy + var20, cposz + kx);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Ender Reaper");
        }

        var16 = width - 2;
        kx = length - 2;
        var20 = 1;
        world.setBlock(cposx + var16, cposy + var20, cposz + kx, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var16, cposy + var20, cposz + kx);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Ender Reaper");
        }

        int var18 = 1;
        kx = length - 2;
        var20 = 1;
        world.setBlock(cposx + var18, cposy + var20, cposz + kx, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var18, cposy + var20, cposz + kx);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Ender Reaper");
        }

        var18 = width - 2;
        int var31 = 1;
        var20 = 1;
        world.setBlock(cposx + var18, cposy + var20, cposz + var31, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var18, cposy + var20, cposz + var31);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Ender Reaper");
        }

        this.makeAGrave(world, cposx, cposy, cposz, 1, 6);
        this.makeAGrave(world, cposx, cposy, cposz, 3, 4);
        this.makeAGrave(world, cposx, cposy, cposz, 5, 4);
        this.makeAGrave(world, cposx, cposy, cposz, 7, 4);
        this.makeAGrave(world, cposx, cposy, cposz, 3, 8);
        this.makeAGrave(world, cposx, cposy, cposz, 5, 8);
        this.makeAGrave(world, cposx, cposy, cposz, 7, 8);
        this.makeAGrave(world, cposx, cposy, cposz, 9, 6);
    }

    public void makeAGrave(World world, int cposx, int cposy, int cposz, int xoff, int zoff) {
        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        chestContents = this.GraveContentsList;
        this.FastSetBlock(world, cposx + xoff, cposy + 1, cposz + zoff - 1, Blocks.obsidian);
        this.FastSetBlock(world, cposx + xoff, cposy, cposz + zoff + 1, Blocks.obsidian);
        world.setBlock(cposx + xoff, cposy, cposz + zoff, Blocks.chest, 0, 2);
        chest = this.getChestTileEntity(world, cposx + xoff, cposy, cposz + zoff);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 3 + world.rand.nextInt(3));
        }
    }

    public void makeUrchinSpawner(World world, int cposx, int cposy, int cposz) {
        TileEntityMobSpawner tileentitymobspawner = null;
        TileEntityChest chest = null;
        int patchy = 3;

        for (int i = 0; i < patchy; i++) {
            Block bid = OreSpawnMain.CrystalStone;
            if (i == 1) {
                bid = OreSpawnMain.CrystalCrystal;
            }

            if (i == 2) {
                bid = OreSpawnMain.TigersEye;
            }

            float dx = world.rand.nextFloat() - world.rand.nextFloat();
            float dz = world.rand.nextFloat() - world.rand.nextFloat();
            float dy = 0.5F + world.rand.nextFloat() / 2.0F;
            int width = world.rand.nextInt(2);
            int length = 10 + width * 3 + world.rand.nextInt(5);
            if (i != 0) {
                length /= 2;
            }

            float rx = cposx;
            float ry = cposy;
            float rz = cposz;

            for (int iy = 0; iy <= length; iy++) {
                for (int ix = 0; ix <= width; ix++) {
                    for (int iz = 0; iz <= width; iz++) {
                        OreSpawnMain.setBlockFast(world, (int) (rx + ix), (int) ry, (int) (rz + iz), bid, 0, 2);
                    }
                }

                ry += dy;
                rx += dx;
                rz += dz;
            }
        }

        world.setBlock(cposx, cposy + 1, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 1, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Crystal Urchin");
        }

        world.setBlock(cposx, cposy + 2, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 2, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Crystal Urchin");
        }

        world.setBlock(cposx, cposy + 3, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 3, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Crystal Urchin");
        }

        world.setBlock(cposx, cposy, cposz, Blocks.air, 0, 2);
        world.setBlock(cposx, cposy - 1, cposz, Blocks.chest, 0, 2);
        world.setBlockMetadataWithNotify(cposx, cposy - 1, cposz, 2, 3);
        chest = this.getChestTileEntity(world, cposx, cposy - 1, cposz);
        if (chest != null) {
            chest.setInventorySlotContents(1, new ItemStack(OreSpawnMain.UrchinEgg, 1 + world.rand.nextInt(5), 0));
            chest.setInventorySlotContents(2, new ItemStack(OreSpawnMain.CrystalCoal, 4 + world.rand.nextInt(16), 0));
            chest.setInventorySlotContents(3, new ItemStack(OreSpawnMain.CrystalCoal, 4 + world.rand.nextInt(16), 0));
        }
    }

    public void makeSpitBugLair(World world, int cposx, int cposy, int cposz) {
        int green = 5;
        int dark_green = 13;
        int width = 9;
        TileEntityMobSpawner tileentitymobspawner = null;
        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        chestContents = this.SpitBugContentsList;

        for (int i = 0; i < width; i++) {
            OreSpawnMain.setBlockFast(
                world,
                cposx + i,
                cposy + width - i + 2,
                cposz,
                Blocks.stained_hardened_clay,
                dark_green,
                2);
            OreSpawnMain.setBlockFast(
                world,
                cposx + i,
                cposy + width - i + 1,
                cposz,
                Blocks.stained_hardened_clay,
                dark_green,
                2);
            OreSpawnMain.setBlockFast(world, cposx + i, cposy + width - i, cposz, Blocks.mossy_cobblestone, 0, 2);
            OreSpawnMain.setBlockFast(
                world,
                cposx - i,
                cposy + width - i + 2,
                cposz,
                Blocks.stained_hardened_clay,
                dark_green,
                2);
            OreSpawnMain.setBlockFast(
                world,
                cposx - i,
                cposy + width - i + 1,
                cposz,
                Blocks.stained_hardened_clay,
                dark_green,
                2);
            OreSpawnMain.setBlockFast(world, cposx - i, cposy + width - i, cposz, Blocks.mossy_cobblestone, 0, 2);
        }

        OreSpawnMain.setBlockFast(world, cposx, cposy + width + 3, cposz, Blocks.emerald_ore, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx, cposy + width + 2, cposz, Blocks.emerald_ore, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx, cposy + width + 1, cposz, Blocks.emerald_ore, 0, 2);
        world.setBlock(cposx, cposy + width + 0, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + width + 0, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Spit Bug");
        }

        world.setBlock(cposx, cposy + width - 1, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + width - 1, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Spit Bug");
        }

        world.setBlock(cposx, cposy + width - 2, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + width - 2, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Spit Bug");
        }

        int k = 0;

        for (int var15 = 0; var15 < width; var15++) {
            for (int j = -var15; j <= var15; j++) {
                OreSpawnMain.setBlockFast(
                    world,
                    cposx - width + var15 + 1,
                    cposy,
                    cposz + j,
                    Blocks.stained_hardened_clay,
                    green,
                    2);
                OreSpawnMain.setBlockFast(
                    world,
                    cposx + width - var15 - 1,
                    cposy,
                    cposz + j,
                    Blocks.stained_hardened_clay,
                    green,
                    2);
                if (j != -var15 && j != var15) {
                    OreSpawnMain.setBlockFast(world, cposx - width + var15 + 1, cposy + 1, cposz + j, Blocks.air, 0, 2);
                    OreSpawnMain.setBlockFast(world, cposx + width - var15 - 1, cposy + 1, cposz + j, Blocks.air, 0, 2);
                    OreSpawnMain.setBlockFast(world, cposx - width + var15 + 1, cposy + 2, cposz + j, Blocks.air, 0, 2);
                    OreSpawnMain.setBlockFast(world, cposx + width - var15 - 1, cposy + 2, cposz + j, Blocks.air, 0, 2);
                } else {
                    OreSpawnMain.setBlockFast(
                        world,
                        cposx - width + var15 + 1,
                        cposy + 1,
                        cposz + j,
                        Blocks.stained_hardened_clay,
                        dark_green,
                        2);
                    OreSpawnMain.setBlockFast(
                        world,
                        cposx + width - var15 - 1,
                        cposy + 1,
                        cposz + j,
                        Blocks.stained_hardened_clay,
                        dark_green,
                        2);
                    OreSpawnMain
                        .setBlockFast(world, cposx - width + var15 + 1, cposy + 2, cposz + j, Blocks.stonebrick, 3, 2);
                    OreSpawnMain
                        .setBlockFast(world, cposx + width - var15 - 1, cposy + 2, cposz + j, Blocks.stonebrick, 3, 2);
                }
            }
        }

        world.setBlock(cposx, cposy + 1, cposz, Blocks.chest, 0, 2);
        chest = this.getChestTileEntity(world, cposx, cposy + 1, cposz);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 4 + world.rand.nextInt(4));
        }
    }

    public void makeIgloo(World world, int cposx, int cposy, int cposz) {
        TileEntityMobSpawner tileentitymobspawner = null;
        TileEntityChest chest = null;
        float currad = 6.0F;

        for (float curdeg = 0.0F; curdeg < 360.0F; curdeg += 5.0F) {
            float curx = (float) (currad * Math.cos(Math.toRadians(curdeg)));
            float curz = (float) (currad * Math.sin(Math.toRadians(curdeg)));
            this.FastSetBlock(world, (int) (cposx + curx + 0.5F), cposy + 1, (int) (cposz + curz + 0.5F), Blocks.snow);
            this.FastSetBlock(world, (int) (cposx + curx + 0.5F), cposy + 2, (int) (cposz + curz + 0.5F), Blocks.ice);
            this.FastSetBlock(world, (int) (cposx + curx + 0.5F), cposy + 3, (int) (cposz + curz + 0.5F), Blocks.snow);
        }

        currad = 5.0F;

        for (float var15 = 0.0F; var15 < 360.0F; var15 += 5.0F) {
            float curx = (float) (currad * Math.cos(Math.toRadians(var15)));
            float curz = (float) (currad * Math.sin(Math.toRadians(var15)));
            this.FastSetBlock(world, (int) (cposx + curx + 0.5F), cposy + 4, (int) (cposz + curz + 0.5F), Blocks.ice);
        }

        currad = 4.0F;

        for (float var16 = 0.0F; var16 < 360.0F; var16 += 5.0F) {
            float curx = (float) (currad * Math.cos(Math.toRadians(var16)));
            float curz = (float) (currad * Math.sin(Math.toRadians(var16)));
            this.FastSetBlock(world, (int) (cposx + curx + 0.5F), cposy + 5, (int) (cposz + curz + 0.5F), Blocks.snow);
        }

        currad = 3.0F;

        for (float var17 = 0.0F; var17 < 360.0F; var17 += 10.0F) {
            float curx = (float) (currad * Math.cos(Math.toRadians(var17)));
            float curz = (float) (currad * Math.sin(Math.toRadians(var17)));
            this.FastSetBlock(world, (int) (cposx + curx + 0.5F), cposy + 5, (int) (cposz + curz + 0.5F), Blocks.ice);
        }

        currad = 2.0F;

        for (float var18 = 0.0F; var18 < 360.0F; var18 += 15.0F) {
            float curx = (float) (currad * Math.cos(Math.toRadians(var18)));
            float curz = (float) (currad * Math.sin(Math.toRadians(var18)));
            this.FastSetBlock(world, (int) (cposx + curx + 0.5F), cposy + 5, (int) (cposz + curz + 0.5F), Blocks.snow);
        }

        currad = 1.0F;

        for (float var19 = 0.0F; var19 < 360.0F; var19 += 15.0F) {
            float curx = (float) (currad * Math.cos(Math.toRadians(var19)));
            float curz = (float) (currad * Math.sin(Math.toRadians(var19)));
            this.FastSetBlock(world, (int) (cposx + curx + 0.5F), cposy + 5, (int) (cposz + curz + 0.5F), Blocks.ice);
        }

        this.FastSetBlock(world, (int) (cposx - 6.0F + 0.5F), cposy, (int) (cposz + 0.5F), Blocks.planks);
        this.FastSetBlock(world, (int) (cposx - 6.0F + 0.5F), cposy + 1, (int) (cposz + 0.5F), Blocks.air);
        this.FastSetBlock(world, (int) (cposx - 6.0F + 0.5F), cposy + 2, (int) (cposz + 0.5F), Blocks.air);
        ItemDoor
            .placeDoorBlock(world, (int) (cposx - 6.0F + 0.5F), cposy + 1, (int) (cposz + 0.5F), 2, Blocks.wooden_door);
        world.setBlock(cposx + 2, cposy + 1, cposz - 4, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 2, cposy + 1, cposz - 4);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Rat");
        }

        world.setBlock(cposx - 1, cposy + 1, cposz + 1, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 1, cposy + 1, cposz + 1);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Ghost");
        }

        world.setBlock(cposx + 3, cposy + 1, cposz + 4, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 3, cposy + 1, cposz + 4);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Ghost Pumpkin Skelly");
        }

        world.setBlock(cposx - 3, cposy + 1, cposz - 3, Blocks.chest, 2, 2);
        chest = this.getChestTileEntity(world, cposx - 3, cposy + 1, cposz - 3);
        if (chest != null) {
            if (world.rand.nextInt(2) == 0) {
                chest.setInventorySlotContents(0, new ItemStack(Items.compass));
            }

            if (world.rand.nextInt(2) == 0) {
                chest.setInventorySlotContents(1, new ItemStack(Items.map));
            }

            if (world.rand.nextInt(2) == 0) {
                chest.setInventorySlotContents(2, new ItemStack(Items.cooked_porkchop, 8));
            }

            if (world.rand.nextInt(2) == 0) {
                chest.setInventorySlotContents(3, new ItemStack(Blocks.torch, 32));
            }

            if (world.rand.nextInt(2) == 0) {
                chest.setInventorySlotContents(4, new ItemStack(Items.coal, 16));
            }

            if (world.rand.nextInt(2) == 0) {
                chest.setInventorySlotContents(5, new ItemStack(Items.bed));
            }

            if (world.rand.nextInt(2) == 0) {
                chest.setInventorySlotContents(6, new ItemStack(Items.bed));
            }

            if (world.rand.nextInt(2) == 0) {
                chest.setInventorySlotContents(7, new ItemStack(Items.wooden_door));
            }

            if (world.rand.nextInt(2) == 0) {
                chest.setInventorySlotContents(8, new ItemStack(Items.iron_pickaxe));
            }

            if (world.rand.nextInt(2) == 0) {
                chest.setInventorySlotContents(9, new ItemStack(Items.iron_sword));
            }

            if (world.rand.nextInt(2) == 0) {
                chest.setInventorySlotContents(10, new ItemStack(Items.iron_axe));
            }

            if (world.rand.nextInt(2) == 0) {
                chest.setInventorySlotContents(11, new ItemStack(Items.bucket));
            }

            if (world.rand.nextInt(2) == 0) {
                chest.setInventorySlotContents(13, new ItemStack(Blocks.chest));
            }

            if (world.rand.nextInt(2) == 0) {
                chest.setInventorySlotContents(14, new ItemStack(Items.gold_nugget, 6));
            }

            if (world.rand.nextInt(2) == 0) {
                chest.setInventorySlotContents(15, new ItemStack(Items.gold_nugget, 8));
            }

            if (world.rand.nextInt(2) == 0) {
                chest.setInventorySlotContents(16, new ItemStack(Items.gold_nugget, 10));
            }
        }
    }

    public void makeEnderDragonHospital(World world, int cposx, int cposy, int cposz) {
        TileEntityMobSpawner tileentitymobspawner = null;
        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        chestContents = this.HospitalContentsList;

        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {
                for (int j = 0; j < 7; j++) {
                    Block blk = Blocks.air;
                    if (i == 0 || k == 0 || i == 9 || k == 9) {
                        blk = Blocks.iron_bars;
                    }

                    if (i == 0 && k == 0) {
                        blk = Blocks.obsidian;
                    }

                    if (i == 9 && k == 9) {
                        blk = Blocks.obsidian;
                    }

                    if (i == 0 && k == 9) {
                        blk = Blocks.obsidian;
                    }

                    if (i == 9 && k == 0) {
                        blk = Blocks.obsidian;
                    }

                    if (j == 0) {
                        blk = Blocks.end_stone;
                    }

                    if (j == 6 && (i == 0 || k == 0 || i == 9 || k == 9)) {
                        blk = Blocks.end_stone;
                    }

                    this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, blk);
                }
            }
        }

        for (int var17 = 1; var17 < 9; var17++) {
            for (int k = 1; k < 9; k++) {
                int j = 7;
                Block blkx = Blocks.air;
                if (var17 == 1 || var17 == 8 || k == 1 || k == 8) {
                    blkx = OreSpawnMain.MyEyeOfEnderBlock;
                }

                this.FastSetBlock(world, cposx + var17, cposy + j, cposz + k, blkx);
            }
        }

        for (int var18 = 2; var18 < 8; var18++) {
            for (int k = 2; k < 8; k++) {
                int j = 8;
                Block blkx = Blocks.air;
                if (var18 == 2 || var18 == 7 || k == 2 || k == 7) {
                    blkx = OreSpawnMain.MyEyeOfEnderBlock;
                }

                this.FastSetBlock(world, cposx + var18, cposy + j, cposz + k, blkx);
            }
        }

        for (int var19 = 3; var19 < 7; var19++) {
            for (int k = 3; k < 7; k++) {
                int j = 9;
                Block blkx = Blocks.air;
                if (var19 == 3 || var19 == 6 || k == 3 || k == 6) {
                    blkx = OreSpawnMain.MyEyeOfEnderBlock;
                }

                this.FastSetBlock(world, cposx + var19, cposy + j, cposz + k, blkx);
            }
        }

        int var20 = -6;
        int j = 1;
        int k = 3;

        for (int m = 0; m < 6; m++) {
            this.FastSetBlock(world, cposx + var20, cposy + j, cposz + k, Blocks.end_stone);
            this.FastSetBlock(world, cposx + var20, cposy + j, cposz + k + 1, Blocks.end_stone);
            this.FastSetBlock(world, cposx + var20, cposy + j, cposz + k + 2, Blocks.end_stone);
            this.FastSetBlock(world, cposx + var20, cposy + j, cposz + k + 3, Blocks.end_stone);
            this.FastSetBlock(world, cposx + var20, cposy + j + 1, cposz + k, Blocks.iron_bars);
            this.FastSetBlock(world, cposx + var20, cposy + j + 1, cposz + k + 3, Blocks.iron_bars);
            this.FastSetBlock(world, cposx + var20, cposy + j + 2, cposz + k, Blocks.glowstone);
            this.FastSetBlock(world, cposx + var20, cposy + j + 2, cposz + k + 3, Blocks.glowstone);
            var20++;
            j++;
        }

        this.FastSetBlock(world, cposx + 0, cposy + 7, cposz + 0, Blocks.obsidian);
        this.FastSetBlock(world, cposx + 0, cposy + 7, cposz + 9, Blocks.obsidian);
        this.FastSetBlock(world, cposx + 9, cposy + 7, cposz + 0, Blocks.obsidian);
        this.FastSetBlock(world, cposx + 9, cposy + 7, cposz + 9, Blocks.obsidian);
        this.FastSetBlock(world, cposx + 0, cposy + 8, cposz + 0, Blocks.obsidian);
        this.FastSetBlock(world, cposx + 0, cposy + 8, cposz + 9, Blocks.obsidian);
        this.FastSetBlock(world, cposx + 9, cposy + 8, cposz + 0, Blocks.obsidian);
        this.FastSetBlock(world, cposx + 9, cposy + 8, cposz + 9, Blocks.obsidian);
        EntityEnderCrystal entityendercrystal = new EntityEnderCrystal(world);
        entityendercrystal
            .setLocationAndAngles(cposx + 0.5F, cposy + 9, cposz + 0.5F, world.rand.nextFloat() * 360.0F, 0.0F);
        world.spawnEntityInWorld(entityendercrystal);
        this.FastSetBlock(world, cposx, cposy + 9, cposz, Blocks.bedrock);
        entityendercrystal = new EntityEnderCrystal(world);
        entityendercrystal
            .setLocationAndAngles(cposx + 0.5F, cposy + 9, cposz + 9.5F, world.rand.nextFloat() * 360.0F, 0.0F);
        world.spawnEntityInWorld(entityendercrystal);
        this.FastSetBlock(world, cposx, cposy + 9, cposz + 9, Blocks.bedrock);
        entityendercrystal = new EntityEnderCrystal(world);
        entityendercrystal
            .setLocationAndAngles(cposx + 9.5F, cposy + 9, cposz + 0.5F, world.rand.nextFloat() * 360.0F, 0.0F);
        world.spawnEntityInWorld(entityendercrystal);
        this.FastSetBlock(world, cposx + 9, cposy + 9, cposz, Blocks.bedrock);
        entityendercrystal = new EntityEnderCrystal(world);
        entityendercrystal
            .setLocationAndAngles(cposx + 9.5F, cposy + 9, cposz + 9.5F, world.rand.nextFloat() * 360.0F, 0.0F);
        world.spawnEntityInWorld(entityendercrystal);
        this.FastSetBlock(world, cposx + 9, cposy + 9, cposz + 9, Blocks.bedrock);
        int var21 = 3;
        int var45 = 3;
        int var33 = 9;
        world.setBlock(cposx + var21, cposy + var33, cposz + var45, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var21, cposy + var33, cposz + var45);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Ender Reaper");
        }

        var21 = 3;
        var45 = 6;
        var33 = 9;
        world.setBlock(cposx + var21, cposy + var33, cposz + var45, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var21, cposy + var33, cposz + var45);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Ender Reaper");
        }

        var21 = 6;
        var45 = 3;
        var33 = 9;
        world.setBlock(cposx + var21, cposy + var33, cposz + var45, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var21, cposy + var33, cposz + var45);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Ender Reaper");
        }

        var21 = 6;
        var45 = 6;
        var33 = 9;
        world.setBlock(cposx + var21, cposy + var33, cposz + var45, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var21, cposy + var33, cposz + var45);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Ender Reaper");
        }

        var21 = 1;
        var45 = 1;
        var33 = 1;
        world.setBlock(cposx + var21, cposy + var33, cposz + var45, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var21, cposy + var33, cposz + var45);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Nightmare");
        }

        var21 = 1;
        var45 = 8;
        var33 = 1;
        world.setBlock(cposx + var21, cposy + var33, cposz + var45, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var21, cposy + var33, cposz + var45);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Nightmare");
        }

        var21 = 8;
        var45 = 1;
        var33 = 1;
        world.setBlock(cposx + var21, cposy + var33, cposz + var45, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var21, cposy + var33, cposz + var45);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Nightmare");
        }

        var21 = 8;
        var45 = 8;
        var33 = 1;
        world.setBlock(cposx + var21, cposy + var33, cposz + var45, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var21, cposy + var33, cposz + var45);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Nightmare");
        }

        world.setBlock(cposx + 4, cposy + 1, cposz + 4, Blocks.chest, 0, 2);
        chest = this.getChestTileEntity(world, cposx + 4, cposy + 1, cposz + 4);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 6 + world.rand.nextInt(5));
        }
    }

    public void makeCrystalHauntedHouse(World world, int cposx, int cposy, int cposz) {
        int deltax = 0;
        int deltaz = 0;
        int bid = 0;
        int dirx = 0;
        int dirz = 0;
        int stuffdir = 0;
        TileEntityMobSpawner tileentitymobspawner = null;
        int length = 3;
        int width = 3;
        int height = 3;
        int var22 = 1;
        int var28 = 2;
        int x = cposx;
        int z = cposz;
        int y = cposy;
        if (!world.isRemote) {
            for (int i = -width; i <= width; i++) {
                for (int j = -length; j <= length; j++) {
                    for (int k = 0; k <= height + 1; k++) {
                        if (k == height + 1) {
                            world.setBlock(x + i, y + k, z + j, OreSpawnMain.CrystalPlanksBlock);
                        } else if (k == 0) {
                            world.setBlock(x + i, y + k, z + j, OreSpawnMain.CrystalStone);
                        } else if (i != width && j != length && i != -width && j != -length) {
                            world.setBlock(x + i, y + k, z + j, Blocks.air);
                        } else if (k == height) {
                            world.setBlock(x + i, y + k, z + j, Blocks.glass);
                        } else if ((k == 1 || k == 2) && i == var22 * width && j == deltaz * length) {
                            world.setBlock(x + i, y + k, z + j, Blocks.air);
                        } else {
                            world.setBlock(x + i, y + k, z + j, OreSpawnMain.CrystalPlanksBlock);
                        }
                    }
                }
            }

            int var23 = 2;
            int kx = 1;
            int j = length - 1;
            world.setBlock(
                x + var23 * var22 + j * deltaz,
                y + kx,
                z + var23 * deltaz + j * var22,
                OreSpawnMain.CrystalFurnaceBlock);
            world.setBlockMetadataWithNotify(
                x + var23 * var22 + j * deltaz,
                y + kx,
                z + var23 * deltaz + j * var22,
                var28,
                3);
            var23 = 1;
            world.setBlock(
                x + var23 * var22 + j * deltaz,
                y + kx,
                z + var23 * deltaz + j * var22,
                OreSpawnMain.CrystalWorkbenchBlock);
            var23 = 0;
            world.setBlock(x + var23 * var22 + j * deltaz, y + kx, z + var23 * deltaz + j * var22, Blocks.chest);
            world.setBlockMetadataWithNotify(
                x + var23 * var22 + j * deltaz,
                y + kx,
                z + var23 * deltaz + j * var22,
                var28,
                3);
            TileEntityChest chest = this
                .getChestTileEntity(world, x + var23 * var22 + j * deltaz, y + kx, z + var23 * deltaz + j * var22);
            if (chest != null) {
                if (world.rand.nextInt(2) == 0) {
                    chest.setInventorySlotContents(0, new ItemStack(Items.compass));
                }

                if (world.rand.nextInt(3) != 0) {
                    chest.setInventorySlotContents(2, new ItemStack(OreSpawnMain.MyPeacock, 8));
                }

                if (world.rand.nextInt(3) != 0) {
                    chest.setInventorySlotContents(3, new ItemStack(OreSpawnMain.CrystalTorch, 32));
                }

                if (world.rand.nextInt(2) == 0) {
                    chest.setInventorySlotContents(4, new ItemStack(OreSpawnMain.CrystalCoal, 16));
                }

                if (world.rand.nextInt(2) == 0) {
                    chest.setInventorySlotContents(5, new ItemStack(Items.bed));
                }

                if (world.rand.nextInt(2) == 0) {
                    chest.setInventorySlotContents(6, new ItemStack(Items.bed));
                }

                if (world.rand.nextInt(2) == 0) {
                    chest.setInventorySlotContents(7, new ItemStack(Items.wooden_door));
                }

                if (world.rand.nextInt(2) == 0) {
                    chest.setInventorySlotContents(8, new ItemStack(OreSpawnMain.MyCrystalPinkPickaxe));
                }

                if (world.rand.nextInt(2) == 0) {
                    chest.setInventorySlotContents(9, new ItemStack(OreSpawnMain.MyCrystalPinkSword));
                }

                if (world.rand.nextInt(2) == 0) {
                    chest.setInventorySlotContents(10, new ItemStack(OreSpawnMain.MyCrystalPinkAxe));
                }

                chest.setInventorySlotContents(11, new ItemStack(OreSpawnMain.KrakenRepellent));
                if (world.rand.nextInt(2) == 0) {
                    chest.setInventorySlotContents(13, new ItemStack(Blocks.chest));
                }
            }

            world.setBlock(cposx, cposy + 1, cposz, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 1, cposz);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Rat");
            }

            world.setBlock(cposx, cposy + 2, cposz, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 2, cposz);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Ghost");
            }

            world.setBlock(cposx, cposy + 3, cposz, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 3, cposz);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Ghost Pumpkin Skelly");
            }
        }
    }

    public void makeBouncyCastle(World world, int cposx, int cposy, int cposz) {
        int deltax = 0;
        int deltaz = 0;
        Block bid = Blocks.air;
        int meta = 0;
        int dirx = 0;
        int dirz = 0;
        int stuffdir = 0;
        TileEntityMobSpawner tileentitymobspawner = null;
        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        chestContents = this.BouncyContentsList;
        int length = 4;
        int width = 4;
        int height = 5;
        boolean var24 = true;
        int var27 = 2;
        if (!world.isRemote) {
            for (int i = -width; i <= width; i++) {
                for (int j = -length; j <= length; j++) {
                    for (int k = 0; k < height; k++) {
                        bid = Blocks.air;
                        int var26 = 0;
                        if (k == height - 1 || k == 0) {
                            bid = OreSpawnMain.MyLavafoamBlock;
                        }

                        if (i == -width || i == width) {
                            bid = OreSpawnMain.MyLavafoamBlock;
                        }

                        if (j == -length || j == length) {
                            bid = OreSpawnMain.MyLavafoamBlock;
                        }

                        if ((i == -width || i == width) && (j == -length || j == length)) {
                            bid = Blocks.stained_hardened_clay;
                            var26 = 14;
                        }

                        if ((k == 1 || k == 2) && i == 0 && j == -length) {
                            var26 = 0;
                            bid = Blocks.air;
                        }

                        OreSpawnMain.setBlockFast(world, cposx + i, cposy + k, cposz + j, bid, var26, 2);
                    }
                }
            }

            world.setBlock(cposx - 1, cposy + 3, cposz + length - 1, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 1, cposy + 3, cposz + length - 1);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Silverfish");
            }

            world.setBlock(cposx, cposy + 3, cposz + length - 1, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 3, cposz + length - 1);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Rat");
            }

            world.setBlock(cposx + 1, cposy + 3, cposz + length - 1, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 1, cposy + 3, cposz + length - 1);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Scorpion");
            }

            world.setBlock(cposx + width - 1, cposy + 3, cposz - 1, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width - 1, cposy + 3, cposz - 1);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Silverfish");
            }

            world.setBlock(cposx + width - 1, cposy + 3, cposz, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width - 1, cposy + 3, cposz);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Rat");
            }

            world.setBlock(cposx + width - 1, cposy + 3, cposz + 1, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width - 1, cposy + 3, cposz + 1);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Scorpion");
            }

            world.setBlock(cposx - width + 1, cposy + 3, cposz - 1, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - width + 1, cposy + 3, cposz - 1);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Silverfish");
            }

            world.setBlock(cposx - width + 1, cposy + 3, cposz, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - width + 1, cposy + 3, cposz);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Rat");
            }

            world.setBlock(cposx - width + 1, cposy + 3, cposz + 1, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - width + 1, cposy + 3, cposz + 1);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Scorpion");
            }

            world.setBlock(cposx + width - 1, cposy + 3, cposz + length - 1, Blocks.chest, 2, 2);
            chest = this.getChestTileEntity(world, cposx + width - 1, cposy + 3, cposz + length - 1);
            if (chest != null) {
                WeightedRandomChestContent
                    .generateChestContents(world.rand, chestContents, chest, 6 + world.rand.nextInt(5));
            }
        }
    }

    public void makeEnderCastle(World world, int cposx, int cposy, int cposz) {
        int width = 22;
        int height = 12;
        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        TileEntityMobSpawner tileentitymobspawner = null;
        chestContents = this.EnderCastleContentsList;
        Block bid = Blocks.obsidian;

        for (int i = -3; i <= width + 3; i++) {
            for (int k = -3; k <= width + 3; k++) {
                for (int j = 0; j <= 1; j++) {
                    bid = Blocks.air;
                    if (j == 0) {
                        bid = Blocks.obsidian;
                    }

                    if (j == 1 && (i == -3 || i == width + 3 || k == width + 3 | k == -3)) {
                        bid = Blocks.iron_bars;
                    }

                    OreSpawnMain.setBlockFast(world, cposx + i, cposy + j, cposz + k, bid, 0, 2);
                }
            }
        }

        for (int var16 = 0; var16 <= width; var16++) {
            for (int k = 0; k <= width; k++) {
                for (int j = 1; j <= height; j++) {
                    bid = Blocks.air;
                    if (var16 == 0 || var16 == width || k == width | k == 0) {
                        bid = Blocks.bedrock;
                    }

                    if (j == height && bid == Blocks.bedrock && (var16 + k & 1) == 0) {
                        bid = Blocks.air;
                    }

                    if (j == height - 2 && bid == Blocks.bedrock && (var16 + k & 1) == 0) {
                        int which = world.rand.nextInt(4);
                        if (which == 0) {
                            bid = OreSpawnMain.MyEnderKnightSpawnBlock;
                        }

                        if (which == 1) {
                            bid = OreSpawnMain.MyEnderReaperSpawnBlock;
                        }

                        if (which == 2) {
                            bid = OreSpawnMain.MyEndermanSpawnBlock;
                        }

                        if (which == 3) {
                            bid = OreSpawnMain.MyEnderDragonSpawnBlock;
                        }
                    }

                    if (j == 7 && bid == Blocks.bedrock && (var16 + k & 1) != 0) {
                        bid = OreSpawnMain.MyEyeOfEnderBlock;
                    }

                    OreSpawnMain.setBlockFast(world, cposx + var16, cposy + j, cposz + k, bid, 0, 2);
                }
            }
        }

        for (int var17 = -1; var17 <= width + 1; var17++) {
            for (int k = -1; k <= width + 1; k++) {
                for (int j = 1; j <= height - 1; j++) {
                    bid = Blocks.air;
                    if (j == 6 || j > 8) {
                        if (var17 == -1 || var17 == width + 1 || k == width + 1 | k == -1) {
                            bid = Blocks.bedrock;
                        }

                        if (j == 6 && bid != Blocks.air && world.rand.nextInt(2) == 1) {
                            OreSpawnMain.setBlockFast(
                                world,
                                cposx + var17,
                                cposy + j - 1,
                                cposz + k,
                                OreSpawnMain.MyEnderPearlBlock,
                                0,
                                2);
                            if (world.rand.nextInt(3) == 1) {
                                OreSpawnMain.setBlockFast(
                                    world,
                                    cposx + var17,
                                    cposy + j - 2,
                                    cposz + k,
                                    OreSpawnMain.MyEnderPearlBlock,
                                    0,
                                    2);
                            }
                        }
                    }

                    if (j == 7) {
                        if (var17 == -1 || var17 == width + 1 || k == width + 1 | k == -1) {
                            bid = Blocks.bedrock;
                        }

                        if (bid == Blocks.bedrock && (var17 + k & 1) == 0) {
                            bid = Blocks.air;
                        }
                    }

                    if (bid != Blocks.air) {
                        OreSpawnMain.setBlockFast(world, cposx + var17, cposy + j, cposz + k, bid, 0, 2);
                    }
                }
            }
        }

        this.makeAColumn(world, cposx - 2, cposy, cposz - 2, height + 1, 0);
        this.makeAColumn(world, cposx + width - 2, cposy, cposz - 2, height + 1, 1);
        this.makeAColumn(world, cposx - 2, cposy, cposz + width - 2, height + 1, 2);
        this.makeAColumn(world, cposx + width - 2, cposy, cposz + width - 2, height + 1, 3);
        int j = 8;

        for (int var18 = 1; var18 <= width - 1; var18++) {
            for (int k = 1; k <= width - 1; k++) {
                bid = Blocks.obsidian;
                if (var18 == width / 2 || k == width / 2 || var18 == k || var18 == width - k) {
                    bid = Blocks.bedrock;
                }

                OreSpawnMain.setBlockFast(world, cposx + var18, cposy + j, cposz + k, bid, 0, 2);
            }
        }

        int var28 = 9;

        for (int var19 = -2; var19 <= 2; var19++) {
            for (int k = -2; k <= 2; k++) {
                bid = Blocks.lava;
                OreSpawnMain
                    .setBlockFast(world, cposx + var19 + width / 2, cposy + var28, cposz + k + width / 2, bid, 0, 2);
            }
        }

        for (int m = -1; m <= 1; m++) {
            OreSpawnMain
                .setBlockFast(world, cposx + width / 2 + m, cposy + var28, cposz + width / 2 + 3, Blocks.bedrock, 0, 2);
            OreSpawnMain
                .setBlockFast(world, cposx + width / 2 + m, cposy + var28, cposz + width / 2 - 3, Blocks.bedrock, 0, 2);
            OreSpawnMain
                .setBlockFast(world, cposx + width / 2 + 3, cposy + var28, cposz + width / 2 + m, Blocks.bedrock, 0, 2);
            OreSpawnMain
                .setBlockFast(world, cposx + width / 2 - 3, cposy + var28, cposz + width / 2 + m, Blocks.bedrock, 0, 2);
        }

        OreSpawnMain
            .setBlockFast(world, cposx + width / 2 - 2, cposy + var28, cposz + width / 2 - 2, Blocks.bedrock, 0, 2);
        OreSpawnMain
            .setBlockFast(world, cposx + width / 2 + 2, cposy + var28, cposz + width / 2 + 2, Blocks.bedrock, 0, 2);
        OreSpawnMain
            .setBlockFast(world, cposx + width / 2 - 2, cposy + var28, cposz + width / 2 + 2, Blocks.bedrock, 0, 2);
        OreSpawnMain
            .setBlockFast(world, cposx + width / 2 + 2, cposy + var28, cposz + width / 2 - 2, Blocks.bedrock, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + width / 2, cposy + var28, cposz + width / 2, Blocks.bedrock, 0, 2);
        world.setBlock(cposx + width / 2, cposy + var28 + 1, cposz + width / 2, Blocks.ender_chest, 2, 2);
        OreSpawnMain
            .setBlockFast(world, cposx + width / 2, cposy + var28 + 2, cposz + width / 2, Blocks.obsidian, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + width / 2, cposy + var28 + 3, cposz + width / 2, Blocks.bedrock, 0, 2);
        OreSpawnMain
            .setBlockFast(world, cposx + width / 2 - 1, cposy + var28 + 3, cposz + width / 2, Blocks.bedrock, 0, 2);
        OreSpawnMain
            .setBlockFast(world, cposx + width / 2 + 1, cposy + var28 + 3, cposz + width / 2, Blocks.bedrock, 0, 2);
        OreSpawnMain
            .setBlockFast(world, cposx + width / 2, cposy + var28 + 3, cposz + width / 2 - 1, Blocks.bedrock, 0, 2);
        OreSpawnMain
            .setBlockFast(world, cposx + width / 2, cposy + var28 + 3, cposz + width / 2 + 1, Blocks.bedrock, 0, 2);
        OreSpawnMain
            .setBlockFast(world, cposx + width / 2 - 1, cposy + var28 + 4, cposz + width / 2, Blocks.torch, 0, 2);
        OreSpawnMain
            .setBlockFast(world, cposx + width / 2 + 1, cposy + var28 + 4, cposz + width / 2, Blocks.torch, 0, 2);
        OreSpawnMain
            .setBlockFast(world, cposx + width / 2, cposy + var28 + 4, cposz + width / 2 - 1, Blocks.torch, 0, 2);
        OreSpawnMain
            .setBlockFast(world, cposx + width / 2, cposy + var28 + 4, cposz + width / 2 + 1, Blocks.torch, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + width / 2, cposy + var28 + 4, cposz + width / 2, Blocks.bedrock, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + width / 2, cposy + var28 + 5, cposz + width / 2, Blocks.bedrock, 0, 2);
        OreSpawnMain
            .setBlockFast(world, cposx + width / 2, cposy + var28 + 6, cposz + width / 2, Blocks.dragon_egg, 0, 2);
        world.setBlock(cposx + width / 2 + 5, cposy + var28, cposz + width / 2 + 5, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this
            .getSpawnerTileEntity(world, cposx + width / 2 + 5, cposy + var28, cposz + width / 2 + 5);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Ender Reaper");
        }

        world.setBlock(cposx + width / 2 + 5, cposy + var28 + 1, cposz + width / 2 + 5, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this
            .getSpawnerTileEntity(world, cposx + width / 2 + 5, cposy + var28 + 1, cposz + width / 2 + 5);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Ender Knight");
        }

        world.setBlock(cposx + width / 2 - 5, cposy + var28, cposz + width / 2 + 5, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this
            .getSpawnerTileEntity(world, cposx + width / 2 - 5, cposy + var28, cposz + width / 2 + 5);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Ender Reaper");
        }

        world.setBlock(cposx + width / 2 - 5, cposy + var28 + 1, cposz + width / 2 + 5, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this
            .getSpawnerTileEntity(world, cposx + width / 2 - 5, cposy + var28 + 1, cposz + width / 2 + 5);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Ender Knight");
        }

        world.setBlock(cposx + width / 2 + 5, cposy + var28, cposz + width / 2 - 5, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this
            .getSpawnerTileEntity(world, cposx + width / 2 + 5, cposy + var28, cposz + width / 2 - 5);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Ender Reaper");
        }

        world.setBlock(cposx + width / 2 + 5, cposy + var28 + 1, cposz + width / 2 - 5, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this
            .getSpawnerTileEntity(world, cposx + width / 2 + 5, cposy + var28 + 1, cposz + width / 2 - 5);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Ender Knight");
        }

        world.setBlock(cposx + width / 2 - 5, cposy + var28, cposz + width / 2 - 5, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this
            .getSpawnerTileEntity(world, cposx + width / 2 - 5, cposy + var28, cposz + width / 2 - 5);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Ender Reaper");
        }

        world.setBlock(cposx + width / 2 - 5, cposy + var28 + 1, cposz + width / 2 - 5, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this
            .getSpawnerTileEntity(world, cposx + width / 2 - 5, cposy + var28 + 1, cposz + width / 2 - 5);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Ender Knight");
        }

        var28 = 4;

        for (int var20 = 1; var20 <= width - 1; var20++) {
            for (int k = 1; k <= width - 1; k++) {
                bid = Blocks.air;
                if (var20 <= 5 || k <= 5 || var20 >= width - 5 || k >= width - 5) {
                    bid = Blocks.bedrock;
                }

                if (bid != Blocks.air) {
                    OreSpawnMain.setBlockFast(world, cposx + var20, cposy + var28, cposz + k, bid, 0, 2);
                }

                if (var20 == 5 && k >= 5 && k <= width - 5) {
                    OreSpawnMain
                        .setBlockFast(world, cposx + var20, cposy + var28 + 1, cposz + k, Blocks.iron_bars, 0, 2);
                    OreSpawnMain
                        .setBlockFast(world, cposx + var20, cposy + var28 + 2, cposz + k, Blocks.iron_bars, 0, 2);
                    OreSpawnMain
                        .setBlockFast(world, cposx + var20, cposy + var28 + 3, cposz + k, Blocks.iron_bars, 0, 2);
                }

                if (var20 == width - 5 && k >= 5 && k <= width - 5) {
                    OreSpawnMain
                        .setBlockFast(world, cposx + var20, cposy + var28 + 1, cposz + k, Blocks.iron_bars, 0, 2);
                    OreSpawnMain
                        .setBlockFast(world, cposx + var20, cposy + var28 + 2, cposz + k, Blocks.iron_bars, 0, 2);
                    OreSpawnMain
                        .setBlockFast(world, cposx + var20, cposy + var28 + 3, cposz + k, Blocks.iron_bars, 0, 2);
                }

                if (k == 5 && var20 >= 5 && var20 <= width - 5) {
                    OreSpawnMain
                        .setBlockFast(world, cposx + var20, cposy + var28 + 1, cposz + k, Blocks.iron_bars, 0, 2);
                    OreSpawnMain
                        .setBlockFast(world, cposx + var20, cposy + var28 + 2, cposz + k, Blocks.iron_bars, 0, 2);
                    OreSpawnMain
                        .setBlockFast(world, cposx + var20, cposy + var28 + 3, cposz + k, Blocks.iron_bars, 0, 2);
                }

                if (k == width - 5 && var20 >= 5 && var20 <= width - 5) {
                    OreSpawnMain
                        .setBlockFast(world, cposx + var20, cposy + var28 + 1, cposz + k, Blocks.iron_bars, 0, 2);
                    OreSpawnMain
                        .setBlockFast(world, cposx + var20, cposy + var28 + 2, cposz + k, Blocks.iron_bars, 0, 2);
                    OreSpawnMain
                        .setBlockFast(world, cposx + var20, cposy + var28 + 3, cposz + k, Blocks.iron_bars, 0, 2);
                }
            }
        }

        bid = Blocks.bedrock;
        var28 = 3;
        int k = width / 2;
        int var21 = width - 6;

        for (int var44 = -1; var44 <= 1; var44++) {
            OreSpawnMain.setBlockFast(world, cposx + var21, cposy + var28, cposz + k + var44, bid, 0, 2);
        }

        var28 = 2;
        k = width / 2;
        var21 = width - 7;

        for (int var45 = -1; var45 <= 1; var45++) {
            OreSpawnMain.setBlockFast(world, cposx + var21, cposy + var28, cposz + k + var45, bid, 0, 2);
        }

        var28 = 1;
        k = width / 2;
        var21 = width - 8;

        for (int var46 = -1; var46 <= 1; var46++) {
            OreSpawnMain.setBlockFast(world, cposx + var21, cposy + var28, cposz + k + var46, bid, 0, 2);
        }

        var28 = 4;
        var21 = width - 5;

        for (int var47 = -1; var47 <= 1; var47++) {
            OreSpawnMain.setBlockFast(world, cposx + var21, cposy + var28 + 1, cposz + k + var47, Blocks.air, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx + var21, cposy + var28 + 2, cposz + k + var47, Blocks.air, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx + var21, cposy + var28 + 3, cposz + k + var47, Blocks.air, 0, 2);
        }

        var28 = 1;
        world.setBlock(cposx + width / 2, cposy + var28, cposz + width / 2, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + var28, cposz + width / 2);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Ender Reaper");
        }

        world.setBlock(cposx + width / 2, cposy + var28 + 1, cposz + width / 2, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this
            .getSpawnerTileEntity(world, cposx + width / 2, cposy + var28 + 1, cposz + width / 2);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Ender Knight");
        }

        var28 = 5;
        world.setBlock(cposx + 1, cposy + var28, cposz + width / 2 - 1, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 1, cposy + var28, cposz + width / 2 - 1);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("CaveFisher");
        }

        world.setBlock(cposx + 1, cposy + var28, cposz + width / 2 + 1, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 1, cposy + var28, cposz + width / 2 + 1);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("CaveFisher");
        }

        world.setBlock(cposx + 1, cposy + var28, cposz + width / 2, Blocks.chest, 2, 2);
        chest = this.getChestTileEntity(world, cposx + 1, cposy + var28, cposz + width / 2);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 6 + world.rand.nextInt(5));
        }

        world.setBlock(cposx + width / 2 - 1, cposy + var28, cposz + 1, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 - 1, cposy + var28, cposz + 1);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("CaveFisher");
        }

        world.setBlock(cposx + width / 2 + 1, cposy + var28, cposz + 1, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 + 1, cposy + var28, cposz + 1);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("CaveFisher");
        }

        world.setBlock(cposx + width / 2, cposy + var28, cposz + 1, Blocks.chest, 3, 2);
        chest = this.getChestTileEntity(world, cposx + width / 2, cposy + var28, cposz + 1);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 6 + world.rand.nextInt(5));
        }

        world.setBlock(cposx + width / 2 - 1, cposy + var28, cposz + width - 1, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this
            .getSpawnerTileEntity(world, cposx + width / 2 - 1, cposy + var28, cposz + width - 1);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("CaveFisher");
        }

        world.setBlock(cposx + width / 2 + 1, cposy + var28, cposz + width - 1, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this
            .getSpawnerTileEntity(world, cposx + width / 2 + 1, cposy + var28, cposz + width - 1);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("CaveFisher");
        }

        world.setBlock(cposx + width / 2, cposy + var28, cposz + width - 1, Blocks.chest, 4, 2);
        chest = this.getChestTileEntity(world, cposx + width / 2, cposy + var28, cposz + width - 1);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 6 + world.rand.nextInt(5));
        }
    }

    private void makeAColumn(World world, int cposx, int cposy, int cposz, int height, int dir) {
        int width = 4;
        int halfwidth = 2;
        int step = dir;

        for (int i = -2; i <= width + 2; i++) {
            for (int k = -2; k <= width + 2; k++) {
                int j = height + 2;
                OreSpawnMain.setBlockFast(world, cposx + i, cposy + j, cposz + k, Blocks.obsidian, 0, 2);
            }
        }

        for (int var14 = -2; var14 <= width + 2; var14++) {
            for (int k = -2; k <= width + 2; k++) {
                Block bid = Blocks.air;
                if (var14 == -2 || var14 == width + 2 || k == width + 2 | k == -2) {
                    bid = Blocks.obsidian;
                }

                int j = height + 3;
                if (bid != Blocks.air && (var14 + k & 1) == 0) {
                    bid = Blocks.air;
                }

                OreSpawnMain.setBlockFast(world, cposx + var14, cposy + j, cposz + k, bid, 0, 2);
            }
        }

        int var15;
        for (var15 = 0; var15 <= width; var15++) {
            for (int k = 0; k <= width; k++) {
                for (int j = 1; j <= height + 2; j++) {
                    Block bidx = Blocks.air;
                    if (var15 == 0 || var15 == width || k == width | k == 0) {
                        bidx = Blocks.obsidian;
                    }

                    if ((j % 3 == 0 || j % 3 == 1) && j != height + 2
                        && bidx == Blocks.obsidian
                        && (var15 == halfwidth || k == halfwidth)) {
                        bidx = Blocks.iron_bars;
                    }

                    OreSpawnMain.setBlockFast(world, cposx + var15, cposy + j, cposz + k, bidx, 0, 2);
                }
            }
        }

        if (dir == 0) {
            for (int j = 1; j <= 2; j++) {
                OreSpawnMain.setBlockFast(world, cposx + width, cposy + j, cposz + width, Blocks.air, 0, 2);
                OreSpawnMain.setBlockFast(world, cposx + width - 1, cposy + j, cposz + width, Blocks.air, 0, 2);
                OreSpawnMain.setBlockFast(world, cposx + width, cposy + j, cposz + width - 1, Blocks.air, 0, 2);
            }

            for (int var19 = 9; var19 <= 10; var19++) {
                OreSpawnMain.setBlockFast(world, cposx + width, cposy + var19, cposz + width, Blocks.air, 0, 2);
                OreSpawnMain.setBlockFast(world, cposx + width - 1, cposy + var19, cposz + width, Blocks.air, 0, 2);
                OreSpawnMain.setBlockFast(world, cposx + width, cposy + var19, cposz + width - 1, Blocks.air, 0, 2);
            }
        }

        if (dir == 1) {
            for (int j = 1; j <= 2; j++) {
                OreSpawnMain.setBlockFast(world, cposx, cposy + j, cposz + width, Blocks.air, 0, 2);
                OreSpawnMain.setBlockFast(world, cposx + 1, cposy + j, cposz + width, Blocks.air, 0, 2);
                OreSpawnMain.setBlockFast(world, cposx, cposy + j, cposz + width - 1, Blocks.air, 0, 2);
            }

            for (int var21 = 9; var21 <= 10; var21++) {
                OreSpawnMain.setBlockFast(world, cposx, cposy + var21, cposz + width, Blocks.air, 0, 2);
                OreSpawnMain.setBlockFast(world, cposx + 1, cposy + var21, cposz + width, Blocks.air, 0, 2);
                OreSpawnMain.setBlockFast(world, cposx, cposy + var21, cposz + width - 1, Blocks.air, 0, 2);
            }

            step = dir + 1;
            if (step > 3) {
                step = 0;
            }
        }

        if (dir == 2) {
            for (int j = 1; j <= 2; j++) {
                OreSpawnMain.setBlockFast(world, cposx + width, cposy + j, cposz, Blocks.air, 0, 2);
                OreSpawnMain.setBlockFast(world, cposx + width - 1, cposy + j, cposz, Blocks.air, 0, 2);
                OreSpawnMain.setBlockFast(world, cposx + width, cposy + j, cposz + 1, Blocks.air, 0, 2);
            }

            for (int var23 = 9; var23 <= 10; var23++) {
                OreSpawnMain.setBlockFast(world, cposx + width, cposy + var23, cposz, Blocks.air, 0, 2);
                OreSpawnMain.setBlockFast(world, cposx + width - 1, cposy + var23, cposz, Blocks.air, 0, 2);
                OreSpawnMain.setBlockFast(world, cposx + width, cposy + var23, cposz + 1, Blocks.air, 0, 2);
            }

            if (++step > 3) {
                step = 0;
            }

            if (++step > 3) {
                step = 0;
            }
        }

        if (dir == 3) {
            for (int j = 1; j <= 2; j++) {
                OreSpawnMain.setBlockFast(world, cposx, cposy + j, cposz, Blocks.air, 0, 2);
                OreSpawnMain.setBlockFast(world, cposx + 1, cposy + j, cposz, Blocks.air, 0, 2);
                OreSpawnMain.setBlockFast(world, cposx, cposy + j, cposz + 1, Blocks.air, 0, 2);
            }

            for (int var25 = 9; var25 <= 10; var25++) {
                OreSpawnMain.setBlockFast(world, cposx, cposy + var25, cposz, Blocks.air, 0, 2);
                OreSpawnMain.setBlockFast(world, cposx + 1, cposy + var25, cposz, Blocks.air, 0, 2);
                OreSpawnMain.setBlockFast(world, cposx, cposy + var25, cposz + 1, Blocks.air, 0, 2);
            }

            if (++step > 3) {
                step = 0;
            }

            if (++step > 3) {
                step = 0;
            }
        }

        Block bidxx = Blocks.nether_brick;
        int k = 0;

        for (int j = 1; j <= height + 2; j++) {
            if (step == 0) {
                k = 1;
                var15 = 1;
            }

            if (step == 1) {
                var15 = 1;
                k = 3;
            }

            if (step == 2) {
                var15 = 3;
                k = 3;
            }

            if (step == 3) {
                var15 = 3;
                k = 1;
            }

            if (++step > 3) {
                step = 0;
            }

            OreSpawnMain.setBlockFast(world, cposx + var15, cposy + j, cposz + k, bidxx, 0, 2);
        }
    }

    public void makeDamselInDistress(World world, int cposx, int cposy, int cposz) {
        Block bid = Blocks.air;
        int meta = 0;
        int stuffdir = 0;
        TileEntityMobSpawner tileentitymobspawner = null;
        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        chestContents = this.DamselContentsList;
        int length = 4;
        int width = 4;
        int height = 5;
        int var37 = 2;
        if (!world.isRemote) {
            for (int i = -width; i <= width; i++) {
                for (int j = -length; j <= length; j++) {
                    for (int k = 0; k < height; k++) {
                        bid = Blocks.air;
                        int var35 = 0;
                        if (k == 0) {
                            bid = Blocks.cobblestone;
                        }

                        if (i == -width || i == width) {
                            bid = Blocks.cobblestone;
                        }

                        if (j == -length || j == length) {
                            bid = Blocks.cobblestone;
                        }

                        if (bid == Blocks.cobblestone && world.rand.nextInt(8) == 1) {
                            bid = Blocks.mossy_cobblestone;
                        }

                        if ((k == 1 || k == 2 || k == 3) && (i == 0 || i == -1 || i == 1) && j == -length) {
                            var35 = 0;
                            bid = Blocks.air;
                        }

                        OreSpawnMain.setBlockFast(world, cposx + i, cposy + k, cposz + j, bid, var35, 2);
                    }
                }
            }

            int var36 = 0;

            for (int var19 = -width + 1; var19 <= width - 1; var19++) {
                for (int j = -length; j <= length - 1; j++) {
                    bid = Blocks.cobblestone;
                    if (world.rand.nextInt(8) == 1) {
                        bid = Blocks.mossy_cobblestone;
                    }

                    OreSpawnMain.setBlockFast(world, cposx + var19, cposy + height, cposz + j, bid, var36, 2);
                }
            }

            for (int var20 = -width + 2; var20 <= width - 2; var20++) {
                for (int j = -length; j <= length - 2; j++) {
                    int k = height + 1;
                    bid = Blocks.cobblestone;
                    if (world.rand.nextInt(8) == 1) {
                        bid = Blocks.mossy_cobblestone;
                    }

                    OreSpawnMain.setBlockFast(world, cposx + var20, cposy + k, cposz + j, bid, var36, 2);
                }
            }

            int k = height;
            int j = -length;

            for (int m = width; m >= 0; m--) {
                for (int var21 = m; var21 >= 0; var21--) {
                    bid = Blocks.cobblestone;
                    if (world.rand.nextInt(8) == 1) {
                        bid = Blocks.mossy_cobblestone;
                    }

                    OreSpawnMain.setBlockFast(world, cposx + var21, cposy + k, cposz + j, bid, var36, 2);
                    bid = Blocks.cobblestone;
                    if (world.rand.nextInt(8) == 1) {
                        bid = Blocks.mossy_cobblestone;
                    }

                    OreSpawnMain.setBlockFast(world, cposx - var21, cposy + k, cposz + j, bid, var36, 2);
                }

                k++;
            }

            for (int var22 = -width + 1; var22 < width; var22++) {
                for (int var26 = 1; var26 < height; var26++) {
                    k = length - 3;
                    OreSpawnMain.setBlockFast(world, cposx - var22, cposy + var26, cposz + k, Blocks.iron_bars, 0, 2);
                }
            }

            world.setBlock(cposx - width + 1, cposy + 1, cposz - length + 1, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - width + 1, cposy + 1, cposz - length + 1);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Scorpion");
            }

            world.setBlock(cposx + width - 1, cposy + 1, cposz - length + 1, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width - 1, cposy + 1, cposz - length + 1);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Scorpion");
            }

            world.setBlock(cposx + width - 1, cposy + 1, cposz + length - 1, Blocks.chest, 2, 2);
            chest = this.getChestTileEntity(world, cposx + width - 1, cposy + 1, cposz + length - 1);
            if (chest != null) {
                WeightedRandomChestContent
                    .generateChestContents(world.rand, chestContents, chest, 10 + world.rand.nextInt(5));
            }

            Entity var8 = null;
            var8 = EntityList.createEntityByName("Girlfriend", world);
            if (var8 != null) {
                var8.setLocationAndAngles(
                    cposx - width + 2,
                    cposy + 1,
                    cposz + length - 1,
                    world.rand.nextFloat() * 360.0F,
                    0.0F);
                world.spawnEntityInWorld(var8);
            }
        }
    }

    public void makeIncaPyramid(World world, int cposx, int cposy, int cposz) {
        Block bid = Blocks.air;
        int meta = 0;
        TileEntityMobSpawner tileentitymobspawner = null;
        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        int width = 21;
        int depth = 11;
        int height = 9;
        int basewidth = 41;
        int basedepth = 31;
        int baseheight = 10;
        if (!world.isRemote) {
            for (int j = 0; j < baseheight; j++) {
                for (int i = 0; i < basewidth - j * 2; i++) {
                    for (int k = 0; k < basedepth - j * 2; k++) {
                        int var70 = 0;
                        bid = Blocks.air;
                        if (i == 0 || k == 0 || i == basewidth - j * 2 - 1 || k == basedepth - j * 2 - 1) {
                            bid = Blocks.stone;
                            if (world.rand.nextInt(2) == 0) {
                                bid = Blocks.cobblestone;
                            }

                            if (world.rand.nextInt(4) == 0) {
                                bid = Blocks.mossy_cobblestone;
                            }
                        }

                        if (j == 0) {
                            bid = Blocks.stonebrick;
                        }

                        if (k == 1 && j % 3 == 2 && i != 0 && i != basewidth - j * 2 - 1) {
                            bid = Blocks.torch;
                            var70 = 3;
                        }

                        OreSpawnMain.setBlockFast(world, cposx + i + j, cposy + j, cposz + k + j, bid, var70, 2);
                        if (k == basedepth - j * 2 - 1 && j % 3 == 2 && i != 0 && i != basewidth - j * 2 - 1) {
                            var70 = 4;
                            OreSpawnMain.setBlockFast(
                                world,
                                cposx + i + j,
                                cposy + j,
                                cposz + k + j - 1,
                                Blocks.torch,
                                var70,
                                2);
                        }
                    }
                }
            }

            int var72 = 0;

            for (int m = 0; m < baseheight * 2 - 1; m++) {
                int i = -baseheight + m;

                for (int p = -2; p <= 2; p++) {
                    int k = basedepth / 2;
                    k += p;
                    int var33 = m / 2;
                    if (p >= -1 && p <= 1) {
                        if (m % 2 == 1) {
                            bid = world.getBlock(cposx + i, cposy + var33 + 1, cposz + k);
                            if (bid == Blocks.air) {
                                OreSpawnMain.setBlockFast(
                                    world,
                                    cposx + i,
                                    cposy + var33 + 1,
                                    cposz + k,
                                    Blocks.stone_slab,
                                    var72,
                                    2);
                            }
                        }
                    } else {
                        bid = world.getBlock(cposx + i, cposy + var33 + 1, cposz + k);
                        if (bid == Blocks.air) {
                            OreSpawnMain.setBlockFast(
                                world,
                                cposx + i,
                                cposy + var33 + 1,
                                cposz + k,
                                Blocks.stonebrick,
                                var72,
                                2);
                            if (m == 0 || m == baseheight * 2 - 2) {
                                OreSpawnMain.setBlockFast(
                                    world,
                                    cposx + i,
                                    cposy + var33 + 2,
                                    cposz + k,
                                    Blocks.torch,
                                    var72,
                                    2);
                            }
                        }
                    }

                    while (var33 >= 0) {
                        bid = world.getBlock(cposx + i, cposy + var33, cposz + k);
                        if (bid != Blocks.air) {
                            break;
                        }

                        OreSpawnMain.setBlockFast(world, cposx + i, cposy + var33, cposz + k, Blocks.stone, var72, 2);
                        var33--;
                    }
                }
            }

            var72 = 0;

            for (int var49 = 0; var49 < baseheight * 2 - 1; var49++) {
                int i = basewidth + baseheight - var49 - 1;

                for (int p = -2; p <= 2; p++) {
                    int k = basedepth / 2;
                    k += p;
                    int var34 = var49 / 2;
                    if (p >= -1 && p <= 1) {
                        if (var49 % 2 == 1) {
                            bid = world.getBlock(cposx + i, cposy + var34 + 1, cposz + k);
                            if (bid == Blocks.air) {
                                OreSpawnMain.setBlockFast(
                                    world,
                                    cposx + i,
                                    cposy + var34 + 1,
                                    cposz + k,
                                    Blocks.stone_slab,
                                    var72,
                                    2);
                            }
                        }
                    } else {
                        bid = world.getBlock(cposx + i, cposy + var34 + 1, cposz + k);
                        if (bid == Blocks.air) {
                            OreSpawnMain.setBlockFast(
                                world,
                                cposx + i,
                                cposy + var34 + 1,
                                cposz + k,
                                Blocks.stonebrick,
                                var72,
                                2);
                            if (var49 == 0 || var49 == baseheight * 2 - 2) {
                                OreSpawnMain.setBlockFast(
                                    world,
                                    cposx + i,
                                    cposy + var34 + 2,
                                    cposz + k,
                                    Blocks.torch,
                                    var72,
                                    2);
                            }
                        }
                    }

                    while (var34 >= 0) {
                        bid = world.getBlock(cposx + i, cposy + var34, cposz + k);
                        if (bid != Blocks.air) {
                            break;
                        }

                        OreSpawnMain.setBlockFast(world, cposx + i, cposy + var34, cposz + k, Blocks.stone, var72, 2);
                        var34--;
                    }
                }
            }

            var72 = 0;

            for (int var50 = 0; var50 < baseheight * 2 - 1; var50++) {
                int k = -baseheight + var50;

                for (int p = -2; p <= 2; p++) {
                    int i = basewidth / 2;
                    i += p;
                    int var35 = var50 / 2;
                    if (p >= -1 && p <= 1) {
                        if (var50 % 2 == 1) {
                            bid = world.getBlock(cposx + i, cposy + var35 + 1, cposz + k);
                            if (bid == Blocks.air) {
                                OreSpawnMain.setBlockFast(
                                    world,
                                    cposx + i,
                                    cposy + var35 + 1,
                                    cposz + k,
                                    Blocks.stone_slab,
                                    var72,
                                    2);
                            }
                        }
                    } else {
                        bid = world.getBlock(cposx + i, cposy + var35 + 1, cposz + k);
                        if (bid == Blocks.air) {
                            OreSpawnMain.setBlockFast(
                                world,
                                cposx + i,
                                cposy + var35 + 1,
                                cposz + k,
                                Blocks.stonebrick,
                                var72,
                                2);
                            if (var50 == 0 || var50 == baseheight * 2 - 2) {
                                OreSpawnMain.setBlockFast(
                                    world,
                                    cposx + i,
                                    cposy + var35 + 2,
                                    cposz + k,
                                    Blocks.torch,
                                    var72,
                                    2);
                            }
                        }
                    }

                    while (var35 >= 0) {
                        bid = world.getBlock(cposx + i, cposy + var35, cposz + k);
                        if (bid != Blocks.air) {
                            break;
                        }

                        OreSpawnMain.setBlockFast(world, cposx + i, cposy + var35, cposz + k, Blocks.stone, var72, 2);
                        var35--;
                    }
                }
            }

            var72 = 0;

            for (int var51 = 0; var51 < baseheight * 2 - 1; var51++) {
                int k = basedepth + baseheight - var51 - 1;

                for (int p = -2; p <= 2; p++) {
                    int i = basewidth / 2;
                    i += p;
                    int var36 = var51 / 2;
                    if (p >= -1 && p <= 1) {
                        if (var51 % 2 == 1) {
                            bid = world.getBlock(cposx + i, cposy + var36 + 1, cposz + k);
                            if (bid == Blocks.air) {
                                OreSpawnMain.setBlockFast(
                                    world,
                                    cposx + i,
                                    cposy + var36 + 1,
                                    cposz + k,
                                    Blocks.stone_slab,
                                    var72,
                                    2);
                            }
                        }
                    } else {
                        bid = world.getBlock(cposx + i, cposy + var36 + 1, cposz + k);
                        if (bid == Blocks.air) {
                            OreSpawnMain.setBlockFast(
                                world,
                                cposx + i,
                                cposy + var36 + 1,
                                cposz + k,
                                Blocks.stonebrick,
                                var72,
                                2);
                            if (var51 == 0 || var51 == baseheight * 2 - 2) {
                                OreSpawnMain.setBlockFast(
                                    world,
                                    cposx + i,
                                    cposy + var36 + 2,
                                    cposz + k,
                                    Blocks.torch,
                                    var72,
                                    2);
                            }
                        }
                    }

                    while (var36 >= 0) {
                        bid = world.getBlock(cposx + i, cposy + var36, cposz + k);
                        if (bid != Blocks.air) {
                            break;
                        }

                        OreSpawnMain.setBlockFast(world, cposx + i, cposy + var36, cposz + k, Blocks.stone, var72, 2);
                        var36--;
                    }
                }
            }

            cposx += baseheight;
            cposy += baseheight;
            cposz += baseheight;

            for (int var37 = 0; var37 < height; var37++) {
                for (int i = 0; i < width; i++) {
                    for (int k = 0; k < depth; k++) {
                        bid = Blocks.air;
                        var72 = 0;
                        if (i == 0 || k == 0 || i == width - 1 || k == depth - 1) {
                            bid = Blocks.stone;
                            if (world.rand.nextInt(2) == 0) {
                                bid = Blocks.cobblestone;
                            }

                            if (world.rand.nextInt(4) == 0) {
                                bid = Blocks.mossy_cobblestone;
                            }
                        }

                        if (var37 == 0 || var37 == height - 1) {
                            bid = Blocks.stonebrick;
                        }

                        if (var37 == 1 || var37 == 2 || var37 == 3) {
                            if ((k == 0 || k == depth - 1) && i >= width / 2 - 1 && i <= width / 2 + 1) {
                                if (var37 == 3) {
                                    bid = Blocks.fence;
                                } else {
                                    bid = Blocks.air;
                                }
                            }

                            if ((i == 0 || i == width - 1) && k >= depth / 2 - 1 && k <= depth / 2 + 1) {
                                if (var37 == 3) {
                                    bid = Blocks.fence;
                                } else {
                                    bid = Blocks.air;
                                }
                            }
                        }

                        if ((var37 == height - 3 || var37 == height - 2) && (i + k) % 2 == 1) {
                            if (var37 == height - 3) {
                                if (bid != Blocks.air) {
                                    bid = Blocks.lit_redstone_lamp;
                                }
                            } else {
                                bid = Blocks.air;
                            }
                        }

                        OreSpawnMain.setBlockFast(world, cposx + i, cposy + var37, cposz + k, bid, var72, 2);
                    }
                }
            }

            Block var69 = Blocks.stone_slab;
            var72 = 0;
            int var38 = height;

            for (int i = -1; i <= width; i++) {
                for (int k = -1; k <= depth; k++) {
                    if ((i == -1 || k == -1 || i == width || k == depth) && (i + k & 1) == 1) {
                        OreSpawnMain.setBlockFast(world, cposx + i, cposy + var38, cposz + k, var69, var72, 2);
                    }
                }
            }

            this.makepoolalter(world, cposx + 1, cposy, cposz + 1);
            this.makepoolalter(world, cposx + width - 2, cposy, cposz + depth - 2);
            this.makepoolalter(world, cposx + 1, cposy, cposz + depth - 2);
            this.makepoolalter(world, cposx + width - 2, cposy, cposz + 1);
            this.makepoolalter(world, cposx + width / 2, cposy, cposz + depth / 2);
            world
                .setBlock(cposx + width / 2 - 1, cposy + 2, cposz + depth / 2 - 1, OreSpawnMain.CreeperRepellent, 0, 2);
            world
                .setBlock(cposx + width / 2 + 1, cposy + 2, cposz + depth / 2 + 1, OreSpawnMain.CreeperRepellent, 0, 2);
            world
                .setBlock(cposx + width / 2 - 1, cposy + 2, cposz + depth / 2 + 1, OreSpawnMain.CreeperRepellent, 0, 2);
            world
                .setBlock(cposx + width / 2 + 1, cposy + 2, cposz + depth / 2 - 1, OreSpawnMain.CreeperRepellent, 0, 2);
            world.setBlock(cposx + width / 2 - 2, cposy + 1, cposz + depth / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this
                .getSpawnerTileEntity(world, cposx + width / 2 - 2, cposy + 1, cposz + depth / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Molenoid");
            }

            OreSpawnMain
                .setBlockFast(world, cposx + width / 2 + 2, cposy + 1, cposz + depth / 2, Blocks.trapdoor, 3, 2);
            OreSpawnMain.setBlockFast(world, cposx + width / 2 + 2, cposy, cposz + depth / 2, Blocks.air, 0, 2);
            int var32 = cposx + width / 2 + 2;
            int kx = cposz + depth / 2;

            for (int var39 = 1; var39 < baseheight; var39++) {
                OreSpawnMain.setBlockFast(world, var32, cposy - var39, kx + 1, Blocks.cobblestone, 0, 2);
                OreSpawnMain.setBlockFast(world, var32, cposy - var39, kx, Blocks.ladder, 2, 2);
            }

            this.makeincagraves(
                world,
                cposx - baseheight,
                cposy - baseheight,
                cposz - baseheight,
                basewidth,
                basedepth);
        }
    }

    private void makepoolalter(World world, int cposx, int cposy, int cposz) {
        for (int i = -1; i <= 1; i++) {
            for (int k = -1; k <= 1; k++) {
                OreSpawnMain.setBlockFast(world, cposx + i, cposy + 1, cposz + k, Blocks.cobblestone, 0, 2);
            }
        }

        OreSpawnMain.setBlockFast(world, cposx, cposy + 1, cposz, Blocks.water, 0, 2);
    }

    private void makeincagraves(World world, int cposx, int cposy, int cposz, int width, int depth) {
        for (int i = 5; i < width - 5; i += 6) {
            this.makeincagrave(world, cposx + i, cposy, cposz + 5, 1);
        }

        for (int var8 = 5; var8 < width - 5; var8 += 6) {
            this.makeincagrave(world, cposx + var8, cposy, cposz + 10, 1);
        }

        for (int var9 = 5; var9 < width - 5; var9 += 6) {
            this.makeincagrave(world, cposx + var9, cposy, cposz + 20, 3);
        }

        for (int var10 = 5; var10 < width - 5; var10 += 6) {
            this.makeincagrave(world, cposx + var10, cposy, cposz + 25, 3);
        }
    }

    private void makeincagrave(World world, int cposx, int cposy, int cposz, int dir) {
        TileEntityMobSpawner tileentitymobspawner = null;
        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        chestContents = this.IncaPyramidContentsList;
        if (dir == 1) {
            OreSpawnMain.setBlockFast(world, cposx - 1, cposy, cposz, Blocks.grass, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx - 1, cposy + 1, cposz, Blocks.red_flower, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx - 1, cposy, cposz + 1, Blocks.grass, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx - 1, cposy + 1, cposz + 1, Blocks.yellow_flower, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx - 1, cposy, cposz + 2, Blocks.grass, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx - 1, cposy + 1, cposz + 2, Blocks.red_flower, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx + 1, cposy, cposz, Blocks.grass, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx + 1, cposy + 1, cposz, Blocks.red_flower, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx + 1, cposy, cposz + 1, Blocks.grass, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx + 1, cposy + 1, cposz + 1, Blocks.yellow_flower, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx + 1, cposy, cposz + 2, Blocks.grass, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx + 1, cposy + 1, cposz + 2, Blocks.red_flower, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx, cposy + 1, cposz, Blocks.stone, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx, cposy + 1, cposz + 1, Blocks.stone_slab, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx, cposy + 1, cposz + 2, Blocks.stone_slab, 0, 2);
            if (world.rand.nextInt(3) == 1) {
                world.setBlock(cposx, cposy + 2, cposz, Blocks.mob_spawner, 0, 2);
                tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 2, cposz);
                if (tileentitymobspawner != null) {
                    tileentitymobspawner.func_145881_a()
                        .setEntityName("Ghost");
                }
            }

            world.setBlock(cposx, cposy + 1, cposz - 1, Blocks.chest, 2, 2);
            chest = this.getChestTileEntity(world, cposx, cposy + 1, cposz - 1);
            if (chest != null) {
                WeightedRandomChestContent
                    .generateChestContents(world.rand, chestContents, chest, 10 + world.rand.nextInt(5));
            }
        }

        if (dir == 3) {
            OreSpawnMain.setBlockFast(world, cposx - 1, cposy, cposz, Blocks.grass, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx - 1, cposy + 1, cposz, Blocks.red_flower, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx - 1, cposy, cposz - 1, Blocks.grass, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx - 1, cposy + 1, cposz - 1, Blocks.yellow_flower, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx - 1, cposy, cposz - 2, Blocks.grass, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx - 1, cposy + 1, cposz - 2, Blocks.red_flower, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx + 1, cposy, cposz, Blocks.grass, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx + 1, cposy + 1, cposz, Blocks.red_flower, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx + 1, cposy, cposz - 1, Blocks.grass, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx + 1, cposy + 1, cposz - 1, Blocks.yellow_flower, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx + 1, cposy, cposz - 2, Blocks.grass, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx + 1, cposy + 1, cposz - 2, Blocks.red_flower, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx, cposy + 1, cposz, Blocks.stone, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx, cposy + 1, cposz - 1, Blocks.stone_slab, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx, cposy + 1, cposz - 2, Blocks.stone_slab, 0, 2);
            if (world.rand.nextInt(3) == 1) {
                world.setBlock(cposx, cposy + 2, cposz, Blocks.mob_spawner, 0, 2);
                tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 2, cposz);
                if (tileentitymobspawner != null) {
                    tileentitymobspawner.func_145881_a()
                        .setEntityName("Ghost");
                }
            }

            world.setBlock(cposx, cposy + 1, cposz + 1, Blocks.chest, 2, 2);
            chest = this.getChestTileEntity(world, cposx, cposy + 1, cposz + 1);
            if (chest != null) {
                WeightedRandomChestContent
                    .generateChestContents(world.rand, chestContents, chest, 10 + world.rand.nextInt(5));
            }
        }
    }

    public void makeRobotLab(World world, int cposx, int cposy, int cposz) {
        Block bid = Blocks.air;
        int meta = 0;
        int width = 10;
        int length = 20;
        int height = 5;
        if (!world.isRemote) {
            for (int j = 0; j <= height; j++) {
                for (int i = 0; i < width; i++) {
                    for (int k = 0; k < length; k++) {
                        bid = Blocks.air;
                        if (i == 0 || k == 0 || i == width - 1 || k == length - 1) {
                            bid = Blocks.quartz_block;
                        }

                        if (j == 0) {
                            bid = Blocks.quartz_block;
                            if (i == width / 2 || i == width / 2 - 1) {
                                bid = Blocks.iron_block;
                            }
                        }

                        if (j == height) {
                            bid = Blocks.quartz_block;
                            if (i == 0 || k == 0 || i == width - 1 || k == length - 1) {
                                bid = Blocks.air;
                            }
                        }

                        OreSpawnMain.setBlockFast(world, cposx + i, cposy + j, cposz + k, bid, 0, 2);
                    }
                }
            }

            OreSpawnMain.setBlockFast(world, cposx + width / 2, cposy + 1, cposz, Blocks.air, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx + width / 2, cposy + 2, cposz, Blocks.air, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx + width / 2 - 1, cposy + 1, cposz, Blocks.air, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx + width / 2 - 1, cposy + 2, cposz, Blocks.air, 0, 2);
            ItemDoor.placeDoorBlock(world, cposx + width / 2, cposy + 1, cposz, 3, Blocks.iron_door);
            ItemDoor.placeDoorBlock(world, cposx + width / 2 - 1, cposy + 1, cposz, 3, Blocks.iron_door);
            OreSpawnMain.setBlockFast(world, cposx + width / 2 - 2, cposy + 2, cposz - 1, Blocks.stone_button, 4, 2);
            OreSpawnMain.setBlockFast(world, cposx + width / 2 + 1, cposy + 2, cposz - 1, Blocks.stone_button, 4, 2);
            this.makerobomain(world, cposx, cposy, cposz + length - 1);
            this.makerobopillar(world, cposx, cposy, cposz + length / 3, 0);
            this.makerobopillar(world, cposx, cposy, cposz + length * 2 / 3, 0);
            this.makerobopillar(world, cposx, cposy, cposz + (length - 1), 0);
            this.makerobopillar(world, cposx + width - 1, cposy, cposz + length / 3, 1);
            this.makerobopillar(world, cposx + width - 1, cposy, cposz + length * 2 / 3, 1);
            this.makerobopillar(world, cposx + width - 1, cposy, cposz + (length - 1), 1);
        }
    }

    private void makerobopillar(World world, int cposx, int cposy, int cposz, int dir) {
        TileEntityMobSpawner tileentitymobspawner = null;

        for (int j = 0; j < 5; j++) {
            for (int i = -1; i < 2; i++) {
                for (int k = -1; k < 2; k++) {
                    Block bid = Blocks.quartz_block;
                    if (j == 2 || j == 3) {
                        if (k == 0 && (i == -1 || i == 1)) {
                            bid = Blocks.redstone_block;
                        }

                        if (i == 0 && (k == -1 || k == 1)) {
                            bid = Blocks.redstone_block;
                        }
                    }

                    OreSpawnMain.setBlockFast(world, cposx + i, cposy + j, cposz + k, bid, 0, 2);
                }
            }
        }

        if (dir == 0) {
            world.setBlock(cposx + 1, cposy + 1, cposz, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 1, cposy + 1, cposz);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Robo-Sniper");
            }
        }

        if (dir == 1) {
            world.setBlock(cposx - 1, cposy + 1, cposz, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 1, cposy + 1, cposz);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Robo-Sniper");
            }
        }
    }

    public void makerobomain(World world, int cposx, int cposy, int cposz) {
        Block bid = Blocks.air;
        int width = 30;
        int length = 30;
        int height = 9;
        cposx -= 10;

        for (int j = 0; j <= height; j++) {
            for (int i = 0; i < width; i++) {
                for (int k = 0; k < length; k++) {
                    bid = Blocks.air;
                    if (i == 0 || k == 0 || i == width - 1 || k == length - 1) {
                        bid = Blocks.quartz_block;
                    }

                    if (j == 0) {
                        bid = Blocks.quartz_block;
                        if (i == width / 2 || i == width / 2 - 1) {
                            bid = Blocks.iron_block;
                        }
                    }

                    if (j == height) {
                        bid = Blocks.quartz_block;
                        if (i == 0 || k == 0 || i == width - 1 || k == length - 1) {
                            bid = Blocks.air;
                        }
                    }

                    if ((j == 1 || j == 2 || j == 3) && k == 0 && i >= width / 3 && i < width * 2 / 3) {
                        bid = Blocks.air;
                    }

                    OreSpawnMain.setBlockFast(world, cposx + i, cposy + j, cposz + k, bid, 0, 2);
                }
            }
        }

        this.makeroboaltar(world, cposx + width / 2 - 4, cposy, cposz + 6);
        this.makeroborailway(world, cposx + 3, cposy, cposz + 10);
        this.makeroboassemblyline(world, cposx + width - 4, cposy, cposz + 4);
        this.makerobotreasureroom(world, cposx + 9, cposy, cposz + 18);
        this.makerobotower(world, cposx + width / 2 - 6, cposy + height, cposz + length / 2 - 6);
    }

    public void makerobotower(World world, int cposx, int cposy, int cposz) {
        Block bid = Blocks.air;

        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 12; i++) {
                for (int k = 0; k < 12; k++) {
                    bid = Blocks.air;
                    if (j == 1) {
                        if (i == 0 || k == 0 || i == 11 || k == 11) {
                            bid = Blocks.iron_bars;
                        }

                        if (i == 0 && (k == 0 || k == 11)) {
                            bid = Blocks.redstone_block;
                        }

                        if (i == 11 && (k == 0 || k == 11)) {
                            bid = Blocks.redstone_block;
                        }
                    }

                    if (j == 0) {
                        bid = Blocks.quartz_block;
                    }

                    OreSpawnMain.setBlockFast(world, cposx + i, cposy + j, cposz + k, bid, 0, 2);
                }
            }
        }

        this.makerobopillar(world, cposx + 4, cposy + 1, cposz + 4, 1);
        this.makerobopillar(world, cposx + 7, cposy + 1, cposz + 7, 0);
        this.makerobopillar(world, cposx + 4, cposy + 1, cposz + 7, 1);
        this.makerobopillar(world, cposx + 7, cposy + 1, cposz + 4, 0);

        for (int var11 = 5; var11 < 35; var11++) {
            for (int i = 0; i < 2; i++) {
                for (int k = 0; k < 3; k++) {
                    bid = Blocks.air;
                    if (var11 < 15) {
                        bid = Blocks.quartz_block;
                    } else if (var11 < 25) {
                        bid = Blocks.quartz_block;
                        if (k == 2) {
                            bid = Blocks.iron_bars;
                        }
                    } else {
                        bid = Blocks.quartz_block;
                        if (k == 1) {
                            bid = Blocks.iron_bars;
                        }

                        if (k == 2) {
                            bid = Blocks.air;
                        }
                    }

                    OreSpawnMain.setBlockFast(world, cposx + i + 5, cposy + var11, cposz + k + 5, bid, 0, 2);
                }
            }
        }
    }

    public void makeroboaltar(World world, int cposx, int cposy, int cposz) {
        Block bid = Blocks.air;
        TileEntityMobSpawner tileentitymobspawner = null;
        bid = Blocks.iron_block;

        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 8; k++) {
                OreSpawnMain.setBlockFast(world, cposx + i, cposy, cposz + k, bid, 0, 2);
            }
        }

        bid = Blocks.quartz_block;

        for (int var10 = 0; var10 < 6; var10++) {
            for (int k = 0; k < 6; k++) {
                OreSpawnMain.setBlockFast(world, cposx + var10 + 1, cposy + 1, cposz + k + 1, bid, 0, 2);
            }
        }

        OreSpawnMain.setBlockFast(world, cposx + 2, cposy + 1, cposz + 2, Blocks.redstone_block, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + 2, cposy + 2, cposz + 2, Blocks.torch, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + 5, cposy + 1, cposz + 5, Blocks.redstone_block, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + 5, cposy + 2, cposz + 5, Blocks.torch, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + 5, cposy + 1, cposz + 2, Blocks.redstone_block, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + 5, cposy + 2, cposz + 2, Blocks.torch, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + 2, cposy + 1, cposz + 5, Blocks.redstone_block, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + 2, cposy + 2, cposz + 5, Blocks.torch, 0, 2);
        world.setBlock(cposx + 3, cposy + 2, cposz + 3, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 3, cposy + 2, cposz + 3);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Robo-Pounder");
        }

        world.setBlock(cposx + 4, cposy + 2, cposz + 4, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 4, cposy + 2, cposz + 4);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Robo-Pounder");
        }
    }

    public void makeroborailway(World world, int cposx, int cposy, int cposz) {
        OreSpawnMain.setBlockFast(world, cposx + 0, cposy + 1, cposz + 0, Blocks.rail, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + 3, cposy + 1, cposz + 0, Blocks.rail, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + 0, cposy + 1, cposz + 1, Blocks.rail, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + 3, cposy + 1, cposz + 1, Blocks.rail, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + 0, cposy + 1, cposz + 2, Blocks.golden_rail, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + 1, cposy + 1, cposz + 2, Blocks.lever, 5, 2);
        OreSpawnMain.setBlockFast(world, cposx + 2, cposy + 1, cposz + 2, Blocks.lever, 5, 2);
        OreSpawnMain.setBlockFast(world, cposx + 3, cposy + 1, cposz + 2, Blocks.golden_rail, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + 0, cposy + 1, cposz + 3, Blocks.rail, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + 3, cposy + 1, cposz + 3, Blocks.rail, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + 0, cposy + 1, cposz + 4, Blocks.rail, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + 3, cposy + 1, cposz + 4, Blocks.rail, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + 0, cposy + 1, cposz + 5, Blocks.rail, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + 3, cposy + 1, cposz + 5, Blocks.rail, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + 0, cposy + 1, cposz + 6, Blocks.golden_rail, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + 1, cposy + 1, cposz + 6, Blocks.lever, 5, 2);
        OreSpawnMain.setBlockFast(world, cposx + 2, cposy + 1, cposz + 6, Blocks.lever, 5, 2);
        OreSpawnMain.setBlockFast(world, cposx + 3, cposy + 1, cposz + 6, Blocks.golden_rail, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + 0, cposy + 1, cposz + 7, Blocks.rail, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + 3, cposy + 1, cposz + 7, Blocks.rail, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + 0, cposy + 1, cposz + 8, Blocks.rail, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + 3, cposy + 1, cposz + 8, Blocks.rail, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + 0, cposy + 1, cposz + 9, Blocks.rail, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + 3, cposy + 1, cposz + 9, Blocks.rail, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + 0, cposy + 1, cposz + 10, Blocks.golden_rail, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + 1, cposy + 1, cposz + 10, Blocks.lever, 5, 2);
        OreSpawnMain.setBlockFast(world, cposx + 2, cposy + 1, cposz + 10, Blocks.lever, 5, 2);
        OreSpawnMain.setBlockFast(world, cposx + 3, cposy + 1, cposz + 10, Blocks.golden_rail, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + 0, cposy + 1, cposz + 11, Blocks.rail, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + 3, cposy + 1, cposz + 11, Blocks.rail, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + 0, cposy + 1, cposz + 12, Blocks.rail, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + 3, cposy + 1, cposz + 12, Blocks.rail, 0, 2);
    }

    public void makeroboassemblyline(World world, int cposx, int cposy, int cposz) {
        for (int k = 0; k < 24; k++) {
            if (k % 3 == 1) {
                OreSpawnMain.setBlockFast(world, cposx - 2, cposy + 1, cposz + k, Blocks.quartz_stairs, 1, 2);
                OreSpawnMain.setBlockFast(world, cposx, cposy + 2, cposz + k, Blocks.sticky_piston, 3, 2);
                OreSpawnMain.setBlockFast(world, cposx, cposy + 3, cposz + k, Blocks.carpet, 0, 2);
            }

            if (k % 3 == 0) {
                OreSpawnMain.setBlockFast(world, cposx, cposy + 2, cposz + k, Blocks.lever, 13, 2);
            }

            OreSpawnMain.setBlockFast(world, cposx, cposy + 1, cposz + k, Blocks.quartz_block, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx + 1, cposy + 1, cposz + k, Blocks.quartz_block, 0, 2);
        }
    }

    public void makerobotreasureroom(World world, int cposx, int cposy, int cposz) {
        Block bid = Blocks.air;
        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        TileEntityMobSpawner tileentitymobspawner = null;
        chestContents = this.RobotContentsList;

        for (int j = 1; j < 7; j++) {
            for (int i = 0; i < 12; i++) {
                for (int k = 0; k < 8; k++) {
                    bid = Blocks.air;
                    if (i == 0 || k == 0 || i == 11 || k == 7) {
                        bid = Blocks.quartz_block;
                    }

                    if (j == 2 && i == 11) {
                        bid = Blocks.iron_bars;
                    }

                    if (j == 3 && bid != Blocks.air) {
                        bid = Blocks.iron_bars;
                    }

                    if ((j == 1 || j == 2 || j == 3) && k == 0 && (i == 1 || i == 2)) {
                        bid = Blocks.air;
                    }

                    OreSpawnMain.setBlockFast(world, cposx + i, cposy + j, cposz + k, bid, 0, 2);
                }
            }
        }

        world.setBlock(cposx + 10, cposy + 1, cposz + 1, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 10, cposy + 1, cposz + 1);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Robo-Warrior");
        }

        world.setBlock(cposx + 8, cposy + 1, cposz + 1, Blocks.chest, 2, 2);
        chest = this.getChestTileEntity(world, cposx + 8, cposy + 1, cposz + 1);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 10 + world.rand.nextInt(5));
        }

        world.setBlock(cposx + 6, cposy + 1, cposz + 1, Blocks.chest, 2, 2);
        chest = this.getChestTileEntity(world, cposx + 6, cposy + 1, cposz + 1);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 10 + world.rand.nextInt(5));
        }
    }

    public void makeKingAltar(World world, int cposx, int cposy, int cposz) {
        Block bid = Blocks.air;
        int width = 51;
        int length = 51;
        int height = 48;
        if (!world.isRemote) {
            for (int j = 0; j <= height + 10; j++) {
                for (int i = -5; i < width + 5; i++) {
                    for (int k = -5; k < length + 5; k++) {
                        bid = Blocks.air;
                        OreSpawnMain.setBlockFast(world, cposx + i, cposy + j, cposz + k, bid, 0, 2);
                    }
                }
            }

            int var17 = 0;

            for (int i = 0; i < width; i++) {
                for (int k = 0; k < length; k++) {
                    Block var24 = Blocks.grass;
                    OreSpawnMain.setBlockFast(world, cposx + i, cposy + var17, cposz + k, var24, 0, 2);

                    for (int v = 1; v < 10; v++) {
                        bid = world.getBlock(cposx + i, cposy + var17 - v, cposz + k);
                        if (bid == Blocks.air || bid == Blocks.tallgrass || bid == Blocks.water) {
                            bid = Blocks.dirt;
                            OreSpawnMain.setBlockFast(world, cposx + i, cposy + var17 - v, cposz + k, bid, 0, 2);
                        }
                    }
                }
            }

            this.makekingcolumn(world, cposx + 1, cposy + 1, cposz + 1);
            this.makekingcolumn(world, cposx + width - 8, cposy + 1, cposz + length - 8);
            this.makekingcolumn(world, cposx + 1, cposy + 1, cposz + length - 8);
            this.makekingcolumn(world, cposx + width - 8, cposy + 1, cposz + 1);
            var17 = height - 1;
            bid = Blocks.quartz_block;

            for (int var15 = 0; var15 < width; var15++) {
                for (int k = 0; k < length; k++) {
                    OreSpawnMain.setBlockFast(world, cposx + var15, cposy + var17, cposz + k, bid, 0, 2);
                }
            }

            var17 = height;
            bid = Blocks.quartz_block;

            for (int var16 = -1; var16 <= width; var16++) {
                for (int k = -1; k <= length; k++) {
                    OreSpawnMain.setBlockFast(world, cposx + var16, cposy + var17, cposz + k, bid, 0, 2);
                }
            }

            this.makekingbackground(world, cposx + 4, cposy + 10, cposz + 9);
            this.makekingcenteraltar(world, cposx + width / 2, cposy, cposz + length / 2);
        }
    }

    private void makekingcolumn(World world, int cposx, int cposy, int cposz) {
        Block bid = Blocks.air;
        int meta = 0;
        int width = 5;
        int length = 5;
        int height = 44;
        if (!world.isRemote) {
            int j = 0;
            bid = Blocks.quartz_block;

            for (int i = 0; i < width + 2; i++) {
                for (int k = 0; k < length + 2; k++) {
                    OreSpawnMain.setBlockFast(world, cposx + i, cposy + j, cposz + k, bid, meta, 2);
                    OreSpawnMain.setBlockFast(world, cposx + i, cposy + j + height + 1, cposz + k, bid, meta, 2);
                }
            }

            cposx++;
            cposz++;
            cposy++;

            for (int var18 = 0; var18 < height; var18++) {
                for (int var17 = 0; var17 < width; var17++) {
                    for (int k = 0; k < length; k++) {
                        bid = Blocks.air;
                        if (var17 == 0 || k == 0 || var17 == width - 1 || k == length - 1) {
                            bid = Blocks.quartz_block;
                        }

                        if (var18 % 4 == 0 && bid != Blocks.air && (var17 == 2 || k == 2)) {
                            bid = Blocks.gold_block;
                        }

                        if (var18 % 4 == 1 && bid != Blocks.air) {
                            if (var17 == 1 || k == 1) {
                                bid = Blocks.gold_block;
                            }

                            if (var17 == 3 || k == 3) {
                                bid = Blocks.gold_block;
                            }
                        }

                        if (var18 % 4 == 2 && bid != Blocks.air) {
                            if (var17 == 1 || k == 1) {
                                bid = Blocks.gold_block;
                            }

                            if (var17 == 3 || k == 3) {
                                bid = Blocks.gold_block;
                            }

                            if (var17 == 2 || k == 2) {
                                bid = Blocks.emerald_block;
                            }
                        }

                        if (var18 % 4 == 3 && bid != Blocks.air) {
                            if (var17 == 1 || k == 1) {
                                bid = Blocks.gold_block;
                            }

                            if (var17 == 3 || k == 3) {
                                bid = Blocks.gold_block;
                            }
                        }

                        int var22 = 0;
                        if (bid == Blocks.quartz_block) {
                            var22 = 2;
                        }

                        OreSpawnMain.setBlockFast(world, cposx + var17, cposy + var18, cposz + k, bid, var22, 2);
                    }
                }
            }
        }
    }

    private void makekingbackground(World world, int cposx, int cposy, int cposz) {
        Block bid = Blocks.air;
        int meta = 0;
        int curz = 0;
        int cury = 0;
        int height = 33;
        int width = 33;
        bid = Blocks.stone;

        for (int m = 0; m < this.king.length; m++) {
            int v = this.king[m];
            if (v < 0) {
                for (bid = Blocks.stone; curz < width; curz++) {
                    OreSpawnMain.setBlockFast(world, cposx, cposy + cury, cposz + curz, bid, 0, 2);
                }

                cury++;
                curz = 0;
            } else {
                for (int n = 0; n < v; n++) {
                    OreSpawnMain.setBlockFast(world, cposx, cposy + cury, cposz + curz, bid, 0, 2);
                    curz++;
                }

                if (bid == Blocks.stone) {
                    bid = Blocks.quartz_block;
                } else {
                    bid = Blocks.stone;
                }
            }
        }

        for (int i = 0; i < width; i++) {
            OreSpawnMain.setBlockFast(world, cposx, cposy - 1, cposz + i, Blocks.gold_block, 0, 2);
        }

        for (int var17 = 0; var17 < width; var17++) {
            OreSpawnMain.setBlockFast(world, cposx, cposy + height, cposz + var17, Blocks.gold_block, 0, 2);
        }

        for (int var18 = -1; var18 <= height; var18++) {
            OreSpawnMain.setBlockFast(world, cposx, cposy + var18, cposz - 1, Blocks.gold_block, 0, 2);
        }

        for (int var19 = -1; var19 <= height; var19++) {
            OreSpawnMain.setBlockFast(world, cposx, cposy + var19, cposz + width, Blocks.gold_block, 0, 2);
        }

        OreSpawnMain.setBlockFast(world, cposx, cposy - 2, cposz - 2, Blocks.diamond_block, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx, cposy + height + 1, cposz + width + 1, Blocks.diamond_block, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx, cposy - 2, cposz + width + 1, Blocks.diamond_block, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx, cposy + height + 1, cposz - 2, Blocks.diamond_block, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx, cposy - 1, cposz - 2, OreSpawnMain.CrystalTorch, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx, cposy + height + 2, cposz + width + 1, OreSpawnMain.CrystalTorch, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx, cposy - 1, cposz + width + 1, OreSpawnMain.CrystalTorch, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx, cposy + height + 2, cposz - 2, OreSpawnMain.CrystalTorch, 0, 2);
    }

    private void makekingcenteraltar(World world, int cposx, int cposy, int cposz) {
        Block bid = Blocks.air;
        int meta = 0;
        int width = 10;
        int length = 10;
        int j = 0;
        bid = Blocks.quartz_block;

        for (int i = -width; i <= width; i++) {
            for (int k = -length; k <= length; k++) {
                OreSpawnMain.setBlockFast(world, cposx + i, cposy + j, cposz + k, bid, 0, 2);
            }
        }

        int var62 = 6;
        int var74 = 20;
        int var25 = 0;
        bid = Blocks.quartz_block;

        for (int var13 = -var62; var13 <= var62; var13++) {
            for (int k = -var74; k <= var74; k++) {
                OreSpawnMain.setBlockFast(world, cposx + var13, cposy + var25, cposz + k, bid, 0, 2);
            }
        }

        var62 = 20;
        var74 = 6;
        var25 = 0;
        bid = Blocks.quartz_block;

        for (int var14 = -var62; var14 <= var62; var14++) {
            for (int k = -var74; k <= var74; k++) {
                OreSpawnMain.setBlockFast(world, cposx + var14, cposy + var25, cposz + k, bid, 0, 2);
            }
        }

        var62 = 8;
        var74 = 8;
        var25 = 1;

        for (int var15 = -var62; var15 <= var62; var15++) {
            for (int k = -var74; k <= var74; k++) {
                bid = Blocks.quartz_block;
                OreSpawnMain.setBlockFast(world, cposx + var15, cposy + var25, cposz + k, bid, 0, 2);
            }
        }

        var62 = 4;
        var74 = 18;
        var25 = 1;

        for (int var16 = -var62; var16 <= var62; var16++) {
            for (int k = -var74; k <= var74; k++) {
                bid = Blocks.quartz_block;
                if (var16 == var62 && (k == -var74 || k == var74)) {
                    bid = Blocks.lapis_block;
                }

                if (var16 == -var62 && (k == -var74 || k == var74)) {
                    bid = Blocks.lapis_block;
                }

                OreSpawnMain.setBlockFast(world, cposx + var16, cposy + var25, cposz + k, bid, 0, 2);
            }
        }

        var62 = 18;
        var74 = 4;
        var25 = 1;

        for (int var17 = -var62; var17 <= var62; var17++) {
            for (int k = -var74; k <= var74; k++) {
                bid = Blocks.quartz_block;
                if (var17 == var62 && (k == -var74 || k == var74)) {
                    bid = Blocks.lapis_block;
                }

                if (var17 == -var62 && (k == -var74 || k == var74)) {
                    bid = Blocks.lapis_block;
                }

                OreSpawnMain.setBlockFast(world, cposx + var17, cposy + var25, cposz + k, bid, 0, 2);
            }
        }

        var62 = 7;
        var74 = 7;
        var25 = 2;
        bid = Blocks.quartz_block;

        for (int var18 = -var62; var18 <= var62; var18++) {
            for (int k = -var74; k <= var74; k++) {
                OreSpawnMain.setBlockFast(world, cposx + var18, cposy + var25, cposz + k, bid, 0, 2);
                if (var18 == var62 && (k == -var74 || k == var74)) {
                    OreSpawnMain.setBlockFast(
                        world,
                        cposx + var18,
                        cposy + var25 + 1,
                        cposz + k,
                        OreSpawnMain.CrystalTorch,
                        0,
                        2);
                }

                if (var18 == -var62 && (k == -var74 || k == var74)) {
                    OreSpawnMain.setBlockFast(
                        world,
                        cposx + var18,
                        cposy + var25 + 1,
                        cposz + k,
                        OreSpawnMain.CrystalTorch,
                        0,
                        2);
                }
            }
        }

        var62 = 3;
        var74 = 17;
        var25 = 2;
        bid = Blocks.quartz_block;

        for (int var19 = -var62; var19 <= var62; var19++) {
            for (int k = -var74; k <= var74; k++) {
                OreSpawnMain.setBlockFast(world, cposx + var19, cposy + var25, cposz + k, bid, 0, 2);
            }
        }

        var62 = 17;
        var74 = 3;
        var25 = 2;
        bid = Blocks.quartz_block;

        for (int var20 = -var62; var20 <= var62; var20++) {
            for (int k = -var74; k <= var74; k++) {
                OreSpawnMain.setBlockFast(world, cposx + var20, cposy + var25, cposz + k, bid, 0, 2);
            }
        }

        var62 = 6;
        var74 = 6;
        var25 = 3;
        bid = Blocks.quartz_block;

        for (int var21 = -var62; var21 <= var62; var21++) {
            for (int k = -var74; k <= var74; k++) {
                OreSpawnMain.setBlockFast(world, cposx + var21, cposy + var25, cposz + k, bid, 0, 2);
            }
        }

        var62 = 2;
        var74 = 16;
        var25 = 3;
        bid = Blocks.quartz_block;

        for (int var22 = -var62; var22 <= var62; var22++) {
            for (int k = -var74; k <= var74; k++) {
                OreSpawnMain.setBlockFast(world, cposx + var22, cposy + var25, cposz + k, bid, 0, 2);
            }
        }

        var62 = 16;
        var74 = 2;
        var25 = 3;
        bid = Blocks.quartz_block;

        for (int var23 = -var62; var23 <= var62; var23++) {
            for (int k = -var74; k <= var74; k++) {
                OreSpawnMain.setBlockFast(world, cposx + var23, cposy + var25, cposz + k, bid, 0, 2);
            }
        }

        var62 = 2;
        var74 = 2;
        var25 = 4;
        bid = Blocks.quartz_block;

        for (int var24 = -var62; var24 <= var62; var24++) {
            for (int k = -var74; k <= var74; k++) {
                OreSpawnMain.setBlockFast(world, cposx + var24, cposy + var25, cposz + k, bid, 0, 2);
                if (var24 == var62 && (k == -var74 || k == var74)) {
                    OreSpawnMain.setBlockFast(
                        world,
                        cposx + var24,
                        cposy + var25 + 1,
                        cposz + k,
                        OreSpawnMain.CrystalTorch,
                        0,
                        2);
                }

                if (var24 == -var62 && (k == -var74 || k == var74)) {
                    OreSpawnMain.setBlockFast(
                        world,
                        cposx + var24,
                        cposy + var25 + 1,
                        cposz + k,
                        OreSpawnMain.CrystalTorch,
                        0,
                        2);
                }
            }
        }

        world.setBlock(cposx, cposy + var25, cposz, Blocks.chest);
        world.setBlockMetadataWithNotify(cposx, cposy + var25, cposz, 2, 3);
        TileEntityChest chest = this.getChestTileEntity(world, cposx, cposy + var25, cposz);
        if (chest != null) {
            chest.setInventorySlotContents(13, new ItemStack(OreSpawnMain.TheKingEgg));
        }
    }

    public void makeLeonNest(World world, int cposx, int cposy, int cposz) {
        Block bid = Blocks.air;
        int rad = 10;
        int dist = 0;
        TileEntityMobSpawner tileentitymobspawner = null;

        for (int j = 0; j <= rad; j++) {
            for (int i = -rad; i <= rad; i++) {
                for (int k = -rad; k <= rad; k++) {
                    bid = Blocks.air;
                    dist = j * j + i * i + k * k;
                    dist = (int) Math.sqrt(dist);
                    if (dist <= rad) {
                        if (dist >= rad - 2) {
                            int which = world.rand.nextInt(6);
                            if (which == 0) {
                                bid = Blocks.leaves;
                            }

                            if (which == 1) {
                                bid = Blocks.log;
                            }

                            if (which == 2) {
                                bid = Blocks.planks;
                            }

                            if (which == 3) {
                                bid = Blocks.dirt;
                            }

                            if (which == 4) {
                                bid = Blocks.cobblestone;
                            }

                            if (which == 5) {
                                bid = Blocks.mossy_cobblestone;
                            }
                        }

                        OreSpawnMain.setBlockFast(world, cposx + i, cposy - j, cposz + k, bid, 0, 2);
                    }
                }
            }
        }

        for (int var15 = 1; var15 <= 5; var15++) {
            for (int i = -rad; i <= rad; i++) {
                for (int kx = -rad; kx <= rad; kx++) {
                    bid = Blocks.air;
                    OreSpawnMain.setBlockFast(world, cposx + i, cposy + var15, cposz + kx, bid, 0, 2);
                }
            }
        }

        world.setBlock(cposx, cposy - (rad - 4), cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy - (rad - 4), cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Leonopteryx");
        }
    }

    public void makeCephadromeAltar(World world, int cposx, int cposy, int cposz) {
        Block bid = Blocks.air;
        int meta = 0;
        int width = 4;
        int length = 4;
        int j = 0;
        bid = Blocks.cobblestone;

        for (int i = -width; i <= width; i++) {
            for (int k = -length; k <= length; k++) {
                OreSpawnMain.setBlockFast(world, cposx + i, cposy + j, cposz + k, bid, 0, 2);
            }
        }

        int var40 = 3;
        int var46 = 3;
        int var18 = 1;
        bid = Blocks.cobblestone;

        for (int var12 = -var40; var12 <= var40; var12++) {
            for (int k = -var46; k <= var46; k++) {
                bid = Blocks.cobblestone;
                if (k == 0 || var12 == 0) {
                    bid = Blocks.stonebrick;
                }

                if ((k == -var46 || k == var46) && (var12 == -var40 || var12 == var40)) {
                    bid = Blocks.stonebrick;
                }

                OreSpawnMain.setBlockFast(world, cposx + var12, cposy + var18, cposz + k, bid, 0, 2);
            }
        }

        var40 = 3;
        var46 = 3;
        var18 = 2;

        for (int var13 = -var40; var13 <= var40; var13++) {
            for (int k = -var46; k <= var46; k++) {
                bid = Blocks.air;
                if ((k == -var46 || k == var46) && (var13 == -var40 || var13 == var40)) {
                    bid = Blocks.stonebrick;
                }

                OreSpawnMain.setBlockFast(world, cposx + var13, cposy + var18, cposz + k, bid, 0, 2);
            }
        }

        var40 = 3;
        var46 = 3;
        var18 = 3;

        for (int var14 = -var40; var14 <= var40; var14++) {
            for (int k = -var46; k <= var46; k++) {
                bid = Blocks.air;
                if ((k == -var46 || k == var46) && (var14 == -var40 || var14 == var40)) {
                    bid = Blocks.end_stone;
                }

                OreSpawnMain.setBlockFast(world, cposx + var14, cposy + var18, cposz + k, bid, 0, 2);
            }
        }

        var40 = 3;
        var46 = 3;
        var18 = 4;

        for (int var15 = -var40; var15 <= var40; var15++) {
            for (int k = -var46; k <= var46; k++) {
                bid = Blocks.air;
                if ((k == -var46 || k == var46) && (var15 == -var40 || var15 == var40)) {
                    bid = OreSpawnMain.ExtremeTorch;
                }

                OreSpawnMain.setBlockFast(world, cposx + var15, cposy + var18, cposz + k, bid, 0, 2);
            }
        }

        var40 = 2;
        var46 = 2;
        var18 = 2;
        bid = Blocks.cobblestone;

        for (int var16 = -var40; var16 <= var40; var16++) {
            for (int k = -var46; k <= var46; k++) {
                bid = Blocks.cobblestone;
                if (k == 0 || var16 == 0) {
                    bid = Blocks.stonebrick;
                }

                if ((k == -var46 || k == var46) && (var16 == -var40 || var16 == var40)) {
                    bid = Blocks.stonebrick;
                }

                OreSpawnMain.setBlockFast(world, cposx + var16, cposy + var18, cposz + k, bid, 0, 2);
            }
        }

        var40 = 1;
        var46 = 1;
        var18 = 3;
        bid = Blocks.cobblestone;

        for (int var17 = -var40; var17 <= var40; var17++) {
            for (int k = -var46; k <= var46; k++) {
                bid = Blocks.cobblestone;
                if (k == 0 && var17 == 0) {
                    bid = OreSpawnMain.MyEyeOfEnderBlock;
                }

                if ((k == -var46 || k == var46) && (var17 == -var40 || var17 == var40)) {
                    bid = Blocks.end_stone;
                }

                OreSpawnMain.setBlockFast(world, cposx + var17, cposy + var18, cposz + k, bid, 0, 2);
            }
        }
    }

    public void makeCrystalBattleTower(World world, int cposx, int cposy, int cposz) {
        TileEntityMobSpawner tileentitymobspawner = null;
        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        float radius = 10.0F;

        for (int j = 0; j <= 20; j++) {
            Block blk = OreSpawnMain.CrystalStone;
            if (j % 5 == 0) {
                for (float currad = 0.0F; currad < radius; currad += 0.33F) {
                    for (float curdeg = 0.0F; curdeg < 360.0F; curdeg += 5.0F) {
                        float curx = (float) (currad * Math.cos(Math.toRadians(curdeg)));
                        float curz = (float) (currad * Math.sin(Math.toRadians(curdeg)));
                        this.FastSetBlock(
                            world,
                            (int) (cposx + curx + 0.5F),
                            cposy + j,
                            (int) (cposz + curz + 0.5F),
                            blk);
                    }
                }
            } else {
                float currad = 10.0F;

                for (float curdeg = 0.0F; curdeg < 360.0F; curdeg += 5.0F) {
                    float curx = (float) (currad * Math.cos(Math.toRadians(curdeg)));
                    float curz = (float) (currad * Math.sin(Math.toRadians(curdeg)));
                    blk = OreSpawnMain.CrystalStone;
                    if (j % 5 >= 1 && j % 5 <= 3 && (curdeg < 10.0F || curdeg > 350.0F)) {
                        blk = Blocks.air;
                    }

                    this.FastSetBlock(world, (int) (cposx + curx + 0.5F), cposy + j, (int) (cposz + curz + 0.5F), blk);
                }
            }
        }

        radius = 10.0F;

        for (int var18 = 21; var18 <= 22; var18++) {
            Block blk = OreSpawnMain.CrystalCrystal;
            float currad = 10.0F;

            for (float curdeg = 0.0F; curdeg < 360.0F; curdeg += 5.0F) {
                float curx = (float) (currad * Math.cos(Math.toRadians(curdeg)));
                float curz = (float) (currad * Math.sin(Math.toRadians(curdeg)));
                this.FastSetBlock(world, (int) (cposx + curx + 0.5F), cposy + var18, (int) (cposz + curz + 0.5F), blk);
            }
        }

        int var19 = 1;
        chestContents = this.CrystalBattleTowerRatContentsList;
        world.setBlock(cposx, cposy + var19 + 1, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + var19 + 1, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Rat");
        }

        world.setBlock(cposx, cposy + var19 + 2, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + var19 + 2, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Rat");
        }

        world.setBlock(cposx, cposy + var19, cposz, Blocks.chest, 0, 2);
        chest = this.getChestTileEntity(world, cposx, cposy + var19, cposz);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 5 + world.rand.nextInt(5));
        }

        var19 = 6;
        chestContents = this.CrystalBattleTowerDungeonBeastContentsList;
        world.setBlock(cposx, cposy + var19 + 1, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + var19 + 1, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Dungeon Beast");
        }

        world.setBlock(cposx, cposy + var19 + 2, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + var19 + 2, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Dungeon Beast");
        }

        world.setBlock(cposx, cposy + var19, cposz, Blocks.chest, 0, 2);
        chest = this.getChestTileEntity(world, cposx, cposy + var19, cposz);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 5 + world.rand.nextInt(5));
        }

        var19 = 11;
        chestContents = this.CrystalBattleTowerUrchinContentsList;
        world.setBlock(cposx, cposy + var19 + 1, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + var19 + 1, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Crystal Urchin");
        }

        world.setBlock(cposx, cposy + var19 + 2, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + var19 + 2, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Crystal Urchin");
        }

        world.setBlock(cposx, cposy + var19, cposz, Blocks.chest, 0, 2);
        chest = this.getChestTileEntity(world, cposx, cposy + var19, cposz);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 5 + world.rand.nextInt(5));
        }

        var19 = 16;
        chestContents = this.CrystalBattleTowerRotatorContentsList;
        world.setBlock(cposx, cposy + var19 + 1, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + var19 + 1, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Rotator");
        }

        world.setBlock(cposx, cposy + var19 + 2, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + var19 + 2, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Rotator");
        }

        world.setBlock(cposx, cposy + var19, cposz, Blocks.chest, 0, 2);
        chest = this.getChestTileEntity(world, cposx, cposy + var19, cposz);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 5 + world.rand.nextInt(5));
        }

        var19 = 21;
        chestContents = this.CrystalBattleTowerVortexContentsList;
        world.setBlock(cposx, cposy + var19 + 1, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + var19 + 1, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Vortex");
        }

        world.setBlock(cposx, cposy + var19 + 2, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + var19 + 2, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Vortex");
        }

        world.setBlock(cposx, cposy + var19, cposz, Blocks.chest, 0, 2);
        chest = this.getChestTileEntity(world, cposx, cposy + var19, cposz);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 6 + world.rand.nextInt(6));
        }
    }

    public void makeGirlfriendIsland(World world, int cposx, int cposy, int cposz) {
        TileEntityMobSpawner tileentitymobspawner = null;
        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        chestContents = this.DamselContentsList;

        for (int i = -5; i <= 5; i++) {
            int k = 3;
            if (i == -5 || i == 5) {
                k = 1;
            }

            if (i == -4 || i == 4) {
                k = 2;
            }

            if (i == -3 || i == 3) {
                k = 2;
            }

            for (int j = -k; j <= k; j++) {
                this.FastSetBlock(world, cposx + i, cposy, cposz + j, Blocks.sand);
                this.FastSetBlock(world, cposx + i, cposy - 1, cposz + j, Blocks.stone);
            }
        }

        for (int var12 = -2; var12 <= 2; var12++) {
            for (int j = -2; j <= 2; j++) {
                this.FastSetBlock(world, cposx + var12, cposy + 3, cposz + j, Blocks.leaves);
            }
        }

        this.FastSetBlock(world, cposx, cposy + 4, cposz, Blocks.leaves);
        this.FastSetBlock(world, cposx, cposy + 3, cposz, Blocks.log);
        this.FastSetBlock(world, cposx, cposy + 2, cposz, Blocks.log);
        this.FastSetBlock(world, cposx, cposy + 1, cposz, Blocks.log);
        this.FastSetBlock(world, cposx + 1, cposy + 3, cposz + 1, Blocks.log);
        this.FastSetBlock(world, cposx - 1, cposy + 3, cposz - 1, Blocks.log);
        this.FastSetBlock(world, cposx + 1, cposy + 3, cposz - 1, Blocks.log);
        this.FastSetBlock(world, cposx - 1, cposy + 3, cposz + 1, Blocks.log);
        world.setBlock(cposx + 1, cposy + 3, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 1, cposy + 3, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Girlfriend");
        }

        world.setBlock(cposx - 1, cposy + 3, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 1, cposy + 3, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Boyfriend");
        }

        world.setBlock(cposx, cposy + 3, cposz + 1, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 3, cposz + 1);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Gold Fish");
        }

        world.setBlock(cposx, cposy + 3, cposz - 1, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 3, cposz - 1);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Gold Fish");
        }

        world.setBlock(cposx, cposy + 1, cposz - 1, Blocks.chest, 0, 2);
        chest = this.getChestTileEntity(world, cposx, cposy + 1, cposz - 1);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 4 + world.rand.nextInt(5));
        }

        world.setBlock(cposx, cposy + 1, cposz + 1, Blocks.chest, 0, 2);
        chest = this.getChestTileEntity(world, cposx, cposy + 1, cposz + 1);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 4 + world.rand.nextInt(5));
        }
    }

    public void makeGreenhouseDungeon(World world, int cposx, int cposy, int cposz) {
        int height = 7;
        int width = 15;
        int length = 23;
        int t = 0;
        TileEntityMobSpawner tileentitymobspawner = null;
        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        chestContents = this.GreenhouseContentsList;

        for (int i = 0; i < length; i++) {
            for (int k = 0; k < width; k++) {
                for (int j = 0; j < height; j++) {
                    Block blk = Blocks.air;
                    if (i == 0 || k == 0 || i == length - 1 || k == width - 1) {
                        blk = Blocks.glass;
                    }

                    if (j == height - 1) {
                        blk = Blocks.iron_block;
                        if (i % 4 == 3 && k % 4 == 3) {
                            blk = Blocks.glowstone;
                        }

                        if (k % 4 == 1) {
                            blk = Blocks.glass;
                        }
                    }

                    if (j == 0) {
                        blk = Blocks.grass;
                        if (i != 0 && k != 0 && i != length - 1 && k != width - 1 && i % 3 == 2) {
                            blk = Blocks.water;
                        }
                    }

                    if (j == 1 && i != 0
                        && k != 0
                        && i != length - 1
                        && k != width - 1
                        && i % 3 != 2
                        && world.rand.nextInt(3) != 1) {
                        blk = Blocks.farmland;
                        this.FastSetBlock(world, cposx + i, cposy + j - 1, cposz + k, blk);
                        t = world.rand.nextInt(20);
                        blk = Blocks.air;
                        if (t == 0) {
                            blk = Blocks.yellow_flower;
                        }

                        if (t == 1) {
                            blk = Blocks.red_flower;
                        }

                        if (t == 2) {
                            blk = Blocks.brown_mushroom;
                        }

                        if (t == 3) {
                            blk = Blocks.red_mushroom;
                        }

                        if (t == 4) {
                            blk = Blocks.wheat;
                        }

                        if (t == 5) {
                            blk = Blocks.carrots;
                        }

                        if (t == 6) {
                            blk = Blocks.potatoes;
                        }

                        if (t == 7) {
                            blk = Blocks.reeds;
                        }

                        if (t == 9) {
                            blk = OreSpawnMain.MyCornPlant1;
                        }

                        if (t == 10) {
                            blk = OreSpawnMain.MyTomatoPlant1;
                        }

                        if (t == 11) {
                            blk = OreSpawnMain.MyStrawberryPlant;
                        }

                        if (t == 12) {
                            blk = OreSpawnMain.MyButterflyPlant;
                        }

                        if (t == 13) {
                            blk = OreSpawnMain.MyMothPlant;
                        }

                        if (t == 14) {
                            blk = OreSpawnMain.MyRadishPlant;
                        }

                        if (t == 15) {
                            blk = OreSpawnMain.MyLettucePlant1;
                        }

                        if (t == 16) {
                            blk = OreSpawnMain.MyFlowerPinkBlock;
                        }

                        if (t == 17) {
                            blk = OreSpawnMain.MyFlowerBlueBlock;
                        }

                        if (t == 18) {
                            blk = OreSpawnMain.MyQuinoaPlant1;
                        }

                        if (t == 19) {
                            blk = OreSpawnMain.MyRicePlant;
                        }
                    }

                    this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, blk);
                }
            }
        }

        for (int var17 = 0; var17 < length; var17++) {
            for (int k = 0; k < width; k++) {
                for (int j = height; j <= height + 6; j++) {
                    this.FastSetBlock(world, cposx + var17, cposy + j, cposz + k, Blocks.air);
                }
            }
        }

        OreSpawnMain.setBlockFast(world, cposx + width / 2, cposy + 1, cposz, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + width / 2, cposy + 2, cposz, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + width / 2 - 1, cposy + 1, cposz, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + width / 2 - 1, cposy + 2, cposz, Blocks.air, 0, 2);
        ItemDoor.placeDoorBlock(world, cposx + width / 2, cposy + 1, cposz, 3, Blocks.iron_door);
        ItemDoor.placeDoorBlock(world, cposx + width / 2 - 1, cposy + 1, cposz, 3, Blocks.iron_door);
        OreSpawnMain.setBlockFast(world, cposx + width / 2 - 2, cposy + 2, cposz, Blocks.stone, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + width / 2 + 1, cposy + 2, cposz, Blocks.stone, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + width / 2 - 2, cposy + 2, cposz - 1, Blocks.stone_button, 4, 2);
        OreSpawnMain.setBlockFast(world, cposx + width / 2 + 1, cposy + 2, cposz - 1, Blocks.stone_button, 4, 2);
        int var18 = length / 2;
        int k = width / 2;
        int j = height + 1;
        world.setBlock(cposx + var18, cposy + j, cposz + k, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var18, cposy + j, cposz + k);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Triffid");
        }

        j = height + 2;
        world.setBlock(cposx + var18, cposy + j, cposz + k, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var18, cposy + j, cposz + k);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Triffid");
        }

        world.setBlock(cposx + var18, cposy + height, cposz + k, Blocks.chest, 0, 2);
        chest = this.getChestTileEntity(world, cposx + var18, cposy + height, cposz + k);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 5 + world.rand.nextInt(5));
        }
    }

    public void makeMonsterIsland(World world, int cposx, int cposy, int cposz) {
        TileEntityMobSpawner tileentitymobspawner = null;
        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        String monster = "Sea Viper";
        chestContents = this.MonsterIslandContentsList;
        if (world.rand.nextInt(2) == 0) {
            monster = "Sea Monster";
        }

        for (int i = -5; i <= 5; i++) {
            int k = 3;
            if (i == -5 || i == 5) {
                k = 1;
            }

            if (i == -4 || i == 4) {
                k = 2;
            }

            if (i == -3 || i == 3) {
                k = 2;
            }

            for (int j = -k; j <= k; j++) {
                this.FastSetBlock(world, cposx + i, cposy, cposz + j, Blocks.sand);
                this.FastSetBlock(world, cposx + i, cposy - 1, cposz + j, Blocks.stone);
            }
        }

        for (int var13 = -2; var13 <= 2; var13++) {
            for (int j = -2; j <= 2; j++) {
                this.FastSetBlock(world, cposx + var13, cposy + 3, cposz + j, Blocks.leaves);
            }
        }

        this.FastSetBlock(world, cposx, cposy + 4, cposz, Blocks.leaves);
        this.FastSetBlock(world, cposx, cposy + 3, cposz, Blocks.log);
        this.FastSetBlock(world, cposx, cposy + 2, cposz, Blocks.log);
        this.FastSetBlock(world, cposx, cposy + 1, cposz, Blocks.log);
        this.FastSetBlock(world, cposx + 1, cposy + 3, cposz + 1, Blocks.log);
        this.FastSetBlock(world, cposx - 1, cposy + 3, cposz - 1, Blocks.log);
        this.FastSetBlock(world, cposx + 1, cposy + 3, cposz - 1, Blocks.log);
        this.FastSetBlock(world, cposx - 1, cposy + 3, cposz + 1, Blocks.log);
        world.setBlock(cposx + 1, cposy + 3, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 1, cposy + 3, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName(monster);
        }

        world.setBlock(cposx - 1, cposy + 3, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 1, cposy + 3, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName(monster);
        }

        world.setBlock(cposx, cposy + 3, cposz + 1, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 3, cposz + 1);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName(monster);
        }

        world.setBlock(cposx, cposy + 3, cposz - 1, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 3, cposz - 1);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName(monster);
        }

        world.setBlock(cposx, cposy + 1, cposz - 1, Blocks.chest, 0, 2);
        chest = this.getChestTileEntity(world, cposx, cposy + 1, cposz - 1);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 4 + world.rand.nextInt(5));
        }

        world.setBlock(cposx, cposy + 1, cposz + 1, Blocks.chest, 0, 2);
        chest = this.getChestTileEntity(world, cposx, cposy + 1, cposz + 1);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 4 + world.rand.nextInt(5));
        }
    }

    public void makeNightmareRookery(World world, int cposx, int cposy, int cposz) {
        TileEntityMobSpawner tileentitymobspawner = null;
        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        String monster = "Nightmare";
        chestContents = this.NightmareRookeryContentsList;
        int h = 0;
        int k = 0;
        int j = 0;
        int i = 0;

        for (int var14 = -5; var14 <= 20; var14++) {
            k += world.rand.nextInt(3) - 1;
            h = world.rand.nextInt(20) + 1;

            for (int var16 = 0; var16 < h; var16++) {
                this.FastSetBlock(world, cposx + var14, cposy + var16, cposz + k, Blocks.stone);
                if (world.rand.nextInt(var16 + 5) == 1) {
                    this.FastSetBlock(world, cposx + var14 + 1, cposy + var16, cposz + k, Blocks.stone);
                }

                if (world.rand.nextInt(var16 + 5) == 1) {
                    this.FastSetBlock(world, cposx + var14 - 1, cposy + var16, cposz + k, Blocks.stone);
                }

                if (world.rand.nextInt(var16 + 5) == 1) {
                    this.FastSetBlock(world, cposx + var14, cposy + var16, cposz + k + 1, Blocks.stone);
                }

                if (world.rand.nextInt(var16 + 5) == 1) {
                    this.FastSetBlock(world, cposx + var14, cposy + var16, cposz + k - 1, Blocks.stone);
                }

                if (var16 >= 18) {
                    world.setBlock(cposx + var14, cposy + var16 + 2, cposz + k, Blocks.mob_spawner, 0, 2);
                    tileentitymobspawner = this
                        .getSpawnerTileEntity(world, cposx + var14, cposy + var16 + 2, cposz + k);
                    if (tileentitymobspawner != null) {
                        tileentitymobspawner.func_145881_a()
                            .setEntityName(monster);
                    }

                    world.setBlock(cposx + var14, cposy + var16 + 1, cposz + k, Blocks.chest, 0, 2);
                    chest = this.getChestTileEntity(world, cposx + var14, cposy + var16 + 1, cposz + k);
                    if (chest != null) {
                        WeightedRandomChestContent
                            .generateChestContents(world.rand, chestContents, chest, 4 + world.rand.nextInt(5));
                    }
                    break;
                }
            }
        }

        for (int var15 = -5; var15 <= 20; var15++) {
            k += world.rand.nextInt(3) - 1;
            h = world.rand.nextInt(20) + 1;

            for (int var17 = 0; var17 < h; var17++) {
                this.FastSetBlock(world, cposx + var15, cposy + var17, cposz + k, Blocks.stone);
                if (world.rand.nextInt(var17 + 5) == 1) {
                    this.FastSetBlock(world, cposx + var15 + 1, cposy + var17, cposz + k, Blocks.stone);
                }

                if (world.rand.nextInt(var17 + 5) == 1) {
                    this.FastSetBlock(world, cposx + var15 - 1, cposy + var17, cposz + k, Blocks.stone);
                }

                if (world.rand.nextInt(var17 + 5) == 1) {
                    this.FastSetBlock(world, cposx + var15, cposy + var17, cposz + k + 1, Blocks.stone);
                }

                if (world.rand.nextInt(var17 + 5) == 1) {
                    this.FastSetBlock(world, cposx + var15, cposy + var17, cposz + k - 1, Blocks.stone);
                }

                if (var17 >= 18) {
                    world.setBlock(cposx + var15, cposy + var17 + 2, cposz + k, Blocks.mob_spawner, 0, 2);
                    tileentitymobspawner = this
                        .getSpawnerTileEntity(world, cposx + var15, cposy + var17 + 2, cposz + k);
                    if (tileentitymobspawner != null) {
                        tileentitymobspawner.func_145881_a()
                            .setEntityName(monster);
                    }

                    world.setBlock(cposx + var15, cposy + var17 + 1, cposz + k, Blocks.chest, 0, 2);
                    chest = this.getChestTileEntity(world, cposx + var15, cposy + var17 + 1, cposz + k);
                    if (chest != null) {
                        WeightedRandomChestContent
                            .generateChestContents(world.rand, chestContents, chest, 4 + world.rand.nextInt(5));
                    }
                    break;
                }
            }
        }
    }

    public void makeStinkyHouse(World world, int cposx, int cposy, int cposz) {
        TileEntityMobSpawner tileentitymobspawner = null;
        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        chestContents = this.StinkyHouseContentsList;
        int height = 2;
        int width = 9;
        int length = 12;
        int yardwidth = 16;
        int yardlength = 24;

        for (int i = 0; i <= yardlength; i++) {
            for (int k = 0; k <= yardwidth; k++) {
                Block bid = Blocks.air;
                if (i == 0 || i == yardlength || k == 0 || k == yardwidth) {
                    bid = Blocks.fence;
                }

                if (bid == Blocks.fence && world.rand.nextInt(3) == 1) {
                    bid = Blocks.air;
                }

                if (bid == Blocks.air && world.rand.nextInt(10) == 1) {
                    bid = Blocks.deadbush;
                }

                if (bid != Blocks.air) {
                    this.FastSetBlock(world, cposx + i - 5, cposy + 1, cposz + k - 4, bid);
                }
            }
        }

        for (int var17 = 0; var17 <= length; var17++) {
            for (int k = 0; k <= width; k++) {
                for (int j = 0; j <= height; j++) {
                    Block bidx = Blocks.air;
                    if (var17 == 0 || var17 == length || k == 0 || k == width) {
                        bidx = Blocks.planks;
                    }

                    if (bidx == Blocks.planks && j == 1
                        && (var17 == 1 || var17 == length - 1 || k == 1 || k == width - 1)) {
                        bidx = Blocks.glass_pane;
                    }

                    if (j == height) {
                        bidx = Blocks.planks;
                    }

                    if (world.rand.nextInt(10) == 1) {
                        bidx = Blocks.air;
                    }

                    if ((j == 0 || j == 1) && var17 == 0 && (k == width / 2 || k == width / 2 + 1)) {
                        bidx = Blocks.air;
                    }

                    this.FastSetBlock(world, cposx + var17, cposy + j + 1, cposz + k, bidx);
                }
            }
        }

        world.setBlock(cposx + 2, cposy + 1, cposz + 2, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 2, cposy + 1, cposz + 2);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Stink Bug");
        }

        world.setBlock(cposx + length - 2, cposy + 1, cposz + width - 2, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + length - 2, cposy + 1, cposz + width - 2);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Stinky");
        }

        world.setBlock(cposx + length / 2, cposy + 1, cposz + width / 2, Blocks.chest, 0, 2);
        chest = this.getChestTileEntity(world, cposx + length / 2, cposy + 1, cposz + width / 2);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 8 + world.rand.nextInt(5));
        }
    }

    public void makeRubberDuckyPond(World world, int cposx, int cposy, int cposz) {
        Block bid = Blocks.air;
        TileEntityMobSpawner tileentitymobspawner = null;
        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        chestContents = this.RubberDuckyContentsList;

        for (int i = 0; i < 2; i++) {
            world.setBlock(cposx + i, cposy + 6, cposz, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + i, cposy + 6, cposz);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Rubber Ducky");
            }
        }

        world.setBlock(cposx, cposy + 5, cposz, Blocks.chest, 0, 2);
        world.setBlock(cposx + 1, cposy + 5, cposz, Blocks.chest, 0, 2);
        chest = this.getChestTileEntity(world, cposx + 1, cposy + 5, cposz);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 8 + world.rand.nextInt(5));
        }

        world.setBlock(cposx, cposy + 4, cposz, Blocks.glass, 0, 2);
        world.setBlock(cposx + 1, cposy + 4, cposz, Blocks.glass, 0, 2);

        for (int var11 = 0; var11 < 2; var11++) {
            world.setBlock(cposx + var11, cposy + 3, cposz, Blocks.water, 0, 3);
        }

        world.setBlock(cposx - 1, cposy + 3, cposz, Blocks.flowing_water, 0, 3);
        world.setBlock(cposx + 2, cposy + 3, cposz, Blocks.flowing_water, 0, 3);

        for (int var12 = 0; var12 < 12; var12++) {
            for (int k = 0; k < 11; k++) {
                bid = Blocks.water;
                if (var12 == 0 || k == 0 || var12 == 11 || k == 10) {
                    bid = Blocks.sand;
                }

                this.FastSetBlock(world, cposx + var12 - 5, cposy, cposz + k - 5, bid);
                bid = Blocks.air;
                this.FastSetBlock(world, cposx + var12 - 5, cposy + 1, cposz + k - 5, bid);
                this.FastSetBlock(world, cposx + var12 - 5, cposy + 2, cposz + k - 5, bid);
            }
        }
    }

    public void makeWhiteHouse(World world, int cposx, int cposy, int cposz) {
        TileEntityMobSpawner tileentitymobspawner = null;
        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        this.makefountain(world, cposx - 5, cposy, cposz - 15);
        this.makefountain(world, cposx + 15, cposy, cposz - 15);
        this.makewalkway(world, cposx + 7, cposy, cposz - 15);
        this.makewhbase(world, cposx - 4, cposy, cposz - 6);
        this.makewhwalls(world, cposx - 3, cposy + 2, cposz - 5);
        this.makewhroof(world, cposx - 4, cposy, cposz - 6);
        this.makewhinterior(world, cposx - 1, cposy + 2, cposz - 3);
    }

    private void makefountain(World world, int cposx, int cposy, int cposz) {
        Block bid = Blocks.air;

        for (int i = 0; i < 7; i++) {
            for (int k = 0; k < 5; k++) {
                for (int j = 0; j < 15; j++) {
                    bid = Blocks.water;
                    if (i == 0 || k == 0 || i == 6 || k == 4) {
                        bid = Blocks.quartz_block;
                    }

                    if (j == 0) {
                        bid = Blocks.quartz_block;
                    }

                    if (j == 1 && i == 3 && k == 2) {
                        bid = Blocks.glowstone;
                    }

                    if (j > 1) {
                        bid = Blocks.air;
                        if (j <= 4 && i == 3 && k == 2) {
                            bid = Blocks.quartz_block;
                        }
                    }

                    this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, bid);
                }
            }
        }

        world.setBlock(cposx + 3, cposy + 5, cposz + 2, Blocks.water, 0, 3);
        world.setBlock(cposx + 2, cposy + 5, cposz + 2, Blocks.flowing_water, 0, 3);
        world.setBlock(cposx + 4, cposy + 5, cposz + 2, Blocks.flowing_water, 0, 3);
    }

    private void makewalkway(World world, int cposx, int cposy, int cposz) {
        Block bid = Blocks.air;

        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 10; k++) {
                for (int j = 0; j < 15; j++) {
                    bid = Blocks.quartz_block;
                    if (j == 1) {
                        bid = Blocks.air;
                        if (k > 6) {
                            bid = Blocks.quartz_block;
                        }
                    }

                    if (j > 1) {
                        bid = Blocks.air;
                    }

                    this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, bid);
                }
            }
        }
    }

    private void makewhbase(World world, int cposx, int cposy, int cposz) {
        Block bid = Blocks.air;

        for (int i = 0; i < 25; i++) {
            for (int k = 0; k < 25; k++) {
                bid = Blocks.quartz_block;
                this.FastSetBlock(world, cposx + i, cposy + 1, cposz + k, bid);
                if ((i == 0 || i == 24) && (k == 0 || k == 24)) {
                    this.FastSetBlock(world, cposx + i, cposy + 2, cposz + k, OreSpawnMain.CrystalTorch);
                }
            }
        }

        for (int var9 = 1; var9 < 24; var9++) {
            for (int kx = 1; kx < 24; kx++) {
                bid = Blocks.quartz_block;
                this.FastSetBlock(world, cposx + var9, cposy + 2, cposz + kx, bid);
            }
        }
    }

    private void makewhwalls(World world, int cposx, int cposy, int cposz) {
        Block bid = Blocks.air;

        for (int i = 0; i < 23; i++) {
            for (int k = 0; k < 23; k++) {
                for (int j = 0; j < 6; j++) {
                    bid = Blocks.air;
                    if (i == 0 || k == 0 || i == 22 || k == 22) {
                        bid = Blocks.quartz_block;
                    }

                    if (j != 0 && bid != Blocks.air) {
                        if (k == 22) {
                            if ((j & 1) == 1) {
                                if ((i & 1) == 0 || (k & 1) == 0) {
                                    bid = Blocks.glass_pane;
                                }
                            } else if ((i & 1) == 1 || (k & 1) == 1) {
                                bid = Blocks.glass_pane;
                            }
                        } else if (k != 0) {
                            if ((j & 1) == 1) {
                                if (i == 2 || k == 2 || i == 20 || k == 20) {
                                    bid = Blocks.glass_pane;
                                }
                            } else if (i == 1 || k == 1 || i == 21 || k == 21) {
                                bid = Blocks.glass_pane;
                            }

                            if (j > 0 && j < 5 && k > 7 && k < 15) {
                                bid = Blocks.glass_pane;
                            }
                        } else if ((j & 1) == 1) {
                            if (i == 2 || k == 2 || i == 20 || k == 20) {
                                bid = Blocks.glass_pane;
                            }
                        } else if (i == 1 || k == 1 || i == 21 || k == 21) {
                            bid = Blocks.glass_pane;
                        }
                    }

                    this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, bid);
                }
            }
        }

        OreSpawnMain.setBlockFast(world, cposx + 11, cposy, cposz, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + 11, cposy + 1, cposz, Blocks.air, 0, 2);
        ItemDoor.placeDoorBlock(world, cposx + 11, cposy, cposz, 3, Blocks.iron_door);
        OreSpawnMain.setBlockFast(world, cposx + 12, cposy + 1, cposz - 1, Blocks.stone_button, 4, 2);
    }

    private void makewhroof(World world, int cposx, int cposy, int cposz) {
        Block bid = Blocks.air;

        for (int j = 0; j < 13; j++) {
            for (int i = 0; i < 25 - 2 * j; i++) {
                for (int k = 0; k < 25 - 2 * j; k++) {
                    bid = Blocks.air;
                    if (i == 0 || k == 0 || i == 24 - 2 * j || k == 24 - 2 * j) {
                        bid = Blocks.quartz_block;
                    }

                    if (j == 0 && bid != Blocks.air && (i + k & 1) == 1) {
                        bid = Blocks.emerald_block;
                    }

                    if (j == 12) {
                        bid = Blocks.emerald_block;
                    }

                    this.FastSetBlock(world, cposx + i + j, cposy + 8 + j, cposz + k + j, bid);
                    if ((i == 0 || i == 24 - 2 * j) && (k == 0 || k == 24 - 2 * j)) {
                        this.FastSetBlock(
                            world,
                            cposx + i + j,
                            cposy + 8 + j + 1,
                            cposz + k + j,
                            OreSpawnMain.CrystalTorch);
                    }
                }
            }
        }

        bid = Blocks.fence;
        this.FastSetBlock(world, cposx + 12, cposy + 8 + 11, cposz + 12, bid);
        this.FastSetBlock(world, cposx + 12, cposy + 8 + 10, cposz + 12, bid);
        this.FastSetBlock(world, cposx + 12, cposy + 8 + 9, cposz + 12, bid);
        this.FastSetBlock(world, cposx + 12, cposy + 8 + 8, cposz + 12, bid);
        this.FastSetBlock(world, cposx + 12, cposy + 8 + 7, cposz + 12, bid);
        this.FastSetBlock(world, cposx + 12, cposy + 8 + 6, cposz + 12, bid);
        this.FastSetBlock(world, cposx + 12, cposy + 8 + 5, cposz + 12, bid);
        this.FastSetBlock(world, cposx + 12, cposy + 8 + 4, cposz + 12, bid);
        this.FastSetBlock(world, cposx + 12, cposy + 8 + 3, cposz + 12, bid);
        this.FastSetBlock(world, cposx + 12, cposy + 8 + 2, cposz + 12, bid);
        this.FastSetBlock(world, cposx + 12, cposy + 8 + 1, cposz + 12, bid);
        this.FastSetBlock(world, cposx + 12, cposy + 8 + 0, cposz + 12, bid);
        this.FastSetBlock(world, cposx + 11, cposy + 8 + 0, cposz + 12, bid);
        this.FastSetBlock(world, cposx + 13, cposy + 8 + 0, cposz + 12, bid);
        this.FastSetBlock(world, cposx + 12, cposy + 8 + 0, cposz + 11, bid);
        this.FastSetBlock(world, cposx + 12, cposy + 8 + 0, cposz + 13, bid);
        bid = OreSpawnMain.CrystalTorch;
        this.FastSetBlock(world, cposx + 11, cposy + 8 + 1, cposz + 12, bid);
        this.FastSetBlock(world, cposx + 13, cposy + 8 + 1, cposz + 12, bid);
        this.FastSetBlock(world, cposx + 12, cposy + 8 + 1, cposz + 11, bid);
        this.FastSetBlock(world, cposx + 12, cposy + 8 + 1, cposz + 13, bid);
    }

    private void makewhinterior(World world, int cposx, int cposy, int cposz) {
        int zoff = 1;
        int xoff = 0;

        for (int i = 0; i < 8; i++) {
            OreSpawnMain.setBlockFast(world, cposx + xoff + i, cposy, cposz + zoff, Blocks.quartz_stairs, 3, 2);
            OreSpawnMain.setBlockFast(world, cposx + xoff + i, cposy, cposz + zoff + 1, Blocks.piston_extension, 1, 2);
            OreSpawnMain.setBlockFast(world, cposx + xoff + i, cposy, cposz + zoff + 2, Blocks.piston_extension, 1, 2);
            OreSpawnMain.setBlockFast(world, cposx + xoff + i, cposy, cposz + zoff + 3, Blocks.quartz_stairs, 2, 2);
        }

        int var22 = 11;

        for (int var14 = 0; var14 < 8; var14++) {
            OreSpawnMain.setBlockFast(world, cposx + var22 + var14, cposy, cposz + zoff, Blocks.quartz_stairs, 3, 2);
            OreSpawnMain
                .setBlockFast(world, cposx + var22 + var14, cposy, cposz + zoff + 1, Blocks.piston_extension, 1, 2);
            OreSpawnMain
                .setBlockFast(world, cposx + var22 + var14, cposy, cposz + zoff + 2, Blocks.piston_extension, 1, 2);
            OreSpawnMain
                .setBlockFast(world, cposx + var22 + var14, cposy, cposz + zoff + 3, Blocks.quartz_stairs, 2, 2);
        }

        int var19 = 7;
        var22 = 0;

        for (int var15 = 0; var15 < 8; var15++) {
            OreSpawnMain.setBlockFast(world, cposx + var22 + var15, cposy, cposz + var19, Blocks.quartz_stairs, 3, 2);
            OreSpawnMain
                .setBlockFast(world, cposx + var22 + var15, cposy, cposz + var19 + 1, Blocks.piston_extension, 1, 2);
            OreSpawnMain
                .setBlockFast(world, cposx + var22 + var15, cposy, cposz + var19 + 2, Blocks.piston_extension, 1, 2);
            OreSpawnMain
                .setBlockFast(world, cposx + var22 + var15, cposy, cposz + var19 + 3, Blocks.quartz_stairs, 2, 2);
        }

        var22 = 11;

        for (int var16 = 0; var16 < 8; var16++) {
            OreSpawnMain.setBlockFast(world, cposx + var22 + var16, cposy, cposz + var19, Blocks.quartz_stairs, 3, 2);
            OreSpawnMain
                .setBlockFast(world, cposx + var22 + var16, cposy, cposz + var19 + 1, Blocks.piston_extension, 1, 2);
            OreSpawnMain
                .setBlockFast(world, cposx + var22 + var16, cposy, cposz + var19 + 2, Blocks.piston_extension, 1, 2);
            OreSpawnMain
                .setBlockFast(world, cposx + var22 + var16, cposy, cposz + var19 + 3, Blocks.quartz_stairs, 2, 2);
        }

        var19 = 13;
        var22 = 0;

        for (int var17 = 0; var17 < 8; var17++) {
            OreSpawnMain.setBlockFast(world, cposx + var22 + var17, cposy, cposz + var19, Blocks.quartz_stairs, 3, 2);
            OreSpawnMain
                .setBlockFast(world, cposx + var22 + var17, cposy, cposz + var19 + 1, Blocks.piston_extension, 1, 2);
            OreSpawnMain
                .setBlockFast(world, cposx + var22 + var17, cposy, cposz + var19 + 2, Blocks.piston_extension, 1, 2);
            OreSpawnMain
                .setBlockFast(world, cposx + var22 + var17, cposy, cposz + var19 + 3, Blocks.quartz_stairs, 2, 2);
        }

        var22 = 11;

        for (int var18 = 0; var18 < 8; var18++) {
            OreSpawnMain.setBlockFast(world, cposx + var22 + var18, cposy, cposz + var19, Blocks.quartz_stairs, 3, 2);
            OreSpawnMain
                .setBlockFast(world, cposx + var22 + var18, cposy, cposz + var19 + 1, Blocks.piston_extension, 1, 2);
            OreSpawnMain
                .setBlockFast(world, cposx + var22 + var18, cposy, cposz + var19 + 2, Blocks.piston_extension, 1, 2);
            OreSpawnMain
                .setBlockFast(world, cposx + var22 + var18, cposy, cposz + var19 + 3, Blocks.quartz_stairs, 2, 2);
        }

        TileEntityMobSpawner tileentitymobspawner = null;
        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        chestContents = this.WhiteHouseContentsList;
        var19 = 18;
        var22 = 2;
        world.setBlock(cposx + var22, cposy + 1, cposz + var19, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var22, cposy + 1, cposz + var19);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Criminal");
        }

        world.setBlock(cposx + var22, cposy, cposz + var19, Blocks.chest, 0, 2);
        chest = this.getChestTileEntity(world, cposx + var22, cposy, cposz + var19);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 3 + world.rand.nextInt(5));
        }

        var22 = 6;
        world.setBlock(cposx + var22, cposy + 1, cposz + var19, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var22, cposy + 1, cposz + var19);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Criminal");
        }

        world.setBlock(cposx + var22, cposy, cposz + var19, Blocks.chest, 0, 2);
        chest = this.getChestTileEntity(world, cposx + var22, cposy, cposz + var19);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 3 + world.rand.nextInt(5));
        }

        var22 = 12;
        world.setBlock(cposx + var22, cposy + 1, cposz + var19, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var22, cposy + 1, cposz + var19);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Criminal");
        }

        world.setBlock(cposx + var22, cposy, cposz + var19, Blocks.chest, 0, 2);
        chest = this.getChestTileEntity(world, cposx + var22, cposy, cposz + var19);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 3 + world.rand.nextInt(5));
        }

        var22 = 16;
        world.setBlock(cposx + var22, cposy + 1, cposz + var19, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var22, cposy + 1, cposz + var19);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Criminal");
        }

        world.setBlock(cposx + var22, cposy, cposz + var19, Blocks.chest, 0, 2);
        chest = this.getChestTileEntity(world, cposx + var22, cposy, cposz + var19);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 3 + world.rand.nextInt(5));
        }
    }

    public void makeQueenAltar(World world, int cposx, int cposy, int cposz) {
        Block bid = Blocks.air;
        int width = 51;
        int length = 51;
        int height = 48;
        if (!world.isRemote) {
            for (int j = 0; j <= height + 10; j++) {
                for (int i = -5; i < width + 5; i++) {
                    for (int k = -5; k < length + 5; k++) {
                        bid = Blocks.air;
                        OreSpawnMain.setBlockFast(world, cposx + i, cposy + j, cposz + k, bid, 0, 2);
                    }
                }
            }

            int var17 = 0;

            for (int i = 0; i < width; i++) {
                for (int k = 0; k < length; k++) {
                    Block var24 = Blocks.grass;
                    OreSpawnMain.setBlockFast(world, cposx + i, cposy + var17, cposz + k, var24, 0, 2);

                    for (int v = 1; v < 10; v++) {
                        bid = world.getBlock(cposx + i, cposy + var17 - v, cposz + k);
                        if (bid == Blocks.air || bid == Blocks.tallgrass || bid == Blocks.water) {
                            bid = Blocks.dirt;
                            OreSpawnMain.setBlockFast(world, cposx + i, cposy + var17 - v, cposz + k, bid, 0, 2);
                        }
                    }
                }
            }

            this.makequeencolumn(world, cposx + 1, cposy + 1, cposz + 1);
            this.makequeencolumn(world, cposx + width - 8, cposy + 1, cposz + length - 8);
            this.makequeencolumn(world, cposx + 1, cposy + 1, cposz + length - 8);
            this.makequeencolumn(world, cposx + width - 8, cposy + 1, cposz + 1);
            var17 = height - 1;
            bid = Blocks.obsidian;

            for (int var15 = 0; var15 < width; var15++) {
                for (int k = 0; k < length; k++) {
                    OreSpawnMain.setBlockFast(world, cposx + var15, cposy + var17, cposz + k, bid, 0, 2);
                }
            }

            var17 = height;
            bid = Blocks.obsidian;

            for (int var16 = -1; var16 <= width; var16++) {
                for (int k = -1; k <= length; k++) {
                    OreSpawnMain.setBlockFast(world, cposx + var16, cposy + var17, cposz + k, bid, 0, 2);
                }
            }

            this.makequeenbackground(world, cposx + 4, cposy + 10, cposz + 9);
            this.makequeencenteraltar(world, cposx + width / 2, cposy, cposz + length / 2);
        }
    }

    private void makequeencolumn(World world, int cposx, int cposy, int cposz) {
        Block bid = Blocks.air;
        int meta = 0;
        int width = 5;
        int length = 5;
        int height = 44;
        if (!world.isRemote) {
            int j = 0;
            bid = Blocks.obsidian;

            for (int i = 0; i < width + 2; i++) {
                for (int k = 0; k < length + 2; k++) {
                    OreSpawnMain.setBlockFast(world, cposx + i, cposy + j, cposz + k, bid, meta, 2);
                    OreSpawnMain.setBlockFast(world, cposx + i, cposy + j + height + 1, cposz + k, bid, meta, 2);
                }
            }

            cposx++;
            cposz++;
            cposy++;

            for (int var18 = 0; var18 < height; var18++) {
                for (int var17 = 0; var17 < width; var17++) {
                    for (int k = 0; k < length; k++) {
                        bid = Blocks.air;
                        if (var17 == 0 || k == 0 || var17 == width - 1 || k == length - 1) {
                            bid = Blocks.obsidian;
                        }

                        if (var18 % 4 == 0 && bid != Blocks.air && (var17 == 2 || k == 2)) {
                            bid = Blocks.redstone_block;
                        }

                        if (var18 % 4 == 1 && bid != Blocks.air) {
                            if (var17 == 1 || k == 1) {
                                bid = Blocks.redstone_block;
                            }

                            if (var17 == 3 || k == 3) {
                                bid = Blocks.redstone_block;
                            }
                        }

                        if (var18 % 4 == 2 && bid != Blocks.air) {
                            if (var17 == 1 || k == 1) {
                                bid = Blocks.redstone_block;
                            }

                            if (var17 == 3 || k == 3) {
                                bid = Blocks.redstone_block;
                            }

                            if (var17 == 2 || k == 2) {
                                bid = OreSpawnMain.MyBlockAmethystBlock;
                            }
                        }

                        if (var18 % 4 == 3 && bid != Blocks.air) {
                            if (var17 == 1 || k == 1) {
                                bid = Blocks.redstone_block;
                            }

                            if (var17 == 3 || k == 3) {
                                bid = Blocks.redstone_block;
                            }
                        }

                        int var22 = 0;
                        OreSpawnMain.setBlockFast(world, cposx + var17, cposy + var18, cposz + k, bid, var22, 2);
                    }
                }
            }
        }
    }

    private void makequeenbackground(World world, int cposx, int cposy, int cposz) {
        Block bid = Blocks.air;
        int meta = 0;
        int curz = 0;
        int cury = 0;
        int height = 33;
        int width = 33;
        bid = Blocks.stone;

        for (int m = 0; m < this.queen.length; m++) {
            int v = this.queen[m];
            if (v < 0) {
                for (bid = Blocks.stone; curz < width; curz++) {
                    OreSpawnMain.setBlockFast(world, cposx, cposy + cury, cposz + curz, bid, 0, 2);
                }

                cury++;
                curz = 0;
            } else {
                for (int n = 0; n < v; n++) {
                    OreSpawnMain.setBlockFast(world, cposx, cposy + cury, cposz + curz, bid, 0, 2);
                    curz++;
                }

                if (bid == Blocks.stone) {
                    bid = OreSpawnMain.MyBlockRubyBlock;
                } else {
                    bid = Blocks.stone;
                }
            }
        }

        for (int i = 0; i < width; i++) {
            OreSpawnMain.setBlockFast(world, cposx, cposy - 1, cposz + i, Blocks.diamond_block, 0, 2);
        }

        for (int var17 = 0; var17 < width; var17++) {
            OreSpawnMain.setBlockFast(world, cposx, cposy + height, cposz + var17, Blocks.diamond_block, 0, 2);
        }

        for (int var18 = -1; var18 <= height; var18++) {
            OreSpawnMain.setBlockFast(world, cposx, cposy + var18, cposz - 1, Blocks.diamond_block, 0, 2);
        }

        for (int var19 = -1; var19 <= height; var19++) {
            OreSpawnMain.setBlockFast(world, cposx, cposy + var19, cposz + width, Blocks.diamond_block, 0, 2);
        }

        OreSpawnMain.setBlockFast(world, cposx, cposy - 2, cposz - 2, Blocks.diamond_block, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx, cposy + height + 1, cposz + width + 1, Blocks.diamond_block, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx, cposy - 2, cposz + width + 1, Blocks.diamond_block, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx, cposy + height + 1, cposz - 2, Blocks.diamond_block, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx, cposy - 1, cposz - 2, OreSpawnMain.CrystalTorch, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx, cposy + height + 2, cposz + width + 1, OreSpawnMain.CrystalTorch, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx, cposy - 1, cposz + width + 1, OreSpawnMain.CrystalTorch, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx, cposy + height + 2, cposz - 2, OreSpawnMain.CrystalTorch, 0, 2);
    }

    private void makequeencenteraltar(World world, int cposx, int cposy, int cposz) {
        Block bid = Blocks.air;
        int meta = 0;
        int width = 10;
        int length = 10;
        int j = 0;
        bid = Blocks.obsidian;

        for (int i = -width; i <= width; i++) {
            for (int k = -length; k <= length; k++) {
                OreSpawnMain.setBlockFast(world, cposx + i, cposy + j, cposz + k, bid, 0, 2);
            }
        }

        int var62 = 6;
        int var74 = 20;
        int var25 = 0;
        bid = Blocks.obsidian;

        for (int var13 = -var62; var13 <= var62; var13++) {
            for (int k = -var74; k <= var74; k++) {
                OreSpawnMain.setBlockFast(world, cposx + var13, cposy + var25, cposz + k, bid, 0, 2);
            }
        }

        var62 = 20;
        var74 = 6;
        var25 = 0;
        bid = Blocks.obsidian;

        for (int var14 = -var62; var14 <= var62; var14++) {
            for (int k = -var74; k <= var74; k++) {
                OreSpawnMain.setBlockFast(world, cposx + var14, cposy + var25, cposz + k, bid, 0, 2);
            }
        }

        var62 = 8;
        var74 = 8;
        var25 = 1;

        for (int var15 = -var62; var15 <= var62; var15++) {
            for (int k = -var74; k <= var74; k++) {
                bid = Blocks.obsidian;
                OreSpawnMain.setBlockFast(world, cposx + var15, cposy + var25, cposz + k, bid, 0, 2);
            }
        }

        var62 = 4;
        var74 = 18;
        var25 = 1;

        for (int var16 = -var62; var16 <= var62; var16++) {
            for (int k = -var74; k <= var74; k++) {
                bid = Blocks.obsidian;
                if (var16 == var62 && (k == -var74 || k == var74)) {
                    bid = OreSpawnMain.MyBlockAmethystBlock;
                }

                if (var16 == -var62 && (k == -var74 || k == var74)) {
                    bid = OreSpawnMain.MyBlockAmethystBlock;
                }

                OreSpawnMain.setBlockFast(world, cposx + var16, cposy + var25, cposz + k, bid, 0, 2);
            }
        }

        var62 = 18;
        var74 = 4;
        var25 = 1;

        for (int var17 = -var62; var17 <= var62; var17++) {
            for (int k = -var74; k <= var74; k++) {
                bid = Blocks.obsidian;
                if (var17 == var62 && (k == -var74 || k == var74)) {
                    bid = OreSpawnMain.MyBlockAmethystBlock;
                }

                if (var17 == -var62 && (k == -var74 || k == var74)) {
                    bid = OreSpawnMain.MyBlockAmethystBlock;
                }

                OreSpawnMain.setBlockFast(world, cposx + var17, cposy + var25, cposz + k, bid, 0, 2);
            }
        }

        var62 = 7;
        var74 = 7;
        var25 = 2;
        bid = Blocks.obsidian;

        for (int var18 = -var62; var18 <= var62; var18++) {
            for (int k = -var74; k <= var74; k++) {
                OreSpawnMain.setBlockFast(world, cposx + var18, cposy + var25, cposz + k, bid, 0, 2);
                if (var18 == var62 && (k == -var74 || k == var74)) {
                    OreSpawnMain.setBlockFast(
                        world,
                        cposx + var18,
                        cposy + var25 + 1,
                        cposz + k,
                        OreSpawnMain.CrystalTorch,
                        0,
                        2);
                }

                if (var18 == -var62 && (k == -var74 || k == var74)) {
                    OreSpawnMain.setBlockFast(
                        world,
                        cposx + var18,
                        cposy + var25 + 1,
                        cposz + k,
                        OreSpawnMain.CrystalTorch,
                        0,
                        2);
                }
            }
        }

        var62 = 3;
        var74 = 17;
        var25 = 2;
        bid = Blocks.obsidian;

        for (int var19 = -var62; var19 <= var62; var19++) {
            for (int k = -var74; k <= var74; k++) {
                OreSpawnMain.setBlockFast(world, cposx + var19, cposy + var25, cposz + k, bid, 0, 2);
            }
        }

        var62 = 17;
        var74 = 3;
        var25 = 2;
        bid = Blocks.obsidian;

        for (int var20 = -var62; var20 <= var62; var20++) {
            for (int k = -var74; k <= var74; k++) {
                OreSpawnMain.setBlockFast(world, cposx + var20, cposy + var25, cposz + k, bid, 0, 2);
            }
        }

        var62 = 6;
        var74 = 6;
        var25 = 3;
        bid = Blocks.obsidian;

        for (int var21 = -var62; var21 <= var62; var21++) {
            for (int k = -var74; k <= var74; k++) {
                OreSpawnMain.setBlockFast(world, cposx + var21, cposy + var25, cposz + k, bid, 0, 2);
            }
        }

        var62 = 2;
        var74 = 16;
        var25 = 3;
        bid = Blocks.obsidian;

        for (int var22 = -var62; var22 <= var62; var22++) {
            for (int k = -var74; k <= var74; k++) {
                OreSpawnMain.setBlockFast(world, cposx + var22, cposy + var25, cposz + k, bid, 0, 2);
            }
        }

        var62 = 16;
        var74 = 2;
        var25 = 3;
        bid = Blocks.obsidian;

        for (int var23 = -var62; var23 <= var62; var23++) {
            for (int k = -var74; k <= var74; k++) {
                OreSpawnMain.setBlockFast(world, cposx + var23, cposy + var25, cposz + k, bid, 0, 2);
            }
        }

        var62 = 2;
        var74 = 2;
        var25 = 4;
        bid = Blocks.obsidian;

        for (int var24 = -var62; var24 <= var62; var24++) {
            for (int k = -var74; k <= var74; k++) {
                OreSpawnMain.setBlockFast(world, cposx + var24, cposy + var25, cposz + k, bid, 0, 2);
                if (var24 == var62 && (k == -var74 || k == var74)) {
                    OreSpawnMain.setBlockFast(
                        world,
                        cposx + var24,
                        cposy + var25 + 1,
                        cposz + k,
                        OreSpawnMain.CrystalTorch,
                        0,
                        2);
                }

                if (var24 == -var62 && (k == -var74 || k == var74)) {
                    OreSpawnMain.setBlockFast(
                        world,
                        cposx + var24,
                        cposy + var25 + 1,
                        cposz + k,
                        OreSpawnMain.CrystalTorch,
                        0,
                        2);
                }
            }
        }

        world.setBlock(cposx, cposy + var25, cposz, Blocks.chest);
        world.setBlockMetadataWithNotify(cposx, cposy + var25, cposz, 2, 3);
        TileEntityChest chest = this.getChestTileEntity(world, cposx, cposy + var25, cposz);
        if (chest != null) {
            chest.setInventorySlotContents(13, new ItemStack(OreSpawnMain.TheQueenEgg));
        }
    }

    public void makeFrogPond(World world, int cposx, int cposy, int cposz) {
        TileEntityMobSpawner tileentitymobspawner = null;
        world.setBlock(cposx, cposy + 2, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 2, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Frog");
        }

        for (int i = -3; i <= 3; i++) {
            for (int j = -3; j <= 3; j++) {
                world.setBlock(cposx + i, cposy, cposz + j, Blocks.water, 0, 3);
            }
        }

        world.setBlock(cposx, cposy + 1, cposz, Blocks.water, 0, 3);
        world.setBlock(cposx - 1, cposy + 1, cposz, Blocks.flowing_water, 0, 3);
        world.setBlock(cposx + 1, cposy + 1, cposz, Blocks.flowing_water, 0, 3);
        world.setBlock(cposx, cposy + 1, cposz - 1, Blocks.flowing_water, 0, 3);
        world.setBlock(cposx, cposy + 1, cposz + 1, Blocks.flowing_water, 0, 3);
        world.setBlock(cposx - 1, cposy + 2, cposz, Blocks.waterlily, 0, 3);
        world.setBlock(cposx + 1, cposy + 2, cposz, Blocks.waterlily, 0, 3);
        world.setBlock(cposx, cposy + 2, cposz - 1, Blocks.waterlily, 0, 3);
        world.setBlock(cposx, cposy + 2, cposz + 1, Blocks.waterlily, 0, 3);
    }

    public void makePumpkin(World world, int cposx, int cposy, int cposz) {
        int width = 14;
        int depth = 12;
        int height = 14;
        int dark_green = 13;
        int orange = 1;
        int which_color = 0;
        Block blk = Blocks.air;
        TileEntityMobSpawner tileentitymobspawner = null;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < depth; k++) {
                    which_color = 0;
                    blk = Blocks.air;
                    if (j == 0 || j == height - 1) {
                        blk = Blocks.stained_hardened_clay;
                        which_color = orange;
                    }

                    if (i == 0 || i == width - 1) {
                        blk = Blocks.stained_hardened_clay;
                        which_color = orange;
                    }

                    if (k == 0 || k == depth - 1) {
                        blk = Blocks.stained_hardened_clay;
                        which_color = orange;
                    }

                    OreSpawnMain.setBlockFast(world, cposx + i, cposy + j, cposz + k, blk, which_color, 2);
                }
            }
        }

        int var16 = width / 2 - 1;
        int k = 0;
        int j = 11;
        OreSpawnMain.setBlockFast(world, cposx + var16 + 3, cposy + j, cposz + k, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + var16 + 4, cposy + j, cposz + k, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + var16 + 5, cposy + j, cposz + k, Blocks.air, 0, 2);
        int var25 = 10;
        OreSpawnMain.setBlockFast(world, cposx + var16 + 3, cposy + var25, cposz + k, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + var16 + 4, cposy + var25, cposz + k, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + var16 + 5, cposy + var25, cposz + k, Blocks.air, 0, 2);
        var25 = 9;
        OreSpawnMain.setBlockFast(world, cposx + var16 + 3, cposy + var25, cposz + k, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + var16 + 4, cposy + var25, cposz + k, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + var16 + 5, cposy + var25, cposz + k, Blocks.air, 0, 2);
        var25 = 8;
        OreSpawnMain.setBlockFast(world, cposx + var16 + 2, cposy + var25, cposz + k, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + var16 + 3, cposy + var25, cposz + k, Blocks.air, 0, 2);
        var25 = 7;
        OreSpawnMain.setBlockFast(world, cposx + var16 + 2, cposy + var25, cposz + k, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + var16 + 3, cposy + var25, cposz + k, Blocks.air, 0, 2);
        var25 = 4;
        OreSpawnMain.setBlockFast(world, cposx + var16 + 1, cposy + var25, cposz + k, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + var16 + 4, cposy + var25, cposz + k, Blocks.air, 0, 2);
        var25 = 3;
        OreSpawnMain.setBlockFast(world, cposx + var16 + 1, cposy + var25, cposz + k, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + var16 + 2, cposy + var25, cposz + k, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + var16 + 3, cposy + var25, cposz + k, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + var16 + 4, cposy + var25, cposz + k, Blocks.air, 0, 2);
        var25 = 2;
        OreSpawnMain.setBlockFast(world, cposx + var16 + 1, cposy + var25, cposz + k, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + var16 + 2, cposy + var25, cposz + k, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + var16 + 3, cposy + var25, cposz + k, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + var16 + 4, cposy + var25, cposz + k, Blocks.air, 0, 2);
        var25 = 1;
        OreSpawnMain.setBlockFast(world, cposx + var16 + 2, cposy + var25, cposz + k, Blocks.air, 0, 2);
        var16 = width / 2;
        int var48 = 0;
        var25 = 11;
        OreSpawnMain.setBlockFast(world, cposx + var16 - 3, cposy + var25, cposz + var48, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + var16 - 4, cposy + var25, cposz + var48, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + var16 - 5, cposy + var25, cposz + var48, Blocks.air, 0, 2);
        var25 = 10;
        OreSpawnMain.setBlockFast(world, cposx + var16 - 3, cposy + var25, cposz + var48, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + var16 - 4, cposy + var25, cposz + var48, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + var16 - 5, cposy + var25, cposz + var48, Blocks.air, 0, 2);
        var25 = 9;
        OreSpawnMain.setBlockFast(world, cposx + var16 - 3, cposy + var25, cposz + var48, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + var16 - 4, cposy + var25, cposz + var48, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + var16 - 5, cposy + var25, cposz + var48, Blocks.air, 0, 2);
        var25 = 8;
        OreSpawnMain.setBlockFast(world, cposx + var16 - 2, cposy + var25, cposz + var48, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + var16 - 3, cposy + var25, cposz + var48, Blocks.air, 0, 2);
        var25 = 7;
        OreSpawnMain.setBlockFast(world, cposx + var16 - 2, cposy + var25, cposz + var48, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + var16 - 3, cposy + var25, cposz + var48, Blocks.air, 0, 2);
        var25 = 4;
        OreSpawnMain.setBlockFast(world, cposx + var16 - 1, cposy + var25, cposz + var48, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + var16 - 4, cposy + var25, cposz + var48, Blocks.air, 0, 2);
        var25 = 3;
        OreSpawnMain.setBlockFast(world, cposx + var16 - 1, cposy + var25, cposz + var48, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + var16 - 2, cposy + var25, cposz + var48, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + var16 - 3, cposy + var25, cposz + var48, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + var16 - 4, cposy + var25, cposz + var48, Blocks.air, 0, 2);
        var25 = 2;
        OreSpawnMain.setBlockFast(world, cposx + var16 - 1, cposy + var25, cposz + var48, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + var16 - 2, cposy + var25, cposz + var48, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + var16 - 3, cposy + var25, cposz + var48, Blocks.air, 0, 2);
        OreSpawnMain.setBlockFast(world, cposx + var16 - 4, cposy + var25, cposz + var48, Blocks.air, 0, 2);
        var25 = 1;
        OreSpawnMain.setBlockFast(world, cposx + var16 - 2, cposy + var25, cposz + var48, Blocks.air, 0, 2);
        var48 = depth / 2 - 1;

        for (int var42 = 0; var42 < 4; var42++) {
            for (int var18 = 0; var18 < 3; var18++) {
                OreSpawnMain.setBlockFast(
                    world,
                    cposx + width / 2 - var18 - var42,
                    cposy + height + var42,
                    cposz + var48,
                    Blocks.stained_hardened_clay,
                    dark_green,
                    2);
            }
        }

        for (int var43 = 0; var43 < 5; var43++) {
            for (int var19 = 0; var19 < 2; var19++) {
                for (int var50 = 0; var50 < 2; var50++) {
                    OreSpawnMain.setBlockFast(
                        world,
                        cposx + width / 2 + var19 - 1,
                        cposy + var43 + 1,
                        cposz + depth / 2 + var50 - 1,
                        Blocks.planks,
                        0,
                        2);
                }
            }
        }

        var25 = 5;

        for (int var20 = 0; var20 < 2; var20++) {
            for (int var51 = 0; var51 < 2; var51++) {
                OreSpawnMain.setBlockFast(
                    world,
                    cposx + width / 2 + var20 - 1,
                    cposy + var25 + 1,
                    cposz + depth / 2 + var51 - 1,
                    Blocks.netherrack,
                    0,
                    2);
            }
        }

        var25 = 6;
        int var52 = 0;

        for (int var21 = 0; var21 < 2; var21++) {
            OreSpawnMain.setBlockFast(
                world,
                cposx + width / 2 + var21 - 1,
                cposy + var25 + 1,
                cposz + depth / 2 + var52 - 1,
                Blocks.fire,
                0,
                2);
        }

        var25 = 6;
        var52 = 1;
        int var22 = 0;
        world.setBlock(
            cposx + width / 2 + var22 - 1,
            cposy + var25 + 1,
            cposz + depth / 2 + var52 - 1,
            Blocks.mob_spawner,
            0,
            2);
        tileentitymobspawner = this.getSpawnerTileEntity(
            world,
            cposx + width / 2 + var22 - 1,
            cposy + var25 + 1,
            cposz + depth / 2 + var52 - 1);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Ghost Pumpkin Skelly");
        }

        var22 = 1;
        world.setBlock(
            cposx + width / 2 + var22 - 1,
            cposy + var25 + 1,
            cposz + depth / 2 + var52 - 1,
            Blocks.mob_spawner,
            0,
            2);
        tileentitymobspawner = this.getSpawnerTileEntity(
            world,
            cposx + width / 2 + var22 - 1,
            cposy + var25 + 1,
            cposz + depth / 2 + var52 - 1);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Ghost Pumpkin Skelly");
        }
    }

    public void makeRoundRotator(World world, int cposx, int cposy, int cposz) {
        TileEntityMobSpawner tileentitymobspawner = null;
        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        float radius = 6.0F;

        for (float curdeg = 0.0F; curdeg < 360.0F; curdeg += 5.0F) {
            float curx = (float) (radius * Math.cos(Math.toRadians(curdeg)));
            float cury = (float) (radius * Math.sin(Math.toRadians(curdeg)));
            Block blk = Blocks.bedrock;
            this.FastSetBlock(world, (int) (cposx + curx + 0.5F), (int) (cposy + 6 + cury + 0.5F), cposz, blk);
        }

        radius = 2.0F;

        for (float var19 = 0.0F; var19 < 360.0F; var19 += 5.0F) {
            float curx = (float) (radius * Math.cos(Math.toRadians(var19)));
            float cury = (float) (radius * Math.sin(Math.toRadians(var19)));
            Block blk = OreSpawnMain.MyCrystalPinkBlock;
            this.FastSetBlock(world, (int) (cposx + curx + 0.5F), (int) (cposy + 6 + cury + 0.5F), cposz, blk);
        }

        world.setBlock(cposx + 1, cposy + 6 + 1, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 1, cposy + 6 + 1, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Rotator");
        }

        world.setBlock(cposx - 1, cposy + 6 - 1, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 1, cposy + 6 - 1, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Rotator");
        }

        world.setBlock(cposx + 1, cposy + 6 - 1, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 1, cposy + 6 - 1, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Rotator");
        }

        world.setBlock(cposx - 1, cposy + 6 + 1, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 1, cposy + 6 + 1, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Rotator");
        }

        world.setBlock(cposx + 5, cposy + 6, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 5, cposy + 6, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Dungeon Beast");
        }

        world.setBlock(cposx - 5, cposy + 6, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 5, cposy + 6, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Dungeon Beast");
        }

        world.setBlock(cposx, cposy + 6 - 5, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 6 - 5, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Dungeon Beast");
        }

        world.setBlock(cposx, cposy + 6 + 5, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 6 + 5, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Dungeon Beast");
        }

        Block blk = OreSpawnMain.CrystalCoal;
        this.FastSetBlock(world, cposx + 1, cposy + 6, cposz, blk);
        this.FastSetBlock(world, cposx - 1, cposy + 6, cposz, blk);
        this.FastSetBlock(world, cposx, cposy + 6 + 1, cposz, blk);
        this.FastSetBlock(world, cposx, cposy + 6 - 1, cposz, blk);
        world.setBlock(cposx, cposy + 6, cposz, Blocks.chest);
        world.setBlockMetadataWithNotify(cposx, cposy + 6, cposz, 2, 3);
        chest = this.getChestTileEntity(world, cposx, cposy + 6, cposz);
        if (chest != null) {
            chestContents = this.CrystalBattleTowerVortexContentsList;
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 6 + world.rand.nextInt(6));
        }
    }

    public void makeRainbow(World world, int cposx, int cposy, int cposz) {
        int width = 12;
        int depth = 1;
        int blk_color = 0;
        TileEntityMobSpawner tileentitymobspawner = null;
        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        int var45 = 0;
        int j = 35;
        int var35 = 12;
        int var40 = 1;

        for (int i = -var35; i < var35; i++) {
            for (int k = -var40; k <= var40; k++) {
                OreSpawnMain
                    .setBlockFast(world, cposx + i, cposy + j, cposz + k, Blocks.stained_hardened_clay, var45, 2);
            }
        }

        int k = 0;

        for (int var16 = -var35 + 1; var16 < var35; var16 += 3) {
            OreSpawnMain.setBlockFast(world, cposx + var16, cposy + j, cposz + k, Blocks.water, 0, 2);
            OreSpawnMain.setBlockFast(world, cposx + var16, cposy + j - 1, cposz + k, Blocks.flowing_water, 0, 2);
        }

        var35 = 13;
        var40 = 2;
        int var23 = 26;

        for (int var17 = -var35; var17 < var35; var17++) {
            for (int var29 = -var40; var29 <= var40; var29++) {
                Block blk = Blocks.air;
                if (var17 == -var35 || var17 == var35 - 1) {
                    blk = Blocks.stained_hardened_clay;
                }

                if (var29 == -var40 || var29 == var40) {
                    blk = Blocks.stained_hardened_clay;
                }

                OreSpawnMain.setBlockFast(world, cposx + var17, cposy + var23, cposz + var29, blk, var45, 2);
            }
        }

        var35 = 14;
        var40 = 3;
        var23 = 27;

        for (int var18 = -var35; var18 < var35; var18++) {
            for (int var30 = -var40; var30 <= var40; var30++) {
                Block blkx = Blocks.air;
                if (var18 == -var35 || var18 == var35 - 1) {
                    blkx = Blocks.stained_hardened_clay;
                }

                if (var30 == -var40 || var30 == var40) {
                    blkx = Blocks.stained_hardened_clay;
                }

                OreSpawnMain.setBlockFast(world, cposx + var18, cposy + var23, cposz + var30, blkx, var45, 2);
            }
        }

        var35 = 13;
        var40 = 2;
        var23 = 28;

        for (int var19 = -var35; var19 < var35; var19++) {
            for (int var31 = -var40; var31 <= var40; var31++) {
                Block blkxx = Blocks.air;
                if (var19 == -var35 || var19 == var35 - 1) {
                    blkxx = Blocks.stained_hardened_clay;
                }

                if (var31 == -var40 || var31 == var40) {
                    blkxx = Blocks.stained_hardened_clay;
                }

                OreSpawnMain.setBlockFast(world, cposx + var19, cposy + var23, cposz + var31, blkxx, var45, 2);
            }
        }

        var23 = 29;
        var35 = 12;
        var40 = 1;

        for (int var20 = -var35; var20 < var35; var20++) {
            for (int var32 = -var40; var32 <= var40; var32++) {
                OreSpawnMain.setBlockFast(
                    world,
                    cposx + var20,
                    cposy + var23,
                    cposz + var32,
                    Blocks.stained_hardened_clay,
                    var45,
                    2);
            }
        }

        var23 = 30;

        for (int m = 3; m < 11; m++) {
            var45 = this.blkcolors[m - 3];

            for (int var21 = 0; var21 < m; var21++) {
                OreSpawnMain.setBlockFast(
                    world,
                    cposx + m,
                    cposy + var23 + var21,
                    cposz,
                    Blocks.stained_hardened_clay,
                    var45,
                    2);
                OreSpawnMain.setBlockFast(
                    world,
                    cposx - (m + 1),
                    cposy + var23 + var21,
                    cposz,
                    Blocks.stained_hardened_clay,
                    var45,
                    2);
            }

            for (int var22 = -(m + 1); var22 <= m; var22++) {
                OreSpawnMain.setBlockFast(
                    world,
                    cposx + var22,
                    cposy + var23 + m,
                    cposz,
                    Blocks.stained_hardened_clay,
                    var45,
                    2);
            }
        }

        world.setBlock(cposx + 2, cposy + var23, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 2, cposy + var23, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Cloud Shark");
        }

        world.setBlock(cposx - 3, cposy + var23, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 3, cposy + var23, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Cloud Shark");
        }

        world.setBlock(cposx + 2, cposy + var23 + 1, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 2, cposy + var23 + 1, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Cloud Shark");
        }

        world.setBlock(cposx - 3, cposy + var23 + 1, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 3, cposy + var23 + 1, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Cloud Shark");
        }

        world.setBlock(cposx + 2, cposy + var23 + 2, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 2, cposy + var23 + 2, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Cloud Shark");
        }

        world.setBlock(cposx - 3, cposy + var23 + 2, cposz, Blocks.mob_spawner, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 3, cposy + var23 + 2, cposz);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a()
                .setEntityName("Cloud Shark");
        }

        chestContents = this.RainbowContentsList;
        world.setBlock(cposx, cposy + var23, cposz, Blocks.chest);
        world.setBlockMetadataWithNotify(cposx, cposy + var23, cposz, 2, 3);
        chest = this.getChestTileEntity(world, cposx, cposy + var23, cposz);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 10 + world.rand.nextInt(5));
        }

        world.setBlock(cposx - 1, cposy + var23, cposz, Blocks.chest);
        world.setBlockMetadataWithNotify(cposx - 1, cposy + var23, cposz, 2, 3);
        chest = this.getChestTileEntity(world, cposx - 1, cposy + var23, cposz);
        if (chest != null) {
            WeightedRandomChestContent
                .generateChestContents(world.rand, chestContents, chest, 10 + world.rand.nextInt(5));
        }
    }

    public void makeEnormousCastleQ(World world, int cposx, int cposy, int cposz) {
        int width = 28;
        int height = 16;
        int platformwidth = 11;
        int level = 0;
        if (!world.isRemote) {
            level = 1 + world.rand.nextInt(6);
            if (level <= 3 && world.rand.nextInt(3) != 1) {
                level += 3;
            }

            for (int i = -20; i < width + 4; i++) {
                for (int j = 1; j < height + 10; j++) {
                    for (int k = -4; k < width + 4; k++) {
                        this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.air);
                    }
                }
            }

            for (int var15 = 0; var15 < width; var15++) {
                int j = 0;

                for (int k = 0; k < width; k++) {
                    this.FastSetBlock(world, cposx + var15, cposy + j, cposz + k, Blocks.obsidian);
                }
            }

            for (int var16 = 0; var16 < width; var16++) {
                int j = height;

                for (int k = 0; k < width; k++) {
                    this.FastSetBlock(world, cposx + var16, cposy + j, cposz + k, Blocks.bedrock);
                }
            }

            for (int var17 = 0; var17 < width; var17++) {
                for (int j = 1; j < height; j++) {
                    int k = 0;
                    this.FastSetBlock(world, cposx + var17, cposy + j, cposz + k, Blocks.iron_bars);
                    k = width - 1;
                    this.FastSetBlock(world, cposx + var17, cposy + j, cposz + k, Blocks.iron_bars);
                }
            }

            for (int k = 0; k < width; k++) {
                for (int j = 1; j < height; j++) {
                    int var18 = 0;
                    this.FastSetBlock(world, cposx + var18, cposy + j, cposz + k, Blocks.iron_bars);
                    var18 = width - 1;
                    this.FastSetBlock(world, cposx + var18, cposy + j, cposz + k, Blocks.iron_bars);
                }
            }

            world.setBlock(cposx + 1, cposy + 1, cposz + 1, OreSpawnMain.ExtremeTorch);
            world.setBlock(cposx + 1, cposy + 1, cposz + width - 2, OreSpawnMain.ExtremeTorch);
            world.setBlock(cposx + width - 2, cposy + 1, cposz + 1, OreSpawnMain.ExtremeTorch);
            world.setBlock(cposx + width - 2, cposy + 1, cposz + width - 2, OreSpawnMain.ExtremeTorch);

            for (int var20 = -4; var20 < width + 4; var20++) {
                for (int var46 = -4; var46 < width + 4; var46++) {
                    if (var20 < 0 || var46 < 0 || var20 >= width || var46 >= width) {
                        this.FastSetBlock(world, cposx + var20, cposy, cposz + var46, Blocks.obsidian);
                    }

                    if (var20 == -4 || var46 == -4 || var20 == width + 3 || var46 == width + 3) {
                        this.FastSetBlock(world, cposx + var20, cposy + 1, cposz + var46, Blocks.nether_brick_fence);
                    }
                }
            }

            TileEntityMobSpawner tileentitymobspawner = null;

            for (int j = 0; j < 4; j++) {
                world.setBlock(cposx - 3, cposy + 1 + j, cposz - 3, Blocks.mob_spawner, 0, 2);
                tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 3, cposy + 1 + j, cposz - 3);
                if (tileentitymobspawner != null) {
                    tileentitymobspawner.func_145881_a()
                        .setEntityName("Lurking Terror");
                }

                world.setBlock(cposx - 3, cposy + 1 + j, cposz + width + 2, Blocks.mob_spawner, 0, 2);
                tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 3, cposy + 1 + j, cposz + width + 2);
                if (tileentitymobspawner != null) {
                    tileentitymobspawner.func_145881_a()
                        .setEntityName("Lurking Terror");
                }

                world.setBlock(cposx + width + 2, cposy + 1 + j, cposz - 3, Blocks.mob_spawner, 0, 2);
                tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width + 2, cposy + 1 + j, cposz - 3);
                if (tileentitymobspawner != null) {
                    tileentitymobspawner.func_145881_a()
                        .setEntityName("Lurking Terror");
                }

                world.setBlock(cposx + width + 2, cposy + 1 + j, cposz + width + 2, Blocks.mob_spawner, 0, 2);
                tileentitymobspawner = this
                    .getSpawnerTileEntity(world, cposx + width + 2, cposy + 1 + j, cposz + width + 2);
                if (tileentitymobspawner != null) {
                    tileentitymobspawner.func_145881_a()
                        .setEntityName("Lurking Terror");
                }
            }

            world.setBlock(cposx + width / 2, cposy + 2, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Emperor Scorpion");
            }

            world.setBlock(cposx + width / 2, cposy + 3, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Emperor Scorpion");
            }

            world.setBlock(cposx + width / 2, cposy + 4, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 4, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Emperor Scorpion");
            }

            this.buildLevelQ(
                world,
                cposx + 1,
                cposy + height,
                cposz + 1,
                width - 2,
                10,
                4,
                "Rotator",
                1,
                -1,
                5,
                1,
                level);
            int var31 = height + 10;
            if (level >= 2) {
                this.buildLevelQ(
                    world,
                    cposx + 1,
                    cposy + var31,
                    cposz + 1,
                    width - 2,
                    10,
                    4,
                    "Bee",
                    0,
                    0,
                    4,
                    2,
                    level);
            }

            var31 += 10;
            if (level >= 3) {
                this.buildLevelQ(
                    world,
                    cposx + 2,
                    cposy + var31,
                    cposz + 2,
                    width - 4,
                    9,
                    4,
                    "Mantis",
                    1,
                    1,
                    4,
                    3,
                    level);
            }

            var31 += 9;
            if (level >= 4) {
                this.buildLevelQ(
                    world,
                    cposx + 2,
                    cposy + var31,
                    cposz + 2,
                    width - 4,
                    9,
                    3,
                    "Mothra",
                    0,
                    0,
                    4,
                    4,
                    level);
            }

            var31 += 9;
            if (level >= 5) {
                this.buildLevelQ(
                    world,
                    cposx + 3,
                    cposy + var31,
                    cposz + 3,
                    width - 6,
                    8,
                    3,
                    "Brutalfly",
                    1,
                    1,
                    4,
                    5,
                    level);
            }

            var31 += 8;
            if (level >= 6) {
                this.buildLevelQ(
                    world,
                    cposx + 3,
                    cposy + var31,
                    cposz + 3,
                    width - 6,
                    16,
                    3,
                    "Vortex",
                    0,
                    0,
                    3,
                    6,
                    level);
            }

            var31 += 16;

            for (int var21 = 0; var21 < platformwidth; var21++) {
                var31 = height;

                for (int var47 = -(platformwidth / 2); var47 <= platformwidth / 2; var47++) {
                    this.FastSetBlock(
                        world,
                        cposx + var21 - 20,
                        cposy + var31,
                        cposz + var47 + width / 2,
                        OreSpawnMain.MyBlockAmethystBlock);
                    if ((var21 == 0 || var21 == platformwidth - 1
                        || var47 == -(platformwidth / 2)
                        || var47 == platformwidth / 2) && (var21 != 0 || var47 < -1 || var47 > 1)) {
                        this.FastSetBlock(
                            world,
                            cposx + var21 - 20,
                            cposy + var31 + 1,
                            cposz + var47 + width / 2,
                            Blocks.nether_brick_fence);
                    }
                }
            }

            for (int var22 = -10; var22 <= -3; var22++) {
                var31 = height;

                for (int var48 = -2; var48 < 3; var48++) {
                    if (var22 != -3 && var22 != -10) {
                        this.FastSetBlock(
                            world,
                            cposx + var22,
                            cposy + var31,
                            cposz + var48 + width / 2,
                            OreSpawnMain.MyBlockAmethystBlock);
                        if (var48 == -2 || var48 == 2) {
                            this.FastSetBlock(
                                world,
                                cposx + var22,
                                cposy + var31 + 1,
                                cposz + var48 + width / 2,
                                Blocks.nether_brick_fence);
                        }
                    } else if (var48 != -2 && var48 != 2) {
                        this.FastSetBlock(
                            world,
                            cposx + var22,
                            cposy + var31 + 1,
                            cposz + var48 + width / 2,
                            Blocks.air);
                    } else {
                        this.FastSetBlock(
                            world,
                            cposx + var22,
                            cposy + var31 + 1,
                            cposz + var48 + width / 2,
                            Blocks.netherrack);
                        this.FastSetBlock(
                            world,
                            cposx + var22,
                            cposy + var31 + 2,
                            cposz + var48 + width / 2,
                            Blocks.netherrack);
                        this.FastSetBlock(
                            world,
                            cposx + var22,
                            cposy + var31 + 3,
                            cposz + var48 + width / 2,
                            Blocks.fire);
                    }
                }
            }

            int var23 = -21;

            for (int var39 = height; var39 >= 0; var39--) {
                for (int var49 = -2; var49 < 3; var49++) {
                    for (int t = 0; t < 6; t++) {
                        this.FastSetBlock(
                            world,
                            cposx + var23,
                            cposy + var39 + t + 1,
                            cposz + var49 + width / 2,
                            Blocks.air);
                    }

                    if (var39 == 0) {
                        if (var49 != -2 && var49 != 2) {
                            this.FastSetBlock(
                                world,
                                cposx + var23,
                                cposy + var39 + 1,
                                cposz + var49 + width / 2,
                                Blocks.air);
                        } else {
                            this.FastSetBlock(
                                world,
                                cposx + var23,
                                cposy + var39 + 1,
                                cposz + var49 + width / 2,
                                Blocks.netherrack);
                            this.FastSetBlock(
                                world,
                                cposx + var23,
                                cposy + var39 + 2,
                                cposz + var49 + width / 2,
                                Blocks.netherrack);
                            this.FastSetBlock(
                                world,
                                cposx + var23,
                                cposy + var39 + 3,
                                cposz + var49 + width / 2,
                                Blocks.fire);
                        }
                    } else {
                        this.FastSetBlock(
                            world,
                            cposx + var23,
                            cposy + var39,
                            cposz + var49 + width / 2,
                            OreSpawnMain.MyBlockAmethystBlock);
                        if (var49 == -2 || var49 == 2) {
                            this.FastSetBlock(
                                world,
                                cposx + var23,
                                cposy + var39 + 1,
                                cposz + var49 + width / 2,
                                Blocks.nether_brick_fence);
                        }
                    }
                }

                var23--;
            }

            if (level >= 6) {
                int span = width * 3;

                for (int tries = 0; tries < 100; tries++) {
                    int var40 = -1;
                    var23 = world.rand.nextInt(span);
                    int var50 = world.rand.nextInt(span);
                    if (var23 < span / 4 || var23 > span * 3 / 4 || var50 < span / 4 || var50 > span * 3 / 4) {
                        var23 -= span / 2;
                        var50 -= span / 2;
                        world.setBlock(
                            cposx + var23 + width / 2,
                            cposy + var40,
                            cposz + var50 + width / 2,
                            Blocks.mob_spawner,
                            0,
                            2);
                        tileentitymobspawner = this.getSpawnerTileEntity(
                            world,
                            cposx + var23 + width / 2,
                            cposy + var40,
                            cposz + var50 + width / 2);
                        if (tileentitymobspawner != null) {
                            tileentitymobspawner.func_145881_a()
                                .setEntityName("Large Worm");
                        }
                    }
                }
            }
        }
    }

    public void buildLevelQ(World world, int cposx, int cposy, int cposz, int width, int height, int pw, String critter,
        int stepside, int stepoff, int holelen, int decor, int level) {
        for (int i = -pw; i < width + pw; i++) {
            for (int j = 1; j < height; j++) {
                for (int k = -pw; k < width + pw; k++) {
                    this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.air);
                }
            }
        }

        for (int var19 = 0; var19 < width; var19++) {
            int j = 0;

            for (int k = 0; k < width; k++) {
                this.FastSetBlock(world, cposx + var19, cposy + j, cposz + k, Blocks.bedrock);
            }
        }

        for (int var20 = 0; var20 < width; var20++) {
            int j = height;

            for (int k = 0; k < width; k++) {
                this.FastSetBlock(world, cposx + var20, cposy + j, cposz + k, Blocks.bedrock);
            }
        }

        for (int var21 = 0; var21 < width; var21++) {
            for (int j = 1; j < height; j++) {
                int k = 0;
                this.FastSetBlock(world, cposx + var21, cposy + j, cposz + k, Blocks.bedrock);
                k = width - 1;
                this.FastSetBlock(world, cposx + var21, cposy + j, cposz + k, Blocks.bedrock);
            }
        }

        for (int k = 0; k < width; k++) {
            for (int j = 1; j < height; j++) {
                Block blk = Blocks.bedrock;
                if (k == 0 || k == width - 1) {
                    blk = OreSpawnMain.MyBlockRubyBlock;
                }

                int var22 = 0;
                this.FastSetBlock(world, cposx + var22, cposy + j, cposz + k, blk);
                var22 = width - 1;
                this.FastSetBlock(world, cposx + var22, cposy + j, cposz + k, blk);
            }
        }

        for (int var24 = -pw; var24 < width + pw; var24++) {
            for (int var40 = -pw; var40 < width + pw; var40++) {
                if (var24 < 0 || var40 < 0 || var24 >= width || var40 >= width) {
                    this.FastSetBlock(world, cposx + var24, cposy, cposz + var40, Blocks.obsidian);
                }

                if (var24 == -pw || var40 == -pw || var24 == width + (pw - 1) || var40 == width + (pw - 1)) {
                    this.FastSetBlock(world, cposx + var24, cposy + 1, cposz + var40, Blocks.nether_brick_fence);
                }
            }
        }

        int var25 = -(height / 2);
        var25 += width / 2;

        for (int j = 1; j < height; j++) {
            if (stepside != 0) {
                int var41 = -1;
                this.FastSetBlock(world, cposx + var25, cposy + j, cposz + var41, Blocks.obsidian);
            } else {
                this.FastSetBlock(world, cposx + var25, cposy + j, cposz + width, Blocks.obsidian);
            }

            var25++;
        }

        if (stepoff >= 0) {
            int var43;
            if (stepside == 0) {
                int var42 = -1;
                var43 = var42 - stepoff;
            } else {
                var43 = width + stepoff;
            }

            var25 = width / 2;
            int var33 = 0;

            for (int l = 0; l < holelen; l++) {
                this.FastSetBlock(world, cposx + var25 + l, cposy + var33, cposz + var43, Blocks.air);
            }
        }

        TileEntityMobSpawner tileentitymobspawner = null;

        for (int var34 = 0; var34 < 4; var34++) {
            world.setBlock(cposx - (pw - 1), cposy + var34 + 1, cposz - (pw - 1), Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this
                .getSpawnerTileEntity(world, cposx - (pw - 1), cposy + var34 + 1, cposz - (pw - 1));
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName(critter);
            }

            world.setBlock(cposx - (pw - 1), cposy + var34 + 1, cposz + width + (pw - 2), Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this
                .getSpawnerTileEntity(world, cposx - (pw - 1), cposy + var34 + 1, cposz + width + (pw - 2));
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName(critter);
            }

            world.setBlock(cposx + width + (pw - 2), cposy + var34 + 1, cposz - (pw - 1), Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this
                .getSpawnerTileEntity(world, cposx + width + (pw - 2), cposy + var34 + 1, cposz - (pw - 1));
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName(critter);
            }

            world.setBlock(
                cposx + width + (pw - 2),
                cposy + var34 + 1,
                cposz + width + (pw - 2),
                Blocks.mob_spawner,
                0,
                2);
            tileentitymobspawner = this
                .getSpawnerTileEntity(world, cposx + width + (pw - 2), cposy + var34 + 1, cposz + width + (pw - 2));
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName(critter);
            }
        }

        this.addLevelDecorationsQ(world, cposx, cposy, cposz, width, height, decor, level);
    }

    public void addLevelDecorationsQ(World world, int cposx, int cposy, int cposz, int width, int height, int decor,
        int difficulty) {
        TileEntityMobSpawner tileentitymobspawner = null;
        int reward = 1;
        String critter = "T. Rex";
        if (decor == 6) {
            this.FastSetBlock(world, cposx, cposy + height, cposz, Blocks.netherrack);
            this.FastSetBlock(world, cposx, cposy + height + 1, cposz, Blocks.fire);
            this.FastSetBlock(world, cposx, cposy + height, cposz + width - 1, Blocks.netherrack);
            this.FastSetBlock(world, cposx, cposy + height + 1, cposz + width - 1, Blocks.fire);
            this.FastSetBlock(world, cposx + width - 1, cposy + height, cposz, Blocks.netherrack);
            this.FastSetBlock(world, cposx + width - 1, cposy + height + 1, cposz, Blocks.fire);
            this.FastSetBlock(world, cposx + width - 1, cposy + height, cposz + width - 1, Blocks.netherrack);
            this.FastSetBlock(world, cposx + width - 1, cposy + height + 1, cposz + width - 1, Blocks.fire);
            this.FastSetBlock(world, cposx + width / 2, cposy + height, cposz + width / 2, Blocks.air);
            world.setBlock(cposx + width / 2 - 1, cposy + height + 2, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this
                .getSpawnerTileEntity(world, cposx + width / 2 - 1, cposy + height + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Nightmare");
            }

            world.setBlock(cposx + width / 2 + 1, cposy + height + 2, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this
                .getSpawnerTileEntity(world, cposx + width / 2 + 1, cposy + height + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Nightmare");
            }

            world.setBlock(cposx + width / 2, cposy + height + 2, cposz + width / 2 - 1, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this
                .getSpawnerTileEntity(world, cposx + width / 2, cposy + height + 2, cposz + width / 2 - 1);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Nightmare");
            }

            world.setBlock(cposx + width / 2, cposy + height + 2, cposz + width / 2 + 1, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this
                .getSpawnerTileEntity(world, cposx + width / 2, cposy + height + 2, cposz + width / 2 + 1);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Nightmare");
            }

            for (int i = 1; i < width - 1; i++) {
                for (int j = 1; j < 5; j++) {
                    for (int k = 1; k < width - 1; k++) {
                        this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.dirt);
                    }
                }
            }

            world.setBlock(cposx + width / 2, cposy + 2, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Large Worm");
            }

            world.setBlock(cposx + width / 2, cposy + 3, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Large Worm");
            }

            world.setBlock(cposx + width / 2, cposy + 4, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 4, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Large Worm");
            }

            for (int j = 0; j < 10; j++) {
                this.FastSetBlock(world, cposx + 1, cposy + j, cposz + 1, Blocks.air);
            }

            this.fill_chests(world, cposx, cposy + 4, cposz, width, height, decor, reward);
        }

        if (decor == 5) {
            if (difficulty == 5) {
                critter = "T. Rex";
                reward = 1;
            }

            if (difficulty == 6) {
                critter = "Nastysaurus";
                reward = 2;
            }

            world.setBlock(cposx + width / 2, cposy + 2, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName(critter);
            }

            world.setBlock(cposx + width / 2, cposy + 3, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName(critter);
            }

            for (int j = 1; j < 5; j++) {
                this.FastSetBlock(world, cposx + width / 2 - 1, cposy + j, cposz + width / 2, Blocks.bedrock);
                this.FastSetBlock(world, cposx + width / 2 + 1, cposy + j, cposz + width / 2, Blocks.bedrock);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 - 1, Blocks.bedrock);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 + 1, Blocks.bedrock);
            }

            this.FastSetBlock(world, cposx + width - 2, cposy, cposz + width - 2, Blocks.air);
            this.FastSetBlock(world, cposx + 1, cposy + height, cposz + 1, Blocks.air);
            this.fill_chests(world, cposx, cposy, cposz, width, height, decor, reward);
        }

        if (decor == 4) {
            if (difficulty == 4) {
                critter = "T. Rex";
                reward = 1;
            }

            if (difficulty == 5) {
                critter = "Nastysaurus";
                reward = 2;
            }

            if (difficulty == 6) {
                critter = "Basilisk";
                reward = 3;
            }

            world.setBlock(cposx + width / 2, cposy + 2, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName(critter);
            }

            world.setBlock(cposx + width / 2, cposy + 3, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName(critter);
            }

            for (int j = 1; j < 5; j++) {
                this.FastSetBlock(world, cposx + width / 2 - 1, cposy + j, cposz + width / 2, Blocks.bedrock);
                this.FastSetBlock(world, cposx + width / 2 + 1, cposy + j, cposz + width / 2, Blocks.bedrock);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 - 1, Blocks.bedrock);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 + 1, Blocks.bedrock);
            }

            this.FastSetBlock(world, cposx + 1, cposy, cposz + 1, Blocks.air);
            this.FastSetBlock(world, cposx + width - 2, cposy + height, cposz + width - 2, Blocks.air);
            this.fill_chests(world, cposx, cposy, cposz, width, height, decor, reward);
        }

        if (decor == 3) {
            if (difficulty == 3) {
                critter = "T. Rex";
                reward = 1;
            }

            if (difficulty == 4) {
                critter = "Nastysaurus";
                reward = 2;
            }

            if (difficulty == 5) {
                critter = "Basilisk";
                reward = 3;
            }

            if (difficulty == 6) {
                critter = "Hercules Beetle";
                reward = 4;
            }

            world.setBlock(cposx + width / 2, cposy + 2, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName(critter);
            }

            world.setBlock(cposx + width / 2, cposy + 3, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName(critter);
            }

            for (int j = 1; j < 5; j++) {
                this.FastSetBlock(world, cposx + width / 2 - 1, cposy + j, cposz + width / 2, Blocks.bedrock);
                this.FastSetBlock(world, cposx + width / 2 + 1, cposy + j, cposz + width / 2, Blocks.bedrock);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 - 1, Blocks.bedrock);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 + 1, Blocks.bedrock);
            }

            this.FastSetBlock(world, cposx + width - 2, cposy, cposz + width - 2, Blocks.air);
            this.FastSetBlock(world, cposx + 1, cposy + height, cposz + 1, Blocks.air);
            this.fill_chests(world, cposx, cposy, cposz, width, height, decor, reward);
        }

        if (decor == 2) {
            if (difficulty == 2) {
                critter = "T. Rex";
                reward = 1;
            }

            if (difficulty == 3) {
                critter = "Nastysaurus";
                reward = 2;
            }

            if (difficulty == 4) {
                critter = "Basilisk";
                reward = 3;
            }

            if (difficulty == 5) {
                critter = "Hercules Beetle";
                reward = 4;
            }

            if (difficulty == 6) {
                critter = "Jumpy Bug";
                reward = 5;
            }

            world.setBlock(cposx + width / 2, cposy + 2, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName(critter);
            }

            world.setBlock(cposx + width / 2, cposy + 3, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName(critter);
            }

            for (int j = 1; j < 5; j++) {
                this.FastSetBlock(world, cposx + width / 2 - 1, cposy + j, cposz + width / 2, Blocks.bedrock);
                this.FastSetBlock(world, cposx + width / 2 + 1, cposy + j, cposz + width / 2, Blocks.bedrock);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 - 1, Blocks.bedrock);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 + 1, Blocks.bedrock);
            }

            this.FastSetBlock(world, cposx + 1, cposy, cposz + 1, Blocks.air);
            this.FastSetBlock(world, cposx + width - 2, cposy + height, cposz + width - 2, Blocks.air);
            this.fill_chests(world, cposx, cposy, cposz, width, height, decor, reward);
        }

        if (decor == 1) {
            if (difficulty == 1) {
                critter = "T. Rex";
            }

            if (difficulty == 2) {
                critter = "Nastysaurus";
            }

            if (difficulty == 3) {
                critter = "Basilisk";
            }

            if (difficulty == 4) {
                critter = "Hercules Beetle";
            }

            if (difficulty == 5) {
                critter = "Jumpy Bug";
            }

            if (difficulty == 6) {
                critter = "CaterKiller";
            }

            world.setBlock(cposx + width / 2, cposy + 2, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName(critter);
            }

            world.setBlock(cposx + width / 2, cposy + 3, cposz + width / 2, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName(critter);
            }

            for (int j = 1; j < 5; j++) {
                this.FastSetBlock(world, cposx + width / 2 - 1, cposy + j, cposz + width / 2, Blocks.bedrock);
                this.FastSetBlock(world, cposx + width / 2 + 1, cposy + j, cposz + width / 2, Blocks.bedrock);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 - 1, Blocks.bedrock);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 + 1, Blocks.bedrock);
            }

            this.FastSetBlock(world, cposx + width / 2 - 1, cposy + 1, cposz + width / 2 - 1, OreSpawnMain.MyRTPBlock);
            this.FastSetBlock(world, cposx + width / 2 + 1, cposy + 1, cposz + width / 2 + 1, OreSpawnMain.MyRTPBlock);
            this.FastSetBlock(world, cposx + width / 2 + 1, cposy + 1, cposz + width / 2 - 1, OreSpawnMain.MyRTPBlock);
            this.FastSetBlock(world, cposx + width / 2 - 1, cposy + 1, cposz + width / 2 + 1, OreSpawnMain.MyRTPBlock);
            this.FastSetBlock(world, cposx + 1, cposy + height, cposz + 1, Blocks.air);
            this.fill_chestsQ(world, cposx, cposy, cposz, width, height, decor, difficulty);
        }
    }

    private void fill_chestsQ(World world, int cposx, int cposy, int cposz, int width, int height, int decor,
        int reward) {
        TileEntityChest chest = null;
        WeightedRandomChestContent[] chestContents = null;
        chestContents = this.level1ContentsList;
        if (reward == 2) {
            chestContents = this.level2ContentsList;
        }

        if (reward == 3) {
            chestContents = this.level3ContentsList;
        }

        if (reward == 4) {
            chestContents = this.level4ContentsList;
        }

        if (reward == 5) {
            chestContents = this.level5ContentsList;
        }

        world.setBlock(cposx + 1, cposy + 1, cposz + width / 2, Blocks.chest, 0, 2);
        world.setBlockMetadataWithNotify(cposx + 1, cposy + 1, cposz + width / 2, 5, 3);
        chest = this.getChestTileEntity(world, cposx + 1, cposy + 1, cposz + width / 2);
        if (chest != null) {
            if (reward == 6) {
                chest.setInventorySlotContents(1, new ItemStack(OreSpawnMain.ThePrincessEgg, 1, 0));
            } else {
                WeightedRandomChestContent
                    .generateChestContents(world.rand, chestContents, chest, 5 + world.rand.nextInt(7));
            }
        }

        world.setBlock(cposx + width - 2, cposy + 1, cposz + width / 2, Blocks.chest, 0, 2);
        world.setBlockMetadataWithNotify(cposx + width - 2, cposy + 1, cposz + width / 2, 4, 3);
        chest = this.getChestTileEntity(world, cposx + width - 2, cposy + 1, cposz + width / 2);
        if (chest != null) {
            if (reward == 6) {
                chest.setInventorySlotContents(1, new ItemStack(OreSpawnMain.QueenHelmet, 1, 0));
                chest.setInventorySlotContents(2, new ItemStack(OreSpawnMain.QueenBody, 1, 0));
            } else {
                WeightedRandomChestContent
                    .generateChestContents(world.rand, chestContents, chest, 5 + world.rand.nextInt(7));
            }
        }

        world.setBlock(cposx + width / 2, cposy + 1, cposz + 1, Blocks.chest, 0, 2);
        world.setBlockMetadataWithNotify(cposx + width / 2, cposy + 1, cposz + 1, 3, 3);
        chest = this.getChestTileEntity(world, cposx + width / 2, cposy + 1, cposz + 1);
        if (chest != null) {
            if (reward == 6) {
                chest.setInventorySlotContents(1, new ItemStack(OreSpawnMain.QueenLegs, 1, 0));
                chest.setInventorySlotContents(2, new ItemStack(OreSpawnMain.QueenBoots, 1, 0));
            } else {
                WeightedRandomChestContent
                    .generateChestContents(world.rand, chestContents, chest, 5 + world.rand.nextInt(7));
            }
        }

        world.setBlock(cposx + width / 2, cposy + 1, cposz + width - 2, Blocks.chest, 0, 2);
        world.setBlockMetadataWithNotify(cposx + width / 2, cposy + 1, cposz + width - 2, 2, 3);
        chest = this.getChestTileEntity(world, cposx + width / 2, cposy + 1, cposz + width - 2);
        if (chest != null) {
            if (reward == 6) {
                chest.setInventorySlotContents(1, new ItemStack(OreSpawnMain.MyRoyal, 1, 0));
            } else {
                WeightedRandomChestContent
                    .generateChestContents(world.rand, chestContents, chest, 5 + world.rand.nextInt(7));
            }
        }
    }

    public void makeSpiderHangout(World world, int cposx, int cposy, int cposz) {
        Entity var8 = null;
        TileEntityMobSpawner tileentitymobspawner = null;

        for (int i = 0; i < 20; i++) {
            for (int j = -1; j < 20; j++) {
                for (int k = 0; k < 20; k++) {
                    Block blk = Blocks.air;
                    if (j == -1) {
                        blk = Blocks.stone;
                    }

                    if (j == 0) {
                        blk = Blocks.gravel;
                    }

                    OreSpawnMain.setBlockFast(world, cposx + i, cposy + j, cposz + k, blk, 0, 2);
                }
            }
        }

        for (int j = 1; j < 4; j++) {
            int k = 0;
            int var11 = 0;
            world.setBlock(cposx + var11, cposy + j, cposz + k, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var11, cposy + j, cposz + k);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Spider Driver");
            }

            int var17 = 19;
            var11 = 19;
            world.setBlock(cposx + var11, cposy + j, cposz + var17, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var11, cposy + j, cposz + var17);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Spider Driver");
            }

            var17 = 0;
            var11 = 19;
            world.setBlock(cposx + var11, cposy + j, cposz + var17, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var11, cposy + j, cposz + var17);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Spider Driver");
            }

            var17 = 19;
            var11 = 0;
            world.setBlock(cposx + var11, cposy + j, cposz + var17, Blocks.mob_spawner, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var11, cposy + j, cposz + var17);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.func_145881_a()
                    .setEntityName("Spider Driver");
            }
        }

        var8 = EntityList.createEntityByName("Robot Spider", world);
        if (var8 != null) {
            var8.setLocationAndAngles(cposx + 10, cposy + 1, cposz + 10, world.rand.nextFloat() * 360.0F, 0.0F);
            world.spawnEntityInWorld(var8);
        }
    }

    public void makeRedAntHangout(World world, int cposx, int cposy, int cposz) {
        Entity var8 = null;

        for (int i = 0; i < 16; i++) {
            for (int j = -1; j < 16; j++) {
                for (int k = 0; k < 16; k++) {
                    Block blk = Blocks.air;
                    if (j == -1) {
                        blk = Blocks.stone;
                    }

                    if (j == 0) {
                        blk = Blocks.gravel;
                        if ((i < 3 || i > 12) && (k < 3 || k > 12)) {
                            blk = OreSpawnMain.MyRedAntBlock;
                        }
                    }

                    OreSpawnMain.setBlockFast(world, cposx + i, cposy + j, cposz + k, blk, 0, 2);
                }
            }
        }

        var8 = EntityList.createEntityByName("Robot Red Ant", world);
        if (var8 != null) {
            var8.setLocationAndAngles(cposx + 8, cposy + 1, cposz + 8, world.rand.nextFloat() * 360.0F, 0.0F);
            world.spawnEntityInWorld(var8);
        }
    }
}
