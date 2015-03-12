package bhouse.travellist;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by megha on 15-03-10.
 */
public class DetailActivity extends Activity {

    public static final String EXTRA_PARAM_ID = "place_id";

    private ImageView mImageView;
    private TextView mTitle;
    private LinearLayout mTitleHolder;

    private Place mPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mPlace = PlaceData.placeList().get(getIntent().getIntExtra(EXTRA_PARAM_ID,0));

        mImageView = (ImageView) findViewById(R.id.imageView);
        mTitle = (TextView) findViewById(R.id.textView);
        mTitleHolder = (LinearLayout) findViewById(R.id.llTextViewHolder);

        loadPlace();

        Bitmap photo = BitmapFactory.decodeResource(getResources(), mPlace.getImageResourceId(this));
        colorize(photo);
    }

    private void loadPlace() {
       mTitle.setText(mPlace.name);
       mImageView.setImageResource(mPlace.getImageResourceId(this));
    }

    private void colorize(Bitmap photo) {
        Palette palette = Palette.generate(photo);
        applyPalette(palette);
    }

    private void applyPalette(Palette palette) {
        getWindow().setBackgroundDrawable(new ColorDrawable(palette.getDarkMutedColor(getResources().getColor(R.color.primary_dark))));
        mTitleHolder.setBackgroundColor(palette.getVibrantColor(getResources().getColor(R.color.primary_dark)));

        /*TextView titleView = (TextView) findViewById(R.id.title);
        titleView.setTextColor(palette.getVibrantColor().getRgb());

        TextView descriptionView = (TextView) findViewById(R.id.description);
        descriptionView.setTextColor(palette.getLightVibrantColor().getRgb());

        colorRipple(R.id.info, palette.getDarkMutedColor().getRgb(),
                palette.getDarkVibrantColor().getRgb());
        colorRipple(R.id.star, palette.getMutedColor().getRgb(),
                palette.getVibrantColor().getRgb());

        View infoView = findViewById(R.id.information_container);
        infoView.setBackgroundColor(palette.getLightMutedColor().getRgb());

        AnimatedPathView star = (AnimatedPathView) findViewById(R.id.star_container);
        star.setFillColor(palette.getVibrantColor().getRgb());
        star.setStrokeColor(palette.getLightVibrantColor().getRgb());*/
    }

}
