package danger.orespawn;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class RenderStinky extends RenderLiving {

    protected ModelStinky model;
    private float scale = 1.0F;
    private static final ResourceLocation texture1 = new ResourceLocation("orespawn", "Stinkytexture1.png");
    private static final ResourceLocation texture2 = new ResourceLocation("orespawn", "Stinkytexture2.png");
    private static final ResourceLocation texture3 = new ResourceLocation("orespawn", "Stinkytexture3.png");
    private static final ResourceLocation texture4 = new ResourceLocation("orespawn", "Stinkytexture4.png");
    private static final ResourceLocation texture5 = new ResourceLocation("orespawn", "Stinkytexture5.png");
    private static final ResourceLocation texture6 = new ResourceLocation("orespawn", "Stinkytexture6.png");
    private static final ResourceLocation texture7 = new ResourceLocation("orespawn", "Stinkytexture7.png");
    private static final ResourceLocation texture8 = new ResourceLocation("orespawn", "Stinkytexture8.png");
    private static final ResourceLocation texture9 = new ResourceLocation("orespawn", "Stinkytexture9.png");
    private static final ResourceLocation texture10 = new ResourceLocation("orespawn", "Stinkytexture10.png");
    private static final ResourceLocation texture11 = new ResourceLocation("orespawn", "Stinkytexture11.png");
    private static final ResourceLocation texture12 = new ResourceLocation("orespawn", "Stinkytexture12.png");
    private static final ResourceLocation texture13 = new ResourceLocation("orespawn", "Stinkytexture13.png");
    private static final ResourceLocation texture14 = new ResourceLocation("orespawn", "Stinkytexture14.png");
    private static final ResourceLocation texture15 = new ResourceLocation("orespawn", "Stinkytexture15.png");
    private static final ResourceLocation texture16 = new ResourceLocation("orespawn", "Stinkytexture16.png");
    private static final ResourceLocation texture17 = new ResourceLocation("orespawn", "Stinkytexture17.png");
    private static final ResourceLocation texture18 = new ResourceLocation("orespawn", "Stinkytexture18.png");
    private static final ResourceLocation texture19 = new ResourceLocation("orespawn", "Stinkytexture19.png");

    public RenderStinky(ModelStinky par1ModelBase, float par2, float par3) {
        super(par1ModelBase, par2 * par3);
        this.model = (ModelStinky) this.mainModel;
        this.scale = par3;
    }

    public void renderStinky(Stinky par1EntityStinky, double par2, double par4, double par6, float par8, float par9) {
        super.doRender(par1EntityStinky, par2, par4, par6, par8, par9);
    }

    public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
        this.renderStinky((Stinky) par1EntityLiving, par2, par4, par6, par8, par9);
    }

    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.renderStinky((Stinky) par1Entity, par2, par4, par6, par8, par9);
    }

    protected void preRenderScale(Stinky par1Entity, float par2) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase par1EntityLiving, float par2) {
        this.preRenderScale((Stinky) par1EntityLiving, par2);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        Stinky s = (Stinky) entity;
        int i = s.getSkin();
        if (i == 1) {
            return texture2;
        } else if (i == 2) {
            return texture3;
        } else if (i == 3) {
            return texture4;
        } else if (i == 4) {
            return texture5;
        } else if (i == 5) {
            return texture6;
        } else if (i == 6) {
            return texture7;
        } else if (i == 7) {
            return texture8;
        } else if (i == 8) {
            return texture9;
        } else if (i == 9) {
            return texture10;
        } else if (i == 10) {
            return texture11;
        } else if (i == 11) {
            return texture12;
        } else if (i == 12) {
            return texture13;
        } else if (i == 13) {
            return texture14;
        } else if (i == 14) {
            return texture15;
        } else if (i == 15) {
            return texture16;
        } else if (i == 16) {
            return texture17;
        } else if (i == 17) {
            return texture18;
        } else {
            return i == 18 ? texture19 : texture1;
        }
    }
}
