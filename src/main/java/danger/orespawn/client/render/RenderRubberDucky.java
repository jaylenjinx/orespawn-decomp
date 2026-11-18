package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelRubberDucky;
import danger.orespawn.entity.RubberDucky;

public class RenderRubberDucky extends RenderLiving {

    protected ModelRubberDucky model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "RubberDuckytexture.png");
    private static final ResourceLocation texture2 = new ResourceLocation("orespawn", "EvilRubberDuckytexture.png");

    public RenderRubberDucky(ModelRubberDucky modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelRubberDucky) this.mainModel;
        this.scale = scale2;
    }

    public void renderRubberDucky(RubberDucky entityRubberDucky, double x, double y, double z, float yaw,
        float partialTicks) {
        super.doRender(entityRubberDucky, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderRubberDucky((RubberDucky) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderRubberDucky((RubberDucky) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(RubberDucky entity, float f) {
        if (entity != null && entity.isChild()) {
            GL11.glScalef(this.scale / 2.0F, this.scale / 2.0F, this.scale / 2.0F);
        } else {
            GL11.glScalef(this.scale, this.scale, this.scale);
        }
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((RubberDucky) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        if (entity instanceof RubberDucky) {
            RubberDucky d = (RubberDucky) entity;
            if (d.getKillCount() >= 5) {
                return texture2;
            }
        }

        return texture;
    }
}
