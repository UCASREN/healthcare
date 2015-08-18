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
		"plugins" : [ "contextmenu", "unique", "dnd", "types" ]
	}).on('delete_node.jstree', function(e, data) {
		$.get('dataresource/nodeoperation?operation=delete_node', {
			'id' : data.node.id,
			'parent' : data.node.parent
		}).done(function(d) {
			alert(d);
		}).fail(function() {
			data.instance.refresh();
		});
	}).on('create_node.jstree', function(e, data) {
		if (data.node.parent.indexOf("alldatabase") != -1) {
			$.get('dataresource/nodeoperation?operation=create_node', {
				'parent' : data.node.parent,
				'position' : data.position,
				'text' : data.node.text
			}).done(function(d) {
				data.instance.set_id(data.node, d);
			}).fail(function() {
				data.instance.refresh();
			});
		} else {
			alert("Can't create node under table node");
			data.instance.refresh();
		}
	}).on('rename_node.jstree', function(e, data) {
		$.get('dataresource/nodeoperation?operation=rename_node', {
			'id' : data.node.id,
			'parent' : data.node.parent,
			'text' : data.text
		}).fail(function() {
			data.instance.refresh();
		});
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

		}
	});

}
