package com.askel.catchup;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends ContactActivity implements LoaderManager.LoaderCallbacks<Cursor> {

        private static final int CONTACTS_LOADER_ID = 1;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_contact);

            // Prepare the loader.  Either re-connect with an existing one,
            // or start a new one.
            getLoaderManager().initLoader(CONTACTS_LOADER_ID,
                    null,
                    this);
        }

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            // This is called when a new Loader needs to be created.

            if (id == CONTACTS_LOADER_ID) {
                return contactsLoader();
            }
            return null;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            //The framework will take care of closing the
            // old cursor once we return.
            List<String> contacts = contactsFromCursor(cursor);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            // This is called when the last Cursor provided to onLoadFinished()
            // above is about to be closed.  We need to make sure we are no
            // longer using it.
        }

        private  Loader<Cursor> contactsLoader() {
            Uri contactsUri = ContactsContract.Contacts.CONTENT_URI; // The content URI of the phone contacts

            String[] projection = {                                  // The columns to return for each row
                    ContactsContract.Contacts.DISPLAY_NAME
            } ;

            String selection = null;                                 //Selection criteria
            String[] selectionArgs = {};                             //Selection criteria
            String sortOrder = null;                                 //The sort order for the returned rows

            return new CursorLoader(
                    getApplicationContext(),
                    contactsUri,
                    projection,
                    selection,
                    selectionArgs,
                    sortOrder);
        }

        private List<String> contactsFromCursor(Cursor cursor) {
            List<String> contacts = new ArrayList<String>();

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    contacts.add(name);
                } while (cursor.moveToNext());
            }

            return contacts;
        }

    }


