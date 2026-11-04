
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

    // Diseño general de las pantallas.
    public static JPanel crearPanel(
            String titulo,
            String descripcion,
            JPanel controles,
            JTextArea salida
    ) {

        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(25, 30, 25, 30));

        JPanel superior = new JPanel();
        superior.setLayout(
                new BoxLayout(superior, BoxLayout.Y_AXIS)
        );
        superior.setOpaque(false);

        JLabel encabezado = new JLabel(titulo);
        encabezado.setFont(
                new Font("SansSerif", Font.BOLD, 23)
        );

        JLabel detalle = new JLabel(descripcion);
        detalle.setFont(
                new Font("SansSerif", Font.PLAIN, 13)
        );
        detalle.setForeground(new Color(90, 100, 115));

        controles.setOpaque(false);

        superior.add(encabezado);
        superior.add(Box.createVerticalStrut(8));
        superior.add(detalle);
        superior.add(Box.createVerticalStrut(20));
        superior.add(controles);

        salida.setEditable(false);
        salida.setLineWrap(true);
        salida.setWrapStyleWord(true);
        salida.setFont(
                new Font("Monospaced", Font.PLAIN, 13)
        );
        salida.setBackground(new Color(250, 251, 253));
        salida.setForeground(new Color(35, 45, 60));
        salida.setMargin(new Insets(15, 15, 15, 15));

        JScrollPane desplazamiento = new JScrollPane(salida);
        desplazamiento.setBorder(
                BorderFactory.createLineBorder(
                        new Color(220, 225, 230)
                )
        );

        panel.add(superior, BorderLayout.NORTH);
        panel.add(desplazamiento, BorderLayout.CENTER);

        return panel;
    }

    // Controla los errores al ejecutar algoritmos.
    public static void ejecutar(
            JTextArea salida,
            Runnable operacion
    ) {
        try {
            operacion.run();
            salida.setCaretPosition(0);

        } catch (Exception e) {
            salida.setText(
                    "No se pudo completar la operacion.\n\n"
                            + "Detalle: " + e.getMessage()
            );
        }
    }

    // Diseño compartido de los seis botones.
    // Se dibuja manualmente para evitar problemas
    // con los colores del estilo de Windows.
    public static JButton crearBoton(String texto) {

        JButton boton = new JButton(texto) {

            @Override
            protected void paintComponent(Graphics g) {

                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                if (getModel().isPressed()) {
                    g2.setColor(new Color(20, 40, 70));
                } else if (getModel().isRollover()) {
                    g2.setColor(new Color(48, 81, 125));
                } else {
                    g2.setColor(new Color(34, 58, 94));
                }

                g2.fillRoundRect(
                        0, 0,
                        getWidth(), getHeight(),
                        12, 12
                );

                g2.dispose();

                super.paintComponent(g);
            }
        };

        boton.setFont(
                new Font("SansSerif", Font.BOLD, 13)
        );

        boton.setForeground(Color.WHITE);
        boton.setContentAreaFilled(false);
        boton.setOpaque(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setRolloverEnabled(true);

        boton.setBorder(
                new EmptyBorder(10, 18, 10, 18)
        );

        boton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        return boton;
    }

    // ==========================================
    // 1. PUBLICACIONES - MERGE SORT
    // ==========================================

    public static JPanel publicaciones() {

        JTextArea salida = new JTextArea(15, 50);

        JPanel controles = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 5)
        );

        JComboBox<String> criterio = new JComboBox<>(
                new String[]{
                        "Fecha mas reciente",
                        "Mayor relevancia"
                }
        );

        JButton boton = crearBoton(
                "Ordenar publicaciones"
        );

        controles.add(new JLabel("Criterio:"));
        controles.add(criterio);
        controles.add(boton);

        Publicacion[] disponibles =
                DatosEjemplo.publicaciones();

        StringBuilder inicial = new StringBuilder();

        inicial.append("PUBLICACIONES DISPONIBLES\n");
        inicial.append("====================================\n\n");

        for (Publicacion publicacion : disponibles) {
            inicial.append(publicacion).append("\n\n");
        }

        inicial.append("------------------------------------\n");
        inicial.append("Cantidad de publicaciones: ")
                .append(disponibles.length);

        inicial.append("\n\nSelecciona un criterio y presiona");
        inicial.append(" el boton para ordenarlas.");

        salida.setText(inicial.toString());

        boton.addActionListener(e ->
                ejecutar(salida, () -> {

                    MergeSortPublicaciones.Criterio tipo =
                            criterio.getSelectedIndex() == 0
                                    ? MergeSortPublicaciones.Criterio.RECIENTE
                                    : MergeSortPublicaciones.Criterio.RELEVANTE;

                    Publicacion[] ordenadas =
                            MergeSortPublicaciones.ordenar(
                                    DatosEjemplo.publicaciones(),
                                    tipo
                            );

                    StringBuilder texto = new StringBuilder();

                    texto.append("PUBLICACIONES ORDENADAS\n");
                    texto.append("====================================\n\n");

                    if (criterio.getSelectedIndex() == 0) {
                        texto.append(
                                "Criterio: Fecha mas reciente\n\n"
                        );
                    } else {
                        texto.append(
                                "Criterio: Mayor relevancia\n\n"
                        );
                    }

                    int numero = 1;

                    for (Publicacion publicacion : ordenadas) {

                        texto.append("PUBLICACION ")
                                .append(numero++)
                                .append("\n");

                        texto.append(publicacion)
                                .append("\n\n");
                    }

                    texto.append("====================================\n");

                    texto.append("TOTAL DE PUBLICACIONES: ")
                            .append(ordenadas.length);

                    texto.append("\n\nAlgoritmo: Merge Sort");
                    texto.append("\nEstrategia: Divide y Conquista");

                    salida.setText(texto.toString());
                })
        );

        return crearPanel(
                "Publicaciones",
                "Organizacion del muro mediante Merge Sort",
                controles,
                salida
        );
    }

    // ==========================================
    // 2. CONTACTOS - DIJKSTRA
    // ==========================================

    public static JPanel contactos() {

        JTextArea salida = new JTextArea(15, 50);

        JPanel controles = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 5)
        );

        String[] usuarios = {
                "Ana", "Beto", "Caro", "Dani", "Eva"
        };

        JComboBox<String> origen =
                new JComboBox<>(usuarios);

        JComboBox<String> destino =
                new JComboBox<>(usuarios);

        destino.setSelectedItem("Eva");

        JButton boton = crearBoton(
                "Buscar camino mas rapido"
        );

        controles.add(new JLabel("Origen:"));
        controles.add(origen);

        controles.add(new JLabel("Destino:"));
        controles.add(destino);

        controles.add(boton);

        salida.setText(
                "RED DE CONTACTOS UNIVERSITARIA\n"
                        + "====================================\n\n"
                        + "Usuarios disponibles:\n\n"
                        + "- Ana\n"
                        + "- Beto\n"
                        + "- Caro\n"
                        + "- Dani\n"
                        + "- Eva\n\n"
                        + "Selecciona el origen y el destino.\n\n"
                        + "El programa calculara el camino de "
                        + "menor tiempo entre ambos usuarios.\n\n"
                        + "Algoritmo: Dijkstra\n"
                        + "Estrategia: Greedy"
        );

        boton.addActionListener(e ->
                ejecutar(salida, () -> {

                    String usuarioOrigen =
                            (String) origen.getSelectedItem();

                    String usuarioDestino =
                            (String) destino.getSelectedItem();

                    Dijkstra.Resultado resultado =
                            Dijkstra.buscar(
                                    DatosEjemplo.red(),
                                    usuarioOrigen,
                                    usuarioDestino
                            );

                    StringBuilder texto = new StringBuilder();

                    texto.append("RESULTADO DE DIFUSION\n");
                    texto.append("====================================\n\n");

                    texto.append("Origen: ")
                            .append(usuarioOrigen)
                            .append("\n");

                    texto.append("Destino: ")
                            .append(usuarioDestino)
                            .append("\n\n");

                    if (resultado.camino.isEmpty()) {

                        texto.append(
                                "No existe un camino entre "
                                        + "los usuarios seleccionados."
                        );

                    } else {

                        texto.append("CAMINO MAS RAPIDO\n\n");

                        texto.append(
                                String.join(
                                        " -> ",
                                        resultado.camino
                                )
                        );

                        texto.append("\n\nTIEMPO MINIMO: ")
                                .append(resultado.minutos)
                                .append(" minutos");

                        texto.append("\nUSUARIOS EN LA CADENA: ")
                                .append(resultado.camino.size());
                    }

                    texto.append("\n\n====================================");
                    texto.append("\nAlgoritmo: Dijkstra");
                    texto.append("\nEstrategia: Greedy");

                    salida.setText(texto.toString());
                })
        );

        return crearPanel(
                "Contactos",
                "Busqueda del camino mas rapido mediante Dijkstra",
                controles,
                salida
        );
    }

    // ==========================================
    // 3. INICIATIVAS - MOCHILA 0/1
    // ==========================================

    public static JPanel iniciativas() {

        JTextArea salida = new JTextArea(15, 50);

        JPanel controles = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 5)
        );

        JSpinner presupuesto = new JSpinner(
                new SpinnerNumberModel(10, 0, 10000, 1)
        );

        JButton boton = crearBoton(
                "Calcular seleccion optima"
        );

        controles.add(new JLabel("Presupuesto:"));
        controles.add(presupuesto);
        controles.add(boton);

        StringBuilder inicial = new StringBuilder();

        inicial.append("INICIATIVAS DISPONIBLES\n");
        inicial.append("====================================\n\n");

        for (Iniciativa iniciativa : DatosEjemplo.iniciativas()) {

            inicial.append(iniciativa)
                    .append("\n\n");
        }

        inicial.append("------------------------------------\n");
        inicial.append("Selecciona un presupuesto disponible.\n\n");

        inicial.append(
                "El programa elegira la combinacion "
                        + "que permita obtener el mayor impacto "
                        + "sin superar el presupuesto."
        );

        salida.setText(inicial.toString());

        boton.addActionListener(e ->
                ejecutar(salida, () -> {

                    int presupuestoDisponible =
                            (Integer) presupuesto.getValue();

                    Mochila.Resultado resultado =
                            Mochila.resolver(
                                    DatosEjemplo.iniciativas(),
                                    presupuestoDisponible
                            );

                    StringBuilder texto = new StringBuilder();

                    texto.append("SELECCION OPTIMA DE INICIATIVAS\n");
                    texto.append("====================================\n\n");

                    texto.append("Presupuesto disponible: ")
                            .append(presupuestoDisponible)
                            .append("\n\n");

                    texto.append("INICIATIVAS SELECCIONADAS\n\n");

                    if (resultado.seleccionadas.isEmpty()) {
                        texto.append(
                                "No se seleccionaron iniciativas.\n\n"
                        );
                    }

                    int numero = 1;

                    for (Iniciativa iniciativa : resultado.seleccionadas) {

                        texto.append("Iniciativa ")
                                .append(numero++)
                                .append(":\n");

                        texto.append(iniciativa)
                                .append("\n\n");
                    }

                    texto.append("====================================\n");

                    texto.append("CANTIDAD SELECCIONADA: ")
                            .append(resultado.seleccionadas.size());

                    texto.append("\nCOSTO TOTAL: ")
                            .append(resultado.costo);

                    texto.append("\nIMPACTO MAXIMO: ")
                            .append(resultado.impacto);

                    texto.append("\nPRESUPUESTO RESTANTE: ")
                            .append(resultado.restante);

                    texto.append("\n\nAlgoritmo: Mochila 0/1");
                    texto.append("\nEstrategia: Programacion Dinamica");

                    salida.setText(texto.toString());
                })
        );

        return crearPanel(
                "Iniciativas",
                "Seleccion optima mediante Mochila 0/1",
                controles,
                salida
        );
    }
}
