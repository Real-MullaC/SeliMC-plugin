package com.yourname.plotplugin;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreatePlotCommand implements CommandExecutor {

    private final PlotPlugin plugin;

    public CreatePlotCommand(PlotPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("plotplugin.createplot")) {
            player.sendMessage("You don't have permission to use this command.");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage("Usage: /createplot <plotID>");
            return true;
        }

        String plotID = args[0];

        if (plotID == null || plotID.isEmpty()) {
            player.sendMessage("Plot ID cannot be null or empty.");
            return true;
        }

        try {
            plugin.getPlotManager().addPlot(plotID, player.getName());
            player.sendMessage("Plot " + plotID + " has been created successfully.");
        } catch (Exception e) {
            player.sendMessage("An error occurred while creating the plot.");
            System.err.println("Error creating plot: " + e.getMessage());
        }

        return true;
    }
}