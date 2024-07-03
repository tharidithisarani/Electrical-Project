package lk.ijse.company.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DashboardFormController {

    @FXML
    private JFXButton btnConOrderTable;

    @FXML
    private JFXButton btnCustomer;

    @FXML
    private JFXButton btnHome;

    @FXML
    private JFXButton btnItem;

    @FXML
    private JFXButton btnLogOut;

    @FXML
    private JFXButton btnMachine;

    @FXML
    private JFXButton btnRegCustomerTable;

    @FXML
    private JFXButton btnSuppliyer;

    @FXML
    private JFXButton btnTechnisiyan;

    @FXML
    private JFXButton btnTechnisiyanTable;

    @FXML
    private Label lblConOrderCount;

    @FXML
    private Label lblInCome;

    @FXML
    private Label lblOutCome;

    @FXML
    private Label lblRegCoustomerCount;

    @FXML
    private Label lblTechnisiyanCount;

    @FXML
    private Pane paneConOrderCount;

    @FXML
    private Pane paneDashboardLode;

    @FXML
    private Pane paneInCome;

    @FXML
    private Pane paneMain;

    @FXML
    private Label paneOutCome;

    @FXML
    private Pane paneRegCustomerCount;

    @FXML
    private Pane paneTechnisiyanCount;

    @FXML
    private AnchorPane rootDashboard;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    void btnConOrderTableOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/conorder_detail.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Constructer Order Details Form");

    }

    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/customer.fxml"));
        Stage stage = (Stage) rootDashboard.getScene().getWindow();
        rootDashboard.getChildren().clear();
        rootDashboard.getChildren().add(rootNode);
        stage.setTitle("Item Form");
        stage.centerOnScreen();

    }

    @FXML
    void btnHomeOnAcrion(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Dashboard Form");

    }

    @FXML
    void btnItemOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/item.fxml"));
        Stage stage = (Stage) rootDashboard.getScene().getWindow();
        rootDashboard.getChildren().clear();
        rootDashboard.getChildren().add(rootNode);
        stage.setTitle("Item Form");
        stage.centerOnScreen();

    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/loging.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login Form");

    }

    @FXML
    void btnMachineOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/machine.fxml"));
        Stage stage = (Stage) rootDashboard.getScene().getWindow();
        rootDashboard.getChildren().clear();
        rootDashboard.getChildren().add(rootNode);
        stage.setTitle("Machine Form");
        stage.centerOnScreen();

    }

    @FXML
    void btnRegCustomerTableOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/regcustomer_detail.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Registed Customers Form");

    }

    @FXML
    void btnSuppliyerOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/supplier.fxml"));
        Stage stage = (Stage) rootDashboard.getScene().getWindow();
        rootDashboard.getChildren().clear();
        rootDashboard.getChildren().add(rootNode);
        stage.setTitle("Supplier Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnTechnisiyanOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/technisiyan.fxml"));
        Stage stage = (Stage) rootDashboard.getScene().getWindow();
        rootDashboard.getChildren().clear();
        rootDashboard.getChildren().add(rootNode);
        stage.setTitle("Technisiyan Form");
        stage.centerOnScreen();

    }

    @FXML
    void btnTechnisiyanTableOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/technisiyan.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Technician Details Form");

    }
    @FXML
    private void initialize() throws Exception {
        LocalTime time = LocalTime.now();
        LocalDate date = LocalDate.now();
        lblTime.setText(time.format(DateTimeFormatter.ofPattern("hh:mm")));
        lblDate.setText(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }


}
