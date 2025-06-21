package com.jsh.erp.datasource.entities;

public class User {

    /**
     * 主键ID
     */
    private Long employeeId;

    /**
     * 展示用户名
     */
    private String actualName;

    /**
     * 登录用户名
     */
    private String loginName;

    /**
     * 登录密码
     */
    private String loginPwd;

    private String leaderFlag;

    private String position;

    /**
     * 部门ID
     */
    private Long departmentId;

    private String email;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 是否是管理员
     */
    private Boolean administratorFlag;

    private Byte isystem;

    /**
     * 禁用状态
     */
    private Boolean disabledFlag;

    private String description;

    /**
     * 备注
     */
    private String remark;

    private String weixinOpenId;
    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 删除状态
     */
    private Boolean deletedFlag;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getActualName() {
        return actualName;
    }

    public void setActualName(String actualName) {
        this.actualName = actualName == null ? null : actualName.trim();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd == null ? null : loginPwd.trim();
    }

    public String getLeaderFlag() {
        return leaderFlag;
    }

    public void setLeaderFlag(String leaderFlag) {
        this.leaderFlag = leaderFlag == null ? null : leaderFlag.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Boolean getAdministratorFlag() {
        return administratorFlag;
    }

    public void setAdministratorFlag(Boolean administratorFlag) {
        this.administratorFlag = administratorFlag;
    }

    public Byte getIsystem() {
        return isystem;
    }

    public void setIsystem(Byte isystem) {
        this.isystem = isystem;
    }

    public Boolean getDisabledFlag() {
        return disabledFlag;
    }

    public void setDisabledFlag(Boolean disabledFlag) {
        this.disabledFlag = disabledFlag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getWeixinOpenId() {
        return weixinOpenId;
    }

    public void setWeixinOpenId(String weixinOpenId) {
        this.weixinOpenId = weixinOpenId == null ? null : weixinOpenId.trim();
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Boolean getDeletedFlag() {
        return deletedFlag;
    }

    public void setDeletedFlag(Boolean deletedFlag) {
        this.deletedFlag = deletedFlag ;
    }


}