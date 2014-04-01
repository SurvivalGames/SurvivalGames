var name = window.location;
window.document.session.name.value = name;
var text = document.session.name.value;
var ws = new WebSocket("ws://" + delineate2(text) + ":8887/");
var name = delineate(text);
var defaultVolume = 25;

var godownmix = new Howl({
	urls : [ 'sounds/godown.ogg' ]
})


ws.onopen = function() {
	ws.send("name:" + delineate(text));
    $('#player').html(delineate(text));
};

ws.onmessage = function(evt) {
    var split = evt.data.split(':');
	if (split[0] == 'points'){
	    $('#points').html(split[1]);
	}
	if (split[0] == 'kills'){
	    $('#kills').html(split[1]);
	}
	if (split[0] == 'wins'){
	    $('#wins').html(split[1]);
	}
	if (split[0] == 'rank'){
	    $('#rank').html(split[1]);
	}
	if (split[0] == 'music'){
	if (split[1] == 'godown') {
		godownmix.play();
		return;
	}
	}
	if (evt.data == "stop") {
		godownmix.stop();
	}
	if(split[0] == 'sound') {
	    var sound = new Howl({
	    	urls : [ 'sounds/' + split[1] + '.ogg' ]
	    }).play();
	}
};

ws.onclose = function() {
	alert("Closed!");
};

ws.onerror = function(err) {
	alert("Error: " + err);
};

function delineate(str) {
	theleft = str.indexOf("=") + 1;
	theright = str.lastIndexOf("&");
	return (str.substring(theleft, theright));
}

function delineate2(str)
{
point = str.lastIndexOf("=");
return(str.substring(point+1,str.length));
}