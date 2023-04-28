package org.matrixnetwork.matrixlifesystem.listener;

import co.aikar.locales.MessageKey;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.format.TextColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.matrixnetwork.matrixlifesystem.Constants;
import org.matrixnetwork.matrixlifesystem.MatrixLifeSystem;
import org.matrixnetwork.matrixlifesystem.entity.PlayerData;
import org.matrixnetwork.matrixlifesystem.util.MLSUtil;

public class LoseLifeDeathListener implements Listener {
    private final MatrixLifeSystem plugin = MatrixLifeSystem.instance();

    @EventHandler
    public void onPlayerDeath(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player player) {
            if(player.getHealth() - event.getDamage() >= 1) {
                return;
            }
            String uuid = player.getUniqueId().toString();
            PlayerData pd = PlayerData.getPlayerData(uuid);

            PlayerData.removeLife(PlayerData.getPlayerData(uuid));

            event.setCancelled(true);
            player.setHealth(player.getMaxHealth());
            player.teleport(player.getWorld().getSpawnLocation());
            player.sendMessage(ChatColor.RED + "You died! Lives left: " + pd.getLives());

            if(pd.getLives() < plugin.getMinLifes() && !player.hasPermission(Constants.ACF_ADMIN_PERMISSION)) {
                MLSUtil.movePlayer(player);
            }
        }
    }
}
