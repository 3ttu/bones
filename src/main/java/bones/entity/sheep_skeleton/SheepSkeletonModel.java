package bones.entity.sheep_skeleton;

import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
public class SheepSkeletonModel extends QuadrupedModel<SheepSkeletonEntity> {

    public SheepSkeletonModel() {
        super(12, 0);

        headModel = new RendererModel(this, 0, 0);
        headModel.setRotationPoint(0, 6, -6);
        headModel.addBox(-3, -4, -6, 6, 6, 7, 0);

        body = new RendererModel(this, 26, 0);
        body.setRotationPoint(0, 5, 2);
        body.addBox(-4, -10, -7, 8, 16, 6, 0);
        body.rotateAngleX = (float) Math.PI / 2;

        legBackRight = new RendererModel(this, 0, 13);
        legBackRight.setRotationPoint(-3, 12, 7);
        legBackRight.addBox(-1, 0, -1, 2, 12, 2, 0);
        legBackLeft = new RendererModel(this, 0, 13);
        legBackLeft.setRotationPoint(3, 12, 7);
        legBackLeft.addBox(-1, 0, -1, 2, 12, 2, 0);
        legFrontRight = new RendererModel(this, 0, 13);
        legFrontRight.setRotationPoint(-3, 12, -5);
        legFrontRight.addBox(-1, 0, -1, 2, 12, 2, 0);
        legFrontLeft = new RendererModel(this, 0, 13);
        legFrontLeft.setRotationPoint(3, 12, -5);
        legFrontLeft.addBox(-1, 0, -1, 2, 12, 2, 0);
    }
}
