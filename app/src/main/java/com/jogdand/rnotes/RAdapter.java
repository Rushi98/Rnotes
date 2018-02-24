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
import io.realm.Sort;

/**
 * @author Rushikesh Jogdand.
 */

public class RAdapter extends RecyclerView.Adapter<NotesViewHolder> implements RealmChangeListener<RealmResults<Note>> {

    private ArrayList<Note> notes;
    private boolean showArchived = false;
    private Realm db;
    private RealmResults<Note> notesResult;

    public RAdapter() {
        this.notes = new ArrayList<>();
        db = Realm.getDefaultInstance();
        loadData();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        if (!db.isClosed()) db.close();
        super.onDetachedFromRecyclerView(recyclerView);
    }

    public void setShowArchived(boolean show) {
        this.showArchived = show;
    }

    public void loadData() {
        // TODO: Replace dummy data by real one -> get Realm!
        // TODO: Also hide/un hide archived items based on the `showArchived`
        RealmQuery<Note> notesQuery;
        notesQuery = db.where(Note.class);
        if (!showArchived) notesQuery = notesQuery.equalTo("isArchived", false);
        notesResult = notesQuery.findAll().sort("date", Sort.DESCENDING);
        notes.clear();
        notes.addAll(notesResult);
        notesResult.removeAllChangeListeners();
        notesResult.addChangeListener(this);
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

    @Override
    public void onChange(@NonNull RealmResults<Note> notesResult) {
        this.notes.clear();
        this.notes.addAll(notesResult);
        notifyDataSetChanged();
    }
}
