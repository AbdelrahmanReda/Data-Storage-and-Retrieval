import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class mian
 {
    
    
static ArrayList<Integer>  coseqentialProcessing(ArrayList<Integer> runLeft , ArrayList<Integer> runRight){
    ArrayList<Integer> fullRun = new ArrayList<>();
       int counter_left = 0;
       int counter_right=0;
        for (int j = 0; j <runLeft.size()+runRight.size()-1 ; j++) {
            System.out.println(counter_left+" "+counter_right);
            if (runLeft.get(counter_left)<runRight.get(counter_right))
            {
                System.out.println("right is greater");
                fullRun.add(runLeft.get(counter_left));
                if ((counter_left+1) != runLeft.size())
                counter_left++;
            }
            else
            {
                System.out.println("left is greater");
                fullRun.add(runRight.get(counter_right));
                if ((counter_right+1) != runRight.size())
                counter_right++;
            }
        }
    
    System.out.println(fullRun);
    
    return null;
    }
    
    public static void main (String args []){
      
        ArrayList<Integer> runLeft = new ArrayList<>(Arrays.asList(1,3,5));
        ArrayList<Integer> runRight = new ArrayList<>(Arrays.asList(2));
        coseqentialProcessing(runLeft,runRight);
    
    
    
    
    }
}
