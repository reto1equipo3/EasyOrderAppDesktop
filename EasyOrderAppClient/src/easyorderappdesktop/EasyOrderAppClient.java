/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyorderappdesktop;


import easyorderappclient.businessLogic.ProductsManager;
import easyorderappclient.businessLogic.ProductsManagerFactory;
import static easyorderappclient.businessLogic.ProductsManagerFactory.REST_WEB_CLIENT_TYPE;
import easyorderappclient.ui.controller.ProductController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Igor
 */
public class EasyOrderAppClient extends Application {
    
     @Override
    public void start(Stage primaryStage) throws Exception {
        //Create Bussines Logic Controller to be passed to UI controllers
        ProductsManager bussinessLogicController=
                ProductsManagerFactory.createProductsManager(REST_WEB_CLIENT_TYPE);
        //Uncomment this sentence if you want fake data for testing the UI 
        //bussinessLogicController=
        //        UsersManagerFactory.createUsersManager(TEST_MOCK_TYPE);
        //Load node graph from fxml file
        FXMLLoader loader=new FXMLLoader(
                getClass().getResource("/easyorderappclient/ui/view/ProductDesktopFXMLDocument.fxml"));
        Parent root = (Parent)loader.load();
        //Get controller for graph 
        ProductController primaryStageController=
                ((ProductController)loader.getController());
        //Set a reference in UI controller para Bussiness Logic Controllesr
        primaryStageController.setProductsManager(bussinessLogicController);
        //Set a reference for Stage
        primaryStageController.setStage(primaryStage);
        //Initializes primary stage
        primaryStageController.initStage(root);
    }

    /**
     * Entry point for the Java application.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
