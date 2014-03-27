var name = window.location
document.session.name.value = name
var text = document.session.name.value
var ws = new WebSocket("ws://" + window.location.hostname + ":8887/");
var godownmix = new Howl({
	urls : [ 'sounds/godown.ogg' ]
})

ws.onopen = function() {

	if (name != null) {
		document.write("Connected to websocket server! <br>");
		ws.send("name:" + delineate(text));
		document.write("Sent data: name:" + delineate(text) + "<br>");
	}

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
