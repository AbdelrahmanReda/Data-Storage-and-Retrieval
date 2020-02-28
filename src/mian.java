import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.RandomAccess;

public class mian {
    public static void cosequentialProcessing(ArrayList<Integer> arrayListOne, ArrayList<Integer> arrayListTwo) {
        ArrayList<Integer> result = new ArrayList<>();
        int a1_counter = 0;
        int a2_counter = 0;
        while (a1_counter != arrayListOne.size() && a2_counter != arrayListTwo.size()) {
            while (a2_counter != arrayListTwo.size()) {
                if (arrayListOne.get(a1_counter) < arrayListTwo.get(a2_counter)) {
                    System.out.println("a1 less than");
                    result.add(arrayListOne.get(a1_counter));
                    a1_counter++;
                    break;
                } else {
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
        while (a1_counter != arrayListOne.size()) {
            result.add(arrayListOne.get(a1_counter));
            a1_counter++;
        }
        while (a2_counter != arrayListTwo.size()) {
            result.add(arrayListTwo.get(a2_counter));
            a2_counter++;
        }
    }
    static String[] DivideInputFileIntoRuns(String Inputfilename, int runSize) {
        try {
            RandomAccessFile file = new RandomAccessFile("../Index.bin", "r");
            System.out.println(file.length());
            for (int i = 0; i < file.length(); i += 512 / 8) {
                String fileName = "src/runs/run_number_" + (i / (512 / 8) + 0) + ".bin";
                RandomAccessFile runFile = new RandomAccessFile(fileName, "rw");
                for (int j = 0; j < 8; j++) {
                    runFile.writeInt(file.readInt());
                    runFile.writeInt(file.readInt());
                }
                System.out.println(fileName);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void readRunFile() {
        try {
            RandomAccessFile file = new RandomAccessFile("src/runs/run_number_0.bin", "rw");
            for (int i = 0; i < 8; i++) {
                System.out.print(file.readInt() + " ");
                System.out.println(file.readInt());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    String[] SortEachRunOnMemoryAndWriteItBack(String[] RunsFilesNames) {

        return null;
    }
    void DoKWayMergeAndWriteASortedFile(String[] SortedRunsNames, int K, String Sortedfilename) {
    }
    int BinarySearchOnSortedFile(String Sortedfilename, int RecordKey) {
        return 0;
    }
    public static void main(String args[]) throws IOException {


    }
}
