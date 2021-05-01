<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="adoptions">
    <h2>Adopciones disponibles</h2>

    <table id="adoptionsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Descripción</th>
            <th style="width: 150px;">Nombre de la mascota</th>
            <th style="width: 150px;">Tipo de mascota</th>
            <th style="width: 150px;">Dueño original</th>
            <th style="width: 150px;">Opciones</th>
        </tr>
        </thead>
        <tbody>
			<c:forEach items="${adoptions}" var="adoption">
				<tr>
					<td><c:out value="${adoption.description}" /></td>
					<td><c:out value="${adoption.pet.name}" /></td>
					<td><c:out value="${adoption.pet.type}" /></td>
					<td><c:out value="${adoption.owner.firstName} ${adoption.owner.lastName}" /></td>
						<td>
							<c:if test="${mine == true}">
							<spring:url value="/adoptions/{adoptionId}" var="detailsUrl"> <spring:param name="adoptionId" value="${adoption.id}" /> </spring:url> 
								<a href="${fn:escapeXml(detailsUrl)}" class="btn btn-default">Detalles</a>
							<spring:url value="/adoptions/{adoptionId}/edit" var="editUrl"> <spring:param name="adoptionId" value="${adoption.id}" /> </spring:url> 
								<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar</a>
                  			<spring:url value="/adoptions/{adoptionId}/delete" var="deleteUrl"> <spring:param name="adoptionId" value="${adoption.id}" /> </spring:url>
                  				<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-default">Eliminar</a>
                  			</c:if>
                  			
                  			<c:if test="${mine == false}">
                  			<spring:url value="/adoptions/{adoptionId}/request/new" var="requestUrl"> <spring:param name="adoptionId" value="${adoption.id}" /> </spring:url>
                  				<a href="${fn:escapeXml(requestUrl)}" class="btn btn-default">Petición de adopción</a>
                  			</c:if>
						</td>
				</tr>
			</c:forEach>
		</tbody>
    </table>
    
    <h2>Adopciones cerradas</h2>
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
			<c:forEach items="${closedAdoptions}" var="adoption">
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