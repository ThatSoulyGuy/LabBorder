package com.thatsoulyguy.labborder;

import com.thatsoulyguy.labborder.command.AuthorCommand;
import com.thatsoulyguy.labborder.command.ReloadCommand;
import com.thatsoulyguy.labborder.command.RemoveControllerCommand;
import com.thatsoulyguy.labborder.command.SpawnControllerCommand;
import com.thatsoulyguy.labborder.event.EventPlayer;
import com.thatsoulyguy.soulcore.command.SCommand;
import com.thatsoulyguy.soulcore.command.SCommandManager;
import com.thatsoulyguy.soulcore.config.SConfig;
import com.thatsoulyguy.soulcore.update.SUpdateManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.A;

import java.util.HashMap;
import java.util.Map;

public final class LabBorder extends JavaPlugin
{
    public SConfig defaultConfig = new SConfig();
    public SConfig controllerConfig = new SConfig();
    public SConfig messagesConfig = new SConfig();

    public Map<World, Entity> worldBorderControllers = new HashMap<>();

    private static LabBorder instance = null;

    @Override
    public void onEnable()
    {
        instance = this;

        defaultConfig.Initialize(this, "config.yml");
        controllerConfig.Initialize(this, "data.yml");
        messagesConfig.Initialize(this, "messages.yml");

        SCommandManager.Register(new AuthorCommand());
        SCommandManager.Register(new ReloadCommand());
        SCommandManager.Register(new SpawnControllerCommand());
        SCommandManager.Register(new RemoveControllerCommand());

        SCommandManager.Initialize(this, "labborder");

        getServer().getPluginManager().registerEvents(new EventPlayer(), this);

        for(World world : Bukkit.getWorlds())
            world.getWorldBorder().setSize((int) defaultConfig.GetValue("border-size"));

        SUpdateManager.Initialize(this);

        SUpdateManager.Register("updateWorldBorder", () ->
        {
            if(worldBorderControllers.keySet().isEmpty())
                return;

            for(Map.Entry<World, Entity> controller : worldBorderControllers.entrySet())
                controller.getKey().getWorldBorder().setCenter(controller.getValue().getLocation());
        });
    }

    @Override
    public void onDisable()
    {

    }

    public static LabBorder GetInstance()
    {
        return instance;
    }
}