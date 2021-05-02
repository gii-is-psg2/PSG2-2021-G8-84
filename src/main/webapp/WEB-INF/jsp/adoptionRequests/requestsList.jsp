<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="adoptions">
    <h1>Mis peticiones de adopción abiertas</h1>

    <table id="adoptionsTable" class="table table-striped">
    	<thead>
        <tr>
        	<th style="width: 50px;">Mascota</th>
            <th style="width: 50px;">Dueño original</th>
            <th style="width: 300px;">Mensaje adjunto</th>
            <th style="width: 50px;">Opciones</th>
        </tr>
        </thead>
        <tbody>
			<c:forEach items="${adoptionRequests}" var="req">
				<tr>
					<td><c:out value="${req.adoption.pet.name} (${req.adoption.pet.type})" /></td>
					<td><c:out value="${req.adoption.pet.owner.firstName} ${req.adoption.pet.owner.lastName}" /></td>
					<td><c:out value="${req.description}" /></td>
					<td>
						<spring:url value="/adoptions/{adoptionId}/request/{arId}/edit" var="editUrl"> <spring:param name="adoptionId" value="${req.adoption.id}" /> <spring:param name="arId" value="${req.id}"/> </spring:url> 
							<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar</a>
						<spring:url value="/adoptions/{adoptionId}/request/{arId}/delete" var="deleteUrl"> <spring:param name="adoptionId" value="${req.adoption.id}" /> <spring:param name="arId" value="${req.id}"/> </spring:url> 
							<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-default">Borrar</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
    </table>
    
    <h1>Mis peticiones de adopción cerradas</h1>
    <table id="adoptionsTable" class="table table-striped">
    	<thead>
        <tr>
        	<th style="width: 50px;">Mascota</th>
            <th style="width: 50px;">Nuevo dueño</th>
            <th style="width: 300px;">Mensaje adjunto</th>
        </tr>
        </thead>
        <tbody>
			<c:forEach items="${closedRequests}" var="req">
				<tr>
					<td><c:out value="${req.adoption.pet.name} (${req.adoption.pet.type})" /></td>
					<td><c:out value="${req.adoption.pet.owner.firstName} ${req.adoption.pet.owner.lastName}" /></td>
					<td><c:out value="${req.description}" /></td>
				</tr>
			</c:forEach>
		</tbody>
    </table>
</petclinic:layout>