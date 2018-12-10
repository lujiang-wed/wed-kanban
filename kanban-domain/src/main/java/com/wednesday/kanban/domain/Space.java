package com.wednesday.kanban.domain;

/**
 * 此处填写功能、用途等，如果多行，用<br>换行
 * <p>创建日期：2014/12/4 </p>
 *
 * @author wypengbo
 * @version V1.0
 * @see
 */
public class Space extends BaseDomain {

    /**
    空间名称
     */
    private String spaceName;

    /**
    唯一标识——探讨是否需要（唯一约束）
     */
    private String spacePin;

    /**
    空间描述
     */
    private String spaceDesc;

    /**
    是否加密（保留）
     */
    private Integer encrypt;

    /**
    产品线
     */
    private String production;

    /**
    机构
     */
    private String organization;

    /**
    生命周期定义（保留）
     */
    private String lifeDef;

    /**
    父空间
     */
    private Long parentSpace;

    /**
    顶层空间
     */
    private Long rootSpace;

    public String getSpaceName() {
        return spaceName;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }

    public String getSpacePin() {
        return spacePin;
    }

    public void setSpacePin(String spacePin) {
        this.spacePin = spacePin;
    }

    public String getSpaceDesc() {
        return spaceDesc;
    }

    public void setSpaceDesc(String spaceDesc) {
        this.spaceDesc = spaceDesc;
    }

    public Integer getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(Integer encrypt) {
        this.encrypt = encrypt;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getLifeDef() {
        return lifeDef;
    }

    public void setLifeDef(String lifeDef) {
        this.lifeDef = lifeDef;
    }

    public Long getParentSpace() {
        return parentSpace;
    }

    public void setParentSpace(Long parentSpace) {
        this.parentSpace = parentSpace;
    }

    public Long getRootSpace() {
        return rootSpace;
    }

    public void setRootSpace(Long rootSpace) {
        this.rootSpace = rootSpace;
    }

    @Override
    public String toString() {
        return "Space{" +
                "spaceName='" + spaceName + '\'' +
                ", spacePin='" + spacePin + '\'' +
                ", spaceDesc='" + spaceDesc + '\'' +
                ", encrypt=" + encrypt +
                ", production='" + production + '\'' +
                ", organization='" + organization + '\'' +
                ", lifeDef='" + lifeDef + '\'' +
                ", parentSpace=" + parentSpace +
                ", rootSpace=" + rootSpace +
                '}';
    }
}