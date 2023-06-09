package sk.matiuss.hideandseek.commands.subcommands;

import sk.matiuss.hideandseek.HideAndSeek;
import sk.matiuss.hideandseek.commands.SubCommand;
import sk.matiuss.hideandseek.managers.ConfigLoader;
import sk.matiuss.hideandseek.managers.ConfigManager;
import sk.matiuss.hideandseek.menusystem.menus.EditMenu;
import sk.matiuss.hideandseek.utilities.Colors;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
public class EditArena extends SubCommand {
    @Override
    public String getName() {
        return "editarena";
    }

    @Override
    public String getDescription() {
        return "You can edit arena set its name etc.";
    }

    @Override
    public String getUsage() {
        return "/has editarena <arenaName>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!(player.hasPermission("has.editArena"))) {
            player.sendMessage(PlaceholderAPI.setPlaceholders((OfflinePlayer) player, Colors.translate(ConfigLoader.noPerms)));
            return;
        }
        if (new ConfigManager().arenaExists(args[1])) {
            new EditMenu(HideAndSeek.getPlayerMenuUtility(player), args[1]).open();
            player.sendMessage(ConfigLoader.arenaNowEditing.replace("%arena%", args[1]));
        } else {
            player.sendMessage(ConfigLoader.arenaDoesntExist.replace("%arena%", args[1]));
        }
    }
}