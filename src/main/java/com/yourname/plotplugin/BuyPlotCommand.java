package com.yourname.plotplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuyPlotCommand implements CommandExecutor {
    private final PlotPlugin plugin;

    public BuyPlotCommand(PlotPlugin plugin) {
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
            player.sendMessage("Usage: /buyplot <plotID>");
            return true;
        }

        String plotID = args[0];
        
        // Find the sign with the matching plot ID
        Sign plotSign = findPlotSign(plotID);
        
        if (plotSign == null) {
            player.sendMessage("Plot " + plotID + " not found or not for sale.");
            return true;
        }

        // Check if the plot is for sale
        if (!plotSign.getLine(1).equals("FOR SALE")) {
            player.sendMessage("Plot " + plotID + " is not for sale.");
            return true;
        }

        // Update the sign to show the new owner
        plotSign.setLine(1, "Owner:");
        plotSign.setLine(2, player.getName());
        plotSign.update();


        player.sendMessage("Congratulations! You are now the owner of plot " + plotID + ".");
        return true;
    }

    private Sign findPlotSign(String plotID) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            Location playerLoc = onlinePlayer.getLocation();
            int radius = 100; // Adjust this value to change the search radius

            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        Block block = playerLoc.getWorld().getBlockAt(playerLoc.getBlockX() + x, playerLoc.getBlockY() + y, playerLoc.getBlockZ() + z);
                        if (block.getState() instanceof Sign) {
                            Sign sign = (Sign) block.getState();
                            if (sign.getLine(0).equals("Plot ID: " + plotID)) {
                                return sign;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}