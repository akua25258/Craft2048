package org.akua25258.Craft2048;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.bukkit.Bukkit.createInventory;
import static org.bukkit.Material.*;

public class MainGame
{
    public static final Material[] itemList = {
            STICK,
            WOODEN_SHOVEL,
            STONE_SHOVEL,
            IRON_SHOVEL,
            GOLDEN_SHOVEL,
            DIAMOND_SHOVEL,
            NETHERITE_SHOVEL,
            WOODEN_PICKAXE,
            STONE_PICKAXE,
            IRON_PICKAXE,
            GOLDEN_PICKAXE,
            DIAMOND_PICKAXE,
            NETHERITE_PICKAXE,
            WOODEN_AXE,
            STONE_AXE,
            IRON_AXE,
            GOLDEN_AXE,
            DIAMOND_AXE,
            NETHERITE_AXE,
            WOODEN_HOE,
            STONE_SWORD,
            IRON_SWORD,
            GOLDEN_SWORD,
            DIAMOND_SWORD,
            NETHERITE_SWORD,
            WOODEN_HOE,
            STONE_HOE,
            IRON_HOE,
            GOLDEN_HOE,
            DIAMOND_HOE,
            NETHERITE_HOE
    };

    private final Player p;
    private Inventory menu;

    public MainGame(Player player)
    {
        this.p = player;
        this.menu = createInventory(null, 54, "2048craft");

        openMenu();

        Craft2048 plugin = Craft2048.getInstance();
        plugin.getServer().getPluginManager().registerEvents(new EventListener(this), plugin);
    }

    public Player getPlayer() { return this.p; }

    public void stop() { menu = null; }

    private void gameClear()
    {
        p.closeInventory();
        p.sendTitle(ChatColor.YELLOW + "Game Clear", ChatColor.WHITE + "Thanks for playing.", 20, 200, 20);
    }

    private ItemStack getItem(Material id) {
        ItemStack item = new ItemStack(id);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        int lv = Arrays.asList(itemList).indexOf(id);
        lore.add("§fLEVEL:§r §b" + (lv + 1) + "§r§8/" + itemList.length);
        if (meta != null) meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public void setRandomItem()
    {
        List<Integer> randomList = new ArrayList<>();
        for (short i = 0; i < 54; i++) if (menu.getItem(i) == null) randomList.add((int) i);
        Random rand = new Random();
        int id = rand.nextInt(4);
        if (!randomList.isEmpty()) {
            int slot = rand.nextInt(randomList.size());
            menu.setItem(randomList.get(slot), getItem(itemList[id]));
        } else p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 2, 1);
    }

    public ItemStack changeItem(Material item) {
        short i = 0;
        for (Material list : itemList)
        {
            if (list == item) {
                if (i + 1 >= itemList.length)
                {
                    gameClear();
                    break;
                }

                return getItem(itemList[i + 1]);
            } else i++;
        }
        return new ItemStack(AIR);
    }

    private void openMenu()
    {
        p.closeInventory();
        p.openInventory(menu);
    }
}
