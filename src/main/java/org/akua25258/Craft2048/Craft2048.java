package org.akua25258.Craft2048;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class Craft2048 extends JavaPlugin
{
    private static Craft2048 instance;

    @Override
    public void onEnable() { instance = this; }

    public static Craft2048 getInstance() { return instance; }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, @NotNull String[] args)
    {
        if (!command.getName().equalsIgnoreCase("2048craft")) return false;
        if (sender instanceof Player p) new MainGame(p);
        else sender.sendMessage("This command is for players only.");
        return true;
    }
}
