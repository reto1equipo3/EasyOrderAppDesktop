/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyorderappdesktop.ui.controller;

import easyorderappdesktop.businessLogic.BusinessLogicException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Imanol
 */
public class CambiarContrasegnaFxmlController extends GenericController{

	@FXML
	private PasswordField pwdActual;
	@FXML
	private PasswordField pwdNueva;
	@FXML
	private PasswordField pwdConfirmar;
	@FXML
	private Button btnAceptar;
	@FXML
	private Button btnCancelar;


	public void initStage(Parent root){
		LOGGER.info("CambiarContrasegnaFxmlController: Initializing stage...");

		
		// Create a scene associated to the node graph root	
		Scene scene = new Scene(root);
		// Associate scene to the stage
		stage = new Stage();
		stage.setScene(scene);
		// Set window properties
		stage.setTitle("EasyOrderApp");
		stage.setResizable(false);
		stage.initModality(Modality.APPLICATION_MODAL);
		// Set window's events handlers
		stage.setOnShowing(this::handleWindowShowing);
		// Set control events handlers

		// Show window
		stage.show();
		
		LOGGER.info("CambiarContrasegnaFxmlController: Initialized stage.");
	}

	private void handleWindowShowing(WindowEvent event){
		LOGGER.info("CambiarContrasegnaFxmlController: Setting default window stage...");

		
		pwdActual.setText("Abcd*1234");
		pwdNueva.setText("pocholo");
		pwdConfirmar.setText("pocholo");
		
		LOGGER.info("CambiarContrasegnaFxmlController: Set default window stage.");
	}
	
	@FXML
	private void handleAceptarAction(ActionEvent event) {
		LOGGER.info("CambiarContrasegnaFxmlController: Handling aceptar action...");
		
		try {
			// TODO
			// Check valid input passwords
			// Check confirmation
			empleadoLogic.cambiarContrasegna(empleado.getLogin(), pwdActual.getText(), pwdNueva.getText());
		} catch (BusinessLogicException ex) {
			Logger.getLogger(CambiarContrasegnaFxmlController.class.getName()).log(Level.SEVERE, null, ex);
			// TODO
		}
		
		LOGGER.info("CambiarContrasegnaFxmlController: Handled aceptar action.");
	}

	@FXML
	private void handleCancelarAction(ActionEvent event) {
		LOGGER.info("CambiarContrasegnaFxmlController: Handling cancelar action...");
		stage.close();

		LOGGER.info("CambiarContrasegnaFxmlController: Handled cancelar action.");
	}
	
}
