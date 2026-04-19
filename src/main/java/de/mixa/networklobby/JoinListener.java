package de.mixa.networklobby;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private final NetworkLobby plugin;

    public JoinListener(NetworkLobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (plugin.isLobbyMode()) {
            plugin.giveCompass(player);
        } else {
            plugin.removeServerCompass(player);
        }
    }
}