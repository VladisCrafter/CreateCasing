package fr.iglee42.createcasing;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import fr.iglee42.createcasing.commands.CreateCasingCommand;
import fr.iglee42.createcasing.config.ModConfigs;
import fr.iglee42.createcasing.registries.*;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Mod(CreateCasing.MODID)
public class CreateCasing {

    public static final String MODID = "createcasing";

    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID)
            .defaultCreativeTab((ResourceKey<CreativeModeTab>) null);
    public static List<ItemLike> hidedItems = new ArrayList<>();

    static {
        REGISTRATE.setTooltipModifierFactory(item -> new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                .andThen(TooltipModifier.mapNull(KineticStats.create(item))));
    }
    public CreateCasing() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get()
                .getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;


        REGISTRATE.registerEventListeners(modEventBus);

        //if (isExtendedCogsLoaded())CreateExtendedCogwheelsCompat.REGISTRATE.registerEventListeners(FMLJavaModLoadingContext.get().getModEventBus());

        //if (isExtendedCogsLoaded()) ExtendedCogwheels.registrate().addRegisterCallback(Registry.BLOCK_REGISTRY, CreateExtendedCogwheelsCompat::register);
        
        ModSounds.prepare();
        ModBlocks.register();
        //ModItems.register();
        ModBlockEntities.register();
        ModCreativeModeTabs.register(modEventBus);
        ModPackets.registerPackets();

        ModBlocks.registerEncasedShafts();

        ModConfigs.register(ModLoadingContext.get());



        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> CreateCasingClient.onCtorClient(modEventBus));

        forgeEventBus.addListener(this::registerCommands);
        modEventBus.addListener(this::setup);
        modEventBus.addListener(ModSounds::register);


        MinecraftForge.EVENT_BUS.register(this);
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MODID, path);
    }


    public static void hideItem(ItemLike it){
        hidedItems.add(it);
    }

    private void setup(final FMLCommonSetupEvent event) {
    }

    private void registerCommands(RegisterCommandsEvent event){
        if (!FMLEnvironment.production) new CreateCasingCommand(event.getDispatcher());
    }


}
