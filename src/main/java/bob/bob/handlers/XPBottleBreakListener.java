package bob.bob.handlers;

import bob.bob.Bob;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExpBottleEvent;

public class XPBottleBreakListener implements Listener {
    public XPBottleBreakListener(Bob plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBottleBreak(ExpBottleEvent e){
        e.setShowEffect(false);
        e.getEntity().setGlowing(true);
    }
}
