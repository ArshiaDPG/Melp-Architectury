package net.digitalpear.melp.fabric;

import net.digitalpear.melp.fabric.datagens.MelpBlockLootTableProvider;
import net.digitalpear.melp.fabric.datagens.MelpLanguageProvider;
import net.digitalpear.melp.fabric.datagens.MelpModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class MelpDataGeneration implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(MelpModelProvider::new);
        pack.addProvider(MelpBlockLootTableProvider::new);
        pack.addProvider(MelpLanguageProvider::new);
    }
}
