package bones.entity.skeleton_sheep;

import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SkeletonSheepFleshModel extends QuadrupedModel<SkeletonSheepEntity> {

    public SkeletonSheepFleshModel() {
        super(12, 0);

        headModel = new RendererModel(this, 0, 0);
        headModel.setRotationPoint(0, 6, -6);
        headModel.addBox(-3, -4, -4, 6, 6, 5, 0.6F);

        body = new RendererModel(this, 26, 0);
        body.setRotationPoint(0, 5, 2);
        body.addBox(-4, -10, -7, 8, 16, 6, 1);
        body.rotateAngleX = (float) Math.PI / 2;

        legBackRight = new RendererModel(this, 0, 13);
        legBackRight.setRotationPoint(-3, 12, 7);
        legBackRight.addBox(-1, 0, -1, 2, 6, 2, 0.5F);
        legBackLeft = new RendererModel(this, 0, 13);
        legBackLeft.setRotationPoint(3, 12, 7);
        legBackLeft.addBox(-1, 0, -1, 2, 6, 2, 0.5F);
        legFrontRight = new RendererModel(this, 0, 13);
        legFrontRight.setRotationPoint(-3, 12, -5);
        legFrontRight.addBox(-1, 0, -1, 2, 6, 2, 0.5F);
        legFrontLeft = new RendererModel(this, 0, 13);
        legFrontLeft.setRotationPoint(3, 12, -5);
        legFrontLeft.addBox(-1, 0, -1, 2, 6, 2, 0.5F);
    }
}
