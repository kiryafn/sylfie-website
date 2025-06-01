package com.sylfie.model;

import jakarta.persistence.*;

@Entity
public class Avatar{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "avatar", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private User user;

    @OneToOne
    private Picture picture;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if (user != null && user.getAvatar() != this) {
            user.setAvatar(this);
        }
    }
}