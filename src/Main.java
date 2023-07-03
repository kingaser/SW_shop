import adminitem.AdminItemService;
import member.MemberService;
import basket.BasketService;
import purchase.PurchaseService;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner kb = new Scanner(System.in);
        MemberService memberService = new MemberService();
        AdminItemService adminItemService = new AdminItemService();
        BasketService basketService = new BasketService();
        PurchaseService purchaseService = new PurchaseService();

        System.out.println("스포츠 몰\n");

        while (true) {
            System.out.print("0. 스포츠 몰 종료\n1. 쇼핑몰\n2. 고객 관리\n3. 상품 관리\n어떤 메뉴를 선택하시겠습니까?\n");
            int category = kb.nextInt();
            if (category == 0) {
                System.out.println("스포츠 몰을 종료합니다.");
                break;
            } else if (category == 1) {
                while (true) {
                    System.out.println("0. 이전 메뉴\n1. 장바구니\n2. 구매\n3. 구매 목록");
                    int shoppingMenu = kb.nextInt();
                    if (shoppingMenu == 0) {
                        basketService.saveBasketFile();
                        System.out.println("이전 메뉴로 돌아갑니다.");
                        break;
                    } else if (shoppingMenu == 1) {
                        basketService.inputBasketMenu();
                    } else if (shoppingMenu == 2) {
                        purchaseService.inputPurchaseMenu();
                    } else if (shoppingMenu == 3) {
                        System.out.println("이름을 입력해주세요.");
                        String userName = kb.next();
                        purchaseService.findAllPurchase(userName);
                    } else {
                        System.out.println("잘못된 입력입니다.\n다시 입력해주세요.");
                    }
                }
            } else if (category == 2) {
                memberService.inputMemberMenu();
            } else if (category == 3) {
                adminItemService.choiceNumber();
            } else {
                System.out.println("잘못된 입력입니다.\n다시 입력해주세요.");
            }
        }
    }
}