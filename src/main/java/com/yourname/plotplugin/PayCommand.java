package com.yourname.plotplugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.yourname.plotplugin.EconomyManager; // Add this import
import com.yourname.plotplugin.BusinessManager; // Add this import

public class PayCommand implements CommandExecutor {
    private final EconomyManager economyManager;
    private final BusinessManager businessManager; // Add BusinessManager

    // Constructor that accepts both EconomyManager and BusinessManager
    public PayCommand(EconomyManager economyManager, BusinessManager businessManager) {
        this.economyManager = economyManager; // Initialize the instance
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
            payer.sendMessage("Usage: /pay <player> <amount>");
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
            economyManager.pay(payer, receiver, amount); // Call the pay method
            return true;
        }

        payer.sendMessage("Player not found or not online.");
        return true;
    }
}