package hcux.lm.util;

import com.hand.hap.attachment.dto.SysFile;
import hcux.lm.dto.LmTransport;
import hcux.lm.dto.LmTransportImg;
import hcux.lm.dto.ZhTransport;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sap.db.util.FileUtils.getFileName;


public class CommonUtil {
    private static final Logger logger = LoggerFactory.getLogger(hcux.doc.util.CommonUtil.class);

    /**
     * ZhTransport转换为LmTransport
     *
     * @param dto
     * @return
     */
    public static LmTransport zhToLmTransport(ZhTransport dto) {
        LmTransport lmTransport = new LmTransport();

        lmTransport.setZhTransportId(dto.getTransportId());
        lmTransport.setTransportCode(dto.getTransportNo());
        lmTransport.setTransportStatus(dto.getStatus());
        lmTransport.setDriverName(dto.getTruckerName());
        lmTransport.setDriverMobile(dto.getTruckerPhone());
        lmTransport.setDriverId(dto.getTruckerIdNumber());
        lmTransport.setCarNumber(dto.getVehicleNo());
        lmTransport.setTrailerCarNumber(dto.getTrailerVehicleNumber());
        lmTransport.setRemark(dto.getRemark());
        lmTransport.setGoodsName(dto.getGoodsName());
        lmTransport.setDispatchAmount(dto.getWeight());
        lmTransport.setUnit(dto.getUnit());
        lmTransport.setDrawGoodsAmount(dto.getWeightSend());
        lmTransport.setDrawGoodsDate(CommonUtil.str2Date(dto.getSendTime(), "yyyy-MM-dd HH:mm:ss"));
        lmTransport.setSignAmount(dto.getWeightGet());
        lmTransport.setSettlementWay(dto.getSettlementType());
        lmTransport.setTransportPrice(dto.getUnitPrice());
        lmTransport.setSignDate(CommonUtil.str2Date(dto.getGetTime(), "yyyy-MM-dd HH:mm:ss"));
        lmTransport.setChangeFlag(dto.getChangeFlag());
        lmTransport.setVehicleSource(dto.getVehicleSource());

        return lmTransport;
    }

    /**
     * str转换为date类型
     *
     * @param date
     * @param formater
     * @return
     */
    public static Date str2Date(String date, String formater) {
        if (date == null || ("").equals(date)) {
            return null;
        }
        if (formater == null) {
            formater = "yyyy-MM-dd";
        }
        Date date1 = null;
        SimpleDateFormat sdf = new SimpleDateFormat(formater);
        try {
            date1 = sdf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date1;
    }

    /**
     * 根据运输单回执链接，下载图片到本地
     *
     * @param path
     * @param transportImg
     * @param sysFile
     * @return
     */
    public static void downloadFile(String path, LmTransportImg transportImg, SysFile sysFile) throws Exception {
        int downloadTimeout = 300;

        File file = new File(path);
        String fileName = getImgName(transportImg.getImgUrl());

        CloseableHttpClient httpClient = HttpClients.custom().disableContentCompression().build();

        HttpGet httpget = new HttpGet(transportImg.getImgUrl());
        httpget.setConfig(RequestConfig.custom()
                .setConnectionRequestTimeout(downloadTimeout)
                .setConnectTimeout(downloadTimeout)
                .setSocketTimeout(downloadTimeout)
                .build());

        try (CloseableHttpResponse response = httpClient.execute(httpget)) {
            HttpEntity entity = response.getEntity();

            try (InputStream in = entity.getContent();
                 ByteArrayOutputStream output = new ByteArrayOutputStream();
                 FileOutputStream localFile = new FileOutputStream(file)) {
                byte[] buffer = new byte[4096];
                int r = 0;
                while ((r = in.read(buffer)) != -1) {
                    output.write(buffer, 0, r);
                }
                output.writeTo(localFile);
                output.flush();

                sysFile.setFilePath(path);
                sysFile.setFileSize(file.length());
                sysFile.setFileName(fileName);
                sysFile.setFileType(entity.getContentType().getValue());
                sysFile.setUploadDate(new Date());
            } catch (IOException e) {
                logger.error("写文件出错", e);
            }
        }
    }

    /**
     * 根据url获取图片名称和图片类型
     *
     * @param url
     * @return
     */
    public static String getImgName(String url) {
        Pattern p = Pattern.compile(".+\\/(.*?)\\?");// ".+\/":贪婪的匹配 "/"
        Matcher m = p.matcher(url);
        if (m.find()){
            return m.group(1);
        }

        return "transport.jpg";
    }
}
