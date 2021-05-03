package bones.client.render.layer;

import bones.Bones;
import bones.client.render.model.SkeletonSheepFleshModel;
import bones.client.render.model.SkeletonSheepModel;
import bones.common.entity.SkeletonSheepEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SkeletonSheepFleshLayer extends LayerRenderer<SkeletonSheepEntity, SkeletonSheepModel> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Bones.MODID, "textures/entity/skeleton_sheep/skeleton_sheep_flesh.png");
    private final SkeletonSheepFleshModel skeletonSheepFleshModel = new SkeletonSheepFleshModel();

    public SkeletonSheepFleshLayer(IEntityRenderer<SkeletonSheepEntity, SkeletonSheepModel> renderer) {
        super(renderer);
    }

    @Override

    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLightIn, SkeletonSheepEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entity.isSheared() && !entity.isInvisible()) {
            renderCopyCutoutModel(this.getEntityModel(), this.skeletonSheepFleshModel, TEXTURE, matrixStack, buffer, packedLightIn, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, 1, 1, 1);
        }
    }
}
