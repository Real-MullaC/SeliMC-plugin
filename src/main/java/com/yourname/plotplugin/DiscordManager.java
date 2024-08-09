package com.yourname.plotplugin;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.HashMap;
import java.util.Map;

public class DiscordManager {
    private final JDA jda;
    private final String channelId;
    private final Map<String, String> plotMessages;

    public DiscordManager(String token, String channelId) throws InterruptedException {
        this.jda = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build()
                .awaitReady();
        this.channelId = channelId;
        this.plotMessages = new HashMap<>();
    }

    public void sendPlotCreationMessage(String plotId) {
        TextChannel channel = jda.getTextChannelById(channelId);
        if (channel != null) {
            channel.sendMessage("New plot created: " + plotId + " (FOR SALE)")
                    .queue(message -> plotMessages.put(plotId, message.getId()));
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
