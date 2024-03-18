package co.ethpays.explorer.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SimpleTransactionDto {
    String  transactionId;
    String currency;

    double amount;
    double amountInUsd;

}
