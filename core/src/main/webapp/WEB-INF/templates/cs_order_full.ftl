<!DOCTYPE html>
<html lang="en">
<head>
    <title>确认收货单</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <style type="text/css">
        body {
            font-family: SimSun;
        }

        @page {
            size: 210mm 297mm;
            margin: 20mm 5mm 40mm 5mm;
        }
        @page{
            @top-center{
                content : element(header);
            }
            @bottom-center{
                content : element(footer)
            }
        }

        #header {
            position: running(header);
            text-align: right;
        }

        table {
            /*border-spacing: 0;*/
            border-collapse: collapse;
            table-layout: fixed;
        }

        td {
            font-size: 14px;
            vertical-align: top;
            text-align: center;
        }
    </style>

</head>

<body>

<div>
    <div id="header" style="margin-top: 0px;">
        <span>编号:${orderFull.invoiceNo!""}</span>
    </div>
    <h1 style="text-align: center;">收 货 证 明</h1>
    <br />
    <br />
    <p style="text-indent: 2em"><b>远大能源化工有限公司：</b></p>
    <p style="text-indent: 4em">我司收到贵司与我司签订的合同（合同编号：${orderFull.actualContractNo!""}）项下的货物，详情如下：</p>
    <p style="text-indent: 4em">货物名称：${orderFull.itemDesc!""}</p>
    <p style="text-indent: 4em">数量：${orderFull.outboundQty!""}${orderFull.unitOfMeasure!""}</p>
    <p style="text-indent: 4em">经我司验收，数量、品质、规格均符合合同约定，特此证明。</p>
    <br />
    <br />
    <br />
    <p style="text-align: right;margin-right: 2em"><b>${orderFull.partyName!""}</b></p>
    <p style="text-align: right;margin-right: 2em"><b>${.now?string("yyyy年MM月dd日")}</b></p>
</div>

</body>
</html>
