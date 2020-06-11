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
import com.tellhow.industry.iot.util.constants.Constants;
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
import java.util.Date;
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
        logger.debug("saveOrUpdateVisitor");
        boolean isNew = StringUtils.isEmpty(account.id);
        logger.debug("isNew:" + isNew);
        if (isNew) {
            account.id = UUID.randomUUID().toString();
        }
        logger.debug("account.id:" + account.id);
        if (faceImg != null) {
            String avatarDir = faceDir;
            if (!avatarDir.endsWith("/") && !avatarDir.endsWith("\\")) {
                avatarDir = avatarDir + "/";
            }
            avatarDir = avatarDir + "visitor";
            if (!FileUtils.uploadFace(faceImg, avatarDir, account.id)) {
                return CommonUtil.errorJson(Constants.ERROR_500, "头像上传失败");
            }
            account.facePic = account.id + ".jpg";
        } else {
            logger.debug("头像未变更");
        }
        List<ElasticsearchApi.Account> accountList = new ArrayList<>();
        accountList.add(account);
        JSONObject jsonObject = accountService.saveOrUpdateUser(accountList);
        if (!Constants.SUCCESS_CODE.equals(jsonObject.getString("code"))) {
            return jsonObject;
        }
        return gatewayPolicyService.addGatewayPolicyForVisitor(account);
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
