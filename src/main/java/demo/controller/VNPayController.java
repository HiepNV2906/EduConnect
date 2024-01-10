package demo.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import demo.config.VNPayConfig;
import demo.dto.ThanhToanDTO;
import demo.entity.ThanhToan;
import demo.entity.UngTuyen;
import demo.mapper.ThanhToanMapper;
import demo.response.BaseResponse;
import demo.security.JwtUtil;
import demo.service.ThanhToanService;
import demo.service.UngTuyenService;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/vnpay")
public class VNPayController {
	
	@Autowired
	UngTuyenService ungTuyenService;
	@Autowired
	ThanhToanService thanhToanService;
	@Autowired
	JwtUtil jwtUtil;

	@PostMapping(value = "", produces = "application/json")
	public BaseResponse<?> createPay(
			@RequestBody ThanhToanDTO thanhToanDTO,
			@RequestHeader("Authorization") String token) {
		try {
			if(thanhToanService.getListThanhToanByUngTuyenId(thanhToanDTO.getUngtuyenid())!=null) {
				return new BaseResponse<>("Phí nhận lớp đã được thanh toán!", null, HttpStatus.BAD_REQUEST);
			}
			UngTuyen u = ungTuyenService.getUngTuyenById(thanhToanDTO.getUngtuyenid());
			String vnp_Version = "2.1.0";
	        String vnp_Command = "pay";
	        String orderType = "other";
	        Long amount = u.getLop().getPhiungtuyen() * 100;
	        String bankCode = thanhToanDTO.getNganhang();
	        
	        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
	        String vnp_IpAddr = "localhost:5000";
	
	        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
	        
	        Map<String, String> vnp_Params = new HashMap();
	        vnp_Params.put("vnp_Version", vnp_Version);
	        vnp_Params.put("vnp_Command", vnp_Command);
	        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
	        vnp_Params.put("vnp_Amount", String.valueOf(amount));
	        vnp_Params.put("vnp_CurrCode", "VND");
	        
	        
	        if (bankCode != null && !bankCode.isEmpty()) {
	            vnp_Params.put("vnp_BankCode", bankCode);
	        }
	        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
	        vnp_Params.put("vnp_OrderInfo", thanhToanDTO.getNoidung());
	        vnp_Params.put("vnp_OrderType", orderType);
	
	        String locate = null;
	        if (locate != null && !locate.isEmpty()) {
	            vnp_Params.put("vnp_Locale", locate);
	        } else {
	            vnp_Params.put("vnp_Locale", "vn");
	        }
	        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl + "/" + thanhToanDTO.getUngtuyenid());
	        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
	
	        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	        String vnp_CreateDate = formatter.format(cld.getTime());
	        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
	        
	        cld.add(Calendar.MINUTE, 15);
	        String vnp_ExpireDate = formatter.format(cld.getTime());
	        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
	        
	        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
	        Collections.sort(fieldNames);
	        StringBuilder hashData = new StringBuilder();
	        StringBuilder query = new StringBuilder();
	        Iterator<String> itr = fieldNames.iterator();
	        while (itr.hasNext()) {
	            String fieldName = (String) itr.next();
	            String fieldValue = (String) vnp_Params.get(fieldName);
	            if ((fieldValue != null) && (fieldValue.length() > 0)) {
	                //Build hash data
	                hashData.append(fieldName);
	                hashData.append('=');
	                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
	                //Build query
	                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
	                query.append('=');
	                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
	                if (itr.hasNext()) {
	                    query.append('&');
	                    hashData.append('&');
	                }
	            }
	        }
	        String queryUrl = query.toString();
	        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
	        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
	        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;
	        return new BaseResponse<>("Successful!", paymentUrl, HttpStatus.OK);
		} catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/transaction_info/{ungtuyenid}", produces = "application/json")
	public void transaction(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("ungtuyenid") Long ungtuyenid) throws IOException{
		try {
			
			Map fields = new HashMap();
	        for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
	        	String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
                String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
	            if ((fieldValue != null) && (fieldValue.length() > 0)) {
	                fields.put(fieldName, fieldValue);
	            }
	        }

	        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
	        if (fields.containsKey("vnp_SecureHashType")) {
	            fields.remove("vnp_SecureHashType");
	        }
	        if (fields.containsKey("vnp_SecureHash")) {
	            fields.remove("vnp_SecureHash");
	        }
			
	        
			// Check checksum
	        String signValue = VNPayConfig.hashAllFields(fields);
	        System.out.println("***********************************");
	        System.out.println(signValue);
	        
            String targetURL = "http://localhost:5000/giasu/giaodichthatbai";
	        if (signValue.equals(vnp_SecureHash)) {

	            boolean checkOrderId = true; // vnp_TxnRef exists in your database
	            boolean checkAmount = true; // vnp_Amount is valid (Check vnp_Amount VNPAY returns compared to the amount of the code (vnp_TxnRef) in the Your database).
	            boolean checkOrderStatus = true; // PaymnentStatus = 0 (pending)

	            
	            if(checkOrderId) {
	                if(checkAmount) {
	                    if (checkOrderStatus) {
	                        if ("00".equals(request.getParameter("vnp_ResponseCode"))) {
	                            //Here Code update PaymnentStatus = 1 into your Database
	                        	String vnp_TransactionNo = request.getParameter("vnp_TransactionNo");
	                			Long vnp_Amount = Long.parseLong(request.getParameter("vnp_Amount"))/100;
	                			String vnp_BankCode = request.getParameter("vnp_BankCode");
	                			String vnp_BankTranNo = request.getParameter("vnp_BankTranNo");
	                			String vnp_OrderInfo = request.getParameter("vnp_OrderInfo");
	                			String vnp_PayDate = request.getParameter("vnp_PayDate");
	                        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	                			ThanhToanDTO t = new ThanhToanDTO(vnp_TransactionNo, vnp_BankCode, vnp_BankTranNo, vnp_OrderInfo,
	                					vnp_Amount, dateFormat.parse(vnp_PayDate), ungtuyenid);
	                			ThanhToan thanhToan = thanhToanService.addThanhToan(t);
	                			targetURL = "http://localhost:5000/giasu/giaodichthanhcong?id=" + thanhToan.getId();
	                        }
//	                        else {
	                            // Here Code update PaymnentStatus = 2 into your Database
//	                        	response.sendRedirect("http://localhost:5000/giasu/giaodichthatbai");
//	                        }
	                    } else { 
	                    	System.out.print("{\"RspCode\":\"02\",\"Message\":\"Order already confirmed\"}");
	                    }
	                } else {
	                	System.out.print("{\"RspCode\":\"04\",\"Message\":\"Invalid Amount\"}"); 
	                }
	            } else {
	            	System.out.print("{\"RspCode\":\"01\",\"Message\":\"Order not Found\"}");
	            }
	        } else {
	        	System.out.print("{\"RspCode\":\"97\",\"Message\":\"Invalid Checksum\"}");
	        }
	        System.out.print(targetURL);
	        response.sendRedirect(targetURL);
	    }
	    catch(Exception e)
	    {
	    	System.out.print("{\"RspCode\":\"99\",\"Message\":\"Unknow error\"}");
	    	System.out.print(e);
	    	response.sendRedirect("http://localhost:5000/giasu/giaodichthatbai");
	    }
	}
}
