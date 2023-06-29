package shop.basket;

import member.Member;
import member.MemberService;
import shop.Item;

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

    LinkedHashMap<String, Basket> basketList = new LinkedHashMap<>();
    MemberService memberService = new MemberService();

    public BasketService() throws IOException {
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
    public void addBasket(String name, Item item) {
        StringBuilder sb = new StringBuilder();
        Member member = memberService.findMember(name);
        Basket basket = new Basket(member.getName(), item.getItemName(), item.getPrice());
        basketList.put(basket.getUserName(), basket);
        sb.append(member.getName()).append(" ").append(basket.getItemName())
                .append(" ").append(basket.getItemPrice()).append("\n");
        try {
            new FileWriter(file).close();
            basketListWriter.append(sb.toString());
            System.out.println("회원 가입 완료!!");
            basketListWriter.flush();
            basketListWriter.close();
        } catch (IOException e) {
            System.out.println("회원 가입 에러");
            e.printStackTrace();
        }
    }

    // TODO: 2023-06-28 장바구니 목록 조회
    public StringBuilder findAllBasketList(String name) {
        StringBuilder sb = new StringBuilder();
        memberService.findMember(name);
        basketList = readBasketInputCheck();
        Iterator<Basket> it = basketList.values().iterator();
        printBasket(it, sb);
        return sb;
    }

    // TODO: 2023-06-28 장바구니 목록 삭제
    public void deleteBasket(String userName, String itemName) {
        memberService.findMember(userName);
        LinkedHashMap<String, Basket> deleteBasketList = new LinkedHashMap<>();
        try {
            String basketInfo;
            while ((basketInfo = basketListReader.readLine()) != null) {
                String[] tmp = basketInfo.split(" ");
                String listUserName = tmp[0];
                String listItemName = tmp[1];
                int listItemPrice = Integer.parseInt(tmp[2]);
                Basket basket = new Basket(listUserName, listItemName, listItemPrice);
                deleteBasketList.put(listUserName + " " + listItemName, basket);
            }
        } catch (IOException e) {
            System.out.println("회원 목록 조회 오류");
            e.printStackTrace();
        }

        if (deleteBasketList.containsKey(userName + " " + itemName)) {
            deleteBasketList.remove(userName + " " + itemName);
            System.out.println("선택하신 제품을 장바구니에서 삭제하였습니다.");
        } else {
            System.out.println("일치하는 제품이 장바구니에 없습니다.");
        }

        StringBuilder sb = new StringBuilder();
        Iterator<Basket> it = deleteBasketList.values().iterator();
        printBasket(it, sb);
        // 파일 내용 지우고 덮어쓰기
        try {
            new FileWriter(file).close();
            basketListWriter.write(sb.toString());
            basketListWriter.flush();
            basketListWriter.close();
        } catch (IOException e) {
            System.out.println("회원 삭제 오류");
            e.printStackTrace();
        }

    }

    // TODO: 2023-06-28 읽어온 파일 Map에 저장
    private LinkedHashMap<String, Basket> readBasketInputCheck() {
        try {
            String basketInfo;
            while ((basketInfo = basketListReader.readLine()) != null) {
                String[] tmp = basketInfo.split(" ");
                String userName = tmp[0];
                String itemName = tmp[1];
                int itemPrice = Integer.parseInt(tmp[2]);
                Basket basket = new Basket(userName, itemName, itemPrice);
                basketList.put(basket.getUserName(), basket);
            }
        } catch (IOException e) {
            System.out.println("회원 목록 조회 오류");
            e.printStackTrace();
        }

        return basketList;
    }

    // TODO: 2023-06-28 장바구니 목록 StringBuilder에 저장
    private static void printBasket(Iterator<Basket> it, StringBuilder sb) {
        while (it.hasNext()) {
            Basket basket = it.next();
            sb.append(basket.getUserName()).append(" ").append(basket.getItemName())
                    .append(" ").append(basket.getItemPrice()).append("\n");
        }
    }

}
