package com.example.pavelponomarev.book;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapterBook extends RecyclerView.Adapter<RecyclerViewAdapterBook.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapterBook";
    private ArrayList<String> mBookName = new ArrayList<>();
    private ArrayList<String> mImageBook = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapterBook(Context context, ArrayList<String> bookName, ArrayList<String> imageBook) {
        mBookName = bookName;
        mImageBook = imageBook;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_list_book, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");
        Glide.with(mContext)
                .asBitmap()
                .load(mImageBook.get(position))
                .into(holder.imageBook);
        holder.nameBook.setText(mBookName.get(position));
        holder.bookParentLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d(TAG,"OnClick: clicked on: " +mBookName.get(position));
                Toast.makeText(mContext,mBookName.get(position),Toast.LENGTH_SHORT).show();
            }
        } );



    }

    @Override
    public int getItemCount() {
        return mImageBook.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imageBook;
        TextView nameBook;
        RelativeLayout bookParentLayout;

        public ViewHolder( View itemView) {
            super(itemView);
            imageBook = itemView.findViewById(R.id.image_book);
            nameBook = itemView.findViewById(R.id.book_name);
            bookParentLayout = itemView.findViewById(R.id.book_parent_layout);

        }
    }
}
