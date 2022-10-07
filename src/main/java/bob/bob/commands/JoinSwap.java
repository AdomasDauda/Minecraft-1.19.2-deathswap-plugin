package bob.bob.commands;

import bob.bob.Bob;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinSwap implements CommandExecutor {
    Bob plugin;
    public JoinSwap(Bob plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage("Only players can join/leave!");
            return false;
        }

        Player player = ((Player) sender).getPlayer();

        if (plugin.joinedPlayers.contains(player)){
            player.sendMessage("You have already joined!");
            return false;
        }

        if (plugin.deathSwapHappening){
            player.sendMessage("You cannot join while a DeathSwap is happening!");
            return false;
        }

        player.sendMessage("You joined the death swap!");
        plugin.joinedPlayers.add(((Player) sender).getPlayer());
        return true;
    }
}
