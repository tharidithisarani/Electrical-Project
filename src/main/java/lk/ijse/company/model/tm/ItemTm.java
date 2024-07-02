package lk.ijse.company.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class ItemTm {
    private String code;
    private String description;
    private BigDecimal unitPrice;
    private int qtyOnHand;

}
