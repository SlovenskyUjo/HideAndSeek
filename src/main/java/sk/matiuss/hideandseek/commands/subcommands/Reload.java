package sk.matiuss.hideandseek.commands.subcommands;

import sk.matiuss.hideandseek.commands.SubCommand;
import sk.matiuss.hideandseek.managers.ConfigLoader;
import sk.matiuss.hideandseek.managers.ConfigManager;
import sk.matiuss.hideandseek.utilities.Colors;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
public class Reload extends SubCommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reloads minecraft plugin";
    }

    @Override
    public String getUsage() {
        return "/has reload";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission("has.reload") || !player.hasPermission("has.*")) { player.sendMessage(Colors.translate(ConfigLoader.prefix + ConfigLoader.noPerms)); return; }
        player.sendMessage(PlaceholderAPI.setPlaceholders((OfflinePlayer) player, Colors.translate(ConfigLoader.prefix + ConfigLoader.reload)));
        new ConfigManager().startup();
        new ConfigLoader();
        player.sendMessage(PlaceholderAPI.setPlaceholders((OfflinePlayer) player, Colors.translate(ConfigLoader.prefix + ConfigLoader.sucReloaded)));
    }
}
