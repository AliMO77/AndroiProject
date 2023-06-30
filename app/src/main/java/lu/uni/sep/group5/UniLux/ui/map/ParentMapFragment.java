package lu.uni.sep.group5.UniLux.ui.map;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import lu.uni.sep.group5.UniLux.R;



public class ParentMapFragment extends Fragment {
    private static final String TAG = ParentMapFragment.class.getName();
    private View root;

    private ImageButton menuBtn, locationBtn;
    private Button buildingButton;
    private DrawerLayout drawer;
    private Fragment childFragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_map, container, false);

        this.childFragment = new ChildFragmentOsmMap();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.child_fragment_container, childFragment).commit();

        Handler locationUpdateHandler = new Handler();
        final Runnable locationUpdateRunnable = new Runnable() {
            @Override
            public void run() {
                while (!((ChildFragmentOsmMap) childFragment).isInitialized()) {
                }
                ((ChildFragmentOsmMap) childFragment).setLocationToLastDisplayed();
            }
        };
        locationUpdateHandler.postDelayed(locationUpdateRunnable, 100);

        initComponents();
        initSetOnClickListener();

//        ((ChildFragmentOsmMap) childFragment).setLoc(Double.parseDouble("49.504218"),
//                Double.parseDouble("5.949260"));
//        ((ChildFragmentOsmMap) childFragment).setZoom(15.0f);

        return root;
    }

    private void initComponents() {
        drawer = getActivity().findViewById(R.id.drawer_layout);
        menuBtn = root.findViewById(R.id.map_to_menu_button);
        locationBtn = root.findViewById(R.id.map_location_button);
        buildingButton = root.findViewById(R.id.map_buildings_button);
    }

    private void initSetOnClickListener() {
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ChildFragmentOsmMap) childFragment).setLocToCurrent();
                ((ChildFragmentOsmMap) childFragment).setZoom(17.5f);
            }
        });

        buildingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(root, childFragment);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                dialog.show(fragmentManager, "Dialog");
            }
        });
    }
}