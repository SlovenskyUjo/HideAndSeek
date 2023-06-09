package sk.matiuss.hideandseek.commands;

import sk.matiuss.hideandseek.HideAndSeek;
import sk.matiuss.hideandseek.commands.subcommands.*;
import sk.matiuss.hideandseek.managers.ConfigLoader;
import sk.matiuss.hideandseek.managers.ConfigManager;
import sk.matiuss.hideandseek.utilities.Colors;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainCommand implements CommandExecutor, TabCompleter {
    private ArrayList<SubCommand> subcommands = new ArrayList<>();

    public MainCommand () {
        subcommands.add(new Help());
        subcommands.add(new Reload());
        subcommands.add(new Setup());
        subcommands.add(new Leave());
        subcommands.add(new EditArena());
        subcommands.add(new Build());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length > 0) {
                for (int i = 0; i < getSubcommands().size(); i++){
                    if (args[0].equalsIgnoreCase(getSubcommands().get(i).getName())){
                        getSubcommands().get(i).perform(player, args);
                    }
                }
            }else if(args.length == 0){
                player.sendMessage(Colors.translate("&8&m--------------------&6 HELP &8&m--------------------"));
                for (int i = 0; i < getSubcommands().size(); i++){
                    player.sendMessage(Colors.translate("&a" + getSubcommands().get(i).getUsage() + " &7- " + getSubcommands().get(i).getDescription()));
                }
                player.sendMessage(Colors.translate("&8&m----------------------------------------"));
            }
            return true;
        } else { sender.sendMessage(PlaceholderAPI.setPlaceholders((OfflinePlayer) sender, Colors.translate(ConfigLoader.notEntity))); return true; }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1){
            List<String> arguments = new ArrayList<>();
            arguments.add("help");
            arguments.add("joinarena");
            arguments.add("leave");
            if (sender.hasPermission("has.tab") || sender.hasPermission("has.*")) {
                arguments.add("reload");
                arguments.add("setup");
                arguments.add("editarena");
                arguments.add("build");
            }
            return arguments;
        } else if(args.length == 2) {
            switch(args[0].toLowerCase()) {
                case "setup":
                    List<String> arguments = new ArrayList<>();
                    arguments.add("arena");
                    arguments.add("lobby");
                    return arguments;
                case "editarena":
                    File arenaFile = new File(HideAndSeek.instance.getDataFolder(), "arenas.yml");
                    YamlConfiguration arena = YamlConfiguration.loadConfiguration(arenaFile);
                    List<String> arenaList = new ArrayList<>();
                    if(arena.getConfigurationSection("arenas").getKeys(false) == null){
                        return arenaList;
                    } else {
                        for (String key : arena.getConfigurationSection("arenas").getKeys(false)) {
                            arenaList.add(key);
                        }
                    }
                    return arenaList;
                case "joinarena":
                    File arenaFilee = new File(HideAndSeek.instance.getDataFolder(), "arenas.yml");
                    YamlConfiguration arenaa = YamlConfiguration.loadConfiguration(arenaFilee);
                    List<String> arenaListt = new ArrayList<>();
                    if(arenaa.getConfigurationSection("arenas").getKeys(false) == null){
                        return arenaListt;
                    } else {
                        for (String key : arenaa.getConfigurationSection("arenas").getKeys(false)) {
                            arenaListt.add(key);
                        }
                    }
                    return arenaListt;
            }
        }
        return null;
    }
    public ArrayList<SubCommand> getSubcommands(){
        return subcommands;
    }

}