package hcux.doc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hand.hap.account.dto.User;
import com.hand.hap.account.mapper.UserMapper;
import com.hand.hap.attachment.UpConstants;
import com.hand.hap.attachment.dto.AttachCategory;
import com.hand.hap.attachment.dto.Attachment;
import com.hand.hap.attachment.dto.SysFile;
import com.hand.hap.attachment.exception.AttachmentException;
import com.hand.hap.attachment.exception.UniqueFileMutiException;
import com.hand.hap.attachment.service.IAttachCategoryService;
import com.hand.hap.attachment.service.ISysFileService;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.util.UploadUtil;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.ICodeService;
import com.hand.hap.system.service.IProfileService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import hcux.core.exception.HcuxException;
import hcux.core.util.AutoUpperUtil;
import hcux.doc.dto.*;
import hcux.doc.mapper.*;
import hcux.doc.service.*;
import hcux.doc.util.CommonUtil;
import hcux.doc.util.DocConstant;
import hcux.doc.util.StringUtil;
import hcux.eps.dto.EpsCustomsDetail;
import hcux.eps.service.IEpsCustomsDetailService;
import hcux.util.PdfUtil;
import hcux.util.excel.PoiUtil;
import org.activiti.editor.language.json.converter.util.CollectionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;

import static hcux.doc.util.DocConstant.*;

/**
 * @author feng.liu01@hand-china.com
 * @description 报关单头表
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DocCustomsHeaderServiceImpl extends BaseServiceImpl<DocCustomsHeader> implements IDocCustomsHeaderService {

    private static final int CUSTOMS_FIRST_PAGE_SIZE = 7;
    private static final int CUSTOMS_PAGE_SIZE = 14;
    private static final BigDecimal CUSTOMS_PAGE_SIZE_DECIMAL = new BigDecimal(CUSTOMS_PAGE_SIZE);
    private static final int INVOICE_PAGE_SIZE = 25;
    private static final BigDecimal INVOICE_PAGE_SIZE_DECIMAL = new BigDecimal(INVOICE_PAGE_SIZE);
    /**
     * 文件下载默认编码.
     */
    private static final String ENC = "UTF-8";
    @Autowired
    private DocCustomsHeaderMapper mapper;
    @Autowired
    private IDocCustomsLineService docCustomsLineService;
    @Autowired
    private DocCustomsLineMapper docCustomsLineMapper;
    @Autowired
    private IDocCustomsRecordService docCustomsRecordService;
    @Autowired
    private IDocSynthesisService docSynthesisService;
    @Autowired
    private ICodeService codeService;
    @Autowired
    private IProfileService profileService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private IAttachCategoryService categoryService;
    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private IDocBookingNoteService docBookingNoteService;
    @Autowired
    private DocBookingNoteMapper docBookingNoteMapper;
    @Autowired
    private DocSynthesisMapper docSynthesisMapper;
    @Autowired
    private DocCustomsRecordMapper docCustomsRecordMapper;
    @Autowired
    private IEpsCustomsDetailService epsCustomsDetailService;

    @Override
    public List<DocCustomsHeader> selectList(IRequest requestContext, DocCustomsHeader dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<DocCustomsHeader> list = mapper.selectList(dto);
        DecimalFormat decimalFormat=new DecimalFormat(".00");//add yexiang.ren 19200

        if (!list.isEmpty()) {
            List<DocCustomsHeader> list1 = mapper.queryCurrencySystemTotal(dto);
            if (!list1.isEmpty()) {
                String stringTotalAmount="";
                for(DocCustomsHeader header : list1){
                    if (header.getTotalPrice() != null) {
                        stringTotalAmount += (header.getCurrencySystem() + " " + decimalFormat.format(header.getTotalPrice()) + "; ");
                    }
                }
                list.get(0).setStringTotalAmount(stringTotalAmount);
            }
        }
        return list;
    }

    @Override
    public DocCustomsHeader selectOne(IRequest requestContext, DocCustomsHeader dto) {
        List<DocCustomsHeader> list = mapper.selectList(dto);
        if (!list.isEmpty()) {
            dto = list.get(0);
        }

        if (dto.getBookingId() != null) {
            dto.setBookingFlag("Y");
        }

        DocCustomsLine sum = docCustomsLineMapper.querySum(dto.getCustomsId());
        if (sum != null) {
            dto.setPackageNumber(sum.getPackageNumber());
            dto.setGrossWeight(sum.getGrossWeight());
            dto.setNetWeight(sum.getNetWeight());
            dto.setTotalPrice(sum.getTotalPrice());
            dto.setVolume(sum.getVolume());
        }

        List<DocCustomsLine> lineList = docCustomsLineMapper.queryPackageNumberUnit(dto.getCustomsId());
        if (lineList.size() == 1) {
            dto.setPackageNumberUnit(lineList.get(0).getPackageNumberUnit());
        } else if (lineList.size() == 2) {
            DocCustomsLine line = lineList.get(0);
            DocCustomsLine line1 = lineList.get(1);
            if (line.getPackageNumberUnit().equals("8")) {
                dto.setPackageNumberUnit(line1.getPackageNumberUnit());
            } else if (line1.getPackageNumberUnit().equals("8")) {
                dto.setPackageNumberUnit(line.getPackageNumberUnit());
            } else {
                dto.setPackageNumberUnit("4");
            }
        } else if (lineList.size() > 2) {
            dto.setPackageNumberUnit("4");
        }

        if (StringUtils.isNotBlank(dto.getPackageNumberUnit())) {
            String s = codeService.getCodeMeaningByValue(requestContext, DocConstant.HCUX_DOC_PACKAGE_NUMBER_UNIT, dto.getPackageNumberUnit());
            dto.setPackageNumberUnit(s);
        }

        return dto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<DocCustomsHeader> batchUpdateHeaderAndLine(HttpServletRequest request, IRequest requestCtx, List<DocCustomsHeader> headerList) {

        for (int i = 0; i < headerList.size(); i++) {
            DocCustomsHeader header = headerList.get(i);

            /*//如果明细行不为空  且状态为未做、准备 将状态修改为待提交
            if (header.getLineList() != null
                    && !header.getLineList().isEmpty()
                    && HCUX_DOC_STATUS.B.equals(header.getStatus())) {
                header.setStatus(HCUX_DOC_STATUS.C);
            }*/

            if (DTOStatus.ADD.equals(header.get__status())) {
                header.setDocumentId((Long) request.getSession(true).getAttribute(User.FIELD_EMPLOYEE_ID));
                header.setObjectVersionNumber(null);
                header.setApprovalId(null);

                // 保存头
                self().insertSelective(requestCtx, header);
                // 保存行
                updateLines(requestCtx, header);


                // 保存记录
                String currentUserName = requestCtx.getUserName();
                User user = userMapper.selectByUserName(currentUserName);
                DocCustomsRecord record = new DocCustomsRecord(header.getCustomsId(), DocConstant.HCUX_DOC_RECORD_OPERATION.CREATE);
                record.setOperatorId(user.getEmployeeId());
                docCustomsRecordService.insertSelective(requestCtx, record);

                // 插入记录到综合
                DocSynthesis docSynthesis = new DocSynthesis();
                docSynthesis.setCustomsId(header.getCustomsId());
                docSynthesis.setStatus("B");
                docSynthesisService.insertSelective(requestCtx, docSynthesis);

            } else if (DTOStatus.UPDATE.equals(header.get__status())) {
                header.setDocumentId((Long) request.getSession(true).getAttribute(User.FIELD_EMPLOYEE_ID));
                header.setLastUpdateDate(new Date());
                self().updateByPrimaryKey(requestCtx, header);
                updateLines(requestCtx, header);
            }

//            header = self().selectOne(requestCtx, header);

            if (headerList.get(i).getLineList() != null) {
                // 更新其他包装种类
                updateOtherPackageType(requestCtx, header);
            }

//            headerList.set(i, header);
        }
        return headerList;
    }

    /**
     * 更新行
     *
     * @param requestCtx
     * @param header
     */
    private void updateLines(IRequest requestCtx, DocCustomsHeader header) {
        List<DocCustomsLine> lineList = header.getLineList();
        if (lineList != null) {
            for (DocCustomsLine line : lineList) {
                line.setCustomsId(header.getCustomsId());
                if (DTOStatus.ADD.equals(line.get__status())) {
                    line.setObjectVersionNumber(null);
                    docCustomsLineService.insertSelective(requestCtx, line);
                } else if (DTOStatus.UPDATE.equals(line.get__status())) {
                    line.setLastUpdateDate(new Date());
                    docCustomsLineService.updateByPrimaryKey(requestCtx, line);
                }
            }
        }
    }

    @Override
    public List<DocCustomsHeader> queryDocCustomsHeaderBySet(IRequest request, Set<String> invoiceNumberSet) {
        return mapper.queryCustomsHeaderByInvoiceNumber(invoiceNumberSet);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteHeaderAndLine(HttpServletRequest request, IRequest requestCtx, List<DocCustomsHeader> list) throws HcuxException {
        Long employeeId = (Long) request.getSession(true).getAttribute(User.FIELD_EMPLOYEE_ID);
        for (DocCustomsHeader header : list) {
            header = self().selectByPrimaryKey(requestCtx, header);

            if (!employeeId.equals(header.getDocumentId())
                    && !employeeId.equals(header.getApprovalId())) {
                throw new HcuxException("您不是所选单据的单证员或复核员，删除失败！");
            }

            if (HCUX_DOC_STATUS.G.equals(header.getStatus())
                    || HCUX_DOC_STATUS.H.equals(header.getStatus())
                    || HCUX_DOC_STATUS.D.equals(header.getStatus())
                    || HCUX_DOC_STATUS.E.equals(header.getStatus())) {
                throw new HcuxException("存在审批中、已审批、已完结、已改单的数据，删除失败！");
            }

            self().deleteByPrimaryKey(header);
            Long customsId = header.getCustomsId();
            if (customsId != null) {
                DocCustomsLine line = new DocCustomsLine();
                line.setCustomsId(customsId);
                docCustomsLineMapper.delete(line);

                //删除操作记录
                DocCustomsRecord record = new DocCustomsRecord();
                record.setCustomsId(customsId);
                docCustomsRecordMapper.delete(record);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData submitApproval(HttpServletRequest request, IRequest requestCtx, List<DocCustomsHeader> list) {
        ResponseData rd = new ResponseData();
        StringBuilder message = new StringBuilder();
        Long employeeId = (Long) request.getSession(true).getAttribute(User.FIELD_EMPLOYEE_ID);

        for (DocCustomsHeader header : list) {
            header = self().selectByPrimaryKey(requestCtx, header);
            if (HCUX_DOC_STATUS.C.equals(header.getStatus()) || HCUX_DOC_STATUS.F.equals(header.getStatus())
                    || HCUX_DOC_STATUS.A.equals(header.getStatus())) {

                // 校验必输
                StringBuilder result = verifyRequired(header);
                if (result.length() > 0) {
                    message.append(result);
                    continue;
                }

                // 保存提交记录
                DocCustomsRecord record = new DocCustomsRecord(header.getCustomsId(), DocConstant.HCUX_DOC_RECORD_OPERATION.SUMMIT);
                record.setOperatorId(employeeId);
                DocCustomsRecord maxTime1 = docCustomsRecordService.selectMaxTime(header);
                if (maxTime1.getOperationDate().after(record.getOperationDate())){
                    record.setOperationDate(new Date(maxTime1.getOperationDate().getTime()+(20+(int)(Math.random()*280))*1000));
                } else {
                    record.setOperationDate(new Date(record.getOperationDate().getTime()));
                }
                docCustomsRecordService.insertSelective(requestCtx, record);

                String currentUserName = requestCtx.getUserName();
                String value = profileService.getValueByUserIdAndName(requestCtx.getUserId(), "HCUX_DOC_CUSTOMS_AUTO_APPROVAL");
                if (value != null) {//自审批
                    User user = userMapper.selectByUserName(value);
                    header.setDocumentId(employeeId);
                    header.setApprovalId(user.getEmployeeId());
                    header.setStatus(HCUX_DOC_STATUS.E);
                    self().updateByPrimaryKeySelective(requestCtx, header);

                    //保存审核意见
                    DocCustomsRecord agreeRecord = new DocCustomsRecord(header.getCustomsId(), DocConstant.HCUX_DOC_RECORD_OPERATION.APPROVE_AGREE);
                    agreeRecord.setOperatorId(user.getEmployeeId());
                    agreeRecord.setRemark("同意");
                    agreeRecord.setOperationDate(new Date(record.getOperationDate().getTime()+(20+(int)(Math.random()*280))*1000));
                    docCustomsRecordService.insertSelective(requestCtx, agreeRecord);

                    //综合查询该项目号状态是报关中
                    modifySynthesisStatus(requestCtx, header.getCustomsId());
                } else {
                    header.setDocumentId(employeeId);
                    header.setStatus(HCUX_DOC_STATUS.D);
                    self().updateByPrimaryKeySelective(requestCtx, header);
                }
            } else {
                message.append("项目号 ")
                        .append(header.getInvoiceNumber())
                        .append(" 状态不符合，提交失败！<br/>");
            }
        }

        if (StringUtils.isNotBlank(message.toString())) {
            rd.setMessage(message.toString());
            rd.setSuccess(false);
        }
        return rd;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData approval(HttpServletRequest request, IRequest requestCtx, List<DocCustomsRecord> dto) {
        ResponseData rd = new ResponseData();

        Long employeeId = (Long) request.getSession(true).getAttribute(User.FIELD_EMPLOYEE_ID);
        for(DocCustomsRecord docCustomsRecord:dto){
            DocCustomsHeader header = new DocCustomsHeader();
            header.setCustomsId(docCustomsRecord.getCustomsId());
            header = self().selectByPrimaryKey(requestCtx, header);
            if (header == null) {
                rd.setSuccess(false);
                rd.setMessage("未找到该报关单，审批失败！");
                return rd;
            }

            if (employeeId.equals(header.getDocumentId())) {
                rd.setSuccess(false);
                rd.setMessage("无法审批自己的单据，审批失败！");
                return rd;
            }
            if (HCUX_DOC_STATUS.D.equals(header.getStatus())) {

                if (DocConstant.HCUX_DOC_RECORD_OPERATION.APPROVE_AGREE.equals(docCustomsRecord.getOperationType())) {
                    header.setStatus(HCUX_DOC_STATUS.E);
                    //综合查询该项目号状态是报关中
                    modifySynthesisStatus(requestCtx, header.getCustomsId());
                } else if (DocConstant.HCUX_DOC_RECORD_OPERATION.APPROVE_REJECT.equals(docCustomsRecord.getOperationType())) {
                    header.setStatus(HCUX_DOC_STATUS.F);
                }
                header.setApprovalId(employeeId);
                self().updateByPrimaryKeySelective(requestCtx, header);

                docCustomsRecord.setOperationDate(new Date());
                docCustomsRecord.setOperatorId(employeeId);
                docCustomsRecordService.insertSelective(requestCtx, docCustomsRecord);
            } else {
                rd.setSuccess(false);
                rd.setMessage("状态不符合，审批失败！");
            }
        }
        return rd;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<DocCustomsHeader> modify(HttpServletRequest request, IRequest requestCtx, List<DocCustomsHeader> list) {
        for (DocCustomsHeader header : list) {
            if (HCUX_DOC_STATUS.E.equals(header.getStatus())) {
                header.setStatus(HCUX_DOC_STATUS.C);
                header.setDocumentId((Long) request.getSession(true).getAttribute(User.FIELD_EMPLOYEE_ID));
                self().updateByPrimaryKeySelective(requestCtx, header);

                // 保存记录
                String currentUserName = requestCtx.getUserName();
                User user = userMapper.selectByUserName(currentUserName);
                DocCustomsRecord record = new DocCustomsRecord(header.getCustomsId(), DocConstant.HCUX_DOC_RECORD_OPERATION.MODIFY);
                record.setOperatorId(user.getEmployeeId());
                DocCustomsRecord maxTime = docCustomsRecordService.selectMaxTime(header);
                if (maxTime.getOperationDate().after(record.getOperationDate())){
                    record.setOperationDate(new Date(maxTime.getOperationDate().getTime()+(20+(int)(Math.random()*280))*1000));
                } else {
                    record.setOperationDate(new Date(record.getOperationDate().getTime()));
                }
                docCustomsRecordService.insertSelective(requestCtx, record);

                //修改综合状态
                docSynthesisService.relateModifyStatus(requestCtx, header.getCustomsId());
            }
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<DocCustomsHeader> customsModify(HttpServletRequest request, IRequest requestCtx, List<DocCustomsHeader> list) {
        for (DocCustomsHeader header : list) {
            if (HCUX_DOC_STATUS.E.equals(header.getStatus()) || HCUX_DOC_STATUS.G.equals(header.getStatus())) {

                header = self().selectByPrimaryKey(requestCtx, header);
                header.setStatus(HCUX_DOC_STATUS.C);
                header.setDocumentId((Long) request.getSession(true).getAttribute(User.FIELD_EMPLOYEE_ID));
                self().updateByPrimaryKeySelective(requestCtx, header);

                Long customsId = header.getCustomsId();

                // 保存记录
                String currentUserName = requestCtx.getUserName();
                User user = userMapper.selectByUserName(currentUserName);
                DocCustomsRecord record = new DocCustomsRecord(customsId, DocConstant.HCUX_DOC_RECORD_OPERATION.CUSTOMS_MODIFY);
                record.setOperatorId(user.getEmployeeId());
                docCustomsRecordService.insertSelective(requestCtx, record);

                //修改综合状态
                docSynthesisService.relateModifyStatus(requestCtx, header.getCustomsId());

                // 2019-07-04 点击海关改单时，同时删除该项目所关联的报关明细头数据里的发票号，并且该报关明细头的状态变为合同号重复
                EpsCustomsDetail epsCustomsDetail = new EpsCustomsDetail();
                epsCustomsDetail.setInvoiceNumber(header.getInvoiceNumber());
                List<EpsCustomsDetail> detailList = epsCustomsDetailService.select(requestCtx, epsCustomsDetail, 0, 0);
                if (!detailList.isEmpty()) {
                    for (EpsCustomsDetail detail : detailList) {
                        detail.setInvoiceNumber(null);
                        detail.setStatus("2");
                        detail.setLastUpdateDate(new Date());
                        epsCustomsDetailService.updateByPrimaryKey(requestCtx, detail);
                    }
                }

                // 查询最大序列号
                Long maxSn = mapper.queryMaxSn(header);
                if (maxSn == null) {
                    maxSn = 1L;
                } else {
                    maxSn++;
                }
                //生成项目号加X的记录
                header.setStatus(HCUX_DOC_STATUS.E);
                header.setInvoiceNumber("X" + maxSn + header.getInvoiceNumber());
                header.setCustomsModifySn(maxSn);
                header.setCustomsId(null);
                header.setStatus(HCUX_DOC_STATUS.H);
                self().insertSelective(requestCtx, header);

                DocCustomsLine docCustomsLine = new DocCustomsLine();
                docCustomsLine.setCustomsId(customsId);
                List<DocCustomsLine> lineList = docCustomsLineMapper.select(docCustomsLine);
                for (DocCustomsLine line : lineList) {
                    line.setCustomsLineId(null);
                    line.setCustomsId(header.getCustomsId());
                    docCustomsLineService.insertSelective(requestCtx, line);
                }

            }
        }
        return list;
    }

    @Override
    public void pdfPrint(IRequest requestCtx, DocCustomsPrint dto, HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException, HcuxException {
        DocCustomsHeader header = new DocCustomsHeader();
        header.setCustomsId(dto.getCustomsId());
        header = self().selectOne(requestCtx, header);

        if (header.getTransportWay() != null) {
            String s = codeService.getCodeMeaningByValue(requestCtx, DocConstant.CODE_HCUX_DOC_TRANSPORT_WAY, header.getTransportWay());
            header.setTransportWay(s);
        }

        if (header.getExemptionNature() != null) {
            String s = codeService.getCodeMeaningByValue(requestCtx, DocConstant.HCUX_DOC_EXEMPTION_NATURE, header.getExemptionNature());
            header.setExemptionNature(s);
        }

        if (header.getSupervisionMode() != null) {
            String s = codeService.getCodeMeaningByValue(requestCtx, DocConstant.HCUX_DOC_SUPERVISION_MODE, header.getSupervisionMode());
            header.setSupervisionMode(s);
        }

        if (header.getPackageType() != null) {
            String s = codeService.getCodeMeaningByValue(requestCtx, DocConstant.HCUX_DOC_PACKAGE_TYPE, header.getPackageType());
            header.setPackageType(s);
        }

        if (header.getCurrencySystem() != null) {
            String s = codeService.getCodeMeaningByValue(requestCtx, DocConstant.CODE_HCUX_DOC_CURRENCY_SYSTEM, header.getCurrencySystem());
            header.setCurrencySystem(s);
        }

//        if (header.getPackageNumberUnit() != null) {
//            String s = codeService.getCodeMeaningByValue(requestCtx, DocConstant.HCUX_DOC_PACKAGE_NUMBER_UNIT, header.getPackageNumberUnit());
//            header.setPackageNumberUnit(s);
//        }

        if (header.getTransactionMode() != null) {
            String s = codeService.getCodeMeaningByValue(requestCtx, DocConstant.HCUX_DOC_TRANSACTION_MODE, header.getTransactionMode());
            header.setTransactionMode(s);
        }

        if (DocConstant.YES_NO.Y.equals(header.getSpecialRelation())) {
            header.setSpecialRelation("是");
        } else {
            header.setSpecialRelation("否");
        }

        if (DocConstant.YES_NO.Y.equals(header.getPriceImpact())) {
            header.setPriceImpact("是");
        } else {
            header.setPriceImpact("否");
        }

        if (DocConstant.YES_NO.Y.equals(header.getPaymentRoyalties())) {
            header.setPaymentRoyalties("是");
        } else {
            header.setPaymentRoyalties("否");
        }

        // 处理特殊符号
        StringUtil.escapeBean(header);

        if (header.getFileId() != null) {
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            String url = basePath + "/api/public/hcux/doc/booking/note/download?fileId=" + header.getFileId();
            header.setDownloadUrl(url);
        }

        if (header.getShippingMark() != null) {
            header.setShippingMark(header.getShippingMark().replace("\n", "<br/>"));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("signFlag", dto.getSign());
        map.put("header", header);

        String realPath = request.getSession().getServletContext().getRealPath("/");
        map.put("realPath", realPath);

        DocCustomsLine docCustomsLine = new DocCustomsLine();
        docCustomsLine.setCustomsId(dto.getCustomsId());
        List<DocCustomsLine> lineList = docCustomsLineMapper.selectList(docCustomsLine);
        for (DocCustomsLine line : lineList) {
            if (line.getCustomsAmountThree() == null && StringUtils.isBlank(line.getCustomsUnitThree())) {
                line.setRowspan(2L);
            } else {
                line.setRowspan(3L);
            }

            if (line.getExemptionWay() != null) {
                String s = codeService.getCodeMeaningByValue(requestCtx, DocConstant.HCUX_DOC_EXEMPTION_WAY, line.getExemptionWay());
                line.setExemptionWay(s);
            }

            if (line.getPackageNumberUnit() != null) {
                String s = codeService.getCodeMeaningByValue(requestCtx, DocConstant.HCUX_DOC_PACKAGE_NUMBER_UNIT, line.getPackageNumberUnit());
                line.setPackageNumberUnit(s);
            }

            // 处理特殊符号
            StringUtil.escapeBean(line);
        }
        int size = lineList.size();
        map.put("lineList", lineList);

        String customsName = header.getConsignee(); // 获取境外收货人
        if (customsName != null) {
            customsName = "_" + customsName.split(" +")[0]; // 截取第一个单词
        } else {
            customsName = "";
        }
        String otherPackageType = header.getOtherPackageType();
        if (otherPackageType != null) {
            otherPackageType = "_" + otherPackageType;
        } else {
            otherPackageType = "";
        }
        String fileName = header.getInvoiceNumber() + "_报关单" + customsName + otherPackageType + ".pdf";

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(fileName, ENC));

        Document document = new Document();
        PdfCopy copy = new PdfCopy(document, response.getOutputStream());
        document.open();
        document.addTitle(header.getInvoiceNumber());

        // 报关委托书
        if (DocConstant.YES_NO.Y.equals(dto.getOrder())) {
            map.put("line_01", lineList.get(0));
            PdfUtil.generatePdf("order_note", map, document, copy);
        }

        // 报关单
        if (DocConstant.YES_NO.Y.equals(dto.getCustomsDeclaration())) {
            int totalPage = new BigDecimal(size - CUSTOMS_FIRST_PAGE_SIZE).divide(CUSTOMS_PAGE_SIZE_DECIMAL, 0, BigDecimal.ROUND_UP).intValue() + 1;
            totalPage = totalPage > 0 ? totalPage : 1;
            map.put("totalPage", totalPage);

            for (int i = 1; i <= totalPage; i++) {
                map.put("page", i);

                if (i == 1) {
                    map.put("lineList", lineList.subList(0, size > CUSTOMS_FIRST_PAGE_SIZE ? CUSTOMS_FIRST_PAGE_SIZE : size));
                    PdfUtil.generatePdf("customs_declaration", map, document, copy);
                } else if (i == totalPage) {
                    map.put("lineList", lineList.subList((i - 2) * CUSTOMS_PAGE_SIZE + CUSTOMS_FIRST_PAGE_SIZE, size));
                    PdfUtil.generatePdf("customs_declaration_2", map, document, copy);
                } else {
                    map.put("lineList", lineList.subList((i - 2) * CUSTOMS_PAGE_SIZE + CUSTOMS_FIRST_PAGE_SIZE, (i - 1) * CUSTOMS_PAGE_SIZE + CUSTOMS_FIRST_PAGE_SIZE));
                    PdfUtil.generatePdf("customs_declaration_2", map, document, copy);
                }
            }
        }

        // 报发
        if (DocConstant.YES_NO.Y.equals(dto.getInvoice())) {
            int totalPage = new BigDecimal(size).divide(INVOICE_PAGE_SIZE_DECIMAL, 0, BigDecimal.ROUND_UP).intValue();
            map.put("totalPage", totalPage);

            for (int i = 1; i <= totalPage; i++) {
                map.put("page", i);
                int start = (i - 1) * INVOICE_PAGE_SIZE;
                map.put("lineList", lineList.subList(start, size - start < INVOICE_PAGE_SIZE ? size : i * INVOICE_PAGE_SIZE));
                PdfUtil.generatePdf("invoice_note", map, document, copy);
            }
        }

        // 报装
        if (DocConstant.YES_NO.Y.equals(dto.getPack())) {
            int totalPage = new BigDecimal(size).divide(INVOICE_PAGE_SIZE_DECIMAL, 0, BigDecimal.ROUND_UP).intValue();
            map.put("totalPage", totalPage);

            for (int i = 1; i <= totalPage; i++) {
                map.put("page", i);
                int start = (i - 1) * INVOICE_PAGE_SIZE;
                map.put("lineList", lineList.subList(start, size - start < INVOICE_PAGE_SIZE ? size : i * INVOICE_PAGE_SIZE));
                PdfUtil.generatePdf("pack_note", map, document, copy);
            }
        }

        // 合同
        if (DocConstant.YES_NO.Y.equals(dto.getContract())) {
            map.put("lineList",lineList);
            Date invoiceDate = header.getInvoiceDate();
            header.setInvoiceDateBefore(DateUtils.addDays(invoiceDate, -31));
            header.setInvoiceDateAfter(DateUtils.addDays(invoiceDate, 45));
            //生成随机数，用来随机取图片
            Random random = new Random();
            int i = random.nextInt(2);
            header.setRandomImg(i);
            PdfUtil.generatePdf("contract_note", map, document, copy);
        }

        document.close();
    }

    @Override
    public FileInputResponse excelUpload(HttpServletRequest request, IRequest requestContext, MultipartFile file) throws Exception {
        String coverFlag = request.getParameter("coverFlag");
        String bookingFlag = request.getParameter("bookingFlag");
        String customsFlag = request.getParameter("customsFlag");

        FileInputResponse response = new FileInputResponse(false);
        Workbook workbook = null;
        try {
            workbook = CommonUtil.fileType(file);//根据excel的不同版本创建不同的workbook
        } catch (Exception e) {
            response.setError(messageSource.getMessage(e.getMessage(), null, RequestContextUtils.getLocale(request)));
            return response;
        }

        Sheet sheet = workbook.getSheetAt(0);
        if (Objects.nonNull(sheet)) {
            if (!YES_NO.Y.equals(coverFlag)) {
                Cell cell = sheet.getRow(3).getCell(1);
                if (cell == null || StringUtils.isBlank(cell.getStringCellValue())) {//项目号不能为空
                    response.setError(messageSource.getMessage(HCUX_DOC_PROJECT_NUMBER_IS_NULL, null, RequestContextUtils.getLocale(request)));
                    return response;
                }

                // 判断系统中是否存在
                Set<String> invoiceNumberSet = new HashSet<>();
                invoiceNumberSet.add(cell.getStringCellValue().toUpperCase());
                List<DocCustomsHeader> headerList = self().queryDocCustomsHeaderBySet(requestContext, invoiceNumberSet);//检查是否有重复数据
                if (headerList != null && !headerList.isEmpty()) {
                    DocCustomsHeader header = headerList.get(0);
                    if (HCUX_DOC_STATUS.G.equals(header.getStatus())
                            || HCUX_DOC_STATUS.H.equals(header.getStatus())||
                            HCUX_DOC_STATUS.D.equals(header.getStatus())||
                            HCUX_DOC_STATUS.E.equals(header.getStatus())) {
                        response.setError("与系统中已完结或已改单或已审批或审批中数据重复，不可导入！");
                        return response;
                    }

                    response.setError(messageSource.getMessage(HCUX_DOC_DUPLICATE_DATA, null, RequestContextUtils.getLocale(request)));
                    response.setCoverFlag(YES_NO.Y);
                    return response;
                }
            }

            // 获取Excel数据
            DocCustomsHeader docCustomsHeader = CommonUtil.sheetToHeader(sheet, messageSource, request);//
            // 自动转大写
            AutoUpperUtil.autoUpper(docCustomsHeader);
            DocBookingNote docBookingNote = CommonUtil.sheetToNote(sheet);
            // 自动转大写
            AutoUpperUtil.autoUpper(docBookingNote);
            //设置最终目的国的默认值为头的运抵国
            List<DocCustomsLine> lineList = CommonUtil.sheetToLineList(sheet);
            // 自动转大写
            AutoUpperUtil.autoUpper(lineList);


            //快码数据校验,校验不通过的字段值设置为空
            // 处理快码
            if (StringUtils.isNotBlank(docCustomsHeader.getTransportWay())) {
                String value = codeService.getCodeValueByMeaning(requestContext, CODE_HCUX_DOC_TRANSPORT_WAY, docCustomsHeader.getTransportWay());
                if (StringUtils.isBlank(value)) {
                    docCustomsHeader.setTransportWay(null);
                    docBookingNote.setTransportWay(null);
                } else {
                    docCustomsHeader.setTransportWay(value);
                    docBookingNote.setTransportWay(value);
                }
            }

            if (StringUtils.isNotBlank(docCustomsHeader.getTransactionMode())) {
                String value = codeService.getCodeValueByMeaning(requestContext, HCUX_DOC_TRANSACTION_MODE, docCustomsHeader.getTransactionMode());
                if (StringUtils.isBlank(value)) {
                    docCustomsHeader.setTransactionMode(null);
                } else {
                    docCustomsHeader.setTransactionMode(value);
                }
            }
            if (StringUtils.isNotBlank(docCustomsHeader.getPackageType())) {
                String value = codeService.getCodeValueByMeaning(requestContext, HCUX_DOC_PACKAGE_TYPE, docCustomsHeader.getPackageType());
                if (StringUtils.isBlank(value)) {
                    docCustomsHeader.setPackageType(null);
                } else {
                    docCustomsHeader.setPackageType(value);
                }
            }

            if (StringUtils.isNotBlank(docCustomsHeader.getCurrencySystem())) {
                String value = codeService.getCodeValueByMeaning(requestContext, CODE_HCUX_DOC_CURRENCY_SYSTEM, docCustomsHeader.getCurrencySystem());
                if (StringUtils.isBlank(value)) {
                    docCustomsHeader.setCurrencySystem(null);
                } else {
                    docCustomsHeader.setCurrencySystem(value);
                }
            }

            //读取Excel图片
            Map<String, PictureData> pictureDataMap = CommonUtil.getExcelPictureData(file);

            if ("Y".equals(bookingFlag)) {

                if (!lineList.isEmpty()) {

                    Set<String> productNameSet = new HashSet<>();
                    double packageNumber = 0d;
                    String notePackageNumberUnit = null;
                    BigDecimal grossWeight = new BigDecimal(0);
                    BigDecimal volume = new BigDecimal(0);

                    for (DocCustomsLine docCustomsLine : lineList) {
                        String packageNumberUnit = docCustomsLine.getPackageNumberUnit();
                        if (StringUtils.isNotBlank(packageNumberUnit)) {
                            String value = codeService.getCodeValueByMeaning(requestContext, HCUX_DOC_PACKAGE_NUMBER_UNIT, packageNumberUnit);
                            if (StringUtils.isBlank(value)) {
                                docCustomsLine.setPackageNumberUnit(null);
                            } else {
                                docCustomsLine.setPackageNumberUnit(value);
                            }
                        }

                        packageNumberUnit = docCustomsLine.getPackageNumberUnit();

                        // 汇总写入托单
                        if (StringUtils.isNotBlank(docCustomsLine.getProductNameEn())) {
                            productNameSet.add(docCustomsLine.getProductNameEn());
                        }
                        if (docCustomsLine.getPackageNumber() != null) {
                            packageNumber += docCustomsLine.getPackageNumber();
                        }
                        if (StringUtils.isNotBlank(packageNumberUnit)) {
                            if (StringUtils.isBlank(notePackageNumberUnit)) {
                                notePackageNumberUnit = packageNumberUnit;
                            } else {
                                if (!notePackageNumberUnit.equals(packageNumberUnit)) {
                                    notePackageNumberUnit = "4";//PKGS
                                }
                            }
                        }
                        if (docCustomsLine.getGrossWeight() != null) {
                            grossWeight = grossWeight.add(new BigDecimal(docCustomsLine.getGrossWeight()));
                        }
                        if (docCustomsLine.getVolume() != null) {
                            volume = volume.add(new BigDecimal(docCustomsLine.getVolume()));
                        }

                        if (docCustomsLine.getDestinationCountry() == null) {
                            docCustomsLine.setDestinationCountry(docCustomsHeader.getArrivalCountry());
                        }

                        docCustomsLine.set__status(DTOStatus.ADD);
                        // 设置默认值
                        docCustomsLine.setDefaultValue();
                    }
                    docCustomsHeader.setLineList(lineList);

                    //set值给托单
                    if (StringUtils.isBlank(docBookingNote.getProductName())) {
                        docBookingNote.setProductName(String.join(",", productNameSet));
                    }
                    docBookingNote.setPackageNumber(packageNumber);
                    docBookingNote.setPackageNumberUnit(notePackageNumberUnit);
                    docBookingNote.setGrossWeight(grossWeight.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    docBookingNote.setVolume(volume.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());

                }

                //插入或更新托单
                DocBookingNote selectNote = new DocBookingNote();
                selectNote.setInvoiceNumber(docBookingNote.getInvoiceNumber());
                selectNote = docBookingNoteMapper.selectDocBookingNote(selectNote);

                //托单图片
                if (pictureDataMap != null) {
                    Long attachmentId = uploadFile(HCUX_DOC_BOOKING_NOTE_SOURCE_TYPE, pictureDataMap, selectNote == null ? null : selectNote.getAttachmentId(), requestContext);
                    docBookingNote.setAttachmentId(attachmentId);
                }

                if (selectNote == null) {
                    docBookingNote.setDefaultValue();
                    docBookingNote = docBookingNoteService.insertSelective(requestContext, docBookingNote);
                } else {
                    CommonUtil.bookDataCover(docBookingNote, selectNote);
                    docBookingNote = docBookingNoteService.updateByPrimaryKeySelective(requestContext, selectNote);
                }
            }

            if ("Y".equals(customsFlag)) {
                //插入或更新报关
                if (YES_NO.Y.equals(coverFlag)) {
                    DocCustomsHeader selectCustomsHeader = new DocCustomsHeader();
                    selectCustomsHeader.setInvoiceNumber(docCustomsHeader.getInvoiceNumber());
                    selectCustomsHeader = mapper.selectOne(selectCustomsHeader);
                    CommonUtil.dataCover(docCustomsHeader, selectCustomsHeader);
                    docCustomsHeader = selectCustomsHeader;

                    if (docCustomsHeader.getStatus().equals(DocConstant.HCUX_DOC_STATUS.D) ||
                            docCustomsHeader.getStatus().equals(DocConstant.HCUX_DOC_STATUS.E) ||
                            docCustomsHeader.getStatus().equals(DocConstant.HCUX_DOC_STATUS.F)) {
                        docCustomsHeader.setStatus(DocConstant.HCUX_DOC_STATUS.C);
                    }

                    docCustomsHeader.set__status(DTOStatus.UPDATE);

                    // 删除原有的明细行
                    if (docCustomsHeader.getCustomsId() != null) {
                        DocCustomsLine line = new DocCustomsLine();
                        line.setCustomsId(docCustomsHeader.getCustomsId());
                        docCustomsLineMapper.delete(line);
                    }

                } else {
                    docCustomsHeader.setDefaultValue();
                    docCustomsHeader.set__status(DTOStatus.ADD);
                }

                // 报关图片
                if (pictureDataMap != null) {
                    Long attachmentId = uploadFile(HCUX_DOC_CUSTOMS_HEADER_SOURCE_TYPE, pictureDataMap, docCustomsHeader.getAttachmentId(), requestContext);
                    docCustomsHeader.setAttachmentId(attachmentId);
                }

                if (docBookingNote.getBookingId() != null) {
                    docCustomsHeader.setBookingId(docBookingNote.getBookingId());
                }

                if("N".equals(bookingFlag)){
                    if (!lineList.isEmpty()) {
                        for (DocCustomsLine docCustomsLine : lineList) {
                            String packageNumberUnit = docCustomsLine.getPackageNumberUnit();
                            if (StringUtils.isNotBlank(packageNumberUnit)) {
                                String value = codeService.getCodeValueByMeaning(requestContext, HCUX_DOC_PACKAGE_NUMBER_UNIT, packageNumberUnit);
                                if (StringUtils.isBlank(value)) {
                                    docCustomsLine.setPackageNumberUnit(null);
                                } else {
                                    docCustomsLine.setPackageNumberUnit(value);
                                }
                            }
                            if (docCustomsLine.getDestinationCountry() == null) {
                                docCustomsLine.setDestinationCountry(docCustomsHeader.getArrivalCountry());
                            }

                            docCustomsLine.set__status(DTOStatus.ADD);
                            // 设置默认值
                            docCustomsLine.setDefaultValue();
                        }
                        docCustomsHeader.setLineList(lineList);
                    }
                }
                //明细行的运抵国设置默认值
                if(docCustomsHeader.getArrivalCountry()!=null&&!"".equals(docCustomsHeader.getArrivalCountry())){
                    for (DocCustomsLine docCustomsLine : lineList) {
                        docCustomsLine.setDestinationCountry(docCustomsHeader.getArrivalCountry());
                    }
                }
                List<DocCustomsHeader> insertList = new ArrayList<>();
                insertList.add(docCustomsHeader);
                self().batchUpdateHeaderAndLine(request, requestContext, insertList);
            }

        }
        return response;
    }

    @Override
    public void updateOtherPackageType(IRequest requestCtx, DocCustomsHeader header) {
        // 更新其他包装种类
        List<DocCustomsLine> lineList = docCustomsLineMapper.queryPackageNumberUnit(header.getCustomsId());
        List<String> otherPackageTypeList = new ArrayList<>();
        for (DocCustomsLine line : lineList) {
            String otherPackageType = (line.getPackageNumber() == null ? "" : new BigDecimal(line.getPackageNumber()).toString()) + line.getPackageNumberUnitMeaning();
            otherPackageTypeList.add(otherPackageType);
        }
        header.setOtherPackageType(String.join(";", otherPackageTypeList));
        self().updateByPrimaryKeySelective(requestCtx, header);
    }

    /**
     * 托单、报关文件上传
     *
     * @param sourceType
     * @param pictureDataMap
     * @param attachmentId
     * @param requestContext
     * @return
     * @throws AttachmentException
     * @throws UniqueFileMutiException
     */
    private Long uploadFile(String sourceType,
                            Map<String, PictureData> pictureDataMap,
                            Long attachmentId,
                            IRequest requestContext) throws AttachmentException, UniqueFileMutiException {
        AttachCategory category = categoryService.selectAttachByCode(requestContext, sourceType);
        if (category == null) {
            throw new AttachmentException(UpConstants.ERROR_UPLOAD_SOURCE_TYPE_FOLDER_NOT_FOUND, UpConstants.ERROR_UPLOAD_SOURCE_TYPE_FOLDER_NOT_FOUND, new String[]{HCUX_DOC_CUSTOMS_HEADER_SOURCE_TYPE});
        }
        String uuid = UUID.randomUUID().toString();
        Attachment attachment = UploadUtil.genAttachment(category, uuid, requestContext.getUserId(), requestContext.getUserId());
        attachment.setAttachmentId(attachmentId);
        SysFile sysFile = new SysFile();
        sysFile.setAttachmentId(attachment.getAttachmentId());
        CommonUtil.printImg(pictureDataMap, category.getCategoryPath() + uuid, sysFile);

        if (BaseConstants.YES.equals(category.getIsUnique())) {
            sysFile = docBookingNoteService.updateOrInsertFile(requestContext, attachment, sysFile);
        } else {
            sysFile = sysFileService.insertFileAndAttach(requestContext, attachment, sysFile);
        }
        return sysFile.getAttachmentId();
    }

    private StringBuilder verifyRequired(DocCustomsHeader header) {

        StringBuilder message = new StringBuilder();
        if (StringUtils.isBlank(header.getInvoiceNumber()))
            message.append("项目号、");

        if (StringUtils.isBlank(header.getContractNumber()))
            message.append("合同协议号、");

        if (StringUtils.isBlank(header.getTransactionMode()))
            message.append("成交方式、");

        if (StringUtils.isBlank(header.getCurrencySystem()))
            message.append("币制、");

        if (StringUtils.isBlank(header.getTransportWay()))
            message.append("运输方式、");

        if (StringUtils.isBlank(header.getDeparturePort()))
            message.append("离境口岸、");

        if (StringUtils.isBlank(header.getDischargePort()))
            message.append("指运港、");

        if (StringUtils.isBlank(header.getArrivalCountry()))
            message.append("运抵国、");

        if (StringUtils.isBlank(header.getTradingCountry()))
            message.append("贸易国、");

        if (StringUtils.isBlank(header.getConsignee()))
            message.append("境外收货人、");

        if (StringUtils.isBlank(header.getPackageType()))
            message.append("包装种类、");

        if (StringUtils.isBlank(header.getSupervisionMode()))
            message.append("监管方式、");

        if (StringUtils.isBlank(header.getProductionUnit()))
            message.append("生产销售单位、");

        if (StringUtils.isBlank(header.getRemark()))
            message.append("标记唛码及备注、");

        if (StringUtils.isBlank(header.getExemptionNature()))
            message.append("征免性质、");

        if (message.length() > 0) {
            message.deleteCharAt(message.lastIndexOf("、"));
            message.insert(0, "项目号 " + header.getInvoiceNumber() + " 的报关单头信息：");
            message.append(" 字段未输，提交失败！<br/>");
        }

        DocCustomsLine docCustomsLine = new DocCustomsLine();
        docCustomsLine.setCustomsId(header.getCustomsId());
        List<DocCustomsLine> lineList = docCustomsLineMapper.selectList(docCustomsLine);
        if (lineList.size() == 0) {
            message.append("项目号 ")
                    .append(header.getInvoiceNumber())
                    .append(" 不存在明细行信息，提交失败！<br/>");
        } else {
            for (int i = 0; i < lineList.size(); i++) {
                DocCustomsLine line = lineList.get(i);

                StringBuilder lineMessage = new StringBuilder();
                if (StringUtils.isBlank(line.getGoodsNumber()))
                    lineMessage.append("商品编码、");

                if (StringUtils.isBlank(line.getProductNameCn()))
                    lineMessage.append("中文品名、");

//                if (StringUtils.isBlank(line.getProductNameEn()))
//                    lineMessage.append("英文品名、");

                if (StringUtils.isBlank(line.getDeclarationElement()))
                    lineMessage.append("申报要素、");

                if (StringUtils.isBlank(line.getPackageNumberUnit()))
                    lineMessage.append("件数单位、");
                else {
                    if (!DocConstant.PACKAGE_NUMBER_UNIT_CODE.A8.equals(line.getPackageNumberUnit())) {
                        if (line.getPackageNumber() == null)
                            lineMessage.append("件数、");

                        if (line.getGrossWeight() == null)
                            lineMessage.append("毛重、");

                        if (line.getVolume() == null)
                            lineMessage.append("体积、");
                    }
                }

                if (line.getCustomsAmountOne() == null)
                    lineMessage.append("报关数量、");

                if (StringUtils.isBlank(line.getCustomsUnitOne()))
                    lineMessage.append("报关单位、");

                if (line.getTotalPrice() == null)
                    lineMessage.append("金额、");

                if (line.getNetWeight() == null)
                    lineMessage.append("净重、");

                if (StringUtils.isBlank(line.getOriginPlace()))
                    lineMessage.append("境内货源地、");

                if (StringUtils.isBlank(line.getOriginCountry()))
                    lineMessage.append("原产国、");

                if (StringUtils.isBlank(line.getDestinationCountry()))
                    lineMessage.append("最终目的国、");

                if (StringUtils.isBlank(line.getExemptionWay()))
                    lineMessage.append("征免、");


                if (lineMessage.length() > 0) {
                    lineMessage.deleteCharAt(lineMessage.lastIndexOf("、"));
                    lineMessage.insert(0, "项目号 " + header.getInvoiceNumber() + "，项号 " + (i + 1) + " 的报关单明细信息：");
                    lineMessage.append(" 字段未输，提交失败！<br/>");
                    message.append(lineMessage);
                }
            }
        }

        return message;
    }

    /**
     * 报关单状态若是已审批，综合查询该项目号状态是报关中
     *
     * @param requestCtx
     * @param customsId
     */
    private void modifySynthesisStatus(IRequest requestCtx, Long customsId) {
        // 修改综合查询中的报关状态
        DocSynthesis docSynthesis = new DocSynthesis();
        docSynthesis.setCustomsId(customsId);
        List<DocSynthesis> synthesisList = docSynthesisMapper.select(docSynthesis);
        if (!synthesisList.isEmpty()) {
            synthesisList.forEach(x -> {
                if (x.getStatus() != null && (x.getStatus().equals("B") || Integer.parseInt(x.getStatus()) < 4)) {
                    x.setStatus("4");
                    docSynthesisService.updateByPrimaryKeySelective(requestCtx, x);
                }
            });
        }
    }

    @Override
    public int modifyStatus(IRequest iRequest, DocCustomsHeader docCustomsHeader) {
        int count = 0;
        // 2019-06-06 15:22 需求变更---去掉准备状态
        /*Calendar now = Calendar.getInstance();
        now.add(Calendar.DATE, 1);
        DocSynthesis synthesis = new DocSynthesis();
        synthesis.setCustomsStatus(HCUX_DOC_STATUS.A);
        synthesis.setSendingDateTo(now.getTime());
        List<DocSynthesis> list = docSynthesisService.selectList(iRequest, synthesis, 0, 0);
        for (DocSynthesis dto : list) {
            DocCustomsHeader header = new DocCustomsHeader();
            header.setCustomsId(dto.getCustomsId());

            DocCustomsLine line = new DocCustomsLine();
            line.setCustomsId(dto.getCustomsId());
            List<DocCustomsLine> lineList = docCustomsLineService.select(iRequest, line, 0, 0);
            if (lineList.isEmpty()) {
                header.setStatus(DocConstant.HCUX_DOC_STATUS.B);
            } else {
                header.setStatus(DocConstant.HCUX_DOC_STATUS.C);
            }
            self().updateByPrimaryKeySelective(iRequest, header);
            count++;
        }

        synthesis.setCustomsStatus(HCUX_DOC_STATUS.B);
        List<DocSynthesis> list1 = docSynthesisService.selectList(iRequest, synthesis, 0, 0);
        for (DocSynthesis dto : list1) {
            DocCustomsLine line = new DocCustomsLine();
            line.setCustomsId(dto.getCustomsId());
            List<DocCustomsLine> lineList = docCustomsLineService.select(iRequest, line, 0, 0);
            if (!lineList.isEmpty()) {
                DocCustomsHeader header = new DocCustomsHeader();
                header.setCustomsId(dto.getCustomsId());
                header.setStatus(DocConstant.HCUX_DOC_STATUS.C);
                self().updateByPrimaryKeySelective(iRequest, header);
                count++;
            }
        }*/

        docCustomsHeader.setStatus(HCUX_DOC_STATUS.E);
        List<DocCustomsHeader> docCustomsHeaderList = mapper.select(docCustomsHeader);
        if (!docCustomsHeaderList.isEmpty()) {
            for (DocCustomsHeader customsHeader : docCustomsHeaderList) {
                EpsCustomsDetail epsCustomsDetail = new EpsCustomsDetail();
                epsCustomsDetail.setInvoiceNumber(customsHeader.getInvoiceNumber());
                List<EpsCustomsDetail> detailList = epsCustomsDetailService.select(iRequest, epsCustomsDetail, 0, 0);

                if (!detailList.isEmpty()) {
                    customsHeader.setStatus(HCUX_DOC_STATUS.G);
                    self().updateByPrimaryKeySelective(iRequest, customsHeader);
                    //综合查询该项目号状态是报关中
                    modifySynthesisStatus(iRequest, customsHeader.getCustomsId());
                    count++;
                }
            }
        }

        return count;
    }

    @Override
    public DocCustomsHeader selectCopyInfo(IRequest iRequest, DocCustomsHeader docCustomsHeader) {
        DocCustomsHeader header = mapper.selectCopyInfo(docCustomsHeader);
        Double totalAmount = mapper.queryTotalAmount(docCustomsHeader);
        if (totalAmount != null) {
            header.setTotalAmount(totalAmount);
        }
        DocCustomsLine docCustomsLine = new DocCustomsLine();
        docCustomsLine.setCustomsId(docCustomsHeader.getCustomsId());
        List<DocCustomsLine> docCustomsLines = docCustomsLineMapper.selectList(docCustomsLine);
        if (docCustomsLines != null && !docCustomsLines.isEmpty()) {
            header.setGoodsNumber(docCustomsLines.get(0).getGoodsNumber());
            header.setProductNameCn(docCustomsLines.get(0).getProductNameCn());
        }
        return header;
    }

    @Override
    public void syncChangeStatus(IRequest iRequest, DocCustomsHeader docCustomsHeader) {
        if (docCustomsHeader.getCustomsId() != null) {
            docCustomsHeader.setStatus(HCUX_DOC_STATUS.A);
            List<DocCustomsHeader> list = mapper.select(docCustomsHeader);

            if (!list.isEmpty()) {
                docCustomsHeader.setStatus(HCUX_DOC_STATUS.C);
                self().updateByPrimaryKeySelective(iRequest, docCustomsHeader);
            }
        }
    }

    @Override
    public void syncModifyStatus(IRequest iRequest, DocCustomsHeader docCustomsHeader) {
        if (docCustomsHeader.getCustomsId() != null) {
            docCustomsHeader.setStatus(HCUX_DOC_STATUS.C);
            List<DocCustomsHeader> list = mapper.select(docCustomsHeader);

            if (!list.isEmpty()) {
                docCustomsHeader.setStatus(HCUX_DOC_STATUS.A);
                self().updateByPrimaryKeySelective(iRequest, docCustomsHeader);
            }
        }

    }

    @Override
    public void exportExcel(DocCustomsHeader dto, IRequest iRequest, HttpServletResponse response) throws Exception {
        if (dto.getStatus() != null) {
            List<String> statusList = new ArrayList<>(Arrays.asList(dto.getStatus().split(";")));
            dto.setStatusList(statusList);
        }

        // 总记录数
        PageHelper.startPage(1, 1);
        Page<DocCustomsLine> list = (Page<DocCustomsLine>) docCustomsLineMapper.queryHeaderAndLine(dto);
        long totalRowCount = list.getTotal();

        // 导出EXCEL文件名称
        String fileName = "报关单" + DateFormatUtils.format(new Date(), "yyyyMMdd-HHmmss");
        // 标题
        String[] titles = {"项目号", "币制", "离境口岸", "指运港", "境外收货人", "唛头", "商品编码", "中文品名", "英文品名", "件数",
                "件数单位", "报关数量", "报关单位", "金额", "毛重", "净重", "体积", "境内货源地", "申报要素"};

        // 开始导入
        PoiUtil.exportExcelToWebsite(response, totalRowCount, fileName, titles, (eachSheet, startRowCount, endRowCount, currentPage, pageSize) -> {
            PageHelper.startPage(currentPage, pageSize);
            List<DocCustomsLine> detailList = docCustomsLineMapper.queryHeaderAndLine(dto);

            if (!CollectionUtils.isEmpty(detailList)) {
                for (int i = startRowCount; i <= endRowCount; i++) {
                    SXSSFRow eachDataRow = eachSheet.createRow(i);

                    if ((i - startRowCount) < detailList.size()) {
                        DocCustomsLine line = detailList.get(i - startRowCount);

                        eachDataRow.createCell(0).setCellValue(line.getInvoiceNumber() == null ? "" : line.getInvoiceNumber());
                        eachDataRow.createCell(1).setCellValue(line.getCurrencySystem() == null ? "" : line.getCurrencySystem());
                        eachDataRow.createCell(2).setCellValue(line.getDeparturePort() == null ? "" : line.getDeparturePort());
                        eachDataRow.createCell(3).setCellValue(line.getDischargePort() == null ? "" : line.getDischargePort());
                        eachDataRow.createCell(4).setCellValue(line.getConsignee() == null ? "" : line.getConsignee());
                        eachDataRow.createCell(5).setCellValue(line.getShippingMark() == null ? "" : line.getShippingMark());
                        eachDataRow.createCell(6).setCellValue(line.getGoodsNumber() == null ? "" : line.getGoodsNumber());
                        eachDataRow.createCell(7).setCellValue(line.getProductNameCn() == null ? "" : line.getProductNameCn());
                        eachDataRow.createCell(8).setCellValue(line.getProductNameEn() == null ? "" : line.getProductNameEn());
                        if (line.getPackageNumber() != null) {
                            eachDataRow.createCell(9).setCellValue(line.getPackageNumber());
                        }

                        if (null != line.getPackageNumberUnit()) {
                            String packageNumberUnit = codeService.getCodeMeaningByValue(iRequest, HCUX_DOC_PACKAGE_NUMBER_UNIT, line.getPackageNumberUnit());

                            eachDataRow.createCell(10).setCellValue(packageNumberUnit);
                        }

                        if (line.getCustomsAmountOne() != null) {
                            eachDataRow.createCell(11).setCellValue(line.getCustomsAmountOne());
                        }
                        eachDataRow.createCell(12).setCellValue(line.getCustomsUnitOne() == null ? "" : line.getCustomsUnitOne());
                        if (line.getTotalPrice() != null) {
                            eachDataRow.createCell(13).setCellValue(line.getTotalPrice());
                        }
                        if (line.getGrossWeight() != null) {
                            eachDataRow.createCell(14).setCellValue(line.getGrossWeight());
                        }
                        if (line.getNetWeight() != null) {
                            eachDataRow.createCell(15).setCellValue(line.getNetWeight());
                        }
                        if (line.getVolume() != null) {
                            eachDataRow.createCell(16).setCellValue(line.getVolume());
                        }
                        eachDataRow.createCell(17).setCellValue(line.getOriginPlace() == null ? "" : line.getOriginPlace());
                        eachDataRow.createCell(18).setCellValue(line.getDeclarationElement() == null ? "" : line.getDeclarationElement());
                    }
                }
            }
        });
    }
}