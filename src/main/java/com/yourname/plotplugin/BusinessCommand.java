package com.yourname.plotplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.yourname.plotplugin.BusinessManager; // Add this import

public class BusinessCommand implements CommandExecutor {
    private final BusinessManager businessManager; // Add this line

    public BusinessCommand(BusinessManager businessManager) { // Modify constructor
        this.businessManager = businessManager; // Initialize the business manager
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 2) {
            player.sendMessage("Usage: /business <businessName> <initialBalance>");
            return true;
        }

        String businessName = args[0];
        double initialBalance;

        try {
            initialBalance = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            player.sendMessage("Please enter a valid initial balance.");
            return true;
        }

        // Create the business
        businessManager.createBusiness(businessName, initialBalance);
        player.sendMessage("Business " + businessName + " created with an initial balance of $" + initialBalance + ".");
        return true;
    }
}