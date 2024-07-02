package lk.ijse.company.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Item implements Serializable {

    private String code;
    private String description;
    private BigDecimal unitPrice;
    private int qtyOnHand;
}
