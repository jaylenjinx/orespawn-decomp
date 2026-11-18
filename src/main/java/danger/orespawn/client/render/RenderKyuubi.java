package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelKyuubi;
import danger.orespawn.entity.Kyuubi;

public class RenderKyuubi extends RenderLiving {

    protected ModelKyuubi model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "Kyuubi.png");

    public RenderKyuubi(ModelKyuubi modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelKyuubi) this.mainModel;
        this.scale = scale2;
    }

    public void renderKyuubi(Kyuubi entityKyuubi, double x, double y, double z, float yaw, float partialTicks) {
        super.doRender(entityKyuubi, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderKyuubi((Kyuubi) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderKyuubi((Kyuubi) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(Kyuubi entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((Kyuubi) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
