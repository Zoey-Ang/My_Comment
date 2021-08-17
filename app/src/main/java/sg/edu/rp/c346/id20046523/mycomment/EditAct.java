package sg.edu.rp.c346.id20046523.mycomment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class EditAct extends AppCompatActivity {

    EditText etID, etName, etYear, etVoice;
    Button btnUpdate, btnRemove, btnCancel;
    RatingBar rbStars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnRemove = (Button) findViewById(R.id.btnRemove);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        etID = (EditText) findViewById(R.id.etID);
        etName = (EditText) findViewById(R.id.etName);
        etYear = (EditText) findViewById(R.id.etYear);
        etVoice = (EditText) findViewById(R.id.etVoice);
        rbStars = findViewById(R.id.rbStars);

        Intent i = getIntent();
        final Show currentShow = (Show) i.getSerializableExtra("show");

        etID.setText(currentShow.getId()+"");
        etName.setText(currentShow.getName());
        etYear.setText(currentShow.getYor()+"");
        etVoice.setText(currentShow.getVoice());
        rbStars.setRating((float) currentShow.getStars());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditAct.this);
                currentShow.setName(etName.getText().toString().trim());
                currentShow.setVoice(etVoice.getText().toString().trim());

                int years = 0;
                try {
                    years = Integer.valueOf(etYear.getText().toString().trim());
                } catch (Exception e){
                    Toast.makeText(EditAct.this, "Invalid year", Toast.LENGTH_SHORT).show();
                    return;
                }
                currentShow.setYor(years);

                int stars = (int) rbStars.getRating();
                currentShow.setStars(stars);
                int result = dbh.updateShow(currentShow);
                if (result>0){
                    Toast.makeText(EditAct.this, "Island updated", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditAct.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditAct.this);
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditAct.this);
                myBuilder.setTitle("Confirmation");
                myBuilder.setMessage("Are you sure you want to delete the island\n" + currentShow.getName());
                myBuilder.setCancelable(false);
                myBuilder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int result = dbh.deleteShow(currentShow.getId());
                        if (result>0){
                            Toast.makeText(EditAct.this, "Show deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(EditAct.this, "Delete failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                myBuilder.setNegativeButton("CANCEL", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditAct.this);
                myBuilder.setTitle("ALERT!!!");
                myBuilder.setMessage("Are you sure you want to discard the changes" + currentShow.getName());
                myBuilder.setCancelable(false);
                myBuilder.setPositiveButton("DISCARD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                myBuilder.setNegativeButton("DO NOT DISCARD", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });
    }
}