package lk.ijse.company.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Order {
    private String orderId;
    private String customerId;

    public Order(String orderId, String itemCode, Date date) {
    }
//  private Date date;

}
