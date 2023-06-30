package lu.uni.sep.group5.UniLux.ui.history;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import lu.uni.sep.group5.UniLux.MainActivity;
import lu.uni.sep.group5.UniLux.R;
import lu.uni.sep.group5.UniLux.ui.Snakey.SnakeFragment;


public class HistoFragment extends Fragment implements View.OnClickListener {


    private  View root;




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_histo, container, false);

        root.findViewById(R.id.start_histo).setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.start_histo:
                Intent j = new Intent(getActivity(), HistoryActivity.class);
                startActivity(j);

                break;

        }
    }
}
