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
	if (evt.data == "godown") {
		godownmix.play();
		return;
	}
	if (evt.data == "stop") {
		godownmix.stop();
	}
	var sound = new Howl({
		urls : [ 'sounds/' + evt.data + '.ogg' ]
	}).play();
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