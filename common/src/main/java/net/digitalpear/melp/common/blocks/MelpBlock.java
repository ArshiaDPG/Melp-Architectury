package net.digitalpear.melp.common.blocks;

import net.digitalpear.melp.init.MBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.HashMap;
import java.util.Map;

public class MelpBlock extends GrowingPlantHeadBlock {
    public static final BooleanProperty FLOWERING = MelpPlantBlock.FLOWERING;

    public static Map<TagKey<Item>, Item> itemConversion = new HashMap<>();
    public static final VoxelShape SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 14.0, 13.0);
    private static final double GROW_PER_TICK_PROBABILITY = 0.14;
    public MelpBlock(Properties properties) {
        super(properties, Direction.UP, SHAPE, true, GROW_PER_TICK_PROBABILITY);
        itemConversion.put(ItemTags.PLANKS, Items.ACACIA_PLANKS);
        itemConversion.put(ItemTags.WOODEN_BUTTONS, Items.ACACIA_BUTTON);
        itemConversion.put(ItemTags.WOODEN_DOORS, Items.ACACIA_DOOR);
        itemConversion.put(ItemTags.WOODEN_FENCES, Items.ACACIA_FENCE);
        itemConversion.put(ItemTags.WOODEN_SLABS, Items.ACACIA_SLAB);
        itemConversion.put(ItemTags.WOODEN_PRESSURE_PLATES, Items.ACACIA_PRESSURE_PLATE);
        itemConversion.put(ItemTags.WOODEN_STAIRS, Items.ACACIA_STAIRS);
        itemConversion.put(ItemTags.FENCE_GATES, Items.ACACIA_FENCE_GATE);
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource randomSource) {
        return 1;
    }


    public boolean canAttachTo(BlockState blockState) {
        return blockState.is(BlockTags.DIRT) || blockState.is(Blocks.FARMLAND) || blockState.is(getBodyBlock());
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ItemStack stack = player.getItemInHand(interactionHand);

        for (Map.Entry<TagKey<Item>, Item> entry : itemConversion.entrySet()) {

            TagKey<Item> itemTagKey = entry.getKey();
            Item item = entry.getValue();

            if (stack.is(itemTagKey)) {
                stack.setCount(stack.getCount() - 1);
                player.swing(interactionHand);
                ItemEntity itemEntity = EntityType.ITEM.create(level);
                itemEntity.setItem(new ItemStack(item));


                level.playSound(player, blockPos, SoundEvents.CAT_EAT, SoundSource.BLOCKS, 1.0F, 1.0F);
                itemEntity.setPos(blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f);
                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockPos, stack);
                }
                if (itemEntity != null) {
                    itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add((level.random.nextFloat() - level.random.nextFloat()) * 0.1F, level.random.nextFloat() * 0.05F, (level.random.nextFloat() - level.random.nextFloat()) * 0.1F));
                }
                level.addFreshEntity(itemEntity);

                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

    @Override
    protected BlockState getGrowIntoState(BlockState blockState, RandomSource randomSource) {
        return super.getGrowIntoState(blockState, randomSource).setValue(MelpPlantBlock.FLOWERING, blockState.getValue(AGE) == MAX_AGE);
    }

    @Override
    protected boolean canGrowInto(BlockState blockState) {
        return blockState.isAir();
    }

    @Override
    protected Block getBodyBlock() {
        return MBlocks.MELP_PLANT.get();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FLOWERING);
        super.createBlockStateDefinition(builder);
    }
}
