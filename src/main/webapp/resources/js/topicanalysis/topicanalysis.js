var getInhospitalNum = function(timeType){
	options1={ 
			type : "get",//请求方式 
			url : "/healthcare/getInhospitalNum",//发送请求地址
			dataType : "json", 
			data:{ 
				timeType : timeType
			}, 
			success :function(data) {
				var inhospital_num = data[0];
				var inhospital_rate = data[1];
//				alert(data);
				$('#inHospital_num').html(inhospital_num);
				$('#inHospital_rate').html(inhospital_rate)
			} 
     }
     $.ajax(options1);
}

var getInhospitalAverageDays = function(timeType){
	options2={ 
			type : "get",//请求方式 
			url : "/healthcare/getInhospitalAverageDays",//发送请求地址
			dataType : "json", 
			data:{ 
				timeType : timeType
			}, 
			success :function(data) {
				var inhospitalAverageDays_num = data[0];
				var inhospitalAverageDays_rate = data[1];
				alert(data);
				$('#inhospitalAverageDays_num').html(inhospitalAverageDays_num);
				$('#inhospitalAverageDays_rate').html(inhospitalAverageDays_rate)
			} 
     }
     $.ajax(options2);
}

var getTreatmentAverageCost = function(timeType){
	options3={ 
			type : "get",//请求方式 
			url : "/healthcare/getTreatmentAverageCost",//发送请求地址
			dataType : "json", 
			data:{ 
				timeType : timeType
			}, 
			success :function(data) {
				var treatmentAverageCost_num = data[0];
				var treatmentAverageCost_rate = data[1];
				alert(data);
				$('#treatmentAverageCost_num').html(treatmentAverageCost_num);
				$('#treatmentAverageCost_rate').html(treatmentAverageCost_rate)
			} 
     }
     $.ajax(options3);
}

var home_time_opt = $('#home_time_opt').val()
getInhospitalNum(home_time_opt);
getInhospitalAverageDays(home_time_opt);
getTreatmentAverageCost(home_time_opt);

$('#home_time_opt').change(function(){
	var home_time_opt = $('#home_time_opt').val()
	getInhospitalNum(home_time_opt);
	getInhospitalAverageDays(home_time_opt);
	getTreatmentAverageCost(home_time_opt);
});

