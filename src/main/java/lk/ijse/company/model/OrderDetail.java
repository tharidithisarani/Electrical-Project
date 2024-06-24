package lk.ijse.company.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class OrderDetail {
    private String orderId;
    private String itemCode;
    private int qty;
    private double unitPrice;

//    public OrderDetail(String orderId, String code, String qty, String unitPrice) {
//    }
}
