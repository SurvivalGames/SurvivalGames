var name = window.location;
window.document.session.name.value = name;
var text = document.session.name.value;
var ws = new WebSocket("ws://" + delineate2(text) + ":8887/");
var name = delineate(text);
var defaultVolume = 25;
var music = null;
SC.initialize({
    client_id: "bb067fb8593f7d0acbc5af49998ddf8c", //This is the public key...  Don't get any ideas heh ;)
  });

ws.onopen = function() {
	ws.send("name:" + delineate(text));
    $('#player').html(delineate(text));
};

ws.onmessage = function(evt) {
    var split = evt.data.split(':');
	if (split[0] == 'nullPlayer'){
	    window.location.replace("http://sg.q64.co/sg/nullobject.html");
	}
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
	if (split[0] == 'arena'){
	    $('#arena').html(split[1]);
	}
	if (split[0] == 'state'){
	    $('#state').html(split[1]);
	}
	if (split[0] == 'max'){
	    $('#max').html(split[1]);
	}
	if (split[0] == 'min'){
	    $('#min').html(split[1]);
	}
	if (split[0] == 'players'){
	    $('#players').html(split[1]);
	}
	if (split[0] == 'alive'){
	    $('#alive').html(split[1]);
	}
	if (split[0] == 'dead'){
	    $('#dead').html(split[1]);
	}
	if (split[0] == 'specs'){
	    $('#specs').html(split[1]);
	}
	if (split[0] == 'music'){
		SC.stream("/tracks/" + split[1], {
            autoPlay: false
        }, function (sound) {
            music = sound;
            music.play();
        });
	}
	if (evt.data == "stop") {
	    if(music != null) {
		    music.stop();
		}
	}
	if(split[0] == 'sound') {
	    var sound = new Howl({
	    	urls : [ 'sounds/' + split[1] + '.ogg' ]
	    }).play();
	}
};

ws.onclose = function() {
};

ws.onerror = function(err) {
	window.location.replace("http://sg.q64.co/sg/serverdown.html");
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
