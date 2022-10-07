package bob.bob.handlers;

import bob.bob.Bob;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.util.Vector;

public class SheerSheepListener implements Listener {
    Bob plugin;
    public SheerSheepListener(Bob plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void SheerSheep(PlayerShearEntityEvent e){
        if (e.getEntity().getType() == EntityType.SHEEP){
            plugin.getServer().getWorld("world").spawnEntity(e.getEntity().getLocation(), EntityType.FIREWORK);
            e.getEntity().remove();
        }
    }
}
