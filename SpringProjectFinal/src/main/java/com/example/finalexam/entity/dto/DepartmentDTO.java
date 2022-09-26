package com.example.finalexam.entity.dto;

import com.example.finalexam.entity.Account;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class DepartmentDTO {

    private Integer id;

    private String name;

    private Integer totalMember;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;

    private String type;

    private List<AccountDTO> accountList;

//    public Date getCreatedDate() {
//        return new Date (createdDate.getTime()+(1000 * 60 * 60 * 24));
//    }

    @Data
    @NoArgsConstructor
    static class AccountDTO{
        @JsonProperty("accountId")
        private int id;
        private String username;
    }





}
