import controlador.Controlador;
import modelo.*;
import vista.VistaPrincipal;

import javax.swing.*;

// Main de ejemplo para probar la instanciación de sensores y polimorfismo
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SmartTecnoHouse modelo = SmartTecnoHouse.getInstance();
            VistaPrincipal vista  = new VistaPrincipal();
            new Controlador(modelo, vista).iniciar();
        });
    }
}
