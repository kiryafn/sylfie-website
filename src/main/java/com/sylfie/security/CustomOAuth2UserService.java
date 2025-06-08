package com.sylfie.security;

import com.sylfie.model.User;
import com.sylfie.service.RoleService;
import com.sylfie.service.UserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;
    private final RoleService roleService;

    public CustomOAuth2UserService(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo userInfo = extractUserInfo(registrationId, oAuth2User.getAttributes());

        User user = userService.createFromOAuth2(userInfo);

        return new CustomUserDetails(user, oAuth2User.getAttributes());
    }

    private String getRegistrationId(OAuth2UserRequest userRequest) {
        return userRequest.getClientRegistration().getRegistrationId();
    }

    private OAuth2UserInfo extractUserInfo(String registrationId, Map<String, Object> attributes) {
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String sub = (String) attributes.get("sub");

        return new OAuth2UserInfo(email, name, sub, registrationId);
    }
}