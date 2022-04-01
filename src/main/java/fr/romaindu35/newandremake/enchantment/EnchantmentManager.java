package fr.romaindu35.newandremake.enchantment;

import dev.jorel.commandapi.CommandAPICommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class EnchantmentManager implements Listener {

    public static final Enchantment IN_GOLD = new EnchantmentBase("in_gold", "In gold", 1, EnchantmentTarget.ARMOR_HEAD);
    public static final Enchantment ANTI_SKELETON = new EnchantmentBase("anti_skeleton", "Anti Skeleton", 1, EnchantmentTarget.ARMOR_TORSO);
    public static final Enchantment ANTI_CREEPER = new EnchantmentBase("anti_creeper", "Anti Creeper", 1, EnchantmentTarget.ARMOR_TORSO);

    public void register() {
        EnchantmentManager.registerEnchantment(IN_GOLD);
        EnchantmentManager.registerEnchantment(ANTI_SKELETON);
        EnchantmentManager.registerEnchantment(ANTI_CREEPER);
    }

    public void registerCommand() {
        new CommandAPICommand("in_gold")
                .executesPlayer((player, args) -> {
                    ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();
                    itemMeta.addEnchant(EnchantmentManager.IN_GOLD, 1, true);
                    itemMeta.lore(List.of(Component.text("In gold I")));
                    player.getInventory().getItemInMainHand().setItemMeta(itemMeta);

                })
                .register();
        new CommandAPICommand("anti_skeleton")
                .executesPlayer((player, args) -> {
                    ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();
                    itemMeta.addEnchant(EnchantmentManager.ANTI_SKELETON, 1, true);
                    itemMeta.lore(List.of(Component.text("Anti Skeleton I")));
                    player.getInventory().getItemInMainHand().setItemMeta(itemMeta);
                })
                .register();
        new CommandAPICommand("anti_creeper")
                .executesPlayer((player, args) -> {
                    ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();
                    itemMeta.addEnchant(EnchantmentManager.ANTI_CREEPER, 1, true);
                    itemMeta.lore(List.of(Component.text("Anti Creeper I")));
                    player.getInventory().getItemInMainHand().setItemMeta(itemMeta);
                })
                .register();
    }

    private static void registerEnchantment(Enchantment enchantment) {
        if (Arrays.stream(Enchantment.values()).toList().contains(enchantment))
            return;

        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
            Enchantment.registerEnchantment(enchantment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @EventHandler
    public void enchantAction(EntityTargetLivingEntityEvent event) {
        if (!(event.getTarget() instanceof Player target)) {
            return;
        }
        if (target.getInventory().getHelmet() != null) {
            if (target.getInventory().getHelmet().hasItemMeta()) {
                if (target.getInventory().getHelmet().getItemMeta().hasEnchant(IN_GOLD)) {
                    if ((event.getEntity().getType() == EntityType.PIGLIN || event.getEntity().getType() == EntityType.PIGLIN_BRUTE) && event.getReason() == EntityTargetEvent.TargetReason.CLOSEST_PLAYER) {
                        event.setCancelled(true);
                    }
                }
            }
        }
        if (target.getInventory().getChestplate() != null) {
            if (target.getInventory().getChestplate().hasItemMeta()) {
                if (target.getInventory().getChestplate().getItemMeta().hasEnchant(ANTI_SKELETON)) {
                    if (event.getEntity().getType() == EntityType.SKELETON && event.getReason() == EntityTargetEvent.TargetReason.CLOSEST_PLAYER) {
                        event.setCancelled(true);
                    }
                }
                if (target.getInventory().getChestplate().getItemMeta().hasEnchant(ANTI_CREEPER)) {
                    if (event.getEntity().getType() == EntityType.CREEPER && event.getReason() == EntityTargetEvent.TargetReason.CLOSEST_PLAYER) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

}
