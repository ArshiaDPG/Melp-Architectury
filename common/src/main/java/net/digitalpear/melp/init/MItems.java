package net.digitalpear.melp.init;

import dev.architectury.event.events.common.BlockEvent;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.digitalpear.melp.Melp;
import net.minecraft.client.particle.SuspendedTownParticle;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.awt.event.ItemEvent;

public class MItems {

    public static final Registrar<Item> ITEMS = Melp.MANAGER.get().get(Registries.ITEM);

    public static RegistrySupplier<Item> register(String name, Item item){
        return ITEMS.register(new ResourceLocation(Melp.MOD_ID, name), () -> item);
    }
    public static final RegistrySupplier<Item> MELP = register("melp", new BlockItem(MBlocks.MELP.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> MELP_SEED = register("melp_seed", new ItemNameBlockItem(MBlocks.MELP_CROP.get(), new Item.Properties()));


    public static void init(){
        CreativeTabRegistry.append(CreativeModeTabs.NATURAL_BLOCKS, MELP, MELP_SEED);
    }

}
