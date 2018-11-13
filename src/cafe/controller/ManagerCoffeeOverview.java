package cafe.controller;

import cafe.MainApp;
import cafe.model.Coffee;
import cafe.model.CoffeeHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Callback;

import java.util.Iterator;

public class ManagerCoffeeOverview {

    @FXML
    private TableView<Coffee> coffeeTable;
    @FXML
    private TableColumn<Coffee, String> coffeeName;
    @FXML
    private TableColumn<Coffee, Integer> coffeePrice;



    private CoffeeHandler coffeeHandler;

    private MainApp mainApp;



    @FXML
    private void initialize() {

        //initialize cell datas

        coffeeName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        coffeePrice.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());


        setCoffeeList();
        addButtonToCoffeeTable();


    }


    public void setCoffeeList() {
        this.coffeeHandler =  new CoffeeHandler();
        this.coffeeTable.setItems(coffeeHandler.getCoffees());
    }


    /**
     * if okClicked == 1 -> ok button is clicked
     * if okClicked == 2 -> save as new menu
     */


    @FXML
    private void handleNewCoffee() {
        Coffee temp = new Coffee();
        int okClicked = mainApp.showCoffeeEditDialog(temp, false);
        if (okClicked == 2) {
            coffeeHandler.getCoffees().add(temp);
        }
    }

    // Event of order button from callback function


    private void handleEdit(Coffee clicked) {
        if (clicked != null) {
            Coffee temp = new Coffee();
            temp.setName(clicked.getName());
            temp.setPrice(clicked.getPrice());
            temp.getIngreList().addAll(clicked.getIngreList());
            int okClicked = mainApp.showCoffeeEditDialog(temp, true);
            if (okClicked == 1) {
                clicked.setName(temp.getName());
                clicked.setPrice(temp.getPrice());
                clicked.getIngreList().clear();
                clicked.getIngreList().addAll(temp.getIngreList());
            } else if (okClicked == 2) {
                coffeeHandler.getCoffees().add(temp);
            }
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            //alert.initOwner(mainApp.getPrimaryStage());//왜오류떠?
            alert.setTitle("No Selection");
            alert.setHeaderText("No Coffee Selected");
            alert.setContentText("Please select an Coffee");
            alert.showAndWait();
        }
    }


    private void addButtonToCoffeeTable() {
        TableColumn<Coffee, Void> colBtn = new TableColumn("");
        Callback<TableColumn<Coffee, Void>, TableCell<Coffee, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Coffee, Void> call(final TableColumn<Coffee, Void> param) {
                final TableCell<Coffee, Void> cell = new TableCell<>() {
                    private final Button actionBtn = new Button("Edit");

                    {
                        actionBtn.setOnAction((ActionEvent event) -> {
                            Coffee clicked = getTableView().getItems().get(getIndex());
                            handleEdit(clicked);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(actionBtn);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        coffeeTable.getColumns().add(colBtn);
    }


}
