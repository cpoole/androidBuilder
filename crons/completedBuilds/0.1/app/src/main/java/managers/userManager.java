package managers;

public final class userManager {
    private static int numPunches;
    private static String guid;

    private userManager(){
        numPunches =0;
    }
    public static void addPunch(){
        numPunches ++;
    }
    public static int getNumPunches(){
        return numPunches;
    }

    public static void setNumPunches(int num){
        numPunches = num;
    }
}