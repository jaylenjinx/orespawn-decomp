package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelButterfly;
import danger.orespawn.entity.EntityButterfly;
import danger.orespawn.entity.EntityLunaMoth;
import danger.orespawn.entity.Mothra;

public class RenderButterfly extends RenderLiving {

    protected ModelButterfly model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("textures/entity/creeper/creeper_armor.png");

    public RenderButterfly(ModelButterfly modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelButterfly) this.mainModel;
        this.scale = scale2;
    }

    public void renderButterfly(EntityButterfly entityButterfly, double x, double y, double z, float yaw,
        float partialTicks) {
        super.doRender(entityButterfly, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderButterfly((EntityButterfly) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderButterfly((EntityButterfly) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(EntityButterfly entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((EntityButterfly) entity, partialTicks);
    }

    public int shouldRenderPass(EntityLivingBase entity, int pass, float partialTicks) {
        int doit = 0;
        if (entity instanceof Mothra) {
            doit++;
        } else if (entity instanceof EntityLunaMoth) {
            EntityLunaMoth e = (EntityLunaMoth) entity;
            if (e.moth_type == 0) {
                doit++;
            }
        }

        if (doit != 0) {
            GL11.glDepthMask(true);
            if (pass == 1) {
                float f = entity.ticksExisted + partialTicks;
                this.bindTexture(texture);
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
            }

            if (pass == 2) {
                GL11.glMatrixMode(5890);
                GL11.glLoadIdentity();
                GL11.glMatrixMode(5888);
                GL11.glEnable(2896);
                GL11.glDisable(3042);
            }
        }

        return -1;
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        EntityButterfly a = (EntityButterfly) entity;
        return a.getTexture(a);
    }
}
