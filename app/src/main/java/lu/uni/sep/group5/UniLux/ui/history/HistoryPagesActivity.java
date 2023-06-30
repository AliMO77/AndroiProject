package lu.uni.sep.group5.UniLux.ui.history;

//This class is basically the pages where the descriptions are.

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import lu.uni.sep.group5.UniLux.R;

public class HistoryPagesActivity extends AppCompatActivity {

    private  View root;

    //This attribute is the content of the image
    private ImageView imageView;
    //This attribute is the content of the title
    private TextView textViewTitle;
    //This attribute is the content of the description
    private TextView textViewDes;



    //These attributes are the button for previous/next page
    private Button nbtn, pbtn;

    //This attribute is the position of the pages
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_pages);

        //Get the position of the HistoryActivity class
        position = getIntent().getIntExtra("position", 0);

        //Get the image from the array from the HistoryActivity class and put it on the screen
        imageView = findViewById(R.id.image);
        final int[] tImage = getIntent().getIntArrayExtra("image");
        imageView.setImageResource(tImage[position]);

        //Get the title from the array HistoryActivity class and put it on the screen
        textViewTitle = findViewById(R.id.title);
        final String[] tStory = getIntent().getStringArrayExtra("title");
        textViewTitle.setText(tStory[position]);

        //Get the description from the array HistoryActivity class and put it on the screen
        textViewDes = findViewById(R.id.txt);
        final String[] dStory = getIntent().getStringArrayExtra("description");
        textViewDes.setText(dStory[position]);

        nbtn = findViewById(R.id.next_btn);
        pbtn = findViewById(R.id.prev_btn);

        //This method enables to get to the next page
        nbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = (position+1)%dStory.length;
                imageView.setImageResource(tImage[position]);
                textViewTitle.setText(tStory[position]);
                textViewDes.setText(dStory[position]);
            }
        });

        //This method enables to get to the previous page.
        // Had to make an if statement because an error statement was shown if you wanted to go
        // to the previous page in the first page/position 0. Also make it that if you are on first
        // page you go to the last page.
        pbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position != 0) {
                    position = (position-1)%dStory.length;
                    imageView.setImageResource(tImage[position]);
                    textViewTitle.setText(tStory[position]);
                    textViewDes.setText(dStory[position]);
                } else {
                    position = dStory.length-1;
                    imageView.setImageResource(tImage[position]);
                    textViewTitle.setText(tStory[position]);
                    textViewDes.setText(dStory[position]);
                }

            }
        });


    }
}
