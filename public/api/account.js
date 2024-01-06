
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

$('.nuttimkiem button').click(function () {
    if (typeof listItem !== 'undefined' || listItem) {
        console.log('Tìm kiếm');
        let key = $('.nuttimkiem input').val();
        data = searchByKey(listItem, key);
        totalPages = Math.ceil(data.length / sizeOfPage);
        renderData(data, totalPages, 1, sizeOfPage, 'table tbody');
    }
})

function removeVietnameseTones(str) {
    if (!str) return str;
    str = str.toString();
    str = str.replace(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g, "a");
    str = str.replace(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g, "e");
    str = str.replace(/ì|í|ị|ỉ|ĩ/g, "i");
    str = str.replace(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g, "o");
    str = str.replace(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g, "u");
    str = str.replace(/ỳ|ý|ỵ|ỷ|ỹ/g, "y");
    str = str.replace(/đ/g, "d");
    str = str.replace(/À|Á|Ạ|Ả|Ã|Â|Ầ|Ấ|Ậ|Ẩ|Ẫ|Ă|Ằ|Ắ|Ặ|Ẳ|Ẵ/g, "A");
    str = str.replace(/È|É|Ẹ|Ẻ|Ẽ|Ê|Ề|Ế|Ệ|Ể|Ễ/g, "E");
    str = str.replace(/Ì|Í|Ị|Ỉ|Ĩ/g, "I");
    str = str.replace(/Ò|Ó|Ọ|Ỏ|Õ|Ô|Ồ|Ố|Ộ|Ổ|Ỗ|Ơ|Ờ|Ớ|Ợ|Ở|Ỡ/g, "O");
    str = str.replace(/Ù|Ú|Ụ|Ủ|Ũ|Ư|Ừ|Ứ|Ự|Ử|Ữ/g, "U");
    str = str.replace(/Ỳ|Ý|Ỵ|Ỷ|Ỹ/g, "Y");
    str = str.replace(/Đ/g, "D");
    // Some system encode vietnamese combining accent as individual utf-8 characters
    // Một vài bộ encode coi các dấu mũ, dấu chữ như một kí tự riêng biệt nên thêm hai dòng này
    str = str.replace(/\u0300|\u0301|\u0303|\u0309|\u0323/g, ""); // ̀ ́ ̃ ̉ ̣  huyền, sắc, ngã, hỏi, nặng
    str = str.replace(/\u02C6|\u0306|\u031B/g, ""); // ˆ ̆ ̛  Â, Ê, Ă, Ơ, Ư
    // Remove extra spaces
    // Bỏ các khoảng trắng liền nhau
    str = str.replace(/ + /g, " ");
    str = str.trim();
    // Remove punctuations
    // Bỏ dấu câu, kí tự đặc biệt
    str = str.replace(/!|@|%|\^|\*|\(|\)|\+|\=|\<|\>|\?|\/|,|\.|\:|\;|\'|\"|\&|\#|\[|\]|~|\$|_|`|-|{|}|\||\\/g, " ");
    return str;
}

function searchByKey(data, key) {
    key = removeVietnameseTones(key);
    console.log(key);
    const searchTerm = new RegExp(key, 'i');
    const filteredData = data.filter(item => {
        for (let prop in item) {
            if (item.hasOwnProperty(prop) && searchTerm.test(removeVietnameseTones(item[prop]))) {
                console.log(removeVietnameseTones(item[prop]));
                return true;
            }
        }
        return false;
    });
    return filteredData;
}

function report(type, callback) {
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
        url: "http://localhost:8080/api/thongke/xuatbaocao?type=" + type + '&' + from + '&' + to,
        success: function (response) {
            console.log(response);
            if (response.httpStatus == "OK") {
                var data = response.data;
                callback(data);
            } else {
                alert(response.message);
            }
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

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
            if (response.httpStatus == "OK") {
                var data = response.data;
                callback(data);
            } else {
                alert(response.message);
            }
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
        method: "GET",
        url: "http://localhost:8080/api/ungtuyen/sapxepgiasu/" + id,
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

function addAccountAdmin(formData) {
    var token = $.cookie('token');

    $.ajax({
        url: "http://localhost:8080/api/user/admin",
        type: "POST",
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

function updateAccountAdmin(id, formData) {
    var token = $.cookie('token');

    $.ajax({
        url: "http://localhost:8080/api/user/admin/" + id,
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

function getListAdmin(cssSelector) {
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "GET",
        url: "http://localhost:8080/api/user/role?vaitro=ADMIN",
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

function getAPIListClassByHocVienAndFinishApply(cssSelector) {
    var id = $.cookie('id');
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "GET",
        url: "http://localhost:8080/api/lop/hocvien/ketthuc?hocvienid=" + id,
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

function deleteAnAdmin(id, callback) {
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
        url: "http://localhost:8080/api/user/admin/" + id,
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
                            <a href="/admin/thongbao">Xem tất cả</a>
                        </div>`
    } else if (role == 'GIASU') {
        thongbaohtml += `<div class="mess__footer">
                            <a href="/giasu/thongbao">Xem tất cả</a>
                        </div>`
    } else if (role == 'MANAGER') {
        thongbaohtml += `<div class="mess__footer">
                            <a href="/quanly/thongbao">Xem tất cả</a>
                        </div>`
    } else {
        thongbaohtml += `<div class="mess__footer">
                            <a href="/hocvien/thongbao">Xem tất cả</a>
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