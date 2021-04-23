<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

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
        <c:forEach items="${causesByOwner}" var="cause">
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
                <td><sec:authorize access="hasAuthority('owner')">
                	<spring:url value="/myCauses/{causeId}/delete" var="delete"> <spring:param name="causeId" value="${cause.id}" />
					          </spring:url> <a href="${fn:escapeXml(delete)}" class="btn btn-default">Eliminar</a></sec:authorize>
                  	<spring:url value="/myCauses/{causeId}/edit" var="edit"> <spring:param name="causeId" value="${cause.id}" />
					          </spring:url> <a href="${fn:escapeXml(edit)}" class="btn btn-default">Editar</a>
				</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    <spring:url value="/myCauses/create" var="newCause">
					          </spring:url> <a href="${fn:escapeXml(newCause)}" class="btn btn-default">Crear nueva causa</a>
</petclinic:layout>