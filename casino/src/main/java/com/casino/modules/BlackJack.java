package com.casino.modules;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class BlackJack {

    
    Inventory invBlackJack;
    int stakeAmount = 1000;
    Player pp;

    int round = 1;

    private BlackJack instance;


    public BlackJack getInstance() {
        instance = this;
        return instance;
    }

    public BlackJack(Player player, Inventory invBJ) {
        invBlackJack = invBJ;
        pp = player;
    }


    public void PlayBlackJack(Player p) {

      //  Scoreboard scoreboard = p.getScoreboard();
       // Score money = scoreboard.getObjective("money").getScore(p.getName());      

    }

    public void DrawCard() {
        int rndOne = (int)(Math.random() * 10 + 1);
        int rndTwo = (int)(Math.random() * 10 + 1);

        invBlackJack.setItem(10, getCard(rndOne));
        invBlackJack.setItem(11, getCard(rndTwo));        
    }

    public void Stake(Player p, int amount) {

        Scoreboard scoreboard = p.getScoreboard();
        Score money = scoreboard.getObjective("money").getScore(p.getName());
        if(amount <= money.getScore() && stakeAmount >= 0) {

            stakeAmount += amount;
        }

        if(stakeAmount < 0) stakeAmount = 0;
    }

    private ItemStack getCard(int rnd) {
            //int rnd = (int)(Math.random() * 10 + 1);       
        final ItemStack item = new ItemStack(Material.PAPER, rnd);
        final ItemMeta itemMeta = item.getItemMeta();      
        
        itemMeta.setDisplayName("Card");
        itemMeta.setLore(Arrays.asList("Number ", String.valueOf(rnd)));
        item.setItemMeta(itemMeta);

        return item;

    }


}
