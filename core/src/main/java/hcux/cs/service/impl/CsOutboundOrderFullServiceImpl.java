package hcux.cs.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.account.dto.User;
import com.hand.hap.account.service.IUserService;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import hcux.core.exception.HcuxException;
import hcux.core.util.HcuxConstant;
import hcux.cs.dto.CsOutboundItemFull;
import hcux.cs.dto.CsOutboundOrderFull;
import hcux.cs.dto.CsOutboundTransFull;
import hcux.cs.mapper.CsOutboundItemFullMapper;
import hcux.cs.mapper.CsOutboundOrderFullMapper;
import hcux.cs.mapper.CsOutboundTransFullMapper;
import hcux.cs.service.ICsOutboundItemFullService;
import hcux.cs.service.ICsOutboundOrderFullService;
import hcux.cs.service.ICsOutboundTransFullService;
import hcux.sys.dto.SysMessage;
import hcux.sys.mapper.SysPlateSectionMapper;
import hcux.sys.service.ISysMessageService;
import hcux.sys.util.SysConstant;
import hcux.util.PdfUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author haojie.zhang@hand-china.com
 * @description 出库物流
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CsOutboundOrderFullServiceImpl extends BaseServiceImpl<CsOutboundOrderFull> implements ICsOutboundOrderFullService {
    /**
     * 文件下载默认编码.
     */
    private static final String ENC = "UTF-8";

    @Autowired
    private CsOutboundOrderFullMapper mapper;
    @Autowired
    private CsOutboundItemFullMapper itemFullMapper;
    @Autowired
    private CsOutboundTransFullMapper transFullMapper;
    @Autowired
    private ICsOutboundItemFullService csOutboundItemFullService;
    @Autowired
    private ICsOutboundTransFullService csOutboundTransFullService;
    @Autowired
    private SysPlateSectionMapper sysPlateSectionMapper;
    @Autowired
    private ISysMessageService sysMessageService;
    @Autowired
    private IUserService iUserService;

    @Override
    public List<CsOutboundOrderFull> selectData(IRequest requestContext, CsOutboundOrderFull dto, int page, int pageSize) {

        if (HcuxConstant.QUERY_TYPE.PORTAL.equals(dto.getQueryType())) {
            List<String> sections = sysPlateSectionMapper.queryPlateSection(requestContext.getUserId());
            dto.setSectionList(sections);
        }

        if (HcuxConstant.QUERY_TYPE.HAP.equals(dto.getQueryType())) {
            List<String> sections = sysPlateSectionMapper.queryUserSection(requestContext.getUserId());
            dto.setSectionList(sections);
        }

        PageHelper.startPage(page, pageSize);
        return mapper.selectData(dto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int ebsCsOutboundFullToHap(IRequest iRequest, CsOutboundOrderFull csOutboundOrderFull) {
        int orderNum = ebsCsOutboundOrderFullToHap(iRequest, csOutboundOrderFull);
        int itemNum = ebsCsOutboundItemFullToHap(iRequest, csOutboundOrderFull);
        int transNum = ebsCsOutboundTransFullToHap(iRequest, csOutboundOrderFull);

        return orderNum + itemNum + transNum;
    }

    @Transactional(rollbackFor = Exception.class)
    public int ebsCsOutboundOrderFullToHap(IRequest iRequest, CsOutboundOrderFull csOutboundOrderFull) {
        List<CsOutboundOrderFull> csOutboundOrderFullList = mapper.selectCsOutboundFullFromEbs(csOutboundOrderFull);
        Optional<List<CsOutboundOrderFull>> optionalOrderFullList = Optional.ofNullable(csOutboundOrderFullList);

        if (optionalOrderFullList.isPresent()) {
            for (CsOutboundOrderFull x : csOutboundOrderFullList) {
                CsOutboundOrderFull csOutboundOrderFull1 = mapper.selectCsOutboundFullFromHap(x);
                Optional<CsOutboundOrderFull> OutboundOptional = Optional.ofNullable(csOutboundOrderFull1);
                if (OutboundOptional.isPresent()) {
                    x.setOrderAllId(csOutboundOrderFull1.getOrderAllId());
                    x.set__status(DTOStatus.UPDATE);
                    x.setObjectVersionNumber(csOutboundOrderFull1.getObjectVersionNumber());
                    self().updateByPrimaryKeySelective(iRequest, x);
                } else {
                    self().insertSelective(iRequest, x);
                }
            }
        }
        return csOutboundOrderFullList == null ? 0 : csOutboundOrderFullList.size();
    }

    @Transactional(rollbackFor = Exception.class)
    public int ebsCsOutboundItemFullToHap(IRequest iRequest, CsOutboundOrderFull csOutboundOrderFull) {
        List<CsOutboundItemFull> csOutboundItemFullList = itemFullMapper.selectCsOutboundItemFullFromEbs(csOutboundOrderFull);
        Optional<List<CsOutboundItemFull>> optionalItemFullList = Optional.ofNullable(csOutboundItemFullList);
        if (optionalItemFullList.isPresent()) {
            for (CsOutboundItemFull x : csOutboundItemFullList) {
                CsOutboundItemFull csOutboundItemFull = itemFullMapper.selectCsOutboundItemFullFromHap(x);
                Optional<CsOutboundItemFull> OutboundOptional = Optional.ofNullable(csOutboundItemFull);

                if (OutboundOptional.isPresent()) {
                    x.setItemAllId(csOutboundItemFull.getItemAllId());
                    x.set__status(DTOStatus.UPDATE);
                    x.setObjectVersionNumber(csOutboundItemFull.getObjectVersionNumber());
                    csOutboundItemFullService.updateByPrimaryKey(iRequest, x);
                } else {
                    csOutboundItemFullService.insertSelective(iRequest, x);
                }
            }
        }

        return csOutboundItemFullList == null ? 0 : csOutboundItemFullList.size();
    }

    @Transactional(rollbackFor = Exception.class)
    public int ebsCsOutboundTransFullToHap(IRequest iRequest, CsOutboundOrderFull csOutboundOrderFull) {
        List<CsOutboundTransFull> csOutboundTransFullList = transFullMapper.selectCsOutboundTransFullFromEbs(csOutboundOrderFull);
        Optional<List<CsOutboundTransFull>> optionalTransFullList = Optional.ofNullable(csOutboundTransFullList);
        if (optionalTransFullList.isPresent()) {
            for (CsOutboundTransFull x : csOutboundTransFullList) {
                CsOutboundTransFull csOutboundTransFull = transFullMapper.selectCsOutboundTransFullFromHap(x);
                Optional<CsOutboundTransFull> OutboundOptional = Optional.ofNullable(csOutboundTransFull);

                if (OutboundOptional.isPresent()) {
                    x.setTransFullId(csOutboundTransFull.getTransFullId());
                    x.set__status(DTOStatus.UPDATE);
                    x.setObjectVersionNumber(csOutboundTransFull.getObjectVersionNumber());
                    csOutboundTransFullService.updateByPrimaryKeySelective(iRequest, x);
                } else {
                    csOutboundTransFullService.insertSelective(iRequest, x);
                }
            }
        }

        return csOutboundTransFullList == null ? 0 : csOutboundTransFullList.size();
    }

    @Override
    public void pdfPrint(IRequest requestCtx, String invoiceIds, HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException, HcuxException {
        List<String> invoiceIdList = new ArrayList<>(Arrays.asList(invoiceIds.split(";")));

        String realPath = request.getSession().getServletContext().getRealPath("/");
        String fileName = "出库物流确认收货单.pdf";
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(fileName, ENC));

        Document document = new Document();
        PdfCopy copy = new PdfCopy(document, response.getOutputStream());
        document.open();

        for (String x : invoiceIdList) {
            Long invoiceId = Long.parseLong(x);
            CsOutboundOrderFull orderFull = new CsOutboundOrderFull();
            orderFull.setInvoiceId(invoiceId);

            List<CsOutboundOrderFull> orderFulls = mapper.selectPrintData(orderFull);
            if (orderFulls != null && !orderFulls.isEmpty()) {
                Map<String, Object> map = new HashMap<>();
                map.put("realPath", realPath);
                map.put("orderFull", orderFulls.get(0));
                PdfUtil.generatePdf("cs_order_full", map, document, copy);
            }
        }

        document.close();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendMessage(IRequest iRequest,CsOutboundOrderFull csOutboundOrderFull){
        SysMessage sysMessage = new SysMessage();
        String createPerson = csOutboundOrderFull.getCreatePerson();
        String pattern = "[\u4e00-\u9fa5]+";
        Pattern reg = Pattern.compile(pattern);
        Matcher match = reg.matcher(createPerson);
        if (match.find()) {
            String employeeName = match.group();
            User user = new User();
            user.setEmployeeName(employeeName);
            List<User>  userList = iUserService.selectUsers(iRequest, user, 0, 0);
            String s = "你有一条收货确认提醒，"+csOutboundOrderFull.getPartyName()+"，"+csOutboundOrderFull.getInvoiceNo()+"，"+csOutboundOrderFull.getOutboundQty()+csOutboundOrderFull.getUnitOfMeasure();
            if (!userList.isEmpty()) {
                sysMessage.setOwnerId(userList.get(0).getUserId());
                sysMessage.setSourceType(SysConstant.MESSAGE_SOURCE_TYPE.HCUX_CS_CONFIRM_RECEIPT);
                sysMessage.setSourceKey(csOutboundOrderFull.getOrderAllId());
                sysMessage.setContent(s);
                sysMessageService.saveAndPublish(iRequest, sysMessage);
            }
        }
    }
}