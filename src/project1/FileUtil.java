package project1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FileUtil {

//    public static class CreateFile {
        File file = new File("C:\\Users\\KOSA\\IdeaProjects\\project1\\src\\project1\\Item.txt");

        public void FileOut(){
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file, true);
                fileOutputStream.write(0);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//    }
    public static List<Item> readFile(File file) throws IOException {
    FileReader filereader = null;
    List<Item> itemList = new ArrayList<>();

    try{
        filereader = new FileReader(file);
        itemList = readReader(filereader);
    }finally{
        if(filereader != null)
            filereader.close();
    }
    return itemList;
    }

    public static void writeFile(File file, List<Item> ItemList) throws IOException {

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            for(Item item : ItemList){
                String writeStr = item.getName()+","+item.getPrice()+","+item.getQuantity();
                writer.write(writeStr);
                writer.newLine();
            }
        }finally{
            if(writer != null)
                writer.close();
        }
    }

    public static List<Item> readReader(Reader input) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(input);
            String line;

            List<Item> ItemList = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length != 3) continue;
                Item item = new Item(data[0], data[1], data[2]);

                ItemList.add(item);
            }
            return ItemList;
        } finally {
            input.close();
        }
    }
}
