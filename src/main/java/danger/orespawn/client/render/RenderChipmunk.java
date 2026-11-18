package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelChipmunk;
import danger.orespawn.entity.Chipmunk;
import danger.orespawn.entity.EntityCannonFodder;

public class RenderChipmunk extends RenderLiving {

    protected ModelChipmunk model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "chipmunktexture.png");
    private static final ResourceLocation texture2 = new ResourceLocation("orespawn", "chipmunktexture2.png");
    private static final ResourceLocation texture3 = new ResourceLocation("orespawn", "chipmunktexture3.png");

    public RenderChipmunk(ModelChipmunk modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelChipmunk) this.mainModel;
        this.scale = scale2;
    }

    public void renderChipmunk(Chipmunk entityChipmunk, double x, double y, double z, float yaw, float partialTicks) {
        super.doRender(entityChipmunk, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderChipmunk((Chipmunk) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderChipmunk((Chipmunk) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(Chipmunk entity, float f) {
        if (entity != null && entity.isChild()) {
            GL11.glScalef(this.scale / 2.0F, this.scale / 2.0F, this.scale / 2.0F);
        } else {
            GL11.glScalef(this.scale, this.scale, this.scale);
        }
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((Chipmunk) entity, partialTicks);
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
