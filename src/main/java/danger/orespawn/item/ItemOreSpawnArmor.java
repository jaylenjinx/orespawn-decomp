package danger.orespawn.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.ArmorStats;
import danger.orespawn.OreSpawnMain;

public class ItemOreSpawnArmor extends ItemArmor {

    private int armor_material = 0;
    private int armor_type = 0;
    private int original_d = 0;

    public ItemOreSpawnArmor(int n, ArmorMaterial enumArmorMaterial, int n2, int n3) {
        super(enumArmorMaterial, n2, n3);
        this.setCreativeTab(CreativeTabs.tabCombat);
        this.armor_material = 0;
        if (enumArmorMaterial == OreSpawnMain.armorLAVAEEL) {
            this.armor_material = 1;
        }

        if (enumArmorMaterial == OreSpawnMain.armorMOTHSCALE) {
            this.armor_material = 2;
        }

        if (enumArmorMaterial == OreSpawnMain.armorEMERALD) {
            this.armor_material = 3;
        }

        if (enumArmorMaterial == OreSpawnMain.armorEXPERIENCE) {
            this.armor_material = 4;
        }

        if (enumArmorMaterial == OreSpawnMain.armorRUBY) {
            this.armor_material = 5;
        }

        if (enumArmorMaterial == OreSpawnMain.armorAMETHYST) {
            this.armor_material = 6;
        }

        if (enumArmorMaterial == OreSpawnMain.armorPINK) {
            this.armor_material = 7;
        }

        if (enumArmorMaterial == OreSpawnMain.armorTIGERSEYE) {
            this.armor_material = 8;
        }

        if (enumArmorMaterial == OreSpawnMain.armorPEACOCK) {
            this.armor_material = 9;
        }

        if (enumArmorMaterial == OreSpawnMain.armorMOBZILLA) {
            this.armor_material = 10;
        }

        if (enumArmorMaterial == OreSpawnMain.armorROYAL) {
            this.armor_material = 11;
        }

        if (enumArmorMaterial == OreSpawnMain.armorLAPIS) {
            this.armor_material = 12;
        }

        if (enumArmorMaterial == OreSpawnMain.armorQUEEN) {
            this.armor_material = 13;
        }

        this.armor_type = n3;
        this.original_d = this.damageReduceAmount;
    }

    public int get_armor_material() {
        return this.armor_material;
    }

    public int get_armor_type() {
        return this.armor_type;
    }

    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        ArmorStats a = null;
        if (this.armor_material == 0) {
            a = OreSpawnMain.Ultimate_armorstats;
        }

        if (this.armor_material == 1) {
            a = OreSpawnMain.LavaEel_armorstats;
        }

        if (this.armor_material == 2) {
            a = OreSpawnMain.MothScale_armorstats;
        }

        if (this.armor_material == 3) {
            a = OreSpawnMain.Emerald_armorstats;
        }

        if (this.armor_material == 4) {
            a = OreSpawnMain.Experience_armorstats;
        }

        if (this.armor_material == 5) {
            a = OreSpawnMain.Ruby_armorstats;
        }

        if (this.armor_material == 6) {
            a = OreSpawnMain.Amethyst_armorstats;
        }

        if (this.armor_material == 7) {
            a = OreSpawnMain.Pink_armorstats;
        }

        if (this.armor_material == 8) {
            a = OreSpawnMain.TigersEye_armorstats;
        }

        if (this.armor_material == 9) {
            a = OreSpawnMain.Peacock_armorstats;
        }

        if (this.armor_material == 10) {
            a = OreSpawnMain.Mobzilla_armorstats;
        }

        if (this.armor_material == 11) {
            a = OreSpawnMain.Royal_armorstats;
        }

        if (this.armor_material == 12) {
            a = OreSpawnMain.Lapis_armorstats;
        }

        if (this.armor_material == 13) {
            a = OreSpawnMain.Queen_armorstats;
        }

        if (a != null) {
            if (a.e_protection != 0) {
                stack.addEnchantment(Enchantment.protection, a.e_protection);
            }

            if (a.e_fireprotection != 0) {
                stack.addEnchantment(Enchantment.fireProtection, a.e_fireprotection);
            }

            if (a.e_blastprotection != 0) {
                stack.addEnchantment(Enchantment.blastProtection, a.e_blastprotection);
            }

            if (a.e_projectileprotection != 0) {
                stack.addEnchantment(Enchantment.projectileProtection, a.e_projectileprotection);
            }

            if (a.e_unbreaking != 0) {
                stack.addEnchantment(Enchantment.unbreaking, a.e_unbreaking);
            }

            if (this.armor_type == 3 && a.e_featherfalling != 0) {
                stack.addEnchantment(Enchantment.featherFalling, a.e_featherfalling);
            }

            if (this.armor_type == 0) {
                if (a.e_respiration != 0) {
                    stack.addEnchantment(Enchantment.respiration, a.e_respiration);
                }

                if (a.e_aquaaffinity != 0) {
                    stack.addEnchantment(Enchantment.aquaAffinity, a.e_aquaaffinity);
                }
            }
        }
    }

    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
        ArmorStats a = null;
        int lvl = 0;
        int enchanted = 0;
        if (this.armor_material == 0) {
            a = OreSpawnMain.Ultimate_armorstats;
        }

        if (this.armor_material == 1) {
            a = OreSpawnMain.LavaEel_armorstats;
        }

        if (this.armor_material == 2) {
            a = OreSpawnMain.MothScale_armorstats;
        }

        if (this.armor_material == 3) {
            a = OreSpawnMain.Emerald_armorstats;
        }

        if (this.armor_material == 4) {
            a = OreSpawnMain.Experience_armorstats;
        }

        if (this.armor_material == 5) {
            a = OreSpawnMain.Ruby_armorstats;
        }

        if (this.armor_material == 6) {
            a = OreSpawnMain.Amethyst_armorstats;
        }

        if (this.armor_material == 7) {
            a = OreSpawnMain.Pink_armorstats;
        }

        if (this.armor_material == 8) {
            a = OreSpawnMain.TigersEye_armorstats;
        }

        if (this.armor_material == 9) {
            a = OreSpawnMain.Peacock_armorstats;
        }

        if (this.armor_material == 10) {
            a = OreSpawnMain.Mobzilla_armorstats;
        }

        if (this.armor_material == 11) {
            a = OreSpawnMain.Royal_armorstats;
        }

        if (this.armor_material == 12) {
            a = OreSpawnMain.Lapis_armorstats;
        }

        if (this.armor_material == 13) {
            a = OreSpawnMain.Queen_armorstats;
        }

        if (a != null) {
            enchanted = a.e_aquaaffinity + a.e_blastprotection + a.e_featherfalling + a.e_fireprotection;
            enchanted += a.e_projectileprotection + a.e_protection + a.e_respiration + a.e_unbreaking;
            if (enchanted > 0) {
                lvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack);
                if (lvl <= 0) {
                    lvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, stack);
                }

                if (lvl <= 0) {
                    lvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, stack);
                }

                if (lvl <= 0) {
                    lvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.projectileProtection.effectId, stack);
                }

                if (lvl <= 0) {
                    lvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.respiration.effectId, stack);
                }

                if (lvl <= 0) {
                    lvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.aquaAffinity.effectId, stack);
                }

                if (lvl <= 0) {
                    lvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
                }

                if (lvl <= 0) {
                    lvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.featherFalling.effectId, stack);
                }

                if (lvl == 0) {
                    if (a.e_protection != 0) {
                        stack.addEnchantment(Enchantment.protection, a.e_protection);
                    }

                    if (a.e_fireprotection != 0) {
                        stack.addEnchantment(Enchantment.fireProtection, a.e_fireprotection);
                    }

                    if (a.e_blastprotection != 0) {
                        stack.addEnchantment(Enchantment.blastProtection, a.e_blastprotection);
                    }

                    if (a.e_projectileprotection != 0) {
                        stack.addEnchantment(Enchantment.projectileProtection, a.e_projectileprotection);
                    }

                    if (a.e_unbreaking != 0) {
                        stack.addEnchantment(Enchantment.unbreaking, a.e_unbreaking);
                    }

                    if (this.armor_type == 3 && a.e_featherfalling != 0) {
                        stack.addEnchantment(Enchantment.featherFalling, a.e_featherfalling);
                    }

                    if (this.armor_type == 0) {
                        if (a.e_respiration != 0) {
                            stack.addEnchantment(Enchantment.respiration, a.e_respiration);
                        }

                        if (a.e_aquaaffinity != 0) {
                            stack.addEnchantment(Enchantment.aquaAffinity, a.e_aquaaffinity);
                        }
                    }
                }
            }
        }
    }

    public String getArmorTexture(ItemStack armor, Entity e, int slot, String layer) {
        if (this.armor_type != 0 && this.armor_type != 1 && this.armor_type != 3) {
            if (this.armor_material == 0) {
                return "orespawn:ultimate_2.png";
            } else if (this.armor_material == 1) {
                return "orespawn:lavaeel_2.png";
            } else if (this.armor_material == 2) {
                return "orespawn:mothscale_2.png";
            } else if (this.armor_material == 4) {
                return "orespawn:experience_2.png";
            } else if (this.armor_material == 5) {
                return "orespawn:ruby_2.png";
            } else if (this.armor_material == 6) {
                return "orespawn:amethyst_2.png";
            } else if (this.armor_material == 7) {
                return "orespawn:pink_2.png";
            } else if (this.armor_material == 8) {
                return "orespawn:tigerseye_2.png";
            } else if (this.armor_material == 9) {
                return "orespawn:peacock_2.png";
            } else if (this.armor_material == 10) {
                return "orespawn:mobzilla_2.png";
            } else if (this.armor_material == 11) {
                return "orespawn:royal_2.png";
            } else if (this.armor_material == 12) {
                return "orespawn:lapis_2.png";
            } else {
                return this.armor_material == 13 ? "orespawn:queen_2.png" : "orespawn:emerald_2.png";
            }
        } else if (this.armor_material == 0) {
            return "orespawn:ultimate_1.png";
        } else if (this.armor_material == 1) {
            return "orespawn:lavaeel_1.png";
        } else if (this.armor_material == 2) {
            return "orespawn:mothscale_1.png";
        } else if (this.armor_material == 4) {
            return "orespawn:experience_1.png";
        } else if (this.armor_material == 5) {
            return "orespawn:ruby_1.png";
        } else if (this.armor_material == 6) {
            return "orespawn:amethyst_1.png";
        } else if (this.armor_material == 7) {
            return "orespawn:pink_1.png";
        } else if (this.armor_material == 8) {
            return "orespawn:tigerseye_1.png";
        } else if (this.armor_material == 9) {
            return "orespawn:peacock_1.png";
        } else if (this.armor_material == 10) {
            return "orespawn:mobzilla_1.png";
        } else if (this.armor_material == 11) {
            return "orespawn:royal_1.png";
        } else if (this.armor_material == 12) {
            return "orespawn:lapis_1.png";
        } else {
            return this.armor_material == 13 ? "orespawn:queen_1.png" : "orespawn:emerald_1.png";
        }
    }

    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        ItemStack boots = null;
        Item ia = null;
        ItemOreSpawnArmor it = null;
        if ((this.armor_material == 11 || this.armor_material == 9) && player != null) {
            boots = player.getEquipmentInSlot(1);
            if (boots != null && (boots.getItem() == OreSpawnMain.RoyalBoots && OreSpawnMain.RoyalGlideEnable != 0
                || boots.getItem() == OreSpawnMain.PeacockFeatherBoots)) {
                if (player.motionY < -0.1F) {
                    player.motionY = -0.1F;
                }

                player.fallDistance = 0.0F;
            }
        }

        if (this.armor_material == 13 && player != null) {
            boots = player.getEquipmentInSlot(1);
            if (boots != null && boots.getItem() == OreSpawnMain.QueenBoots && OreSpawnMain.RoyalGlideEnable != 0) {
                if (player.motionY < -0.25) {
                    player.motionY = -0.25;
                }

                player.fallDistance = 0.0F;
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(
            "OreSpawn:" + this.getUnlocalizedName()
                .substring(5));
    }
}
