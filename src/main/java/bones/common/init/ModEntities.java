package bones.common.init;

import bones.common.entity.SkeletonChickenEntity;
import bones.client.render.SkeletonChickenRenderer;
import bones.common.entity.SkeletonCowEntity;
import bones.client.render.SkeletonCowRenderer;
import bones.common.entity.SkeletonPigEntity;
import bones.client.render.SkeletonPigRenderer;
import bones.common.entity.SkeletonSheepEntity;
import bones.client.render.SkeletonSheepRenderer;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static bones.Bones.MODID;

public class ModEntities {

    public static final EntityType<SkeletonSheepEntity> SKELETON_SHEEP = EntityType.Builder.create(SkeletonSheepEntity::new, EntityClassification.CREATURE)
            .size(0.9F, 1.3F)
            .immuneToFire()
            .build("skeleton_sheep");
    public static final EntityType<SkeletonPigEntity> SKELETON_PIG = EntityType.Builder.create(SkeletonPigEntity::new, EntityClassification.CREATURE)
            .size(0.9F, 0.9F)
            .immuneToFire()
            .build("skeleton_pig");
    public static final EntityType<SkeletonCowEntity> SKELETON_COW = EntityType.Builder.create(SkeletonCowEntity::new, EntityClassification.CREATURE)
            .size(0.9F, 1.4F)
            .immuneToFire()
            .build("skeleton_cow");
    public static final EntityType<SkeletonChickenEntity> SKELETON_CHICKEN = EntityType.Builder.create(SkeletonChickenEntity::new, EntityClassification.CREATURE)
            .size(0.4F, 0.7F)
            .immuneToFire()
            .build("skeleton_chicken");

    public static void register(RegistryEvent.Register<EntityType<?>> event) {
        SKELETON_SHEEP.setRegistryName(new ResourceLocation(MODID, "skeleton_sheep"));
        SKELETON_PIG.setRegistryName(new ResourceLocation(MODID, "skeleton_pig"));
        SKELETON_COW.setRegistryName(new ResourceLocation(MODID, "skeleton_cow"));
        SKELETON_CHICKEN.setRegistryName(new ResourceLocation(MODID, "skeleton_chicken"));

        event.getRegistry().registerAll(
                SKELETON_SHEEP,
                SKELETON_PIG,
                SKELETON_COW,
                SKELETON_CHICKEN
        );
        EntitySpawnPlacementRegistry.register(SKELETON_SHEEP, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, (animal, world, reason, pos, random) -> true);
        EntitySpawnPlacementRegistry.register(SKELETON_PIG, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, (animal, world, reason, pos, random) -> true);
        EntitySpawnPlacementRegistry.register(SKELETON_COW, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, (animal, world, reason, pos, random) -> true);
        EntitySpawnPlacementRegistry.register(SKELETON_CHICKEN, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, (animal, world, reason, pos, random) -> true);
    }

    public static void addSpawns(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.Category.NETHER) {
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(SKELETON_SHEEP, 50, 2, 6));
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(SKELETON_PIG, 50, 2, 6));
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(SKELETON_COW, 50, 2, 6));
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(SKELETON_CHICKEN, 50, 2, 6));
        }
    }

    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(SKELETON_SHEEP, SkeletonSheepEntity.createAttributes().create());
        event.put(SKELETON_PIG, SkeletonPigEntity.createAttributes().create());
        event.put(SKELETON_COW, SkeletonCowEntity.createAttributes().create());
        event.put(SKELETON_CHICKEN, SkeletonChickenEntity.createAttributes().create());
    }

    @SuppressWarnings("unused")
    public static void registerRenderingHandlers(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(SKELETON_SHEEP, SkeletonSheepRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SKELETON_PIG, SkeletonPigRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SKELETON_COW, SkeletonCowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SKELETON_CHICKEN, SkeletonChickenRenderer::new);
    }
}
