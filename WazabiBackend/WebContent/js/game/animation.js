var hasClick = 0;
function myEnterFunction(){
	if(hasClick<=0)
		$(".wazabi-dice").css("animation","spin 8s infinite linear");
}

function myOutFunction(){
	$(".wazabi-dice").css("animation","none");
}

function myClickFunction(){
	$("dices-list-view").css("animation","none");  
	$("wazabi-dice").css("animation","none"); 
	if(hasClick<=0)
		$(".wazabi-dice").css("animation","throw 0.5s linear");   
	hasClick=1;
}

function mySecondClickFunction(){
	hasClick=0;
}
