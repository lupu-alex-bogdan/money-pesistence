package model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@ApiModel(description = "Details about the transaction")
@Table(name="Transactions")
public class Transaction {
    @Id
    @ApiModelProperty(notes = "The unique id of the transaction.")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ApiModelProperty(notes = "The type of the transaction")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @ApiModelProperty(notes = "The description of the transaction")
    private String description;
    @ApiModelProperty(notes = "The amount that was transacted")
    private double amount;
    @OneToOne
    @ApiModelProperty(notes = "The person who sent the money")
    private Person payer;
    @OneToOne
    @ApiModelProperty(notes = "The person who received the money")
    private Person payee;

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Person getPayer() {
        return payer;
    }

    public void setPayer(Person payer) {
        this.payer = payer;
    }

    public Person getPayee() {
        return payee;
    }

    public void setPayee(Person payee) {
        this.payee = payee;
    }
}