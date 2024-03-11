package com.thatsoulyguy.labborder.command;

import com.thatsoulyguy.soulcore.command.SCommand;
import com.thatsoulyguy.soulcore.command.SCommandRegistration;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AuthorCommand extends SCommand
{

    @Override
    public int Execute(Player player, String[] strings)
    {
        player.sendMessage(ChatColor.YELLOW + "This plugin was created by ThatSoulyGuy, also known as 0x0000FFFD.");

        return 0;
    }

    @Override
    public SCommandRegistration Register()
    {
        return SCommandRegistration.Register("author", "Tells you who made the plugin", "/labborder author");
    }
}