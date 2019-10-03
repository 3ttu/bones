package bones.setup;

import bones.Bones;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;

public class SoundEvents {

    public static final SoundEvent SHEEP_SKELETON_AMBIENT = new SoundEvent(new ResourceLocation(Bones.MODID, "entity.sheep_skeleton.ambient"));
    public static final SoundEvent SHEEP_SKELETON_DEATH = new SoundEvent(new ResourceLocation(Bones.MODID, "entity.sheep_skeleton.death"));
    public static final SoundEvent SHEEP_SKELETON_HURT = new SoundEvent(new ResourceLocation(Bones.MODID, "entity.sheep_skeleton.hurt"));
    public static final SoundEvent SHEEP_SKELETON_SHEAR = new SoundEvent(new ResourceLocation(Bones.MODID, "entity.sheep_skeleton.shear"));

    public static void register(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll(
                SHEEP_SKELETON_AMBIENT,
                SHEEP_SKELETON_DEATH,
                SHEEP_SKELETON_HURT,
                SHEEP_SKELETON_SHEAR
        );
    }
}
