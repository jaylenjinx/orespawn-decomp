package danger.orespawn;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class RenderRockBase extends RenderLiving {

    protected ModelRockBase model;
    private float scale = 1.0F;
    private static final ResourceLocation texture1 = new ResourceLocation("orespawn", "Rocktexture.png");
    private static final ResourceLocation texture2 = new ResourceLocation("orespawn", "Rocktexture.png");
    private static final ResourceLocation texture3 = new ResourceLocation("orespawn", "RockRedtexture.png");
    private static final ResourceLocation texture4 = new ResourceLocation("orespawn", "RockGreentexture.png");
    private static final ResourceLocation texture5 = new ResourceLocation("orespawn", "RockBluetexture.png");
    private static final ResourceLocation texture6 = new ResourceLocation("orespawn", "RockPurpletexture.png");
    private static final ResourceLocation texture7 = new ResourceLocation("orespawn", "Rocktexture.png");
    private static final ResourceLocation texture8 = new ResourceLocation("orespawn", "RockTNTtexture.png");
    private static final ResourceLocation texture9 = new ResourceLocation("orespawn", "rockcrystaltexture.png");
    private static final ResourceLocation texture10 = new ResourceLocation("orespawn", "rockcrystalgreentexture.png");
    private static final ResourceLocation texture11 = new ResourceLocation("orespawn", "rockcrystalbluetexture.png");
    private static final ResourceLocation texture12 = new ResourceLocation("orespawn", "rockcrystaltnttexture.png");

    public RenderRockBase(ModelRockBase par1ModelBase, float par2, float par3) {
        super(par1ModelBase, par2 * par3);
        this.model = (ModelRockBase) this.mainModel;
        this.scale = par3;
    }

    public void renderRockBase(RockBase par1EntityRockBase, double par2, double par4, double par6, float par8,
        float par9) {
        super.doRender(par1EntityRockBase, par2, par4, par6, par8, par9);
    }

    public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
        this.renderRockBase((RockBase) par1EntityLiving, par2, par4, par6, par8, par9);
    }

    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.renderRockBase((RockBase) par1Entity, par2, par4, par6, par8, par9);
    }

    protected void preRenderScale(RockBase par1Entity, float par2) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase par1EntityLiving, float par2) {
        this.preRenderScale((RockBase) par1EntityLiving, par2);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        RockBase r = (RockBase) entity;
        int i = r.getRockType();
        if (i == 1) {
            return texture1;
        } else if (i == 2) {
            return texture2;
        } else if (i == 3) {
            return texture3;
        } else if (i == 4) {
            return texture4;
        } else if (i == 5) {
            return texture5;
        } else if (i == 6) {
            return texture6;
        } else if (i == 7) {
            return texture7;
        } else if (i == 8) {
            return texture8;
        } else if (i == 9) {
            return texture9;
        } else if (i == 10) {
            return texture10;
        } else if (i == 11) {
            return texture11;
        } else {
            return i == 12 ? texture12 : texture1;
        }
    }
}
