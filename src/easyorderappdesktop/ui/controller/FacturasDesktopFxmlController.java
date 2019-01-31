package easyorderappdesktop.ui.controller;

import easyorderappdesktop.businessLogic.BusinessLogicException;
import easyorderappdesktop.businessLogic.FTPLogic;
import easyorderappdesktop.businessLogic.FTPLogicFactory;
import easyorderappdesktop.transferObject.MyFtpFile;
import easyorderappdesktop.utils.MyAlert;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class for ftp management view. It contains event handlers and
 * initialization code for the view defined in FacturasDesktopFXMLDocument.fxml
 * file.
 *
 * @author Imanol
 */
public class FacturasDesktopFxmlController extends GenericController {

	/**
	 * FTP content tree view.
	 */
	@FXML
	private TreeView<MyFtpFile> trvFacturas;
	/**
	 * Upload file button.
	 */
	@FXML
	private Button btnSubir;
	/**
	 * Download file button.
	 */
	@FXML
	private Button btnDescargar;
	/**
	 * Delete file button.
	 */
	@FXML
	private Button btnEliminar;
	/**
	 * Create directory button.
	 */
	@FXML
	private Button btnCrearDirectorio;
	/**
	 * Directory name text field.
	 */
	@FXML
	private TextField txtDirectorio;

	/**
	 * FTPLogic object.
	 */
	private FTPLogic ftpLogic;

	/**
	 * Method for initializating FTPFacturas stage.
	 *
	 * @param root The Parent object representing root node of view graph.
	 */
	public void initStage(Parent root) {
		LOGGER.info("FacturasDesktopFxmlController: Initializing stage...");

		// Create a scene associated to the node graph root	
		Scene scene = new Scene(root);
		// Associate scene to the stage
		stage = new Stage();
		stage.setScene(scene);
		// Set window properties
		stage.setTitle("Facturas FTP");
		stage.setResizable(false);
		stage.initModality(Modality.APPLICATION_MODAL);
		// Set window's events handlers
		stage.setOnShowing(this::handleWindowShowing);
		// Set control events handlers

		ftpLogic = FTPLogicFactory.createFTPLogicImplementation();
		ftpLogic.iniciarSesion();
		// Show window
		stage.show();

		LOGGER.info("FacturasDesktopFxmlController: Initialized stage.");
	}

	/**
	 * This method add leaves to a branch item.
	 *
	 * @param branchItem The branch item
	 * @return The branch with its leaves.
	 */
	private TreeItem addLeaves(TreeItem<MyFtpFile> branchItem) {
		ArrayList<MyFtpFile> branch = ftpLogic.listarFicheros(branchItem.getValue().getAbsolutePath());

		branch.forEach((leaf) -> {
			if (leaf.isDirectory()) {
				TreeItem directory = addLeaves(new TreeItem<>(leaf));
				branchItem.getChildren().add(directory);
			} else {
				TreeItem<MyFtpFile> item = new TreeItem<>(leaf);
				branchItem.getChildren().add(item);
			}
		});
		return branchItem;
	}

	private void refreshTreeView() {
		// Create a custom file object for root
		MyFtpFile ftpRoot = new MyFtpFile("", "/", true);
		// Create a TreeItem for root with custom file object
		TreeItem<MyFtpFile> rootItem = new TreeItem<>(ftpRoot);
		rootItem.setExpanded(true);

		trvFacturas.setRoot(addLeaves(rootItem));
	}

	/**
	 * Initializes window state.
	 *
	 * @param event The window event.
	 */
	private void handleWindowShowing(WindowEvent event) {
		LOGGER.info("FacturasDesktopFxmlController: Setting default window state...");
		// Set tree view
		// Create a custom file object for root
		MyFtpFile ftpRoot = new MyFtpFile("", "/", true);
		// Create a TreeItem for root with custom file object
		TreeItem<MyFtpFile> rootItem = new TreeItem<>(ftpRoot);
		rootItem.setExpanded(true);

		trvFacturas.setRoot(addLeaves(rootItem));
	}

	/**
	 * Action event handler for upload button.
	 *
	 * @param event The ActionEvent object for the event.
	 */
	@FXML
	private void handleSubirAction(ActionEvent event) {
		LOGGER.info("FacturasDesktopFxmlController: Handling subir archivo action...");

		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(stage);

		if (file != null) {
			try {
				TreeItem<MyFtpFile> ftpItem = trvFacturas.getSelectionModel().getSelectedItem();
				if (ftpItem == null) {
					ftpItem = trvFacturas.getRoot();
				}
				String path = ftpItem.getValue().getAbsolutePath() + "/" + txtDirectorio.getText();
				if (ftpLogic.subirArchivo(path, file)) {
					// Actualizar tabla
					MyFtpFile ftpFile = new MyFtpFile(file.getName(), file.getAbsolutePath(), false);
					//trvFacturas.getRoot().getChildren().add(new TreeItem<>(ftpFile));
					ftpItem.getChildren().add(new TreeItem<>(ftpFile));
					//trvFacturas.refresh();
					refreshTreeView();

					MyAlert.showAlert(Alert.AlertType.INFORMATION, "Archivo subido con exito.");
				} else {
					throw new BusinessLogicException();
				}
			} catch (BusinessLogicException ex) {
				LOGGER.log(Level.SEVERE, "FacturasDesktopFxmlController: Exception uploading file, {0}.", ex.getMessage());
				MyAlert.showAlert(Alert.AlertType.ERROR, "Error subiendo el archivo.");
			}
		}
		LOGGER.info("FacturasDesktopFxmlController: Handled subir archivo action.");
	}

	/**
	 * Action event handler for download button.
	 *
	 * @param event The ActionEvent object for the event.
	 */
	@FXML
	private void handleDescargarAction(ActionEvent event) {
		LOGGER.info("FacturasDesktopFxmlController: Handling descargar archivo action...");

		try {
			DirectoryChooser directoryChooser = new DirectoryChooser();
			File file = directoryChooser.showDialog(stage);

			TreeItem<MyFtpFile> ftpItem = trvFacturas.getSelectionModel().getSelectedItem();
			FileOutputStream o = new FileOutputStream(file.getAbsolutePath() + "/" + ftpItem.getValue().getName());

			if (ftpLogic.descargarArchivo(ftpItem.getValue().getAbsolutePath(), o)) {
				MyAlert.showAlert(Alert.AlertType.INFORMATION, "Archivo descargado con exito.");
			} else {
				throw new BusinessLogicException();
			}
		} catch (FileNotFoundException | BusinessLogicException ex) {
			LOGGER.log(Level.SEVERE, "FacturasDesktopFxmlController: Exception downloading file, {0}.", ex.getMessage());
			MyAlert.showAlert(Alert.AlertType.ERROR, "Error descargando el archivo.");
		}

		LOGGER.info("FacturasDesktopFxmlController: Handled descargar archivo action.");
	}

	/**
	 * Action event handler for deleting button.
	 *
	 * @param event The ActionEvent object for the event.
	 */
	@FXML
	private void handleEliminarAction(ActionEvent event) {
		LOGGER.info("FacturasDesktopFxmlController: Handling eliminar archivo action...");

		TreeItem<MyFtpFile> ftpItem = trvFacturas.getSelectionModel().getSelectedItem();

		if (ftpItem.getValue().isDirectory()) {
			Optional<ButtonType> result = MyAlert.showAlert(Alert.AlertType.CONFIRMATION, "¿Desea eliminar el directorio definitivamente?");

			if (result.get() == ButtonType.OK) {
				try {
					if (ftpItem.getChildren().isEmpty()) {
						if (ftpLogic.borrarDirectorio(ftpItem.getValue().getAbsolutePath())) {

							//trvFacturas.getRoot().getChildren().remove(ftpItem);
							ftpItem.getChildren().remove(ftpItem);
							//trvFacturas.refresh();
							refreshTreeView();
							MyAlert.showAlert(Alert.AlertType.INFORMATION, "Directorio borrado con exito.");
						} else {
							throw new BusinessLogicException();
						}
					} else {
						MyAlert.showAlert(Alert.AlertType.ERROR, "No se pudo borrar el directorio, pruebe a borrar los ficheros que contiene.");
					}
				} catch (BusinessLogicException ex) {
					LOGGER.log(Level.SEVERE, " FacturasDesktopFxmlController: Error deleting directory, {0}.", ex.getMessage());
					MyAlert.showAlert(Alert.AlertType.ERROR, "Error borrando el directorio.");
				}
			}
		} else {
			Optional<ButtonType> result = MyAlert.showAlert(Alert.AlertType.CONFIRMATION, "¿Desea eliminar el archivo definitivamente?");

			if (result.get() == ButtonType.OK) {

				try {
					if (ftpLogic.borrarArchivo(ftpItem.getValue().getPath())) {
						//trvFacturas.getRoot().getChildren().remove(ftpItem);
						ftpItem.getChildren().remove(ftpItem);
						//trvFacturas.refresh();
						refreshTreeView();
						LOGGER.info("FacturasDesktopFxmlController: Archivo eliminado con exito.");
						MyAlert.showAlert(Alert.AlertType.INFORMATION, "Archivo eliminado con exito.");
					} else {
						throw new BusinessLogicException();
					}
				} catch (BusinessLogicException ex) {
					LOGGER.log(Level.SEVERE, "FacturasDesktopFxmlController: Error borrando el archivo, {0}.", ex.getMessage());
					MyAlert.showAlert(Alert.AlertType.ERROR, "Error borrando el archivo.");
				}
			}
		}
		LOGGER.info("FacturasDesktopFxmlController: Handled eliminar archivo action.");
	}

	/**
	 * Action event handler for creating directory button.
	 *
	 * @param event The ActionEvent object for the event.
	 */
	@FXML
	private void handleCrearDirectorioAction(ActionEvent event) {
		LOGGER.info("FacturasDesktopFxmlController: Handling crear directorio action...");
		if (!txtDirectorio.getText().equals("")) {

			TreeItem<MyFtpFile> ftpItem = trvFacturas.getSelectionModel().getSelectedItem();
			if (ftpItem == null) {
				ftpItem = trvFacturas.getRoot();
			}
			String path = ftpItem.getValue().getAbsolutePath() + "/" + txtDirectorio.getText();
			try {
				ftpLogic.crearDirectorio(path);

				TreeItem<MyFtpFile> ti = new TreeItem<>(new MyFtpFile(txtDirectorio.getText(), ftpItem.getValue().getAbsolutePath(), true));
				ftpItem.getChildren().add(ti);
				//trvFacturas.refresh();
				refreshTreeView();

				txtDirectorio.clear();
			} catch (BusinessLogicException ex) {
				LOGGER.log(Level.SEVERE, "FacturasDesktopFxmlController: Error creating directory, {0}.", ex.getMessage());
				MyAlert.showAlert(Alert.AlertType.ERROR, "Error creando directorio.");
			}
		}
		LOGGER.info("FacturasDesktopFxmlController: Handled crear directorio action.");
	}

}
