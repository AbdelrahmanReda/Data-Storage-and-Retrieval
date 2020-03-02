import java.io.File;
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
                Thread.sleep(0);
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
    
    public static void readRunFile(String path, int k) {
        try {
            RandomAccessFile file = new RandomAccessFile(path, "rw");
            for (int i = 0; i < k * 8; i++) {
                System.out.print(i + " >> ");
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
                    obj.get(i).byteOffset = obj.get(j).byteOffset;
                    obj.get(j).byteOffset = tepOffset;
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
                for (int j = 0; j < run.size(); j++) {
                    System.out.println(run.get(j).key + " *** " + run.get(j).byteOffset);
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
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(ptrs);
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
    
    public static int getFileRecords(RandomAccessFile file) {
        try {
            Long len = file.length();
            return len.intValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public static String makeTempFile(String str) {
        String newString = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            if (str.charAt(i) == '.')//we detect a dot
            {
                int filter = str.length() - ((str.length() - i));
                newString = str.substring(0, filter);
                newString += "cpy";
                newString += str.substring(filter);
                break;
            }
        }
        return newString;
    }
    
    private static void replacefile(String path, String orifginal) {
        
        
        File file = new File(path);
        File originalfile = new File(orifginal);
        if (file.renameTo(originalfile)) {
            System.out.println("renamed copy to original ");
        } else {
            System.out.println("not renamed ");
        }


//        if(originalfile.delete()) System.out.println("file deleted");
//        else System.out.println("not deleted");
//        file.renameTo(originalfile);
    }
    
    public static void kways(String[] fileNames, int kways, String fullFileName) {
    
        String original = fullFileName;
        try {
            fullFileName = makeTempFile(fullFileName); //containing mask file
            RandomAccessFile fullFile = new RandomAccessFile(fullFileName, "rw");
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
                int x = getFileRecords(files.get(minIndex)) / 8;
                if (ptrs.get(minIndex) + 1 > x - 1) //if the pointer value exceed the interval of the run
                {
                    ptrs.set(minIndex, -1);
                } else {
                    ptrs.set(minIndex, ptrs.get(minIndex) + 1);
                }
                taken.clear();
                byteOffset.clear();
            }
            for (int i = 0; i < files.size(); i++) {
                files.get(i).close();
            }
            
            fullFile.close();
            File originalFile = new File(original);
            originalFile.delete();
            
            File myfile = new File("C:\\Users\\boody\\IdeaProjects\\k-way-merge\\src\\runcpy\\fullcpy.bin");
            myfile.renameTo(originalFile); // renaming the mask to original
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    static void automatedTest() {
        
        String[] p1 = {" C:\\Users\\boody\\IdeaProjects\\k-way-merge\\src\\runcpy\\run_number_0.bin", " C:\\Users\\boody\\IdeaProjects\\k-way-merge\\src\\runcpy\\run_number_1.bin", " C:\\Users\\boody\\IdeaProjects\\k-way-merge\\src\\runcpy\\run_number_2.bin"};
        
        String[] p2 = {" C:\\Users\\boody\\IdeaProjects\\k-way-merge\\src\\runcpy\\full.bin", " C:\\Users\\boody\\IdeaProjects\\k-way-merge\\src\\runcpy\\run_number_3.bin", " C:\\Users\\boody\\IdeaProjects\\k-way-merge\\src\\runcpy\\run_number_4.bin"};
        
        String[] p3 = {" C:\\Users\\boody\\IdeaProjects\\k-way-merge\\src\\runcpy\\full.bin", " C:\\Users\\boody\\IdeaProjects\\k-way-merge\\src\\runcpy\\run_number_5.bin", " C:\\Users\\boody\\IdeaProjects\\k-way-merge\\src\\runcpy\\run_number_6.bin"};
        
        kways(p1, 3, "C:\\Users\\boody\\IdeaProjects\\k-way-merge\\src\\runcpy\\full.bin");
        kways(p2, 3, "C:\\Users\\boody\\IdeaProjects\\k-way-merge\\src\\runcpy\\full.bin");
        //  kways(p3,3,"C:\\Users\\boody\\IdeaProjects\\k-way-merge\\src\\runcpy\\full.bin");
        
    }
    
    static void mergerHelper(String[] filesName, int _Kways, String dist_file) {
        ArrayList<String> choosenFiles = new ArrayList<>();  //k=3
        for (int i = 0; i < filesName.length; i++) {
            if ((choosenFiles.size()) % _Kways != 0 || choosenFiles.size() == 0) {
                choosenFiles.add(filesName[i]); //populate chosen files which the chosen files //
            } else {
                String[] queuredFiles = new String[choosenFiles.size()];
                for (int j = 0; j < choosenFiles.size(); j++) {
                    queuredFiles[j] = choosenFiles.get(j);
                }
                for (int j = 0; j < queuredFiles.length; j++) {
                    System.out.println(">>" + queuredFiles[j]);
                }
                kways(queuredFiles, queuredFiles.length, dist_file);
                choosenFiles.clear();
                choosenFiles.add(dist_file);
                choosenFiles.add(filesName[i]);
            }
        }
        String[] queuredFiles = new String[choosenFiles.size()];
    
        for (int j = 0; j < choosenFiles.size(); j++) {
            queuredFiles[j] = choosenFiles.get(j);
        }
        kways(queuredFiles, queuredFiles.length, dist_file);
        
        
    }
    
    int BinarySearchOnSortedFile(String Sortedfilename, int RecordKey) {
        return 0;
    }
    
    static long getFileRecords(String path) {
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile(path, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            return file.length() / 8;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
        
    }
    
    public static void main(String args[]) throws IOException {
        String[] filesName = {"C:\\Users\\boody\\IdeaProjects\\k-way-merge\\src\\runcpy\\run_number_0.bin", "C:\\Users\\boody\\IdeaProjects\\k-way-merge\\src\\runcpy\\run_number_1.bin", "C:\\Users\\boody\\IdeaProjects\\k-way-merge\\src\\runcpy\\run_number_2.bin", "C:\\Users\\boody\\IdeaProjects\\k-way-merge\\src\\runcpy\\run_number_3.bin", "C:\\Users\\boody\\IdeaProjects\\k-way-merge\\src\\runcpy\\run_number_4.bin", "C:\\Users\\boody\\IdeaProjects\\k-way-merge\\src\\runcpy\\run_number_5.bin", "C:\\Users\\boody\\IdeaProjects\\k-way-merge\\src\\runcpy\\run_number_6.bin", "C:\\Users\\boody\\IdeaProjects\\k-way-merge\\src\\runcpy\\run_number_7.bin"
        
        };
        int k = 3;
        String distname = "C:\\Users\\boody\\IdeaProjects\\k-way-merge\\src\\runcpy\\full.bin";
        mergerHelper(filesName, k, distname);
        readRunFile(distname, 8);
        
        
    }
    
}
