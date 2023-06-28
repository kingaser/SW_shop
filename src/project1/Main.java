
package project1;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    static  Scanner sc = new Scanner((System.in));

    public static void main(String[] args) throws IOException {
        Itemcrud ic = new Itemcrud();
        String filepath = "C:\\Users\\KOSA\\IdeaProjects\\project1\\src\\project1";
        String filename = "Item.txt";
        boolean power = true;

        FileUtil fileUtil = new FileUtil();

//        fileUtil.new CreateFile().FileOut();
        File inputFile = new File(filepath,filename);
        List<Item> itemList = FileUtil.readFile(inputFile);

        while (power) {
            sc.reset();
            ic.choiceNumber();

            int num = sc.nextInt();

            if (num == 1) {
                ic.insert();
            } else if (num == 2) {
                ic.delete();
            } else if (num == 3) {
                ic.update();
            } else if (num == 4) {
                ic.search();
            } else if (num == 5) {
                ic.allview();
            } else if (num == 0) {
                return;
            }
        }
    }
}






