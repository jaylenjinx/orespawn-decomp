package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelPurplePower;
import danger.orespawn.entity.PurplePower;

public class RenderPurplePower extends RenderLiving {

    protected ModelPurplePower model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "PurplePowertexture.png");
    private static final ResourceLocation texture2 = new ResourceLocation("orespawn", "PurplePowertexture2.png");
    private static final ResourceLocation texture3 = new ResourceLocation("orespawn", "PurplePowertexture3.png");
    private static final ResourceLocation texture4 = new ResourceLocation("orespawn", "PurplePowertexture4.png");
    private static final ResourceLocation texture10 = new ResourceLocation("orespawn", "PurplePowertexture10.png");

    public RenderPurplePower(ModelPurplePower modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelPurplePower) this.mainModel;
        this.scale = scale2;
    }

    public void renderPurplePower(PurplePower entityPurplePower, double x, double y, double z, float yaw,
        float partialTicks) {
        super.doRender(entityPurplePower, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderPurplePower((PurplePower) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(PurplePower entity, float f) {
        float localscale = this.scale;
        if (entity.getPurpleType() != 0) {
            localscale = 0.55F;
        }

        GL11.glScalef(localscale, localscale, localscale);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        PurplePower p = (PurplePower) entity;
        int i = p.getPurpleType();
        if (i == 1) {
            return texture2;
        } else if (i == 2) {
            return texture3;
        } else if (i == 3) {
            return texture4;
        } else {
            return i == 10 ? texture10 : texture;
        }
    }
}
