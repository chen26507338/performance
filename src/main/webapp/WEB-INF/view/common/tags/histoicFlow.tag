<fieldset>
    <legend>流转信息</legend>
    <div id="histoicFlowList">
        正在加载流转信息...
    </div>
</fieldset>
<script type="text/javascript">
    $.get("${ctxPath}/act/task/histoicFlow?procInsId=${procInsId!}&startAct=${startAct!}&endAct=${endAct!}&t="+new Date().getTime(), function(data){
        $("#histoicFlowList").html(data);
    });
</script>


