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

    // Getters
    public String getOwnerName() {
        return ownerName;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getStartZ() {
        return startZ;
    }

    public int getSize() {
        return size;
    }

    // Setters
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public void setStartZ(int startZ) {
        this.startZ = startZ;
    }

    public void setSize(int size) {
        this.size = size;
    }
}