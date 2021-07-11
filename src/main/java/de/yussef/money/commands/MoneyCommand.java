package de.yussef.money.commands;

import de.yussef.money.Money;
import de.yussef.money.manager.MoneyManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MoneyCommand implements CommandExecutor {

    private final MoneyManager moneyManager = new MoneyManager();

    private void sendPlayerMoney(Player player) {
        player.sendMessage(Money.MONEY_PREFIX + "Du besitzt zurzeit §3§l" + String.format("%,d", moneyManager.getMoney(player.getUniqueId().toString())) + "$ §7Dollar.");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof final Player player)) {
            Bukkit.getConsoleSender().sendMessage("Dies kannst du als Konsole nicht.");
            return false;
        }

        sendPlayerMoney(player);
        return false;
    }
}