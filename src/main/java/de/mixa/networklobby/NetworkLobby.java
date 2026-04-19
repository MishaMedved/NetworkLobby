package de.mixa.networklobby;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class NetworkLobby extends JavaPlugin {

    private String mode;
    private int compassSlot;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        mode = getConfig().getString("mode", "backend");
        compassSlot = getConfig().getInt("compass-slot", 4);

        getLogger().info("NetworkLobby wurde aktiviert! Modus: " + mode);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new InteractListener(this), this);
        getServer().getPluginManager().registerEvents(new MenuClickListener(this), this);
        getServer().getPluginManager().registerEvents(new ProtectionListener(this), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("NetworkLobby wurde deaktiviert!");
    }

    public boolean isLobbyMode() {
        return mode.equalsIgnoreCase("lobby");
    }

    public int getCompassSlot() {
        return compassSlot;
    }

    public ItemStack createCompass() {
        ItemStack item = new ItemStack(Material.COMPASS);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§aServer-Menü");
            item.setItemMeta(meta);
        }

        return item;
    }
    public boolean isServerCompass(ItemStack item) {
        if (item == null) return false;
        if (item.getType() != Material.COMPASS) return false;
        if (!item.hasItemMeta()) return false;
        if (!item.getItemMeta().hasDisplayName()) return false;

        return item.getItemMeta().getDisplayName().equals("§aServer-Menü");
    }

    public void giveCompass(Player player) {
        if (!isLobbyMode()) return;

        player.getInventory().setItem(compassSlot, createCompass());
    }

    public void removeServerCompass(Player player) {
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack item = player.getInventory().getItem(i);
            if (isServerCompass(item)) {
                player.getInventory().setItem(i, null);
            }
        }
    }

    public void connect(Player player, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        player.sendPluginMessage(this, "BungeeCord", out.toByteArray());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Nur Spieler können diesen Command ausführen.");
            return true;
        }

        String cmd = command.getName().toLowerCase();

        switch (cmd) {
            case "smp" -> {
                connect(player, "smp");
                return true;
            }
            case "pvp" -> {
                connect(player, "pvp");
                return true;
            }
            case "parkour" -> {
                connect(player, "parkour");
                return true;
            }
            case "lobby" -> {
                connect(player, "lobby");
                return true;
            }
            case "bedfight" -> {
                connect(player, "bedfight");
                return true;
            }
        }

        return false;
    }
}