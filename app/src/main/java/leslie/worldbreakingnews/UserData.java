package leslie.worldbreakingnews;

/**
 * Created by Leslie on 5/21/2021.
 */

public class UserData {
    private String email;
    private String name;
    private String following_msg;

    public UserData(){
    }//empty constructor

    public UserData(String email,String name) {
        this.email = email;
        this.name = name;
    }
    public UserData(String following_msg) {
        this.following_msg = following_msg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getFollowing_msg() {
        return following_msg;
    }

    public void setFollowing_msg(String following_msg) {
        this.following_msg = following_msg;
    }


}

