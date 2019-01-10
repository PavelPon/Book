package com.example.pavelponomarev.book;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class RecyclerViewAdapterFindFile extends RecyclerView.Adapter<RecyclerViewAdapterFindFile.FileFindViewHolder> {

    Context context;
    ArrayList<FindFiles> findFiles;

    public RecyclerViewAdapterFindFile(Context context, ArrayList<FindFiles> findFiles) {
        this.context = context;
        this.findFiles = findFiles;
    }

    @NonNull
    @Override
    public FileFindViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_file_find_in_downloads, parent, false);
        return new FileFindViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FileFindViewHolder holder, int position) {
        final FindFiles findFile = findFiles.get(position);
        holder.nameTxt.setText(findFile.getName());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                openFindFiles(findFile.getPath());
            }
        });
    }

    @Override
    public int getItemCount() {
        return findFiles.size();
    }

    //open file
    private void openFindFiles(String path) {
        Intent intent = new Intent(context, PdfActivity.class);
        intent.putExtra("PATH", path);
        context.startActivity(intent);
    }


    class FileFindViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameTxt;
        ImageView imageFormat;
        ItemClickListener itemClickListener;

        public FileFindViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = (TextView) itemView.findViewById(R.id.find_file_name);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(getLayoutPosition());
        }
    }

    interface ItemClickListener {
        void onItemClick(int pos);
    }


}
