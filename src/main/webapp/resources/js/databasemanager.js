

var TableEditable = function () {
	var AjaxTree = function() {

        $("#tree").jstree({
            "core" : {
                "themes" : {
                    "responsive": false
                }, 
                // so that create works
                "check_callback" : true,
                'data' : {
                    'url' : function (node) {
                      return 'dataresource/getdatabasetreeinfo';
                    },
                    'data' : function (node) {
                      return { 'parent' : node.id };
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
            "state" : { "key" : "demo3" },
            "plugins" : [ "contextmenu","unique","dnd", "types" ]
        }).on('delete_node.jstree', function (e, data) {
			$.get('?operation=delete_node', { 'id' : data.node.id })
			.fail(function () {
				data.instance.refresh();
			});
	})
	.on('create_node.jstree', function (e, data) {
		$.get('?operation=create_node', { 'parent' : data.node.parent, 'position' : data.position, 'text' : data.node.text })
			.done(function (d) {
				data.instance.set_id(data.node, d.id);
			})
			.fail(function () {
				data.instance.refresh();
			});
	})
	.on('rename_node.jstree', function (e, data) {
		$.get('?operation=rename_node', { 'id' : data.node.id, 'text' : data.text })
			.fail(function () {
				data.instance.refresh();
			});
	})
	.on('move_node.jstree', function (e, data) {
		$.get('?operation=move_node', { 'id' : data.node.id, 'parent' : data.parent, 'position' : data.position })
			.fail(function () {
				data.instance.refresh();
			});
	})
	.on('copy_node.jstree', function (e, data) {
		$.get('?operation=copy_node', { 'id' : data.original.id, 'parent' : data.parent, 'position' : data.position })
			.always(function () {
				data.instance.refresh();
			});
	})
	.on('changed.jstree', function (e, data) {
		if(data && data.selected && data.selected.length) {
			$.get('?operation=get_content&id=' + data.selected.join(':'), function (d) {
				$('#data .default').html(d.content).show();
			});
		}
		else {
			$('#data .content').hide();
			$('#data .default').html('Select a file from the tree.').show();
		}
	});

        
    }
    var handleTable = function () {
    	
    	
    	

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
            //jqTds[0].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[0] + '">';
            jqTds[1].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[1] + '">';
            jqTds[2].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[2] + '">';
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
          //向后台更改数据逻辑
            var aData = oTable.fnGetData(nRow);
            var options;
            if(nNew){
            	/*
            	options={
            		type : "post",//请求方式
            		url : "addpath.action",//发送请求地址
            		dataType : "json",
            		data:{
    					"path.pid":aData[0],
    					"path.name":aData[1],
    					"path.startPosition":aData[2],
    					"path.endPosition":aData[3],
    					"path.startTime":aData[4]+':00',
    					"path.endTime":aData[5]+':00',
    					"path.part":aData[6]
    				},
            		success : function(data) {
            			if(nNew) maxId++;
            			alert(data);
            		}
            	}
            	*/
            	alert("添加加入接口");
            	nNew=false;//xingkong add
            }
            else {
            	/*
            	options={
                		type : "post",//请求方式
                		url : "updatepath.action",//发送请求地址
                		dataType : "json",
                		data:{
                			"path.pid":aData[0],
        					"path.name":aData[1],
        					"path.startPosition":aData[2],
        					"path.endPosition":aData[3],
        					"path.startTime":aData[4],
        					"path.endTime":aData[5],
        					"path.part":aData[6]
        				},
                		success : function(data) {
                			if(nNew) maxId++;
                			alert(data);
                		}
                	}
                */
            	alert("添加更新接口");
            }
            
            $.ajax(options);
        }
        
        var table = $('#sample_editable_1');

        var oTable = table.dataTable({
            "lengthMenu": [
                [5, 15, 20, -1],
                [5, 15, 20, "All"] // change per page values here
            ],
            "pageLength": 10,

            "language": {
                "lengthMenu": " _MENU_ records",
                "paging": {
                    "previous": "Prev",
                    "next": "Next"
                }
            },
            "columnDefs": [{ // set default column settings
                'orderable': true,
                'targets': [0]
            }, {
                "searchable": true,
                "targets": [0]
            }],
            "order": [
                [0, "asc"]
            ] // set first column as a default sort by asc
        });

        var tableWrapper = $("#sample_editable_1_wrapper");

        tableWrapper.find(".dataTables_length select").select2({
            showSearchInput: false //hide search box with special css class
        }); // initialize select2 dropdown

        var nEditing = null;
        var nNew = false;
        var maxId=0;
        $('#sample_editable_1_new').click(function (e) {
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

            var aiNew = oTable.fnAddData([maxId+1,"","",
       		        	                 '<a class="edit" href="">Edit</a>',
       		        	                 '<a class="delete" href="">Delete</a>']);
            var nRow = oTable.fnGetNodes(aiNew[0]);
            nEditing = nRow;
            nNew = true;
            editRow(oTable, nRow);
        });
        $(function () {
 			var url = "../../dataResource/getalldatabaseinfo";
 		    $.getJSON(url, function(data) {
 		        $.each(data, function(i, database)
 		        {
 		        	if(database.databaseid>maxId) maxId=database.databaseid;
 		        	oTable.fnAddData([database.databaseid,database.name,database.comments,
 		        	                 '<a class="edit" href="">Edit</a>',
 		        	                 '<a class="delete" href="">Delete</a>']);
 		        });
 		    });
 		});

        table.on('click', '.delete', function (e) {
            e.preventDefault();

            if (confirm("确定删除该行 ?") == false) {
                return;
            }

            var nRow = $(this).parents('tr')[0];
            
            var aData = oTable.fnGetData(nRow);
            
            $(function () {
            	alert("添加删除接口");
            	/*
                var url = "deletepath.action?path.pid="+aData[0];  			
     		    $.getJSON(url, function(data) {
     		        if(data=="删除路线成功！"){
     		            oTable.fnDeleteRow(nRow);
     		            alert(data);
     		        }
     		    });
     		    */
     		});
            
        });
        
        table.on('click', '.cancel', function (e) {
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
        table.on('click', '.edit', function (e) {
            e.preventDefault();

            /* Get the row as a parent of the link that was clicked on */
            var nRow = $(this).parents('tr')[0];
            
            var aData = oTable.fnGetData(nRow);

            if (nEditing !== null && nEditing != nRow) {
                /* Currently editing - but not this row - restore the old before continuing to edit mode */
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

    return {

        //main function to initiate the module
        init: function () {
            handleTable();
            AjaxTree();
        }

    };

}();