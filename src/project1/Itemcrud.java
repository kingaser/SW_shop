package project1;
import java.io.File;
import java.io.IOException;
import java.util.*;
public class Itemcrud{
    private Map<String, Item> itemMap;
    Scanner sc = new Scanner(System.in);
    String filepath = "C:\\Users\\KOSA\\IdeaProjects\\project1\\src\\project1";
    String filename = "Item.txt";
    boolean power = true;

    FileUtil fileUtil = new FileUtil();



    File inputFile = new File(filepath,filename);
    List<Item> itemList = FileUtil.readFile(inputFile);
    public Itemcrud() throws IOException {
        itemMap = new HashMap<>();
    }
    public void choiceNumber() throws IOException {

        while(true) {
            System.out.println("번호를 입력하세요");
            System.out.println("1. 입력");
            System.out.println("2. 삭제");
            System.out.println("3. 수정");
            System.out.println("4. 조회");
            System.out.println("5. 전체목록조회");
            System.out.println("0. 돌아가기");
            int num = sc.nextInt();
            if(num==1) {
                insert();
            }
            else if(num==2){
                delete();
            }
            else if(num==3) {
                update();
            }
            else if(num==4) {
                search();
            }
            else if(num==5) {
                allview();
            }
            else if (num==0) {
                List<Item> list = new ArrayList<>();
                Iterator<Item> it = itemMap.values().iterator();
                while (it.hasNext()) {
                    itemList.add(it.next());
                }
                FileUtil.writeFile(fileUtil.file, itemList);
                break;
            }
        }
    }


    public void insert() {
        System.out.println("새롭게 저장할 상품명를 입력하세요");
        System.out.print("이름 = ");
        String name = sc.next();
        if(itemMap.get(name) != null) {
            System.out.println(name+"이미 등록된 상품명입니다.");
            return;
        }

        System.out.print("가격 = ");
        String price = sc.next();
        System.out.print("재고수량 = ");
        String quantity = sc.next();
        itemMap.put(name, new Item(name, price,quantity));
        System.out.println(name + " 상품 등록완료");
        return;
    }
    public void delete() {
        System.out.println("삭제할 ID를 입력하세요.");
        System.out.print("이름 = ");
        String name = sc.next();
        if(itemMap.remove(name)==null) {
            System.out.println(name+"는 등록된 상품명이 아닙니다.");
        }
        else {
            System.out.println(name+"의 상품을 삭제했습니다.");
        }
        System.out.println("삭제완료");
    }
    public void update() {
        System.out.println("수정하실 상품명를 입력하세요.");
        System.out.print("이름 = ");
        String name = sc.next();
        if(itemMap.get(name)==null) {
            System.out.println("수정하실 상품"+name+"정보가 없습니다.");
            return;
        }
        System.out.print("이름 = ");
        String name1 = sc.next();
        System.out.print("가격 = ");
        String price = sc.next();
        System.out.print("재고수량 = ");
        String quantity = sc.next();

        Item i = new Item(name1,price,quantity);
        itemMap.put(name,  i);
        System.out.println("수정완료");
    }
    public void search() {
        System.out.println("검색할 상품 이름을 입력하세요");
        System.out.print("상품명 = ");
        String name = sc.next();



        Item i = itemMap.get(name);

        if(i==null) {
            System.out.println(name+"의 정보가 없습니다.");
        }
        else {
            System.out.println(name+"의 상품 정보");
            System.out.println("가격 = "+i.getPrice());
            System.out.println("재고수량 = "+i.getQuantity());
        }
    }
    public void allview() {
        Set<String> keySet = itemMap.keySet();

        System.out.println("이름"+"     "+"가격"+"     "+"재고수량\n");
        if(keySet.size()==0) {
            System.out.println("등록된 상품 정보가 없습니다.");
        }
        else {
            Iterator<String> it = keySet.iterator();


            while(it.hasNext()) {

                String name = it.next();
                Item item = itemMap.get(name);

                System.out.println(item.getName()+"\t"+item.getPrice()+"\t"+item.getQuantity());

            }
        }

    }

}