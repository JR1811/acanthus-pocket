package net.shirojr.screen.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.shirojr.screen.AcanthusPocketScreens;
import org.jetbrains.annotations.Nullable;

public class BasicPocketScreenHandler extends ScreenHandler {

    private final Inventory inventory;
    public BasicPocketScreenHandler(int syncId, PlayerInventory inventory) {
        this(syncId, inventory, new SimpleInventory(3));
    }

    public BasicPocketScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(AcanthusPocketScreens.BASIC_POCKET_HANDLER, syncId);
        checkSize(inventory, 3);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);

        //TODO: custom 3 slots
        //this.addSlot(new Slot(inventory, 0, 56, 17));
        //this.addSlot(new Slot(inventory, 1, 56, 53));
        //this.addSlot(new Slot(inventory, 2, 116, 35));

        addPlayerHotbar(playerInventory);

    }


    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return false;
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {

        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
