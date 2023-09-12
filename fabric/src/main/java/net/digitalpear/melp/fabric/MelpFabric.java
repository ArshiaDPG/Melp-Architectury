package net.digitalpear.melp.fabric;

import net.digitalpear.melp.Melp;
import net.fabricmc.api.ModInitializer;

public class MelpFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Melp.init();
    }
}