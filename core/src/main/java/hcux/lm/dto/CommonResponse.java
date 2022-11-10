package hcux.lm.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

public class CommonResponse {

    private String status;//同步状态  E:错误 U:警告  S:成功

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorMessage;//错误消息

    public CommonResponse() {
    }

    public CommonResponse(String status) {
        this.status = status;
    }

    public CommonResponse(String status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "CommonResponse{" +
                "status='" + status + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
