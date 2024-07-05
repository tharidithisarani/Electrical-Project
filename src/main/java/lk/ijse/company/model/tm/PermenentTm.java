package lk.ijse.company.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class PermenentTm {
    private String code;
    private String name;
    private String address;
    private String contact;
    private String description;
}
