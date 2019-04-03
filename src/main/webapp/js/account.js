$(function() {
	$('.my-prize').on('click', function() {
		window.location.href = 'prize.html'
	})
	
	$('.my-address').on('click', function() {
		window.location.href = 'address.html'
	})
	
	$.ajax({
		type:"get",
		url:"https://www.xingyuanji.com/wxp/userInfo",
//url:"192.168.1.101:8080/wish_war_exploded/wxp/userInfo",
		xhrFields: {
			withCredentials: true
		}
	}).done(function(res) {
		if(res.result === 0) {
			$('header img').attr('src', res.data.avatarUrl)
			$('header .username').html(res.data.nickName)
		} else {
			pointOut('获取用户信息失败')
		}
	}).fail(function(res) {
		pointOut('服务器异常')
	})
})