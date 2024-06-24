package lk.ijse.company.model.tm;

import com.jfoenix.controls.JFXBadge;
import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartTm {
    private String code;
    private String name;
    private double unitPrice;
    private int qty;
    private double total;
    private JFXButton btnRemove;
}
