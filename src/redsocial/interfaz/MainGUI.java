package redsocial.interfaz;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
public class MainGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName()
                );
            } catch (Exception e) {
                System.out.println(
                        "Se utilizara el estilo predeterminado."
                );
            }
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}
