let mailButton = document.getElementById("mailButton");
console.log("navbar" + mailButton);
mailButton.addEventListener("click", function () {
    console.log("navbarEmailButton");
    showSmallNotification("This is an info notification", "primary");
    showSmallNotification("This is a success notification", "success");
    showSmallNotification("This is an error notification", "danger");
});
