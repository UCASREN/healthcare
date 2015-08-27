var FormDropzone = function() {

	return {
		//main function to initiate the module
		init : function() {

			Dropzone.options.myDropzone = {
				// url: "dataresource/batchupload?${_csrf.parameterName}=${_csrf.token}",
				//关闭自动上传功能，默认会true会自动上传
				//也就是添加一个文件向服务器发送一次请求
				autoProcessQueue : false,
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
						console.log("submit");
						myDropzone.processQueue();
					});
					this.on("addedfile", function(file) {
						// Create the remove button
						var removeButton = Dropzone.createElement("<button class='btn btn-sm btn-block'>Remove file</button>");

						// Capture the Dropzone instance as closure.
						var _this = this;

						// Listen to the click event
						removeButton.addEventListener("click", function(e) {
							// Make sure the button click doesn't submit the form:
							e.preventDefault();
							e.stopPropagation();

							// Remove the file preview.
							_this.removeFile(file);
							// If you want to the delete the file on the server as well,
							// you can do the AJAX request here.
						});

						// Add the button to the file preview element.
						file.previewElement.appendChild(removeButton);
						//当添加文件后的事件，上传按钮恢复可用
						$("#submit-all").removeAttr('disabled');
					});
					//当上传完成后的事件，接受的数据为JSON格式
					this.on("complete", function(data) {
						console.log("hello");
						if (this.getUploadingFiles().length === 0 && this.getQueuedFiles().length === 0) {
							var res = eval('(' + data.xhr.responseText + ')');
							var msg;
							if (res.result) {
								msg = "恭喜，已成功上传" + res.count + "个文件！";
							} else {
								msg = "上传失败，失败的原因是：" + res.message;
							}
							console.log(msg);
							// $("#message").text(msg);
							// $("#dialog").dialog("open");
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
