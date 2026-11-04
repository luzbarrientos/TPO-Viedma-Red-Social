package redsocial.interfaz;

import redsocial.algoritmos.Dijkstra;
import redsocial.algoritmos.MergeSortPublicaciones;
import redsocial.algoritmos.Mochila;
import redsocial.datos.DatosEjemplo;
import redsocial.modelo.Iniciativa;
import redsocial.modelo.Publicacion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PanelFuncionalidades {

    private PanelFuncionalidades() {
    }

    // Diseño compartido por las seis funcionalidades.
    static JPanel crearPanel(
            String titulo,
            String descripcion,
            JPanel controles,
            JTextArea salida
    ) {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(25, 30, 25, 30));

        JPanel superior = new JPanel();
        superior.setLayout(new BoxLayout(superior, BoxLayout.Y_AXIS));
        superior.setOpaque(false);

        JLabel encabezado = new JLabel(titulo);
        encabezado.setFont(new Font("SansSerif", Font.BOLD, 22));

        JLabel detalle = new JLabel(descripcion);
        detalle.setForeground(Color.DARK_GRAY);

        superior.add(encabezado);
        superior.add(Box.createVerticalStrut(8));
        superior.add(detalle);
        superior.add(Box.createVerticalStrut(20));
        superior.add(controles);

        salida.setEditable(false);
        salida.setFont(new Font("Monospaced", Font.PLAIN, 13));
        salida.setMargin(new Insets(12, 12, 12, 12));

        panel.add(superior, BorderLayout.NORTH);
        panel.add(new JScrollPane(salida), BorderLayout.CENTER);

        return panel;
    }

    static void ejecutar(JTextArea salida, Runnable operacion) {
        try {
            operacion.run();
        } catch (Exception e) {
            salida.setText("Error: " + e.getMessage());
        }
    }

    // 1. MERGE SORT
    public static JPanel publicaciones() {

        JTextArea salida = new JTextArea(15, 50);
        JPanel controles = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JComboBox<String> criterio = new JComboBox<>(
                new String[]{"Fecha reciente", "Relevancia"}
        );

        JButton boton = new JButton("Ordenar publicaciones");

        controles.add(new JLabel("Ordenar por:"));
        controles.add(criterio);
        controles.add(boton);

        boton.addActionListener(e -> ejecutar(salida, () -> {

            MergeSortPublicaciones.Criterio tipo =
                    criterio.getSelectedIndex() == 0
                            ? MergeSortPublicaciones.Criterio.RECIENTE
                            : MergeSortPublicaciones.Criterio.RELEVANTE;

            Publicacion[] publicaciones =
                    MergeSortPublicaciones.ordenar(
                            DatosEjemplo.publicaciones(), tipo
                    );

            StringBuilder texto =
                    new StringBuilder("PUBLICACIONES ORDENADAS\n\n");

            for (Publicacion publicacion : publicaciones) {
                texto.append(publicacion).append("\n");
            }

            salida.setText(texto.toString());
        }));

        return crearPanel(
                "Publicaciones",
                "Ordenamiento mediante Merge Sort",
                controles,
                salida
        );
    }

    // 2. DIJKSTRA
    public static JPanel contactos() {

        JTextArea salida = new JTextArea(15, 50);
        JPanel controles = new JPanel(new FlowLayout(FlowLayout.LEFT));

        String[] usuarios = {"Ana", "Beto", "Caro", "Dani", "Eva"};

        JComboBox<String> origen = new JComboBox<>(usuarios);
        JComboBox<String> destino = new JComboBox<>(usuarios);

        destino.setSelectedItem("Eva");

        JButton boton = new JButton("Buscar camino");

        controles.add(new JLabel("Origen:"));
        controles.add(origen);
        controles.add(new JLabel("Destino:"));
        controles.add(destino);
        controles.add(boton);

        boton.addActionListener(e -> ejecutar(salida, () -> {

            Dijkstra.Resultado resultado = Dijkstra.buscar(
                    DatosEjemplo.red(),
                    (String) origen.getSelectedItem(),
                    (String) destino.getSelectedItem()
            );

            if (resultado.camino.isEmpty()) {
                salida.setText("No existe un camino entre los usuarios.");
                return;
            }

            salida.setText(
                    "CAMINO MAS CORTO\n\n"
                            + String.join(" -> ", resultado.camino)
                            + "\nTiempo total: "
                            + resultado.minutos + " minutos"
            );
        }));

        return crearPanel(
                "Contactos",
                "Camino mas corto mediante Dijkstra",
                controles,
                salida
        );
    }

    // 3. MOCHILA 0/1
    public static JPanel iniciativas() {

        JTextArea salida = new JTextArea(15, 50);
        JPanel controles = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JSpinner presupuesto = new JSpinner(
                new SpinnerNumberModel(10, 0, 10000, 1)
        );

        JButton boton = new JButton("Seleccionar iniciativas");

        controles.add(new JLabel("Presupuesto:"));
        controles.add(presupuesto);
        controles.add(boton);

        boton.addActionListener(e -> ejecutar(salida, () -> {

            Mochila.Resultado resultado = Mochila.resolver(
                    DatosEjemplo.iniciativas(),
                    (Integer) presupuesto.getValue()
            );

            StringBuilder texto =
                    new StringBuilder("INICIATIVAS SELECCIONADAS\n\n");

            for (Iniciativa iniciativa : resultado.seleccionadas) {
                texto.append(iniciativa).append("\n");
            }

            texto.append("\nCosto total: ").append(resultado.costo);
            texto.append("\nImpacto total: ").append(resultado.impacto);
            texto.append("\nPresupuesto restante: ")
                    .append(resultado.restante);

            salida.setText(texto.toString());
        }));

        return crearPanel(
                "Iniciativas",
                "Optimizacion mediante Mochila 0/1",
                controles,
                salida
        );
    }
}
