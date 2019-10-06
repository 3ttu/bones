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

    public static void register(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll(
                SKELETON_SHEEP_AMBIENT,
                SKELETON_SHEEP_DEATH,
                SKELETON_SHEEP_HURT,
                SKELETON_SHEEP_SHEAR
        );
    }
}
