package com.webservice.soap.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpSoapTest {
    public static void main(String[] args) {
        String uri = "http://localhost:8080/hello/HelloApi.wsdl";
        try {
            URL url = new URL(uri);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            // httpURLConnection.setRequestProperty("content-type", "text/xml;charset=utf-8"); soap1.1
            httpURLConnection.setRequestProperty("content-type", "application/soap+xml;charset=UTF-8");// soap1.2
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            String soapXML = getXML();
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(soapXML.getBytes());
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader bufferedReader =
                        new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String temp;
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp);
                }
                System.out.println("result:{}" + stringBuilder.toString());
                bufferedReader.close();
            } else {
                System.out.println("resultCode:{}" + responseCode);
            }
            outputStream.close();
            httpURLConnection.disconnect();
        } catch (Exception e) {
            System.out.println("error:{}" + e.getMessage());
        }
    }

    public static String getXML() {
        String soapXML =
                "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">"
                        + "  <soap:Header>"
                        + "  </soap:Header>"
                        + "  <soap:Body >"
                        + "    <ns2:HelloRequest xmlns:ns2=\"http://service.ws.webservice.com\">"
                        + "      <ns2:HelloReq>"
                        + "        <username>test</username>"
                        + "      </ns2:HelloReq>"
                        + "    </ns2:HelloRequest>"
                        + "  </soap:Body>"
                        + "</soap:Envelope>";
        return soapXML;
    }
}
