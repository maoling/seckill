var seckill = {
	URL : {
		now : function(){
			return '/seckill/time/now';
		}
	},
	handleSeckillkill : function(){
		
	},
	validatePhone : function(phone){
		if(phone && phone.length == 11 && !isNaN(phone)){
			return true;
		}else{
			return false;
		}
	},
	countdown : function(seckillId,nowTime,startTime,endTime){
		var seckillBox = $('#seckill-box');
		if(nowTime > endTime){
			seckillBox.html('秒杀结束！');
		}else if(nowTime <　startTime){
			var killTime = new Date(startTime +　1000);
			seckillBox.countdown(killTime,function(event){
				var format = event.strftime('秒杀倒计时: %D天  %H时  %M分 %S秒');
				seckillBox.html(format);
				/*时间完成后回调事件*/
			}).on('finish.countdown',function(){
				seckill.handleSeckillkill();
			});
		}else{
			//执行秒杀逻辑
			seckill.handleSeckillkill();
		}
	},
	detail : {
		init : function(params){
			var killPhone = $.cookie('killPhone');
			
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
			    		$('#killPhoneMessage').hide().html('<lable class="label label-danger">手机号码错误！</lable>').show(500);
			    	}
			    });
			}
			var startTime = params['startTime'];
			var endTime = params['endTime'];
			var seckillId = params['seckillId'];
			//已经登录，计时交互
			$.get(seckill.URL.now(),{},function(result){
				if(result && result['success']){
					var nowTime = result['data'];
					seckill.countdown(seckillId,nowTime,startTime,endTime);
				}else{
					console.log('result:'+result);
				}
			});
			
			
		}
	}				
}