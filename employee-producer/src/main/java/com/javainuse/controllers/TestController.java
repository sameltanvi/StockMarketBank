package com.javainuse.controllers;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONObject;
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
	public @ResponseBody String clientDetails(@RequestParam("id") String id) {
		JSONObject json_response = null;
		if (id != null) {
			try {
				// Get Connection
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3302/bankdb", "root", "");

				// Create Statement
				Statement st = connection.createStatement();

				// ResultSet
				ResultSet rs = st.executeQuery("SELECT * FROM client\n" + "INNER JOIN demat ON client.DematID=demat.DematID\n" + "WHERE client.ClientID='" + id+"'");
				while (rs.next()) {
					json_response = new JSONObject();
					json_response.put("Clientid", rs.getString(1));
					// json_response.put("ClientName", rs.getString(2));
					json_response.put("Balance", rs.getInt(3));
					json_response.put("DematID", rs.getInt(4));
					json_response.put("ShareName", rs.getString(6));
					json_response.put("ShareQty", rs.getInt(7));
					System.out.println("The JSON object is " + json_response);

				}

			}

			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return json_response.toString();
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

				// ClientID, ClientName, Balance, DematID, DematID ,ShareName, ShareQty,
				
				// Buyer Updation
				String q = "SELECT * FROM client INNER JOIN demat ON client.DematID=demat.DematID WHERE ClientID='" + response.getBuyer()+"'";
				int og_bal = 0;
				int og_numb_shares = 0;
				int demat_id_buyer=0;
				Statement st1 = connection.createStatement();
				ResultSet rs1 = st1.executeQuery(q);
				while (rs1.next()) {
					og_bal = rs1.getInt(3);
					og_numb_shares = rs1.getInt(7);
					demat_id_buyer=rs1.getInt(4);
				}
				int final_shares_buyer = og_numb_shares + response.getNumber_of_shares();
				int final_bal_buyer = (int) (og_bal - (response.getNumber_of_shares() * response.getStock_price()));
				String j = "UPDATE client SET Balance=" + final_bal_buyer + " WHERE ClientID='" + response.getBuyer()+"'";
				Statement st2 = connection.createStatement();
				st2.executeUpdate(j);
				String k = "UPDATE demat SET ShareQty=" + final_shares_buyer + " WHERE DematID="+demat_id_buyer;
				Statement st3 = connection.createStatement();
				st3.executeUpdate(k);
				// Seller Updation
				String r = "SELECT * FROM client  INNER JOIN demat ON client.DematID=demat.DematID  WHERE client.ClientID='" + response.getSeller()+"'";
				int og_bal1 = 0;
				int og_numb_shares1 = 0;
				int demat_id_seller=0;
				Statement st4 = connection.createStatement();
				ResultSet rs2 = st4.executeQuery(r);
				while (rs2.next()) {
					og_bal1 = rs2.getInt(3);
					og_numb_shares1 = rs2.getInt(7);
					demat_id_seller=rs2.getInt(4);
				}
				int final_shares_seller = og_numb_shares1 - response.getNumber_of_shares();
				int final_bal_seller = (int) (og_bal1 + (response.getNumber_of_shares() * response.getStock_price()));
				String l = "UPDATE client SET Balance="+ final_bal_seller+" WHERE ClientID='" + response.getSeller()+ "'";
				Statement st5 = connection.createStatement();
				st5.executeUpdate(l);

				String m = "UPDATE demat SET ShareQty=" + final_shares_seller + " WHERE DematID="+demat_id_seller;
				Statement st6 = connection.createStatement();
				st6.executeUpdate(m);
				connection.close();
			} catch (SQLException e) {
				//TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new ResponseEntity<ResponseUpgrade>(response, HttpStatus.OK);
	}
}
