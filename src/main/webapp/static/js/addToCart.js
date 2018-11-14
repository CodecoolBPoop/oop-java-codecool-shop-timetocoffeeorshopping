let buttons = document.getElementsByClassName("addToCart");
console.log("buttons:" + buttons.length);
for (var i = 0; i < buttons.length; i++) {
    let button = buttons[i];

    console.log("inputFieldId " + button.id);

    button.addEventListener("click", function () {
        console.log("Sending add request");
        ajaxPost(button.id, "add", "command=add&product=" + button.id, "Item added to your cart!")
    });
}


function ajaxPost(productId, action, data, msg) {
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            showSmallNotification(msg, "success");
            console.log(action);
            if (action === "remove") {
                document.getElementById("tr_" + productId).remove();
            }
            updateNavBarCounter(this.response);
        }
        if (this.readyState == 4 && this.status !== 200) {
            showSmallNotification("Could not communicate with server! Status: " + this.status, "danger");
        }
    };
    xhttp.open("POST", "/", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send(data);
}

function updateNavBarCounter(response){
    let num = response.split("value=\"")[1].split("\">")[0];
    let counterElement = document.getElementById("navbarCartItemCounter");
    counterElement.innerText = "Items in cart: " + num;
}