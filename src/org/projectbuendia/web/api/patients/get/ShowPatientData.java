package org.projectbuendia.web.api.patients.get;

import org.projectbuendia.server.Server;
import org.projectbuendia.sqlite.SQLiteQuery;
import org.projectbuendia.web.api.ApiInterface;
import org.projectbuendia.web.api.SharedFunctions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wwadewitte on 10/11/14.
 */
public class ShowPatientData implements ApiInterface {
    @Override
    public void call(final HttpServletRequest request, final HttpServletResponse response,final HashMap<String, String> urlVariables, final Map<String, String[]> parameterMap, final HashMap<String, String> payLoad){

        final String[] responseText = new String[]{null};

        SQLiteQuery checkQuery = new SQLiteQuery("SELECT * FROM `patients` WHERE `id` = '"+urlVariables.get("id")+"'  ") {

            @Override
            public void execute(ResultSet result) throws SQLException {
                while (result.next()) {
                    responseText[0] = SharedFunctions.SpecificPatientResponse(result);
                }
            }
        };

        Server.getLocalDatabase().executeQuery(checkQuery);

        while(responseText[0] == null) {
            // wait until the response is given
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        response.setStatus(HttpServletResponse.SC_OK);
        try {
            response.getWriter().write(responseText[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
