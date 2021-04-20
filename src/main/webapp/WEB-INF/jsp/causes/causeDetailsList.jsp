<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="causes">
	<h2>Causa</h2>
    <table id="causesTable" class="table table-striped">
        <thead>
        <tr>
            <th>Nombre</th>
            <th>Total recaudado</th>
            <th>Objetivo</th>
            <th>ONG</th>
            <th>Descripción</th>
        </tr>
        </thead>
        <tbody>
            <tr>
                <td>
                    <c:out value="${cause.name}"/>
                </td>
                <td>
                    <c:out value="${cause.gathered}"/>
                </td>
                <td>
                    <c:out value="${cause.target}"/>
                </td>
                <td>
                    <c:out value="${cause.ngo}"/>
                </td>
                <td>
                    <c:out value="${cause.description}"/>
                </td>
            </tr>
        </tbody>
    </table>
    
    <h2>Donaciones</h2>
    <table id="causesTable" class="table table-striped">
        <thead>
        <tr>
            <th>Donador</th>
            <th>Fecha de la donacion</th>
            <th>Cantidad</th>
        </tr>
        </thead>
        <tbody>
         <c:forEach items="${donations}" var="donation">
            <tr>
                <td>
                    <c:out value="${donation.owner.firstName} ${donation.owner.lastName}"/>
                </td>
                <td>
                    <c:out value="${donation.date}"/>
                </td>
                <td>
                    <c:out value="${donation.amount}"/>
                </td>
            </tr>
       	 </c:forEach>
        </tbody>
    </table>
   	<spring:url value="/causes" var="back"></spring:url>	
    <a class="btn btn-default" href="${fn:escapeXml(back)}">Volver</a>
</petclinic:layout>