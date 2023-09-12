package net.digitalpear.melp;

import com.google.common.base.Suppliers;
import dev.architectury.event.events.common.LootEvent;
import dev.architectury.registry.registries.RegistrarManager;
import net.digitalpear.melp.init.MBlocks;
import net.digitalpear.melp.init.MItems;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.DisplayRenderer;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.*;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;

import java.util.function.Supplier;

public class Melp
{
	public static final String MOD_ID = "melp";
	public static final Supplier<RegistrarManager> MANAGER = Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));
	public static void init() {
		MBlocks.init();
		MItems.init();

		LootEvent.MODIFY_LOOT_TABLE.register((lootDataManager, id, context, builtin) -> {
			if (builtin && id == BuiltInLootTables.SNIFFER_DIGGING){
				context.addPool(LootPool.lootPool().add(LootItem.lootTableItem(MItems.MELP_SEED.get()).when(LootItemRandomChanceCondition.randomChance(0.3f))));
			}
        });
	}
}
