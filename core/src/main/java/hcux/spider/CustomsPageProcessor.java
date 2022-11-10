package hcux.spider;

import hcux.eps.dto.EpsCustomsDetail;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.utils.HttpConstant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static hcux.spider.SpiderConstant.*;

/**
 * 报关页面爬虫
 */
public class CustomsPageProcessor implements PageProcessor {
    private Site site = Site.me()
            .setCharset(encoding).setDomain("nbeport.com")
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.80 Safari/537.36");


    private List<EpsCustomsDetail> epsCustomsDetailList;
    private String contractNumber;//合同号

    @Override
    public void process(Page page) {
        Html html = page.getHtml();

        // 获取校验参数
        Selectable viewState = html.xpath("//input[@name='__VIEWSTATE']/@value");
        Selectable eventValidation = html.xpath("//input[@name='__EVENTVALIDATION']/@value");

        //判断是否初始的单点登录URL
        if (!page.getUrl().toString().contains("login.nbeport.com")) {

            //当访问第一页是判断是否有数据
            Selectable record = html.xpath("//td[@align='right']/span[@style='color:Green;']/tidyText()");

            if (!"共0条记录，1/1".equals(record.toString())) {

                // 获取页面，如果页数大于1，则添加下一页的请求
                if (page.getUrl().toString().contains("page=1")) {
                    Selectable pageNum = html.xpath("//a[starts-with(@id,'ctl00_content_pagePagination_NUM')]/@go_index");

                    List<String> pageList = pageNum.all();
                    for (String s : pageList) {
                        if (!"1".equals(s)) {
                            page.addTargetRequest(getRequest(s, viewState.toString(), eventValidation.toString()));
                        }
                    }
                }

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
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            detail.setDeclareDate(formatter.parse(declareDateString));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        epsCustomsDetailList.add(detail);

                    }
                }
            }

        } else {
            // 第一页链接
            page.addTargetRequest(getRequest("1", viewState.toString(), eventValidation.toString()));
        }

    }

    @Override
    public Site getSite() {
        return site;
    }

    public void start() {
        Request request = new Request(loginUrlPrefix + customsUrl);
        Spider spider = Spider.create(this);
        spider.addRequest(request);
        spider.setDownloader(new CustomsHttpClientDownloader());
        spider.thread(1).run();
    }

//    public static void main(String[] args) {
//        List<EpsCustomsDetail> customsPageList = new ArrayList<>();
//        CustomsPageProcessor processor = new CustomsPageProcessor();
//        processor.setEpsCustomsDetailList(customsPageList);
//        processor.setContractNumber("1");
//        processor.start();
//    }

    private Request getRequest(String page, String viewState, String eventValidation) {
        Request request = new Request(customsUrl + "?page=" + page);
        request.setMethod(HttpConstant.Method.POST);
        Map<String, Object> params = new HashMap<>();
        params.put("__EVENTARGUMENT", "");
        params.put("__EVENTTARGET", "ctl00$content$pagePagination_NUM_" + page);
        params.put("__VIEWSTATE", viewState);

        params.put("__EVENTVALIDATION", eventValidation);

        params.put("ctl00$content$cicContrNO_b", contractNumber);
        params.put("ctl00$content$pagePagination_inputTxt", "1");

        HttpRequestBody body = HttpRequestBody.form(params, encoding);
        request.setRequestBody(body);
        return request;
    }

    public List<EpsCustomsDetail> getEpsCustomsDetailList() {
        return epsCustomsDetailList;
    }

    public void setEpsCustomsDetailList(List<EpsCustomsDetail> epsCustomsDetailList) {
        this.epsCustomsDetailList = epsCustomsDetailList;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }
}
