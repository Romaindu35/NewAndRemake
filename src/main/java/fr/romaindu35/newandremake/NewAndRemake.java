package fr.romaindu35.newandremake;

import fr.romaindu35.newandremake.command.SearchCommand;
import fr.romaindu35.newandremake.config.ConfigManager;
import fr.romaindu35.newandremake.enchantment.EnchantmentManager;
import fr.romaindu35.newandremake.manager.SearchManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class NewAndRemake extends JavaPlugin {

    private SearchManager searchManager;


    @Override
    public void onEnable() {
        // Plugin startup logic
        ConfigManager configManager = new ConfigManager(this);
        this.searchManager = new SearchManager(configManager);
        EnchantmentManager enchantmentManager = new EnchantmentManager();
        enchantmentManager.register();
        enchantmentManager.registerCommand();
        new SearchCommand(this.searchManager);

        this.getServer().getPluginManager().registerEvents(this.searchManager, this);
        this.getServer().getPluginManager().registerEvents(enchantmentManager, this);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.searchManager.clearPlayers();
    }
}
