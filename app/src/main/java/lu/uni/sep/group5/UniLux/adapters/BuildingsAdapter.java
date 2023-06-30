package lu.uni.sep.group5.UniLux.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lu.uni.sep.group5.UniLux.R;
import lu.uni.sep.group5.UniLux.models.Building;
import lu.uni.sep.group5.UniLux.ui.map.ChildFragmentOsmMap;

public class BuildingsAdapter extends RecyclerView.Adapter<BuildingsAdapter.BuildingsViewHolder> {

    private ArrayList<Building> buildings;
    private Context context;
    private Dialog dialogFragment;
    private Button mainBtn;
    private Fragment fragment;
    private Map<String, List<String>> coordinates;

    public BuildingsAdapter(Context ctx, ArrayList<Building> list, Dialog dialog, View view,
                            Fragment f, Map<String, List<String>> c) {
        context = ctx;
        buildings = list;
        dialogFragment = dialog;
        fragment = f;
        coordinates = c;

        mainBtn = view.findViewById(R.id.map_buildings_button);
    }

    @NonNull
    @Override
    public BuildingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_building, parent, false);

        BuildingsAdapter.BuildingsViewHolder nvh = new BuildingsAdapter.BuildingsViewHolder(v);
        return nvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final BuildingsViewHolder holder, int position) {
        String name = buildings.get(position).getName();
        holder.name.setText(name);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainBtn.setText(holder.name.getText());
                dialogFragment.dismiss();
                ((ChildFragmentOsmMap) fragment).setLoc(Double.parseDouble(coordinates.get(holder.name.getText()).get(0)),
                        Double.parseDouble(coordinates.get(holder.name.getText()).get(1)));
                ((ChildFragmentOsmMap) fragment).setZoom(18.0f);
            }
        });
    }

    @Override
    public int getItemCount() {
        return buildings.size();
    }

    public static class BuildingsViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public LinearLayout linearLayout;

        public BuildingsViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.item_one_building);
            name = itemView.findViewById(R.id.item_building_name);
        }
    }
}
