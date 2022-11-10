package hcux.doc.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.doc.dto.DocCustomsHeader;
import hcux.doc.dto.DocCustomsLine;
import hcux.doc.mapper.DocCustomsHeaderMapper;
import hcux.doc.mapper.DocCustomsLineMapper;
import hcux.doc.service.IDocCustomsHeaderService;
import hcux.doc.service.IDocCustomsLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 报关单行表
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DocCustomsLineServiceImpl extends BaseServiceImpl<DocCustomsLine> implements IDocCustomsLineService {

    @Autowired
    private DocCustomsLineMapper mapper;
    @Autowired
    private DocCustomsHeaderMapper headerMapper;
    @Autowired
    private IDocCustomsHeaderService docCustomsHeaderService;

    @Override
    public List<DocCustomsLine> selectList(IRequest requestContext, DocCustomsLine dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.selectList(dto);
    }

    @Override
    public List<DocCustomsLine> copyRow(IRequest requestCtx, DocCustomsLine dto) {
        dto = self().selectByPrimaryKey(requestCtx, dto);

        dto.setCustomsLineId(null);
        dto.setObjectVersionNumber(null);
        dto.setPackageNumber(null);
        dto.setCustomsAmountOne(null);
        dto.setCustomsAmountThree(null);
        dto.setUnitPrice(null);
        dto.setTotalPrice(null);
        dto.setGrossWeight(null);
        dto.setNetWeight(null);
        dto.setVolume(null);
        self().insertSelective(requestCtx, dto);

        List<DocCustomsLine> list = new ArrayList<>();
        list.add(dto);
        return list;
    }

    @Override
    public List<DocCustomsLine> queryAll(IRequest requestContext, DocCustomsLine dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.queryList(dto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<DocCustomsLine> copyDocCustomsLine(IRequest requestCtx, List<DocCustomsLine> dto, String customsLineId) {
        List<DocCustomsLine> docCustomsLines = new ArrayList<>();
        if (customsLineId != null) {
            DocCustomsLine dto0 = dto.get(0);
            DocCustomsLine docCustomsLine = mapper.selectByPrimaryKey(customsLineId);
            if (dto0.getGoodsNumber() == null) {
                docCustomsLine.setGoodsNumber("");
            } else {
                docCustomsLine.setGoodsNumber(dto0.getGoodsNumber());
            }
            if (dto0.getProductNameCn() == null) {
                docCustomsLine.setProductNameCn("");
            } else {
                docCustomsLine.setProductNameCn(dto0.getProductNameCn());
            }
            if (dto0.getProductNameEn() == null) {
                docCustomsLine.setProductNameEn("");
            } else {
                docCustomsLine.setProductNameEn(dto0.getProductNameEn());
            }
            if (dto0.getCustomsUnitOne() == null) {
                docCustomsLine.setCustomsUnitOne("");
            } else {
                docCustomsLine.setCustomsUnitOne(dto0.getCustomsUnitOne());
            }
            if (dto0.getCustomsUnitThree() == null) {
                docCustomsLine.setCustomsUnitThree("");
            } else {
                docCustomsLine.setCustomsUnitThree(dto0.getCustomsUnitThree());
            }
            if (dto0.getOriginPlace() == null) {
                docCustomsLine.setOriginPlace("");
            } else {
                docCustomsLine.setOriginPlace(dto0.getOriginPlace());
            }
            if (dto0.getDeclarationElement() == null) {
                docCustomsLine.setDeclarationElement("");
            } else {
                docCustomsLine.setDeclarationElement(dto0.getDeclarationElement());
            }

            DocCustomsHeader header = new DocCustomsHeader();
            header.setCustomsId(docCustomsLine.getCustomsId());
            header = headerMapper.selectByPrimaryKey(header);
            if (header != null && header.getArrivalCountry() != null) {
                docCustomsLine.setDestinationCountry(header.getArrivalCountry());
            }
            docCustomsLine.setPackageNumberUnit("1");
            docCustomsLine.setExemptionWay("1");
            docCustomsLine.setOriginCountry("中国");
            docCustomsLines.add(self().updateByPrimaryKeySelective(requestCtx, docCustomsLine));
        } else {
            dto.forEach(x -> {
                DocCustomsHeader header = new DocCustomsHeader();
                header.setCustomsId(x.getCustomsId());
                header = headerMapper.selectByPrimaryKey(header);
                if (header != null && header.getArrivalCountry() != null) {
                    x.setDestinationCountry(header.getArrivalCountry());
                }
                x.setCustomsLineId(null);
                x.setPackageNumberUnit("1");
                x.setExemptionWay("1");
                x.setOriginCountry("中国");
                docCustomsLines.add(self().insertSelective(requestCtx, x));
            });
        }
        return docCustomsLines;
    }

    @Override
    public int batchDeleteLine(IRequest requestCtx, List<DocCustomsLine> list) {
        int i = self().batchDelete(list);

        if (!list.isEmpty()) {
            //更新头其他包装种类字段
            Long customsId = list.get(0).getCustomsId();
            if (customsId != null) {
                DocCustomsHeader header = new DocCustomsHeader();
                header.setCustomsId(customsId);
                docCustomsHeaderService.updateOtherPackageType(requestCtx, header);
            }
        }

        return i;
    }
}