package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelRobot5;
import danger.orespawn.entity.Robot5;

public class RenderRobot5 extends RenderLiving {

    protected ModelRobot5 model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "Robot5texture.png");

    public RenderRobot5(ModelRobot5 par1ModelBase, float par2, float par3) {
        super(par1ModelBase, par2 * par3);
        this.model = (ModelRobot5) this.mainModel;
        this.scale = par3;
    }

    public void renderRobot5(Robot5 par1EntityRobot5, double par2, double par4, double par6, float par8, float par9) {
        super.doRender(par1EntityRobot5, par2, par4, par6, par8, par9);
    }

    public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
        this.renderRobot5((Robot5) par1EntityLiving, par2, par4, par6, par8, par9);
    }

    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.renderRobot5((Robot5) par1Entity, par2, par4, par6, par8, par9);
    }

    protected void preRenderScale(Robot5 par1Entity, float par2) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase par1EntityLiving, float par2) {
        this.preRenderScale((Robot5) par1EntityLiving, par2);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
