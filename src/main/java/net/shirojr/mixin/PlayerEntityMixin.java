package net.shirojr.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.shirojr.screen.custom.BasicPocketScreenHandler;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.OptionalInt;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow
    public abstract boolean isCreative();

    @Shadow
    public abstract boolean isSpectator();

    @Shadow
    public abstract OptionalInt openHandledScreen(@Nullable NamedScreenHandlerFactory factory);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    public void interact(Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (isCreative() || isSpectator()) return;
        if (entity instanceof PlayerEntity target) {
            this.openHandledScreen(new SimpleNamedScreenHandlerFactory(
                    (syncId, inv, player) -> new BasicPocketScreenHandler(syncId, inv, target.getInventory(),
                            target, ScreenHandlerContext.create(this.getWorld(), target.getBlockPos())),
                    target.getName())
            );

            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }
}
