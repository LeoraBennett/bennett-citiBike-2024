package bennett.citibike.json.map;

import bennett.citibike.json.Station;
import bennett.citibike.json.lambda.CitiBikeResponse;
import bennett.citibike.json.lambda.Location;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MapFrame extends JFrame {
    private final JXMapViewer mapViewer = new JXMapViewer();
    private List<GeoPosition> selectedPoints;
    private MapController mapController;
    private MapComponent mapComponent;
    private JLabel startLabel;
    private JLabel endLabel;
    private JButton findRouteButton;
    private RoutePainter routePainter;

    private Set<Waypoint> waypoints;

    public MapFrame() {
        setTitle("CitiBike Map");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        selectedPoints = new ArrayList<>();

        MapComponent mapComponent = new MapComponent();
        mapController = new MapController(mapComponent);

        mapComponent.getMapViewer().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GeoPosition clickedPosition = mapComponent.getMapViewer().getTileFactory()
                        .pixelToGeo(e.getPoint(), mapComponent.getMapViewer().getZoom());

                if (selectedPoints.size() < 2) {
                    selectedPoints.add(clickedPosition);

                    if (selectedPoints.size() == 1) {
                        startLabel.setText("Start: "
                                + clickedPosition.getLatitude()
                                + ", "
                                + clickedPosition.getLongitude());
                    } else {
                        endLabel.setText("End: "
                                + clickedPosition.getLatitude()
                                + ", "
                                + "" + clickedPosition.getLongitude());
                    }
                }

                if (selectedPoints.size() == 2) {
                    findRouteButton.setEnabled(true);
                }
            }
        });

        startLabel = new JLabel("Start: ");
        endLabel = new JLabel("End: ");

        findRouteButton = new JButton("Find Route");
        findRouteButton.setEnabled(false);
        findRouteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CitiBikeResponse response = getCitiBikeResponse();

                drawRoute(response);
            }
        });

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(startLabel);
        infoPanel.add(endLabel);
        infoPanel.add(findRouteButton);

        setLayout(new BorderLayout());
        add(mapComponent, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);

    }

    private void drawRoute(CitiBikeResponse response) {
        List<GeoPosition> track = new ArrayList<>();
        Station startStation = response.start;
        Station endStation = response.end;
        track.add(new GeoPosition(response.from.lat, response.from.lon));
        track.add(new GeoPosition(startStation.lat, startStation.lon));
        track.add(new GeoPosition(endStation.lat, endStation.lon));
        track.add(new GeoPosition(response.to.lat, response.to.lon));

        this.routePainter = new RoutePainter(track);

        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();
        waypoints = Set.of(
                new DefaultWaypoint(startStation.lat, startStation.lon),
                new DefaultWaypoint(endStation.lat, endStation.lon)
        );
        waypointPainter.setWaypoints(waypoints);

        CompoundPainter<JXMapViewer> compoundPainter = new CompoundPainter<>(List.of(routePainter, waypointPainter));

        mapViewer.setOverlayPainter(compoundPainter);
        mapViewer.repaint();
    }


    private CitiBikeResponse getCitiBikeResponse() {
        String startText = startLabel.getText();
        String endText = endLabel.getText();

        String[] startCoordinates = startText.replaceAll("[^0-9.,-]", "").split(",");
        String[] endCoordinates = endText.replaceAll("[^0-9.,-]", "").split(",");

        double startLat = Double.parseDouble(startCoordinates[0].trim());
        double startLon = Double.parseDouble(startCoordinates[1].trim());
        double endLat = Double.parseDouble(endCoordinates[0].trim());
        double endLon = Double.parseDouble(endCoordinates[1].trim());

        Location startLocation = new Location(startLat, startLon);
        Location endLocation = new Location(endLat, endLon);

        String startStationId = "start123";
        String startStationName = "Start Station";
        Station startStation = new Station(startStationId, startStationName, startLat, startLon);

        String endStationId = "end123";
        String endStationName = "End Station";
        Station endStation = new Station(endStationId, endStationName, endLat, endLon);

        CitiBikeResponse response = new CitiBikeResponse(startLocation, endLocation, startStation, endStation);

        GeoPosition startgp = new GeoPosition(startStation.lat, startStation.lon);
        GeoPosition endgp = new GeoPosition(endStation.lat, endStation.lon);

        return response;
    }
}
