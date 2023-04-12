package net.shirojr.screen.custom;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.shirojr.screen.AcanthusPocketScreens;
import net.shirojr.sound.AcanthusPocketSounds;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class BasicPocketScreenHandler extends ScreenHandler {

    private final Inventory playerInventory;
    private final Inventory targetInventory;
    private final ScreenHandlerContext context;
    private PlayerEntity target;

    public BasicPocketScreenHandler(int syncId, PlayerInventory inventory) {
        this(syncId, inventory, new SimpleInventory(3), null, ScreenHandlerContext.EMPTY);
    }

    public BasicPocketScreenHandler(int syncId, PlayerInventory playerInventory, Inventory targetInventory,
                                    @Nullable PlayerEntity target, ScreenHandlerContext context) {
        super(AcanthusPocketScreens.BASIC_POCKET_HANDLER, syncId);

        // handling client-sided null of target
        if (target == null) {
            target = playerInventory.player;
            targetInventory = target.getInventory();
        }
        this.target = target;
        this.context = context;
        this.playerInventory = playerInventory;
        this.targetInventory = targetInventory;

        checkSize(playerInventory, 3);
        checkSize(targetInventory, 3);

        this.playerInventory.onOpen(playerInventory.player);
        this.targetInventory.onOpen(target);

        addPlayerHotbar(playerInventory);
        addTargetSlots(targetInventory);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = this.slots.get(slot);
        if (slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            if (slot == 2) {
                if (!this.insertItem(itemStack2, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
                slot2.onQuickTransfer(itemStack2, itemStack);
                this.playPocketingSound();
            } else if (slot == 0 || slot == 1 ? !this.insertItem(itemStack2, 3, 39, false) : (slot >= 3 && slot < 30 ? !this.insertItem(itemStack2, 30, 39, false) : slot >= 30 && slot < 39 && !this.insertItem(itemStack2, 3, 30, false))) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot2.onTakeItem(player, itemStack2);
        }
        return itemStack;
    }

    private void playPocketingSound() {
        if (!this.target.getWorld().isClient()) {
            Entity entity = this.target;
            entity.getWorld().playSound(entity.getX(), entity.getY(), entity.getZ(), AcanthusPocketSounds.ACTION_POCKETING, SoundCategory.NEUTRAL, 1.0f, 1.0f, false);
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return player != null;
    }

    private void addTargetSlots(Inventory targetInventory) {
        int y = 63;
        this.addSlot(new Slot(assembleRandomEntries(targetInventory), 0, 54, y));
        this.addSlot(new Slot(assembleRandomEntries(targetInventory), 1, 81, y));
        this.addSlot(new Slot(assembleRandomEntries(targetInventory), 2, 108, y));
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 9 + i * 18, 101));
        }
    }

    public void succeededQuickTimeEvent() {

    }

    public void failedQuickTimeEvent() {

    }

    private Inventory assembleRandomEntries(Inventory targetInventory) {
        if (MinecraftClient.getInstance().player == null) return null;
        ArrayList<Integer> randomStackIndex = new ArrayList<>();

        // avoid randomly picked duplicate slots of targetInventory
        while (randomStackIndex.size() < 3) {
            int randomIndex = MinecraftClient.getInstance().player.getWorld().getRandom().nextInt(targetInventory.size());
            if (!randomStackIndex.contains(randomIndex)) {
                randomStackIndex.add(randomIndex);
            }
        }
        Inventory inventory = new SimpleInventory(3);
        checkSize(inventory, 3);

        inventory.setStack(0, targetInventory.getStack(randomStackIndex.get(0)).copy());    //FIXME: Index 1 out of bounds for length 0
        inventory.setStack(1, targetInventory.getStack(randomStackIndex.get(1)).copy());
        inventory.setStack(2, targetInventory.getStack(randomStackIndex.get(2)).copy());
        inventory.markDirty();

        return inventory;
    }

}
