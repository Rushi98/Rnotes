package com.jogdand.rnotes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * @author Rushikesh Jogdand.
 */

@SuppressWarnings("WeakerAccess")
public class RAdapter extends RecyclerView.Adapter<NotesViewHolder> implements RealmChangeListener<RealmResults<Note>>{

    private ArrayList<Note> notes;
    private boolean showArchived = false;

    public RAdapter() {
        this.notes = new ArrayList<>();
        loadData();
    }

    public void setShowArchived(boolean show) {
        this.showArchived = show;
    }

    public void loadData() {

        Realm db = Realm.getDefaultInstance();
        RealmQuery<Note> query = db.where(Note.class);
        if (!showArchived) {
            query.equalTo("isArchived", false);
        }
        /* A query object doesn;t containd any data't */
        final RealmResults<Note> results = query.findAll();
        /* Results of `query` are now stored in `results variable` */
        results.addChangeListener(this);

        this.notes = new ArrayList<>();
        for (Note n :
                results) {
            notes.add(db.copyFromRealm(n));
        }

        db.close();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View noteView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vh_do_item, parent, false);
        return new NotesViewHolder(noteView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.populateViews(this.notes.get(position));
    }

    @Override
    public int getItemCount() {
        return this.notes.size();
    }

    @Override
    public void onChange(@NonNull RealmResults<Note> notes) {
        Realm db = Realm.getDefaultInstance();
        this.notes = new ArrayList<>();
        for (Note n :
                notes) {
            this.notes.add(db.copyFromRealm(n));
        }
        db.close();
        notifyDataSetChanged();
    }
}
