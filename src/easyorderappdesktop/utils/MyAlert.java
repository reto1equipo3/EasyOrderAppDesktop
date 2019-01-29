package easyorderappdesktop.utils;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Custom class to create {@link Alert} objects.
 * @author Imanol
 */
public class MyAlert {
	public static Optional<ButtonType> showAlert(Alert.AlertType type, String context){
		Alert alert = new Alert(type);
		alert.setHeaderText(null);
		alert.setContentText(context);
		return alert.showAndWait();
	}
}
