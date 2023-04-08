package net.shirojr.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.shirojr.AcanthusPocket;
import net.shirojr.screen.custom.BasicPocketScreenHandler;

public class AcanthusPocketScreens {
    public static ScreenHandlerType<BasicPocketScreenHandler> BASIC_POCKET_HANDLER;

    static {
        BASIC_POCKET_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(AcanthusPocket.MOD_ID, "basic_pocket_gui"), BasicPocketScreenHandler::new);
    }

    public static void registerScreens() {
        AcanthusPocket.LOGGER.info("Registering " + AcanthusPocket.MOD_ID + " screens");
    }
}
