package sk.matiuss.hideandseek.menusystem.menus;

import sk.matiuss.hideandseek.game.Arena;
import sk.matiuss.hideandseek.managers.ConfigLoader;
import sk.matiuss.hideandseek.managers.GameManager;
import sk.matiuss.hideandseek.managers.ArenaManager;
import sk.matiuss.hideandseek.managers.ConfigManager;
import sk.matiuss.hideandseek.menusystem.PaginatedMenu;
import sk.matiuss.hideandseek.menusystem.PlayerMenuUtility;
import sk.matiuss.hideandseek.utilities.Colors;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ArenaSelector extends PaginatedMenu {
    public ArenaSelector(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return Colors.translate(ConfigLoader.arenaSelectorTitle);
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        ItemStack item = e.getCurrentItem();
        Material mat = Material.matchMaterial(ConfigLoader.arenaItem.toUpperCase());
        if(item.getType() == mat) {
            Arena arenaClicked = new ArenaManager().getArenaByString(ChatColor.stripColor(item.getItemMeta().getDisplayName()));
            e.getView().close();
            new GameManager().joinArena((Player) e.getWhoClicked(), arenaClicked);
        }
    }

    @Override
    public void setMenuItems() {
        super.addMenuBorder();
        if(new ArenaManager().STATICARENAS != null && !new ArenaManager().STATICARENAS.isEmpty()) {
            for(int i = 0; i < getMaxItemsPerPage(); i++) {
                index = getMaxItemsPerPage() * page + i;
                if(index >= new ArenaManager().STATICARENAS.size()) break;
                if (new ArenaManager().STATICARENAS.get(index) != null){
                    ItemStack playerItem = new ItemStack(Material.matchMaterial(ConfigLoader.arenaItem.toUpperCase()), 1);
                    ItemMeta playerMeta = playerItem.getItemMeta();
                    playerMeta.setDisplayName(ChatColor.RED + new ArenaManager().STATICARENAS.get(index).arenaName);
                    ArrayList<String> jj = new ArrayList<>();
                    jj.add(Colors.translate(new ArenaManager().STATICARENAS.get(index).arenaWorldName));
                    jj.add(Colors.translate(String.valueOf(new ArenaManager().STATICARENAS.get(index).maxPlayers)));
                    playerMeta.setLore(jj);
                    playerItem.setItemMeta(playerMeta);

                    inventory.addItem(playerItem);
                }
            }
        }

    }
}
