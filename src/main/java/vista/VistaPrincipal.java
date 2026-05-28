package vista;

import modelo.Actuador;
import modelo.Sensor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VistaPrincipal extends JFrame {
    // --- Sensores ---
    private DefaultTableModel modeloSensores;
    private JButton botonSimularLectura;

    // --- Actuadores (construidos dinámicamente por configurarActuadores) ---
    private JPanel contenedorActuadores;
    private final List<JLabel> etiquetasEstado = new ArrayList<>();
    private final List<JComboBox<String>> combosAccion    = new ArrayList<>();
    private final List<JButton>           botonesAplicar  = new ArrayList<>();

    // --- Reglas (construidas dinámicamente por configurarReglas) ---
    private JPanel contenedorReglas;
    private final List<JCheckBox> checksReglas = new ArrayList<>();

    // --- Log ---
    private JTextArea areaLog;

    // --- Barra inferior ---
    private JButton botonCiclo;

    public VistaPrincipal() {
        super("SmartTecnoHouse - Panel de Control");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1150, 640);
        setLayout(new BorderLayout(8, 8));

        add(crearPanelSensores(), BorderLayout.WEST);
        add(crearPanelCentro(),   BorderLayout.CENTER);
        add(crearPanelLog(),      BorderLayout.EAST);
        add(crearBarraInferior(), BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    // -------------------------------------------------------------------------
    // Construcción de paneles
    // -------------------------------------------------------------------------

    private JPanel crearPanelSensores() {
        modeloSensores = new DefaultTableModel(new String[]{"Sensor", "Estado"}, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        JTable tabla = new JTable(modeloSensores);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(140);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(80);

        botonSimularLectura = new JButton("Simular Lectura");

        JPanel panel = new JPanel(new BorderLayout(4, 4));
        panel.setBorder(BorderFactory.createTitledBorder("Sensores"));
        panel.setPreferredSize(new Dimension(250, 0));
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        panel.add(botonSimularLectura,    BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelCentro() {
        // Panel de actuadores con scroll
        contenedorActuadores = new JPanel();
        contenedorActuadores.setLayout(new BoxLayout(contenedorActuadores, BoxLayout.Y_AXIS));
        JScrollPane scrollAct = new JScrollPane(contenedorActuadores);
        scrollAct.setBorder(BorderFactory.createTitledBorder("Actuadores"));

        // Panel de reglas (checkboxes, sin scroll)
        contenedorReglas = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 6));
        contenedorReglas.setBorder(BorderFactory.createTitledBorder("Reglas de Automatización"));

        JPanel wrapper = new JPanel(new BorderLayout(0, 6));
        wrapper.add(scrollAct,        BorderLayout.CENTER);
        wrapper.add(contenedorReglas, BorderLayout.SOUTH);
        return wrapper;
    }

    private JPanel crearPanelLog() {
        areaLog = new JTextArea();
        areaLog.setEditable(false);
        areaLog.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Log de Acciones"));
        panel.setPreferredSize(new Dimension(390, 0));
        panel.add(new JScrollPane(areaLog), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearBarraInferior() {
        botonCiclo = new JButton("Procesar Ciclo Automatico");
        botonCiclo.setFont(botonCiclo.getFont().deriveFont(Font.BOLD, 13f));
        botonCiclo.setPreferredSize(new Dimension(300, 36));

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 6));
        panel.add(botonCiclo);
        return panel;
    }

    // -------------------------------------------------------------------------
    // API pública para el Controlador
    // -------------------------------------------------------------------------

    /**
     * Construye una fila de control (nombre | estado | combo | botón) por cada actuador.
     */
    public void configurarActuadores(List<Actuador> actuadores) {
        contenedorActuadores.removeAll();
        etiquetasEstado.clear();
        combosAccion.clear();
        botonesAplicar.clear();

        for (Actuador a : actuadores) {
            JLabel lblNombre = new JLabel(a.getNombre());
            lblNombre.setPreferredSize(new Dimension(170, 24));

            JLabel lblEstado = new JLabel(a.getEstadoActual());
            lblEstado.setPreferredSize(new Dimension(65, 24));
            lblEstado.setForeground(new Color(0, 100, 180));
            etiquetasEstado.add(lblEstado);

            JComboBox<String> combo = new JComboBox<>(a.getAccionesPosibles());
            combosAccion.add(combo);

            JButton btn = new JButton("Aplicar");
            botonesAplicar.add(btn);

            JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
            fila.setBorder(BorderFactory.createEtchedBorder());
            fila.add(lblNombre);
            fila.add(lblEstado);
            fila.add(combo);
            fila.add(btn);
            fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
            contenedorActuadores.add(fila);
        }

        contenedorActuadores.revalidate();
        contenedorActuadores.repaint();
    }

    /**
     * Construye un checkbox por cada regla con su estado inicial (activa/inactiva).
     * Debe llamarse una sola vez, antes de mostrar la ventana.
     *
     * @param nombres  nombres de todas las reglas posibles
     * @param activas  {@code true} si la regla del mismo índice está activa
     */
    public void configurarReglas(List<String> nombres, boolean[] activas) {
        contenedorReglas.removeAll();
        checksReglas.clear();

        for (int i = 0; i < nombres.size(); i++) {
            JCheckBox cb = new JCheckBox(nombres.get(i), activas[i]);
            checksReglas.add(cb);
            contenedorReglas.add(cb);
        }

        contenedorReglas.revalidate();
        contenedorReglas.repaint();
    }

    /** Reemplaza las filas de la tabla de sensores con los datos actuales. */
    public void actualizarSensores(List<Sensor> sensores) {
        modeloSensores.setRowCount(0);
        for (Sensor s : sensores) {
            modeloSensores.addRow(new Object[]{s.getNombre(), s.getEstadoActual()});
        }
    }

    /** Actualiza la etiqueta de estado de un actuador concreto (por índice de lista). */
    public void actualizarEstadoActuador(int idx, String estado) {
        etiquetasEstado.get(idx).setText(estado);
    }

    /** Reemplaza el contenido del área de log con las líneas del historial. */
    public void actualizarLog(List<String> lineas) {
        StringBuilder sb = new StringBuilder();
        for (String l : lineas) sb.append(l).append('\n');
        areaLog.setText(sb.toString());
        areaLog.setCaretPosition(areaLog.getDocument().getLength());
    }

    // Getters para que el Controlador adjunte los listeners
    public JButton   getBotonCiclo()                { return botonCiclo; }
    public JButton   getBotonSimularLectura()       { return botonSimularLectura; }
    public JButton   getBotonAplicar(int idx)       { return botonesAplicar.get(idx); }
    public JCheckBox getCheckRegla(int idx)         { return checksReglas.get(idx); }
    public String    getAccionSeleccionada(int idx) { return (String) combosAccion.get(idx).getSelectedItem(); }
}
