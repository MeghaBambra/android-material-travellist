package bhouse.travellist;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by megha on 15-03-06.
 */
public class PlaceData {

    private static String[] placeNameArray = {"Bora Bora", "Canada", "Dubai", "Hong Kong", "Iceland", "India", "Kenya", "London", "Switzerland", "Sydney"};

    public static ArrayList<Place> placeList() {
        ArrayList<Place> list = new ArrayList<>();

        for (String name: placeNameArray) {
            Place place = new Place();
            place.name = name;
            place.imageName = name.replaceAll("\\s+","").toLowerCase();
            place.todo = new ArrayList<>();

            list.add(place);
        }
        return(list);
    }

    public void addToDo(String _placeId, Todo _todo) {
        Place item = getItem(_placeId);
        item.todo.add(_todo);
    }

    public void removeToDo(String _placeId, Todo _todo) {
        Place item = getItem(_placeId);

        for (Todo t : item.todo) {
            if (t.toDoId.equals(_todo.toDoId)) {
                item.todo.remove(_todo);
            }
        }
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
