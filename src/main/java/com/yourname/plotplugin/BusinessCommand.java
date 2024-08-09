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

        if (args.length != 1) {
            player.sendMessage("Usage: /business <businessName>");
            return true;
        }

        String businessName = args[0];

        if (businessManager.businessExists(businessName)) {
            player.sendMessage("This business name is already taken.");
            return true;
        }

        businessManager.createBusiness(businessName, 0.0);
        player.sendMessage("You have created a business named " + businessName + "!");
        return true;
    }

    public static boolean businessExists(String businessName) {
        // Check if the business exists in your data structure
        return businessBalances.containsKey(businessName);
    }

    public void payBusiness(String businessName, double amount) {
        businessBalances.put(businessName, businessBalances.getOrDefault(businessName, 0.0) + amount);
    }
}