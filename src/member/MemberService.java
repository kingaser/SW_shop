package member;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

public class MemberService {

    // TODO: 2023-06-27 파일 / console 입출력
    Scanner kb = new Scanner(System.in);
    File file = new File("C:\\Users\\gram15\\Desktop\\SWedu\\shop\\shop\\memberList.txt");
    BufferedReader memberListReader = new BufferedReader(
            new FileReader(file, UTF_8)
    );
    BufferedWriter memberListWriter = new BufferedWriter(
            new FileWriter(file, true)
    );
    // TODO: 2023-06-27 멤버 변수
    private LinkedHashMap<String, Member> memberList = new LinkedHashMap<>();

    public MemberService() throws IOException {
    }

    // TODO: 2023-06-27 회원 가입
    public void addMember(Member member) {
        StringBuilder sb = new StringBuilder();
        memberList.put(member.getName(), member);
        sb.append(member.getName()).append(" ").append(member.getNickName())
                .append(" ").append(member.getPhoneNumber()).append(" ").append(member.getAddress()).append("\n");
        try {
            memberListWriter.append(sb.toString());
            System.out.println("회원 가입 완료!!");
            memberListWriter.flush();
            memberListWriter.close();
        } catch (IOException e) {
            System.out.println("회원 가입 에러");
            e.printStackTrace();
        }
    }

    // TODO: 2023-06-28 회원 조회
    public Member findMember(String name) {
        memberList = readInputCheck();
        return memberList.get(name);
    }

    // TODO: 2023-06-27 회원 전체 조회
    public StringBuilder findAllMember() {
        StringBuilder sb = new StringBuilder();
        memberList = readInputCheck();
        Iterator<Member> it = memberList.values().iterator();
        printMember(it, sb);
        return sb;
    }

    // TODO: 2023-06-27 회원 수정

    public void updateMember(String name) {
        System.out.println("수정할 닉네임, 전화번호, 주소를 입력해 주세요.");

        memberList = readInputCheck();

        String nickName = kb.next();
        String updatePhoneNumber = kb.next();
        String address = kb.next();

        memberList.put(name, new Member(name, nickName, updatePhoneNumber, address));

        StringBuilder sb = new StringBuilder();
        memberList = readInputCheck();
        Iterator<Member> it = memberList.values().iterator();
        printMember(it, sb);
        // 파일 내용 지우고 덮어쓰기
        try {
            new FileWriter(file).close();
            memberListWriter.write(sb.toString());
            memberListWriter.flush();
            memberListWriter.close();
        } catch (IOException e) {
            System.out.println("회원 수정 오류");
            e.printStackTrace();
            return;
        }

        System.out.println("수정이 완료되었습니다.");
    }
    // TODO: 2023-06-28 회원 탈퇴

    public void deleteMember(String name) {
        memberList = readInputCheck();
        memberList.remove(name);

        StringBuilder sb = new StringBuilder();
        Iterator<Member> it = memberList.values().iterator();
        printMember(it, sb);
        // 파일 내용 지우고 덮어쓰기
        try {
            new FileWriter(file).close();
            memberListWriter.write(sb.toString());
            memberListWriter.flush();
            memberListWriter.close();
        } catch (IOException e) {
            System.out.println("회원 삭제 오류");
            e.printStackTrace();
            return;
        }

        System.out.println("회원 탈퇴 완료!!");
    }

    // TODO: 2023-06-28 읽어온 파일 Map에 저장
    private LinkedHashMap<String, Member> readInputCheck() {
        try {
            String memberInfo;
            while ((memberInfo = memberListReader.readLine()) != null) {
                String[] tmp = memberInfo.split(" ");
                String name = tmp[0];
                String nickName = tmp[1];
                String phoneNumber = tmp[2];
                String address = tmp[3];
                Member member = new Member(name, nickName, phoneNumber, address);
                memberList.put(name, member);
            }
        } catch (IOException e) {
            System.out.println("회원 목록 조회 오류");
            e.printStackTrace();
        }

        return memberList;
    }

    // TODO: 2023-06-28 회원 목록 StringBuilder에 저장
    private static void printMember(Iterator<Member> it, StringBuilder sb) {
        while (it.hasNext()) {
            Member member = it.next();
            sb.append(member.getName()).append(" ").append(member.getNickName())
                    .append(" ").append(member.getPhoneNumber()).append(" ").append(member.getAddress()).append("\n");
        }
    }
}
