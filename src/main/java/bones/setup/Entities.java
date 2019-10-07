package bones.setup;

import bones.entity.skeleton_cow.SkeletonCowEntity;
import bones.entity.skeleton_pig.SkeletonPigEntity;
import bones.entity.skeleton_sheep.SkeletonSheepEntity;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

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

    public static void register(RegistryEvent.Register<EntityType<?>> event) {
        SKELETON_SHEEP.setRegistryName(new ResourceLocation(MODID, "skeleton_sheep"));
        SKELETON_PIG.setRegistryName(new ResourceLocation(MODID, "skeleton_pig"));
        SKELETON_COW.setRegistryName(new ResourceLocation(MODID, "skeleton_cow"));

        event.getRegistry().registerAll(
                SKELETON_SHEEP,
                SKELETON_PIG,
                SKELETON_COW
        );

        EntitySpawnPlacementRegistry.register(SKELETON_SHEEP, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, Entities::mobCondition);
        EntitySpawnPlacementRegistry.register(SKELETON_PIG, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, Entities::mobCondition);
        EntitySpawnPlacementRegistry.register(SKELETON_COW, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, Entities::mobCondition);
    }

    private static boolean mobCondition(EntityType<? extends MobEntity> entityType, IWorld world, SpawnReason spawnReason, BlockPos pos, Random random) {
        BlockPos blockpos = pos.down();
        return spawnReason == SpawnReason.SPAWNER || world.getWorld().getBlockState(blockpos).canEntitySpawn(world, blockpos, entityType);
    }

    public static void addSpawns() {
        Collection<Biome> netherBiomes = BiomeDictionary.getBiomes(BiomeDictionary.Type.NETHER);
        Collection<Biome> spookyBiomes = new HashSet<>(BiomeDictionary.getBiomes(BiomeDictionary.Type.SPOOKY));
        spookyBiomes.removeAll(netherBiomes);

        for (Biome biome : netherBiomes) {
            biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(SKELETON_SHEEP, 24, 3, 7));
            biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(SKELETON_PIG, 24, 2, 5));
            biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(SKELETON_COW, 24, 2, 8));
        }
        for (Biome biome : spookyBiomes) {
            biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(SKELETON_SHEEP, 1, 1, 3));
            biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(SKELETON_PIG, 1, 1, 3));
            biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(SKELETON_COW, 1, 1, 3));
        }
    }
}
