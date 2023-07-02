package ru.bcs.perseus.bloomberg.model.db;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Setter
@Getter
public class CurrencySync {

    @Id
    private String currencyPair;
}
