/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyorderappclient.ui.controller.test;

import easyorderappclient.transferObject.ProductBean;
import easyorderappdesktop.EasyOrderAppClient;
import java.util.List;
import java.util.Random;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isFocused;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.ButtonMatchers.isCancelButton;
import static org.testfx.matcher.control.ButtonMatchers.isDefaultButton;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;



/**
 *
 * @author Igor
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductControllerTI extends ApplicationTest{
    private static final String OVERSIZED_TEXT="XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+
                                               "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+
                                               "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+
                                               "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+
                                               "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+
                                               "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+
                                               "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+
                                               "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
    
    /*@BeforeClass
    public static void config() throws Exception {
        System.getProperties().put("testfx.robot", "glass");
    }*/
    private TableView table;
   
    @Override
    public void start(Stage stage) throws Exception {
        new EasyOrderAppClient().start(stage);
        
        table=lookup("#tbProductos").queryTableView();
        
    }
    
    @Test
    //@Ignore
    public void test01_initialState(){
        verifyThat("#textId", hasText(""));
        verifyThat("#textNombre", hasText(""));
        verifyThat("#textPrecioUnidad", hasText(""));
        verifyThat("#textStock", hasText(""));
        
        verifyThat("#btnGuardar", isDisabled());
        verifyThat("#btnActualizar", isDisabled());
        verifyThat("#btnEliminar", isDisabled());
        verifyThat("#btnCancelar", isDisabled());
        
        verifyThat("#tbProductos", isVisible());
        verifyThat("#colCodigo", isVisible());
        verifyThat("#colNombre", isVisible());
        verifyThat("#colPrecioUnidad", isVisible());
        verifyThat("#colStock", isVisible());
        
        verifyThat("#textId",  (TextField t) -> t.isFocused());
    }
    
    @Test
    //@Ignore
    public void test02_buttonGuardarIsDisabledEnabled(){
        doubleClickOn("#textId");
        write("77777");
        verifyThat("#btnGuardar", isDisabled());
        doubleClickOn("#textNombre");
        write("Manzana");
        verifyThat("#btnGuardar", isDisabled());
        doubleClickOn("#textPrecioUnidad");
        write("2");
        verifyThat("#btnGuardar", isDisabled());
        doubleClickOn("#textStock");
        write("60");
        verifyThat("#btnGuardar", isEnabled());
        
    }
    
    
    @Test
    //@Ignore
    public void test03_cancelOnDeleteProduct(){
        int rowCount=table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",rowCount,0);
        Node row=lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ",row);
        clickOn(row);
        verifyThat("#btnEliminar", isEnabled());
        clickOn("#btnEliminar");
        verifyThat("¿Eliminar la fila seleccionada?\n"
                    + "Esta operación no se puede deshacer.",isVisible());    
        clickOn(isCancelButton());
        assertEquals("A row has been deleted!!!",rowCount,table.getItems().size());
    }
    
    
    @Test
    //@Ignore
    public void test04_DeleteProduct(){
        int rowCount=table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",rowCount,0);
        Node row=lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ",row);
        clickOn(row);
        verifyThat("#btnEliminar", isEnabled());
        clickOn("#btnEliminar");
        verifyThat("¿Eliminar la fila seleccionada?\n"
                        + "Esta operación no se puede deshacer.",isVisible());    
        clickOn(isDefaultButton());
        assertEquals("The row has been deleted!!!",
                    rowCount-1,table.getItems().size());
        verifyThat("#textId", isFocused());
    }
    
    @Test
    //@Ignore
    public void test05_ProductExistOnCreate() { 
        int rowCount=table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",rowCount,0);
        
        String id=((ProductBean)table.getItems().get(0)).getId().toString();
        clickOn("#textId");
        write(id);
        
        doubleClickOn("#textNombre");
        write("anyname");
        
        doubleClickOn("#textPrecioUnidad");
        write("3");
        
        doubleClickOn("#textStock");
        write("3");
        
        clickOn("#btnGuardar");  
        clickOn(isDefaultButton());
        assertEquals("A row has not been added!!!",rowCount,table.getItems().size());
        
    }
    
    
    @Test
    //@Ignore
    public void test06_createProduct() { 
        int rowCount=table.getItems().size();
        //get an existing login from random generator
        //Integer id=7+new Random().toString().nextInt();
        //write that login on text field
        clickOn("#textId");
        write("777");
        doubleClickOn("#textNombre");
        write("Pipas");
        clickOn("#textPrecioUnidad");
        write("2");
        doubleClickOn("#textStock");
        write("50");
        clickOn("#btnGuardar");
        assertEquals("The row has been added!!!",rowCount,table.getItems().size());
        
                
    }
    
    
    @Test
    @Ignore
    public void test07_modifyProduct() { 
         int rowCount=table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                        rowCount,0);
        //look for 1st row in table view and click it
        Node row=lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ",row);
        clickOn(row);
        //get selected item and index from table
        ProductBean selectedProduct=(ProductBean)table.getSelectionModel()
                                     .getSelectedItem();
        int selectedIndex=table.getSelectionModel().getSelectedIndex();
        //Modify user data
        ProductBean modifiedProduct=new ProductBean();
        
        modifiedProduct.setNombre(selectedProduct.getNombre()+new Random());
        doubleClickOn("#textNombre");
        eraseText(1);
        write(modifiedProduct.getNombre());
        
       /* modifiedProduct.setPrecioUnidad(selectedProduct.getPrecioUnidad().intValue()+new Random().nextInt());
        doubleClickOn("#textPrecioUnidad");
        write(" ");
        write(modifiedProduct.getPrecioUnidad().toString());*/
        
       /* modifiedProduct.setStock(selectedProduct.getStock()+new Random().nextInt());
        doubleClickOn("#textPrecioUnidad");
        write(" ");
        write((char) modifiedProduct.getStock());*/
        
        clickOn("#btnActualizar");
        
    }
    
    
   

    
    @Test
    @Ignore
    public void test08_tableSelection() { 
        //get row count
        int rowCount=table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                        rowCount,0);
        //look for 1st row in table view and select it
        Node row=lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ",row);
        clickOn(row);
        //get selected item and index from table
        ProductBean selectedProduct=(ProductBean)table.getSelectionModel()
                                     .getSelectedItem();
        int selectedIndex=table.getSelectionModel().getSelectedIndex();
        //assertions
        verifyThat("#textId",hasText(selectedProduct.getId().toString()));
        verifyThat("#textNombre",hasText(selectedProduct.getNombre()));
        verifyThat("#textPrecioUnidad",hasText(selectedProduct.getPrecioUnidad().toString()));
        //verifyThat("#textStock",has(selectedProduct.getStock()));
        verifyThat("#btnEliminar",isEnabled());
        verifyThat("#btnActualizar",isEnabled());
        clickOn("#btnCancelar");
        
        
    }
    
    
    @Test
    @Ignore
    public void test09_textFieldsMaxLength() {
        doubleClickOn("#textId");
        write(OVERSIZED_TEXT);
        verifyThat("La longitud máxima del campo es de 255 caracteres.",isVisible());
        clickOn(isDefaultButton());
        doubleClickOn("#textId");
        eraseText(1);
       
        doubleClickOn("#textNombre");
        write(OVERSIZED_TEXT);
        verifyThat("La longitud máxima del campo es de 255 caracteres.",isVisible());
        clickOn(isDefaultButton());
        doubleClickOn("#textNombre");
        eraseText(1);
        
        doubleClickOn("#textPrecioUnidad");
        write(OVERSIZED_TEXT);
        verifyThat("La longitud máxima del campo es de 255 caracteres.",isVisible());
        clickOn(isDefaultButton());
        doubleClickOn("#textPrecioUnidad");
        eraseText(1);
        
        doubleClickOn("#textStock");
        write(OVERSIZED_TEXT);
        verifyThat("La longitud máxima del campo es de 255 caracteres.",isVisible());
        clickOn(isDefaultButton());
        doubleClickOn("#textStock");
        eraseText(1);
    } 
    
}
