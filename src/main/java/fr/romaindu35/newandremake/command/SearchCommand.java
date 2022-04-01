package fr.romaindu35.newandremake.command;

import dev.jorel.commandapi.CommandAPICommand;
import fr.romaindu35.newandremake.enchantment.EnchantmentManager;
import fr.romaindu35.newandremake.manager.SearchManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class SearchCommand {

    public SearchCommand(SearchManager searchManager) {
        new CommandAPICommand("search")
                .executesPlayer((player, args) -> {

                    if (searchManager.getPlayers().contains(player)) {
                        searchManager.removePlayer(player);
                        player.sendMessage(Component.text("Search system is deactivated", TextColor.color(255,0,0)));
                    } else {
                        searchManager.addPlayer(player);
                        player.sendMessage(Component.text("Search system is activated", TextColor.color(0,255,0)));
                    }

                })
                .register();
    }

}
