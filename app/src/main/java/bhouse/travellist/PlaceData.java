package bhouse.travellist;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by megha on 15-03-06.
 */
public class PlaceData {

    public static String[] placeNameArray = {"Bora Bora", "Canada", "Dubai", "Hong Kong", "Iceland", "India", "Kenya", "London", "Switzerland", "Sydney","Blank"};

    public static ArrayList<Place> placeList() {
        ArrayList<Place> list = new ArrayList<>();
        for (int i=0; i<placeNameArray.length; i++) {
            Place place = new Place();
            place.name = placeNameArray[i];
            place.imageName = placeNameArray[i].replaceAll("\\s+","").toLowerCase();
            if (i==2 || i==5 || i==placeNameArray.length-1) {
                place.isFav = true;
            }
            if (i!=placeNameArray.length-1) {
                place.type = 0;
            } else {
                place.type = 1;
            }
            list.add(place);
        }
        return(list);
    }

    public static Place getItem(String _id) {
        for (Place place : placeList()) {
            if (place.id.equals(_id)) {
                return place;
            }
        }
        return null;
    }
}
