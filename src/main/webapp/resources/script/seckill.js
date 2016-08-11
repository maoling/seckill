//存放主要交互逻辑js代码
// javascript 模块化
var seckill = {
	URL : {
		
	},
	validatePhone : function(phone){
		if(phone && phone.length == 11 && !isNaN(phone)){
			return true;
		}else{
			return false;
		}
	},
	detail : {
		init : function(params){
			var killPhone = $.cookie('killPhone');
			var startTime = params['startTime'];
			var endTime = params['endTime'];
			var seckillId = params['seckillId'];
			if(!seckill.validatePhone(killPhone)){
			    var killPhoneModal = $("#killPhoneModal");
			    killPhoneModal.modal({
			    	show:true,
			    	backdrop:'static',
			    	keyboard:false
			    });
			    $("#killPhoneBtn").click(function(){
			    	var inputPhone = $("#killPhoneKey").val();
			    	console.log('inputPhone='+inputPhone);
			    	if(seckill.validatePhone(inputPhone)){
			    		$.cookie('killPhone',inputPhone,{expires:7,path:'/seckill'});
			    		window.location.reload();
			    	}else{
			    		$('#killPhoneMessage').hide().html('<lable class="label label-danger">手机号错误！</lable>').show(500);
			    	}
			    });
			}
		}
	}				
}