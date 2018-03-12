package com.jogdand.rnotes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Date;
import java.util.Calendar;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmObject;

@SuppressWarnings("FieldCanBeLocal")
public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button doneBtn;
    private EditText titleEt;
    private EditText contentEt;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() == null || getIntent().getAction() == null) {
            finish();
        }
        setContentView(R.layout.activity_details);
        bindViews();
        addListeners();
        note = new Note();

        if (Objects.equals(getIntent().getAction(), "editNote")) {
            note.title = getIntent().getStringExtra("noteTitle");
            note.content = getIntent().getStringExtra("noteContent");
            note.id = getIntent().getStringExtra("noteId");
        } else if (Objects.equals(getIntent().getAction(), "createNote")){
            note.id = Calendar.getInstance().getTimeInMillis() + "";
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
            note.title = titleEt.getText().toString();
            note.content = contentEt.getText().toString();
            note.date = new Date(Calendar.getInstance().getTimeInMillis());
            note.isArchived = false;
            Realm db = Realm.getDefaultInstance(); /* Always, get database reference */
            db.beginTransaction();  /* For update, insertion operations */
            db.insertOrUpdate(note);
            db.commitTransaction();     /* finalise the operation */
            db.close();     /* close db reference */
            finish();
        }
    }
}
