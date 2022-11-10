package hcux.doc.dto;


public class DocCustomsPrint {

    private Long customsId; //

    private String order; //报关委托书

    private String customsDeclaration; //报关单

    private String invoice; //报发

    private String pack; //报装

    private String contract; //合同

    private String sign; //是否签章

    public Long getCustomsId() {
        return customsId;
    }

    public void setCustomsId(Long customsId) {
        this.customsId = customsId;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getCustomsDeclaration() {
        return customsDeclaration;
    }

    public void setCustomsDeclaration(String customsDeclaration) {
        this.customsDeclaration = customsDeclaration;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
