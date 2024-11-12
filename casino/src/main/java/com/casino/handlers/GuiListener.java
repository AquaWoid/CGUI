package com.casino.handlers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.casino.modules.BlackJack;

public class GuiListener implements Listener {

    private final Inventory inv;
    private final Map<UUID, Inventory> playerInventories = new HashMap<>();

    private BlackJack blackJack;

    public GuiListener() {
        inv = Bukkit.createInventory(null, 18, "Casino");
        initItems();
    }

    // Initialize Casino items (not player-specific)
    public void initItems() {
        inv.addItem(createGuiItem(Material.DIAMOND, "Black Jack", "§aClick to", "§bPlay Blackjack"));
    }

    // Helper method to create custom item stacks
    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(Arrays.asList(lore));
        item.setItemMeta(itemMeta);
        return item;
    }

    // Open Casino inventory
    public void openInventory(HumanEntity ent) {
        ent.openInventory(inv);
    }

    // Open player-specific BlackJack inventory
    public void openBlackJack(Player player) {
        Inventory invBlackJack = playerInventories.computeIfAbsent(player.getUniqueId(), uuid -> {
            Inventory newInv = Bukkit.createInventory(null, 18, "BJ");
            initBlackJackItems(newInv);
            return newInv;
        });
        player.openInventory(invBlackJack);
    }

    // Initialize BlackJack items specific to each player’s inventory
    private void initBlackJackItems(Inventory inv) {
        inv.setItem(0, createGuiItem(Material.GOLD_NUGGET, "Stake", "§a+ to", "§b1000"));
        inv.setItem(1, createGuiItem(Material.GOLD_NUGGET, "Stake", "§a- to", "§b1000"));
        inv.setItem(9, createGuiItem(Material.IRON_INGOT, "Draw", "§aClick to", "§bDraw"));
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (!e.getInventory().equals(inv) && !playerInventories.containsValue(e.getInventory())) return;

        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem == null || clickedItem.getType().isAir()) return;

        final Player p = (Player) e.getWhoClicked();

        // Check if player clicked BlackJack item in Casino inventory
        if (e.getInventory().equals(inv) && e.getRawSlot() == 0) {
            openBlackJack(p);  // Open BlackJack inventory specific to this player
        }

        if (e.getInventory().equals(playerInventories.get(p.getUniqueId()))) {
            // Handle BlackJack-specific inventory actions here, e.g., checking for stake items
            if (e.getRawSlot() == 0) {
                p.sendMessage("You clicked Stake -");
                blackJack.Stake(p, -1000);
            } else if (e.getRawSlot() == 1) {
                p.sendMessage("You clicked Stake +");
                blackJack.Stake(p, 1000);
            } else if (e.getRawSlot() == 9) {
                blackJack.DrawCard();
            }
        }

        p.sendMessage("Clicked slot: " + e.getRawSlot());
    }

    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {

       // Inventory BJINV = playerInventories.get(e.getWhoClicked().getUniqueId());

        if (e.getInventory().equals(inv) || e.getInventory().equals(playerInventories.get(e.getWhoClicked().getUniqueId()))) {
            e.setCancelled(true);
        }
    }



    

    // Optional: method to clear player's inventory upon exit or game end
    public void clearPlayerInventory(Player player) {
        playerInventories.remove(player.getUniqueId());
    }


    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
    Player player = (Player) event.getPlayer();
    clearPlayerInventory(player); 
    }
}
