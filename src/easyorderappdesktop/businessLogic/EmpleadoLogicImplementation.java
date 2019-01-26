package easyorderappdesktop.businessLogic;

import easyorderappdesktop.rest.EmpleadoRESTClient;
import easyorderappdesktop.transferObject.Empleado;
import easyorderappdesktop.utils.Crypto;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;

/**
 *
 * @author Imanol
 */
public class EmpleadoLogicImplementation implements EmpleadoLogic {

	private final EmpleadoRESTClient webClient;
	private static final Logger LOGGER = Logger.getLogger("easyorderappclient");

	public EmpleadoLogicImplementation() {
		webClient = new EmpleadoRESTClient();
	}

	@Override
	public void createEmpleado(Empleado empleado) throws BusinessLogicException {
		try {
			LOGGER.log(Level.INFO, "EmpleadoLogic: Creating employee {0}.", empleado.getLogin());
			empleado.setPassword(Crypto.encryptPassword(empleado.getPassword()));
			webClient.create(empleado);
			LOGGER.log(Level.INFO, "EmpleadoLogic: Created employee.");
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "EmpleadoLogic: Exception creating employee, {0}.", ex.getMessage());
			throw new BusinessLogicException("Error creating employee: \n" + ex.getMessage());
		}
	}

	@Override
	public void updateEmpleado(Empleado empleado) throws BusinessLogicException {
		try {
			LOGGER.log(Level.INFO, "EmpleadoLogic: Updating employee {0}.", empleado.getLogin());
			webClient.update(empleado);
			LOGGER.log(Level.INFO, "EmpleadoLogic: Updated employee.");
		} catch (ClientErrorException ex) {
			LOGGER.log(Level.SEVERE, "EmpleadoLogic: Exception updating employee, {0}.", ex.getMessage());
			throw new BusinessLogicException("Error updating employee: \n" + ex.getMessage());
		}
	}

	@Override
	public void deleteEmpleado(Empleado empleado) throws BusinessLogicException {
		try {
			LOGGER.log(Level.INFO, "EmpleadoLogic: Deleting employee {0}.", empleado.getLogin());
			webClient.delete(Integer.toString(empleado.getId()));
			LOGGER.log(Level.INFO, "EmpleadoLogic: Deleted employee.");
		} catch (ClientErrorException ex) {
			LOGGER.log(Level.SEVERE, "Exception deleting employee, {0}.", ex.getMessage());
			throw new BusinessLogicException("Error deleting employee:\n" + ex.getMessage());
		}
	}

	@Override
	public Empleado inicioSesion(String login, String password) throws BusinessLogicException {

		Empleado empleado = null;

		try {
			LOGGER.log(Level.INFO, "EmpleadoLogic: Signing in employee {0}.", login);
			password = Crypto.encryptPassword(password);
			empleado = webClient.iniciarSesion(Empleado.class, login, password);
			LOGGER.log(Level.INFO, "EmpleadoLogic: Signed in.");
		} catch (ClientErrorException ex) {
			LOGGER.log(Level.SEVERE, "Exception signing in employee, {0}.", ex.getMessage());
			throw new BusinessLogicException("Error signing in employee:\n" + ex.getMessage());
		}

		return empleado;
	}

	@Override
	public void recuperarContrasegna(String login) throws BusinessLogicException {
		LOGGER.log(Level.INFO, "EmpleadoLogic: Restoring password.");

		Empleado empleado = null;
		try {
			webClient.recuperarContrasegna(Empleado.class, login);

		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "Exception restoring password, {0}.", ex.getMessage());
			throw new BusinessLogicException("Error restoring password:\n" + ex.getMessage());
		}
		LOGGER.log(Level.INFO, "EmpleadoLogic: Restored password.");
	}

	@Override
	public Empleado cambiarContrasegna(String login, String actualPassword, String nuevaPassword) throws BusinessLogicException {
		LOGGER.log(Level.INFO, "EmpleadoLogic: Changing password employee {0}...", login);

		Empleado empleado = null;
		try {
			actualPassword = Crypto.encryptPassword(actualPassword);
			nuevaPassword = Crypto.encryptPassword(nuevaPassword);
			empleado = webClient.cambiarContrasegna(Empleado.class, login, actualPassword, nuevaPassword);
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "Exception changing password, {0}.", ex.getMessage());
			throw new BusinessLogicException("Error changing password:\n" + ex.getMessage());

		}

		LOGGER.log(Level.INFO, "EmpleadoLogic: Changed password employee {0}.", login);
		return empleado;
	}

}
