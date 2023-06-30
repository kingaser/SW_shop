package member;

// TODO: 2023-06-27 회원 엔티티
public class Member {

    private int id;
    private String name;
    private String nickName;
    private String phoneNumber;
    private String address;

    public Member(int id, String name, String nickName, String phoneNumber, String address) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
