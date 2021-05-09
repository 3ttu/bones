package bones.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {

    public final EntityConfig skeleton_sheep;
    public final EntityConfig skeleton_pig;
    public final EntityConfig skeleton_cow;
    public final EntityConfig skeleton_chicken;

    CommonConfig(ForgeConfigSpec.Builder builder) {
        builder.push("spawning");
        skeleton_sheep = new EntityConfig(builder, "skeleton_sheep");
        skeleton_pig = new EntityConfig(builder, "skeleton_pig");
        skeleton_cow = new EntityConfig(builder, "skeleton_cow");
        skeleton_chicken = new EntityConfig(builder, "skeleton_chicken");
        builder.pop();
    }

    void bake() {
        skeleton_sheep.bake();
        skeleton_pig.bake();
        skeleton_cow.bake();
        skeleton_chicken.bake();
    }
}
