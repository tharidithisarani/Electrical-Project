package lk.ijse.company.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class TechDetail {
    private String code;
    private String nic;
    private String name;
    private String address;
    private String contact;
    private String bankName;
    private String accountNum;
    private String toolCode;
    private String description;

    public TechDetail(String s, String name, String contact) {
    }
}
