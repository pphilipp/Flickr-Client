package com.example.philipp.photogallery.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.philipp.photogallery.R;
import com.example.philipp.photogallery.model.GalleryItem;

import java.util.ArrayList;

public class GalleryAdapter  extends RecyclerView.Adapter<GalleryAdapter.RecyclerViewHolders> {

    private ArrayList<GalleryItem> mItemList;
    private Context mContext;

    public GalleryAdapter(Context context, ArrayList<GalleryItem> itemList) {
        mItemList = itemList;
        mContext = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.itemName.setText(mItemList.get(position).getUrl());
        holder.itemPhoto.setImageResource(R.drawable.image_cool);
    }

    /**
     * RecyclerViewHolders - ViewHolder for this adapter.
     */
    class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView itemName;
        ImageView itemPhoto;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemName = (TextView)itemView.findViewById(R.id.country_name);
            itemPhoto = (ImageView)itemView.findViewById(R.id.gallery_item_imageView);
        }


        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "Clicked Item Position = "
                    + getAdapterPosition(), Toast.LENGTH_SHORT).show();
        }
    }

}
