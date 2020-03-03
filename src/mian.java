import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;

public class mian {
    static String[] DivideInputFileIntoRuns(String Inputfilename, int runSize) {
        try {
            RandomAccessFile file = new RandomAccessFile(Inputfilename, "r");
            String[] runsName = new String[(int) Math.ceil(file.length() / (double) (runSize * 8))];
            for (int i = 0; i < runsName.length; i++) {
                String fileName = "src/runs/run_number_" + i + ".bin";
                runsName[i] = fileName;
                RandomAccessFile runFile = new RandomAccessFile(fileName, "rw");
                for (int j = 0; j < runSize; j++) {
                    if (file.getFilePointer() == file.length())
                        break;
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
            for (int i = 0; i < file.length() / 8; i++) {
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
        /*Internal Sorting function to sort each run*/
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
                run = sort(run);
                for (int j = 0; j < run.size(); j++) {
                    System.out.println(run.get(j).key + " *** " + run.get(j).byteOffset);
                }
                System.out.println("------------------------------------------");
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
        return RunsFilesNames;
    }

    public static boolean terminatable(ArrayList<Integer> runPtr) {
        if (Collections.frequency(runPtr, -1) == runPtr.size())
            return true;
        else
            return false;
    }

    static int getMinimumWithException(ArrayList<Integer> taken) {
        ArrayList<Integer> arr = new ArrayList(taken);
        Collections.sort(arr);
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) != -1) return arr.get(i);
        }
        return 0;
    }

    public static int[] getKeyAndByteOffset(RandomAccessFile file, int ptr) {
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

    private static void replacefile(String sortedFileNameCpy, String sortedFileName) {
        File sortedFileNameCpyFile = new File(sortedFileNameCpy);
        sortedFileNameCpyFile.delete(); // delete the original full.bin file//
        File myfile = new File(sortedFileName); // control the masked file //
        myfile.renameTo(sortedFileNameCpyFile); // renaming the mask file to sortedFileNameCpy
    }

    public static void kways(String[] fileNames, int kways, String sortedFileName) {
        String sortedFileNameCpy = sortedFileName;  /*keep a copy of file name */
        try {
            sortedFileName = makeTempFile(sortedFileName); /*mask file name with 'cpy.bin'*/
            RandomAccessFile fullFile = new RandomAccessFile(sortedFileName, "rw"); /*make random access file with the masked name*/
            ArrayList<RandomAccessFile> files = new ArrayList<>();
            /*open k run files from file names array and store it into arraylist*/
            for (int i = 0; i < kways; i++) {
                RandomAccessFile file = new RandomAccessFile(fileNames[i], "rw");
                files.add(file);
            }
            ArrayList<Integer> runPtr = new ArrayList<>(); //make run pointers array list
            ArrayList<Integer> taken = new ArrayList<>();
            ArrayList<Integer> byteOffset = new ArrayList<>();
            //make pointer equal to number of k ways
            for (int i = 0; i < kways; i++) {
                Integer pointer = 0;
                runPtr.add(pointer);
            }
            while (!terminatable(runPtr)) {
                for (int j = 0; j < runPtr.size(); j++) {
                    if (runPtr.get(j) == -1) {
                        taken.add(-1);
                        byteOffset.add(-1);
                    } else {
                        taken.add(getKeyAndByteOffset(files.get(j), runPtr.get(j))[0]);
                        byteOffset.add(getKeyAndByteOffset(files.get(j), runPtr.get(j))[1]);
                    }
                }
                fullFile.writeInt(getMinimumWithException(taken));
                int minIndex = taken.indexOf(getMinimumWithException(taken));
                fullFile.writeInt(byteOffset.get(minIndex));
                int x = getFileRecords(files.get(minIndex)) / 8;
                if (runPtr.get(minIndex) + 1 > x - 1) //if the pointer value exceed the interval of the run
                    runPtr.set(minIndex, -1);
                else
                    runPtr.set(minIndex, runPtr.get(minIndex) + 1);
                taken.clear();
                byteOffset.clear();
            }
            /*very important to delete the full.bin file*/
            for (int i = 0; i < files.size(); i++)
                files.get(i).close(); //close all the opened files
            fullFile.close(); //close the masked file
            replacefile(sortedFileNameCpy, sortedFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    static int getkey(int index, RandomAccessFile file) {
        try {
            file.seek(index * 8);
            return file.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    static int BinarySearchOnSortedFile(String Sortedfilename, int l, int r, int RecordKey) {
        try {
            RandomAccessFile file = new RandomAccessFile(Sortedfilename, "rw");
            if (r >= l) {
                int mid = l + (r - l) / 2;

                // If the element is present at the
                // middle itself
                if (getkey(mid, file) == RecordKey)
                    return mid;

                // If element is smaller than mid, then
                // it can only be present in left subarray
                if (getkey(mid, file) > RecordKey)
                    return BinarySearchOnSortedFile(Sortedfilename, l, mid - 1, RecordKey);

                // Else the element can only be present
                // in right subarray
                return BinarySearchOnSortedFile(Sortedfilename, mid + 1, r, RecordKey);
            }

            // We reach here when element is not present
            // in array
            return -1;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    static void binartSearchHelper(String fileName, int recordKey) {
        try {
            RandomAccessFile file = new RandomAccessFile(fileName, "rw");
            int r = (int) (file.length() / 8) - 1; /*r=7*/
            int l = 0;
            int result = BinarySearchOnSortedFile(fileName, l, r, recordKey);
            if (result != -1) {
                file.seek(result * 8);
                System.out.println(file.readInt() + " is founded with key reference " + file.readInt());
            } else
                System.out.println(recordKey + " couldn't founded !");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static long getFileRecords(String path) {
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile(path, "rw");
            return file.length() / 8;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String args[]) throws IOException {
        mergerHelper(SortEachRunOnMemoryAndWriteItBack(
                DivideInputFileIntoRuns("src/runs/Index.bin", 2)),
                7,
                "src/runs/full.bin");
        readRunFile("src/runs/full.bin");

    }
}