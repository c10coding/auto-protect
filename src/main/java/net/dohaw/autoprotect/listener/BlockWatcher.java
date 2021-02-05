package net.dohaw.autoprotect.listener;

import net.dohaw.autoprotect.AreaManager;
import net.dohaw.autoprotect.AutoProtectPlugin;
import net.dohaw.autoprotect.config.BaseConfig;
import net.dohaw.autoprotect.utils.Area;
import net.dohaw.autoprotect.utils.WandUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockWatcher implements Listener {

    private AutoProtectPlugin plugin;
    private AreaManager areaManager;
    private BaseConfig baseConfig;

    public BlockWatcher(AutoProtectPlugin plugin){
        this.plugin = plugin;
        this.areaManager = plugin.getAreaManager();
        this.baseConfig = plugin.getBaseConfig();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){

        if(!e.isCancelled()){

            ItemStack itemInHand = e.getPlayer().getInventory().getItemInMainHand();
            if(WandUtils.isAPWand(itemInHand)){
                e.setCancelled(true);
                return;
            }

            Block blockBroken = e.getBlock();

            Area areaBlockBroken = areaManager.getAreaBlockBroken(blockBroken);
            if(areaBlockBroken != null){

                final Material BLOCK_MAT = blockBroken.getType();
                final Location BLOCK_LOCATION = blockBroken.getLocation();

                areaBlockBroken.getBlocksNeedRestoring().put(BLOCK_LOCATION, BLOCK_MAT);
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    areaBlockBroken.getBlocksNeedRestoring().remove(BLOCK_LOCATION);
                    blockBroken.setType(BLOCK_MAT);
                }, baseConfig.getBlockRestoreInterval() * 20);

            }

        }

    }

}
