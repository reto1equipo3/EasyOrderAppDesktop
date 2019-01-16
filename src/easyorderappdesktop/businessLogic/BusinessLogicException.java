/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyorderappdesktop.businessLogic;

/**
 *
 * @author Imanol
 */
public class BusinessLogicException extends Exception {

	/**
	 * Creates a new instance of <code>BusinessLogicException</code> without
	 * detail message.
	 */
	public BusinessLogicException() {
	}

	/**
	 * Constructs an instance of <code>BusinessLogicException</code> with the
	 * specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public BusinessLogicException(String msg) {
		super(msg);
	}
}
