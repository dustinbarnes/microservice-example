package com.github.dustinbarnes.microservice.money.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceStatement {
    String userid;
    double balance;
    double availableBalance;
}
