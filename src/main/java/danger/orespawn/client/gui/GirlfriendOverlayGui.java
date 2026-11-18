package danger.orespawn.client.gui;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.Alien;
import danger.orespawn.entity.Alosaurus;
import danger.orespawn.entity.AntRobot;
import danger.orespawn.entity.BandP;
import danger.orespawn.entity.Basilisk;
import danger.orespawn.entity.Boyfriend;
import danger.orespawn.entity.CaterKiller;
import danger.orespawn.entity.Cephadrome;
import danger.orespawn.entity.Crab;
import danger.orespawn.entity.Dragon;
import danger.orespawn.entity.EmperorScorpion;
import danger.orespawn.entity.GiantRobot;
import danger.orespawn.entity.Girlfriend;
import danger.orespawn.entity.Godzilla;
import danger.orespawn.entity.Hammerhead;
import danger.orespawn.entity.HerculesBeetle;
import danger.orespawn.entity.Irukandji;
import danger.orespawn.entity.Kraken;
import danger.orespawn.entity.Kyuubi;
import danger.orespawn.entity.Leon;
import danger.orespawn.entity.Mantis;
import danger.orespawn.entity.Molenoid;
import danger.orespawn.entity.Mothra;
import danger.orespawn.entity.Nastysaurus;
import danger.orespawn.entity.PitchBlack;
import danger.orespawn.entity.Robot2;
import danger.orespawn.entity.Robot4;
import danger.orespawn.entity.SeaMonster;
import danger.orespawn.entity.SeaViper;
import danger.orespawn.entity.SpiderRobot;
import danger.orespawn.entity.SpitBug;
import danger.orespawn.entity.Spyro;
import danger.orespawn.entity.TRex;
import danger.orespawn.entity.TheKing;
import danger.orespawn.entity.ThePrince;
import danger.orespawn.entity.ThePrinceAdult;
import danger.orespawn.entity.ThePrinceTeen;
import danger.orespawn.entity.ThePrincess;
import danger.orespawn.entity.TheQueen;
import danger.orespawn.entity.Triffid;
import danger.orespawn.entity.TrooperBug;
import danger.orespawn.entity.Vortex;
import danger.orespawn.entity.WaterDragon;
import danger.orespawn.entity.WormLarge;

public class GirlfriendOverlayGui extends Gui {

    private Minecraft mc;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "girlfriendgui.png");

    public GirlfriendOverlayGui(Minecraft mc) {
        this.mc = mc;
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent event) {
        if (!event.isCancelable() && event.type == ElementType.HOTBAR) {
            int u = 0;
            int v = 0;
            String outstring = null;
            int color = 16725044;
            FontRenderer fr = this.mc.fontRenderer;
            int barWidth = 182;
            int barHeight = 5;
            float gfHealth = 0.0F;
            Entity entity = null;
            EntityPlayer player = null;
            if (!this.mc.gameSettings.hideGUI && this.mc.currentScreen == null) {
                EntityPlayer thePlayer = this.mc.thePlayer;
                if (thePlayer != null) {
                    OreSpawnMain.current_dimension = thePlayer.worldObj.provider.dimensionId;
                    if (this.mc.gameSettings.fancyGraphics) {
                        OreSpawnMain.FastGraphicsLeaves = 0;
                    } else {
                        OreSpawnMain.FastGraphicsLeaves = 1;
                    }

                    if (OreSpawnMain.GuiOverlayEnable != 0) {
                        entity = this.mc.pointedEntity;
                        if (entity == null) {
                            entity = OreSpawnMain.getPointedAtEntity(this.mc.theWorld, thePlayer, 16.0);
                            if (entity == null) {
                                return;
                            }

                            if (!(entity instanceof EntityLivingBase)) {
                                return;
                            }
                        }

                        if (entity instanceof Girlfriend) {
                            Girlfriend gf = null;
                            gf = (Girlfriend) entity;
                            if (!gf.func_152114_e(thePlayer)) {
                                return;
                            }

                            if (gf.passenger != 0) {
                                return;
                            }

                            if (gf.hasCustomNameTag()) {
                                outstring = gf.getCustomNameTag();
                            }

                            if (outstring == null || outstring.equals("")) {
                                outstring = "Girlfriend";
                            }

                            gfHealth = gf.getGirlfriendHealth() / gf.getMaxHealth();
                        }

                        if (entity instanceof Boyfriend) {
                            Boyfriend gfx = null;
                            gfx = (Boyfriend) entity;
                            if (!gfx.func_152114_e(thePlayer)) {
                                return;
                            }

                            if (gfx.passenger != 0) {
                                return;
                            }

                            if (gfx.hasCustomNameTag()) {
                                outstring = gfx.getCustomNameTag();
                            }

                            if (outstring == null || outstring.equals("")) {
                                outstring = "Boyfriend";
                            }

                            gfHealth = gfx.getBoyfriendHealth() / gfx.getMaxHealth();
                        }

                        if (entity instanceof ThePrince) {
                            ThePrince gfxx = null;
                            gfxx = (ThePrince) entity;
                            if (!gfxx.func_152114_e(thePlayer)) {
                                return;
                            }

                            if (gfxx.hasCustomNameTag()) {
                                outstring = gfxx.getCustomNameTag();
                            }

                            if (outstring == null || outstring.equals("")) {
                                outstring = "The Toddler Prince";
                            }

                            gfHealth = gfxx.getHealth() / gfxx.getMaxHealth();
                        }

                        if (entity instanceof ThePrincess) {
                            ThePrincess gfxxx = null;
                            gfxxx = (ThePrincess) entity;
                            if (!gfxxx.func_152114_e(thePlayer)) {
                                return;
                            }

                            if (gfxxx.hasCustomNameTag()) {
                                outstring = gfxxx.getCustomNameTag();
                            }

                            if (outstring == null || outstring.equals("")) {
                                outstring = "The Toddler Princess";
                            }

                            gfHealth = gfxxx.getHealth() / gfxxx.getMaxHealth();
                        }

                        if (entity instanceof ThePrinceTeen) {
                            ThePrinceTeen gfxxxx = null;
                            gfxxxx = (ThePrinceTeen) entity;
                            if (!gfxxxx.func_152114_e(thePlayer)) {
                                return;
                            }

                            if (gfxxxx.hasCustomNameTag()) {
                                outstring = gfxxxx.getCustomNameTag();
                            }

                            if (outstring == null || outstring.equals("")) {
                                outstring = "The Young Prince";
                            }

                            if (gfxxxx.getActivity() != 0) {
                                return;
                            }

                            gfHealth = gfxxxx.getHealth() / gfxxxx.getMaxHealth();
                        }

                        if (entity instanceof ThePrinceAdult) {
                            ThePrinceAdult gfxxxxx = null;
                            gfxxxxx = (ThePrinceAdult) entity;
                            if (!gfxxxxx.func_152114_e(thePlayer)) {
                                return;
                            }

                            if (gfxxxxx.hasCustomNameTag()) {
                                outstring = gfxxxxx.getCustomNameTag();
                            }

                            if (outstring == null || outstring.equals("")) {
                                outstring = "The Young Adult Prince";
                            }

                            if (gfxxxxx.getActivity() != 0) {
                                return;
                            }

                            gfHealth = gfxxxxx.getHealth() / gfxxxxx.getMaxHealth();
                        }

                        if (entity instanceof Dragon) {
                            Dragon df = null;
                            df = (Dragon) entity;
                            if (df.hasCustomNameTag()) {
                                outstring = df.getCustomNameTag();
                            }

                            if (outstring == null || outstring.equals("")) {
                                outstring = "Dragon";
                            }

                            if (df.getActivity() != 0) {
                                return;
                            }

                            gfHealth = df.getDragonHealth() / df.getMaxHealth();
                        }

                        if (entity instanceof EmperorScorpion) {
                            EmperorScorpion e = (EmperorScorpion) entity;
                            outstring = "Emperor Scorpion";
                            gfHealth = e.getEmperorScorpionHealth() / e.getMaxHealth();
                        }

                        if (entity instanceof Basilisk) {
                            Basilisk e = (Basilisk) entity;
                            outstring = "Basilisk";
                            gfHealth = e.getBasiliskHealth() / e.getMaxHealth();
                        }

                        if (entity instanceof Mothra) {
                            Mothra e = (Mothra) entity;
                            outstring = "Mothra!";
                            gfHealth = e.getMothraHealth() / e.getMaxHealth();
                        }

                        if (entity instanceof Spyro) {
                            Spyro e = (Spyro) entity;
                            if (e.hasCustomNameTag()) {
                                outstring = e.getCustomNameTag();
                            }

                            if (outstring == null || outstring.equals("")) {
                                outstring = "Baby Dragon";
                            }

                            gfHealth = e.getSpyroHealth() / e.getMaxHealth();
                        }

                        if (entity instanceof WormLarge) {
                            WormLarge ex = (WormLarge) entity;
                            if (!ex.noClip) {
                                outstring = "Worm";
                                gfHealth = ex.getHealth() / ex.getMaxHealth();
                            }
                        }

                        if (entity instanceof Alien) {
                            Alien ex = (Alien) entity;
                            outstring = "Alien!";
                            gfHealth = ex.getAlienHealth() / ex.getMaxHealth();
                        }

                        if (entity instanceof WaterDragon) {
                            WaterDragon ex = (WaterDragon) entity;
                            if (ex.hasCustomNameTag()) {
                                outstring = ex.getCustomNameTag();
                            }

                            if (outstring == null || outstring.equals("")) {
                                outstring = "WaterDragon";
                            }

                            gfHealth = ex.getWaterDragonHealth() / ex.getMaxHealth();
                        }

                        if (entity instanceof Kraken) {
                            Kraken exx = (Kraken) entity;
                            outstring = "Kraken";
                            gfHealth = exx.getKrakenHealth() / exx.getMaxHealth();
                        }

                        if (entity instanceof Cephadrome) {
                            Cephadrome exx = (Cephadrome) entity;
                            outstring = "Cephadrome";
                            gfHealth = exx.getCephadromeHealth() / exx.getMaxHealth();
                            if (exx.getActivity() != 0) {
                                return;
                            }
                        }

                        if (entity instanceof TrooperBug) {
                            TrooperBug exx = (TrooperBug) entity;
                            outstring = "Jumpy Bug";
                            gfHealth = exx.getTrooperBugHealth() / exx.getMaxHealth();
                        }

                        if (entity instanceof SpitBug) {
                            SpitBug exx = (SpitBug) entity;
                            outstring = "Spit Bug";
                            gfHealth = exx.getHealth() / exx.getMaxHealth();
                        }

                        if (entity instanceof PitchBlack) {
                            PitchBlack exx = (PitchBlack) entity;
                            outstring = "Nightmare";
                            gfHealth = exx.getHealth() / exx.getMaxHealth();
                        }

                        if (entity instanceof Alosaurus) {
                            Alosaurus exx = (Alosaurus) entity;
                            outstring = "Alosaurus";
                            gfHealth = exx.getHealth() / exx.getMaxHealth();
                        }

                        if (entity instanceof Nastysaurus) {
                            Nastysaurus exx = (Nastysaurus) entity;
                            outstring = "Nastysaurus";
                            gfHealth = exx.getHealth() / exx.getMaxHealth();
                        }

                        if (entity instanceof TRex) {
                            TRex exx = (TRex) entity;
                            outstring = "T. Rex";
                            gfHealth = exx.getHealth() / exx.getMaxHealth();
                        }

                        if (entity instanceof Kyuubi) {
                            Kyuubi exx = (Kyuubi) entity;
                            outstring = "Kyuubi";
                            gfHealth = exx.getHealth() / exx.getMaxHealth();
                        }

                        if (entity instanceof Robot2) {
                            Robot2 exx = (Robot2) entity;
                            outstring = "Robo-Pounder";
                            gfHealth = exx.getHealth() / exx.getMaxHealth();
                        }

                        if (entity instanceof Robot4) {
                            Robot4 exx = (Robot4) entity;
                            outstring = "Robo-Warrior";
                            gfHealth = exx.getRobot4Health() / exx.getMaxHealth();
                        }

                        if (entity instanceof Triffid) {
                            Triffid exx = (Triffid) entity;
                            outstring = "Triffid";
                            gfHealth = exx.getHealth() / exx.getMaxHealth();
                        }

                        if (entity instanceof Godzilla) {
                            Godzilla exx = (Godzilla) entity;
                            outstring = "Mobzilla";
                            gfHealth = exx.getHealth() / exx.getMaxHealth();
                        }

                        if (entity instanceof Vortex) {
                            Vortex exx = (Vortex) entity;
                            outstring = "Vortex";
                            gfHealth = exx.getHealth() / exx.getMaxHealth();
                        }

                        if (entity instanceof Irukandji) {
                            Irukandji exx = (Irukandji) entity;
                            outstring = "Irukandji";
                            gfHealth = exx.getHealth() / exx.getMaxHealth();
                        }

                        if (entity instanceof Mantis) {
                            Mantis exx = (Mantis) entity;
                            outstring = "Mantis";
                            gfHealth = exx.getHealth() / exx.getMaxHealth();
                        }

                        if (entity instanceof HerculesBeetle) {
                            HerculesBeetle exx = (HerculesBeetle) entity;
                            outstring = "Hercules Beetle";
                            gfHealth = exx.getHealth() / exx.getMaxHealth();
                        }

                        if (entity instanceof TheKing) {
                            TheKing exx = (TheKing) entity;
                            outstring = "The King";
                            gfHealth = exx.getHealth() / exx.getMaxHealth();
                        }

                        if (entity instanceof TheQueen) {
                            TheQueen exx = (TheQueen) entity;
                            outstring = "The Queen";
                            gfHealth = exx.getHealth() / exx.getMaxHealth();
                        }

                        if (entity instanceof SeaViper) {
                            SeaViper exx = (SeaViper) entity;
                            outstring = "Sea Viper";
                            gfHealth = exx.getHealth() / exx.getMaxHealth();
                        }

                        if (entity instanceof SeaMonster) {
                            SeaMonster exx = (SeaMonster) entity;
                            outstring = "Sea Monster";
                            gfHealth = exx.getHealth() / exx.getMaxHealth();
                        }

                        if (entity instanceof Molenoid) {
                            Molenoid exx = (Molenoid) entity;
                            outstring = "Molenoid";
                            gfHealth = exx.getHealth() / exx.getMaxHealth();
                        }

                        if (entity instanceof CaterKiller) {
                            CaterKiller exx = (CaterKiller) entity;
                            outstring = "CaterKiller";
                            gfHealth = exx.getHealth() / exx.getMaxHealth();
                        }

                        if (entity instanceof Leon) {
                            Leon exx = (Leon) entity;
                            if (exx.hasCustomNameTag()) {
                                outstring = exx.getCustomNameTag();
                            }

                            if (outstring == null || outstring.equals("")) {
                                outstring = "Leonopteryx";
                            }

                            gfHealth = exx.getHealth() / exx.getMaxHealth();
                        }

                        if (entity instanceof Hammerhead) {
                            Hammerhead exxx = (Hammerhead) entity;
                            outstring = "Hammerhead";
                            gfHealth = exxx.getHealth() / exxx.getMaxHealth();
                        }

                        if (entity instanceof BandP) {
                            BandP exxx = (BandP) entity;
                            if (exxx.getWhat() == 0) {
                                outstring = "Banker";
                            } else {
                                outstring = "Politician";
                            }

                            gfHealth = exxx.getHealth() / exxx.getMaxHealth();
                        }

                        if (entity instanceof SpiderRobot) {
                            SpiderRobot exxx = (SpiderRobot) entity;
                            outstring = "Giant Robot Spider";
                            gfHealth = exxx.getHealth() / exxx.getMaxHealth();
                        }

                        if (entity instanceof GiantRobot) {
                            GiantRobot exxx = (GiantRobot) entity;
                            outstring = "Jeffery";
                            gfHealth = exxx.getHealth() / exxx.getMaxHealth();
                        }

                        if (entity instanceof AntRobot) {
                            AntRobot exxx = (AntRobot) entity;
                            outstring = "Giant Robot Red Ant";
                            gfHealth = exxx.getHealth() / exxx.getMaxHealth();
                        }

                        if (entity instanceof Crab) {
                            Crab exxx = (Crab) entity;
                            float myf = exxx.getCrabScale();
                            if (myf > 0.75F) {
                                outstring = "Very Large Crab";
                                gfHealth = exxx.getHealth() / exxx.getMaxHealth();
                            }
                        }

                        if (outstring != null) {
                            ScaledResolution res = new ScaledResolution(
                                this.mc,
                                this.mc.displayWidth,
                                this.mc.displayHeight);
                            int width = res.getScaledWidth();
                            int barWidthFilled = (int) (gfHealth * (barWidth + 1));
                            int x = width / 2 - barWidth / 2;
                            int y = 25;
                            if (thePlayer.isInsideOfMaterial(Material.water) || thePlayer.getTotalArmorValue() > 0) {
                                y -= 10;
                            }

                            fr.drawStringWithShadow(
                                outstring,
                                width / 2 - fr.getStringWidth(outstring) / 2,
                                y - 10,
                                color);
                            this.mc.renderEngine.bindTexture(texture);
                            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                            this.drawTexturedModalRect(x, y, u, v, barWidth, barHeight);
                            if (barWidthFilled > 0) {
                                this.drawTexturedModalRect(x, y, u, v + barHeight, barWidthFilled, barHeight);
                            }
                        }
                    }
                }
            }
        }
    }
}
