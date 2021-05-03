package bones.client.render;

import bones.Bones;
import bones.client.render.model.SkeletonPigModel;
import bones.common.entity.SkeletonPigEntity;
import bones.client.render.layer.SkeletonPigSaddleLayer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SkeletonPigRenderer extends MobRenderer<SkeletonPigEntity, SkeletonPigModel> {
    private static final ResourceLocation PIG_TEXTURES = new ResourceLocation(Bones.MODID, "textures/entity/skeleton_pig.png");

    public SkeletonPigRenderer(EntityRendererManager renderManager) {
        super(renderManager, new SkeletonPigModel(0), 0.7F);
        this.addLayer(new SkeletonPigSaddleLayer(this));
    }

    @Override
    public ResourceLocation getEntityTexture(SkeletonPigEntity entity) {
        return PIG_TEXTURES;
    }
}
