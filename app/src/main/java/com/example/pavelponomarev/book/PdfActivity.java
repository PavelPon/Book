package com.example.pavelponomarev.book;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.widget.ScrollView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;


import java.io.File;

public class PdfActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_book_reader);

        PDFView pdfView = (PDFView) findViewById(R.id.pdf_view);
        ScrollView scrollView = (ScrollView) findViewById(R.id.scroll_pdf_view);
        pdfView.setSwipeVertical(true);
        scrollView.setHorizontalScrollBarEnabled(false);
        Intent intent = this.getIntent();
        String path = intent.getExtras().getString("PATH");
        File file = new File(path);
        if(file.canRead()){
            pdfView.fromFile(file).defaultPage(1).onLoad(new OnLoadCompleteListener() {
                @Override
                public void loadComplete(int nbPages) {
                    Toast.makeText(PdfActivity.this,String.valueOf(nbPages),Toast.LENGTH_LONG).show();
                }
            }).load();
        }
    }
}
