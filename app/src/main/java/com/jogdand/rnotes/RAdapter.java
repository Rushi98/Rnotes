package com.jogdand.rnotes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

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
        String titles[] = {
                "Polly Draper",
                "LMS Garratt",
                "Pavel Yengalychev",
                "VA-176 (U.S. Navy)",
                "1895 ICA Track Cycling World Championships"
        };
        String contents[] = {
                "Polly Carey Draper[2] (born June 15, 1955)[3] is an American actress, writer, producer, and director. Draper has received several awards, including a Writers Guild of America Award (WGA), and is noted for speaking in a \"trademark throaty voice.\"[4][5] She first gained recognition for her role in the ABC primetime television drama Thirtysomething (1987–91)",
                "The London Midland and Scottish Railway (LMS) Garratt was a class of Garratt 2-6-0+0-6-2 steam locomotive designed for heavy freight. A total of 33 were built from 1927, making them the most numerous class of Garratt in Britain.",
                "Prince Pavel Yengalychev or Engalytshev (Russian: Павел Николаевич Енгалычев; 25 March 1864 – 12 August 1944, Lausanne) was a Russian prince and general.",
                "Attack Squadron 176 (VA-176), known as the \"Thunderbolts\", was a United States Navy carrier-based medium attack squadron that saw combat service in the Vietnam War and later in 1983 in both Grenada and Lebanon.",
                "The 1895 ICA Track Cycling World Championships were the World Championship for track cycling. They took place in Cologne, Germany from 17 to 19 August 1895.[1] Four events for men were contested, two for professionals and two for amateurs."
        };
        this.notes = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            Note n = new Note();
            n.title = titles[i];
            n.content = contents[i];
            n.date = new Date(Calendar.getInstance().getTimeInMillis());
            notes.add(n);
        }
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
