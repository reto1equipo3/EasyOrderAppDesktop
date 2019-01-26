package easyorderappdesktop.businessLogic;

import easyorderappdesktop.transferObject.Empleado;

/**
 *
 * @author Imanol
 */
public interface EmpleadoLogic {

	public void createEmpleado(Empleado empleado) throws BusinessLogicException;

	public void updateEmpleado(Empleado empleado) throws BusinessLogicException;

	public void deleteEmpleado(Empleado empleado) throws BusinessLogicException;

	public Empleado inicioSesion(String login, String password) throws BusinessLogicException;

	public void recuperarContrasegna(String login) throws BusinessLogicException;

	public Empleado cambiarContrasegna(String login, String actualPassword, String nuevaPassword) throws BusinessLogicException;

}
