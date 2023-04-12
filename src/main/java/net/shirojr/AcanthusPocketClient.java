package net.shirojr;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.util.Identifier;
import net.shirojr.screen.AcanthusPocketScreens;
import net.shirojr.screen.custom.BasicPocketScreen;
import net.shirojr.screen.custom.BasicPocketScreenHandler;

public class AcanthusPocketClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(AcanthusPocketScreens.BASIC_POCKET_HANDLER, BasicPocketScreen::new);
    }
}
