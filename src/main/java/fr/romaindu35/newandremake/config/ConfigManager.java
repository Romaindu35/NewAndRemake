package fr.romaindu35.newandremake.config;

import fr.romaindu35.newandremake.NewAndRemake;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Map;

public class ConfigManager {

    private final NewAndRemake newAndRemake;
    private final FileConfiguration fileConfiguration;

    public ConfigManager(NewAndRemake newAndRemake) {
        this.newAndRemake = newAndRemake;
        this.fileConfiguration = this.newAndRemake.getConfig();
    }

    public List<String> getSearchBlock() {
        return this.fileConfiguration.getStringList("search_block");
    }

    public Integer getSearchRadius() {
        return this.fileConfiguration.getInt("search_radius");
    }

}
