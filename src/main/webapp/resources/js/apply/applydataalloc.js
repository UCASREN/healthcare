var applydataAlloc = function () {
	
    return {
    	
        //main function to initiate the module
        init: function () {
        	
        	  $("#logoutbutton").click(function(){
              	$("#logoutform").submit();
              });
        	  
            var applydataid = $.query.get('applydataid');

            if(applydataid != ""){
            	console.log('参数:'+applydataid);
            	options={ 
       					type : "get",//请求方式 
       					url : "getdocdatabyapplyid",//发送请求地址
       					dataType : "json", 
       					data:{ 
       						applyid : applydataid
       					}, 
       					success :function(data) {
       						//alert(data); 
       						console.log(applydataid+"   -------- 对应数据集   --------  "+data["applyData"]);
       						$('#upload_applyid').val(data["idApplydata"]);
       						fillShoppingCartFromDB(data["applyData"]);
       					} 
       				}
       			$.ajax(options);
            }else{
            	console.log('applydataid参数为空:'+applydataid)
            };
            
            function fillShoppingCartFromDB(shopdata){
            	var emptyshop = $('#emptyshoppingcart');
            	var shoptable = $('#shoptable');
            	var shoppanel = $("#shoppanel");
            	
            	if(shopdata == null){
            		shoptable.hide();
            		emptyshop.show();
            		shoppanel.show();
            	}else{
            		
            		var shop = shopdata.split(',');
            		for(var i=1; i<shop.length; i++){
            			var db_name = shop[i].split('_')[1];
            			var t_name = shop[i].split('_')[3];
            			var t_comment = shop[i].split('_')[4];
            			console.log(db_name+" : "+t_name+" : "+t_comment);
            			
            			var tr = $("<tr class='append'></tr>");
            			tr.append('<td style="width:10%;text-align:center;">'+db_name+'</td>');
            			tr.append('<td style="width:10%;text-align:center;">'+t_name+'</td>');
            			tr.append('<td style="width:10%;text-align:center;">'+t_comment+'</td>');
            			shoptable.append(tr);
            		}
            		emptyshop.hide();
            		shoptable.show();
            		shoppanel.show();
                }
            }
            
        }
    };
}();