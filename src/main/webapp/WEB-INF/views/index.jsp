<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Greengrocery Web Content</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
     <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="/js/index.js"></script>
</head>
<body>
    분류 : <select id="category" onchange="getList()">
        <option value="fruit" selected="selected">과일</option>
        <option value="vegetable">채소</option>
    </select>
    이름 : <select id="kinds"></select>
    <button onclick="getPrice()">조회</button>
    <br>
    <br>
    <div id="priceArea"></div>
</body>
</html>