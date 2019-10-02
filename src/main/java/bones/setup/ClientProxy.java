package bones.setup;

import bones.entity.sheep_skeleton.SheepSkeletonEntity;
import bones.entity.sheep_skeleton.SheepSkeletonRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy implements IProxy {

    @Override
    public void init() {
        RenderingRegistry.registerEntityRenderingHandler(SheepSkeletonEntity.class, SheepSkeletonRenderer::new);
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
