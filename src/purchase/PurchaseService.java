package purchase;

import item.Item;
import item.ItemService;
import member.Member;
import member.MemberService;

import java.io.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

public class PurchaseService extends ItemService {

    Scanner kb = new Scanner(System.in);
    // DB 입출력 설정
    File file = new File("C:\\Users\\gram15\\Desktop\\SWedu\\shop\\shop\\database\\purchaseList.txt");
    BufferedWriter purchaseListWriter;
    BufferedReader purchaseListReader = new BufferedReader(
            new FileReader(file, UTF_8)
    );

    MemberService memberService = new MemberService();

    // 구매 목록 Map 변수
    private LinkedHashMap<Integer, Purchase> purchaseList = new LinkedHashMap<>();
    private int purchaseId = 1;

    // 구매목록 파일 입력
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

    // 구매 메뉴 설정
    public void inputPurchaseMenu() {
        while (true) {
            System.out.println("0. 이전 메뉴(진행 내용 저장)\n1. 상품 리스트");
            int purchaseMenu = kb.nextInt();
            // 장바구니, 구매 목록 내용 저장
            if (purchaseMenu == 0) {
                savePurchaseFile();
                saveItemFile();
                break;
            // 회원이름 입력 후 상품 구매
            } else if (purchaseMenu == 1) {
                System.out.println("회원 이름을 입력해주세요.");
                String userName = kb.next();
                // 상품 전체 목록 조회
                findAllItem();
                System.out.println("구매하실 상품의 이름과 개수를 입력해주세요.");
                String itemName = kb.next();
                int itemCount = kb.nextInt();
                addPurchase(userName, itemName, itemCount);
            // 메뉴에 없는 번호 입력 시
            } else {
                System.out.println("잘못된 입력입니다.\n다시 입력해주세요.");
            }
        }
    }

    // 구매 목록에 추가
    public void addPurchase(String userName, String itemName, int itemCount) {
        Member member = memberService.checkMember(userName);
        Iterator<Item> itemIterator = itemList.values().iterator();
        Item item = null;
        // 입력한 상품 이름과 맞는 상품이 있으면 Item 객체에 저장
        while (itemIterator.hasNext()) {
            item = itemIterator.next();
            if (item.getItemName().equals(itemName)) {
                break;
            }
        }

        // 입력한 이름과 일치하는 상품이 없으면 예외 메시지 출력 후 종료
        if (item == null) {
            System.out.println("입력하신 상품은 없는 상품입니다.");
            return;
        }

        // 남은 재고 수량보다 구매수량이 많으면 예외 메시지 출력 후 종료
        if (item.getQuantity() < itemCount) {
            System.out.println("입력하신 갯수만큼의 제고가 없습니다.");
            return;
        }

        Purchase purchase = new Purchase(purchaseId, member.getName(), item.getItemName()
                , item.getItemPrice(), itemCount);
        // 구매목록에 상품이 있을 경우
        if (!purchaseList.isEmpty()) {
            // Collections의 max 를 사용하여 키값(고유번호)중
            // 가장 큰값을 가져와 +1 해서 고유번호 값 자동 증가
            purchaseId = Collections.max(purchaseList.keySet()) + 1;
            purchase.setId(purchaseId);
            purchaseList.put(purchaseId, purchase);
        // 구매목록이 비어있을 경우
        } else {
            purchaseList.put(purchaseId, purchase);
        }
        System.out.println("상품 구매 완료");

        //상품 재고수량에서 구매 수량 빼기
        item.setQuantity(item.getQuantity() - itemCount);
        itemList.put(item.getId(), item);
    }

    // 구매 목록 전체 조회, 회원 이름으로 구매목록 탐색 후 입력한 회원의 전체 구매목록 반환
    public void findAllPurchase(String userName) {
        // 회원 검색
        Member member = memberService.checkMember(userName);
        System.out.println(member.getName() + "님의 구매목록!!");


        StringBuilder sb = new StringBuilder();
        Iterator<Purchase> purchaseIterator = purchaseList.values().iterator();
        // 구매목록에서 회원 이름에 맞는 구매 내역 확인 후 포맷에 맞게 저장
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

    //구매리스트에 저장된 구매 목록 Map에 저장
    private void readPurchaseInputCheck() {
        try {
            String purchaseTmp;
            while ((purchaseTmp = purchaseListReader.readLine()) != null) {
                String[] tmp = purchaseTmp.split(" ");
                //구매목록 텍스트파일 저장 형식에 맞추어 Purchase객체 생성
                int id = Integer.parseInt(tmp[0]);
                purchaseId = id;
                String userName = tmp[1];
                String itemName = tmp[2];
                int itemPrice = Integer.parseInt(tmp[3]);
                int quantity = Integer.parseInt(tmp[4]);
                Purchase purchase = new Purchase(id, userName, itemName, itemPrice, quantity);
                //Map에 저장
                purchaseList.put(purchase.getId(), purchase);
            }
        } catch (IOException e) {
            System.out.println("구매 목록 조회 오류");
            e.printStackTrace();
        }
    }

    //포맷 맞추기용 메서드
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

    //구매정보리스트에 저장 메서드
    private void savePurchaseFile() {
        StringBuilder sb = new StringBuilder();
        //StringBuilder에 Map의 내용 포멧에 맞게 저장
        printPurchaseList(sb);
        //DB에 저장하기 IOException이 발생할 수 있으므로 try-catch로 감싸줌
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
