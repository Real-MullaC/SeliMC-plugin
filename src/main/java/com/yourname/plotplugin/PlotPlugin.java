package com.yourname.plotplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class PlotPlugin extends JavaPlugin {
    private PlotManager plotManager;
    private EconomyManager economyManager; // Correctly initialize EconomyManager
    private BusinessManager businessManager; // Correctly initialize BusinessManager

    @Override
    public void onEnable() {
        saveDefaultConfig();
        
        plotManager = new PlotManager(this); // Pass the PlotPlugin instance

        // Initialize the economy manager
        economyManager = new EconomyManager(); // Correctly initialize EconomyManager
        businessManager = new BusinessManager(); // Correctly initialize BusinessManager

        // Register commands
        getCommand("work").setExecutor(new WorkCommand(economyManager));
        getCommand("business").setExecutor(new BusinessCommand(businessManager));
        getCommand("pay").setExecutor(new PayCommand(economyManager, businessManager)); // Pass both instances
        getCommand("createplot").setExecutor(new CreatePlotCommand(this));
        getCommand("buyplot").setExecutor(new BuyPlotCommand(this));
        getCommand("addeditor").setExecutor(new AddEditorCommand(this));
        getCommand("towntp").setExecutor(new TownTeleportCommand(this));
        getLogger().info("PlotPlugin has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("PlotPlugin has been disabled!");
    }

    public PlotManager getPlotManager() {
        return plotManager;
    }

    public BusinessManager getBusinessManager() {
        return businessManager;
    }
}