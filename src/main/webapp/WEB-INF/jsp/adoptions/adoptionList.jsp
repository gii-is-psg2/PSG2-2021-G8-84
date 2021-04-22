<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="adoptions">
    <h2>Adopciones</h2>

    <table id="adoptionsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Descripción</th>
            <th style="width: 150px;">Nombre de la mascota</th>
            <th style="width: 150px;">Tipo de mascota</th>
            <th style="width: 150px;">Dueño original</th>
        </tr>
        </thead>
        <tbody>
			<c:forEach items="${adoptions}" var="adoption">
				<tr>
					<td><c:out value="${adoption.description}" /></td>
					<td><c:out value="${adoption.pet.name}" /></td>
					<td><c:out value="${adoption.pet.type}" /></td>
					<td><c:out value="${adoption.owner.firstName} ${adoption.owner.lastName}" /></td>
				</tr>
			</c:forEach>
		</tbody>
    </table>
    			
	<spring:url value="/adoptions/new" var="newAdoption"></spring:url>
	<a class="btn btn-default" href="${fn:escapeXml(newAdoption)}">Poner una mascota en adopción</a>
</petclinic:layout>