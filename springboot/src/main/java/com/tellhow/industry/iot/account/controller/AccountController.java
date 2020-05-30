package com.tellhow.industry.iot.account.controller;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.account.service.AccountService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequiresPermissions("recognization:person")
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/list")
    public JSONObject getAccountList(@RequestBody JSONObject requestJson) {
        return accountService.getAccountList(requestJson);
    }

    @GetMapping("/sync")
    public JSONObject syncAccount() {
        return accountService.syncAccount();
    }
}
