package danger.orespawn.client.render.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import danger.orespawn.client.model.ModelQueenBattleAxe;

public class RenderQueenBattleAxe implements IItemRenderer {

    protected ModelQueenBattleAxe modelQueenBattleAxe = new ModelQueenBattleAxe();
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "QueenBattleAxetexture.png");

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
                this.renderSwordF5(3.0F, -8.0F, -2.0F, 0.35F);
                break;
            case EQUIPPED_FIRST_PERSON:
                this.renderSword(-2.0F, -4.0F, -6.0F, 0.35F);
        }
    }

    private void renderSword(float x, float y, float z, float scale) {
        GL11.glPushMatrix();
        GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(190.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(15.0F, 0.0F, 0.0F, 1.0F);
        GL11.glScalef(scale, scale, scale);
        GL11.glTranslatef(x, y, z);
        FMLClientHandler.instance()
            .getClient().renderEngine.bindTexture(texture);
        this.modelQueenBattleAxe.render();
        GL11.glPopMatrix();
    }

    private void renderSwordF5(float x, float y, float z, float scale) {
        GL11.glPushMatrix();
        GL11.glRotatef(180.0F, 1.0F, 0.25F, 0.0F);
        GL11.glScalef(scale, scale, scale);
        GL11.glTranslatef(x, y, z);
        FMLClientHandler.instance()
            .getClient().renderEngine.bindTexture(texture);
        this.modelQueenBattleAxe.render();
        GL11.glPopMatrix();
    }
}
