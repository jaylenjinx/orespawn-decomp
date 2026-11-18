package danger.orespawn.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.client.model.ModelElevator;
import danger.orespawn.entity.Elevator;

@SideOnly(Side.CLIENT)
public class RenderElevator extends Render {

    protected ModelBase modelElevator;

    public RenderElevator() {
        this.shadowSize = 0.25F;
        this.modelElevator = new ModelElevator();
    }

    public void renderElevator(Elevator entityElevator, double x, double y, double z, float f, float f5) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glRotatef(180.0F - f, 0.0F, 1.0F, 0.0F);
        float f2 = entityElevator.getTimeSinceHit() - f5;
        float f3 = entityElevator.getDamageTaken() - f5;
        if (f3 < 0.0F) {
            f3 = 0.0F;
        }

        if (f2 > 0.0F) {
            GL11.glRotatef(
                MathHelper.sin(f2) * f2 * f3 / 10.0F * entityElevator.getForwardDirection(),
                1.0F,
                0.0F,
                0.0F);
        }

        float f4 = 0.75F;
        GL11.glScalef(f4, f4, f4);
        GL11.glScalef(1.0F / f4, 1.0F / f4, 1.0F / f4);
        this.bindTexture(entityElevator.getTexture());
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        this.modelElevator.render(entityElevator, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderElevator((Elevator) entity, x, y, z, yaw, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        Elevator a = (Elevator) entity;
        return a.getTexture();
    }
}
