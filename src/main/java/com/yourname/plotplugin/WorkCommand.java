package com.yourname.plotplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class WorkCommand implements CommandExecutor {
    private final EconomyManager economyManager;
    private final HashMap<Player, Long> cooldowns = new HashMap<>(); // Store cooldowns

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

        // Check cooldown
        if (cooldowns.containsKey(player)) {
            long lastUsed = cooldowns.get(player);
            long cooldownTime = TimeUnit.MINUTES.toMillis(1); // 1 minute cooldown
            if (System.currentTimeMillis() - lastUsed < cooldownTime) {
                long remainingTime = cooldownTime - (System.currentTimeMillis() - lastUsed);
                player.sendMessage("You must wait " + (remainingTime / 1000) + " seconds before using this command again.");
                return true;
            }
        }

        // Simulate working and earning money
        double earnedAmount = 10.0; // Amount earned per work command
        economyManager.addMoney(player, earnedAmount);
        player.sendMessage("You worked and earned $" + earnedAmount + "! Your new balance is $" + economyManager.getBalance(player) + ".");

        // Update cooldown
        cooldowns.put(player, System.currentTimeMillis());
        return true;
    }
}