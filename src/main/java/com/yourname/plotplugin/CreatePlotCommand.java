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
        Location playerLocation = player.getLocation();

        // Create the 22x22 plot
        for (int x = 0; x < 22; x++) {
            for (int z = 0; z < 22; z++) {
                Block block = playerLocation.getWorld().getBlockAt(playerLocation.getBlockX() + x, playerLocation.getBlockY() - 1, playerLocation.getBlockZ() + z);
                block.setType(Material.GRASS_BLOCK);
            }
        }

        // Place the sign
        Block signBlock = playerLocation.getWorld().getBlockAt(playerLocation.getBlockX(), playerLocation.getBlockY(), playerLocation.getBlockZ());
        signBlock.setType(Material.OAK_SIGN);
        Sign sign = (Sign) signBlock.getState();
        sign.setLine(0, "Plot ID: " + plotID);
        sign.setLine(1, "FOR SALE");
        sign.update();

        // After creating the plot and sign
        plugin.getPlotManager().setPlotOwner(plotID, "FOR SALE");
        plugin.getPlotManager().setPlotLocation(plotID, playerLocation);
        plugin.getDiscordManager().sendPlotCreationMessage(plotID);

        player.sendMessage("Plot " + plotID + " has been created successfully!");
        return true;
    }
}