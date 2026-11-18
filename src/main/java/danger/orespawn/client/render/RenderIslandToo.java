package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelIsland;
import danger.orespawn.entity.IslandToo;

public class RenderIslandToo extends RenderLiving {

    protected ModelIsland model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "IslandToo.png");

    public RenderIslandToo(ModelIsland modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelIsland) this.mainModel;
        this.scale = scale2;
    }

    public void renderIslandToo(IslandToo entityIslandToo, double x, double y, double z, float yaw,
        float partialTicks) {
        super.doRender(entityIslandToo, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderIslandToo((IslandToo) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderIslandToo((IslandToo) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(IslandToo entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((IslandToo) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
