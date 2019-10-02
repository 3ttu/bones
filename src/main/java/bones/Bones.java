package bones;

import bones.entity.sheep_skeleton.SheepSkeletonEntity;
import bones.setup.ClientProxy;
import bones.setup.IProxy;
import bones.setup.ServerProxy;
import bones.setup.SoundEvents;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ObjectHolder;

import static bones.Bones.MODID;

@Mod(MODID)
@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Bones {

    public static final String MODID = "bones";

    @ObjectHolder(MODID + ":sheep_skeleton")
    public static EntityType<SheepSkeletonEntity> SHEEP_SKELETON_ENTITY_TYPE;

    public Bones() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

    public void setup(FMLCommonSetupEvent event) {
        proxy.init();
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().register(EntityType.Builder.create(SheepSkeletonEntity::new, EntityClassification.CREATURE).size(0.9F, 1.3F).immuneToFire().build("sheep_skeleton").setRegistryName(new ResourceLocation(MODID, "sheep_skeleton")));
    }

    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        SoundEvents.register(event);
    }
}
