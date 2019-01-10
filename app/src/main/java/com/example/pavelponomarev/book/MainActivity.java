package com.example.pavelponomarev.book;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d(TAG, "onCreate: Stared.");
        inItImageBitmap();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_my_book_gallery) {

        } else if (id == R.id.nav_live_books) {

        } else if (id == R.id.nav_open_new_file_view) {
            Toast.makeText(MainActivity.this,"hi new file",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this,ActivityFileFind.class);
            startActivity(intent);
        } else if (id == R.id.nav_search_book_view) {

        } else if (id == R.id.nav_setting) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private ArrayList<FindFiles> mNamesBook = new ArrayList<>();
    private ArrayList<String> mImageBookFormat = new ArrayList<>();

    private void inItImageBitmap() {
        Log.d(TAG, "inItImageBitmap: preparing bitmaps");
//if(mNamesBook.getPath().endsWith("pdf")) {
//    mImageBookFormat.add("http://image.flaticon.com/icons/png/128/179/179483.png");
//}else if(mNamesBook.getPath().endsWith("epub")) {
//    mImageBookFormat.add("http://aux.iconspalace.com/uploads/file-epub-icon-256.png");
//}
        inItRecyclerView();
    }

    private void inItRecyclerView() {
        Log.d(TAG, "inItRecyclerView: init recycler_view.");
        RecyclerView recyclerView = findViewById(R.id.recycler_view_books);
        RecyclerViewAdapterBook adapterBook = new RecyclerViewAdapterBook(this, mNamesBook, mImageBookFormat);
        recyclerView.setAdapter(adapterBook);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
