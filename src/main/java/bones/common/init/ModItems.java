package bones.common.init;

import bones.Bones;
import bones.common.item.PorkchopOnAStickItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;

import static bones.Bones.MODID;

public class ModItems {

    public static final Item SKELETON_SHEEP_SPAWN_EGG = new SpawnEggItem(ModEntities.SKELETON_SHEEP, 0xffffff, 15198183, new Item.Properties().group(ItemGroup.MISC))
            .setRegistryName(new ResourceLocation(MODID, "skeleton_sheep_spawn_egg"));
    public static final Item SKELETON_PIG_SPAWN_EGG = new SpawnEggItem(ModEntities.SKELETON_PIG, 0xffffff, 15771042, new Item.Properties().group(ItemGroup.MISC))
            .setRegistryName(new ResourceLocation(MODID, "skeleton_pig_spawn_egg"));
    public static final Item SKELETON_COW_SPAWN_EGG = new SpawnEggItem(ModEntities.SKELETON_COW, 0xffffff, 4470310, new Item.Properties().group(ItemGroup.MISC))
            .setRegistryName(new ResourceLocation(MODID, "skeleton_cow_spawn_egg"));
    public static final Item SKELETON_CHICKEN_SPAWN_EGG = new SpawnEggItem(ModEntities.SKELETON_CHICKEN, 0xffffff, 16711680, new Item.Properties().group(ItemGroup.MISC))
            .setRegistryName(new ResourceLocation(MODID, "skeleton_chicken_spawn_egg"));

    public static final Item PORKCHOP_ON_A_STICK = new PorkchopOnAStickItem(new Item.Properties().group(ItemGroup.TRANSPORTATION).maxDamage(25))
            .setRegistryName(new ResourceLocation(Bones.MODID, "porkchop_on_a_stick"));
    
    public static void register(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                PORKCHOP_ON_A_STICK,
                SKELETON_SHEEP_SPAWN_EGG,
                SKELETON_PIG_SPAWN_EGG,
                SKELETON_COW_SPAWN_EGG,
                SKELETON_CHICKEN_SPAWN_EGG
        );
    }
}
