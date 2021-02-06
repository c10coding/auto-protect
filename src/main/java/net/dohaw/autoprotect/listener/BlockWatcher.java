package net.dohaw.autoprotect.listener;

import net.dohaw.autoprotect.AreaManager;
import net.dohaw.autoprotect.AutoProtectPlugin;
import net.dohaw.autoprotect.config.BaseConfig;
import net.dohaw.autoprotect.utils.Area;
import net.dohaw.autoprotect.utils.WandUtils;
import net.dohaw.corelib.ChatSender;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BlockWatcher implements Listener {

    private AutoProtectPlugin plugin;
    private AreaManager areaManager;

    public BlockWatcher(AutoProtectPlugin plugin){
        this.plugin = plugin;
        this.areaManager = plugin.getAreaManager();
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

                if(areaBlockBroken.isCanBreak()){

                    if(areaBlockBroken.isWillRestoreOnBreak()){

                        final Material BLOCK_MAT = blockBroken.getType();
                        final Location BLOCK_LOCATION = blockBroken.getLocation();

                        areaBlockBroken.getBlocksNeedRestoring().put(BLOCK_LOCATION, BLOCK_MAT);

                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                            areaBlockBroken.getBlocksNeedRestoring().remove(BLOCK_LOCATION);
                            blockBroken.setType(BLOCK_MAT);
                        }, (long) (plugin.getBlockRestoreInterval() * 60 * 20));

                    }

                }else{
                    ChatSender.sendPlayerMessage(plugin.getCantBreakMessage(), true, e.getPlayer(), AutoProtectPlugin.PREFIX);
                    e.setCancelled(true);
                }

            }

        }

    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){

        if(!e.isCancelled()){

            Block blockPlaced = e.getBlockPlaced();
            Area areaBlockBroken = areaManager.getAreaBlockBroken(blockPlaced);
            if(areaBlockBroken != null){

                if(areaBlockBroken.isCanBuild()){

                    if(areaBlockBroken.isWillRestoreOnBuild()){

                        final Material BLOCK_MAT = Material.AIR;
                        final Location BLOCK_LOCATION = blockPlaced.getLocation();

                        areaBlockBroken.getBlocksNeedRestoring().put(BLOCK_LOCATION, BLOCK_MAT);

                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                            areaBlockBroken.getBlocksNeedRestoring().remove(BLOCK_LOCATION);
                            blockPlaced.setType(BLOCK_MAT);
                        }, (long) (plugin.getBlockRestoreInterval() * 60 * 20));

                    }

                }else{
                    e.setCancelled(true);
                    ChatSender.sendPlayerMessage(plugin.getCantBuildMessage(), true, e.getPlayer(), AutoProtectPlugin.PREFIX);
                }

            }

        }

    }

}
