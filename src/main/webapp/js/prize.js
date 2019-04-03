var defAddress = []
var hasDef = false

function getAddress() {
	$.ajax({
		type: "get",
		url: "https://www.xingyuanji.com/wxp/address/getAddress",
		xhrFields: {
			withCredentials: true
		}
	}).done(function(res) {
		if(res.result === 0) {
			res.list.forEach(function(ele) {
				if(ele.def) {
					hasDef = true
					defAddress = ele
				}
			})
		} else {
			pointOut('获取地址信息失败')
		}
	}).fail(function(err) {
		pointOut('服务器异常')
	})
}

function surePrize(id, addressId) {
	$.ajax({
		type: "post",
		url: "https://www.xingyuanji.com/wxp/prize/cash",
		xhrFields: {
			withCredentials: true
		},
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify({
			id: id,
			addressId: addressId
		}),
		dataType: "json"
	}).done(function(res) {
		if(res.result === 0) {
			pointOut('兑换成功')
			
			window.location.reload();
		} else {
			pointOut('兑换失败')
		}
	}).fail(function(err) {
		pointOut('服务器异常')
	})
}

$(function() {
	getAddress()
	$('header i').on('click', function() {
        window.location.href = 'my.html'
	})

	$.ajax({
		type: "get",
		url: "https://www.xingyuanji.com/wxp/prize/getPrizeList",
		xhrFields: {
			withCredentials: true
		}
	}).done(function(res) {
		var prizeList = ''
		res.list.forEach(function(ele, index) {
			prizeList += '<li>' +
				'<img src="images/prize/' + ele.prizeId + '.jpg" alt="" />' +
				'<div>' +
				'<p>' + ele.name + '</p>' +
				'<button class="exchange' + (ele.status === 2 ? '' : ' active') + '" pid="' + ele.id + '" prizeId="' + ele.prizeId + '">兑换</button>' +
				'</div>' +
				'</li>'
		})
		$('section ul').append(prizeList)
	}).fail(function(err) {

	})

	$(document).on('click', '.exchange', function() {
		if($(this).hasClass('active')) {
//			if(hasDef) {
				var id = this.getAttribute("pid");
				var prizeId = parseInt($(this).attr('prizeId'))
				$('img').stop().animate({
                	opacity: 1
            	}, 500, function() {
                setTimeout(function() {
                    $('img').stop().animate({
                        opacity: 0
                    	}, 100)
                    $('body img').remove()
                	}, 2000)
            	})
//				console.log("id");
//				console.log(defAddress);
				if(prizeId === 20 || 21 || 22) {
					surePrize(id, defAddress.id)
				} else {
					confirmOut('奖品将发送至默认地址', function() {
						surePrize(id, defAddress.id)
					})
				}
//			} else {
//				confirmOut('您尚未添加默认地址，赶快去设置吧', function() {
//					window.location.href = 'address.html'
//				})
//			}
		}
	})
})