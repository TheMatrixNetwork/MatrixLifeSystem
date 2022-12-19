package org.matrixnetwork.matrixlifesystem;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import net.milkbowl.vault.economy.Economy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

public abstract class TestBase {

    protected ServerMock server;
    protected MatrixLifeSystem plugin;
    protected Economy economy;

    @BeforeEach
    public void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(MatrixLifeSystem.class);
        mockVaultEconomy();
    }

    private void mockVaultEconomy() {
        economy = mock(Economy.class);
        plugin.setEcon(economy);
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }
}
