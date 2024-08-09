package com.yourname.plotplugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddEditorCommand implements CommandExecutor {
    private final PlotPlugin plugin;

    public AddEditorCommand(PlotPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player owner = (Player) sender;

        if (args.length != 2) {
            owner.sendMessage("Usage: /addeditor <plotID> <playerName>");
            return true;
        }

        String plotID = args[0];
        String editorName = args[1];

        Player editor = Bukkit.getPlayer(editorName);
        if (editor == null) {
            owner.sendMessage("Player " + editorName + " is not online or doesn't exist.");
            return true;
        }

        // Check if the plot exists and the sender is the owner
        if (!plugin.getPlotManager().isPlotOwner(plotID, owner.getName())) {
            owner.sendMessage("You don't own plot " + plotID + " or it doesn't exist.");
            return true;
        }

        // Add the editor to the plot
        plugin.getPlotManager().addEditor(plotID, editor.getName());
        owner.sendMessage("Added " + editorName + " as an editor to plot " + plotID + ".");
        editor.sendMessage("You have been added as an editor to plot " + plotID + ".");

        return true;
    }
}
