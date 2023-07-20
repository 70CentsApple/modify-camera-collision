package net.apple70cents.ModifyCameraCollision.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.ArrayList;
import java.util.List;

@me.shedaniel.autoconfig.annotation.Config(name = "modify-camera-collision")
public class ModConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    public List<String> forceEnableCollisionList = new ArrayList<>();
    @ConfigEntry.Gui.Tooltip
    public boolean disableCollisionForAllBlocks = false;
    @ConfigEntry.Gui.Tooltip
    public List<String> forceDisableCollisionList = new ArrayList<>();
}
