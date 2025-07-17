package com.jsh.erp.datasource.entities;

import java.util.ArrayList;
import java.util.List;

public class AddressExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AddressExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }
    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }
    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }
    protected Criteria createCriteriaInternal() {
        return new Criteria();
    }
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    // 这里只保留 Address 字段的条件方法
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;
        protected GeneratedCriteria() { criteria = new ArrayList<>(); }
        public boolean isValid() { return criteria.size() > 0; }
        public List<Criterion> getAllCriteria() { return criteria; }
        public List<Criterion> getCriteria() { return criteria; }

        protected void addCriterion(String condition) {
            if (condition == null) throw new RuntimeException("Value for condition cannot be null");
            criteria.add(new Criterion(condition));
        }
        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) throw new RuntimeException("Value for " + property + " cannot be null");
            criteria.add(new Criterion(condition, value));
        }
        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null)
                throw new RuntimeException("Between values for " + property + " cannot be null");
            criteria.add(new Criterion(condition, value1, value2));
        }

        // id
        public Criteria andIdIsNull() { addCriterion("address_id is null"); return (Criteria) this; }
        public Criteria andIdIsNotNull() { addCriterion("address_id is not null"); return (Criteria) this; }
        public Criteria andIdEqualTo(Long value) { addCriterion("address_id =", value, "id"); return (Criteria) this; }
        public Criteria andIdNotEqualTo(Long value) { addCriterion("address_id <>", value, "id"); return (Criteria) this; }
        public Criteria andIdIn(List<Long> values) { addCriterion("address_id in", values, "id"); return (Criteria) this; }
        public Criteria andIdNotIn(List<Long> values) { addCriterion("address_id not in", values, "id"); return (Criteria) this; }
        public Criteria andIdBetween(Long value1, Long value2) { addCriterion("address_id between", value1, value2, "id"); return (Criteria) this; }
        public Criteria andIdNotBetween(Long value1, Long value2) { addCriterion("address_id not between", value1, value2, "id"); return (Criteria) this; }

        // supplierId
        public Criteria andSupplierIdIsNull() { addCriterion("supplier_id is null"); return (Criteria) this; }
        public Criteria andSupplierIdIsNotNull() { addCriterion("supplier_id is not null"); return (Criteria) this; }
        public Criteria andSupplierIdEqualTo(Long value) { addCriterion("supplier_id =", value, "supplierId"); return (Criteria) this; }
        public Criteria andSupplierIdNotEqualTo(Long value) { addCriterion("supplier_id <>", value, "supplierId"); return (Criteria) this; }
        public Criteria andSupplierIdIn(List<Long> values) { addCriterion("supplier_id in", values, "supplierId"); return (Criteria) this; }
        public Criteria andSupplierIdNotIn(List<Long> values) { addCriterion("supplier_id not in", values, "supplierId"); return (Criteria) this; }
        public Criteria andSupplierIdBetween(Long value1, Long value2) { addCriterion("supplier_id between", value1, value2, "supplierId"); return (Criteria) this; }
        public Criteria andSupplierIdNotBetween(Long value1, Long value2) { addCriterion("supplier_id not between", value1, value2, "supplierId"); return (Criteria) this; }


        // place
        public Criteria andPlaceIsNull() { addCriterion("place is null"); return (Criteria) this; }
        public Criteria andPlaceIsNotNull() { addCriterion("place is not null"); return (Criteria) this; }
        public Criteria andPlaceEqualTo(String value) { addCriterion("place =", value, "place"); return (Criteria) this; }
        public Criteria andPlaceNotEqualTo(String value) { addCriterion("place <>", value, "place"); return (Criteria) this; }
        public Criteria andPlaceLike(String value) { addCriterion("place like", value, "place"); return (Criteria) this; }
        public Criteria andPlaceNotLike(String value) { addCriterion("place not like", value, "place"); return (Criteria) this; }
        public Criteria andPlaceIn(List<String> values) { addCriterion("place in", values, "place"); return (Criteria) this; }
        public Criteria andPlaceNotIn(List<String> values) { addCriterion("place not in", values, "place"); return (Criteria) this; }
        public Criteria andPlaceBetween(String value1, String value2) { addCriterion("place between", value1, value2, "place"); return (Criteria) this; }
        public Criteria andPlaceNotBetween(String value1, String value2) { addCriterion("place not between", value1, value2, "place"); return (Criteria) this; }

        // coordinates
        public Criteria andCoordinatesIsNull() { addCriterion("coordinates is null"); return (Criteria) this; }
        public Criteria andCoordinatesIsNotNull() { addCriterion("coordinates is not null"); return (Criteria) this; }
        public Criteria andCoordinatesEqualTo(String value) { addCriterion("coordinates =", value, "coordinates"); return (Criteria) this; }
        public Criteria andCoordinatesNotEqualTo(String value) { addCriterion("coordinates <>", value, "coordinates"); return (Criteria) this; }
        public Criteria andCoordinatesLike(String value) { addCriterion("coordinates like", value, "coordinates"); return (Criteria) this; }
        public Criteria andCoordinatesNotLike(String value) { addCriterion("coordinates not like", value, "coordinates"); return (Criteria) this; }
        public Criteria andCoordinatesIn(List<String> values) { addCriterion("coordinates in", values, "coordinates"); return (Criteria) this; }
        public Criteria andCoordinatesNotIn(List<String> values) { addCriterion("coordinates not in", values, "coordinates"); return (Criteria) this; }
        public Criteria andCoordinatesBetween(String value1, String value2) { addCriterion("coordinates between", value1, value2, "coordinates"); return (Criteria) this; }
        public Criteria andCoordinatesNotBetween(String value1, String value2) { addCriterion("coordinates not between", value1, value2, "coordinates"); return (Criteria) this; }

        // price
        public Criteria andPriceIsNull() { addCriterion("price is null"); return (Criteria) this; }
        public Criteria andPriceIsNotNull() { addCriterion("price is not null"); return (Criteria) this; }
        public Criteria andPriceEqualTo(String value) { addCriterion("price =", value, "price"); return (Criteria) this; }
        public Criteria andPriceNotEqualTo(String value) { addCriterion("price <>", value, "price"); return (Criteria) this; }
        public Criteria andPriceLike(String value) { addCriterion("price like", value, "price"); return (Criteria) this; }
        public Criteria andPriceNotLike(String value) { addCriterion("price not like", value, "price"); return (Criteria) this; }
        public Criteria andPriceIn(List<String> values) { addCriterion("price in", values, "price"); return (Criteria) this; }
        public Criteria andPriceNotIn(List<String> values) { addCriterion("price not in", values, "price"); return (Criteria) this; }
        public Criteria andPriceBetween(String value1, String value2) { addCriterion("price between", value1, value2, "price"); return (Criteria) this; }
        public Criteria andPriceNotBetween(String value1, String value2) { addCriterion("price not between", value1, value2, "price"); return (Criteria) this; }

        // remark
        public Criteria andRemarkIsNull() { addCriterion("remark is null"); return (Criteria) this; }
        public Criteria andRemarkIsNotNull() { addCriterion("remark is not null"); return (Criteria) this; }
        public Criteria andRemarkEqualTo(String value) { addCriterion("remark =", value, "remark"); return (Criteria) this; }
        public Criteria andRemarkNotEqualTo(String value) { addCriterion("remark <>", value, "remark"); return (Criteria) this; }
        public Criteria andRemarkLike(String value) { addCriterion("remark like", value, "remark"); return (Criteria) this; }
        public Criteria andRemarkNotLike(String value) { addCriterion("remark not like", value, "remark"); return (Criteria) this; }
        public Criteria andRemarkIn(List<String> values) { addCriterion("remark in", values, "remark"); return (Criteria) this; }
        public Criteria andRemarkNotIn(List<String> values) { addCriterion("remark not in", values, "remark"); return (Criteria) this; }
        public Criteria andRemarkBetween(String value1, String value2) { addCriterion("remark between", value1, value2, "remark"); return (Criteria) this; }
        public Criteria andRemarkNotBetween(String value1, String value2) { addCriterion("remark not between", value1, value2, "remark"); return (Criteria) this; }

        // type
        public Criteria andTypeIsNull() { addCriterion("type is null"); return (Criteria) this; }
        public Criteria andTypeIsNotNull() { addCriterion("type is not null"); return (Criteria) this; }
        public Criteria andTypeEqualTo(String value) { addCriterion("type =", value, "type"); return (Criteria) this; }
        public Criteria andTypeNotEqualTo(String value) { addCriterion("type <>", value, "type"); return (Criteria) this; }
        public Criteria andTypeLike(String value) { addCriterion("type like", value, "type"); return (Criteria) this; }
        public Criteria andTypeNotLike(String value) { addCriterion("type not like", value, "type"); return (Criteria) this; }
        public Criteria andTypeIn(List<String> values) { addCriterion("type in", values, "type"); return (Criteria) this; }
        public Criteria andTypeNotIn(List<String> values) { addCriterion("type not in", values, "type"); return (Criteria) this; }
        public Criteria andTypeBetween(String value1, String value2) { addCriterion("type between", value1, value2, "type"); return (Criteria) this; }
        public Criteria andTypeNotBetween(String value1, String value2) { addCriterion("type not between", value1, value2, "type"); return (Criteria) this; }

        // creator
        public Criteria andCreatorIsNull() { addCriterion("creator is null"); return (Criteria) this; }
        public Criteria andCreatorIsNotNull() { addCriterion("creator is not null"); return (Criteria) this; }
        public Criteria andCreatorEqualTo(Long value) { addCriterion("creator =", value, "creator"); return (Criteria) this; }
        public Criteria andCreatorNotEqualTo(Long value) { addCriterion("creator <>", value, "creator"); return (Criteria) this; }
        public Criteria andCreatorIn(List<Long> values) { addCriterion("creator in", values, "creator"); return (Criteria) this; }
        public Criteria andCreatorNotIn(List<Long> values) { addCriterion("creator not in", values, "creator"); return (Criteria) this; }
        public Criteria andCreatorBetween(Long value1, Long value2) { addCriterion("creator between", value1, value2, "creator"); return (Criteria) this; }
        public Criteria andCreatorNotBetween(Long value1, Long value2) { addCriterion("creator not between", value1, value2, "creator"); return (Criteria) this; }

        // tenantId
        public Criteria andTenantIdIsNull() { addCriterion("tenant_id is null"); return (Criteria) this; }
        public Criteria andTenantIdIsNotNull() { addCriterion("tenant_id is not null"); return (Criteria) this; }
        public Criteria andTenantIdEqualTo(Long value) { addCriterion("tenant_id =", value, "tenantId"); return (Criteria) this; }
        public Criteria andTenantIdNotEqualTo(Long value) { addCriterion("tenant_id <>", value, "tenantId"); return (Criteria) this; }
        public Criteria andTenantIdIn(List<Long> values) { addCriterion("tenant_id in", values, "tenantId"); return (Criteria) this; }
        public Criteria andTenantIdNotIn(List<Long> values) { addCriterion("tenant_id not in", values, "tenantId"); return (Criteria) this; }
        public Criteria andTenantIdBetween(Long value1, Long value2) { addCriterion("tenant_id between", value1, value2, "tenantId"); return (Criteria) this; }
        public Criteria andTenantIdNotBetween(Long value1, Long value2) { addCriterion("tenant_id not between", value1, value2, "tenantId"); return (Criteria) this; }

        // deletedFlag
        public Criteria andDeletedFlagIsNull() { addCriterion("deleted_flag is null"); return (Criteria) this; }
        public Criteria andDeletedFlagIsNotNull() { addCriterion("deleted_flag is not null"); return (Criteria) this; }
        public Criteria andDeletedFlagEqualTo(String value) { addCriterion("deleted_flag =", value, "deletedFlag"); return (Criteria) this; }
        public Criteria andDeletedFlagNotEqualTo(String value) { addCriterion("deleted_flag <>", value, "deletedFlag"); return (Criteria) this; }
        public Criteria andDeletedFlagLike(String value) { addCriterion("deleted_flag like", value, "deletedFlag"); return (Criteria) this; }
        public Criteria andDeletedFlagNotLike(String value) { addCriterion("deleted_flag not like", value, "deletedFlag"); return (Criteria) this; }
        public Criteria andDeletedFlagIn(List<String> values) { addCriterion("deleted_flag in", values, "deletedFlag"); return (Criteria) this; }
        public Criteria andDeletedFlagNotIn(List<String> values) { addCriterion("deleted_flag not in", values, "deletedFlag"); return (Criteria) this; }
        public Criteria andDeletedFlagBetween(String value1, String value2) { addCriterion("deleted_flag between", value1, value2, "deletedFlag"); return (Criteria) this; }
        public Criteria andDeletedFlagNotBetween(String value1, String value2) { addCriterion("deleted_flag not between", value1, value2, "deletedFlag"); return (Criteria) this; }

    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() { super(); }
    }

    public static class Criterion {
        private String condition;
        private Object value;
        private Object secondValue;
        private boolean noValue;
        private boolean singleValue;
        private boolean betweenValue;
        private boolean listValue;
        private String typeHandler;

        public String getCondition() { return condition; }
        public Object getValue() { return value; }
        public Object getSecondValue() { return secondValue; }
        public boolean isNoValue() { return noValue; }
        public boolean isSingleValue() { return singleValue; }
        public boolean isBetweenValue() { return betweenValue; }
        public boolean isListValue() { return listValue; }
        public String getTypeHandler() { return typeHandler; }

        protected Criterion(String condition) {
            this.condition = condition; this.typeHandler = null; this.noValue = true;
        }
        protected Criterion(String condition, Object value, String typeHandler) {
            this.condition = condition; this.value = value; this.typeHandler = typeHandler;
            this.listValue = value instanceof List<?>;
            this.singleValue = !this.listValue;
        }
        protected Criterion(String condition, Object value) { this(condition, value, null); }
        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            this.condition = condition; this.value = value; this.secondValue = secondValue; this.typeHandler = typeHandler;
            this.betweenValue = true;
        }
        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}
