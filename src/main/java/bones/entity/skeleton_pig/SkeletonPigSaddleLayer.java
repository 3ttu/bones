package bones.entity.skeleton_pig;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
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

    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int light, SkeletonPigEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.getSaddled()) {
            getEntityModel().copyModelAttributesTo(this.pigModel);
            pigModel.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);
            pigModel.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            IVertexBuilder ivertexbuilder = buffer.getBuffer(RenderType.getEntityCutoutNoCull(TEXTURE));
            pigModel.render(matrixStack, ivertexbuilder, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        }
    }
}
