package member;

// TODO: 2023-06-27 회원 엔티티
public class Member {

//    private int id;
    private String name;
    private String nickName;
    private String phoneNumber;
    private String address;

    public Member() {};

    public Member(String name, String nickName, String phoneNumber, String address) {
//        this.id = id;
        this.name = name;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getName() {
        return this.name;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getNickName() {
        return nickName;
    }

    public String getAddress() {
        return address;
    }
//    public int getId() {
//        return this.id;
//    }
}
