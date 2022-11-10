<!DOCTYPE html>
<html lang="en">
<head>
    <title>发票</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <style type="text/css">
        table {
            /*border-spacing: 0;*/
            border-collapse: collapse;
            table-layout: fixed;
        }

        body {
            font-family: SimSun;
        }

        @page {
            size: 297mm 210mm;
        }

        td {
            font-size: 14px;
            vertical-align: top;
            text-align: center;
        }

        .border_1 {
            border: 1px solid black;
        }

        .border_2 {
            border-right: none;
        }

        .border_3 {
            text-align: left;
            border-left: none;
            border-right: none;
            border-top: none;
        }

        .border_4 {
            border-top: none;
            border-bottom: none;
        }

        .border_5 {
            border-bottom: none;
        }

        .border_6 {
            border-left: none;
        }


        .title {
            text-align: center;
            border-top: 2px solid black;
            border-bottom: 2px solid black;
        }

        .total_page {
            width: 120px;
            font-size: 14px;
            position: absolute;
            z-index: 999;
            right: 30px;
            top: -10px;
            font-weight: bold;
            text-align: center;
            background-color: #c9ccd1;
        }
    </style>

</head>

<body>

<div>
    <#if signFlag == "Y">
        <div style="position: relative;">
            <img alt="2" style="width: 300px;position: absolute;left: 95mm;top: 120mm;z-index: 2;"
                 src="file://${realPath}/resources/img/xinjing.png"/>
        </div>
    </#if>

    <div style="text-align: center;font-size: 30px;font-weight: bold;">
        浙江新景进出口有限公司
    </div>
    <div style="text-align: center;font-size: 20px;font-weight: bold;margin-top: 5px;">
        ZHEJIANG NEW VISION IMPORT AND EXPORT CO LTD.
    </div>

    <div style="position: relative;">
        <div class="total_page">
            页码/页数：${page!}/${totalPage!}
        </div>
    </div>

    <div style="text-align: center;font-size: 24px;font-weight: bold;margin-top: 5px;">
        发&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;票
    </div>
    <div style="text-align: center;font-size: 20px;font-weight: bold;margin-top: 5px;">
        INVOICE
    </div>
    <table style="width:100%;border-style: none ;margin-top: 5px;">
        <tr>
            <td colspan="7"></td>
            <td>INVOICE NO:</td>
            <td class="border_1 border_3" colspan="4">${header.invoiceNumber!""}</td>
        </tr>
        <tr>
            <td colspan="2">CONTRACT NO:</td>
            <td class="border_1 border_3" colspan="5">${header.contractNumber!""}</td>
            <td>DATE:</td>
            <td class="border_3 border_1" colspan="4">${header.invoiceDate?string('yyyy/MM/dd')}</td>
        </tr>
        <tr>
            <td>TO:</td>
            <td class="border_1 border_3" colspan="6">${header.consignee!""}</td>
        </tr>
        <tr>
            <td>ROUTE:</td>
            <td>FROM</td>
            <td class="border_3 border_1" colspan="5">${header.departurePort!""}</td>
            <td>TO</td>
            <td class="border_1 border_3" colspan="4">${header.dischargePort!""}&nbsp;${header.arrivalCountry!""}</td>
        </tr>
        <tr style="height: 5px;"></tr>
        <tr>
            <td class="border_1 border_6 title" colspan="2">唛头<br/>SHIPPING MARK</td>
            <td class="border_1 title" colspan="5">货名<br/>DESCRIPTION OF GOODS</td>
            <td class="border_1 title">件数<br/>PACKAGE</td>
            <td class="border_1 title" colspan="2">数量<br/>QUANTITY</td>
            <td class="border_1 title">单价<br/>PRICE</td>
            <td class="border_1 border_2 title">金额<br/>AMOUNT</td>
        </tr>

        <tr>
            <td class="border_1 border_5  border_6" colspan="2"></td>
            <td class="border_1 border_5" colspan="5"></td>
            <td class="border_1 border_5"></td>
            <td class="border_1 border_5" colspan="2"></td>
            <td class="border_1 border_5 border_2" colspan="2">
                <#if header.transactionMode = 'FOB' || header.transactionMode = 'EXW'>
                    <![CDATA[${header.transactionMode!""}]]> ${header.departurePort!""}
                    <br/>
                    ${header.currencySystem!""}
                <#elseif header.transactionMode = '垫仓' || header.transactionMode = '市场价'>
                 ${header.transactionMode!""}
                    <br/>
                 ${header.currencySystem!""}
                <#else>
                    ${header.transactionMode!""} ${header.dischargePort!""}
                    <br/>
                    ${header.currencySystem!""}
                </#if>

            </td>
        </tr>
        <tr aria-rowspan="12">
            <td class="border_1 border_6 border_4" colspan="2"
                style="word-wrap:break-word; word-break:break-all;">
                <#if page == 1>
                    <#if header.shippingMark??>
                        ${header.shippingMark!""}
                    <#else>
                        <img alt="1" style="height: 100mm;width: 35mm;"
                             src="${header.downloadUrl!}"/>
                    </#if>
                </#if>
            </td>
            <td class="border_1 border_4" colspan="5" style="text-align: left">
                <#list lineList as line>
                    ${line.productNameCn!""}&nbsp;${line.productNameEn!""}<br/>
                </#list>

            </td>
            <td class="border_1 border_4">
                <#list lineList as line>
                    <#if line.packageNumber??>
                        ${line.packageNumber?c}&nbsp;${line.packageNumberUnit!""}<br/>
                    <#else>
                        ${line.packageNumberUnit!""}<br/>
                    </#if>
                </#list>
            </td>
            <td class="border_1 border_4" colspan="2">
                <#list lineList as line>
                    <#if line.customsAmountOne??>
                        ${line.customsAmountOne?c}&nbsp;${line.customsUnitOne!""}<br/>
                    <#else>
                        <br/>
                    </#if>
                </#list>
            </td>
            <td class="border_1 border_4">
                <#list lineList as line>
                    <#if line.unitPrice??>
                        ${line.unitPrice?c}<br/>
                    <#else>
                        <br/>
                    </#if>
                </#list>
            </td>
            <td class="border_1 border_4 border_2">
                <#list lineList as line>
                    <#if line.totalPrice??>
                        ${line.totalPrice?c}<br/>
                    <#else>
                        <br/>
                    </#if>
                </#list>
            </td>
        </tr>

        <#if page == totalPage>
            <tr>
                <td class="border_1 border_5 border_6" colspan="2" style="text-align: right">TOTAL:</td>
                <td class="border_1 border_5" colspan="5">
                    *************
                </td>
                <td class="border_1 border_5">
                    <#if header.packageNumber??>
                        ${header.packageNumber?c}&nbsp;${header.packageNumberUnit!""}
                    </#if>

                </td>
                <td class="border_1 border_5" colspan="2">*******</td>
                <td class="border_1 border_5">*******</td>
                <td class="border_1 border_5 border_2">
                    <#if header.totalPrice??>
                        ${header.totalPrice?c}
                    </#if>
                </td>
            </tr>
        </#if>
    </table>
</div>

</body>
</html>
