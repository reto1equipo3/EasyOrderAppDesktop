package easyorderappdesktop.businessLogic;

import easyorderappdesktop.transferObject.MyFtpFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 *
 * @author Imanol
 */
public class FTPLogicImplementation implements FTPLogic {

	/**
	 * {@link Logger} object used to log messages for the app.
	 */
	private static final Logger LOGGER = Logger.getLogger("easyorderappclient");
	private FTPClient ftp;
	private final String HOSTNAME = ResourceBundle.getBundle("easyorderappdesktop.config.parameters").getString("ftp.hostname");
	private final int PORT = Integer.parseInt(ResourceBundle.getBundle("easyorderappdesktop.config.parameters").getString("ftp.port"));
	private final String USERNAME = ResourceBundle.getBundle("easyorderappdesktop.config.parameters").getString("ftp.username");
	private final String PASSWORD = ResourceBundle.getBundle("easyorderappdesktop.config.parameters").getString("ftp.password");
	private final String DESTINO = ResourceBundle.getBundle("easyorderappdesktop.config.parameters").getString("ftp.destino");

	@Override
	public void iniciarSesion() {
		LOGGER.info("FTPLogicImplementation: Connecting...");
		try {
			ftp = new FTPClient();
			ftp.connect(HOSTNAME, PORT);
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftp.login(USERNAME, PASSWORD);
			ftp.changeWorkingDirectory("/");
			// TODO
			// Usar remote cuando el hostname no es localhost
			//ftp.enterRemotePassiveMode();
			ftp.enterLocalPassiveMode();
		} catch (IOException ex) {
			Logger.getLogger(FTPLogicImplementation.class.getName()).log(Level.SEVERE, null, ex);
		}
		LOGGER.info("FTPLogicImplementation: Connected.");
	}

	@Override
	public void cerrarSesion() {
		try {
			ftp.logout();
			ftp.disconnect();
		} catch (IOException ex) {
			Logger.getLogger(FTPLogicImplementation.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private MyFtpFile FTPFileToMyFtpFile(String path, FTPFile ftpFile) {
		MyFtpFile myFtpFile = new MyFtpFile();
		myFtpFile.setName(ftpFile.getName());
		myFtpFile.setPath(path);

		switch (ftpFile.getType()) {
			case FTPFile.DIRECTORY_TYPE:
				myFtpFile.setDirectory(true);
				break;
			case FTPFile.FILE_TYPE:
				myFtpFile.setDirectory(false);
				break;
			default:
				break;
		}

		return myFtpFile;
	}

	@Override
	public ArrayList<MyFtpFile> listarFicheros(String path) {
		LOGGER.log(Level.INFO,"FTPLogicImplementation: Listando ficheros path={0}.", path);
		FTPFile[] ftpFiles = null;
		ArrayList<MyFtpFile> myFtpFiles = new ArrayList<>();

		try {
			//ftpFiles = ftp.listFiles("C:\\Users\\iRoib\\Documents\\NetBeansProjects\\EasyOrderAppDesktop\\ftpSharedFolder");
			ftpFiles = ftp.listFiles(path);
			for (FTPFile ftpFile : ftpFiles) {
				myFtpFiles.add(FTPFileToMyFtpFile(path, ftpFile));
			}
		} catch (IOException ex) {
			Logger.getLogger(FTPLogicImplementation.class.getName()).log(Level.SEVERE, null, ex);
		}

		return myFtpFiles;
	}

	@Override
	public void crearDirectorio(String path) {
		try {
			ftp.makeDirectory(path);
		} catch (IOException ex) {
			Logger.getLogger(FTPLogicImplementation.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	@Override
	public void borrarDirectorio(String path) {
		try {
			ftp.removeDirectory(path);
		} catch (IOException ex) {
			Logger.getLogger(FTPLogicImplementation.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void descargarArchivo(String path) {
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(DESTINO);
			ftp.retrieveFile(path, fileOutputStream);
		} catch (IOException ex) {
			Logger.getLogger(FTPLogicImplementation.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
			} catch (IOException ex) {
				Logger.getLogger(FTPLogicImplementation.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

	}

	@Override
	public void agnadirArchivo(String path, File file) {
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			ftp.storeFile(path, fileInputStream);
		} catch (IOException ex) {
			Logger.getLogger(FTPLogicImplementation.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			} catch (IOException ex) {
				Logger.getLogger(FTPLogicImplementation.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	@Override
	public void borrarArchivo(String path) {
		try {
			ftp.deleteFile(path);
		} catch (IOException ex) {
			Logger.getLogger(FTPLogicImplementation.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
