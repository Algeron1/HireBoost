package com.hireboost.util;

import com.hireboost.model.enums.Currency;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Embeddable
@Data
public class MonetaryAmount implements Serializable {

    private BigDecimal amount;

    private Currency currency;

    @Override
    public String toString() {
        return amount + " " + currency;
    }
}
