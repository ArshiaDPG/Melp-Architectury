package net.digitalpear.melp.fabric.datagens;

import net.digitalpear.melp.common.blocks.MelpCropBlock;
import net.digitalpear.melp.init.MBlocks;
import net.digitalpear.melp.init.MItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;

import java.util.function.BiConsumer;

public class MelpBlockLootTableProvider extends SimpleFabricLootTableProvider {
    public MelpBlockLootTableProvider(FabricDataOutput output) {
        super(output, LootContextParamSets.BLOCK);
    }

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> biConsumer) {
        biConsumer.accept(MBlocks.MELP.get().getLootTable(), LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(MItems.MELP.get()))));
        biConsumer.accept(MBlocks.MELP_PLANT.get().getLootTable(), LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(MItems.MELP.get()))));
        biConsumer.accept(MBlocks.MELP_CROP.get().getLootTable(), LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(MItems.MELP_SEED.get()))));
    }

    public ResourceLocation getName(Block block){
        return block.getLootTable();
    }
}
