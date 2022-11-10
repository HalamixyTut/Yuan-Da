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
            size: 210mm 297mm;
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

    <#--    <h3 style="text-align: center;">中华人民共和国海关出口货物报关单</h3>-->
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
            <td class="border_1 title" colspan="2">
                境内发货人
                <br/>
                No
            </td>
            <td class="border_1" colspan="6">
                AEOCN3302210027,商检3801600511,社会信用913302067960082341
                <br/>
                3302210027 浙江新景进出口有限公司
            </td>
            <td class="border_1" colspan="2">
                <div class="title">出境关别</div>
                &nbsp;
            </td>
            <td class="border_1" colspan="2">
                <div class="title">出口日期</div>
                &nbsp;
            </td>
            <td class="border_1" colspan="2">
                <div class="title">申报日期</div>
                &nbsp;
            </td>
            <td class="border_1" colspan="4">
                <div class="title">备案号</div>
                ${header.recordNumber!"&nbsp;"}
            </td>
        </tr>

        <tr>
            <td class="border_1 title" colspan="2">
                境外收货人
            </td>
            <td class="border_1" colspan="6">
                ${header.consignee!"&nbsp;"}
            </td>

            <td class="border_1" colspan="2">
                <div class="title">运输方式</div>
                ${header.transportWay!"&nbsp;"}
            </td>
            <td class="border_1" colspan="2">
                <div class="title">运输工具名称及航次号</div>
                ${header.vesselVoyage!"&nbsp;"}
            </td>
            <td class="border_1 " colspan="6">
                <div class="title">提运单号</div>
                ${header.deliveryNumber!"&nbsp;"}
            </td>
        </tr>

        <tr>

            <td class="border_1 title" colspan="2">
                生产销售单位
                <br/>
                No
            </td>
            <td class="border_1" colspan="6">
                ${header.productionUnit!"&nbsp;"}
                <br/>
                ${header.productionUnitCode!"&nbsp;"}
            </td>
            <td class="border_1" colspan="2">
                <div class="title">监管方式</div>
                ${header.supervisionMode!"&nbsp;"}
            </td>
            <td class="border_1" colspan="2">
                <div class="title">征免性质</div>
                ${header.exemptionNature!"&nbsp;"}
            </td>
            <td class="border_1 " colspan="6">
                <div class="title">许可证号</div>
                ${header.licenseKey!"&nbsp;"}
            </td>
        </tr>

        <tr aria-rowspan="2">
            <td class=" border_1" colspan="4">
                <div class="title">合同协议号</div>
                ${header.contractNumber!"&nbsp;"}
            </td>
            <td class=" border_1" colspan="4">
                <div class="title">贸易国（地区）</div>
                ${header.tradingCountry!"&nbsp;"}
            </td>
            <td class=" border_1" colspan="4">
                <div class="title">运抵国（地区）</div>
                ${header.arrivalCountry!"&nbsp;"}
            </td>
            <td class="border_1" colspan="2">
                <div class="title">指运港</div>
                ${header.dischargePort!"&nbsp;"}
            </td>
            <td class=" border_1" colspan="4">
                <div class="title">离境口岸</div>
                ${header.departurePort!"&nbsp;"}
            </td>
        </tr>
        <tr aria-rowspan="2">
            <td class="border_1" colspan="2">
                <div class="title">包装种类</div>
                ${header.packageType!"&nbsp;"}
            </td>
            <td class="border_1" colspan="1">
                <div class="title">件数</div>
                <#if header.packageNumber??>
                    ${header.packageNumber?c}
                <#else>
                    <br/>
                </#if>
            </td>
            <td class="border_1" colspan="3">
                <div class="title">其他包装种类</div>
                ${header.otherPackageType!"&nbsp;"}
            </td>
            <td class="border_1" colspan="2">
                <div class="title">毛重（千克）</div>
                <#if header.grossWeight??>
                    ${header.grossWeight?c}
                <#else>
                    <br/>
                </#if>
            </td>
            <td class="border_1" colspan="2">
                <div class="title">净重（千克）</div>
                <#if header.netWeight??>
                    ${header.netWeight?c}
                <#else>
                    <br/>
                </#if>
            </td>
            <td class="border_1" colspan="2">
                <div class="title">成交方式</div>
                ${header.transactionMode!"&nbsp;"}
            </td>
            <td class="border_1" colspan="2">
                <div class="title">运费</div>
                <#if header.freight??>
                    ${header.freight?c}
                <#else>
                    <br/>
                </#if>
            </td>
            <td class="border_1" colspan="2">
                <div class="title">保费</div>
                <#if header.premium??>
                    ${header.premium?c}
                <#else>
                    <br/>
                </#if>
            </td>
            <td class="border_1" colspan="2">
                <div class="title">杂费</div>
                <#if header.incidental??>
                    ${header.incidental?c}
                <#else>
                    <br/>
                </#if>
            </td>
        </tr>
        <tr>
            <td class="border_1" colspan="18">
                <div class="title">随附单证及编号</div>
                ${header.attachedDocument!"&nbsp;"}
            </td>
        </tr>
        <tr aria-rowspan="2">
            <td class="border_1" colspan="18">
                <div class="title">标记唛码及备注</div>
                ${header.remark!"&nbsp;"}
            </td>
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
                <td class="border_1">${line_index + 1}</td>
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
                        ${line.netWeight?c}千克<br/>
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
        <#--            <tr>-->
        <#--                <td colspan="2" class="border_1">-->
        <#--                    -->
        <#--                </td>-->

        <#--            </tr>-->
        <#--            <#if line.rowspan == 3>-->
        <#--                <tr>-->
        <#--                    <td colspan="2" class="border_1">-->
        <#--                        <#if line.customsAmountThree??>-->
        <#--                            ${line.customsAmountThree?c}${line.customsUnitThree!"&nbsp;"}<br/>-->
        <#--                        <#else>-->
        <#--                            <br/>-->
        <#--                        </#if>-->
        <#--                    </td>-->
        <#--                </tr>-->
        <#--            </#if>-->
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
            <td class="border_1" colspan="4" style="border-right: none">
                <span class="title">特殊关系确认：</span>
                ${header.specialRelation!"&nbsp;"}
            </td>
            <td class="border_1" colspan="4" style="border-right: none;border-left: none;">
                <span class="title">价格影响确认：</span>
                ${header.priceImpact!"&nbsp;"}
            </td>
            <td class="border_1" colspan="4" style="border-right: none;border-left: none;">
                <span class="title">支付特许权使用费确认：</span>
                ${header.paymentRoyalties!"&nbsp;"}
            </td>
            <td class="border_1" colspan="6" style="border-left: none;">
                <span class="title">自报自缴：</span>
            </td>
        </tr>
        <tr>
            <td class="border_1" colspan="4" style="border-right: none">
                <span class="title">申报人员证号</span>
            </td>
            <td class="border_1" colspan="4" style="border-right: none;border-left: none">
                <span class="title">电话</span>
            </td>
            <td class="border_1" colspan="5" style="border-left: none">
                <span class="title">兹申明对以上内容承担如实申报、依法纳税之法律责任</span>
            </td>
            <td class="border_1" colspan="5" style="border-bottom: none">
                <span class="title">海关批注及签章</span>
            </td>
        </tr>
        <tr aria-rowspan="3" style="height: 100px">
            <td class="border_1" colspan="9">
                <div class="title">申报单位</div>
            </td>
            <td class="border_1" colspan="4" style="position: relative;">
                <#if signFlag =="Y">
                    <img style="width: 300px;position: absolute;z-index: 999;left: -50px;top: -60px;" alt=""
                         src="${realPath}/resources/img/customs_declaration.png"/>
                </#if>
                <span class="title">
                    申报单位（签章）
                </span>

            </td>
            <td class="border_1" colspan="5" style="border-top:none;"></td>
        </tr>
        <tr>
            <td colspan="18" style="border-top:1px solid black;"></td>
        </tr>
    </table>
</div>

</body>
</html>
