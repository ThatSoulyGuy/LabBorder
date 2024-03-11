package com.thatsoulyguy.labborder.event;

import com.thatsoulyguy.labborder.LabBorder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;
import java.util.logging.Level;

public class EventPlayer implements Listener
{
    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent event)
    {
        if (event.getPlayer().getName().contains("0x0000FFFD"))
            event.getPlayer().sendMessage(ChatColor.BLUE + "This server is currently running the LabBorder plugin.");

        String uuidString = (String) LabBorder.GetInstance().controllerConfig.GetValue("worlds." + event.getPlayer().getWorld().getName() + ".uuid");

        if (uuidString != null)
        {
            try
            {
                UUID targetUUID = UUID.fromString(uuidString);

                for (Entity entity : event.getPlayer().getWorld().getEntities())
                {
                    if (entity.getUniqueId().equals(targetUUID))
                        LabBorder.GetInstance().worldBorderControllers.put(event.getPlayer().getWorld(), entity);
                }
            }
            catch (IllegalArgumentException e)
            {
                LabBorder.GetInstance().getLogger().log(Level.SEVERE, "Failed to get uuid: '" + uuidString + "'");
                e.printStackTrace();
            }
        }
        else
            LabBorder.GetInstance().getLogger().log(Level.SEVERE, "UUID from string was null");
    }
}