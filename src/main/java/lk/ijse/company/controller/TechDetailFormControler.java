package lk.ijse.company.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import lk.ijse.company.model.Item;
import lk.ijse.company.model.TechDetail;
import lk.ijse.company.reposotory.ItemRepo;
import lk.ijse.company.reposotory.TechDetailRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TechDetailFormControler {

    @FXML
    private JFXButton btnAdd1;

    @FXML
    private JFXButton btnClear1;

    @FXML
    private JFXButton btnDelete1;

    @FXML
    private JFXButton btnUpdate1;

    @FXML
    private TextArea txtFullSalary;

    @FXML
    private TextArea txtSalaryID;

    @FXML
    private TextArea txtTechID;

    @FXML
    private TextArea txtUserID;

    @FXML
    private TextArea txtToolCode;

    @FXML
    private AnchorPane roorUpdateTechnisiyan;

    @FXML
    private AnchorPane rootSalary;

    @FXML
    private JFXTextArea txtAccountNum;

    @FXML
    private JFXTextArea txtAddress;

    @FXML
    private JFXTextArea txtBanckName;

    @FXML
    private JFXTextArea txtBasicSalary;

    @FXML
    private JFXTextArea txtContaceNum;

    @FXML
    private TextArea txtDescription;

    @FXML
    private JFXTextArea txtNIC;

    @FXML
    private JFXTextArea txtName;

    @FXML
    private JFXTextArea txtOThours;

    @FXML
    private JFXTextArea txtOTpayHour;
/*
    private List<TechDetail> techDetailList = new ArrayList<>();

    public void initialize() {
        this.techDetailList = getAllItem();
    }

    private List<TechDetail> getAllItem() {
        List<TechDetail> techDetailList = null;
        try {
            techDetailList = TechDetailRepo.getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return techDetailList;
    }

 */


    @FXML
    void btnAddOnAction(ActionEvent event) {

        String ID = "1"; // Set user ID to 1

        String TechID = txtTechID.getText();
        String toolCode = txtToolCode.getText();
        String nic = txtNIC.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContaceNum.getText();
        String accountNum = txtAccountNum.getText();
        String banckName = txtBanckName.getText();
        String description = txtDescription.getText();

        String basicSalary = txtBasicSalary.getText();
        String otPayHour = txtOTpayHour.getText();
        String otHours = txtOThours.getText();
        String salaryID = txtSalaryID.getText();
        String fullSalary = txtFullSalary.getText();

        TechDetail techDetail = new TechDetail(ID,TechID, toolCode, nic, name, address, contact, accountNum, banckName, description,basicSalary, otHours,otPayHour,salaryID, fullSalary);
/*
        try {
            boolean isAdd = TechDetailRepo.add(techDetail);
            if (isAdd){
                new Alert(Alert.AlertType.CONFIRMATION,"This" + TechID + "Technisiyan is SAVED !").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

 */

    }

    private void clearForm(){
        txtUserID.setText("");
        txtTechID.setText("");
        txtToolCode.setText("");
        txtNIC.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContaceNum.setText("");
        txtAccountNum.setText("");
        txtBanckName.setText("");
        txtDescription.setText("");
        txtOTpayHour.setText("");
        txtOThours.setText("");
        txtSalaryID.setText("");
        txtFullSalary.setText("");
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearForm();

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String nicc = txtNIC.getText();
/*
        try {
            boolean isDeleted = TechDetailRepo.delete(nicc);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "technisiyan  deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

 */

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        String ID = txtUserID.getText();
        String TechID = txtTechID.getText();
        String toolCode = txtToolCode.getText();
        String nic = txtNIC.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContaceNum.getText();
        String accountNum = txtAccountNum.getText();
        String banckName = txtBanckName.getText();
        String description = txtDescription.getText();

        String basicSalary = txtBasicSalary.getText();
        String otPayHour = txtOTpayHour.getText();
        String otHours = txtOThours.getText();
        String salaryID = txtSalaryID.getText();
        String fullSalary = txtFullSalary.getText();

        TechDetail techDetail = new TechDetail(ID,TechID, toolCode, nic, name, address, contact, accountNum, banckName, description,basicSalary, otHours,otPayHour,salaryID, fullSalary);
/*
        try {
            boolean isUpdated = TechDetailRepo.update(techDetail);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

 */

    }

    @FXML
    void txtNicOnAction(ActionEvent event) {
        String nic = txtNIC.getText();
/*
        try {
            TechDetail techDetail = TechDetailRepo.searchbyCode(nic);
            if (techDetail != null){
                txtUserID.setText(techDetail.getID());
                txtTechID.setText(techDetail.getTechID());
                txtToolCode.setText(techDetail.getToolCode());
                txtNIC.setText(techDetail.getNic());
                txtName.setText(techDetail.getName());
                txtAddress.setText(techDetail.getAddress());
                txtContaceNum.setText(techDetail.getContact());
                txtAccountNum.setText(techDetail.getAccountNum());
                txtBanckName.setText(techDetail.getBanckName());
                txtDescription.setText(techDetail.getDescription());
                txtBasicSalary.setText(techDetail.getBasicSalary());
                txtOThours.setText(techDetail.getOtHours());
                txtOTpayHour.setText(techDetail.getOtPayHoue());
                txtSalaryID.setText(techDetail.getSalaryID());
                txtFullSalary.setText(techDetail.getFullSalary());
            }else {
                new Alert(Alert.AlertType.ERROR,"This" + nic + " NIC number is new, You can't Search it !").show();

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

 */
    }
}
