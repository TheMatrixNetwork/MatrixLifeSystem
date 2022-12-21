package org.matrixnetwork.matrixlifesystem;

import co.aikar.commands.PaperCommandManager;
import co.aikar.locales.MessageKey;
import kr.entree.spigradle.annotations.PluginMain;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.kyori.adventure.text.Component;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.matrixnetwork.matrixlifesystem.commands.LifeSystemCommands;
import org.matrixnetwork.matrixlifesystem.entity.PlayerData;
import org.matrixnetwork.matrixlifesystem.enums.LoseLifeMethod;
import org.matrixnetwork.matrixlifesystem.listener.LoseLifeDeathListener;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@PluginMain
public class MatrixLifeSystem extends JavaPlugin implements Listener {

    @Getter
    @Accessors(fluent = true)
    private static MatrixLifeSystem instance;
    @Getter
    private PaperCommandManager commandManager;
    @Getter
    @Setter(AccessLevel.PACKAGE)
    private Economy econ;
    @Getter
    private int minLifes;

    public MatrixLifeSystem() {
        instance = this;
    }

    public MatrixLifeSystem(
            JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
        instance = this;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadConfig();

        setupVaultIntegration();
        setupCommands();

        loadLoseLifeMethods();

        getServer().getPluginManager().registerEvents(this, this);
    }

    private void loadConfig() {
        this.minLifes = this.getConfig().getInt("min-lives");
    }

    private void loadLoseLifeMethods() {
        List<LoseLifeMethod> enabledMethods = getConfig()
                .getStringList("lose-life-methods")
                .stream()
                .map(LoseLifeMethod::valueOf)
                .toList();

        if(enabledMethods.contains(LoseLifeMethod.DEATH)) {
            getServer().getPluginManager().registerEvents(new LoseLifeDeathListener(), this);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if(event.getPlayer().hasPermission(Constants.ACF_ADMIN_PERMISSION)) {
            return;
        }

        PlayerData pd = PlayerData.getPlayerData(event.getPlayer().getUniqueId().toString());

        if(pd == null) {
            event.getPlayer().kick(Component.text("Life System not working!"));
            return;
        }

        if (pd.getLives() < minLifes) {
            event.getPlayer()
                    .kick(Component.text(commandManager
                            .getLocales()
                            .getMessage(null, MessageKey.of("kick.message")
                            )));
        }

    }

    private void setupVaultIntegration() {
        if (Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            econ = Objects.requireNonNull(getServer().getServicesManager().getRegistration(Economy.class)).getProvider();
        } else {

        }
    }


    private void setupCommands() {
        commandManager = new PaperCommandManager(this);
        commandManager.enableUnstableAPI("help");

        loadCommandLocales(commandManager);

        commandManager.registerCommand(new LifeSystemCommands());
    }

    // see https://github.com/aikar/commands/wiki/Locales
    private void loadCommandLocales(PaperCommandManager commandManager) {
        try {
            saveResource("lang-en.yaml", true);
            commandManager.getLocales().setDefaultLocale(Locale.ENGLISH);
            commandManager.getLocales().loadYamlLanguageFile("lang-en.yaml", Locale.ENGLISH);
            // this will detect the client locale and use it where possible
            commandManager.usePerIssuerLocale(true);
        } catch (IOException | InvalidConfigurationException e) {
            getLogger().severe("Failed to load language config 'lang_en.yaml': " + e.getMessage());
            e.printStackTrace();
        }
    }
}
