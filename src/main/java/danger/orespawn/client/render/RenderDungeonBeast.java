package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelDungeonBeast;
import danger.orespawn.entity.DungeonBeast;

public class RenderDungeonBeast extends RenderLiving {

    protected ModelDungeonBeast model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "Botwtexture.png");

    public RenderDungeonBeast(ModelDungeonBeast modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelDungeonBeast) this.mainModel;
        this.scale = scale2;
    }

    public void renderDungeonBeast(DungeonBeast entityDungeonBeast, double x, double y, double z, float yaw,
        float partialTicks) {
        super.doRender(entityDungeonBeast, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderDungeonBeast((DungeonBeast) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderDungeonBeast((DungeonBeast) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(DungeonBeast entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((DungeonBeast) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
