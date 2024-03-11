package com.thatsoulyguy.labborder.command;

import com.thatsoulyguy.labborder.LabBorder;
import com.thatsoulyguy.soulcore.command.SCommand;
import com.thatsoulyguy.soulcore.command.SCommandRegistration;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ReloadCommand extends SCommand
{

    @Override
    public int Execute(Player player, String[] strings)
    {
        player.sendMessage(ChatColor.YELLOW + "Attempting to the reloading plugin...");

        LabBorder.GetInstance().defaultConfig.Reload();
        LabBorder.GetInstance().controllerConfig.Reload();
        LabBorder.GetInstance().messagesConfig.Reload();

        player.sendMessage(ChatColor.GREEN + "Successfully reloaded the plugin!");

        return 0;
    }

    @Override
    public SCommandRegistration Register()
    {
        return SCommandRegistration.Register("reload", "Reloads the plugin.", "/labborder reload");
    }
}