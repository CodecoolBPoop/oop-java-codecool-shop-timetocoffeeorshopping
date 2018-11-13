let inputFields = document.getElementsByClassName("quantity");

console.log("inputs: " + inputFields.length);

for (var i = 0; i < inputFields.length; i++) {
    let inputField = inputFields[i];

    console.log("inputFieldId " + inputField.id);

    inputField.addEventListener("change", function(){
        let input = inputField;
        console.log("Sending edit request");
        editQuantity(input, "command=editQuantity&product=" + input.id + "&newQuantity=" + inputField.value)
    });
}

function removeFromCart(productId){
    let data = "command=removeProduct&product=" + input.id;
    ajaxPost(document.getElementById(productId), data)
}

function editQuantity(input, data){
    ajaxPost(input, data, "Quantity updated successfully!");
}

function ajaxPost(input, data, msg){
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {

        if (this.readyState == 4 && this.status == 200) {
            input.parentElement().classList.remove("has-danger");
            showSmallNotification(msg, "primary");
        }
        if (this.readyState == 4 && this.status !== 200) {
            showSmallNotification("Could not communicate with server!", "danger");
            input.parentElement().classList.add("has-danger");
        }
    };
    xhttp.open("POST", "cart", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send(data);
}
