function checkPassword() {
    var pass1 = document.getElementById("pass1").value;
    var pass2 = document.getElementById("pass2").value;
    let submitButton = document.getElementById('submit');
    if (pass1 !== pass2) {
        submitButton.style.display = "none";
    }
    else {
        submitButton.style.display = "block";
    }
}

