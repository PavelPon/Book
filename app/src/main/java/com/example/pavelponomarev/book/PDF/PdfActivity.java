package com.example.pavelponomarev.book.PDF;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.pavelponomarev.book.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.github.barteksc.pdfviewer.scroll.ScrollHandle;


import java.io.File;

public class PdfActivity extends AppCompatActivity implements View.OnClickListener {
    public PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_book_reader);
        pdfView = (PDFView) findViewById(R.id.pdf_view);
        Intent intent = this.getIntent();
        String path = intent.getExtras().getString("PATH");
        File file = new File(path);
        if (file.canRead()) {
            pdfView.fromFile(file)
                    .enableDoubletap(true)
                    .enableAntialiasing(true)
                    .spacing(0)
                    .swipeHorizontal(true)
                    .onLoad(new OnLoadCompleteListener() {
                        @Override
                        public void loadComplete(int nbPages) {
                            Toast.makeText(PdfActivity.this, String.valueOf(nbPages), Toast.LENGTH_LONG).show();
                        }
                    })
                    .load();
        }

        Button buttonNext = findViewById(R.id.button_next);
        Button buttonPrev = findViewById(R.id.button_prev);
        buttonNext.setOnClickListener(this);
        buttonPrev.setOnClickListener(this);
    }

    public void nextPage(View view) {
        pdfView.jumpTo(pdfView.getCurrentPage() + 1, true);
    }

    public void prevPage(View view) {
        pdfView.jumpTo(pdfView.getCurrentPage() - 1, true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_next:
                nextPage(pdfView);
                break;
            case R.id.button_prev:
                prevPage(pdfView);
                break;

        }
    }
}