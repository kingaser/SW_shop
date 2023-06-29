package shop.basket;

import adminitem.AdminItem;
import adminitem.AdminItemService;
import member.Member;
import member.MemberService;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedHashMap;

import static java.nio.charset.StandardCharsets.UTF_8;

public class BasketService {

    File file = new File("C:\\Users\\gram15\\Desktop\\SWedu\\shop\\shop\\database\\basketList.txt");
    BufferedWriter basketListWriter;
    BufferedReader basketListReader = new BufferedReader(
            new FileReader(file, UTF_8)
    );

    LinkedHashMap<Integer, Basket> basketList = new LinkedHashMap<>();
    MemberService memberService = new MemberService();
    AdminItemService adminItemService = new AdminItemService();
    private int basketId;

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
    }

    ;

    // TODO: 2023-06-28 장바구니에 추가
    public void addBasket(String userName) {
        StringBuilder sb = new StringBuilder();
        Member member = memberService.findMember(userName);
        AdminItem adminItem = adminItemService.findItem();

        Basket basket = new Basket(1, member.getName(), adminItem.getItemName(), adminItem.getItemPrice());
        if (!basketList.isEmpty()) {
            basketList.put(++basketId, basket);
            basket.setId(basketId);
        }
        sb.append(basket.getId()).append(" ").append(member.getName()).append(" ")
                .append(basket.getItemName()).append(" ").append(basket.getItemPrice()).append("\n");
        System.out.println("상품 등록 완료");
    }

    // TODO: 2023-06-28 장바구니 목록 조회
    public StringBuilder findAllBasketList(String userName) {
        StringBuilder sb = new StringBuilder();
        Iterator<Basket> basketIterator = basketList.values().iterator();
        while (basketIterator.hasNext()) {
            Basket basket = basketIterator.next();
            if (basket.getUserName().equals(userName)) {
                sb.append(basket.getId()).append(" ").append(basket.getUserName()).append(" ")
                        .append(basket.getItemName()).append(" ").append(basket.getItemPrice()).append("\n");
            }
        }
        return sb;
    }

    // TODO: 2023-06-28 장바구니 목록 삭제
    public void deleteBasket(String userName, String itemName) {
        Iterator<Basket> basketIterator = basketList.values().iterator();
        Basket basket = null;
        while (basketIterator.hasNext()) {
            basket = basketIterator.next();
            if (basket.getUserName().equals(userName) && basket.getItemName().equals(itemName)) {
                break;
            }
        }

        if (basket != null && basketList.containsKey(basket.getId())) {
            basketList.remove(basket.getId());
            System.out.println("선택하신 상품을 장바구니에서 삭제하였습니다.");
        } else {
            System.out.println("일치하는 상품이 장바구니에 없습니다.");

        }
    }

    // TODO: 2023-06-28 읽어온 파일 Map에 저장
    private void readBasketInputCheck() {
        try {
            String basketInfo;
            while ((basketInfo = basketListReader.readLine()) != null) {
                String[] tmp = basketInfo.split(" ");
                int id = Integer.parseInt(tmp[0]);
                basketId = id;
                String userName = tmp[1];
                String itemName = tmp[2];
                int itemPrice = Integer.parseInt(tmp[2]);
                Basket basket = new Basket(id, userName, itemName, itemPrice);
                basketList.put(basket.getId(), basket);
            }
        } catch (IOException e) {
            System.out.println("장바구니 목록 조회 오류");
            e.printStackTrace();
        }
    }

    private void printBasket(StringBuilder sb) {
        Iterator<Basket> basketIterator = basketList.values().iterator();
        while (basketIterator.hasNext()) {
            Basket basket = basketIterator.next();
            sb.append(basket.getId()).append(" ").append(basket.getUserName()).append(" ")
                    .append(basket.getItemName()).append(" ").append(basket.getItemPrice()).append("\n");
        }
    }

    public void saveFile() {
        StringBuilder sb = new StringBuilder();
        printBasket(sb);
        try {
            new FileWriter(file).close();
            basketListWriter.write(sb.toString());
            basketListWriter.flush();
            basketListWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
