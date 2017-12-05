package com.javainuse.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.javainuse.controllers.ResponseUpgrade;

@Controller
@RestController
public class TestController {

	@RequestMapping(value = "/Bank", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseEntity<ArrayList> clientDetails(@RequestParam("id") String id) {
		ArrayList<String> al=new ArrayList<String>();
		JSONObject json_response = null;
		if (id != null) {
			try {
				// Get Connection
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3302/bankdb", "root", "");

				// Create Statement
				Statement st = connection.createStatement();

				System.out.println("SELECT * FROM client Inner join balance ON client.ClientID = balance.ClientID where client.ClientID='"+id+"'");
				// ResultSet
				ResultSet rs = st.executeQuery("SELECT * FROM client Inner join balance ON client.ClientID = balance.ClientID where client.ClientID=\'"+id+"\'");

				while (rs.next()) {
					json_response = new JSONObject();
					json_response.put("ClientId", rs.getString(1));
					json_response.put("ShareName", rs.getString(2));
					json_response.put("ShareQty", rs.getInt(3));
					// json_response.put("ClientID", rs.getInt(4));
					json_response.put("Balance", rs.getInt(5));
					System.out.println("The JSON object is " + json_response);
					al.add(json_response.toString());
					}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		//return al;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Access-Control-Allow-Origin","*");
		return new ResponseEntity<ArrayList>(al, responseHeaders, HttpStatus.CREATED);
	}
	@RequestMapping(value = "/Bank", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ResponseUpgrade> update(@RequestBody ResponseUpgrade response) {
		// ResponseUpgrade response= null;
		if (response != null) {
			Connection connection;
			try {
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3302/bankdb", "root", "");

				// getting the seller shares
				// Create Statement

				// ClientID,ShareName, ShareQty,ClientID, Balance
				// Buyer, Seller, share_name, amt_shares, share_price
		
				//Buyer Shares Updation
				String q = "SELECT * FROM client WHERE client.ClientID='" + response.getBuyerName() + "' AND client.ShareName='"+response.getCompanyName()+"'";
				Statement st1 = connection.createStatement();
				ResultSet rs1 = st1.executeQuery(q);
				int final_numb_shares=0;
				int og_numb_shares = 0;
				if(rs1.wasNull())
				{
					String r = "INSERT INTO client VALUES ('"+response.getBuyerName()+"','"+response.getCompanyName()+"',"+response.getNumberOfShares()+");";
					Statement st2 = connection.createStatement();
					st2.executeUpdate(r);
				}
				else {
				while (rs1.next()) {
						og_numb_shares=rs1.getInt(3);
				}
				final_numb_shares=og_numb_shares+response.getNumberOfShares();
				String j = "UPDATE client SET ShareQty=" + final_numb_shares + " WHERE ClientID='" + response.getBuyerName()+ "' AND ShareName='"+response.getCompanyName()+"'";
				Statement st3 = connection.createStatement();
				st3.executeUpdate(j);
				}
				// Buyer Balance Updation
				String s = "SELECT * FROM balance WHERE ClientID='" + response.getBuyerName() +"'";
				Statement st4 = connection.createStatement();
				ResultSet rs4 = st4.executeQuery(s);
				int og_bal = 0;			
				while (rs4.next()) {
					og_bal=rs4.getInt(2);
				}
				int final_bal=og_bal-(response.getNumberOfShares()*response.getStockPrice());
				String k = "UPDATE balance SET Balance=" + final_bal + " WHERE ClientID='" + response.getBuyerName()+ "'";
				Statement st5 = connection.createStatement();
				st5.executeUpdate(k);
				
				
				String t = "SELECT * FROM client WHERE client.ClientID='" + response.getSellerName() + "' AND client.ShareName='"+response.getCompanyName()+"'";
				Statement st6 = connection.createStatement();
				ResultSet rs5 = st6.executeQuery(t);
				int final_numb_shares_seller=0;
				int og_numb_shares_seller = 0;
				while (rs5.next()) {
						og_numb_shares_seller=rs5.getInt(3);
				}
				final_numb_shares_seller=og_numb_shares_seller-response.getNumberOfShares();
				String l = "UPDATE client SET ShareQty=" + final_numb_shares_seller + " WHERE ClientID='" + response.getSellerName()+ "' AND ShareName='"+response.getCompanyName()+"'";
				Statement st7 = connection.createStatement();
				st7.executeUpdate(l);
				
				// Buyer Balance Updation
				String u = "SELECT * FROM balance WHERE ClientID='" + response.getSellerName() +"'";
				Statement st8 = connection.createStatement();
				ResultSet rs7 = st8.executeQuery(u);
				int og_bal_seller = 0;			
				while (rs7.next()) {
					og_bal_seller=rs7.getInt(2);
				}
				int final_bal_seller=og_bal_seller+(response.getNumberOfShares()*response.getStockPrice());
				String m = "UPDATE balance SET Balance=" + final_bal_seller + " WHERE ClientID='" + response.getSellerName()+ "'";
				Statement st9 = connection.createStatement();
				st9.executeUpdate(m);	
				
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
		return new ResponseEntity<ResponseUpgrade>(response, HttpStatus.OK);
	}
	
}
