let inputFields = document.getElementsByClassName("quantity");

console.log("inputs: " + inputFields.length);

for (var i = 0; i < inputFields.length; i++) {
    let inputField = inputFields[i];

    console.log("inputFieldId " + inputField.id);

    inputField.addEventListener("change", function () {
        let input = inputField;
        console.log("Sending edit request");
        editQuantity(input, "command=editQuantity&product=" + input.id + "&newQuantity=" + inputField.value)
    });
}

function removeFromCart(productId) {
    let data = "command=removeProduct&product=" + productId;
    ajaxPost(productId, "remove", data, "Deleted successfully!")
}

function editQuantity(input, data) {
    ajaxPost(input, "edit", data, "Quantity updated successfully!");
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
    xhttp.open("POST", "cart", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send(data);
}
