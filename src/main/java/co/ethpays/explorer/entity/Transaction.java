package co.ethpays.explorer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double amount;

    private String username;
    private String currency;
    private String fromWallet;
    private String toWallet;
    private String type; //deposit, withdraw, transfer, trade
    private String status; //pending, completed, failed
    private String transactionId;
    private String title;
    private Date createdAt;

    private boolean isEthpaysTransaction;

    public Transaction() {}

    public Transaction(double amount, String username, String currency, String fromWallet, String toWallet, String type, String status, String transactionId, Date createdAt, String title) {
        this.amount = amount;
        this.username = username;
        this.currency = currency;
        this.fromWallet = fromWallet;
        this.toWallet = toWallet;
        this.type = type;
        this.status = status;
        this.transactionId = transactionId;
        this.createdAt = createdAt;
        this.title = title;
    }
}
