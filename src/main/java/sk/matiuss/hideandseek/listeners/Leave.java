package sk.matiuss.hideandseek.listeners;

import sk.matiuss.hideandseek.game.Arena;
import sk.matiuss.hideandseek.managers.ArenaManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
public class Leave implements Listener {
    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        for(Arena arena : ArenaManager.STATICARENAS) {
            if(arena.players.contains(event.getPlayer().getUniqueId())) {
                if(arena.seekers.contains(event.getPlayer().getUniqueId())){
                    arena.seekers.remove(event.getPlayer().getUniqueId());
                } else {
                    arena.players.remove(event.getPlayer().getUniqueId());
                }
            }
        }
    }
}
