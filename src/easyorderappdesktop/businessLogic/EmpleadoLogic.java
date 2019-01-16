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

	public Empleado recuperarContrasegna(Empleado empleado);

	public Empleado cambiarContrasegna(Empleado empleado);

}
