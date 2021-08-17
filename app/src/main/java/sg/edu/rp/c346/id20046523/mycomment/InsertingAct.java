package sg.edu.rp.c346.id20046523.mycomment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

public class InsertingAct extends AppCompatActivity {

    ImageView ivPic;
    EditText etName, etYOR, etVoice;
    RatingBar rbStar;
    Button btnAdd, btnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserting);

        ivPic = findViewById(R.id.ivPic);
        etName = findViewById(R.id.etName);
        etYOR = findViewById(R.id.etYOR);
        etVoice = findViewById(R.id.mlVoice);
        rbStar = findViewById(R.id.rbStar);
        btnAdd = findViewById(R.id.btnAdd);
        btnList = findViewById(R.id.btnListHistory);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String voice =etVoice.getText().toString().trim();

                if (name.length()==0 || voice.length()==0)
                {
                    Toast.makeText(InsertingAct.this,"Incomplete data", Toast.LENGTH_SHORT).show();
                    return;
                }

                String strYear = etYOR.getText().toString().trim();
                int year = 0;
                try
                {
                    year = Integer.valueOf(strYear);
                }
                catch (Exception e){
                    Toast.makeText(InsertingAct.this, "Invalid year", Toast.LENGTH_LONG).show();
                    return;
                }

                DBHelper dbh = new DBHelper(InsertingAct.this);

                int stars = (int)rbStar.getRating();
                dbh.insertShow(name,year,stars,voice);
                dbh.close();
                Toast.makeText(InsertingAct.this, "Inserted", Toast.LENGTH_LONG).show();

                etName.setText("");
                etYOR.setText("");
                etVoice.setText("");
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InsertingAct.this,ShowListAct.class);
                startActivity(i);
            }
        });


    }
}