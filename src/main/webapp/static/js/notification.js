function showSmallNotification(msg, color) {
    let icon = "done_outline";
    if (color == "danger") {
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