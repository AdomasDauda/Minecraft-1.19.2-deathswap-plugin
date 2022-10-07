package bob.bob.commands;

import bob.bob.Bob;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveSwap implements CommandExecutor {
    Bob plugin;
    public LeaveSwap(Bob plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage("Only players can join/leave!");
            return false;
        }

        Player player = ((Player) sender).getPlayer();



        if (!(plugin.joinedPlayers.contains(player))){
            player.sendMessage("You have not joined death swap yet!");
            return false;
        }

        if (plugin.deathSwapHappening){
            player.sendMessage("Cannot leave while deathswap is happening! Consider leaving the server.");
            return false;
        }

        player.sendMessage("You left the death swap!");
        plugin.joinedPlayers.remove(((Player) sender).getPlayer());
        return true;
    }
}
