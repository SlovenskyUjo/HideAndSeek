package sk.matiuss.hideandseek.managers;

import sk.matiuss.hideandseek.HideAndSeek;
import sk.matiuss.hideandseek.game.Arena;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class ArenaManager {
    public List<Arena> arenas = new ArrayList<Arena>();

    public static List<Arena> STATICARENAS = new ArrayList<Arena>();

    public void addArenaToList(Arena arena, CommandSender sender){
        arenas.add(arena);
        new ConfigManager().saveArenasToConfig(arenas, sender);
    }
    public void removeArenaFromList(Arena arena) {
        arenas.remove(arena);
    }

    public void loadArenas() {
        File arenaFile = new File(HideAndSeek.instance.getDataFolder(), "arenas.yml");
        YamlConfiguration arenacfg = YamlConfiguration.loadConfiguration(arenaFile);

        for (String key : arenacfg.getConfigurationSection("arenas").getKeys(false)) {
            String worldName = ConfigManager.arenas.getString("arenas." + key + ".ArenaWorld");
            int arenaMaxPlayers = ConfigManager.arenas.getInt("arenas." + key + ".ArenaMaxPlayers");
            int arenaMinPlayers = ConfigManager.arenas.getInt("arenas." + key + ".ArenaMinPlayers");
            int arenaSeekersCount = ConfigManager.arenas.getInt("arenas." + key + ".ArenaSeekersCount");
            Location lobbyLocation = ConfigManager.arenas.getLocation("arenas." + key + ".LobbyLocation");
            Location hidersLocation = ConfigManager.arenas.getLocation("arenas." + key + ".HidersLocation");
            Location seekersLocation = ConfigManager.arenas.getLocation("arenas." + "SeekersLocation");

            Arena arena = new Arena(key, worldName, arenaMaxPlayers, arenaMinPlayers, arenaSeekersCount, lobbyLocation, hidersLocation, seekersLocation);

            STATICARENAS.add(arena);
        }
    }
    public List<Arena> getListOfArenas() {
        return arenas;
    }
    public void renameArena(String oldName, String newName) {
        ConfigurationSection parentSection = ConfigManager.arenas.getConfigurationSection("arenas");
        ConfigurationSection childSection = parentSection.getConfigurationSection(oldName);
        ConfigurationSection childNewSection = parentSection.createSection(newName);
        if (parentSection.getConfigurationSection(oldName) == null)  return;

        childNewSection.set("ArenaWorld", childSection.get("ArenaWorld"));
        childNewSection.set("ArenaMaxPlayers", childSection.get("ArenaMaxPlayers"));
        childNewSection.set("ArenaMinPlayers", childSection.get("ArenaMinPlayers"));
        childNewSection.set("ArenaSeekersCount", childSection.get("ArenaSeekersCount"));

        parentSection.set(oldName, null);
        try {
            ConfigManager.arenas.save(ConfigManager.arenasFile);
        } catch (IOException error) {
            error.printStackTrace();
        }
        new ConfigManager().startup();
    }
    public void changeArenaMaxPlayers(String arenaName, Long maxPlayers) {
        ConfigurationSection parentSection = ConfigManager.arenas.getConfigurationSection("arenas");
        ConfigurationSection arena = parentSection.getConfigurationSection(arenaName);
        arena.set("ArenaMaxPlayers", maxPlayers);
        try {
            ConfigManager.arenas.save(ConfigManager.arenasFile);
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
    public void changeArenaWorld(String arenaName, String world) {
        ConfigurationSection parentSection = ConfigManager.arenas.getConfigurationSection("arenas");
        ConfigurationSection arena = parentSection.getConfigurationSection(arenaName);
        arena.set("ArenaWorld", world);
        try {
            ConfigManager.arenas.save(ConfigManager.arenasFile);
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
    public void changeMinPlayers(String arenaName, Long minPlayers) {
        ConfigurationSection parentSection = ConfigManager.arenas.getConfigurationSection("arenas");
        ConfigurationSection arena = parentSection.getConfigurationSection(arenaName);
        arena.set("ArenaMinPlayers", minPlayers);
        try {
            ConfigManager.arenas.save(ConfigManager.arenasFile);
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
    public void changeSeekersCount(String arenaName, Long seekers) {
        ConfigurationSection parentSection = ConfigManager.arenas.getConfigurationSection("arenas");
        ConfigurationSection arena = parentSection.getConfigurationSection(arenaName);
        arena.set("ArenaSeekersCount", seekers);
        try {
            ConfigManager.arenas.save(ConfigManager.arenasFile);
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
    public Arena getArenaByString(String arenaName) {
        ConfigurationSection parentSection = ConfigManager.arenas.getConfigurationSection("arenas");
        ConfigurationSection arena = parentSection.getConfigurationSection(arenaName);
        int index = -1;
        for (int i = 0; i < STATICARENAS.size(); i++) {
            if (STATICARENAS.get(i).arenaName.toString().equals(arenaName.toString())) {
                index = i;
                return STATICARENAS.get(index);
            }
        }
        return null;
    }
}
