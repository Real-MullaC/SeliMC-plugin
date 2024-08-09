package com.yourname.plotplugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.yourname.plotplugin.BusinessManager; // Add this import

public class PayCommand implements CommandExecutor {
    private final BusinessManager businessManager;

    public PayCommand(BusinessManager businessManager) {
        this.businessManager = businessManager; // Initialize the instance
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player payer = (Player) sender;

        if (args.length != 2) {
            payer.sendMessage("Usage: /pay <player|business> <amount>");
            return true;
        }

        String targetName = args[0];
        double amount;

        try {
            amount = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            payer.sendMessage("Please enter a valid amount.");
            return true;
        }

        // Check if the target is a player
        Player receiver = Bukkit.getPlayer(targetName);
        if (receiver != null && receiver.isOnline()) {
            return economyManager.pay(payer, receiver, amount);
        }

        // If the target is not a player, check if it's a business
        // Assuming you have a method to check if a business exists
        if (businessManager.businessExists(targetName)) {
            // Handle payment to a business (you can implement this logic)
            payer.sendMessage("Payment to businesses is not yet implemented.");
            return true;
        }

        payer.sendMessage("Player or business not found.");
        return true;
    }
}