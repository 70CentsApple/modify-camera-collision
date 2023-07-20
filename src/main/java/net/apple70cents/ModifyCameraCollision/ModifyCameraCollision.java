package net.apple70cents.ModifyCameraCollision;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.apple70cents.ModifyCameraCollision.config.ModConfig;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModifyCameraCollision implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("modify-camera-collision");

	@Override
	public void onInitialize() {
		AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
	}
}