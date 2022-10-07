package bob.bob.commands;

import bob.bob.Bob;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import java.util.ArrayList;

public class StartSwap implements CommandExecutor {
    Bob plugin;
    BukkitTask currentTask = null;

    public StartSwap(Bob plugin) {
        this.plugin = plugin;
        startDeathSwap();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can join!");
            return false;
        }

        if (plugin.joinedPlayers.size() < 2) {
            sender.sendMessage("Not enough players!");
            ArrayList<String> playernames = new ArrayList<>();
            for (Player player : plugin.joinedPlayers) {
                playernames.add(player.getName());
            }
            sender.sendMessage(playernames + " have joined the death swap!");

            return false;
        }

        if (plugin.deathSwapHappening) {
            Bukkit.broadcastMessage("Stopping deathswap");
            plugin.deathSwapHappening = false;
            return false;
        }

        sender.sendMessage(plugin.deathSwapDelay + "s is the current Death Swap delay.");
        plugin.deathSwapHappening = true;
        return true;
    }
    private void swapPlayers(Player a, Player b){
        Location aloc = a.getLocation();
        Location bloc = b.getLocation();
        a.teleport(bloc);
        b.teleport(aloc);
    }

    private void startDeathSwap(){
        new BukkitRunnable() {
            @Override
            public void run() {
                if (currentTask == null){
                    if (plugin.deathSwapHappening){
                        currentTask = deathswap(plugin.deathSwapDelay);
                    }
                    return;
                }

                if (!plugin.deathSwapHappening){
                    currentTask.cancel();
                    StartSwap.this.currentTask = null;
                }
            }
        }.runTaskTimerAsynchronously(plugin,0,0);
    }

    private BukkitTask deathswap(int delay){
        for (int i = 1; i < 6; i++) {
            int finalI = i;
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (plugin.deathSwapHappening){
                        Bukkit.broadcastMessage("Switching in " + finalI + " seconds!");
                        for (Player player: plugin.joinedPlayers) {
                            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 1, 1+finalI);
                        }
                    }
                }
            }.runTaskLaterAsynchronously(plugin, delay*20L - i*20L);
        }
        BukkitTask b = new BukkitRunnable(){
            @Override
            public void run() {
                for (int i = 0; i < plugin.joinedPlayers.size() - 1; i++) {
                    Player a = plugin.joinedPlayers.get(i);
                    Player b = plugin.joinedPlayers.get(i + 1);
                    swapPlayers(a, b);
                }
                Bukkit.broadcastMessage("SWITCHING!");
                for (Player player : plugin.joinedPlayers) {
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                }
                StartSwap.this.currentTask = null;
            }
        }.runTaskLater(plugin, delay*20L); //20L is because there are 20ticks in second
        return b;
    }

}

