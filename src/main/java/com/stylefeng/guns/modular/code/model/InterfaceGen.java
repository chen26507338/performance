package com.stylefeng.guns.modular.code.model;

import java.util.List;

public class InterfaceGen {
    private String interfaceName;
    private String interfaceLink;
    private String className;
    private String lowClassName;
    private String returnName;
    private String token;
    private String funcName;
    private String page;
    private List<Parameter> parameters;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getInterfaceLink() {
        return interfaceLink;
    }

    public void setInterfaceLink(String interfaceLink) {
        this.interfaceLink = interfaceLink;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getReturnName() {
        return returnName;
    }

    public void setReturnName(String returnName) {
        this.returnName = returnName;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLowClassName() {
        return lowClassName;
    }

    public void setLowClassName(String lowClassName) {
        this.lowClassName = lowClassName;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
