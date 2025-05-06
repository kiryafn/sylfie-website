package com.sylfie.model.entity;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("AVATAR")
public class Avatar extends Picture {

    @OneToOne(mappedBy = "avatar", cascade = CascadeType.ALL, orphanRemoval = true)
    private User user;

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