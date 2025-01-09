package bennett.citibike.json.map;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            SwingUtilities.invokeLater(() -> {
                new MapFrame().setVisible(true);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
