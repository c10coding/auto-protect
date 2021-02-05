package net.dohaw.autoprotect.utils;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Map;

public class Area {

    @Getter @Setter
    private Location point1;

    @Getter @Setter
    private Location point2;

    @Getter
    private Map<Location, Material> blocksNeedRestoring = new HashMap<>();

    public boolean isBlockWithinArea(Block block){
        return true;
    }

    public Area(Location point1, Location point2){
        this.point1 = point1;
        this.point2 = point2;
    }

}
