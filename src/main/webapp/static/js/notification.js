function showSmallNotification(msg, color) {
    let icon = "";
    if (color == "primary") {
        icon = "info"
    } else if (color == "success") {
        icon = "done_outline"
    } else if (color == "danger") {
        icon = "error_outline"
    }

    $.notify({
        icon: icon,
        message: msg
    }, {
        type: color,
        timer: 1000,
        placement: {
            from: 'top',
            align: 'right'
        }
    });
}