package com.example.test_php2;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class StrokeActivity extends Activity {

    private SQLiteDatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stroke);
        // create our sqlite helper class
        db = new SQLiteDatabaseHandler(this);
        // create some players
        Stroke stroke1 = new Stroke();
        Stroke stroke2 = new Stroke();
        stroke1.setId(111111111);
        stroke1.setSm1(23);
        stroke1.setSm2(24);
        stroke2.setSm1(25);
        stroke2.setSm2(21);
        stroke2.setId(222222222);

        // add them
        db.addStroke(stroke1);
        db.addStroke(stroke2);
        // list all players
        List<Stroke> strokes = db.allStrokes();

        if (strokes != null) {
            String[] itemsNames = new String[strokes.size()];

            for (int i = 0; i < strokes.size(); i++) {
                itemsNames[i] = strokes.get(i).toString();
            }

            // display like string instances
            ListView list = (ListView) findViewById(R.id.list);
            list.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, itemsNames));

        }

    }
}