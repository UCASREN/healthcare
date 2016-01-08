var FormWizard = function () {
	
	  var initPickers = function () {
	        //init date pickers
		  	var myDate = new Date();
		  	var dataArray = myDate.toLocaleDateString().split('/');
		  	var month = dataArray[1];
		  	var day = dataArray[2];
		  	if (month >= 1 && month <= 9) {
		  		  month = "0" + month;
		  	 }
		  	if (day >= 0 && day <= 9) {
		  		day = "0" + day;
		  	}
		  	var date_default = dataArray[0]+"/"+month+"/"+day;
		  	$('#submit_form_applyDate').val(date_default);
	        $('.date-picker').datepicker({
//	        	defaultDate: +0,
	            rtl: Metronic.isRTL(),
	            autoclose: true,
	        });
	    }

    return {
    	
        //main function to initiate the module
        init: function () {
        	
            if (!jQuery().bootstrapWizard) {
                return;
            }

        	initPickers();
            
            var form = $('#submit_form');
            var error = $('.alert-danger', form);
            var success = $('.alert-success', form);
            
            var fillForm = function (hc_doc){
            	console.log("begin fill the form---");
//            	$.each(hc_doc,function(key,val){
//            		console.log(key+" : "+val);
//            	});
            	
            	$('#submit_form_userName').val(hc_doc.name);
            	$('#submit_form_userDepartment').val(hc_doc.department);
            	$('#submit_form_userAddress').val(hc_doc.address);
            	$('#submit_form_userTel').val(hc_doc.tel);
            	$('#submit_form_userEmail').val(hc_doc.email);
            	
            	if(hc_doc.demandtype == '数据分析需求')
            		$('#form_wizard_1 input[data-title="数据分析需求"]').iCheck('check');
            	else if(hc_doc.demandtype == '数据使用需求')
            		$('#form_wizard_1 input[data-title="数据使用需求"]').iCheck('check');
            	$('#submit_form_userDemand').val(hc_doc.demand);
            	if(hc_doc.applyDataExportType == 'CSV')
            		$('#form_wizard_1 input[data-title="CSV"]').iCheck('check');
            	else if(hc_doc.applyDataExportType == 'Excel')
            		$('#form_wizard_1 input[data-title="Excel"]').iCheck('check');
            	
            	arr = hc_doc.proUsefield.split(",");
            	console.log(arr)
            	for(var i=0; i<arr.length; i++){
            		var tmp = arr[i];
            		switch(tmp){
            			case "政府决策":
            				$('#form_wizard_1 input[data-title="政府决策"]').iCheck('check');break;
            			case "科学研究":
            				$('#form_wizard_1 input[data-title="科学研究"]').iCheck('check');break;
            			case "教学":
            				$('#form_wizard_1 input[data-title="教学"]').iCheck('check');break;
            			case "博士论文":
            				$('#form_wizard_1 input[data-title="博士论文"]').iCheck('check');break;
            			case "硕士论文":
            				$('#form_wizard_1 input[data-title="硕士论文"]').iCheck('check');break;
            			case "商业应用":
            				$('#form_wizard_1 input[data-title="商业应用"]').iCheck('check');break;
            			default:
            				if(tmp.indexOf('其他') == -1) break;
            				$('#form_wizard_1 input[data-title="其他"]').iCheck('check');
            				var tmp1 = tmp.substring(2);
            				if(tmp1 != ""){
            					$('#form_wizard_1 #others').val(tmp1)
            					$('#form_wizard_1 #others').show();
            				}
            		}
            	}
            	$('#submit_form_projectName').val(hc_doc.proName);
            	$('#submit_form_projectChairman').val(hc_doc.proChair);
            	$('#submit_form_projectSource').val(hc_doc.proSource);
            	$('#submit_form_projectUndertaking').val(hc_doc.proUndertake);
            	$('#submit_form_applyDate').val(hc_doc.applyTime);
            	$('#submit_form_projectRemarks').val(hc_doc.proRemark);
            	
            	$('#applydata').val(hc_doc.applyData);
            	var shopdata = hc_doc.applyData.split(',');
            	
            	var shoptable = $('#shoptable');
            	var shoppanel = $("#shoppanel");
            	
            	for(var i=1; i<shopdata.length; i++){
//            		alert(shopdata[i]);
        			var tr = $("<tr class='append'></tr>");
        			var db_name = shopdata[i].split('_')[1];
        			var t_name = shopdata[i].split('_')[3];
        			var t_comment = shopdata[i].split('_')[4];
//        			console.log(db_name+" : "+t_name+" : "+t_comment);
        			tr.append('<td style="width:10%;text-align:center;">'+db_name+'</td>');
        			tr.append('<td style="width:10%;text-align:center;">'+t_name+'</td>');
        			tr.append('<td style="width:10%;text-align:center;">'+t_comment+'</td>');
            		shoptable.append(tr);
            	}
            }
            
            //获取文档id
            var docid = $.query.get('docid');
            var applydataid = $.query.get('applydataid');
            if(applydataid != ""){
            	$('#applydataid').val(applydataid);
            }
            	
            if(docid != ""){//编辑原有申请阶段
            	console.log('编辑原有申请阶段:'+docid)
            	$('#docid').val(docid);
            	console.log('参数:'+docid);
            	
            	options1={ 
   					type : "get",//请求方式 
   					url : "getdocdatabydocid",//发送请求地址
   					dataType : "json", 
   					data:{ 
   						docid : docid
   					}, 
   					success :function(data) {
   						//alert(data); 
   						console.log(docid+" : "+data);
   						options2={ 
   		       					type : "get",//请求方式 
   		       					url : "/healthcare/putshopdetailIntoSessionfromdb",//发送请求地址
   		       					dataType : "json", 
   		       					data:{ 
   		       						docid : docid,
   		       						applyType : "env"
   		       					}, 
   		       					success :function(data1) {
   		       						//alert(data); 
   		       						console.log("keep session synchronization!");
   		       						console.log(docid+" : "+data1);
   		       						fillForm(data);
   		       						$('#shoppingCart').trigger("click");
   		       					} 
   		       				}
   		           		$.ajax(options2);
   					} 
   				}
       			$.ajax(options1);
            	
            }else{//新建申请阶段
            	console.log('新建申请阶段:'+docid)
            	
            	//history标志位，标志是否从数据选择界面加载得到---(如果从数据选择界面来，不对session做处理)
                var historyFlag = $.query.get('historyFlag');
                if(historyFlag == "1"){//不对session做处理
                	
                	$.ajax({ 
      					type : "get",//请求方式 
      					url : "/healthcare/getshoppingcartAlldetail",//发送请求地址
      					dataType : "json", 
      					success :function(data) {
      						fillShoppingCart(data);
      					} 
              		});
                	
                }else{//清空session内容
                	
                	var emptyshop = $('#emptyshoppingcart');
                	options2={ 
       					type : "get",//请求方式 
       					url : "/healthcare/deleteallshoppingcart",
       					success :function(data) {
       						console.log("shopping is emptying!"); 
       						emptyshop.show();
       					} 
       				}
           			$.ajax(options2);
                	
                }
            };

            form.validate({
                doNotHideMessage: true, //this option enables to show the error/success messages on tab switch.
                errorElement: 'span', //default input error message container
                errorClass: 'help-block help-block-error', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                rules: {
                    //用户信息
                	userName: {
                        required: true
                    },
                    userDepartment: {
                        required: true
                    },
                    userAddress: {
                        required: true,
//                        equalTo: "#submit_form_password"
                    },
                    userTel: {
//                        digits: true,
                    	required: true,
                    },
                    userEmail: {
                    	required:true,
//                    	email: true
                    },
                    
                    //数据需求
                    userDemandType:{
                    	required: true,
                    },
                    dataExportType:{
                    	required: true,
                    },
                    userDemand: {
                        required: true
                    },
                    
                    //数据使用目的--project
                    projectName: {
                        required: true
                    },
                    projectChairman: {
                        required: true
                    },
                    projectSource: {
                        required: true,
                    },
                    projectUndertaking: {
                        required: true
                    },
                    applyDate:{
                    	required: true,
                    },
                    'useFields': {
                        required: true,
                        minlength: 1
                    },
                    projectRemarks:{
                    	required: true,
                    }
                },

                messages: { // custom messages for radio buttons and checkboxes
                    'useFields': {
                        required: "至少选择一个应用领域",
                        minlength: jQuery.validator.format("至少选择一个应用领域")
                    }
                },

                errorPlacement: function (error, element) { // render error placement for each input type
                    if (element.attr("name") == "userDemandType") { // for uniform radio buttons, insert the after the given container
                        error.insertAfter("#form_demand_error");
                    } else if (element.attr("name") == "useFields") { // for uniform checkboxes, insert the after the given container
                        error.insertAfter("#form_useFields_error");
                    } else {
                        error.insertAfter(element); // for other inputs, just perform default behavior
                    }
                },

                invalidHandler: function (event, validator) { //display error alert on form submit   
                    success.hide();
                    error.show();
                    Metronic.scrollTo(error, -200);
                },

                highlight: function (element) { // hightlight error inputs
                    $(element)
                        .closest('.form-group').removeClass('has-success').addClass('has-error'); // set error class to the control group
                },

                unhighlight: function (element) { // revert the change done by hightlight
                    $(element)
                        .closest('.form-group').removeClass('has-error'); // set error class to the control group
                },

                success: function (label) {
                    if (label.attr("for") == "userDemandType" || label.attr("for") == "useFields") { // for checkboxes and radio buttons, no need to show OK icon
                        label
                            .closest('.form-group').removeClass('has-error').addClass('has-success');
                        label.remove(); // remove error label here
                    } else { // display success icon for other inputs
                        label.addClass('valid') // mark the current input as valid and display OK icon
                        .closest('.form-group').removeClass('has-error').addClass('has-success'); // set success class to the control group
                    }
                },

                //处理提交信息
                submitHandler: function (form) {
                    success.show();
                    error.hide();
                    //add here some ajax code to submit your form or just call form.submit() if you want to submit the form without ajax
                    form.submit();
                }

            });

            var displayConfirm = function() {
                $('#tab4 .form-control-static', form).each(function(){
                    var input = $('[name="'+$(this).attr("data-display")+'"]', form);
                    if (input.is(":radio")) {
                        input = $('[name="'+$(this).attr("data-display")+'"]:checked', form);
                    }
                    if (input.is(":text") || input.is("textarea")) {
                        $(this).html(input.val());
                    } else if (input.is("select")) {
                        $(this).html(input.find('option:selected').text());
                    } else if (input.is(":radio") && input.is(":checked")) {
                        $(this).html(input.attr("data-title"));
                    } else if ($(this).attr("data-display") == 'useFields') {
                        var useFields = [];
                        $('[name="useFields"]:checked', form).each(function(){ 
                            useFields.push($(this).attr('data-title'));
                        });
                        $(this).html(useFields.join("<br>"));
                    }
                });
            }

            var handleTitle = function(tab, navigation, index) {
                var total = navigation.find('li').length;
                var current = index + 1;
                // set wizard title
                $('.step-title', $('#form_wizard_1')).text(' ' + (index + 1) + ' / ' + total);
                // set done steps
                jQuery('li', $('#form_wizard_1')).removeClass("done");
                var li_list = navigation.find('li');
                for (var i = 0; i < index; i++) {
                    jQuery(li_list[i]).addClass("done");
                }

                if (current == 1) {
                    $('#form_wizard_1').find('.button-previous').hide();
                } else {
                    $('#form_wizard_1').find('.button-previous').show();
                }

                if (current >= total) {
                    $('#form_wizard_1').find('.button-next').hide();
                    $('#form_wizard_1').find('.button-submit').show();
                    $('#form_wizard_1').find('.button-wordPreview').show();
                    displayConfirm();
                } else {
                    $('#form_wizard_1').find('.button-next').show();
                    $('#form_wizard_1').find('.button-submit').hide();
                    $('#form_wizard_1').find('.button-wordPreview').hide();
                }
                Metronic.scrollTo($('.page-title'));
            }

            // default form wizard
            $('#form_wizard_1').bootstrapWizard({
                'nextSelector': '.button-next',
                'previousSelector': '.button-previous',
                
                onTabClick: function (tab, navigation, index, clickedIndex) {
                    return false;
                    /*
                    success.hide();
                    error.hide();
                    if (form.valid() == false) {
                        return false;
                    }
                    handleTitle(tab, navigation, clickedIndex);
                    */
                },
                onNext: function (tab, navigation, index) {
                    success.hide();
                    error.hide();

                    if (form.valid() == false) {
                        return false;
                    }

                    handleTitle(tab, navigation, index);
                },
                onPrevious: function (tab, navigation, index) {
                    success.hide();
                    error.hide();

                    handleTitle(tab, navigation, index);
                },
                
                onTabShow: function (tab, navigation, index) {
                    var total = navigation.find('li').length;
                    var current = index + 1;
                    var $percent = (current / total) * 100;
                    $('#form_wizard_1').find('.progress-bar').css({
                        width: $percent + '%'
                    });
                }
            });
            
            $('#form_wizard_1 #others').hide();
            $('#form_wizard_1').find('.button-previous').hide();
            $('#form_wizard_1 .button-submit').click(function () {
                alert('提交成功，我们会尽快进行审核，请耐心等待！');
            	if($('#others').val() != ""){
            		var allUseField_tmp = $('#allUseField').val();
               	 	$('#allUseField').val(allUseField_tmp.substring(0,allUseField_tmp.length-1)+""+$('#others').val());
            	}
            	
            	$('#submit_form').attr("action","/healthcare/applyenv/createdataword");
                $('#submit_form').submit(); 
            }).hide();
            
            //预览
            $('#form_wizard_1 .button-wordPreview').click(function () {
            	if(!confirm('确定文档预览？\n（这需要等待一段时间）'))
            		return false;
                //表单异步提交
	      		var ajax_url = "/healthcare/applyenv/firstwordonline"; //表单目标 
	      		var ajax_type = $('#submit_form').attr('method'); //提交方法 
	      		var ajax_data = $('#submit_form').serialize(); //表单数据 
	          		
	      		$.ajax({
	      		url: ajax_url,
	      		data: ajax_data,
	      		type: ajax_type,
	      		success: function(data){
	                  window.open ("/healthcare/applyenv/first_documentView", "word预览", "height=800, width=600, target=_parent,toolbar=no,menubar=no, scrollbars=no, resizable=no, location=no, status=no");
	      		}
	      		});
          		
            }).hide();

            
            $('#db_select').click(function(){
            	if(confirm('选择数据加入购物车后，回到本界面加载购物车！')){
            		window.open ("/healthcare/userdatabaseview");
                  	return false;
            	}
            });
            
            $('#shoppingCart').click(function(){
            	$.ajax({ 
  					type : "get",//请求方式 
  					url : "/healthcare/getshoppingcartAlldetail",//发送请求地址
  					dataType : "json", 
  					success :function(data) {
//  						alert("购物车内容 : "+data); 
  						fillShoppingCart(data);
  					} 
          		});
            	return false;
            });
            
            $("#cleanShopingCart").click(function(){
            	if(!confirm('确定清空数据集？'))
            		return false;
            	$.ajax({ 
            		type : "get",//请求方式 
            		url : "/healthcare/deleteallshoppingcart",//发送请求地址
            		success :function(data) {
            			//location.reload(true);
            		} 
            	});
            	$('#shoppingCart').trigger("click");
            	return false;
            });

            
            function fillShoppingCart(shopdata){
            	
            	var flag = true;
            	var emptyshop = $('#emptyshoppingcart');
            	var shoptable = $('#shoptable');
            	var shoppanel = $("#shoppanel");
            	
            	$.each(shopdata, function(key,values){
            		if(flag){
            			$('#applydata').val("");
            			$("tr").remove(".append");
                    	flag = false;
            		}
            		$('#applydata').val($('#applydata').val()+','+values);
//            		alert($('#applydata').val());
            		var db_name = key.split('_')[1];
            		for(var i=0; i<values.length; i++){
//            			alert("倪嘉志test"+values);
            			var tr = $("<tr class='append'></tr>");
            			var t_name = values[i].split('_')[3];
            			var t_comment = values[i].split('_')[4];
            			console.log(db_name+" : "+t_name+" : "+t_comment);
            			tr.append('<td style="width:10%;text-align:center;">'+db_name+'</td>');
            			tr.append('<td style="width:10%;text-align:center;">'+t_name+'</td>');
            			tr.append('<td style="width:10%;text-align:center;">'+t_comment+'</td>');
            			shoptable.append(tr);
            		}
//        		    console.log(key+"包含:"+values);
        		});
            	
            	if(flag){
            		shoptable.hide();
            		emptyshop.show();
            		shoppanel.show();
            	}else{
            		emptyshop.hide();
            		shoptable.show();
            		shoppanel.show();
            	}
            };
            
            $("#checkbox_other").on('ifChecked', function(event){
           	 	$('#form_wizard_1 #others').show();
            });
            $("#checkbox_other").on('ifUnchecked', function(event){
           	 	$('#form_wizard_1 #others').hide();
            });
            
            $('#form_wizard_1 input[type="checkbox"]').on('ifChanged', function(event){
//            	alert(event.type + ' callback');
            	var tmp = '';
//            	console.log($('#form_wizard_1 div.icheckbox_flat-blue').attr('checked'));
            	$('#form_wizard_1 input[type="checkbox"]').each(function(){
//            		console.log($(this).attr('checked'));
            		if($(this).val()!="projectApply" && $(this).attr("checked") == "checked"){
            				tmp += $(this).val()+",";
//            			console.log(tmp);
            		}
            	});
            	$('#allUseField').val(tmp);
            });
            
            $('.applytime').hide();
            
            $("#projectApply").on('ifUnchecked', function(event){
           	 	$('#form_wizard_1 .projectItems').hide();
           	 	$('#form_wizard_1 #submit_form_projectName').val("无");
           	 	$('#form_wizard_1 #submit_form_projectChairman').val("无");
           	 	$('#form_wizard_1 #submit_form_projectSource').val("无");
           	 	$('#form_wizard_1 #submit_form_projectUndertaking').val("无");
            });
            
            $("#projectApply").on('ifChecked', function(event){
           	 	$('#form_wizard_1 #submit_form_projectName').val("");
        	 	$('#form_wizard_1 #submit_form_projectChairman').val("");
        	 	$('#form_wizard_1 #submit_form_projectSource').val("");
        	 	$('#form_wizard_1 #submit_form_projectUndertaking').val("");
           	 	$('#form_wizard_1 .projectItems').show();
            });
            
            
        }

    };

}();