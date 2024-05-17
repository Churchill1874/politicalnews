package com.news.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDisableException extends RuntimeException{

    private Integer code = -6;

    private String message = "账号已禁用";

}
