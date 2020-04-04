package com.lingnan.supermarket.view;
import javax.swing.UIManager;

public class Nimbus {

	/*nimbus风格*/
	public static void Nimbus() {
	  	try {
    		UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
    		} catch (Exception e) {
    		e.printStackTrace();
    		}
	}
}
