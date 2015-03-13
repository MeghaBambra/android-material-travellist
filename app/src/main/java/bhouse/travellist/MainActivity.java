package bhouse.travellist;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;


public class MainActivity extends Activity {

    //private ListView mListView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private boolean isListView;
    private Menu menu;
    private TravelListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // use a linear layout manager
        //mLayoutManager = new LinearLayoutManager(this);
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mStaggeredLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        mStaggeredLayoutManager.supportsPredictiveItemAnimations();

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(mStaggeredLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(false);
        mAdapter = new TravelListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        isListView = true;
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
            //mRecyclerView.setLayoutManager(mStaggeredLayoutManager);
            item.setIcon(R.drawable.ic_action_list);
            isListView = false;
        } else {
            mStaggeredLayoutManager.setSpanCount(1);
            //mRecyclerView.setLayoutManager(mLayoutManager);
            item.setIcon(R.drawable.ic_action_grid);
            isListView = true;
        }
        mAdapter.notifyItemMoved(0,0);
        mStaggeredLayoutManager.invalidateSpanAssignments();
        //mStaggeredLayoutManager.onLayoutChildren(mRecyclerView, mRecyclerView.SCROLL_STATE_IDLE);
    }

}
