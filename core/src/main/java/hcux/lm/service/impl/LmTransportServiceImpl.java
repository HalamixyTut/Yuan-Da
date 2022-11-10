package hcux.lm.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hand.hap.attachment.UpConstants;
import com.hand.hap.attachment.dto.AttachCategory;
import com.hand.hap.attachment.dto.Attachment;
import com.hand.hap.attachment.dto.SysFile;
import com.hand.hap.attachment.exception.AttachmentException;
import com.hand.hap.attachment.service.IAttachCategoryService;
import com.hand.hap.attachment.service.IAttachmentService;
import com.hand.hap.attachment.service.ISysFileService;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.util.UploadUtil;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.IProfileService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.core.exception.HcuxException;
import hcux.lm.components.HttpUtil;
import hcux.lm.dto.*;
import hcux.lm.mapper.LmOrderHeaderMapper;
import hcux.lm.mapper.LmTransportImgMapper;
import hcux.lm.mapper.LmTransportMapper;
import hcux.lm.service.ILmOrderHeaderService;
import hcux.lm.service.ILmTransportImgService;
import hcux.lm.service.ILmTransportService;
import hcux.lm.util.CommonUtil;
import hcux.sys.dto.SysMessage;
import hcux.sys.service.ISysMessageService;
import hcux.sys.util.SysConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static hcux.doc.util.DocConstant.HCUX_DOC_CUSTOMS_HEADER_SOURCE_TYPE;

@Service
@Transactional(rollbackFor = Exception.class)
public class LmTransportServiceImpl extends BaseServiceImpl<LmTransport> implements ILmTransportService {
    @Autowired
    private LmTransportMapper mapper;
    @Autowired
    private IProfileService profileService;
    @Autowired
    private HttpUtil httpUtil;
    @Autowired
    private ILmOrderHeaderService orderHeaderService;
    @Autowired
    private ILmTransportService transportService;
    @Autowired
    private ILmTransportImgService transportImgService;
    @Autowired
    private LmTransportImgMapper lmTransportImgMapper;
    @Autowired
    private ISysMessageService sysMessageService;
    @Autowired
    private IAttachCategoryService categoryService;
    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private IAttachmentService attachmentService;
    @Autowired
    private LmOrderHeaderMapper orderHeaderMapper;


    @Override
    public ResponseData queryFromZh(IRequest requestCtx, @RequestBody ZhTransport dto) throws Exception {
        ResponseData rd = new ResponseData();

        if (dto.getEntrustId() == null && dto.getTransportId() == null) {
            throw new HcuxException("委托单ID跟运输单ID必传一个");
        }
        // 获取URL
        String url = profileService.getProfileValue(requestCtx, "HCUX_LM_TRANSPORT_QUERY_URL");

        try {
            ZhTransportResponse response = httpUtil.post(dto, url, "运单查询接口-补偿机制", ZhTransportResponse.class);

            if (response.getStatus().equals("20000")) {
                ZhTransportList zhTransportList = response.getData();

                LmOrderHeader orderHeader = new LmOrderHeader();
                orderHeader.setZhOrderId(zhTransportList.getEntrustId());
                List<LmOrderHeader> headerList = orderHeaderService.select(requestCtx, orderHeader, 0, 0);
                if (headerList.isEmpty()) {
                    throw new HcuxException("系统中未能找到委托单ID对应的委托单");
                }

                orderHeader = headerList.get(0);
                String transportUrl = profileService.getProfileValue(requestCtx, "HCUX_LM_TRANSPORT_ZH_PC");
                List<ZhTransport> list = zhTransportList.getTransportList();
                for (ZhTransport zhTransport : list) {
                    LmTransport lmTransport = new LmTransport();
                    lmTransport.setZhTransportId(zhTransport.getTransportId());
                    List<LmTransport> transportList = transportService.select(requestCtx, lmTransport, 0, 0);

                    lmTransport = CommonUtil.zhToLmTransport(zhTransport);
                    lmTransport.setLink(transportUrl + lmTransport.getZhTransportId());
                    lmTransport.setOrderId(orderHeader.getOrderId());


                    if (transportList.isEmpty()) {
                        //保存运输单
                        transportService.insertSelective(requestCtx, lmTransport);
                    } else {
                        if (zhTransport.getChangeFlag() == "1"){
                            transportService.sendMessage(requestCtx,zhTransport);
                        }
                        lmTransport.setTransportId(transportList.get(0).getTransportId());
                        transportService.updateByPrimaryKeySelective(requestCtx, lmTransport);

                        LmTransportImg lmTransportImg = new LmTransportImg();
                        lmTransportImg.setTransportId(lmTransport.getTransportId());
                        List<LmTransportImg> lmTransportImgList = transportImgService.select(requestCtx, lmTransportImg, 0, 0);
                        if (!lmTransportImgList.isEmpty()) {
                            lmTransportImgList.forEach(x -> {
                                Attachment attach = new Attachment();
                                attach.setAttachmentId(x.getAttachmentId());
                                // 确定attachmentId一定存在，否则会清空attachment表
                                if (attach.getAttachmentId() != null) {
                                    attachmentService.deleteAttachment(requestCtx, attach);
                                }
                            });

                            transportImgService.deleteImg(requestCtx, lmTransportImg);
                        }
                    }

                    //保存运输单图片
                    List<ZhTransportImg> imgList = zhTransport.getBillProgressLogVoList();
                    List<LmTransportImg> transportImgList = new ArrayList<>();
                    for (ZhTransportImg img : imgList) {
                        List<String> logContentImgList = new ArrayList<>(img.getLogContentImg());
                        for (String logContentImg : logContentImgList) {
                            LmTransportImg transportImg = new LmTransportImg();
                            transportImg.setTransportId(lmTransport.getTransportId());
                            transportImg.setImgType(img.getLogCode());
                            transportImg.setImgUrl(logContentImg);
                            transportImg = saveTransImg(requestCtx, transportImg);

                            transportImgList.add(transportImg);
                        }
                    }
                    transportImgList.forEach(x -> transportImgService.insertSelective(requestCtx, x));
                }
            } else {
                rd.setSuccess(false);
                rd.setCode(response.getStatus());
                rd.setMessage(response.getMsg());
            }

            return rd;
        } catch (HcuxException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw new HcuxException("接口调用出错");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendMessage(IRequest iRequest, ZhTransport zhTransport){
        SysMessage sysMessage = new SysMessage();
        LmOrderHeader orderHeader = new LmOrderHeader();
        LmTransport transport = new LmTransport();
        orderHeader.setZhOrderId(zhTransport.getEntrustId());
        transport.setTransportCode(zhTransport.getTransportNo());
        LmOrderHeader lmOrderHeader = orderHeaderMapper.selectById(orderHeader);
        LmTransport lmTransport = mapper.selectByTrancode(transport);
        if(!lmTransport.getDriverName().equals(zhTransport.getTruckerName()) || !lmTransport.getCarNumber().equals(zhTransport.getVehicleNo())){
            String s = "";
            Date date = new Date();
            if (!lmTransport.getDriverName().equals(zhTransport.getTruckerName()) && !lmTransport.getCarNumber().equals(zhTransport.getVehicleNo())){
                s = "委托单"+lmOrderHeader.getOrderNumber()+"下运输单"+zhTransport.getTransportNo()+"司机和车牌发生变更，原司机"+lmTransport.getDriverName()+"变更为"+zhTransport.getTruckerName()+"，原车号"+lmTransport.getCarNumber()+"变更为"+zhTransport.getVehicleNo()+"。";
            } else if (!lmTransport.getDriverName().equals(zhTransport.getTruckerName()) && lmTransport.getCarNumber().equals(zhTransport.getVehicleNo())){
                s = "委托单"+lmOrderHeader.getOrderNumber()+"下运输单"+zhTransport.getTransportNo()+"司机发生变更，原司机"+lmTransport.getDriverName()+"变更为"+zhTransport.getTruckerName()+"。";
            } else if (lmTransport.getDriverName().equals(zhTransport.getTruckerName()) && !lmTransport.getCarNumber().equals(zhTransport.getVehicleNo())){
                s = "委托单"+lmOrderHeader.getOrderNumber()+"下运输单"+zhTransport.getTransportNo()+"车牌发生变更，原车号"+lmTransport.getCarNumber()+"变更为"+zhTransport.getVehicleNo()+"。";
            }
            sysMessage.setOwnerId(lmOrderHeader.getCreatedBy());
            sysMessage.setSourceType(SysConstant.MESSAGE_SOURCE_TYPE.HCUX_LM_TRANSPORT_CHANGE);
            sysMessage.setSourceKey(date.getTime());
            sysMessage.setChangeKey(lmTransport.getTransportId());
            sysMessage.setContent(s);
            sysMessageService.saveAndPublish(iRequest, sysMessage);
        }
    }

    @Override
    public List<LmTransport> queryTransport(IRequest requestContext, LmTransport dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<LmTransport> list = mapper.queryTransport(dto);
        for (LmTransport lmTransport : list) {
            LmTransportImg img = new LmTransportImg();
            img.setTransportId(lmTransport.getTransportId());
            Long imgCount = lmTransportImgMapper.queryImgCount(img);
            lmTransport.setImgCount(imgCount);
        }

        return list;
    }

    @Override
    public List<LmTransport> queryForPortal(IRequest requestContext, LmTransport dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.queryForPortal(dto);
    }

    @Override
    public void saveAndUpdate(IRequest iRequest, ZhTransport dto, LmOrderHeader orderHeader) throws Exception {
        String transportUrl = profileService.getProfileValue(iRequest, "HCUX_LM_TRANSPORT_ZH_PC");
        LmTransport lmTransport = new LmTransport();
        lmTransport.setZhTransportId(dto.getTransportId());
        List<LmTransport> transportList = transportService.select(iRequest, lmTransport, 0, 0);

        lmTransport = CommonUtil.zhToLmTransport(dto);
        lmTransport.setLink(transportUrl + lmTransport.getZhTransportId());
        lmTransport.setOrderId(orderHeader.getOrderId());

        if (transportList.isEmpty()) {
            //保存运输单
            transportService.insertSelective(iRequest, lmTransport);

            String s = orderHeader.getOrderNumber() +
                    "该委托单" +
                    orderHeader.getCarrier() +
                    "已经派车，运输单号为" +
                    lmTransport.getTransportCode() +"，数量"+lmTransport.getDispatchAmount()+lmTransport.getUnit()+
                    "。温馨提醒：早发货可减少司机的等待时间哦！";

            // 保存消息记录并推送消息
            SysMessage message = new SysMessage();
            message.setContent(s);
            message.setSourceKey(lmTransport.getTransportId());
            message.setSourceType(SysConstant.MESSAGE_SOURCE_TYPE.HCUX_LM_TRANSPORT);
            message.setOwnerId(orderHeader.getCreatedBy());
            sysMessageService.saveAndPublish(iRequest, message);


        } else {
            if (dto.getChangeFlag().equals("1")){
                transportService.sendMessage(iRequest,dto);
            }
            lmTransport.setTransportId(transportList.get(0).getTransportId());
            transportService.updateByPrimaryKeySelective(iRequest, lmTransport);

            LmTransportImg lmTransportImg = new LmTransportImg();
            lmTransportImg.setTransportId(lmTransport.getTransportId());
            List<LmTransportImg> lmTransportImgList = transportImgService.select(iRequest, lmTransportImg, 0, 0);
            if (!lmTransportImgList.isEmpty()) {
                lmTransportImgList.forEach(x -> {
                    Attachment attach = new Attachment();
                    attach.setAttachmentId(x.getAttachmentId());
                    // 确定attachmentId一定存在，否则会清空attachment表
                    if (attach.getAttachmentId() != null) {
                        attachmentService.deleteAttachment(iRequest, attach);
                    }
                });

                transportImgService.deleteImg(iRequest, lmTransportImg);
            }
        }
        //保存运输单图片
        List<ZhTransportImg> imgList = dto.getBillProgressLogVoList();
        List<LmTransportImg> transportImgList = new ArrayList<>();
        for (ZhTransportImg img : imgList) {
            List<String> logContentImgList = new ArrayList<>(img.getLogContentImg());
            for (String logContentImg : logContentImgList) {
                LmTransportImg transportImg = new LmTransportImg();
                transportImg.setTransportId(lmTransport.getTransportId());
                transportImg.setImgType(img.getLogCode());
                transportImg.setImgUrl(logContentImg);
                transportImg = saveTransImg(iRequest, transportImg);

                transportImgList.add(transportImg);
            }
        }
        transportImgList.forEach(x -> transportImgService.insertSelective(iRequest, x));
    }

    /**
     * 获取运输单下载后的attachmentId
     *
     * @param iRequest
     * @param transportImg
     * @return
     * @throws Exception
     */
    @Override
    public LmTransportImg saveTransImg (IRequest iRequest, LmTransportImg transportImg) throws Exception {
        AttachCategory category = categoryService.selectAttachByCode(iRequest, "LM_TRANSPORT_IMG");
        if (category == null) {
            throw new AttachmentException(UpConstants.ERROR_UPLOAD_SOURCE_TYPE_FOLDER_NOT_FOUND, UpConstants.ERROR_UPLOAD_SOURCE_TYPE_FOLDER_NOT_FOUND, new String[]{HCUX_DOC_CUSTOMS_HEADER_SOURCE_TYPE});
        }
        String uuid = UUID.randomUUID().toString();
        Attachment attachment = UploadUtil.genAttachment(category, uuid, iRequest.getUserId(), iRequest.getUserId());
        SysFile sysFile = new SysFile();
        CommonUtil.downloadFile(category.getCategoryPath() + uuid, transportImg, sysFile);

        if (BaseConstants.YES.equals(category.getIsUnique())) {
            sysFile = sysFileService.updateOrInsertFile(iRequest, attachment, sysFile);
        } else {
            sysFile = sysFileService.insertFileAndAttach(iRequest, attachment, sysFile);
        }

        transportImg.setAttachmentId(sysFile.getAttachmentId());

        return transportImg;
    }
}