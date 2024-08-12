package com.yourname.plotplugin;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class PlotPlugin extends JavaPlugin {
    private EconomyManager economyManager;
    private BusinessManager businessManager;

    @Override
    public void onEnable() {
        File dataFile = new File(getDataFolder(), "balances.json"); // Create a JSON file for balances
        economyManager = new EconomyManager(dataFile); // Initialize EconomyManager
        businessManager = new BusinessManager(); // Initialize BusinessManager

        // Register commands
        getCommand("work").setExecutor(new WorkCommand(economyManager));
        getCommand("business").setExecutor(new BusinessCommand(businessManager));
        getCommand("pay").setExecutor(new PayCommand(economyManager, businessManager)); // Pass both managers
        getCommand("balance").setExecutor(new BalanceCommand(economyManager));
        getCommand("update").setExecutor(new UpdateCommand(getFile())); // Register update command

        // Ensure Keep Inventory is enabled
        enableKeepInventory();
    }

    private void enableKeepInventory() {
        Bukkit.getServer().getWorlds().forEach(world -> {
            world.setGameRule(GameRule.KEEP_INVENTORY, true);
        });
        getLogger().info("Keep Inventory has been enabled for all worlds.");
    }
}