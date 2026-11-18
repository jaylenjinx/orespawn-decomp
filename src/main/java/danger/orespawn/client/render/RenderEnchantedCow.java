package danger.orespawn.client.render;

import net.minecraft.client.model.ModelCow;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.entity.CrystalCow;
import danger.orespawn.entity.EnchantedCow;
import danger.orespawn.entity.GoldCow;
import danger.orespawn.entity.RedCow;

public class RenderEnchantedCow extends RenderLiving {

    protected ModelCow model = (ModelCow) this.mainModel;
    private static final ResourceLocation texture3 = new ResourceLocation("orespawn", "crystal_cow.png");
    private static final ResourceLocation texture1 = new ResourceLocation("orespawn", "red_cow.png");
    private static final ResourceLocation texture2 = new ResourceLocation("orespawn", "gold_cow.png");

    public RenderEnchantedCow(ModelCow modelBase, float f) {
        super(modelBase, f);
    }

    public void renderEnchantedCow(RedCow entityEnchantedCow, double x, double y, double z, float yaw,
        float partialTicks) {
        super.doRender(entityEnchantedCow, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderEnchantedCow((RedCow) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderEnchantedCow((RedCow) entity, x, y, z, yaw, partialTicks);
    }

    public int shouldRenderPass(EntityLivingBase entity, int pass, float partialTicks) {
        if (entity instanceof EnchantedCow && pass == 3) {
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
