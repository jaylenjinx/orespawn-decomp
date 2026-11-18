package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelCaterKiller;
import danger.orespawn.entity.CaterKiller;

public class RenderCaterKiller extends RenderLiving {

    protected ModelCaterKiller model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "CaterKillertexture.png");

    public RenderCaterKiller(ModelCaterKiller par1ModelBase, float par2, float par3) {
        super(par1ModelBase, par2 * par3);
        this.model = (ModelCaterKiller) this.mainModel;
        this.scale = par3;
    }

    public void renderCaterKiller(CaterKiller par1EntityCaterKiller, double par2, double par4, double par6, float par8,
        float par9) {
        super.doRender(par1EntityCaterKiller, par2, par4, par6, par8, par9);
    }

    public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
        this.renderCaterKiller((CaterKiller) par1EntityLiving, par2, par4, par6, par8, par9);
    }

    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.renderCaterKiller((CaterKiller) par1Entity, par2, par4, par6, par8, par9);
    }

    protected void preRenderScale(CaterKiller par1Entity, float par2) {
        if (par1Entity != null && par1Entity.getPlayNicely() != 0) {
            GL11.glScalef(this.scale / 2.0F, this.scale / 2.0F, this.scale / 2.0F);
        } else {
            GL11.glScalef(this.scale, this.scale, this.scale);
        }
    }

    protected void preRenderCallback(EntityLivingBase par1EntityLiving, float par2) {
        this.preRenderScale((CaterKiller) par1EntityLiving, par2);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
