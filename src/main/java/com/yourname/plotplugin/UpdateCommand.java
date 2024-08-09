package com.yourname.plotplugin;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class UpdateCommand implements CommandExecutor {
    private final String repoUrl = "https://api.github.com/repos/Real_MullaC/SeliMC-Plugin/releases/latest"; // Update with your repo
    private final File pluginFile;

    public UpdateCommand(File pluginFile) {
        this.pluginFile = pluginFile;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(repoUrl);
            HttpResponse response = httpClient.execute(request);

            if (response.getStatusLine().getStatusCode() == 200) {
                // Read the response
                InputStream inputStream = response.getEntity().getContent();
                String jsonResponse = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

                // Parse the JSON to get the download URL
                String downloadUrl = parseDownloadUrl(jsonResponse);
                if (downloadUrl != null) {
                    downloadFile(downloadUrl);
                    player.sendMessage("Plugin updated successfully! Please restart the server.");
                } else {
                    player.sendMessage("Could not find the download URL.");
                }
            } else {
                player.sendMessage("Failed to fetch the latest release.");
            }
        } catch (Exception e) {
            player.sendMessage("An error occurred while updating: " + e.getMessage());
            e.printStackTrace();
        }

        return true;
    }

    private String parseDownloadUrl(String jsonResponse) {
        // Simple JSON parsing to extract the download URL
        // You can use a library like Gson or Jackson for more robust parsing
        String[] lines = jsonResponse.split(",");
        for (String line : lines) {
            if (line.contains("browser_download_url")) {
                return line.split(":")[1].replace("\"", "").trim();
            }
        }
        return null;
    }

    private void downloadFile(String fileUrl) {
        try (InputStream in = new URL(fileUrl).openStream();
             OutputStream out = new FileOutputStream(pluginFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
