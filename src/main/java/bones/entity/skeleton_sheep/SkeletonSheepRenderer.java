package bones.entity.skeleton_sheep;

import bones.Bones;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@OnlyIn(Dist.CLIENT)
public class SkeletonSheepRenderer extends MobRenderer<SkeletonSheepEntity, SkeletonSheepModel> {

    private static final ResourceLocation TEXTURES = new ResourceLocation(Bones.MODID, "textures/entity/skeleton_sheep/skeleton_sheep.png");

    public SkeletonSheepRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new SkeletonSheepModel(), 0.7F);
        addLayer(new SkeletonSheepFleshLayer(this));
    }

    protected ResourceLocation getEntityTexture(SkeletonSheepEntity entity) {
        return TEXTURES;
    }
}
