package basket;

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

public class BasketService extends ItemService {

    Scanner kb = new Scanner(System.in);
    File file = new File("C:\\Users\\gram15\\Desktop\\SWedu\\shop\\shop\\database\\basketList.txt");
    BufferedWriter basketListWriter;
    BufferedReader basketListReader = new BufferedReader(
            new FileReader(file, UTF_8)
    );

    private LinkedHashMap<Integer, Basket> basketList = new LinkedHashMap<>();
    MemberService memberService = new MemberService();
    private int basketId = 0;

    // 클래스 객체 생성 시 DB 내용 basketList에 입력, basketList이름의 DB없을 시 파일 생성
    public BasketService() throws IOException {
        readBasketInputCheck();
        try {
            basketListWriter = new BufferedWriter(
                    new FileWriter(file, true)
            );
            basketListWriter.write("");
            basketListWriter.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    };

    // 장바구니 메뉴 선택 하기
    public void inputBasketMenu() {
        while (true) {
            System.out.println("0. 이전 메뉴(진행 내용 저장)\n1. 장바구니에 추가\n2. 장바구니 전체 조회\n3. 장바구니에서 상품 삭제");
            int basketMenu = kb.nextInt();
            if (basketMenu == 0) {
                saveBasketFile();
                break;
            } else if (basketMenu == 1) {
                System.out.println("이름을 입력해주세요.");
                String userName = kb.next();
                findAllItem();
                System.out.println("장바구니에 담을 상품의 이름을 입력해 주세요");
                String itemName = kb.next();
                addBasket(userName, itemName);
            } else if (basketMenu == 2) {
                System.out.println("이름을 입력해주세요.");
                String userName = kb.next();
                findAllBasketList(userName);
            } else if (basketMenu == 3) {
                System.out.println("이름과, 장바구니에서 삭제할 상품의 이름을 입력해주세요.");
                String userName = kb.next();
                String itemName = kb.next();
                deleteBasket(userName, itemName);
            } else {
                System.out.println("잘못된 입력입니다.\n다시 입력해주세요.");
            }
        }
    }

    // 장바구니에 상품 담기
    public void addBasket(String userName, String itemName) {
        Member member = memberService.findMember(userName);
        Iterator<Item> itemIterator = itemList.values().iterator();
        Item item = null;
        // item객체에 입력한 상품 이름과 일치하는 상품 정보 가져오기
        while (itemIterator.hasNext()) {
            item = itemIterator.next();
            if (item.getItemName().equals(itemName)) {
                break;
            }
        }

        // 입력한 상품 이름과 일치하는 상품 없을시 예외처리 후 장바구니에 추가 로직 종료
        if (item == null) {
            System.out.println("입력하신 상품은 없는 상품입니다.");
            return;
        }

        // 입력한 상품 이름과 일치하는 상품 있을 시
        Basket basket = new Basket(1, member.getName(), item.getItemName(), item.getItemPrice());
        // DB에 상품이 있을 때
        if (!basketList.isEmpty()) {
            // Collections의 max 메서드를 사용하여 키값(고유번호)중 가장 큰값에 +1 하여 고유 번호 증가
            basketId = Collections.max(basketList.keySet()) + 1;
            // 생성된 Basket객체의 id값 수정
            basket.setId(basketId);
            basketList.put(basketId, basket);
        // DB에 상품이 없을 때
        } else {
            basketList.put(basket.getId(), basket);
        }
        System.out.println("상품 등록 완료");
    }

    // 장바구니에 담겨있는 상품 전체 조회
    public void findAllBasketList(String userName) {
        StringBuilder sb = new StringBuilder();
        Iterator<Basket> basketIterator = basketList.values().iterator();
        // 장바구니에 담겨있는 상품 없을 시 예외 메시지 출력 후 종료
        if (!basketIterator.hasNext()) {
            System.out.println("장바구니에 담겨있는 상품이 없습니다.");
            return;
        }

        // 장바구니에 있는 상품 목록 StringBuilder에 포맷형식에 맞춰 저장
        while (basketIterator.hasNext()) {
            Basket basket = basketIterator.next();
            if (basket.getUserName().equals(userName)) {
                sb.append(basket.getId()).append(" ")
                        .append(basket.getUserName()).append(" ")
                        .append(basket.getItemName()).append(" ")
                        .append(basket.getItemPrice()).append("\n");
            }
        }

        // 장바구니 목록 출력
        System.out.println(userName + "님의 장바구니 목록!!\n" + sb);
    }

    // 장바구니의 목록 삭제
    public void deleteBasket(String userName, String itemName) {
        Iterator<Basket> basketIterator = basketList.values().iterator();
        // 입력한 상품 이름에 맞는 상품이 장바구니에 있는지 확인
        Basket basket = null;
        while (basketIterator.hasNext()) {
            basket = basketIterator.next();
            // 해당 상품이 있으면 Basket객체에 저장되어있으니 while문 종료해서 Basket객체의 정보 유지
            if (basket.getUserName().equals(userName) && basket.getItemName().equals(itemName)) {
                break;
            }
        }

        // Basket객체가 null이 아니고, Basket객체의 고유번호 값에 맞는 정보가 있는지의 조건
        if (basket != null && basketList.containsKey(basket.getId())) {
            // 해당 조건을 만족하면 해당 상품 장바구니에서 삭제
            basketList.remove(basket.getId());
            System.out.println("선택하신 상품을 장바구니에서 삭제하였습니다.");
        // 해당 상품이 장바구니에 없으면 예외 메시지 출력
        } else {
            System.out.println("일치하는 상품이 장바구니에 없습니다.");
        }
    }

    // DB에 저장된 장바구니 목록 Map에 저장
    private void readBasketInputCheck() {
        try {
            String basketInfo;
            while ((basketInfo = basketListReader.readLine()) != null) {
                String[] tmp = basketInfo.split(" ");
                // Basket 엔티티의 형식에 맞춰 Basket 객체 생성
                int id = Integer.parseInt(tmp[0]);
                basketId = id;
                String userName = tmp[1];
                String itemName = tmp[2];
                int itemPrice = Integer.parseInt(tmp[3]);
                Basket basket = new Basket(id, userName, itemName, itemPrice);
                // Map에 저장
                basketList.put(basket.getId(), basket);
            }
        } catch (IOException e) {
            System.out.println("장바구니 목록 조회 오류");
            e.printStackTrace();
        }
    }

    // 포맷 맞추기용 메서드
    private void printBasket(StringBuilder sb) {
        Iterator<Basket> basketIterator = basketList.values().iterator();
        while (basketIterator.hasNext()) {
            Basket basket = basketIterator.next();
            sb.append(basket.getId()).append(" ")
                    .append(basket.getUserName()).append(" ")
                    .append(basket.getItemName()).append(" ")
                    .append(basket.getItemPrice()).append("\n");
        }
    }

    // DB에 저장 메서드
    public void saveBasketFile() {
        StringBuilder sb = new StringBuilder();
        // StringBuilder에 Map의 내용 포맷에 맞게 저장
        printBasket(sb);
        // DB에 저장하기 IOException이 발생할 수 있으므로 try-catch로 감싸줌
        try {
            new FileWriter(file).close();
            basketListWriter.append(sb.toString());
            basketListWriter.flush();
            basketListWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
