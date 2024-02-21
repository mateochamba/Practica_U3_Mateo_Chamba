/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package vista.Grafo;

import controlador.DAO.grafosExemplo.SubEstacionDao;
import controlador.TDA.grafos.Grafo;
import controlador.TDA.grafos.Adyacencia;
import controlador.TDA.grafos.PaintGraph;
import controlador.TDA.listas.DynamicList;
import controlador.TDA.listas.Exception.EmptyException;
import controlador.Utiles.Utiles;
import grafo.modelo.SubEstacion;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import vista.Grafo.utiles.UtilesVsitaSubEstacion;
import vista.Grafo.tabla.ModeloTablaAdayacencua;

/**
 *
 * @author FA506ICB-HN114W
 */
public class FrmGrafo extends javax.swing.JDialog {

    private Integer nodoOrigenSeleccionado;
    private Integer nodoDestinoSeleccionado;
    private DynamicList<Integer> caminoMasCorto;
    private Grafo grafo;
    private PaintGraph paintGraph;
    SubEstacionDao ed;
    ModeloTablaAdayacencua mta = new ModeloTablaAdayacencua();

    public FrmGrafo(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        //NUEVO METODO

        ed = new SubEstacionDao();
        //cargarGrafoInicial();
        paintGraph = new PaintGraph(ed);
        limpiar();

    }

    private void cargarGrafoInicial() {
        try {
            ed.cargarGrafo();  // Lógica para cargar el grafo
            grafo = ed.getGrafo(); // Asegúrate de asignar el grafo correctamente
            if (grafo == null) {
                throw new NullPointerException("El grafo no se ha cargado correctamente.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void limpiar() {
        try {
            UtilesVsitaSubEstacion.cargarComboSubEstacion(cbxOrigen);
            UtilesVsitaSubEstacion.cargarComboSubEstacion(cbxDestino);
            cargarTabla();
        } catch (Exception e) {

        }
    }

    private DynamicList<Integer> obtenerNodosDisponibles(int indiceActual) {
        DynamicList<Integer> disponibles = new DynamicList<>();
        for (int j = indiceActual + 1; j < ed.getLista().getLenght(); j++) {
            disponibles.add(j);
        }
        return disponibles;
    }

    public void Adyacencia() throws EmptyException, Exception {
        Random random = new Random();
        int maxAdyacencias = 2;

        for (int i = 0; i < ed.getLista().getLenght(); i++) {
            int numAdyacencias = random.nextInt(maxAdyacencias - 1) + 2;

            DynamicList<Integer> disponibles = obtenerNodosDisponibles(i);

            for (int k = 0; k < numAdyacencias && !disponibles.isEmpty(); k++) {
                int indiceAleatorio = random.nextInt(disponibles.getLenght());
                int indiceNodo = disponibles.getInfo(indiceAleatorio);

                Double distancia = UtilesVsitaSubEstacion.calcularDistanciaEscuelas(
                        ed.getLista().getInfo(i),
                        ed.getLista().getInfo(indiceNodo)
                );
                distancia = UtilesVsitaSubEstacion.redondear(distancia);
                ed.getGrafo().insertEdge(
                        ed.getLista().getInfo(i),
                        ed.getLista().getInfo(indiceNodo),
                        distancia
                );
                disponibles.delete(indiceAleatorio);
            }
        }
        ed.guardarGrafo();
    }

    public void mostrarGrafo() throws Exception {
        PaintGraph p = new PaintGraph(ed);
        p.updateFile(ed.getGrafo());
        Utiles.abrirArchivoHTML("C:/Users/FA506ICB-HN114W/Downloads/ESTRUCTURA/UNIDAD3/Practica_U3/d3/grafo.html");

    }

    public void mostrarMapa() throws Exception {
        UtilesVsitaSubEstacion.crearMapaSubestacion(ed.getGrafo());
        Runtime rt = Runtime.getRuntime();
        Utiles.abrirArchivoHTML("C:/Users/FA506ICB-HN114W/Downloads/ESTRUCTURA/UNIDAD3/Practica_U3/mapas/index.html");
    }

    public void cargarTabla() throws Exception {
        mta.setGrafo(ed.getGrafo());
        mta.fireTableDataChanged();
        tblTabla.setModel(mta);
        tblTabla.updateUI();
    }

    public void guardarGrafo() {
        try {
            int i = JOptionPane.
                    showConfirmDialog(null, "Esta seguro de Guardar?", "Advertencia", JOptionPane.OK_CANCEL_OPTION);

            if (i == JOptionPane.OK_OPTION) {
                if (ed.getGrafo() != null) {
                    ed.guardarGrafo();
                    JOptionPane.showMessageDialog(null, "Guardado");
                } else {
                    JOptionPane.showMessageDialog(null, "Los grafos vacios no se guardaran");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void load() throws Exception {
        int i = JOptionPane.showConfirmDialog(null, "¿Está seguro de cargar el grafo?", "Advertencia", JOptionPane.OK_CANCEL_OPTION);

        if (i == JOptionPane.OK_OPTION) {
            ed.cargarGrafo();
            limpiar();  // Limpia y actualiza la tabla después de cargar el grafo
            JOptionPane.showMessageDialog(null, "¡Se ha cargado el grafo!");
        }
    }

    private void mostrarCaminoMasCorto(DynamicList<Adyacencia> camino) throws EmptyException {
        StringBuilder mensaje = new StringBuilder("Camino mas corto:  ");

        for (int i = 0; i < camino.getLenght(); i++) {
            Adyacencia adyacencia = camino.getInfo(i);
            mensaje.append("Nodo:  ").append(adyacencia).append(" ->   ");
        }

        mensaje.delete(mensaje.length() - 4, mensaje.length());
        lblFloyd.setText(mensaje.toString());
    }

    

    //RECORRIDO PARA BUSQUEDA POR PROFUNDIDAD
    public DynamicList<Integer> recorridoProfundidad(Integer v) throws Exception {
        DynamicList<Integer> recorrido = new DynamicList<>();
        DynamicList<Integer> pila = new DynamicList<>();
        DynamicList<Integer> visitados = new DynamicList<>();
        Integer w;
        pila.add(v);
        while (!pila.isEmpty()) {
            Integer u = pila.delete(pila.getLenght() - 1);
            if (!visitados.contains(u)) {
                visitados.add(u);
                recorrido.add(u);
                DynamicList<Adyacencia> listaA = ed.getGrafo().adyacentes(u);
                if (listaA != null) { // add verificación de nulidad para adyacentes
                    for (int i = 0; i < listaA.getLenght(); i++) {
                        Adyacencia a = listaA.getInfo(i);
                        w = a.getDestino();
                        if (!visitados.contains(w)) {
                            pila.add(w);
                        }
                    }
                } else {
                    throw new Exception("Error al realizar el recorrido en profundidad: Lista de adyacentes nula.");
                }
            }
        }
        return recorrido;
    }

    //MOSTRAR RECORRIDO
    private void mostrarRecorridoEnTextArea(DynamicList<Integer> recorrido, javax.swing.JTextArea textArea) throws EmptyException {
        textArea.setText("");
        for (int i = 0; i < recorrido.getLenght(); i++) {
            textArea.append(recorrido.getInfo(i) + " -> ");
        }
    }

    //RECORRIDO POARA BUSQUEDA POR ANCHURA
    public DynamicList<Integer> recorridoAnchura(Integer v) throws Exception {
        DynamicList<Integer> recorrido = new DynamicList<>();
        DynamicList<Integer> cola = new DynamicList<>();
        DynamicList<Integer> visitados = new DynamicList<>();

        if (ed.getGrafo() == null) {
            throw new Exception("Error al realizar el recorrido en anchura: El grafo es nulo.");
        }

        cola.add(v);
        visitados.add(v);

        while (!cola.isEmpty()) {
            Integer u = cola.delete(0);
            recorrido.add(u);

            DynamicList<Adyacencia> listaA = ed.getGrafo().adyacentes(u);
            if (listaA != null) {
                for (int i = 0; i < listaA.getLenght(); i++) {
                    Adyacencia a = listaA.getInfo(i);
                    Integer w = a.getDestino();
                    if (!visitados.contains(w)) {
                        visitados.add(w);
                        cola.add(w);
                    }
                }
            } else {
                throw new Exception(
                        "Error al realizar el recorrido en anchura: Lista de adyacentes nula para el vértice " + u);
            }
        }
        return recorrido;
    }

    private void mostrarResultadoEnTextArea(String resultado, javax.swing.JTextArea textArea) {
        textArea.setText(resultado);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbxDestino = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTabla = new javax.swing.JTable();
        btnAdyacencia = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        bntCargar = new javax.swing.JButton();
        panelRound1 = new org.edisoncor.gui.panel.PanelRound();
        btnMapa = new org.edisoncor.gui.button.ButtonAqua();
        btnGrafo = new org.edisoncor.gui.button.ButtonAqua();
        cbxOrigen = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnBuscarCamino = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        lblFloyd = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        lblBellaman = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        btnAnchura = new javax.swing.JButton();
        btnProfundidad = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        lblanchura = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        lblprofundidad = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(3, 25, 38));

        jLabel1.setFont(new java.awt.Font("Rockstar Extra Bold", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Origen:");

        jLabel2.setFont(new java.awt.Font("Rockstar Extra Bold", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Destino:");

        cbxDestino.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxDestino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxDestinoActionPerformed(evt);
            }
        });

        tblTabla.setBackground(new java.awt.Color(157, 190, 187));
        tblTabla.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblTabla);

        btnAdyacencia.setBackground(new java.awt.Color(119, 172, 162));
        btnAdyacencia.setFont(new java.awt.Font("Rockstar Extra Bold", 0, 12)); // NOI18N
        btnAdyacencia.setForeground(new java.awt.Color(255, 255, 255));
        btnAdyacencia.setText("Adyacencia");
        btnAdyacencia.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnAdyacencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdyacenciaActionPerformed(evt);
            }
        });

        btnGuardar.setBackground(new java.awt.Color(119, 172, 162));
        btnGuardar.setFont(new java.awt.Font("Rockstar Extra Bold", 0, 12)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("GUARDAR");
        btnGuardar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        bntCargar.setBackground(new java.awt.Color(119, 172, 162));
        bntCargar.setFont(new java.awt.Font("Rockstar Extra Bold", 0, 12)); // NOI18N
        bntCargar.setForeground(new java.awt.Color(255, 255, 255));
        bntCargar.setText("CARGAR");
        bntCargar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        bntCargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntCargarActionPerformed(evt);
            }
        });

        btnMapa.setBackground(new java.awt.Color(70, 129, 137));
        btnMapa.setText("Mapa");
        btnMapa.setFont(new java.awt.Font("Rockstar Extra Bold", 0, 12)); // NOI18N
        btnMapa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMapaActionPerformed(evt);
            }
        });

        btnGrafo.setBackground(new java.awt.Color(70, 129, 137));
        btnGrafo.setText("Grafo");
        btnGrafo.setFont(new java.awt.Font("Rockstar Extra Bold", 0, 12)); // NOI18N
        btnGrafo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGrafoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(btnMapa, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGrafo, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMapa, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGrafo, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        cbxOrigen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxOrigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxOrigenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 44, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAdyacencia, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(140, 140, 140))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(panelRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxDestino, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxOrigen, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bntCargar, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbxOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbxDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAdyacencia, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bntCargar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(3, 25, 38));

        jPanel4.setBackground(new java.awt.Color(157, 190, 187));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CAMINO MAS CORTO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Rockstar Extra Bold", 0, 18), new java.awt.Color(255, 255, 255))); // NOI18N

        btnBuscarCamino.setBackground(new java.awt.Color(119, 172, 162));
        btnBuscarCamino.setFont(new java.awt.Font("Rockstar Extra Bold", 0, 12)); // NOI18N
        btnBuscarCamino.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarCamino.setText("Buscar Camino");
        btnBuscarCamino.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnBuscarCamino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarCaminoActionPerformed(evt);
            }
        });

        lblFloyd.setColumns(20);
        lblFloyd.setRows(5);
        jScrollPane2.setViewportView(lblFloyd);

        lblBellaman.setColumns(20);
        lblBellaman.setRows(5);
        jScrollPane5.setViewportView(lblBellaman);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addComponent(jScrollPane5)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(btnBuscarCamino, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(94, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBuscarCamino, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(157, 190, 187));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "BUSQUEDA DE ANCHURA Y PROFUNDIDAD", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Rockstar Extra Bold", 0, 18), new java.awt.Color(255, 255, 255))); // NOI18N

        btnAnchura.setBackground(new java.awt.Color(3, 25, 38));
        btnAnchura.setFont(new java.awt.Font("Rockstar Extra Bold", 0, 12)); // NOI18N
        btnAnchura.setForeground(new java.awt.Color(255, 255, 255));
        btnAnchura.setText("ANCHURA");
        btnAnchura.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnAnchura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnchuraActionPerformed(evt);
            }
        });

        btnProfundidad.setBackground(new java.awt.Color(3, 25, 38));
        btnProfundidad.setFont(new java.awt.Font("Rockstar Extra Bold", 0, 12)); // NOI18N
        btnProfundidad.setForeground(new java.awt.Color(255, 255, 255));
        btnProfundidad.setText("Profundidad");
        btnProfundidad.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnProfundidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProfundidadActionPerformed(evt);
            }
        });

        lblanchura.setColumns(20);
        lblanchura.setRows(5);
        jScrollPane3.setViewportView(lblanchura);

        lblprofundidad.setColumns(20);
        lblprofundidad.setRows(5);
        jScrollPane4.setViewportView(lblprofundidad);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(btnProfundidad, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(btnAnchura, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(94, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAnchura, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnProfundidad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMapaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMapaActionPerformed
        try {
            mostrarMapa();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnMapaActionPerformed

    private void btnGrafoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGrafoActionPerformed
        try {
            mostrarGrafo();
        } catch (Exception ex) {
            Logger.getLogger(FrmGrafo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGrafoActionPerformed

    private void bntCargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntCargarActionPerformed
        try {
            load();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_bntCargarActionPerformed

    private void btnAdyacenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdyacenciaActionPerformed
        try {
            Adyacencia();
            cargarTabla();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error");
        }
    }//GEN-LAST:event_btnAdyacenciaActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardarGrafo();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void cbxOrigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxOrigenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxOrigenActionPerformed

    private void cbxDestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxDestinoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxDestinoActionPerformed

    private void btnBuscarCaminoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarCaminoActionPerformed
        String algoritmo;
    JTextArea textArea;

    // Determinar el algoritmo y el área de texto según el botón que haya sido presionado
    if (evt.getSource() == btnBuscarCamino) {
        // Puedes ejecutar ambos algoritmos en este bloque
        try {
            long tiempoInicioFloyd = System.currentTimeMillis();
            String resultadoFloyd = ed.floydWarshall(ed.getGrafo());
            mostrarResultadoEnTextArea(resultadoFloyd, lblFloyd);
            long tiempoFinFloyd = System.currentTimeMillis();
            long tiempoEjecucionFloyd = tiempoFinFloyd - tiempoInicioFloyd;
            JOptionPane.showMessageDialog(null, "Tiempo de ejecución de Floyd-Warshall: " + tiempoEjecucionFloyd + " ms");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al ejecutar el algoritmo de Floyd-Warshall: " + ex.getMessage());
        }

        try {
            long tiempoInicioBellman = System.currentTimeMillis();
            String resultadoBellman = ed.bellmanFord(ed.getGrafo(), 1);
            mostrarResultadoEnTextArea(resultadoBellman, lblBellaman);
            long tiempoFinBellman = System.currentTimeMillis();
            long tiempoEjecucionBellman = tiempoFinBellman - tiempoInicioBellman;
            JOptionPane.showMessageDialog(null, "Tiempo de ejecución de Bellman-Ford: " + tiempoEjecucionBellman + " ms");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al ejecutar el algoritmo de Bellman-Ford: " + ex.getMessage());
        }
    }
    }//GEN-LAST:event_btnBuscarCaminoActionPerformed

    private void btnAnchuraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnchuraActionPerformed
        try {
            long tiempoInicio = System.currentTimeMillis();
            DynamicList<Integer> recorrido = recorridoAnchura(1);
            mostrarRecorridoEnTextArea(recorrido, lblanchura);
            long tiempoFin = System.currentTimeMillis();
            long tiempoEjecucion = tiempoFin - tiempoInicio;
            JOptionPane.showMessageDialog(null, "Tiempo de ejecución: " + tiempoEjecucion + " ms");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al realizar el recorrido en anchura: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnAnchuraActionPerformed

    private void btnProfundidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfundidadActionPerformed
        try {
            long tiempoInicio = System.currentTimeMillis();
            DynamicList<Integer> recorrido = recorridoProfundidad(1);
            mostrarRecorridoEnTextArea(recorrido, lblprofundidad);
            long tiempoFin = System.currentTimeMillis();
            long tiempoEjecucion = tiempoFin - tiempoInicio;
            JOptionPane.showMessageDialog(null, "Tiempo de ejecución: " + tiempoEjecucion + " ms");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al realizar el recorrido en profundidad: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnProfundidadActionPerformed

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
            java.util.logging.Logger.getLogger(FrmGrafo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmGrafo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmGrafo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmGrafo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmGrafo dialog = new FrmGrafo(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton bntCargar;
    private javax.swing.JButton btnAdyacencia;
    private javax.swing.JButton btnAnchura;
    private javax.swing.JButton btnBuscarCamino;
    private org.edisoncor.gui.button.ButtonAqua btnGrafo;
    private javax.swing.JButton btnGuardar;
    private org.edisoncor.gui.button.ButtonAqua btnMapa;
    private javax.swing.JButton btnProfundidad;
    private javax.swing.JComboBox<String> cbxDestino;
    private javax.swing.JComboBox<String> cbxOrigen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea lblBellaman;
    private javax.swing.JTextArea lblFloyd;
    private javax.swing.JTextArea lblanchura;
    private javax.swing.JTextArea lblprofundidad;
    private org.edisoncor.gui.panel.PanelRound panelRound1;
    private javax.swing.JTable tblTabla;
    // End of variables declaration//GEN-END:variables
}
