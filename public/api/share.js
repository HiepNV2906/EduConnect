var subjectSelection = $("#monhocfilter");
var listSubjects;
var listLevelSubjects;

$(document).ready(function () {
    var role = $.cookie('role');
    var html;
    if (role == "HOCVIEN") {
        html = `<a href="/hocvien">Tài khoản</a>`
    } else if (role == "GIASU") {
        html = `<a href="/giasu">Tài khoản</a>`
    } else if (role == "ADMIN") {
        html = `<a href="/admin">Tài khoản</a>`
    } else if (role == "MANAGER") {
        html = `<a href="/quanly">Tài khoản</a>`
    } else {
        html = `<a href="/dangnhap">Đăng nhập</a>`
    }
    $('.account').html(html);
});


var intervalId = setInterval(function () {
    var token = $.cookie('token');
    var refreshtoken = $.cookie('refreshtoken');
    if (token) {
        console.log('token');
        return;
    }
    if (refreshtoken) {
        // Thực hiện nhiệm vụ khi cookie hết hạn
        console.log('refreshtoken');
        refreshToken();
    } else {
        console.log('login');
        clearInterval(intervalId);
    }
}, 1000);

function getAPIListSubject(callback) {
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        method: "GET",
        url: "http://localhost:8080/api/chude",
        success: function (response) {
            if (response.httpStatus == "OK") {
                listSubjects = [...new Set(response.data.map(item => item.tenmonhoc))];
                console.log(listSubjects);
                listLevelSubjects = Object.groupBy(response.data, ({ tenmonhoc }) => tenmonhoc);
                listLevelSubjects[''] = [];
                console.log(listLevelSubjects);
                callback();
            }
            else {
                alert(response.httpStatus + ": " + response.message);
            }
            console.log(response);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function createSubjectFilterHTML(subjects) {
    var data = `<option value="" data-display="Môn">Môn</option>`;
    for (let i = 0; i < subjects.length; i++) {
        data += `<option value="${subjects[i]}">${subjects[i]}</option>`
    }
    return data;
}

function createSubjectLevelFilterHTML(subjectlevel) {
    var data = `<option value="" data-display="Trình độ">Trình độ</option>`;
    for (let i = 0; i < subjectlevel.length; i++) {
        data += `<option value=${subjectlevel[i].id}>${subjectlevel[i].trinhdo}</option>`
    }
    return data;
}

function createDistrictFilterHTML(title = 'Quận/Huyện') {
    var data = `<option value="" data-display="Quận/Huyện">${title}</option>`;
    var quanhuyen = ['Ba Đình', 'Bắc Từ Liêm', 'Cầu Giấy', 'Đống Đa', 'Hà Đông', 'Hai Bà Trưng',
        'Hoàn Kiếm', 'Hoàng Mai', 'Long Biên', 'Nam Từ Liêm', 'Tây Hồ', 'Thanh Xuân',
        'Ba Vì', 'Chương Mỹ', 'Đan Phượng', 'Đông Anh', 'Gia Lâm', 'Hoài Đức',
        'Mê Linh', 'Mỹ Đức', 'Phú Xuyên', 'Phúc Thọ', 'Quốc Oai', 'Sóc Sơn', 'Sơn Tây',
        'Thạch Thất', 'Thanh Oai', 'Thanh Trì', 'Thường Tín', 'Ứng Hoà']
    for (let i = 0; i < quanhuyen.length; i++) {
        data += `<option value='${quanhuyen[i]}'>${quanhuyen[i]}</option>`
    }
    return data;
}

function getParamFromURL(name) {
    var urlParams = new URLSearchParams(window.location.search);
    var paramValue = urlParams.get(name);
    console.log(paramValue);
    return paramValue;
}


function saveCookies(data) {
    var expirationDateToken = new Date();
    var expirationDateRefreshToken = new Date();
    var timeToken = 1;
    var timeRefreshToken = 1;
    expirationDateToken.setHours(expirationDateToken.getHours() + timeToken);
    expirationDateRefreshToken.setHours(expirationDateRefreshToken.getHours() + timeRefreshToken);
    $.cookie('token', data.type + ' ' + data.token, { expires: expirationDateRefreshToken });
    $.cookie('refreshtoken', data.refreshtoken, { expires: expirationDateRefreshToken });
    $.cookie('id', data.id, { expires: expirationDateRefreshToken });
    $.cookie('username', data.username, { expires: expirationDateRefreshToken });
    $.cookie('role', data.roles[0], { expires: expirationDateRefreshToken });
}

function refreshToken() {
    var userid = $.cookie('id');
    var refreshToken = $.cookie('refreshtoken');

    var infoAcc = {
        "userid": userid,
        "refreshtoken": refreshToken
    }

    var jsonString = JSON.stringify(infoAcc);

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        url: "http://localhost:8080/changeRefreshToken",
        method: "POST",
        data: jsonString,
        success: function (response) {
            console.log(response);
            if (response.httpStatus == 'OK') {
                var data = response.data;
                $.cookie('token', data.type + ' ' + data.token, { expires: expirationDateToken });
                alert('refresh');
            } else {
                alert(response.message);
            }
        },
        error: function (xhr, status, error) {
            alert(error);
        }
    });
}

function changePassWord() {
    var oldpass = $('.oldpass input').val();
    var newpass = $('.newpass input').val();
    var renewpass = $('.renewpass input').val();

    if (newpass !== renewpass) {
        alert('Nhập lại mật khẩu không khớp');
        return;
    }

    var id = $.cookie('id');
    var token = $.cookie('token');

    var changePass = {
        'oldPassword': oldpass,
        'newPassword': newpass,
        'confirmPassword': renewpass
    }

    var json = JSON.stringify(changePass);

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        url: "http://localhost:8080/changePassword/" + id,
        method: "PUT",
        data: json,
        success: function (response) {
            console.log(response);
            alert(response.message);
        },
        error: function (xhr, status, error) {
            alert(error);
        }
    });
}

function logout() {
    var token = $.cookie('token');
    var id = $.cookie('id');
    if (!id) return;
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        url: "http://localhost:8080/logout/" + id,
        method: "GET",
        success: function (response) {
            console.log(response);
            if (response.httpStatus == 'OK') {
                var optionCookie = {
                    path: '/',
                    domain: 'localhost'
                }
                $.removeCookie('token', optionCookie);
                $.removeCookie('refreshtoken', optionCookie);
                $.removeCookie('id', optionCookie);
                $.removeCookie('username', optionCookie);
                $.removeCookie('role', optionCookie);
                window.location.href = "/";
            } else {
                alert(response.message);
            }
        },
        error: function (xhr, status, error) {
            alert(error);
        }
    });
}
