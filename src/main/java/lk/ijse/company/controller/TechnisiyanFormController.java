package lk.ijse.company.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class TechnisiyanFormController {

    @FXML
    private JFXButton btnNo;

    @FXML
    private JFXButton btnSalary;

    @FXML
    private JFXButton btnTechDetail;

    @FXML
    private JFXButton btnYes;

    @FXML
    private JFXComboBox<?> cmbTechName;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private Label lblId;

    @FXML
    private AnchorPane rootTechDashBoard;

    @FXML
    private AnchorPane rootTechnisiyan;

    @FXML
    private TableView<?> tblAttend;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtTechCode;

    @FXML
    void btnNo_OnAction(ActionEvent event) {

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
    void btnYes_OnAction(ActionEvent event) {

    }

}