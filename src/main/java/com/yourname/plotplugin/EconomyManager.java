package com.yourname.plotplugin;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class EconomyManager {
    private final HashMap<UUID, Double> balances = new HashMap<>();

    public void addMoney(Player player, double amount) {
        UUID playerId = player.getUniqueId();
        balances.put(playerId, getBalance(player) + amount);
    }

    public void subtractMoney(Player player, double amount) {
        UUID playerId = player.getUniqueId();
        balances.put(playerId, getBalance(player) - amount);
    }

    public double getBalance(Player player) {
        return balances.getOrDefault(player.getUniqueId(), 0.0);
    }

    public boolean pay(Player sender, Player receiver, double amount) {
        if (getBalance(sender) < amount) {
            sender.sendMessage("You do not have enough money to make this payment.");
            return false;
        }
        subtractMoney(sender, amount);
        addMoney(receiver, amount);
        sender.sendMessage("You paid $" + amount + " to " + receiver.getName() + ".");
        receiver.sendMessage("You received $" + amount + " from " + sender.getName() + ".");
        return true;
    }
}
