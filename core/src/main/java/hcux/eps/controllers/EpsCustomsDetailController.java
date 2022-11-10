package hcux.eps.controllers;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hand.hap.core.IRequest;
import com.hand.hap.excel.dto.ColumnInfo;
import com.hand.hap.excel.dto.ExportConfig;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.ICodeService;
import hcux.core.exception.HcuxException;
import hcux.core.util.AutoUpperUtil;
import hcux.core.util.HcuxConstant;
import hcux.doc.util.CommonUtil;
import hcux.eps.dto.EpsCustomsDetail;
import hcux.eps.dto.LogisticsFlow;
import hcux.eps.dto.LogisticsFlowData;
import hcux.eps.mapper.EpsCustomsDetailMapper;
import hcux.eps.service.IEpsCustomsDetailService;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static hcux.eps.util.EpsConstant.TITLE;

@Controller
public class EpsCustomsDetailController extends BaseController {

    @Autowired
    private IEpsCustomsDetailService service;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private EpsCustomsDetailMapper epsCustomsDetailMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ICodeService codeService;
    private static final Logger logger = LoggerFactory.getLogger(EpsCustomsDetailController.class);

    @RequestMapping(value = "/hcux/eps/customs/detail/query")
    @ResponseBody
    public ResponseData query(EpsCustomsDetail dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectLists(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcux/eps/customs/detail/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<EpsCustomsDetail> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hcux/eps/customs/detail/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<EpsCustomsDetail> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/hcux/eps/customs/detail/excel/import")
    @ResponseBody
    public ResponseData excelUpload(@RequestParam MultipartFile file, HttpServletRequest request) throws Exception {
        IRequest requestContext = createRequestContext(request);
        ResponseData responseData = new ResponseData();
        Workbook workbook = null;
        try {
            workbook = CommonUtil.fileType(file);//根据excel的不同版本创建不同的workbook
        } catch (Exception e) {
            responseData.setSuccess(false);
            responseData.setMessage(messageSource.getMessage(e.getMessage(), null, RequestContextUtils.getLocale(request)));
        }
        String errorMessage = service.importExcel(request, requestContext, workbook);
        if (!CommonUtil.objectIsNull(errorMessage)) {
            responseData.setMessage(errorMessage);
        }
        return responseData;
    }

    @RequestMapping(value = "/hcux/eps/customs/detail/excel/export")
    @ResponseBody
    public ResponseData downloadTemplate(HttpServletRequest request, HttpServletResponse response, @RequestParam String config) throws IOException {
        JavaType type = objectMapper.getTypeFactory().constructParametrizedType(ExportConfig.class,
                ExportConfig.class, EpsCustomsDetail.class, ColumnInfo.class);
        ExportConfig<EpsCustomsDetail, ColumnInfo> exportConfig = objectMapper.readValue(config, type);
        EpsCustomsDetail dto = exportConfig.getParam();
        List<EpsCustomsDetail> epsCustomsDetailList = epsCustomsDetailMapper.selectList(dto);
        hcux.eps.util.CommonUtil.createExcel(request, response, epsCustomsDetailList, "Excel导出", TITLE);
        return new ResponseData();

    }

    @RequestMapping(value = "/hcux/eps/customs/detail/head/exportExcel")
    @ResponseBody
    public void exportExcel(EpsCustomsDetail dto, HttpServletRequest request, HttpServletResponse response) throws Exception {
        IRequest requestContext = createRequestContext(request);
        service.exportExcel(requestContext, dto, response);
    }

    @RequestMapping(value = "/hcux/eps/customs/detail/error/query")
    @ResponseBody
    public ResponseData errorQuery(EpsCustomsDetail dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                   @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectErrorLists(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcux/eps/customs/detail/error/submit")
    @ResponseBody
    public ResponseData updateCustomsDetail(@RequestBody List<EpsCustomsDetail> dto, BindingResult result, HttpServletRequest request) throws HcuxException {
        ResponseData responseData = new ResponseData();
        IRequest requestCtx = createRequestContext(request);
        // 自动转大写
        AutoUpperUtil.autoUpper(dto);

        dto = dto.stream().filter(x -> x.getInvoiceNumber() != null && !x.getInvoiceNumber().equals("")).collect(Collectors.toList());
        List<EpsCustomsDetail> epsCustomsDetailList = new ArrayList<EpsCustomsDetail>();
        List<EpsCustomsDetail> epsCustomsDetailList2 = new ArrayList<EpsCustomsDetail>();
        Pattern pattern = Pattern.compile("^[PA][BD]1250[1-9A-Z][A-Z]{2}\\d{5}[A-Z]$");

        for (EpsCustomsDetail x : dto) {
            List<EpsCustomsDetail> detailList = epsCustomsDetailMapper.selectListByConditions(new EpsCustomsDetail() {{
                setInvoiceNumber(x.getInvoiceNumber());
                setModifyFlag("N");
            }});
            if (detailList.size() == 0) {
                List<EpsCustomsDetail> epsCustomsDetails = epsCustomsDetailMapper.selectListByConditions(new EpsCustomsDetail() {{
                    setCustomsNumber(x.getCustomsNumber());
                    setModifyFlag("N");
                }});
                epsCustomsDetailList2.addAll(epsCustomsDetails);

                Matcher matcher = pattern.matcher(x.getInvoiceNumber());
                if (matcher.find()) {
                    epsCustomsDetails.forEach(y -> {
                        y.set__status(DTOStatus.UPDATE);
                        y.setInvoiceNumber(x.getInvoiceNumber());
                        y.setStatus("1");
                        epsCustomsDetailList.add(y);
                    });
                }
            } else {
                responseData.setSuccess(false);
                responseData.setMessage("有发票号重复数据，保存失败");
                return responseData;
            }
        }
        if (epsCustomsDetailList != null && !epsCustomsDetailList.isEmpty()) {
            if (epsCustomsDetailList.size() == epsCustomsDetailList2.size()) {
                return new ResponseData(service.batchUpdate(requestCtx, epsCustomsDetailList));
            } else {
                responseData.setSuccess(false);
                responseData.setMessage("有发票号不符合编码规则,保存失败");
                return responseData;
            }
        } else {
            responseData.setSuccess(false);
            responseData.setMessage("有发票号不符合编码规则,保存失败");
            return responseData;
        }
    }

    @RequestMapping(value = {"/hcux/eps/customs/detail/portal/query", "/api/hcux/eps/customs/detail/portal/query"})
    @ResponseBody
    public ResponseData portalQuery(EpsCustomsDetail dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        dto.setQueryType(HcuxConstant.QUERY_TYPE.PORTAL);
        return new ResponseData(service.selectPortalList(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/api/hcux/eps/customs/detail/portal/exportExcel")
    @ResponseBody
    public void exportExcelForPortal(EpsCustomsDetail dto, HttpServletRequest request, HttpServletResponse response) throws Exception {
        IRequest requestContext = createRequestContext(request);
        dto.setQueryType(HcuxConstant.QUERY_TYPE.PORTAL);
        service.exportExcelForPortal(requestContext, dto, response);
    }

    @RequestMapping(value = {"/api/hcux/eps/customs/detail/portal/query/total"})
    @ResponseBody
    public ResponseData queryTotal(EpsCustomsDetail dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                   @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        dto.setQueryType(HcuxConstant.QUERY_TYPE.PORTAL);
        return new ResponseData(service.selectTotalAmount(requestContext, dto, page, pageSize));
    }

    /**
     * 微信端头查询接口
     *
     * @param dto
     * @param page
     * @param pageSize
     * @param request
     * @return
     */
    @RequestMapping(value = {"/api/hcux/eps/customs/wechat/header/query"})
    @ResponseBody
    public ResponseData wechatHeaderQuery(EpsCustomsDetail dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                          @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        dto.setQueryType(HcuxConstant.QUERY_TYPE.PORTAL);
        return new ResponseData(service.selectHeaderListForWechat(requestContext, dto, page, pageSize));
    }

    /**
     * 微信端明细行查询接口
     *
     * @param dto
     * @param page
     * @param pageSize
     * @param request
     * @return
     */
    @RequestMapping(value = {"/api/hcux/eps/customs/wechat/detail/query"})
    @ResponseBody
    public ResponseData wechatDetailQuery(EpsCustomsDetail dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                          @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        dto.setQueryType(HcuxConstant.QUERY_TYPE.PORTAL);
        return new ResponseData(service.selectDetailListForWechat(requestContext, dto, page, pageSize));
    }


    /**
     * 海关数据查询入口
     *
     * @param dto
     * @param page
     * @param pageSize
     * @param request
     * @return
     */
    @RequestMapping(value = {"/hcux/eps/customs/data/query", "/api/hcux/eps/customs/data/query"})
    @ResponseBody
    public ResponseData customsDataQuery(EpsCustomsDetail dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                         @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) throws IOException, ParseException {
        IRequest requestContext = createRequestContext(request);
        return service.customsDataQuery(requestContext, dto, page, pageSize);
    }


    /**
     * 门户调用物流可视化，如果口岸是上海或宁波，在调用船舶接口
     *
     * @param dto
     * @param request
     * @return
     */
    @RequestMapping(value = {"/hcux/eps/customs/data/logisticsFlow", "/api/hcux/eps/customs/data/logisticsFlow"})
    @ResponseBody
    public ResponseData logisticsFlow(EpsCustomsDetail dto, HttpServletRequest request) throws IOException {
        ResponseData rd = logisticsFlowData(dto, request);
        if (rd.isSuccess() && !rd.getRows().isEmpty()) {
          LogisticsFlowData flowData = (LogisticsFlowData) rd.getRows().get(0);

            if (StringUtils.isBlank(flowData.getVesselName()) || StringUtils.isBlank(flowData.getVoyageNo())) {
                return rd;
            }
            if (dto.getImportExportPort().equals("22")) {
                ResponseData responseData = vesselBerth(flowData, request, "22");
                if (responseData.isSuccess() && !responseData.getRows().isEmpty()) {
                    return responseData;
                }
            }
            if (dto.getImportExportPort().equals("31")) {
                ResponseData responseData = vesselBerth(flowData, request, "31");
                if (responseData.isSuccess() && !responseData.getRows().isEmpty()) {
                    return responseData;
                }
            }
        }

        return rd;
    }


    /**
     * 海关数据 物流可视化查询
     *
     * @param dto
     * @param request
     * @return
     */
    @RequestMapping(value = {"/hcux/eps/customs/data/logisticsFlowData", "/api/hcux/eps/customs/data/logisticsFlowData"})
    @ResponseBody
    public ResponseData logisticsFlowData(EpsCustomsDetail dto, HttpServletRequest request) throws IOException {
        IRequest iRequest = createRequestContext(request);
        ResponseData rd = new ResponseData();

        String billNumber = dto.getBillNumber();
        String importExportPort = dto.getImportExportPort();
        if (StringUtils.isBlank(billNumber)) {
            rd.setSuccess(false);
            rd.setMessage("提单号为空，查询失败！");
            return rd;
        }

        String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        String encode = URLEncoder.encode(date, StandardCharsets.UTF_8.name());
        String stringSign = "";
        String place = "";

        if (StringUtils.isBlank(importExportPort)) {
            stringSign = "format=json&method=eportyun.logistics.flow.get&number=" + billNumber + "&timestamp=" + date + "&user_id=zjvision&key=79d25323-7b90-4002-b070-f6f69c34bc3d";
        } else {
            iRequest.setLocale("zh_CN");
            place = codeService.getCodeMeaningByValue(iRequest, "HCUX_EPS_LOGISTICS_FLOW", importExportPort);
            if (StringUtils.isBlank(place)) {
                rd.setSuccess(false);
                rd.setMessage("未找到口岸名称，查询失败！");
                return rd;
            }

            stringSign = "format=json&method=eportyun.logistics.flow.get&number=" + billNumber + "&place=" + place + "&timestamp=" + date + "&user_id=zjvision&key=79d25323-7b90-4002-b070-f6f69c34bc3d";
        }

        String sign = DigestUtils.md5Hex(stringSign).toUpperCase();
        String url = "http://api.nbeport.com/router/rest?sign=" + sign + "&user_id=zjvision&timestamp=" + encode + "&format=json&method=eportyun.logistics.flow.get&place=" + place +"&number=" + billNumber;

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse httpResponse = httpClient.execute(get);
        InputStream content = httpResponse.getEntity().getContent();
        LogisticsFlow logisticsFlow = objectMapper.readValue(content, LogisticsFlow.class);
        if ("F".equals(logisticsFlow.getCode())) {
            rd.setSuccess(false);
            rd.setMessage(logisticsFlow.getMsg());
            return rd;
        }
        rd.setRows(logisticsFlow.getData());
        return rd;
    }

    /**
     * 海关数据 船舶查询
     *
     * @param flowData
     * @param request
     * @return
     */
    @RequestMapping(value = {"/hcux/eps/customs/data/vesselBerth", "/api/hcux/eps/customs/data/vesselBerth"})
    @ResponseBody
    public ResponseData vesselBerth(LogisticsFlowData flowData, HttpServletRequest request, String place) throws IOException {
        IRequest iRequest = createRequestContext(request);
        ResponseData rd = new ResponseData();

        String vesselName = flowData.getVesselName();
        String voyage = flowData.getVoyageNo();
        if (place == null) {
            rd.setSuccess(false);
            rd.setMessage("方法名为空，查询失败！");
            return rd;
        }
        if (vesselName == null || voyage == null) {
            rd.setSuccess(false);
            rd.setMessage("船名或航次为空，查询失败！");
            return rd;
        }

        vesselName = vesselName.replace(" ","");
        voyage = voyage.replace(" ", "");

        String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        String encode = URLEncoder.encode(date, StandardCharsets.UTF_8.name());
        String method = "";
        String stringSign = "";
        if (place.equals("22")) { //上海
            method = "eportyun.logistics.shberth.get";
            stringSign = "format=json&method=" + method + "&timestamp=" + date + "&user_id=zjvision&vesselname=" + vesselName + "&voyage=" + voyage + "&key=79d25323-7b90-4002-b070-f6f69c34bc3d";
        } else { //宁波
            method = "nbeport.vessel.berth.get";
            stringSign = "zjvision79d25323-7b90-4002-b070-f6f69c34bc3d" + date;
        }

        String sign = DigestUtils.md5Hex(stringSign).toUpperCase();
        String url = "http://api.nbeport.com/router/rest?sign=" + sign + "&user_id=zjvision&timestamp=" + encode + "&format=json&method=" + method + "&vesselname=" + vesselName +"&voyage=" + voyage;

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse httpResponse = httpClient.execute(get);
        String json = EntityUtils.toString(httpResponse.getEntity(), "UTF-8").trim();
        JSONObject jsonObject = JSONObject.fromObject(json);

        if ("F".equals(jsonObject.get("code"))) {
            rd.setSuccess(false);
            rd.setMessage(jsonObject.get("msg").toString());
            return rd;
        }
        JSONObject jsonData = JSONObject.fromObject(jsonObject.get("data"));
        LogisticsFlowData logisticsFlowData = (LogisticsFlowData) JSONObject.toBean(jsonData, LogisticsFlowData.class);

        if (!hcux.doc.util.CommonUtil.objectIsNull(logisticsFlowData)) {
            List<LogisticsFlowData> list = new ArrayList<>();
            list.add(logisticsFlowData);
            rd.setRows(list);
        }

        return rd;
    }
    @RequestMapping(value = "/hcux/eps/customs/detail/head/authorize")
    @ResponseBody
    public ResponseData exportHasAuthorized(HttpServletRequest request, HttpServletResponse response) throws Exception {
        IRequest requestContext = createRequestContext(request);
        return service.exportHasAuthorized(requestContext);
    }
    @RequestMapping(value = "/hcux/eps/customs/detail/head/selfExportExcel")
    @ResponseBody
    public void exportExcelForSelf(EpsCustomsDetail dto, HttpServletRequest request, HttpServletResponse response) throws Exception {
        IRequest requestContext = createRequestContext(request);
        service.exportExcelForSelf(requestContext, dto, response);
    }
}
