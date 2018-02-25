package com.jogdand.rnotes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * @author Rushikesh Jogdand.
 */

public class RAdapter extends RecyclerView.Adapter<NotesViewHolder> {

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
        // TODO: Replace dummy data by real one -> get Realm!
        // TODO: Also hide/un hide archived items based on the `showArchived`

        Realm db = Realm.getDefaultInstance();
        RealmQuery<Note> query = db.where(Note.class);
        /* A query object doesn;t containd any data't */
        RealmResults<Note> results = query.findAll();
        /* Results of `query` are now stored in `results variable` */

        this.notes = new ArrayList<>();
        for (Note n :
                results) {
            notes.add(db.copyFromRealm(n));
        }

        db.close();
        notifyDataSetChanged();
    }

    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View noteView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vh_do_item, parent, false);
        return new NotesViewHolder(noteView);
    }

    @Override
    public void onBindViewHolder(NotesViewHolder holder, int position) {
        holder.populateViews(this.notes.get(position));
    }

    @Override
    public int getItemCount() {
        return this.notes.size();
    }

}
