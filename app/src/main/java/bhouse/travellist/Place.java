package bhouse.travellist;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by megha on 15-03-06.
 */
public class Place {

    public String id;
    public String name;
    public String imageName;
    public boolean isFav;

    public int getImageResourceId(Context context)
    {
        try {
            return context.getResources().getIdentifier(this.imageName, "drawable", context.getPackageName());

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}
