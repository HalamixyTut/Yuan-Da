package hcux.eps.util;

import com.hand.hap.core.BaseConstants;
import hcux.eps.dto.EpsCustomsDetail;
import hcux.spider.SpiderConstant;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.PlainText;
import us.codecraft.webmagic.selector.Selectable;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static hcux.spider.SpiderConstant.*;

public class SpiderUtil {

    private CookieStore cookieStore = new BasicCookieStore();


    public void customs(List<EpsCustomsDetail> list, String contractNumber) throws IOException, ParseException {
        Html html = postHtml(contractNumber, customsUrl);

        //当访问第一页是判断是否有数据
        Selectable record = html.xpath("//td[@align='right']/span[@style='color:Green;']/tidyText()");

        if (!"共0条记录，1/1".equals(record.toString())) {


            //获取数据
            for (int i = 1; i <= 10; i++) {

                Selectable tr = html.xpath("//table[@id='ctl00_content_Gridlist']/tbody/tr[" + (i + 1) + "]/td[2]/a/text()");
                if (tr.get() != null) {
                    EpsCustomsDetail detail = new EpsCustomsDetail();
                    detail.setCustomsNumber(html.xpath("//table[@id='ctl00_content_Gridlist']/tbody/tr[" + (i + 1) + "]/td[2]/a/text()").toString().trim());
                    detail.setBillNumber(html.xpath("//table[@id='ctl00_content_Gridlist']/tbody/tr[" + (i + 1) + "]/td[4]/text()").toString().trim());
                    detail.setImportExport(html.xpath("//table[@id='ctl00_content_Gridlist']/tbody/tr[" + (i + 1) + "]/td[5]/text()").toString().trim());
                    detail.setCountry(html.xpath("//table[@id='ctl00_content_Gridlist']/tbody/tr[" + (i + 1) + "]/td[6]/text()").toString().trim());
                    detail.setContractNumber(html.xpath("//table[@id='ctl00_content_Gridlist']/tbody/tr[" + (i + 1) + "]/td[7]/text()").toString().trim());

                    String declareDateString = html.xpath("//table[@id='ctl00_content_Gridlist']/tbody/tr[" + (i + 1) + "]/td[8]/text()").toString().trim();
                    if (StringUtils.isNotBlank(declareDateString)) {
                        detail.setDeclareDate(DateUtils.parseDate(declareDateString.replace("/", "-"), BaseConstants.DATE_TIME_FORMAT));
                    }

                    list.add(detail);
                }
            }
        }

    }

    public void externalCustoms(List<EpsCustomsDetail> list, String contractNumber) throws IOException, ParseException {

        Html html = postHtml(contractNumber, externalCustomsUrl);
        //当访问第一页是判断是否有数据
        Selectable record = html.xpath("//td[@align='right']/span[@style='color:Green;']/tidyText()");

        if (!"共0条记录，1/1".equals(record.toString())) {


            //获取数据
            for (int i = 1; i <= 10; i++) {

                Selectable tr = html.xpath("//table[@id='ctl00_content_Gridlist']/tbody/tr[" + (i + 1) + "]/td[2]/a/text()");
                if (tr.get() != null) {
                    EpsCustomsDetail detail = new EpsCustomsDetail();
                    detail.setCustomsNumber(html.xpath("//table[@id='ctl00_content_Gridlist']/tbody/tr[" + (i + 1) + "]/td[2]/a/text()").toString().trim());
                    detail.setBillNumber(html.xpath("//table[@id='ctl00_content_Gridlist']/tbody/tr[" + (i + 1) + "]/td[3]/text()").toString().trim());
                    detail.setImportExport(html.xpath("//table[@id='ctl00_content_Gridlist']/tbody/tr[" + (i + 1) + "]/td[4]/text()").toString().trim());
                    detail.setCountry(html.xpath("//table[@id='ctl00_content_Gridlist']/tbody/tr[" + (i + 1) + "]/td[6]/text()").toString().trim());
                    detail.setContractNumber(html.xpath("//table[@id='ctl00_content_Gridlist']/tbody/tr[" + (i + 1) + "]/td[5]/text()").toString().trim());

                    String declareDateString = html.xpath("//table[@id='ctl00_content_Gridlist']/tbody/tr[" + (i + 1) + "]/td[8]/text()").toString().trim();
                    if (StringUtils.isNotBlank(declareDateString)) {
                        detail.setDeclareDate(DateUtils.parseDate(declareDateString.replace("/", "-"), BaseConstants.DATE_TIME_FORMAT));
                    }

                    list.add(detail);

                }
            }
        }
    }

    private Html postHtml(String contractNumber, String url) throws IOException {
        Html loginHtml = login(cookieStore, url);

        Selectable __VIEWSTATE = loginHtml.xpath("//input[@name='__VIEWSTATE']/@value");
        Selectable __EVENTVALIDATION = loginHtml.xpath("//input[@name='__EVENTVALIDATION']/@value");


        CloseableHttpClient client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();

        HttpPost httpPost = new HttpPost(url);

        Map<String, String> map = new HashMap<>();
        map.put("__EVENTARGUMENT", "");
        map.put("__EVENTTARGET", "ctl00$content$pagePagination_NUM_1");
        map.put("__VIEWSTATE", __VIEWSTATE.toString());

        map.put("__EVENTVALIDATION", __EVENTVALIDATION.toString());

        map.put("ctl00$content$cicContrNO_b", contractNumber);
        map.put("ctl00$content$pagePagination_inputTxt", "1");
        //装填参数
        List<NameValuePair> nvps = new ArrayList<>();
        if (map.size() != 0) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        //设置参数到请求对象中
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, SpiderConstant.encoding));

        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.80 Safari/537.36");

        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);
//
        Request request = new Request();
        request.setUrl(url);
        Page page = handleResponse(request, SpiderConstant.encoding, response);
        return page.getHtml();

    }

    private Html login(CookieStore cookieStore, String suffixUrl) throws IOException {
        CloseableHttpClient get = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
        String url = loginUrlPrefix + suffixUrl;
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse getResponse = get.execute(httpGet);
        Request request = new Request();
        request.setUrl(url);
        Page page = handleResponse(request, SpiderConstant.encoding, getResponse);
        return page.getHtml();

    }


    private Page handleResponse(Request request, String charset, HttpResponse httpResponse) throws IOException {
        byte[] bytes = IOUtils.toByteArray(httpResponse.getEntity().getContent());
        Page page = new Page();
        page.setBytes(bytes);
        page.setCharset(charset);
        page.setRawText(new String(bytes, charset));
        page.setUrl(new PlainText(request.getUrl()));
        page.setRequest(request);
        page.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        page.setDownloadSuccess(true);
        return page;
    }
}
