package net.shirojr.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.shirojr.AcanthusPocket;

public class AcanthusPocketItems {
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(AcanthusPocket.MOD_ID, name), item);
    }

    public static void registerModItems() {
        AcanthusPocket.LOGGER.info("Registering " + AcanthusPocket.MOD_ID + " Mod items");
    }
}
