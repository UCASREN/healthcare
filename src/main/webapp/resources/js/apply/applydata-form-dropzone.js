var FormDropzone = function() {

	return {
		//main function to initiate the module
		init : function() {

			Dropzone.options.myDropzone = {
				// url: "dataresource/batchupload?${_csrf.parameterName}=${_csrf.token}",
				//关闭自动上传功能，默认会true会自动上传
				//也就是添加一个文件向服务器发送一次请求
				autoProcessQueue : false,
				acceptedFiles:".zip",
				/*
				//允许上传多个文件
				uploadMultiple : true,

				//每次上传的最多文件数，经测试默认为2
				//记得修改web.config 限制上传文件大小的节
				parallelUploads : 100,*/
				init : function() {
					myDropzone = this;
					 var submitButton = $("#submit-all");
					submitButton.click(function() {
						myDropzone.processQueue();
					});
					this.on("addedfile", function(file) {
						// Create the remove button
						var removeButton = Dropzone.createElement("<button class='btn btn-sm btn-block'>删除</button>");

						// Capture the Dropzone instance as closure.
						var _this = this;

						// Listen to the click event
						removeButton.addEventListener("click", function(e) {
							// Make sure the button click doesn't submit the form:
							e.preventDefault();
							e.stopPropagation();

							// Remove the file preview.
							_this.removeFile(file);
							// If you d to the delete the file on the server as well,
							// you can do the AJAX request here.
						});

						// Add the button to the file preview element.
						file.previewElement.appendChild(removeButton);
						//当添加文件后的事件，上传按钮恢复可用
						$("#submit-all").removeAttr('disabled');
					});
					//当上传完成后的事件，接受的数据为JSON格式
					this.on("complete", function(data) {
						if (this.getUploadingFiles().length === 0 && this.getQueuedFiles().length === 0) {
							console.log(data);
							//var msg = eval('(' + data.xhr.responseText + ')');
							//console.log(msg);
							// $("#message").text(msg);
							// $("#dialog").dialog("open");
							console.log("上传完成");
						}
					});
					this.on("removedfile", function() {
						if (this.getAcceptedFiles().length === 0) {
							$("#submit-all").attr("disabled", true);
						}
					});
				}
			}
		}
	};
}();
