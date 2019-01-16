package easyorderappdesktop.transferObject;

import java.io.Serializable;

/**
 * This enumeration is for users profiles. Includes the values USER and ADMIN.
 *
 * @author Leticia
 */
public enum UserPrivilege implements Serializable {

	/**
	 * The user is a regular user.
	 */
	USER,
	/**
	 * The user is a privileged user.
	 */
	ADMIN;
}
