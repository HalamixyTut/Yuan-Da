<!DOCTYPE html>
<html lang="en">
<head>
    <title>出口商品配船单</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <style type="text/css">
        table {
            border-collapse: collapse;
        }

        body {
            font-family: SimSun;
        }

        .td_1 {
            width: 12%;
        }

        .td_2 {
            width: 68%;
        }

        .td_3 {
            width: 20%;
        }

        .border_1 {
            border: 1px solid black;
        }

        td {
            vertical-align: top;
            padding-top: 5px;
            padding-bottom: 5px;
            padding-left: 2px;
        }
    </style>

</head>

<body>

<div>


    <h1 style="text-align: center;">浙 江 新 景 进 出 口 有 限 公 司</h1>
    <h2 style="text-align: center;">出 口 商 品 配 船 单</h2>

    <table style="width:100%;border-style: none ;">

        <tr>
            <td class="td_1" style="">
                <span style="font-weight:bold;font-size: 24px">TO:</span>
            </td>
            <td class="td_2" style="display:table-cell; vertical-align:middle">
                ${booking.cargoAgentUnit!""} ${booking.cargoAgent!""} ${booking.cargoAgentMobile!""} ${booking.cargoAgentEmail!""}
            </td>
            <td class="td_3" style="text-align: center;">
                ${booking.creationDate?string('yyyy年MM月dd日')}
            </td>
        </tr>

        <tr>
            <td class="td_1 border_1" style="">
                托运单位
                <br/>
                SHIPPER
            </td>
            <td class="td_2 border_1" style="">
                ${booking.shipper!""}
                <br/>
                ${booking.shipperAddress!""}
                <br/>
                ${booking.shipperMobile!""}
            </td>
            <td class="td_3 border_1" style="">
                起运港
                <br/>
                ${booking.shipmentPort!""}
            </td>
        </tr>

        <tr>
            <td class="td_1 border_1" style="">
                收货单位
                <br/>
                CONSIGNEE
            </td>
            <td class="td_2 border_1" style="">
                ${booking.consignee!""}
                <br/>
                ${booking.consigneeAddress!""}
                <br/>
                ${booking.consigneeMobile!""}

            </td>
            <td class="td_3 border_1" style="">
                卸货港
                <br/>
                ${booking.dischargePort!""}

            </td>
        </tr>

        <tr>
            <td class="td_1 border_1" style="">
                通知单位
                <br/>
                NOTIFY
                <br/>
                PARTY
            </td>
            <td class="td_2 border_1" style="">
                ${booking.notificationUnit!""}
                <br/>
                ${booking.notificationAddress!""}
                <br/>
                ${booking.notificationMobile!""}

            </td>
            <td class="td_3 border_1" style="">
                目的港
                <br/>
                ${booking.destinationPort!""}

            </td>
        </tr>

        <tr>
            <td class="td_1 border_1" style="">
                运输方式
            </td>
            <td class="td_2 border_1" style="">
                ${booking.transportWay!""}

            </td>
            <td rowspan="2" class="td_3 border_1" style="">
                运费条款
                <br/>
                ${booking.freightClause!""}

            </td>
        </tr>

        <tr>
            <td class="td_1 border_1" style="">
                提单份数
            </td>
            <td class="td_2 border_1" style="">
                ${booking.billCopies!""}

            </td>

        </tr>

        <tr>
            <td class="td_1 border_1" style="">
                货好日
            </td>
            <td class="td_2 border_1" style="padding: 0;">

                <table style="width:100%;border-style: none;">
                    <tr>
                        <td style="width: 20%;border-right: 1px solid black;">
                            <#if booking.goodsDate??>
                                ${booking.goodsDate?string('yyyy.MM.dd')}
                            </#if>
                        </td>
                        <td style="width: 20%;border-right: 1px solid black;">
                            箱型
                        </td>
                        <td style="width: 20%;border-right: 1px solid black;">
                            ${booking.boxType!""}

                        </td>
                        <td style="width: 20%;border-right: 1px solid black;">
                            进仓或装柜
                        </td>
                        <td style="width: 20%;">
                            ${booking.cabinet!""}

                        </td>
                    </tr>
                </table>
            </td>
            <td rowspan="2" class="td_3 border_1" style="">
                发票号
                <br/>
                ${booking.invoiceNumber!""}

            </td>
        </tr>

        <tr>
            <td class="td_1 border_1" style="">
                装柜地址及联系方式
            </td>
            <td class="td_2 border_1" style="">
                ${booking.cabinetAddress!""}
            </td>
        </tr>

        <tr>
            <td class="td_1 border_1" style="">
                其他备注信息
            </td>
            <td class="td_2 border_1" style="">
                ${booking.remark!""}
            </td>

            <td class="td_3 border_1" style="">
                船期
                <br/>
                <#if booking.sailingDate??>
                    ${booking.sailingDate?string('yyyy.MM.dd')}
                </#if>
            </td>
        </tr>

        <tr>
            <td rowspan="3" class="td_1 border_1" style="">
                品名
            </td>
            <td rowspan="3" class="td_2 border_1" style="">
                ${booking.productName!""}

            </td>
            <td class="td_3 border_1" style="">
                件数
                <br/>
                <#if booking.packageNumber??>
                    ${booking.packageNumber?c}${booking.packageNumberUnit!""}
                <#else>
                    <br/>
                </#if>
            </td>
        </tr>

        <tr>
            <td class="td_3 border_1" style="">
                毛重
                <br/>
                <#if booking.grossWeight??>
                    ${booking.grossWeight?c}KGS
                <#else>
                    <br/>
                </#if>
            </td>
        </tr>

        <tr>
            <td class="td_3 border_1" style="">
                体积
                <br/>
                <#if booking.volume??>
                    ${booking.volume?c}CBM
                <#else>
                    <br/>
                </#if>

            </td>
        </tr>

        <tr>
            <td class="td_1 border_1" style="">
                唛头
            </td>
            <td colspan="2" class="td_2 border_1" style="">
                <#if booking.shippingMark??>
                    <div style="overflow:hidden;height: 150px;">
                        ${booking.shippingMark!""}
                    </div>
                <#else>
                    <img alt="" width="500px" height="200px"
                         src="${booking.downloadUrl!}"/>
                </#if>
            </td>

        </tr>


        <tr>
            <td class="td_1 border_1" style="">
                联系人
            </td>
            <td colspan="2" class="td_2 border_1" style="padding: 0;">
                <table style="width:100%;border-style: none;">
                    <tr>
                        <td style="width: 10%;border-right: 1px solid black;">
                            ${booking.createdByName!""}
                        </td>
                        <td style="width: 10%;border-right: 1px solid black;">
                            TEL
                        </td>
                        <td style="width: 20%;border-right: 1px solid black;">
                            ${booking.contactMobile!""}

                        </td>
                        <td style="width: 15%;border-right: 1px solid black;">
                            E-MAIL
                        </td>
                        <td style="width: 45%;">
                            ${booking.contactEmail!""}
                        </td>
                    </tr>
                </table>

            </td>
        </tr>

        <tr>
            <td class="td_1 border_1" style="">
                联络地址
            </td>
            <td colspan="2" class="td_2 border_1" style="">
                宁波市惊驾路555号泰富广场A座1412室（邮编315040）
            </td>
        </tr>
        <tr>
            <td colspan="3" style="position: relative;">
                <img alt="2" style="width: 300px;position: absolute;right: 2px;top: -200px;z-index: 2;"
                     src="file://${realPath}/resources/img/xinjing.png"/>
            </td>
        </tr>
    </table>

</div>

</body>
</html>
