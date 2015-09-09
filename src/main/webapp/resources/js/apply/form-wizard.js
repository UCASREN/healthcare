var FormWizard = function () {
	
	  var initPickers = function () {
	        //init date pickers
	        $('.date-picker').datepicker({
	            rtl: Metronic.isRTL(),
	            autoclose: true
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
            	$.each(hc_doc,function(key,val){
            		console.log(key+" : "+val);
            	});
            	
            	$('#submit_form_userName').val(hc_doc.name);
            	$('#submit_form_userDepartment').val(hc_doc.department);
            	$('#submit_form_userAddress').val(hc_doc.address);
            	$('#submit_form_userTel').val(hc_doc.tel);
                
            	
            	$('#submit_form_userDemand').val(hc_doc.demand);
            	
            	$('#submit_form_projectName').val(hc_doc.proName);
            	$('#submit_form_projectChairman').val(hc_doc.proChair);
            	$('#submit_form_projectSource').val(hc_doc.proSource);
            	$('#submit_form_projectUndertaking').val(hc_doc.proUndertake);
            	$('#submit_form_applyDate').val('暂无');
            	$('#submit_form_projectRemarks').val(hc_doc.proRemark);

            }
            
            //获取文档id
            var param = $.query.get('docid');
            if(param != ""){
            	   console.log('参数:'+param);
            	   options={ 
       					type : "get",//请求方式 
       					url : "getdocdatabydocid",//发送请求地址
       					dataType : "json", 
       					data:{ 
       						docid : param
       					}, 
       					success :function(data) {
       						//alert(data); 
       						console.log(param+" : "+data);
       						fillForm(data);
       					} 
       				}
       			$.ajax(options);
            }else{
            	console.log('参数为空:'+param)
            };
            
         
            

            form.validate({
                doNotHideMessage: true, //this option enables to show the error/success messages on tab switch.
                errorElement: 'span', //default input error message container
                errorClass: 'help-block help-block-error', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                rules: {
                    //用户信息
                	userName: {
//                        required: true
                    },
                    userDepartment: {
//                        required: true
                    },
                    userAddress: {
//                        required: true,
//                        equalTo: "#submit_form_password"
                    },
                    userTel: {
//                        digits: true,
//                    	required: true,
                    },
                    userEmail: {
//                    	required:true,
//                    	email: true
                    },
                    
                    //数据需求
                    userDemandType:{
//                    	required: true,
                    },
                    userDemand: {
//                        required: true
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
                $('#submit_form').submit(); 
            }).hide();
            
            $('#form_wizard_1 .button-wordPreview').click(function () {
                alert('word文档预览');
                //window.open ("/healthcare/", "word预览", "height=800, width=600, target=_parent,toolbar=no,menubar=no, scrollbars=no, resizable=no, location=no, status=no");
            }).hide();

            $('#form_wizard_1 input[type="checkbox"]').on('ifChecked', function(event){
//            	alert(event.type + ' callback');
            	var tmp = '';
//            	console.log($('#form_wizard_1 div.icheckbox_flat-blue').attr('checked'));
            	$('#form_wizard_1 input[type="checkbox"]').each(function(){
//            		console.log('njz');
//            		console.log($(this).attr('checked'));
            		
            		if($(this).attr("checked") == "checked"){
            			tmp += $(this).val()+",";
            			console.log(tmp);
            		}
            	});
            	$('#allUseField').val(this.value+","+tmp);
            });
        }

    };

}();