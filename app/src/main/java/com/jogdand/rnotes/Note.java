package com.jogdand.rnotes;


import android.support.annotation.NonNull;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Rushikesh Jogdand.
 */

@SuppressWarnings({"WeakerAccess", "NullableProblems"})
public class Note extends RealmObject {
    @PrimaryKey
    public String id;

    @NonNull
    public String title;

    @NonNull
    public String content;

    @NonNull
    public Date date;

    public boolean isArchived = false;
}
