package ch.supsi.dti.i2b.shrug.optitravel.ui;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import javafx.fxml.FXML;

public class MainController implements MapComponentInitializedListener {

    @FXML
    private GoogleMapView mapView;

    @FXML
    private void initialize() {
        mapView.addMapInializedListener(this);
    }

    @Override
    public void mapInitialized() {

        MapOptions options = new MapOptions();
        options.mapType(MapTypeIdEnum.ROADMAP)
                .mapTypeControl(false)
                .center(new LatLong(-50.6071131, 165.9725615))
                .zoom(13)
                .streetViewControl(false)
                .mapTypeControl(false);
        mapView.setKey("AIzaSyAvtzzsAPAlOrK8JbGfXfHMt18MbqCqrj4");

        GoogleMap map = mapView.createMap(options);
    }
}
