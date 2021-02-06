package net.dohaw.autoprotect.config;

import net.dohaw.autoprotect.utils.Area;
import net.dohaw.corelib.Config;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class AreasConfig extends Config {

    public AreasConfig(JavaPlugin plugin) {
        super(plugin, "areas.yml");
    }

    public void saveArea(String name, Area area){
        config.set("Areas." + name + ".Point 1", area.getPoint1());
        config.set("Areas." + name + ".Point 2", area.getPoint2());
        config.set("Areas." + name + ".Can Break", area.isCanBreak());
        config.set("Areas." + name + ".Can Build", area.isCanBuild());
        config.set("Areas." + name + ".Will Restore On Build", area.isWillRestoreOnBuild());
        config.set("Areas." + name + ".Will Restore On Break", area.isWillRestoreOnBreak());
        saveConfig();
    }

    public Map<String, Area> loadAreas(){
        Map<String, Area> areas = new HashMap<>();
        ConfigurationSection section = config.getConfigurationSection("Areas");
        if(section != null){
            for(String key : section.getKeys(false)){

                Location point1 = section.getLocation(key + ".Point 1");
                Location point2 = section.getLocation(key + ".Point 2");
                Area area = new Area(point1, point2);

                boolean canBreak = section.getBoolean(key + ".Can Break");
                boolean canBuild = section.getBoolean(key + ".Can Build");
                boolean willRestoreOnBuild = section.getBoolean(key + ".Will Restore On Build");
                boolean willRestoreOnBreak = section.getBoolean(key + ".Will Restore On Break");

                area.setWillRestoreOnBuild(willRestoreOnBuild);
                area.setWillRestoreOnBreak(willRestoreOnBreak);
                area.setCanBreak(canBreak);
                area.setCanBuild(canBuild);

                areas.put(key, area);
            }
        }else{
            plugin.getLogger().warning("The areas section in areas.yml is either empty or has been removed entirely from the configuration file. This is normally not something to worry about...");
        }
        return areas;
    }

    public Area getArea(String name){
        Location point1 = config.getLocation("Areas." + name + ".Point 1");
        Location point2 = config.getLocation("Areas." + name + ".Point 2");
        return new Area(point1, point2);
    }

}
