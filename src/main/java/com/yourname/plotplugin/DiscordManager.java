package com.yourname.plotplugin;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.TextChannel; // Added this import
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.HashMap;
import java.util.Map;

public class DiscordManager {
    private final JDA jda;
    private final String channelId;
    private final Map<String, String> plotMessages;

    public DiscordManager(String token, String channelId) throws Exception {
        this.jda = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.GUILD_MESSAGES) // Ensure you have the correct intents
                .build();
        this.jda.awaitReady(); // Wait for JDA to be ready
        this.channelId = channelId;
        this.plotMessages = new HashMap<>();
    }

    public void sendPlotCreationMessage(String plotId) {
        if (channelId == null || plotId == null) return; // {{ edit_1 }}
        
        TextChannel channel = jda.getTextChannelById(channelId);
        if (channel == null) {
            System.err.println("Channel not found: " + channelId); // {{ edit_2 }}
            return;
        }

        try {
            channel.sendMessage("New plot created: " + plotId + " (FOR SALE)")
                    .queue(message -> plotMessages.put(plotId, message.getId()));
        } catch (Exception e) {
            System.err.println("Failed to send message to Discord: " + e.getMessage()); // {{ edit_4 }}
        }
    }

    public void updatePlotPurchaseMessage(String plotId, String ownerName) {
        TextChannel channel = jda.getTextChannelById(channelId);
        if (channel != null && plotMessages.containsKey(plotId)) {
            String messageId = plotMessages.get(plotId);
            channel.editMessageById(messageId, "Plot " + plotId + " purchased by " + ownerName).queue();
        }
    }
}