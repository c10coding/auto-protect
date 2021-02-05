package net.dohaw.autoprotect.listener;

import net.dohaw.autoprotect.AutoProtectPlugin;
import net.dohaw.autoprotect.utils.Area;
import net.dohaw.autoprotect.utils.WandUtils;
import net.dohaw.corelib.ChatSender;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;

public class WandWatcher implements Listener {

    private AutoProtectPlugin plugin;

    public WandWatcher(AutoProtectPlugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onUseWand(PlayerInteractEvent e){

        Action action = e.getAction();
        if(action == Action.RIGHT_CLICK_BLOCK || action == Action.LEFT_CLICK_BLOCK){
            ItemStack item = e.getItem();
            if(item != null){
                if(WandUtils.isAPWand(item)){

                    Player player = e.getPlayer();
                    Area session;
                    Map<UUID, Area> sessions = plugin.getSessions();
                    if(sessions.containsKey(player.getUniqueId())){
                        session = sessions.get(player.getUniqueId());
                    }else{
                        session = new Area(null, null);
                    }

                    // Can't be NPE because getClickedBlock has to be a block, based on the already checked action above (RIGHT_CLICK_BLOCK and LEFT_CLICK_BLOCK)
                    Location locationClicked = e.getClickedBlock().getLocation();
                    if(action == Action.RIGHT_CLICK_BLOCK){
                        ChatSender.sendPlayerMessage("You have set the second point for the defined area!", true, player, AutoProtectPlugin.PREFIX);
                        session.setPoint2(locationClicked);
                    }else{
                        ChatSender.sendPlayerMessage("You have set the first point for the defined area!", true, player, AutoProtectPlugin.PREFIX);
                        session.setPoint1(locationClicked);
                    }

                }
            }
        }
    }

}
