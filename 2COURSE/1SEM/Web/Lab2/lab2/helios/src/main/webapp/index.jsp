<%@ page import="org.example.Result" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Lab</title>
    <link rel="stylesheet" href="styles.css">
    <script src="globalVariables.js" defer></script>
    <script src="init.js" defer></script>
    <script src="validation.js" defer></script>
    <script src="drawing.js" defer></script>
    <script src="canvasClick.js" defer></script>
</head>

<body>
<div class="container">
    <header>
        <h1>Лабораторная работа 2</h1>
        <p>Раевский Григорий, P3221, Вар. 2133</p>
    </header>

    <section>
        <form id="data_form" action="controller" method="GET">
            <table>
                <tr>
                    <td>Выберите X:</td>
                    <td>
                        <input type="checkbox" id="x_-3" name="x" value="-3">-3
                        <input type="checkbox" id="x_-2" name="x" value="-2">-2
                        <input type="checkbox" id="x_-1" name="x" value="-1">-1
                        <input type="checkbox" id="x_0" name="x" value="0">0
                        <input type="checkbox" id="x_1" name="x" value="1">1
                        <input type="checkbox" id="x_2" name="x" value="2">2
                        <input type="checkbox" id="x_3" name="x" value="3">3
                        <input type="checkbox" id="x_4" name="x" value="4">4
                        <input type="checkbox" id="x_5" name="x" value="5">5
                    </td>
                </tr>

                <tr>
                    <td>
                        <label for="y_input">Введите Y (-5 до 5):</label>
                    </td>
                    <td>
                        <input type="text" id="y_input" name="y" required>
                    </td>
                </tr>
                <tr>
                    <td>Выберите R:</td>
                    <td>
                        <button type="button" class="r-button" data-value="1">1</button>
                        <button type="button" class="r-button" data-value="1.5">1.5</button>
                        <button type="button" class="r-button" data-value="2">2</button>
                        <button type="button" class="r-button" data-value="2.5">2.5</button>
                        <button type="button" class="r-button" data-value="3">3</button>
                        <input type="hidden" id="r_input" name="r" required>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="Проверить">
                    </td>
                </tr>
            </table>
        </form>
    </section>

    <section>
        <canvas id="graph" width="700" height="700" ></canvas>
    </section>

    <section>
        <table>
            <thead>
            <tr>
                <th>X</th>
                <th>Y</th>
                <th>R</th>
                <th>Результат</th>
                <th>Текущее время</th>
                <th>Время вычисления</th>
            </tr>
            </thead>
            <tbody id="results_table">
            <%
                List<Result> results = (List<Result>) session.getAttribute("results");
                if (results != null && !results.isEmpty()) {
                    for (Result result : results) {
            %>
            <tr>
                <td><%= String.format("%.2f", result.getX()) %></td>
                <td><%= String.format("%.2f", result.getY()) %></td>
                <td><%= String.format("%.2f", result.getR()) %></td>
                <td><%= result.isHit() ? "Hit" : "Miss" %></td>
                <td><%= result.getCurrentTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")) %></td>
                <td><%= result.getExecutionTime() + " ms" %></td>
            </tr>
            <%
                    }
                }
            %>



            </tbody>
        </table>
    </section>
</div>
</body>

</html>
