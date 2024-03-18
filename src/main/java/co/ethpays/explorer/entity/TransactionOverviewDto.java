package co.ethpays.explorer.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TransactionOverviewDto {
    String  transactionId;
    String status;
    String fromWallet;
    String toWallet;
    String currency;
    String type;
    String fromEthpaysUsername;
    String toEthpaysUsername;

    Date createdAt;

    double amount;

    boolean fromIsEthpaysAccount;
    boolean toIsEthpaysAccount;
}
