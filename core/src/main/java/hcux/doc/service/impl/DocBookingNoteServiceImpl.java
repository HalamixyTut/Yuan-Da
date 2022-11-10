package hcux.doc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hand.hap.account.dto.User;
import com.hand.hap.account.mapper.UserMapper;
import com.hand.hap.attachment.dto.Attachment;
import com.hand.hap.attachment.dto.SysFile;
import com.hand.hap.attachment.exception.UniqueFileMutiException;
import com.hand.hap.attachment.mapper.AttachmentMapper;
import com.hand.hap.attachment.mapper.SysFileMapper;
import com.hand.hap.attachment.service.ISysFileService;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.core.util.UploadUtil;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.ICodeService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.doc.dto.DocBookingNote;
import hcux.doc.dto.DocCustomsHeader;
import hcux.doc.dto.DocCustomsRecord;
import hcux.doc.dto.DocSynthesis;
import hcux.doc.mapper.DocBookingNoteMapper;
import hcux.doc.mapper.DocCustomsHeaderMapper;
import hcux.doc.service.IDocBookingNoteService;
import hcux.doc.service.IDocCustomsHeaderService;
import hcux.doc.service.IDocCustomsRecordService;
import hcux.doc.service.IDocSynthesisService;
import hcux.doc.util.DocConstant;
import hcux.mdm.dto.MdmCustomer;
import hcux.mdm.service.IMdmCustomerService;
import hcux.util.excel.PoiUtil;
import org.activiti.editor.language.json.converter.util.CollectionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static hcux.eps.util.EpsConstant.HCUX_EPS_CUSTOMS_DETAIL_STATUS;

/**
 * @author feng.liu01@hand-china.com
 * @description 托单数据
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DocBookingNoteServiceImpl extends BaseServiceImpl<DocBookingNote> implements IDocBookingNoteService {

    @Autowired
    private DocBookingNoteMapper mapper;
    @Autowired
    private SysFileMapper sysFileMapper;
    @Autowired
    private AttachmentMapper attachmentMapper;
    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private IDocCustomsHeaderService docCustomsHeaderService;
    @Autowired
    private IDocCustomsRecordService docCustomsRecordService;
    @Autowired
    private IDocSynthesisService docSynthesisService;
    @Autowired
    private IMdmCustomerService mdmCustomerService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DocCustomsHeaderMapper docCustomsHeaderMapper;
    @Autowired
    private ICodeService codeService;
    @Override
    public List<DocBookingNote> selectList(IRequest requestContext, DocBookingNote dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.selectList(dto);
    }

    @Override
    public ResponseData updateDocBookingNote(HttpServletRequest request, IRequest iRequest, List<DocBookingNote> list) {
        ResponseData rd = new ResponseData();
        for (DocBookingNote note : list) {
            switch (note.get__status()) {
                case DTOStatus.ADD:
                    note.setObjectVersionNumber(1L);
                    self().insertSelective(iRequest, note);

                    //插入记录到报关
                    DocCustomsHeader header = new DocCustomsHeader();
                    header.setInvoiceNumber(note.getInvoiceNumber());

                    // 首先查询下
                    List<DocCustomsHeader> headerList = docCustomsHeaderService.select(iRequest, header, 0, 0);
                    if (headerList.isEmpty()) {
                        header.setBookingId(note.getBookingId());

                        // 查询客户数据国别
                        if (note.getCustomerId() != null) {
                            MdmCustomer customer = new MdmCustomer();
                            customer.setCustomerId(note.getCustomerId());
                            customer = mdmCustomerService.selectByPrimaryKey(iRequest, customer);
                            header.setTradingCountry(customer.getCountry());
                            header.setArrivalCountry(customer.getCountry());
                        }

                        header.setConsignee(note.getConsignee());
                        header.setTransportWay(note.getTransportWay());
                        header.setDeparturePort(note.getShipmentPort());
                        header.setDischargePort(note.getDischargePort());
                        header.setDestinationPort(note.getDestinationPort());
                        header.setExitPort(note.getShipmentPort());
                        header.setShippingMark(note.getShippingMark());
                        header.setAttachmentId(note.getAttachmentId());
                        header.setStatus(DocConstant.HCUX_DOC_STATUS.A);
                        header.setDefaultValue();
                        header.setDocumentId((Long) request.getSession(true).getAttribute(User.FIELD_EMPLOYEE_ID));
                        docCustomsHeaderService.insertSelective(iRequest, header);

                        // 保存记录
                        String currentUserName = iRequest.getUserName();
                        User user = userMapper.selectByUserName(currentUserName);
                        DocCustomsRecord record = new DocCustomsRecord(header.getCustomsId(), DocConstant.HCUX_DOC_RECORD_OPERATION.CREATE);
                        record.setOperatorId(user.getEmployeeId());
                        docCustomsRecordService.insertSelective(iRequest, record);

                        // 插入记录到综合
                        DocSynthesis docSynthesis = new DocSynthesis();
                        docSynthesis.setCustomsId(header.getCustomsId());
                        docSynthesis.setStatus("B");
                        docSynthesis.setDeclareCustomLine(note.getDeclareCustomLine());
                        docSynthesisService.insertSelective(iRequest, docSynthesis);
                    }

                    break;
                case DTOStatus.UPDATE:
                    note.setLastUpdateDate(new Date());
                    self().updateByPrimaryKey(iRequest, note);

                    //更新报关
                    DocCustomsHeader docCustomsHeader = new DocCustomsHeader();
                    docCustomsHeader.setBookingId(note.getBookingId());
                    // 查询
                    List<DocCustomsHeader> docCustomsHeaderList = docCustomsHeaderMapper.selectByBookingId(docCustomsHeader);
                    if (!docCustomsHeaderList.isEmpty()) {
                        docCustomsHeader = docCustomsHeaderList.get(0);
                        // 查询客户数据国别
                        if (note.getCustomerId() != null) {
                            MdmCustomer customer = new MdmCustomer();
                            customer.setCustomerId(note.getCustomerId());
                            customer = mdmCustomerService.selectByPrimaryKey(iRequest, customer);
                            docCustomsHeader.setTradingCountry(customer.getCountry());
                            docCustomsHeader.setArrivalCountry(customer.getCountry());
                        }

                        docCustomsHeader.setInvoiceNumber(note.getInvoiceNumber());
                        docCustomsHeader.setContractNumber(note.getInvoiceNumber());
                        docCustomsHeader.setConsignee(note.getConsignee());
                        docCustomsHeader.setTransportWay(note.getTransportWay());
                        docCustomsHeader.setDeparturePort(note.getShipmentPort());
                        docCustomsHeader.setDischargePort(note.getDischargePort());
                        docCustomsHeader.setDestinationPort(note.getDestinationPort());
                        docCustomsHeader.setExitPort(note.getShipmentPort());
                        docCustomsHeader.setShippingMark(note.getShippingMark());
                        docCustomsHeader.setAttachmentId(note.getAttachmentId());
                       /* if (DocConstant.HCUX_DOC_STATUS.D.equals(docCustomsHeader.getStatus())
                                || DocConstant.HCUX_DOC_STATUS.E.equals(docCustomsHeader.getStatus())) {
                            rd.setMessage("保存成功，报关状态为待提交，请重新提交审批");
                            docCustomsHeader.setStatus(DocConstant.HCUX_DOC_STATUS.C);
                        } else {
                            rd.setMessage("保存成功，报关单已同步修改！");
                        }*/
                        if (docCustomsHeader.getStatus().equals(DocConstant.HCUX_DOC_STATUS.A) /*|| docCustomsHeader.getStatus().equals(DocConstant.HCUX_DOC_STATUS.B)*/
                            || docCustomsHeader.getStatus().equals(DocConstant.HCUX_DOC_STATUS.C) || docCustomsHeader.getStatus().equals(DocConstant.HCUX_DOC_STATUS.F)) {
                            rd.setMessage("保存成功，报关单已同步修改！");
                        } else {
                            rd.setMessage("保存成功！");
                        }
                        docCustomsHeaderService.updateByPrimaryKeySelective(iRequest, docCustomsHeader);

                        // 保存记录
                        String currentUserName = iRequest.getUserName();
                        User user = userMapper.selectByUserName(currentUserName);
                        DocCustomsRecord record = new DocCustomsRecord(docCustomsHeader.getCustomsId(), DocConstant.HCUX_DOC_RECORD_OPERATION.MODIFY);
                        record.setOperatorId(user.getEmployeeId());
                        docCustomsRecordService.insertSelective(iRequest, record);

                    }

                    break;
                default:
                    break;
            }
        }
        rd.setRows(list);
        return rd;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysFile updateOrInsertFile(IRequest requestContext, @StdWho Attachment attach, @StdWho SysFile file)
            throws UniqueFileMutiException {
        String path;
        Long attachmentId = attach.getAttachmentId();

        // 第一次上传
        if (attachmentId == null) {
            attach.setSourceKey(UUID.randomUUID().toString());
            attach.setStartActiveDate(new Date());
            attach.setEndActiveDate(new Date());
            attachmentMapper.insertSelective(attach);
            file.setAttachmentId(attach.getAttachmentId());
            sysFileService.insert(requestContext, file);
            return file;
        } else {
            // 第二次上传,更新SysFile
            SysFile sysParams = new SysFile();
            sysParams.setAttachmentId(attachmentId);
            List<SysFile> sysFiles = sysFileMapper.select(sysParams);
            if (sysFiles.isEmpty()) {
                file.setAttachmentId(attachmentId);
                sysFileMapper.insertSelective(file);
                return file;
            } else if (sysFiles.size() > 1) {
                throw new UniqueFileMutiException();
            } else {
                // 至少存在一个
                path = sysFiles.get(0).getFilePath();
                SysFile sysFile = sysFiles.get(0);
                sysFile.setFileName(file.getFileName());
                sysFile.setFilePath(file.getFilePath());
                sysFile.setFileSize(file.getFileSize());
                sysFile.setUploadDate(file.getUploadDate());
                sysFile.setLastUpdatedBy(file.getLastUpdatedBy());
                sysFile.setFileType(file.getFileType());
                sysFileMapper.updateByPrimaryKeySelective(sysFile);
                UploadUtil.deleteFile(path);
                return sysFile;
            }
        }
    }

    public void exportExcel(DocBookingNote dto, IRequest iRequest, HttpServletResponse response) throws Exception {

        // 总记录数
        PageHelper.startPage(1, 1);
        Page<DocBookingNote> list = (Page<DocBookingNote>) mapper.selectList(dto);
        long totalRowCount = list.getTotal();

        // 导出EXCEL文件名称
        String fileName = "托单导出" + DateFormatUtils.format(new Date(), "yyyyMMdd-HHmmss");

        // 标题
        String[] titles = {"发票号", "客户订单号", "船期", "货好日","箱型","进仓或装柜","装柜地址及联系方式","其他备注信息","起运港","卸货港","目的港","运输方式","运费条款","提单份数","货代简称",
        "货代联系人","货代电话","货代邮件","报关行","托运单位","托运单位地址","托运单位代码和电话","收货单位","收货单位地址","收货单位代码和电话","通知单位","通知单位地址","通知单位代码和电话",
        "件数","件数单位","毛重","体积","品名","唛头","储运员"};

        // 开始导入
        PoiUtil.exportExcelToWebsite(response, totalRowCount, fileName, titles, (eachSheet, startRowCount, endRowCount, currentPage, pageSize) -> {

            PageHelper.startPage(currentPage, pageSize);
            List<DocBookingNote> detailList = mapper.selectList(dto);

            if (!CollectionUtils.isEmpty(detailList)) {

                for (int i = startRowCount; i <= endRowCount; i++) {
                    SXSSFRow eachDataRow = eachSheet.createRow(i);
                    if ((i - startRowCount) < detailList.size()) {

                        DocBookingNote docBookingNote = detailList.get(i - startRowCount);

                        eachDataRow.createCell(0).setCellValue(docBookingNote.getInvoiceNumber() == null ? "" : docBookingNote.getInvoiceNumber());
                        eachDataRow.createCell(1).setCellValue(docBookingNote.getCustomerOrderNum() == null ? "" : docBookingNote.getCustomerOrderNum());
                        if (null != docBookingNote.getSailingDate()) {
                            eachDataRow.createCell(2).setCellValue(org.apache.commons.lang3.time.DateFormatUtils.ISO_DATE_FORMAT.format(docBookingNote.getSailingDate()));
                        }
                        if (null != docBookingNote.getGoodsDate()) {
                            eachDataRow.createCell(3).setCellValue(org.apache.commons.lang3.time.DateFormatUtils.ISO_DATE_FORMAT.format(docBookingNote.getGoodsDate()));
                        }
                        eachDataRow.createCell(4).setCellValue(docBookingNote.getBoxType() == null ? "" : docBookingNote.getBoxType());
                        eachDataRow.createCell(5).setCellValue(docBookingNote.getCabinet() == null ? "" : docBookingNote.getCabinet());
                        eachDataRow.createCell(6).setCellValue(docBookingNote.getCabinetAddress() == null ? "" : docBookingNote.getCabinetAddress());
                        eachDataRow.createCell(7).setCellValue(docBookingNote.getRemark() == null ? "" : docBookingNote.getRemark());
                        eachDataRow.createCell(8).setCellValue(docBookingNote.getShipmentPort() == null ? "" : docBookingNote.getShipmentPort());

                        //如果卸货港为空的时候取目的港的值
                        if (null == docBookingNote.getDischargePort()) {
                            eachDataRow.createCell(9).setCellValue(docBookingNote.getDestinationPort() == null ? "" : docBookingNote.getDestinationPort());
                        }else {
                            eachDataRow.createCell(9).setCellValue(docBookingNote.getDischargePort() == null ? "" : docBookingNote.getDischargePort());
                        }
                        eachDataRow.createCell(10).setCellValue(docBookingNote.getDestinationPort() == null ? "" : docBookingNote.getDestinationPort());
                        if (null != docBookingNote.getTransportWay()) {
                            String status = codeService.getCodeMeaningByValue(iRequest, DocConstant.HCUX_DOC_TRANSPORT_WAY, docBookingNote.getTransportWay());
                            eachDataRow.createCell(11).setCellValue(status);
                        }
                        if (null != docBookingNote.getFreightClause()) {
                            String status = codeService.getCodeMeaningByValue(iRequest, DocConstant.CODE_HCUX_DOC_FREIGHT_CLAUSE, docBookingNote.getFreightClause());
                            eachDataRow.createCell(12).setCellValue(status);
                        }
                        eachDataRow.createCell(13).setCellValue(docBookingNote.getBillCopies() == null ? "" : docBookingNote.getBillCopies());
                        eachDataRow.createCell(14).setCellValue(docBookingNote.getCargoAgentUnit() == null ? "" : docBookingNote.getCargoAgentUnit());
                        eachDataRow.createCell(15).setCellValue(docBookingNote.getCargoAgent() == null ? "" : docBookingNote.getCargoAgent());
                        eachDataRow.createCell(16).setCellValue(docBookingNote.getCargoAgentMobile() == null ? "" : docBookingNote.getCargoAgentMobile());
                        eachDataRow.createCell(17).setCellValue(docBookingNote.getCargoAgentEmail() == null ? "" : docBookingNote.getCargoAgentEmail());
                        eachDataRow.createCell(18).setCellValue(docBookingNote.getDeclareCustomLine() == null ? "" : docBookingNote.getDeclareCustomLine());
                        eachDataRow.createCell(19).setCellValue(docBookingNote.getShipper() == null ? "" : docBookingNote.getShipper());
                        eachDataRow.createCell(20).setCellValue(docBookingNote.getShipperAddress() == null ? "" : docBookingNote.getShipperAddress());
                        eachDataRow.createCell(21).setCellValue(docBookingNote.getShipperMobile() == null ? "" : docBookingNote.getShipperMobile());
                        eachDataRow.createCell(22).setCellValue(docBookingNote.getConsignee() == null ? "" : docBookingNote.getConsignee());
                        eachDataRow.createCell(23).setCellValue(docBookingNote.getConsigneeAddress() == null ? "" : docBookingNote.getConsigneeAddress());
                        eachDataRow.createCell(24).setCellValue(docBookingNote.getConsigneeMobile() == null ? "" : docBookingNote.getConsigneeMobile());
                        eachDataRow.createCell(25).setCellValue(docBookingNote.getNotificationUnit() == null ? "" : docBookingNote.getNotificationUnit());
                        eachDataRow.createCell(26).setCellValue(docBookingNote.getNotificationAddress() == null ? "" : docBookingNote.getNotificationAddress());
                        eachDataRow.createCell(27).setCellValue(docBookingNote.getNotificationMobile() == null ? "" : docBookingNote.getNotificationMobile());
                        if (null !=docBookingNote.getPackageNumber()){
                            eachDataRow.createCell(28).setCellValue(docBookingNote.getPackageNumber());
                        }
                        //eachDataRow.createCell(28).setCellValue(docBookingNote.getPackageNumber());
                        if (null != docBookingNote.getPackageNumberUnit()) {
                            String status = codeService.getCodeMeaningByValue(iRequest, DocConstant.HCUX_DOC_PACKAGE_NUMBER_UNIT, docBookingNote.getPackageNumberUnit());
                            eachDataRow.createCell(29).setCellValue(status);
                        }
                        if (null !=docBookingNote.getGrossWeight()){
                            eachDataRow.createCell(30).setCellValue(docBookingNote.getGrossWeight());
                        }
                        //eachDataRow.createCell(30).setCellValue(docBookingNote.getGrossWeight());
                        if (null !=docBookingNote.getVolume()){
                            eachDataRow.createCell(31).setCellValue(docBookingNote.getVolume());
                        }
                        //eachDataRow.createCell(31).setCellValue(docBookingNote.getVolume());
                        eachDataRow.createCell(32).setCellValue(docBookingNote.getProductName() == null ? "" : docBookingNote.getProductName());
                        eachDataRow.createCell(33).setCellValue(docBookingNote.getShippingMark() == null ? "" : docBookingNote.getShippingMark());
                        eachDataRow.createCell(34).setCellValue(docBookingNote.getCreatedByName() == null ? "" : docBookingNote.getCreatedByName());

                    }
                }
            }

        });
    }
}