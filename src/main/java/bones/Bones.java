package bones;

import bones.common.init.ModEntities;
import bones.common.init.ModItems;
import bones.common.init.ModSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static bones.Bones.MODID;

@Mod(MODID)
@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Bones {

    public static final String MODID = "bones";

    public Bones() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addGenericListener(EntityType.class, ModEntities::register);
        modEventBus.addGenericListener(Item.class, ModItems::register);
        modEventBus.addGenericListener(SoundEvent.class, ModSoundEvents::register);
        modEventBus.addListener(ModEntities::addEntityAttributes);
        modEventBus.addListener(ModEntities::registerRenderingHandlers);
        MinecraftForge.EVENT_BUS.addListener(ModEntities::addSpawns);
    }
}
