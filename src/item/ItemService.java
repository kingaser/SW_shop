package item;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ItemService {
    int num = 0;

    Scanner kb = new Scanner(System.in);
    File file = new File("C:\\Users\\gram15\\Desktop\\SWedu\\shop\\shop\\database\\itemList.txt");
    BufferedWriter itemListWriter;

    BufferedReader itemListReader = new BufferedReader(
            new FileReader(file, UTF_8));


    protected LinkedHashMap<Integer, Item> itemList = new LinkedHashMap<>();

    // 클래스 객체 생성 시 상품 목록에 입력, 상품 DB파일 없을시 파일 생성
    public ItemService() throws IOException {
        readItemInputCheck();
        try {
            itemListWriter = new BufferedWriter(
                    new FileWriter(file, true)
            );
            itemListWriter.write("");
            itemListWriter.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    //상품관리에서 상품관리 메뉴 번호 선정
    public void choiceNumber() {

        while (true) {
            System.out.println("0. 이전 메뉴(진행 내용 저장)\n1. 상품 등록\n2. 상품 등록 삭제\n3. 상품 등록 수정\n" +
                    "4. 상품 상세 조회\n5. 상품 전체 조회");
            int itemMenu = kb.nextInt();
            if (itemMenu == 1) {
                addItem();
            } else if (itemMenu == 2) {
                deleteItem();
            } else if (itemMenu == 3) {
                updateItem();
            } else if (itemMenu == 4) {
                findItem();
            } else if (itemMenu == 5) {
                findAllItem();
            } else if (itemMenu == 0) {
                saveItemFile();
                break;
            }
        }
    }

    // 상품 구매 로직
    public void addItem() {
        System.out.println("상품명을 입력하세요.");
        String itemName = kb.next();
        // 상품리스트에 저장된 이름과 입력된 이름과 일치하면 이미 등록된 상품 출력
        Iterator<Item> itemIterator = itemList.values().iterator();
        while (itemIterator.hasNext()) {
            Item item = itemIterator.next();
            if (item.getItemName().equals(itemName)) {
                System.out.println(itemName + "은(는) 이미 등록된 상품명 입니다.");
                return;
            }
        }


        System.out.println();
        System.out.print("가격 = ");
        int itemPrice = kb.nextInt();
        System.out.print("재고수량 = ");
        int quantity = kb.nextInt();
        // num = 고유번호 id, 상품이 입력될때마다 고유번호가 +1씩 증가
        num++;
        // 입력받은 정보로 상품 객체 만듬
        Item item = new Item(num, itemName, itemPrice, quantity);
        System.out.println(itemName + " 상품 등록완료!!");

        // 아이템 정보를 상품 Map에 저장
        itemList.put(item.getId(), item);
        StringBuilder sb = new StringBuilder();
        // DB 저장시 사용될 포맷
        sb.append(item.getId()).append(" ")
                .append(item.getItemName()).append(" ")
                .append(item.getItemPrice()).append("원 ")
                .append(item.getQuantity()).append("개 ").append("\n");
    }

    // 상품 조회
    public void findItem() {
        System.out.println("조회할 상품명를 입력하세요.");
        String itemName = kb.next();
        Item findItem = null;
        for (int itemId : itemList.keySet()) {
            findItem = itemList.get(itemId);
            // 입력받은 상품명과 같은 이름의 상품이 있으면 출력
            if (findItem.getItemName().equals(itemName)) {
                System.out.println(itemName + "의 상품 정보");
                System.out.println("고유번호 = " + findItem.getId());
                System.out.println("이름 = " + findItem.getItemName());
                System.out.println("가격 = " + findItem.getItemPrice()+"원 ");
                System.out.println("재고수량 = " + findItem.getQuantity()+"개 ");
            }
        }

        //상품정보리스트에 상품정보가 없으면 출력
        if (findItem == null) {
            System.out.println(itemName + "의 정보가 없습니다.");
        }
    }

    //상품 전체 조회
    public void findAllItem() {
        Iterator<Item> itemIterator = itemList.values().iterator();
        // 등록된 상품이 없을 시
        if (!itemIterator.hasNext()) {
            System.out.println("등록된 상품이 없습니다.");
        // 등록된 상품이 있을 시
        } else {
            System.out.println("전체 상품 목록");
            // 전체 상품 포멧에 맞춰 출력
            while (itemIterator.hasNext()) {
                Item item = itemIterator.next();
                System.out.println(item.getId() + " " + item.getItemName() + " " + item.getItemPrice() + "원 " + item.getQuantity()+"개 ");
            }
        }
    }

    // 상품 수정
    public void updateItem() {
        System.out.println("수정하실 상품명를 입력하세요.");
        String itemName = kb.next();

        int id = 0;
        String updateItemName;
        int updateItemPrice;
        int updateQuantity;

        Iterator<Item> itemIterator = itemList.values().iterator();
        // 입력한 상품명과 일치하는 상품이 없을 떄 예외 메시지 출려 후 종료
        if (!itemIterator.hasNext()) {
            System.out.println("등록된 상품이 없습니다.");
            return;
        }
        while (itemIterator.hasNext()) {
            Item item = itemIterator.next();
            // 일치하는 상품이 있을 시
            if (item.getItemName().equals(itemName)) {
                id = item.getId();
                System.out.print("이름 = ");
                updateItemName = kb.next();
                System.out.print("가격 = ");
                updateItemPrice = kb.nextInt();
                System.out.print("재고수량 = ");
                updateQuantity = kb.nextInt();
            } else {
                System.out.println("수정하실 상품 " + itemName + "의 정보가 없습니다.");
                return;
            }

            // 수정사항으로 입력받은 정보로 상품 객체 생성 후 저장
            Item updateItem = new Item(id, updateItemName, updateItemPrice, updateQuantity);
            itemList.put(updateItem.getId(), updateItem);
            System.out.println("수정완료!!");

            StringBuilder sb = new StringBuilder();
            printItem(sb);
        }
    }

    // 상품 삭제
    public void deleteItem() {
        System.out.println("삭제할 상품명을 입력하세요.");
        String itemName = kb.next();
        boolean itemFound = false;
        Iterator<Item> itemIterator = itemList.values().iterator();
        // 상품 목록에 등록된 상품명과 입력된 상품명이 같은지 확인
        while (itemIterator.hasNext()) {
            Item item = itemIterator.next();
            if (item.getItemName().equals(itemName)) {
                // 상품명과 같은 상품 찾을 시 상품 제거
                itemList.remove(item.getId());
                itemFound = true;
                System.out.println(itemName + " 의 상품을 삭제했습니다.");
                break;
            }
        }

        // 상품명과 일치하는 상품이 없을 시
        if(!itemFound) {
            System.out.println(itemName + " 는 등록된 상품명이 아닙니다.");
            return;
        }


        StringBuilder sb = new StringBuilder();
        printItem(sb);
    }

    // DB에 저장된 상품 목록 Map에 저장
    private void readItemInputCheck() {
        try {

            String itemInfo;
            while ((itemInfo = itemListReader.readLine()) != null) {
                String[] tmp = itemInfo.split(" ");
                // Item 엔티티의 형식에 맞춰 Item 객체 생성
                int id = Integer.parseInt(tmp[0]);
                String itemName = tmp[1];
                int itemPrice = Integer.parseInt(tmp[2]);
                int quantity = Integer.parseInt(tmp[3]);
                num = id + 1;
                Item item = new Item(id, itemName, itemPrice, quantity);
                // Map에 저장
                itemList.put(Integer.valueOf(item.getId()), item);
            }
        } catch (IOException e) {
            System.out.println("상품 목록 조회 오류");
            e.printStackTrace();
        }
    }

    // 포맷 맞추기용 메서드
    private void printItem(StringBuilder sb) {
        Iterator<Item> itemIterator = itemList.values().iterator();
        while (itemIterator.hasNext()) {
            Item item = itemIterator.next();
            sb.append(item.getId()).append(" ")
                    .append(item.getItemName()).append(" ")
                    .append(item.getItemPrice()).append(" ")
                    .append(item.getQuantity()).append("\n");
        }
    }

    // DB 저장 메서드
    protected void saveItemFile() {
        StringBuilder sb = new StringBuilder();
        printItem(sb);
        try {
            new FileWriter(file).close();
            itemListWriter.append(sb.toString());
            itemListWriter.flush();
            itemListWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}