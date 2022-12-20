package org.matrixnetwork.matrixlifesystem;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;
import org.matrixnetwork.matrixlifesystem.database.SessionFactoryMaker;
import org.matrixnetwork.matrixlifesystem.entity.PlayerData;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.UUID;

public class TemplatePluginTests extends TestBase {

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
    public void testMinusEnoughLifes() {
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
