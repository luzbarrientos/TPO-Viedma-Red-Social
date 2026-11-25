
package redsocial.interfaz;

import redsocial.algoritmos.AsignacionTutores;
import redsocial.datos.DatosTutores;

import javax.swing.*;
import java.awt.GridLayout;

public class PanelAsignacionTutores {

    public static JPanel tutores() {

        String[] tutores = DatosTutores.tutores();
        String[] grupos = DatosTutores.grupos();
        int[][] costos = DatosTutores.costos();

        JTextField[][] campos =
                new JTextField[tutores.length][grupos.length];

        JPanel matriz = new JPanel(new GridLayout(
                tutores.length + 1, grupos.length + 1, 10, 8));

        matriz.add(new JLabel("Tutor / Grupo"));

        for (String grupo : grupos) {
            matriz.add(new JLabel(grupo));
        }

        // Crear la matriz editable.
        for (int i = 0; i < tutores.length; i++) {

            matriz.add(new JLabel(tutores[i]));

            for (int j = 0; j < grupos.length; j++) {

                campos[i][j] = new JTextField(
                        String.valueOf(costos[i][j]), 5);

                matriz.add(campos[i][j]);
            }
        }

        JButton boton = PanelFuncionalidades.crearBoton(
                "Asignar tutores");

        JTextArea salida = new JTextArea();

        JPanel controles = new JPanel();

        controles.setLayout(
                new BoxLayout(controles, BoxLayout.Y_AXIS));

        controles.add(matriz);
        controles.add(Box.createVerticalStrut(10));
        controles.add(new JLabel(
                "Ingrese -1 para prohibir una asignacion."));
        controles.add(Box.createVerticalStrut(10));
        controles.add(boton);

        boton.addActionListener(e ->
                PanelFuncionalidades.ejecutar(salida, () -> {

                    int[][] nuevosCostos =
                            new int[tutores.length][grupos.length];

                    // Leer los costos ingresados.
                    for (int i = 0; i < tutores.length; i++) {
                        for (int j = 0; j < grupos.length; j++) {

                            nuevosCostos[i][j] = Integer.parseInt(
                                    campos[i][j].getText().trim());
                        }
                    }

                    AsignacionTutores algoritmo =
                            new AsignacionTutores();

                    int[] asignacion =
                            algoritmo.resolver(nuevosCostos);

                    StringBuilder resultado = new StringBuilder();

                    resultado.append("ASIGNACION OPTIMA\n\n");

                    if (asignacion == null) {

                        resultado.append(
                                "No existe una asignacion valida.");

                    } else {

                        for (int grupo = 0;
                             grupo < grupos.length; grupo++) {

                            int tutor = asignacion[grupo];

                            resultado.append(grupos[grupo])
                                    .append(" -> ")
                                    .append(tutores[tutor])
                                    .append(" | Costo: ")
                                    .append(nuevosCostos[tutor][grupo])
                                    .append("\n");
                        }

                        resultado.append("\nCosto minimo total: ")
                                .append(algoritmo.getMejorCosto());
                    }

                    salida.setText(resultado.toString());
                }));

        return PanelFuncionalidades.crearPanel(
                "Asignacion de tutores a grupos",
                "Encontrar la asignacion de menor costo mediante Backtracking.",
                controles,
                salida
        );
    }
}
