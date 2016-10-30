package net.crunchdroid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    MailComponent mail;

    @GetMapping("/")
    public String index(@ModelAttribute Contact contact) {
        return "index";
    }

    @PostMapping("/")
    public String processContact(@Validated Contact contact, RedirectAttributes redirectAttributes, Model model, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "index";

        if (!mail.sendHtmlMail(contact)) {
            model.addAttribute("success", false);
            model.addAttribute("message", "An unexpected error occurred thank you to repeat your request later");
            return "index";
        }

        redirectAttributes.addFlashAttribute("success", true);
        redirectAttributes.addFlashAttribute("message", "Your message has been sent");

        return "redirect:/";
    }


}
