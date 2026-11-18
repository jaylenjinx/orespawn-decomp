package danger.orespawn.client.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSpinner extends Render {

    public int spinItemIconIndex = 160;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "spinners.png");

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.bindTexture(texture);
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glEnable(32826);
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        Tessellator tessellator = Tessellator.instance;
        this.func_77026_a(tessellator, this.spinItemIconIndex, entity.rotationPitch);
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }

    private void func_77026_a(Tessellator tessellator, int n, float angle) {
        float f = (n % 16 * 16 + 0) / 256.0F;
        float f2 = (n % 16 * 16 + 16) / 256.0F;
        float f3 = (n / 16 * 16 + 0) / 256.0F;
        float f4 = (n / 16 * 16 + 16) / 256.0F;
        float f5 = 1.0F;
        float f6 = 0.5F;
        float f7 = 0.25F;
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(angle, 0.0F, 0.0F, 1.0F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        tessellator.addVertexWithUV(0.0F - f6, 0.0F - f7, 0.0, f, f4);
        tessellator.addVertexWithUV(f5 - f6, 0.0F - f7, 0.0, f2, f4);
        tessellator.addVertexWithUV(f5 - f6, f5 - f7, 0.0, f2, f3);
        tessellator.addVertexWithUV(0.0F - f6, f5 - f7, 0.0, f, f3);
        tessellator.draw();
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
