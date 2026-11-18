package danger.orespawn;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;

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
