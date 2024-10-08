package com.yourname.plotplugin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.Location; // Ensure this import is present
import org.bukkit.World; // Add this import if you're using World class

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yourname.plotplugin.PlotData; // Add this import if needed

public class PlotManager {
    private final PlotPlugin plugin;
    private final File plotDataFile;
    private FileConfiguration plotData;
    private final Map<String, PlotData> plots = new HashMap<>(); // plotID -> PlotData

    public PlotManager(PlotPlugin plugin) {
        this.plugin = plugin;
        this.plotDataFile = new File(plugin.getDataFolder(), "plotdata.yml");
        loadPlotData();
    }

    private void loadPlotData() {
        if (!plotDataFile.exists()) {
            plugin.saveResource("plotdata.yml", false);
        }
        plotData = YamlConfiguration.loadConfiguration(plotDataFile);
    }

    public void savePlotData() {
        try {
            plotData.save(plotDataFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save plot data: " + e.getMessage());
        }
    }

    public boolean isPlotOwner(String plotID, String playerName) {
        if (plotID == null || playerName == null) return false;
        PlotData plotData = plots.get(plotID);
        if (plotData != null) {
            return plotData.getOwnerName().equals(playerName);
        }
        return false;
    }

    public void setPlotOwner(String plotID, String playerName) {
        if (plotID == null || playerName == null) return;
        PlotData plotData = new PlotData(playerName, 0, 0, 0, 0);
        plots.put(plotID, plotData);
        plotData.setOwnerName(playerName); // Use the setter method
        savePlotData();
    }

    public void addPlot(String plotID, String ownerName, int startX, int startY, int startZ, int size) {
        PlotData plotData = new PlotData(ownerName, startX, startY, startZ, size);
        plots.put(plotID, plotData);
    }

    public void addEditor(String plotID, String editorName) {
        if (plotID == null || editorName == null) return;
        List<String> editors = plotData.getStringList(plotID + ".editors");
        if (!editors.contains(editorName)) {
            editors.add(editorName);
            plotData.set(plotID + ".editors", editors);
            savePlotData();
        }
    }

    public boolean isEditor(String plotID, String playerName) {
        List<String> editors = plotData.getStringList(plotID + ".editors");
        return editors.contains(playerName);
    }

    public List<String> getEditors(String plotID) {
        return plotData.getStringList(plotID + ".editors");
    }

    public void setPlotLocation(String plotID, Location location) {
        plotData.set(plotID + ".world", location.getWorld().getName());
        plotData.set(plotID + ".x", location.getX());
        plotData.set(plotID + ".y", location.getY());
        plotData.set(plotID + ".z", location.getZ());
        savePlotData();
    }

    public Location getPlotLocation(String plotID) {
        if (!plotData.contains(plotID + ".world")) {
            return null;
        }

        World world = plugin.getServer().getWorld(plotData.getString(plotID + ".world"));
        double x = plotData.getDouble(plotID + ".x");
        double y = plotData.getDouble(plotID + ".y");
        double z = plotData.getDouble(plotID + ".z");

        return new Location(world, x, y, z);
    }

    public boolean plotExists(String plotID) {
        return plots.containsKey(plotID);
    }

    public String getPlotOwner(String plotID) {
        PlotData plotData = plots.get(plotID);
        if (plotData != null) {
            return plotData.getOwnerName(); // Ensure you are returning a String
        }
        return null; // Or handle the case where the plot does not exist
    }
}