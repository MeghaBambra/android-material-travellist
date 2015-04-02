package bhouse.travellist;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;


public class MainActivity extends Activity {

  private Toolbar toolbar;

  private RecyclerView mRecyclerView;
  private StaggeredGridLayoutManager mStaggeredLayoutManager;
  private TravelListAdapter mAdapter;
  private boolean isListView;
  private Menu menu;

  //Transition Items
  Intent transitionIntent;
  CardView placeCard;
  TextView placeName;
  ImageView placeImage;
  ValueAnimator anim;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    toolbar = (Toolbar) findViewById(R.id.toolbar);
    setUpActionBar();

    mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
    mStaggeredLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
    mStaggeredLayoutManager.supportsPredictiveItemAnimations();

    mRecyclerView = (RecyclerView) findViewById(R.id.list);
    mRecyclerView.setLayoutManager(mStaggeredLayoutManager);
    mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    mRecyclerView.setHasFixedSize(true); //Data size is fixed - improves performance
    mAdapter = new TravelListAdapter(this);
    mRecyclerView.setAdapter(mAdapter);
    mAdapter.setOnItemClickListener(new TravelListAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(View v, int position) {
        transitionIntent = new Intent(MainActivity.this, DetailActivity.class);
        transitionIntent.putExtra(DetailActivity.EXTRA_PARAM_ID, position);
        placeName = (TextView) v.findViewById(R.id.placeName);
        placeImage = (ImageView) v.findViewById(R.id.placeImage);
        placeCard = (CardView) v.findViewById(R.id.placeCard);
        animateCardRadiusChange(10, 0, 150);
        startTransition();
      }
    });

    isListView = true;
  }

  @Override
  public void onResume() {
    super.onResume();
    if (placeCard != null) {
      animateCardRadiusChange(0, 10, 150);
    }
  }

  private void animateCardRadiusChange(int start, int end, int duration) {
    anim = ValueAnimator.ofInt(start, end);
    anim.setDuration(duration);
    anim.start();
    anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      public void onAnimationUpdate(ValueAnimator animation) {
        int value = (int) animation.getAnimatedValue();
        placeCard.setRadius(value);
      }
    });
  }

  private void startTransition() {
    anim.addListener(new AnimatorListenerAdapter() {
      public void onAnimationEnd(Animator animation) {
        View navigationBar = findViewById(android.R.id.navigationBarBackground);

        Pair<View, String> imagePair = Pair.create((View) placeImage, "tImage");
        Pair<View, String> titlePair = Pair.create((View) placeName, "tName");
        Pair<View, String> navPair = Pair.create(navigationBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, imagePair,
                titlePair, navPair);
        ActivityCompat.startActivity(MainActivity.this, transitionIntent, options.toBundle());
      }
    });
  }

  private void setUpActionBar() {
    if (toolbar != null) {
      setActionBar(toolbar);
      getActionBar().setDisplayHomeAsUpEnabled(false);
      getActionBar().setDisplayShowTitleEnabled(true);
      getActionBar().setElevation(7);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_main, menu);
    this.menu = menu;
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_toggle) {
      toggle();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void toggle() {
    MenuItem item = menu.findItem(R.id.action_toggle);
    if (isListView) {
      mStaggeredLayoutManager.setSpanCount(2);
      item.setIcon(R.drawable.ic_action_list);
      item.setTitle("Show as list");
      isListView = false;
    } else {
      mStaggeredLayoutManager.setSpanCount(1);
      item.setIcon(R.drawable.ic_action_grid);
      item.setTitle("Show as grid");
      isListView = true;
    }
  }
}