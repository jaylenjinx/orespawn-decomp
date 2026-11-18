package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import danger.orespawn.client.model.ModelTheKing;
import danger.orespawn.entity.KingHead;

public class RenderKingHead extends RenderLiving {

    public RenderKingHead(ModelTheKing modelBase, float f, float f2) {
        super(modelBase, f * f2);
    }

    public void renderKingHead(KingHead entityKingHead, double x, double y, double z, float f, float f2) {}

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {}

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {}

    protected void preRenderScale(KingHead entity, float f) {}

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {}

    protected ResourceLocation getEntityTexture(Entity entity) {
        return null;
    }
}
