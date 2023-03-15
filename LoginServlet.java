import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    try (Connection connection = DatabaseConnection.getConnection()) {
      String query = "SELECT id FROM users WHERE username = ? AND password = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, username);
      statement.setString(2, password);
      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        int userId = resultSet.getInt("id");
        HttpSession session = request.getSession();
        session.setAttribute("userId", userId);
        response.sendRedirect("dashboard.jsp");
      } else {
        response.sendRedirect("login.jsp?error=Invalid credentials");
      }
    } catch (SQLException e) {
      e.printStackTrace();
      response.sendRedirect("login.jsp?error=Database error");
    }
  }
}
