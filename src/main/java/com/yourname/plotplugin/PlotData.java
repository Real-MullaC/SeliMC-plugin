package com.yourname.plotplugin;

public class PlotData {
    private String ownerName;
    private int startX;
    private int startY;
    private int startZ;
    private int size;

    public PlotData(String ownerName, int startX, int startY, int startZ, int size) {
        this.ownerName = ownerName;
        this.startX = startX;
        this.startY = startY;
        this.startZ = startZ;
        this.size = size;
    }

    public String getOwnerName() {
        return ownerName;
    }

    // Getters and setters...
}