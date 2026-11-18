package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelLeafMonster;
import danger.orespawn.entity.LeafMonster;

public class RenderLeafMonster extends RenderLiving {

    protected ModelLeafMonster model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "LeafMonstertexture.png");

    public RenderLeafMonster(ModelLeafMonster modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelLeafMonster) this.mainModel;
        this.scale = scale2;
    }

    public void renderLeafMonster(LeafMonster entityLeafMonster, double x, double y, double z, float yaw,
        float partialTicks) {
        super.doRender(entityLeafMonster, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderLeafMonster((LeafMonster) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderLeafMonster((LeafMonster) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(LeafMonster entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((LeafMonster) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
