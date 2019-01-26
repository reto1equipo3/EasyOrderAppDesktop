package easyorderappdesktop.ui.controller;

import easyorderappdesktop.businessLogic.EmpleadoLogic;
import easyorderappdesktop.transferObject.Empleado;
import java.util.logging.Logger;
import javafx.stage.Stage;

/**
 * This is the base class for UI controllers in SignInSignUp app. It contains
 * common methods and references for objects used by UI controllers.
 *
 * @author Imanol
 */
public class GenericController {

	/**
	 * {@link Logger} object used to log messages for the app.
	 */
	protected static final Logger LOGGER = Logger.getLogger("easyorderappclient");
	/**
	 * Maximum text fields lengths.
	 */
	protected final int MAX_LENGTH = 255;
	protected final int MAX_LENGTH_FULLNAME = 50;
	protected final int MAX_LENGTH_EMAIL = 50;
	protected final int MAX_LENGTH_LOGIN = 20;
	protected final int MAX_LENGTH_PASSWORD = 10;
	protected final int MIN_LENGTH_PASSWORD = 6;
	/**
	 * The stage object associated to the Scene controlled by this controller.
	 * This method provides quick access inside the controller to the Stage
	 * object.
	 */
	protected Stage stage;

	/**
	 * Gets the Stage object related to this controller.
	 *
	 * @return The Stage object initialized by this controller.
	 */
	public Stage getStage() {
		return stage;
	}

	/**
	 * Sets the Stage object related to this controller.
	 *
	 * @param stage The Stage object to be initialized.
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	/**
	 * Business empleadoEmpleadoLogic object containing all
	 * empleadoEmpleadoLogic methods.
	 */
	protected EmpleadoLogic empleadoLogic;

	/**
	 * Sets the business empleadoEmpleadoLogic object to be used by this UI
	 * controller.
	 *
	 * @param empleadoLogic
	 */
	public void setEmpleadoLogic(EmpleadoLogic empleadoLogic) {
		this.empleadoLogic = empleadoLogic;
	}

	protected Empleado empleado;

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
}
