package bones.common.config;

import bones.Bones;
import com.google.common.collect.Lists;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class EntityConfig {

    private Set<Biome.Category> worldGenBiomeCategories;
    private Set<ResourceLocation> worldGenBiomeBlacklist;
    private Set<String> worldGenModIdBlacklist;

    private final ForgeConfigSpec.ConfigValue<List<String>> biomeCategories;
    private final ForgeConfigSpec.ConfigValue<List<String>> biomeBlacklist;

    public final ForgeConfigSpec.IntValue weight;
    public final ForgeConfigSpec.IntValue minRolls;
    public final ForgeConfigSpec.IntValue maxRolls;

    EntityConfig(ForgeConfigSpec.Builder builder, String name) {
        builder.push(name);
        biomeCategories = builder
                .worldRestart()
                .comment(
                        "List of biome categories in which this entity is allowed to spawn",
                        "To allow spawning in all biomes except those blacklisted, use \"*\"",
                        "All biomes have one of the following categories: none, taiga, extreme_hills, jungle, mesa, plains, savanna, icy, the_end, beach, forest, ocean, desert, river, swamp, mushroom, nether"
                )
                .translation(Bones.MODID + ".config.common.spawning.biome_categories")
                .define("biome_categories", Lists.newArrayList("nether"));
        biomeBlacklist = builder
                .worldRestart()
                .comment(
                        "List of biome IDs in which this entity not allowed to spawn",
                        "To blacklist all biomes from a single mod, use \"modid:*\""
                )
                .translation(Bones.MODID + ".config.common.spawning.biome_blacklist")
                .define("biome_blacklist", new ArrayList<>()); // list config options break with immutable lists
        weight = builder
                .worldRestart()
                .comment(
                        "The likelihood that this entity spawns (compared to other entities that can spawn in the same biome)",
                        "Set to 0 to prevent this entity from spawning"
                )
                .translation(Bones.MODID + ".config.common.spawning.weight")
                .defineInRange("weight", 50, 0, Integer.MAX_VALUE);
        minRolls = builder
                .worldRestart()
                .comment(
                        "The minimum amount of entities to spawn when this entity spawns",
                        "(The actual amount of spawned entities in a group can be lower, not every attempt at spawning an entity succeeds)"
                )
                .translation(Bones.MODID + ".config.common.spawning.min_rolls")
                .defineInRange("min_rolls", 2, 0, Integer.MAX_VALUE);
        maxRolls = builder
                .worldRestart()
                .comment("The maximum amount of entities to spawn when this entity spawns")
                .translation(Bones.MODID + ".config.common.spawning.max_rolls")
                .defineInRange("max_rolls", 4, 0, 12);
        builder.pop();
    }

    void bake() {
        if (biomeCategories.get().contains("*")) {
            worldGenBiomeCategories = new HashSet<>(Arrays.asList(Biome.Category.values()));
        } else {
            worldGenBiomeCategories = biomeCategories.get().stream().map(Biome.Category::byName).collect(Collectors.toSet());
        }
        worldGenBiomeBlacklist = biomeBlacklist.get()
                .stream()
                .filter(string -> !string.endsWith("*"))
                .map(ResourceLocation::new)
                .collect(Collectors.toSet());
        worldGenModIdBlacklist = biomeBlacklist.get()
                .stream()
                .filter(string -> string.endsWith(":*"))
                .map(string -> string.substring(0, string.length() - 2))
                .collect(Collectors.toSet());
    }

    public boolean canSpawnIn(@Nullable ResourceLocation biome, Biome.Category category) {
        return weight.get() > 0 && biome != null && worldGenBiomeCategories.contains(category) && !worldGenBiomeBlacklist.contains(biome) && !worldGenModIdBlacklist.contains(biome.getNamespace());
    }
}
