package net.dohaw.autoprotect;

import lombok.Getter;
import net.dohaw.autoprotect.config.AreasConfig;
import net.dohaw.autoprotect.utils.Area;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Map;

public class AreaManager {

    private AreasConfig areasConfig;
    @Getter private Map<String, Area> areas;

    public AreaManager(AutoProtectPlugin plugin){
        load(plugin);
    }

    public Area getAreaBlockBroken(Block block){
        for(Map.Entry<String, Area> entry : areas.entrySet()){
            Area area = entry.getValue();
            if(entry.getValue().isBlockWithinArea(block)){
                return area;
            }
        }
        return null;
    }

    public void load(AutoProtectPlugin plugin){
         this.areasConfig = plugin.getAreasConfig();
         this.areas = areasConfig.loadAreas();
    }

    public void addNewArea(String areaName, Area area){
        area.setCanBuild(false);
        area.setCanBreak(false);
        area.setWillRestoreOnBreak(true);
        area.setWillRestoreOnBuild(true);
        areas.put(areaName, area);
        areasConfig.saveArea(areaName, area);
    }

    public boolean isDuplicateAreaName(String areaName){
        return areas.containsKey(areaName);
    }

}
