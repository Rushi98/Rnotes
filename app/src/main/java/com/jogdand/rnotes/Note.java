package com.jogdand.rnotes;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Rushikesh Jogdand.
 */

public class Note extends RealmObject {

    @PrimaryKey
    public String id;

    public String title;

    public String content;

    public Date date;

    public boolean isArchived;
}
