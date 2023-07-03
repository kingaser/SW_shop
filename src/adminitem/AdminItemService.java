package adminitem;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

public class AdminItemService {
    int num = 0;

    Scanner kb = new Scanner(System.in);
    File file = new File("C:\\Users\\gram15\\Desktop\\SWedu\\shop\\shop\\database\\adminItemList.txt");
    BufferedWriter adminItemListWriter;

    BufferedReader adminItemListReader = new BufferedReader(
            new FileReader(file, UTF_8));


    protected LinkedHashMap<Integer, AdminItem> adminItemList = new LinkedHashMap<>();

    public AdminItemService() throws IOException {
        readAdminItemInputCheck();
        try {
            adminItemListWriter = new BufferedWriter(
                    new FileWriter(file, true)
            );
            adminItemListWriter.write("");
            adminItemListWriter.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

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
                saveAdminItemFile();
                break;
            }
        }
    }

    public void addItem() {
        System.out.println("상품명을 입력하세요.");
        String itemName = kb.next();
        Iterator<AdminItem> adminItemIterator = adminItemList.values().iterator();
        while (adminItemIterator.hasNext()) {
            AdminItem adminItem = adminItemIterator.next();
            if (adminItem.getItemName().equals(itemName)) {
                System.out.println(itemName + "은(는) 이미 등록된 상품명 입니다.");
                return;
            }
        }


        System.out.println();
        System.out.print("가격 = ");
        int itemPrice = kb.nextInt();
        System.out.print("재고수량 = ");
        int quantity = kb.nextInt();
        num++;
        AdminItem addItem = new AdminItem(num, itemName, itemPrice, quantity);
        System.out.println(itemName + " 상품 등록완료!!");

        adminItemList.put(addItem.getId(), addItem);
        StringBuilder sb = new StringBuilder();
        sb.append(addItem.getId()).append(" ").append(addItem.getItemName()).append(" ").append(addItem.getItemPrice())
                .append("원 ").append(addItem.getQuantity()).append("개 ").append("\n");
    }

    public void findItem() {
        System.out.println("조회할 상품명를 입력하세요.");
        String itemName = kb.next();
        AdminItem findItem = null;
        for (int itemId : adminItemList.keySet()) {
            findItem = adminItemList.get(itemId);
            if (findItem.getItemName().equals(itemName)) {
                System.out.println(itemName + "의 상품 정보");
                System.out.println("고유번호 = " + findItem.getId());
                System.out.println("이름 = " + findItem.getItemName());
                System.out.println("가격 = " + findItem.getItemPrice()+"원 ");
                System.out.println("재고수량 = " + findItem.getQuantity()+"개 ");
            }
        }
        if (findItem == null) {
            System.out.println(itemName + "의 정보가 없습니다.");
        }
    }

    public void findAllItem() {
        Iterator<AdminItem> adminItems = adminItemList.values().iterator();
        if (!adminItems.hasNext()) {
            System.out.println("등록된 상품이 없습니다.");
        } else {
            System.out.println("전체 상품 목록");
            while (adminItems.hasNext()) {
                AdminItem item = adminItems.next();
                System.out.println(item.getId() + " " + item.getItemName() + " " + item.getItemPrice() + "원 " + item.getQuantity()+"개 ");
            }
        }
    }

    public void updateItem() {
        System.out.println("수정하실 상품명를 입력하세요.");
        String itemName = kb.next();

        int id = 0;
        String updateItemName;
        int updateItemPrice;
        int updateQuantity;

        Iterator<AdminItem> adminItemIterator = adminItemList.values().iterator();
        if (!adminItemIterator.hasNext()) {
            System.out.println("등록된 상품이 없습니다.");
            return;
        }
        while (adminItemIterator.hasNext()) {
            AdminItem adminItem = adminItemIterator.next();
            if (adminItem.getItemName().equals(itemName)) {
                id = adminItem.getId();
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

//            updateItemName = updateItemName.replace(itemName,updateItemName);
            AdminItem updateAdminItem = new AdminItem(id, updateItemName, updateItemPrice, updateQuantity);
//            adminItemList.remove(adminItem.getItemName());
            updateItemName = updateItemName.replace(itemName, updateItemName);
            adminItemList.put(updateAdminItem.getId(), updateAdminItem);
            System.out.println("수정완료!!");

            StringBuilder sb = new StringBuilder();
            printAdminItem(sb);
        }
    }

    public void deleteItem() {
        System.out.println("삭제할 상품명을 입력하세요.");
        String itemName = kb.next();
        boolean itemFound = false;
        Iterator<AdminItem> adminItemIterator = adminItemList.values().iterator();
        while (adminItemIterator.hasNext()) {
            AdminItem adminItem = adminItemIterator.next();
            if (adminItem.getItemName().equals(itemName)) {
                adminItemList.remove(adminItem.getId());
                itemFound = true;
                System.out.println(itemName + " 의 상품을 삭제했습니다.");
                break;
            }
        }if(!itemFound) {
            System.out.println(itemName + " 는 등록된 상품명이 아닙니다.");
        }


        StringBuilder sb = new StringBuilder();
        printAdminItem(sb);
    }

    // TODO: 2023-06-29 읽어온 파일 Map에 저장
    private void readAdminItemInputCheck() {
        try {

            String itemInfo;
            while ((itemInfo = adminItemListReader.readLine()) != null) {
                String[] tmp = itemInfo.split(" ");
                int id = Integer.parseInt(tmp[0]);

                String itemName = tmp[1];
                int itemPrice = Integer.parseInt(tmp[2]);
                int quantity = Integer.parseInt(tmp[3]);
                num = id + 1;
                AdminItem adminItem = new AdminItem(id, itemName, itemPrice, quantity);
                adminItemList.put(Integer.valueOf(adminItem.getId()), adminItem);
            }
        } catch (IOException e) {
            System.out.println("상품 목록 조회 오류");
            e.printStackTrace();
        }
    }

    // TODO: 2023-06-29 Item 목록 Map에 저장
    private void printAdminItem(StringBuilder sb) {
        Iterator<AdminItem> adminItemIterator = adminItemList.values().iterator();
        while (adminItemIterator.hasNext()) {
            AdminItem adminItem = adminItemIterator.next();
            sb.append(adminItem.getId()).append(" ")
                    .append(adminItem.getItemName()).append(" ")
                    .append(adminItem.getItemPrice()).append(" ")
                    .append(adminItem.getQuantity()).append("\n");
        }
    }

    protected void saveAdminItemFile() {
        StringBuilder sb = new StringBuilder();
        printAdminItem(sb);
        try {
            new FileWriter(file).close();
            adminItemListWriter.append(sb.toString());
            adminItemListWriter.flush();
            adminItemListWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}