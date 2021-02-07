package net.dohaw.autoprotect.utils;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.math.IntRange;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Area {

    @Getter @Setter
    private Location point1;

    @Getter @Setter
    private Location point2;

    @Getter @Setter
    private boolean canBuild, canBreak, willRestoreOnBreak, willRestoreOnBuild;

    @Getter @Setter
    private UUID owner;

    @Getter
    private Map<Location, Material> blocksNeedRestoring = new HashMap<>();

    public boolean isBlockWithinArea(Block block){
        return new IntRange(point1.getX(), point2.getX()).containsDouble(block.getX())
                && new IntRange(point1.getY(), point2.getY()).containsDouble(block.getY())
                &&  new IntRange(point1.getZ(), point2.getZ()).containsDouble(block.getZ());
    }

    public Area(UUID owner, Location point1, Location point2){
        this.point1 = point1;
        this.point2 = point2;
        this.owner = owner;
    }

}
