package easyorderappdesktop.ui.controller;

import easyorderappdesktop.businessLogic.BusinessLogicException;
import easyorderappdesktop.transferObject.Empleado;
import easyorderappdesktop.transferObject.UserPrivilege;
import easyorderappdesktop.transferObject.UserStatus;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Level;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * SignUpDesktopFXMLDocument FXML Controller class.
 *
 * @author Imanol
 */
public class SignUpDesktopFxmlController extends GenericController {

	@FXML
	private GridPane gpSignUp;
	/**
	 * User's fullname UI text field.
	 */
	@FXML
	private TextField txtFullname;
	/**
	 * User's email UI text field.
	 */
	@FXML
	private TextField txtEmail;
	/**
	 * User's login UI text field.
	 */
	@FXML
	private TextField txtLogin;
	/**
	 * User's password UI password field.
	 */
	@FXML
	private PasswordField pwdPassword;
	/**
	 * User's password confirmation UI password field.
	 */
	@FXML
	private PasswordField pwdConfirmPassword;
	@FXML
	private TextField txtTelefono;
	@FXML
	private DatePicker dtpFechaDeNacimiento;

	/**
	 * Sign up button.
	 */
	@FXML
	private Button btnSignUp;
	/**
	 * Sign in hyperlink.
	 */
	@FXML
	private Hyperlink hpSignIn;
	/**
	 * Error label for fullname field.
	 */
	/**
	 * Error label for email field.
	 */
	@FXML
	private Label lblErrorEmail;
	/**
	 * Error label for login field.
	 */
	@FXML
	private Label lblErrorLogin;
	/**
	 * Error label for password field.
	 */
	@FXML
	private Label lblErrorPassword;
	/**
	 * Error label for terms of use checkbox.
	 */
	private Label lblErrorTermsOfUse;
	@FXML
	private Label lblErrorFullname;

	/**
	 * Method for initializing Sign Up Desktop View {@link Stage}.
	 *
	 * @param root The parent object representing root node of view graph.
	 */
	public void initStage(Parent root) {
		LOGGER.info("SignUpDesktopFxmlController::initStage: Initializing stage.");
		// Create a scene associated to the node graph root	
		Scene scene = new Scene(root);
		// Associate scene to the stage
		stage = new Stage();
		stage.setScene(scene);
		// Set window properties
		stage.setTitle("Sign Up");
		stage.setResizable(false);
		stage.initModality(Modality.APPLICATION_MODAL);
		// Set window's events handlers
		stage.setOnShowing(this::handleWindowShowing);
		// Set control events handlers
		txtFullname.textProperty().addListener(this::textChanged);
		txtEmail.textProperty().addListener(this::textChanged);
		txtLogin.textProperty().addListener(this::textChanged);
		pwdPassword.textProperty().addListener(this::textChanged);
		// Show window
		stage.show();
	}

	/**
	 * Window event method handler. It implements behaviour for WINDOW_SHOWING
	 * type event.
	 *
	 * @param event The window event
	 */
	private void handleWindowShowing(WindowEvent event) {
		LOGGER.info("SignUpDesktopFxmlController: Setting default window state.");

		// Full name
		lblErrorFullname.setVisible(false);
		txtFullname.setStyle("");

		// Email
		txtEmail.setStyle("");
		lblErrorEmail.setVisible(false);

		// Login
		txtLogin.setStyle("");
		lblErrorLogin.setVisible(false);

		// Password
		pwdPassword.setStyle("");
		pwdConfirmPassword.setStyle("");
		lblErrorPassword.setVisible(false);

		btnSignUp.setMnemonicParsing(true);
		btnSignUp.setText("_Darse de alta");
		hpSignIn.setMnemonicParsing(true);
		hpSignIn.setText("_Iniciar sesión");

		// TODO
		// DELETE THIS
		txtLogin.setText("imanol02");
		txtEmail.setText("imanol02@gmail.com");
		pwdPassword.setText("Abcd*1234");
		pwdConfirmPassword.setText("Abcd*1234");
		txtFullname.setText("imanol");
		txtTelefono.setText("651511511");
		dtpFechaDeNacimiento.setValue(LocalDate.now());
	}

	/**
	 * Text changed event handler. It changes the border colors to default and
	 * hides the error labels when some correction is done on a textfield.
	 *
	 * @param observable The value being observed.
	 * @param oldValue The old value of the observable.
	 * @param newValue The new value of the observable.
	 */
	private void textChanged(ObservableValue observable, String oldValue, String newValue) {
		// Set fullname TextField to default
		if (!txtFullname.getText().trim().isEmpty()) {
			txtFullname.setStyle("");
			lblErrorFullname.setVisible(false);
		}
		// Set email TextField to default
		if (!txtEmail.getText().trim().isEmpty()) {
			txtEmail.setStyle("");
			lblErrorEmail.setVisible(false);
		}
		// Set login TextField to default
		if (!txtLogin.getText().trim().isEmpty()) {
			txtLogin.setStyle("");
			lblErrorLogin.setVisible(false);
		}
		// Set password and confirm password PasswordFields to default
		if (!pwdPassword.getText().trim().isEmpty()) {
			pwdPassword.setStyle("");
			pwdConfirmPassword.setStyle("");
			lblErrorPassword.setVisible(false);
		}
	}

	/**
	 * Method for validating fullname.
	 *
	 * @param fullname Fullname to validate
	 * @throws IllegalArgumentException Fullname is not valid
	 */
	private void validateFullname(String fullname) throws IllegalArgumentException {
		String FULLNAME_PATTERN = "[a-zA-Z ñÑáÁéÉíÍóÓúÚ]+$";
		Pattern pattern = Pattern.compile(FULLNAME_PATTERN);

		if (fullname == null || fullname.trim().equals("")) {
			throw new IllegalArgumentException("* Field can not be empty.");
		}

		if (fullname.trim().length() >= MAX_LENGTH_FULLNAME) {
			throw new IllegalArgumentException("* Fullname is too long.");
		}

		if (!pattern.matcher(fullname).matches()) {
			throw new IllegalArgumentException("* Only uppercase or lowercase letters.");
		}
	}

	/**
	 * Method for validating email.
	 *
	 * @param email Email to validate.
	 * @throws IllegalArgumentException Email is not valid.
	 */
	private void validateEmail(String email) throws IllegalArgumentException {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);

		if (email == null || email.trim().equals("")) {
			throw new IllegalArgumentException("* Field can not be empty.");
		}

		if (email.trim().length() >= MAX_LENGTH_EMAIL) {
			throw new IllegalArgumentException("* Email is too long.");
		}

		if (!pattern.matcher(email).matches()) {
			throw new IllegalArgumentException("* Enter a valid email.");
		}
	}

	/**
	 * Method for validating login.
	 *
	 * @param login Login to validate
	 * @throws IllegalArgumentException Login is not valid.
	 */
	private void validateLogin(String login) throws IllegalArgumentException {
		String LOGIN_PATTERN = "[a-zA-Z0-9]+";
		Pattern pattern = Pattern.compile(LOGIN_PATTERN);

		if (login == null || login.trim().equals("")) {
			throw new IllegalArgumentException("* How are you supposed to sign in?");
		}

		if (login.trim().length() >= MAX_LENGTH_LOGIN) {
			throw new IllegalArgumentException("* Login is too long.");
		}

		if (!pattern.matcher(login).matches()) {
			throw new IllegalArgumentException("* Login can only be composed by letters and numbers.");
		}
	}

	/**
	 * Method for validating password.
	 *
	 * @param password Password to validate.
	 * @param confirmPassword Password confirmation.
	 * @throws IllegalArgumentException Password is not valid.
	 */
	private void validatePassword(String password, String confirmPassword) throws IllegalArgumentException {
		String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$";
		Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

		if (password == null || password.trim().equals("")) {
			throw new IllegalArgumentException("* Security first, enter a password.");
		}

		if (password.trim().length() >= MAX_LENGTH_PASSWORD) {
			throw new IllegalArgumentException("* Password is too long.");
		}

		if (password.trim().length() < MIN_LENGTH_PASSWORD) {
			throw new IllegalArgumentException("* Password is too short.");
		}

		if (!pattern.matcher(password).matches()) {
			throw new IllegalArgumentException("* Not secure enough! Try combining lowercase, uppercase and numbers.");
		}

		if (confirmPassword == null || (!confirmPassword.trim().equals(password))) {
			throw new IllegalArgumentException("* Passwords must coincide.");
		}
	}

	/**
	 * Action event handler for SignUp button. It validates that all fields are
	 * filled and that they don't have invalid characters. If they are not
	 * border of the corresponding text fields are red colored and an error
	 * labels are shown. If all fields are filled correctly it sends the user
	 * data to the business logic tier.
	 *
	 * @param event The Action event
	 */
	@FXML
	private void handleSignUpAction(ActionEvent event) {
		LOGGER.info("SignUpDesktopFxmlController: Signing up action.");

		// Boolean to check if all fields are filled correctly
		boolean validFields = true;

		// Validate password
		try {
			validatePassword(pwdPassword.getText(), pwdConfirmPassword.getText());
		} catch (IllegalArgumentException e) {
			LOGGER.log(Level.SEVERE, "Sign Up controller::handleSignUpAction: {0}", e.getMessage());

			lblErrorPassword.setText(e.getMessage());
			lblErrorPassword.setVisible(true);

			pwdPassword.setStyle("-fx-border-color:red;");
			pwdConfirmPassword.setStyle("-fx-border-color:red;");
			pwdPassword.requestFocus();

			validFields = false;
		}
		// Validate login
		try {
			validateLogin(txtLogin.getText());
		} catch (IllegalArgumentException e) {
			LOGGER.log(Level.SEVERE, "Sign Up controller::handleSignUpAction: {0}", e.getMessage());

			lblErrorLogin.setText(e.getMessage());
			lblErrorLogin.setVisible(true);

			txtLogin.setStyle("-fx-border-color:red;");
			txtLogin.requestFocus();

			validFields = false;
		}
		// Validate email
		try {
			validateEmail(txtEmail.getText());
		} catch (IllegalArgumentException e) {
			LOGGER.log(Level.SEVERE, "Sign Up controller::handleSignUpAction: {0}", e.getMessage());

			lblErrorEmail.setText(e.getMessage());
			lblErrorEmail.setVisible(true);

			txtEmail.setStyle("-fx-border-color:red;");
			txtEmail.requestFocus();

			validFields = false;
		}
		// Validate full name
		try {
			validateFullname(txtFullname.getText());
		} catch (IllegalArgumentException e) {
			LOGGER.log(Level.SEVERE, "Sign Up controller::handleSignUpAction: {0}", e.getMessage());

			lblErrorFullname.setText(e.getMessage());
			lblErrorFullname.setVisible(true);

			txtFullname.setStyle("-fx-border-color:red;");
			txtFullname.requestFocus();

			validFields = false;
		}
		// If all fields are filled correctly sign up the user
		if (validFields) {
			// Create an employee to encapsulate all the data
			Empleado empleado = new Empleado();
			empleado.setLogin(txtLogin.getText());
			empleado.setEmail(txtEmail.getText());
			empleado.setPassword(pwdPassword.getText());
			empleado.setFullname(txtFullname.getText());
			empleado.setFechaDeNacimiento(new Date(dtpFechaDeNacimiento.getValue().toEpochDay()));
			empleado.setTelefono(txtTelefono.getText());

			empleado.setStatus(UserStatus.ENABLED);
			empleado.setPrivilege(UserPrivilege.USER);
			empleado.setLastAccess(new Date());
			empleado.setLastPasswordChange(new Date());

			// Sign up the new employee
			try {
				LOGGER.log(Level.INFO, "SignUpDesktopFxmlController: Signing up employee {0}.", empleado.getLogin());
				empleadoLogic.createEmpleado(empleado);
				LOGGER.log(Level.INFO, "SignUpDesktopFxmlController: Signed up employee.");
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setContentText("¡Felicidades! El empleado " + empleado.getLogin() + " se ha dado de alta correctamente.");
				alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> stage.hide());

				// If user is signed up correctly open SignIn window
				openSignInWindow();
			} catch (BusinessLogicException ex) {
				LOGGER.log(Level.SEVERE, "SignUpDesktopFxmlController: Exception signing up employee, {0}.", ex.getMessage());

				// TODO
				// Show an error feedback
			}

			/*
			} catch (LoginExistingException e) {
				LOGGER.log(Level.SEVERE, "Sign Up controller::handleSignUpAction: {0}", e.getMessage());
				lblErrorLogin.setText("* Login already exists.");
				lblErrorLogin.setVisible(true);
			} catch (EmailNotUniqueException e) {
				LOGGER.log(Level.SEVERE, "Sign Up controller::handleSignUpAction: {0}", e.getMessage());
				lblErrorEmail.setText("* Email already used.");
				lblErrorEmail.setVisible(true);
			} catch (DatabaseException e) {
				LOGGER.log(Level.SEVERE, "Sign Up controller::handleSignUpAction: {0}", e.getMessage());

				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText("Sorry, something went wrong. Try again.");
				alert.showAndWait();
			}
			 */
		}
	}

	/**
	 * Opens Sign In window.
	 */
	private void openSignInWindow() {
		LOGGER.info("SignUpDesktopFxmlController: Opening SignIn window action.");
		try {
			// Load node graph from fxml file
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/easyorderappdesktop/ui/fxml/SignInDesktopFXMLDocument.fxml"));
			Parent root = (Parent) loader.load();
			// Get controller for graph
			SignInDesktopFxmlController controller = ((SignInDesktopFxmlController) loader.getController());
			controller.setEmpleadoLogic(empleadoLogic);
			// Initialize stage
			controller.initStage(root);
			// Hide sign up stage
			//stage.hide();
		} catch (IOException ex) {
			LOGGER.log(Level.SEVERE, "SignUpDesktopFxmlController: Error opening SignIn window.", ex.getMessage());
		}
	}

	/**
	 * Action event handler for SignIn hyperlink. It opens the sign in window.
	 *
	 * @param event The Action event
	 */
	@FXML
	private void handleSignInAction(ActionEvent event) {
		LOGGER.info("SignUpDesktopFxmlController: Opening SignIn action.");
		openSignInWindow();
	}
}
