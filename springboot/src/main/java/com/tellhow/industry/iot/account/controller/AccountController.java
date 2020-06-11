package com.tellhow.industry.iot.account.controller;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.account.model.BatchDeleteAccountRequest;
import com.tellhow.industry.iot.account.model.IAMAccount;
import com.tellhow.industry.iot.account.service.AccountService;
import com.tellhow.industry.iot.account.service.impl.AccountServiceImpl;
import com.tellhow.industry.iot.elasticsearch.ElasticsearchApi;
import com.tellhow.industry.iot.gateway.service.GatewayPolicyService;
import com.tellhow.industry.iot.util.CommonUtil;
import com.tellhow.industry.iot.util.FileUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//@RequiresPermissions("face:person")
@RestController
@RequestMapping("/account")
public class AccountController {

    private Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private GatewayPolicyService gatewayPolicyService;

    @Value("${web.faceDir}")
    private String faceDir;

    @PostMapping("/list")
    public JSONObject getAccountList(@RequestBody JSONObject requestJson) {
        return accountService.getAccountList(requestJson);
    }

    @GetMapping("/sync")
    public JSONObject syncAccount() {
        return accountService.syncAccount();
    }

    @GetMapping("/{accountId}")
    public JSONObject getAccountInfo(@PathVariable String accountId) {
        return accountService.getAccountInfo(accountId);
    }

    @PostMapping("/saveOrUpdateAccount")
    public JSONObject saveOrUpdateAccount(ElasticsearchApi.Account iamAccount, @RequestParam("faceImg") MultipartFile file) {
//        FileUtils.uploadFace(file, faceDir, iamAccount.id);
//        logger.debug("orgId:" + iamAccount.orgId);
        //TODO
        return CommonUtil.successJson();
    }

    @PostMapping("/saveOrUpdateVisitor")
    public JSONObject saveOrUpdateVisitor(ElasticsearchApi.Account account, @RequestParam("faceImg") MultipartFile faceImg) {
        if (StringUtils.isEmpty(account.id)) {
            account.id = UUID.randomUUID().toString();
        }
        if (faceImg != null) {
            FileUtils.uploadFace(faceImg, faceDir, account.id);
            account.facePic = account.id;
        }
        List<ElasticsearchApi.Account> accountList = new ArrayList<>();
        accountList.add(account);
        accountService.saveOrUpdateUser(accountList);
        //TODO 添加门禁权限
        return CommonUtil.successJson();
    }

    @PostMapping("/saveOrUpdateUser")
    public JSONObject saveOrUpdateUser(@RequestBody List<IAMAccount> iamAccountList) {
        if (iamAccountList == null) {
            return CommonUtil.successJson();
        }
        List<ElasticsearchApi.Account> accountList = new ArrayList<>();
        for (IAMAccount account : iamAccountList) {
            accountList.add(new ElasticsearchApi.Account(account));
        }
        return accountService.saveOrUpdateUser(accountList);
    }

    @PostMapping("/deleteUsers")
    public JSONObject deleteUsers(@RequestBody BatchDeleteAccountRequest batchDeleteAccountRequest) {
        return accountService.deleteUsers(batchDeleteAccountRequest.deleteUsers);
    }
}
