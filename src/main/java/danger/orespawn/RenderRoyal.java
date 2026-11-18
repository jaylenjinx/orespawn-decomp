package danger.orespawn;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

public class RenderRoyal implements IItemRenderer {

    protected ModelSlice modelSlice = new ModelSlice();
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "Royaltexture.png");

    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        switch (type) {
            case EQUIPPED:
                return true;
            case EQUIPPED_FIRST_PERSON:
                return true;
            default:
                return false;
        }
    }

    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        switch (type) {
            case EQUIPPED:
                this.renderSwordF5(-4.0F, 2.0F, -3.0F, 0.35F);
                break;
            case EQUIPPED_FIRST_PERSON:
                this.renderSword(6.0F, 3.0F, -5.0F, 0.35F);
        }
    }

    private void renderSword(float x, float y, float z, float scale) {
        GL11.glPushMatrix();
        GL11.glRotatef(190.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(25.0F, 0.0F, 0.0F, 1.0F);
        GL11.glScalef(scale, scale, scale);
        GL11.glTranslatef(x, y, z);
        FMLClientHandler.instance()
            .getClient().renderEngine.bindTexture(texture);
        this.modelSlice.render();
        GL11.glPopMatrix();
    }

    private void renderSwordF5(float x, float y, float z, float scale) {
        GL11.glPushMatrix();
        GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
        GL11.glScalef(scale, scale, scale);
        GL11.glTranslatef(x, y, z);
        FMLClientHandler.instance()
            .getClient().renderEngine.bindTexture(texture);
        this.modelSlice.render();
        GL11.glPopMatrix();
    }
}
