package net.dohaw.autoprotect;

import lombok.Getter;
import net.dohaw.autoprotect.config.AreasConfig;
import net.dohaw.autoprotect.config.BaseConfig;
import net.dohaw.autoprotect.listener.BlockWatcher;
import net.dohaw.autoprotect.listener.WandWatcher;
import net.dohaw.autoprotect.utils.Area;
import net.dohaw.corelib.CoreLib;
import net.dohaw.corelib.JPUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Plugin made by Caleb / c10coding on github
 * Description: Plugin that allows you define areas, and if blocks are broken in these areas, they are restored
 * For: chcheesesauce on Fiverr
 */
public final class AutoProtectPlugin extends JavaPlugin {

    @Getter
    private Map<UUID, Area> sessions = new HashMap<>();

    public static final String PREFIX = "&f[&aAuto&9Protect&f]";

    @Getter
    private AreaManager areaManager;

    @Getter
    private AreasConfig areasConfig;
    @Getter
    private BaseConfig baseConfig;

    @Override
    public void onEnable() {
        CoreLib.setInstance(this);
        JPUtils.validateFiles("config.yml", "areas.yml");
        this.baseConfig = new BaseConfig(this);
        this.areasConfig = new AreasConfig(this);
        this.areaManager = new AreaManager(this);
        JPUtils.registerCommand("autoprotect", new AutoProtectCommand(this));
        JPUtils.registerEvents(new BlockWatcher(this), new WandWatcher(this));
    }

    @Override
    public void onDisable() {
        areaManager.saveAreas();
    }

}
