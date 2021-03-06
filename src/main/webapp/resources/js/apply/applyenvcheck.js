var applyenvCheck = function () {
	
    return {
    	
        //main function to initiate the module
        init: function () {
        	
        	  $("#logoutbutton").click(function(){
              	$("#logoutform").submit();
              });
        	
            var form = $('#applycheck_form');
            
            var fillForm = function (hc_doc){
//            	console.log("begin fill the form---");
//            	$.each(hc_doc,function(key,val){
//            		console.log(key+" : "+val);
//            	});
            	
            	$('#userName').html(hc_doc.name);
            	$('#userDepartment').html(hc_doc.department);
            	$('#userAddress').html(hc_doc.address);
            	$('#userTel').html(hc_doc.tel);
            	$('#userEmail').html(hc_doc.email);
            	
            	$('#userDemandType').html(hc_doc.demandtype);
            	$('#userDemand').html(hc_doc.demand);
            	
            	$('#useFields').html(hc_doc.proUsefield);
            	$('#projectName').html(hc_doc.proName);
            	$('#projectChairman').html(hc_doc.proChair);
            	$('#projectSource').html(hc_doc.proSource);
            	$('#projectUndertaking').html(hc_doc.proUndertake);
            	$('#applyDate').html(hc_doc.applyTime);
            	$('#projectRemarks').html(hc_doc.proRemark);

            }
            
            //获取文档id
            var docid = $.query.get('docid');
            var applydataid = $.query.get('applydataid');
            	
            if(applydataid != ""){
            		$('#applydataid').val(applydataid);
            		console.log('参数:'+docid);
            	   options={ 
       					type : "get",//请求方式 
       					url : "getdocenvbyapplyid",//发送请求地址
       					dataType : "json", 
       					data:{ 
       						applyid : applydataid
       					}, 
       					success :function(data) {
       						//alert(data); 
       						console.log(applydataid+" : "+data);
       						fillForm(data);
       					} 
       				}
       			$.ajax(options);
            }else{
            	console.log('参数为空:'+docid)
            };
            
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

            
            $('#applycheck_form #submit_button').click(function () {
            	//获取用户身份
            	$.ajax({ 
  					type : "get",//请求方式 
  					url : "applycheck_getauthority",//发送请求地址
  					data:{ 
  					},
  					success :function(data) {
//   						alert(data); 
  						applyenvcheck_success(data);
   					} 
      			});
            });
            
            var applyenvcheck_success = function(curAuthority){
            	if(curAuthority == 'ROLE_SU1')
            		curAuthority = '卒中中心';
            	if(curAuthority == 'ROLE_SU2')
            		curAuthority = '卒中防治办公室';
            	
            	if(!confirm('当前审核单位 ：'+curAuthority+'\n\n您确定同意本次虚拟环境申请?'))
            		return;
            	$.ajax({ 
      					type : "get",//请求方式 
      					url : "applyenvcheck_success",//发送请求地址
      					data:{ 
      						applyid : applydataid
      					},
      					success :function() {
      					  window.open ("/healthcare/adminpanel/applyenvtable", target="_self");
       					} 
          			});
            };
            
            $('#cancle_button').click(function(){
            	//获取用户身份
            	$.ajax({ 
  					type : "get",//请求方式 
  					url : "applycheck_getauthority",//发送请求地址
  					data:{ 
  					},
  					success :function(curAuthority) {
  						if(curAuthority == 'ROLE_SU1')
  		            		curAuthority = '卒中中心';
  		            	if(curAuthority == 'ROLE_SU2')
  		            		curAuthority = '卒中防治办公室';
  						$('#rejectTitle').text('确认拒绝本次申请？'+'(当前审核单位：'+curAuthority+')');
   					} 
      			});
            });
            
            $('#reject_button').click(function () {
//            	var rejectReason = $('#rejectReason').val();
            	var rejectReason = encodeURI(encodeURI( $('#rejectReason').val() ));
            	var title = $('#rejectTitle').text();
            	if(title.indexOf('办公室') >= 0)
            		rejectReason += encodeURI(encodeURI('(国家卫生计生委脑卒中防治委员会办公室)' ));
            	else if(title.indexOf('中心') >= 0)
            		rejectReason += encodeURI(encodeURI('(中国卒中数据中心)'));
//            	console.log('njz : '+rejectReason);
            	$.ajax({ 
  					type : "get",//请求方式 
  					url : "applyenvcheck_reject",//发送请求地址
  					data:{ 
  						applyid : applydataid,
  						rejectReason : rejectReason
  					},
  					success :function() {
  					  window.open ("/healthcare/adminpanel/applyenvtable", target="_self");
   					} 
  				});
            });

        }
    };
}();