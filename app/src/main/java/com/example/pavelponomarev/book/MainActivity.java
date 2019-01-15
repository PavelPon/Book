package com.example.pavelponomarev.book;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.pavelponomarev.book.Epub.BookInfo;
import com.example.pavelponomarev.book.Epub.BookInfoGridFavoriteAdapter;
import com.example.pavelponomarev.book.Epub.EpubReader;
import com.example.pavelponomarev.book.PDF.PdfActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    public ArrayList<BookInfo> favoriteBook = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d(TAG, "onCreate: Stared.");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Дабавить в избранное при длительном нажатии на файл", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent1 = new Intent(MainActivity.this, ActivityBookFindTest.class);
                startActivity(intent1);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // попытка передать данные из активити
//       try {
//            String bookInfoTitle = getIntent().getExtras().getString("books1");
//            byte[] bookInfoCoverImage = (byte[]) getIntent().getExtras().get("books2");
//            String bookInfoFilePath = getIntent().getExtras().getString("books3");
//            boolean bookInfoIsCoverImageNotExists = getIntent().getExtras().getBoolean("books4");
//            Bitmap bookInfoCoverImageBitmap = (Bitmap) getIntent().getExtras().get("books5");
//
//            BookInfo bookInfo = new BookInfo(bookInfoTitle, bookInfoCoverImage, bookInfoFilePath, bookInfoIsCoverImageNotExists, bookInfoCoverImageBitmap);
//            favoriteBook.add(bookInfo);
//            inBooksFavorite();
//        }catch (Exception e){
//           e.printStackTrace();
//           Toast.makeText(MainActivity.this,"Список Пуст",Toast.LENGTH_LONG).show();
//       }
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
            Toast.makeText(MainActivity.this, "Функция пока не работает", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_my_book_gallery) {
            Toast.makeText(MainActivity.this, "Функция пока не работает", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_live_books) {
            Toast.makeText(MainActivity.this, "Функция пока не работает", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_open_new_file_view) {
            Intent intent1 = new Intent(MainActivity.this, ActivityBookFindTest.class);
            startActivity(intent1);
        } else if (id == R.id.nav_search_book_view) {
            Toast.makeText(MainActivity.this, "Функция пока не работает", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_setting) {
            Toast.makeText(MainActivity.this, "Функция пока не работает", Toast.LENGTH_LONG).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    private void inBooksFavorite() {
//        GridView gridView = (GridView) findViewById(R.id.grid_book_info_main);
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String bookInfoFilePath = ((BookInfo) adapterView.getAdapter().getItem(i)).getTitle();
//                if (bookInfoFilePath.endsWith(".epub")) {
//                    askForWidgetToUse(bookInfoFilePath);
//
//                } else if (bookInfoFilePath.endsWith(".pdf")) {
//                    Intent intent = new Intent(MainActivity.this, PdfActivity.class);
//                    intent.putExtra("PATH", bookInfoFilePath);
//                    startActivity(intent);
//
//                }
//            }
//        });
//
//        BookInfoGridFavoriteAdapter adapter = new BookInfoGridFavoriteAdapter(MainActivity.this, favoriteBook);
//        ((GridView) findViewById(R.id.grid_book_info_main)).setAdapter(adapter);

//
//    }
//
//    private void askForWidgetToUse(final String filePath) {
//        final Intent intent = new Intent(MainActivity.this, EpubReader.class);
//        intent.putExtra("filePath", filePath);
//        new AlertDialog.Builder(MainActivity.this)
//                .setTitle("Как вы хотите открыть книгу: ")
//                .setMessage("В текстовом или Web виде?")
//
//                .setPositiveButton("Текстовый формат", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        intent.putExtra("isWebView", false);
//                        startActivity(intent);
//                    }
//                })
//                .setNegativeButton("Web формат", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        intent.putExtra("isWebView", true);
//                        startActivity(intent);
//
//                    }
//                })
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .show();
//
//    }


}
