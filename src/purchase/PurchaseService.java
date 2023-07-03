package purchase;

import adminitem.AdminItem;
import adminitem.AdminItemService;
import member.Member;
import member.MemberService;

import java.io.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

public class PurchaseService extends AdminItemService {

    // TODO: 2023-06-30 database 입출력 설정
    Scanner kb = new Scanner(System.in);
    File file = new File("C:\\Users\\gram15\\Desktop\\SWedu\\shop\\shop\\database\\purchaseList.txt");
    BufferedWriter purchaseListWriter;
    BufferedReader purchaseListReader = new BufferedReader(
            new FileReader(file, UTF_8)
    );

    MemberService memberService = new MemberService();

    // TODO: 2023-06-30 멤버 변수 설정
    private LinkedHashMap<Integer, Purchase> purchaseList = new LinkedHashMap<>();
    private int purchaseId = 1;

    public PurchaseService() throws IOException {
        readPurchaseInputCheck();
        try {
            purchaseListWriter = new BufferedWriter(
                    new FileWriter(file, true)
            );
            purchaseListWriter.write("");
            purchaseListWriter.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void inputPurchaseMenu() {
        while (true) {
            System.out.println("0. 이전 메뉴(진행 내용 저장)\n1. 상품 리스트");
            int purchaseMenu = kb.nextInt();
            if (purchaseMenu == 0) {
                savePurchaseFile();
                saveAdminItemFile();
                break;
            } else if (purchaseMenu == 1) {
                System.out.println("회원 이름을 입력해주세요.");
                String userName = kb.next();
                findAllItem();
                System.out.println("구매하실 상품의 이름과 개수를 입력해주세요.");
                String itemName = kb.next();
                int itemCount = kb.nextInt();
                addPurchase(userName, itemName, itemCount);
            } else {
                System.out.println("잘못된 입력입니다.\n다시 입력해주세요.");
            }
        }
    }

    public void addPurchase(String userName, String itemName, int itemCount) {
        Member member = memberService.checkMember(userName);
        Iterator<AdminItem> adminItemIterator = adminItemList.values().iterator();
        AdminItem adminItem = null;
        while (adminItemIterator.hasNext()) {
            adminItem = adminItemIterator.next();
            if (adminItem.getItemName().equals(itemName)) {
                break;
            }
        }

        if (adminItem == null) {
            System.out.println("입력하신 상품은 없는 상품입니다.");
            return;
        }

        if (adminItem.getQuantity() < itemCount) {
            System.out.println("입력하신 갯수만큼의 제고가 없습니다.");
            return;
        }

        Purchase purchase = new Purchase(purchaseId, member.getName(), adminItem.getItemName()
                , adminItem.getItemPrice(), itemCount);
        if (!purchaseList.isEmpty()) {
            purchaseId = Collections.max(purchaseList.keySet()) + 1;
            purchase.setId(purchaseId);
            purchaseList.put(purchaseId, purchase);
        } else {
            purchaseList.put(purchaseId, purchase);
        }
        System.out.println("상품 구매 완료");

        adminItem.setQuantity(adminItem.getQuantity() - itemCount);
        adminItemList.put(adminItem.getId(), adminItem);
    }

    public void findAllPurchase(String userName) {
        Member member = memberService.checkMember(userName);
        System.out.println(member.getName() + "님의 구매목록!!");

        StringBuilder sb = new StringBuilder();
        Iterator<Purchase> purchaseIterator = purchaseList.values().iterator();
        while (purchaseIterator.hasNext()) {
            Purchase purchase = purchaseIterator.next();
            if (purchase.getUserName().equals(userName)) {
                sb.append(purchase.getId()).append(" ")
                        .append(purchase.getUserName()).append(" ")
                        .append(purchase.getItemName()).append(" ")
                        .append(purchase.getQuantity()).append("\n");
            }
        }
        System.out.println(sb);
    }

    // TODO: 2023-06-28 읽어온 파일 Map에 저장
    private void readPurchaseInputCheck() {
        try {
            String purchaseTmp;
            while ((purchaseTmp = purchaseListReader.readLine()) != null) {
                String[] tmp = purchaseTmp.split(" ");
                int id = Integer.parseInt(tmp[0]);
                purchaseId = id;
                String userName = tmp[1];
                String itemName = tmp[2];
                int itemPrice = Integer.parseInt(tmp[3]);
                int quantity = Integer.parseInt(tmp[4]);
                Purchase purchase = new Purchase(id, userName, itemName, itemPrice, quantity);
                purchaseList.put(purchase.getId(), purchase);
            }
        } catch (IOException e) {
            System.out.println("구매 목록 조회 오류");
            e.printStackTrace();
        }
    }

    private void printPurchaseList(StringBuilder sb) {
        Iterator<Purchase> memberIterator = purchaseList.values().iterator();
        while (memberIterator.hasNext()) {
            Purchase purchase = memberIterator.next();
            sb.append(purchase.getId()).append(" ")
                    .append(purchase.getUserName()).append(" ")
                    .append(purchase.getItemName()).append(" ")
                    .append(purchase.getItemPrice()).append(" ")
                    .append(purchase.getQuantity()).append("\n");
        }
    }

    private void savePurchaseFile() {
        StringBuilder sb = new StringBuilder();
        printPurchaseList(sb);
        try {
            new FileWriter(file).close();
            purchaseListWriter.append(sb.toString());
            purchaseListWriter.flush();
            purchaseListWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
