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
    public StringBuilder findAllMember() {
        StringBuilder sb = new StringBuilder();
        printMember(sb);
        return sb;
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
}
