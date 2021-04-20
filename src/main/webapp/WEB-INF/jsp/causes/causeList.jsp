<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="causes">
	<h2>Causas</h2>
    <table id="causesTable" class="table table-striped">
        <thead>
        <tr>
            <th>Nombre</th>
            <th>Total recaudado</th>
            <th>Objetivo</th>
            <th>Opciones</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${causes}" var="cause">
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
                	<spring:url value="/causes/{causeId}/donation" var="donationUrl"> <spring:param name="causeId" value="${cause.id}" />
					          </spring:url> <a href="${fn:escapeXml(donationUrl)}" class="btn btn-default">Donar</a>
                  	<spring:url value="/causes/{causeId}/details" var="detailsUrl"> <spring:param name="causeId" value="${cause.id}" />
					          </spring:url> <a href="${fn:escapeXml(detailsUrl)}" class="btn btn-default">Ver detalles</a>
				</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>