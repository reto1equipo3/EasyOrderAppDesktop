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
public class FTPLogicFactory {
	
	public static FTPLogic createFTPLogicImplementation() {
		return new FTPLogicImplementation();
	}
}
