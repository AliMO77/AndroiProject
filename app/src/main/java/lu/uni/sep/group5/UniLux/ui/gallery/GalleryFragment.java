package lu.uni.sep.group5.UniLux.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import lu.uni.sep.group5.UniLux.R;

public class GalleryFragment extends Fragment {
    private final String TAG = GalleryFragment.class.getName();

    private View root;

    private GalleryViewModel galleryViewModel;
    private int size, i;
    private TextView descriptionTxt;
    private RelativeLayout layout;

    private ImageButton previousBtn, nextBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        root = inflater.inflate(R.layout.fragment_gallery, container, false);

        initComponents();
        initSetOnClickListeners();

        return root;
    }

    private void initComponents() {

        i = 1;
        size = 11;

        previousBtn = root.findViewById(R.id.gallery_previous);
        nextBtn = root.findViewById(R.id.gallery_next);
        descriptionTxt = root.findViewById(R.id.gallery_description);
        layout = root.findViewById(R.id.gallery_layout);

        setImageAndDescription();
    }

    private void initSetOnClickListeners() {
        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i--;
                if (i == 0) {
                    i = size;
                }
                setImageAndDescription();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                if (i > size) {
                    i = 1;
                }
                setImageAndDescription();
            }
        });

        layout.setOnTouchListener(new RelativeLayoutTouchListener(getActivity()) {
            @Override
            public void onRightToLeftSwipe() {
                i++;
                if (i > size) {
                    i = 1;
                }
                setImageAndDescription();
            }

            @Override
            public void onLeftToRightSwipe() {
                i--;
                if (i == 0) {
                    i = size;
                }
                setImageAndDescription();
            }
        });
    }

    private void setImageAndDescription() {
        int backgroundId = getResources().getIdentifier("gallery_" + i, "drawable", getActivity().getPackageName());
        layout.setBackground(getResources().getDrawable(backgroundId));

        int descriptionId = getResources().getIdentifier("gallery_desc_" + i, "string", getActivity().getPackageName());
        descriptionTxt.setText(getResources().getString(descriptionId));
    }
}
