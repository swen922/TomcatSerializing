package app;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class ReadWrapper extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);

        byte[] input = new byte[req.getContentLength()];

        ServletInputStream sin = req.getInputStream();
        int c = 0;
        int count = 0;
        while ((c = sin.read(input, count, (input.length - count))) > 0) {
            count += 1;
        }
        sin.close();

        String received = new String(input);

        ObjectMapper mapper = new ObjectMapper();

        MockDataWrapper mockDataWrapper = mapper.readValue(received, MockDataWrapper.class);

        StringBuilder sb = new StringBuilder();
        sb.append("numberOne = ").append(mockDataWrapper.getNumberOne()).append("   \n");
        sb.append("myMap.get(2) = ").append(mockDataWrapper.getMyMap().get(2)).append("   \n");
        sb.append("names.get(2) = ").append(mockDataWrapper.getNames().get(2));

        resp.setStatus(200);
        OutputStreamWriter out = new OutputStreamWriter(resp.getOutputStream());
        out.write(sb.toString());
        out.flush();
        out.close();
    }
}
