package net.crunchdroid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author CrunchDroid
 */
@Controller
public class OnePageController {

    @Autowired
    MailComponent mailComponent;

    @GetMapping("/")
    public String index(@ModelAttribute Contact contact) {
        return "index";
    }

    @PostMapping("/")
    public String processContact(@Validated Contact contact, RedirectAttributes model, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "index";

        if (mailComponent.sendSimpleMail(contact)) {
            model.addFlashAttribute("classCss", "alert alert-success");
            model.addFlashAttribute("message", "Your message has been sent");
        } else {
            model.addFlashAttribute("classCss", "alert alert-warning");
            model.addFlashAttribute("message", "An unexpected error occurred thank you to repeat your request later");
        }

        return "redirect:/";
    }


}
