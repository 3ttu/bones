package bones.entity.skeleton_pig;

import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SkeletonPigModel extends QuadrupedModel<SkeletonPigEntity> {

    public SkeletonPigModel(float scale) {
        super(6, scale, false, 4.0F, 4.0F, 2.0F, 2.0F, 24);
        headModel.setTextureOffset(16, 16).addBox(-2, 0, -9, 4, 3, 1, scale);
        legBackRight = new ModelRenderer(this, 0, 16);
        legBackRight.setRotationPoint(-4, 18, 7);
        legBackRight.addBox(-1, 0, -1, 2, 6, 2, scale);
        legBackLeft = new ModelRenderer(this, 0, 16);
        legBackLeft.setRotationPoint(4, 18, 7);
        legBackLeft.addBox(-1, 0, -1, 2, 6, 2, scale);
        legFrontRight = new ModelRenderer(this, 0, 16);
        legFrontRight.setRotationPoint(-4, 18, -5);
        legFrontRight.addBox(-1, 0, -1, 2, 6, 2, scale);
        legFrontLeft = new ModelRenderer(this, 0, 16);
        legFrontLeft.setRotationPoint(4, 18, -5);
        legFrontLeft.addBox(-1, 0, -1, 2, 6, 2, scale);
    }
}
