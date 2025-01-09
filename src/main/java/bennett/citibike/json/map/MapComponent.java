package bennett.citibike.json.map;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;

import javax.swing.*;
import java.awt.*;

public class MapComponent extends JPanel {
    private final JXMapViewer mapViewer;

    public MapComponent() {
        setLayout(new BorderLayout());
        mapViewer = new JXMapViewer();

        OSMTileFactoryInfo tileFactoryInfo = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(tileFactoryInfo);
        mapViewer.setTileFactory(tileFactory);

        GeoPosition initialPosition = new GeoPosition(40.7128, -74.0060);
        mapViewer.setZoom(7);
        mapViewer.setAddressLocation(initialPosition);

        setupMapInteractions();
        add(mapViewer, BorderLayout.CENTER);
    }

    private void setupMapInteractions() {
        mapViewer.addMouseListener(new CenterMapListener(mapViewer));
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));
    }

    public JXMapViewer getMapViewer() {
        return mapViewer;
    }
}
