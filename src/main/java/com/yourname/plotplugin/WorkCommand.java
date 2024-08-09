package com.yourname.plotplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorkCommand implements CommandExecutor {
    private final EconomyManager economyManager;

    public WorkCommand(EconomyManager economyManager) {
        this.economyManager = economyManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        // Simulate working and earning money
        double earnedAmount = 10.0; // Amount earned per work command
        economyManager.addMoney(player, earnedAmount);
        player.sendMessage("You worked and earned $" + earnedAmount + "! Your new balance is $" + economyManager.getBalance(player) + ".");
        return true;
    }
}
