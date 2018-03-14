package ManageFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecursiveMain {

    private static ArrayList<MyFile> allFiles = new ArrayList<>();
    private static ArrayList<MyFile> searchResult;

    private void listChild(File dir, int level) {
        MyFile file;

        if (dir.isDirectory()) {// dung neu la tap tin
            System.out.println(getPadding(level) + " - " + dir.getName() + ":");
            File[] children = dir.listFiles();
            for (File child : children) {
                this.listChild(child, level + 1); //goi de quy
            }
        } else {
            file = new MyFile();
            file.setFileName(dir.getName());
            file.setSizeFileName(dir.length());
            file.setFullPath(dir.getAbsolutePath());
            allFiles.add(file);
            System.out.println(getPadding(level) + " + " + dir.getName());

        }

    }

    private String getPadding(int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= level; i++) {
            sb.append("    ");
        }
        return sb.toString();
    }

    private void SelectionSort(ArrayList<MyFile> allFiles) {
        int minIndex;

        for (int i = 0; i < allFiles.size() - 1; i++) {
            minIndex = i;
            for (int j = i + 1; j < allFiles.size(); j++) {
                if (allFiles.get(j).getSizeFileName() < allFiles.get(minIndex).getSizeFileName()) {
                    minIndex = j;
                }
            }
            MyFile tmp = allFiles.get(i);
            allFiles.set(i, allFiles.get(minIndex));
            allFiles.set(minIndex, tmp);

        }

    }

    private void InsertionSort(ArrayList<MyFile> allFiles) {
        MyFile current;
        int post;
        for (int i = 0; i < allFiles.size(); i++) {
            current = allFiles.get(i);
            post = i - 1;
            while ((post >= 0) && (allFiles.get(post).getSizeFileName() > current.getSizeFileName())) {
                allFiles.set(post + 1, allFiles.get(post));
                post--;
            }
            allFiles.set(post + 1, current);

        }
    }

    public ArrayList<MyFile> linearSearch(ArrayList<MyFile> allFiles, String str) throws IOException {
        searchResult = new ArrayList<>();

        for (int i = 0; i < allFiles.size(); i++) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(allFiles.get(i).getFullPath()));
                String str1;
                while ((str1 = br.readLine()) != null) {
                    if (str.contains(str1)) {
                        searchResult.add(allFiles.get(i));
                        break;
                    }
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(RecursiveMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return searchResult;

    }

    public void displayArraylist(ArrayList<MyFile> allFiles) {
        System.out.println(String.format("%-10s\t%-5s", "Name", "Size"));
        for (int i = 0; i < allFiles.size(); i++) {
            System.out.println(String.format("%-10s\t%-5d", allFiles.get(i).getFileName(), allFiles.get(i).getSizeFileName()));

        }
    }

    public static void main(String[] args) throws IOException {
        RecursiveMain ex = new RecursiveMain();
        System.out.print("Please enter your directory: ");
        String dir = Utility.nhapString();
        File path = new File(dir);
        ex.listChild(path, 0);
        System.out.print("output: ");
        for (int i = 0; i < allFiles.size(); i++) {
            System.out.print(allFiles.get(i).getFileName() + " ");

        }
        System.out.println("");

        int option;
        do {
            System.out.println(" ___________________________MENU______________________");
            System.out.println("| 1. Insertion sort...................................| ");
            System.out.println("| 2. Selection sort...................................| ");
            System.out.println("| 3. Linear Search....................................| ");
            System.out.println("| 4. Exit.............................................| ");
            System.out.print("Please enter your option: ");
            option = Utility.nhapInt();
            System.out.println("");
            switch (option) {
                case 1:
                    ex.InsertionSort(allFiles);
                    ex.displayArraylist(allFiles);
                    break;
                case 2:
                    ex.SelectionSort(allFiles);
                    ex.displayArraylist(allFiles);
                    break;
                case 3:
                    System.out.println("Please enter a string to search");
                    String str = Utility.nhapString();
                    ex.linearSearch(allFiles, str);
                    System.out.println("The Files were found: ");
                    ex.displayArraylist(searchResult);

                    break;
                case 4:
                    System.out.println("----------SEE YOU AGAIN!----------");
                    break;

            }
        } while (option != 4);

    }

}
