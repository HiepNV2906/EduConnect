
function loadHeaderWraper() {
    if ($.cookie('role') == 'ADMIN' || $.cookie('role') == 'MANAGER') {
        getAccountAdmin($.cookie('id'), createHeaderWrapHTML);
    } else if ($.cookie('role') == 'GIASU') {
        getAccountTutor($.cookie('id'), createHeaderWrapHTML);
    } else if ($.cookie('role') == 'HOCVIEN') {
        getAccountStudent($.cookie('id'), createHeaderWrapHTML);
    }
};

$('#nutdangxuat').click(function () {
    if (confirm("Đăng xuất tài khoản?")) {
        logout();
    }
})

$('#nutdoimatkhau').click(function () {
    changePassWord();
})

function static(type, callback) {
    var token = $.cookie('token');
    var from = 'from=' + $('.from input').val();
    var to = 'to=' + $('.to input').val();
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "GET",
        url: "http://localhost:8080/api/thongke/" + type + '?' + from + '&' + to,
        success: function (response) {
            console.log(response);
            var data = response.data;
            callback(data);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function payForDebt(jsonString, callback) {
    var token = $.cookie('token');

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "POST",
        url: "http://localhost:8080/api/vnpay",
        data: jsonString,
        success: function (response) {
            console.log(response);
            // alert(response.message);
            callback(response.data);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function applyForClass(giasuid, lopid, callback) {
    var token = $.cookie('token');

    var infoAcc = {
        "giasuid": giasuid,
        "lopid": lopid
    }

    var jsonString = JSON.stringify(infoAcc);

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "POST",
        url: "http://localhost:8080/api/ungtuyen",
        data: jsonString,
        success: function (response) {
            console.log(response);
            alert(response.message);
            callback();
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function selectTutorForClass(id, callback) {
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "PUT",
        url: "http://localhost:8080/api/ungtuyen/chongiasu/" + id,
        success: function (response) {
            console.log(response);
            callback();
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function getDetailTransaction(id, callback) {
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "GET",
        url: "http://localhost:8080/api/thanhtoan/" + id,
        success: function (response) {
            console.log(response);
            var data = response.data;
            callback(data);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function getDetailApply(id, callback) {
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "GET",
        url: "http://localhost:8080/api/ungtuyen/" + id,
        success: function (response) {
            console.log(response);
            var data = response.data;
            callback(data);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function getDetailClass(id, callback) {
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "GET",
        url: "http://localhost:8080/api/lop/" + id,
        success: function (response) {
            console.log(response);
            var data = response.data;
            callback(data);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function updateDetailClass(id, formData) {
    var token = $.cookie('token');

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "PUT",
        url: "http://localhost:8080/api/lop/" + id,
        data: formData,
        success: function (response) {
            console.log(response);
            alert(response.message);
            location.reload();
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function getAccountTutor(id, callback) {
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "GET",
        url: "http://localhost:8080/api/giasu/" + id,
        success: function (response) {
            console.log(response);
            var data = response.data;
            callback(data);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function updateAccountTutor(id, formData) {
    var token = $.cookie('token');

    $.ajax({
        url: "http://localhost:8080/api/giasu/" + id,
        type: "PUT",
        data: formData,
        enctype: 'multipart/form-data',
        headers: {
            'Authorization': token ? token : ''
        },
        contentType: false,
        processData: false,
        success: function (response) {
            console.log(response);
            alert(response.message);
            location.reload();
        },
        error: function (xhr, status, error) {
            alert(error);
        }
    });
}

function getAccountStudent(id, callback) {
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "GET",
        url: "http://localhost:8080/api/hocvien/" + id,
        success: function (response) {
            console.log(response);
            var data = response.data;
            callback(data);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function updateAccountStudent(id, formData) {
    var token = $.cookie('token');

    $.ajax({
        url: "http://localhost:8080/api/hocvien/" + id,
        type: "PUT",
        data: formData,
        enctype: 'multipart/form-data',
        headers: {
            'Authorization': token ? token : ''
        },
        contentType: false,
        processData: false,
        success: function (response) {
            console.log(response);
            alert(response.message);
            location.reload();
        },
        error: function (xhr, status, error) {
            alert(error);
        }
    });
}

function getAccountAdmin(id, callback) {
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "GET",
        url: "http://localhost:8080/api/user/" + id,
        success: function (response) {
            console.log(response);
            var data = response.data;
            callback(data);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function updateAccountAdmin(id, formData) {
    var token = $.cookie('token');

    $.ajax({
        url: "http://localhost:8080/api/user/" + id,
        type: "PUT",
        data: formData,
        enctype: 'multipart/form-data',
        headers: {
            'Authorization': token ? token : ''
        },
        contentType: false,
        processData: false,
        success: function (response) {
            console.log(response);
            alert(response.message);
            location.reload();
        },
        error: function (xhr, status, error) {
            alert(error);
        }
    });
}

function getListSubject(cssSelector) {
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "GET",
        url: "http://localhost:8080/api/chude",
        success: function (response) {
            console.log(response);
            listItem = response.data;
            totalPages = Math.ceil(listItem.length / sizeOfPage);
            renderData(listItem, totalPages, 1, sizeOfPage, cssSelector);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function getAPIListInvitationByStudent(cssSelector) {
    var token = $.cookie('token');
    var id = $.cookie('id');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "GET",
        url: "http://localhost:8080/api/loimoi/hocvien/" + id,
        success: function (response) {
            console.log(response);
            listItem = response.data;
            totalPages = Math.ceil(listItem.length / sizeOfPage);
            renderData(listItem, totalPages, 1, sizeOfPage, cssSelector);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function getAPIListInvitationByTutor(cssSelector) {
    var token = $.cookie('token');
    var id = $.cookie('id');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "GET",
        url: "http://localhost:8080/api/loimoi/giasu/" + id,
        success: function (response) {
            console.log(response);
            listItem = response.data;
            totalPages = Math.ceil(listItem.length / sizeOfPage);
            renderData(listItem, totalPages, 1, sizeOfPage, cssSelector);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function getAPIListTransactionByGiaSu(id, cssSelector) {
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "GET",
        url: "http://localhost:8080/api/thanhtoan/giasu/" + id,
        success: function (response) {
            console.log(response);
            listItem = response.data;
            totalPages = Math.ceil(listItem.length / sizeOfPage);
            renderData(listItem, totalPages, 1, sizeOfPage, cssSelector);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function getAPIListTransaction(cssSelector) {
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "GET",
        url: "http://localhost:8080/api/thanhtoan",
        success: function (response) {
            console.log(response);
            listItem = response.data;
            totalPages = Math.ceil(listItem.length / sizeOfPage);
            renderData(listItem, totalPages, 1, sizeOfPage, cssSelector);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function getAPIListNotificationByUser(cssSelector) {
    var token = $.cookie('token');
    var id = $.cookie('id');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "GET",
        url: "http://localhost:8080/api/thongbao/user/" + id,
        success: function (response) {
            console.log(response);
            listItem = response.data;
            totalPages = Math.ceil(listItem.length / sizeOfPage);
            renderData(listItem, totalPages, 1, sizeOfPage, cssSelector);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function getAPIListApplyByDebt(status, cssSelector) {
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "GET",
        url: "http://localhost:8080/api/ungtuyen/trangthaicongno?status=" + status,
        success: function (response) {
            console.log(response);
            listItem = response.data;
            totalPages = Math.ceil(listItem.length / sizeOfPage);
            renderData(listItem, totalPages, 1, sizeOfPage, cssSelector);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function getAPIListApplyByDebtAndTutor(tutorid, status, cssSelector) {
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "GET",
        url: "http://localhost:8080/api/ungtuyen/congnogiasu?giasuid=" + tutorid + "&status=" + status,
        success: function (response) {
            console.log(response);
            listItem = response.data;
            totalPages = Math.ceil(listItem.length / sizeOfPage);
            renderData(listItem, totalPages, 1, sizeOfPage, cssSelector);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

// function getAPIListTutorApplyByClass(id, cssSelector) {
//     var token = $.cookie('token');
//     $.ajax({
//         headers: {
//             'Accept': 'application/json',
//             'Content-Type': 'application/json',
//             'Authorization': token ? token : ''
//         },
//         method: "GET",
//         url: "http://localhost:8080/api/ungtuyen/lop/giasu/" + id,
//         success: function (response) {
//             console.log(response);
//             listItem = response.data;
//             totalPages = Math.ceil(listItem.length / sizeOfPage);
//             renderData(listItem, totalPages, 1, sizeOfPage, cssSelector);
//         },
//         error: function (xhr, status, error) {
//             // Xử lý lỗi
//             console.log(error);
//             alert("Có lỗi xảy ra!!!");
//         }
//     });
// }

function getAPIListApplyByTutor(id, cssSelector) {
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "GET",
        url: "http://localhost:8080/api/ungtuyen/giasu/" + id,
        success: function (response) {
            console.log(response);
            listItem = response.data;
            totalPages = Math.ceil(listItem.length / sizeOfPage);
            renderData(listItem, totalPages, 1, sizeOfPage, cssSelector);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function getAPIListApplyByClass(id, cssSelector) {
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "GET",
        url: "http://localhost:8080/api/ungtuyen/lop/" + id,
        success: function (response) {
            console.log(response);
            listItem = response.data;
            totalPages = Math.ceil(listItem.length / sizeOfPage);
            renderData(listItem, totalPages, 1, sizeOfPage, cssSelector);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function getAPIListTutorByStatus(status, cssSelector) {
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "GET",
        url: "http://localhost:8080/api/giasu/trangthai?status=" + status,
        success: function (response) {
            console.log(response);
            listItem = response.data;
            totalPages = Math.ceil(listItem.length / sizeOfPage);
            renderData(listItem, totalPages, 1, sizeOfPage, cssSelector);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function getAPIListStudentByStatus(status, cssSelector) {
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "GET",
        url: "http://localhost:8080/api/hocvien/trangthai?status=" + status,
        success: function (response) {
            console.log(response);
            listItem = response.data;
            totalPages = Math.ceil(listItem.length / sizeOfPage);
            renderData(listItem, totalPages, 1, sizeOfPage, cssSelector);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function getAPIListClassByStatus(status, cssSelector) {
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "GET",
        url: "http://localhost:8080/api/lop/trangthai?status=" + status,
        success: function (response) {
            console.log(response);
            listItem = response.data;
            totalPages = Math.ceil(listItem.length / sizeOfPage);
            renderData(listItem, totalPages, 1, sizeOfPage, cssSelector);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function getAPIListClassByGiaSuAndStatusApply(status, cssSelector) {
    var id = $.cookie('id');
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "GET",
        url: "http://localhost:8080/api/lop/giasu?giasuid=" + id + "&status=" + status,
        success: function (response) {
            console.log(response);
            listItem = response.data;
            totalPages = Math.ceil(listItem.length / sizeOfPage);
            renderData(listItem, totalPages, 1, sizeOfPage, cssSelector);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function getAPIListClassByHocVienAndStatus(status, cssSelector) {
    var id = $.cookie('id');
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "GET",
        url: "http://localhost:8080/api/lop/hocvien?hocvienid=" + id + "&status=" + status,
        success: function (response) {
            console.log(response);
            listItem = response.data;
            totalPages = Math.ceil(listItem.length / sizeOfPage);
            renderData(listItem, totalPages, 1, sizeOfPage, cssSelector);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function deleteAApplication(id, callback) {
    if (!confirm("Bạn muốn huỷ ứng tuyển?")) {
        return;
    }
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "DELETE",
        url: "http://localhost:8080/api/ungtuyen/" + id,
        success: function (response) {
            console.log(response);
            callback();
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function deleteAInvitation(id, callback) {
    if (!confirm("Bạn muốn huỷ lời mời?")) {
        return;
    }
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "DELETE",
        url: "http://localhost:8080/api/loimoi/" + id,
        success: function (response) {
            console.log(response);
            callback();
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function deleteANotification(id, callback) {
    if (!confirm("Bạn muốn xoá?")) {
        return;
    }
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "DELETE",
        url: "http://localhost:8080/api/thongbao/" + id,
        success: function (response) {
            console.log(response);
            callback();
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function deleteAStudent(id, callback) {
    if (!confirm("Bạn muốn xoá?")) {
        return;
    }
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "DELETE",
        url: "http://localhost:8080/api/hocvien/" + id,
        success: function (response) {
            console.log(response);
            callback();
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function deleteATutor(id, callback) {
    if (!confirm("Bạn muốn xoá?")) {
        return;
    }
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "DELETE",
        url: "http://localhost:8080/api/giasu/" + id,
        success: function (response) {
            console.log(response);
            callback();
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function deleteAClass(id, callback) {
    if (!confirm("Bạn muốn xoá?")) {
        return;
    }
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "DELETE",
        url: "http://localhost:8080/api/lop/" + id,
        success: function (response) {
            console.log(response);
            callback();
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function addASubject(formData) {
    var token = $.cookie('token');

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "POST",
        url: "http://localhost:8080/api/chude",
        data: formData,
        success: function (response) {
            console.log(response);
            alert(response.message);
            location.reload();
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function deleteASubject(id, callback) {
    if (!confirm("Bạn muốn xoá?")) {
        return;
    }
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "DELETE",
        url: "http://localhost:8080/api/chude/" + id,
        success: function (response) {
            console.log(response);
            callback();
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function createHeaderWrapHTML(acc) {
    var dsthongbao = acc.dsthongbao;
    dsthongbao.sort(function (a, b) {
        var dateA = new Date(a.ngay).getTime();
        var dateB = new Date(b.ngay).getTime();
        if (dateA < dateB) {
            return 1;
        }
        if (dateA > dateB) {
            return -1;
        }
        return 0;
    })
    var countnew = 0;
    for (let i = 0; i < dsthongbao.length; i++) {
        if (dsthongbao[i].trangthaithongbao == 'CHUAXEM') {
            countnew += 1;
        }
    }
    var thongbaohtml = `<div class="mess__title">
                            <p>Bạn có ${countnew} thông báo mới</p>
                        </div>`
    for (let i = 0; i < (dsthongbao.length > 1 ? 2 : dsthongbao.length); i++) {
        thongbaohtml += `<a href="${dsthongbao[i].link ? dsthongbao[i].link : ''}" class="mess__item">
                            <div class="content">
                                <h6>${dsthongbao[i].tieude}</h6>
                                <p>${dsthongbao[i].noidung}</p>
                                <span class="time">${moment(dsthongbao[i].ngay).format("DD-MM-YYYY HH:mm:ss")}</span>
                            </div>
                        </a>`
    }
    var role = $.cookie('role');
    if (role == 'ADMIN') {
        thongbaohtml += `<div class="mess__footer">
                            <a href="E_listNotificationA.html">Xem tất cả</a>
                        </div>`
    } else if (role == 'GIASU') {
        thongbaohtml += `<div class="mess__footer">
                            <a href="E_listNotificationT.html">Xem tất cả</a>
                        </div>`
    } else {
        thongbaohtml += `<div class="mess__footer">
                            <a href="E_listNotificationS.html">Xem tất cả</a>
                        </div>`
    }
    $('.mess-dropdown').html(thongbaohtml);
    $('.noti__item .quantity').text(countnew);

    var srcAvata = acc.avata ? 'http://localhost:8080/uploads/images/' + acc.avata : '../../img/avatadefault.png';
    $('.account-item .image img').attr("src", srcAvata);
    $('.account-item .content a').text(acc.hoten);
    $('.account-dropdown .image img').attr("src", srcAvata);
    $('.account-dropdown .content a').text(acc.hoten);
    $('.account-dropdown .content .email').text(acc.email);

    if (acc.trangthai) {
        var parentElement = $('.zmdi-account').parent();
        var status = ``;
        if (acc.trangthai == 'DAPHEDUYET') {
            status = `<span class="badge badge-success account-status ml-1">Đã phê duyệt</span>`
        } else if (acc.trangthai == 'CHUAPHEDUYET') {
            status = `<span class="badge badge-warning account-status ml-1">Chờ phê duyệt</span>`
        } else {
            status = `<span class="badge badge-danger account-status ml-1">Đình chỉ</span>`
        }
        parentElement.append(status);
    }
}

function handlePagenable(currentpage, cssSelector) {
    renderData(listItem, totalPages, currentpage, sizeOfPage, cssSelector);
}

function renderData(listdata, totalPages, currentPage, sizeOfPage, cssSelector) {
    var start = (currentPage - 1) * sizeOfPage;
    var end = currentPage * sizeOfPage;
    if (listdata.length < end) {
        end = listdata.length;
    }
    var classesHTML = createListDataHTML(listdata, start, end);
    var pagesHTML = createPageNavHTML(totalPages, currentPage, cssSelector);
    $(cssSelector).html(classesHTML);
    $(".pagination").html(pagesHTML);
}

function createListPage(totalPages, pageNumber) {
    var listPageNum = [];
    if (totalPages <= 5) {
        for (let i = 1; i <= totalPages; i++) {
            listPageNum.push(i);
        }
        return listPageNum;
    }
    if (pageNumber <= 3)
        return [1, 2, 3, 4, 5];
    if (pageNumber >= totalPages - 2)
        return [totalPages - 4, totalPages - 3, totalPages - 2, totalPages - 1, totalPages];
    return [pageNumber - 2, pageNumber - 1, pageNumber, pageNumber + 1, pageNumber + 2];
}

function createPageNavHTML(totalPages, pageNumber, cssSelector) {
    var pagenav = ``;
    var prebtn = `<li class="page-item">
                        <div class="page-link" aria-label="Previous" 
                        onClick = "handlePagenable(${pageNumber - 1}, '${cssSelector}')">
                            <span aria-hidden="true">&laquo;</span>
                            <span class="sr-only">Previous</span>
                        </div>
                    </li>`;
    var nextbtn = `<li class="page-item">
                        <div class="page-link" aria-label="Next" 
                        onClick = "handlePagenable(${pageNumber + 1}, '${cssSelector}')">
                            <span aria-hidden="true">&raquo;</span>
                            <span class="sr-only">Next</span>
                        </div>
                    </li>`;
    var listPageNum = createListPage(totalPages, pageNumber);
    for (let i = 0; i < listPageNum.length; i++) {
        if (listPageNum[i] == pageNumber) {
            pagenav += `<li class="page-item"><div class="page-link page_selected">${listPageNum[i]}</div></li>`
        } else {
            pagenav += `<li class="page-item"><div class="page-link" 
            onClick = "handlePagenable(${listPageNum[i]}, '${cssSelector}')">${listPageNum[i]}</div></li>`
        }
    }
    if (totalPages > 5) {
        if (pageNumber > 3)
            pagenav = prebtn + pagenav;
        if (pageNumber < totalPages - 2)
            pagenav = pagenav + nextbtn;
    }
    return pagenav;
}

function convertDateYYYYMMDD(string) {
    var parts = string.split("-");
    var formattedDate = parts[2] + "-" + parts[1] + "-" + parts[0];
    return formattedDate;
}