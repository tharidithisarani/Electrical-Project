package lk.ijse.company.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.company.database.DbConnection;
import lk.ijse.company.model.Attend;
import lk.ijse.company.model.Item;
import lk.ijse.company.model.TechDetail;
import lk.ijse.company.model.tm.AttendTM;
import lk.ijse.company.model.tm.OrderDetailTm;
import lk.ijse.company.model.tm.TechDetailTm;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Optional;

public class TechnisiyanFormController {

    @FXML
    private JFXButton btnSalary;

    @FXML
    private JFXButton btnTechDetail;

    @FXML
    private JFXComboBox<String> cmbTechCode;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private Label lblId;

    @FXML
    private AnchorPane rootTechDashBoard;

    @FXML
    private AnchorPane rootTechnisiyan;

    @FXML
    private TableView<AttendTM> tblAttend;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtName;

    @FXML
    private JFXButton btnAdd;

    private String attendId;


    public void initialize(){
        tblAttend.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("no"));
        tblAttend.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblAttend.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblAttend.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact"));
        TableColumn<AttendTM, Button> lastCol = (TableColumn<AttendTM, Button>) tblAttend.getColumns().get(4);

        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");

            btnDelete.setOnAction(event -> {
                tblAttend.getItems().remove(param.getValue());
                tblAttend.getSelectionModel().clearSelection();
            });

            return new ReadOnlyObjectWrapper<>(btnDelete);
        });

        attendId = generateNewAttendId();
        lblId.setText("Attend ID: " + attendId);
        txtContact.setFocusTraversable(false);
        txtContact.setEditable(false);
        txtName.setFocusTraversable(false);
        txtName.setDisable(false);
        btnAdd.setDisable(true);

        cmbTechCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            enableOrDisableAddButton();

            if (newValue != null) {
                try {
                    /*Search Technisiyan*/
                    Connection connection = DbConnection.getInstance().getConnection();
                    try {
                        if (!existTechnisiyan(newValue + "")) {
                            new Alert(Alert.AlertType.ERROR, "There is no such Technisiyan associated with the code " + newValue + "").show();
                        }

                        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM techdetail WHERE code=?");
                        pstm.setString(1, newValue + "");
                        ResultSet rst = pstm.executeQuery();
                        rst.next();
                        TechDetail techDetail = new TechDetail(newValue + "", rst.getString("name"), rst.getString("contact"));

                        txtName.setText(techDetail.getName());
                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR, "Failed to find the Technisiyan " + newValue + "" + e).show();
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else {
                txtName.clear();
                txtContact.clear();
            }
        });

        tblAttend.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectAttend) -> {

            if (selectAttend != null) {
                cmbTechCode.setDisable(true);
                cmbTechCode.setValue(selectAttend.getCode());
                btnAdd.setText("Update");
            } else {
                btnAdd.setText("Add");
                cmbTechCode.setDisable(false);
                cmbTechCode.getSelectionModel().clearSelection();
            }

        });

        loadAllTechCode();
    }

    private boolean existTechnisiyan(String code) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT code FROM techdetail WHERE code=?");
        pstm.setString(1, code);
        return pstm.executeQuery().next();
    }

    private void enableOrDisableAddButton() {
    }

    private void loadAllTechCode() {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM techdetail");

            while (rst.next()) {
                cmbTechCode.getItems().add(rst.getString("code"));
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load Technisiyan Codes").show();
        }
    }

    private boolean existAttend(String code) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT code FROM techdetail WHERE code=?");
        pstm.setString(1, code);
        return pstm.executeQuery().next();
    }


    private String generateNewAttendId() {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT code FROM `technisiyan` ORDER BY code DESC LIMIT 1;");

            return rst.next() ? String.format("ATID-%03d", (Integer.parseInt(rst.getString("code").replace("ATID-", "")) + 1)) : "ATID-001";
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new Attend code").show();
        }
        return "ATID-001";
    }

    @FXML
    void btnSalary_OnAction(ActionEvent event) {

    }

    @FXML
    void btnTechDetail_OnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/tech_details.fxml"));
        Stage stage = (Stage) rootTechDashBoard.getScene().getWindow();
        rootTechDashBoard.getChildren().clear();
        rootTechDashBoard.getChildren().add(rootNode);
        stage.setTitle("Customer Form");
        stage.centerOnScreen();
    }

    @FXML
    void txtYN_OnAction(ActionEvent event) {

    }

    @FXML
    void btnAdd_OnAction(ActionEvent event) {
        String techCode = cmbTechCode.getSelectionModel().getSelectedItem();
        String name = txtName.getText();
        String contact = txtContact.getText();

        boolean exists = tblAttend.getItems().stream().anyMatch(detail -> detail.getCode().equals(techCode));

        if (exists) {
            AttendTM attendTM = tblAttend.getItems().stream().filter(detail -> detail.getCode().equals(techCode)).findFirst().get();

            if (btnAdd.getText().equalsIgnoreCase("Update")) {
                attendTM.setCode(techCode);
                attendTM.setName(name);
                attendTM.setContact(contact);
                tblAttend.getSelectionModel().clearSelection();
            } else {
                System.out.println("i cant");
            }
            tblAttend.refresh();
        } else {
            tblAttend.getItems().add(new AttendTM(attendId,techCode, name, contact));
        }
        cmbTechCode.getSelectionModel().clearSelection();
        cmbTechCode.requestFocus();
    }

}