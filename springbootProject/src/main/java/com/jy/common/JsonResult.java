package com.jy.common;

import java.util.List;

/**
 * ClassName:JsonResult
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   shenwei
 * @version
 * @since    Ver 1.1
 * @Date	 2017年1月19日		下午6:38:53
 */
public class JsonResult<T> {
    private Integer code;
    private String message;
    private T result;
    private List<T> resultList;
    private Integer total;

    public JsonResult() {
        super();
        this.code = ResultCode.SUCCESS.getCode();
        this.message = ResultCode.SUCCESS.getMessage();
    }

    public JsonResult( ResultCode code  ) {
        super();
        setResultCode(code);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setResultCode( ResultCode code ){
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }


}

