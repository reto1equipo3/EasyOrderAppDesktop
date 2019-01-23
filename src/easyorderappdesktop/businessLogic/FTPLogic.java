package easyorderappdesktop.businessLogic;

import easyorderappdesktop.transferObject.MyFtpFile;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Imanol
 */
public interface FTPLogic {
	public void iniciarSesion();
	public void cerrarSesion();
	public ArrayList<MyFtpFile> listarFicheros(String path);
	public void crearDirectorio(String path);
	public void borrarDirectorio(String path);
	public void descargarArchivo(String path);
	public void agnadirArchivo(String path, File file);
	public void borrarArchivo(String path);
}
