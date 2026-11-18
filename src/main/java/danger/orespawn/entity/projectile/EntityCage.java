package danger.orespawn.entity.projectile;

import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.Alien;
import danger.orespawn.entity.Alosaurus;
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
import danger.orespawn.entity.Crab;
import danger.orespawn.entity.CreepingHorror;
import danger.orespawn.entity.Cricket;
import danger.orespawn.entity.Cryolophosaurus;
import danger.orespawn.entity.CrystalCow;
import danger.orespawn.entity.Dragon;
import danger.orespawn.entity.Dragonfly;
import danger.orespawn.entity.DungeonBeast;
import danger.orespawn.entity.EasterBunny;
import danger.orespawn.entity.EmperorScorpion;
import danger.orespawn.entity.EnchantedCow;
import danger.orespawn.entity.EnderKnight;
import danger.orespawn.entity.EnderReaper;
import danger.orespawn.entity.Fairy;
import danger.orespawn.entity.Firefly;
import danger.orespawn.entity.Flounder;
import danger.orespawn.entity.Frog;
import danger.orespawn.entity.GammaMetroid;
import danger.orespawn.entity.Gazelle;
import danger.orespawn.entity.Girlfriend;
import danger.orespawn.entity.GoldCow;
import danger.orespawn.entity.GoldFish;
import danger.orespawn.entity.Hammerhead;
import danger.orespawn.entity.HerculesBeetle;
import danger.orespawn.entity.Hydrolisc;
import danger.orespawn.entity.Irukandji;
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
import danger.orespawn.entity.Rat;
import danger.orespawn.entity.RedCow;
import danger.orespawn.entity.Rotator;
import danger.orespawn.entity.RubberDucky;
import danger.orespawn.entity.Scorpion;
import danger.orespawn.entity.SeaMonster;
import danger.orespawn.entity.SeaViper;
import danger.orespawn.entity.Skate;
import danger.orespawn.entity.SpiderDriver;
import danger.orespawn.entity.SpitBug;
import danger.orespawn.entity.Spyro;
import danger.orespawn.entity.StinkBug;
import danger.orespawn.entity.Stinky;
import danger.orespawn.entity.TRex;
import danger.orespawn.entity.TerribleTerror;
import danger.orespawn.entity.Triffid;
import danger.orespawn.entity.TrooperBug;
import danger.orespawn.entity.Urchin;
import danger.orespawn.entity.VelocityRaptor;
import danger.orespawn.entity.Vortex;
import danger.orespawn.entity.WaterDragon;
import danger.orespawn.entity.Whale;
import danger.orespawn.entity.WormLarge;
import danger.orespawn.entity.WormMedium;
import danger.orespawn.entity.WormSmall;

public class EntityCage extends EntityThrowable {

    private float my_rotation = 0.0F;
    public int my_index = 160;
    private World throwerWorld = null;
    private EntityPlayer thrower = null;

    public EntityCage(World world) {
        super(world);
        this.throwerWorld = world;
    }

    public EntityCage(World world, int i) {
        super(world);
        this.throwerWorld = world;
        this.my_index = i;
    }

    public EntityCage(World world, EntityPlayer living, int i) {
        super(world, living);
        this.throwerWorld = world;
        this.thrower = living;
        this.my_index = i;
        if (this.thrower.worldObj != null) {
            this.throwerWorld = this.thrower.worldObj;
        }
    }

    public int getCageIndex() {
        return this.my_index;
    }

    protected void onImpact(MovingObjectPosition hit) {
        if (hit.entityHit != null && this.rand.nextInt(10) >= 2) {
            if (this.throwerWorld != null) {
                for (int i = 0; i < 4; i++) {
                    this.throwerWorld.spawnParticle(
                        "smoke",
                        hit.entityHit.posX,
                        hit.entityHit.posY + 0.25,
                        hit.entityHit.posZ,
                        0.0,
                        0.0,
                        0.0);
                    this.throwerWorld.spawnParticle(
                        "explode",
                        hit.entityHit.posX,
                        hit.entityHit.posY + 0.25,
                        hit.entityHit.posZ,
                        0.0,
                        0.0,
                        0.0);
                    this.throwerWorld.spawnParticle(
                        "reddust",
                        hit.entityHit.posX,
                        hit.entityHit.posY + 0.25,
                        hit.entityHit.posZ,
                        0.0,
                        0.0,
                        0.0);
                }

                if (this.thrower != null) {
                    this.throwerWorld.playSoundAtEntity(this.thrower, "random.explode", 1.0F, 1.5F);
                }
            }

            if (hit.entityHit instanceof EntityPlayer) {
                if (!this.worldObj.isRemote) {
                    this.dropItem(OreSpawnMain.CageEmpty, 1);
                    this.setDead();
                }

                return;
            }

            if (hit.entityHit instanceof SpiderDriver) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedSpiderDriver, 1);
            } else if (hit.entityHit instanceof EntitySpider) {
                if (hit.entityHit instanceof EntityCaveSpider) {
                    hit.entityHit.setDead();
                    this.dropItem(OreSpawnMain.CagedCaveSpider, 1);
                } else {
                    hit.entityHit.setDead();
                    this.dropItem(OreSpawnMain.CagedSpider, 1);
                }
            }

            if (hit.entityHit instanceof Crab) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedCrab, 1);
            }

            if (hit.entityHit instanceof EntityBat) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedBat, 2);
            }

            if (hit.entityHit instanceof EntityPig) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedPig, 1);
            }

            if (hit.entityHit instanceof EntitySquid) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedSquid, 1);
            }

            if (hit.entityHit instanceof EntityChicken) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedChicken, 1);
            }

            if (hit.entityHit instanceof EntityCreeper) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedCreeper, 1);
            }

            if (hit.entityHit instanceof EntityHorse) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedHorse, 1);
            }

            if (hit.entityHit instanceof EntitySkeleton) {
                EntitySkeleton sk = (EntitySkeleton) hit.entityHit;
                if (sk.getSkeletonType() != 0) {
                    this.dropItem(OreSpawnMain.CagedWitherSkeleton, 1);
                } else {
                    this.dropItem(OreSpawnMain.CagedSkeleton, 1);
                }

                hit.entityHit.setDead();
            }

            if (hit.entityHit instanceof EntityZombie) {
                if (hit.entityHit instanceof EntityPigZombie) {
                    hit.entityHit.setDead();
                    this.dropItem(OreSpawnMain.CagedZombiePigman, 1);
                } else {
                    hit.entityHit.setDead();
                    this.dropItem(OreSpawnMain.CagedZombie, 1);
                }
            }

            if (hit.entityHit instanceof EntitySlime) {
                if (hit.entityHit instanceof EntityMagmaCube) {
                    hit.entityHit.setDead();
                    this.dropItem(OreSpawnMain.CagedMagmaCube, 1);
                } else {
                    hit.entityHit.setDead();
                    this.dropItem(OreSpawnMain.CagedSlime, 1);
                }
            }

            if (hit.entityHit instanceof EntityGhast) {
                if (this.rand.nextInt(10) < 2) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedGhast, 1);
            }

            if (hit.entityHit instanceof EntityEnderman) {
                if (this.rand.nextInt(10) < 2) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedEnderman, 1);
            }

            if (hit.entityHit instanceof EntitySilverfish) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedSilverfish, 2);
            }

            if (hit.entityHit instanceof EntityWitch) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedWitch, 1);
            }

            if (hit.entityHit instanceof EntitySheep) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedSheep, 1);
            }

            if (hit.entityHit instanceof EntityWolf) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedWolf, 1);
            }

            if (hit.entityHit instanceof EntityOcelot) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedOcelot, 1);
            }

            if (hit.entityHit instanceof EntityBlaze) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedBlaze, 1);
            }

            if (hit.entityHit instanceof Girlfriend) {
                Girlfriend gf = (Girlfriend) hit.entityHit;
                if (!gf.isTamed()) {
                    hit.entityHit.setDead();
                    this.dropItem(OreSpawnMain.CagedGirlfriend, 1);
                }
            }

            if (hit.entityHit instanceof Boyfriend) {
                Boyfriend gf = (Boyfriend) hit.entityHit;
                if (!gf.isTamed()) {
                    hit.entityHit.setDead();
                    this.dropItem(OreSpawnMain.CagedBoyfriend, 1);
                }
            }

            if (hit.entityHit instanceof EntityDragon) {
                if (this.rand.nextInt(10) < 5) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                EntityDragon dr = (EntityDragon) hit.entityHit;
                dr.setDead();
                this.dropItem(OreSpawnMain.CagedEnderDragon, 1);
            }

            if (hit.entityHit instanceof EntityDragonPart) {
                if (this.rand.nextInt(10) < 5) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                EntityDragonPart dp = (EntityDragonPart) hit.entityHit;
                EntityDragon dr = (EntityDragon) dp.entityDragonObj;
                dr.setDead();
                this.dropItem(OreSpawnMain.CagedEnderDragon, 1);
            }

            if (hit.entityHit instanceof EntitySnowman) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedSnowGolem, 1);
            }

            if (hit.entityHit instanceof EntityIronGolem) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedIronGolem, 1);
            }

            if (hit.entityHit instanceof EntityWither) {
                if (this.rand.nextInt(10) < 2) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedWitherBoss, 1);
            }

            if (hit.entityHit instanceof CrystalCow) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedCrystalCow, 1);
                if (!this.worldObj.isRemote) {
                    this.setDead();
                }

                return;
            }

            if (hit.entityHit instanceof EnchantedCow) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedEnchantedCow, 1);
                if (!this.worldObj.isRemote) {
                    this.setDead();
                }

                return;
            }

            if (hit.entityHit instanceof GoldCow) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedGoldCow, 1);
                if (!this.worldObj.isRemote) {
                    this.setDead();
                }

                return;
            }

            if (hit.entityHit instanceof RedCow) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedRedCow, 1);
                if (!this.worldObj.isRemote) {
                    this.setDead();
                }

                return;
            }

            if (hit.entityHit instanceof EntityCow) {
                if (hit.entityHit instanceof EntityMooshroom) {
                    hit.entityHit.setDead();
                    this.dropItem(OreSpawnMain.CagedMooshroom, 1);
                } else {
                    hit.entityHit.setDead();
                    this.dropItem(OreSpawnMain.CagedCow, 1);
                }

                if (!this.worldObj.isRemote) {
                    this.setDead();
                }

                return;
            }

            if (hit.entityHit instanceof EntityVillager) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedVillager, 1);
                if (!this.worldObj.isRemote) {
                    this.setDead();
                }

                return;
            }

            if (hit.entityHit instanceof Mothra) {
                if (this.rand.nextInt(10) < 4) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedMOTHRA, 1);
            }

            if (hit.entityHit instanceof Alosaurus) {
                if (this.rand.nextInt(10) < 4) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedAlo, 1);
            }

            if (hit.entityHit instanceof Cryolophosaurus) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedCryo, 1);
            }

            if (hit.entityHit instanceof Camarasaurus) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedCama, 1);
            }

            if (hit.entityHit instanceof VelocityRaptor) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedVelo, 1);
            }

            if (hit.entityHit instanceof Hydrolisc) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedHydro, 1);
            }

            if (hit.entityHit instanceof Basilisk) {
                if (this.rand.nextInt(10) < 6) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedBasil, 1);
            }

            if (hit.entityHit instanceof Dragonfly) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedDragonfly, 2);
            }

            if (hit.entityHit instanceof EmperorScorpion) {
                if (this.rand.nextInt(10) < 7) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedEmperorScorpion, 1);
            }

            if (hit.entityHit instanceof Cephadrome) {
                if (this.rand.nextInt(10) < 7) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedCephadrome, 1);
            }

            if (hit.entityHit instanceof Dragon) {
                if (this.rand.nextInt(10) < 7) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedDragon, 1);
            }

            if (hit.entityHit instanceof Scorpion) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedScorpion, 1);
            }

            if (hit.entityHit instanceof CaveFisher) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedCaveFisher, 1);
            }

            if (hit.entityHit instanceof Spyro) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedSpyro, 1);
            }

            if (hit.entityHit instanceof Baryonyx) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedBaryonyx, 1);
            }

            if (hit.entityHit instanceof GammaMetroid) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedGammaMetroid, 1);
            }

            if (hit.entityHit instanceof Cockateil) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedCockateil, 4);
            }

            if (hit.entityHit instanceof AttackSquid) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedAttackSquid, 6);
            }

            if (hit.entityHit instanceof Kyuubi) {
                if (this.rand.nextInt(10) < 3) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedKyuubi, 1);
            }

            if (hit.entityHit instanceof WaterDragon) {
                if (this.rand.nextInt(10) < 6) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedWaterDragon, 1);
            }

            if (hit.entityHit instanceof Kraken) {
                if (this.rand.nextInt(100) < 95) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedKraken, 1);
            }

            if (hit.entityHit instanceof Lizard) {
                if (this.rand.nextInt(10) < 2) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedLizard, 1);
            }

            if (hit.entityHit instanceof Alien) {
                if (this.rand.nextInt(10) < 5) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedAlien, 1);
            }

            if (hit.entityHit instanceof Bee) {
                if (this.rand.nextInt(10) < 3) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedBee, 1);
            }

            if (hit.entityHit instanceof Firefly) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedFirefly, 1);
            }

            if (hit.entityHit instanceof Chipmunk) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedChipmunk, 1);
            }

            if (hit.entityHit instanceof Gazelle) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedGazelle, 1);
            }

            if (hit.entityHit instanceof Ostrich) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedOstrich, 1);
            }

            if (hit.entityHit instanceof TrooperBug) {
                if (this.rand.nextInt(10) < 6) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedTrooper, 1);
            }

            if (hit.entityHit instanceof SpitBug) {
                if (this.rand.nextInt(10) < 3) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedSpit, 1);
            }

            if (hit.entityHit instanceof StinkBug) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedStink, 1);
            }

            if (hit.entityHit instanceof CreepingHorror) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedCreepingHorror, 1);
            }

            if (hit.entityHit instanceof TerribleTerror) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedTerribleTerror, 1);
            }

            if (hit.entityHit instanceof CliffRacer) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedCliffRacer, 1);
            }

            if (hit.entityHit instanceof Triffid) {
                if (this.rand.nextInt(10) < 6) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedTriffid, 1);
            }

            if (hit.entityHit instanceof PitchBlack) {
                if (this.rand.nextInt(10) < 7) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedPitchBlack, 1);
            }

            if (hit.entityHit instanceof LurkingTerror) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedLurkingTerror, 1);
            }

            if (hit.entityHit instanceof WormSmall) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedSmallWorm, 1);
            }

            if (hit.entityHit instanceof WormMedium) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedMediumWorm, 1);
            }

            if (hit.entityHit instanceof Cassowary) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedCassowary, 1);
            }

            if (hit.entityHit instanceof CloudShark) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedCloudShark, 1);
            }

            if (hit.entityHit instanceof GoldFish) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedGoldFish, 1);
            }

            if (hit.entityHit instanceof LeafMonster) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedLeafMonster, 1);
            }

            if (hit.entityHit instanceof WormLarge) {
                if (this.rand.nextInt(10) < 5) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedLargeWorm, 1);
            }

            if (hit.entityHit instanceof EnderKnight) {
                if (this.rand.nextInt(10) < 3) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedEnderKnight, 1);
            }

            if (hit.entityHit instanceof EnderReaper) {
                if (this.rand.nextInt(10) < 2) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedEnderReaper, 1);
            }

            if (hit.entityHit instanceof Beaver) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedBeaver, 1);
            }

            if (hit.entityHit instanceof Urchin) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedUrchin, 1);
            }

            if (hit.entityHit instanceof Flounder) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedFlounder, 1);
            }

            if (hit.entityHit instanceof Skate) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedSkate, 1);
            }

            if (hit.entityHit instanceof Rotator) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedRotator, 1);
            }

            if (hit.entityHit instanceof Peacock) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedPeacock, 1);
            }

            if (hit.entityHit instanceof Fairy) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedFairy, 1);
            }

            if (hit.entityHit instanceof DungeonBeast) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedDungeonBeast, 1);
            }

            if (hit.entityHit instanceof Vortex) {
                if (this.rand.nextInt(10) < 3) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedVortex, 1);
            }

            if (hit.entityHit instanceof Rat) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedRat, 1);
            }

            if (hit.entityHit instanceof Whale) {
                if (this.rand.nextInt(10) < 2) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedWhale, 1);
            }

            if (hit.entityHit instanceof Irukandji) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedIrukandji, 1);
            }

            if (hit.entityHit instanceof Stinky) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedStinky, 1);
            }

            if (hit.entityHit instanceof Mantis) {
                if (this.rand.nextInt(10) < 3) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedMantis, 1);
            }

            if (hit.entityHit instanceof TRex) {
                if (this.rand.nextInt(10) < 4) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedTRex, 1);
            }

            if (hit.entityHit instanceof HerculesBeetle) {
                if (this.rand.nextInt(10) < 5) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedHercules, 1);
            }

            if (hit.entityHit instanceof EasterBunny) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedEasterBunny, 1);
            }

            if (hit.entityHit instanceof CaterKiller) {
                if (this.rand.nextInt(10) < 7) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedCaterKiller, 1);
            }

            if (hit.entityHit instanceof Molenoid) {
                if (this.rand.nextInt(10) < 5) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedMolenoid, 1);
            }

            if (hit.entityHit instanceof SeaMonster) {
                if (this.rand.nextInt(10) < 3) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedSeaMonster, 1);
            }

            if (hit.entityHit instanceof SeaViper) {
                if (this.rand.nextInt(10) < 4) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedSeaViper, 1);
            }

            if (hit.entityHit instanceof RubberDucky) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedRubberDucky, 1);
            }

            if (hit.entityHit instanceof Leon) {
                if (this.rand.nextInt(10) < 7) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedLeon, 1);
            }

            if (hit.entityHit instanceof Hammerhead) {
                if (this.rand.nextInt(10) < 7) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedHammerhead, 1);
            }

            if (hit.entityHit instanceof BandP) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedCriminal, 1);
            }

            if (hit.entityHit instanceof Cricket) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedCricket, 1);
            }

            if (hit.entityHit instanceof Frog) {
                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedFrog, 1);
            }

            if (hit.entityHit instanceof Brutalfly) {
                if (this.rand.nextInt(10) < 5) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedBrutalfly, 1);
            }

            if (hit.entityHit instanceof Nastysaurus) {
                if (this.rand.nextInt(10) < 7) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedNastysaurus, 1);
            }

            if (hit.entityHit instanceof Pointysaurus) {
                if (this.rand.nextInt(10) < 2) {
                    if (!this.worldObj.isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }

                    return;
                }

                hit.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedPointysaurus, 1);
            }
        } else if (!this.worldObj.isRemote) {
            this.dropItem(OreSpawnMain.CageEmpty, 1);
        }

        if (!this.worldObj.isRemote) {
            this.setDead();
        }
    }

    public void onUpdate() {
        super.onUpdate();
        this.my_rotation += 20.0F;

        while (this.my_rotation > 360.0F) {
            this.my_rotation -= 360.0F;
        }

        this.rotationPitch = this.prevRotationPitch = this.my_rotation;
    }
}
