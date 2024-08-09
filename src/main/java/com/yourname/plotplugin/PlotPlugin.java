package com.yourname.plotplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class PlotPlugin extends JavaPlugin {
    private PlotManager plotManager;
    private EconomyManager economyManager;
    private BusinessManager businessManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        
        plotManager = new PlotManager(this);

        economyManager = new EconomyManager();
        businessManager = new BusinessManager();

        // Register commands
        getCommand("work").setExecutor(new WorkCommand(economyManager));
        getCommand("business").setExecutor(new BusinessCommand(businessManager));
        getCommand("pay").setExecutor(new PayCommand(economyManager, businessManager));
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