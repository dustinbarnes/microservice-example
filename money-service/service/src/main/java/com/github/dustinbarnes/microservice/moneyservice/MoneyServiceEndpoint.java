package com.github.dustinbarnes.microservice.moneyservice;


import com.github.dustinbarnes.microservice.money.api.BalanceStatement;
import com.github.dustinbarnes.microservice.money.api.MoneyTransaction;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Random;


@RestController
@RequestMapping(value="/{user}/money", produces=MediaType.APPLICATION_JSON_VALUE)
public class MoneyServiceEndpoint {
    private static final Random random = new Random();

    @RequestMapping(method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BalanceStatement> addTransaction(@PathVariable @NotEmpty String user, @Valid MoneyTransaction transaction) {
        if ( user.startsWith("500") ) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else if ( user.startsWith("404") ) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new BalanceStatement(user, transaction.getAmount(), 0.00f), HttpStatus.CREATED);
        }
    }

    @RequestMapping(method=RequestMethod.GET)
    public BalanceStatement getBalance(@PathVariable @NotEmpty String user) {
        // Act like it's doing some real calculations
        try {
            Thread.sleep((long) getNumberInRange(2, 50));
        } catch ( InterruptedException e ) {
            // ignore
        }

        return new BalanceStatement(user, 100.00f, 0.00f);
    }

    private int getNumberInRange(int min, int max)
    {
        return min + (random.nextInt(max - min + 1));
    }
}
