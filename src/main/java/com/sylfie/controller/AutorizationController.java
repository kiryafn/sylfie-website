package com.sylfie.controller;

import com.sylfie.model.dto.UserLoginDTO;
import com.sylfie.model.dto.UserRegisterDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/autorization")
public class AutorizationController {


    @GetMapping
    public String getAutorization(@RequestParam(value = "type", defaultValue = "login") String type, Model model) {
        model.addAttribute("userLoginDTO", new UserLoginDTO());
        model.addAttribute("userRegisterDTO", new UserRegisterDTO());
        model.addAttribute("activeTab", type);    // "login" или "register"
        return "autorization";
    }

    @PostMapping("/login")
    public String doLogin(@Valid @ModelAttribute("userLoginDTO") UserLoginDTO loginDTO, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("activeTab", "login");
            model.addAttribute("userRegisterDTO", new UserRegisterDTO());
            return "redirect:/home";
        }
        // тут ваша логика аутентификации
        return "redirect:/home";
    }

    @PostMapping("/register")
    public String doRegister(
            @Valid @ModelAttribute("userRegisterDTO") UserRegisterDTO regDTO,
            BindingResult br,
            Model model
    ) {
        if (br.hasErrors()) {
            model.addAttribute("activeTab", "register");
            model.addAttribute("userLoginDTO", new UserLoginDTO());
            return "redirect:/home";
        }
        // тут ваша логика регистрации
        return "redirect:/home";
    }


}
