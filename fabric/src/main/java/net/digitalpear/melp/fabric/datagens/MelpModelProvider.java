package net.digitalpear.melp.fabric.datagens;

import net.digitalpear.melp.common.blocks.MelpCropBlock;
import net.digitalpear.melp.common.blocks.MelpPlantBlock;
import net.digitalpear.melp.init.MBlocks;
import net.digitalpear.melp.init.MItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class MelpModelProvider extends FabricModelProvider {
    public MelpModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {

        blockStateModelGenerator.skipAutoItemBlock(MBlocks.MELP.get());
        createMelpPlantBlock(blockStateModelGenerator, MBlocks.MELP.get());
        blockStateModelGenerator.createSimpleFlatItemModel(MBlocks.MELP.get());

        createMelpPlantBlock(blockStateModelGenerator, MBlocks.MELP_PLANT.get());


        blockStateModelGenerator.createCrossBlock(MBlocks.MELP_CROP.get(), BlockModelGenerators.TintState.NOT_TINTED, MelpCropBlock.AGE, 0, 1, 2);
    }

    public final void createMelpPlantBlock(BlockModelGenerators blockStateModelGenerator, Block block) {
        blockStateModelGenerator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(MelpPlantBlock.FLOWERING).generate(aBoolean -> {

            String name = aBoolean ? "_flowering" : "";
            TextureMapping textureMapping = TextureMapping.singleSlot(TextureSlot.CROSS, TextureMapping.getBlockTexture(block, name));

            ResourceLocation resourceLocation = BlockModelGenerators.TintState.NOT_TINTED.getCross().createWithSuffix(block, name, textureMapping, blockStateModelGenerator.modelOutput);

            return Variant.variant().with(VariantProperties.MODEL, resourceLocation);
        })));
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
    }
}
