package org.matrixnetwork.matrixlifesystem.commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.matrixnetwork.matrixlifesystem.MatrixLifeSystem;
import org.matrixnetwork.matrixlifesystem.TestBase;
import org.matrixnetwork.matrixlifesystem.entity.PlayerData;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class LifeSystemCommandsTests extends TestBase {

    private PlayerMock player;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        player = server.addPlayer();
    }

    @Test
    void test_default() {
        player.performCommand("livesystem info");

        int lives = PlayerData.getPlayerData(player.getUniqueId().toString()).getLives();
        assertThat(player.nextMessage()).contains(lives + " lives");
    }

    @Test
    void test_buy_life_ok() {
        when(economy.getBalance(player)).thenReturn(400D);
        int lives = PlayerData.getPlayerData(player.getUniqueId().toString()).getLives();

        player.performCommand("livesystem advance");
        lives++;

        assertThat(player.nextMessage()).contains(lives + " lives");

        player.performCommand("livesystem info");
        assertThat(player.nextMessage()).contains("You have " + lives + " lives.");
    }

    @Test
    void test_buy_life_no_money() {
        when(economy.getBalance(player)).thenReturn(100D);

        player.performCommand("livesystem advance");

        assertThat(player.nextMessage()).contains("Sorry! You do not have " +
                "" + MatrixLifeSystem.instance().getConfig().getDouble("advance-cost") + " $.");
    }
}
