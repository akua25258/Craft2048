package org.akua25258.Craft2048;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class EventListener implements Listener
{
    private final MainGame game;
    private final Player p;

    public EventListener(MainGame game)
    {
        this.game = game;
        this.p = game.getPlayer();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        if (!e.getView().getPlayer().equals(p) || !e.getView().getTitle().equals(game.getMenuName())) return;

        if (e.getClickedInventory() == e.getView().getBottomInventory() || e.getClickedInventory() == null)
        {
            e.setCancelled(true);
            return;
        }

        if (e.getClick() == ClickType.LEFT) {
            if (e.getCurrentItem() == null) return;
            Material item = e.getCurrentItem().getType();

            if (e.getView().getCursor() != null && item == e.getView().getCursor().getType())
            {
                e.setCancelled(true);
                e.setCurrentItem(game.changeItem(item));
                e.getView().setCursor(null);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2, 0.4f);
            }
        } else if (e.getClick() == ClickType.RIGHT) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) game.setRandomItem();
            else {
                e.setCurrentItem(null);
                p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_BURN, 2, 1);
            }
        } else e.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e)
    {
        if (!e.getView().getPlayer().equals(p) || !e.getView().getTitle().equals(game.getMenuName())) return;

        ItemStack item = e.getView().getCursor();
        if (item != null) e.getView().setCursor(null);

        p.sendMessage("§b[2048craft]§r The game has ended.");

        HandlerList.unregisterAll(this);
        game.stop();
    }
}
