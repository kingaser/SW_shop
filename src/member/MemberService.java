package member;

import java.io.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

public class MemberService {

    Scanner kb = new Scanner(System.in);
    File file = new File("C:\\Users\\gram15\\Desktop\\SWedu\\shop\\shop\\database\\memberList.txt");
    BufferedWriter memberListWriter;
    BufferedReader memberListReader = new BufferedReader(
            new FileReader(file, UTF_8)
    );
    private int signUp = 0;
    private LinkedHashMap<Integer, Member> memberList = new LinkedHashMap<>();

    //클래스 객체 생성 시 고객정보리스트에 입력, member이름의 텍스트파일 없을 시 파일 생성
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

    //고객관리 메뉴 선택
    public void inputMemberMenu() {
        while (true) {
            System.out.println("0. 이전 메뉴(진행 내용 저장)\n1. 회원 가입\n2. 회원 전체 조회\n3. 회원 수정\n4. 회원 탈퇴");
            int memberMenu = kb.nextInt();
            // 이전 메뉴로 돌아가면서 DB에 회원 정보 저장
            if (memberMenu == 0) {
                saveFile();
                break;
            // 회원 가입
            } else if (memberMenu == 1) {
                System.out.println("이름, 닉네임, 전화 번호, 주소를 입력해 주세요.");
                String userName = kb.next();
                String nickName = kb.next();
                String phoneNumber = kb.next();
                String address = kb.next();
                // 정보 입력 후 Member 객체로 생성
                Member member = new Member(1, userName, nickName, phoneNumber, address);
                addMember(member);
            // 회원 전체 조회
            } else if (memberMenu == 2) {
                findAllMember();
            // 회원 수정
            } else if (memberMenu == 3) {
                System.out.println("이름을 확인해 주세요.");
                String userName = kb.next();
                Member member;
                // 회원 확인 try-catch로 Null 예외 처리
                try {
                    member = checkMember(userName);
                    updateMember(member.getName());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            // 회원 탈퇴
            } else if (memberMenu == 4) {
                System.out.println("이름을 확인해 주세요.");
                String userName = kb.next();
                Member member;
                // 회원 확인 try-catch로 Null 예외 처리
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

    // 회원 가입
    public void addMember(Member member) {
        // Collections의 max 를 사용하여 키값(고유번호)중
        // 가장 큰값을 가져와 +1 해서 고유번호 값 자동 증가
        if (!memberList.isEmpty()) {
            signUp = Collections.max(memberList.keySet());
            member.setId(++signUp);
            memberList.put(signUp, member);
        } else {
            memberList.put(member.getId(), member);
        }
        System.out.println("회원 가입 완료");
    }

    // 회원 조회
    public Member findMember(String name) {
        Iterator<Member> memberIterator = memberList.values().iterator();
        // 입력된 고객이름과 고객정보리스트에
        // 저장된 고객이름과 일치하는지 확인
        while (memberIterator.hasNext()) {
            Member member = memberIterator.next();
            if (member.getName().equals(name)) {
                return member;
            }
        }
        return null;
    }

    // 회원 전체 조회
    public void findAllMember() {
        StringBuilder sb = new StringBuilder();
        printMember(sb);
        System.out.println(sb);
    }

    // 회원 수정

    public void updateMember(String name) {
        System.out.println("수정할 닉네임, 전화번호, 주소를 입력해 주세요.");

        Member findMember = findMember(name);

        String nickName = kb.next();
        String updatePhoneNumber = kb.next();
        String address = kb.next();

        // 수정된 회원 정보 Map에 저장
        memberList.put(findMember.getId(),
                new Member(findMember.getId(), findMember.getName(), nickName, updatePhoneNumber, address));

        System.out.println("수정이 완료되었습니다.");
    }

    // 회원 탈퇴
    public void deleteMember(String name) {
        // 입력 받은 이름에 맞는 회원 정보 찾고 회원 삭제
        Member findMember = findMember(name);
        memberList.remove(findMember.getId());

        System.out.println("회원 탈퇴 완료!!");
    }

    // 고객정보 리스트에 저장된 회원 목록 Map에 저장
    private void readMemberInputCheck() {
        try {
            String memberInfo;
            while ((memberInfo = memberListReader.readLine()) != null) {
                String[] tmp = memberInfo.split(" ");
                //고객정보 엔티티의 형식에 맞춰 member 객체 생성
                int id = Integer.parseInt(tmp[0]);
                signUp = id;
                String name = tmp[1];
                String nickName = tmp[2];
                String phoneNumber = tmp[3];
                String address = tmp[4];
                Member member = new Member(id, name, nickName, phoneNumber, address);
                //Map에 저장
                memberList.put(member.getId(), member);
            }
        } catch (IOException e) {
            System.out.println("회원 목록 조회 오류");
            e.printStackTrace();
        }
    }

    //포맷 맞추기용 메서드
    private void printMember(StringBuilder sb) {
        Iterator<Member> memberIterator = memberList.values().iterator();
        while (memberIterator.hasNext()) {
            Member member = memberIterator.next();
            sb.append(member.getId()).append(" ")
                    .append(member.getName()).append(" ")
                    .append(member.getNickName()).append(" ")
                    .append(member.getPhoneNumber()).append(" ")
                    .append(member.getAddress()).append("\n");
        }
    }

    // 입력한 이름과 회원 목록에 일치하는 회원유무 확인
    public Member checkMember(String name) {
        Member findMember = findMember(name);
        if (findMember == null) {
            System.out.println("입력하신 정보와 일치하는 회원이 없습니다.\n카테고리를 다시 선택해주세요.");
        }
        return findMember;
    }

    // 고객정보리스트 저장 메서드
    private void saveFile() {
        StringBuilder sb = new StringBuilder();
        //StringBuilder에 Map의 내용 포맷에 맞게 저장
        printMember(sb);
        //고객정보 리스트에 저장하기 위해 IOException이 발생할 수 있으므로, try-catch로 감싸줌
        try {
            new FileWriter(file).close();
            memberListWriter.append(sb.toString());
            memberListWriter.flush();
            memberListWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
