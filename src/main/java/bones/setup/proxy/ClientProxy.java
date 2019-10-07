package bones.setup.proxy;

import bones.entity.skeleton_cow.SkeletonCowEntity;
import bones.entity.skeleton_cow.SkeletonCowRenderer;
import bones.entity.skeleton_pig.SkeletonPigEntity;
import bones.entity.skeleton_pig.SkeletonPigRenderer;
import bones.entity.skeleton_sheep.SkeletonSheepEntity;
import bones.entity.skeleton_sheep.SkeletonSheepRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy implements IProxy {

    @Override
    public void init() {
        RenderingRegistry.registerEntityRenderingHandler(SkeletonSheepEntity.class, SkeletonSheepRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SkeletonPigEntity.class, SkeletonPigRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SkeletonCowEntity.class, SkeletonCowRenderer::new);
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getInstance().world;
    }

    @Override
    public PlayerEntity getClientPlayer() {
        return Minecraft.getInstance().player;
    }
}
