package hcux.cs.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.account.dto.User;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.CodeValue;
import com.hand.hap.system.service.ICodeService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.cs.dto.CsInvoiceExpress;
import hcux.cs.mapper.CsInvoiceExpressMapper;
import hcux.cs.service.ICsInvoiceExpressService;
import hcux.cs.util.CommonUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static hcux.cs.util.CsConstant.*;
import static hcux.eps.util.EpsConstant.HCUX_EPS_ROW;
import static hcux.eps.util.EpsConstant.HCUX_EPS_THE_FIRST;

/**
 * @author feng.liu01@hand-china.com
 * @description 发票快递单号
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CsInvoiceExpressServiceImpl extends BaseServiceImpl<CsInvoiceExpress> implements ICsInvoiceExpressService {

    @Autowired
    private CsInvoiceExpressMapper mapper;
    @Autowired
    private ICodeService codeService;
    @Autowired
    private MessageSource messageSource;

    @Override
    public List<CsInvoiceExpress> selectData(IRequest requestContext, CsInvoiceExpress dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.selectData(dto);
    }

    @Override
    public List<CodeValue> queryPlate(IRequest requestContext) {
        return mapper.queryPlate();
    }

    @Override
    public String importExcel(HttpServletRequest request, IRequest requestContext, Workbook workbook) throws Exception {
        String errorMessage = "";
        try {
            String Error1 = ""; // 板块不存在
            String Error2 = ""; // 客户名称不存在
            String Error3 = ""; // 客户对应多个账户
            int SuccessCount = 0; // 导入成功
            int numberOfSheets = workbook.getNumberOfSheets();
            if (numberOfSheets > 0) {
                Sheet sheet = workbook.getSheetAt(0);
                if (Objects.nonNull(sheet)) {
                    int firstRowNum = sheet.getFirstRowNum();
                    //获取sheet中最后一行行号
                    int lastRowNum = sheet.getLastRowNum();
                    int i = firstRowNum + 1;
                    List<CsInvoiceExpress> list = new ArrayList<>();

                    for (; i <= lastRowNum; i++) {
                        Row row = sheet.getRow(i);

                        if (!CommonUtil.rowIsNull(row)) {
                            CsInvoiceExpress csInvoiceExpress = CommonUtil.rowToCsInvoiceExpress(row);
                            String plateValue = codeService.getCodeValueByMeaning(requestContext, HCUX_SYS_PLATE, csInvoiceExpress.getPlate());
                            String expressCompanyValue = codeService.getCodeValueByMeaning(requestContext, HCUX_CS_EXPRESS_COMPANY, csInvoiceExpress.getExpressCompany());
                            if (expressCompanyValue != null) {
                                csInvoiceExpress.setExpressCompany(expressCompanyValue);
                            }
                            if (plateValue != null) { //板块存在
                                List<User> userList = mapper.queryCustoms(csInvoiceExpress);
                                if (!userList.isEmpty()) {
                                    if (userList.size() == 1) {
                                        csInvoiceExpress.setPlate(plateValue);
                                        csInvoiceExpress.setCustomerCode(userList.get(0).getUserName());
                                        SuccessCount++;
                                        list.add(csInvoiceExpress);
                                    } else {
                                        Error3 += messageSource.getMessage(HCUX_EPS_THE_FIRST, null, RequestContextUtils.getLocale(request)) + (i + 1) + messageSource.getMessage(HCUX_EPS_ROW, null, RequestContextUtils.getLocale(request));
                                    }
                                } else {
                                    Error2 += messageSource.getMessage(HCUX_EPS_THE_FIRST, null, RequestContextUtils.getLocale(request)) + (i + 1) + messageSource.getMessage(HCUX_EPS_ROW, null, RequestContextUtils.getLocale(request));
                                }
                            } else {
                                Error1 += messageSource.getMessage(HCUX_EPS_THE_FIRST, null, RequestContextUtils.getLocale(request)) + (i + 1) + messageSource.getMessage(HCUX_EPS_ROW, null, RequestContextUtils.getLocale(request));
                            }

                        }
                    }
                    list.forEach(x -> self().insertSelective(requestContext, x));
                }
            }

            if (!("").equals(Error1)) {
                errorMessage += (messageSource.getMessage(HCUX_CS_IMVOICE_PLATE_NOT_EXIST, null, RequestContextUtils.getLocale(request)) + Error1) + "<br />";
            }
            if (!("").equals(Error2)) {
                errorMessage += (messageSource.getMessage(HCUX_CS_INVOICE_CUSTOM_NAME_NOT_EXIST, null, RequestContextUtils.getLocale(request)) + Error2) + "<br />";
            }
            if (!("").equals(Error3)) {
                errorMessage += messageSource.getMessage(HCUX_CS_INVOICE_MULTIPLE_CUSTOM_NAME, null, RequestContextUtils.getLocale(request)) + Error3 + messageSource.getMessage(HCUX_CONTACT_ADMINISTRATOR, null, RequestContextUtils.getLocale(request)) + "<br />";
            }
            if (SuccessCount > 0) {
                errorMessage += messageSource.getMessage(HCUX_EXCEL_IMPORT_SUCCESS, null, RequestContextUtils.getLocale(request)) + SuccessCount + messageSource.getMessage(HCUX_EPS_ROW, null, RequestContextUtils.getLocale(request));
            }

            return errorMessage;
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}