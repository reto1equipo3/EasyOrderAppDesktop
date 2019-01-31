/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyorderappclient.ui.controller;

import easyorderappclient.businessLogic.BusinessLogicException;
import easyorderappclient.businessLogic.IdExistsException;
import easyorderappclient.transferObject.ProductBean;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;


/**
 *
 * @author Igor
 */
public class ProductController extends GenericController{
    
    @FXML
    private Menu menu;
    
    
    /**
    * Product´s code(id) UI text field.
    */
    @FXML
    private TextField textId;
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
    private Button btnEliminar;
    /**
     * Cancel edit product data button.
     */
    @FXML
    private Button btnCancelar;
    
    /**
     * Update product data button.
     */
    @FXML
    private Button btnActualizar;
    
    /**
     * Filter product data button.
     */
    @FXML
    private Button btnFiltrar;
    
    /**
     * Create Product Report.
     */
    @FXML
    private Button btnReport;
    
    /**
    * Combobox with options to find a product.
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
    
    private TableRowSorter trsfiltro;
    
    /**
     * Method for initializing ProductController Stage. 
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
            
            textId.textProperty().addListener(this::handleTextChanged);
            textStock.textProperty().addListener(this::handleTextChanged);
            textPrecioUnidad.textProperty().addListener(this::handleTextChanged);
            textNombre.textProperty().addListener(this::handleTextChanged);
            textFiltrar.textProperty().addListener(this::handleTextChanged);
            
            textId.focusedProperty().addListener(this::focusChanged);
            
            btnGuardar.setOnAction(this::handleGuardarAction);
            btnActualizar.setOnAction(this::handleActualizarAction);
            btnEliminar.setOnAction(this::handleEliminarAction);
            btnCancelar.setOnAction(this::handleCancelarAction);
            btnFiltrar.setOnAction(this::handleFiltrarAction);
            btnReport.setOnAction(this::handleReportAction);
            
            ObservableList<String> opciones = FXCollections.observableArrayList("ID", "NOMBRE","TODOS LOS PRODUCTOS");
            cbFiltrar.setItems(opciones);
            //Seleccionar un estado por defecto
            //cbFiltrar.getSelectionModel().select("");
            
            tbProductos.getSelectionModel().selectedItemProperty()
                    .addListener(this::handleProductsTableSelectionChanged);
            
            colCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            colPrecioUnidad.setCellValueFactory(new PropertyValueFactory<>("precioUnidad"));
            colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
           
            productsData=FXCollections.observableArrayList(productsManager.getAllProducts());
            tbProductos.setItems(productsData);
            
             

            /*FilteredList<ProductBean> filterData = new FilteredList<>(productsData,p -> true);
            textFiltrar.textProperty().addListener((observable,oldValue, newValue)->{
                filterData.setPredicate(product ->{
                    if(newValue == null || newValue.isEmpty()){
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    
                    if(product.getNombre().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }
                    if(product.getId().toString().contains(lowerCaseFilter)){
                        return true;
                    }
                    return false;
                });
            });
            SortedList<ProductBean> sortedData = new SortedList<>(filterData);
            sortedData.comparatorProperty().bind(tbProductos.comparatorProperty());
            tbProductos.setItems(sortedData);*/
 
            stage.show();
            
        }catch(BusinessLogicException e){
            showErrorAlert("No se ha podido abrir la ventana.\n"+
                            e.getMessage());
        }
        
        
    }
     
    /**
     * Initializes window state. It implements behavior for WINDOW_SHOWING type 
     * event.
     * @param event  The window event 
     */
    private void handleWindowShowing(WindowEvent event){
        LOGGER.info("Beginning ProductController::handleWindowShowing");
        
        btnGuardar.setDisable(true);
        btnActualizar.setDisable(true);
        btnEliminar.setDisable(true);
        btnCancelar.setDisable(true);
        
        textId.setPromptText("Introduzca id del producto");
        textPrecioUnidad.setPromptText("Introduzca precio del producto");
        textNombre.setPromptText("Introduzca nombre del producto");
        textStock.setPromptText("Introduzca stock del producto");
        textFiltrar.setPromptText("Introduzca nombre o id del producto que busca");
        
        textId.requestFocus();
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
            textId.getText().trim().length()>this.MAX_LENGTH ||
            textPrecioUnidad.getText().trim().length()>this.MAX_LENGTH ||
            textStock.getText().trim().length()>this.MAX_LENGTH ||
            textFiltrar.getText().trim().length()>this.MAX_LENGTH ){
                showErrorAlert("La longitud máxima del campo es de 255 caracteres.");
                btnGuardar.setDisable(true);
                btnEliminar.setDisable(true);
                btnCancelar.setDisable(true);
                btnActualizar.setDisable(true);
                 
        }
        else if(textId.getText().trim().isEmpty()||
            textNombre.getText().trim().isEmpty() ||
            textPrecioUnidad.getText().trim().isEmpty() ||
            textStock.getText().trim().isEmpty() ){
                textId.setDisable(false);
                btnGuardar.setDisable(true);
                //btnEliminar.setDisable(true);
                btnCancelar.setDisable(false);
                //btnActualizar.setDisable(true);
                
        } 
        else if(textId.isDisable()){
            btnGuardar.setDisable(false);
            
        }
        else{
            btnGuardar.setDisable(false);
            btnEliminar.setDisable(true);
            btnCancelar.setDisable(false);
            //btnActualizar.setDisable(false);
            
        }
        
        textId.setStyle("-fx-text-inner-color: black;");
    }
    
    /**
     * Products table selection changed event handler. It enables or disables buttons
     * depending on selection state of the table.
     * @param observable the property being observed: SelectedItem Property
     * @param oldValue   old UserBean value for the property.
     * @param newValue   new UserBean value for the property.
     */
    private void handleProductsTableSelectionChanged(ObservableValue observable,
             Object oldValue,
             Object newValue) {
         
        //If there is a row selected, move row data to corresponding fields in the
        //window and enable create, modify and delete buttons
        if(newValue!=null){ 
            ProductBean product = (ProductBean)newValue;
            textId.setText(String.valueOf(product.getId()));
            textNombre.setText(product.getNombre());
            textPrecioUnidad.setText(String.valueOf(product.getPrecioUnidad()));
            textStock.setText(String.valueOf(product.getStock()));
            
            textId.setDisable(true);
            btnGuardar.setDisable(true);
            btnEliminar.setDisable(false);
            btnCancelar.setDisable(false);
            btnActualizar.setDisable(false);
            
        }else{
            textId.setText("");
            textNombre.setText("");
            textPrecioUnidad.setText("");
            textStock.setText("");

            btnGuardar.setDisable(true);
            btnEliminar.setDisable(true);
            btnCancelar.setDisable(true);
            
        }
        textId.requestFocus();
     }
     
     
    /**
     * Action event handler for Guardar button. It validates new product data, send it
     * to the business logic tier and updates product table view with new product data.
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleGuardarAction(ActionEvent event){
        try{
            productsManager.isIdExisting(Integer.parseInt(textId.getText().trim()));
 
            ProductBean product = new ProductBean(Integer.parseInt(textId.getText()), 
                                        textNombre.getText(),
                                        Integer.parseInt(textPrecioUnidad.getText()), 
                                        Double.parseDouble(textStock.getText()));
            
            this.productsManager.createProduct(product); 
            
            tbProductos.getItems().add(product);
            
            textId.setText("");
            textNombre.setText("");
            textPrecioUnidad.setText("");
            textStock.setText("");
            
            btnGuardar.setDisable(true);
            btnEliminar.setDisable(true);
            btnCancelar.setDisable(true);
            
        }catch(IdExistsException e){
            showErrorAlert("El id del producto ya existe.");
            textId.requestFocus();
            textId.setStyle("-fx-text-inner-color: red;");
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
            
            if(!selectedProduct.getId().equals(Integer.parseInt(textId.getText()))){
                
                productsManager.isIdExisting(Integer.parseInt(textId.getText().trim()));
                selectedProduct.setId(Integer.parseInt(textId.getText().trim()));
               
            }
            
            selectedProduct.setNombre(textNombre.getText().trim());
            selectedProduct.setId(Integer.parseInt(textId.getText().trim()));
            selectedProduct.setPrecioUnidad(Integer.parseInt(textPrecioUnidad.getText().trim()));
            selectedProduct.setStock(Double.parseDouble(textStock.getText()));
            
           
            this.productsManager.updateProduct(selectedProduct);

            textId.setText("");
            textNombre.setText("");
            textPrecioUnidad.setText("");
            textStock.setText("");
            
           // btnGuardar.setDisable(true);
            btnActualizar.setDisable(true);
           // btnEliminar.setDisable(true);
            //btnCancelar.setDisable(true);
            
            tbProductos.getSelectionModel().clearSelection();
            tbProductos.refresh();
            
        }catch(IdExistsException e){
            showErrorAlert("El ID del producto pertenece ya existe.");
            textId.requestFocus();
            textId.setStyle("-fx-text-inner-color: red;");
            
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
    private void handleEliminarAction(ActionEvent event){
        Alert alert=null;
        try{
            ProductBean selectedProduct = ((ProductBean) tbProductos
                    .getSelectionModel().getSelectedItem());
            
            //Ask for confirmation on delete
            alert=new Alert(Alert.AlertType.CONFIRMATION,
                                    "¿Eliminar la fila seleccionada?\n"
                                    + "Esta operación no se puede deshacer.",
                                    ButtonType.OK,ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            
            //If OK to deletion
            if(result.isPresent() && result.get() == ButtonType.OK){
                //delete product from server side
                this.productsManager.deleteProduct(selectedProduct);
                
                //removes selected item from table
                tbProductos.getItems().remove(selectedProduct);
                /*Collection<String> st =new Stack();
                st.removeIf(s -> s.contains((CharSequence) selectedProduct));
                */
                tbProductos.refresh();
                
    
    
                //clears editing fields
                textId.setText("");
                textNombre.setText("");
                textPrecioUnidad.setText("");
                textStock.setText(""); 
                
                //btnGuardar.setDisable(true);
               //btnEliminar.setDisable(true);
               // btnCancelar.setDisable(true);
                btnActualizar.setDisable(true);
                //Clear selection and refresh table view 
                tbProductos.getSelectionModel().clearSelection();
                tbProductos.refresh();
            }
        }catch(BusinessLogicException e){
            showErrorAlert("Error al eliminar producto:\n"+
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
        textId.setText("");
        textNombre.setText("");
        textPrecioUnidad.setText("");
        textStock.setText("");
        textId.setDisable(false);
        btnGuardar.setDisable(true);
        btnEliminar.setDisable(true);
        btnCancelar.setDisable(true);
        btnActualizar.setDisable(true);
    }
    
 
    @FXML
    private void handleFiltrarAction(ActionEvent event){
        try{
           
            productsData = FXCollections.observableArrayList(productsManager.getAllProducts());

            if(cbFiltrar.getSelectionModel().getSelectedItem().equals("NOMBRE")){
                for(Iterator<ProductBean> it = productsData.iterator(); it.hasNext();){
                    ProductBean next = it.next();
                    if(!next.getNombre().toLowerCase().equals(textFiltrar.getText().toLowerCase())){
                        it.remove();
                    }
                }
            }
            else if(cbFiltrar.getSelectionModel().getSelectedItem().equals("ID")){
                for(Iterator<ProductBean> it = productsData.iterator(); it.hasNext();){
                    ProductBean next = it.next();
                    if(!next.getId().toString().equals(textFiltrar.getText().toString())){
                        it.remove();
                     }
                } 
            }
            else if(cbFiltrar.getSelectionModel().getSelectedItem().equals("TODOS LOS PRODUCTOS")){
                textFiltrar.setText("");
                productsData = FXCollections.observableArrayList(productsManager.getAllProducts());
            }
            tbProductos.setItems(productsData);
        
        }catch(BusinessLogicException e){
            showErrorAlert("Error al filtrar productos:\n"+
                            e.getMessage());
            LOGGER.log(Level.SEVERE,
                        "UI ProductController: Error finding products: {0}",
                        e.getMessage());
        }
       
        
        
     
       
    }
    
   
    
    @FXML
    private void handleReportAction(ActionEvent event){
        try {
            JasperReport report=
                JasperCompileManager.compileReport(getClass()
                    .getResourceAsStream("/easyorderappclient/ui/report/ProductReport.jrxml"));
            //Data for the report: a collection of UserBean passed as a JRDataSource 
            //implementation 
            JRBeanCollectionDataSource dataItems=
                    new JRBeanCollectionDataSource((Collection<ProductBean>)this.tbProductos.getItems());
            //Map of parameter to be passed to the report
            Map<String,Object> parameters=new HashMap<>();
            //Fill report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(report,parameters,dataItems);
            //Create and show the report window. The second parameter false value makes 
            //report window not to close app.
            JasperViewer jasperViewer = new JasperViewer(jasperPrint);
            jasperViewer.setVisible(true);
           // jasperViewer.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        } catch (JRException ex) {
            //If there is an error show message and
            //log it.
            showErrorAlert("Error al imprimir:\n"+
                            ex.getMessage());
            LOGGER.log(Level.SEVERE,
                        "UI GestionUsuariosController: Error printing report: {0}",
                        ex.getMessage());
        }
    }
}
