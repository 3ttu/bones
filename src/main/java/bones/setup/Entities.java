package bones.setup;

import bones.config.EntityConfig;
import bones.config.ModConfig;
import bones.entity.skeleton_chicken.SkeletonChickenEntity;
import bones.entity.skeleton_chicken.SkeletonChickenRenderer;
import bones.entity.skeleton_cow.SkeletonCowEntity;
import bones.entity.skeleton_cow.SkeletonCowRenderer;
import bones.entity.skeleton_pig.SkeletonPigEntity;
import bones.entity.skeleton_pig.SkeletonPigRenderer;
import bones.entity.skeleton_sheep.SkeletonSheepEntity;
import bones.entity.skeleton_sheep.SkeletonSheepRenderer;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.registries.ForgeRegistries;

import static bones.Bones.MODID;

public class Entities {

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

    public static void addSpawns() {
        for (Biome biome : ForgeRegistries.BIOMES) {
            addSpawn(ModConfig.COMMON.skeleton_sheep, SKELETON_SHEEP, biome);
            addSpawn(ModConfig.COMMON.skeleton_pig, SKELETON_PIG, biome);
            addSpawn(ModConfig.COMMON.skeleton_cow, SKELETON_COW, biome);
            addSpawn(ModConfig.COMMON.skeleton_chicken, SKELETON_CHICKEN, biome);
        }
    }

    private static void addSpawn(EntityConfig config, EntityType<?> type, Biome biome) {
        if (config.canSpawnIn(biome)) {
            biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(type, config.weight.get(), config.minRolls.get(), config.maxRolls.get()));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerRenderingHandlers() {
        RenderingRegistry.registerEntityRenderingHandler(SKELETON_SHEEP, SkeletonSheepRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SKELETON_PIG, SkeletonPigRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SKELETON_COW, SkeletonCowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SKELETON_CHICKEN, SkeletonChickenRenderer::new);
    }
}
