package lu.uni.sep.group5.UniLux.ui.Snakey;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lu.uni.sep.group5.UniLux.MainActivity;
import lu.uni.sep.group5.UniLux.R;


public class GameFragment extends Fragment implements View.OnClickListener {

private  View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_game, container, false);

        root.findViewById(R.id.quit_button).setOnClickListener(this);
        root.findViewById(R.id.play_button).setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.play_button:
                Intent j = new Intent(getActivity(), SnakeFragment.class);
                startActivity(j);

                break;
            case R.id.quit_button:
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(0, 0);
                break;
        }
    }
}
