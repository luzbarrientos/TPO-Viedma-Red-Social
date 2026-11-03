package redsocial.interfaz;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
public class VentanaPrincipal extends JFrame {
    private final JTabbedPane pestanias;
    public VentanaPrincipal() {
        setTitle("Red Social Universitaria - Programacion III");
        setSize(1000, 700);
        setMinimumSize(new Dimension(800, 550));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBackground(new Color(245, 247, 250));
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(new Color(34, 58, 94));
        encabezado.setBorder(new EmptyBorder(22, 25, 22, 25));
        JLabel titulo = new JLabel("Red Social Universitaria");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 25));
        titulo.setForeground(Color.WHITE);
        JLabel subtitulo = new JLabel(
                "Plataforma de colaboracion entre estudiantes"
        );
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitulo.setForeground(new Color(210, 225, 245));
        JPanel textos = new JPanel(new GridLayout(2, 1, 0, 5));
        textos.setOpaque(false);
        textos.add(titulo);
        textos.add(subtitulo);
        encabezado.add(textos, BorderLayout.WEST);
        pestanias = new JTabbedPane();
        pestanias.setFont(new Font("SansSerif", Font.PLAIN, 13));
        // Paneles desarrollados por German.
        pestanias.addTab(
                "Publicaciones",
                PanelFuncionalidades.publicaciones()
        );
        pestanias.addTab(
                "Contactos",
                PanelFuncionalidades.contactos()
        );
        pestanias.addTab(
                "Iniciativas",
                PanelFuncionalidades.iniciativas()
        );
        // Paneles desarrollados por Luz.
        pestanias.addTab(
                "Actividades",
                PanelFuncionalidadesExtra.actividades()
        );
        pestanias.addTab(
                "Equipos",
                PanelFuncionalidadesExtra.equipos()
        );
        pestanias.addTab(
                "Difusion",
                PanelFuncionalidadesExtra.difusion()
        );
        JLabel pie = new JLabel(
                "TPO Programacion III - Red Social Universitaria",
                SwingConstants.CENTER
        );
        pie.setFont(new Font("SansSerif", Font.PLAIN, 12));
        pie.setForeground(Color.GRAY);
        pie.setBorder(new EmptyBorder(12, 10, 12, 10));
        contenedor.add(encabezado, BorderLayout.NORTH);
        contenedor.add(pestanias, BorderLayout.CENTER);
        contenedor.add(pie, BorderLayout.SOUTH);
        setContentPane(contenedor);
    }
}