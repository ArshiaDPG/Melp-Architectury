package net.digitalpear.melp.common.blocks;

import net.digitalpear.melp.init.MBlocks;
import net.digitalpear.melp.init.MItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MelpCropBlock extends CropBlock {
    public static final int MAX_AGE = 2;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.box(5.0, 0.0, 5.0, 11.0, 6.0, 11.0), Block.box(5.0, 0.0, 5.0, 11.0, 10.0, 11.0)};
    private static final int BONEMEAL_INCREASE = 1;

    public MelpCropBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE_BY_AGE[this.getAge(blockState)];
    }

    protected IntegerProperty getAgeProperty() {
        return AGE;
    }

    public int getMaxAge() {
        return MAX_AGE;
    }

    protected ItemLike getBaseSeedId() {
        return MItems.MELP_SEED.get();
    }

    public BlockState getStateForAge(int i) {
        return i == 2 ? MBlocks.MELP.get().defaultBlockState() : super.getStateForAge(i);
    }

    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (randomSource.nextInt(3) != 0) {
            super.randomTick(blockState, serverLevel, blockPos, randomSource);
        }
    }

    protected int getBonemealAgeIncrease(Level level) {
        return BONEMEAL_INCREASE;
    }

}
