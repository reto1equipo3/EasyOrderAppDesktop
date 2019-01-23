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
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

}
