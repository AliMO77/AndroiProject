package lu.uni.sep.group5.UniLux.ui.map;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lu.uni.sep.group5.UniLux.R;
import lu.uni.sep.group5.UniLux.adapters.BuildingsAdapter;
import lu.uni.sep.group5.UniLux.models.Building;

public class Dialog extends DialogFragment {

    private View root;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Building> buildings;

    private View rootView;
    private Fragment OSMFragment;
    private Map<String, List<String>> coordinates;

    Dialog(View view, Fragment fragment){
        rootView = view;
        OSMFragment = fragment;


        createBuildings();
        createCoordinates();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.dialog_buildings, container, false);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setCancelable(false);
        initComponents();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.getDialog().setCanceledOnTouchOutside(true);
    }

    public void initComponents(){
        recyclerView = root.findViewById(R.id.buildings_list_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        BuildingsAdapter adapter = new BuildingsAdapter(getActivity(), buildings, getDialog(),
                rootView, OSMFragment, coordinates);
        recyclerView.setAdapter(adapter);
    }

    private void createBuildings() {
        buildings = new ArrayList<>();

        buildings.add(new Building("Maison du Savoir"));
        buildings.add(new Building("Maison du Nombre"));
        buildings.add(new Building("Maison des Arts et des Etudiants"));
        buildings.add(new Building("Maison des Sciences Humaines"));
        buildings.add(new Building("Maison de la Biomédecine I"));
        buildings.add(new Building("Maison de la Biomédecine II"));
        buildings.add(new Building("Luxembourg Learning Centre"));
        buildings.add(new Building("Rockhal"));
        buildings.add(new Building("Gare Belval Universite"));
        buildings.add(new Building("Plaza I"));
        buildings.add(new Building("Plaza II"));
        buildings.add(new Building("Food Lab"));
        buildings.add(new Building("Food House"));
    }

    private void createCoordinates() {
        coordinates = new HashMap<>();

        coordinates.put("Maison du Savoir", Arrays.asList("49.504218", "5.949260"));
        coordinates.put("Maison du Nombre", Arrays.asList("49.503634", "5.94778"));
        coordinates.put("Maison des Arts et des Etudiants", Arrays.asList("49.503912", "5.948484"));
        coordinates.put("Maison des Sciences Humaines", Arrays.asList("49.504114", "5.947132"));
        coordinates.put("Maison de la Biomédecine I", Arrays.asList("49.501561", "5.949205"));
        coordinates.put("Maison de la Biomédecine II", Arrays.asList("49.502048", "5.942973"));
        coordinates.put("Luxembourg Learning Centre", Arrays.asList("49.502554", "5.947737"));
        coordinates.put("Rockhal", Arrays.asList("49.500106", "5.947502"));
        coordinates.put("Gare Belval Universite", Arrays.asList("49.499588", "5.946146"));
        coordinates.put("Plaza I", Arrays.asList("49.500748", "5.945427"));
        coordinates.put("Plaza II", Arrays.asList("49.501776", "5.945223"));
        coordinates.put("Food Lab", Arrays.asList("49.503950", "5.946981"));
        coordinates.put("Food House", Arrays.asList("49.503800", "5.949280"));
    }
}