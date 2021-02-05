package net.dohaw.autoprotect.command;

import net.dohaw.corelib.StringUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class AutoProtectCommand implements CommandExecutor {

    private final String WAND_PDC_KEY = "apWand";

    public AutoProtectCommand(){}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player pSender = (Player) sender;
            if(args[0].equalsIgnoreCase("wand") && pSender.hasPermission("ap.wand")){
                pSender.getInventory().addItem(apWand());
                pSender.sendMessage("You have been given the Auto Protect wand!");
            }
        }else{
            sender.sendMessage("Only players can use commands from this plugin!");
        }
        return false;
    }

    private ItemStack apWand(){

        ItemStack wand = new ItemStack(Material.DIAMOND_AXE);
        ItemMeta wandMeta = wand.getItemMeta();
        PersistentDataContainer pdc = wandMeta.getPersistentDataContainer();

        pdc.set(NamespacedKey.minecraft(WAND_PDC_KEY), PersistentDataType.STRING, "");
        wandMeta.setDisplayName(StringUtils.colorString("&aAuto Protect Wand"));

        List<String> wandLore = new ArrayList<>();
        wandLore.add("Use this sort of like a world-edit wand");
        wandLore.add("Choose the areas you want to automatically be re-generated");
        wandMeta.setLore(wandLore);
        wand.setItemMeta(wandMeta);
        return wand;

    }

}
