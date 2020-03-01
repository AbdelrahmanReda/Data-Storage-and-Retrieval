import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;

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
    public static void readRunFile(String path,int k) {
        try {
            RandomAccessFile file = new RandomAccessFile(path, "rw");
            for (int i = 0; i < k*8; i++) {
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
    
    public static boolean terminatable(ArrayList<Integer> ptrs) {
        if (Collections.frequency(ptrs, -1) == ptrs.size()) {
            return true;
        } else {
            return false;
        }
    }
    static int getMinimumWithException(ArrayList<Integer> taken) {
        ArrayList<Integer> arr = new ArrayList(taken);
        Collections.sort(arr);
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) != -1) return arr.get(i);
        }
        return 0;
    }
    public static int[] getKey(RandomAccessFile file, int ptr) {
        int[] record = new int[2];
        try {
            file.seek(0); //
            file.seek(ptr * 8);
            record[0] = file.readInt();
            record[1] = file.readInt();
            return record;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static int getFileLength(RandomAccessFile file) {
        try {
            Long len = file.length();
            return len.intValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public static void kways(String[] fileNames, int kways,String fullFileName) {
        try {
            RandomAccessFile fullFile = new RandomAccessFile(fullFileName,"rw");
            ArrayList<RandomAccessFile> files = new ArrayList<>();
            for (int i = 0; i < kways; i++) {
                RandomAccessFile file = new RandomAccessFile(fileNames[i], "rw");
                files.add(file);
            }
            ArrayList<Integer> ptrs = new ArrayList<>();
            ArrayList<Integer> results = new ArrayList<>();
            ArrayList<Integer> resultsTwo = new ArrayList<>();
            ArrayList<Integer> taken = new ArrayList<>();
            ArrayList<Integer> byteOffset = new ArrayList<>();
            //make pointer equal to number of k ways
            for (int i = 0; i < kways; i++) {
                Integer pointer = 0;
                ptrs.add(pointer);
            }
            while (!terminatable(ptrs)) {
                for (int j = 0; j < ptrs.size(); j++) {
                    if (ptrs.get(j) == -1) {
                        taken.add(-1);
                        byteOffset.add(-1);
                        
                    } else {
                        taken.add(getKey(files.get(j), ptrs.get(j))[0]);
                        byteOffset.add(getKey(files.get(j), ptrs.get(j))[1]);
                    }
                }
                fullFile.writeInt(getMinimumWithException(taken));
                results.add(getMinimumWithException(taken));
                int minIndex = taken.indexOf(getMinimumWithException(taken));
                fullFile.writeInt(byteOffset.get(minIndex));
                resultsTwo.add(byteOffset.get(minIndex));
                int x = getFileLength(files.get(minIndex)) / 8;
                if (ptrs.get(minIndex) + 1 > x - 1) //if the pointer value exceed the interval of the run
                {
                    ptrs.set(minIndex, -1);
                } else {
                    ptrs.set(minIndex, ptrs.get(minIndex) + 1);
                }
                taken.clear();
                byteOffset.clear();
            }
            fullFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static  void mergerHelper(String []filesName,  int _Kways,String dist_file ){
       
        ArrayList<String> queredFiles = new ArrayList<>();
        for (int i = 0; i <filesName.length; i++) {
            System.out.println("i is"+i);
            if ((i)%_Kways==0 && i != 0 )
            {
                System.out.println("qureid is "+queredFiles);
                String[] array = new String[queredFiles.size()];
                for (int j = 0; j <queredFiles.size() ; j++) {
                    array[j]=queredFiles.get(j);
                }
                
                
                
            }
            else
            {
                queredFiles.add(filesName[i]);
    
                //implement k way merge with queried Files
            }
         
        
        }
        
    }
    int BinarySearchOnSortedFile(String Sortedfilename, int RecordKey) {
        return 0;
    }
    public static void main(String args[]) throws IOException {
        String[] filesName = {"C:\\Users\\boody\\IdeaProjects\\k-way-merge\\src\\runcpy\\run_number_0.bin",
                "C:\\Users\\boody\\IdeaProjects\\k-way-merge\\src\\runcpy\\run_number_1.bin",
                "C:\\Users\\boody\\IdeaProjects\\k-way-merge\\src\\runcpy\\run_number_2.bin",
                "C:\\Users\\boody\\IdeaProjects\\k-way-merge\\src\\runcpy\\run_number_3.bin",
                "C:\\Users\\boody\\IdeaProjects\\k-way-merge\\src\\runcpy\\run_number_4.bin",
                "C:\\Users\\boody\\IdeaProjects\\k-way-merge\\src\\runcpy\\run_number_5.bin",
                "C:\\Users\\boody\\IdeaProjects\\k-way-merge\\src\\runcpy\\run_number_6.bin",
                "C:\\Users\\boody\\IdeaProjects\\k-way-merge\\src\\runcpy\\run_number_7.bin"
        };
        int  k = 3 ;
        String distname = "C:\\\\Users\\\\boody\\\\IdeaProjects\\\\k-way-merge\\\\src\\\\runcpy\\\\full.bin\"" ;
        kways(filesName, k,"C:\\Users\\boody\\IdeaProjects\\k-way-merge\\src\\runcpy\\full.bin");
//        readRunFile("C:\\Users\\boody\\IdeaProjects\\k-way-merge\\src\\runcpy\\full.bin",k);
        mergerHelper(filesName,k,distname);
    
    
    }
}
