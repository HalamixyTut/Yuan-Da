<!DOCTYPE html>
<html lang="en">
<head>
    <title>中华人民共和国海关出口货物报关单</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <style type="text/css">
        body {
            font-family: SimSun;
        }

        table {
            border-collapse: collapse;
            table-layout: fixed;
        }

        td {
            vertical-align: center;
            padding-top: 2px;
            padding-bottom: 2px;
            padding-left: 2px;
            font-size: 10px;
            line-height: 14px;
        }

        @page {
            size: 210mm 298mm;
            /*size: 11in 8.5in;*/
        }

        .border_1 {
            border: 1px solid black;
        }

        .title {
            font-weight: bold;
        }

        .total_page {
            width: 120px;
            font-size: 14px;
            position: absolute;
            z-index: 999;
            right: 30px;
            top: -30px;
            font-weight: bold;
            text-align: center;
            background-color: #c9ccd1;
        }
    </style>

</head>

<body>

<div>
    <div style="position: relative;">
        <#if signFlag =="Y">
            <img style="width: 300px;position: absolute;z-index: 999;left: 50mm;top: 210mm;" alt=""
                 src="${realPath}/resources/img/customs_declaration.png"/>
        </#if>

    </div>

    <div style="text-align: center;font-size: 20px;font-weight: bold;margin-bottom: 4px;">
        中华人民共和国海关出口货物报关单
    </div>

    <div style="position: relative;">
        <div class="total_page">
            ${header.contractNumber!"&nbsp;"}<br/>
            页码/页数：${page!}/${totalPage!}
        </div>
    </div>

    <table style="width:100%;border-style: none ;font-size: 14px">
        <tr>
            <td class="title" colspan="5">预录入编号:</td>
            <td class="title" colspan="8">海关编号:</td>
            <td class="title" colspan="5"></td>
        </tr>

        <tr>
            <td class="border_1">
                <div class="title">项号</div>
            </td>
            <td class="border_1" colspan="2">
                <div class="title">商品编号</div>
            </td>
            <td class="border_1" colspan="3">
                <div class="title">商品名称</div>
            </td>
            <td colspan="2" class="border_1">
                <div class="title">数量及单位</div>
            </td>

            <td class="border_1">
                <div class="title">单价</div>
            </td>
            <td class="border_1" colspan="2">
                <div class="title">总价</div>
            </td>
            <td class="border_1">
                <div class="title">币制</div>
            </td>
            <td class="border_1">
                <div class="title">原产国</div>
            </td>
            <td class="border_1" colspan="2">
                <div class="title">最终目的国</div>
            </td>
            <td class="border_1" colspan="2">
                <div class="title">境内货源地</div>
            </td>
            <td class="border_1">
                <div class="title">征免</div>
            </td>
        </tr>
        <#list lineList as line>
            <tr>
                <td class="border_1">${line_index + 1 + 14 * (page -2) + 7}</td>
                <td class="border_1" colspan="2">
                    ${line.goodsNumber!"&nbsp;"}
                </td>
                <td class="border_1" colspan="3">
                    ${line.productNameCn!"&nbsp;"}
                </td>
                <td colspan="2" class="border_1">
                    <#if line.customsAmountOne??>
                        ${line.customsAmountOne?c}${line.customsUnitOne!"&nbsp;"}<br/>
                    <#else>
                        <br/>
                    </#if>

                    <#if line.netWeight??>
                        ${line.netWeight?c}KGS<br/>
                    <#else>
                        <br/>
                    </#if>

                    <#if line.customsAmountThree??>
                        ${line.customsAmountThree?c}${line.customsUnitThree!"&nbsp;"}<br/>
                    <#else>
                        <br/>
                    </#if>
                </td>

                <td class="border_1">
                    <#if line.unitPrice??>
                        ${line.unitPrice?c}<br/>
                    <#else>
                        <br/>
                    </#if>
                </td>
                <td class="border_1" colspan="2">
                    <#if line.totalPrice??>
                        ${line.totalPrice?c}<br/>
                    <#else>
                        <br/>
                    </#if>
                </td>
                <td class="border_1">
                    ${header.currencySystem!"&nbsp;"}
                </td>
                <td class="border_1">
                    ${line.originCountry!"&nbsp;"}
                </td>
                <td class="border_1" colspan="2">
                    ${line.destinationCountry!"&nbsp;"}
                </td>
                <td class="border_1" colspan="2">
                    ${line.originPlace!"&nbsp;"}
                </td>
                <td class="border_1">
                    ${line.exemptionWay!"&nbsp;"}
                </td>
            </tr>

            <tr>
                <td class="border_1" colspan="3">
                    <div class="title">规格型号（申报要素）</div>
                </td>
                <td class="border_1" colspan="15">
                    ${line.declarationElement!"&nbsp;"}
                </td>
            </tr>
        </#list>

        <#if page == totalPage>
            <tr>
                <td class="border_1" colspan="18" style="text-align: center;">
                    <span class="title">TOTAL：</span>
                    <#if header.totalPrice??>
                        ${header.totalPrice?c}&nbsp;${header.currencySystem!}
                    </#if>
                </td>
            </tr>
        </#if>

        <tr>
            <td colspan="18" style="border-top:1px solid black;"></td>
        </tr>
    </table>
</div>

</body>
</html>
