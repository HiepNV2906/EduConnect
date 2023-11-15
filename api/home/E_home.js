$(document).ready(function () {
    getAPINewClass();
    getAPINewTutor();
});

function getAPINewClass() {
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "GET",
        url: "http://localhost:8080/api/lop/topnew",
        success: function (response) {
            var data = response.data;
            var html = ``;
            for (let i = 0; i < data.length; i++) {
                html += `<div class="col-lg-12 col-md-12">
                            <div class="single_jobs white-bg d-flex justify-content-between">
                                <div class="jobs_left d-flex align-items-center">
                                    <div class="thumb">
                                        <img src=${data[i].chude.anh} alt="">
                                    </div>
                                    <div class="jobs_conetent">
                                        <a href="E_job_details.html">
                                            <h4>${data[i].tieude}</h4>
                                        </a>
                                        <div class="links_locat">
                                            <div class="location">
                                                <p> <i class="fa fa-leanpub"></i> ${data[i].chude.tenmonhoc + " " + data[i].chude.trinhdo} </p>
                                            </div>
                                            <div class="location">
                                                <p> <i class="fa fa-money"></i> ${data[i].hocphi}/buổi</p>
                                            </div>
                                            <div class="location">
                                                <p> <i class="fa fa-calendar"></i> ${data[i].sobuoi} buổi/tuần</p>
                                            </div>
                                            <div class="location">
                                                <p> <i class="fa fa-map-marker"></i> ${data[i].diachi + ", " + data[i].quan}</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="jobs_right">
                                    <div class="apply_now">
                                        <!-- <a class="heart_mark" href="#"> <i class="fa fa-heart"></i> </a> -->
                                        <a href="E_job_details.html" class="boxed-btn3">Ứng tuyển</a>
                                    </div>
                                    <div class="date">
                                        <p>Ngày tạo: ${moment(data[i].ngaytao).format('DD-MM-YYYY')}</p>
                                    </div>
                                </div>
                            </div>
                        </div>`
            }
            $("#topnewclass").html(html);
            console.log(response);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function getAPINewTutor() {
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "GET",
        url: "http://localhost:8080/api/giasu/topnew",
        success: function (response) {
            var data = response.data;
            var html = ``;
            for (let i = 0; i < data.length; i++) {
                var chudearr = new Set();
                var dschude = data[i].dschude;
                for (let i = 0; i < dschude.length; i++) {
                    chudearr.add(dschude[i].tenmonhoc);
                }
                chudearr = [...chudearr];
                html += `<div class="single_candidates">
                            <div class="thumb">
                                <img src=${data[i].avata ? 'http://localhost:8080/uploads/images/' + data[i].avata : '../../img/avatadefault.png'} alt="">
                            </div>
                            <a href="E_candidate_details.html?id=${data[i].id}" class=" text-center">
                                <h4>${data[i].hoten}</h4>
                            </a>
                            <div>
                                <p>
                                    <i class="fa fa-leanpub fa-fw"></i>
                                    <span>${chudearr.join(', ')}</span>
                                </p>
                                <p>
                                    <i class="fa fa-user fa-fw"></i>
                                    <span>${data[i].nghenghiep}</span>
                                </p>
                                <p>
                                    <i class="fa fa-graduation-cap fa-fw"></i>
                                    <span>${data[i].truong}</span>
                                </p>
                                <p>
                                    <i class="fa fa-map-marker fa-fw"></i>
                                    <span>${data[i].diachi + ", " + data[i].quan}</span>
                                </p>
                            </div>
                        </div>`
            }
            $("#toptutor").html(html);
            console.log(response);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}