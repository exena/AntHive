package com.anthive.blogservice.accountsystem.base;

import com.anthive.blogservice.accountsystem.base.dto.AccountRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
        if(accountService.isDuplicatedId(request.getLoginId())){
            bindingResult.addError(new FieldError("registerRequest", "loginId", "중복된 ID 입니다."));
        }
        if(bindingResult.hasErrors()){
            return "/account/register";
        }
        accountService.register(request);
        return "redirect:/";
    }
}
