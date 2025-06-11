package com.sylfie.controller.mvc;

import com.sylfie.exception.EmailTakenException;
import com.sylfie.exception.UsernameTakenException;
import com.sylfie.dto.mvc.UserLoginDTO;
import com.sylfie.dto.mvc.UserRegisterDTO;
import com.sylfie.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthorizationController {


    private final UserService userService;

    public AuthorizationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("login")
    public String getLogin(Model model) {
        model.addAttribute("userLoginDTO", new UserLoginDTO());
        return "authorization/login";
    }

    @GetMapping("register")
    public String getRegister(Model model) {
        model.addAttribute("userRegisterDTO", new UserRegisterDTO());
        return "authorization/register";
    }

    @PostMapping("register")
    public String doRegister(@Valid @ModelAttribute("userRegisterDTO") UserRegisterDTO regDTO,
                             BindingResult br, Model model) {

        if (br.hasErrors()) {
            return "authorization/register";
        }

        try {
            userService.createFromDTO(regDTO);
            return "redirect:/login";
        } catch (UsernameTakenException e) {
            model.addAttribute("usernameError", e.getMessage());
        } catch (EmailTakenException e) {
            model.addAttribute("emailError", e.getMessage());
        }

        return "authorization/register";
    }

}
