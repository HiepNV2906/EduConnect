var listPost;
var totalPages;
var sizeOfPage = 10;

$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '../E_login.html';
    }
    loadHeaderWraper();
    getAPIListClassByHocVienAndStatus("CHOPHEDUYET", '.listclasswaiting');
});

function viewDetailClass(classid) {
    window.location.href = 'E_edit_post.html?id=' + classid;
}

function createListDataHTML(data, start, end) {
    var datahtml = ``;
    for (let i = start; i < end; i++) {
        datahtml += `<tr>
                        <td>${moment(data[i].ngaytao).format('DD-MM-YYYY')}</td>
                        <td>${data[i].id}</td>
                        <td>${data[i].tieude}</td>
                        <td>${data[i].quan}</td>
                        <td>
                            <button type="button" class="btn btn-outline-primary btn-sm" 
                                onClick="viewDetailClass(${data[i].id})">Xem lớp</button>
                        </td>
                    </tr>`
    }
    return datahtml;
}

// function handlePagenable(currentpage) {
//     renderData(listPost, totalPages, currentpage, sizeOfPage);
// }

// function getAPIListClassWaiting() {
//     var id = $.cookie('id');
//     var token = $.cookie('token');
//     var status = "CHOPHEDUYET";
//     $.ajax({
//         headers: {
//             'Accept': 'application/json',
//             'Content-Type': 'application/json',
//             'Authorization': token ? token : ''
//         },
//         method: "GET",
//         url: "http://localhost:8080/api/lop/hocvien?hocvienid=" + id + "&status=" + status,
//         success: function (response) {
//             console.log(response);
//             listPost = response.data;
//             totalPages = Math.ceil(listPost.length / sizeOfPage);
//             renderData(listPost, totalPages, 1, sizeOfPage, ".listclasswaiting");
//         },
//         error: function (xhr, status, error) {
//             // Xử lý lỗi
//             console.log(error);
//             alert("Có lỗi xảy ra!!!");
//         }
//     });
// }

// function renderData(listdata, totalPages, currentPage, sizeOfPage) {
//     var start = (currentPage - 1) * sizeOfPage;
//     var end = currentPage * sizeOfPage;
//     if (listdata.length + 1 < end) {
//         end = listdata.length;
//     }
//     var classesHTML = createListDataHTML(listdata, start, end);
//     var pagesHTML = createPageNavHTML(totalPages, currentPage);
//     $(".listclasswaiting").html(classesHTML);
//     $(".pagination").html(pagesHTML);
// }

// function createListPage(totalPages, pageNumber) {
//     var listPageNum = [];
//     if (totalPages <= 5) {
//         for (let i = 1; i <= totalPages; i++) {
//             listPageNum.push(i);
//         }
//         return listPageNum;
//     }
//     if (pageNumber <= 3)
//         return [1, 2, 3, 4, 5];
//     if (pageNumber >= totalPages - 2)
//         return [totalPages - 4, totalPages - 3, totalPages - 2, totalPages - 1, totalPages];
//     return [pageNumber - 2, pageNumber - 1, pageNumber, pageNumber + 1, pageNumber + 2];
// }

// function createPageNavHTML(totalPages, pageNumber) {
//     var pagenav = ``;
//     var prebtn = `<li class="page-item">
//                         <div class="page-link" aria-label="Previous"
//                         onClick = "handlePagenable(${pageNumber - 1})">
//                             <span aria-hidden="true">&laquo;</span>
//                             <span class="sr-only">Previous</span>
//                         </div>
//                     </li>`;
//     var nextbtn = `<li class="page-item">
//                         <div class="page-link" aria-label="Next"
//                         onClick = "handlePagenable(${pageNumber + 1})">
//                             <span aria-hidden="true">&raquo;</span>
//                             <span class="sr-only">Next</span>
//                         </div>
//                     </li>`;
//     var listPageNum = createListPage(totalPages, pageNumber);
//     for (let i = 0; i < listPageNum.length; i++) {
//         if (i + 1 == pageNumber) {
//             pagenav += `<li class="page-item"><div class="page-link page_selected">${listPageNum[i]}</div></li>`
//         } else {
//             pagenav += `<li class="page-item"><div class="page-link"
//             onClick = "handlePagenable(${listPageNum[i]})">${listPageNum[i]}</div></li>`
//         }
//     }
//     if (totalPages > 5) {
//         if (pageNumber > 3)
//             pagenav = prebtn + pagenav;
//         if (pageNumber < totalPages - 2)
//             pagenav = pagenav + nextbtn;
//     }
//     return pagenav;
// }