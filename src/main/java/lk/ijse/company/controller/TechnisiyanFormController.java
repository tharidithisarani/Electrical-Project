package lk.ijse.company.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class TechnisiyanFormController {

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnTechDetail;

    @FXML
    private JFXComboBox<?> cmbAttend;

    @FXML
    private JFXComboBox<?> cmbName;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colAttendNo;

    @FXML
    private TableColumn<?, ?> colAttendense;

    @FXML
    private TableColumn<?, ?> colContace;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colNIC;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colNo;

    @FXML
    private TableColumn<?, ?> colTechID;

    @FXML
    private TableColumn<?, ?> colToolCode;

    @FXML
    private TableColumn<?, ?> conTechID;

    @FXML
    private TableColumn<?, ?> conname;

    @FXML
    private Label lblTechID;

    @FXML
    private Pane paneSummary;

    @FXML
    private AnchorPane rootTechDashBoard;

    @FXML
    private AnchorPane rootTechnisiyan;

    @FXML
    private TableView<?> tblAttendanseOK;

    @FXML
    private TableView<?> tblSummary;

    @FXML
    void btnAddOnAction(ActionEvent event) {

    }



    @FXML
    void btnTechDetailOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/tech_details.fxml"));
        Stage stage = (Stage) rootTechDashBoard.getScene().getWindow();
        rootTechDashBoard.getChildren().clear();
        rootTechDashBoard.getChildren().add(rootNode);
        stage.setTitle("Customer Form");
        stage.centerOnScreen();

    }

}
