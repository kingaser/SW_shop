package member;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.TooManyListenersException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class MemberService {

    // TODO: 2023-06-27 파일 / console 입출력
    Scanner kb = new Scanner(System.in);
    File file = new File("C:\\Users\\gram15\\Desktop\\SWedu\\shop\\shop\\memberList.txt");
    BufferedReader memberListReader = new BufferedReader(
            new FileReader(file, UTF_8)
    );
    BufferedWriter memberListWriter = new BufferedWriter(
            new FileWriter("C:\\Users\\gram15\\Desktop\\SWedu\\shop\\shop\\memberList.txt", true)
    );
    // TODO: 2023-06-27 멤버 변수
    private LinkedHashMap<String[], Member> memberList = new LinkedHashMap<>();
    private StringBuilder sb;

    public MemberService() throws IOException {}

    // TODO: 2023-06-27 회원 가입
    public void addMember(Member member) {
        memberList.put(new String[]{member.getName(), member.getPhoneNumber()}, member);
        String addMember = "\n" + member.getName() + ", " + member.getNickName() + ", "
                + member.getPhoneNumber() + ", " + member.getAddress();
        try {
            memberListWriter.write(addMember);
            System.out.println("회원 가입 완료!!");
            memberListWriter.flush();
            memberListWriter.close();
        } catch (IOException e) {
            System.out.println("회원 가입 에러");
            e.printStackTrace();
        }
    }

    public Member findMember(String name, String phoneNumber) {
        return memberList.get(new String[]{name, phoneNumber});
    }

    // TODO: 2023-06-27 회원 전체 조회
    public StringBuilder findAllMember() {
        ReadInputCheck();
        return sb;
    }

    // TODO: 2023-06-27 회원 수정
    public void updateMember(String name, String phoneNumber) {
        System.out.println("수정할 닉네임, 전화번호, 주소를 입력해 주세요.");
        String nickName = kb.next();
        String updatePhoneNumber = kb.next();
        String address = kb.next();
        memberList.put(new String[]{name, phoneNumber}, new Member(name, nickName, updatePhoneNumber, address));
        System.out.println("수정이 완료되었습니다.");
    }

    private void ReadInputCheck() {
        sb = new StringBuilder();
        try {
            String memberInfo;
            while ((memberInfo = memberListReader.readLine()) != null) {
                String[] tmp = memberInfo.split(", ");
                String name = tmp[0];
                String nickName = tmp[1];
                String phoneNumber = tmp[2];
                String address = tmp[3];
                Member member = new Member(name, nickName, phoneNumber, address);
                memberList.put(new String[]{name, phoneNumber}, member);
                sb.append(member.getName()).append(" ").append(nickName)
                        .append(" ").append(phoneNumber).append(" ").append(address).append("\n");
            }
        } catch (IOException e) {
            System.out.println("회원 목록 조회 오류");
            e.printStackTrace();
        }
    }
}
