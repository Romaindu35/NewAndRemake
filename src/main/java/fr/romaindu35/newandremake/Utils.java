package fr.romaindu35.newandremake;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Utils {

    public static Optional<Location> getNearbyLocationBlocks(Location location, int radius, Set<Material> materials) {
        for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    for (Material material : materials) {
                        if (location.getWorld().getBlockAt(x, y, z).getType().equals(material))
                            return Optional.of(new Location(location.getWorld(), x, y, z));
                    }
                }
            }
        }
        return Optional.empty();
    }

    public static void spawnParticle(Player player, Location location1, Location location2, Particle particle, int count, Particle.DustOptions dustOptions) {
        if (location1.getWorld() != location2.getWorld()) return;

        location1 = location1.clone();
        location2 = location2.clone();

        Vector location1Vector = location1.toVector();
        Vector location2Vector = location2.toVector();

        Vector vector = location2Vector.clone().subtract(location1Vector);
        Vector directionVector = vector.clone().normalize();

        for (int i = 0; i < vector.length(); i++) {
            Vector particlePoint = location1Vector.clone().add(directionVector.clone().multiply(i));
            player.getWorld().spawnParticle(particle, particlePoint.toLocation(player.getWorld()), count, dustOptions);
        }
    }

}
