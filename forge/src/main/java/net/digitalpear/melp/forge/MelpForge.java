package net.digitalpear.melp.forge;

import dev.architectury.platform.forge.EventBuses;
import net.digitalpear.melp.Melp;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Melp.MOD_ID)
public class MelpForge {
    public MelpForge() {
		// Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(Melp.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        Melp.init();
    }

}