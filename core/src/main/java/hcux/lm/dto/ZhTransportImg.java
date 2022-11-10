package hcux.lm.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ZhTransportImg {
    private String logCode;//图片类型
    private List<String> logContentImg;//图片地址

    public ZhTransportImg() {
    }

    public ZhTransportImg(String logCode, List<String> logContentImg) {
        this.logCode = logCode;
        this.logContentImg = logContentImg;
    }

    public String getLogCode() { return logCode; }

    public void setLogCode(String logCode) { this.logCode = logCode; }

    public List<String> getLogContentImg() { return logContentImg; }

    public void setLogContentImg(List<String> logContentImg) { this.logContentImg = logContentImg; }
}
