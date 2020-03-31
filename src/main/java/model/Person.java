package model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@ApiModel(description = "Details about the person")
public class Person {
    @Id
    @ApiModelProperty(notes = "The unique identifier for each person in Romanian format (similar to Social Security Number)")
    private long cnp;
    @ApiModelProperty(notes = "The person's name")
    private String name;
    @ApiModelProperty(notes = "The person's IBAN (International Bank Account Number)")
    private String iban;

    public long getCnp() {
        return cnp;
    }

    public void setCnp(long cnp) {
        this.cnp = cnp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
}
