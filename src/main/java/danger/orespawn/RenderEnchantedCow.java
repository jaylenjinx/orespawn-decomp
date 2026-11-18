package danger.orespawn;

import net.minecraft.client.model.ModelCow;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class RenderEnchantedCow extends RenderLiving {

    protected ModelCow model = (ModelCow) this.mainModel;
    private static final ResourceLocation texture3 = new ResourceLocation("orespawn", "crystal_cow.png");
    private static final ResourceLocation texture1 = new ResourceLocation("orespawn", "red_cow.png");
    private static final ResourceLocation texture2 = new ResourceLocation("orespawn", "gold_cow.png");

    public RenderEnchantedCow(ModelCow par1ModelBase, float par2) {
        super(par1ModelBase, par2);
    }

    public void renderEnchantedCow(RedCow par1EntityEnchantedCow, double par2, double par4, double par6, float par8,
        float par9) {
        super.doRender(par1EntityEnchantedCow, par2, par4, par6, par8, par9);
    }

    public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
        this.renderEnchantedCow((RedCow) par1EntityLiving, par2, par4, par6, par8, par9);
    }

    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.renderEnchantedCow((RedCow) par1Entity, par2, par4, par6, par8, par9);
    }

    public int shouldRenderPass(EntityLivingBase par1EntityLiving, int par2, float par3) {
        if (par1EntityLiving instanceof EnchantedCow && par2 == 3) {
            this.setRenderPassModel(this.model);
            GL11.glColor3f(1.0F, 1.0F, 1.0F);
            return 31;
        } else {
            return -1;
        }
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        if (entity instanceof EnchantedCow) {
            return texture2;
        } else if (entity instanceof GoldCow) {
            return texture2;
        } else {
            return entity instanceof CrystalCow ? texture3 : texture1;
        }
    }
}
