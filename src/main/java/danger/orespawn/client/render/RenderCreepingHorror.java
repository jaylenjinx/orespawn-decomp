package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelCreepingHorror;
import danger.orespawn.entity.CreepingHorror;

public class RenderCreepingHorror extends RenderLiving {

    protected ModelCreepingHorror model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "CreepingHorror.png");

    public RenderCreepingHorror(ModelCreepingHorror modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelCreepingHorror) this.mainModel;
        this.scale = scale2;
    }

    public void renderCreepingHorror(CreepingHorror entityCreepingHorror, double x, double y, double z, float yaw,
        float partialTicks) {
        super.doRender(entityCreepingHorror, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderCreepingHorror((CreepingHorror) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderCreepingHorror((CreepingHorror) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(CreepingHorror entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((CreepingHorror) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
