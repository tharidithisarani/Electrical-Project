package lk.ijse.company.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Supplier {
    private String itemCode;
    private String companyName;
    private String supplierName;
    private String description;
    private String email;
    private String contact;
    private String qty;

}
