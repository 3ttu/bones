package bones.entity.skeleton_chicken;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SkeletonChickenModel extends EntityModel<SkeletonChickenEntity> {
    public RendererModel head;
    public RendererModel bill;
    public RendererModel body;
    public RendererModel legLeft;
    public RendererModel legRight;
    public RendererModel wingLeft;
    public RendererModel wingRight;

    public SkeletonChickenModel() {
        head = new RendererModel(this, 0, 0);
        head.setRotationPoint(0, 15, -4);
        head.addBox(-2, -6, -2, 4, 5, 3, 0);
        legRight = new RendererModel(this, 26, 0);
        legRight.setRotationPoint(-2, 19, 1);
        legRight.addBox(-1, 0, -3, 3, 5, 3, 0);
        wingRight = new RendererModel(this, 38, 13);
        wingRight.setRotationPoint(-4, 13, 0);
        wingRight.addBox(0, 0, -3, 1, 4, 6, 0);
        body = new RendererModel(this, 0, 9);
        body.setRotationPoint(0, 16, 0);
        body.addBox(-3, -4, -3, 6, 8, 6, 0);
        body.rotateAngleX = (float) Math.PI / 2;
        bill = new RendererModel(this, 14, 0);
        bill.setRotationPoint(0, 15, -4);
        bill.addBox(-2, -4, -4, 4, 2, 2, 0);
        wingLeft = new RendererModel(this, 24, 13);
        wingLeft.setRotationPoint(4, 13, 0);
        wingLeft.addBox(-1, 0, -3, 1, 4, 6, 0);
        legLeft = new RendererModel(this, 26, 0);
        legLeft.setRotationPoint(1, 19, 1);
        legLeft.addBox(-1, 0, -3, 3, 5, 3, 0);
    }

    public void render(SkeletonChickenEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        if (isChild) {
            GlStateManager.pushMatrix();
            GlStateManager.translatef(0, 5 * scale, 2 * scale);
            head.render(scale);
            bill.render(scale);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scalef(0.5F, 0.5F, 0.5F);
            GlStateManager.translatef(0, 24 * scale, 0);
            body.render(scale);
            legRight.render(scale);
            legLeft.render(scale);
            wingRight.render(scale);
            wingLeft.render(scale);
            GlStateManager.popMatrix();
        } else {
            head.render(scale);
            legRight.render(scale);
            legLeft.render(scale);
            bill.render(scale);
            body.render(scale);
            wingRight.render(scale);
            wingLeft.render(scale);
        }

    }

    public void setRotationAngles(SkeletonChickenEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        head.rotateAngleX = headPitch * ((float) Math.PI / 180);
        head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180);
        bill.rotateAngleX = head.rotateAngleX;
        bill.rotateAngleY = head.rotateAngleY;
        body.rotateAngleX = ((float) Math.PI / 2);
        legRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        legLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        wingRight.rotateAngleZ = ageInTicks;
        wingLeft.rotateAngleZ = -ageInTicks;
    }
}
