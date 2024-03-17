package co.ethpays.explorer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "balance")
public class Balance {
    @Id
    private String address;
    private String username;
    private String currency;
    private String type; //spot, margin, futures

    private double balance; //total
    private double available; //available
    private double pending; //in process
    private double locked; //in trade

    private boolean isSystemAccount; //true if system account (trading, fees, etc)
    private boolean isEthpaysAccount; //true if ethpays account (employee, marketing, etc)
}
