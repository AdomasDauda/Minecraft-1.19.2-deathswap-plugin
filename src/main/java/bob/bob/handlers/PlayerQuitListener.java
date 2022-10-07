package bob.bob.handlers;

import bob.bob.Bob;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

public class PlayerQuitListener implements Listener {
    Bob plugin;
    public PlayerQuitListener(Bob plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent e) {
        if (!plugin.deathSwapHappening) {
            return;
        }

        if (!(plugin.joinedPlayers.contains(e.getPlayer()))) {
            return;
        }

        plugin.joinedPlayers.remove(e.getPlayer());

        if (plugin.joinedPlayers.size() > 1){
            Bukkit.broadcastMessage(e.getPlayer().getName()+" was eliminated!");
            return;
        }

        Player player = plugin.joinedPlayers.get(0);
        Bukkit.broadcastMessage(player.getName()+" is THE WINNER (because everyone quit lol)");
        plugin.deathSwapHappening = false;
        plugin.joinedPlayers.clear();
        Objects.requireNonNull(plugin.getServer().getWorld("world")).spawnEntity(player.getLocation(), EntityType.LIGHTNING);
        Bukkit.broadcastMessage(e.getPlayer().getName()+" has quit! Stopping deathswap.");
    }
}