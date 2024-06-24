package lk.ijse.company.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class ItemTm {
    private String code;
    private String name;
    private String status;
    private String unitPrice;
    private String QTY;
    private String ID;

}
