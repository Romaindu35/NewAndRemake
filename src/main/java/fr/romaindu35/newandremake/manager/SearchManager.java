package fr.romaindu35.newandremake.manager;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import fr.romaindu35.newandremake.Utils;
import fr.romaindu35.newandremake.config.ConfigManager;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class SearchManager implements Listener {

    private final Set<Player> players = new HashSet<>();
    private final Set<Material> search_block = new HashSet<>();
    private final Integer search_radius;

    public SearchManager(ConfigManager configManager) {
        configManager.getSearchBlock().forEach(material -> this.search_block.add(Material.matchMaterial(material)));
        search_radius = configManager.getSearchRadius();
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
    }

    public void clearPlayers() {
        this.players.clear();
    }

    @EventHandler
    public void onTickEvent(ServerTickStartEvent playerMoveEvent) {

        for (Player player : this.getPlayers()) {
            Location location = player.getLocation();

            Optional<Location> optionalLocation = Utils.getNearbyLocationBlocks(location, this.search_radius, this.search_block);

            if(optionalLocation.isPresent()) {
                Location nearby_block = optionalLocation.get();
                Utils.spawnParticle(player, location, nearby_block, Particle.REDSTONE, 1, new Particle.DustOptions(Color.PURPLE, 1));
            }
        }
    }

}
