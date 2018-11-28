function showPayment() {
    let paymentButtons = document.getElementsByClassName("pay");

    for (var i = 0; i < inputFields.length; i++) {
        let paymentButton = paymentButtons[i];

        paymentButton.addEventListener("change", function () {
            showSmallNotification("Payment successfull");
        });
    }
}