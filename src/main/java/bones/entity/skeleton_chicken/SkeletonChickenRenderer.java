package bones.entity.skeleton_chicken;

import bones.Bones;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class SkeletonChickenRenderer extends MobRenderer<SkeletonChickenEntity, SkeletonChickenModel> {

    private static final ResourceLocation CHICKEN_TEXTURES = new ResourceLocation(Bones.MODID, "textures/entity/skeleton_chicken.png");

    public SkeletonChickenRenderer(EntityRendererManager renderManager) {
        super(renderManager, new SkeletonChickenModel(), 0.3F);
    }

    @Override
    public ResourceLocation getEntityTexture(SkeletonChickenEntity entity) {
        return CHICKEN_TEXTURES;
    }

    @Override
    protected float handleRotationFloat(SkeletonChickenEntity livingBase, float partialTicks) {
        float f = MathHelper.lerp(partialTicks, livingBase.flap, livingBase.wingRotation);
        float f1 = MathHelper.lerp(partialTicks, livingBase.flapSpeed, livingBase.destinationPos);
        return (MathHelper.sin(f) + 1) * f1;
    }
}
