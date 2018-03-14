package com.jogdand.rnotes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

@SuppressWarnings({"WeakerAccess", "FieldCanBeLocal"})
public class RAdapter extends RecyclerView.Adapter<NotesViewHolder> implements RealmChangeListener<RealmResults<Note>>{

    private ArrayList<Note> notes;

    private boolean showArchived = false;
    /** _as a thumb rule_ we want realm instances to be available strictly where needed
     * see last comment of {@link #loadData()} for more
     */
    private Realm db;
    private RealmQuery<Note> notesQuery;
    private RealmResults<Note> notesResult;


    public RAdapter() {
        this.notes = new ArrayList<>();
        db = Realm.getDefaultInstance();
        loadData();
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        if (!db.isClosed()) db.close();
        Log.d("Realm Status", "onDetachedFromRecyclerView: closed");
        super.onDetachedFromRecyclerView(recyclerView);
    }

    public void setShowArchived(boolean show) {
        this.showArchived = show;
    }

    public void loadData() {
        notesQuery = db.where(Note.class);
        if (!showArchived) notesQuery = notesQuery.equalTo("isArchived", false);
        if (notesResult != null) notesResult.removeAllChangeListeners();    // to avoid listener code running multiple times
        notesResult = notesQuery.findAll().sort("date", Sort.DESCENDING);
        notes.clear();
        for (Note n :
                notesResult) {
            notes.add(db.copyFromRealm(n));
        }
        notesResult.addChangeListener(this);
        notifyDataSetChanged();
        /** db.close();       <-- this will close all references related to db
                                including `notesResult`
                                but we want to listen notesResult while this recyclerView is visible
                                so make db available for whole adapter life. see {@link Realm#close()};
        */
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
    public void onChange(@NonNull RealmResults<Note> notesResult) {
        notes.clear();
        for (Note n :
                notesResult) {
            notes.add(db.copyFromRealm(n));
        }
        notifyDataSetChanged();
    }
}
