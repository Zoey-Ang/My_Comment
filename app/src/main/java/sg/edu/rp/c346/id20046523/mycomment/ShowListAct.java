package sg.edu.rp.c346.id20046523.mycomment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class ShowListAct extends AppCompatActivity {

    ListView lv;
    ArrayList<Show> showsList;
    CustomAdapter adapter;
    Button btn5stars;

    ArrayList<String> years;
    Spinner spn;
    ArrayAdapter<String> spnAdapter;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        DBHelper dbh = new DBHelper(this);
        showsList.clear();
        showsList.addAll(dbh.getAllShows());
        spnAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);

        lv = (ListView) this.findViewById(R.id.lv);
        btn5stars = (Button) this.findViewById(R.id.btn5stars);
        spn = (Spinner) this.findViewById(R.id.spnYear);

        DBHelper dbh = new DBHelper(this);
        showsList = dbh.getAllShows();
        years = dbh.getYears();
        dbh.close();

        adapter = new CustomAdapter(ShowListAct.this, R.layout.row,showsList);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ShowListAct.this, EditAct.class);
                i.putExtra("show", (Serializable) showsList.get(position));
                startActivity(i);
            }
        });

        btn5stars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ShowListAct.this);
                showsList.clear();
                showsList.addAll(dbh.getAllShowsByStars(5));
                adapter.notifyDataSetChanged();
            }
        });

        spnAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, years);
        spn.setAdapter(spnAdapter);

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DBHelper dbh = new DBHelper(ShowListAct.this);
                showsList.clear();
                showsList.addAll(dbh.getAllShowsByYear(Integer.valueOf(years.get(position))));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}