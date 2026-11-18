package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelPitchBlack;
import danger.orespawn.entity.PitchBlack;

public class RenderPitchBlack extends RenderLiving {

    protected ModelPitchBlack model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "PitchBlacktexture.png");

    public RenderPitchBlack(ModelPitchBlack modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelPitchBlack) this.mainModel;
        this.scale = scale2;
    }

    public void renderPitchBlack(PitchBlack entityPitchBlack, double x, double y, double z, float yaw,
        float partialTicks) {
        super.doRender(entityPitchBlack, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderPitchBlack((PitchBlack) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderPitchBlack((PitchBlack) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(PitchBlack entity, float f) {
        float pscale = entity.getPitchBlackScale();
        GL11.glScalef(pscale, pscale, pscale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((PitchBlack) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
