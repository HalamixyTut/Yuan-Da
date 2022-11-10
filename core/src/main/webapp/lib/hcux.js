/**
 * 保存成功后提示
 *
 */
Hap.showToast = function (options) {
    var opts = $.extend({
        "closeButton": true,
        "debug": false,
        "positionClass": "toast-bottom-right",
        "onclick": null,
        "showDuration": "1000",
        "hideDuration": "1000",
        "timeOut": "3000",
        "extendedTimeOut": "3000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    }, options || {});

    var toastr = top.toastr;
    if (toastr && options.type) {
        var op = {
            'success': toastr.success,
            'info': toastr.info,
            'warning': toastr.warning,
            'error': toastr.error
        };
        op[options.type](options.message, options.title, opts);
    }
};