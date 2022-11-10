package hcux.lm.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ZhOrderResponse {

    private String status; // 响应状态码
    private String msg; // 响应说明
    private ZhOrderHeader data; // 响应数据
    private String refreshToken;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ZhOrderHeader getData() {
        return data;
    }

    public void setData(ZhOrderHeader data) {
        this.data = data;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
