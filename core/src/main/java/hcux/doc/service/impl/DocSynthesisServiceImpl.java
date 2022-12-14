package hcux.doc.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.doc.dto.DocBookingNote;
import hcux.doc.dto.DocCustomsHeader;
import hcux.doc.dto.DocSynthesisFile;
import hcux.doc.mapper.DocCustomsHeaderMapper;
import hcux.doc.mapper.DocSynthesisFileMapper;
import hcux.doc.mapper.DocSynthesisMapper;
import hcux.doc.service.IDocBookingNoteService;
import hcux.doc.service.IDocCustomsHeaderService;
import hcux.doc.util.CommonUtil;
import hcux.doc.util.DocConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hcux.doc.dto.DocSynthesis;
import hcux.doc.service.IDocSynthesisService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class DocSynthesisServiceImpl extends BaseServiceImpl<DocSynthesis> implements IDocSynthesisService {
    @Autowired
    private DocSynthesisMapper docSynthesisMapper;
    @Autowired
    private IDocBookingNoteService docBookingNoteService;
    @Autowired
    private DocCustomsHeaderMapper docCustomsHeaderMapper;
    @Autowired
    private IDocCustomsHeaderService iDocCustomsHeaderService;
    @Autowired
    private DocSynthesisFileMapper docSynthesisFileMapper;

    @Override
    public List<DocSynthesis> selectList(IRequest requestContext, DocSynthesis dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return docSynthesisMapper.selectLists(dto);
    }

    @Override
    public List<DocSynthesis> updateSynthesis(IRequest requestContext, List<DocSynthesis> dto) {
        if (dto != null && !dto.isEmpty()) {
            for (DocSynthesis docSynthesis : dto) {
//                if (docSynthesis.getBookingId() != null) {
//                    if (!CommonUtil.objectIsNull(docSynthesis.getCargoAgentUnit()) || !CommonUtil.objectIsNull(docSynthesis.getDeclareCustomLine()) ||
//                            !CommonUtil.objectIsNull(docSynthesis.getBoxType())) {
//                        DocBookingNote docBookingNote = new DocBookingNote() {{
//                            set__status(DTOStatus.UPDATE);
//                            setBookingId(docSynthesis.getBookingId());
//                            if (!CommonUtil.objectIsNull(docSynthesis.getCargoAgentUnit())) {
//                                setCargoAgentUnit(docSynthesis.getCargoAgentUnit());
//                            }
//                            if (!CommonUtil.objectIsNull(docSynthesis.getDeclareCustomLine())) {
//                                setDeclareCustomLine(docSynthesis.getDeclareCustomLine());
//                            }
//                            if (!CommonUtil.objectIsNull(docSynthesis.getBoxType())) {
//                                setBoxType(docSynthesis.getBoxType());
//                            }
//                        }};
//                        docBookingNoteService.updateByPrimaryKeySelective(requestContext, docBookingNote);
//                    }
//                }
                modifyDocSynthesisStatus(requestContext,docSynthesis);
                if(DTOStatus.UPDATE.equals(docSynthesis.get__status())){
                    docSynthesis.setLastUpdateDate(new Date());
                    self().updateByPrimaryKey(requestContext,docSynthesis);
                }
            }
        }

        return dto;
    }

    @Override
    public int modifyStatus(IRequest iRequest, DocSynthesis docSynthesis) {
        int count = 0;
        List<DocSynthesis> list = docSynthesisMapper.queryStatus();
        if (!list.isEmpty()) {
            Calendar now2 =Calendar.getInstance();
            Calendar now3 =Calendar.getInstance();
            now2.add(Calendar.DATE, 2);
            now3.add(Calendar.DATE, 1);

            for (DocSynthesis dto : list) {
                String status = dto.getStatus();

                if (!CommonUtil.objectIsNull(dto.getBoxDate()) && (dto.getStatus().equals("B") || dto.getStatus().equals("0") ||  dto.getStatus().equals("1"))) {
                    //???????????? - ??????????????????????????????????????????????????????
                    if (now2.getTime().compareTo(dto.getBoxDate())>=0) {
                        dto.setStatus("2");
                    }
                }

                if (!CommonUtil.objectIsNull(dto.getSendingDate())) {
                    //???????????? - ????????????????????????????????????-1
                    if (now3.getTime().compareTo(dto.getSendingDate())>=0) {
                        dto.setStatus("3");
                        //2019-06-06 15:14 ????????????---?????????????????????????????????
                        DocCustomsHeader header = new DocCustomsHeader();
                        header.setCustomsId(dto.getCustomsId());
                        iDocCustomsHeaderService.syncChangeStatus(iRequest, header);
                    }
                }
                if (!status.equals(dto.getStatus())) {
                    count++;
                }
                docSynthesisMapper.updateByPrimaryKeySelective(dto);
            }
        }

        return count;
    }

    /**
     * ?????????????????????????????????????????????????????????????????????????????????????????????????????? ??????????????????????????????????????????????????? ?????????
     *
     * @param requestCtx
     * @param customsId
     */
//    public void modifyDocCustomsStatus(IRequest requestCtx, Long customsId) {
//        //????????????????????????????????????
//        DocCustomsHeader docCustomsHeader = new DocCustomsHeader();
//        docCustomsHeader.setCustomsId(customsId);
//        DocCustomsHeader dto = docCustomsHeaderMapper.selectByPrimaryKey(docCustomsHeader);
//        if (!CommonUtil.objectIsNull(dto)) {
//            dto.setStatus(DocConstant.HCUX_DOC_STATUS.G);
//            iDocCustomsHeaderService.updateByPrimaryKeySelective(requestCtx, dto);
//        }
//    }

    /**
     * ????????????????????????
     *
     * @param requestContext
     * @param docSynthesis
     */
    @Override
    public DocSynthesis modifyDocSynthesisStatus(IRequest requestContext, DocSynthesis docSynthesis) {
        Calendar now =Calendar.getInstance();
        if (!CommonUtil.objectIsNull(docSynthesis.getCancelFlag()) && docSynthesis.getCancelFlag().equals("Y")) {
            docSynthesis.setStatus("8");//8????????????
            //?????????????????????????????????
            //modifyDocCustomsStatus(requestContext, docSynthesis.getCustomsId());
        } else  {
            DocSynthesisFile synthesisFile = new DocSynthesisFile();
            synthesisFile.setSynthesisId(docSynthesis.getSynthesisId());
            synthesisFile.setFileType("0");
            List<DocSynthesisFile> list = docSynthesisFileMapper.selectLists(synthesisFile);
            Boolean flag = false; //?????????????????????????????????(?????????????????????????????????)
            if (!list.isEmpty()) {
                for (DocSynthesisFile file : list) {
                    if (file.getFileId() != null) {
                        flag = true;
                        break;
                    }
                }
            }

            if (flag) {
                docSynthesis.setStatus("7"); //7???????????????
            } else if (!CommonUtil.objectIsNull(docSynthesis.getFreightFlag()) && docSynthesis.getFreightFlag().equals("Y")) {
                docSynthesis.setStatus("6");//6.????????????;
                //?????????????????????????????????
                //modifyDocCustomsStatus(requestContext, docSynthesis.getCustomsId());
            } else if (!CommonUtil.objectIsNull(docSynthesis.getAtd()) && now.getTime().compareTo(docSynthesis.getAtd()) >= 0) {
                docSynthesis.setStatus("5");//5????????????
                //?????????????????????????????????
                // modifyDocCustomsStatus(requestContext, docSynthesis.getCustomsId());
            } else {
                DocCustomsHeader docCustomsHeader = new DocCustomsHeader();
                docCustomsHeader.setCustomsId(docSynthesis.getCustomsId());
                DocCustomsHeader dto = docCustomsHeaderMapper.selectByPrimaryKey(docCustomsHeader);

                Calendar now2 =Calendar.getInstance();
                Calendar now3 =Calendar.getInstance();
                now2.add(Calendar.DATE, 2);
                now3.add(Calendar.DATE, 1);

                if (!CommonUtil.objectIsNull(dto) && !CommonUtil.objectIsNull(dto.getStatus()) && (dto.getStatus().equals(DocConstant.HCUX_DOC_STATUS.E) || dto.getStatus().equals(DocConstant.HCUX_DOC_STATUS.G))) {
                    docSynthesis.setStatus("4");//4???????????? - ?????????????????????????????????
                } else if (!CommonUtil.objectIsNull(docSynthesis.getSendingDate()) && now3.getTime().compareTo(docSynthesis.getSendingDate()) >= 0) {
                    //docSynthesis.setStatus("3");//3??????????????? - ????????????????????????????????????
                    docSynthesis.setStatus("3");//2019-06-27????????????: ???????????? - ??????????????????????????????????????????1???

                    //2019-06-06 15:14 ????????????---?????????????????????????????????
                    DocCustomsHeader header = new DocCustomsHeader();
                    header.setCustomsId(docSynthesis.getCustomsId());
                    iDocCustomsHeaderService.syncChangeStatus(requestContext, header);
                } else {
                    if (!CommonUtil.objectIsNull(docSynthesis.getBoxDate()) && now2.getTime().compareTo(docSynthesis.getBoxDate()) >= 0) {
                        docSynthesis.setStatus("2");//2??????????????? ??????????????????????????????????????????????????????
                    } else if (!CommonUtil.objectIsNull(docSynthesis.getCabinFlag()) && docSynthesis.getCabinFlag().equals("Y")) {
                        docSynthesis.setStatus("1");//1????????????
                    } else if (!CommonUtil.objectIsNull(docSynthesis.getSailingDate())) {
                        docSynthesis.setStatus("0");//0????????????
                    } else {
                        docSynthesis.setStatus("B"); //??????
                    }

                    //2019-06-06 16:43 ????????????---??????????????????????????????
                    DocCustomsHeader header = new DocCustomsHeader();
                    header.setCustomsId(docSynthesis.getCustomsId());
                    iDocCustomsHeaderService.syncModifyStatus(requestContext, header);
                }
            }
        }

        return docSynthesis;
    }

    /**
     * ????????????????????????????????????????????????????????????????????????????????????
     *
     * @param requestContext
     * @param customsId
     */
    @Override
    public void relateModifyStatus(IRequest requestContext, Long customsId) {
        if (customsId != null) {
            DocSynthesis dto = new DocSynthesis();
            dto.setCustomsId(customsId);
            dto.setStatus("4");
            List<DocSynthesis> list = docSynthesisMapper.select(dto);

            if (!list.isEmpty()) {
                Calendar now2 =Calendar.getInstance();
                Calendar now3 =Calendar.getInstance();
                now2.add(Calendar.DATE, 2);
                now3.add(Calendar.DATE, 1);

                for (DocSynthesis docSynthesis : list) {
                    if (!CommonUtil.objectIsNull(docSynthesis.getSendingDate()) && now3.getTime().compareTo(docSynthesis.getSendingDate()) >= 0) {
                        // docSynthesis.setStatus("3");//3??????????????? - ????????????????????????????????????
                        docSynthesis.setStatus("3");//2019-06-27????????????: ???????????? - ??????????????????????????????????????????1???
                    } else {
                        if (!CommonUtil.objectIsNull(docSynthesis.getBoxDate()) && now2.getTime().compareTo(docSynthesis.getBoxDate()) >= 0) {
                            docSynthesis.setStatus("2");//2??????????????? ??????????????????????????????????????????????????????
                        } else if (!CommonUtil.objectIsNull(docSynthesis.getCabinFlag()) && docSynthesis.getCabinFlag().equals("Y")) {
                            docSynthesis.setStatus("1");//1????????????
                        } else if (!CommonUtil.objectIsNull(docSynthesis.getSailingDate())) {
                            docSynthesis.setStatus("0");//0????????????
                        } else {
                            docSynthesis.setStatus("B");//??????
                        }

                        //2019-06-06 16:43 ????????????---??????????????????????????????
                        DocCustomsHeader header = new DocCustomsHeader();
                        header.setCustomsId(customsId);
                        iDocCustomsHeaderService.syncModifyStatus(requestContext, header);
                    }

                    self().updateByPrimaryKeySelective(requestContext, docSynthesis);
                }
            }
        }
    }
}