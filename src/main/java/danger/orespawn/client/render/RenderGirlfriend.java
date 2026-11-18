package danger.orespawn.client.render;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.OreSpawnMain;
import danger.orespawn.entity.Girlfriend;

public class RenderGirlfriend extends RenderBiped {

    protected ModelBiped model = (ModelBiped) this.mainModel;

    public RenderGirlfriend(ModelBiped modelBase, float f) {
        super(modelBase, f);
    }

    public void renderGirlfriend(Girlfriend entityGirlfriend, double x, double y, double z, float yaw,
        float partialTicks) {
        super.doRender(entityGirlfriend, x, y, z, yaw, partialTicks);
    }

    public void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        if (OreSpawnMain.valentines_day != 0 && entity != null && entity instanceof Girlfriend) {
            Girlfriend gf = (Girlfriend) entity;
            if (gf.feelingBetter == 0) {
                GL11.glScalef(5.0F, 5.0F, 5.0F);
            }
        }
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderGirlfriend((Girlfriend) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderGirlfriend((Girlfriend) entity, x, y, z, yaw, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        Girlfriend g = (Girlfriend) entity;
        return g.getTexture();
    }
}
