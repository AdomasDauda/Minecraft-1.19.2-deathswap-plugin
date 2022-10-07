package bob.bob;

import bob.bob.commands.JoinSwap;
import bob.bob.commands.LeaveSwap;
import bob.bob.commands.StartSwap;
import bob.bob.commands.SwapDelay;
import bob.bob.handlers.PlayerDiedListener;
import bob.bob.handlers.PlayerQuitListener;
import bob.bob.handlers.SheerSheepListener;
import bob.bob.handlers.XPBottleBreakListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;

public final class Bob extends JavaPlugin {
    public ArrayList<Player> joinedPlayers = new ArrayList<>();
    public int deathSwapDelay = 60;
    public boolean deathSwapHappening = false;
    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("startswap").setExecutor(new StartSwap(this));
        getCommand("joinswap").setExecutor(new JoinSwap(this));
        getCommand("leaveswap").setExecutor(new LeaveSwap(this));
        getCommand("changeswapdelay").setExecutor(new SwapDelay(this));
        new PlayerQuitListener(this);
        //new SheerSheepListener(this);      //experimental features
        //new XPBottleBreakListener(this);      // --||--
        new PlayerDiedListener(this);
    }
}

