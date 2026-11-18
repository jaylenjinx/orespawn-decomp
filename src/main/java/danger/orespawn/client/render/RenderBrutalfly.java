package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelBrutalfly;
import danger.orespawn.entity.Brutalfly;

public class RenderBrutalfly extends RenderLiving {

    protected ModelBrutalfly model;
    private float scale = 1.0F;
    private static final ResourceLocation overlay = new ResourceLocation("orespawn", "Brutalfly_overlay2.png");
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "Brutalflytexture.png");

    public RenderBrutalfly(ModelBrutalfly modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelBrutalfly) this.mainModel;
        this.scale = scale2;
    }

    public void renderBrutalfly(Brutalfly brutalfly, double x, double y, double z, float yaw, float partialTicks) {
        super.doRender(brutalfly, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderBrutalfly((Brutalfly) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderBrutalfly((Brutalfly) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(Brutalfly entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((Brutalfly) entity, partialTicks);
    }

    public int shouldRenderPass(EntityLivingBase entity, int pass, float partialTicks) {
        int doit = 0;
        GL11.glDepthMask(true);
        if (pass == 1) {
            float f = entity.ticksExisted + partialTicks;
            this.bindTexture(overlay);
            GL11.glMatrixMode(5890);
            GL11.glLoadIdentity();
            float x = f * 0.01F;
            float y = f * 0.01F;
            GL11.glTranslatef(x, y, 0.0F);
            this.setRenderPassModel(this.model);
            GL11.glMatrixMode(5888);
            GL11.glEnable(3042);
            float f2 = 0.5F;
            GL11.glColor4f(f2, f2, f2, 1.0F);
            GL11.glDisable(2896);
            GL11.glBlendFunc(1, 1);
            return 1;
        } else {
            if (pass == 2) {
                GL11.glMatrixMode(5890);
                GL11.glLoadIdentity();
                GL11.glMatrixMode(5888);
                GL11.glEnable(2896);
                GL11.glDisable(3042);
            }

            return -1;
        }
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
