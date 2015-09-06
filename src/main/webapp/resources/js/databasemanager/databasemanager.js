/*
 * This file is responsible for database information show
 *  @author xingkong
 * */
var reloadCount=0;//控制恢复数据的次数，一次页面只有一次
function getCookie(objName){//获取指定名称的cookie的值 
	var arrStr = document.cookie.split(";");
	var returnvalue;
	for(var i = 0;i < arrStr.length;i ++){ 
		var temp = arrStr[i].split("="); 
		if($.trim(temp[0]) == $.trim(objName)) {
			returnvalue=unescape(temp[1]);
			return returnvalue; 
		}
	} 
} 
var ajaxTable1 = function() {

	function restoreRow(oTable, nRow) {
		var aData = oTable.fnGetData(nRow);
		var jqTds = $('>td', nRow);

		for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
			oTable.fnUpdate(aData[i], nRow, i, false);
		}

		oTable.fnDraw();
	}

	function editRow(oTable, nRow) {

		var aData = oTable.fnGetData(nRow);
		var jqTds = $('>td', nRow);
		// jqTds[0].innerHTML = '<input type="text" class="form-control
		// input-small" value="' + aData[0] + '">';
		jqTds[1].innerHTML = '<input type="text" class="form-control input-small" value="'
				+ aData[1] + '">';
		jqTds[2].innerHTML = '<input type="text" class="form-control input-small" value="'
				+ aData[2] + '">';
		jqTds[3].innerHTML = '<a class="edit" href="">Save</a>';
		jqTds[4].innerHTML = '<a class="cancel" href="">Cancel</a>';
		ComponentsPickers.init();
	}

	function saveRow(oTable, nRow) {
		var jqInputs = $('input', nRow);
		oTable.fnUpdate(jqInputs[0].value, nRow, 1, false);
		oTable.fnUpdate(jqInputs[1].value, nRow, 2, false);
		oTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow, 3, false);
		oTable.fnUpdate('<a class="delete" href="">Delete</a>', nRow, 4, false);
		oTable.fnDraw();
		// 向后台更改数据逻辑
		var aData = oTable.fnGetData(nRow);
		var options;
		if (nNew) {
			options={ 
					type : "get",//请求方式 
					url : "dataresource/nodeoperation",//发送请求地址
					dataType : "json", 
					data:{ 
						operation:"create_node",
						parent:"alldatabase_"+$("#whichdatabaseid").text(),
						text:aData[1], 
						comments:aData[2]
					}, 
					success :function(data) {
						alert(data); 
						//data.instance.refresh();
						
					} 
				}
			//alert("添加加入接口");
			//location.reload(true);
			nNew = false;// xingkong add
		} else {
			options={ 
					type : "get",//请求方式 
					url : "dataresource/nodeoperation",//发送请求地址
					dataType : "json", 
					data:{ 
						operation:"rename_node",
						id:aData[0],
						parent:"alldatabase_"+$("#whichdatabaseid").text(),
						text:aData[1], 
						comments:aData[2]
					}, 
					success :function(data) {
						alert(data); 
						//data.instance.refresh();
						
					} 
				}
			//alert("添加更新接口");
		}
		
		$.ajax(options);
		location.reload(true);
	}

	var table = $('#editable_1');

	var oTable = table.dataTable({
		"lengthMenu" : [ [ 5, 15, 20, -1 ], [ 5, 15, 20, "All" ] // change
		// per
		// page
		// values
		// here
		],
		"pageLength" : 5,

		"language" : {
			"lengthMenu" : " _MENU_ records",
			"paging" : {
				"previous" : "Prev",
				"next" : "Next"
			}
		},
		"columnDefs" : [ { // set default column settings
			'orderable' : true,
			'targets' : [ 0 ]
		}, {
			"searchable" : true,
			"targets" : [ 0 ]
		} ],
		"order" : [ [ 0, "asc" ] ]
	// set first column as a default sort by asc
	});

	var tableWrapper = $("#editable_1_wrapper");

	tableWrapper.find(".dataTables_length select").select2({
		showSearchInput : false
	// hide search box with special css class
	}); // initialize select2 dropdown

	var nEditing = null;
	var nNew = false;
	var maxId = 0;
	$('#editable_1_new').click(
			function(e) {
				e.preventDefault();

				if (nNew && nEditing) {
					if (confirm("之前的行没有保存，你想保存它嘛 ?")) {
						saveRow(oTable, nEditing); // save
						$(nEditing).find("td:first").html("Untitled");
						nEditing = null;
						nNew = false;

					} else {
						oTable.fnDeleteRow(nEditing); // cancel
						nEditing = null;
						nNew = false;

						return;
					}
				}

				var aiNew = oTable.fnAddData([ "自动生成", "", "",
						'<a class="edit" href="">Edit</a>',
						'<a class="delete" href="">Delete</a>' ]);
				var nRow = oTable.fnGetNodes(aiNew[0]);
				nEditing = nRow;
				nNew = true;
				editRow(oTable, nRow);
			});
	

	table.on('click', '.delete', function(e) {
		e.preventDefault();

		if (confirm("确定删除该行 ?") == false) {
			return;
		}

		var nRow = $(this).parents('tr')[0];

		var aData = oTable.fnGetData(nRow);

		$(function() {
			//alert("添加删除接口");
			options={ 
					type : "get",//请求方式 
					url : "dataresource/nodeoperation",//发送请求地址
					dataType : "json", 
					data:{ 
						operation:"delete_node",
						id:aData[0],
						parent:"alldatabase_"+$("#whichdatabaseid").text()
					}, 
					success :function(data) {
						alert(data); 
						//data.instance.refresh();
						
					} 
				}
			$.ajax(options);
			oTable.fnDeleteRow(nRow);
			location.reload(true);
			/*
			 * var url = "deletepath.action?path.pid="+aData[0]; $.getJSON(url,
			 * function(data) { if(data=="删除路线成功！"){ oTable.fnDeleteRow(nRow);
			 * alert(data); } });
			 */
		});

	});

	table.on('click', '.cancel', function(e) {
		e.preventDefault();
		if (nNew) {
			oTable.fnDeleteRow(nEditing);
			nEditing = null;
			nNew = false;
		} else {
			restoreRow(oTable, nEditing);
			nEditing = null;
		}
	});
	table.on('click', '.edit', function(e) {
		e.preventDefault();

		/* Get the row as a parent of the link that was clicked on */
		var nRow = $(this).parents('tr')[0];

		var aData = oTable.fnGetData(nRow);

		if (nEditing !== null && nEditing != nRow) {
			/*
			 * Currently editing - but not this row - restore the old before
			 * continuing to edit mode
			 */
			restoreRow(oTable, nEditing);
			editRow(oTable, nRow);
			nEditing = nRow;
		} else if (nEditing == nRow && this.innerHTML == "Save") {
			/* Editing this row and want to save it */
			saveRow(oTable, nEditing);
			nEditing = null;
		} else {
			/* No edit in progress - let's start one */
			editRow(oTable, nRow);
			nEditing = nRow;
		}
	});
}
/*
 * This file is responsible for database information show
 *  @author xingkong
 * */

var ajaxTable2 = function() {

	function restoreRow(oTable, nRow) {
		var aData = oTable.fnGetData(nRow);
		var jqTds = $('>td', nRow);

		for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
			oTable.fnUpdate(aData[i], nRow, i, false);
		}

		oTable.fnDraw();
	}

	function editRow(oTable, nRow) {

		var aData = oTable.fnGetData(nRow);
		var jqTds = $('>td', nRow);
		// jqTds[0].innerHTML = '<input type="text" class="form-control
		// input-small" value="' + aData[0] + '">';
		jqTds[1].innerHTML = '<input type="text" class="form-control input-small" value="'
				+ aData[1] + '">';
		jqTds[2].innerHTML = '<input type="text" class="form-control input-small" value="'
				+ aData[2] + '">';
		jqTds[3].innerHTML = '<a class="edit" href="">Save</a>';
		jqTds[4].innerHTML = '<a class="cancel" href="">Cancel</a>';
		ComponentsPickers.init();
	}

	function saveRow(oTable, nRow) {
		var jqInputs = $('input', nRow);
		oTable.fnUpdate(jqInputs[0].value, nRow, 1, false);
		oTable.fnUpdate(jqInputs[1].value, nRow, 2, false);
		oTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow, 3, false);
		oTable.fnUpdate('<a class="delete" href="">Delete</a>', nRow, 4, false);
		oTable.fnDraw();
		// 向后台更改数据逻辑
		var aData = oTable.fnGetData(nRow);
		var options;
		if (nNew) {
			options={ 
				type : "get",//请求方式 
				url : "dataresource/fieldoperation",//发送请求地址
				dataType : "json", 
				data:{ 
					operation:"create",
					databaseid:$("#whichtableid_belong").text(),
					tableid:$("#whichtableid").text(),
					name:aData[1], 
					comments:aData[2]
				}, 
				success :function(data) {
					alert(data); 
					//data.instance.refresh();
					
				} 
			}
			
			//alert("添加加入接口");
			nNew = false;// xingkong add
		} else {
			options={ 
					type : "get",//请求方式 
					url : "dataresource/fieldoperation",//发送请求地址
					dataType : "json", 
					data:{ 
						operation:"rename",
						databaseid:$("#whichtableid_belong").text(),
						tableid:$("#whichtableid").text(),
						fieldid:aData[0],
						name:aData[1], 
						comments:aData[2]
					}, 
					success :function(data) {
						alert(data); 
						//data.instance.refresh();
						//location.reload(true);
					} 
				}
			//alert("添加更新接口");
		}

		$.ajax(options);
		location.reload(true);
	}

	var table = $('#editable_2');

	var oTable = table.dataTable({
		"lengthMenu" : [ [ 5, 15, 20, -1 ], [ 5, 15, 20, "All" ] // change
		// per
		// page
		// values
		// here
		],
		"pageLength" : 5,

		"language" : {
			"lengthMenu" : " _MENU_ records",
			"paging" : {
				"previous" : "Prev",
				"next" : "Next"
			}
		},
		"columnDefs" : [ { // set default column settings
			'orderable' : true,
			'targets' : [ 0 ]
		}, {
			"searchable" : true,
			"targets" : [ 0 ]
		} ],
		"order" : [ [ 0, "asc" ] ]
	// set first column as a default sort by asc
	});

	var tableWrapper = $("#editable_2_wrapper");

	tableWrapper.find(".dataTables_length select").select2({
		showSearchInput : false
	// hide search box with special css class
	}); // initialize select2 dropdown

	var nEditing = null;
	var nNew = false;
	var maxId = 0;
	$('#editable_2_new').click(
			function(e) {
				e.preventDefault();

				if (nNew && nEditing) {
					if (confirm("之前的行没有保存，你想保存它嘛 ?")) {
						saveRow(oTable, nEditing); // save
						$(nEditing).find("td:first").html("Untitled");
						nEditing = null;
						nNew = false;

					} else {
						oTable.fnDeleteRow(nEditing); // cancel
						nEditing = null;
						nNew = false;

						return;
					}
				}

				var aiNew = oTable.fnAddData([ "自动生成", "", "",
						'<a class="edit" href="">Edit</a>',
						'<a class="delete" href="">Delete</a>' ]);
				var nRow = oTable.fnGetNodes(aiNew[0]);
				nEditing = nRow;
				nNew = true;
				editRow(oTable, nRow);
			});
	

	table.on('click', '.delete', function(e) {
		e.preventDefault();

		if (confirm("确定删除该行 ?") == false) {
			return;
		}

		var nRow = $(this).parents('tr')[0];

		var aData = oTable.fnGetData(nRow);

		$(function() {
			//alert("添加删除接口");
			options={ 
					type : "get",//请求方式 
					url : "dataresource/fieldoperation",//发送请求地址
					dataType : "json", 
					data:{ 
						operation:"delete",
						databaseid:$("#whichtableid_belong").text(),
						tableid:$("#whichtableid").text(),
						fieldid:aData[0]
					}, 
					success :function(data) {
						alert(data); 
						//data.instance.refresh();
						//location.reload(true);
					} 
				}
			$.ajax(options);
			oTable.fnDeleteRow(nRow);
			location.reload(true);
			/*
			 * var url = "deletepath.action?path.pid="+aData[0]; $.getJSON(url,
			 * function(data) { if(data=="删除路线成功！"){ oTable.fnDeleteRow(nRow);
			 * alert(data); } });
			 */
		});

	});

	table.on('click', '.cancel', function(e) {
		e.preventDefault();
		if (nNew) {
			oTable.fnDeleteRow(nEditing);
			nEditing = null;
			nNew = false;
		} else {
			restoreRow(oTable, nEditing);
			nEditing = null;
		}
	});
	table.on('click', '.edit', function(e) {
		e.preventDefault();

		/* Get the row as a parent of the link that was clicked on */
		var nRow = $(this).parents('tr')[0];

		var aData = oTable.fnGetData(nRow);

		if (nEditing !== null && nEditing != nRow) {
			/*
			 * Currently editing - but not this row - restore the old before
			 * continuing to edit mode
			 */
			restoreRow(oTable, nEditing);
			editRow(oTable, nRow);
			nEditing = nRow;
		} else if (nEditing == nRow && this.innerHTML == "Save") {
			/* Editing this row and want to save it */
			saveRow(oTable, nEditing);
			nEditing = null;
		} else {
			/* No edit in progress - let's start one */
			editRow(oTable, nRow);
			nEditing = nRow;
		}
	});
}


ajaxTable1();
var oTable1=$('#editable_1').dataTable();
ajaxTable2();
var oTable2=$('#editable_2').dataTable();

/*
 * This file is responsible for database tree
 *  @author xingkong
 * */
var AjaxTree = function() {

	$("#tree").jstree({
		"core" : {
			"themes" : {
				"responsive" : false
			},
			// so that create works
			"check_callback" : true,
			'data' : {
				'url' : function(node) {
					return 'dataresource/getdatabasetreeinfo';
				},
				'data' : function(node) {
					return {
						'parent' : node.id
					};
				}
			}
		},
		"types" : {
			"default" : {
				"icon" : "fa fa-folder icon-state-warning icon-lg"
			},
			"file" : {
				"icon" : "fa fa-file icon-state-warning icon-lg"
			}
		},
		"state" : {
			"key" : "demo3"
		},
		"plugins" : [ "contextmenu", "unique", "dnd", "types","state" ]
	}).on('delete_node.jstree', function(e, data) {
		$.get('dataresource/nodeoperation?operation=delete_node', {
			'id' : data.node.id,
			'parent' : data.node.parent
		}).done(function(d) {
			//alert(d);
			console.log(d);
		}).fail(function() {
			data.instance.refresh();
		});
	}).on('create_node.jstree', function(e, data) {
		if (data.node.parent.indexOf("alltable") != -1) {
			alert("Can't create node under table node");
			data.instance.refresh();
		} else {
			$.get('dataresource/nodeoperation?operation=create_node', {
				'parent' : data.node.parent,
				'position' : data.position,
				'text' : data.node.text
			}).done(function(d) {
				data.instance.set_id(data.node, d);
			}).fail(function() {
				data.instance.refresh();
			});
		}
	}).on('rename_node.jstree', function(e, data) {
		$.get('dataresource/nodeoperation?operation=rename_node', {
			'id' : data.node.id,
			'parent' : data.node.parent,
			'text' : data.node.text
		}).fail(function() {
			data.instance.refresh();
		});
		location.reload(true);
	}).on('move_node.jstree', function(e, data) {
		/*
		 * $.get('?operation=move_node', { 'id' : data.node.id, 'parent' :
		 * data.parent, 'position' : data.position }) .fail(function () {
		 * data.instance.refresh(); });
		 */
		alert("Move operation not supported");
		data.instance.refresh();
	}).on('copy_node.jstree', function(e, data) {
		/*
		 * $.get('?operation=copy_node', { 'id' : data.original.id, 'parent' :
		 * data.parent, 'position' : data.position }) .always(function () {
		 * data.instance.refresh(); });
		 */
		alert("Copy operation not supported");
		data.instance.refresh();
	}).on('changed.jstree', function(e, data) {

	}).on('select_node.jstree', function(e, data) {
		
		if (data.node.id.indexOf("alldatabase") != -1) {
			//更新“更改数据库信息”模态框中表单内容
			$("#setchangedatabasetitle").text("更改数据库"+data.node.text+"的信息");
			$.ajax({
				type : "get",//请求方式
				url : "dataresource/databaseoperation",//发送请求地址
				data:{
					operation:"get",
					id:data.node.id.substring(data.node.id.indexOf("_")+1)
				},
				dataType : "json",
				success : function(data) {
					$("#form_database_name").val(data.name);
					$("#form_database_comments").val(data.comments);
				}
			});
			oTable1.fnClearTable();
			$(function() {
				var url = "dataresource/getdatabaseinfo?databaseid="+data.node.id.substring(data.node.id.indexOf("_")+1);
				$.getJSON(url, function(data) {
					$.each(data, function(i, table) {
						/* if (table.tableid > maxId)
							maxId = table.tableid; */
						oTable1.fnAddData([ table.tableid, table.name,
						                   table.comments, '<a class="edit" href="">Edit</a>',
								'<a class="delete" href="">Delete</a>' ]);
					});
				});
			});
			$("#whichdatabase").text("数据库:"+data.node.text);
			$("#whichdatabaseid").text(data.node.id.substring(data.node.id.indexOf("_")+1));
			$("#form_database_id").val(data.node.id.substring(data.node.id.indexOf("_")+1));
			
			
			//恢复操作
			if(getCookie("lastclick")=="database"&&reloadCount==0&&typeof(getCookie("restoretable"))!= "undefined"&&getCookie("restoretable")!=null){
				reloadCount++;
				//reload tabletable data
				oTable2.fnClearTable();
				$(function() {
					var url = "dataresource/gettableinfo?databaseid="+(getCookie("restoretable")+"").split("_")[0]+"&tableid="+getCookie("restoretable").split("_")[1];
					$.getJSON(url, function(data) {
						$.each(data, function(i, field) {
							/* if (field.fieldid > maxId)
								maxId = field.fieldid; */
							oTable2.fnAddData([ field.fieldid, field.name,
							                   field.comments, '<a class="edit" href="">Edit</a>',
									'<a class="delete" href="">Delete</a>' ]);
						});
					});
				});
				$("#whichtable").text("表:"+getCookie("restoretable").split("_")[2]);
				console.log(getCookie("restoretable").split("_")[1]);
				$("#whichtableid").text(getCookie("restoretable").split("_")[1]);
				console.log(getCookie("restoretable").split("_")[0]);
				$("#whichtableid_belong").text(getCookie("restoretable").split("_")[0]);
			}
			//end恢复操作
			//记录上次点击的是哪种元素
			document.cookie="restoredatabase="+data.node.id.substring(data.node.id.indexOf("_")+1)+"_"+data.node.text;
			//记录上次带年纪的是哪种
			document.cookie="lastclick="+"database";
		}
		if (data.node.id.indexOf("alltable") != -1) {
			//reload database
			oTable2.fnClearTable();
			$(function() {
				var url = "dataresource/gettableinfo?databaseid="+data.node.parent.substring(data.node.parent.indexOf("_")+1)+"&tableid="+data.node.id.substring(data.node.id.indexOf("_")+1);
				$.getJSON(url, function(data) {
					$.each(data, function(i, field) {
						/* if (field.fieldid > maxId)
							maxId = field.fieldid; */
						oTable2.fnAddData([ field.fieldid, field.name,
						                   field.comments, '<a class="edit" href="">Edit</a>',
								'<a class="delete" href="">Delete</a>' ]);
					});
				});
			});
			$("#whichtable").text("表:"+data.node.text);
			console.log(data.node.id.substring(data.node.id.indexOf("_")+1));
			$("#whichtableid").text(data.node.id.substring(data.node.id.indexOf("_")+1));
			console.log(data.node.parent.substring(data.node.parent.indexOf("_")+1));
			$("#whichtableid_belong").text(data.node.parent.substring(data.node.parent.indexOf("_")+1));
			
			
			//恢复数据
			if(getCookie("lastclick")=="table"&&reloadCount==0&&typeof(getCookie("restoredatabase"))!= "undefined"&&getCookie("restoredatabase")!=null){
				reloadCount++;
				//reload tabletable data
				oTable1.fnClearTable();
				$(function() {
					var url = "dataresource/getdatabaseinfo?databaseid="+getCookie("restoredatabase").split("_")[0];
					$.getJSON(url, function(data) {
						$.each(data, function(i, table) {
							/* if (table.tableid > maxId)
								maxId = table.tableid; */
							oTable1.fnAddData([ table.tableid, table.name,
							                   table.comments, '<a class="edit" href="">Edit</a>',
									'<a class="delete" href="">Delete</a>' ]);
						});
					});
				});
				$("#whichdatabase").text("数据库:"+getCookie("restoredatabase").split("_")[1]);
				$("#whichdatabaseid").text(getCookie("restoredatabase").split("_")[0]);
			}
			
			//记录上次点击的是哪种元素
			document.cookie="restoretable="+data.node.parent.substring(data.node.parent.indexOf("_")+1)+"_"
							+data.node.id.substring(data.node.id.indexOf("_")+1)+"_"+data.node.text;
			//记录上次带年纪的是哪种
			document.cookie="lastclick="+"table";
			
		}
	});
	
}
AjaxTree();
$.ajax({
	type : "get",//请求方式
	url : "dataresource/getalldatabaseinfo",//发送请求地址
	dataType : "json",
	success : function(data) {
		$(data).each(function (i,databaseinfo) {
			$("#database").append("<option value='"+databaseinfo.databaseid+"'>"+databaseinfo.name+"("+databaseinfo.comments+")"+"</option>"); 
		});
	}
});

