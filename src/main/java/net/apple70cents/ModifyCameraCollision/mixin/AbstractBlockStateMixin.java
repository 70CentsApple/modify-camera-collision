package net.apple70cents.ModifyCameraCollision.mixin;

import me.shedaniel.autoconfig.AutoConfig;
import net.apple70cents.ModifyCameraCollision.ModifyCameraCollision;
import net.apple70cents.ModifyCameraCollision.config.ModConfig;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ShapeContext;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin {

    @Shadow
    public abstract Block getBlock();

    @Inject(method = "getCameraCollisionShape", at = @At(value = "HEAD"), cancellable = true)
    private void modify(BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        ModConfig config;
        try {
            config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        } catch (Exception e) {
            ModifyCameraCollision.LOGGER.warn("[ModifyCameraCollision] config has not yet been registered");
            return;
        }
        // if the block should NOT look through returns VoxelShapes.fullCube()
        if (config.forceEnableCollisionList.contains(Registries.BLOCK.getId(this.getBlock()).toString()))
            cir.setReturnValue(VoxelShapes.fullCube());

        // if the block should look through returns VoxelShapes.empty()
        if (config.disableCollisionForAllBlocks || config.forceDisableCollisionList.contains(Registries.BLOCK.getId(this.getBlock()).toString()))
            cir.setReturnValue(VoxelShapes.empty());
    }
}
