/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyorderappdesktop.ui.controller;

import easyorderappdesktop.businessLogic.BusinessLogicException;
import easyorderappdesktop.businessLogic.IdExistsException;
import easyorderappdesktop.businessLogic.ProductsManager;
import easyorderappdesktop.transferObject.ProductBean;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Optional;
import java.util.logging.Level;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;


/**
 *
 * @author Igor
 */
public class ProductController extends GenericController{
    /**
    * Product´s code(id) UI text field.
    */
    @FXML
    private TextField textCodigo;
    /**
    * Product´s unit price UI text field.
    */
    @FXML
    private TextField textPrecioUnidad;
    /**
    * Product´s name UI text field.
    */
    @FXML
    private TextField textNombre;
    /**
    * Product´s stock UI text field.
    */
    @FXML
    private TextField textStock;
    /**
    * Product´s filter UI text field.
    */
    @FXML
    private TextField textFiltrar;
    
    /**
     * Save product data button.
     */
    @FXML
    private Button btnGuardar;
    /**
     * Delete product data button.
     */
    @FXML
    private Button btnBorrar;
    /**
     * Cancel edit product data button.
     */
    @FXML
    private Button btnCancelar;
    /**
     * Filter product data button.
     */
    @FXML
    private Button btnFiltrar;
    
    /**
     * Filter product data button.
     */
    @FXML
    private Button btnActualizar;
    
    /**
    * Product's combo box.
    */
    @FXML
    private ComboBox cbFiltrar;
    
    /**
    * Product's data table view.
    */
    @FXML
    private TableView tbProductos;
    
    /**
    * Product's code table column.
    */
    @FXML
    private TableColumn colCodigo;
    /**
    * Product's name table column.
    */
    @FXML
    private TableColumn colNombre;
    /**
    * Product's unit price table column.
    */
    @FXML
    private TableColumn colPrecioUnidad;
    /**
    * Product's stock table column.
    */
    @FXML
    private TableColumn colStock;
    /**
     * Product's table data model.
     */
    @FXML
    private ObservableList<ProductBean> productsData;
    
    
    private TableRowSorter trsFiltro;
    
    
    
    
    /**
     * Method for initializing GestionUsuarios Stage. 
     * @param root The Parent object representing root node of view graph.
     */
    public void initStage(Parent root) {
        try{
            Scene scene = new Scene(root);
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Productos");
            stage.setResizable(false);
            
            stage.setOnShowing(this::handleWindowShowing);
            
            textCodigo.textProperty().addListener(this::handleTextChanged);
            textStock.textProperty().addListener(this::handleTextChanged);
            textPrecioUnidad.textProperty().addListener(this::handleTextChanged);
            textNombre.textProperty().addListener(this::handleTextChanged);
            textFiltrar.textProperty().addListener(this::handleTextChanged);
            
            btnGuardar.setOnAction(this::handleGuardarAction);
            btnActualizar.setOnAction(this::handleActualizarAction);
            btnBorrar.setOnAction(this::handleBorrarAction);
            btnCancelar.setOnAction(this::handleCancelarAction);
            btnFiltrar.setOnAction(this::handleFiltrarAction);
           
            ObservableList<String>filtro = 
                        FXCollections.observableArrayList("Id","Nombre");
            
            cbFiltrar.setItems(filtro);
            //cbFiltrar.getSelectionModel().select("");
            
            tbProductos.getSelectionModel().selectedItemProperty()
                    .addListener(this::handleProductsTableSelectionChanged);
            
            colCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            colPrecioUnidad.setCellValueFactory(new PropertyValueFactory<>("precioUnidad"));
            colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
            
            productsData=FXCollections.observableArrayList(productsManager.getAllProducts());
            
            tbProductos.setItems(productsData);
            
            stage.show();
            
        }catch(BusinessLogicException e){
            showErrorAlert("No se ha podido abrir la ventana.\n"+
                            e.getMessage());
        }
    }
    
    private void handleWindowShowing(WindowEvent event){
        LOGGER.info("Beginning ProductController::handleWindowShowing");
        
        btnGuardar.setDisable(true);
        btnActualizar.setDisable(true);
        btnBorrar.setDisable(true);
        btnCancelar.setDisable(true);
        //btnFiltrar.setDisable(true);
       
        cbFiltrar.getSelectionModel().selectFirst();
        
        textCodigo.setPromptText("Introduzca id del producto");
        textPrecioUnidad.setPromptText("Introduzca precio del producto");
        textNombre.setPromptText("Introduzca nombre del producto");
        textStock.setPromptText("Introduzca stock del producto");
        textFiltrar.setPromptText("Introduzca nombre o id del producto que busca");
        
       
        textCodigo.requestFocus();
        
    }
    
    /**
     * A focus change event event handler. This is an example that only logs a message.
     * @param observable the observable focus property.
     * @param oldValue the old boolean value for the property.
     * @param newValue the new boolean value for the property.
     */
    private void focusChanged(ObservableValue observable,
             Boolean oldValue,
             Boolean newValue){
        if(newValue)
            LOGGER.info("onFocus");
        else if(oldValue)
            LOGGER.info("onBlur");
    }
    
    /**
     * Text change event handler for login and name text fields.
     * It enables or disables create and modify buttons depending on those fields state.
     * @param observable the property being observed: TextProperty of TextField.
     * @param oldValue   old String value for the property.
     * @param newValue   new String value for the property.
     */
    private void handleTextChanged(ObservableValue observable,
             String oldValue,
             String newValue) {
        if(textNombre.getText().trim().length()>this.MAX_LENGTH ||
            textCodigo.getText().trim().length()>this.MAX_LENGTH ||
            textPrecioUnidad.getText().trim().length()>this.MAX_LENGTH ||
            textStock.getText().trim().length()>this.MAX_LENGTH ){
                showErrorAlert("La longitud máxima del campo es de 255 caracteres.");
                btnGuardar.setDisable(true);
                btnBorrar.setDisable(true);
                btnCancelar.setDisable(true);
                btnActualizar.setDisable(true);
                 
        }
        else if(textCodigo.getText().trim().isEmpty()||
            textNombre.getText().trim().isEmpty() ||
            textPrecioUnidad.getText().trim().isEmpty() ||
            textStock.getText().trim().isEmpty() ){
                textCodigo.setDisable(false);
                btnGuardar.setDisable(true);
                //btnBorrar.setDisable(true);
                btnCancelar.setDisable(false);
                //btnActualizar.setDisable(true);
                btnFiltrar.setDisable(false);
        } 
        else if(textCodigo.isDisable()){
            btnGuardar.setDisable(true);
            
        }
        else{
            btnGuardar.setDisable(false);
            btnBorrar.setDisable(true);
            btnCancelar.setDisable(false);
            //btnActualizar.setDisable(false);
            
        }
        
        
        /*if(textFiltrar.getText().trim().length()>this.MAX_LENGTH){
             btnFiltrar.setDisable(true);
        }
        else if(textCodigo.getText().trim().isEmpty()){
            btnFiltrar.setDisable(true);
        }
        else{
            btnFiltrar.setDisable(false);
        }*/
        
    
        //Set color for text in login to black (it can be red if it wasn't valid).
        textCodigo.setStyle("-fx-text-inner-color: black;");
    }
    
    
     private void handleProductsTableSelectionChanged(ObservableValue observable,
             Object oldValue,
             Object newValue) {
         
        //If there is a row selected, move row data to corresponding fields in the
        //window and enable create, modify and delete buttons
        if(newValue!=null){ 
            ProductBean product = (ProductBean)newValue;
            textCodigo.setText(String.valueOf(product.getId()));
            textNombre.setText(product.getNombre());
            textPrecioUnidad.setText(String.valueOf(product.getPrecioUnidad()));
            textStock.setText(String.valueOf(product.getStock()));
            
            textCodigo.setDisable(true);
            btnGuardar.setDisable(true);
            btnBorrar.setDisable(false);
            btnCancelar.setDisable(false);
            btnActualizar.setDisable(false);
            
        }else{
            textCodigo.setText("");
            textNombre.setText("");
            textPrecioUnidad.setText("");
            textStock.setText("");
            cbFiltrar.getSelectionModel().clearSelection();
            
           // btnGuardar.setDisable(true);
            btnBorrar.setDisable(true);
            btnCancelar.setDisable(true);
            
        }
        textCodigo.requestFocus();
     }
     
     
    /**
     * Action event handler for Guardar button. It validates new product data, send it
     * to the business logic tier and updates product table view with new product data.
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleGuardarAction(ActionEvent event){
        try{
            productsManager.isIdExisting(Integer.parseInt(textCodigo.getText().trim()));
 
            ProductBean product = new ProductBean(Integer.parseInt(textCodigo.getText()), 
                                        textNombre.getText(),
                                        Integer.parseInt(textPrecioUnidad.getText()), 
                                        Integer.parseInt(textStock.getText()));
            
            this.productsManager.createProduct(product); 
            
            tbProductos.getItems().add(product);
            
            textCodigo.setText("");
            textNombre.setText("");
            textPrecioUnidad.setText("");
            textStock.setText("");
            
            btnGuardar.setDisable(true);
            btnBorrar.setDisable(true);
            btnCancelar.setDisable(true);
            
        }catch(IdExistsException e){
            showErrorAlert("El id del producto ya existe.");
            textCodigo.requestFocus();
            textCodigo.setStyle("-fx-text-inner-color: red;");
            LOGGER.severe("El codigo(id) del producto ya existe.");
            
        }catch(BusinessLogicException e){
            showErrorAlert("Error al crear producto:\n"+
                            e.getMessage());
            LOGGER.log(Level.SEVERE,
                        "UI ProductController: Error creating product: {0}",
                        e.getMessage());
        }
    } 
     
    /** 
     * Action event handler for Actualizar button. It validates product data, send it
     * to the business logic tier and updates product table view with new product data.
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleActualizarAction(ActionEvent event){
         try{
            
            ProductBean selectedProduct = ((ProductBean)tbProductos.getSelectionModel()
                                                    .getSelectedItem());
            
            if(!selectedProduct.getId().equals(Integer.parseInt(textCodigo.getText()))){
                
                productsManager.isIdExisting(Integer.parseInt(textCodigo.getText().trim()));
                selectedProduct.setId(Integer.parseInt(textCodigo.getText().trim()));
               
            }
            
            selectedProduct.setNombre(textNombre.getText().trim());
            selectedProduct.setId(Integer.parseInt(textCodigo.getText().trim()));
            selectedProduct.setPrecioUnidad(Integer.parseInt(textPrecioUnidad.getText().trim()));
            selectedProduct.setStock(Integer.parseInt(textStock.getText().trim()));
            
           
            this.productsManager.updateProduct(selectedProduct);

            textCodigo.setText("");
            textNombre.setText("");
            textPrecioUnidad.setText("");
            textStock.setText("");
            
           // btnGuardar.setDisable(true);
            btnActualizar.setDisable(true);
           // btnBorrar.setDisable(true);
            //btnCancelar.setDisable(true);
            
            tbProductos.getSelectionModel().clearSelection();
            tbProductos.refresh();
            
        }catch(IdExistsException e){
            showErrorAlert("El ID del producto pertenece ya existe.");
            textCodigo.requestFocus();
            textCodigo.setStyle("-fx-text-inner-color: red;");
            
        }catch(BusinessLogicException e){
            showErrorAlert("Error al modificar producto:\n"+
                            e.getMessage());
            LOGGER.log(Level.SEVERE,
                        "UI ProductController: Error updating product: {0}",
                        e.getMessage());
        }
    }
    
    
    /**
     * Action event handler for delete button. It asks user for confirmation on delete,
     * sends delete message to the business logic tier and updates product table view.
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleBorrarAction(ActionEvent event){
        Alert alert=null;
        try{
            ProductBean selectedProduct = ((ProductBean) tbProductos
                    .getSelectionModel().getSelectedItem());
            
            //Ask for confirmation on delete
            alert=new Alert(Alert.AlertType.CONFIRMATION,
                                    "¿Borrar la fila seleccionada?\n"
                                    + "Esta operación no se puede deshacer.",
                                    ButtonType.OK,ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            
            //If OK to deletion
            if (result.isPresent() && result.get() == ButtonType.OK) {
                //delete product from server side
                this.productsManager.deleteProduct(selectedProduct);
                
                //removes selected item from table
                tbProductos.getItems().remove(selectedProduct);
                tbProductos.refresh();
                
                //clears editing fields
                textCodigo.setText("");
                textNombre.setText("");
                textPrecioUnidad.setText("");
                textStock.setText(""); 
                
                //btnGuardar.setDisable(true);
               // btnBorrar.setDisable(true);
               // btnCancelar.setDisable(true);
                btnActualizar.setDisable(true);
                //Clear selection and refresh table view 
                tbProductos.getSelectionModel().clearSelection();
                tbProductos.refresh();
            }
        }catch(BusinessLogicException e){
            //If there is an error in the business logic tier show message and log it.
            showErrorAlert("Error al borrar producto:\n"+
                            e.getMessage());
            LOGGER.log(Level.SEVERE,
                        "UI ProductController: Error deleting product: {0}",
                        e.getMessage());
        }
        
    
    }
    
    /**
     * Action event handler for Cancelar button. 
     * Clear id, Nombre, PrecioUnidad and Stock textFields.
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleCancelarAction(ActionEvent event){
        textCodigo.setText("");
        textNombre.setText("");
        textPrecioUnidad.setText("");
        textStock.setText("");
        textCodigo.setDisable(false);
        btnGuardar.setDisable(true);
        btnBorrar.setDisable(true);
        btnCancelar.setDisable(true);
        btnActualizar.setDisable(true);
    }
    
    
    
    @FXML
    private void handleFiltrarAction(ActionEvent event){
         
    
   
    
    }
     
}
