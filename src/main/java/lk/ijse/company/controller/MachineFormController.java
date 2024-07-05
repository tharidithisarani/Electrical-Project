package lk.ijse.company.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.company.database.DbConnection;
import lk.ijse.company.model.tm.ItemTm;
import lk.ijse.company.model.tm.MachinTm;

import java.math.BigDecimal;
import java.sql.*;

public class MachineFormController {

    @FXML
    private JFXButton btnAddNewMachin;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private TableView<MachinTm> tblMachine;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtStatus;

    public void initialize() {
        tblMachine.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblMachine.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblMachine.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("status"));

        initUI();

        tblMachine.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtCode.setText(newValue.getCode());
                txtDescription.setText(newValue.getDescription());
                txtStatus.setText(newValue.getStatus());

                txtCode.setDisable(false);
                txtDescription.setDisable(false);
                txtStatus.setDisable(false);
            }
        });

        txtStatus.setOnAction(event -> btnSave.fire());
        loadAllMachines();
    }

    private void loadAllMachines() {
        tblMachine.getItems().clear();
        try {
            /*Get all Machine*/
            Connection connection = DbConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Machine");
            while (rst.next()) {
                tblMachine.getItems().add(new MachinTm(rst.getString("code"),
                        rst.getString("description"),
                        rst.getString("status")));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void initUI() {
        txtCode.clear();
        txtDescription.clear();
        txtStatus.clear();
        txtCode.setDisable(true);
        txtDescription.setDisable(true);
        txtStatus.setDisable(true);
        txtCode.setEditable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    @FXML
    void btnAddNew_OnAction(ActionEvent event) {
        txtCode.setDisable(false);
        txtDescription.setDisable(false);
        txtStatus.setDisable(false);
        txtCode.clear();
        txtCode.setText(generateNewId());
        txtDescription.clear();
        txtStatus.clear();
        txtDescription.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblMachine.getSelectionModel().clearSelection();
    }

    private String generateNewId() {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            ResultSet rst = connection.createStatement().executeQuery("SELECT code FROM Machine ORDER BY code DESC LIMIT 1;");
            if (rst.next()) {
                String id = rst.getString("code");
                int newMachibeId = Integer.parseInt(id.replace("M00-", "")) + 1;
                return String.format("M00-%03d", newMachibeId);
            } else {
                return "M00-001";
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return "M00-001";
    }

    @FXML
    void btnDelete_OnAction(ActionEvent event) {
        String code = tblMachine.getSelectionModel().getSelectedItem().getCode();
        try {
            if (!existMachine(code)) {
                new Alert(Alert.AlertType.ERROR, "There is no such Machine associated with the code " + code).show();
            }
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM Machine WHERE code=?");
            pstm.setString(1, code);
            pstm.executeUpdate();

            tblMachine.getItems().remove(tblMachine.getSelectionModel().getSelectedItem());
            tblMachine.getSelectionModel().clearSelection();
            initUI();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the Machine " + code).show();
        }
    }

    private boolean existMachine(String code) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT code FROM Machine WHERE code=?");
        pstm.setString(1, code);
        return pstm.executeQuery().next();
    }

    @FXML
    void btnSave_OnAction(ActionEvent event) {
        String code = txtCode.getText();
        String description = txtDescription.getText();
        String status = txtStatus.getText();

        /*if (!description.matches("[A-Za-z0-9 ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid description").show();
            txtDescription.requestFocus();
            return;
        } else if (!txtUnitPrice.getText().matches("^[0-9]+[.]?[0-9]*$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid unit price").show();
            txtUnitPrice.requestFocus();
            return;
        } else if (!txtQtyOnHand.getText().matches("^\\d+$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty on hand").show();
            txtQtyOnHand.requestFocus();
            return;
        }*/


        if (btnSave.getText().equalsIgnoreCase("save")) {
            try {
                if (existMachine(code)) {
                    new Alert(Alert.AlertType.ERROR, code + " already exists").show();
                }
                //Save machine
                Connection connection = DbConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement("INSERT INTO Machine (code, description, status) VALUES (?,?,?)");
                pstm.setString(1, code);
                pstm.setString(2, description);
                pstm.setString(3, status);
                pstm.executeUpdate();
                tblMachine.getItems().add(new MachinTm(code, description, status));

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        } else {
            try {
                if (!existMachine(code)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such Machine associated with the code " + code).show();
                }
                /*Update machine*/
                Connection connection = DbConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement("UPDATE Machine SET description=?, status=? WHERE code=?");
                pstm.setString(1, description);
                pstm.setString(2, status);
                pstm.setString(3, code);
                pstm.executeUpdate();

                MachinTm selectedMachin = tblMachine.getSelectionModel().getSelectedItem();

                selectedMachin.setDescription(description);
                selectedMachin.setStatus(status);
                tblMachine.refresh();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

        btnAddNewMachin.fire();
    }

}
