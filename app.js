const express = require("express");
const mysql = require("mysql2");
const path = require("path");
const router = express.Router();
const hbs = require("express-handlebars");
const bodyParser = require("body-parser");
const { connection } = require("./config/db");
const app = express();
const port = 5000;
const requestIp = require("request-ip");
const user = require("./routes/user");
const reader = require("xlsx");
var server = require("http").Server(app);
var io = require("socket.io")(server);

const fs = require('fs');
const ExcelJS = require('exceljs');
const { saveAs } = require('file-saver');

app.use(express.json());
app.engine("handlebars", hbs.engine());
app.set("view engine", "handlebars");
app.set("views", "./views");

app.use(express.static(path.join(__dirname, "public")));

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());



const modeacc = {
  a: false // hoặc a: 0
};
const modehome = {
  a: true // hoặc a: 0
};

// route UI->home : Begin
app.get("/", (req, res) => {
  res.render("UI/home/E_home", modehome);
});
app.get("/danhsachlop", (req, res) => {
  res.render("UI/home/E_jobs", modehome);
});
app.get("/chitietlop", (req, res) => {
  res.render("UI/home/E_job_details", modehome);
});
app.get("/danhsachgiasu", (req, res) => {
  res.render("UI/home/E_candidate", modehome);
});
app.get("/chitietgiasu", (req, res) => {
  res.render("UI/home/E_candidate_details", modehome);
});
app.get("/taolop", (req, res) => {
  res.render("UI/home/E_postclass", modehome);
});
app.get("/dangkygiasu", (req, res) => {
  res.render("UI/home/E_register_tutor", modehome);
});
app.get("/dangkyhocvien", (req, res) => {
  res.render("UI/home/E_register_student", modehome);
});
app.get("/quydinh", (req, res) => {
  res.render("UI/home/E_regulation", modehome);
});
app.get("/hopdong", (req, res) => {
  res.render("UI/home/E_contract", modehome);
});
app.get("/hopdong", (req, res) => {
  res.render("UI/home/E_contract", modehome);
});
// route UI->home : End
// --------------------------------------

// route Login : Begin
app.get("/dangnhap", (req, res) => {
  res.render("UI/E_login", modehome);
});
app.get("/quenmatkhau", (req, res) => {
  res.render("UI/E_forgotpassword", modehome);
});
// route Login : End


//  route manager: Begin
app.get("/quanly", (req, res) => {
  res.render("UI/manager/E_profileManager", modeacc);
});
app.get("/quanly/doimatkhau", (req, res) => {
  res.render("UI/manager/E_changePassM", modeacc);
});
app.get("/quanly/xuatdulieu", (req, res) => {
  res.render("UI/manager/E_excel", modeacc);
});
app.get("/quanly/danhsachadmin", (req, res) => {
  res.render("UI/manager/E_listAdmin", modeacc);
});
app.get("/quanly/thongbao", (req, res) => {
  res.render("UI/manager/E_listNotificationM", modeacc);
});
app.get("/quanly/luuadmin", (req, res) => {
  res.render("UI/manager/E_saveAdmin", modeacc);
});
app.get("/quanly/thongke", (req, res) => {
  res.render("UI/manager/E_static", modeacc);
});
//  route manager: End

//  route student: Begin
app.get("/hocvien", (req, res) => {
  res.render("UI/student/E_profileStudent", modeacc);
});
app.get("/hocvien/doimatkhau", (req, res) => {
  res.render("UI/student/E_changePassS", modeacc);
});
app.get("/hocvien/lopchoduyet", (req, res) => {
  res.render("UI/student/E_postWaiting", modeacc);
});
app.get("/hocvien/lopdangtim", (req, res) => {
  res.render("UI/student/E_postFinding", modeacc);
});
app.get("/hocvien/lopdagiao", (req, res) => {
  res.render("UI/student/E_postHanded", modeacc);
});
app.get("/hocvien/thongbao", (req, res) => {
  res.render("UI/student/E_listNotificationS", modeacc);
});
app.get("/hocvien/thongtinlop", (req, res) => {
  res.render("UI/student/E_editPost", modeacc);
});
app.get("/hocvien/moigiasu", (req, res) => {
  res.render("UI/student/E_listTutorInvited", modeacc);
});
app.get("/hocvien/lop/ungtuyen", (req, res) => {
  res.render("UI/student/E_postTutorApplying", modeacc);
});
//  route student: End

//  route tutor: Begin
app.get("/giasu", (req, res) => {
  res.render("UI/tutor/E_profileTutor", modeacc);
});
app.get("/giasu/doimatkhau", (req, res) => {
  res.render("UI/tutor/E_changePassT", modeacc);
});
app.get("/giasu/lichsuungtuyen", (req, res) => {
  res.render("UI/tutor/E_classApplied", modeacc);
});
app.get("/giasu/loimoiungtuyen", (req, res) => {
  res.render("UI/tutor/E_classInvited", modeacc);
});
app.get("/giasu/lopdangday", (req, res) => {
  res.render("UI/tutor/E_classTeaching", modeacc);
});
app.get("/giasu/giaodichthatbai", (req, res) => {
  res.render("UI/tutor/E_error", modeacc);
});
app.get("/giasu/congno", (req, res) => {
  res.render("UI/tutor/E_listDebt", modeacc);
});
app.get("/giasu/thongbao", (req, res) => {
  res.render("UI/tutor/E_listNotificationT", modeacc);
});
app.get("/giasu/lichsugiaodich", (req, res) => {
  res.render("UI/tutor/E_listTransaction", modeacc);
});
app.get("/giasu/thanhtoan", (req, res) => {
  res.render("UI/tutor/E_payment", modeacc);
});
app.get("/giasu/giaodichthanhcong", (req, res) => {
  res.render("UI/tutor/E_success", modeacc);
});
//  route tutor: End

//  route admin: Begin
app.get("/admin", (req, res) => {
  res.render("UI/admin/E_profileAdmin", modeacc);
});
app.get("/admin/doimatkhau", (req, res) => {
  res.render("UI/admin/E_changePassA", modeacc);
});
app.get("/admin/chitietungtuyen", (req, res) => {
  res.render("UI/admin/E_detailApply", modeacc);
});
app.get("/admin/chitietlop", (req, res) => {
  res.render("UI/admin/E_detailClass", modeacc);
});
app.get("/admin/chitiethocvien", (req, res) => {
  res.render("UI/admin/E_detailStudent", modeacc);
});
app.get("/admin/chitietgiasu", (req, res) => {
  res.render("UI/admin/E_detailTutor", modeacc);
});
app.get("/admin/lopdangtim", (req, res) => {
  res.render("UI/admin/E_listClassFinding", modeacc);
});
app.get("/admin/lopdagiao", (req, res) => {
  res.render("UI/admin/E_listClassHanded", modeacc);
});
app.get("/admin/lopcangiao", (req, res) => {
  res.render("UI/admin/E_listClassHanding", modeacc);
});
app.get("/admin/congno", (req, res) => {
  res.render("UI/admin/E_listDebt", modeacc);
});
app.get("/admin/thongbao", (req, res) => {
  res.render("UI/admin/E_listNotificationA", modeacc);
});
app.get("/admin/yeucautaolop", (req, res) => {
  res.render("UI/admin/E_listRegisterClass", modeacc);
});
app.get("/admin/dangkyhocvien", (req, res) => {
  res.render("UI/admin/E_listRegisterStudent", modeacc);
});
app.get("/admin/dangkygiasu", (req, res) => {
  res.render("UI/admin/E_listRegisterTutor", modeacc);
});
app.get("/admin/danhsachhocvien", (req, res) => {
  res.render("UI/admin/E_listStudent", modeacc);
});
app.get("/admin/chudeday", (req, res) => {
  res.render("UI/admin/E_listSubject", modeacc);
});
app.get("/admin/lichsugiaodich", (req, res) => {
  res.render("UI/admin/E_listTransaction", modeacc);
});
app.get("/admin/danhsachgiasu", (req, res) => {
  res.render("UI/admin/E_listTutor", modeacc);
});
app.get("/admin/lop/ketquaungtuyen", (req, res) => {
  res.render("UI/admin/E_listTutorApplied", modeacc);
});
app.get("/admin/lop/ungtuyen", (req, res) => {
  res.render("UI/admin/E_listTutorApplying", modeacc);
});
//  route admin: End

app.use("/exportdoanhthu", (req, res) => {
  console.log(req.body);

  const data = req.body;
  const cost = data.cost;
  const detail = data.detail;
  const from = data.from;
  const to = data.to;

  const templateFilePath = 'public/document/budget.xlsx';

  const workbook = new ExcelJS.Workbook();

  workbook.xlsx.readFile(templateFilePath)
    .then(() => {
      const sheet = workbook.getWorksheet('Sheet1');

      sheet.getCell('C5').value = from;
      sheet.getCell('C7').value = to;

      for (let index = 0; index < cost.length; index++) {
        sheet.getCell('G' + (index + 3)).value = cost[index];
      };

      for (let index = 0; index < detail.length; index++) {
        var row = detail[index];
        var column = ['B', 'C', 'D', 'E', 'F', 'G', 'H']
        for (let i = 0; i < row.length; i++) {
          const cell = sheet.getCell(column[i] + (14 + index));
          cell.value = row[i];
        }
      };

      workbook.xlsx.writeBuffer()
        .then(buffer => {
          res.setHeader('Content-Disposition', 'attachment; filename="file.xlsx"');
          res.setHeader('Content-Type', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet');
          res.send(buffer);
        })
        .catch(error => {
          console.error('Error:', error);
          res.status(500).send('Internal Server Error');
        });
    })
    .then(() => {
      console.log('Đã điền dữ liệu vào file Excel thành công.');
    })
    .catch((error) => {
      console.error('Đã xảy ra lỗi:', error);
    });
});

app.use("/exportgiasumoi", (req, res) => {
  console.log(req.body);

  const data = req.body;
  const detail = data.detail;
  const from = data.from;
  const to = data.to;

  const templateFilePath = 'public/document/tutor.xlsx';

  const workbook = new ExcelJS.Workbook();

  workbook.xlsx.readFile(templateFilePath)
    .then(() => {
      const sheet = workbook.getWorksheet('Sheet1');

      sheet.getCell('H4').value = from;
      sheet.getCell('H5').value = to;

      for (let index = 0; index < detail.length; index++) {
        var row = detail[index];
        var column = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L']
        for (let i = 0; i < row.length; i++) {
          const cell = sheet.getCell(column[i] + (8 + index));
          cell.value = row[i];
        }
      };

      workbook.xlsx.writeBuffer()
        .then(buffer => {
          res.setHeader('Content-Disposition', 'attachment; filename="file.xlsx"');
          res.setHeader('Content-Type', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet');
          res.send(buffer);
        })
        .catch(error => {
          console.error('Error:', error);
          res.status(500).send('Internal Server Error');
        });
    })
    .then(() => {
      console.log('Đã điền dữ liệu vào file Excel thành công.');
    })
    .catch((error) => {
      console.error('Đã xảy ra lỗi:', error);
    });
});

app.use("/user", user);
server.listen(port, function () {
  console.log("Server listening on port " + port);
});


