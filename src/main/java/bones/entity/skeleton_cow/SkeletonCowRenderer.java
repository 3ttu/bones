package bones.entity.skeleton_cow;

import bones.Bones;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
public class SkeletonCowRenderer extends MobRenderer<SkeletonCowEntity, SkeletonCowModel> {
    private static final ResourceLocation COW_TEXTURES = new ResourceLocation(Bones.MODID, "textures/entity/skeleton_cow.png");

    public SkeletonCowRenderer(EntityRendererManager renderer) {
        super(renderer, new SkeletonCowModel(), 0.7F);
    }

    @Override
    protected ResourceLocation getEntityTexture(SkeletonCowEntity entity) {
        return COW_TEXTURES;
    }
}
