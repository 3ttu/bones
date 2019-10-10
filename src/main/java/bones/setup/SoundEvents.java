package bones.setup;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;

import static bones.Bones.MODID;

public class SoundEvents {

    public static final SoundEvent SKELETON_SHEEP_AMBIENT = new SoundEvent(new ResourceLocation(MODID, "entity.skeleton_sheep.ambient")).setRegistryName(MODID, "entity.skeleton_sheep.ambient");
    public static final SoundEvent SKELETON_SHEEP_DEATH = new SoundEvent(new ResourceLocation(MODID, "entity.skeleton_sheep.death")).setRegistryName(MODID, "entity.skeleton_sheep.death");
    public static final SoundEvent SKELETON_SHEEP_HURT = new SoundEvent(new ResourceLocation(MODID, "entity.skeleton_sheep.hurt")).setRegistryName(MODID, "entity.skeleton_sheep.hurt");
    public static final SoundEvent SKELETON_SHEEP_SHEAR = new SoundEvent(new ResourceLocation(MODID, "entity.skeleton_sheep.shear")).setRegistryName(MODID, "entity.skeleton_sheep.shear");

    public static final SoundEvent SKELETON_PIG_AMBIENT = new SoundEvent(new ResourceLocation(MODID, "entity.skeleton_pig.ambient")).setRegistryName(MODID, "entity.skeleton_pig.ambient");
    public static final SoundEvent SKELETON_PIG_DEATH = new SoundEvent(new ResourceLocation(MODID, "entity.skeleton_pig.death")).setRegistryName(MODID, "entity.skeleton_pig.death");
    public static final SoundEvent SKELETON_PIG_HURT = new SoundEvent(new ResourceLocation(MODID, "entity.skeleton_pig.hurt")).setRegistryName(MODID, "entity.skeleton_pig.hurt");

    public static final SoundEvent SKELETON_COW_AMBIENT = new SoundEvent(new ResourceLocation(MODID, "entity.skeleton_cow.ambient")).setRegistryName(MODID, "entity.skeleton_cow.ambient");
    public static final SoundEvent SKELETON_COW_DEATH = new SoundEvent(new ResourceLocation(MODID, "entity.skeleton_cow.death")).setRegistryName(MODID, "entity.skeleton_cow.death");
    public static final SoundEvent SKELETON_COW_HURT = new SoundEvent(new ResourceLocation(MODID, "entity.skeleton_cow.hurt")).setRegistryName(MODID, "entity.skeleton_cow.hurt");

    public static final SoundEvent SKELETON_CHICKEN_AMBIENT = new SoundEvent(new ResourceLocation(MODID, "entity.skeleton_chicken.ambient")).setRegistryName(MODID, "entity.skeleton_chicken.ambient");
    public static final SoundEvent SKELETON_CHICKEN_DEATH = new SoundEvent(new ResourceLocation(MODID, "entity.skeleton_chicken.death")).setRegistryName(MODID, "entity.skeleton_chicken.death");
    public static final SoundEvent SKELETON_CHICKEN_HURT = new SoundEvent(new ResourceLocation(MODID, "entity.skeleton_chicken.hurt")).setRegistryName(MODID, "entity.skeleton_chicken.hurt");

    public static void register(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll(
                SKELETON_SHEEP_AMBIENT,
                SKELETON_SHEEP_DEATH,
                SKELETON_SHEEP_HURT,
                SKELETON_SHEEP_SHEAR,
                SKELETON_PIG_AMBIENT,
                SKELETON_PIG_DEATH,
                SKELETON_PIG_HURT,
                SKELETON_COW_AMBIENT,
                SKELETON_COW_DEATH,
                SKELETON_COW_HURT,
                SKELETON_CHICKEN_AMBIENT,
                SKELETON_CHICKEN_DEATH,
                SKELETON_CHICKEN_HURT
        );
    }
}
