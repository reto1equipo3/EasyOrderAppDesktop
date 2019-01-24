/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyorderappdesktop.ui.controller;

import easyorderappdesktop.businessLogic.FTPLogic;
import easyorderappdesktop.businessLogic.FTPLogicFactory;
import easyorderappdesktop.transferObject.MyFtpFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * FXML Controller class
 *
 * @author Imanol
 */
public class FacturasDesktopFxmlController extends GenericController {

	@FXML
	private TreeView<MyFtpFile> trvFacturas;
	@FXML
	private Button btnSubir;
	@FXML
	private Button btnDescargar;
	@FXML
	private Button btnEliminar;

	private final FTPLogic ftpLogic = FTPLogicFactory.createFTPLogicImplementation();
	@FXML
	private Button btnCrearDirectorio;
	@FXML
	private TextField txtDirectorio;

	public void initStage(Parent root) {
		LOGGER.info("FacturasDesktopFxmlController: Initializing stage.");

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

		ftpLogic.iniciarSesion();
		// Show window
		stage.show();

	}

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

	private void setTreeView() {
		// Create a custom file object for root
		MyFtpFile ftpRoot = new MyFtpFile("", "/", true);
		// Create a TreeItem for root with custom file object
		TreeItem<MyFtpFile> rootItem = new TreeItem<>(ftpRoot);
		rootItem.setExpanded(true);

		trvFacturas.setRoot(addLeaves(rootItem));
	}

	private void handleWindowShowing(WindowEvent event) {
		LOGGER.info("FacturasDesktopFxmlController: Setting default window state.");
		setTreeView();
	}

	@FXML
	private void handleSubirAction(ActionEvent event) {
		LOGGER.info("FacturasDesktopFxmlController: Handling subir archivo action...");

		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(stage);

		if (file != null) {
			//TreeItem<MyFtpFile> item = trvFacturas.getSelectionModel().getSelectedItem();
			if (ftpLogic.subirArchivo("/", file)) {
				// Actualizar tabla
				MyFtpFile ftpFile = new MyFtpFile(file.getName(), file.getAbsolutePath(), false);
				TreeItem<MyFtpFile> ftpItem = new TreeItem<>(ftpFile);
				trvFacturas.getRoot().getChildren().add(ftpItem);
				trvFacturas.refresh();

				Alert alert = new Alert(Alert.AlertType.INFORMATION); // Change alert type to CONFIRMATION
				alert.setHeaderText(null);
				alert.setContentText("Archivo subido con exito.");
				alert.showAndWait();
			}
		}
		LOGGER.info("FacturasDesktopFxmlController: Handled subir archivo action.");
	}

	@FXML
	private void handleDescargarAction(ActionEvent event) {
		LOGGER.info("FacturasDesktopFxmlController: Handling descargar archivo action...");

		try {
			DirectoryChooser directoryChooser = new DirectoryChooser();
			File file = directoryChooser.showDialog(stage);

			TreeItem<MyFtpFile> ftpItem = trvFacturas.getSelectionModel().getSelectedItem();
			FileOutputStream o = new FileOutputStream(file.getAbsolutePath()+ "/" + ftpItem.getValue().getName());

			if (ftpLogic.descargarArchivo(ftpItem.getValue().getAbsolutePath(), o)) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION); // Change alert type to CONFIRMATION
				alert.setHeaderText(null);
				alert.setContentText("Archivo descargado con exito.");
				alert.showAndWait();
			}
		} catch (FileNotFoundException ex) {
			Logger.getLogger(FacturasDesktopFxmlController.class.getName()).log(Level.SEVERE, null, ex);
		}

		LOGGER.info("FacturasDesktopFxmlController: Handled descargar archivo action.");
	}

	@FXML
	private void handleEliminarAction(ActionEvent event) {
		LOGGER.info("FacturasDesktopFxmlController: Handling eliminar archivo action...");

		TreeItem<MyFtpFile> ftpItem = trvFacturas.getSelectionModel().getSelectedItem();

		if (ftpItem.getValue().isDirectory()) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setContentText("¿Desea eliminar el directorio definitivamente?");
			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.OK) {
				if (ftpItem.getChildren().isEmpty()) {
					if (ftpLogic.borrarDirectorio(ftpItem.getValue().getAbsolutePath())) {

						trvFacturas.getRoot().getChildren().remove(ftpItem);
						trvFacturas.refresh();
						alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setHeaderText(null);
						alert.setContentText("Directorio borrado con exito.");
						alert.showAndWait();
					}
				} else {

					alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setHeaderText(null);
					alert.setContentText("No se pudo borrar el directorio, pruebe a borrar los ficheros que contiene.");
					alert.showAndWait();
				}
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setContentText("¿Desea eliminar el archivo definitivamente?");
			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.OK) {

				if (ftpLogic.borrarArchivo(ftpItem.getValue().getAbsolutePath())) {

					trvFacturas.getRoot().getChildren().remove(ftpItem);
					trvFacturas.refresh();
					LOGGER.info("FacturasDesktopFxmlController: Archivo eliminado con exito.");
					alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setHeaderText(null);
					alert.setContentText("Archivo eliminado con exito.");
					alert.showAndWait();
				}
			}
		}
		LOGGER.info("FacturasDesktopFxmlController: Handled eliminar archivo action.");
	}

	@FXML
	private void handleCrearDirectorioAction(ActionEvent event) {
		LOGGER.info("FacturasDesktopFxmlController: Handling crear directorio action...");
		if (!txtDirectorio.getText().equals("")) {

			TreeItem<MyFtpFile> ftpItem = trvFacturas.getSelectionModel().getSelectedItem();
			if (ftpItem == null) {
				ftpItem = trvFacturas.getRoot();
			}
			String path = ftpItem.getValue().getAbsolutePath() + "/" + txtDirectorio.getText();
			ftpLogic.crearDirectorio(path);

			TreeItem<MyFtpFile> ti = new TreeItem<>(new MyFtpFile(txtDirectorio.getText(), ftpItem.getValue().getAbsolutePath(), true));
			ftpItem.getChildren().add(ti);
			trvFacturas.refresh();
		}
		LOGGER.info("FacturasDesktopFxmlController: Handled crear directorio action.");
	}

}
