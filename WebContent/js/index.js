// 抽奖代码
$(function() {
	
	$("#ttMapId_").val(getParameter("mapId"));
	
	$("#sub_").click(function() {
		/*setTimeout(function() {
			$('#hand_div').addClass("_hand_div_1");
		}, 800);*/
		
		var mapId = $("#ttMapId_").val();
		var phone = document.getElementById("phone_").value;
		if (mapId.length == 0) {
			dialogsContent(3, '数据丢失，请刷新后再试');
		} else if (phone.length != 11 || !isNumber(phone)) {
			dialogsContent(3, '请您输入正确的手机号码');
		} else {
			$.ajax({
				type : "post",
				dataType : "json",
				url : getBasePath() + "/addrcd",
				data : {
					"mapId" : mapId,
					"phone" : phone
				},
				async : false,
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					/*dialogsContent(3, '网络开小差，请稍后再试<br>如需帮助，请拔打4006896628.');*/
                    alert(XMLHttpRequest.status);
                    alert(XMLHttpRequest.readyState);
                    alert(textStatus);
				},
				beforeSend : function() {
				},
				success : function(msg) {
					dialogsContent(msg.code, msg.result);
				}
			})
		}
	});

	function dialogsContent(code, logs) {
		if (code == 0) {
			$("#dialogs-top").attr('src', 'img/dialogs_bd_1_cg.jpg');
			/*$('.texts').html('您推荐的号码已记录，工作人员会在一个工作日内审核号码有效性并为您派发红包。');*/
		} else if (code == -1) {
			$("#dialogs-top").attr('src', 'img/dialogs_bd_1_cf.jpg');
			/*$('.texts').html('此号码已有推荐人喽。请重新确认您推荐的手机号，<br>如有疑问可联系客服4006896628');*/
		} else if (code == 3) {
			$("#dialogs-top").attr('src', 'img/dialogs_bd_1_qt.jpg');
		} else {
			$("#dialogs-top").attr('src', 'img/dialogs_bd_1_qt.jpg');
			/*$('.texts').html('网络开小差，请稍后再试，<br>如需帮助，请拔打4006896628.');*/
		}
		$('.texts').html(logs);
		
		$(".zz").show();
		$(".jl-tk").show();
		$(".ok-img").on('click', function() {
			$(".zz").hide();
			$(".jl-tk").hide();
		});
	}

});

function getParameter(param) {
	var query = window.location.search;
	var iLen = param.length;
	var iStart = query.indexOf(param);
	if (iStart == -1) {
		return ""
	}
	iStart += iLen + 1;
	var iEnd = query.indexOf("&", iStart);
	if (iEnd == -1) {
		return query.substring(iStart)
	}
	return query.substring(iStart, iEnd)
}
function getBasePath() {
	var obj = window.location;
	var contextPath = obj.pathname.split("/")[1];
	/* var basePath = obj.protocol + "//" + obj.host; */
	var basePath = obj.protocol + "//" + obj.host + "/" + contextPath;
	return basePath
}

function isNumber(strx) {
	var numberStr = "1234567890";
	for (var i = 0; i < strx.length; i++) {
		if (numberStr.indexOf(strx.charAt(i)) == -1) {
			return false;
		}
	}
	return true;
}
