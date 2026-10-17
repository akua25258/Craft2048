package org.akua25258.Craft2048;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.bukkit.Bukkit.createBossBar;
import static org.bukkit.Bukkit.createInventory;
import static org.bukkit.Material.*;

public class MainGame
{
    private static final int nextStage = 10;

    public static final Material[] itemList = {
            STICK,
            WOODEN_HOE,
            WOODEN_SHOVEL,
            WOODEN_PICKAXE,
            WOODEN_AXE,
            WOODEN_SWORD,

            STONE,
            STONE_HOE,
            STONE_SHOVEL,
            STONE_PICKAXE,
            STONE_AXE,
            STONE_SWORD,

            IRON_INGOT,
            IRON_HOE,
            IRON_SHOVEL,
            IRON_PICKAXE,
            IRON_AXE,
            IRON_SWORD,

            GOLD_INGOT,
            GOLDEN_HOE,
            GOLDEN_SHOVEL,
            GOLDEN_PICKAXE,
            GOLDEN_AXE,
            GOLDEN_SWORD,

            DIAMOND,
            DIAMOND_HOE,
            DIAMOND_SHOVEL,
            DIAMOND_PICKAXE,
            DIAMOND_AXE,
            DIAMOND_SWORD,

            NETHERITE_INGOT,
            NETHERITE_HOE,
            NETHERITE_SHOVEL,
            NETHERITE_PICKAXE,
            NETHERITE_AXE,
            NETHERITE_SWORD
    };

    private static final Craft2048 plugin = Craft2048.getInstance();
    private final Player p;
    private Inventory menu = createInventory(null, 54, "2048craft");
    private final BossBar Bossbar = createBossBar(ChatColor.YELLOW + "Right-click an empty slot", BarColor.WHITE, BarStyle.SOLID);

    private int count = 0;

    public MainGame(Player player)
    {
        this.p = player;
        Bossbar.setProgress(0);
        Bossbar.addPlayer(p);
        p.closeInventory();
        p.openInventory(menu);

        plugin.getServer().getPluginManager().registerEvents(new EventListener(this), plugin);
    }

    public Player getPlayer() { return this.p; }

    public String getMenuName() { return this.p.getOpenInventory().getTitle(); }

    public void stop()
    {
        Bossbar.removeAll();
        this.menu = null;
    }

    private void gameClear()
    {
        p.closeInventory();
        p.sendTitle(ChatColor.YELLOW + "Game Clear", ChatColor.WHITE + "Thanks for playing.", 20, 200, 20);
    }

    private void changeBossbar()
    {
        count++;
        if (count % nextStage == 0)
        {
            for (short i = 0; i < 54; i++)
            {
                ItemStack item = menu.getItem(i);
                if (item == null || item.getType() == Material.AIR) continue;
                Material id = item.getType();
                menu.setItem(i, getItem(id));
            }
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, 1);
        }
        int stage = count / nextStage;
        if (stage > itemList.length - 4) stage = itemList.length - 4;
        String name = "§7Total:§r §a" + count + "§r §7| Stage:§r §c" + (stage + 1);
        Bossbar.setTitle(name);
    }

    private ItemStack getItem(Material id) {
        ItemStack item = new ItemStack(id);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        int lv = Arrays.asList(itemList).indexOf(id);
        int stage = count / nextStage;
        lore.add("§fLEVEL:§r §b" + (lv + 1) + "§r§8/" + itemList.length);
        if (lv < stage) lore.add(ChatColor.RED + "This item does not appear in the current stage.");
        if (meta != null) meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public void setRandomItem()
    {
        List<Integer> randomList = new ArrayList<>();
        for (short i = 0; i < 54; i++) if (menu.getItem(i) == null) randomList.add((int) i);
        Random rand = new Random();
        int stage = count / nextStage;
        if (stage > itemList.length - 4) stage = itemList.length - 4;
        int id = rand.nextInt(4);
        if (!randomList.isEmpty()) {
            int slot = rand.nextInt(randomList.size());
            menu.setItem(randomList.get(slot), getItem(itemList[id + stage]));
        } else p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 2, 1);
    }

    public ItemStack changeItem(Material item) {
        short i = 0;
        for (Material list : itemList)
        {
            if (list == item) {
                if (i + 1 >= itemList.length) {
                    gameClear();
                    break;
                } else {
                    changeBossbar();
                    return getItem(itemList[i + 1]);
                }
            } else i++;
        }
        return new ItemStack(AIR);
    }
}
