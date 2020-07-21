package bones;

import bones.setup.Entities;
import bones.setup.Items;
import bones.setup.SoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import static bones.Bones.MODID;

@Mod(MODID)
@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Bones {

    public static final String MODID = "bones";

    @SubscribeEvent
    public static void setupCommon(FMLCommonSetupEvent event) {
        DeferredWorkQueue.runLater(Entities::addSpawns);
    }

    @SubscribeEvent
    public static void setupClient(FMLClientSetupEvent event) {
        Entities.registerRenderingHandlers();
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        Entities.register(event);
    }

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        SoundEvents.register(event);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        Items.register(event);
    }
}
