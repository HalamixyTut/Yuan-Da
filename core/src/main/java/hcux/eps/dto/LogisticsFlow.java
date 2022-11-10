package hcux.eps.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LogisticsFlow {

    @JsonProperty("code")
    private String code; //调用是否成功（T或F）
    @JsonProperty("msg")
    private String msg; //结果描述（操作失败时必需）
    @JsonProperty("data")
    private List<LogisticsFlowData> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<LogisticsFlowData> getData() {
        return data;
    }

    public void setData(List<LogisticsFlowData> data) {
        this.data = data;
    }
}
