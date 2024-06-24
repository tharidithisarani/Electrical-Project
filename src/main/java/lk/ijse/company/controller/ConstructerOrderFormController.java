package lk.ijse.company.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ConstructerOrderFormController {

    @FXML
    private JFXButton btnBack;

    @FXML
    private ChoiceBox<?> choiceTechnisiyan;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colAdvanse;

    @FXML
    private TableColumn<?, ?> colConstructerID;

    @FXML
    private TableColumn<?, ?> colFullPayment;

    @FXML
    private TableColumn<?, ?> colItemNo;

    @FXML
    private TableColumn<?, ?> colLocation;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colRemainingPayment;

    @FXML
    private TableColumn<?, ?> colTecCount;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblConId;

    @FXML
    private Label lblConPayID;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblID;

    @FXML
    private Label lblName;

    @FXML
    private Label lblRePay;

    @FXML
    private Label lblTechCount;

    @FXML
    private AnchorPane rootConOrder;

    @FXML
    private TableView<?> tblOrderCart;

    @FXML
    private TextField txtAdwance;

    @FXML
    private TextField txtContact;

    @FXML
    private TextArea txtDescription;

    @FXML
    private TextField txtFullPayment;

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootConOrder.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");

    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

    }

}
