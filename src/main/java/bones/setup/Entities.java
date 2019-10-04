package bones.setup;

import bones.entity.sheep_skeleton.SheepSkeletonEntity;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;

import java.util.Random;

import static bones.Bones.MODID;

public class Entities {

    public static final EntityType<SheepSkeletonEntity> SHEEP_SKELETON = EntityType.Builder.create(SheepSkeletonEntity::new, EntityClassification.CREATURE)
            .size(0.9F, 1.3F)
            .immuneToFire()
            .build("sheep_skeleton");

    public static void register(RegistryEvent.Register<EntityType<?>> event) {
        SHEEP_SKELETON.setRegistryName(new ResourceLocation(MODID, "sheep_skeleton"));

        event.getRegistry().registerAll(
                SHEEP_SKELETON
        );

        EntitySpawnPlacementRegistry.register(SHEEP_SKELETON, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, Entities::mobCondition);
    }

    private static boolean mobCondition(EntityType<? extends MobEntity> entityType, IWorld world, SpawnReason spawnReason, BlockPos pos, Random random) {
        BlockPos blockpos = pos.down();
        return spawnReason == SpawnReason.SPAWNER || world.getWorld().getBlockState(blockpos).canEntitySpawn(world, blockpos, entityType);
    }

    public static void addSpawns() {

    }
}
