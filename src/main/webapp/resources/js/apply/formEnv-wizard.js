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

            }
            
            //获取文档id
            var docid = $.query.get('docid');
            var applydataid = $.query.get('applydataid');
            if(applydataid != ""){
            	$('#applydataid').val(applydataid);
            }
            	
            if(docid != ""){
            	   $('#docid').val(docid);
            	   console.log('参数:'+docid);
            	   options={ 
       					type : "get",//请求方式 
       					url : "getdocdatabydocid",//发送请求地址
       					dataType : "json", 
       					data:{ 
       						docid : docid
       					}, 
       					success :function(data) {
       						//alert(data); 
       						console.log(docid+" : "+data);
       						fillForm(data);
       					} 
       				}
       			$.ajax(options);
            }else{
            	console.log('参数为空:'+docid)
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
                    $('#form_wizard_1').find('.button-wordPreview').hide();
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
           	 	
                $('#submit_form').submit(); 
            }).hide();
            
            $('#form_wizard_1 .button-wordPreview').click(function () {
                alert('word文档预览');
                //window.open ("/healthcare/", "word预览", "height=800, width=600, target=_parent,toolbar=no,menubar=no, scrollbars=no, resizable=no, location=no, status=no");
            }).hide();

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
            		if($(this).attr("checked") == "checked"){
            				tmp += $(this).val()+",";
//            			console.log(tmp);
            		}
            	});
            	$('#allUseField').val(tmp);
            });
            
            
        }

    };

}();