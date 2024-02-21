/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package vista.Grafo;

import java.io.File;
import javax.swing.ImageIcon;

/**
 *
 * @author FA506ICB-HN114W
 */
public class FrmFoto extends javax.swing.JDialog {

    /**
     * Creates new form FrmFoto
     */
    public FrmFoto(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        panelImage1.setIcon(new ImageIcon("Foto/fondo.jpg"));
    }
    
    public FrmFoto(java.awt.Frame parent, boolean modal, File file) {
        super(parent, modal);
        initComponents();
        if (file == null) {
          panelImage1.setIcon(new ImageIcon("Foto/fondo.jpg"));  
        }
        panelImage1.setIcon(new ImageIcon(file.getAbsolutePath()));
        this.setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelImage1 = new org.edisoncor.gui.panel.PanelImage();
        labelHeader1 = new org.edisoncor.gui.label.LabelHeader();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout panelImage1Layout = new javax.swing.GroupLayout(panelImage1);
        panelImage1.setLayout(panelImage1Layout);
        panelImage1Layout.setHorizontalGroup(
            panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        panelImage1Layout.setVerticalGroup(
            panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );

        getContentPane().add(panelImage1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 400, 400));

        labelHeader1.setText("Foto");
        getContentPane().add(labelHeader1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, 120, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmFoto dialog = new FrmFoto(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.label.LabelHeader labelHeader1;
    private org.edisoncor.gui.panel.PanelImage panelImage1;
    // End of variables declaration//GEN-END:variables
}
