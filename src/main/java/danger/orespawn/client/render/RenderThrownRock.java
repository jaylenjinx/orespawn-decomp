package danger.orespawn.client.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.entity.projectile.EntityThrownRock;

@SideOnly(Side.CLIENT)
public class RenderThrownRock extends Render {

    private static final ResourceLocation texture1 = new ResourceLocation("orespawn", "textures/items/rocksmall.png");
    private static final ResourceLocation texture2 = new ResourceLocation("orespawn", "textures/items/rock.png");
    private static final ResourceLocation texture3 = new ResourceLocation("orespawn", "textures/items/rockred.png");
    private static final ResourceLocation texture4 = new ResourceLocation("orespawn", "textures/items/rockgreen.png");
    private static final ResourceLocation texture5 = new ResourceLocation("orespawn", "textures/items/rockblue.png");
    private static final ResourceLocation texture6 = new ResourceLocation("orespawn", "textures/items/rockpurple.png");
    private static final ResourceLocation texture7 = new ResourceLocation("orespawn", "textures/items/rockspikey.png");
    private static final ResourceLocation texture8 = new ResourceLocation("orespawn", "textures/items/rocktnt.png");
    private static final ResourceLocation texture9 = new ResourceLocation(
        "orespawn",
        "textures/items/rockcrystalred.png");
    private static final ResourceLocation texture10 = new ResourceLocation(
        "orespawn",
        "textures/items/rockcrystalgreen.png");
    private static final ResourceLocation texture11 = new ResourceLocation(
        "orespawn",
        "textures/items/rockcrystalblue.png");
    private static final ResourceLocation texture12 = new ResourceLocation(
        "orespawn",
        "textures/items/rockcrystaltnt.png");

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.bindTexture(this.getEntityTexture(entity));
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glEnable(32826);
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        Tessellator tessellator = Tessellator.instance;
        this.func_77026_a(tessellator, 0, entity.rotationPitch);
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }

    private void func_77026_a(Tessellator tessellator, int n, float angle) {
        float f = (n % 16 * 16 + 0) / 16.0F;
        float f2 = (n % 16 * 16 + 16) / 16.0F;
        float f3 = (n / 16 * 16 + 0) / 16.0F;
        float f4 = (n / 16 * 16 + 16) / 16.0F;
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
        EntityThrownRock r = (EntityThrownRock) entity;
        if (r.getRockType() == 1) {
            return texture1;
        } else if (r.getRockType() == 2) {
            return texture2;
        } else if (r.getRockType() == 3) {
            return texture3;
        } else if (r.getRockType() == 4) {
            return texture4;
        } else if (r.getRockType() == 5) {
            return texture5;
        } else if (r.getRockType() == 6) {
            return texture6;
        } else if (r.getRockType() == 7) {
            return texture7;
        } else if (r.getRockType() == 8) {
            return texture8;
        } else if (r.getRockType() == 9) {
            return texture9;
        } else if (r.getRockType() == 10) {
            return texture10;
        } else if (r.getRockType() == 11) {
            return texture11;
        } else {
            return r.getRockType() == 12 ? texture12 : texture1;
        }
    }
}
