package de.mixa.networklobby;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MenuClickListener implements Listener {

    private final NetworkLobby plugin;

    public MenuClickListener(NetworkLobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        if (!plugin.isLobbyMode()) return;
        if (event.getView().getTitle() == null) return;
        if (!event.getView().getTitle().equals("§8Server-Menü")) return;

        event.setCancelled(true);

        if (event.getCurrentItem() == null) return;

        ItemStack item = event.getCurrentItem();

        switch (item.getType()) {
            case GRASS_BLOCK -> {
                event.getWhoClicked().closeInventory();
                plugin.connect((org.bukkit.entity.Player) event.getWhoClicked(), "smp");
            }
            case DIAMOND_SWORD -> {
                event.getWhoClicked().closeInventory();
                plugin.connect((org.bukkit.entity.Player) event.getWhoClicked(), "pvp");
            }
            case WATER_BUCKET -> {
                event.getWhoClicked().closeInventory();
                plugin.connect((org.bukkit.entity.Player) event.getWhoClicked(), "parkour");
            }
        }
    }
}
