package com.thatsoulyguy.labborder.command;

import com.thatsoulyguy.labborder.LabBorder;
import com.thatsoulyguy.soulcore.command.SCommand;
import com.thatsoulyguy.soulcore.command.SCommandRegistration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RemoveControllerCommand extends SCommand
{
    @Override
    public int Execute(Player player, String[] args)
    {
        if(args.length == 0)
        {
            player.sendMessage(ChatColor.DARK_RED + "You must supply the world of the border controller!");
            return -1;
        }

        UUID targetUUID = UUID.fromString((String) LabBorder.GetInstance().controllerConfig.GetValue("worlds." + player.getWorld().getName() + ".uuid"));

        for (Entity entity : player.getWorld().getEntities())
        {
            if (entity.getUniqueId().equals(targetUUID))
            {
                entity.remove();

                for(Player onlinePlayers : Bukkit.getOnlinePlayers())
                {
                    onlinePlayers.playSound(onlinePlayers.getLocation(), Sound.ENTITY_WITHER_DEATH, 1.0F, 1.0F);
                    onlinePlayers.sendMessage(((String) LabBorder.GetInstance().messagesConfig.GetValue("border-controller-kill")).replace('&', '§'));
                }
            }
        }

        return 0;
    }

    @Override
    public SCommandRegistration Register()
    {
        return SCommandRegistration.Register("removecontroller", "Removes the current world border comtroller", "/labborder removecontroller");
    }
}