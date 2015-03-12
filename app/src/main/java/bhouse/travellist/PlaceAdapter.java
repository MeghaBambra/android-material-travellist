package bhouse.travellist;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by megha on 15-03-10.
 */
public class PlaceAdapter extends BaseAdapter {

    Activity mContext;

    public PlaceAdapter(Activity context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return new PlaceData().placeList().size();
    }

    @Override
    public Place getItem(int position) {
        return new PlaceData().placeList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        public LinearLayout placeHolder;
        public TextView placeName;
        public ImageView placeImage;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        final Place place = getItem(position);

        if (rowView == null) {
            LayoutInflater inflater = mContext.getLayoutInflater();
            rowView = inflater.inflate(R.layout.row_places, null);
            ViewHolder holder = new ViewHolder();
            holder.placeHolder = (LinearLayout) rowView.findViewById(R.id.mainHolder);
            holder.placeImage = (ImageView) rowView.findViewById(R.id.placeImage);
            holder.placeName = (TextView) rowView.findViewById(R.id.placeName);
            rowView.setTag(holder);
        }

        final ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.placeName.setText(place.name);
        holder.placeImage.setImageResource(place.getImageResourceId(mContext));

        holder.placeHolder.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String id = place.id;
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_PARAM_ID, position);

                ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        mContext,
                        new Pair<View, String>(holder.placeImage,"tImage"),
                        new Pair<View, String>(holder.placeName,"tName"));
                // Now we can start the Activity, providing the activity options as a bundle
                ActivityCompat.startActivity(mContext, intent, activityOptions.toBundle());

            }
        });

        return rowView;
    }
}
