package danger.orespawn;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;

import danger.orespawn.entity.Boyfriend;
import danger.orespawn.entity.Cephadrome;
import danger.orespawn.entity.Cockateil;
import danger.orespawn.entity.Cricket;
import danger.orespawn.entity.Dragon;
import danger.orespawn.entity.Dragonfly;
import danger.orespawn.entity.Elevator;
import danger.orespawn.entity.EntityAnt;
import danger.orespawn.entity.EntityButterfly;
import danger.orespawn.entity.EntityMosquito;
import danger.orespawn.entity.Firefly;
import danger.orespawn.entity.GammaMetroid;
import danger.orespawn.entity.Ghost;
import danger.orespawn.entity.GhostSkelly;
import danger.orespawn.entity.Girlfriend;
import danger.orespawn.entity.KingHead;
import danger.orespawn.entity.Leon;
import danger.orespawn.entity.Mothra;
import danger.orespawn.entity.PurplePower;
import danger.orespawn.entity.QueenHead;
import danger.orespawn.entity.RockBase;
import danger.orespawn.entity.Spyro;
import danger.orespawn.entity.Stinky;
import danger.orespawn.entity.Termite;
import danger.orespawn.entity.TheKing;
import danger.orespawn.entity.ThePrince;
import danger.orespawn.entity.ThePrinceAdult;
import danger.orespawn.entity.ThePrinceTeen;
import danger.orespawn.entity.ThePrincess;
import danger.orespawn.entity.TheQueen;
import danger.orespawn.entity.WaterDragon;

public class MyUtils {

    public static boolean isRoyalty(Entity e) {
        if (!(e instanceof EntityLivingBase)) {
            return false;
        } else if (e instanceof ThePrince) {
            return true;
        } else if (e instanceof ThePrinceTeen) {
            return true;
        } else if (e instanceof ThePrinceAdult) {
            return true;
        } else if (e instanceof ThePrincess) {
            return true;
        } else if (e instanceof TheKing) {
            return true;
        } else if (e instanceof KingHead) {
            return true;
        } else if (e instanceof TheQueen) {
            return true;
        } else {
            return e instanceof QueenHead ? true : e instanceof PurplePower;
        }
    }

    public static boolean isAttackableNonMob(EntityLivingBase par1EntityLiving) {
        if (par1EntityLiving instanceof EntityMob) {
            return true;
        } else if (par1EntityLiving instanceof Mothra) {
            return true;
        } else if (par1EntityLiving instanceof Leon) {
            return true;
        } else if (par1EntityLiving instanceof Dragon) {
            return true;
        } else if (par1EntityLiving instanceof Spyro) {
            return true;
        } else if (isRoyalty(par1EntityLiving)) {
            return true;
        } else if (par1EntityLiving instanceof GammaMetroid) {
            return true;
        } else if (par1EntityLiving instanceof Cephadrome) {
            return true;
        } else if (par1EntityLiving instanceof WaterDragon) {
            return true;
        } else if (par1EntityLiving instanceof Girlfriend) {
            return true;
        } else if (par1EntityLiving instanceof Boyfriend) {
            return true;
        } else {
            return par1EntityLiving instanceof EntityVillager ? true : par1EntityLiving instanceof Stinky;
        }
    }

    public static boolean isIgnoreable(EntityLivingBase par1EntityLiving) {
        if (par1EntityLiving instanceof RockBase) {
            return true;
        } else if (par1EntityLiving instanceof EntityAnt) {
            return true;
        } else if (par1EntityLiving instanceof EntityButterfly) {
            return true;
        } else if (par1EntityLiving instanceof EntityMosquito) {
            return true;
        } else if (par1EntityLiving instanceof Dragonfly) {
            return true;
        } else if (par1EntityLiving instanceof Firefly) {
            return true;
        } else if (par1EntityLiving instanceof Cricket) {
            return true;
        } else if (par1EntityLiving instanceof Cockateil) {
            return true;
        } else if (par1EntityLiving instanceof Termite) {
            return true;
        } else if (par1EntityLiving instanceof Ghost) {
            return true;
        } else {
            return par1EntityLiving instanceof GhostSkelly ? true : par1EntityLiving instanceof Elevator;
        }
    }
}
