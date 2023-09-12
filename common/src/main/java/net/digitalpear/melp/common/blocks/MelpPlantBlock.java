package net.digitalpear.melp.common.blocks;

import net.digitalpear.melp.init.MBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class MelpPlantBlock extends GrowingPlantBodyBlock {

    public static final BooleanProperty FLOWERING = BooleanProperty.create("flowering");
    public static final VoxelShape SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);

    public MelpPlantBlock(Properties properties) {
        super(properties, Direction.UP, SHAPE, true);
        registerDefaultState(this.defaultBlockState().setValue(FLOWERING, false));
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) MBlocks.MELP.get();
    }

    protected boolean canAttachTo(BlockState blockState) {
        return ((MelpBlock)this.getHeadBlock()).canAttachTo(blockState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FLOWERING);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ItemStack stack = player.getItemInHand(interactionHand);
        if (stack.getItem() instanceof ShearsItem && blockState.getValue(FLOWERING)){
            level.setBlock(blockPos, blockState.setValue(FLOWERING, false), 3);
            player.swing(interactionHand);
            stack.hurtAndBreak(1, player, (playerx) -> {
                playerx.broadcastBreakEvent(interactionHand);
            });
            ItemEntity itemEntity = EntityType.ITEM.create(level);
            itemEntity.setItem(Items.PINK_TULIP.getDefaultInstance());
            level.playSound(player, blockPos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 0.5F);
            itemEntity.setPos(blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f);
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockPos, stack);
            }
            if (itemEntity != null) {
                itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add((level.random.nextFloat() - level.random.nextFloat()) * 0.1F, level.random.nextFloat() * 0.05F, (level.random.nextFloat() - level.random.nextFloat()) * 0.1F));
            }
            level.addFreshEntity(itemEntity);
        }
        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (randomSource.nextInt(25) == 0 && !blockState.getValue(FLOWERING)){
            serverLevel.setBlock(blockPos, blockState.setValue(FLOWERING, true), 3);
        }
        super.randomTick(blockState, serverLevel, blockPos, randomSource);
    }

    @Override
    public boolean isRandomlyTicking(BlockState blockState) {
        return !blockState.getValue(FLOWERING);
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        if (!blockState.getValue(FLOWERING)){
            serverLevel.setBlock(blockPos, blockState.setValue(FLOWERING, true), 3);
        }
        else{
            super.performBonemeal(serverLevel, randomSource, blockPos, blockState);
        }
    }
}
