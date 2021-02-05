package net.dohaw.autoprotect.utils;

import net.dohaw.corelib.StringUtils;
import net.dohaw.corelib.helpers.ItemStackHelper;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class WandUtils {

    private static final String WAND_PDC_KEY = "ap_wand";
    private static final ItemStack AP_WAND = apWand();

    public static boolean isAPWand(ItemStack stack){
        if(stack.getType() == AP_WAND.getType()){
            ItemMeta meta = stack.getItemMeta();
            PersistentDataContainer pdc = meta.getPersistentDataContainer();
            return pdc.has(NamespacedKey.minecraft(WAND_PDC_KEY), PersistentDataType.STRING);
        }
        return false;
    }

    /**
     * The Auto Protect wand
     * @return The wand
     */
    public static ItemStack apWand(){

        ItemStack wand = new ItemStack(Material.DIAMOND_AXE);
        ItemMeta wandMeta = wand.getItemMeta();
        PersistentDataContainer pdc = wandMeta.getPersistentDataContainer();

        pdc.set(NamespacedKey.minecraft(WAND_PDC_KEY), PersistentDataType.STRING, "value");
        wandMeta.setDisplayName(StringUtils.colorString("&aAuto Protect Wand"));

        List<String> wandLore = new ArrayList<>();
        wandLore.add("Use this sort of like a world-edit wand");
        wandLore.add("Choose the areas you want to automatically be regenerated");
        wandMeta.setLore(wandLore);
        wand.setItemMeta(wandMeta);
        ItemStackHelper.addGlowToItem(wand);

        return wand;

    }

    public static ItemStack getApWand(){
        return AP_WAND.clone();
    }

}
