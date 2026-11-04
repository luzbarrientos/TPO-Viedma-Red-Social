
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

public class PanelFuncionalidadesExtra {

    private PanelFuncionalidadesExtra() {
    }

    // 4. SELECCION DE ACTIVIDADES
    public static JPanel actividades() {

        JTextArea salida = new JTextArea(15, 50);
        JPanel controles = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton boton = new JButton("Seleccionar actividades");
        controles.add(boton);

        boton.addActionListener(e ->
                PanelFuncionalidades.ejecutar(salida, () -> {

                    SeleccionActividades.Resultado resultado =
                            SeleccionActividades.resolver(
                                    DatosActividades.crearActividades()
                            );

                    StringBuilder texto =
                            new StringBuilder("ACTIVIDADES COMPATIBLES\n\n");

                    for (Actividad actividad : resultado.seleccionadas) {
                        texto.append(actividad).append("\n");
                    }

                    texto.append("\nInteres total: ")
                            .append(resultado.interesTotal);

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

    // 5. FORMACION DE EQUIPOS
    public static JPanel equipos() {

        JTextArea salida = new JTextArea(15, 50);
        JPanel controles = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton boton = new JButton("Formar equipos");
        controles.add(boton);

        boton.addActionListener(e ->
                PanelFuncionalidades.ejecutar(salida, () -> {

                    List<List<Estudiante>> equipos =
                            FormacionEquipos.resolver(
                                    DatosEquipos.crearEstudiantes(),
                                    DatosEquipos.crearTamanioEquipo(),
                                    DatosEquipos.crearHabilidadesRequeridas(),
                                    DatosEquipos.crearIntegrantesObligatorios(),
                                    DatosEquipos.crearIncompatibilidades()
                            );

                    StringBuilder texto =
                            new StringBuilder("EQUIPOS VALIDOS\n\n");

                    int numero = 1;

                    for (List<Estudiante> equipo : equipos) {

                        texto.append("Equipo ")
                                .append(numero++)
                                .append(": ");

                        for (int i = 0; i < equipo.size(); i++) {
                            if (i > 0) {
                                texto.append(", ");
                            }

                            texto.append(equipo.get(i).getNombre());
                        }

                        texto.append("\n");
                    }

                    texto.append("\nTotal de equipos: ")
                            .append(equipos.size());

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

    // 6. CADENAS ALTERNATIVAS DE DIFUSION
    public static JPanel difusion() {

        JTextArea salida = new JTextArea(15, 50);
        JPanel controles = new JPanel(new FlowLayout(FlowLayout.LEFT));

        String[] usuarios = {"Ana", "Beto", "Caro", "Dani", "Eva"};

        JComboBox<String> origen = new JComboBox<>(usuarios);
        JComboBox<String> destino = new JComboBox<>(usuarios);

        destino.setSelectedItem("Eva");

        JSpinner tiempo = new JSpinner(
                new SpinnerNumberModel(16, 0, 10000, 1)
        );

        JSpinner intermediarios = new JSpinner(
                new SpinnerNumberModel(3, 0, 100, 1)
        );

        JTextField excluidos = new JTextField(10);
        JButton boton = new JButton("Buscar cadenas");

        controles.add(new JLabel("Origen:"));
        controles.add(origen);
        controles.add(new JLabel("Destino:"));
        controles.add(destino);
        controles.add(new JLabel("Tiempo maximo:"));
        controles.add(tiempo);
        controles.add(new JLabel("Intermediarios:"));
        controles.add(intermediarios);
        controles.add(new JLabel("Excluir:"));
        controles.add(excluidos);
        controles.add(boton);

        boton.addActionListener(e ->
                PanelFuncionalidades.ejecutar(salida, () -> {

                    Set<String> usuariosExcluidos = new HashSet<>();

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

                    StringBuilder texto =
                            new StringBuilder("CADENAS VALIDAS\n\n");

                    int numero = 1;

                    for (CadenasDifusion.Cadena cadena : resultado.cadenas) {

                        texto.append("Cadena ")
                                .append(numero++)
                                .append(": ")
                                .append(String.join(" -> ", cadena.usuarios))
                                .append(" | ")
                                .append(cadena.tiempoTotal)
                                .append(" minutos\n");
                    }

                    texto.append("\nTotal de cadenas: ")
                            .append(resultado.cadenas.size());

                    if (resultado.cadenaMasRapida != null) {

                        texto.append("\n\nCADENA MAS RAPIDA\n")
                                .append(String.join(
                                        " -> ",
                                        resultado.cadenaMasRapida.usuarios
                                ))
                                .append("\nTiempo: ")
                                .append(resultado.cadenaMasRapida.tiempoTotal)
                                .append(" minutos");

                    } else {
                        texto.append("\nNo hay cadenas validas.");
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
