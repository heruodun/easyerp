package com.jsh.erp.service;

import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.Address;
import com.jsh.erp.datasource.entities.AddressExample;
import com.jsh.erp.datasource.entities.Supplier;
import com.jsh.erp.datasource.entities.SupplierExample;
import com.jsh.erp.datasource.mappers.AddressMapper;
import com.jsh.erp.exception.JshException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AddressService {
    private Logger logger = LoggerFactory.getLogger(AddressService.class);

    @Resource
    private AddressMapper addressMapper;

    // 查询单条
    public Address getAddress(Long id) {
        try {
            return addressMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            JshException.readFail(logger, e);
            return null;
        }
    }

    // 根据suppierId查询单条
    public List<Address> getAddressBySupplierId (Long supplierId, boolean containDeleted) {
        AddressExample example = new AddressExample();
        AddressExample.Criteria criteria = example.createCriteria().andSupplierIdEqualTo(supplierId);
        // 如果有 deleteFlag 字段，可以加未删除条件
        if(!containDeleted) {
            criteria.andDeletedFlagNotEqualTo("1");
        }

        try {
            return addressMapper.selectByExample(example);
        } catch (Exception e) {
            JshException.readFail(logger, e);
            return null;
        }
    }

    public int checkIsPlaceAndTypeExist(Long supplierId, String place, String type)throws Exception {
        place = place == null? "": place;
        AddressExample example = new AddressExample();
        example.createCriteria().andSupplierIdEqualTo(supplierId).andPlaceEqualTo(place).andTypeEqualTo(type)
                .andDeletedFlagEqualTo(BusinessConstants.DELETE_FLAG_EXISTS);
        List<Address> list=null;
        try{
            list= addressMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list==null?0:list.size();
    }

    // 查询全部
    public List<Address> getAddressList() {
        AddressExample example = new AddressExample();
        // 如果有 deleteFlag 字段，可以加未删除条件
        // example.createCriteria().andDeletedFlagNotEqualTo("1");
        try {
            return addressMapper.selectByExample(example);
        } catch (Exception e) {
            JshException.readFail(logger, e);
            return null;
        }
    }

    // 新增
    @Transactional
    public int insertAddress(Address address) {
        try {
            return addressMapper.insertSelective(address);
        } catch (Exception e) {
            JshException.writeFail(logger, e);
            return 0;
        }
    }

    // 更新
    @Transactional
    public int updateAddress(Address address) {
        try {
            return addressMapper.updateByPrimaryKeySelective(address);
        } catch (Exception e) {
            JshException.writeFail(logger, e);
            return 0;
        }
    }

    // 删除
    @Transactional
    public int deleteAddress(Long id) {
        try {
            return addressMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            JshException.writeFail(logger, e);
            return 0;
        }
    }

    // 批量删除（逻辑删除）
    @Transactional
    public int batchDeleteAddressByIds(List<Long> ids) {
        int result = 0;
        try {
            for (Long id : ids) {
                Address address = new Address();
                address.setId(id);
                address.setDeletedFlag(1);
                result += addressMapper.updateByPrimaryKeySelective(address);
            }
        } catch (Exception e) {
            JshException.writeFail(logger, e);
        }
        return result;
    }

}
