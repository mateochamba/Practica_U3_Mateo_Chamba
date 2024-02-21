/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista.Grafo;

import controlador.DAO.grafosExemplo.SubEstacionDao;
import controlador.Utiles.Utiles;
import java.io.File;
import java.util.UUID;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import vista.Grafo.tabla.ModeloTablaSubEstacion;

/**
 *
 * @author FA506ICB-HN114W
 */
public class FrmSubEstacion extends javax.swing.JFrame {

    private File fportada;
    private File fescudo;
    private File ficono;

    private ModeloTablaSubEstacion mte = new ModeloTablaSubEstacion();
    private SubEstacionDao ed = new SubEstacionDao();

    public FrmSubEstacion() {
        initComponents();
        limpiar();
    }

    private File cargarFoto() throws Exception {
        File obj = null;
        JFileChooser chooser = new JFileChooser();
        Integer resp = chooser.showOpenDialog(this);
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter
                = new FileNameExtensionFilter("Imagenes", "jpg", "png", "jpeg");
        chooser.addChoosableFileFilter(filter);
        if (resp == JFileChooser.APPROVE_OPTION) {
            obj = chooser.getSelectedFile();
            System.out.println("Ok" + obj.getName());
        } else {
            System.out.println("Error");
        }
        return obj;
    }

    public Boolean verificar() {
        return (!txtEscudo.getText().trim().isEmpty()
                && !txtNombre.getText().trim().isEmpty()
                && !txtPortada.getText().trim().isEmpty()
                && !txtLatitud.getText().trim().isEmpty()
                && !txtLongitud.getText().trim().isEmpty());
    }

    private void cargarTabla() {
        mte.setSubEstaciones(ed.all());
        tbEscuela.setModel(mte);
        tbEscuela.updateUI();
    }

    public void limpiar() {
        txtEscudo.setText("");
        txtNombre.setText("");
        txtPortada.setText("");
        txtLatitud.setText("");
        txtLongitud.setText("");
        cargarTabla();
    }

    public void guardar() throws Exception {
        if (verificar()) {
            String uuidF = UUID.randomUUID().toString();
            String uuidl = UUID.randomUUID().toString();

            Utiles.copiarArchivo(fescudo, new File("Foto/" + uuidl + "." + Utiles.extension(fescudo.getName())));
            Utiles.copiarArchivo(fportada, new File("Foto/" + uuidF + "." + Utiles.extension(fportada.getName())));
            ed.getSubEstacion().getCordenada().setLogitud(Double.parseDouble(txtLongitud.getText()));
            ed.getSubEstacion().getCordenada().setLatitud(Double.parseDouble(txtLatitud.getText()));
            ed.getSubEstacion().setNombre(txtNombre.getText().trim());
            ed.getSubEstacion().setEscudo(uuidl + "." + Utiles.extension(fescudo.getName()));
            ed.getSubEstacion().setPortada(uuidF + "." + Utiles.extension(fportada.getName()));

            if (ed.persist()) {
                limpiar();
                JOptionPane.showMessageDialog(null, "Datos guardados");
                ed.setSubEstacion(null);
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo guardar, hubo un error");
            }
        }
    }

    private void cargarVista() {
        int fila = tbEscuela.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Escoja un registro de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                ed.setSubEstacion(mte.getSubEstaciones().getInfo(fila));
                txtEscudo.setText(ed.getSubEstacion().getEscudo());
                txtPortada.setText(ed.getSubEstacion().getPortada());
                fescudo = new File("foto/" + ed.getSubEstacion().getEscudo());
                fportada = new File("foto/" + ed.getSubEstacion().getPortada());
                txtLatitud.setText(String.valueOf(ed.getSubEstacion().getCordenada().getLatitud()));
                txtLongitud.setText(String.valueOf(ed.getSubEstacion().getCordenada().getLogitud()));
                txtNombre.setText(ed.getSubEstacion().getNombre());
            } catch (Exception ex) {
                ex.toString();
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelShadow1 = new org.edisoncor.gui.panel.PanelShadow();
        jLabel1 = new javax.swing.JLabel();
        txtLongitud = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel2 = new javax.swing.JLabel();
        txtLatitud = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        buttonAction1 = new org.edisoncor.gui.button.ButtonAction();
        txtEscudo = new org.edisoncor.gui.textField.TextFieldRound();
        txtNombre = new org.edisoncor.gui.textField.TextFieldRound();
        buttonAction2 = new org.edisoncor.gui.button.ButtonAction();
        buttonAction3 = new org.edisoncor.gui.button.ButtonAction();
        txtPortada = new org.edisoncor.gui.textField.TextFieldRound();
        buttonAero1 = new org.edisoncor.gui.button.ButtonAero();
        labelHeader1 = new org.edisoncor.gui.label.LabelHeader();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbEscuela = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelShadow1.setBackground(new java.awt.Color(42, 27, 61));
        panelShadow1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Rockstar Extra Bold", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(3, 25, 38));
        jLabel1.setText("Nombre:");

        jLabel2.setFont(new java.awt.Font("Rockstar Extra Bold", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(3, 25, 38));
        jLabel2.setText("Longitud:");

        jLabel3.setFont(new java.awt.Font("Rockstar Extra Bold", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(3, 25, 38));
        jLabel3.setText("Escudo:");

        jLabel4.setFont(new java.awt.Font("Rockstar Extra Bold", 0, 12)); // NOI18N
        jLabel4.setText("Latitid:");

        jLabel5.setFont(new java.awt.Font("Rockstar Extra Bold", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(3, 25, 38));
        jLabel5.setText("Portada:");

        buttonAction1.setBackground(new java.awt.Color(102, 102, 255));
        buttonAction1.setText("Cargar");
        buttonAction1.setFont(new java.awt.Font("Rockstar Extra Bold", 1, 14)); // NOI18N
        buttonAction1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAction1ActionPerformed(evt);
            }
        });

        txtEscudo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtEscudoMouseClicked(evt);
            }
        });
        txtEscudo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEscudoActionPerformed(evt);
            }
        });

        buttonAction2.setText("Cargar");
        buttonAction2.setFont(new java.awt.Font("Rockstar Extra Bold", 1, 14)); // NOI18N
        buttonAction2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAction2ActionPerformed(evt);
            }
        });

        buttonAction3.setBackground(new java.awt.Color(255, 102, 102));
        buttonAction3.setText("Guardar");
        buttonAction3.setFont(new java.awt.Font("Rockstar Extra Bold", 1, 14)); // NOI18N
        buttonAction3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAction3ActionPerformed(evt);
            }
        });

        txtPortada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPortadaMouseClicked(evt);
            }
        });

        buttonAero1.setBackground(new java.awt.Color(153, 0, 0));
        buttonAero1.setText("Limpiar");
        buttonAero1.setFont(new java.awt.Font("Rockstar Extra Bold", 1, 14)); // NOI18N
        buttonAero1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAero1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelShadow1Layout = new javax.swing.GroupLayout(panelShadow1);
        panelShadow1.setLayout(panelShadow1Layout);
        panelShadow1Layout.setHorizontalGroup(
            panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelShadow1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelShadow1Layout.createSequentialGroup()
                        .addGroup(panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1))
                        .addGap(24, 24, 24)
                        .addGroup(panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEscudo, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(buttonAction3, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelShadow1Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addGap(18, 18, 18)
                            .addComponent(txtPortada, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelShadow1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelShadow1Layout.createSequentialGroup()
                                .addComponent(buttonAction2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                                .addGroup(panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(panelShadow1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(29, 29, 29)
                                        .addComponent(txtLatitud, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelShadow1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtLongitud, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(55, 55, 55))
                            .addGroup(panelShadow1Layout.createSequentialGroup()
                                .addComponent(buttonAction1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelShadow1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonAero1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))))
        );
        panelShadow1Layout.setVerticalGroup(
            panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelShadow1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLatitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtEscudo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonAction1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtLongitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtPortada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonAction2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonAero1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonAction3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        labelHeader1.setText("Escuela");
        labelHeader1.setFont(new java.awt.Font("Rockstar Extra Bold", 1, 14)); // NOI18N

        tbEscuela.setBackground(new java.awt.Color(157, 190, 187));
        tbEscuela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbEscuela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbEscuelaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbEscuela);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelHeader1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(panelShadow1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(labelHeader1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelShadow1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonAction2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAction2ActionPerformed

        try {
            fportada = cargarFoto();
            System.out.println(fportada);
            if (fportada != null) {
                txtPortada.setText(fportada.getAbsolutePath());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_buttonAction2ActionPerformed

    private void buttonAction1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAction1ActionPerformed
        try {
            fescudo = cargarFoto();
            System.out.println(fescudo);
            if (fescudo != null) {
                txtEscudo.setText(fescudo.getAbsolutePath());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_buttonAction1ActionPerformed

    private void txtEscudoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtEscudoMouseClicked
        if (evt.getClickCount() >= 2) {
            if (fescudo != null) {
                new FrmFoto(null, true, fescudo).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "No existe ninguna imagen");
            }
        }
    }//GEN-LAST:event_txtEscudoMouseClicked

    private void buttonAction3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAction3ActionPerformed
        try {
            guardar();
        } catch (Exception ex) {
            System.out.println("Error" + ex.getMessage());
        }
    }//GEN-LAST:event_buttonAction3ActionPerformed

    private void txtPortadaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPortadaMouseClicked
        if (evt.getClickCount() >= 2) {
            new FrmFoto(null, true, fportada).setVisible(true);
        }
    }//GEN-LAST:event_txtPortadaMouseClicked

    private void buttonAero1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAero1ActionPerformed
        limpiar();
    }//GEN-LAST:event_buttonAero1ActionPerformed

    private void tbEscuelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbEscuelaMouseClicked
//        cargarVista();
//        if (evt.getClickCount() >= 2) {
//            FrmDetalleEscuela escuelaFrm = new FrmDetalleEscuela(null, true);
//            escuelaFrm.cargarEscuela(ed.get(tbEscuela.getSelectedRow()));
//            escuelaFrm.cargarVista();
//            escuelaFrm.setVisible(true);
        // }
    }//GEN-LAST:event_tbEscuelaMouseClicked

    private void txtEscudoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEscudoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEscudoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmSubEstacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmSubEstacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmSubEstacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmSubEstacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmSubEstacion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonAction buttonAction1;
    private org.edisoncor.gui.button.ButtonAction buttonAction2;
    private org.edisoncor.gui.button.ButtonAction buttonAction3;
    private org.edisoncor.gui.button.ButtonAero buttonAero1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private org.edisoncor.gui.label.LabelHeader labelHeader1;
    private org.edisoncor.gui.panel.PanelShadow panelShadow1;
    private javax.swing.JTable tbEscuela;
    private org.edisoncor.gui.textField.TextFieldRound txtEscudo;
    private org.edisoncor.gui.textField.TextFieldRound txtLatitud;
    private org.edisoncor.gui.textField.TextFieldRound txtLongitud;
    private org.edisoncor.gui.textField.TextFieldRound txtNombre;
    private org.edisoncor.gui.textField.TextFieldRound txtPortada;
    // End of variables declaration//GEN-END:variables
}
