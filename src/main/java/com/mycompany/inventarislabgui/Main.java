/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventarislabgui;
import com.mycompany.inventarislabgui.controller.AlatController;
import com.mycompany.inventarislabgui.view.AlatView;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author muhammadyoga
 */
public class Main {
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | UnsupportedLookAndFeelException ex) {
            System.err.println("GUI: This Theme can't re-render");
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            AlatView view = new AlatView();
            AlatController controller = new AlatController(view);
            view.setVisible(true);
            controller.muatData();
        });
    }
}
