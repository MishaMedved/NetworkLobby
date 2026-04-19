package de.mixa.networklobby;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InteractListener implements Listener {

    private final NetworkLobby plugin;

    public InteractListener(NetworkLobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!plugin.isLobbyMode()) return;
        if (event.getItem() == null) return;

        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return;

        ItemStack item = event.getItem();
        if (!plugin.isServerCompass(item)) return;

        event.setCancelled(true);

        Inventory menu = Bukkit.createInventory(null, 9, "§8Server-Menü");

        ItemStack smp = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta smpMeta = smp.getItemMeta();
        if (smpMeta != null) {
            smpMeta.setDisplayName("§aSMP");
            smp.setItemMeta(smpMeta);
        }

        ItemStack pvp = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta pvpMeta = pvp.getItemMeta();
        if (pvpMeta != null) {
            pvpMeta.setDisplayName("§cPvP");
            pvp.setItemMeta(pvpMeta);
        }

        ItemStack parkour = new ItemStack(Material.WATER_BUCKET);
        ItemMeta parkourMeta = parkour.getItemMeta();
        if (parkourMeta != null) {
            parkourMeta.setDisplayName("§bParkour");
            parkour.setItemMeta(parkourMeta);
        }

        menu.setItem(2, smp);
        menu.setItem(4, pvp);
        menu.setItem(6, parkour);

        event.getPlayer().openInventory(menu);
    }
}