package bhouse.travellist;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.transition.Transition;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by megha on 15-03-10.
 */
public class DetailActivity extends Activity {

    public static final String EXTRA_PARAM_ID = "place_id";

    private ListView mList;
    private ImageView mImageView;
    private TextView mTitle;
    private LinearLayout mTitleHolder;
    private ImageButton mAddButton;

    private Place mPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mPlace = PlaceData.placeList().get(getIntent().getIntExtra(EXTRA_PARAM_ID,0));

        mList = (ListView) findViewById(R.id.list);
/*        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.view_header, mList, false);
        mList.addHeaderView(header, null, false);*/

        mImageView = (ImageView) findViewById(R.id.imageView);
        mTitle = (TextView) findViewById(R.id.textView);
        mTitleHolder = (LinearLayout) findViewById(R.id.llTextViewHolder);
        mAddButton = (ImageButton) findViewById(R.id.btn_add);

        ArrayAdapter adapter = new ArrayAdapter(this,
                R.layout.row_todo, PlaceData.placeNameArray);

        mList.setAdapter(adapter);

        loadPlace();

        getWindow().getEnterTransition().addListener(new TransitionAdapter() {
            @Override
            public void onTransitionEnd(Transition transition) {
                mAddButton.animate().alpha(1.0f);
                getWindow().getEnterTransition().removeListener(this);
            }
        });

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

        int defaultColor = getResources().getColor(R.color.primary_dark);

        getWindow().setBackgroundDrawable(new ColorDrawable(palette.getDarkMutedColor(defaultColor)));
        mTitleHolder.setBackgroundColor(palette.getLightMutedColor(defaultColor));
        colorRipple(mAddButton, palette.getVibrantColor(defaultColor),
                palette.getDarkVibrantColor(defaultColor));
    }

    private void colorRipple(ImageButton id, int bgColor, int tintColor) {
        View buttonView = id;
        RippleDrawable ripple = (RippleDrawable) buttonView.getBackground();
        GradientDrawable rippleBackground = (GradientDrawable) ripple.getDrawable(0);
        rippleBackground.setColor(bgColor);
        ripple.setColor(ColorStateList.valueOf(tintColor));
    }
}
