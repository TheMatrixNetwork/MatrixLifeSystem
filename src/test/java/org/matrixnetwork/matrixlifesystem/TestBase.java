package org.matrixnetwork.matrixlifesystem;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import net.milkbowl.vault.economy.Economy;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.matrixnetwork.matrixlifesystem.database.SessionFactoryMaker;
import org.matrixnetwork.matrixlifesystem.entity.PlayerData;
import org.matrixnetwork.matrixlifesystem.util.PluginMessageUtil;

import static org.mockito.Mockito.mock;

public abstract class TestBase {

    protected ServerMock server;
    protected MatrixLifeSystem plugin;
    protected Economy economy;
    protected PluginMessageUtil pluginMessageUtil;

    @BeforeEach
    public void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(MatrixLifeSystem.class);
        pluginMessageUtil = new PluginMessageUtil();
        server.getMessenger().registerIncomingPluginChannel(plugin, "BungeeCord", pluginMessageUtil);
        mockVaultEconomy();
    }

    protected void mockVaultEconomy() {
        economy = mock(Economy.class);
        plugin.setEcon(economy);
    }

    public static PlayerData addPlayerdData(String uuid, int lives) {
        PlayerData pd;

        SessionFactory sessionFactory = SessionFactoryMaker.getFactory();
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

    @AfterEach
    public void tearDown() {
        server.getMessenger().unregisterIncomingPluginChannel(plugin);
        MockBukkit.unmock();
    }
}
