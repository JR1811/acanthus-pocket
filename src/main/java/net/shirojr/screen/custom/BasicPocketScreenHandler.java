package net.shirojr.screen.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.shirojr.screen.AcanthusPocketScreens;

public class BasicPocketScreenHandler extends ScreenHandler {

    private final Inventory playerInventory;
    private final Inventory targetInventory;
    public BasicPocketScreenHandler(int syncId, PlayerInventory inventory) {
        this(syncId, inventory, inventory);
    }

    public BasicPocketScreenHandler(int syncId, PlayerInventory playerInventory, PlayerInventory targetInventory) {
        super(AcanthusPocketScreens.BASIC_POCKET_HANDLER, syncId);

        checkSize(playerInventory, 3);
        checkSize(targetInventory, 3);

        this.playerInventory = playerInventory;
        this.targetInventory = targetInventory;
        this.playerInventory.onOpen(playerInventory.player);
        this.targetInventory.onOpen(targetInventory.player);

        addPlayerHotbar(playerInventory);
        addTargetSlots(targetInventory);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return player != null;
    }

    private void addTargetSlots(PlayerInventory targetInventory) {
        this.addSlot(new Slot(targetInventory, 0, 53, 17));
        this.addSlot(new Slot(targetInventory, 1, 80, 17));
        this.addSlot(new Slot(targetInventory, 2, 107, 17));
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 43));
        }
    }

    public void succeededQuickTimeEvent() {

    }

    public void failedQuickTimeEvent() {

    }
}
