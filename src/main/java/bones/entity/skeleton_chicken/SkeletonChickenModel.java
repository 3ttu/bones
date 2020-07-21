package bones.entity.skeleton_chicken;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SkeletonChickenModel extends AgeableModel<SkeletonChickenEntity> {

    public ModelRenderer head;
    public ModelRenderer bill;
    public ModelRenderer body;
    public ModelRenderer leftLeg;
    public ModelRenderer rightLeg;
    public ModelRenderer leftWing;
    public ModelRenderer rightWing;

    public SkeletonChickenModel() {
        head = new ModelRenderer(this, 0, 0);
        head.setRotationPoint(0, 15, -4);
        head.addBox(-2, -6, -2, 4, 5, 3, 0);
        rightLeg = new ModelRenderer(this, 26, 0);
        rightLeg.setRotationPoint(-2, 19, 1);
        rightLeg.addBox(-1, 0, -3, 3, 5, 3, 0);
        rightWing = new ModelRenderer(this, 38, 13);
        rightWing.setRotationPoint(-4, 13, 0);
        rightWing.addBox(0, 0, -3, 1, 4, 6, 0);
        body = new ModelRenderer(this, 0, 9);
        body.setRotationPoint(0, 16, 0);
        body.addBox(-3, -4, -3, 6, 8, 6, 0);
        body.rotateAngleX = (float) Math.PI / 2;
        bill = new ModelRenderer(this, 14, 0);
        bill.setRotationPoint(0, 15, -4);
        bill.addBox(-2, -4, -4, 4, 2, 2, 0);
        leftWing = new ModelRenderer(this, 24, 13);
        leftWing.setRotationPoint(4, 13, 0);
        leftWing.addBox(-1, 0, -3, 1, 4, 6, 0);
        leftLeg = new ModelRenderer(this, 26, 0);
        leftLeg.setRotationPoint(1, 19, 1);
        leftLeg.addBox(-1, 0, -3, 3, 5, 3, 0);
    }

    @Override
    public void setRotationAngles(SkeletonChickenEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        head.rotateAngleX = headPitch * ((float) Math.PI / 180);
        head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180);
        bill.rotateAngleX = head.rotateAngleX;
        bill.rotateAngleY = head.rotateAngleY;
        body.rotateAngleX = ((float) Math.PI / 2);
        rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        rightWing.rotateAngleZ = ageInTicks;
        leftWing.rotateAngleZ = -ageInTicks;
    }

    @Override
    protected Iterable<ModelRenderer> getHeadParts() {
        return ImmutableList.of(head, bill);
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of(body, leftLeg, rightLeg, leftWing, rightWing);
    }
}
