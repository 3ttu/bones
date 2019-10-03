package bones.entity.sheep_skeleton;

import bones.Bones;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
public class SheepSkeletonFleshLayer extends LayerRenderer<SheepSkeletonEntity, SheepSkeletonModel> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Bones.MODID, "textures/entity/sheep_skeleton/sheep_skeleton_flesh.png");
    private final SheepSkeletonFleshModel sheepSkeletonFleshModel = new SheepSkeletonFleshModel();

    public SheepSkeletonFleshLayer(IEntityRenderer<SheepSkeletonEntity, SheepSkeletonModel> renderer) {
        super(renderer);
    }

    public void render(SheepSkeletonEntity entity, float f1, float f2, float f3, float f4, float f5, float f6, float f7) {
        if (!entity.isSheared() && !entity.isInvisible()) {
            bindTexture(TEXTURE);
            getEntityModel().setModelAttributes(sheepSkeletonFleshModel);
            sheepSkeletonFleshModel.setLivingAnimations(entity, f1, f2, f3);
            sheepSkeletonFleshModel.render(entity, f1, f2, f4, f5, f6, f7);
        }
    }

    public boolean shouldCombineTextures() {
        return true;
    }
}
