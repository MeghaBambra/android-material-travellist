package bhouse.travellist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by megha on 15-03-06.
 */
public class TravelListAdapter extends RecyclerView.Adapter<TravelListAdapter.ViewHolder> {

    Context mContext;

    public TravelListAdapter (Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_places, parent, false);
        return new ViewHolder(v, mContext);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Place place = new PlaceData().placeList().get(position);
        holder.placeName.setText(place.name);
        //holder.placeImage.setImageResource(place.getImageResourceId(mContext));
        Picasso.with(mContext).load(place.getImageResourceId(mContext)).into(holder.placeImage);

        holder.placeHolder.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_PARAM_ID, position);

                ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        (Activity)mContext,
                        new Pair<View, String>(holder.placeImage,"tImage"),
                        new Pair<View, String>(holder.placeName,"tName"));
                // Now we can start the Activity, providing the activity options as a bundle
                ActivityCompat.startActivity((Activity)mContext, intent, activityOptions.toBundle());

            }
        });
    }

    @Override
    public int getItemCount() {
        return new PlaceData().placeList().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout placeHolder;
        public TextView placeName;
        public ImageView placeImage;

        public Context context;

        public ViewHolder(View itemView, Context _context) {
            super(itemView);
            context = _context;
            placeHolder = (LinearLayout) itemView.findViewById(R.id.mainHolder);
            placeName = (TextView) itemView.findViewById(R.id.placeName);
            placeImage = (ImageView) itemView.findViewById(R.id.placeImage);
        }

    }
}
