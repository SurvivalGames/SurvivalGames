var name = window.location;
document.session.name.value = name;
var text = document.session.name.value;
var ws = new WebSocket("ws://" + window.location.hostname + ":8887/");
var defaultVolume = 25;

$(function(){
	$("#jslider").slider({max: 50, min: 0, animate: "fast", value: defaultVolume, slide: function(event, ui) {
            $('#volumelabel').text("Volume: " + ui.value * 2 + "%");
            defaultVolume = ui.value;
        }});
});
var godownmix = new Howl({
	urls : [ 'sounds/godown.ogg' ]
})

ws.onopen = function() {

	if (name != null) {
		ws.send("name:" + delineate(text));
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
