package net.shirojr;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.shirojr.item.AcanthusPocketItems;
import net.shirojr.screen.AcanthusPocketScreens;
import net.shirojr.screen.custom.BasicPocketScreenHandler;
import net.shirojr.sound.AcanthusPocketSounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AcanthusPocket implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("acanthus-pocket");
    public static final String MOD_ID = "acanthus-pocket";

    public static final Identifier FAILED_QUICK_TIME_PACKET_ID = new Identifier(AcanthusPocket.MOD_ID, "failed_quick_time_packet");


    @Override
    public void onInitialize() {
        AcanthusPocketItems.registerModItems();
        AcanthusPocketSounds.initializeSounds();
        AcanthusPocketScreens.registerScreens();

        receiveQuickTimeFailedPacket();
    }

    public void receiveQuickTimeFailedPacket() {
        ServerPlayNetworking.registerGlobalReceiver(AcanthusPocket.FAILED_QUICK_TIME_PACKET_ID, (server, player, handler, receivingBuf, responseSender) -> {
            server.execute(() -> {
                BasicPocketScreenHandler screenHandler = (BasicPocketScreenHandler) player.currentScreenHandler;
                screenHandler.failedQuickTimeEvent(receivingBuf.readUuid());
            });
        });
    }
}
