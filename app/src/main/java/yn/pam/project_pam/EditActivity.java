package yn.pam.project_pam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import yn.pam.project_pam.database.AppDatabase;
import yn.pam.project_pam.database.entity.Transaksi;

public class EditActivity extends AppCompatActivity {

    ImageView closeButton;
    Button continueButton;
    private AppDatabase database;

    String kategori, deskripsi, nominal, sumber;

    int id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);

        Intent intent = getIntent();



        database = AppDatabase.getInstance(getApplicationContext());
        Transaksi transaksi = database.transaksiDao().get(intent.getIntExtra("id", 0));

        closeButton = findViewById(R.id.closeButton_edit);
        continueButton = findViewById(R.id.bt_continueEdit);
        String jsonItem = getIntent().getStringExtra("itemData");

        Spinner spinner_cat = findViewById(R.id.sp_category);
        Spinner spinner_wall = findViewById(R.id.sp_wallet);
        EditText amount = findViewById(R.id.et_amount);
        EditText desc = findViewById(R.id.et_desc);

        String[] category = {"Food", "Health", "Income", "Transport"};
        String[] wallet = {"Mandiri", "Cash"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, category );
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, wallet );

        spinner_cat.setAdapter(adapter);
        spinner_wall.setAdapter(adapter2);
        amount.setText(transaksi.nominal);
        desc.setText(transaksi.deskripsi);

        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).equals(transaksi.kategori)) {
                spinner_cat.setSelection(i);
                break;
            }
        }

        for (int i = 0; i < adapter2.getCount(); i++) {
            if (adapter2.getItem(i).equals(transaksi.sumber)) {
                spinner_wall.setSelection(i);
                break;
            }
        }
//
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                database.transaksiDao().updateAll(spinner_cat.getSelectedItem().toString(), desc.getText().toString(),
                        amount.getText().toString(), spinner_wall.getSelectedItem().toString(), transaksi.tid );
                finish();
            }
        });


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent i = this.getIntent();
    }

}