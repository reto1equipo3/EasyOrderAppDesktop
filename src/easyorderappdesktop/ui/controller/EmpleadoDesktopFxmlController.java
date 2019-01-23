package easyorderappdesktop.ui.controller;

import easyorderappdesktop.businessLogic.BusinessLogicException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Imanol
 */
public class EmpleadoDesktopFxmlController extends GenericController {

	@FXML
	private Hyperlink hpCambiarContrasegna;
	@FXML
	private TextField txtLogin;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtFullname;
	@FXML
	private TextField txtTelefono;
	@FXML
	private DatePicker dtpFechaDeNacimiento;
	@FXML
	private Button btnEditarGuardar;
	@FXML
	private Button btnEliminarCancelar;

	public void initStage(Parent root) {
		LOGGER.log(Level.INFO, "EmpleadoDesktopFxmlController: Initializing stage.");
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
	}

	private void handleWindowShowing(WindowEvent event) {
		LOGGER.log(Level.INFO, "EmpleadoDesktopFxmlController: Setting default window state.");

		// Fill textviews with employee data
		txtLogin.setText(empleado.getLogin());
		txtEmail.setText(empleado.getEmail());
		txtFullname.setText(empleado.getFullname());
		LocalDate ldFechaDeNacimiento = empleado.getFechaDeNacimiento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		dtpFechaDeNacimiento.setValue(ldFechaDeNacimiento);
		txtTelefono.setText(empleado.getTelefono());

	}

	@FXML
	private void handleEditarGuardarAction(ActionEvent event) {
		if (btnEditarGuardar.getText().equals("Editar")) {
			txtLogin.setEditable(true);
			txtEmail.setEditable(true);
			txtFullname.setEditable(true);
			dtpFechaDeNacimiento.setEditable(true);
			txtTelefono.setEditable(true);

			btnEditarGuardar.setText("Guardar");
			btnEliminarCancelar.setText("Cancelar");

			LOGGER.info("EmpleadoDesktopFxmlController: handled edit action.");
		} else if (btnEditarGuardar.getText().equals("Guardar")) {

			try {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setHeaderText(null);
				alert.setContentText("¿Está seguro que desea modificar sus datos?");
				Optional<ButtonType> result = alert.showAndWait();

				if (result.get() == ButtonType.OK) {
					LOGGER.info("EmpleadoDesktopFxmlController: handling update action.");

					// TODO
					// Validate fields' input
					empleado.setLogin(txtLogin.getText());
					empleado.setEmail(txtEmail.getText());
					empleado.setFullname(txtFullname.getText());
					java.sql.Date d = java.sql.Date.valueOf(dtpFechaDeNacimiento.getValue());
					empleado.setFechaDeNacimiento(d);
					empleado.setTelefono(txtTelefono.getText());

					empleadoLogic.updateEmpleado(empleado);

					txtLogin.setEditable(false);
					txtEmail.setEditable(false);
					txtFullname.setEditable(false);
					dtpFechaDeNacimiento.setEditable(false);
					txtTelefono.setEditable(false);

					btnEditarGuardar.setText("Editar");
					btnEliminarCancelar.setText("Eliminar");
					LOGGER.info("EmpleadoDesktopFxmlController: handled update action.");
				}
			} catch (BusinessLogicException ex) {
				Logger.getLogger(EmpleadoDesktopFxmlController.class.getName()).log(Level.SEVERE, null, ex);
			}

		}

	}

	@FXML
	private void handleEliminarCancelarAction(ActionEvent event) {
		if (btnEliminarCancelar.getText().equals("Eliminar")) {
			try {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setHeaderText(null);
				alert.setContentText("¿Está seguro que desea darse de baja?");
				Optional<ButtonType> result = alert.showAndWait();

				if (result.get() == ButtonType.OK) {
					LOGGER.info("EmpleadoDesktopFxmlController: handling delete action.");

					empleadoLogic.deleteEmpleado(empleado);
					LOGGER.info("EmpleadoDesktopFxmlController: handled delete action.");

					openSignInWindow();
				}
			} catch (BusinessLogicException ex) {
				LOGGER.log(Level.SEVERE, "EmpleadoDesktopFxmlController: Error deleting employee, {0}.", ex.getMessage());
			}
		} else if (btnEliminarCancelar.getText().equals("Cancelar")) {
			LOGGER.info("EmpleadoDesktopFxmlController: handled cancel action.");
			txtLogin.setEditable(false);
			txtEmail.setEditable(false);
			txtFullname.setEditable(false);
			dtpFechaDeNacimiento.setEditable(false);
			txtTelefono.setEditable(false);

			btnEditarGuardar.setText("Editar");
			btnEliminarCancelar.setText("Eliminar");
		}
	}

	/**
	 * Opens Sign In window.
	 */
	private void openSignInWindow() {
		LOGGER.info("SignUpDesktopFxmlController: Opening SignIn window action.");
		try {
			// Load node graph from fxml file
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/easyorderappdesktop/ui/fxml/SignInDesktopFXMLDocument.fxml"));
			Parent root = (Parent) loader.load();
			// Get controller for graph
			SignInDesktopFxmlController controller = ((SignInDesktopFxmlController) loader.getController());
			controller.setEmpleadoLogic(empleadoLogic);
			// Initialize stage
			controller.initStage(root);
			// Hide sign up stage
			stage.hide();
		} catch (IOException ex) {
			LOGGER.log(Level.SEVERE, "SignUpDesktopFxmlController: Error opening SignIn window.", ex.getMessage());
		}
	}

}
