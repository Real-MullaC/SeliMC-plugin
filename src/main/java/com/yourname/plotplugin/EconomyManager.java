package com.yourname.plotplugin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EconomyManager {
    private final HashMap<UUID, Double> balances = new HashMap<>();
    private final File dataFile;
    private final Gson gson = new Gson(); // Create a Gson instance

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
        try (FileWriter writer = new FileWriter(dataFile)) {
            gson.toJson(balances, writer); // Save balances to JSON file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadBalances() {
        if (dataFile.exists()) {
            try (FileReader reader = new FileReader(dataFile)) {
                Type type = new TypeToken<HashMap<UUID, Double>>() {}.getType();
                Map<UUID, Double> loadedBalances = gson.fromJson(reader, type);
                if (loadedBalances != null) {
                    balances.putAll(loadedBalances); // Load balances from JSON file
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