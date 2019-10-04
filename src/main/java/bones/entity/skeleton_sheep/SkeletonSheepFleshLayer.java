package bones.entity.skeleton_sheep;

import bones.Bones;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
public class SkeletonSheepFleshLayer extends LayerRenderer<SkeletonSheepEntity, SkeletonSheepModel> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Bones.MODID, "textures/entity/skeleton_sheep/skeleton_sheep_flesh.png");
    private final SkeletonSheepFleshModel skeletonSheepFleshModel = new SkeletonSheepFleshModel();

    public SkeletonSheepFleshLayer(IEntityRenderer<SkeletonSheepEntity, SkeletonSheepModel> renderer) {
        super(renderer);
    }

    public void render(SkeletonSheepEntity entity, float f1, float f2, float f3, float f4, float f5, float f6, float f7) {
        if (!entity.isSheared() && !entity.isInvisible()) {
            bindTexture(TEXTURE);
            getEntityModel().setModelAttributes(skeletonSheepFleshModel);
            skeletonSheepFleshModel.setLivingAnimations(entity, f1, f2, f3);
            skeletonSheepFleshModel.render(entity, f1, f2, f4, f5, f6, f7);
        }
    }

    public boolean shouldCombineTextures() {
        return true;
    }
}
