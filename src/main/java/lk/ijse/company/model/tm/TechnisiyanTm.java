package lk.ijse.company.model.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class TechnisiyanTm {
    private String no;
    private String tech_id;
    private String name;
    private String contact;
    private String nic;
    private String tool_code;
    private JFXButton btnRemove;

}
