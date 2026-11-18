package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelCricket;
import danger.orespawn.entity.Cricket;

public class RenderCricket extends RenderLiving {

    protected ModelCricket model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "Crickettexture.png");

    public RenderCricket(ModelCricket modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelCricket) this.mainModel;
        this.scale = scale2;
    }

    public void renderCricket(Cricket entityCricket, double x, double y, double z, float yaw, float partialTicks) {
        super.doRender(entityCricket, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderCricket((Cricket) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderCricket((Cricket) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(Cricket entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((Cricket) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
