package bones.entity.skeleton_pig;

import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
public class SkeletonPigSaddleLayer extends LayerRenderer<SkeletonPigEntity, SkeletonPigModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/pig/pig_saddle.png");
    private final SkeletonPigModel pigModel = new SkeletonPigModel(0.5F);

    public SkeletonPigSaddleLayer(IEntityRenderer<SkeletonPigEntity, SkeletonPigModel> renderer) {
        super(renderer);
    }

    @Override
    public void render(SkeletonPigEntity entityIn, float f1, float f2, float f3, float f4, float f5, float f6, float f7) {
        if (entityIn.getSaddled()) {
            bindTexture(TEXTURE);
            getEntityModel().setModelAttributes(pigModel);
            pigModel.render(entityIn, f1, f2, f4, f5, f6, f7);
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
