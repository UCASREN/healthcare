//显示数据库概述信息部分
var alldatabase_grid;
var database_grid;
var table_grid;
var TableAjax_alldatabase = function () {

    var initPickers = function () {
        //init date pickers
        $('.date-picker').datepicker({
            rtl: Metronic.isRTL(),
            autoclose: true
        });
    }

    var handleRecords = function () {

        var grid = new Datatable();
        alldatabase_grid=grid;
        grid.init({
            src: $("#datatable_ajax_alldatabase"),
            onSuccess: function (grid) {
            	console.log(alldatabase_grid.getDataTable().ajax.json().recordsTotal);
            	$("#datatable_ajax_alldatabase").show();
            	$("#showalldatabaseinfo_number").text("数据库的数目："+alldatabase_grid.getDataTable().ajax.json().recordsTotal);
            	
            },
            onError: function (grid) {
                // execute some code on network or other general error  
            },
            onDataLoad: function(grid) {
                // execute some code on ajax data load
            },
            loadingMessage: 'Loading...',
            dataTable: { // here you can define a typical datatable settings from http://datatables.net/usage/options 

                // Uncomment below line("dom" parameter) to fix the dropdown overflow issue in the datatable cells. The default datatable layout
                // setup uses scrollable div(table-scrollable) with overflow:auto to enable vertical scroll(see: assets/global/scripts/datatable.js). 
                // So when dropdowns used the scrollable div should be removed. 
                //"dom": "<'row'<'col-md-8 col-sm-12'pli><'col-md-4 col-sm-12'<'table-group-actions pull-right'>>r>t<'row'<'col-md-8 col-sm-12'pli><'col-md-4 col-sm-12'>>",
                
                "bStateSave": true, // save datatable state(pagination, sort, etc) in cookie.

                "lengthMenu": [
                    [2,10, 20, 50, 100, 150, -1],
                    [2,10, 20, 50, 100, 150, "所有"] // change per page values here
                ],
                "pageLength": 10, // default record count per page
                "ajax": {
                    "url": "dataresource/getalldatabasecssinfo", // ajax source
                },
                "order": [
                    [1, "asc"]
                ],// set first column as a default sort by asc
                "columnDefs": [ {
                    "targets": [ 1 ],
                    "visible": false,
                    "searchable":false
                },{
                    "targets": [ 3 ],
                    "visible": false
                },{
                    "targets": [ 4 ],
                    "visible": false
                },{
                    "targets": [ 5 ],
                    "visible": false
                },{
                    "targets": [ 6 ],
                    "visible": false
                },{
                    "targets": [ 7 ],
                    "visible": false
                },{
                    "targets": [ 8 ],
                    "visible": false
                },{
                    "targets": [ 10 ],
                    "visible": false
                },{
                    "targets": [ 12 ],
                    "visible": false
                },{
                    "targets": [ 13 ],
                    "visible": false
                },{
                    "targets": [ 14 ],
                    "visible": false
                },{
                    "targets": [ 15 ],
                    "visible": false
                },{
                    "targets": [ 16 ],
                    "visible": false
                },{
                    "targets": [ 17 ],
                    "visible": false
                }]
            }
        });//,4,5,6,7,8,10,12,13,14,15,16,17
        var table = $('#datatable_ajax_alldatabase');
        var oTable = table.dataTable();
    	table.on('click', '.checkmore', function(e) {
    		e.preventDefault();

    		var nRow = $(this).parents('tr')[0];

    		var aData = oTable.fnGetData(nRow);
    			
    		//读取表格隐藏数据，将详细内容显示到固定位置
    		$("#showdetail_db_zhname").text(aData[3]==null?"空":aData[3]);
    		$("#showdetail_description").text(aData[4]==null?"空":aData[4]);
    		$("#showdetail_resinstitution").text("负责单位名称："+(aData[11]==null?"空":aData[11]));
    		$("#showdetail_resaddress").text("负责单位通讯地址："+(aData[13]==null?"空":aData[13]));
    		$("#showdetail_respostalcode").text("负责单位邮政编码："+(aData[14]==null?"空":aData[14]));
    		$("#showdetail_resphone").text("负责单位联系电话："+(aData[15]==null?"空":aData[15]));
    		$("#showdetail_resemail").text("负责单位电子邮件地址："+(aData[16]==null?"空":aData[16]));
    		$("#showdetail_identifier").text("标识符："+(aData[5]==null?"空":aData[5]));
    		$("#showdetail_keywords").text("关键字："+(aData[9]==null?"空":aData[9]));
    		$("#showdetail_language").text("数据集语种："+(aData[6]==null?"空":aData[6]));
    		$("#showalldatabaseinfo").hide();
    		$("#showdetail").show();
    	});
    }

    return {

        //main function to initiate the module
        init: function () {

            initPickers();
            handleRecords();
        }

    };

}();
TableAjax_alldatabase.init();



var TableAjax_database = function () {

    var initPickers = function () {
        //init date pickers
        $('.date-picker').datepicker({
            rtl: Metronic.isRTL(),
            autoclose: true
        });
    }

    var handleRecords = function () {

        var grid = new Datatable();
        database_grid=grid;
        grid.init({
            src: $("#datatable_ajax_database"),
            onSuccess: function (grid) {
                // execute some code after table records loaded
            },
            onError: function (grid) {
                // execute some code on network or other general error  
            },
            onDataLoad: function(grid) {
                // execute some code on ajax data load
            },
            loadingMessage: 'Loading...',
            dataTable: { // here you can define a typical datatable settings from http://datatables.net/usage/options 

                // Uncomment below line("dom" parameter) to fix the dropdown overflow issue in the datatable cells. The default datatable layout
                // setup uses scrollable div(table-scrollable) with overflow:auto to enable vertical scroll(see: assets/global/scripts/datatable.js). 
                // So when dropdowns used the scrollable div should be removed. 
                //"dom": "<'row'<'col-md-8 col-sm-12'pli><'col-md-4 col-sm-12'<'table-group-actions pull-right'>>r>t<'row'<'col-md-8 col-sm-12'pli><'col-md-4 col-sm-12'>>",
                
                "bStateSave": true, // save datatable state(pagination, sort, etc) in cookie.

                "lengthMenu": [
                    [2,10, 20, 50, 100, 150, -1],
                    [2,10, 20, 50, 100, 150, "所有"] // change per page values here
                ],
                "pageLength": 20, // default record count per page
                "ajax": {
                    "url": "dataresource/getdatabasecssinfo", // ajax source
                },
                "order": [
                    [1, "asc"]
                ],// set first column as a default sort by asc
                "columnDefs": [ {
                    "targets": [ 2 ],
                    "visible": false
                }]
            }
        });
//        grid.getDataTable().on( 'draw.dt', function (e, settings, data) {
//        	// Get the column API object
//        	var tempd=grid.getDataTable();
//            var column = grid.getDataTable().column( 1 );
//
//            // Toggle the visibility
//            column.visible( false );
//        } );
        // handle group actionsubmit button click
        /*
        grid.getTableWrapper().on('click', '.table-group-action-submit', function (e) {
        	console.log("hello");
            e.preventDefault();
            var action = $(".table-group-action-input", grid.getTableWrapper());
            if (action.val() != "" && grid.getSelectedRowsCount() > 0) {
                grid.setAjaxParam("customActionType", "group_action");
                grid.setAjaxParam("customActionName", action.val());
                grid.setAjaxParam("id", grid.getSelectedRows());
                grid.getDataTable().ajax.reload();
                grid.clearAjaxParams();
            } else if (action.val() == "") {
                Metronic.alert({
                    type: 'danger',
                    icon: 'warning',
                    message: 'Please select an action',
                    container: grid.getTableWrapper(),
                    place: 'prepend'
                });
            } else if (grid.getSelectedRowsCount() === 0) {
                Metronic.alert({
                    type: 'danger',
                    icon: 'warning',
                    message: 'No record selected',
                    container: grid.getTableWrapper(),
                    place: 'prepend'
                });
            }
        });
        */
        $(".table-group-action-submit").click(function(){
        	console.log("选中数量:"+grid.getSelectedRowsCount());
        	console.log("选中的数据:"+grid.getSelectedRows());
        	console.log("未选中的数据:"+grid.getUnSelectedRows());
        	//更新session中的信息
			$.ajax({
		    	type : "get",//请求方式
		    	url : "updateshoppingcart",//发送请求地址
		    	dataType : "json",
		    	data:{
		    		addinfolist:grid.getSelectedRows()+"",
		    		deleteinfolist:grid.getUnSelectedRows()+""
		    	},
		    	success : function(data) {
		    		//console.log(data);
		    		console.log("当前购物车信息:");
		    		$.each(data,function(key,values){     
		    		    console.log("数据库"+key+"包含:");     
		    		    $(values).each(function(){     
		    		        console.log(this+"");     
		    		    });     
		    		 });
		    		alert("成功添加！");
		    	}
		    });
        });
    }

    return {

        //main function to initiate the module
        init: function () {

            initPickers();
            handleRecords();
        }

    };

}();
TableAjax_database.init();
var TableAjax = function () {

    var initPickers = function () {
        //init date pickers
        $('.date-picker').datepicker({
            rtl: Metronic.isRTL(),
            autoclose: true
        });
    }

    var handleRecords = function () {

        var grid = new Datatable();
        table_grid=grid;
        grid.init({
            src: $("#datatable_ajax"),
            onSuccess: function (grid) {
                // execute some code after table records loaded
            },
            onError: function (grid) {
                // execute some code on network or other general error  
            },
            onDataLoad: function(grid) {
                // execute some code on ajax data load
            },
            loadingMessage: 'Loading...',
            dataTable: { // here you can define a typical datatable settings from http://datatables.net/usage/options 

                // Uncomment below line("dom" parameter) to fix the dropdown overflow issue in the datatable cells. The default datatable layout
                // setup uses scrollable div(table-scrollable) with overflow:auto to enable vertical scroll(see: assets/global/scripts/datatable.js). 
                // So when dropdowns used the scrollable div should be removed. 
                //"dom": "<'row'<'col-md-8 col-sm-12'pli><'col-md-4 col-sm-12'<'table-group-actions pull-right'>>r>t<'row'<'col-md-8 col-sm-12'pli><'col-md-4 col-sm-12'>>",
                
                "bStateSave": true, // save datatable state(pagination, sort, etc) in cookie.

                "lengthMenu": [
                    [2,10, 20, 50, 100, 150, -1],
                    [2,10, 20, 50, 100, 150, "All"] // change per page values here
                ],
                "pageLength": 20, // default record count per page
                "ajax": {
                    "url": "dataresource/gettablecssinfo", // ajax source
                },
                "order": [
                    [1, "asc"]
                ],// set first column as a default sort by asc
                "columnDefs": [ {
                    "targets": [ 0 ],
                    "visible": false
                }, {
                    "targets": [ 2 ],
                    "visible": false
                },{
                    "targets": [ 6 ],
                    "visible": false
                }]
            }
        });
        
        // handle group actionsubmit button click
        /*
        grid.getTableWrapper().on('click', '.table-group-action-submit', function (e) {
            e.preventDefault();
            var action = $(".table-group-action-input", grid.getTableWrapper());
            if (action.val() != "" && grid.getSelectedRowsCount() > 0) {
                grid.setAjaxParam("customActionType", "group_action");
                grid.setAjaxParam("customActionName", action.val());
                grid.setAjaxParam("id", grid.getSelectedRows());
                grid.getDataTable().ajax.reload();
                grid.clearAjaxParams();
            } else if (action.val() == "") {
                Metronic.alert({
                    type: 'danger',
                    icon: 'warning',
                    message: 'Please select an action',
                    container: grid.getTableWrapper(),
                    place: 'prepend'
                });
            } else if (grid.getSelectedRowsCount() === 0) {
                Metronic.alert({
                    type: 'danger',
                    icon: 'warning',
                    message: 'No record selected',
                    container: grid.getTableWrapper(),
                    place: 'prepend'
                });
            }
        });
        */
        
        $.ajax({
        	type : "get",//请求方式
        	url : "dataresource/getalldatabaseinfowithclass",//发送请求地址
        	dataType : "json",
        	data:{
        		operation:"all"
        	},
        	success : function(data) {
        		$(data).each(function(k,classificationinfo){
        			temp_databaseinfo_list=classificationinfo.databaseinfolist;
        			appenddatabaselist="";
	        		$(temp_databaseinfo_list).each(function (i,databaseinfo) {
	        			//下拉列表数据填充
	        			$("#database").append("<option value='"+databaseinfo.databaseid+"'>"+databaseinfo.name+"</option>"); //+"("+databaseinfo.comments+")"
	        			var tablelist="";
	        			$(databaseinfo.tablelist).each(function (j,tableinfo) {
	        				tablelist=tablelist+"<li ><a href='#' id='databasetable_"+databaseinfo.databaseid+"_"+tableinfo.tableid+"'> <i class='fa fa-table'></i>"+tableinfo.name+"</a></li>";
	        				
	        			});
	        			var appenddatabase="<li><a href='#' id='database_"+databaseinfo.databaseid+"'><i class='fa fa-database'></i><span class='title'>"
	        			+databaseinfo.name
	        			+"</span><span class='arrow open'> </span></a><ul class='sub-menu'>"
						+tablelist
						+"</ul></li>"
	//        			$("#alldatabaselist").append(appendlist);
						appenddatabaselist=appenddatabaselist+appenddatabase;
	//        			$("a[id^=databasetable_"+databaseinfo.databaseid+"_]").click(function(){
	//        				$("#showtableinfo").show();
	//                    	$("#showdatabaseinfo").hide();
	//                    	$("#showalldatabaseinfo").hide();
	//        				var thisidlist=$(this).attr("id").split("_");//databasetable_1_1
	//        				
	//        				var databaseid=thisidlist[1];
	//        				var tableid=thisidlist[2];
	//        				table_grid.setAjaxParam("databaseid",databaseid);
	//        				table_grid.setAjaxParam("tableid", tableid);
	//        				table_grid.getDataTable().ajax.reload();
	//        				//更新表概要信息
	//        				$.ajax({
	//        			    	type : "get",//请求方式
	//        			    	url : "dataresource/gettablesummary",//发送请求地址
	//        			    	dataType : "json",
	//        			    	data:{
	//        			    		databaseid:databaseid,
	//        			    		tableid:tableid
	//        			    	},
	//        			    	success : function(data) {
	//        			    		//console.log(data);
	//        			    		$("#showtableinfo_name").text("表名："+data.name);
	//        			    		$("#showtableinfo_comments").text("描述："+data.comments);
	////        			    		$("#showtableinfo_others").text("其它："+data.others);
	//        			    		$("#showtableinfo_fieldnumber").text("包含列的个数："+data.length);
	//        			    		$("#showtableinfo_numrows").text("数据量："+data.numrows);
	//        			    	}
	//        			    });
	//        			});
	        		});
	        		var appenddatabaselist="<li><a href='#' id='classification_"+classificationinfo.classificationid+"'><i class='icon-folder'></i><span class='title'>"
	    			+classificationinfo.name
	    			+"</span><span class='arrow open'> </span></a><ul class='sub-menu'>"
					+appenddatabaselist
					+"</ul></li>";
        		$("#allclassificationlist").append(appenddatabaselist);
        		
        		});
        		//绑定所有事件
    			$("a[id^=databasetable_]").click(function(){
    			$("#showdetail").hide();
				$("#showtableinfo").show();
            	$("#showdatabaseinfo").hide();
            	$("#showalldatabaseinfo").hide();
				var thisidlist=$(this).attr("id").split("_");//databasetable_1_1
				
				var databaseid=thisidlist[1];
				var tableid=thisidlist[2];
				table_grid.setAjaxParam("databaseid",databaseid);
				table_grid.setAjaxParam("tableid", tableid);
				table_grid.getDataTable().ajax.reload();
				//更新表概要信息
				$.ajax({
			    	type : "get",//请求方式
			    	url : "dataresource/gettablesummary",//发送请求地址
			    	dataType : "json",
			    	data:{
			    		databaseid:databaseid,
			    		tableid:tableid
			    	},
			    	success : function(data) {
			    		//console.log(data);
			    		$("#showtableinfo_name").text("Table name："+data.name);
			    		$("#showtableinfo_comments").text("desc："+data.comments);
//			    		$("#showtableinfo_others").text("其它："+data.others);
			    		$("#showtableinfo_fieldnumber").text("Number of Records："+data.length);
			    		$("#showtableinfo_numrows").text("Records："+data.numrows);
			    	}
			    });
			});
    		//绑定事件结束
        	}
        });
        
        $("#database").change(function(){
        	if($("#database").val()!=""){
        		$("#table").empty();
        		$("#table").append("<option value=''>Please select one table...</option>"); 
        		$.ajax({
        			type : "get",//请求方式
        			url : "dataresource/getdatabaseinfo",//发送请求地址
        			dataType : "json",
        			data:{
            			databaseid:$("#database").val()
    				},
        			success : function(data) {
        				$(data).each(function (i,tableinfo) {
        					$("#table").append("<option value='"+tableinfo.tableid+"'>"+tableinfo.name+"</option>"); //+"("+tableinfo.comments+")"
        		        });
        			}
        		});
        		database_grid.setAjaxParam("databaseid", $("#database").select2("val"));
        		database_grid.getDataTable().ajax.reload();
        		//更新数据库概要信息
        		$.ajax({
                	type : "get",//请求方式
                	url : "dataresource/getdatabasesummary",//发送请求地址
                	dataType : "json",
                	data:{
                		databaseid:$("#database").select2("val")
                	},
                	success : function(data) {
                		$("#showdatabaseinfo_name").text("Database Name："+data.name);
                		$("#showdatabaseinfo_comments").text("Desc："+data.comments);
//                		$("#showdatabaseinfo_identifier").text("标识符："+data.identifier==null?"空":data.identifier);
//                		$("#showdatabaseinfo_language").text("语种："+data.language==null?"空":data.identifier);
//                		$("#showdatabaseinfo_charset").text("字符集："+data.charset==null?"空":data.identifier);
//                		$("#showdatabaseinfo_subjectclassification").text("学科分类："+data.subjectclassification==null?"空":data.identifier);
//                		$("#showdatabaseinfo_keywords").text("关键词："+data.keywords==null?"空":data.identifier);
//                		$("#showdatabaseinfo_credibility").text("可信度："+data.credibility==null?"空":data.identifier);
//                		$("#showdatabaseinfo_resinstitution").text("负责单位："+data.resinstitution==null?"空":data.identifier);
//                		$("#showdatabaseinfo_resname").text("负责人："+data.resname==null?"空":data.identifier);
//                		$("#showdatabaseinfo_resaddress").text("通讯地址："+data.resaddress==null?"空":data.identifier);
//                		$("#showdatabaseinfo_respostalcode").text("邮政编码："+data.respostalcode==null?"空":data.identifier);
//                		$("#showdatabaseinfo_resphone").text("联系电话："+data.resphone==null?"空":data.identifier);
//                		$("#showdatabaseinfo_resemail").text("电子邮件："+data.resemail==null?"空":data.identifier);
//                		$("#showdatabaseinfo_resourceurl").text("资源链接："+data.resourceurl==null?"空":data.identifier);
                		$("#showdatabaseinfo_tablenumber").text("Number of Tables："+data.length);
                		$("#showtableinfo").hide();
                    	$("#showdatabaseinfo").show();
                    	$("#showalldatabaseinfo").hide();
                	}
                });
        	}
        });
        $("#table").change(function(){
        	if($("#table").val()!=""&&$("#database").val()!=""){
        		//console.log("tableid:"+$("#table").select2("val"));
        		grid.setAjaxParam("databaseid", $("#database").select2("val"));
                grid.setAjaxParam("tableid", $("#table").select2("val"));
                grid.getDataTable().ajax.reload();
              //更新表概要信息
    			$.ajax({
                	type : "get",//请求方式
                	url : "dataresource/gettablesummary",//发送请求地址
                	dataType : "json",
                	data:{
                		databaseid:$("#database").select2("val"),
                		tableid:$("#table").select2("val")
                	},
                	success : function(data) {
                		console.log(data);
                		$("#showtableinfo_name").text("Table Name："+data.name);
                		$("#showtableinfo_comments").text("Desc："+data.comments);
                		$("#showtableinfo_others").text("Other："+data.others);
                		$("#showtableinfo_fieldnumber").text("Number of Columns："+data.length);
                		$("#showtableinfo").show();
                    	$("#showdatabaseinfo").hide();
                    	$("#showalldatabaseinfo").hide();
                	}
                });
        	}
        });
    }

    return {

        //main function to initiate the module
        init: function () {

            initPickers();
            handleRecords();
        }

    };

}();
TableAjax.init();
$("#refreshalldatabaseinfo").click(function(){
	$("#showtableinfo").hide();
	$("#showdatabaseinfo").hide();
	$("#showalldatabaseinfo").show();
});
$("#logoutbutton").click(function(){
	$("#logoutform").submit();
});
$("#showshoppingcart").click(function(){
	$("#selectedtableinfo").empty();
	$.ajax({
    	type : "get",//请求方式
    	url : "getcssshoppingcartdetail",//发送请求地址
    	dataType : "json",
    	success : function(data) {
    		console.log("当前购物车信息:");
    		var shoppingcart=$("<ul></ul>");
    		$.each(data,function(key,values){     
    		    console.log(key+"包含:");
    		    var tempdatabase=$("<li><label class='checkbox-inline' style='padding-left: 0px;'><i class='fa fa-database'></i>"+key.substring(key.indexOf("_")+1)+"<a href='#' id='cart_database_"+key.substring(0,key.indexOf("_"))+"' class='del-goods'>&nbsp;</a></label></li>");
    		    var tablelist=$("<ul></ul>");
    		    $(values).each(function(){     
    		        console.log(this+"");
    		        tablelist.append($("<li><label class='checkbox-inline' style='padding-left: 0px;'><i class='fa fa-table'></i>"+this.substring(this.indexOf("_",this.indexOf("_")+1)+1)+"<a href='#' id='cart_table_"+this.substring(0,this.indexOf("_",this.indexOf("_")+1))+"' class='del-goods'>&nbsp;</a></label></li>"));
    		    });
    		    tempdatabase.append(tablelist);
    		    shoppingcart.append(tempdatabase);
    		 });
    		
    		$("#selectedtableinfo").append(shoppingcart);
    		$("a[id^=cart_database_]").click(function(){
    			console.log("id:"+$(this).attr("id").substring($(this).attr("id")));
    			console.log($(this).attr("id").substring($(this).attr("id").indexOf("cart_database_")+14));
		    	$.ajax({
			    	type : "get",//请求方式
			    	url : "deletedatabaseshoppingcart",//发送请求地址
			    	dataType : "json",
			    	data:{
			    		database:$(this).attr("id").substring($(this).attr("id").indexOf("cart_database_")+14)
			    	},
			    	success : function(data) {
			    		location.reload(true);
			    	}
			    });
			});
    		$("a[id^=cart_table_]").click(function(){
    			var temp=$(this).attr("id").substring($(this).attr("id").indexOf("cart_table_")+11).split("_");
    			console.log("temp:"+temp);
    			$.ajax({
			    	type : "get",//请求方式
			    	url : "deletedatabasetableshoppingcart",//发送请求地址
			    	dataType : "json",
			    	data:{
			    		database:temp[0],
			    		table:temp[1]
			    	},
			    	success : function(data) {
			    		location.reload(true);
			    	}
			    });
			});
    	}
    });
	$("#showshoppingcart_model").modal('show');
});
$("#deleteallshoppingcart").click(function(){
	$.ajax({ 
		type : "get",//请求方式 
		url : "deleteallshoppingcart",//发送请求地址
		success :function(data) {
			location.reload(true);
		} 
	});
});
$("#applaydata").click(function(){
	window.open('/healthcare/applydata/applydata?historyFlag=1')
});
$("#back").click(function(){
	$("#showdetail").hide();
	$("#showalldatabaseinfo").show();
});