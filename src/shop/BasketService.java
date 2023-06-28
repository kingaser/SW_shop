package shop;

import member.Member;
import member.MemberService;

import java.io.*;
import java.util.*;
import static java.nio.charset.StandardCharsets.UTF_8;

public class BasketService {

    Scanner kb = new Scanner(System.in);
    File file = new File("C:\\Users\\gram15\\Desktop\\SWedu\\shop\\shop\\basketList.txt");
    BufferedReader basketListReader = new BufferedReader(
            new FileReader(file, UTF_8)
    );
    BufferedWriter basketListWriter = new BufferedWriter(
            new FileWriter(file, true)
    );

    LinkedHashMap<String, Basket> basketList = new LinkedHashMap<>();
    MemberService memberService = new MemberService();

    public BasketService() throws IOException {};

    // TODO: 2023-06-28 장바구니에 추가
    public void addBasket(String name, Item item) {
        StringBuilder sb = new StringBuilder();
        Member member = memberService.findMember(name);
        Basket basket = new Basket(item.getItemName(), item.getPrice());
        basketList.put(name, basket);
        sb.append(member.getName()).append(" ").append(basket.getItemName())
                .append(" ").append(basket.getItemPrice()).append("\n");
        try {
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
        Member member = memberService.findMember(name);
        basketList = readBasketInputCheck();
        Iterator<Basket> it = basketList.values().iterator();
        printBasket(member.getName(), it, sb);
        return sb;
    }

    public void deleteBasket(String name, Basket basket) {
        Member member = memberService.findMember(name);

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
                Basket basket = new Basket(itemName, itemPrice);
                basketList.put(userName, basket);
            }
        } catch (IOException e) {
            System.out.println("회원 목록 조회 오류");
            e.printStackTrace();
        }

        return basketList;
    }

    // TODO: 2023-06-28 장바구니 목록 StringBuilder에 저장
    private static void printBasket(String memberName, Iterator<Basket> it, StringBuilder sb) {
        while (it.hasNext()) {
            Basket basket = it.next();
            sb.append(memberName).append(" ").append(basket.getItemName())
                    .append(" ").append(basket.getItemPrice()).append("\n");
        }
    }

}
