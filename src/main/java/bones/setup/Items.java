package bones.setup;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;

import static bones.Bones.MODID;

public class Items {

    public static void register(RegistryEvent.Register<Item> event) {
        Item.Properties properties = new Item.Properties().group(ItemGroup.MISC);
        event.getRegistry().registerAll(
                new SpawnEggItem(Entities.SKELETON_SHEEP, 0xffffff, 0xffd9c7, properties)
                        .setRegistryName(new ResourceLocation(MODID, "skeleton_sheep_spawn_egg"))
        );
    }
}
