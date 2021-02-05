package net.dohaw.autoprotect;

import net.dohaw.autoprotect.command.AutoProtectCommand;
import net.dohaw.autoprotect.config.BaseConfig;
import net.dohaw.corelib.CoreLib;
import net.dohaw.corelib.JPUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class AutoProtect extends JavaPlugin {

    private BaseConfig baseConfig;

    @Override
    public void onEnable() {
        CoreLib.setInstance(this);
        JPUtils.validateFiles("config.yml", "regions.yml");
        this.baseConfig = new BaseConfig(this);
        JPUtils.registerCommand("autoprotect", new AutoProtectCommand());
    }

    @Override
    public void onDisable() {

    }
}
