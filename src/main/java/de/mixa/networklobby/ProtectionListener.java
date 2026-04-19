package de.mixa.networklobby;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

public class ProtectionListener implements Listener {

    private final NetworkLobby plugin;

    public ProtectionListener(NetworkLobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (!plugin.isLobbyMode()) return;

        if (plugin.isServerCompass(event.getItemDrop().getItemStack())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!plugin.isLobbyMode()) return;

        ItemStack current = event.getCurrentItem();
        ItemStack cursor = event.getCursor();

        if (plugin.isServerCompass(current) || plugin.isServerCompass(cursor)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!plugin.isLobbyMode()) return;

        ItemStack oldCursor = event.getOldCursor();
        if (plugin.isServerCompass(oldCursor)) {
            event.setCancelled(true);
        }
    }
}