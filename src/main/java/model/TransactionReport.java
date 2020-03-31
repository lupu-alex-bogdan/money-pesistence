package model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "Report containing all the transactions initiated by an user")
public class TransactionReport {
    @ApiModelProperty(notes = "Details about the person")
    private Person person;
    @ApiModelProperty(notes = "List of all the IBAN to IBAN transactions")
    private List<Transaction> ibanToIban;
    @ApiModelProperty(notes = "List of all the IBAN to Wallet transactions")
    private List<Transaction> ibanToWallet;
    @ApiModelProperty(notes = "List of all the Wallet to IBAN transactions")
    private List<Transaction> walletToIban;
    @ApiModelProperty(notes = "List of all the Wallet to Wallet transactions")
    private List<Transaction> walletToWallet;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Transaction> getIbanToIban() {
        return ibanToIban;
    }

    public void setIbanToIban(List<Transaction> ibanToIban) {
        this.ibanToIban = ibanToIban;
    }

    public List<Transaction> getIbanToWallet() {
        return ibanToWallet;
    }

    public void setIbanToWallet(List<Transaction> ibanToWallet) {
        this.ibanToWallet = ibanToWallet;
    }

    public List<Transaction> getWalletToIban() {
        return walletToIban;
    }

    public void setWalletToIban(List<Transaction> walletToIban) {
        this.walletToIban = walletToIban;
    }

    public List<Transaction> getWalletToWallet() {
        return walletToWallet;
    }

    public void setWalletToWallet(List<Transaction> walletToWallet) {
        this.walletToWallet = walletToWallet;
    }

}
