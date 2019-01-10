package com.example.pavelponomarev.book;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.io.File;
import java.util.ArrayList;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class ActivityFileFind extends AppCompatActivity {
private static final String TAG = "проверка работы списка";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_find);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_file_downloads_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewAdapterFindFile adapterFindFile = new RecyclerViewAdapterFindFile(this,getFiles());
        recyclerView.setAdapter(adapterFindFile);

    }

    private ArrayList<FindFiles> getFiles() {

        ArrayList<FindFiles> filesList = new ArrayList<>();


           File downloadsFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
           Log.e(TAG, "загрузки");
           FindFiles findFiles;
           if (downloadsFolder.exists()) {
               File[] filesInDownloads = downloadsFolder.listFiles();
               Log.e(TAG, "список всех файлов в загрузках");
               int i = 0;
               while (i < filesInDownloads.length) {
                   File fileBookDownloads = filesInDownloads[i];
                   Log.e(TAG, "выборочный список");
                   if (fileBookDownloads.getPath().endsWith("pdf")|| fileBookDownloads.getPath().endsWith("epub")) {
                       findFiles = new FindFiles();
                       findFiles.setName(fileBookDownloads.getName());
                       findFiles.setPath(fileBookDownloads.getAbsolutePath());
                       filesList.add(findFiles);
                   }
                   i++;
               }
           }
           Toast.makeText(this,"создание списка",Toast.LENGTH_LONG).show();
       return filesList;

    }

}
