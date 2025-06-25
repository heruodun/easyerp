package com.jsh.erp.datasource.entities;

public class Role {
    private Long roleId;

    private String roleName;

    private String roleType;

    private String priceLimit;

    private Long tenantId;



    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType == null ? null : roleType.trim();
    }

    public String getPriceLimit() {
        return priceLimit;
    }

    public void setPriceLimit(String priceLimit) {
        this.priceLimit = priceLimit == null ? null : priceLimit.trim();
    }


    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

   }