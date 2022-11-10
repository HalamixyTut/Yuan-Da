package hcux.lm.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ZhTransportList {
    private String entrustId;//委托单ID
    private String consigNo;//货主自定义单号
    private List<ZhTransport> transportList = new ArrayList<>();//运输单集合
    private String status; // 响应状态码
    private String msg; // 响应说明
    private String refreshToken;

    public String getEntrustId() { return entrustId; }

    public void setEntrustId(String entrustId) { this.entrustId = entrustId; }

    public String getConsigNo() { return consigNo; }

    public void setConsigNo(String consigNo) { this.consigNo = consigNo; }

    public List<ZhTransport> getTransportList() { return transportList; }

    public void setTransportList(List<ZhTransport> transportList) { this.transportList = transportList; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getMsg() { return msg; }

    public void setMsg(String msg) { this.msg = msg; }

    public String getRefreshToken() { return refreshToken; }

    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
}
