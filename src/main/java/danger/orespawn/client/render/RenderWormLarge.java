package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelWormLarge;
import danger.orespawn.entity.WormLarge;

public class RenderWormLarge extends RenderLiving {

    protected ModelWormLarge model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "WormLargetexture.png");

    public RenderWormLarge(ModelWormLarge modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelWormLarge) this.mainModel;
        this.scale = scale2;
    }

    public void renderWormLarge(WormLarge wormLarge, double x, double y, double z, float yaw, float partialTicks) {
        super.doRender(wormLarge, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderWormLarge((WormLarge) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderWormLarge((WormLarge) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(WormLarge entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((WormLarge) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
