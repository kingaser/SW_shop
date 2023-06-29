package member;

import java.io.*;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;

public class MemberService {

    // TODO: 2023-06-27 파일 / console 입출력
    Scanner kb = new Scanner(System.in);
    File file = new File("C:\\Users\\gram15\\Desktop\\SWedu\\shop\\shop\\database\\memberList.txt");
    BufferedWriter memberListWriter;
    BufferedReader memberListReader = new BufferedReader(
            new FileReader(file, UTF_8)
    );
    // TODO: 2023-06-27 멤버 변수
    private LinkedHashMap<Integer, Member> memberList = new LinkedHashMap<>();
    private int singUpId = 0;

    public MemberService() throws IOException {
        readMemberInputCheck();
        try {
            memberListWriter = new BufferedWriter(
                    new FileWriter(file, true)
            );
            memberListWriter.write("");
            memberListWriter.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void inputMemberMenu() {
        while (true) {
            System.out.println("0. 이전 메뉴(진행 내용 저장)\n1. 회원 가입\n2. 회원 전체 조회\n3. 회원 수정\n4. 회원 탈퇴");
            int memberMenu = kb.nextInt();
            if (memberMenu == 0) {
                saveFile();
                break;
            } else if (memberMenu == 1) {
                System.out.println("이름, 닉네임, 전화 번호, 주소를 입력해 주세요.");
                String userName = kb.next();
                String nickName = kb.next();
                String phoneNumber = kb.next();
                String address = kb.next();
                Member member = new Member(1, userName, nickName, phoneNumber, address);
                addMember(member);
            } else if (memberMenu == 2) {
                findAllMember();
            } else if (memberMenu == 3) {
                System.out.println("이름을 확인해 주세요.");
                String userName = kb.next();
                Member member;
                try {
                    member = checkMember(userName);
                    updateMember(member.getName());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (memberMenu == 4) {
                System.out.println("이름을 확인해 주세요.");
                String userName = kb.next();
                Member member;
                try {
                    member = checkMember(userName);
                    deleteMember(member.getName());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("잘못된 입력입니다.\n다시 입력해주세요.");
            }
        }
    }

    // TODO: 2023-06-27 회원 가입
    public void addMember(Member member) {
        if (!memberList.isEmpty()) {
            member.setId(++singUpId);
            memberList.put(singUpId, member);
        } else {
            memberList.put(member.getId(), member);
        }
    }

    // TODO: 2023-06-28 회원 조회
    public Member findMember(String name) {
        Iterator<Member> memberIterator = memberList.values().iterator();
        while (memberIterator.hasNext()) {
            Member member = memberIterator.next();
            if (member.getName().equals(name)) {
                return member;
            }
        }
        return null;
    }

    // TODO: 2023-06-27 회원 전체 조회
    public void findAllMember() {
        StringBuilder sb = new StringBuilder();
        printMember(sb);
        System.out.println(sb);
    }

    // TODO: 2023-06-27 회원 수정

    public void updateMember(String name) {
        System.out.println("수정할 닉네임, 전화번호, 주소를 입력해 주세요.");

        Member findMember = findMember(name);

        String nickName = kb.next();
        String updatePhoneNumber = kb.next();
        String address = kb.next();

        memberList.put(findMember.getId(),
                new Member(findMember.getId(), findMember.getName(), nickName, updatePhoneNumber, address));

        System.out.println("수정이 완료되었습니다.");
    }
    // TODO: 2023-06-28 회원 탈퇴

    public void deleteMember(String name) {
        Member findMember = findMember(name);
        memberList.remove(findMember.getId());

        System.out.println("회원 탈퇴 완료!!");
    }

    // TODO: 2023-06-28 읽어온 파일 Map에 저장
    private void readMemberInputCheck() {
        try {
            String memberInfo;
            while ((memberInfo = memberListReader.readLine()) != null) {
                String[] tmp = memberInfo.split(" ");
                int id = Integer.parseInt(tmp[0]);
                singUpId = id;
                String name = tmp[1];
                String nickName = tmp[2];
                String phoneNumber = tmp[3];
                String address = tmp[4];
                Member member = new Member(id, name, nickName, phoneNumber, address);
                memberList.put(member.getId(), member);
            }
        } catch (IOException e) {
            System.out.println("회원 목록 조회 오류");
            e.printStackTrace();
        }
    }

    private void printMember(StringBuilder sb) {
        Iterator<Member> memberIterator = memberList.values().iterator();
        while (memberIterator.hasNext()) {
            Member member = memberIterator.next();
            sb.append(member.getId()).append(" ").append(member.getName()).append(" ").append(member.getNickName())
                    .append(" ").append(member.getPhoneNumber()).append(" ").append(member.getAddress()).append("\n");
        }
    }

    public void saveFile() {
        StringBuilder sb = new StringBuilder();
        printMember(sb);
        try {
            new FileWriter(file).close();
            memberListWriter.write(sb.toString());
            memberListWriter.flush();
            memberListWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: 2023-06-28 입력한 이름과 회원 목록에 일치하는 회원유무 확인
    private Member checkMember(String name) {
        Member findMember = findMember(name);
        if (findMember == null) {
            System.out.println("입력하신 정보와 일치하는 회원이 없습니다.\n카테고리를 다시 선택해주세요.");
        }
        return findMember;
    }
}
