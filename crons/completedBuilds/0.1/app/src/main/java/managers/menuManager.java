package managers;

import java.util.ArrayList;

import DAO.menuItem;

public final class menuManager {
    private static ArrayList<menuItem> menuEntries;

    public static ArrayList<menuItem> getMenuEntries() {
        return menuEntries;
    }

    public static void setMenuEntries(ArrayList<menuItem> menuEntriesnew) {
        menuEntries = menuEntriesnew;
    }

    public static ArrayList<menuItem> getCategory(String categoryTitle){
        ArrayList<menuItem> categoryEntries = new ArrayList<>();
        for(int i=0; i< menuEntries.size(); i++){
            if((menuEntries.get(i).getType()).equals(categoryTitle)){
                categoryEntries.add(menuEntries.get(i));
            }
        }
        return categoryEntries;
    }


}
