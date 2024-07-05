package lk.ijse.company.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lk.ijse.company.database.DbConnection;
import lk.ijse.company.model.tm.ItemTm;
import lk.ijse.company.model.tm.PermenentTm;

import java.math.BigDecimal;
import java.sql.*;

public class PermenentBuyerFormController {

    @FXML
    private JFXButton btnAddNewCustomer;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime1;

    @FXML
    private Pane paneBack;

    @FXML
    private AnchorPane rootPermanentBuyerLode;

    @FXML
    private AnchorPane rootPermenent;

    @FXML
    private TableView<PermenentTm> tblCustomer;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtRegCode;

    public void initialize() {
        tblCustomer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblCustomer.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustomer.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblCustomer.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblCustomer.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("description"));

        initUI();

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtRegCode.setText(newValue.getCode());
                txtName.setText(newValue.getName());
                txtAddress.setText(newValue.getAddress());
                txtContact.setText(newValue.getContact());
                txtDescription.setText(newValue.getDescription());

                txtRegCode.setDisable(false);
                txtName.setDisable(false);
                txtAddress.setDisable(false);
                txtContact.setDisable(false);
                txtDescription.setDisable(false);
            }
        });

        txtDescription.setOnAction(event -> btnSave.fire());
        loadAllPermenetCustomer();
    }

    private void loadAllPermenetCustomer() {
        tblCustomer.getItems().clear();
        try {
            /*Get all permenent customer*/
            Connection connection = DbConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM permenentCus");
            while (rst.next()) {
                tblCustomer.getItems().add(new PermenentTm(rst.getString("code"),
                        rst.getString("name"),
                        rst.getString("address"),
                        rst.getString("contact"),
                        rst.getString("description")));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void initUI() {
        txtRegCode.clear();
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
        txtDescription.clear();

        txtRegCode.setDisable(true);
        txtName.setDisable(true);
        txtAddress.setDisable(true);
        txtContact.setDisable(true);
        txtDescription.setDisable(true);

        txtRegCode.setEditable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    @FXML
    void btnAddNew_OnAction(ActionEvent event) {
        txtRegCode.setDisable(false);
        txtName.setDisable(false);
        txtAddress.setDisable(false);
        txtContact.setDisable(false);
        txtDescription.setDisable(false);

        txtRegCode.clear();
        txtRegCode.setText(generateNewId());
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
        txtDescription.clear();

        txtName.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblCustomer.getSelectionModel().clearSelection();
    }

    private String generateNewId() {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            ResultSet rst = connection.createStatement().executeQuery("SELECT code FROM permenentCus ORDER BY code DESC LIMIT 1;");
            if (rst.next()) {
                String id = rst.getString("code");
                int newId = Integer.parseInt(id.replace("CUS00-", "")) + 1;
                return String.format("CUS00-%03d", newId);
            } else {
                return "CUS00-001";
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return "CUS00-001";
    }

    @FXML
    void btnDelete_OnAction(ActionEvent event) {
        String code = tblCustomer.getSelectionModel().getSelectedItem().getCode();
        try {
            if (!existPermenentCustomer(code)) {
                new Alert(Alert.AlertType.ERROR, "There is no such permenent customer associated with the code " + code).show();
            }
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM permenentCus WHERE code=?");
            pstm.setString(1, code);
            pstm.executeUpdate();

            tblCustomer.getItems().remove(tblCustomer.getSelectionModel().getSelectedItem());
            tblCustomer.getSelectionModel().clearSelection();
            initUI();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the permenent customer " + code).show();
        }
    }

    private boolean existPermenentCustomer(String code) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT code FROM permenentCus WHERE code=?");
        pstm.setString(1, code);
        return pstm.executeQuery().next();
    }

    @FXML
    void btnSave_OnAction(ActionEvent event) {
        String code = txtRegCode.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String description = txtDescription.getText();

        if (!description.matches("[A-Za-z0-9 ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid description").show();
            txtDescription.requestFocus();
            return;
        /*} else if (!txtUnitPrice.getText().matches("^[0-9]+[.]?[0-9]*$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid unit price").show();
            txtUnitPrice.requestFocus();
            return;
        } else if (!txtQtyOnHand.getText().matches("^\\d+$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty on hand").show();
            txtQtyOnHand.requestFocus();
            return;*/
        }


        if (btnSave.getText().equalsIgnoreCase("save")) {
            try {
                if (existPermenentCustomer(code)) {
                    new Alert(Alert.AlertType.ERROR, code + " already exists").show();
                }
                //Save customer
                Connection connection = DbConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement("INSERT INTO permenentCus (code, name, address, contact, description) VALUES (?,?,?,?,?)");
                pstm.setString(1, code);
                pstm.setString(2, name);
                pstm.setString(3, address);
                pstm.setString(4, contact);
                pstm.setString(5, description);
                pstm.executeUpdate();
                tblCustomer.getItems().add(new PermenentTm(code, name, address, contact, description));

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        } else {
            try {
                if (!existPermenentCustomer(code)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such permenent customer associated with the id " + code).show();
                }
                /*Update customer*/
                Connection connection = DbConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement("UPDATE permenentCus SET name=?, address=?, contact=?, description=? WHERE code=?");
                pstm.setString(1, name);
                pstm.setString(2, address);
                pstm.setString(3, contact);
                pstm.setString(4, description);
                pstm.setString(5, code);
                pstm.executeUpdate();

                PermenentTm selectedCustomer = tblCustomer.getSelectionModel().getSelectedItem();

                selectedCustomer.setName(name);
                selectedCustomer.setAddress(address);
                selectedCustomer.setContact(contact);
                selectedCustomer.setDescription(description);
                tblCustomer.refresh();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

        btnAddNewCustomer.fire();
    }

}
