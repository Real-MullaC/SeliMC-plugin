package com.yourname.plotplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class PlotPlugin extends JavaPlugin {
    private PlotManager plotManager;
    private EconomyManager economyManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        
        plotManager = new PlotManager(this); // Pass the PlotPlugin instance

        // Initialize the economy manager
        economyManager = new EconomyManager();

        // Register commands
        getCommand("work").setExecutor(new WorkCommand(economyManager));
        getCommand("business").setExecutor(new BusinessCommand(economyManager));
        getCommand("pay").setExecutor(new PayCommand(economyManager));
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

    public EconomyManager getEconomyManager() {
        return economyManager;
    }
}