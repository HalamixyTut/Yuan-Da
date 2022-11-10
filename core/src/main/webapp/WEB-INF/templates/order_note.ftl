<!DOCTYPE html>
<html lang="en">
<head>
    <title>报关委托书</title>
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
            size: 210mm 297mm;
        }

        .div_1 {
            font-size: 12px;
        }

        td {
            font-size: 12px;
            padding-top: 2px;
            padding-bottom: 2px;
        }

        .border_1 {
            border: 1px solid black;
        }

        .td_1 {
            text-align: center;
        }

        .td_center {
            width: 10px;
        }

        .td_2 {
            padding-left: 6px;
        }

        .td_3 {
            vertical-align: top;
        }


    </style>

</head>

<body>

<div>
    <h3 style="text-align: center;">代 理 报 关 委 托 书</h3>
    <br/>
    <p style="text-indent: 2em">
        我单位现 &nbsp;&nbsp;&nbsp; (A逐票、B长期)委托贵公司代理&nbsp;&nbsp;&nbsp;等通关事宜。
        (A、填单申报 B、辅助查验 C、垫缴税款 D、办理海关证明联
        E、审批手册F、核销手册 G、申办减免税手续 H、其他)详见《委托报关协议》。
    </p>

    <p style="text-indent: 2em">
        我单位保证遵守《海关法》和国家有关法规，保证所提供的情况真实、完整、单货相符，无侵犯他人知识产权的行为。否则，愿承担相关法律责任。
    </p>

    <p style="text-indent: 2em">
        本委托书有效期自签字之日起至&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日止。
    </p>
    <p style="text-align: center;margin-left: 200px;">
        委托方（盖章）：
    </p>
    <br/>
    <p style="text-align: center;margin-left: 100px;">
        法定代表人或其授权签署《代理报关委托书》的人（签字）
    </p>
    <p style="text-align: right;">
        年 &nbsp;&nbsp;&nbsp; 月 &nbsp;&nbsp;&nbsp; 日
    </p>

    <h3 style="text-align: center;">委 托 报 关 协 议</h3>

    <div class="div_1">
        为明确委托报关具体事项和各自责任，双方经平等协商签定协议如下：
    </div>
    <table style="width:100%;border-style: none ;margin-top: 5px;">
        <tr>
            <td class="border_1 td_1">委托方</td>
            <td class="border_1 td_1" colspan="2">浙江新景进出口有限公司</td>
            <td class="td_center"></td>
            <td class="border_1 td_1">被委托方</td>
            <td class="border_1 td_2" colspan="2"></td>
        </tr>

        <tr>
            <td class="border_1 td_1">主要货物名称</td>
            <td class="border_1 td_1" colspan="2">${line_01.productNameCn!""}</td>
            <td class="td_center"></td>
            <td class="border_1 td_1">*报关单编码</td>
            <td class="border_1 td_2" colspan="2"> No.</td>
        </tr>

        <tr>
            <td class="border_1 td_1">HS 编码</td>
            <td class="border_1 td_1" colspan="2">${line_01.goodsNumber!""}</td>
            <td class="td_center"></td>
            <td class="border_1 td_1">收到单证日期</td>
            <td class="border_1 td_1" colspan="2">
                年&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;日
            </td>
        </tr>

        <tr>
            <td class="border_1 td_1">货物总价</td>
            <td class="border_1 td_1" colspan="2">${header.totalPrice!""}</td>
            <td class="td_center"></td>
            <td class="border_1 td_1" rowspan="4">收到单证情况</td>
            <td class="border_1 td_1">合同□</td>
            <td class="border_1 td_1">发票□</td>
        </tr>

        <tr>
            <td class="border_1 td_1">进出口日期</td>
            <td class="border_1 td_1" colspan="2">
                年&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;日
            </td>
            <td class="td_center"></td>
            <td class="border_1 td_1">装箱清单□</td>
            <td class="border_1 td_1">提（运）单□</td>
        </tr>

        <tr>
            <td class="border_1 td_1">提单号</td>
            <td class="border_1 td_1" colspan="2"></td>
            <td class="td_center"></td>
            <td class="border_1 td_1">加工贸易手册□</td>
            <td class="border_1 td_1">许可证件□</td>
        </tr>

        <tr>
            <td class="border_1 td_1">贸易方式</td>
            <td class="border_1 td_1" colspan="2">${header.supervisionMode!""}</td>
            <td class="td_center"></td>
            <td class="border_1 td_2" colspan="2">其他</td>
        </tr>

        <tr>
            <td class="border_1 td_1">原产地/货源地</td>
            <td class="border_1 td_1" colspan="2">${line_01.originCountry!""}/${line_01.originPlace!""}</td>
            <td class="td_center"></td>
            <td class="border_1 td_1">报关收费</td>
            <td class="border_1 td_2" colspan="2">人民币： &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 元</td>
        </tr>

        <tr>
            <td class="border_1 td_2 td_3" colspan="3" style="height: 120px;">
                其他要求：
            </td>
            <td class="td_center"></td>
            <td class="border_1 td_2 td_3" colspan="3">
                承诺说明：
            </td>
        </tr>
        <tr>
            <td class="border_1 td_2 td_3" colspan="3">
                背面所列通用条款是本协议不可分割的一部分，对本协议的签署构成了对背面通用条款的同意。
                <br/>
                <br/>
            </td>
            <td class="td_center"></td>
            <td class="border_1 td_2 td_3" colspan="3">
                背面所列通用条款是本协议不可分割的一部分，对本协议的签署构成了对背面通用条款的同意。
                <br/>
                <br/>
            </td>
        </tr>

        <tr>
            <td class="border_1 td_2 td_3" colspan="3">
                委托方业务签章：
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                经办人签章：
                <br/>
                联系电话：
                <span style="float: right;">
                    年&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;日
                </span>
                <br/>
                <br/>
                <br/>
            </td>
            <td class="td_center"></td>
            <td class="border_1 td_2 td_3" colspan="3">
                被委托方业务签章：
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                经办报关员签章：
                <br/>
                联系电话：
                <span style="float: right;">
                    年&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;日
                </span>
                <br/>
                <br/>
                <br/>
            </td>
        </tr>
    </table>
    <div class="div_1" style="margin-top: 2px;text-align: center;">
        <span>(白联:海关留存、黄联:被委托方留存、红联:委托方留存)</span>
        <span style="float: right;">中国报关协会监制</span>
    </div>
</div>

</body>
</html>
