package com.yourname.plotplugin;

import org.bukkit.entity.Player;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EconomyManager {
    private final HashMap<UUID, Double> balances = new HashMap<>();
    private final File dataFile;

    // Constructor that accepts a File parameter
    public EconomyManager(File dataFile) {
        this.dataFile = dataFile;
        loadBalances();
    }

    public void addMoney(Player player, double amount) {
        UUID playerId = player.getUniqueId();
        balances.put(playerId, getBalance(player) + amount);
        saveBalances(); // Save balances after adding money
    }

    public void subtractMoney(Player player, double amount) {
        UUID playerId = player.getUniqueId();
        balances.put(playerId, getBalance(player) - amount);
        saveBalances(); // Save balances after subtracting money
    }

    public double getBalance(Player player) {
        return balances.getOrDefault(player.getUniqueId(), 0.0);
    }

    public void saveBalances() {
        Yaml yaml = new Yaml();
        try (FileWriter writer = new FileWriter(dataFile)) {
            yaml.dump(balances, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadBalances() {
        if (dataFile.exists()) {
            Yaml yaml = new Yaml();
            try (InputStream inputStream = Files.newInputStream(dataFile.toPath())) {
                Map<UUID, Double> loadedBalances = yaml.load(inputStream);
                if (loadedBalances != null) {
                    balances.putAll(loadedBalances);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}