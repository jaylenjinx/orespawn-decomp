package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelVelocityRaptor;
import danger.orespawn.entity.EntityCannonFodder;
import danger.orespawn.entity.VelocityRaptor;

public class RenderVelocityRaptor extends RenderLiving {

    protected ModelVelocityRaptor model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "velocityraptor.png");
    private static final ResourceLocation texture2 = new ResourceLocation("orespawn", "velocityraptor2.png");
    private static final ResourceLocation texture3 = new ResourceLocation("orespawn", "velocityraptor3.png");

    public RenderVelocityRaptor(ModelVelocityRaptor modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelVelocityRaptor) this.mainModel;
        this.scale = scale2;
    }

    public void renderVelocityRaptor(VelocityRaptor entityVelocityRaptor, double x, double y, double z, float yaw,
        float partialTicks) {
        super.doRender(entityVelocityRaptor, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderVelocityRaptor((VelocityRaptor) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderVelocityRaptor((VelocityRaptor) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(VelocityRaptor entity, float f) {
        if (entity != null && entity.isChild()) {
            GL11.glScalef(this.scale / 2.0F, this.scale / 2.0F, this.scale / 2.0F);
        } else {
            GL11.glScalef(this.scale, this.scale, this.scale);
        }
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((VelocityRaptor) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        if (entity instanceof EntityCannonFodder) {
            EntityCannonFodder c = (EntityCannonFodder) entity;
            if (c.get_is_activated() != 0) {
                if (c.getHatColor() == 2) {
                    return texture2;
                }

                if (c.getHatColor() == 3) {
                    return texture3;
                }
            }
        }

        return texture;
    }
}
