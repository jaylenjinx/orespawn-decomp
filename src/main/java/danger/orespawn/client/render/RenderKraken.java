package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelKraken;
import danger.orespawn.entity.Kraken;

public class RenderKraken extends RenderLiving {

    protected ModelKraken model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "Kraken.png");

    public RenderKraken(ModelKraken modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelKraken) this.mainModel;
        this.scale = scale2;
    }

    public void renderKraken(Kraken entityKraken, double x, double y, double z, float yaw, float partialTicks) {
        super.doRender(entityKraken, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderKraken((Kraken) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderKraken((Kraken) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(Kraken entity, float f) {
        if (entity != null && entity.getPlayNicely() != 0) {
            GL11.glScalef(this.scale / 3.0F, this.scale / 3.0F, this.scale / 3.0F);
        } else {
            GL11.glScalef(this.scale, this.scale, this.scale);
        }
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((Kraken) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
