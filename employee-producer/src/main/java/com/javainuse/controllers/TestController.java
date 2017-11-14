package com.javainuse.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;


@RestController
public class TestController {

	@RequestMapping(value = "/Bank", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody String clientDetails(@RequestParam("id") String id) {
		System.out.println(id);
		String json = null;
		JSONObject json_response=null;
		if (id != null) {
			try {
				// Get Connection
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3302/bankdb", "root", "");

				// Create Statement
				Statement st = connection.createStatement();

				// ResultSet
				ResultSet rs = st.executeQuery("SELECT * FROM client\n" + "INNER JOIN demat\n"
						+ "ON client.DematID=demat.DematID\n" + "WHERE client.ClientID=" + id);

				String res;
				while (rs.next()) {
//					System.out.println("Your Client ID is " + rs.getInt("ClientID"));
//					System.out.println("Your Client Name " + rs.getString("ClientName"));
//					System.out.println("Your Balance is " + rs.getInt("Balance"));
//					System.out.println("Your Demat ID is " + rs.getInt("DematID"));
//					// System.out.println("Your Demat ID is " + rs.getInt("DematID"));
//					System.out.println("Your Share Name is " + rs.getString("ShareName"));
//					System.out.println("Your Share Quantity is " + rs.getInt("ShareQty"));

					res = (rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3) + " " + rs.getInt(5) + " "
							+ rs.getString(6) + " " +rs.getInt(7));
					json_response = new JSONObject();
					json_response.put("Clientid", rs.getInt(1));
					//json_response.put("ClientName", rs.getString(2));
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
//		return new ResponseEntity<JSONObject>(json_response, HttpStatus.OK);

	}

}
