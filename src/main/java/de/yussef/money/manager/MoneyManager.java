package de.yussef.money.manager;

import de.yussef.money.Money;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MoneyManager {

    public boolean hasMoney(String playerUUID) {
        try {
            ResultSet resultSet = Money.getAsyncMySQL().prepare("SELECT * FROM MoneySystem WHERE PlayerUUID='"+playerUUID+"'").executeQuery();
            return resultSet.next();
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    private void newMoney(String playerUUID) {
        Money.getAsyncMySQL().update("INSERT INTO MoneySystem (PlayerUUID, Money) VALUES ('"+playerUUID+"', '1000');");
    }

    public Integer getMoney(String playerUUID) {
        if(hasMoney(playerUUID)) {
            try {
                ResultSet resultSet = Money.getAsyncMySQL().prepare("SELECT * FROM MoneySystem WHERE PlayerUUID='"+playerUUID+"'").executeQuery();

                while(resultSet.next()) {
                    return resultSet.getInt("Money");
                }
            } catch(SQLException exception) {
                exception.printStackTrace();
            }
        } else {
            newMoney(playerUUID);
        }
        return 1000;
    }

    public void addMoney(String playerUUID, int moneyAmount) {
        if(hasMoney(playerUUID)) {
            if(moneyAmount < 0) {
                moneyAmount = 0;
            }

            final int totalMoney = getMoney(playerUUID) + moneyAmount;
            Money.getAsyncMySQL().update("UPDATE MoneySystem SET Money='"+totalMoney+"' WHERE PlayerUUID='"+playerUUID+"'");
        } else {
            newMoney(playerUUID);
        }
    }

    public void removeMoney(String playerUUID, int moneyAmount) {
        if(hasMoney(playerUUID)) {
            if(moneyAmount < 0) {
                moneyAmount = 0;
            }

            final int totalMoney = getMoney(playerUUID) - moneyAmount;
            Money.getAsyncMySQL().update("UPDATE MoneySystem SET Money='"+totalMoney+"' WHERE PlayerUUID='"+playerUUID+"'");
        } else {
            newMoney(playerUUID);
        }
    }

    public void setMoney(String playerUUID, int moneyAmount) {
        if(hasMoney(playerUUID)) {
            if(moneyAmount < 0) {
                moneyAmount = 0;
            }

            final int totalMoney = moneyAmount;
            Money.getAsyncMySQL().update("UPDATE MoneySystem SET Money='"+totalMoney+"' WHERE PlayerUUID='"+playerUUID+"'");
        } else {
            newMoney(playerUUID);
        }
    }

    public void clearMoneyList() {
        Money.getAsyncMySQL().update("TRUNCATE TABLE MoneySystem");
    }
}