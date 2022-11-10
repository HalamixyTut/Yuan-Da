package hcux.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.*;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import hcux.core.exception.HcuxException;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author feng.liu01@hand-china.com
 * @version 1.0
 * @description PDF方法类
 */
public class PdfUtil {

    private PdfUtil() {
    }

    private static final String FONTS_PATH = "/resources/font/simsun.ttf";

    private static Configuration cfg = null;

    static {
        cfg = new Configuration(Configuration.VERSION_2_3_21);
        //freemarker的模板目录
        try {
            String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            path = path.replace("classes", "");
            path = path + "/templates";
            cfg.setDirectoryForTemplateLoading(new File(path));
            cfg.setDefaultEncoding("UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void parseHtmlToPdfByItext(HttpServletRequest request,
                                             HttpServletResponse response,
                                             String content) throws IOException, DocumentException {
        Document document = new Document(PageSize.A4);
//        document.setPageSize(PageSize.A4.rotate());

        ServletOutputStream outputStream = response.getOutputStream();

        PdfWriter writer = PdfWriter.getInstance(document, outputStream);

        document.open();

        XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);

        String realPath = request.getSession().getServletContext().getRealPath("/");
        fontImp.register(realPath + FONTS_PATH);

        try {

            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(content.getBytes()), Charset.forName("UTF-8"), fontImp);

//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        InputStream is = new FileInputStream(outputFileName);
//
//        int buf;
//        while ((buf = is.read()) != -1) {
//            baos.write(buf);
//        }
//        baos.flush();
//        is.close();
//        File f = new File(outputFileName);
//        f.deleteOnExit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();

        }
    }

    public static void parseHtmlToPdfByFlying(Map<String, Object> map,
                                              OutputStream os,
                                              String content) throws IOException, DocumentException {
        ITextRenderer render = new ITextRenderer();
        //添加中文字体
        ITextFontResolver fontResolver = render.getFontResolver();
        fontResolver.addFont(map.get("realPath") + FONTS_PATH, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        //设置转换的HTML内容
        render.setDocumentFromString(content);
        render.layout();
        render.createPDF(os);
    }


    /**
     * freemarker渲染html
     *
     * @param data    数据
     * @param htmlTmp 模板名称
     * @return
     */
    public static String freeMarkerRender(Map<String, Object> data, String htmlTmp) throws HcuxException {
        Writer out = new StringWriter();
        try {
            // 获取模板,并设置编码方式
            Template template = cfg.getTemplate(htmlTmp + ".ftl");
            template.setEncoding("UTF-8");
            // 合并数据模型与模板
            template.process(data, out); //将合并后的数据和模板写入到流中，这里使用的字符流
            out.flush();
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new HcuxException("转换freemarker模板出错");
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * PDF复制合成
     *
     * @param document
     * @param copy
     * @param os
     * @throws IOException
     * @throws BadPdfFormatException
     */
    public static void copyPdf(Document document, PdfCopy copy, ByteArrayOutputStream os) throws IOException, BadPdfFormatException {
        PdfReader reader = new PdfReader(new ByteArrayInputStream(os.toByteArray()));
        int n = reader.getNumberOfPages();
        for (int j = 1; j <= n; j++) {
            document.newPage();
            PdfImportedPage page = copy.getImportedPage(reader, j);
            copy.addPage(page);
        }
    }



    /**
     * 生成PDF
     *
     * @param template 模板名
     * @param map      数据
     * @param document
     * @param copy
     * @throws IOException
     * @throws DocumentException
     */
    public static void generatePdf(String template, Map<String, Object> map, Document document, PdfCopy copy) throws IOException, DocumentException, HcuxException {
        //freemarker渲染
        String content = PdfUtil.freeMarkerRender(map, template);
        //转成PDF
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PdfUtil.parseHtmlToPdfByFlying(map, os, content);
        //多个PDF合成一个
        PdfUtil.copyPdf(document, copy, os);
    }
}
