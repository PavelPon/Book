package com.example.pavelponomarev.book.Epub;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pavelponomarev.book.ActivityBookFindTest;
import com.example.pavelponomarev.book.R;

import java.util.List;

public class BookInfoGridAdapter extends BaseAdapter {
    private Context context;
    private List<BookInfo> bookInfoList;


    public BookInfoGridAdapter(Context context, List<BookInfo> bookInfoList) {
        this.context = context;
        this.bookInfoList = bookInfoList;

    }

    private final class ViewHolder {
        public TextView title;
        public ImageView coverImage;
    }

    @Override
    public int getCount() {
        return bookInfoList.size();
    }

    @Override
    public Object getItem(int i) {
        return bookInfoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.book_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.txt_book_title);
            viewHolder.coverImage = (ImageView) convertView.findViewById(R.id.img_cover);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        viewHolder.title.setText(bookInfoList.get(position).getTitle());
        boolean isCoverImageNotExists = bookInfoList.get(position).isCoverImageNotExists();
        if (!isCoverImageNotExists) {
            Bitmap saveBitmap = bookInfoList.get(position).getCoverImageBitmap();
            if (saveBitmap != null) {
                viewHolder.coverImage.setImageBitmap(saveBitmap);
            } else {
                byte[] coverImageAsBytes = bookInfoList.get(position).getCoverImage();
                if (coverImageAsBytes != null) {
                    Bitmap bitmap = decodeBitmapFromByteArray(coverImageAsBytes, 100, 200);
                    bookInfoList.get(position).setCoverImageBitmap(bitmap);
                    bookInfoList.get(position).setCoverImage(null);
                    viewHolder.coverImage.setImageBitmap(bitmap);
                } else {
                    bookInfoList.get(position).setCoverImageNotExists(true);
                    viewHolder.coverImage.setImageResource(R.drawable.ic_pdf_file);
                }
            }
        } else {

            viewHolder.coverImage.setImageResource(R.drawable.ic_pdf_file);
        }
        return convertView;
    }

    private Bitmap decodeBitmapFromByteArray(byte[] coverImage, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(coverImage, 0, coverImage.length, options);
        options.inSampleSize = calculateInSampelSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(coverImage, 0, coverImage.length, options);
    }

    private int calculateInSampelSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampelSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidht = width / 2;
            while ((halfHeight / inSampelSize) > reqHeight && (halfWidht / inSampelSize) > reqWidth) {
                inSampelSize *= 2;
            }
        }
        return inSampelSize;
    }

}

