package lu.uni.sep.group5.UniLux.ui.history;

//This is the front page where you can get to the descriptions.
// Created array of image, title, and description with translation.

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import lu.uni.sep.group5.UniLux.R;

public class HistoryActivity extends AppCompatActivity {
    //This attribute is the view of the list where the titles will be.
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //Array of the images.
        // Had to do it that way because could not find a way ont the .xml file.
        final int[] images = new int[] {
                R.drawable.image1850, R.drawable.image1868, R.drawable.image1909,
                R.drawable.image1953, R.drawable.image1965, R.drawable.image1993,
                R.drawable.image1995, R.drawable.image1997, R.drawable.image2000,
                R.drawable.image2001, R.drawable.image2002, R.drawable.image2005,
                R.drawable.image2006, R.drawable.image2007, R.drawable.image2008,
                R.drawable.image2018
        };

        //Array of the title. You can find the content in string.xml.
        final String[] title = getResources().getStringArray(R.array.title_story);

        //Array of the description. You can find the content in string.xml.
        // There is also the translations.
        final String[] description = getResources().getStringArray(R.array.details_story);

        listView = findViewById(R.id.list);

        //This method puts the listView/titles on the screen and makes it works as buttons.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, title);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HistoryActivity.this, HistoryPagesActivity.class);

                intent.putExtra("image", images);
                intent.putExtra("title", title);
                intent.putExtra("description", description);
                intent.putExtra("position", position);

                startActivity(intent);
            }
        });
    }
}
