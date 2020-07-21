package bones.entity.skeleton_cow;

import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SkeletonCowModel extends QuadrupedModel<SkeletonCowEntity> {

    public SkeletonCowModel() {
        super(12, 0, false, 10, 4, 2, 2, 24);
        headModel = new ModelRenderer(this, 0, 0);
        headModel.setRotationPoint(0, 4, -8);
        headModel.addBox(-4, -4, -6, 8, 8, 6, 0);
        headModel.setTextureOffset(22, 0).addBox(-5, -5, -4, 1, 3, 1, 0);
        headModel.setTextureOffset(22, 0).addBox(4, -5, -4, 1, 3, 1, 0);

        body = new ModelRenderer(this, 18, 4);
        body.setRotationPoint(0, 5, 2);
        body.addBox(-6, -10, -7, 12, 18, 10, 0);

        body.rotateAngleX = (float) Math.PI / 2;

        legBackRight = new ModelRenderer(this, 0, 16);
        legBackRight.setRotationPoint(-5, 12, 8);
        legBackRight.addBox(-1, 0, -1, 2, 12, 2, 0);
        legBackLeft = new ModelRenderer(this, 0, 16);
        legBackLeft.setRotationPoint(5, 12, 8);
        legBackLeft.addBox(-1, 0, -1, 2, 12, 2, 0);
        legFrontRight = new ModelRenderer(this, 0, 16);
        legFrontRight.setRotationPoint(-5, 12, -7);
        legFrontRight.addBox(-1, 0, -1, 2, 12, 2, 0);
        legFrontLeft = new ModelRenderer(this, 0, 16);
        legFrontLeft.setRotationPoint(5, 12, -7);
        legFrontLeft.addBox(-1, 0, -1, 2, 12, 2, 0);
    }
}
