package com.anthive.blogservice.basicsystem.accountsystem.base;

import com.anthive.blogservice.basicsystem.accountsystem.base.dto.AccountRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/login")
    public String login(){
        return "account/login";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("registerRequest", new AccountRegisterRequest());
        return "account/register";
    }

    @PostMapping("/register")
    public String register(@Validated @ModelAttribute("registerRequest") AccountRegisterRequest request,
                           BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/account/register";
        }
        accountService.register(request);
        return "redirect:/";
    }
}
