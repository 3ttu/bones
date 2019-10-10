package bones.setup;

import bones.Bones;
import bones.item.PorkchopOnAStickItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;

import static bones.Bones.MODID;

public class Items {

    public static final Item PORKCHOP_ON_A_STICK = new PorkchopOnAStickItem(new Item.Properties().group(ItemGroup.TRANSPORTATION).maxDamage(25))
            .setRegistryName(new ResourceLocation(Bones.MODID, "porkchop_on_a_stick"));

    public static void register(RegistryEvent.Register<Item> event) {
        Item.Properties eggProperties = new Item.Properties().group(ItemGroup.MISC);
        event.getRegistry().registerAll(
                PORKCHOP_ON_A_STICK,
                new SpawnEggItem(Entities.SKELETON_SHEEP, 0xffffff, 15198183, eggProperties)
                        .setRegistryName(new ResourceLocation(MODID, "skeleton_sheep_spawn_egg")),
                new SpawnEggItem(Entities.SKELETON_PIG, 0xffffff, 15771042, eggProperties)
                        .setRegistryName(new ResourceLocation(MODID, "skeleton_pig_spawn_egg")),
                new SpawnEggItem(Entities.SKELETON_COW, 0xffffff, 4470310, eggProperties)
                        .setRegistryName(new ResourceLocation(MODID, "skeleton_cow_spawn_egg")),
                new SpawnEggItem(Entities.SKELETON_CHICKEN, 0xffffff, 16711680, eggProperties)
                        .setRegistryName(new ResourceLocation(MODID, "skeleton_chicken_spawn_egg"))
        );
    }
}
