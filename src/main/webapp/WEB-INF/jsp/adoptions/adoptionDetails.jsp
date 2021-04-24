<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="adoptions">
    <h1>Detalle de la adopción:</h1>

	<p>Mascota en adopción:</p>
	<p><b><c:out value="${adoption.pet.name} (${adoption.pet.type})" /></b></p>
    <table id="adoptionsTable" class="table table-striped">
    	<thead>
        <tr>
            <th style="width: 50px;">Dueño</th>
            <th style="width: 350px;">Mensaje adjunto</th>
            <th style="width: 50px;">Opciones</th>
        </tr>
        </thead>
        <tbody>
			<c:forEach items="${adoption.adoptionRequests}" var="req">
				<tr>
					<td><c:out value="${req.owner.firstName} ${req.owner.lastName}" /></td>
					<td><c:out value="${req.description}" /></td>
					<td>
						<spring:url value="/adoptions/{adoptionId}/request/{arId}/close" var="closeUrl"> <spring:param name="adoptionId" value="${adoption.id}" /> <spring:param name="arId" value="${req.id}"/> </spring:url> 
							<a href="${fn:escapeXml(closeUrl)}" class="btn btn-default">Aceptar petición</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
    </table>
</petclinic:layout>