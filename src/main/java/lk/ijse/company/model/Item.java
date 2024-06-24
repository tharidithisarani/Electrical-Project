package lk.ijse.company.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Item {

    private String code;
    private String name;
    private  String status;
    private String unitPrice;
    private String QTY;
    private String ID;

    public Item(int anInt, String string, String string1, BigDecimal bigDecimal, int anInt1, int anInt2) {
    }

    public String getUserID() {
        return null;
    }
}
