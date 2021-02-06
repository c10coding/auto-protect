package net.dohaw.autoprotect;

import lombok.Getter;
import net.dohaw.autoprotect.config.AreasConfig;
import net.dohaw.autoprotect.config.BaseConfig;
import net.dohaw.autoprotect.listener.BlockWatcher;
import net.dohaw.autoprotect.listener.WandWatcher;
import net.dohaw.autoprotect.utils.Area;
import net.dohaw.corelib.CoreLib;
import net.dohaw.corelib.JPUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Author: c10coding on Github
 * Description: Plugin that allows you define areas, and if blocks are broken in these areas, they are restored
 * Date Started: 2/5/2021
 * Date Finished: 2/5/2021
 * For: chcheesesauce on Fiverr
 * Hello future person :)
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

    /*
        Config values
     */
    @Getter
    private String cantBuildMessage, cantBreakMessage;
    @Getter
    private double blockRestoreInterval;

    @Override
    public void onEnable() {
        CoreLib.setInstance(this);
        JPUtils.validateFiles("config.yml", "areas.yml");
        this.baseConfig = new BaseConfig(this);
        loadBaseConfigValues();
        this.areasConfig = new AreasConfig(this);
        this.areaManager = new AreaManager(this);
        JPUtils.registerCommand("autoprotect", new AutoProtectCommand(this));
        JPUtils.registerEvents(new BlockWatcher(this), new WandWatcher(this));
    }

    @Override
    public void onDisable() {
        for(Map.Entry<String, Area> entry : areaManager.getAreas().entrySet()){
            Area area = entry.getValue();
            for(Map.Entry<Location, Material> entry1 : area.getBlocksNeedRestoring().entrySet()){
                Location locBlock = entry1.getKey();
                Material mat = entry1.getValue();
                locBlock.getBlock().setType(mat);
            }
        }
    }

    public void loadBaseConfigValues(){
        this.cantBreakMessage = baseConfig.getCantBreakMessage();
        this.cantBuildMessage = baseConfig.getCantBuildMessage();
        this.blockRestoreInterval = baseConfig.getBlockRestoreInterval();
    }

}
