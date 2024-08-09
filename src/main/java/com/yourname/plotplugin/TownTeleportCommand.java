package com.yourname.plotplugin;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TownTeleportCommand implements CommandExecutor {
    private final PlotPlugin plugin;

    public TownTeleportCommand(PlotPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage("Usage: /towntp <plotID>");
            return true;
        }

        String plotID = args[0];

        // Get the plot location
        Location plotLocation = plugin.getPlotManager().getPlotLocation(plotID);

        if (plotLocation == null) {
            player.sendMessage("Plot " + plotID + " doesn't exist or hasn't been set up for teleportation.");
            return true;
        }

        // Teleport the player
        player.teleport(plotLocation);
        player.sendMessage("You have been teleported to plot " + plotID + ".");

        return true;
    }
}