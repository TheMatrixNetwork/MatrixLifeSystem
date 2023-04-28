package org.matrixnetwork.matrixlifesystem.util;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;

public class PluginMessageUtil implements PluginMessageListener {
    private String latestMessage = "";

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, @NotNull byte[] message) {
        this.latestMessage = new String(message, StandardCharsets.UTF_8);
    }

    public String getLatestMessage() {
        return latestMessage;
    }
}
