package bennett.citibike.json.map;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MapController {
    private final MapComponent mapComponent;
    private final List<Painter<JXMapViewer>> painters;

    public MapController(MapComponent mapComponent) {
        this.mapComponent = mapComponent;
        this.painters = new ArrayList<>();
    }

    public void addWaypoints(Set<Waypoint> waypoints) {
        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();
        waypointPainter.setWaypoints(waypoints);
        painters.add(waypointPainter);
        updateOverlay();
    }

    public void addRoutePainter(RoutePainter routePainter) {
        painters.add(routePainter);
        updateOverlay();
    }

    private void updateOverlay() {
        CompoundPainter<JXMapViewer> compoundPainter = new CompoundPainter<>(painters);
        mapComponent.getMapViewer().setOverlayPainter(compoundPainter);
    }

    public void zoomToBestFit(Set<GeoPosition> positions, double margin) {
        mapComponent.getMapViewer().zoomToBestFit(positions, margin);
    }
}
