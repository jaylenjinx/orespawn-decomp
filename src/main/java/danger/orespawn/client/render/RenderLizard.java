package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelLizard;
import danger.orespawn.entity.EntityCannonFodder;
import danger.orespawn.entity.Lizard;

public class RenderLizard extends RenderLiving {

    protected ModelLizard model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "Lizard.png");
    private static final ResourceLocation texture2 = new ResourceLocation("orespawn", "Lizard2.png");
    private static final ResourceLocation texture3 = new ResourceLocation("orespawn", "Lizard3.png");

    public RenderLizard(ModelLizard modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelLizard) this.mainModel;
        this.scale = scale2;
    }

    public void renderLizard(Lizard entityLizard, double x, double y, double z, float yaw, float partialTicks) {
        super.doRender(entityLizard, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderLizard((Lizard) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderLizard((Lizard) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(Lizard entity, float f) {
        if (entity != null && entity.isChild()) {
            GL11.glScalef(this.scale / 2.0F, this.scale / 2.0F, this.scale / 2.0F);
        } else {
            GL11.glScalef(this.scale, this.scale, this.scale);
        }
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((Lizard) entity, partialTicks);
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
