package sk.matiuss.hideandseek.commands;

import sk.matiuss.hideandseek.managers.ConfigManager;
import org.bukkit.entity.Player;

public abstract class SubCommand {
    public abstract String getName();
    public abstract String getDescription();
    public abstract String getUsage();
    public abstract void perform(Player player, String[] args);
}
