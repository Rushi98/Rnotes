package com.jogdand.rnotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Date;
import java.util.Calendar;
import java.util.Objects;

import io.realm.Realm;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button doneBtn;
    private EditText titleEt;
    private EditText contentEt;
    private Note note;
    private Realm db;

    @Override
    protected void onDestroy() {
        if (!db.isClosed()) db.close();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent receivedArgs = getIntent();
        if (receivedArgs == null || receivedArgs.getAction() == null) {
            finish();
            return;
        }
        db = Realm.getDefaultInstance();
        setContentView(R.layout.activity_details);
        bindViews();
        addListeners();

        String id = receivedArgs.getStringExtra("id");
        if (Objects.equals(receivedArgs.getAction(), "editNote")
                && id != null) {
            note = db.where(Note.class).equalTo("id", id).findFirst();
        }

        if (note == null) {
            note = new Note();
            note.id = "" + Calendar.getInstance().getTimeInMillis();
            note.title = "";
            note.content = "";
        }
        populateViews();
    }

    private void populateViews() {
        titleEt.setText(note.title);
        contentEt.setText(note.content);
    }

    private void bindViews() {
        titleEt = findViewById(R.id.et_title);
        contentEt = findViewById(R.id.et_content);
        doneBtn = findViewById(R.id.btn_done);
    }

    private void addListeners() {
        doneBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_done) {
            db.beginTransaction();
            note.title = titleEt.getText().toString();
            note.content = contentEt.getText().toString();
            note.date = new Date(Calendar.getInstance().getTimeInMillis());
            note.isArchived = false;
            db.insertOrUpdate(note);
            db.commitTransaction();
            finish();
        }
    }
}
