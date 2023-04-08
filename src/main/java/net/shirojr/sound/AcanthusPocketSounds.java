package net.shirojr.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.shirojr.AcanthusPocket;

public class AcanthusPocketSounds {
    public static SoundEvent ACTION_METAL_WHISTLE = registerSound("metal_whistle"); // https://freesound.org/people/strongbot/sounds/568995/
    public static SoundEvent ACTION_POCKETING = registerSound("pocketing"); // https://freesound.org/people/Fugeni/sounds/416286/

    static SoundEvent registerSound(String id) {
        SoundEvent sound = SoundEvent.of(new Identifier(AcanthusPocket.MOD_ID, id));
        return Registry.register(Registries.SOUND_EVENT, new Identifier(AcanthusPocket.MOD_ID, id), sound);
    }

    public static void initializeSounds() {
        // initialize NeMuelchSounds class
        AcanthusPocket.LOGGER.info("Registering " + AcanthusPocket.MOD_ID + " sounds");
    }
}
