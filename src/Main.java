import member.Member;
import member.MemberService;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner kb = new Scanner(System.in);
        MemberService memberService = new MemberService();

        System.out.println("스포츠 몰\n");

        while (true) {
            System.out.print("0. 스포츠 몰 종료\n1. 쇼핑몰\n2. 고객 관리\n3. 제품 관리\n어떤 메뉴를 선택하시겠습니까?\n");
            int category = kb.nextInt();
            if (category == 0) {
                System.out.println("스포츠 몰을 종료합니다.");
                break;
            } else if (category == 1) {
                while (true) {
                    System.out.print("0. 처음 메뉴\n1. 장바구니\n2. 구매\n3. 구매 목록");
                    int shoppingMenu = kb.nextInt();
                    if (shoppingMenu == 0) {
                        System.out.println("처음 메뉴로 돌아갑니다.");
                        break;
                    } else if (shoppingMenu == 1) {
                        System.out.println("장바구니");
                    } else if (shoppingMenu == 2) {
                        System.out.println("아이템");
                    } else if (shoppingMenu == 3) {
                        System.out.println("구매 목록");
                    } else {
                        System.out.println("잘못된 입력입니다.\n다시 입력해주세요.");
                    }
                }
            } else if (category == 2) {
                while (true) {
                    System.out.println("0. 처음 메뉴\n1. 회원 가입\n2. 회원 수정\n3. 회원 탈퇴\n4. 회원 전체 조회");
                    int memberMenu = kb.nextInt();
                    if (memberMenu == 0)
                        break;
                    else if (memberMenu == 1) {
                        System.out.println("이름, 닉네임, 전화 번호, 주소를 입력해 주세요.");
                        String name = kb.next();
                        String nickName = kb.next();
                        String phoneNumber = kb.next();
                        String address = kb.next();
                        Member member = new Member(name, nickName, phoneNumber, address);
                        memberService.addMember(member);
                    } else if (memberMenu == 2) {
                        System.out.println("이름을 확인해 주세요.");
                        String name = kb.next();
                        Member member;
                        try {
                            member = checkMember(memberService, name);
                            memberService.updateMember(member.getName());
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    } else if (memberMenu == 3) {
                        System.out.println("이름을 확인해 주세요.");
                        String name = kb.next();
                        Member member;
                        try {
                            member = checkMember(memberService, name);
                            memberService.deleteMember(member.getName());
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    } else if (memberMenu == 4) {
                        StringBuilder sb = memberService.findAllMember();
                        System.out.println(sb);
                    } else {
                        System.out.println("잘못된 입력입니다.\n다시 입력해주세요.");
                    }
                }
            } else if (category == 3) {

            } else {
                System.out.println("잘못된 입력입니다.\n다시 입력해주세요.");
            }
        }
    }

    // TODO: 2023-06-28 입력한 이름과 회원 목록에 일치하는 회원유무 확인
    private static Member checkMember(MemberService memberService, String name) {
        Member findMember = memberService.findMember(name);
        if (findMember == null) {
            System.out.println("입력하신 정보와 일치하는 회원이 없습니다.\n카테고리를 다시 선택해주세요.");
        }
        return findMember;
    }
}