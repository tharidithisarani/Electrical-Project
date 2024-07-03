package lk.ijse.company.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Order {
    private String orderId;
    private String customerId;
    private LocalDate orderDate;
    private double orderTotal;
}
