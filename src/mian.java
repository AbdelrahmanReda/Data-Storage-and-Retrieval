import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class mian
 {
     public static void cosequentialProcessing(ArrayList<Integer> arrayListOne ,ArrayList<Integer> arrayListTwo  ){
         ArrayList <Integer> result = new ArrayList<>();
         int a1_counter = 0;
         int a2_counter = 0 ;
         while (a1_counter!=arrayListOne.size() && a2_counter!=arrayListTwo.size() )
         {
             while (a2_counter!=arrayListTwo.size())
             {
                 if (arrayListOne.get(a1_counter)<arrayListTwo.get(a2_counter))
                 {
                     System.out.println("a1 less than");
                     result.add(arrayListOne.get(a1_counter));
                     a1_counter++;
                     break;
                 }
                 else
                 {
                     System.out.println("a2 less than");
                     result.add(arrayListTwo.get(a2_counter));
                     a2_counter++;
                 }
             }
             try {
                 Thread.sleep(300);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
         System.out.println("aoutded");
         while (a1_counter!=arrayListOne.size())
         {
             result.add(arrayListOne.get(a1_counter));
             a1_counter++;
         }
         while (a2_counter!=arrayListTwo.size())
         {
             result.add(arrayListTwo.get(a2_counter));
             a2_counter++;
         }
         System.out.println("resultis"+result);
     }
    
    public static void main (String args []){
      
        ArrayList<Integer> runLeft = new ArrayList<>(Arrays.asList(1,3,5));
        ArrayList<Integer> runRight = new ArrayList<>(Arrays.asList(2));
        cosequentialProcessing(runLeft,runRight);
    
    
    
    
    }
}
