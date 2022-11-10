<!DOCTYPE html>
<#setting locale="en_US">
<html lang="en">
<head>
    <title>SALES CONTRACT</title>
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
            size: 200mm 280mm;
        }

        td {
            font-size: 14px;
            line-height: 18px;
        }

        .border_1 {
            border: 1px solid black;
        }

        .border_3 {
            border-left: none;
            border-right: none;
            border-top: none;
        }

        .border_4 {
            border-bottom: none;
        }

        .border_5 {
            border-top: none;
            border-bottom: none;
        }

        .title {
            text-align: center;
        }
    </style>

</head>
<body>

<div>


    <div style="text-align: center;font-size: 26px;font-weight: bold;margin-top: 5px;">
        ZHEJIANG NEW VISION IMPORT AND EXPORT CO LTD.
    </div>
    <div style="text-align: center;font-size: 24px;font-weight: bold;margin-top: 5px;">

        <span style="border-bottom: 1px solid black;">SALES CONTRACT</span>

    </div>

    <table style="width:100%;border-style: none ;margin-top: 5px;">
        <tr>
            <td>CONT NO:</td>
            <td class="border_1 border_3" colspan="2">${header.contractNumber!""}</td>
            <td></td>
            <td colspan="2" rowspan="3">
                ADDRESS:13/F., CAIHONG BUILDING NINGBO,CHINA 315040
                <br/>
                TEL: 0574-87724969 FAX:0574-87723661
            </td>
        </tr>
        <tr>
            <td>DATE:</td>
            <td class="border_1 border_3" colspan="2">${header.invoiceDateBefore?string('MMM.d,YYYY')}</td>
        </tr>
        <tr>
            <td>SIGNED AT:</td>
            <td class="border_3 border_4" colspan="2">NINGBO CHINA</td>
        </tr>
        <tr>
            <td>THE BUYER:</td>
            <td class="border_1 border_3" colspan="5">${header.consignee!""}</td>
        </tr>
        <tr>
            <td colspan="6">
                THIS CONTRACT IS MADE BY AND BETWEEN THE BUYERS AND SELLERS ACCORDING TO THE TERMS AND CONDITIONS
                STIPULATE BELOW:
            </td>
        </tr>

        <tr>
            <td class="border_1 title" colspan="3">1.COMMODITY AND SPECIFICATION</td>
            <td class="border_1 title">2.QUANTITY</td>
            <td class="border_1 title">3.PRICE</td>
            <td class="border_1 title">4.AMOUNT</td>
        </tr>
        <tr>
            <td class="border_1 border_5" colspan="3"></td>
            <td class="border_1 border_5"></td>
            <td class="border_1 border_5" colspan="2" style="text-align: center">
                <#if header.transactionMode = 'FOB'>
                    ${header.transactionMode!""} ${header.departurePort!""}(${header.currencySystem!""})
                <#else>
                    ${header.transactionMode!""} ${header.dischargePort!""}(${header.currencySystem!""})
                </#if>

            </td>
        </tr>
        <tr aria-rowspan="6">
            <td class="border_1 border_5" colspan="3">
                <#list lineList as line>
                ${line.productNameCn!""}&nbsp;${line.productNameEn!""}<br/>
                </#list>
            </td>
            <td class="border_1 border_5">
                <#list lineList as line>
                    <#if line.customsAmountOne??>
                        ${line.customsAmountOne?c}&nbsp;${line.customsUnitOne!""}<br/>
                    <#else>
                        <br/>
                    </#if>
                </#list>

            </td>
            <td class="border_1 border_5">
                <#list lineList as line>
                    <#if line.unitPrice??>
                        ${line.unitPrice?c}<br/>
                    <#else>
                        <br/>
                    </#if>
                </#list>
            </td>
            <td class="border_1 border_5">
                <#list lineList as line>
                    <#if line.totalPrice??>
                        ${line.totalPrice?c}<br/>
                    <#else>
                        <br/>
                    </#if>
                </#list>
            </td>
        </tr>
        <tr>
            <td class="border_1" colspan="5" style="text-align: right">
                TOTAL AMOUNT:
            </td>
            <td class="border_1">

                <#if header.totalPrice??>
                    ${header.totalPrice?c}
                </#if>
            </td>
        </tr>

        <tr>
            <td colspan="6">
                5.MORE OR LESS WITH
                <span style="border-bottom: 1px solid black;">&nbsp;&nbsp;&nbsp;5&nbsp;&nbsp;&nbsp;</span>
                % MORE OR LESS BOTH IN AMOUNT &amp; QUANTITY ALLOWED AT THE SELLER'S OPTION.
            </td>
        </tr>

        <tr>
            <td>6.PACKING:</td>
            <td class="border_1 border_3" colspan="2">AS PER THE SELLER'S</td>
        </tr>

        <tr>
            <td colspan="4">7.TIME OF SHIPMENT:NOT LATE THAN</td>
            <td class="border_1 border_3">${header.invoiceDateAfter?string('MMM.d,YYYY')}</td>
        </tr>

        <tr>
            <td colspan="4">8.LOADING PORT &amp; DESTINATION:</td>
            <td class="border_1 border_3">${header.dischargePort!""}</td>
        </tr>

        <tr>
            <td colspan="6">
                <#if (header.transactionMode == "CIF" || header.transactionMode == "C&amp;I")>
                    9.INSURANCE: ( ) TO BE EFFECTED BY THE BUYERS.
                    <br/>
                    (&nbsp;X) TO BE EFFECTED BY THE SELLERS FOR 110% OF INVOICE VALUE
                    AGAINST F.P.A.W. ALL RISKS AND WAR RISK AS PER C.I.C DATED 1/1/1981.
                <#else>
                    9.INSURANCE: ( X) TO BE EFFECTED BY THE BUYERS.
                    <br/>
                    (&nbsp;&nbsp;) TO BE EFFECTED BY THE SELLERS FOR 110% OF INVOICE VALUE
                    AGAINST F.P.A.W. ALL RISKS AND WAR RISK AS PER C.I.C DATED 1/1/1981.
                </#if>
            </td>
        </tr>

        <tr>
            <td colspan="3">10.TERMS OF PAYMENT:</td>
            <td class="border_1 border_3">T/T</td>
        </tr>

        <tr>
            <td colspan="6">11. SHIPPING MARK:
                <span style="border-bottom: 1px solid black;">AT SELLERS' OPTION</span>
            </td>
        </tr>

        <tr>
            <td colspan="6">12. SPECIAL CLAUSE:</td>
        </tr>
        <tr style="height: 5px;"></tr>
        <tr>
            <td></td>
            <td>
                THE SELLERS
                <#if signFlag =="Y">
                    <div style="position: relative;">
                        <img alt="2" style="width: 300px;position: absolute;left: -100px;z-index: 2;top: -60px;"
                             src="file://${realPath}/resources/img/xinjing.png"/>
                    </div>
                </#if>
            </td>
            <td></td>
            <td></td>
            <td>
                THE BUYERS
                <#if signFlag == "Y" && header.randomImg == 1>
                    <div style="position: relative;">
                        <img alt="2" style="width: 180px;position: absolute;left: -60px;z-index: 1;top: -20px;"
                             src="file://${realPath}/resources/img/contract_sign_1.png"/>
                    </div>
                </#if>
                <#if signFlag = "Y" && header.randomImg == 0>
                    <div style="position: relative;">
                        <img alt="2" style="width: 140px;position: absolute;left: -20px;z-index: 1;top: -80px;"
                             src="file://${realPath}/resources/img/contract_sign_2.png"/>
                    </div>
                </#if>
            </td>
            <td></td>
        </tr>
        <tr style="height: 80px;">
            <td></td>
            <td style="border-bottom: 1px solid black;">
            </td>
            <td></td>
            <td></td>
            <td style="border-bottom: 1px solid black;">
            </td>
            <td></td>
        </tr>

        <tr>
            <td colspan="3" style="text-align: center;">MANAGER (OR REPRESENTATIVE)</td>
            <td colspan="3" style="text-align: center;">MANAGER (OR REPRESENTATIVE)</td>
        </tr>
    </table>
</div>

</body>
</html>
