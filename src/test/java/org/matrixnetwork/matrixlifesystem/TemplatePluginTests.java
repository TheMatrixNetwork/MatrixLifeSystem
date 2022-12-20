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


    public static PlayerData addPlayerdData(String uuid, int lives) {
        PlayerData pd;

        SessionFactory sessionFactory = SessionFactoryMaker.getInstance().getFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            pd = new PlayerData(uuid, lives);
            session.merge(pd);
            tx.commit();
        } catch (Exception ignored) {
            pd = null;
        }

        return pd;
    }
}
