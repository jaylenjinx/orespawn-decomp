package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelCoin;
import danger.orespawn.entity.Coin;

public class RenderCoin extends RenderLiving {

    protected ModelCoin model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "Cointexture.png");

    public RenderCoin(ModelCoin modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelCoin) this.mainModel;
        this.scale = scale2;
    }

    public void renderCoin(Coin entityCoin, double x, double y, double z, float yaw, float partialTicks) {
        super.doRender(entityCoin, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderCoin((Coin) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderCoin((Coin) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(Coin entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((Coin) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
