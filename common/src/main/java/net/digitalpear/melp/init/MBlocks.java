package net.digitalpear.melp.init;

import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.digitalpear.melp.Melp;
import net.digitalpear.melp.common.blocks.MelpBlock;
import net.digitalpear.melp.common.blocks.MelpCropBlock;
import net.digitalpear.melp.common.blocks.MelpPlantBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Supplier;

public class MBlocks {

    public static final Registrar<Block> BLOCKS = Melp.MANAGER.get().get(Registries.BLOCK);

    public static RegistrySupplier<Block> register(String name, Block item){
        return BLOCKS.register(new ResourceLocation(Melp.MOD_ID, name), () -> item);
    }


    public static final RegistrySupplier<Block> MELP = register("melp", new MelpBlock(BlockBehaviour.Properties.copy(Blocks.KELP).mapColor(DyeColor.ORANGE)));
    public static final RegistrySupplier<Block> MELP_PLANT = register("melp_plant", new MelpPlantBlock(BlockBehaviour.Properties.copy(Blocks.KELP_PLANT).mapColor(DyeColor.ORANGE)));
    public static final RegistrySupplier<Block> MELP_CROP = register("melp_crop", new MelpCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).mapColor(blockState -> blockState.getValue(MelpCropBlock.AGE) == CropBlock.MAX_AGE ? MapColor.COLOR_ORANGE : MapColor.COLOR_GREEN)));


    @Environment(EnvType.CLIENT)
    private static void registerRenderType(RenderType type, RegistrySupplier<Block> supplier) {
        supplier.listen(block -> RenderTypeRegistry.register(type, block));
    }
    public static void init(){
        registerRenderType(RenderType.cutout(), MELP);
        registerRenderType(RenderType.cutout(), MELP_PLANT);
        registerRenderType(RenderType.cutout(), MELP_CROP);
    }
}
