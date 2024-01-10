
var listClass;
var totalPages;
var sizeOfPage = 10;


$(document).ready(function () {
    var quanhuyenHTML = createDistrictFilterHTML();
    $("#quanfilter").html(quanhuyenHTML);
    getAPIListSubject(() => renderSubject());
    getAPIListClass();
});

// Handle Event
$("#monhocfilter").change(reloadSubjectLevel);

$('#nutfilter').click(function () {
    getAPIListClassBySearch();
})

$(".sapxep").change(function (event) {
    listClass.sortBy = function (prop, asc) {
        console.log('sort')
        if (asc) {
            return this.sort(function (a, b) {
                for (let i = 0; i < prop.length; i++) {
                    if (a[prop[i]] < b[prop[i]]) {
                        return -1;
                    }
                    if (a[prop[i]] > b[prop[i]]) {
                        return 1;
                    }
                }
                return 0;
            });
        }
        return this.sort(function (a, b) {
            for (let i = 0; i < prop.length; i++) {
                if (a[prop[i]] < b[prop[i]]) {
                    return 1;
                }
                if (a[prop[i]] > b[prop[i]]) {
                    return -1;
                }
            }
            return 0;
        });
    };
    var select = event.target.value;
    if (select == 0) {
        listClass.sortBy(['ngaytao'], false);
        renderData(listClass, totalPages, 1, sizeOfPage);
    } else if (select == 1) {
        listClass.sortBy(['hocphi'], true);
        renderData(listClass, totalPages, 1, sizeOfPage);
    } else if (select == 2) {
        listClass.sortBy(['hocphi'], false);
        renderData(listClass, totalPages, 1, sizeOfPage);
    }
    else {
        comparation(function () {
            listClass.sortBy(['phuhop', 'hocphi'], false);
            renderData(listClass, totalPages, 1, sizeOfPage);
        });
    }
})

function comparation(callback) {
    var token = $.cookie('token');
    var id = $.cookie('id');
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
            var me = response.data;
            var chudeday = me.dschude.map((value) => value.tenmonhoc + ' ' + value.trinhdo).join(', ');
            for (let i = 0; i < listClass.length; i++) {
                let lop = listClass[i];
                let point = 0;
                if (me.khuvucday.includes(lop.quan)) point += 1;
                if (lop.gioitinhgs == "" || lop.gioitinhgs == me.gioitinh) point += 1;
                if (lop.nghenghiepgs == "" || lop.nghenghiepgs == "Khac" || lop.nghenghiepgs == me.nghenghiep) point += 1;
                if (chudeday.includes(lop.chude.tenmonhoc)) point += 1;
                if (chudeday.includes(lop.chude.tenmonhoc + ' ' + lop.chude.trinhdo)) point += 1;
                listClass[i]['phuhop'] = point;
            }
            callback();
            console.log(listClass);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

// Function handle event
function handlePage(currentPage) {
    renderData(listClass, totalPages, currentPage, sizeOfPage);
}

// Function get API

function handleUngTuyen(lopid) {
    console.log('aaa');
    if ($.cookie('role') == "GIASU") {
        ungtuyen(lopid);
    } else {
        alert("Đăng ký làm gia sư để ứng tuyển nhận lớp");
    }
};

function ungtuyen(lopid) {
    var token = $.cookie('token');

    var infoAcc = {
        "giasuid": $.cookie('id'),
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
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function getAPIListClass() {
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "GET",
        url: "http://localhost:8080/api/lop/trangthai?status=DANGTIM",
        success: function (response) {
            listClass = response.data;
            totalPages = Math.ceil(listClass.length / sizeOfPage);
            renderData(listClass, totalPages, 1, sizeOfPage);
            console.log(response);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function getAPIListClassBySearch() {
    var key = 'key=' + $("#input-search").val();
    var quan = 'quan=' + $("#quanfilter").val();
    var hinhthuc = 'hinhthuc=' + $("#hinhthucfilter").val();
    var mon = 'mon=' + $("#monhocfilter").val();
    var trinhdo = 'trinhdo=' + (($("#trinhdofilter").val() == '') ? '' : $("#trinhdofilter option:selected").text());
    var hocphival = $("#hocphifilter").val();
    var hocphimin, hocphimax;
    if (hocphival == 0) {
        hocphimin = 0;
        hocphimax = 1000000000;
    } else {
        if (hocphival == 6) {
            hocphimin = 500000;
            hocphimax = 1000000000;
        } else {
            hocphimin = 100000 * (hocphival - 1);
            hocphimax = 100000 * hocphival;
        }
    }
    hocphimin = 'hocphimin=' + hocphimin;
    hocphimax = 'hocphimax=' + hocphimax;
    var params = [key, quan, hocphimin, hocphimax, hinhthuc, mon, trinhdo];
    var newUrl = "http://localhost:8080/api/lop/filter?" + params.join('&');
    console.log(newUrl);
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "GET",
        url: newUrl,
        success: function (response) {
            listClass = response.data;
            console.log(response);
            totalPages = Math.ceil(listClass.length / sizeOfPage);
            renderData(listClass, totalPages, 1, sizeOfPage);
            console.log(response);
            console.log(response);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}


function renderSubject() {
    var monhocHTML = createSubjectFilterHTML(listSubjects);
    $("#monhocfilter").html(monhocHTML);
}

function reloadSubjectLevel() {
    var mon = $("#monhocfilter").val();
    console.log(mon);
    console.log(listLevelSubjects);
    var data = createSubjectLevelFilterHTML(listLevelSubjects[mon]);
    $("#trinhdofilter").html(data);
}

// -----------------------------------
// Function render Data
function renderData(listdata, totalPages, currentPage, sizeOfPage) {
    var start = (currentPage - 1) * sizeOfPage;
    var end = currentPage * sizeOfPage;
    if (listdata.length < end) {
        end = listdata.length;
    }
    var classesHTML = createListClassHTML(listdata, start, end);
    var pagesHTML = createPageNavHTML(totalPages, currentPage);
    $("#listclass").html(classesHTML);
    $("#pageable").html(pagesHTML);
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

function createListClassHTML(apicontent, start, end) {
    var data = ``;
    for (let i = start; i < end; i++) {
        data += `<div class="col-lg-12 col-md-12">
                    <div class="single_jobs white-bg d-flex justify-content-between">
                        <div class="jobs_left d-flex align-items-center">
                            <div class="thumb">
                                <img src=${apicontent[i].chude.anh} alt="">
                            </div>
                            <div class="jobs_conetent">
                                <a href="/chitietlop?id=${apicontent[i].id}">
                                    <h4>${apicontent[i].tieude}</h4>
                                </a>
                                <div class="links_locat">
                                    <div class="location">
                                        <p> <i class="fa fa-leanpub"></i>${apicontent[i].chude.tenmonhoc + " " + apicontent[i].chude.trinhdo}</p>
                                    </div>
                                    <div class="location">
                                        <p> <i class="fa fa-money"></i> ${apicontent[i].hocphi} VND/buổi</p>
                                    </div>
                                    <div class="location">
                                        <p> <i class="fa fa-calendar"></i> ${apicontent[i].sobuoi} buổi/tuần</p>
                                    </div>
                                    <div class="location">
                                        <p> <i class="fa fa-map-marker"></i> ${apicontent[i].diachi + ", " + apicontent[i].quan}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="jobs_right">
                            <div class="apply_now">
                                <button class="boxed-btn3" onClick="handleUngTuyen(${apicontent[i].id})">Ứng tuyển</button>
                            </div>
                            <div class="date">
                                <p>Ngày tạo: ${moment(apicontent[i].ngaytao).format('DD-MM-YYYY')}</p>
                            </div>
                        </div>
                    </div>
                </div>`
    }
    return data;
}

function createPageNavHTML(totalPages, pageNumber) {
    var pagenav = ``;
    var prebtn = `<li><a href="#"> <i class="ti-angle-left"></i> </a></li>`;
    var nextbtn = `<li><a href="#"> <i class="ti-angle-right"></i> </a></li>`;
    var listPageNum = createListPage(totalPages, pageNumber);
    for (let i = 0; i < listPageNum.length; i++) {
        if (i + 1 == pageNumber) {
            pagenav += `<li><a class="page_selected" href="#"><span>${listPageNum[i]}</span></a></li>`
        } else {
            pagenav += `<li><a class="" href="#"><span>${listPageNum[i]}</span></a></li>`
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
