package easyorderappdesktop.transferObject;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Data Transfer Object used in UI and client side for representing Empleado
 * entity.
 *
 * @author Imanol
 */
@XmlRootElement(name = "empleado")
public class Empleado implements Serializable {

	private final SimpleIntegerProperty id;
	private final SimpleStringProperty login;
	private final SimpleStringProperty email;
	private final SimpleStringProperty fullname;
	private final SimpleStringProperty password;
	private final SimpleStringProperty telefono;
	private final SimpleObjectProperty<UserStatus> status;
	private final SimpleObjectProperty<UserPrivilege> privilege;
	private final SimpleObjectProperty<Date> lastAccess;
	private final SimpleObjectProperty<Date> lastPasswordChange;
	private final SimpleObjectProperty<Date> fechaDeNacimiento;

	public Empleado() {
		this.id = new SimpleIntegerProperty();
		this.login = new SimpleStringProperty();
		this.email = new SimpleStringProperty();
		this.fullname = new SimpleStringProperty();
		this.password = new SimpleStringProperty();
		this.telefono = new SimpleStringProperty();
		this.status = new SimpleObjectProperty();
		this.privilege = new SimpleObjectProperty();
		this.lastAccess = new SimpleObjectProperty();
		this.lastPasswordChange = new SimpleObjectProperty();
		this.fechaDeNacimiento = new SimpleObjectProperty();
	}

	public Empleado(SimpleIntegerProperty id,
		SimpleStringProperty login,
		SimpleStringProperty email,
		SimpleStringProperty fullname,
		SimpleStringProperty password,
		SimpleStringProperty telefono,
		SimpleObjectProperty<UserStatus> status,
		SimpleObjectProperty<UserPrivilege> privilege,
		SimpleObjectProperty<Date> lastAccess,
		SimpleObjectProperty<Date> lastPasswordChange,
		SimpleObjectProperty<Date> fechaDeNacimiento) {
		this.id = id;
		this.login = login;
		this.email = email;
		this.fullname = fullname;
		this.password = password;
		this.telefono = telefono;
		this.status = status;
		this.privilege = privilege;
		this.lastAccess = lastAccess;
		this.lastPasswordChange = lastPasswordChange;
		this.fechaDeNacimiento = fechaDeNacimiento;
	}


	public int getId() {
		return this.id.get();
	}

	public void setId(int id) {
		this.id.set(id);
	}

	@XmlElement(name = "login")
	public String getLogin() {
		return this.login.get();
	}

	public void setLogin(String login) {
		this.login.set(login);
	}

	@XmlElement(name = "email")
	public String getEmail() {
		return this.email.get();
	}

	public void setEmail(String email) {
		this.email.set(email);
	}

	@XmlElement(name = "fullname")
	public String getFullname() {
		return this.fullname.get();
	}

	public void setFullname(String fullname) {
		this.fullname.set(fullname);
	}

	@XmlElement(name = "password")
	public String getPassword() {
		return this.password.get();
	}

	public void setPassword(String password) {
		this.password.set(password);
	}

	@XmlElement(name = "telefono")
	public String getTelefono() {
		return this.telefono.get();
	}

	public void setTelefono(String telefono) {
		this.telefono.set(telefono);
	}

	@XmlElement(name = "status")
	public UserStatus getStatus() {
		return this.status.get();
	}

	public void setStatus(UserStatus status) {
		this.status.set(status);
	}

	@XmlElement(name = "privilege")
	public UserPrivilege getPrivilege() {
		return this.privilege.get();
	}

	public void setPrivilege(UserPrivilege privilege) {
		this.privilege.set(privilege);
	}

	@XmlElement(name = "lastAccess")
	public Date getLastAccess() {
		return this.lastAccess.get();
	}

	public void setLastAccess(Date lastAccess) {
		this.lastAccess.set(lastAccess);
	}

	@XmlElement(name = "lastPasswordChange")
	public Date getLastPasswordChange() {
		return this.lastPasswordChange.get();
	}

	public void setLastPasswordChange(Date lastPasswordChange) {
		this.lastPasswordChange.set(lastPasswordChange);
	}

	@XmlElement(name = "fechaDeNacimiento")
	public Date getFechaDeNacimiento() {
		return this.fechaDeNacimiento.get();
	}

	public void setFechaDeNacimiento(Date fechaDeNacimiento) {
		this.fechaDeNacimiento.set(fechaDeNacimiento);
	}

	/**
	 * HashCode method implementation for the entity.
	 *
	 * @return An integer value as hashcode for the object.
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	/**
	 * This method compares two employee entities for equality. This
	 * implementation compares id field value for equality.
	 *
	 * @param obj The object to compare to.
	 * @return True if objects are equal, otherwise false.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Empleado other = (Empleado) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}

	/**
	 * This method returns a String representation for an employee entity
	 * instance.
	 *
	 * @return The String representation for the employee object.
	 */
	@Override
	public String toString() {
		return "Empleado{" + "id=" + id + ", login=" + login + '}';
	}

}
