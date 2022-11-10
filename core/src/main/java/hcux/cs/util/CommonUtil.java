package hcux.cs.util;

import hcux.core.exception.HcuxException;
import hcux.cs.dto.CsContract;
import hcux.cs.dto.CsInvoiceExpress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.hand.hap.core.BaseConstants.DATE_FORMAT;

public class CommonUtil {
    /**
     * 用于校验excel导入时某行数据是否为空
     *
     * @param row
     * @return
     */
    public static boolean rowIsNull(Row row) {
        boolean flag = true;
        for (int i = 0; i < 8; i++) {
            if (!hcux.doc.util.CommonUtil.objectIsNull(row.getCell(i))) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    public static CsInvoiceExpress rowToCsInvoiceExpress(Row row) throws ParseException, HcuxException {
        String receiverMobile = null;
        Date invoiceDate = null;
        String expressNumber = null;
        if (!hcux.doc.util.CommonUtil.objectIsNull(row.getCell(3))) {
            if (row.getCell(3).getCellTypeEnum().equals(CellType.STRING)) {
                receiverMobile = row.getCell(3).getStringCellValue();
            } else {
                BigDecimal bigDecimal = new BigDecimal(row.getCell(3).getNumericCellValue());

                receiverMobile = bigDecimal.toString();
            }
        }
        if (!hcux.doc.util.CommonUtil.objectIsNull(row.getCell(4))) {
            try {
                if (row.getCell(4).getCellTypeEnum().equals(CellType.STRING)) {
                    invoiceDate = new SimpleDateFormat(DATE_FORMAT).parse(row.getCell(4).getStringCellValue());
                } else {
                    invoiceDate = row.getCell(4).getDateCellValue();
                }
            } catch (Exception e) {
                throw new HcuxException("日期格式错误,日期格式需为Excel标准日期框或yyyy-MM-dd格式,请修改后上传");
            }
        }
        if (!hcux.doc.util.CommonUtil.objectIsNull(row.getCell(5))) {
            if (row.getCell(5).getCellTypeEnum().equals(CellType.STRING)) {
                expressNumber = row.getCell(5).getStringCellValue();
            } else {
                BigDecimal bigDecimal = new BigDecimal(row.getCell(5).getNumericCellValue());
                expressNumber = bigDecimal.toString();
            }
        }

        CsInvoiceExpress csInvoiceExpress = new CsInvoiceExpress() {{
            setPlate(hcux.doc.util.CommonUtil.objectIsNull(row.getCell(0)) ? null : row.getCell(0).toString());
            setCustomerName(hcux.doc.util.CommonUtil.objectIsNull(row.getCell(1)) ? null : row.getCell(1).toString());
            setReceiver(hcux.doc.util.CommonUtil.objectIsNull(row.getCell(2)) ? null : row.getCell(2).toString());
            setExpressCompany(hcux.doc.util.CommonUtil.objectIsNull(row.getCell(6)) ? null : row.getCell(6).toString());
            setRemark(hcux.doc.util.CommonUtil.objectIsNull(row.getCell(7)) ? null : row.getCell(7).toString());
        }};
        csInvoiceExpress.setReceiverMobile(receiverMobile);
        csInvoiceExpress.setInvoiceDate(invoiceDate);
        csInvoiceExpress.setExpressNumber(expressNumber);

        return csInvoiceExpress;
    }
}
