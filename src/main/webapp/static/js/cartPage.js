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

function editQuantity(input, data){
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {

        if (this.readyState == 4 && this.status == 200) {
            input.parentElement().classList.remove("has-danger");
            showSmallNotification("Quantity updated successfully", "primary");
        }
        if (this.readyState == 4 && this.status !== 200) {
            showSmallNotification("Could not update quantity!", "danger");
            input.parentElement().classList.add("has-danger");
        }
    };
    xhttp.open("POST", "cart", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send(data);
}