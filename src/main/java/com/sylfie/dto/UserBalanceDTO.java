package com.sylfie.dto;

import java.math.BigDecimal;

public class UserBalanceDTO {
    private BigDecimal balance;
    private BigDecimal bonusBalance;

    public BigDecimal getBalance() {
        return balance;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    public BigDecimal getBonusBalance() {
        return bonusBalance;
    }
    public void setBonusBalance(BigDecimal bonusBalance) {
        this.bonusBalance = bonusBalance;
    }
}
