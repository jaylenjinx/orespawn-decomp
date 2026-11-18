package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import danger.orespawn.client.model.ModelTheQueen;
import danger.orespawn.entity.QueenHead;

public class RenderQueenHead extends RenderLiving {

    public RenderQueenHead(ModelTheQueen modelBase, float f, float f2) {
        super(modelBase, f * f2);
    }

    public void renderQueenHead(QueenHead entityQueenHead, double x, double y, double z, float f, float f2) {}

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {}

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {}

    protected void preRenderScale(QueenHead entity, float f) {}

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {}

    protected ResourceLocation getEntityTexture(Entity entity) {
        return null;
    }
}
