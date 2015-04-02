package bhouse.travellist;

import java.util.ArrayList;

/**
 * Created by megha on 15-03-06.
 */
public class PlaceData {

  public static String[] placeNameArray = {"Bora Bora", "Canada", "Dubai", "Hong Kong", "Iceland", "India", "Kenya", "London", "Switzerland", "Sydney"};

  public static ArrayList<Place> placeList() {
    ArrayList<Place> list = new ArrayList<>();
    for (int i = 0; i < placeNameArray.length; i++) {
      Place place = new Place();
      place.name = placeNameArray[i];
      place.imageName = placeNameArray[i].replaceAll("\\s+", "").toLowerCase();
      if (i == 2 || i == 5) {
        place.isFav = true;
      }
      list.add(place);
    }
    return (list);
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
