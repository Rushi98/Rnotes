package com.jogdand.rnotes;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import io.realm.Realm;

/**
 * @author Rushikesh Jogdand.
 */

public class NotesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private ImageButton archiveBtn;
    private ImageButton deleteBtn;
    private TextView titleTv;
    private TextView contentTv;
    private View self;
    private Note note;
    private boolean showContent;

    public NotesViewHolder(View itemView) {
        super(itemView);
        bindViews(itemView);
        addListeners();
        this.showContent = false;
    }

    private void bindViews(View self) {
        this.self = self;
        this.archiveBtn = self.findViewById(R.id.btn_archive);
        this.deleteBtn = self.findViewById(R.id.btn_delete);
        this.titleTv = self.findViewById(R.id.tv_note_title);
        this.contentTv = self.findViewById(R.id.tv_note_content);
    }

    private void addListeners() {
        self.setOnClickListener(this);
        self.setOnLongClickListener(this);
        archiveBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }

    public void populateViews(Note note) {
        this.note = note;
        titleTv.setText(note.title);
        contentTv.setText(note.content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_archive: {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                note.isArchived = true;
                realm.copyToRealmOrUpdate(note);
                realm.commitTransaction();
                realm.close();
            }
            break;
            case R.id.btn_delete: {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.where(Note.class).equalTo("id", note.id).findAll().deleteAllFromRealm();
                realm.commitTransaction();
                realm.close();
            }
            break;
            default: {
                showContent = !showContent;
                if (showContent) contentTv.setVisibility(View.VISIBLE);
                else contentTv.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == R.id.cv_item) {
            Intent intent = new Intent(self.getContext(), DetailsActivity.class);
            intent.setAction("editNote");
            intent.putExtra("id", note.id);
            self.getContext().startActivity(intent);
        }
        return false;
    }
}
