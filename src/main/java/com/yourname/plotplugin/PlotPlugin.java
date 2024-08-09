package com.yourname.plotplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class PlotPlugin extends JavaPlugin {
    private DiscordManager discordManager;
    private PlotManager plotManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        String token = getConfig().getString("discord.token");
        String channelId = getConfig().getString("discord.channel_id");

        try {
            discordManager = new DiscordManager(token, channelId);
        } catch (Exception e) {
            getLogger().severe("Failed to initialize DiscordManager: " + e.getMessage());
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        plotManager = new PlotManager(this); // Pass the PlotPlugin instance

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

    public DiscordManager getDiscordManager() {
        return discordManager;
    }

    public PlotManager getPlotManager() {
        return plotManager;
    }
}