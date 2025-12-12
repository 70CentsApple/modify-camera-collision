package net.apple70cents.ModifyCameraCollision.mixin;

import me.shedaniel.autoconfig.AutoConfig;
import net.apple70cents.ModifyCameraCollision.ModifyCameraCollision;
import net.apple70cents.ModifyCameraCollision.config.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateBaseMixin {

    @Shadow
    public abstract Block getBlock();

    @Inject(method = "getVisualShape", at = @At(value = "HEAD"), cancellable = true)
    private void modify(BlockGetter world, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        ModConfig config;
        try {
            config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        } catch (Exception e) {
            ModifyCameraCollision.LOGGER.warn("[ModifyCameraCollision] config has not yet been registered");
            return;
        }
        // if the block should NOT look through returns VoxelShapes.fullCube()
        if (config.forceEnableCollisionList.contains(BuiltInRegistries.BLOCK.getKey(this.getBlock()).toString()))
            cir.setReturnValue(Shapes.block());

        // if the block should look through returns VoxelShapes.empty()
        if (config.disableCollisionForAllBlocks || config.forceDisableCollisionList.contains(BuiltInRegistries.BLOCK.getKey(this.getBlock()).toString()))
            cir.setReturnValue(Shapes.empty());
    }
}
