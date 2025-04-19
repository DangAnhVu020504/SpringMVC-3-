<%@page import="com.abc.entities.*" %>
<%@page import="java.util.*" %>
<%@page contentType="text/html; charset=UTF-8" %>

<%
    User user = (User) request.getAttribute("user");
    Map<String, String> errors = (Map<String, String>) request.getAttribute("errors");
    List<Province> provinces = (List<Province>) request.getAttribute("provinces");
%>

<html>
<head>
    <title>Chỉnh sửa hồ sơ</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2>Chỉnh sửa hồ sơ</h2>
    <form method="post" action="<%= request.getContextPath() %>/edit">
        <div class="mb-3">
            <label>Email:</label>
            <input type="text" class="form-control" name="email" value="<%= user.getEmail() %>">
            <span class="text-danger"><%= (errors != null && errors.get("email") != null) ? errors.get("email") : "" %></span>
        </div>
        <div class="mb-3">
            <label>Ngày sinh:</label>
            <input type="date" class="form-control" name="birthday" value="<%= user.getBirthday() != null ? user.getBirthday().toString() : "" %>">
            <span class="text-danger"><%= (errors != null && errors.get("birthday") != null) ? errors.get("birthday") : "" %></span>
        </div>
        <div class="mb-3">
            <label>Thành phố:</label>
            <select name="provinceId" class="form-select">
                <%
                    if (provinces != null) {
                        for (Province p : provinces) {
                            boolean selected = user.getProvince() != null && p.getIdProvince() == user.getProvince().getIdProvince();
                %>
                            <option value="<%= p.getIdProvince() %>" <%= selected ? "selected" : "" %>><%= p.getNameProvince() %></option>
                <%
                        }
                    }
                %>
            </select>
        </div>
        <button type="submit" class="btn btn-success">Lưu thay đổi</button>
        <a href="<%= request.getContextPath() %>/profile" class="btn btn-secondary">Hủy</a>
    </form>
</div>
</body>
</html>
