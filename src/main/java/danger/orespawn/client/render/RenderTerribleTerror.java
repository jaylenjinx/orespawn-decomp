package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelTerribleTerror;
import danger.orespawn.entity.TerribleTerror;

public class RenderTerribleTerror extends RenderLiving {

    protected ModelTerribleTerror model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "TerribleTerror.png");

    public RenderTerribleTerror(ModelTerribleTerror modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelTerribleTerror) this.mainModel;
        this.scale = scale2;
    }

    public void renderTerribleTerror(TerribleTerror entityTerribleTerror, double x, double y, double z, float yaw,
        float partialTicks) {
        super.doRender(entityTerribleTerror, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderTerribleTerror((TerribleTerror) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderTerribleTerror((TerribleTerror) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(TerribleTerror entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((TerribleTerror) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
