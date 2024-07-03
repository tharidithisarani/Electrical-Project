package lk.ijse.company.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class OrderDetailTm {
    private String code;
    private String description;
    private int qty;
    private BigDecimal unitPrice;
    private BigDecimal total;

}
