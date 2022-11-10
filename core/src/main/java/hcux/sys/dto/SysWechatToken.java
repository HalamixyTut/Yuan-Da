package hcux.sys.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import hcux.core.util.HcuxConstant;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @author feng.liu01@hand-china.com
 * @description 微信token维护
 */
@ExtensionAttribute(disable = true)
@Table(name = "HCUX_SYS_WECHAT_TOKEN")
public class SysWechatToken extends BaseDTO {

    public static final String FIELD_WECHAT_TOKEN_ID = "wechatTokenId";
    public static final String FIELD_OPENID = "openid";
    public static final String FIELD_ACCESS_TOKEN = "accessToken";
    public static final String FIELD_REFRESH_TOKEN = "refreshToken";
    public static final String FIELD_USER_NAME = "userName";
    public static final String FIELD_TOKEN_UPDATE_DATE = "tokenUpdateDate";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";

    @Id
    @GeneratedValue
    private Long wechatTokenId;

    @Length(max = 100)
    private String openid; //微信用户唯一标识

    @Length(max = 1024)
    private String accessToken;

    @Length(max = 1024)
    private String refreshToken;

    @Length(max = 40)
    private String userName; //用户名

    private Date tokenUpdateDate; // token更新时间

    @Length(max = 1)
    private String deleteFlag = HcuxConstant.YES_NO.N;

    @Transient
    private String code;

    @Transient
    private String plate; // 板块： NH、XJ

    @Transient
    private Date tokenExpiresTime; //token失效日期

    @Transient
    private String revokeFlag; //是否有效

    @Transient
    private String tokenStatus; //token状态

    public void setWechatTokenId(Long wechatTokenId) {
        this.wechatTokenId = wechatTokenId;
    }

    public Long getWechatTokenId() {
        return wechatTokenId;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setTokenUpdateDate(Date tokenUpdateDate) {
        this.tokenUpdateDate = tokenUpdateDate;
    }

    public Date getTokenUpdateDate() {
        return tokenUpdateDate;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public Date getTokenExpiresTime() {
        return tokenExpiresTime;
    }

    public void setTokenExpiresTime(Date tokenExpiresTime) {
        this.tokenExpiresTime = tokenExpiresTime;
    }

    public String getRevokeFlag() {
        return revokeFlag;
    }

    public void setRevokeFlag(String revokeFlag) {
        this.revokeFlag = revokeFlag;
    }

    public String getTokenStatus() {
        return tokenStatus;
    }

    public void setTokenStatus(String tokenStatus) {
        this.tokenStatus = tokenStatus;
    }
}
