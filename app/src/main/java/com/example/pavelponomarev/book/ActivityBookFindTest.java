package com.example.pavelponomarev.book;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.pavelponomarev.book.Epub.BookInfo;
import com.example.pavelponomarev.book.Epub.BookInfoGridAdapter;
import com.example.pavelponomarev.book.Epub.EpubReader;
import com.example.pavelponomarev.book.PDF.PdfActivity;
import com.github.mertakdut.Reader;
import com.github.mertakdut.exception.ReadingException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActivityBookFindTest extends AppCompatActivity {
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_test);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_book_test);
        setSupportActionBar(toolbar);

        GridView gridView = (GridView) findViewById(R.id.grid_book_info);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String bookInfoTitle = ((BookInfo) adapterView.getAdapter().getItem(i)).getTitle();
//                byte[] bookInfoCoverImege = ((BookInfo) adapterView.getAdapter().getItem(i)).getCoverImage();
                String bookInfoFilePath = ((BookInfo) adapterView.getAdapter().getItem(i)).getFilePath();
//                boolean bookInfoIsCoverImageNotExists = ((BookInfo) adapterView.getAdapter().getItem(i)).isCoverImageNotExists();
//                Bitmap bookInfoCoverImageBitmap = ((BookInfo) adapterView.getAdapter().getItem(i)).getCoverImageBitmap();
                Intent intent = null;
                if (bookInfoFilePath.endsWith(".epub")) {
//                    Intent intentToMain = new Intent(ActivityBookFindTest.this, MainActivity.class);
//                    intentToMain.putExtra("books", bookInfoTitle);
//                    intentToMain.putExtra("books", bookInfoCoverImege);
//                    intentToMain.putExtra("books", bookInfoFilePath);
//                    intentToMain.putExtra("books", bookInfoIsCoverImageNotExists);
//                    intentToMain.putExtra("books", bookInfoCoverImageBitmap);
                    askForWidgetToUse(bookInfoFilePath);

                } else if (bookInfoFilePath.endsWith(".pdf")) {
                    Intent intentToMain = new Intent(ActivityBookFindTest.this, MainActivity.class);
//                    intentToMain.putExtra("books", bookInfoTitle);
//                    intentToMain.putExtra("books", bookInfoCoverImege);
//                    intentToMain.putExtra("books", bookInfoFilePath);
//                    intentToMain.putExtra("books", bookInfoIsCoverImageNotExists);
//                    intentToMain.putExtra("books", bookInfoCoverImageBitmap);
                    intent = new Intent(ActivityBookFindTest.this, PdfActivity.class);
                    intent.putExtra("PATH", bookInfoFilePath);
                    startActivity(intent);


                }
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        new ListBookInfoTask().execute();
    }

    private class ListBookInfoTask extends AsyncTask<Object, Object, List<BookInfo>> {

        private Exception occuredException;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<BookInfo> doInBackground(Object... objects) {
            List<BookInfo> bookInfoList = searchForPdfFiles();
            Reader reader = new Reader();
            for (BookInfo bookInfo : bookInfoList) {
                try {
                    reader.setInfoContent(bookInfo.getFilePath());

                    String title = reader.getInfoPackage().getMetadata().getTitle();
                    if (title != null && !title.equals("")) {
                        bookInfo.setTitle(reader.getInfoPackage().getMetadata().getTitle());
                    } else {//если нет заголовка используем название файла
                        int dotIndex = bookInfo.getTitle().lastIndexOf('.');
                        bookInfo.setTitle(bookInfo.getTitle().substring(0, dotIndex));
                    }
                    bookInfo.setCoverImage(reader.getCoverImage());

                } catch (ReadingException e) {
                    occuredException = e;
                    e.printStackTrace();
                }
            }

            return bookInfoList;
        }

        @Override
        protected void onPostExecute(List<BookInfo> bookInfoList) {
            super.onPostExecute(bookInfoList);
            progressBar.setVisibility(View.GONE);

            if (bookInfoList != null) {
                BookInfoGridAdapter adapter = new BookInfoGridAdapter(ActivityBookFindTest.this, bookInfoList);
                ((GridView) findViewById(R.id.grid_book_info)).setAdapter(adapter);
            }

            if (occuredException != null) {
                Toast.makeText(ActivityBookFindTest.this, occuredException.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

    }

    private List<BookInfo> searchForPdfFiles() {
        boolean isSDPresent = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        List<BookInfo> bookInfoList = null;
        if (isSDPresent) {
            bookInfoList = new ArrayList<>();
            List<File> files = getListFiles(new File(Environment.getExternalStorageDirectory().getAbsolutePath()));
            File sampleFile = getFileFromAssets("pg28885-images_new.epub");
            files.add(0, sampleFile);
            for (File file : files) {
                BookInfo bookInfo = new BookInfo();
                bookInfo.setTitle(file.getName());
                bookInfo.setFilePath(file.getPath());
                bookInfoList.add(bookInfo);

            }

        }
        return bookInfoList;
    }

    public File getFileFromAssets(String fileName) {
        File file = new File(getCacheDir() + "/" + fileName);
        if (!file.exists()) try {
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(buffer);
            fos.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return file;
    }

    private List<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<>();
        File[] files = parentDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    inFiles.addAll(getListFiles(file));
                } else {
                    if (file.getName().endsWith(".epub") || file.getName().endsWith(".pdf")) {
                        inFiles.add(file);
                    }
                }

            }
        }
        return inFiles;

    }


    private void askForWidgetToUse(final String filePath) {
        final Intent intent = new Intent(ActivityBookFindTest.this, EpubReader.class);
        intent.putExtra("filePath", filePath);
        new AlertDialog.Builder(ActivityBookFindTest.this)
                .setTitle("Как вы хотите открыть книгу: ")
                .setMessage("В текстовом или Web виде?")

                .setPositiveButton("Текстовый формат", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        intent.putExtra("isWebView", false);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Web формат", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        intent.putExtra("isWebView", true);
                        startActivity(intent);

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }
}
