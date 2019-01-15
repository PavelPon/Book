package com.example.pavelponomarev.book.Epub;

import android.graphics.Bitmap;

public class BookInfo {

    private String title;
    private byte[] coverImage;
    private String filePath;
    private boolean isCoverImageNotExists;
    private Bitmap coverImageBitmap;

//    public BookInfo(String bookInfoTitle, byte[] bookInfoCoverImage, String bookInfoFilePath, boolean bookInfoIsCoverImageNotExists, Bitmap bookInfoCoverImageBitmap) {
//        this.title = bookInfoTitle;
//        this.coverImage = bookInfoCoverImage;
//        this.filePath = bookInfoFilePath;
//        this.isCoverImageNotExists = bookInfoIsCoverImageNotExists;
//        this.coverImageBitmap =bookInfoCoverImageBitmap;
//
//    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(byte[] coverImage) {
        this.coverImage = coverImage;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isCoverImageNotExists() {
        return isCoverImageNotExists;
    }

    public void setCoverImageNotExists(boolean coverImageNotExists) {
        isCoverImageNotExists = coverImageNotExists;
    }

    public Bitmap getCoverImageBitmap() {
        return coverImageBitmap;
    }

    public void setCoverImageBitmap(Bitmap coverImageBitmap) {
        this.coverImageBitmap = coverImageBitmap;
    }

}
