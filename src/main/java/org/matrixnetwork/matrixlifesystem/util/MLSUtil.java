package org.matrixnetwork.matrixlifesystem.util;

import co.aikar.locales.MessageKey;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.matrixnetwork.matrixlifesystem.MatrixLifeSystem;

public class MLSUtil {
    private static MatrixLifeSystem plugin = MatrixLifeSystem.instance();
    private static String fallbackServer = MatrixLifeSystem.instance().getFallbackServer();

    public static void movePlayer(Player player) {
        plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                if(!plugin.getServer().getName().equalsIgnoreCase(fallbackServer)) {
                    ByteArrayDataOutput out = ByteStreams.newDataOutput();
                    out.writeUTF("Message");
                    out.writeUTF(Component.text(plugin.getCommandManager()
                            .getLocales()
                            .getMessage(null, MessageKey.of("kick.message")
                            )).toString());
                    player.sendPluginMessage(MatrixLifeSystem.instance(), "BungeeCord", out.toByteArray());

                    out = ByteStreams.newDataOutput();
                    out.writeUTF("Connect");
                    out.writeUTF(MatrixLifeSystem.instance().getFallbackServer());
                    player.sendPluginMessage(MatrixLifeSystem.instance(), "BungeeCord", out.toByteArray());
                }
            }
        }, 10L);
    }
}
