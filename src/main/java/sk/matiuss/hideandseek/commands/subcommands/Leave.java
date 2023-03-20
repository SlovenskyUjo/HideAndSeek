package sk.matiuss.hideandseek.commands.subcommands;

import sk.matiuss.hideandseek.commands.SubCommand;
import sk.matiuss.hideandseek.managers.GameManager;
import org.bukkit.entity.Player;
public class Leave extends SubCommand {
    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getDescription() {
        return "You can leave arena";
    }

    @Override
    public String getUsage() {
        return "/has leave";
    }

    @Override
    public void perform(Player player, String[] args) {
        new GameManager().leaveArena(player);
    }
}
