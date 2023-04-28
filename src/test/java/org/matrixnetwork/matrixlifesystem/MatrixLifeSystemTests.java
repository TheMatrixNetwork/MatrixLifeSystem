package org.matrixnetwork.matrixlifesystem;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Disabled
public class MatrixLifeSystemTests extends TestBase {

    @Test
    public void testEnoughLifes() {
        PlayerMock player = new PlayerMock(server, "TestPlayer", UUID.randomUUID());
        addPlayerdData(player.getUniqueId().toString(), 7);
        server.addPlayer(player);
        assert server.getOnlinePlayers().contains(player);
    }

    @Test
    public void testNotEnoughLifes() {
        PlayerMock player = new PlayerMock(server, "TestPlayer", UUID.randomUUID());
        addPlayerdData(player.getUniqueId().toString(), 0);
        server.addPlayer(player);
        assert !server.getOnlinePlayers().contains(player);
    }

    @Test
    public void testMinusEnoughLifes() throws InterruptedException {
        PlayerMock player = new PlayerMock(server, "TestPlayer", UUID.randomUUID());
        addPlayerdData(player.getUniqueId().toString(), -1);
        server.addPlayer(player);
        server.getPluginManager().assertEventFired(PlayerJoinEvent.class);
        assert !server.getOnlinePlayers().contains(player);
    }

    @Test
    public void testExactLifes() {
        PlayerMock player = new PlayerMock(server, "TestPlayer", UUID.randomUUID());
        addPlayerdData(player.getUniqueId().toString(), 1);
        server.addPlayer(player);
        assert server.getOnlinePlayers().contains(player);
    }

    @Test
    public void testWithPermissions() {
        PlayerMock player = new PlayerMock(server, "TestPlayer", UUID.randomUUID());
        player.addAttachment(plugin, Constants.ACF_ADMIN_PERMISSION, true);
        server.addPlayer(player);
        assert server.getOnlinePlayers().contains(player);
    }
}
