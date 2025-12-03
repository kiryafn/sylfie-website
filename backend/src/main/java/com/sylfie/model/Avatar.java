package com.sylfie.model;

import jakarta.persistence.*;

@Entity
public class Avatar{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "avatar", cascade = CascadeType.PERSIST)
    private User user;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Picture picture;

    public Avatar() {}

    public Avatar(Picture picture) {
        this.picture = picture;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if (user != null && user.getAvatar() != this) {
            user.setAvatar(this);
        }
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}