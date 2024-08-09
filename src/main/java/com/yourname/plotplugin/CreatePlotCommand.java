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

        Player owner = (Player) sender;

        if (args.length != 1) {
            owner.sendMessage("Usage: /createplot <plotID>");
            return true;
        }

        String plotID = args[0];

        // Check if the plot ID already exists
        if (plugin.getPlotManager().plotExists(plotID)) {
            owner.sendMessage("A plot with this ID already exists.");
            return true;
        }

        // Define the plot area (10x10 area around the player's location)
        Location location = owner.getLocation();
        int plotSize = 22; // Size of the plot
        int startX = location.getBlockX() - plotSize / 2;
        int startZ = location.getBlockZ() - plotSize / 2;

        // Create a border for the plot
        for (int x = startX; x < startX + plotSize; x++) {
            for (int z = startZ; z < startZ + plotSize; z++) {
                // Set the border blocks (e.g., using stone)
                owner.getWorld().getBlockAt(x, location.getBlockY(), z).setType(Material.STONE);
            }
        }

        // Place a sign at the plot's location
        Block signBlock = owner.getWorld().getBlockAt(startX, location.getBlockY() + 1, startZ);
        signBlock.setType(Material.OAK_SIGN); // Change to the desired sign type
        Sign sign = (Sign) signBlock.getState();
        sign.setLine(0, "Plot ID: " + plotID);
        sign.setLine(1, "Owner: " + owner.getName());
        sign.setLine(2, "For Sale");
        sign.setLine(3, "$100"); // Set the price or any other information
        sign.update();

        // Save the plot data
        plugin.getPlotManager().addPlot(plotID, owner.getName(), startX, location.getBlockY(), startZ, plotSize);

        owner.sendMessage("Plot " + plotID + " has been created successfully and is for sale.");
        return true;
    }
}