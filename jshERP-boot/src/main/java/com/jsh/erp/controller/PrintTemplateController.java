package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.base.BaseController;
import com.jsh.erp.base.TableDataInfo;
import com.jsh.erp.datasource.entities.Account;
import com.jsh.erp.datasource.entities.PrintTemplate;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.vo.AccountVo4InOutList;
import com.jsh.erp.datasource.vo.AccountVo4List;
import com.jsh.erp.service.AccountService;
import com.jsh.erp.service.PrintTemplateService;
import com.jsh.erp.service.SystemConfigService;
import com.jsh.erp.service.UserService;
import com.jsh.erp.utils.BaseResponseInfo;
import com.jsh.erp.utils.Constants;
import com.jsh.erp.utils.ErpInfo;
import com.jsh.erp.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jsh.erp.utils.ResponseJsonUtil.returnJson;
import static com.jsh.erp.utils.ResponseJsonUtil.returnStr;

/**
 * @author jishenghua 75271*8920
 */
@RestController
@RequestMapping(value = "/print")
@Api(tags = {"打印管理"})
public class PrintTemplateController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(PrintTemplateController.class);

    @Resource
    private PrintTemplateService printTemplateService;
    @Resource
    private UserService userService;

    @Resource
    private SystemConfigService systemConfigService;

    @GetMapping(value = "/findBillPrintTemplate")
    @ApiOperation(value = "根据打印类型获取信息")
    public String findBillPrintTemplate(@RequestParam("printType") String printType,
                          HttpServletRequest request) throws Exception {

        User user = userService.getCurrentUser();
        Long tenantId = user.getTenantId();
        PrintTemplate printTemplate = printTemplateService.getTemplate(printType, tenantId);
        Map<String, Object> objectMap = new HashMap<>();
        if(printTemplate == null) {
            printTemplate = new PrintTemplate();
        }
        objectMap.put("info", printTemplate);
        return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
    }



    @PostMapping(value = "/upsertBillPrintTemplate")
    @ApiOperation(value = "新增或者更新")
    public String upsertBillPrintTemplate(@RequestBody JSONObject obj, HttpServletRequest request)throws Exception {
        User user = userService.getCurrentUser();
        Long tenantId = user.getTenantId();
        Map<String, Object> objectMap = new HashMap<>();
        PrintTemplate printTemplate = JSONObject.parseObject(obj.toJSONString(), PrintTemplate.class);
        logger.info("打印模板打印类型："+printTemplate.getPrintType() + "租户ID："+printTemplate.getTenantId()
        );
        if(printTemplate.getTenantId() != tenantId || printTemplate.getPrintType() == null){
            return returnJson(objectMap, ErpInfo.BAD_REQUEST.name, ErpInfo.BAD_REQUEST.code);
        }
        int effectRows = printTemplateService.upsertTemplate(printTemplate);
        boolean insert = effectRows > 0;
        return returnStr(objectMap, insert?  1 : 0);
    }

}
