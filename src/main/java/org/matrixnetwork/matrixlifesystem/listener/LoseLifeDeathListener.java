package org.matrixnetwork.matrixlifesystem.listener;

import co.aikar.locales.MessageKey;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.matrixnetwork.matrixlifesystem.Constants;
import org.matrixnetwork.matrixlifesystem.MatrixLifeSystem;
import org.matrixnetwork.matrixlifesystem.entity.PlayerData;
import org.matrixnetwork.matrixlifesystem.util.MLSUtil;

public class LoseLifeDeathListener implements Listener {
    private final MatrixLifeSystem plugin = MatrixLifeSystem.instance();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if(event.getPlayer().hasPermission(Constants.ACF_ADMIN_PERMISSION)) {
            return;
        }

        PlayerData.removeLife(PlayerData.getPlayerData(event.getPlayer().getUniqueId().toString()));

        if(PlayerData.getPlayerData(event.getPlayer().getUniqueId().toString()).getLives() < plugin.getMinLifes()) {
            MLSUtil.movePlayer(event.getPlayer());
           /* event.getPlayer()
                    .kick(Component.text(plugin.getCommandManager()
                            .getLocales()
                            .getMessage(null, MessageKey.of("kick.message")
                            )));*/
        }
    }
}
