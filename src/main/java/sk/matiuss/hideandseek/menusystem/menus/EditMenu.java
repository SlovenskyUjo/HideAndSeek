package sk.matiuss.hideandseek.menusystem.menus;

import sk.matiuss.hideandseek.HideAndSeek;
import sk.matiuss.hideandseek.managers.ArenaManager;
import sk.matiuss.hideandseek.managers.ConfigManager;
import sk.matiuss.hideandseek.menusystem.Menu;
import sk.matiuss.hideandseek.menusystem.PlayerMenuUtility;
import sk.matiuss.hideandseek.utilities.Colors;
import sk.matiuss.hideandseek.utilities.Digit;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class EditMenu extends Menu {
    /*
     * This menu won't be items editable because it is plugins settings
     * */
    public EditMenu(PlayerMenuUtility playerMenuUtility, String arenaName) {
        super(playerMenuUtility);
        this.arenaname = arenaName;
    }
    private String arenaname;

    private String newArenaName;

    @Override
    public String getMenuName() {
        return Colors.translate("&7Edit Arena " + arenaname);
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        switch (e.getCurrentItem().getType()) {
            case OAK_SIGN:
                new AnvilGUI.Builder()
                        .onComplete((completion) -> {
                            newArenaName = completion.getText();
                            new ArenaManager().renameArena(arenaname, newArenaName);
                            newArenaName = null;
                            completion.getPlayer().sendMessage(Colors.translate("Saving..."));
                            return Arrays.asList(AnvilGUI.ResponseAction.close());
                        })
                        .preventClose()
                        .text("Arena Name")
                        .itemLeft(new ItemStack(Material.OAK_SIGN))
                        .title("Editing Arena Name")
                        .plugin(HideAndSeek.instance)
                        .open((Player) e.getWhoClicked());
                break;
            case BEACON:
                new AnvilGUI.Builder()
                        .onComplete((completion) -> {
                            if(new Digit().containsDigits(completion.getText())) {
                                new ArenaManager().changeArenaMaxPlayers(arenaname, Long.parseLong(completion.getText()));
                                completion.getPlayer().sendMessage(Colors.translate("Saving..."));
                                return Arrays.asList(AnvilGUI.ResponseAction.close());
                            } else {
                                completion.getPlayer().sendMessage("You need to use number input and not text input!");
                                return Arrays.asList(AnvilGUI.ResponseAction.close());
                            }
                        })
                        .preventClose()
                        .text("Max players")
                        .itemLeft(new ItemStack(Material.BEACON))
                        .title("Editing max players count")
                        .plugin(HideAndSeek.instance)
                        .open((Player) e.getWhoClicked());
                break;
            case END_PORTAL_FRAME:
                new AnvilGUI.Builder()
                        .onComplete((completion) -> {
                            new ArenaManager().changeArenaWorld(arenaname, completion.getText());
                            completion.getPlayer().sendMessage(Colors.translate("Saving..."));
                            return Arrays.asList(AnvilGUI.ResponseAction.close());
                        })
                        .preventClose()
                        .text("Arena world")
                        .itemLeft(new ItemStack(Material.END_PORTAL_FRAME))
                        .title("Editing arena world")
                        .plugin(HideAndSeek.instance)
                        .open((Player) e.getWhoClicked());
                break;
            case APPLE:
                new AnvilGUI.Builder()
                        .onComplete((completion) -> {
                            if(new Digit().containsDigits(completion.getText())) {
                                new ArenaManager().changeMinPlayers(arenaname, Long.parseLong(completion.getText()));
                                completion.getPlayer().sendMessage(Colors.translate("Saving..."));
                                return Arrays.asList(AnvilGUI.ResponseAction.close());
                            } else {
                                completion.getPlayer().sendMessage(Colors.translate("&cYou need to use number input and not text input!"));
                                return Arrays.asList(AnvilGUI.ResponseAction.close());
                            }
                        })
                        .preventClose()
                        .text("Arena min players")
                        .itemLeft(new ItemStack(Material.APPLE))
                        .title("Editing arena min players")
                        .plugin(HideAndSeek.instance)
                        .open((Player) e.getWhoClicked());
                break;
            case DIAMOND_SWORD:
                new AnvilGUI.Builder()
                        .onComplete((completion) -> {
                            if(new Digit().containsDigits(completion.getText())) {
                                new ArenaManager().changeSeekersCount(arenaname, Long.parseLong(completion.getText()));
                                completion.getPlayer().sendMessage(Colors.translate("Saving..."));
                                return Arrays.asList(AnvilGUI.ResponseAction.close());
                            } else {
                                completion.getPlayer().sendMessage(Colors.translate("&cYou need to use number input and not text input!"));
                                return Arrays.asList(AnvilGUI.ResponseAction.close());
                            }
                        })
                        .preventClose()
                        .text("Arena seekers count")
                        .itemLeft(new ItemStack(Material.DIAMOND_SWORD))
                        .title("Editing arena seekers count")
                        .plugin(HideAndSeek.instance)
                        .open((Player) e.getWhoClicked());
                break;
        }
    }

    @Override
    public void setMenuItems() {
        ItemStack sign = new ItemStack(Material.OAK_SIGN);
        ItemStack beacon = new ItemStack(Material.BEACON);
        ItemStack end = new ItemStack(Material.END_PORTAL_FRAME);
        ItemStack bell = new ItemStack(Material.APPLE);
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);

        ItemMeta signItemMeta = sign.getItemMeta();
        ItemMeta beaconItemMeta = beacon.getItemMeta();
        ItemMeta endItemMeta = end.getItemMeta();
        ItemMeta bellMeta = bell.getItemMeta();
        ItemMeta swordMeta = sword.getItemMeta();

        ArrayList<String> signLore = new ArrayList<>();
        ArrayList<String> beaconLore = new ArrayList<>();
        ArrayList<String> endLore = new ArrayList<>();
        ArrayList<String> bellLore = new ArrayList<>();
        ArrayList<String> swordLore = new ArrayList<>();

        signLore.add(Colors.translate("&7Arena Name"));
        signItemMeta.setDisplayName(Colors.translate("&cEdit arena name"));
        signItemMeta.setLore(signLore);
        sign.setItemMeta(signItemMeta);

        beaconLore.add(Colors.translate("&7Max Players"));
        beaconItemMeta.setDisplayName(Colors.translate("&cEdit max player count"));
        beaconItemMeta.setLore(beaconLore);
        beacon.setItemMeta(beaconItemMeta);

        endLore.add(Colors.translate("&7Arena World"));
        endItemMeta.setDisplayName(Colors.translate("&cEdit the arena world name"));
        endItemMeta.setLore(endLore);
        end.setItemMeta(endItemMeta);

        bellLore.add(Colors.translate("&7Min players"));
        bellMeta.setDisplayName(Colors.translate("&cEdit the min players"));
        bellMeta.setLore(bellLore);
        bell.setItemMeta(bellMeta);

        swordLore.add(Colors.translate("&7Seekers count"));
        swordMeta.setDisplayName(Colors.translate("&cEdit the seekers count"));
        swordMeta.setLore(swordLore);
        sword.setItemMeta(swordMeta);

        inventory.setItem(13, sign);
        inventory.setItem(11, beacon);
        inventory.setItem(9, end);
        inventory.setItem(15, bell);
        inventory.setItem(17, sword);
    }
}