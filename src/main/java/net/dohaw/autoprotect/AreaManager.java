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
        this.areasConfig = plugin.getAreasConfig();
        this.areas = areasConfig.loadAreas();
    }

    public Area getAreaBlockBroken(Block blockBroken){
        for(Map.Entry<String, Area> entry : areas.entrySet()){
            Area area = entry.getValue();
            if(entry.getValue().isBlockWithinArea(blockBroken)){
                return area;
            }
        }
        return null;
    }

    public void addNewArea(String areaName, Area area){
        areas.put(areaName, area);
    }

    public boolean isDuplicateAreaName(String areaName){
        return areas.containsKey(areaName);
    }

    public void saveAreas(){
        for(Map.Entry<String, Area> entry : areas.entrySet()){
            String key = entry.getKey();
            Area area = entry.getValue();
            areasConfig.saveArea(key, area);
            Map<Location, Material> blocksNeedRestoring = area.getBlocksNeedRestoring();
            blocksNeedRestoring.forEach((l, m) -> l.getBlock().setType(m));
        }
    }

}
