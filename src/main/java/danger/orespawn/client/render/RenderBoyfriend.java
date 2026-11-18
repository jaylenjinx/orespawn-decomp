package danger.orespawn.client.render;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

import danger.orespawn.entity.Boyfriend;

public class RenderBoyfriend extends RenderBiped {

    protected ModelBiped model = (ModelBiped) this.mainModel;

    public RenderBoyfriend(ModelBiped modelBase, float f) {
        super(modelBase, f);
    }

    public void renderBoyfriend(Boyfriend entityBoyfriend, double x, double y, double z, float yaw,
        float partialTicks) {
        super.doRender(entityBoyfriend, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderBoyfriend((Boyfriend) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderBoyfriend((Boyfriend) entity, x, y, z, yaw, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        Boyfriend g = (Boyfriend) entity;
        return g.getTexture();
    }
}
