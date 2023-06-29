package adminitem;

import member.Member;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

public class AdminItemService {
    Scanner kb = new Scanner(System.in);
    File file = new File("C:\\Users\\gram15\\Desktop\\SWedu\\shop\\shop\\database\\adminItemList.txt");
    BufferedWriter adminItemListWriter;
    BufferedReader adminItemListReader = new BufferedReader(
            new FileReader(file, UTF_8)
    );


    private LinkedHashMap<String, AdminItem> adminItemList = new LinkedHashMap<>();

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
            System.out.println("0. 이전 메뉴\n1. 상품 등록\n2. 상품 등록 삭제\n3. 상품 등록 수정\n" +
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
                break;
            }
        }
    }


    public void addItem() {
        System.out.println("상품명을 입력하세요.");
        String itemName = kb.next();
        if (adminItemList.containsKey(itemName)) {
            System.out.println(itemName + " 이미 등록된 상품명입니다.");
            return;
        }

        System.out.print("가격 = ");
        int itemPrice = kb.nextInt();
        System.out.print("재고수량 = ");
        int quantity = kb.nextInt();
        AdminItem addItem = new AdminItem(itemName, itemPrice, quantity);
        adminItemList.put(itemName, addItem);
        System.out.println(itemName + " 상품 등록완료!!");

        StringBuilder sb = new StringBuilder();
        sb.append(addItem.getItemName()).append(" ").append(addItem.getItemPrice())
                .append(" ").append(addItem.getQuantity()).append("\n");
        try {
            adminItemListWriter.append(sb.toString());
            System.out.println("상품 추가 완료!!");
            adminItemListWriter.flush();
            adminItemListWriter.close();
        } catch (IOException e) {
            System.out.println("상품 추가 에러");
            e.printStackTrace();
        }
    }

    public void findItem() {
        System.out.println("조회할 상품 이름을 입력하세요.");
        String itemName = kb.next();

        AdminItem findItem = adminItemList.get(itemName);

        if (findItem == null) {
            System.out.println(itemName + "의 정보가 없습니다.");
        } else {
            System.out.println(itemName + "의 상품 정보");
            System.out.println("가격 = " + findItem.getItemPrice());
            System.out.println("재고수량 = " + findItem.getQuantity());
        }
    }

    public void findAllItem() {
        System.out.println("전체 상품 목록");
        if (adminItemList.size() == 0) {
            System.out.println("등록된 상품 정보가 없습니다.");
        } else {
            Iterator<AdminItem> adminItems = adminItemList.values().iterator();

            while (adminItems.hasNext()) {
                AdminItem item = adminItems.next();
                System.out.println(item.getItemName() + " " + item.getItemPrice() + " " + item.getQuantity());
            }
        }
    }

    public void updateItem() {
        System.out.println("수정하실 상품명를 입력하세요.");
        String itemName = kb.next();
        String updateItemName;
        int updateItemPrice;
        int updateQuantity;

        if (adminItemList.containsKey(itemName)) {
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

        AdminItem updateAdminItem = new AdminItem(updateItemName, updateItemPrice, updateQuantity);
        adminItemList.remove(itemName);
        adminItemList.put(updateAdminItem.getItemName(), updateAdminItem);
        System.out.println("수정완료!!");

        StringBuilder sb = new StringBuilder();
        Iterator<AdminItem> it = adminItemList.values().iterator();
        printAdminItem(it, sb);

        try {
            new FileWriter(file).close();
            adminItemListWriter.write(sb.toString());
            adminItemListWriter.flush();
            adminItemListWriter.close();
        } catch (IOException e) {
            System.out.println("회원 삭제 오류");
            e.printStackTrace();
        }
    }

    public void deleteItem() {
        System.out.println("삭제할 상품명을 입력하세요.");
        String itemName = kb.next();
        if (adminItemList.containsKey(itemName)) {
            adminItemList.remove(itemName);
            System.out.println(itemName + " 의 상품을 삭제했습니다.");
        } else {
            System.out.println(itemName + " 는 등록된 상품명이 아닙니다.");
        }

        StringBuilder sb = new StringBuilder();
        Iterator<AdminItem> it = adminItemList.values().iterator();
        printAdminItem(it, sb);

        try {
            new FileWriter(file).close();
            adminItemListWriter.write(sb.toString());
            adminItemListWriter.flush();
            adminItemListWriter.close();
        } catch (IOException e) {
            System.out.println("회원 삭제 오류");
            e.printStackTrace();
        }
    }

    // TODO: 2023-06-29 읽어온 파일 Map에 저장
    private void readAdminItemInputCheck() {
        try {
            String itemInfo;
            while ((itemInfo = adminItemListReader.readLine()) != null) {
                String[] tmp = itemInfo.split(" ");
                String itemName = tmp[0];
                int itemPrice = Integer.parseInt(tmp[1]);
                int quantity = Integer.parseInt(tmp[2]);
                AdminItem adminItem = new AdminItem(itemName, itemPrice, quantity);
                adminItemList.put(adminItem.getItemName(), adminItem);
            }
        } catch (IOException e) {
            System.out.println("상품 목록 조회 오류");
            e.printStackTrace();
        }
    }

    // TODO: 2023-06-29 Item 목록 Map에 저장
    private static void printAdminItem(Iterator<AdminItem> it, StringBuilder sb) {
        while (it.hasNext()) {
            AdminItem adminItem = it.next();
            sb.append(adminItem.getItemName()).append(" ").append(adminItem.getItemPrice())
                    .append(" ").append(adminItem.getQuantity()).append("\n");
        }
    }
}