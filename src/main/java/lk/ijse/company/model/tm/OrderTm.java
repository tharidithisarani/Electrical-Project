package lk.ijse.company.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class OrderTm {
    private String orderId;
    private String customerId;
    private LocalDate orderDate;
    private BigDecimal orderTotal;
}
