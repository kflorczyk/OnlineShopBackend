package pl.kflorczyk.onlineshopbackend.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue
    private long ID;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<UserAddress> addresses;

//    @OneToMany
//    @JoinColumn(name = "user_id")
//    private List<Order> orders;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    public User() {}

    public User(long ID, String email, String password) {
        this.ID = ID;
        this.email = email;
        this.password = password;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
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

    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}