package hcux.eps.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.eps.dto.EpsBalance;
import hcux.eps.dto.EpsBill;
import hcux.eps.mapper.EpsBillMapper;
import hcux.eps.service.IEpsBillService;
import hcux.util.excel.PoiUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class EpsBillServiceImpl extends BaseServiceImpl<EpsBill> implements IEpsBillService {
    @Autowired
    private EpsBillMapper epsBillMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int epsBillToHap(IRequest iRequest) {
        epsBillMapper.delete(new EpsBill());
        int firstCount = epsBillMapper.insertAll();
        int secondCount = epsBillMapper.insertPayment();
        int thirdCount = epsBillMapper.insertReceiptForeign();
        int fourthCount = epsBillMapper.insertReceipt();
        return firstCount + secondCount + thirdCount + fourthCount;
    }

    @Override
    public List<EpsBill> queryEpsBill(IRequest iRequest, EpsBill epsBill, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<EpsBill> list = epsBillMapper.queryEpsBill(epsBill);
        if (!list.isEmpty()) {
            DecimalFormat decimalFormat=new DecimalFormat(".00");
            List<EpsBill> list1 = epsBillMapper.selectTotalAmount(epsBill);
            if (!list1.isEmpty()) {
                String totalAmount="";
                for(EpsBill bill:list1){
                    totalAmount+=(bill.getCurrencyCode()+" "+decimalFormat.format(bill.getRemainAmount())+"; ");
                }
 //               list.get(0).setTotalAmount(decimalFormat.format(list1.get(0).getRemainAmount()));
                list.get(0).setTotalAmount(totalAmount);
            }
        }
        return list;
    }

    @Override
    public void exportExcel(IRequest iRequest, EpsBill epsBill, HttpServletResponse response) throws Exception {

        // 总记录数
        PageHelper.startPage(1, 1);
        Page<EpsBill> list = (Page<EpsBill>) epsBillMapper.queryEpsBill(epsBill);
        long totalRowCount = list.getTotal();

        // 导出EXCEL文件名称
        String fileName = "挂账查询" + DateFormatUtils.format(new Date(), "yyyyMMdd-HHmmss");

        // 标题
        String[] titles = {"发票号", "往来方", "科目项", "业务日期", "币种", "金额"};

        // 开始导入
        PoiUtil.exportExcelToWebsite(response, totalRowCount, fileName, titles, (eachSheet, startRowCount, endRowCount, currentPage, pageSize) -> {

            PageHelper.startPage(currentPage, pageSize);
            List<EpsBill> detailList = epsBillMapper.queryEpsBill(epsBill);

            if (!CollectionUtils.isEmpty(detailList)) {

                for (int i = startRowCount; i <= endRowCount; i++) {
                    SXSSFRow eachDataRow = eachSheet.createRow(i);
                    if ((i - startRowCount) < detailList.size()) {

                        EpsBill dto = detailList.get(i - startRowCount);

                        eachDataRow.createCell(0).setCellValue(dto.getProjectName() == null ? "" : dto.getProjectName());
                        eachDataRow.createCell(1).setCellValue(dto.getPartyName() == null ? "" : dto.getPartyName());
                        eachDataRow.createCell(2).setCellValue(dto.getAccounts() == null ? "" : dto.getAccounts());

                        if (null != dto.getBalanceDate()) {
                            eachDataRow.createCell(3).setCellValue(DateFormatUtils.ISO_DATE_FORMAT.format(dto.getBalanceDate()));
                        }

                        eachDataRow.createCell(4).setCellValue(dto.getCurrencyCode() == null ? "" : dto.getCurrencyCode());

                        if (dto.getRemainAmount() != null) {
                            eachDataRow.createCell(5).setCellValue(dto.getRemainAmount());
                        }
                    }
                }
            }

        });
    }

    @Override
    public List<EpsBill> selectTotalAmount(IRequest requestContext, EpsBill dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return epsBillMapper.selectTotalAmount(dto);
    }

}