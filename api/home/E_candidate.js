var listTutor;
var totalPages;
var sizeOfPage = 12;

$(document).ready(function () {
    var quanhuyenHTML = createDistrictFilterHTML('Khu vực dạy');
    $("#quanfilter").html(quanhuyenHTML);
    getAPIListSubject(() => renderSubject());
    getAPIListTutor();
});

// Handle Event
$("#monhocfilter").change(reloadSubjectLevel);

$("#nutfilter").click(function () {
    getAPIListTutorBySearch();
})

// Function handle event
function handlePage(currentPage) {
    renderData(listTutor, totalPages, currentPage, sizeOfPage);
}

// Function get API

function getAPIListTutor() {
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "GET",
        url: "http://localhost:8080/api/giasu/trangthai?status=DAPHEDUYET",
        success: function (response) {
            listTutor = response.data;
            totalPages = Math.ceil(listTutor.length / sizeOfPage);
            renderData(listTutor, totalPages, 1, sizeOfPage);
            console.log(response);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function getAPIListTutorBySearch() {
    var key = 'key=' + $("#input-search").val();
    var quan = 'quan=' + $("#quanfilter").val();
    var hinhthuc = 'gioitinh=' + $("#gioitinhfilter").val();
    var mon = 'mon=' + $("#monhocfilter").val();
    var trinhdo = 'trinhdo=' + (($("#trinhdofilter").val() == '') ? '' : $("#trinhdofilter option:selected").text());

    var params = [key, quan, hinhthuc, mon, trinhdo];

    var newUrl = "http://localhost:8080/api/giasu/filter?" + params.join('&');
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
            listTutor = response.data;
            totalPages = Math.ceil(listTutor.length / sizeOfPage);
            renderData(listTutor, totalPages, 1, sizeOfPage);
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

// Function render Data
function renderData(listdata, totalPages, currentPage, sizeOfPage) {
    var start = (currentPage - 1) * sizeOfPage;
    var end = currentPage * sizeOfPage;
    if (listdata.length < end) {
        end = listdata.length;
    }
    var classesHTML = createListTutorHTML(listdata, start, end);
    var pagesHTML = createPageNavHTML(totalPages, currentPage);
    $("#listtutors").html(classesHTML);
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

function createListTutorHTML(apicontent, start, end) {
    var data = ``;
    for (let i = start; i < end; i++) {
        var chude = apicontent[i].dschude;
        var dsmon = [];
        for (let j = 0; j < chude.length; j++) {
            dsmon.push(chude[j].tenmonhoc);
        }
        var dsmon = [...new Set(dsmon)];
        var mon = dsmon[0];
        for (let j = 1; j < dsmon.length; j++) {
            mon += ', ' + dsmon[j];
        }
        data += `<div class="col-md-6 col-lg-3">
                    <div class="single_candidates">
                        <div class="thumb">
                            <img src="http://localhost:8080/uploads/images/${apicontent[i].avata}" alt="">
                        </div>
                        <a href="E_candidate_details.html?id=${apicontent[i].id}" class=" text-center">
                            <h4>${apicontent[i].hoten}</h4>
                        </a>
                        <div>
                            <p>
                                <i class="fa fa-leanpub fa-fw"></i>
                                <span>${mon}</span>
                            </p>
                            <p>
                                <i class="fa fa-user fa-fw"></i>
                                <span>${apicontent[i].nghenghiep}</span>
                            </p>
                            <p>
                                <i class="fa fa-graduation-cap fa-fw"></i>
                                <span>${apicontent[i].truong}</span>
                            </p>
                            <p>
                                <i class="fa fa-map-marker fa-fw"></i>
                                <span>${apicontent[i].diachi + ', ' + apicontent[i].quan}</span>
                            </p>
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