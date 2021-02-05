package net.dohaw.autoprotect;

import net.dohaw.autoprotect.utils.Area;
import net.dohaw.autoprotect.utils.WandUtils;
import net.dohaw.corelib.ChatSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class AutoProtectCommand implements CommandExecutor {

    private AreaManager areaManager;
    private AutoProtectPlugin plugin;

    public AutoProtectCommand(AutoProtectPlugin plugin){
        this.plugin = plugin;
        this.areaManager = plugin.getAreaManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player pSender = (Player) sender;
            if(args[0].equalsIgnoreCase("wand") && pSender.hasPermission("ap.wand") && args.length == 1){
                pSender.getInventory().addItem(WandUtils.getApWand());
                ChatSender.sendPlayerMessage("You have been given the AutoProtect wand!", true, sender, AutoProtectPlugin.PREFIX);
                ChatSender.sendPlayerMessage("Right-click and left-click blocks to define different points for your area!", true, sender, AutoProtectPlugin.PREFIX);
            }else if(args[0].equalsIgnoreCase("define") && args.length == 2){

                if(plugin.getSessions().containsKey(pSender.getUniqueId())){

                    Map<UUID, Area> sessions = plugin.getSessions();
                    Area session = sessions.get(pSender.getUniqueId());
                    if(session.getPoint1() != null && session.getPoint2() != null){

                        String areaName = args[1];
                        if(!areaManager.isDuplicateAreaName(areaName)){
                            sessions.remove(pSender.getUniqueId());
                            areaManager.addNewArea(areaName, session);
                            ChatSender.sendPlayerMessage("You have defined a new area!", true, pSender, AutoProtectPlugin.PREFIX);
                        }else{
                            ChatSender.sendPlayerMessage("This is already a area name! Please choose another one...", true, pSender, AutoProtectPlugin.PREFIX);
                        }

                    }else{
                        ChatSender.sendPlayerMessage("One of your points haven't been set! Please set both points to define an area...", true, pSender, AutoProtectPlugin.PREFIX);
                        return false;
                    }

                }else{
                    ChatSender.sendPlayerMessage("You aren't defining a session at the moment. Set 2 points for your area and then use this command!", true, sender, AutoProtectPlugin.PREFIX);
                }

            }else{
                sendHelp(pSender);
            }
        }else{
            sender.sendMessage("Only players can use commands from this plugin!");
        }
        return false;
    }

    private void sendHelp(Player playerToSendTo){
        ChatSender.sendPlayerMessage("Commands for this AutoProtect:", true, playerToSendTo, AutoProtectPlugin.PREFIX);
        ChatSender.sendPlayerMessage("&6/aup wand &f- Gives you the AutoProtect wand so you can define areas", true, playerToSendTo, AutoProtectPlugin.PREFIX);
        ChatSender.sendPlayerMessage("&6/aup define <area name> &f- Defines a new AutoProtect area", true, playerToSendTo, AutoProtectPlugin.PREFIX);
    }

}
