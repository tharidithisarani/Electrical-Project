package lk.ijse.company.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class TechDetail {
    private String ID;
    private String TechID;
    private String toolCode;
    private String nic;
    private String name;
    private String address;
    private String contact;
    private String accountNum;
    private String banckName;
    private String description;
    private String basicSalary;
    private String otHours;
    private String otPayHoue;
    private String salaryID;
    private String fullSalary;
}
