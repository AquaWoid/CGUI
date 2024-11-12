package com.casino.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.casino.Casino;
import com.casino.handlers.GuiListener;

public class cgui implements CommandExecutor{

    Casino c;

    public cgui(Casino cas) {
        c = cas;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            return true;
        }

        Player p = (Player) sender; 


        GuiListener gui = c.getGuiListener();



        gui.openInventory(p);
        p.sendMessage("CGUI Command Works as intended");


        return false;


    }

}
