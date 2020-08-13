package top.migod.pojo;

public class TempInfo {
    private String check_code;
    private int type;
    private Integer uid;
    private String username;
    private String email;
    private String password;
    private String nickname;

    public TempInfo() {
    }

    public TempInfo(String check_code, int type, Integer uid, String username, String email, String password, String nickname) {
        this.check_code = check_code;
        this.type = type;
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public String getCheck_code() {
        return check_code;
    }

    public void setCheck_code(String check_code) {
        this.check_code = check_code;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "TempInfo{" +
                "check_code='" + check_code + '\'' +
                ", type=" + type +
                ", uid=" + uid +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
