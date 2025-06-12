package com.sylfie.controller.mvc;

import com.sylfie.exception.InsufficientBalanceException;
import com.sylfie.security.CustomUserDetails;
import com.sylfie.service.BookingService;
import com.sylfie.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.security.Principal;

@Controller
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;
    private final UserService userService;

    public BookingController(BookingService bookingService, UserService userService) {
        this.bookingService = bookingService;
        this.userService = userService;
    }

    @PostMapping("/tour/{id}")
    public String bookTour(@AuthenticationPrincipal CustomUserDetails principal, @PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            bookingService.bookTour(id, principal.getName());
            redirectAttributes.addFlashAttribute("success", "Бронирование успешно!");
        } catch (InsufficientBalanceException e) {
            redirectAttributes.addFlashAttribute("error", "Недостаточно средств!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при бронировании");
        }
        return "redirect:/profile/history";
    }
}