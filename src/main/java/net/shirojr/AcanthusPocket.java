package net.shirojr;

import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.shirojr.item.AcanthusPocketItems;
import net.shirojr.sound.AcanthusPocketSounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AcanthusPocket implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("acanthus-pocket");
	public static final String MOD_ID = "acanthus-pocket";

	@Override
	public void onInitialize() {
		AcanthusPocketItems.registerModItems();
		AcanthusPocketSounds.initializeSounds();
	}
}
