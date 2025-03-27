package fr.iglee42.createcasing;

import fr.iglee42.createcasing.ponder.CasingPonderPlugin;
import fr.iglee42.createcasing.registries.ModPartialModels;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class CreateCasingClient {

    public static void onCtorClient(IEventBus modEventBus) {
        //if (CreateCasing.isExtendedCogsLoaded())CreateExtendedCogwheelsPartials.init();

        modEventBus.addListener(CreateCasingClient::clientInit);
        modEventBus.addListener(CreateCasingClient::onRegisterAdditionalModels);

    }

    public static void clientInit(final FMLClientSetupEvent event) {
        ModPartialModels.init();

        //CasingPonderTags.register();
        //CasingPonderScenes.register();

        PonderIndex.addPlugin(new CasingPonderPlugin());
    }

    public static void onRegisterAdditionalModels(ModelEvent.RegisterAdditional event){
        ModPartialModels.ALL_ENCASED_MODELS.forEach(m->event.register(m.modelLocation()));
    }
}