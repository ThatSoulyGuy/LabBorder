package com.thatsoulyguy.labborder.command;

import com.thatsoulyguy.labborder.LabBorder;
import com.thatsoulyguy.soulcore.command.SCommand;
import com.thatsoulyguy.soulcore.command.SCommandRegistration;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class SpawnControllerCommand extends SCommand
{
    @Override
    public int Execute(Player player, String[] args)
    {
        EntityType entityType = EntityType.valueOf((String) LabBorder.GetInstance().defaultConfig.GetValue("border-controller-type"));
        World world = player.getWorld();

        Entity controller = world.spawnEntity(player.getLocation(), entityType);

        controller.setCustomName(((String) LabBorder.GetInstance().defaultConfig.GetValue("border-controller-name")).replace('&', '§'));
        controller.setCustomNameVisible(true);
        controller.setInvulnerable(true);
        controller.setPersistent(true);

        LabBorder.GetInstance().controllerConfig.SetValue("worlds." + world.getName() + ".uuid", controller.getUniqueId().toString());

        LabBorder.GetInstance().worldBorderControllers.put(world, controller);

        for(Player onlinePlayers : Bukkit.getOnlinePlayers())
        {
            onlinePlayers.playSound(onlinePlayers.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 1.0F, 1.0F);
            onlinePlayers.sendMessage(((String) LabBorder.GetInstance().messagesConfig.GetValue("border-controller-summon")).replace('&', '§'));
        }

        return 0;
    }

    @Override
    public SCommandRegistration Register()
    {
        return SCommandRegistration.Register("spawncontroller", "Spawns in the worldborder controller.", "/labborder spawncontroller");
    }
}