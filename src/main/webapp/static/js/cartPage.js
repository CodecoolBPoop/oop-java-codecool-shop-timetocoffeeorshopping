let inputFields = document.getElementsByClassName("quantity");

console.log("inputs: " + inputFields.length);

for (var i = 0; i < inputFields.length; i++) {
    let inputField = inputFields[i];

    console.log("inputFieldId " + inputField.id);

    inputField.addEventListener("change", function () {
        let input = inputField;
        console.log("Sending edit request");
        editQuantity(input.id, input.value, "command=editQuantity&product=" + input.id + "&newQuantity=" + inputField.value)
    });
}

function removeFromCart(productId) {
    console.log("REMOVE");
    let data = "command=removeProduct&product=" + productId;
    ajaxPost(productId, "remove", data, "Deleted successfully!")
}

function editQuantity(productId, value, data) {
    if (parseInt(value) < 1) {
        removeFromCart(productId.split("_")[1])
    } else {
        ajaxPost(productId, "edit", data, "Quantity updated successfully!");
    }
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
    xhttp.open("POST", "cart", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send(data);
}

function updateNavBarCounter(response){
    let num = response.split("value=\"")[1].split("\">")[0];
    let counterElement = document.getElementById("navbarCartItemCounter");
    counterElement.innerText = "Items in cart: " + num;
}