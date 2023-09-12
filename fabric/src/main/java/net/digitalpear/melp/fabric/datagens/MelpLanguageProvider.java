package net.digitalpear.melp.fabric.datagens;

import net.digitalpear.melp.init.MBlocks;
import net.digitalpear.melp.init.MItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class MelpLanguageProvider extends FabricLanguageProvider {
    public MelpLanguageProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(MItems.MELP_SEED.get(), "Melp Seed");
        translationBuilder.add(MBlocks.MELP_CROP.get(), "Melp Crop");
        translationBuilder.add(MBlocks.MELP.get(), "Melp");
        translationBuilder.add(MBlocks.MELP_PLANT.get(), "Melp");
    }
}
