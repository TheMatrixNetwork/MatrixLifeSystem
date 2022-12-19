package org.matrixnetwork.matrixlifesystem.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.MessageType;
import co.aikar.commands.annotation.*;
import co.aikar.locales.MessageKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.matrixnetwork.matrixlifesystem.MatrixLifeSystem;
import org.matrixnetwork.matrixlifesystem.entity.PlayerData;

import static org.matrixnetwork.matrixlifesystem.Constants.ACF_BASE_KEY;

@CommandAlias("livesystem")
public class LifeSystemCommands extends BaseCommand {
    private final MatrixLifeSystem plugin = MatrixLifeSystem.instance();

    // see https://github.com/aikar/commands/wiki/Locales
    static MessageKey key(String key) {
        return MessageKey.of(ACF_BASE_KEY + "." + key);
    }

    // see https://github.com/aikar/commands/wiki/Command-Help
    @HelpCommand
    @Subcommand("help")
    public void showHelp(CommandSender sender, CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("info|i")
    @CommandAlias("info")
    @Description("{@@commands.descriptions.info}")
    public void info(@Flags("self") Player player) {
        success("info",
                "{lives}", String.valueOf(PlayerData.getPlayerData(player
                        .getUniqueId().toString()).getLives())
        );
    }

    @Subcommand("advance|a")
    @CommandAlias("advance")
    @Description("{@@commands.descriptions.info}")
    public void advance(@Flags("self") Player player) {
        if(plugin.getEcon().getBalance(player) > getAdvanceCosts()) {
            plugin.getEcon().withdrawPlayer(player, getAdvanceCosts());

            PlayerData.advance(PlayerData.getPlayerData(player.getUniqueId().toString()));

            success("advance.success",
                    "{lives}", String.valueOf(PlayerData.getPlayerData(player
                            .getUniqueId().toString()).getLives())
            );
        }
        else {
            error("advance.ne-money",
                    "{cost}", String.valueOf(getAdvanceCosts())
            );
        }
    }

    private double getAdvanceCosts() {
        return plugin.getConfig().getDouble("advance-cost");
    }

    private void success(String key, String... replacements) {
        getCurrentCommandIssuer().sendMessage(MessageType.INFO, key(key), replacements);
    }

    private void error(String key, String... replacements) {
        getCurrentCommandIssuer().sendMessage(MessageType.ERROR, key(key), replacements);
    }
}
