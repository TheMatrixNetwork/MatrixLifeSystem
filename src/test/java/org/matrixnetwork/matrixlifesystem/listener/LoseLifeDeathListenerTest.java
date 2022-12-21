package org.matrixnetwork.matrixlifesystem.listener;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.matrixnetwork.matrixlifesystem.Constants;
import org.matrixnetwork.matrixlifesystem.MatrixLifeSystem;
import org.matrixnetwork.matrixlifesystem.TestBase;
import org.matrixnetwork.matrixlifesystem.entity.PlayerData;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class LoseLifeDeathListenerTest extends TestBase {
    private PlayerMock player;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        player = new PlayerMock(server, "TestPlayer", UUID.randomUUID());
        addPlayerdData(player.getUniqueId().toString(), 1);
        server.addPlayer(player);
    }

    @Test
    void test_default() {
        int lives = PlayerData.getPlayerData(player.getUniqueId().toString()).getLives();
        player.damage(22);

        assertThat(PlayerData.getPlayerData(player.getUniqueId().toString()).getLives()).isEqualTo(lives-1);
        assert !server.getOnlinePlayers().contains(player);
    }

    @Test
    void test_with_permission() {
        player.addAttachment(plugin, Constants.ACF_ADMIN_PERMISSION, true);
        int lives = PlayerData.getPlayerData(player.getUniqueId().toString()).getLives();
        player.damage(22);

        assertThat(PlayerData.getPlayerData(player.getUniqueId().toString()).getLives()).isEqualTo(lives);
        assert server.getOnlinePlayers().contains(player);
    }
}