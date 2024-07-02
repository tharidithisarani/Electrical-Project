package lk.ijse.company.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class SupplierTm {
    private String code;
    private String name;
    private String contact;
    private String email;
    private String description;
}
