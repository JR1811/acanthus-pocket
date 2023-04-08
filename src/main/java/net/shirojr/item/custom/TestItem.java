package net.shirojr.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.shirojr.AcanthusPocket;
import net.shirojr.screen.custom.BasicPocketScreenHandler;

public class TestItem extends Item {
    public TestItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            AcanthusPocket.LOGGER.info("Test item executed");

            world.playSound(null, user.getBlockPos(),
                    SoundEvents.BLOCK_COPPER_PLACE, SoundCategory.PLAYERS,
                    1f, 1f);
        }

        user.openHandledScreen(new SimpleNamedScreenHandlerFactory(
                (syncId, inv, player) -> new BasicPocketScreenHandler(syncId, inv, inv),
                user.getName())
        );

        return super.use(world, user, hand);
    }
}
