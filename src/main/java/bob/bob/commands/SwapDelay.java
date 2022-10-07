package bob.bob.commands;

import bob.bob.Bob;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SwapDelay implements CommandExecutor {

    Bob plugin;
    public SwapDelay(Bob plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Invalid arguments");
            return false;
        }
        if (args.length > 1) {
            sender.sendMessage("Invalid arguments");
            return false;
        }
        int num;
        try {
            num = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid arguments");
            return false;
        }

        if (num < 10){
            sender.sendMessage("Delay cannot be less then 10");
            return false;
        }

        Bukkit.broadcastMessage("New death swap delay: " + args[0]);
        plugin.deathSwapDelay = num;
        return true;
    }
}
