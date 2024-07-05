package lk.ijse.company.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.company.database.DbConnection;
import lk.ijse.company.model.tm.ItemTm;
import lk.ijse.company.model.tm.TechDetailTm;

import java.math.BigDecimal;
import java.sql.*;

public class TechDetailFormControler {

    @FXML
    private JFXButton btnAddNewTechnisiyan;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private AnchorPane roorUpdateTechnisiyan;

    @FXML
    private TextField txtAccountNum;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtBanckName;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtNIC;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTechCode;

    @FXML
    private TextField txtToolCode;

    @FXML
    private TableView<TechDetailTm> tblTechDetail;

    public void initialize() {
        tblTechDetail.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblTechDetail.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("nic"));
        tblTechDetail.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblTechDetail.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblTechDetail.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblTechDetail.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("bankName"));
        tblTechDetail.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("accountNum"));
        tblTechDetail.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("toolCode"));
        tblTechDetail.getColumns().get(8).setCellValueFactory(new PropertyValueFactory<>("description"));

        initUI();

        tblTechDetail.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtTechCode.setText(newValue.getCode());
                txtNIC.setText(newValue.getNic());
                txtName.setText(newValue.getName());
                txtAddress.setText(newValue.getAddress());
                txtContact.setText(newValue.getContact());
                txtBanckName.setText(newValue.getBankName());
                txtAccountNum.setText(newValue.getAccountNum());
                txtToolCode.setText(newValue.getToolCode());
                txtDescription.setText(newValue.getDescription());

                txtTechCode.setDisable(false);
                txtNIC.setDisable(false);
                txtName.setDisable(false);
                txtAddress.setDisable(false);
                txtContact.setDisable(false);
                txtBanckName.setDisable(false);
                txtAccountNum.setDisable(false);
                txtToolCode.setDisable(false);
                txtDescription.setDisable(false);
            }
        });

        txtDescription.setOnAction(event -> btnSave.fire());
        loadAllTechDetails();
    }

    private void loadAllTechDetails() {
        tblTechDetail.getItems().clear();
        try {
            /*get all Technisiyan Details*/
            Connection connection = DbConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM techDetail");
            while (rst.next()){
                tblTechDetail.getItems().add(new TechDetailTm(rst.getString("code"),
                        rst.getString("NIC"),
                        rst.getString("name"),
                        rst.getString("address"),
                        rst.getString("contact"),
                        rst.getString("bankname"),
                        rst.getString("accountNum"),
                        rst.getString("toolCode"),
                        rst.getString("description")));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void initUI() {
        txtTechCode.clear();
        txtNIC.clear();
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
        txtBanckName.clear();
        txtAccountNum.clear();
        txtToolCode.clear();
        txtDescription.clear();

        txtTechCode.setDisable(true);
        txtNIC.setDisable(true);
        txtName.setDisable(true);
        txtAddress.setDisable(true);
        txtContact.setDisable(true);
        txtBanckName.setDisable(true);
        txtAccountNum.setDisable(true);
        txtToolCode.setDisable(true);
        txtDescription.setDisable(true);

        txtTechCode.setEditable(false);
        txtToolCode.setEditable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    @FXML
    void btnAddNew_OnAction(ActionEvent event) {
        txtTechCode.setDisable(false);
        txtNIC.setDisable(false);
        txtName.setDisable(false);
        txtAddress.setDisable(false);
        txtContact.setDisable(false);
        txtBanckName.setDisable(false);
        txtAccountNum.setDisable(false);
        txtToolCode.setDisable(false);
        txtDescription.setDisable(false);

        txtTechCode.clear();
        txtTechCode.setText(generateNewId());
        txtNIC.clear();
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
        txtBanckName.clear();
        txtAccountNum.clear();
        txtToolCode.clear();
        txtToolCode.setText(generateNewCode());
        txtDescription.clear();

        txtNIC.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblTechDetail.getSelectionModel().clearSelection();
    }

    private String generateNewId() {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            ResultSet rst = connection.createStatement().executeQuery("SELECT code FROM techDetail ORDER BY code DESC LIMIT 1;");
            if (rst.next()) {
                String id = rst.getString("code");
                int newTechDetailId = Integer.parseInt(id.replace("TD-000-", "")) + 1;
                return String.format("TD-000-%03d", newTechDetailId);
            } else {
                return "TD-000-001";
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return "TD-000-001";
    }

    private String generateNewCode() {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            ResultSet rst = connection.createStatement().executeQuery("SELECT toolCode FROM techDetail ORDER BY toolCode DESC LIMIT 1;");
            if (rst.next()) {
                String id = rst.getString("toolCode");
                int newToolCoed = Integer.parseInt(id.replace("Code-000-", "")) + 1;
                return String.format("Code-000-%03d", newToolCoed);
            } else {
                return "Code-000-001";
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return "Code-000-001";
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String NIC = tblTechDetail.getSelectionModel().getSelectedItem().getNic();
        try {
            if (!existTechDetail(NIC)) {
                new Alert(Alert.AlertType.ERROR, "There is no such Technisiyan associated with the NIC no " + NIC).show();
            }
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM techDetail WHERE NIC=?");
            pstm.setString(1, NIC);
            pstm.executeUpdate();

            tblTechDetail.getItems().remove(tblTechDetail.getSelectionModel().getSelectedItem());
            tblTechDetail.getSelectionModel().clearSelection();
            initUI();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the Technisiyan " + NIC).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String code = txtTechCode.getText();
        String NIC = txtNIC.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String bankName = txtBanckName.getText();
        String accountNum = txtAccountNum.getText();
        String toolCode = txtToolCode.getText();
        String description = txtDescription.getText();

        if (!description.matches("[A-Za-z0-9 ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid description").show();
            txtDescription.requestFocus();
            return;
        }


        if (btnSave.getText().equalsIgnoreCase("save")) {
            try {
                if (existTechDetail(NIC)) {
                    new Alert(Alert.AlertType.ERROR, NIC + " already exists").show();
                }
                //Save Item
                Connection connection = DbConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement("INSERT INTO techDetail (code, NIC, name, address, contact, bankName, accountNum, toolCode, description) VALUES (?,?,?,?,?,?,?,?,?)");
                pstm.setString(1, code);
                pstm.setString(2, NIC);
                pstm.setString(3, name);
                pstm.setString(4, address);
                pstm.setString(5, contact);
                pstm.setString(6, bankName);
                pstm.setString(7, accountNum);
                pstm.setString(8, toolCode);
                pstm.setString(9, description);
                pstm.executeUpdate();
                tblTechDetail.getItems().add(new TechDetailTm(code, NIC, name, address, contact, bankName, accountNum, toolCode, description));

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        } else {
            try {

                if (!existTechDetail(NIC)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such Technisiyan associated with the NIC no " + NIC).show();
                }
                /*Update Item*/
                Connection connection = DbConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement("UPDATE techDetail SET name=?, address=?, contact=?, bankName=?, accountNum=?, description=?, code=?, toolCode=? WHERE NIC=?");
                pstm.setString(1, name);
                pstm.setString(2, address);
                pstm.setString(3, contact);
                pstm.setString(4, bankName);
                pstm.setString(5, accountNum);
                pstm.setString(6, description);
                pstm.setString(7, NIC);
                pstm.setString(8, code);
                pstm.setString(9, toolCode);
                pstm.executeUpdate();

                TechDetailTm selectedItem = tblTechDetail.getSelectionModel().getSelectedItem();

                selectedItem.setName(name);
                selectedItem.setAddress(address);
                selectedItem.setContact(contact);
                selectedItem.setBankName(bankName);
                selectedItem.setAccountNum(accountNum);
                selectedItem.setDescription(description);
                selectedItem.setNic(NIC);
                selectedItem.setCode(code);
                selectedItem.setToolCode(toolCode);
                tblTechDetail.refresh();

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

        btnAddNewTechnisiyan.fire();
    }

    private boolean existTechDetail(String nic) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT NIC FROM techDetail WHERE NIC=?");
        pstm.setString(1, nic);
        return pstm.executeQuery().next();
    }

}
