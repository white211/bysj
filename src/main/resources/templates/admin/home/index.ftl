<#include "/admin/layout/layout.ftl">
<#import "/admin/layout/macro.ftl" as macro>
<#assign css>
<style>
</style>
</#assign>
<#assign js>
<script>
    function del(id) {
        layer.confirm('确定删除吗?', {icon: 3, title: '提示'}, function (index) {
            $.ajax({
                type: "POST",
                dataType: "json",
                url: "${ctx!}/admin/home/delete/" + id,
                success: function (res) {
                    layer.msg(res.message, {time: 2000}, function () {
                        location.reload();
                    });
                }
            });
        });
    }

    function use(id, type) {
       $.ajax({
           type: "POST",
           dataType: "json",
           url: "${ctx!}/admin/home/use/"+id+"/"+type,
           success: function (res) {
               layer.msg(res.msg, {time: 2000}, function () {
                   location.reload();
               });
           }
       });
    }
</script>
</#assign>
<@layout title="首页管理" active="main">
<!-- Content Header (Page header) -->
<section class="content-header">
    <h1>
        首页列表
        <small>一切从这里开始</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-database"></i> 内容</a></li>
        <li><a href="#"><i class="fa fa-list-ul"></i> 首页管理</a></li>
        <li class="active"><i class="fa fa-table"></i> 首页列表</li>
    </ol>
</section>

<!-- Main content -->
<section class="content">
    <!-- Default box -->
    <div class="box box-primary">
        <div class="box-header">
            <@shiro.hasPermission name="system:home:add">
                <a class="btn btn-sm btn-success" href="${ctx!}/admin/home/add">新增</a>
            </@shiro.hasPermission>
        </div>
        <div class="box-body">
            <table class="table table-striped">
                <tr>
                    <th>序号</th>
                    <th>使用状态</th>
                    <th>创建者</th>
                    <th>创建时间</th>
                    <th>操作</th>
                </tr>
                <#list pageInfo.content as homeInfo>
                    <tr>
                        <#--<td>${homeInfo.cn_home_id}</td>-->
                            <td>${(pageInfo.number)*10+(homeInfo_index+1)}</td>
                        <td>
                            <#if homeInfo.cnHomeType == 0>
                                <span class="label label-info">正在使用</span>
                            <#elseif homeInfo.cnHomeType == 1>
                                <span class="label label-danger">未使用</span>
                            <#else >
                                <span class="label label-warning">未知</span>
                            </#if>
                        </td>
                        <td>${homeInfo.cnHomeUserName}</td>
                        <td>${homeInfo.cnHomeCreateTime?date}</td>
                        <td>


                            <@shiro.hasPermission name="system:home:edit">
                                <a class="btn btn-sm btn-primary" href="${ctx!}/admin/home/edit/${homeInfo.cnHomeId}">编辑</a>
                            </@shiro.hasPermission>

                            <#if homeInfo.cnHomeType == 0>
                                <@shiro.hasPermission name="system:home:use">
                                    <button class="btn btn-sm btn-warning" onclick="use(${homeInfo.cnHomeId},1)">取消
                                    </button>
                                </@shiro.hasPermission>
                            <#elseif homeInfo.cnHomeType == 1>
                                <@shiro.hasPermission name="system:home:use">
                                    <button class="btn btn-sm btn-warning" onclick="use(${homeInfo.cnHomeId},0)">使用
                                    </button>
                                </@shiro.hasPermission>
                            </#if>

                            <@shiro.hasPermission name="system:home:deleteBatch">
                                <button class="btn btn-sm btn-danger" onclick="del(${homeInfo.cnHomeId})">删除</button>
                            </@shiro.hasPermission>
                        </td>
                    </tr>
                </#list>
            </table>
        </div>
        <!-- /.box-body -->
        <div class="box-footer clearfix">
            <@macro.page pageInfo=pageInfo url="${ctx!}/admin/home/index?" />
        </div>
    </div>
    <!-- /.box -->

</section>
<!-- /.content -->
</@layout>