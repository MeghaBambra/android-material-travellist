package bhouse.travellist;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by megha on 15-03-06.
 */
public class TravelListAdapter extends RecyclerView.Adapter<TravelListAdapter.ViewHolder> {

    Context mContext;
    OnItemClickListener mItemClickListener;

    private final int VIEW_TYPE_DATA		= 0;
    private final int VIEW_TYPE_FOOTER 		= 1;

    public TravelListAdapter (Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType){
            case VIEW_TYPE_DATA:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_places, parent, false);
                break;
            case VIEW_TYPE_FOOTER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer, parent, false);
                break;
        }
        return new ViewHolder(view, mContext, viewType);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Place place = new PlaceData().placeList().get(position);

        if (place.type==VIEW_TYPE_DATA) {
            holder.placeName.setText(place.name);
            Picasso.with(mContext).load(place.getImageResourceId(mContext)).into(holder.placeImage);

            Bitmap photo = BitmapFactory.decodeResource(mContext.getResources(), place.getImageResourceId(mContext));

            Palette.generateAsync(photo, new Palette.PaletteAsyncListener() {
                public void onGenerated(Palette palette) {
                    int mutedLight = palette.getDarkVibrantColor(mContext.getResources().getColor(android.R.color.black));
                    holder.placeName.setTextColor(mutedLight);
                }
            });
        }

        final ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams sglp = (StaggeredGridLayoutManager.LayoutParams) lp;
            sglp.setFullSpan(place.isFav);
            holder.itemView.setLayoutParams(sglp);
        }
    }

    @Override
    public int getItemCount() {
        return new PlaceData().placeList().size();
    }

    @Override
    public int getItemViewType(int position) {
        Place place = new PlaceData().placeList().get(position);
        return place.type;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public LinearLayout placeHolder;
        public TextView placeName;
        public ImageView placeImage;
        public Context context;

        public ViewHolder(View itemView, Context _context, int viewType) {
            super(itemView);
            context = _context;

            if (viewType==VIEW_TYPE_DATA) {
                placeHolder = (LinearLayout) itemView.findViewById(R.id.mainHolder);
                placeName = (TextView) itemView.findViewById(R.id.placeName);
                placeImage = (ImageView) itemView.findViewById(R.id.placeImage);
                placeHolder.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(itemView, getPosition());
            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view , int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}
