package easyorderappdesktop.businessLogic;

import easyorderappdesktop.transferObject.MyFtpFile;
import java.io.File;
import java.io.OutputStream;
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
	public boolean borrarDirectorio(String path);
	public boolean descargarArchivo(String path, OutputStream destino);
	public boolean subirArchivo(String path, File file);
	public boolean borrarArchivo(String path);
}
