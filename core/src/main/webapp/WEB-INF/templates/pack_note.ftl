<!DOCTYPE html>
<html lang="en">
<head>
    <title>装箱单</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <style type="text/css">
        table {
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
        装&nbsp;&nbsp;箱&nbsp;&nbsp;单
    </div>
    <div style="text-align: center;font-size: 20px;font-weight: bold;margin-top: 5px;">
        PACKING LIST
    </div>
    <table style="width:100%;border-style: none ;margin-top: 5px;">
        <tr>
            <td style="width: 50px;"></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td style="width: 130px;"></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td colspan="6"></td>
            <td class="border_3">INVOICE NO：</td>
            <td class="border_1 border_3" colspan="4">${header.invoiceNumber!""}</td>
        </tr>
        <tr>
            <td class="border_3" colspan="2">CONTRACT NO：</td>
            <td class="border_1 border_3" colspan="4">${header.contractNumber!""}</td>
            <td class="border_3">DATE：</td>
            <td class="border_3 border_1" colspan="4">${header.invoiceDate?string('yyyy/MM/dd')}</td>
        </tr>
        <tr>
            <td class="border_3">TO：</td>
            <td class="border_1 border_3" colspan="5">${header.consignee!""}</td>
        </tr>
        <tr>
            <td class="border_3">ROUTE:</td>
            <td class="border_3" style="font-weight: bold;">FROM</td>
            <td class="border_3 border_1" colspan="4">${header.departurePort!""}</td>
            <td class="border_3" style="font-weight: bold;">TO</td>
            <td class="border_1 border_3" colspan="4">${header.dischargePort!""}&nbsp;${header.arrivalCountry!""}</td>
        </tr>
        <tr style="height: 5px;"></tr>
        <tr>
            <td class="border_1 title border_6" colspan="2">唛头<br/>SHIPPING MARK</td>
            <td class="border_1 title" colspan="4">货名<br/>DESCRIPTION OF GOODS</td>
            <td class="border_1 title">件数<br/>PACKAGE</td>
            <td class="border_1 title">数量<br/>QUANTITY</td>
            <td class="border_1 title">毛重<br/>G.W.(KGS)</td>
            <td class="border_1 title">净重<br/>N.W.(KGS)</td>
            <td class="border_1 title border_2">体积<br/>MEAS(CBM)</td>
        </tr>
        <tr aria-rowspan="11" class="border_1">
            <td class="border_1 border_4 border_6" colspan="2" style="word-wrap:break-word; word-break:break-all;">
                <#if page == 1>
                    <#if header.shippingMark??>
                        ${header.shippingMark!""}
                    <#else>
                        <img alt="" style="height: 100mm;width: 35mm;"
                             src="${header.downloadUrl!}"/>
                    </#if>
                </#if>
            </td>
            <td class="border_1 border_4" colspan="4" style="text-align: left;">
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
            <td class="border_1 border_4">
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
                    <#if line.grossWeight??>
                        ${line.grossWeight?c}<br/>
                    <#else>
                        <br/>
                    </#if>
                </#list>
            </td>
            <td class="border_1 border_4">
                <#list lineList as line>
                    <#if line.netWeight??>
                        ${line.netWeight?c}<br/>
                    <#else>
                        <br/>
                    </#if>
                </#list>
            </td>
            <td class="border_1 border_4 border_2">
                <#list lineList as line>
                    <#if line.volume??>
                        ${line.volume?c}<br/>
                    <#else>
                        <br/>
                    </#if>
                </#list>
            </td>
        </tr>

        <#if page == totalPage>
            <tr>
                <td class="border_1 border_4 border_6" colspan="2" style="text-align: right">TOTAL：</td>
                <td class="border_1 border_4" colspan="4">
                    *************
                </td>
                <td class="border_1 border_4">
                    <#if header.packageNumber??>
                        ${header.packageNumber?c}&nbsp;${header.packageNumberUnit!""}
                    </#if>
                </td>
                <td class="border_1 border_4">*******</td>
                <td class="border_1 border_4">
                    <#if header.grossWeight??>
                        ${header.grossWeight?c}
                    </#if>
                </td>
                <td class="border_1 border_4">
                    <#if header.netWeight??>
                        ${header.netWeight?c}
                    </#if>
                </td>
                <td class="border_1 border_4 border_2">
                    <#if header.volume??>
                        ${header.volume?c}
                    </#if>
                </td>
            </tr>
        </#if>
    </table>
</div>

</body>
</html>
