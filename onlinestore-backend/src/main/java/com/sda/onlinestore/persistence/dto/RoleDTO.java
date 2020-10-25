package com.sda.onlinestore.persistence.dto;

import java.util.ArrayList;
import java.util.List;

public class RoleDTO {

    private long id;

    private String name;

    private List<PrivilegeDTO> privilegeDTOList = new ArrayList<>();

    public void setPrivilegeDTOList(List<PrivilegeDTO> privilegeDTOList) {
        this.privilegeDTOList = privilegeDTOList;
    }

    public List<PrivilegeDTO> getPrivilegeDTOList() {
        return privilegeDTOList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
