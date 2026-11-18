package danger.orespawn.client.render;

import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;

import danger.orespawn.entity.SpiderDriver;

public class RenderSpiderDriver extends RenderSpider {

    private static final ResourceLocation texture = new ResourceLocation("orespawn", "spiderdriver.png");

    public RenderSpiderDriver(ModelSpider modelSpider, float f) {}

    public void renderSpiderDriver(SpiderDriver entitySpiderDriver, double x, double y, double z, float yaw,
        float partialTicks) {
        super.doRender(entitySpiderDriver, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderSpiderDriver((SpiderDriver) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderSpiderDriver((SpiderDriver) entity, x, y, z, yaw, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }

    protected ResourceLocation getSpiderTextures(EntitySpider entitySpider) {
        return texture;
    }
}
