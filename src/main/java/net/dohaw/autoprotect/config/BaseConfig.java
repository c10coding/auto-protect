package net.dohaw.autoprotect.config;

import net.dohaw.corelib.Config;
import org.bukkit.plugin.java.JavaPlugin;

public class BaseConfig extends Config {

    public BaseConfig(JavaPlugin plugin) {
        super(plugin, "config.yml");
    }

    public int getBlockRestoreInterval(){
        return config.getInt("Restore Block Interval");
    }

}
