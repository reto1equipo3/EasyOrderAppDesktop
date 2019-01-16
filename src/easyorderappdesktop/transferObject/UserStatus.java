package easyorderappdesktop.transferObject;

import java.io.Serializable;

/**
 * This enumeration is for users status. Includes the values ENABLED and
 * DISABLED.
 *
 * @author Leticia
 */
public enum UserStatus implements Serializable {
	/**
	 * If user is in date base is ENABLED.
	 */
	ENABLED,
	/**
	 * If user isn't in date base is DISABLED .
	 */
	DISABLED;
}
