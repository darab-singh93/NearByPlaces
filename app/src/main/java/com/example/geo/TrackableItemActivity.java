package com.example.geo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.geo.List.DBHelper;
import com.example.geo.List.ListAddedItemActivity;
import com.example.geo.List.Trackable;

public class TrackableItemActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = TrackableItemActivity.this;
    private DBHelper dbHelper;
    private Trackable trackable;

    EditText edtName,edtDescription,edtWebsiteURL,edtCategory;
    Button btnRecordVideo,btnSelectMap,btnAddData,btnList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trackable_item);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        initObjects();
        initListeners();
    }
        private void initViews() {
            edtDescription=(EditText)findViewById(R.id.edtDescription);
            edtWebsiteURL=(EditText)findViewById(R.id.edtWebsiteURL);
            edtName=(EditText)findViewById(R.id.edtName);
            edtCategory=(EditText)findViewById(R.id.edtCategory);
            btnList=(Button)findViewById(R.id.btnList);
            btnAddData=(Button)findViewById(R.id.btnAddData);


        }

    private void initListeners() {
        btnAddData.setOnClickListener(this);
        btnList.setOnClickListener(this);

    }
    private void initObjects() {
        dbHelper = new DBHelper(activity);
        trackable = new Trackable();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnAddData:
                postDataToSQLite();
                break;

            case R.id.btnList:
                Intent accountsIntent = new Intent(activity, ListAddedItemActivity.class);
                accountsIntent.putExtra("NAME", edtName.getText().toString().trim());
                accountsIntent.putExtra("EMAIL", edtDescription.getText().toString().trim());
                accountsIntent.putExtra("ADDRESS", edtWebsiteURL.getText().toString().trim());
                accountsIntent.putExtra("COUNTRY", edtCategory.getText().toString().trim());
                emptyInputEditText();
                startActivity(accountsIntent);
                break;
        }
    }

    private void postDataToSQLite() {

        if (edtName.getText().toString().trim().length() == 0) {
            edtName.setError("Enter Name");
            edtName.requestFocus();
        }
        if (edtDescription.getText().toString().trim().length() == 0) {
            edtDescription.setError("Enter Descriiption");
            edtDescription.requestFocus();
        }
        if (edtWebsiteURL.getText().toString().trim().length() == 0) {
            edtWebsiteURL.setError("Enter Website URL");
            edtWebsiteURL.requestFocus();
        }
        if (edtCategory.getText().toString().trim().length() == 0) {
            edtCategory.setError("Enter Category");
            edtCategory.requestFocus();
        }

        if (!dbHelper.checkUser(edtName.getText().toString().trim())) {

            trackable.setName(edtName.getText().toString().trim());
            trackable.setDesc(edtDescription.getText().toString().trim());
            trackable.setWebsite(edtWebsiteURL.getText().toString().trim());
            trackable.setCategory(edtCategory.getText().toString().trim());

            dbHelper.addItem(trackable);

            // Snack Bar to show success message that record saved successfully
            Intent accountsIntent = new Intent(activity, ListAddedItemActivity.class);
            Toast.makeText(this, "Data Added Track!", Toast.LENGTH_SHORT)
                    .show();
            accountsIntent.putExtra("NAME", edtName.getText().toString().trim());
            accountsIntent.putExtra("EMAIL", edtDescription.getText().toString().trim());
            accountsIntent.putExtra("ADDRESS", edtWebsiteURL.getText().toString().trim());
            accountsIntent.putExtra("COUNTRY", edtCategory.getText().toString().trim());
            emptyInputEditText();
            startActivity(accountsIntent);


        } else {
        }


    }

    private void emptyInputEditText() {
        edtName.setText(null);
        edtDescription.setText(null);
        edtWebsiteURL.setText(null);
        edtCategory.setText(null);
    }
}