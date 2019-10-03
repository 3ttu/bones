package bones.entity.sheep_skeleton;

import bones.Bones;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@OnlyIn(Dist.CLIENT)
public class SheepSkeletonRenderer extends MobRenderer<SheepSkeletonEntity, SheepSkeletonModel> {

    private static final ResourceLocation TEXTURES = new ResourceLocation(Bones.MODID, "textures/entity/sheep_skeleton/sheep_skeleton.png");

    public SheepSkeletonRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new SheepSkeletonModel(), 0.7F);
        addLayer(new SheepSkeletonFleshLayer(this));
    }

    protected ResourceLocation getEntityTexture(SheepSkeletonEntity entity) {
        return TEXTURES;
    }
}
