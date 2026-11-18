package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelTheKing;
import danger.orespawn.entity.TheKing;

public class RenderTheKing extends RenderLiving {

    protected ModelTheKing model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "TheKingtexture.png");

    public RenderTheKing(ModelTheKing modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelTheKing) this.mainModel;
        this.scale = scale2;
    }

    public void renderTheKing(TheKing entityTheKing, double x, double y, double z, float yaw, float partialTicks) {
        super.doRender(entityTheKing, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderTheKing((TheKing) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderTheKing((TheKing) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(TheKing entity, float f) {
        if (entity != null && entity.getPlayNicely() != 0) {
            GL11.glScalef(this.scale / 4.0F, this.scale / 4.0F, this.scale / 4.0F);
        } else {
            GL11.glScalef(this.scale, this.scale, this.scale);
        }
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((TheKing) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
