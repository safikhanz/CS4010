function loadProblemsAtPage(u) {
	var pgnum = parseInt(document.getElementById("crtpg").value);
	
	if (u == 0) {
		pgnum--;
	} else {
		pgnum++;
	}
	
	document.location.href = "listmath?pg=" + pgnum;
}

function goToProblemsAtPage() {
    var pgn = document.getElementById("probpage").value;
	
	if (pgn.length == 0) {
		pgn = document.getElementById("probpage2").value;
	}
	
	document.location.href = "listmath?pg=" + pgn;
}

function loadProblemsInAss(v) {
	document.location.href = "listmath?as=" + v;
}

function validation() {
	   var x = document.forms["problemform"]["problem"].value;
	    if (x == null || x == "") {
	        alert("username cannot be empty..!!");
	        return false;
	    }
	}