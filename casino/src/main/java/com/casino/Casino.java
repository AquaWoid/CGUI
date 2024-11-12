package com.casino;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.casino.commands.cgui;
import com.casino.handlers.GuiListener;

/*
 * casino java plugin
 */
public class Casino extends JavaPlugin
{
  private static final Logger LOGGER=Logger.getLogger("casino");
  private GuiListener guiL;



  public void onEnable()
  {
    LOGGER.info("casino enabled");

    try {

      getCommand("cgui").setExecutor(new cgui(this));
      guiL = new GuiListener();
      getServer().getPluginManager().registerEvents(guiL, this);

    } catch(Exception e) {
        Bukkit.getLogger().info("Error:" + e);
    }


  }

  public GuiListener getGuiListener() {
    return guiL;
  }

  public void onDisable() 
  {
    LOGGER.info("casino disabled");
  }
}
