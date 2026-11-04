
package redsocial.interfaz;

import redsocial.algoritmos.CadenasDifusion;
import redsocial.algoritmos.FormacionEquipos;
import redsocial.algoritmos.SeleccionActividades;

import redsocial.datos.DatosActividades;
import redsocial.datos.DatosDifusion;
import redsocial.datos.DatosEquipos;

import redsocial.modelo.Actividad;
import redsocial.modelo.Estudiante;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PanelFuncionalidadesExtra {

    private PanelFuncionalidadesExtra() {
    }

    // ==========================================
    // 4. ACTIVIDADES - PROGRAMACION DINAMICA
    // ==========================================

    public static JPanel actividades() {

        JTextArea salida = new JTextArea(15, 50);

        JPanel controles = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 5)
        );

        JButton boton = PanelFuncionalidades.crearBoton(
                "Calcular seleccion optima"
        );

        controles.add(boton);

        List<Actividad> disponibles =
                DatosActividades.crearActividades();

        StringBuilder informacion = new StringBuilder();

        informacion.append("ACTIVIDADES DISPONIBLES\n");
        informacion.append("====================================\n\n");

        for (Actividad actividad : disponibles) {

            informacion.append(actividad.getNombre())
                    .append("\n")
                    .append("Horario: ")
                    .append(formatearHora(actividad.getInicio()))
                    .append(" a ")
                    .append(formatearHora(actividad.getFin()))
                    .append("\n")
                    .append("Interes: ")
                    .append(actividad.getInteres())
                    .append(" puntos\n\n");
        }

        informacion.append("------------------------------------\n");
        informacion.append("Algoritmo: Programacion Dinamica\n");
        informacion.append("Objetivo: maximizar el interes total");
        informacion.append(" sin superponer actividades.");

        salida.setText(informacion.toString());

        boton.addActionListener(e ->
                PanelFuncionalidades.ejecutar(salida, () -> {

                    SeleccionActividades.Resultado resultado =
                            SeleccionActividades.resolver(
                                    DatosActividades.crearActividades()
                            );

                    StringBuilder texto = new StringBuilder();

                    texto.append("SELECCION OPTIMA DE ACTIVIDADES\n");
                    texto.append("====================================\n\n");

                    if (resultado.seleccionadas.isEmpty()) {
                        texto.append(
                                "No se seleccionaron actividades.\n\n"
                        );
                    }

                    for (Actividad actividad : resultado.seleccionadas) {

                        texto.append(actividad.getNombre())
                                .append("\n")
                                .append("Horario: ")
                                .append(formatearHora(actividad.getInicio()))
                                .append(" a ")
                                .append(formatearHora(actividad.getFin()))
                                .append("\n")
                                .append("Interes: ")
                                .append(actividad.getInteres())
                                .append(" puntos\n\n");
                    }

                    texto.append("====================================\n");

                    texto.append("ACTIVIDADES SELECCIONADAS: ")
                            .append(resultado.seleccionadas.size());

                    texto.append("\nINTERES MAXIMO: ")
                            .append(resultado.interesTotal)
                            .append(" puntos");

                    texto.append("\n\nEstrategia: Programacion Dinamica");

                    salida.setText(texto.toString());
                })
        );

        return PanelFuncionalidades.crearPanel(
                "Actividades",
                "Seleccion mediante Programacion Dinamica",
                controles,
                salida
        );
    }

    private static String formatearHora(int minutos) {

        int horas = minutos / 60;
        int minutosRestantes = minutos % 60;

        return String.format(
                "%02d:%02d",
                horas,
                minutosRestantes
        );
    }

    // ==========================================
    // 5. FORMACION DE EQUIPOS - BACKTRACKING
    // ==========================================

    public static JPanel equipos() {

        JTextArea salida = new JTextArea(15, 50);

        List<Estudiante> estudiantes =
                DatosEquipos.crearEstudiantes();

        JSpinner tamanioEquipo = new JSpinner(
                new SpinnerNumberModel(
                        DatosEquipos.crearTamanioEquipo(),
                        1,
                        estudiantes.size(),
                        1
                )
        );

        JTextField habilidades = new JTextField(
                DatosEquipos.crearHabilidadesRequeridas()
                        .stream()
                        .sorted()
                        .collect(Collectors.joining(",")),
                15
        );

        JTextField obligatorios = new JTextField(
                DatosEquipos.crearIntegrantesObligatorios()
                        .stream()
                        .sorted()
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")),
                10
        );

        JTextField incompatibles = new JTextField(
                DatosEquipos.crearIncompatibilidades()
                        .stream()
                        .sorted()
                        .collect(Collectors.joining(",")),
                15
        );

        JButton boton = PanelFuncionalidades.crearBoton(
                "Formar equipos"
        );

        JPanel controles = new JPanel(
                new GridLayout(2, 1, 0, 8)
        );

        JPanel primeraFila = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 5)
        );

        JPanel segundaFila = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 5)
        );

        primeraFila.setOpaque(false);
        segundaFila.setOpaque(false);

        primeraFila.add(new JLabel("Tamaño:"));
        primeraFila.add(tamanioEquipo);

        primeraFila.add(new JLabel("Habilidades:"));
        primeraFila.add(habilidades);

        segundaFila.add(new JLabel("IDs obligatorios:"));
        segundaFila.add(obligatorios);

        segundaFila.add(new JLabel("Incompatibles:"));
        segundaFila.add(incompatibles);

        segundaFila.add(boton);

        controles.add(primeraFila);
        controles.add(segundaFila);

        StringBuilder inicial = new StringBuilder();

        inicial.append("ESTUDIANTES DISPONIBLES\n");
        inicial.append("====================================\n\n");

        for (Estudiante estudiante : estudiantes) {

            inicial.append("ID: ")
                    .append(estudiante.getId())
                    .append(" | ")
                    .append(estudiante.getNombre())
                    .append("\n");

            inicial.append("Habilidades: ")
                    .append(estudiante.getHabilidades())
                    .append("\n\n");
        }

        inicial.append("====================================\n");
        inicial.append("RESTRICCIONES INICIALES\n\n");

        inicial.append("Tamaño del equipo: ")
                .append(tamanioEquipo.getValue())
                .append("\n");

        inicial.append("Habilidades requeridas: ")
                .append(habilidades.getText())
                .append("\n");

        inicial.append("IDs obligatorios: ")
                .append(obligatorios.getText())
                .append("\n");

        inicial.append("Parejas incompatibles: ")
                .append(incompatibles.getText())
                .append("\n\n");

        inicial.append("Podes modificar las restricciones");
        inicial.append(" y presionar Formar equipos.\n\n");

        inicial.append("Formato de habilidades: Java,Testing\n");
        inicial.append("Formato de IDs: 1,2\n");
        inicial.append("Formato de incompatibles: 2-4,3-5");

        salida.setText(inicial.toString());

        boton.addActionListener(e ->
                PanelFuncionalidades.ejecutar(salida, () -> {

                    int tamanio =
                            (Integer) tamanioEquipo.getValue();

                    Set<String> habilidadesRequeridas =
                            new HashSet<>();

                    for (String dato : habilidades.getText().split(",")) {

                        String valor = dato.trim();

                        if (!valor.isEmpty()) {
                            habilidadesRequeridas.add(valor);
                        }
                    }

                    Set<Integer> idsObligatorios =
                            new HashSet<>();

                    String textoIds =
                            obligatorios.getText().trim();

                    if (!textoIds.isEmpty()) {

                        for (String dato : textoIds.split(",")) {

                            String valor = dato.trim();

                            if (!valor.matches("\\d+")) {
                                throw new IllegalArgumentException(
                                        "Los IDs obligatorios deben ser numeros."
                                );
                            }

                            idsObligatorios.add(
                                    Integer.parseInt(valor)
                            );
                        }
                    }

                    Set<String> paresIncompatibles =
                            new HashSet<>();

                    String textoPares =
                            incompatibles.getText().trim();

                    if (!textoPares.isEmpty()) {

                        for (String dato : textoPares.split(",")) {

                            String par = dato.trim();

                            if (!par.matches("\\d+-\\d+")) {
                                throw new IllegalArgumentException(
                                        "Las incompatibilidades deben tener "
                                                + "el formato 2-4,3-5."
                                );
                            }

                            String[] partes = par.split("-");

                            int primero =
                                    Integer.parseInt(partes[0]);

                            int segundo =
                                    Integer.parseInt(partes[1]);

                            if (primero == segundo) {
                                throw new IllegalArgumentException(
                                        "Un estudiante no puede ser "
                                                + "incompatible consigo mismo."
                                );
                            }

                            int menor = Math.min(primero, segundo);
                            int mayor = Math.max(primero, segundo);

                            paresIncompatibles.add(
                                    menor + "-" + mayor
                            );
                        }
                    }

                    Set<Integer> idsDisponibles = new HashSet<>();

                    for (Estudiante estudiante : estudiantes) {
                        idsDisponibles.add(estudiante.getId());
                    }

                    if (!idsDisponibles.containsAll(idsObligatorios)) {
                        throw new IllegalArgumentException(
                                "Hay IDs obligatorios que no existen."
                        );
                    }

                    if (idsObligatorios.size() > tamanio) {
                        throw new IllegalArgumentException(
                                "La cantidad de integrantes obligatorios "
                                        + "supera el tamaño del equipo."
                        );
                    }

                    for (String par : paresIncompatibles) {

                        String[] partes = par.split("-");

                        int primero = Integer.parseInt(partes[0]);
                        int segundo = Integer.parseInt(partes[1]);

                        if (!idsDisponibles.contains(primero)
                                || !idsDisponibles.contains(segundo)) {

                            throw new IllegalArgumentException(
                                    "Una incompatibilidad contiene "
                                            + "un ID inexistente."
                            );
                        }
                    }

                    List<List<Estudiante>> equipos =
                            FormacionEquipos.resolver(
                                    estudiantes,
                                    tamanio,
                                    habilidadesRequeridas,
                                    idsObligatorios,
                                    paresIncompatibles
                            );

                    StringBuilder texto = new StringBuilder();

                    texto.append("RESULTADO DE FORMACION DE EQUIPOS\n");
                    texto.append("====================================\n\n");

                    texto.append("RESTRICCIONES UTILIZADAS\n\n");

                    texto.append("Tamaño del equipo: ")
                            .append(tamanio)
                            .append("\n");

                    texto.append("Habilidades requeridas: ")
                            .append(habilidadesRequeridas)
                            .append("\n");

                    texto.append("Integrantes obligatorios: ")
                            .append(idsObligatorios)
                            .append("\n");

                    texto.append("Incompatibilidades: ")
                            .append(paresIncompatibles)
                            .append("\n\n");

                    texto.append("====================================\n");
                    texto.append("EQUIPOS VALIDOS\n\n");

                    int numero = 1;

                    for (List<Estudiante> equipo : equipos) {

                        texto.append("Equipo ")
                                .append(numero++)
                                .append(":\n");

                        for (Estudiante estudiante : equipo) {

                            texto.append("- ")
                                    .append(estudiante.getNombre())
                                    .append("\n");
                        }

                        texto.append("\n");
                    }

                    texto.append("====================================\n");

                    texto.append("TOTAL DE EQUIPOS VALIDOS: ")
                            .append(equipos.size());

                    if (equipos.isEmpty()) {
                        texto.append(
                                "\nNo existen equipos que cumplan "
                                        + "las restricciones seleccionadas."
                        );
                    }

                    texto.append("\n\nAlgoritmo: Backtracking");

                    salida.setText(texto.toString());
                })
        );

        return PanelFuncionalidades.crearPanel(
                "Equipos",
                "Formacion de equipos mediante Backtracking",
                controles,
                salida
        );
    }

    // ==========================================
    // 6. CADENAS ALTERNATIVAS - BACKTRACKING
    // ==========================================

    public static JPanel difusion() {

        JTextArea salida = new JTextArea(15, 50);

        // Distribuimos los controles en dos filas.
        JPanel controles = new JPanel(
                new GridLayout(2, 1, 0, 8)
        );

        JPanel primeraFila = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 5)
        );

        JPanel segundaFila = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 5)
        );

        primeraFila.setOpaque(false);
        segundaFila.setOpaque(false);

        String[] usuarios = {
                "Ana", "Beto", "Caro", "Dani", "Eva"
        };

        JComboBox<String> origen =
                new JComboBox<>(usuarios);

        JComboBox<String> destino =
                new JComboBox<>(usuarios);

        destino.setSelectedItem("Eva");

        JSpinner tiempo = new JSpinner(
                new SpinnerNumberModel(16, 0, 10000, 1)
        );

        JSpinner intermediarios = new JSpinner(
                new SpinnerNumberModel(3, 0, 100, 1)
        );

        JTextField excluidos = new JTextField(10);

        JButton boton = PanelFuncionalidades.crearBoton(
                "Buscar cadenas"
        );

        // Primera fila: origen y destino.
        primeraFila.add(new JLabel("Origen:"));
        primeraFila.add(origen);

        primeraFila.add(new JLabel("Destino:"));
        primeraFila.add(destino);

        // Segunda fila: restricciones.
        segundaFila.add(new JLabel("Tiempo maximo:"));
        segundaFila.add(tiempo);

        segundaFila.add(new JLabel("Intermediarios:"));
        segundaFila.add(intermediarios);

        segundaFila.add(new JLabel("Excluir:"));
        segundaFila.add(excluidos);

        segundaFila.add(boton);

        controles.add(primeraFila);
        controles.add(segundaFila);

        salida.setText(
                "CADENAS ALTERNATIVAS DE DIFUSION\n"
                        + "====================================\n\n"
                        + "Selecciona un origen y un destino.\n\n"
                        + "Restricciones disponibles:\n"
                        + "- Tiempo maximo de difusion.\n"
                        + "- Cantidad maxima de intermediarios.\n"
                        + "- Usuarios excluidos.\n\n"
                        + "Para excluir usuarios, escribi sus nombres "
                        + "separados por comas.\n"
                        + "Ejemplo: Beto,Caro\n\n"
                        + "El algoritmo encontrara todas las cadenas "
                        + "validas sin repetir usuarios.\n\n"
                        + "Algoritmo: Backtracking"
        );

        boton.addActionListener(e ->
                PanelFuncionalidades.ejecutar(salida, () -> {

                    Set<String> usuariosExcluidos =
                            new HashSet<>();

                    String entrada = excluidos.getText().trim();

                    if (!entrada.isEmpty()) {

                        for (String usuario : entrada.split(",")) {

                            String nombre = usuario.trim();

                            if (!nombre.isEmpty()) {
                                usuariosExcluidos.add(nombre);
                            }
                        }
                    }

                    CadenasDifusion.Resultado resultado =
                            CadenasDifusion.resolver(
                                    DatosDifusion.crearRed(),
                                    (String) origen.getSelectedItem(),
                                    (String) destino.getSelectedItem(),
                                    (Integer) tiempo.getValue(),
                                    (Integer) intermediarios.getValue(),
                                    usuariosExcluidos
                            );

                    StringBuilder texto = new StringBuilder();

                    texto.append("CADENAS VALIDAS\n");
                    texto.append("====================================\n\n");

                    texto.append("Origen: ")
                            .append(origen.getSelectedItem())
                            .append("\n");

                    texto.append("Destino: ")
                            .append(destino.getSelectedItem())
                            .append("\n");

                    texto.append("Tiempo maximo: ")
                            .append(tiempo.getValue())
                            .append(" minutos\n");

                    texto.append("Maximo de intermediarios: ")
                            .append(intermediarios.getValue())
                            .append("\n");

                    texto.append("Usuarios excluidos: ")
                            .append(usuariosExcluidos)
                            .append("\n\n");

                    int numero = 1;

                    for (CadenasDifusion.Cadena cadena : resultado.cadenas) {

                        texto.append("Cadena ")
                                .append(numero++)
                                .append(":\n");

                        texto.append(
                                String.join(" -> ", cadena.usuarios)
                        );

                        texto.append("\nTiempo total: ")
                                .append(cadena.tiempoTotal)
                                .append(" minutos\n\n");
                    }

                    texto.append("====================================\n");

                    texto.append("TOTAL DE CADENAS: ")
                            .append(resultado.cadenas.size());

                    if (resultado.cadenaMasRapida != null) {

                        texto.append("\n\nCADENA MAS RAPIDA\n");

                        texto.append(
                                String.join(
                                        " -> ",
                                        resultado.cadenaMasRapida.usuarios
                                )
                        );

                        texto.append("\nTiempo minimo: ")
                                .append(resultado.cadenaMasRapida.tiempoTotal)
                                .append(" minutos");

                    } else {
                        texto.append(
                                "\n\nNo se encontraron cadenas validas."
                        );
                    }

                    salida.setText(texto.toString());
                })
        );

        return PanelFuncionalidades.crearPanel(
                "Difusion",
                "Cadenas alternativas mediante Backtracking",
                controles,
                salida
        );
    }
}
