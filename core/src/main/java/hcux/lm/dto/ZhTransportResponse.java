package hcux.lm.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ZhTransportResponse {
    private String status; // 响应状态码
    private String msg; // 响应说明
    private ZhTransportList data; // 响应数据
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

    public ZhTransportList getData() {
        return data;
    }

    public void setData(ZhTransportList data) {
        this.data = data;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
