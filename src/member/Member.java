package member;

// TODO: 2023-06-27 회원 엔티티
public class Member {

//    private int id;
    private String name;
    private String nickName;
    private String phoneNumber;
    private String address;

    public Member(String name, String nickName, String phoneNumber, String address) {
        this.name = name;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }
}
