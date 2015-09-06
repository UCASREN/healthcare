//显示数据库概述信息部分
var database_grid;
var table_grid;
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
                    [2,10, 20, 50, 100, 150, "All"] // change per page values here
                ],
                "pageLength": 10, // default record count per page
                "ajax": {
                    "url": "dataresource/getdatabasecssinfo", // ajax source
                },
                "order": [
                    [1, "asc"]
                ]// set first column as a default sort by asc
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
                "pageLength": 10, // default record count per page
                "ajax": {
                    "url": "dataresource/gettablecssinfo", // ajax source
                },
                "order": [
                    [1, "asc"]
                ]// set first column as a default sort by asc
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
        	url : "dataresource/getalldatabaseinfo",//发送请求地址
        	dataType : "json",
        	data:{
        		operation:"all"
        	},
        	success : function(data) {
        		$(data).each(function (i,databaseinfo) {
        			$("#database").append("<option value='"+databaseinfo.databaseid+"'>"+databaseinfo.name+"("+databaseinfo.comments+")"+"</option>"); 
        			var tablelist="";
        			$(databaseinfo.tablelist).each(function (j,tableinfo) {
        				tablelist=tablelist+"<li class='active'><a href='#' id='databasetable_"+databaseinfo.databaseid+"_"+tableinfo.tableid+"'> <i class='fa fa-table'></i>"+tableinfo.name+"</a></li>";
        				
        			});
        			var appendlist="<li><a href='#' id='database_"+databaseinfo.databaseid+"'><i class='fa fa-database'></i><span class='title'>"
        			+databaseinfo.name
        			+"</span><span class='arrow open'> </span></a><ul class='sub-menu'>"
					+tablelist
					+"</ul></li>"
        			$("#alldatabaselist").append(appendlist);
        			$("a[id^=databasetable_"+databaseinfo.databaseid+"_]").click(function(){
        				$("#showtableinfo").show();
                    	$("#showdatabaseinfo").hide();
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
        			    		$("#showtableinfo_name").text("表名："+data.name);
        			    		$("#showtableinfo_comments").text("备注："+data.comments);
        			    		$("#showtableinfo_others").text("其它："+data.others);
        			    		$("#showtableinfo_fieldnumber").text("包含列的个数："+data.length);
        			    	}
        			    });
        			});
        		});
        	}
        });
        
        $("#database").change(function(){
        	if($("#database").val()!=""){
        		$("#table").empty();
        		$("#table").append("<option value=''>请选择数据库下的表...</option>"); 
        		$.ajax({
        			type : "get",//请求方式
        			url : "dataresource/getdatabaseinfo",//发送请求地址
        			dataType : "json",
        			data:{
            			databaseid:$("#database").val()
    				},
        			success : function(data) {
        				$(data).each(function (i,tableinfo) {
        					$("#table").append("<option value='"+tableinfo.tableid+"'>"+tableinfo.name+"("+tableinfo.comments+")"+"</option>"); 
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
                		$("#showdatabaseinfo_name").text("数据库名："+data.name);
                		$("#showdatabaseinfo_comments").text("备注："+data.comments);
                		$("#showdatabaseinfo_others").text("其它："+data.others);
                		$("#showdatabaseinfo_tablenumber").text("包含表的个数："+data.length);
                		$("#showtableinfo").hide();
                    	$("#showdatabaseinfo").show();
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
                		$("#showtableinfo_name").text("表名："+data.name);
                		$("#showtableinfo_comments").text("备注："+data.comments);
                		$("#showtableinfo_others").text("其它："+data.others);
                		$("#showtableinfo_fieldnumber").text("包含列的个数："+data.length);
                		$("#showtableinfo").show();
                    	$("#showdatabaseinfo").hide();
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



