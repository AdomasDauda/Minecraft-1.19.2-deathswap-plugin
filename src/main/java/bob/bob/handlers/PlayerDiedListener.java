package bob.bob.handlers;

import bob.bob.Bob;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;

public class PlayerDiedListener implements Listener {

    Bob plugin;
    public PlayerDiedListener(Bob plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void playerDied(PlayerDeathEvent e){
        if (!plugin.deathSwapHappening){
            return;
        }

        if (!(plugin.joinedPlayers.contains(e.getEntity().getPlayer()))){
            return;
        }

        // from this point forward it means that player that died is in the list of players that are playing death swap

        Player player = e.getEntity().getPlayer();
        plugin.joinedPlayers.remove(player);
        player.sendMessage("You died and now you are eliminated from deaths swap");
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1,1);
        plugin.getServer().getWorld("world").spawnEntity(player.getLocation(), EntityType.LIGHTNING);

        if (plugin.joinedPlayers.size() > 1){
            Bukkit.broadcastMessage(player.getName()+" was ELIMINATED!");
            return;
        }

        // if there is only one player left

        Player winner = plugin.joinedPlayers.get(0);
        winner.playSound(winner.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST_FAR, 1, 1);
        for (int i = 0; i < 20; i++) {
            plugin.getServer().getWorld("world").spawnEntity(winner.getLocation(), EntityType.FIREWORK);
        }
        plugin.deathSwapHappening = false;
        plugin.joinedPlayers.clear();
    }
}


