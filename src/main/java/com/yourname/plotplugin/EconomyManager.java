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
        loadBalances(); // Load balances from file
    }

    public void addMoney(Player player, double amount) {
        UUID playerId = player.getUniqueId();
        double newBalance = getBalance(player) + amount; // Calculate new balance
        balances.put(playerId, newBalance); // Update balance
        System.out.println("Added " + amount + " to " + player.getName() + ". New balance: " + newBalance);
        saveBalances(); // Save balances after adding money
    }

    public void subtractMoney(Player player, double amount) {
        UUID playerId = player.getUniqueId();
        balances.put(playerId, getBalance(player) - amount); // Subtract from balance
        saveBalances(); // Save balances after subtracting money
    }

    public double getBalance(Player player) {
        return balances.getOrDefault(player.getUniqueId(), 0.0); // Return balance or 0 if not found
    }

    public void saveBalances() {
        Yaml yaml = new Yaml();
        Map<String, Double> stringBalances = new HashMap<>();
        
        // Convert UUIDs to Strings for saving
        for (Map.Entry<UUID, Double> entry : balances.entrySet()) {
            stringBalances.put(entry.getKey().toString(), entry.getValue());
        }

        try (FileWriter writer = new FileWriter(dataFile)) {
            yaml.dump(stringBalances, writer); // Save balances to file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadBalances() {
        if (dataFile.exists()) {
            Yaml yaml = new Yaml();
            try (InputStream inputStream = Files.newInputStream(dataFile.toPath())) {
                Map<String, Double> loadedBalances = yaml.load(inputStream);
                if (loadedBalances != null) {
                    // Convert Strings back to UUIDs
                    for (Map.Entry<String, Double> entry : loadedBalances.entrySet()) {
                        balances.put(UUID.fromString(entry.getKey()), entry.getValue());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void pay(Player payer, Player receiver, double amount) {
        UUID payerId = payer.getUniqueId();
        UUID receiverId = receiver.getUniqueId();

        // Check if the payer has enough balance
        if (getBalance(payer) >= amount) {
            subtractMoney(payer, amount); // Subtract from payer
            addMoney(receiver, amount); // Add to receiver
            payer.sendMessage("You paid $" + amount + " to " + receiver.getName() + ".");
            receiver.sendMessage("You received $" + amount + " from " + payer.getName() + ".");
        } else {
            payer.sendMessage("You do not have enough balance to make this payment.");
        }
    }
}