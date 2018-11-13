let buttons = document.getElementsByClassName("addToCart");

for (var i = 0; i < buttons.length; i++) {
    let button = buttons[i];

    console.log("inputFieldId " + button.id);

    button.addEventListener("change", function () {
        console.log("Sending add request");
        editQuantity(input, "command=add&product=" + input.id)
    });
}


function ajaxPost(productId, action, data, msg) {
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            showSmallNotification(msg, "primary");
            console.log(action);
            if (action === "remove") {
                document.getElementById("tr_" + productId).remove();
            }
        }
        if (this.readyState == 4 && this.status !== 200) {
            showSmallNotification("Could not communicate with server! Status: " + this.status, "danger");
        }
    };
    xhttp.open("POST", "/", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send(data);
}
