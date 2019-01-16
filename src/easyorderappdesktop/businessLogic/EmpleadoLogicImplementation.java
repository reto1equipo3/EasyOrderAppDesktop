package easyorderappdesktop.businessLogic;

import easyorderappdesktop.rest.EmpleadoRESTClient;
import easyorderappdesktop.transferObject.Empleado;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Imanol
 */
public class EmpleadoLogicImplementation implements EmpleadoLogic {
	private EmpleadoRESTClient webClient;
	private static final Logger LOGGER = Logger.getLogger("easyorderappclient");

	public EmpleadoLogicImplementation(){
		webClient = new EmpleadoRESTClient();
	}

	@Override
	public void createEmpleado(Empleado empleado) throws BusinessLogicException{
		try{
			LOGGER.log(Level.INFO, "EmpleadoLogic: Creating employee {0}.", empleado.getLogin());
			webClient.create(empleado);
			LOGGER.log(Level.INFO, "EmpleadoLogic: Created employee.");
		} catch(Exception ex){
			LOGGER.log(Level.SEVERE, "EmpleadoLogic: Exception creating employee, {0}.", ex.getMessage());
			throw new BusinessLogicException("Error creating employee: \n"+ex.getMessage());
		}
	}


	@Override
	public void updateEmpleado(Empleado empleado) throws BusinessLogicException{
		try{
			LOGGER.log(Level.INFO, "EmpleadoLogic: Updating employee {0}.", empleado.getLogin());
			webClient.update(empleado);
			LOGGER.log(Level.INFO, "EmpleadoLogic: Updated employee.");
		} catch(Exception ex) {
			LOGGER.log(Level.SEVERE, "EmpleadoLogic: Exception updating employee, {0}.", ex.getMessage());
			throw new BusinessLogicException("Error updating employee: \n"+ex.getMessage());
		}
	}

	@Override
	public void deleteEmpleado(Empleado empleado) throws BusinessLogicException{
		try{
			LOGGER.log(Level.INFO, "EmpleadoLogic: Deleting employee {0}.", empleado.getLogin());
			webClient.delete(Integer.toString(empleado.getId()));
			LOGGER.log(Level.INFO,"EmpleadoLogic: Deleted employee.");
		} catch(Exception ex){
			LOGGER.log(Level.SEVERE, "Exception deleting employee, {0}.", ex.getMessage());
			throw new BusinessLogicException("Error deleting employee:\n"+ex.getMessage());
		}
	}

	@Override
	public Empleado inicioSesion(String login, String password) throws BusinessLogicException {
		Empleado empleado = null;
		// TODO
		// Encriptar contrasegna 
		try{
			LOGGER.log(Level.INFO, "EmpleadoLogic: Signing in employee {0}.", login);
			empleado = webClient.iniciarSesion(Empleado.class, login, password);
			LOGGER.log(Level.INFO,"EmpleadoLogic: Signed in.");
		} catch(Exception ex){
			LOGGER.log(Level.SEVERE, "Exception signing in employee, {0}.", ex.getMessage());
			throw new BusinessLogicException("Error signing in employee:\n"+ex.getMessage());
			
		}

		return empleado;
	}

	@Override
	public Empleado recuperarContrasegna(Empleado empleado) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Empleado cambiarContrasegna(Empleado empleado) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	
}
