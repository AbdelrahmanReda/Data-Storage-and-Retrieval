import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

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
            RandomAccessFile file = new RandomAccessFile("C:\\Users\\boody\\Desktop\\New folder (2)\\src\\Index.bin", "r");
            String[] runsName = new String[512 / (runSize * 8)];
            System.out.println(file.length());
            for (int i = 0; i < file.length(); i += 512 / 8) {
                String fileName = "src/runs/run_number_" + (i / (512 / 8) + 0) + ".bin";
                runsName[i / (512 / 8)] = fileName;
                RandomAccessFile runFile = new RandomAccessFile(fileName, "rw");
                for (int j = 0; j < 8; j++) {
                    runFile.writeInt(file.readInt());
                    runFile.writeInt(file.readInt());
                }

            }

            return runsName;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;


    }
    public static void readRunFile(String path) {

        try {
            RandomAccessFile file = new RandomAccessFile(path, "rw");
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
    public static ArrayList<Pair> sort(ArrayList<Pair> obj) {
        for (int i = 0; i < obj.size(); i++) {
            for (int j = i + 1; j < obj.size(); j++) {
                if (obj.get(i).key > obj.get(j).key) {
                    int tempKey = obj.get(i).key;
                    obj.get(i).key = obj.get(j).key;
                    obj.get(j).key = tempKey;
                    
                    int tepOffset = obj.get(i).byteOffset;
                    obj.get(i).byteOffset =  obj.get(j).byteOffset;
                    obj.get(j).byteOffset= tepOffset ;
                }
            }
        }
        return obj;
    }
    static String[] SortEachRunOnMemoryAndWriteItBack(String[] RunsFilesNames) {
        for (int i = 0; i < RunsFilesNames.length; i++) {
            ArrayList<Pair> run = new ArrayList<>();
            try {
                RandomAccessFile file = new RandomAccessFile(RunsFilesNames[i], "rw");
                for (int j = 0; j < file.length() / 8; j++) {
                    Pair pair = new Pair();
                    pair.key = file.readInt();
                    pair.byteOffset = file.readInt();
                    run.add(pair);
                }
                for (int j = 0; j <run.size() ; j++) {
                    System.out.println(run.get(j).key+" *** "+run.get(j).byteOffset);
                }
                run = sort(run);
                file.seek(0);
                for (int ii = 0; ii < run.size(); ii++) {

                    file.writeInt(run.get(ii).key);
                    file.writeInt(run.get(ii).byteOffset);
                }
                file.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    void DoKWayMergeAndWriteASortedFile(String[] SortedRunsNames, int K, String Sortedfilename) {
    }
    int BinarySearchOnSortedFile(String Sortedfilename, int RecordKey) {
        return 0;
    }
    public static void main(String args[]) throws IOException {
        String[] Runs = DivideInputFileIntoRuns("m", 8);
        SortEachRunOnMemoryAndWriteItBack(Runs);
        readRunFile("C:\\Users\\boody\\Desktop\\New folder (2)\\src\\runs\\run_number_0.bin");

    }
}
