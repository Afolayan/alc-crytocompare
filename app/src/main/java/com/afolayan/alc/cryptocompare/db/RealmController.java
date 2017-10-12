package com.afolayan.alc.cryptocompare.db;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by Afolayan Oluwaseyi on 10/11/17.
 */

public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {
        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {
        return instance;
    }

    public Realm getRealm() {
        return realm;
    }

    //Refresh the realm instance
    public void refresh() {
        //realm.refresh();
    }

    /**
     * clear all objects
     * @param which the RealmObject model class to clear
     *//*
    public void clearAll(Class<? extends io.realm.RealmObject> which) {

        realm.beginTransaction();
        realm.clear(which);
        realm.commitTransaction();
    }

    //find all objects in the Book.class
    public RealmResults<Book> getBooks() {

        return realm.where(Book.class).findAll();
    }

    //query a single item with the given id
    public Book getBook(String id) {

        return realm.where(Book.class).equalTo("id", id).findFirst();
    }

    //check if Book.class is empty
    public boolean hasBooks() {

        return !realm.allObjects(Book.class).isEmpty();
    }

    //query example
    public RealmResults<Book> queryedBooks() {

        return realm.where(Book.class)
                .contains("author", "Author 0")
                .or()
                .contains("title", "Realm")
                .findAll();

    }*/
}
