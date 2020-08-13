package top.migod.pojo;

public class User {
    private int uid;
    private String username;
    private String email;
    private String password;
    private String nickname;
    private String self_intro;
    private String avatar_name;

    public User() {
    }

    public User(int uid, String username, String email, String password, String nickname, String self_intro, String avatar_name) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.self_intro = self_intro;
        this.avatar_name = avatar_name;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", self_intro='" + self_intro + '\'' +
                ", avatar_name='" + avatar_name + '\'' +
                '}';
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
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

    public String getSelf_intro() {
        return self_intro;
    }

    public void setSelf_intro(String self_intro) {
        this.self_intro = self_intro;
    }

    public String getAvatar_name() {
        return avatar_name;
    }

    public void setAvatar_name(String avatar_name) {
        this.avatar_name = avatar_name;
    }
}
