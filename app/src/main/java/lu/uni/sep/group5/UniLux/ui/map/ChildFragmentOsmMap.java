package lu.uni.sep.group5.UniLux.ui.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import lu.uni.sep.group5.UniLux.R;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

public class ChildFragmentOsmMap extends Fragment {
    private static final String TAG = ChildFragmentOsmMap.class.getName();

    private View root;
    private MapView osmMap;
    private MapController osmMapController;
    private LocationManager locationManager;
    private MyLocationNewOverlay myLocationOverlay;
    private static final int PERMISSION_REQUEST = 0;
    private boolean initialized = false;
    private Marker marker;
    ItemizedIconOverlay<OverlayItem> touchedPointIconOverlay = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_osm_map, container, false);
        Configuration.getInstance().setUserAgentValue(getContext().getPackageName());

        osmMap = root.findViewById(R.id.osm_map);
        osmMap.setTileSource(TileSourceFactory.MAPNIK);
        osmMap.setMultiTouchControls(true);

        osmMap.setTilesScaledToDpi(true);

        osmMapController = (MapController) osmMap.getController();
        osmMapController.setZoom(4);

        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        initialized = true;

        displayMarker();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        double centerLatitude = osmMap.getBoundingBox().getCenterLatitude();
        double centerLongitude = osmMap.getBoundingBox().getCenterLongitude();
    }

    public void updateLoc() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION};
                requestPermissions(permission, PERMISSION_REQUEST);
            }
        }

        osmMap.getOverlays().remove(myLocationOverlay);

        myLocationOverlay = new MyLocationNewOverlay(osmMap);
        myLocationOverlay.enableFollowLocation();
        myLocationOverlay.enableMyLocation();

        osmMap.getOverlays().add(myLocationOverlay);
        Log.d("location", osmMap.getMapCenter().toString());
        osmMap.invalidate();
    }

    public void setLoc(double latitude, double longitude) {
        GeoPoint geoPoint = new GeoPoint(latitude, longitude);
        osmMapController.animateTo(geoPoint);
        updateMarker(latitude, longitude);
    }

    private void displayMarker() {
        marker = new Marker(osmMap);
        GeoPoint location = new GeoPoint(49.504218f, 5.949260f);
        marker.setPosition(location);
        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                setLoc(marker.getPosition().getLatitude(), marker.getPosition().getLongitude());
                setZoom(18.0f);
                return false;
            }
        });
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        osmMap.getOverlays().add(marker);
        osmMap.invalidate();
    }

    private void updateMarker(double latitude, double longitude) {
        osmMap.getOverlays().remove(marker);
        marker = new Marker(osmMap);
        GeoPoint location = new GeoPoint(latitude, longitude);
        marker.setPosition(location);
        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                setLoc(marker.getPosition().getLatitude(), marker.getPosition().getLongitude());
                setZoom(18.0f);
                return false;
            }
        });
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        osmMap.getOverlays().add(marker);
        osmMap.invalidate();
    }


    public void setZoom(float zoom) {
        osmMapController.setZoom(zoom);
    }

    public void setOnTouch() {
//        final MapEventsReceiver mReceive = new MapEventsReceiver() {
//            @Override
//            public boolean singleTapConfirmedHelper(GeoPoint p) {
//                Toast.makeText(getActivity().getBaseContext(), p.getLatitude() + " - " + p.getLongitude(), Toast.LENGTH_SHORT).show();
//                final Drawable marker = getActivity().getApplicationContext().getResources().getDrawable(R.drawable.add_point_location);
//                ArrayList<OverlayItem> overlayArray = new ArrayList<OverlayItem>();
//                OverlayItem mapItem = new OverlayItem("", "", p);
//                mapItem.setMarker(marker);
//                overlayArray.add(mapItem);
//                if (touchedPointIconOverlay == null) {
//                    touchedPointIconOverlay = new ItemizedIconOverlay<OverlayItem>(getActivity().getApplicationContext(), overlayArray, null);
//                    osmMap.getOverlays().add(touchedPointIconOverlay);
//                    osmMap.invalidate();
//                } else {
//                    osmMap.getOverlays().remove(touchedPointIconOverlay);
//                    osmMap.invalidate();
//                    touchedPointIconOverlay = new ItemizedIconOverlay<OverlayItem>(getActivity().getApplicationContext(), overlayArray, null);
//                    osmMap.getOverlays().add(touchedPointIconOverlay);
//                }
//                return false;
//            }
//
//            @Override
//            public boolean longPressHelper(GeoPoint p) {
//                Toast.makeText(getContext(), "long", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        };
//        osmMap.getOverlays().add(new MapEventsOverlay(mReceive));
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setLocationToLastDisplayed() {
        osmMap.getOverlays().remove(myLocationOverlay);
//            osmMap.getOverlays().add(myLocationOverlay);
//            osmMapController.setCenter(locGeoPoint);

        myLocationOverlay = new MyLocationNewOverlay(osmMap);
//        myLocationOverlay.enableFollowLocation();
        myLocationOverlay.enableMyLocation();

        osmMap.getOverlays().add(myLocationOverlay);
        Log.d(TAG, osmMap.getMapCenter().toString());
        osmMap.invalidate();
    }

    public void setLocToCurrent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
                requestPermissions(permission, PERMISSION_REQUEST);
            }
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                setLocToCurrentAfterPermissionCheck();
            }
        } else {
            setLocToCurrentAfterPermissionCheck();
        }
    }

    private void setLocToCurrentAfterPermissionCheck() {
        Location lastLocation = getLastKnownLocation();
        if (lastLocation != null) {
            GeoPoint locGeoPoint = new GeoPoint(lastLocation.getLatitude(), lastLocation.getLongitude());
            osmMap.getOverlays().remove(myLocationOverlay);
            osmMapController.setCenter(locGeoPoint);

            myLocationOverlay = new MyLocationNewOverlay(osmMap);
            myLocationOverlay.enableMyLocation();

            osmMap.getOverlays().add(myLocationOverlay);
            Log.d("location", osmMap.getMapCenter().toString());
            osmMap.invalidate();
        }
    }

    private Location getLastKnownLocation() {
        locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            @SuppressLint("MissingPermission") Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

}
