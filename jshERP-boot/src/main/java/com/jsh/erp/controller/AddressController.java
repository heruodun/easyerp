package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.base.BaseController;
import com.jsh.erp.base.TableDataInfo;
import com.jsh.erp.datasource.entities.Address;
import com.jsh.erp.datasource.entities.AddressExample;
import com.jsh.erp.datasource.mappers.AddressMapper;
import com.jsh.erp.service.AddressService;
import com.jsh.erp.utils.Constants;
import com.jsh.erp.utils.StringUtil;
import com.jsh.erp.utils.ErpInfo;
import com.jsh.erp.utils.ResponseJsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jsh.erp.utils.ResponseJsonUtil.returnJson;
import static com.jsh.erp.utils.ResponseJsonUtil.returnStr;

@RestController
@RequestMapping(value = "/address")
@Api(tags = {"地址管理"})
public class AddressController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(AddressController.class);

    @Resource
    private AddressService addressService;
    @Resource
    private AddressMapper  addressMapper;

    @GetMapping(value = "/info")
    @ApiOperation(value = "根据id获取地址信息")
    public String getInfo(@RequestParam("id") Long id) throws Exception {
        Address address = addressService.getAddress(id);
        Map<String, Object> objectMap = new HashMap<>();
        if(address != null) {
            objectMap.put("info", address);
            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } else {
            return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
        }
    }

    @GetMapping(value = "/infoBySId")
    @ApiOperation(value = "根据id获取地址信息")
    public String infoBySId(@RequestParam("sId") Long infoBySId) throws Exception {
        List<Address> addressList = addressService.getAddressBySupplierId(infoBySId, true);
        Map<String, Object> objectMap = new HashMap<>();
        if(addressList != null) {
            objectMap.put("info", addressList);
            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } else {
            return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
        }
    }

    @GetMapping(value = "/list")
    @ApiOperation(value = "获取地址列表")
    public TableDataInfo getList(@RequestParam(value = Constants.SEARCH, required = false) String search) throws Exception {
        AddressExample addressExample = new AddressExample();
        addressExample.setOrderByClause("id desc");

        String place = StringUtil.getInfo(search, "place");
        String type = StringUtil.getInfo(search, "type");
        addressExample.createCriteria().andPlaceLike("%" + place + "%").andTypeLike("%" + type + "%");
        List<Address> list = addressMapper.selectByExample(addressExample);
        return getDataTable(list);
    }

    @PostMapping(value = "/add")
    @ApiOperation(value = "新增地址")
    public String addResource(@RequestBody Address address) throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        int count = addressService.checkIsPlaceAndTypeExist(address.getSupplierId(), address.getPlace(), address.getType());
        if(count > 0){
            return returnJson(objectMap, "地址已存在", ErpInfo.BAD_REQUEST.code);
        }
        int insert = addressService.insertAddress(address);
        return returnStr(objectMap, insert);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "修改地址")
    public String updateResource(@RequestBody Address address) throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        int update = addressService.updateAddress(address);
        return returnStr(objectMap, update);
    }

    @GetMapping(value = "/checkIsPlaceAndTypeExist")
    @ApiOperation(value = "检查名称和类型是否存在")
    public String checkIsPlaceAndTypeExist(@RequestParam Long supplierId,
                                          @RequestParam(value ="place", required = false) String place,
                                          @RequestParam(value ="type") String type,
                                          HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        int exist = addressService.checkIsPlaceAndTypeExist(supplierId, place, type);
        if(exist > 0) {
            objectMap.put("status", true);
        } else {
            objectMap.put("status", false);
        }
        return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
    }

    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "删除地址")
    public String deleteResource(@RequestParam("id") Long id) throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        int delete = addressService.deleteAddress(id);
        return returnStr(objectMap, delete);
    }

    @DeleteMapping(value = "/deleteBatch")
    @ApiOperation(value = "批量删除地址")
    public String batchDeleteResource(@RequestParam("ids") List<Long> ids) throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        int delete = addressService.batchDeleteAddressByIds(ids);
        return returnStr(objectMap, delete);
    }
}
