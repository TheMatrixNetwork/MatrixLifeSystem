package org.matrixnetwork.matrixlifesystem.commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.matrixnetwork.matrixlifesystem.Constants;
import org.bukkit.Statistic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.matrixnetwork.matrixlifesystem.TestBase;
import org.matrixnetwork.matrixlifesystem.entity.PlayerData;

import static org.assertj.core.api.Assertions.assertThat;

public class LifeSystemCommandsTests extends TestBase {

    private PlayerMock player;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        player = server.addPlayer();
    }

    @Test
    void info_forSelf_printsOwnPlayerName() {
        player.performCommand("lifesystem info");

        int lifes = PlayerData.getPlayerData(player.getUniqueId().toString()).getLifes();
        assertThat(player.nextMessage()).contains(lifes + " lifes");
    }
}
